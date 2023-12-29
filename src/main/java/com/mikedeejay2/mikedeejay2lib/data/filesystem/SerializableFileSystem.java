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
 *         method is best for single file mode or batch operations of multi-file mode. It saves all modified objects
 *         separately, so it could have a higher memory profile with large operations than auto save.
 *     </li>
 *     <li>
 *         <strong>{@link SaveMode#AUTO_SAVE}</strong> - Files are automatically saved using this method. Best for
 *         multi-file mode where objects are added over time. The drawback to this method is higher disk usage, as it
 *         saves whenever an object is added or removed.
 *     </li>
 * </ul>
 * The save mode is set by the <code>autoWrite</code> parameter in the constructor.
 * <p>
 * The {@link FileMode} enum specifies how data is stored to disk:
 * <ul>
 *     <li>
 *         <strong>{@link FileMode#SINGLE_FILE}</strong> - Save all objects to a single .json file. Good for small
 *         portable object file systems but requires that the entire file system be loaded into json.
 *         <p>
 *         Storage format:
 *         <ul>
 *             <li><code>objects.json</code></li>
 *         </ul>
 *     </li>
 *     <li>
 *         <strong>{@link FileMode#FILE_SYSTEM}</strong> - Save objects to their own .json file in a real file system on
 *         the server machine. This solution is less portable but ensures that only updated or loaded files are saved to
 *         the system. This implementation is good for large object file systems.
 *         <p>
 *         Storage format:
 *         <ul>
 *             <li><code>objects</code>
 *                 <ul>
 *                     <li><code>folder1</code>
 *                         <ul>
 *                             <li><code>sub_object1.json</code></li>
 *                             <li><code>sub_object2.json</code></li>
 *                         </ul>
 *                     </li>
 *                     <li><code>folder2</code>
 *                         <ul>
 *                             <li><code>sub_object1.json</code></li>
 *                             <li><code>sub_object2.json</code></li>
 *                         </ul>
 *                     </li>
 *                     <li><code>object1.json</code></li>
 *                     <li><code>object2.json</code></li>
 *                     <li><code>object3.json</code></li>
 *                     <li><code>object4.json</code></li>
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
     * @param savePath          The path to save this file system to. Will be appended to the plugin's data folder path
     */
    public SerializableFileSystem(BukkitPlugin plugin, Class<T> serializableClass, String name, String savePath, FileMode fileMode) {
        this(plugin, serializableClass, name, savePath, fileMode, false);
    }

    /**
     * Construct a new <code>SerializableFileSystem</code> with auto write off in single file mode
     *
     * @param plugin            The {@link BukkitPlugin} instance
     * @param serializableClass The class type of the {@link ConfigurationSerializable} being handled
     * @param name              The display name of the root folder
     * @param savePath          The path to save this file system to. Will be appended to the plugin's data folder path
     */
    public SerializableFileSystem(BukkitPlugin plugin, Class<T> serializableClass, String name, String savePath) {
        this(plugin, serializableClass, name, savePath, FileMode.SINGLE_FILE);
    }

    /**
     * Add an object to the file system at a specified path.
     *
     * @param path The path to add the object to
     * @param name The file name of the object
     * @param obj The object to add
     */
    public void addObject(String path, String name, T obj) {
        SerializableFolderFS<T> folder = getFolder(path);
        Validate.notNull(folder, "Tried to add object to null folder at path \"%s\"", path);
        folder.addObject(name, obj);
    }

    /**
     * Remove an object from the file system
     *
     * @param path The path to remove the object from
     * @param name The file name of the object
     */
    public void removeObject(String path, String name) {
        name = getSafeName(name);
        SerializableFolderFS<T> folder = getFolder(path);
        Validate.notNull(folder, "Tried to remove object from null folder at path \"%s\"", path);
        folder.removeObject(name);
    }

    /**
     * Clear all objects at a specified path (shallow)
     *
     * @param path The path to clear all objects from
     */
    public void clearObjects(String path) {
        SerializableFolderFS<T> folder = getFolder(path);
        Validate.notNull(folder, "Tried to clear objects from null folder at path \"%s\"", path);
        folder.clearObjects();
    }

    /**
     * Rename an object in the file system
     *
     * @param path    The path of the object to rename
     * @param name    The current name of the object
     * @param newName The new name of the object
     */
    public void renameObject(String path, String name, String newName) {
        SerializableFolderFS<T> folder = getFolder(path);
        Validate.notNull(folder, "Tried to rename object from null folder at path \"%s\"", path);
        name = getSafeName(name);
        newName = getSafeName(newName);
        folder.renameObject(name, newName);
    }

    /**
     * Rename an object in the file system
     *
     * @param path    The path of the object to rename
     * @param name    The current name of the object
     * @param newPath The new path of the object
     * @param newName The new name of the object
     */
    public void moveObject(String path, String name, String newPath, String newName) {
        newPath = getSafePath(newPath);
        SerializableFolderFS<T> folder = getFolder(path);
        Validate.notNull(folder, "Tried to move object from null folder at path \"%s\"", path);
        SerializableFolderFS<T> destination = getFolder(newPath);
        Validate.notNull(destination, "Tried to move object to null folder at path \"%s\"", newPath);
        name = getSafeName(name);
        newName = getSafeName(newName);
        folder.moveObject(destination, name, newName);
    }

    /**
     * Get whether this file system contains an object at a specified path and name
     *
     * @param path The path to the object
     * @param name The name of the object
     * @return Whether an object was found at that path and name
     */
    public boolean containsObject(String path, String name) {
        return getObject(path, name) != null;
    }

    /**
     * Create a new folder in the file system
     *
     * @param path The path to create the folder at
     * @param name The name of the folder
     * @return The created folder
     */
    public SerializableFolderFS<T> createFolder(String path, String name) {
        name = getSafeName(name);
        SerializableFolderFS<T> folder = getFolder(path);
        Validate.notNull(folder, "Tried to create folder into null folder at path \"%s\"", path);
        return folder.addFolder(name);
    }

    /**
     * Remove a folder from the file system
     *
     * @param path The path to remove the folder from
     * @param name The name of the folder to remove
     */
    public void removeFolder(String path, String name) {
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
        SerializableFolderFS<T> folder = getFolder(path);
        Validate.notNull(folder, "Tried to clear folders from null folder at path \"%s\"", path);
        folder.clearFolders();
    }

    /**
     * Rename a folder in the file system
     *
     * @param path    The path of the folder
     * @param newName The new name of the folder
     */
    public void renameFolder(String path, String newName) {
        SerializableFolderFS<T> folder = getFolder(path);
        Validate.notNull(folder, "Tried to rename folder from null folder at path \"%s\"", path);
        newName = getSafeName(newName);
        modifier.renameFolder(folder, newName);
    }

    /**
     * Move a folder in the file system
     *
     * @param path    The current path of the folder
     * @param newPath The new path of the folder
     */
    public void moveFolder(String path, String newPath) {
        newPath = getSafePath(newPath);
        SerializableFolderFS<T> folder = getFolder(path);
        Validate.notNull(folder, "Tried to move folder from null folder at path \"%s\"", path);
        String folderPath = newPath.contains("/") ? newPath.substring(0, newPath.lastIndexOf('/')) : null;
        String folderName = newPath.contains("/") ? newPath.substring(newPath.lastIndexOf('/') + 1) : newPath;
        SerializableFolderFS<T> newOwner = getFolder(folderPath);
        Validate.notNull(folder, "Tried to move folder to null folder at path \"%s\"", folderPath);
        modifier.moveFolder(folder, newOwner, folderName);
    }

    /**
     * Get whether this file system contains a folder of a specified path
     *
     * @param path The path to check for
     * @return Whether a folder was found at the path
     */
    public boolean containsFolder(String path) {
        return getFolder(path) != null;
    }

    /**
     * Save a specific folder to disk. Redundant if auto write is on.
     *
     * @param path The path to save to disk
     */
    public void save(String path) {
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
    public T getObject(String path, String name) {
        name = getSafeName(name);
        SerializableFolderFS<T> folder = getFolder(path);
        Validate.notNull(folder, "Tried to get item from null folder at path \"%s\"", path);
        return folder.getObject(name);
    }

    /**
     * Get the file name of an object based off of an equal object
     *
     * @param path The path to get the object name from
     * @param obj The object to find the name of
     * @return The retrieved file name, null if not found
     */
    public String getObjectName(String path, T obj) {
        SerializableFolderFS<T> folder = getFolder(path);
        Validate.notNull(folder, "Tried to get item name from null folder at path \"%s\"", path);
        return folder.getObjectName(obj);
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
    public List<T> getObjects(String path) {
        SerializableFolderFS<T> folder = getFolder(path);
        Validate.notNull(folder, "Tried to get items from null folder at path \"%s\"", path);
        return folder.getObjects();
    }

    /**
     * Get all objects contained in a path as a map, where the key is the file name of the object
     *
     * @param path The path to get objects from
     * @return The map of file name to object
     */
    public Map<String, T> getObjectsMap(String path) {
        SerializableFolderFS<T> folder = getFolder(path);
        Validate.notNull(folder, "Tried to get items from null folder at path \"%s\"", path);
        return folder.getObjectsMap();
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
        if(path.isEmpty()) return null;
        path = path.replace('\\', '/');

        while(!path.isEmpty() && (path.charAt(0) == '/' || path.charAt(0) == ' ')) {
            path = path.substring(1);
        }
        while(!path.isEmpty() && (path.charAt(path.length() - 1) == '/' || path.charAt(path.length() - 1) == ' ')) {
            path = path.substring(0, path.length() - 1);
        }
        if(path.isEmpty()) return null;

        final List<String> strs = new ArrayList<>(Arrays.asList(path.split("/")));
        strs.removeIf(cur -> cur.equals("..") || cur.equals(".") || cur.isEmpty());
        StringBuilder builder = new StringBuilder();
        for(String cur : strs) {
            if(builder.length() == 0) builder.append(cur);
            else builder.append('/').append(cur);
        }
        String result = builder.toString();
        return result.isEmpty() ? null : result.trim();
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
        name = name.replace(":", "");
        name = name.replace("*", "");
        name = name.replace("?", "");
        name = name.replace("\"", "");
        name = name.replace("<", "");
        name = name.replace(">", "");
        name = name.replace("|", "");
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
     * Get the parent path of a path, example:
     * <p>
     * <code>folder1/folder2/item1</code> would become <code>folder1/folder2</code>
     *
     * @param path The path
     * @return The parent path
     */
    public static String getParentPath(String path) {
        return path.indexOf('/') != -1 ? path.substring(0, path.lastIndexOf('/')) : null;
    }

    /**
     * Get the name out of a path, example:
     * <p>
     * <code>folder1/folder2/item1</code> would become <code>item1</code>
     *
     * @param path The path
     * @return The retrieved name
     */
    public static String getNameFromPath(String path) {
        return path.indexOf('/') != -1 ? path.substring(path.lastIndexOf('/') + 1) : path;
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
         * operations of multi-file mode. It saves all modified objects separately, so it could have a higher memory
         * profile with large operations than auto save.
         */
        MANUAL_SAVE(ManualFileSystemModifier::new),

        /**
         * Files are automatically saved using this method. Best for multi-file mode where objects are added over time.
         * The drawback to this method is higher disk usage, as it saves whenever an object is added or removed.
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
     * The file mode used for saving and loading objects from disk.
     *
     * @author Mikedeejay2
     */
    public enum FileMode {
        /**
         * Save all objects to a single .json file. Good for small portable object file systems but requires that the
         * entire file system be loaded into json.
         * <p>
         * Storage format:
         * <ul>
         *     <li><code>objects.json</code></li>
         * </ul>
         */
        SINGLE_FILE(SingleFileSaveLoad::new),
        /**
         * Save objects to their own .json file in a real file system on the server machine. This solution is less
         * portable but ensures that only updated or loaded files are saved to the system. This implementation is good
         * for large object file systems.
         * <p>
         * Storage format:
         * <ul>
         *     <li><code>objects</code>
         *     <ul>
         *         <li><code>folder1</code>
         *         <ul>
         *             <li><code>sub_object1.json</code></li>
         *             <li><code>sub_object2.json</code></li>
         *         </ul>
         *         </li>
         *         <li><code>folder2</code>
         *         <ul>
         *             <li><code>sub_object1.json</code></li>
         *             <li><code>sub_object2.json</code></li>
         *         </ul>
         *         </li>
         *         <li><code>object1.json</code></li>
         *         <li><code>object2.json</code></li>
         *         <li><code>object3.json</code></li>
         *         <li><code>object4.json</code></li>
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
