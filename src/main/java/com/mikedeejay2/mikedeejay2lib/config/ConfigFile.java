package com.mikedeejay2.mikedeejay2lib.config;

import com.mikedeejay2.mikedeejay2lib.BukkitPlugin;
import com.mikedeejay2.mikedeejay2lib.data.DataFile;
import com.mikedeejay2.mikedeejay2lib.data.json.JsonFile;
import com.mikedeejay2.mikedeejay2lib.data.section.SectionAccessor;
import com.mikedeejay2.mikedeejay2lib.data.section.SectionInstancer;
import com.mikedeejay2.mikedeejay2lib.data.yaml.YamlFile;
import org.apache.commons.io.FilenameUtils;
import org.bukkit.*;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.block.banner.Pattern;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.util.BlockVector;
import org.bukkit.util.BoundingBox;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;
import java.util.function.*;

/**
 * A configuration file. The basis of this class is a {@link DataFile} with extra features such as handled
 * {@link ConfigValue configuration values}, {@link ValueType value types}, an {@link Updater updater}, and abstraction
 * of saving, loading, and resetting the file.
 * <p>
 * The main objects this class holds are {@link ConfigValue ConfigValues} and child {@link ConfigFile ConfigFiles}.
 * These objects can be added using {@link ConfigFile#value(ValueType, String)} and
 * {@link ConfigFile#child(ConfigFile)}.
 * <p>
 * To begin using this class, extend this class and create a {@link ConfigValue} using
 * {@link ConfigFile#value(ValueType, String)}. If you want to create your own {@link ValueType}, you can start with a
 * pre-existing type and add on to it using its built-in methods or create a new type using
 * {@link ValueType#of(ValueType.Loader, ValueType.Saver)}.
 * <p>
 * Recommended use is to create and load this config when enabling your plugin. Create the config using
 * {@link ConfigFile#ConfigFile(BukkitPlugin, String, FileType, boolean)}}, once it has been instantiated,
 * load it using {@link ConfigFile#load()}. The rest of the work will be done automatically (loading from disk, getting
 * the default configuration from jar if specified, filling the values, updating the config, saving the default config
 * if a file doesn't exist on the disk.). During plugin disabling, if your plugin modifies the config at run time, it's
 * modified state should be checked using {@link ConfigFile#isModified()}, and if it has been modified at, it should be
 * saved to disk using {@link ConfigFile#save()}.
 *
 * @author Mikedeejay2
 */
public class ConfigFile {
    /**
     * The {@link BukkitPlugin} instance.
     */
    protected final BukkitPlugin plugin;

    /**
     * The internal {@link DataFile}, used to manage the file on disk.
     */
    protected final DataFile dataFile;

    /**
     * The type of {@link DataFile} being used. Either YAML or JSON.
     */
    protected final FileType fileType;

    /**
     * The list of stored {@link ConfigValue ConfigValues}. This list is automatically managed through
     * {@link ConfigValue#value(ValueType, String)}, values should never be manually added to this list.
     */
    protected final List<ConfigValue<?>> values;

    /**
     * The list of child {@link ConfigFile ConfigFiles}. This list is automatically managed through
     * {@link ConfigValue#child(ConfigFile)}, values should never be manually added to this list.
     */
    protected final List<ConfigFile> children;

    /**
     * Whether the file should be loaded from the plugin's jar file. This assumes that a file of the same name exists
     * within the jar file.
     */
    protected final boolean loadFromJar;

    /**
     * The {@link Updater} for this configuration.
     */
    protected final Updater updater;

    /**
     * The parent {@link ConfigFile}, only non-null if this config is a child of a parent config.
     */
    protected @Nullable ConfigFile parent;

    /**
     * Whether this config has been modified (a value has been updated) since being loaded.
     */
    protected boolean modified;

    /**
     * Whether this config has been loaded (using {@link ConfigFile#load()})
     */
    protected boolean loaded;

    /**
     * Create a new {@link ConfigFile}
     *
     * @param plugin      The {@link BukkitPlugin} instance
     * @param configPath  Path from the plugins folder to the file. (This DOES NOT include
     *                    <code>plugin.getDataFolder()</code>)
     * @param fileType    The type of {@link DataFile} being used. Either YAML or JSON
     * @param loadFromJar Whether the file should be loaded from the plugin's jar file. This assumes that a file of the
     *                    same name exists within the jar file
     */
    protected ConfigFile(BukkitPlugin plugin, String configPath, FileType fileType, boolean loadFromJar) {
        this.fileType = fileType;
        this.plugin = plugin;
        this.dataFile = fileType.create(plugin, configPath);
        this.modified = false;
        this.loaded = false;
        this.values = new ArrayList<>();
        this.children = new ArrayList<>();
        this.loadFromJar = loadFromJar;
        this.updater = new Updater(this);
        this.parent = null;
    }

    /**
     * Add a {@link ConfigValue} to this config.
     *
     * @param type         The {@link ValueType} of the value
     * @param path         The path to the value in the file. This includes depth, separated by a <code>.</code> for
     *                     each level. For example, "<code>root.level1.level2.name</code>"
     * @param defaultValue The default value to use if a value is not on the disk or in the jar file
     * @param <T>          The data type that is being stored within the value
     * @return The new {@link ConfigValue}
     */
    protected <T> ConfigValue<T> value(ValueType<T> type, String path, T defaultValue) {
        final ConfigValue<T> value = new ConfigValue<>(this, type, path, defaultValue);
        this.values.add(value);
        return value;
    }

    /**
     * Add a {@link ConfigValue} to this config.
     *
     * @param type                 The {@link ValueType} of the value
     * @param path                 The path to the value in the file. This includes depth, separated by a <code>.</code>
     *                             for each level. For example, "<code>root.level1.level2.name</code>"
     * @param defaultValueSupplier The default value supplier to use if a value is not on the disk or in the jar file
     * @param <T>                  The data type that is being stored within the value
     * @return The new {@link ConfigValue}
     */
    protected <T> ConfigValue<T> value(ValueType<T> type, String path, Supplier<T> defaultValueSupplier) {
        return value(type, path, defaultValueSupplier.get());
    }

    /**
     * Add a {@link ConfigValue} to this config.
     *
     * @param type The {@link ValueType} of the value
     * @param path The path to the value in the file. This includes depth, separated by a <code>.</code> for each level.
     *             For example, "<code>root.level1.level2.name</code>"
     * @param <T>  The data type that is being stored within the value
     * @return The new {@link ConfigValue}
     */
    protected <T> ConfigValue<T> value(ValueType<T> type, String path) {
        return value(type, path, (T) null);
    }

    /**
     * Add a {@link ConfigValue} of a {@link Collection} type to this config. A default value is required, as it must be
     * a new or existing instantiation of a collection. The collection will never be re-instantiated or recreated in
     * this value, only cleared and refilled.
     *
     * @param type         The {@link ValueType} of the value
     * @param path         The path to the value in the file. This includes depth, separated by a <code>.</code> for
     *                     each level. For example, "<code>root.level1.level2.name</code>"
     * @param defaultValue The default value to use. This should never be null, it needs to be a new or existing
     *                     instantiation of a collection data type.
     * @param <T>          The data type that is being stored within the value
     * @return The new {@link ConfigValue}
     */
    protected <T extends Collection<E>, E> ConfigValueCollection<T, E> collectionValue(ValueType<T> type, String path, T defaultValue) {
        final ConfigValueCollection<T, E> value = new ConfigValueCollection<>(this, type, path, defaultValue);
        this.values.add(value);
        return value;
    }

    /**
     * Add a {@link ConfigValue} of a {@link Collection} type to this config. A default value is required, as it must be
     * a new or existing instantiation of a collection. The collection will never be re-instantiated or recreated in
     * this value, only cleared and refilled.
     *
     * @param type                 The {@link ValueType} of the value
     * @param path                 The path to the value in the file. This includes depth, separated by a <code>.</code>
     *                             for each level. For example, "<code>root.level1.level2.name</code>"
     * @param defaultValueSupplier The default value supplier to use. This should never be null, it needs to be a new or
     *                             existing instantiation of a collection data type.
     * @param <T>                  The data type that is being stored within the value
     * @return The new {@link ConfigValue}
     */
    protected <T extends Collection<E>, E> ConfigValueCollection<T, E> collectionValue(ValueType<T> type, String path, Supplier<T> defaultValueSupplier) {
        final ConfigValueCollection<T, E> value = new ConfigValueCollection<>(this, type, path, defaultValueSupplier.get());
        this.values.add(value);
        return value;
    }

    /**
     * Add a {@link ConfigValue} of a {@link Map} type to this config. A default value is required, as it must be
     * a new or existing instantiation of a map. The map will never be re-instantiated or recreated in this value, only
     * cleared and refilled.
     *
     * @param type         The {@link ValueType} of the value
     * @param path         The path to the value in the file. This includes depth, separated by a <code>.</code> for
     *                     each level. For example, "<code>root.level1.level2.name</code>"
     * @param defaultValue The default value to use. This should never be null, it needs to be a new or existing
     *                     instantiation of a map data type.
     * @param <T>          The data type that is being stored within the value
     * @return The new {@link ConfigValue}
     */
    protected <T extends Map<K, V>, K, V> ConfigValueMap<T, K, V> mapValue(ValueType<T> type, String path, T defaultValue) {
        final ConfigValueMap<T, K, V> value = new ConfigValueMap<>(this, type, path, defaultValue);
        this.values.add(value);
        return value;
    }

    /**
     * Add a {@link ConfigValue} of a {@link Map} type to this config. A default value is required, as it must be
     * a new or existing instantiation of a map. The map will never be re-instantiated or recreated in this value, only
     * cleared and refilled.
     *
     * @param type                 The {@link ValueType} of the value
     * @param path                 The path to the value in the file. This includes depth, separated by a <code>.</code>
     *                             for each level. For example, "<code>root.level1.level2.name</code>"
     * @param defaultValueSupplier The default value supplier to use. This should never be null, it needs to be a new or
     *                             existing instantiation of a map data type.
     * @param <T>                  The data type that is being stored within the value
     * @return The new {@link ConfigValue}
     */
    protected <T extends Map<K, V>, K, V> ConfigValueMap<T, K, V> mapValue(ValueType<T> type, String path, Supplier<T> defaultValueSupplier) {
        final ConfigValueMap<T, K, V> value = new ConfigValueMap<>(this, type, path, defaultValueSupplier.get());
        this.values.add(value);
        return value;
    }

    /**
     * Add a {@link ConfigFile} child to this config. The child will be controlled by the parent config and will follow
     * all actions of the parent, such as saving and loading.
     *
     * @param configFile The {@link ConfigFile} to add as a child
     * @param <T>        The type of the {@link ConfigFile}
     * @return The {@link ConfigFile} child
     */
    protected <T extends ConfigFile> T child(T configFile) {
        this.children.add(configFile);
        configFile.parent = this;
        return configFile;
    }

    /**
     * Get this config's {@link Updater}
     *
     * @return This config's {@link Updater}
     */
    public Updater getUpdater() {
        return updater;
    }

    /**
     * Get whether this config has been modified (a value has been updated) since being loaded.
     *
     * @return Whether this config has been modified (a value has been updated) since being loaded
     */
    public boolean isModified() {
        return modified;
    }

    /**
     * Set whether this config has been modified (a value has been updated) since being loaded.
     *
     * @param modified New modified state
     */
    public void setModified(boolean modified) {
        this.modified = modified;
        if(this.parent != null) this.parent.setModified(true);
    }

    /**
     * Get whether this config has been loaded (using {@link ConfigFile#load()})
     *
     * @return Whether this config has been loaded
     */
    public boolean isLoaded() {
        return loaded;
    }

    /**
     * Get The list of stored {@link ConfigValue ConfigValues}. This list is automatically managed through
     * {@link ConfigValue#value(ValueType, String)}, values should never be manually added to this list.
     *
     * @return The list of {@link ConfigValue ConfigValues}
     */
    public List<ConfigValue<?>> getValues() {
        return values;
    }

    /**
     * Get the list of child {@link ConfigFile ConfigFiles}. This list is automatically managed through
     * {@link ConfigValue#child(ConfigFile)}, values should never be manually added to this list.
     *
     * @return The list of child {@link ConfigFile ConfigFiles}
     */
    public List<ConfigFile> getChildren() {
        return children;
    }

    /**
     * Get the type of {@link DataFile} being used. Either YAML or JSON.
     *
     * @return The {@link FileType} of this config
     */
    public FileType getFileType() {
        return fileType;
    }

    /**
     * Get whether the file should be loaded from the plugin's jar file. This assumes that a file of the same name
     * exists within the jar file.
     *
     * @return Whether the file should be loaded from the plugin's jar file
     */
    public boolean shouldLoadFromJar() {
        return loadFromJar;
    }

    /**
     * Load this config.
     * <p>
     * List of operations that this method performs:
     * <ul>
     *     <li>Perform preload updates ({@link Updater#updatePreLoad()})</li>
     *     <li>
     *          If file does not exist on disk: ({@link ConfigFile#internalFileExists()})
     *          <ul>
     *              <li>If config should load from jar, load from jar ({@link ConfigFile#internalLoadFromJar()})</li>
     *              <li>Save the config loaded from jar to disk ({@link ConfigFile#internalSaveToDisk()})</li>
     *          </ul>
     *     </li>
     *     <li>Else, load from disk ({@link ConfigFile#internalLoadFromDisk()})</li>
     * </ul>
     *
     * @return Whether loading was successful
     */
    public boolean load() {
        boolean success = true;
        this.updater.updatePreLoad();
        if(!internalFileExists()) {
            if(loadFromJar) success &= internalLoadFromJar();
            if(success) this.loaded = true;
            success &= internalSaveToDisk();
        } else {
            success = internalLoadFromDisk();
        }
        children.forEach(ConfigFile::load);
        return success;
    }

    /**
     * Save this config. If this config hasn't been loaded using {@link ConfigFile#load()}, this method will fail
     * automatically.
     *
     * @return Whether saving was successful
     */
    public boolean save() {
        if(!isLoaded()) return false;
        boolean success = internalSaveToDisk();
        children.forEach(ConfigFile::save);
        if(success) setModified(false);
        return success;
    }

    /**
     * Reload this config. If the config has been modified and not saved yet, this method will save the file to disk.
     * Else, this config will be reloaded from disk.
     *
     * @return Whether reloading was successful
     */
    public boolean reload() {
        final boolean success = isModified() ? save() : load();
        children.forEach(ConfigFile::reload);
        return success;
    }

    /**
     * Reset the config file on the disk to default values.
     *
     * @return Whether resetting was successful
     */
    public boolean reset() {
        if(!internalDelete()) return false;
        final boolean success = load();
        children.forEach(ConfigFile::reset);
        return success;
    }

    /**
     * Delete the config file on disk
     *
     * @return Whether deleting was successful
     */
    public boolean delete() {
        final boolean success = internalDelete();
        children.forEach(ConfigFile::delete);
        return success;
    }

    /**
     * Internal method for loading the config from jar. This method assumes that a default config file of the same name
     * exists within the jar file.
     *
     * @return Whether loading from jar was successful
     */
    protected boolean internalLoadFromJar() {
        boolean success = dataFile.loadFromJar(true);
        if(success) {
            values.forEach(ConfigValue::load);
            this.loaded = true;
        }
        return success;
    }

    /**
     * Internal method for saving the config to disk.
     *
     * @return Whether saving to disk was successful
     */
    protected boolean internalSaveToDisk() {
        if(!isLoaded()) return false;
        values.forEach(ConfigValue::save);
        return dataFile.saveToDisk(true);
    }

    /**
     * Internal method for loading from disk.
     * <p>
     * List of operations that this method performs:
     * <ul>
     *     <li>Load the {@link DataFile} from disk ({@link DataFile#loadFromDisk(boolean)})</li>
     *     <li>
     *         If this config file should be loaded from jar:
     *         <ul>
     *             <li>Update this config from jar, add any missing values ({@link ConfigFile#internalUpdateFromJar()})</li>
     *             <li>Save the updated values to disk ({@link ConfigFile#internalSaveToDisk()})</li>
     *         </ul>
     *     </li>
     *     <li>Run updater post-load operations ({@link Updater#updateOnLoad()})</li>
     * </ul>
     *
     * @return Whether loading from disk was successful
     */
    protected boolean internalLoadFromDisk() {
        boolean success = dataFile.loadFromDisk(true);
        if(loadFromJar) {
            internalUpdateFromJar();
            internalSaveToDisk();
        }
        if(success) {
            this.updater.updateOnLoad();
            values.forEach(ConfigValue::load);
            this.loaded = true;
        }
        return success;
    }

    /**
     * Internal method for updating from jar. This method assumes that a default config file of the same name exists
     * within the jar file.
     *
     * @return Whether updating from jar was successful
     */
    protected boolean internalUpdateFromJar() {
        return dataFile.updateFromJar(true);
    }

    /**
     * Internal method for finding whether the config file exists on disk.
     *
     * @return Whether the file exists on disk
     */
    protected boolean internalFileExists() {
        return dataFile.fileExists();
    }

    /**
     * Internal method for deleting the config file from disk.
     *
     * @return Whether deletion was successful
     */
    protected boolean internalDelete() {
        boolean success = dataFile.delete(true);
        this.loaded = false;
        return success;
    }

    /**
     * Get an accessor based off of a provided path. This will locate the path as an accessor, including depth. For
     * example, a path of "<code>root.level1.level2.name</code>" would result in an accessor for <code>level2</code> of
     * the path, which you could then get <code>name</code> from the accessor using
     * {@link SectionAccessor#get(String) <code>accessor.get("name")</code>}. The name can be obtained from the path
     * using {@link ConfigFile#getName(String)}.
     *
     * @param path The path to the value in the file. This includes depth, separated by a <code>.</code> for each level.
     *             For example, "<code>root.level1.level2.name</code>"
     * @return The specified {@link SectionAccessor} of the path
     */
    private SectionAccessor<DataFile, Object> getAccessor(String path) {
        final String[] splitPath = path.split("\\.");
        SectionAccessor<DataFile, Object> accessor = ((SectionInstancer<?, DataFile, Object>) dataFile).getAccessor();
        for(int i = 0; i < splitPath.length - 1; ++i) {
            accessor = accessor.getSection(splitPath[i]);
        }
        return accessor;
    }

    /**
     * Get the name (key) of a path String. For example, if a path is "<code>root.level1.level2.name</code>", the key,
     * or name within the path would be "name" at the end. This method extracts that key for use with a
     * {@link SectionAccessor} retrieved from {@link ConfigFile#getAccessor(String)}.
     *
     * @param path The path to the value in the file. This includes depth, separated by a <code>.</code> for each level.
     *             For example, "<code>root.level1.level2.name</code>"
     * @return The name (key) of a path
     */
    private String getName(String path) {
        final String[] splitPath = path.split("\\.");
        return splitPath[splitPath.length - 1];
    }

    /**
     * A configuration value. Stores a value of a specified {@link ValueType} and automatically manages it in the
     * configuration. To create a {@link ConfigValue}, see {@link ConfigFile#value(ValueType, String)}.
     * <p>
     * Common methods are the {@link ConfigValue#get()} and {@link ConfigValue#set(Object)} methods to modify the value.
     *
     * @param <T> The data type of the value
     * @author Mikedeejay2
     */
    public static class ConfigValue<T> {
        /**
         * The parent {@link ConfigFile} of this value.
         */
        protected final ConfigFile file;

        /**
         * The {@link ValueType} of this value. Used to load, save, and manage the value.
         */
        protected final ValueType<T> type;

        /**
         * The path to the value in the file. This includes depth, separated by a <code>.</code> for each level. For
         * example, "<code>root.level1.level2.name</code>"
         */
        protected final String path;

        /**
         * The name of the value in the file. Extracted from {@link ConfigValue#path} using
         * {@link ConfigFile#getName(String)}.
         */
        protected final String name;

        /**
         * The value being stored. Data type is specified via {@link ValueType}.
         */
        protected T value;

        /**
         * Internal constructor
         *
         * @param file         The parent {@link ConfigFile} of this value.
         * @param type         The {@link ValueType} of this value. Used to load, save, and manage the value.
         * @param path         The path to the value in the file. This includes depth, separated by a <code>.</code> for
         *                     each level. For example, "<code>root.level1.level2.name</code>"
         * @param defaultValue The default value to use if a value is not on the disk or in the jar file
         */
        private ConfigValue(ConfigFile file, ValueType<T> type, String path, T defaultValue) {
            this.file = file;
            this.type = type;
            this.path = path;
            this.name = file.getName(path);
            this.value = defaultValue;
        }

        /**
         * Get the value.
         *
         * @return The value
         */
        public T get() {
            return value;
        }

        /**
         * Set the value. This method will automatically set the parent {@link ConfigFile} as modified.
         *
         * @param value The new value
         */
        public void set(T value) {
            this.value = value;
            this.file.setModified(false);
        }

        /**
         * Load this value via {@link ValueType#load()}.
         */
        protected void load() {
            final T loaded = this.type.load(file.getAccessor(path), name);
            if(loaded != null) value = loaded;
        }

        /**
         * Save this value via {@link ValueType#save()}
         */
        protected void save() {
            this.type.save(file.getAccessor(path), name, value);
        }

        /**
         * Get the {@link ValueType} of this value. Used to load, save, and manage the value.
         *
         * @return The {@link ValueType}
         */
        public ValueType<T> getType() {
            return type;
        }

        /**
         * Get the path to the value in the file. This includes depth, separated by a <code>.</code> for each level. For
         * example, "<code>root.level1.level2.name</code>"
         *
         * @return The path to this value in the file
         */
        public String getPath() {
            return path;
        }

        /**
         * Get the name of the value in the file. Extracted from {@link ConfigValue#path} using
         * {@link ConfigFile#getName(String)}.
         *
         * @return The name of this value
         */
        public String getName() {
            return name;
        }
    }

    /**
     * A configuration value of a {@link Collection} type. Stores a value of a specified {@link ValueType} and
     * automatically manages it in the configuration. To create a {@link ConfigValue}, see
     * {@link ConfigFile#collectionValue(ValueType, String, Collection)}.
     * <p>
     * A default value is required, as it must be a new or existing instantiation of a collection. The collection will
     * never be re-instantiated or recreated in this value, only cleared and refilled.
     *
     * @param <T> The collection data type of the value
     * @param <E> The type maintained by the collection
     * @author Mikedeejay2
     */
    public static class ConfigValueCollection<T extends Collection<E>, E> extends ConfigValue<T> {
        /**
         * Internal constructor
         *
         * @param file         The parent {@link ConfigFile} of this value.
         * @param type         The {@link ValueType} of this value. Used to load, save, and manage the value.
         * @param path         The path to the value in the file. This includes depth, separated by a <code>.</code> for
         *                     each level. For example, "<code>root.level1.level2.name</code>"
         * @param defaultValue The default value to use. This should never be null, it needs to be a new or existing
         *                     instantiation of a collection data type.
         */
        private ConfigValueCollection(ConfigFile file, ValueType<T> type, String path, T defaultValue) {
            super(file, type, path, defaultValue);
        }

        /**
         * Overridden set method to clear the collection and add all new values.
         *
         * @param value The new value
         */
        @Override
        public void set(T value) {
            this.value.clear();
            this.value.addAll(value);
            this.file.setModified(true);
        }

        /**
         * Overridden load value to clear the existing collection and add all loaded values.
         */
        @Override
        public void load() {
            final T loaded = this.type.load(file.getAccessor(path), name);
            if(loaded != null) {
                this.value.clear();
                this.value.addAll(loaded);
            }
        }
    }

    /**
     * A configuration value of a {@link Map} type. Stores a value of a specified {@link ValueType} and
     * automatically manages it in the configuration. To create a {@link ConfigValue}, see
     * {@link ConfigFile#collectionValue(ValueType, String, Collection)}.
     * <p>
     * A default value is required, as it must be a new or existing instantiation of a map. The map will
     * never be re-instantiated or recreated in this value, only cleared and refilled.
     *
     * @param <T> The map data type of the value
     * @param <K> The type of keys maintained by the map
     * @param <V> The type of values maintained by the map
     * @author Mikedeejay2
     */
    public static class ConfigValueMap<T extends Map<K, V>, K, V> extends ConfigValue<T> {
        private ConfigValueMap(ConfigFile file, ValueType<T> type, String path, T defaultValue) {
            super(file, type, path, defaultValue);
        }

        /**
         * Overridden set method to clear the map and put all new key values.
         *
         * @param value The new value
         */
        @Override
        public void set(T value) {
            this.value.clear();
            this.value.putAll(value);
            this.file.setModified(true);
        }

        /**
         * Overridden load value to clear the existing map and add all loaded key values.
         */
        @Override
        public void load() {
            final T loaded = this.type.load(file.getAccessor(path), name);
            if(loaded != null) {
                this.value.clear();
                this.value.putAll(loaded);
            }
        }
    }

    /**
     * A type of value for use with {@link ConfigValue ConfigValues}. Pre-existing types or custom types can be used.
     * Pre-existing types can be found as static variables of this class, such as {@link ValueType#BOOLEAN} or
     * {@link ValueType#ITEM_STACK}. If you want to create your own {@link ValueType}, you can start with a pre-existing
     * type and add on to it using its built-in methods or create a new type using
     * {@link ValueType#of(ValueType.Loader, ValueType.Saver)}.
     *
     * @param <T> The type that is maintained
     * @author Mikedeejay2
     */
    public static final class ValueType<T> {
        public static final ValueType<Boolean> BOOLEAN = of(SectionAccessor::getBoolean, SectionAccessor::setBoolean);
        public static final ValueType<Integer> INTEGER = of(SectionAccessor::getInt, SectionAccessor::setInt);
        public static final ValueType<Float> FLOAT = of(SectionAccessor::getFloat, SectionAccessor::setFloat);
        public static final ValueType<Double> DOUBLE = of(SectionAccessor::getDouble, SectionAccessor::setDouble);
        public static final ValueType<Long> LONG = of(SectionAccessor::getLong, SectionAccessor::setLong);
        public static final ValueType<String> STRING = of(SectionAccessor::getString, SectionAccessor::setString);
        public static final ValueType<ItemStack> ITEM_STACK = of(SectionAccessor::getItemStack, SectionAccessor::setItemStack);
        public static final ValueType<Location> LOCATION = of(SectionAccessor::getLocation, SectionAccessor::setLocation);
        public static final ValueType<Vector> VECTOR = of(SectionAccessor::getVector, SectionAccessor::setVector);
        public static final ValueType<ItemMeta> ITEM_META = of(SectionAccessor::getItemMeta, SectionAccessor::setItemMeta);
        public static final ValueType<OfflinePlayer> PLAYER = of(SectionAccessor::getPlayer, SectionAccessor::setPlayer);
        public static final ValueType<AttributeModifier> ATTRIBUTE_MODIFIER = of(SectionAccessor::getAttributeModifier, SectionAccessor::setAttributeModifier);
        public static final ValueType<BlockVector> BLOCK_VECTOR = of(SectionAccessor::getBlockVector, SectionAccessor::setBlockVector);
        public static final ValueType<BoundingBox> BOUNDING_BOX = of(SectionAccessor::getBoundingBox, SectionAccessor::setBoundingBox);
        public static final ValueType<Color> COLOR = of(SectionAccessor::getColor, SectionAccessor::setColor);
        public static final ValueType<FireworkEffect> FIREWORK_EFFECT = of(SectionAccessor::getFireworkEffect, SectionAccessor::setFireworkEffect);
        public static final ValueType<Pattern> PATTERN = of(SectionAccessor::getPattern, SectionAccessor::setPattern);
        public static final ValueType<PotionEffect> POTION_EFFECT = of(SectionAccessor::getPotionEffect, SectionAccessor::setPotionEffect);
        public static final ValueType<Material> MATERIAL = of(SectionAccessor::getMaterial, SectionAccessor::setMaterial);
        public static final ValueType<ConfigurationSerializable> CONFIGURATION_SERIALIZABLE = of(SectionAccessor::getSerialized, SectionAccessor::setSerialized);
        public static final ValueType<List<Boolean>> BOOLEAN_LIST = of(SectionAccessor::getBooleanList, SectionAccessor::setBooleanList);
        public static final ValueType<List<Integer>> INTEGER_LIST = of(SectionAccessor::getIntList, SectionAccessor::setIntList);
        public static final ValueType<List<Float>> FLOAT_LIST = of(SectionAccessor::getFloatList, SectionAccessor::setFloatList);
        public static final ValueType<List<Double>> DOUBLE_LIST = of(SectionAccessor::getDoubleList, SectionAccessor::setDoubleList);
        public static final ValueType<List<Long>> LONG_LIST = of(SectionAccessor::getLongList, SectionAccessor::setLongList);
        public static final ValueType<List<String>> STRING_LIST = of(SectionAccessor::getStringList, SectionAccessor::setStringList);
        public static final ValueType<List<ItemStack>> ITEM_STACK_LIST = of(SectionAccessor::getItemStackList, SectionAccessor::setItemStackList);
        public static final ValueType<List<Location>> LOCATION_LIST = of(SectionAccessor::getLocationList, SectionAccessor::setLocationList);
        public static final ValueType<List<Vector>> VECTOR_LIST = of(SectionAccessor::getVectorList, SectionAccessor::setVectorList);
        public static final ValueType<List<ItemMeta>> ITEM_META_LIST = of(SectionAccessor::getItemMetaList, SectionAccessor::setItemMetaList);
        public static final ValueType<List<OfflinePlayer>> PLAYER_LIST = of(SectionAccessor::getPlayerList, SectionAccessor::setPlayerList);
        public static final ValueType<List<AttributeModifier>> ATTRIBUTE_MODIFIER_LIST = of(SectionAccessor::getAttributeModifierList, SectionAccessor::setAttributeModifierList);
        public static final ValueType<List<BlockVector>> BLOCK_VECTOR_LIST = of(SectionAccessor::getBlockVectorList, SectionAccessor::setBlockVectorList);
        public static final ValueType<List<BoundingBox>> BOUNDING_BOX_LIST = of(SectionAccessor::getBoundingBoxList, SectionAccessor::setBoundingBoxList);
        public static final ValueType<List<Color>> COLOR_LIST = of(SectionAccessor::getColorList, SectionAccessor::setColorList);
        public static final ValueType<List<FireworkEffect>> FIREWORK_EFFECT_LIST = of(SectionAccessor::getFireworkEffectList, SectionAccessor::setFireworkEffectList);
        public static final ValueType<List<Pattern>> PATTERN_LIST = of(SectionAccessor::getPatternList, SectionAccessor::setPatternList);
        public static final ValueType<List<PotionEffect>> POTION_EFFECT_LIST = of(SectionAccessor::getPotionEffectList, SectionAccessor::setPotionEffectList);
        public static final ValueType<List<Material>> MATERIAL_LIST = of(SectionAccessor::getMaterialList, SectionAccessor::setMaterialList);
        public static final ValueType<List<ConfigurationSerializable>> CONFIGURATION_SERIALIZABLE_LIST = of(SectionAccessor::getSerializedList, SectionAccessor::setSerializedList);

        /**
         * The {@link Loader} used to load the value
         */
        private final Loader<T> loader;

        /**
         * The {@link Saver} used to save the value
         */
        private final Saver<T> saver;

        /**
         * Internal constructor
         *
         * @param loader The {@link Loader} used to load the value
         * @param saver  The {@link Saver} used to save the value
         */
        private ValueType(Loader<T> loader, Saver<T> saver) {
            this.loader = loader;
            this.saver = saver;
        }

        /**
         * Mapper to map one type to another and visa-versa. This mapping method can be used when a data type must be
         * saved as a different type as its loaded type. For example, <code>String</code> could be mapped to
         * <code>Text</code>, as there is no primitive saving mechanism for <code>Text</code>, but <code>Text</code>
         * can be saved as a <code>String</code> and retain its data.
         *
         * @param mapper   The function used to map from the loaded value to the mapped value. This mapper is used when a
         *                 value is loaded.
         * @param unmapper The function used to unmap from the mapped value back into the loaded value for saving. This
         *                 unmapper is used when a value is saved.
         * @param <U>      The type to map to
         * @return The new {@link ValueType} of the mapped type
         */
        public <U> ValueType<U> map(Function<T, U> mapper, Function<U, T> unmapper) {
            return new ValueType<>(
                (accessor, name) -> mapper.apply(loader.load(accessor, name)),
                (accessor, name, value) -> saver.save(accessor, name, unmapper.apply(value))
            );
        }

        /**
         * Adds a consumer that runs when a value is loaded.
         *
         * @param consumer The consumer to run when a value is loaded
         * @return The new {@link ValueType}
         */
        public ValueType<T> onLoadDo(Consumer<T> consumer) {
            return new ValueType<>(
                (accessor, name) -> {
                    final T loaded = loader.load(accessor, name);
                    consumer.accept(loaded);
                    return loaded;
                }, saver);
        }

        /**
         * Adds a consumer that runs when a value is saved.
         *
         * @param consumer The consumer to run when a value is saved
         * @return The new {@link ValueType}
         */
        public ValueType<T> onSaveDo(Consumer<T> consumer) {
            return new ValueType<>(
                loader,
                (accessor, name, value) -> {
                    consumer.accept(value);
                    saver.save(accessor, name, value);
                });
        }

        /**
         * Adds a function that runs when a value is loaded. The return value of the function is used as the value.
         *
         * @param function The function to run when a value is loaded
         * @return The new {@link ValueType}
         */
        public ValueType<T> onLoadReplace(Function<T, T> function) {
            return new ValueType<>((accessor, name) -> function.apply(loader.load(accessor, name)), saver);
        }

        /**
         * Adds a function that runs when a value is saved. The return value of the function is used as the value that
         * will be saved.
         *
         * @param function The function to run when a value is saved
         * @return The new {@link ValueType}
         */
        public ValueType<T> onSaveReplace(Function<T, T> function) {
            return new ValueType<>(loader, (accessor, name, value) -> saver.save(accessor, name, function.apply(value)));
        }

        /**
         * A boolean function to load either true or false based on a true value and a false value. This is used as a
         * mapping from one data type to a boolean data type.
         * <p>
         * For example:
         * <ul>
         *     <li>"whitelist" -&gt; <code>true</code></li>
         *     <li>"blacklist" -&gt; <code>false</code></li>
         *     <li>"neither"   -&gt; orElse function</li>
         * </ul>
         *
         * @param trueValue  The value that will load true to the value
         * @param falseValue The value that will load false to the value
         * @param orElse     Is called when neither the true nor false value is found.
         * @return The new {@link ValueType}
         */
        public ValueType<Boolean> bool(T trueValue, T falseValue, Function<T, Boolean> orElse) {
            return map(value -> {
                if(value.equals(trueValue)) return true;
                if(value.equals(falseValue)) return false;
                return orElse != null ? orElse.apply(value) : null;
            }, value -> value ? trueValue : falseValue);
        }

        /**
         * A boolean function to load either true or false based on a true value and a false value. This is used as a
         * mapping from one data type to a boolean data type. A null value will be produced if neither the true nor
         * false value is found.
         * <p>
         * For example:
         * <ul>
         *     <li>"whitelist" -&gt; <code>true</code></li>
         *     <li>"blacklist" -&gt; <code>false</code></li>
         *     <li>"neither"   -&gt; <code>null</code></li>
         * </ul>
         *
         * @param trueValue  The value that will load true to the value
         * @param falseValue The value that will load false to the value
         * @return The new {@link ValueType}
         */
        public ValueType<Boolean> bool(T trueValue, T falseValue) {
            return bool(trueValue, falseValue, null);
        }

        /**
         * Specify a value if the loaded value is null.
         *
         * @param other The value to use if the loaded value is null
         * @return The new {@link ValueType}
         */
        public ValueType<T> orElse(T other) {
            return map(value -> value != null ? value : other, value -> value);
        }

        /**
         * Specify a supplier that will be called if the loaded value is null.
         *
         * @param other The supplier to use if the loaded value is null
         * @return The new {@link ValueType}
         */
        public ValueType<T> orElseGet(Supplier<T> other) {
            return map(value -> value != null ? value : other.get(), value -> value);
        }

        /**
         * Load a value from a {@link SectionAccessor} and a path.
         *
         * @param accessor The {@link SectionAccessor} to retrieve data from
         * @param path     The path to retrieve
         * @return The retrieved data
         */
        private T load(SectionAccessor<?, ?> accessor, String path) {
            return this.loader.load(accessor, path);
        }

        /**
         * Save a value to a {@link SectionAccessor}
         *
         * @param accessor The {@link SectionAccessor} to save data to
         * @param path     The path to save to
         * @param value    The value to save
         */
        private void save(SectionAccessor<?, ?> accessor, String path, T value) {
            this.saver.save(accessor, path, value);
        }

        /**
         * Create a new type utilizing a {@link Loader} and {@link Saver}
         *
         * @param loader The {@link Loader} to use for loading data
         * @param saver  The {@link Saver} to use for saving data
         * @param <T>    The type that is contained within the {@link ValueType}
         * @return The new {@link ValueType}
         */
        public static <T> ValueType<T> of(Loader<T> loader, Saver<T> saver) {
            return new ValueType<>(loader, saver);
        }

        /**
         * Create a new type of key value pairs, where key values are stored in a map as the value.
         *
         * @param valueType The {@link ValueType} used to retrieve values
         * @param <T>       The type maintained by the value of the map
         * @return The new {@link ValueType} of key pairs
         */
        public static <T> ValueType<Map<String, T>> keyValues(ValueType<T> valueType) {
            return new ValueType<>(
                (accessor, name) -> {
                    final Map<String, T> map = new LinkedHashMap<>();
                    final SectionAccessor<?, ?> section = accessor.getSection(name);
                    for(String key : section.getKeys(false)) {
                        map.put(key, valueType.load(section, key));
                    }
                    return map;
                },
                (accessor, name, map) -> {
                    final SectionAccessor<?, ?> section = accessor.getSection(name);
                    for(String key : map.keySet()) {
                        T value = map.get(key);
                        valueType.save(section, key, value);
                    }
                });
        }

        /**
         * An interface for loading a value from a {@link SectionAccessor} and name.
         *
         * @param <T> The type to be loaded
         * @author Mikedeejay2
         */
        @FunctionalInterface
        private interface Loader<T> {
            /**
             * Load a value from a {@link SectionAccessor} and a path.
             *
             * @param accessor The {@link SectionAccessor} to retrieve data from
             * @param name     The name to retrieve
             * @return The retrieved data
             */
            T load(SectionAccessor<?, ?> accessor, String name);
        }

        /**
         * An interface for saving a value to a {@link SectionAccessor}
         *
         * @param <T> The type to be saved
         * @author Mikedeejay2
         */
        @FunctionalInterface
        private interface Saver<T> {
            /**
             * Save a value to a {@link SectionAccessor}
             *
             * @param accessor The {@link SectionAccessor} to save data to
             * @param name     The name to save to
             * @param value    The value to save
             */
            void save(SectionAccessor<?, ?> accessor, String name, T value);
        }
    }

    /**
     * File type enumeration. Used for dynamic creation of file types.
     *
     * @author Mikedeejay2
     */
    public enum FileType {
        /**
         * The YAML file type. Instantiates {@link YamlFile} for extensions "yaml" and "yml"
         */
        YAML(YamlFile::new, "yaml", "yml"),

        /**
         * The JSON file type. Instantiates {@link JsonFile} for extensions "json"
         */
        JSON(JsonFile::new, "json");

        /**
         * The function used to create a {@link DataFile} of the specified type
         */
        private final BiFunction<BukkitPlugin, String, DataFile> generator;

        /**
         * Array of file extensions associated with the file type
         */
        private final String[] extensions;

        /**
         * Internal constructor
         *
         * @param generator  The function used to create a {@link DataFile} of the specified type
         * @param extensions Array of file extensions associated with the file type
         */
        FileType(BiFunction<BukkitPlugin, String, DataFile> generator, String... extensions) {
            this.generator = generator;
            this.extensions = extensions;
        }

        /**
         * Create a new {@link DataFile} of the file type
         *
         * @param plugin   The {@link BukkitPlugin} instance
         * @param filePath The path to the file
         * @return The newly created {@link DataFile}
         */
        public DataFile create(BukkitPlugin plugin, String filePath) {
            return generator.apply(plugin, filePath);
        }

        /**
         * Get the array of file extensions associated with the file type
         *
         * @return The array of file extensions
         */
        public String[] getExtensions() {
            return extensions;
        }

        /**
         * Get the {@link FileType} of a path based off of its file extension
         *
         * @param path The path of the file
         * @return The {@link FileType} of the path, if detected
         */
        public static FileType pathToType(String path) {
            final String extension = FilenameUtils.getExtension(path);
            for(FileType value : values()) {
                for(String curExt : value.getExtensions()) {
                    if(curExt.equals(extension)) return value;
                }
            }
            return null;
        }
    }

    /**
     * An updater for updating legacy data in relation to a {@link ConfigFile}. This updater can be used to ensure data
     * is valid on newer versions of a plugin given that the structure of a configuration file has changed over time.
     * <p>
     * List of operations available:
     * <ul>
     *     <li>{@link Updater#remove(String)} - Remove a value from a configuration file.</li>
     *     <li>{@link Updater#relocate(String, String)} - Relocate a key in a configuration file to a new key.</li>
     *     <li>{@link Updater#rename} - Rename a file on disk to a new name.</li>
     *     <li>{@link Updater#convert(String, String, BiConsumer)} - Convert one file type to another.</li>
     * </ul>
     *
     * @author Mikedeejay2
     */
    public static final class Updater {
        /**
         * The parent {@link ConfigFile}
         */
        private final ConfigFile file;

        /**
         * The list of {@link UpdateOperation UpdateOperations} that will occur at the time of loading the file
         */
        private final List<UpdateOperation> loadUpdates;

        /**
         * The list of {@link UpdateOperation UpdateOperations} that will occur before loading the file
         */
        private final List<UpdateOperation> preLoadUpdates;

        /**
         * Internal constructor
         *
         * @param file The parent {@link ConfigFile}
         */
        private Updater(ConfigFile file) {
            this.file = file;
            this.loadUpdates = new ArrayList<>();
            this.preLoadUpdates = new ArrayList<>();
        }

        /**
         * Remove a value from a configuration file. This update occurs when the file is loaded.
         *
         * @param key The key to remove from the configuration file.
         * @return This updater
         */
        public Updater remove(String key) {
            this.loadUpdates.add(new UpdateRemove(key));
            return this;
        }

        /**
         * Relocate a key in a configuration file to a new key. This update occurs when the file is loaded.
         *
         * @param key            The old key to be updated
         * @param destinationKey The new key
         * @return This updater
         */
        public Updater relocate(String key, String destinationKey) {
            this.loadUpdates.add(new UpdateRelocate(key, destinationKey));
            return this;
        }

        /**
         * Rename a file on disk to a new name. This update occurs before the file is loaded.
         *
         * @param originalPath    The original path to be updated
         * @param destinationPath The new destination path
         * @return This updater
         */
        public Updater rename(String originalPath, String destinationPath) {
            this.preLoadUpdates.add(new UpdateRenameFile(originalPath, destinationPath));
            return this;
        }

        /**
         * Convert one file type to another. This update occurs before the file is loaded.
         *
         * @param originalPath    The original path of the file to be converted
         * @param destinationPath The destination path to save to once conversion is done
         * @param converter       The converter consumer. This consumer must account for all values that should be
         *                        converted and manually move each value from the old {@link SectionAccessor} to the new
         *                        accessor.
         * @return This updater
         */
        public Updater convert(String originalPath, String destinationPath, BiConsumer<SectionAccessor<?, ?>, SectionAccessor<?, ?>> converter) {
            this.preLoadUpdates.add(new UpdateConvertFile(originalPath, destinationPath, converter));
            return this;
        }

        /**
         * Call all updates that should occur when a file is loaded
         */
        private void updateOnLoad() {
            this.loadUpdates.forEach(operation -> operation.update(file));
        }

        /**
         * Call all updates that should occur before a file is loaded
         */
        private void updatePreLoad() {
            this.preLoadUpdates.forEach(operation -> operation.update(file));
        }

        /**
         * A single update operation.
         *
         * @author Mikedeejay2
         */
        private interface UpdateOperation {
            /**
             * Update the {@link ConfigFile} with the specified operation
             *
             * @param file The {@link ConfigFile} to update
             */
            void update(ConfigFile file);
        }

        /**
         * An update operation to remove a key from a configuration file.
         *
         * @author Mikedeejay2
         */
        private static class UpdateRemove implements UpdateOperation {
            /**
             * The key to remove
             */
            private final String key;

            /**
             * Internal constructor
             *
             * @param key The key to remove
             */
            private UpdateRemove(String key) {
                this.key = key;
            }

            /**
             * Remove the {@link UpdateRemove#key} from the {@link ConfigFile} if it was found.
             *
             * @param file The {@link ConfigFile} to update
             */
            @Override
            public void update(ConfigFile file) {
                final SectionAccessor<DataFile, Object> accessor = file.getAccessor(key);
                final String name = file.getName(key);
                if(!accessor.contains(name)) return;
                accessor.delete(name);
            }
        }

        /**
         * An update operation to relocate a key to a new key in a configuration file.
         *
         * @author Mikedeejay2
         */
        private static class UpdateRelocate implements UpdateOperation {
            /**
             * The original key to be relocated
             */
            private final String key;

            /**
             * The destination key, the name of the key after relocation
             */
            private final String destinationKey;

            /**
             * Internal constructor
             *
             * @param key            The original key to be relocated
             * @param destinationKey The destination key, the name of the key after relocation
             */
            private UpdateRelocate(String key, String destinationKey) {
                this.key = key;
                this.destinationKey = destinationKey;
            }

            /**
             * Relocate the key to the destination key if the key was found.
             *
             * @param file The {@link ConfigFile} to update
             */
            @Override
            public void update(ConfigFile file) {
                final SectionAccessor<DataFile, Object> accessor = file.getAccessor(key);
                final String name = file.getName(key);
                final String newName = file.getName(destinationKey);
                if(!accessor.contains(name)) return;
                file.getAccessor(destinationKey).set(newName, accessor.get(name));
                accessor.delete(name);
            }
        }

        /**
         * An update operation to rename a file on the disk.
         *
         * @author Mikedeejay2
         */
        private static class UpdateRenameFile implements UpdateOperation {
            /**
             * The path to the file to be renamed
             */
            private final String originalPath;

            /**
             * The path of the file after renaming
             */
            private final String destinationPath;

            /**
             * Internal constructor
             *
             * @param originalPath    The path to the file to be renamed
             * @param destinationPath The path of the file after renaming
             */
            private UpdateRenameFile(String originalPath, String destinationPath) {
                this.originalPath = originalPath;
                this.destinationPath = destinationPath;
            }

            /**
             * Rename the original file to the destination path if found.
             *
             * @param file The {@link ConfigFile} to update
             */
            @Override
            public void update(ConfigFile file) {
                final File oldFile = new File(file.plugin.getDataFolder(), originalPath);
                final File newFile = new File(file.plugin.getDataFolder(), destinationPath);
                if(!oldFile.exists() || newFile.exists()) return;
                try {
                    Files.move(oldFile.toPath(), newFile.toPath());
                } catch(IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        /**
         * An update operation to convert a file to another file type.
         *
         * @author Mikedeejay2
         */
        private static class UpdateConvertFile implements UpdateOperation {
            /**
             * The path to the file to be converted
             */
            private final String originalPath;

            /**
             * The path of the file after converting
             */
            private final String destinationPath;

            /**
             * The converter consumer. This consumer must account for all values that should be converted and manually
             * move each value from the old {@link SectionAccessor} to the new accessor.
             */
            private final BiConsumer<SectionAccessor<?, ?>, SectionAccessor<?, ?>> updater;

            /**
             * Internal constructor
             *
             * @param originalPath    The path to the file to be converted
             * @param destinationPath he path of the file after converting
             * @param converter       The converter consumer. This consumer must account for all values that should be
             *                        converted and manually move each value from the old {@link SectionAccessor} to the
             *                        new accessor.
             */
            private UpdateConvertFile(String originalPath, String destinationPath, BiConsumer<SectionAccessor<?, ?>, SectionAccessor<?, ?>> converter) {
                this.originalPath = originalPath;
                this.destinationPath = destinationPath;
                this.updater = converter;
            }

            /**
             * Convert the original file from its format to the new format at the destination path using the
             * {@link UpdateConvertFile#updater} function.
             *
             * @param file The {@link ConfigFile} to update
             */
            @Override
            public void update(ConfigFile file) {
                final FileType oldType = FileType.pathToType(originalPath);
                final FileType newType = FileType.pathToType(destinationPath);
                if(oldType == null || newType == null) return;
                final ConfigFile oldFile = new ConfigFile(file.plugin, originalPath, oldType, false);
                final ConfigFile newFile = new ConfigFile(file.plugin, destinationPath, newType, false);
                if(!oldFile.internalFileExists() || newFile.internalFileExists()) return;
                oldFile.load();
                newFile.load();
                final SectionAccessor<?, ?> oldSection = ((SectionInstancer<?, ?, ?>) oldFile.dataFile).getAccessor();
                final SectionAccessor<?, ?> newSection = ((SectionInstancer<?, ?, ?>) newFile.dataFile).getAccessor();
                updater.accept(oldSection, newSection);
                newFile.save();
            }
        }
    }
}
