package com.mikedeejay2.mikedeejay2lib.config;

import com.mikedeejay2.mikedeejay2lib.BukkitPlugin;
import com.mikedeejay2.mikedeejay2lib.data.DataFile;
import com.mikedeejay2.mikedeejay2lib.data.FileType;
import com.mikedeejay2.mikedeejay2lib.data.section.SectionAccessor;
import com.mikedeejay2.mikedeejay2lib.data.section.SectionInstancer;
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

import java.util.*;
import java.util.function.*;

/**
 * A configuration file. The basis of this class is a {@link DataFile} with extra features such as handled
 * {@link ConfigValue configuration values}, {@link ValueType value types}, an {@link ConfigUpdater updater}, and abstraction
 * of saving, loading, and resetting the file.
 * <p>
 * The main objects this class holds are {@link ConfigValue ConfigValues} and child {@link ConfigFile ConfigFiles}.
 * These objects can be added using {@link ConfigFile#value(ValueType, String)} and
 * {@link ConfigFile#child(ConfigFile)}.
 * <p>
 * To begin using this class, extend this class and create a {@link ConfigValue} using
 * {@link ConfigFile#value(ValueType, String)}. If you want to create your own {@link ValueType}, you can start with a
 * pre-existing type and add on to it using its built-in methods or create a new type using
 * {@link ConfigFile.ValueType#of(ConfigFile.ValueType.Loader, ConfigFile.ValueType.Saver)}.
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
     * The {@link ConfigUpdater} for this configuration.
     */
    protected final ConfigUpdater updater;

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
        this.updater = new ConfigUpdater(this);
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
     * @param <T>          The collection data type of the value
     * @param <E>          The type maintained by the collection
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
     * @param <T>                  The collection data type of the value
     * @param <E>                  The type maintained by the collection
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
     * @param <T>          The map data type of the value
     * @param <K>          The type of keys maintained by the map
     * @param <V>          The type of values maintained by the map
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
     * @param <T>                  The map data type of the value
     * @param <K>                  The type of keys maintained by the map
     * @param <V>                  The type of values maintained by the map
     * @return The new {@link ConfigValue}
     */
    protected <T extends Map<K, V>, K, V> ConfigValueMap<T, K, V> mapValue(ValueType<T> type, String path, Supplier<T> defaultValueSupplier) {
        final ConfigValueMap<T, K, V> value = new ConfigValueMap<>(this, type, path, defaultValueSupplier.get());
        this.values.add(value);
        return value;
    }

    /**
     * Add a {@link ConfigValueBoolean} to this config.
     *
     * @param type         The {@link ConfigValueBoolean} of the value
     * @param path         The path to the value in the file. This includes depth, separated by a <code>.</code> for
     *                     each level. For example, "<code>root.level1.level2.name</code>"
     * @param defaultValue The default value to use if a value is not on the disk or in the jar file
     * @return The new {@link ConfigValueBoolean}
     */
    protected ConfigValueBoolean valueBoolean(ValueType<Boolean> type, String path, boolean defaultValue) {
        final ConfigValueBoolean value = new ConfigValueBoolean(this, type, path, defaultValue);
        this.values.add(value);
        return value;
    }

    /**
     * Add a {@link ConfigValueBoolean} to this config.
     *
     * @param type The {@link ConfigValueBoolean} of the value
     * @param path The path to the value in the file. This includes depth, separated by a <code>.</code> for
     *             each level. For example, "<code>root.level1.level2.name</code>"
     * @return The new {@link ConfigValueBoolean}
     */
    protected ConfigValueBoolean valueBoolean(ValueType<Boolean> type, String path) {
        return valueBoolean(type, path, false);
    }

    /**
     * Add a {@link ConfigValueInteger} to this config.
     *
     * @param type         The {@link ConfigValueInteger} of the value
     * @param path         The path to the value in the file. This includes depth, separated by a <code>.</code> for
     *                     each level. For example, "<code>root.level1.level2.name</code>"
     * @param defaultValue The default value to use if a value is not on the disk or in the jar file
     * @return The new {@link ConfigValueInteger}
     */
    protected ConfigValueInteger valueInteger(ValueType<Integer> type, String path, int defaultValue) {
        final ConfigValueInteger value = new ConfigValueInteger(this, type, path, defaultValue);
        this.values.add(value);
        return value;
    }

    /**
     * Add a {@link ConfigValueInteger} to this config.
     *
     * @param type The {@link ConfigValueInteger} of the value
     * @param path The path to the value in the file. This includes depth, separated by a <code>.</code> for
     *             each level. For example, "<code>root.level1.level2.name</code>"
     * @return The new {@link ConfigValueInteger}
     */
    protected ConfigValueInteger valueInteger(ValueType<Integer> type, String path) {
        return valueInteger(type, path, 0);
    }

    /**
     * Add a {@link ConfigValueFloat} to this config.
     *
     * @param type         The {@link ConfigValueFloat} of the value
     * @param path         The path to the value in the file. This includes depth, separated by a <code>.</code> for
     *                     each level. For example, "<code>root.level1.level2.name</code>"
     * @param defaultValue The default value to use if a value is not on the disk or in the jar file
     * @return The new {@link ConfigValueFloat}
     */
    protected ConfigValueFloat valueFloat(ValueType<Float> type, String path, float defaultValue) {
        final ConfigValueFloat value = new ConfigValueFloat(this, type, path, defaultValue);
        this.values.add(value);
        return value;
    }

    /**
     * Add a {@link ConfigValueFloat} to this config.
     *
     * @param type The {@link ConfigValueFloat} of the value
     * @param path The path to the value in the file. This includes depth, separated by a <code>.</code> for
     *             each level. For example, "<code>root.level1.level2.name</code>"
     * @return The new {@link ConfigValueFloat}
     */
    protected ConfigValueFloat valueFloat(ValueType<Float> type, String path) {
        return valueFloat(type, path, 0.0f);
    }

    /**
     * Add a {@link ConfigValueDouble} to this config.
     *
     * @param type         The {@link ConfigValueDouble} of the value
     * @param path         The path to the value in the file. This includes depth, separated by a <code>.</code> for
     *                     each level. For example, "<code>root.level1.level2.name</code>"
     * @param defaultValue The default value to use if a value is not on the disk or in the jar file
     * @return The new {@link ConfigValueDouble}
     */
    protected ConfigValueDouble valueDouble(ValueType<Double> type, String path, double defaultValue) {
        final ConfigValueDouble value = new ConfigValueDouble(this, type, path, defaultValue);
        this.values.add(value);
        return value;
    }

    /**
     * Add a {@link ConfigValueDouble} to this config.
     *
     * @param type The {@link ConfigValueDouble} of the value
     * @param path The path to the value in the file. This includes depth, separated by a <code>.</code> for
     *             each level. For example, "<code>root.level1.level2.name</code>"
     * @return The new {@link ConfigValueDouble}
     */
    protected ConfigValueDouble valueDouble(ValueType<Double> type, String path) {
        return valueDouble(type, path, 0.0);
    }

    /**
     * Add a {@link ConfigValueLong} to this config.
     *
     * @param type         The {@link ConfigValueLong} of the value
     * @param path         The path to the value in the file. This includes depth, separated by a <code>.</code> for
     *                     each level. For example, "<code>root.level1.level2.name</code>"
     * @param defaultValue The default value to use if a value is not on the disk or in the jar file
     * @return The new {@link ConfigValueLong}
     */
    protected ConfigValueLong valueLong(ValueType<Long> type, String path, long defaultValue) {
        final ConfigValueLong value = new ConfigValueLong(this, type, path, defaultValue);
        this.values.add(value);
        return value;
    }

    /**
     * Add a {@link ConfigValueLong} to this config.
     *
     * @param type The {@link ConfigValueLong} of the value
     * @param path The path to the value in the file. This includes depth, separated by a <code>.</code> for
     *             each level. For example, "<code>root.level1.level2.name</code>"
     * @return The new {@link ConfigValueLong}
     */
    protected ConfigValueLong valueLong(ValueType<Long> type, String path) {
        return valueLong(type, path, 0L);
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
     * Get this config's {@link ConfigUpdater}
     *
     * @return This config's {@link ConfigUpdater}
     */
    public ConfigUpdater getUpdater() {
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
     *     <li>Perform preload updates ({@link ConfigUpdater#updatePreLoad()})</li>
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
        values.forEach(ConfigValue::reset);
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
     *     <li>Run updater post-load operations ({@link ConfigUpdater#updateOnLoad()})</li>
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
    SectionAccessor<DataFile, Object> getAccessor(String path) {
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
    String getName(String path) {
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
         * The default value. Will be used upon initialization or upon reset.
         */
        protected T defaultValue;

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
            this.defaultValue = defaultValue;
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
         * Get the default value.
         *
         * @return The default value
         */
        public T getDefaultValue() {
            return defaultValue;
        }

        /**
         * Set the value. This method will automatically set the parent {@link ConfigFile} as modified.
         *
         * @param value The new value
         */
        public void set(T value) {
            this.value = value;
            this.file.setModified(true);
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

        protected void reset() {
            set(defaultValue);
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

        @Override
        protected void reset() {
            this.value.clear();
            this.file.setModified(true);
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

        @Override
        protected void reset() {
            this.value.clear();
            this.file.setModified(true);
        }
    }

    /**
     * A configuration value for a primitive boolean.
     *
     * @see ConfigValue
     * @author Mikedeejay2
     */
    public static class ConfigValueBoolean extends ConfigValue<Boolean> {
        /**
         * The boolean value
         */
        private boolean value;

        private ConfigValueBoolean(ConfigFile file, ValueType<Boolean> type, String path, boolean defaultValue) {
            super(file, type, path, defaultValue);
            this.value = defaultValue;
        }

        /**
         * Get the boolean value
         *
         * @return The boolean value
         */
        public boolean getBoolean() {
            return value;
        }

        /**
         * Set the boolean value
         *
         * @param value The new boolean value
         */
        public void setBoolean(boolean value) {
            this.value = value;
        }

        @Override
        @Deprecated
        public Boolean get() {
            return getBoolean();
        }

        @Override
        @Deprecated
        public void set(Boolean value) {
            setBoolean(value);
        }

        @Override
        protected void reset() {
            this.value = defaultValue;
        }

        @Override
        protected void load() {
            super.load();
            this.value = super.value;
        }

        @Override
        protected void save() {
            super.value = this.value;
            super.save();
        }
    }

    /**
     * A configuration value for a primitive integer.
     *
     * @see ConfigValue
     * @author Mikedeejay2
     */
    public static class ConfigValueInteger extends ConfigValue<Integer> {
        /**
         * The integer value
         */
        private int value;

        private ConfigValueInteger(ConfigFile file, ValueType<Integer> type, String path, int defaultValue) {
            super(file, type, path, defaultValue);
            this.value = defaultValue;
        }

        /**
         * Get the integer value
         *
         * @return The integer value
         */
        public int getInteger() {
            return value;
        }

        /**
         * Set the integer value
         *
         * @param value The new integer value
         */
        public void setInteger(int value) {
            this.value = value;
        }

        @Override
        @Deprecated
        public Integer get() {
            return getInteger();
        }

        @Override
        @Deprecated
        public void set(Integer value) {
            setInteger(value);
        }

        @Override
        protected void reset() {
            this.value = defaultValue;
        }

        @Override
        protected void load() {
            super.load();
            this.value = super.value;
        }

        @Override
        protected void save() {
            super.value = this.value;
            super.save();
        }
    }

    /**
     * A configuration value for a primitive float.
     *
     * @see ConfigValue
     * @author Mikedeejay2
     */
    public static class ConfigValueFloat extends ConfigValue<Float> {
        /**
         * The float value
         */
        private float value;

        private ConfigValueFloat(ConfigFile file, ValueType<Float> type, String path, float defaultValue) {
            super(file, type, path, defaultValue);
            this.value = defaultValue;
        }

        /**
         * Get the float value
         *
         * @return The float value
         */
        public float getFloat() {
            return value;
        }

        /**
         * Set the float value
         *
         * @param value The new float value
         */
        public void setFloat(float value) {
            this.value = value;
        }

        @Override
        @Deprecated
        public Float get() {
            return getFloat();
        }

        @Override
        @Deprecated
        public void set(Float value) {
            setFloat(value);
        }

        @Override
        protected void reset() {
            this.value = defaultValue;
        }

        @Override
        protected void load() {
            super.load();
            this.value = super.value;
        }

        @Override
        protected void save() {
            super.value = this.value;
            super.save();
        }
    }

    /**
     * A configuration value for a primitive double.
     *
     * @see ConfigValue
     * @author Mikedeejay2
     */
    public static class ConfigValueDouble extends ConfigValue<Double> {
        /**
         * The double value
         */
        private double value;

        private ConfigValueDouble(ConfigFile file, ValueType<Double> type, String path, double defaultValue) {
            super(file, type, path, defaultValue);
            this.value = defaultValue;
        }

        /**
         * Get the double value
         *
         * @return The double value
         */
        public double getDouble() {
            return value;
        }

        /**
         * Set the double value
         *
         * @param value The new double value
         */
        public void setDouble(double value) {
            this.value = value;
        }

        @Override
        @Deprecated
        public Double get() {
            return getDouble();
        }

        @Override
        @Deprecated
        public void set(Double value) {
            setDouble(value);
        }

        @Override
        protected void reset() {
            this.value = defaultValue;
        }

        @Override
        protected void load() {
            super.load();
            this.value = super.value;
        }

        @Override
        protected void save() {
            super.value = this.value;
            super.save();
        }
    }

    /**
     * A configuration value for a primitive long.
     *
     * @see ConfigValue
     * @author Mikedeejay2
     */
    public static class ConfigValueLong extends ConfigValue<Long> {
        /**
         * The long value
         */
        private long value;

        private ConfigValueLong(ConfigFile file, ValueType<Long> type, String path, long defaultValue) {
            super(file, type, path, defaultValue);
            this.value = defaultValue;
        }

        /**
         * Get the long value
         *
         * @return The long value
         */
        public long getLong() {
            return value;
        }

        /**
         * Set the long value
         *
         * @param value The new long value
         */
        public void setLong(long value) {
            this.value = value;
        }

        @Override
        @Deprecated
        public Long get() {
            return getLong();
        }

        @Override
        @Deprecated
        public void set(Long value) {
            setLong(value);
        }

        @Override
        protected void reset() {
            this.value = defaultValue;
        }

        @Override
        protected void load() {
            super.load();
            this.value = super.value;
        }

        @Override
        protected void save() {
            super.value = this.value;
            super.save();
        }
    }

    /**
     * A type of value for use with {@link ConfigValue ConfigValues}. Pre-existing types or custom types can be used.
     * Pre-existing types can be found as static variables of this class, such as {@link ValueType#BOOLEAN} or
     * {@link ValueType#ITEM_STACK}. If you want to create your own {@link ValueType}, you can start with a pre-existing
     * type and add on to it using its built-in methods or create a new type using
     * {@link ConfigFile.ValueType#of(ConfigFile.ValueType.Loader, ConfigFile.ValueType.Saver)}.
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
        private T load(SectionAccessor<DataFile, Object> accessor, String path) {
            return this.loader.load(accessor, path);
        }

        /**
         * Save a value to a {@link SectionAccessor}
         *
         * @param accessor The {@link SectionAccessor} to save data to
         * @param path     The path to save to
         * @param value    The value to save
         */
        private void save(SectionAccessor<DataFile, Object> accessor, String path, T value) {
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
         * Create a new type utilizing a {@link ConfigurationSerializable} class
         *
         * @param clazz The {@link ConfigurationSerializable} Class type
         * @param <T>    The type that is contained within the {@link ValueType}
         * @return The new {@link ValueType}
         */
        public static <T extends ConfigurationSerializable> ValueType<T> ofSerializable(Class<T> clazz) {
            return new ValueType<>(
                (accessor, name) -> accessor.getSerialized(name, clazz),
                SectionAccessor::setSerialized);
        }

        /**
         * Create a new list type utilizing a {@link ConfigurationSerializable} class
         *
         * @param clazz The {@link ConfigurationSerializable} Class type
         * @param <T>    The type that is contained within the {@link ValueType}
         * @return The new {@link ValueType}
         */
        public static <T extends ConfigurationSerializable> ValueType<List<T>> ofSerializableList(Class<T> clazz) {
            return new ValueType<>(
                (accessor, name) -> accessor.getSerializedList(name, clazz),
                (accessor, name, value) -> accessor.setSerializedList(name, (List<ConfigurationSerializable>) value));
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
                    final SectionAccessor<DataFile, Object> section = accessor.getSection(name);
                    for(String key : section.getKeys(false)) {
                        map.put(key, valueType.load(section, key));
                    }
                    return map;
                },
                (accessor, name, map) -> {
                    final SectionAccessor<DataFile, Object> section = accessor.getSection(name);
                    for(String key : section.getKeys(false)) {
                        section.delete(key);
                    }
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
        public interface Loader<T> {
            /**
             * Load a value from a {@link SectionAccessor} and a path.
             *
             * @param accessor The {@link SectionAccessor} to retrieve data from
             * @param name     The name to retrieve
             * @return The retrieved data
             */
            T load(SectionAccessor<DataFile, Object> accessor, String name);
        }

        /**
         * An interface for saving a value to a {@link SectionAccessor}
         *
         * @param <T> The type to be saved
         * @author Mikedeejay2
         */
        @FunctionalInterface
        public interface Saver<T> {
            /**
             * Save a value to a {@link SectionAccessor}
             *
             * @param accessor The {@link SectionAccessor} to save data to
             * @param name     The name to save to
             * @param value    The value to save
             */
            void save(SectionAccessor<DataFile, Object> accessor, String name, T value);
        }
    }
}
