package com.mikedeejay2.mikedeejay2lib.data.filesystem;

import com.mikedeejay2.mikedeejay2lib.BukkitPlugin;
import com.mikedeejay2.mikedeejay2lib.data.filesystem.modifier.AutoFileSystemModifier;
import com.mikedeejay2.mikedeejay2lib.data.filesystem.modifier.FileSystemModifier;
import com.mikedeejay2.mikedeejay2lib.data.filesystem.modifier.ManualFileSystemModifier;
import com.mikedeejay2.mikedeejay2lib.data.filesystem.modifier.BaseSystemModifier;
import com.mikedeejay2.mikedeejay2lib.data.filesystem.saveload.FileSystemSaveLoad;
import com.mikedeejay2.mikedeejay2lib.data.filesystem.saveload.MultiFileSaveLoad;
import com.mikedeejay2.mikedeejay2lib.data.filesystem.saveload.NoOpSaveLoad;
import com.mikedeejay2.mikedeejay2lib.data.filesystem.saveload.SingleFileSaveLoad;
import org.apache.commons.lang3.Validate;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.jetbrains.annotations.Nullable;

import java.nio.file.InvalidPathException;
import java.nio.file.Paths;
import java.util.*;

/**
 * A flexible file system for storing serializable objects to disk. Includes several ways of modifying and saving data.
 * <p>
 * The {@link SaveMode} enum specifies when data is saved to disk:
 * <ul>
 *     <li>
 *         <strong>{@link SaveMode#MANUAL_SAVE}</strong> - Files must be saved manually using the save method. This
 *         method is best for single file mode or batch operations of multi-file mode. It saves all modified items
 *         separately, so it could have a higher memory profile with large operations than auto save.
 *     </li>
 *     <li>
 *         <strong>{@link SaveMode#AUTO_SAVE}</strong> - Files are automatically saved using this method. Best for
 *         multi-file mode where items are added over time. The drawback to this method is higher disk usage, as it
 *         saves whenever an item is added or removed.
 *     </li>
 * </ul>
 * The save mode is set by the <code>autoWrite</code> parameter in the constructor.
 * <p>
 * The {@link FileMode} enum specifies how data is stored to disk:
 * <ul>
 *     <li>
 *         <strong>{@link FileMode#SINGLE_FILE}</strong> - Save all items to a single .json file. Good for small
 *         portable item file systems but requires that the entire file system be loaded into json.
 *         <p>
 *         Storage format:
 *         <ul>
 *             <li><code>items.json</code></li>
 *         </ul>
 *     </li>
 *     <li>
 *         <strong>{@link FileMode#FILE_SYSTEM}</strong> - Save items to their own .json file in a real file system on
 *         the server machine. This solution is less portable but ensures that only updated or loaded files are saved to
 *         the system. This implementation is good for large item file systems.
 *         <p>
 *         Storage format:
 *         <ul>
 *             <li><code>items</code>
 *                 <ul>
 *                     <li><code>folder1</code>
 *                         <ul>
 *                             <li><code>sub_item1.json</code></li>
 *                             <li><code>sub_item2.json</code></li>
 *                         </ul>
 *                     </li>
 *                     <li><code>folder2</code>
 *                         <ul>
 *                             <li><code>sub_item1.json</code></li>
 *                             <li><code>sub_item2.json</code></li>
 *                         </ul>
 *                     </li>
 *                     <li><code>item1.json</code></li>
 *                     <li><code>item2.json</code></li>
 *                     <li><code>item3.json</code></li>
 *                     <li><code>item4.json</code></li>
 *                 </ul>
 *             </li>
 *         </ul>
 *     </li>
 * </ul>
 * Additionally, {@link FileMode#NO_OP} allows small file systems to be created and exist only in memory. To avoid
 * potential in-memory data loss on no-op mode, the maximum folder pool size should be set to something large enough to
 * store all folders using {@link SerializableFileSystem#setMaxPoolSize(int)}.
 * <p>
 * The file system can be displayed in a GUI by using the
 * {@link com.mikedeejay2.mikedeejay2lib.gui.modules.explorer.GUISerializableExplorerModule GUISerializableExplorerModule}
 * module.
 *
 * @param <T> The type of {@link ConfigurationSerializable} being stored
 * @author Mikedeejay2
 */
public class SerializableFileSystem<T extends ConfigurationSerializable> {
    /**
     * The {@link BukkitPlugin} instance
     */
    protected final BukkitPlugin plugin;

    /**
     * The class type of the {@link ConfigurationSerializable} being handled
     */
    protected final Class<T> serializableClass;

    /**
     * The root {@link SerializableFolderFS}
     */
    protected final SerializableFolderFS<T> root;

    /**
     * The path to save this file system to. Will be appended to the plugin's data folder path like
     * <code>plugins/plugin_name/savePath</code>
     */
    protected final String savePath;

    /**
     * The {@link FolderPool} for storing and retrieving folders in memory
     */
    protected final FolderPool<T> folderPool;

    /**
     * Whether to auto write the file system to disk upon a change or not
     */
    protected boolean autoWrite;

    /**
     * The {@link FileMode} of this file system
     */
    protected final FileMode fileMode;

    /**
     * The {@link FileSystemModifier} of this file system. Implements logic for modifying the file system.
     */
    protected FileSystemModifier<T> modifier;

    /**
     * The {@link FileSystemSaveLoad} of this file system. Implements logic for saving the data to disk.
     */
    protected FileSystemSaveLoad<T> saveLoad;

    /**
     * Construct a new <code>SerializableFileSystem</code>
     *
     * @param plugin            The {@link BukkitPlugin} instance
     * @param serializableClass The class type of the {@link ConfigurationSerializable} being handled
     * @param name              The display name of the root folder
     * @param savePath          The path to save this file system to. Will be appended to the plugin's data folder path
     *                          like <code>plugins/plugin_name/savePath</code>
     * @param fileMode          The {@link FileMode} of this file system
     * @param autoWrite         Whether to auto write the file system to disk upon a change or not
     */
    public SerializableFileSystem(BukkitPlugin plugin, Class<T> serializableClass, String name, String savePath, FileMode fileMode, boolean autoWrite) {
        this.serializableClass = serializableClass;
        this.plugin = plugin;
        this.folderPool = new FolderPool<>(10);
        this.root = new SerializableFolderFS<>(getSafeName(name), null, this, true);
        this.autoWrite = autoWrite;
        this.fileMode = fileMode;
        this.savePath = savePath.replace('\\', '/');

        this.modifier = SaveMode.of(fileMode, autoWrite).getSystemModifier(this);
        this.saveLoad = fileMode.getSaveLoad(this);
    }

    /**
     * Construct a new <code>SerializableFileSystem</code> with auto write off
     *
     * @param plugin            The {@link BukkitPlugin} instance
     * @param serializableClass The class type of the {@link ConfigurationSerializable} being handled
     * @param name              The display name of the root folder
     * @param fileMode          The {@link FileMode} of this file system
     */
    public SerializableFileSystem(BukkitPlugin plugin, Class<T> serializableClass, String name, String initialPath, FileMode fileMode) {
        this(plugin, serializableClass, name, initialPath, fileMode, false);
    }

    /**
     * Construct a new <code>SerializableFileSystem</code> with auto write off in single file mode
     *
     * @param plugin            The {@link BukkitPlugin} instance
     * @param serializableClass The class type of the {@link ConfigurationSerializable} being handled
     * @param name              The display name of the root folder
     */
    public SerializableFileSystem(BukkitPlugin plugin, Class<T> serializableClass, String name, String initialPath) {
        this(plugin, serializableClass, name, initialPath, FileMode.SINGLE_FILE);
    }

    /**
     * Add an object to the file system at a specified path.
     *
     * @param path The path to add the object to
     * @param name The file name of the object
     * @param obj The object to add
     */
    public void addItem(String path, String name, T obj) {
        path = getSafePath(path);
        name = getSafeName(name);
        SerializableFolderFS<T> folder = getFolder(path);
        Validate.notNull(folder, "Tried to add item to null folder at path \"%s\"", path);
        folder.addItem(name, obj);
    }

    /**
     * Remove an object from the file system
     *
     * @param path The path to remove the object from
     * @param name The file name of the object
     */
    public void removeItem(String path, String name) {
        path = getSafePath(path);
        name = getSafeName(name);
        SerializableFolderFS<T> folder = getFolder(path);
        Validate.notNull(folder, "Tried to remove item from null folder at path \"%s\"", path);
        folder.removeItem(name);
    }

    /**
     * Clear all objects at a specified path (shallow)
     *
     * @param path The path to clear all objects from
     */
    public void clearItems(String path) {
        path = getSafePath(path);
        SerializableFolderFS<T> folder = getFolder(path);
        Validate.notNull(folder, "Tried to clear items from null folder at path \"%s\"", path);
        folder.clearItems();
    }

    /**
     * Create a new folder in the file system
     *
     * @param path The path to create the folder at
     * @param name The name of the folder
     */
    public void createFolder(String path, String name) {
        path = getSafePath(path);
        name = getSafeName(name);
        SerializableFolderFS<T> folder = getFolder(path);
        Validate.notNull(folder, "Tried to create folder into null folder at path \"%s\"", path);
        folder.addFolder(name);
    }

    /**
     * Remove a folder from the file system
     *
     * @param path The path to remove the folder from
     * @param name The name of the folder to remove
     */
    public void removeFolder(String path, String name) {
        path = getSafePath(path);
        name = getSafeName(name);
        SerializableFolderFS<T> folder = getFolder(path);
        Validate.notNull(folder, "Tried to remove folder from null folder at path \"%s\"", path);
        folder.removeFolder(name);
    }

    /**
     * Clear all folders at a specified path (shallow)
     *
     * @param path The path to clear all folders from
     */
    public void clearFolders(String path) {
        path = getSafePath(path);
        SerializableFolderFS<T> folder = getFolder(path);
        Validate.notNull(folder, "Tried to clear folders from null folder at path \"%s\"", path);
        folder.clearFolders();
    }

    /**
     * Save a specific folder to disk. Redundant if auto write is on.
     *
     * @param path The path to save to disk
     */
    public void save(String path) {
        path = getSafePath(path);
        SerializableFolderFS<T> folder = getFolder(path);
        Validate.notNull(folder, "Tried to save null folder at path \"%s\"", path);
        folder.save();
    }

    /**
     * Save all unsaved changes to disk. Redundant if auto write is on.
     */
    public void saveAll() {
        modifier.saveAll();
    }

    /**
     * Get an object from the file system
     *
     * @param path The path to get the object from
     * @param name The file name of the object
     * @return The retrieved object, null if not found
     */
    public T getItem(String path, String name) {
        path = getSafePath(path);
        name = getSafeName(name);
        SerializableFolderFS<T> folder = getFolder(path);
        Validate.notNull(folder, "Tried to get item from null folder at path \"%s\"", path);
        return folder.getItem(name);
    }

    /**
     * Get the file name of an object based off of an equal object
     *
     * @param path The path to get the object name from
     * @param obj The object to find the name of
     * @return The retrieved file name, null if not found
     */
    public String getItemName(String path, T obj) {
        path = getSafePath(path);
        SerializableFolderFS<T> folder = getFolder(path);
        Validate.notNull(folder, "Tried to get item name from null folder at path \"%s\"", path);
        return folder.getItemName(obj);
    }

    /**
     * Get a folder form the file system
     *
     * @param path The path of the folder to get
     * @return The retrieved folder, null if not found
     */
    public SerializableFolderFS<T> getFolder(String path) {
        path = getSafePath(path);
        if(path == null) return root;
        FolderInfo<T> folderInfo = folderPool.get(path);
        return folderInfo == null ? saveLoad.loadFolder(path) : folderInfo.getOwner();
    }

    /**
     * Get all folders contained within a path
     *
     * @param path The path to get folders from
     * @return The list of folders in the path
     */
    public List<SerializableFolderFS<T>> getFolders(String path) {
        path = getSafePath(path);
        SerializableFolderFS<T> folder = getFolder(path);
        Validate.notNull(folder, "Tried to get folders from null folder at path \"%s\"", path);
        return folder.getFolders();
    }

    /**
     * Get all folders as a map, where the key is the path to the folder
     *
     * @param path The path to get folders from
     * @return The map of path to folder
     */
    public Map<String, SerializableFolderFS<T>> getFoldersMap(String path) {
        path = getSafePath(path);
        SerializableFolderFS<T> folder = getFolder(path);
        Validate.notNull(folder, "Tried to get folders from null folder at path \"%s\"", path);
        return folder.getFoldersMap();
    }

    /**
     * Get all objects contained in a path
     *
     * @param path The path to get objects from
     * @return The list of objects in the path
     */
    public List<T> getItems(String path) {
        path = getSafePath(path);
        SerializableFolderFS<T> folder = getFolder(path);
        Validate.notNull(folder, "Tried to get items from null folder at path \"%s\"", path);
        return folder.getItems();
    }

    /**
     * Get all objects contained in a path as a map, where the key is the file name of the object
     *
     * @param path The path to get objects from
     * @return The map of file name to object
     */
    public Map<String, T> getItemsMap(String path) {
        path = getSafePath(path);
        SerializableFolderFS<T> folder = getFolder(path);
        Validate.notNull(folder, "Tried to get items from null folder at path \"%s\"", path);
        return folder.getItemsMap();
    }

    /**
     * Get whether this file system is in auto write mode
     *
     * @return Whether auto write is enabled
     */
    public boolean isAutoWrite() {
        return autoWrite;
    }

    /**
     * Set whether to auto write the file system to disk upon a change or not
     *
     * @param autoWrite New auto write state
     */
    public void setAutoWrite(boolean autoWrite) {
        this.autoWrite = autoWrite;
        this.modifier = SaveMode.of(fileMode, autoWrite).getSystemModifier(this);
    }

    /**
     * Get the path to save this file system to. Will be appended to the plugin's data folder path like
     * <code>plugins/plugin_name/savePath</code>
     *
     * @return The path to save this file system to
     */
    public String getSavePath() {
        return savePath;
    }

    /**
     * Get the {@link FolderPool} of this file system. This should <strong>only be used internally</strong>.
     *
     * @return The {@link FolderPool}
     */
    public FolderPool<T> getFolderPool() {
        return folderPool;
    }

    /**
     * Get the {@link FileSystemModifier} of this file system. This should <strong>only be used internally</strong>.
     *
     * @return The {@link FileSystemModifier}
     */
    public FileSystemModifier<T> getModifier() {
        return modifier;
    }

    /**
     * Get the {@link FileSystemSaveLoad} of this file system. This should <strong>only be used internally</strong>.
     *
     * @return The {@link FileSystemSaveLoad}
     */
    public FileSystemSaveLoad<T> getSaveLoad() {
        return saveLoad;
    }

    /**
     * Get the class type of the {@link ConfigurationSerializable} being handled
     *
     * @return The class type being handled
     */
    public Class<T> getSerializableClass() {
        return serializableClass;
    }

    /**
     * Verifies that a specified file name is valid
     *
     * @param name The name to check
     * @return Whether the file name is valid
     */
    public static boolean checkValidFilename(String name) {
        if(name == null) return false;
        try {
            Paths.get(name);
        } catch(InvalidPathException ignored) {
            return false;
        }
        return name.indexOf('/') == -1 && name.indexOf('\\') == -1;
    }

    /**
     * Get the safe path of a potentially unsafe source
     *
     * @param path The path to process
     * @return The safe version of the path
     */
    public static String getSafePath(String path) {
        if(path == null) return null;
        path = path.trim();
        path = path.replace('\\', '/');
        if(path.charAt(0) == '/') path = path.substring(1);
        if(path.charAt(path.length() - 1) == '/') path = path.substring(0, path.length() - 1);
        return path;
    }

    /**
     * Get the safe name of a potentially unsafe source
     *
     * @param name The name to process
     * @return The safe version of the name
     */
    public static String getSafeName(String name) {
        if(name == null) return null;
        name = name.trim();
        name = name.replace('\\', '/');
        name = name.replace("/", "");
        return name;
    }

    /**
     * Get the combined path of a path and a name
     *
     * @param path The path, can be null for root
     * @param name The name
     * @return The combined path
     */
    public static String getPath(@Nullable String path, String name) {
        return path == null ? name : path + "/" + name;
    }

    /**
     * Get the full path from a path. The full path includes the save path but not the plugin data folder path.
     *
     * @param path The path, can be null for root
     * @return The full path
     */
    public String getFullPath(@Nullable String path) {
        return path == null ? savePath : savePath + "/" + path;
    }

    /**
     * Get the full path from a path and a name. The full path includes the save path but not the plugin data folder
     * path.
     *
     * @param path The path, can be null for root
     * @param name The name
     * @return The full path
     */
    public String getFullPath(@Nullable String path, String name) {
        return path == null ? savePath + "/" + name : savePath + "/" + path + "/" + name;
    }

    /**
     * Get the root {@link SerializableFolderFS} of this file system
     *
     * @return The root folder
     */
    public SerializableFolderFS<T> getRootFolder() {
        return root;
    }

    /**
     * Set the maximum folder pool size. The folder pool size specifies how many folders can be stored in memory at one
     * time. The maximum size by default is 10. If using no-op saving (in memory mode), the maximum folder pool size
     * should be set to something large enough to store all folders.
     *
     * @param maxSize The new maximum folder pool size
     */
    public void setMaxPoolSize(int maxSize) {
        this.folderPool.setMaxSize(maxSize);
    }

    /**
     * The mode in which files are saved to disk.
     *
     * @author Mikedeejay2
     */
    public enum SaveMode {
        /**
         * Files must be saved manually using the save method. This method is best for single file mode or batch
         * operations of multi-file mode. It saves all modified items separately, so it could have a higher memory
         * profile with large operations than auto save.
         */
        MANUAL_SAVE(ManualFileSystemModifier::new),

        /**
         * Files are automatically saved using this method. Best for multi-file mode where items are added over time.
         * The drawback to this method is higher disk usage, as it saves whenever an item is added or removed.
         */
        AUTO_SAVE(AutoFileSystemModifier::new),

        /**
         * No operation. Files are not saved to disk.
         */
        NO_OP(BaseSystemModifier::new),
        ;

        /**
         * The {@link ModifierGetter} of the enum
         */
        private final ModifierGetter modifierGetter;

        /**
         * Internal initializer
         *
         * @param modifierGetter The {@link ModifierGetter} of the enum
         */
        SaveMode(ModifierGetter modifierGetter) {
            this.modifierGetter = modifierGetter;
        }

        /**
         * Get the {@link FileSystemModifier} associated with this enum
         *
         * @param system The reference {@link SerializableFileSystem}
         * @return The new {@link FileSystemModifier}
         * @param <T> The data type being handled by the file system
         */
        public <T extends ConfigurationSerializable> FileSystemModifier<T> getSystemModifier(SerializableFileSystem<T> system) {
            return modifierGetter.get(system);
        }

        /**
         * Get the <code>SaveMode</code> based off of a {@link FileMode} and whether auto write is enabled
         *
         * @param mode      The {@link FileMode}
         * @param autoWrite Auto write state
         * @return The enum associated with the inputs
         */
        public static SaveMode of(FileMode mode, boolean autoWrite) {
            if(mode == FileMode.NO_OP) return NO_OP;
            return autoWrite ? AUTO_SAVE : MANUAL_SAVE;
        }

        /**
         * A functional interface for getting a {@link FileSystemModifier}
         *
         * @author Mikedeejay2
         */
        @FunctionalInterface
        public interface ModifierGetter {
            <T extends ConfigurationSerializable> FileSystemModifier<T> get(SerializableFileSystem<T> system);
        }
    }

    /**
     * The file mode used for saving and loading items from disk.
     *
     * @author Mikedeejay2
     */
    public enum FileMode {
        /**
         * Save all items to a single .json file. Good for small portable item file systems but requires that the entire
         * file system be loaded into json.
         * <p>
         * Storage format:
         * <ul>
         *     <li><code>items.json</code></li>
         * </ul>
         */
        SINGLE_FILE(SingleFileSaveLoad::new),
        /**
         * Save items to their own .json file in a real file system on the server machine. This solution is less
         * portable but ensures that only updated or loaded files are saved to the system. This implementation is good
         * for large item file systems.
         * <p>
         * Storage format:
         * <ul>
         *     <li><code>items</code>
         *     <ul>
         *         <li><code>folder1</code>
         *         <ul>
         *             <li><code>sub_item1.json</code></li>
         *             <li><code>sub_item2.json</code></li>
         *         </ul>
         *         </li>
         *         <li><code>folder2</code>
         *         <ul>
         *             <li><code>sub_item1.json</code></li>
         *             <li><code>sub_item2.json</code></li>
         *         </ul>
         *         </li>
         *         <li><code>item1.json</code></li>
         *         <li><code>item2.json</code></li>
         *         <li><code>item3.json</code></li>
         *         <li><code>item4.json</code></li>
         *     </ul>
         *     </li>
         * </ul>
         */
        FILE_SYSTEM(MultiFileSaveLoad::new),

        /**
         * No operation. Items are only stored in memory.
         */
        NO_OP(NoOpSaveLoad::new)
        ;

        /**
         * The {@link SaveLoadGetter} of the enum
         */
        private final SaveLoadGetter saveLoadGetter;

        /**
         * Internal initializer
         *
         * @param saveLoadGetter The {@link SaveLoadGetter} of the enum
         */
        FileMode(SaveLoadGetter saveLoadGetter) {
            this.saveLoadGetter = saveLoadGetter;
        }

        /**
         * Get the {@link FileSystemSaveLoad} associated with this enum
         *
         * @param system The reference {@link SerializableFileSystem}
         * @return The new {@link FileSystemSaveLoad}
         * @param <T> The data type being handled by the file system
         */
        public <T extends ConfigurationSerializable> FileSystemSaveLoad<T> getSaveLoad(SerializableFileSystem<T> system) {
            return saveLoadGetter.get(system.plugin, system);
        }

        /**
         * A functional interface for getting a {@link FileSystemSaveLoad}
         *
         * @author Mikedeejay2
         */
        @FunctionalInterface
        public interface SaveLoadGetter {
            <T extends ConfigurationSerializable> FileSystemSaveLoad<T> get(BukkitPlugin plugin, SerializableFileSystem<T> system);
        }
    }
}
