package com.mikedeejay2.mikedeejay2lib.data.filesystem;

import com.mikedeejay2.mikedeejay2lib.BukkitPlugin;
import com.mikedeejay2.mikedeejay2lib.data.filesystem.modifier.AutoFileSystemModifier;
import com.mikedeejay2.mikedeejay2lib.data.filesystem.modifier.FileSystemModifier;
import com.mikedeejay2.mikedeejay2lib.data.filesystem.modifier.ManualFileSystemModifier;
import com.mikedeejay2.mikedeejay2lib.data.filesystem.modifier.NoOpSystemModifier;
import com.mikedeejay2.mikedeejay2lib.data.filesystem.saveload.FileSystemSaveLoad;
import com.mikedeejay2.mikedeejay2lib.data.filesystem.saveload.MultiFileSaveLoad;
import com.mikedeejay2.mikedeejay2lib.data.filesystem.saveload.NoOpSaveLoad;
import com.mikedeejay2.mikedeejay2lib.data.filesystem.saveload.SingleFileSaveLoad;
import org.apache.commons.lang3.Validate;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

import java.nio.file.InvalidPathException;
import java.nio.file.Paths;
import java.util.*;

public class SerializableFileSystem<T extends ConfigurationSerializable> implements SerializableFolder<T> {
    protected final BukkitPlugin plugin;

    protected final Class<T> serializableClass;
    protected final SerializableFolderFS<T> root;
    protected final String savePath;
    protected final FolderPool<T> folderPool;
    protected boolean autoWrite;
    protected final FileMode fileMode;
    protected FileSystemModifier<T> modifier;
    protected FileSystemSaveLoad<T> saveLoad;

    public SerializableFileSystem(BukkitPlugin plugin, Class<T> serializableClass, String name, String initialPath, FileMode fileMode, boolean autoWrite) {
        this.serializableClass = serializableClass;
        this.plugin = plugin;
        this.folderPool = new FolderPool<>(this, 10);
        this.root = new SerializableFolderFS<>(getSafeName(name), null, this, true);
        this.autoWrite = autoWrite;
        this.fileMode = fileMode;
        this.savePath = initialPath.replace('\\', '/');

        this.modifier = SaveMode.of(fileMode, autoWrite).getSystemModifier(this);
        this.saveLoad = fileMode.getSaveLoad(this);
    }

    public SerializableFileSystem(BukkitPlugin plugin, Class<T> serializableClass, String name, String initialPath, FileMode fileMode) {
        this(plugin, serializableClass, name, initialPath, fileMode, false);
    }

    public SerializableFileSystem(BukkitPlugin plugin, Class<T> serializableClass, String name, String initialPath) {
        this(plugin, serializableClass, name, initialPath, FileMode.SINGLE_FILE);
    }

    public void addItem(String path, String name, T item) {
        path = getSafePath(path);
        name = getSafeName(name);
        SerializableFolderFS<T> folder = getFolder(path);
        Validate.notNull(folder, "Tried to add item to null folder at path \"%s\"", path);
        folder.addItem(name, item);
    }

    public void removeItem(String path, String name) {
        path = getSafePath(path);
        name = getSafeName(name);
        SerializableFolderFS<T> folder = getFolder(path);
        Validate.notNull(folder, "Tried to remove item from null folder at path \"%s\"", path);
        folder.removeItem(name);
    }

    public void clearItems(String path) {
        path = getSafePath(path);
        SerializableFolderFS<T> folder = getFolder(path);
        Validate.notNull(folder, "Tried to clear items from null folder at path \"%s\"", path);
        folder.clearItems();
    }

    public void createFolder(String path, String name) {
        path = getSafePath(path);
        name = getSafeName(name);
        SerializableFolderFS<T> folder = getFolder(path);
        Validate.notNull(folder, "Tried to create folder into null folder at path \"%s\"", path);
        folder.addFolder(name);
    }

    public void removeFolder(String path, String name) {
        path = getSafePath(path);
        name = getSafeName(name);
        SerializableFolderFS<T> folder = getFolder(path);
        Validate.notNull(folder, "Tried to remove folder from null folder at path \"%s\"", path);
        folder.removeFolder(name);
    }

    public void clearFolders(String path) {
        path = getSafePath(path);
        SerializableFolderFS<T> folder = getFolder(path);
        Validate.notNull(folder, "Tried to clear folders from null folder at path \"%s\"", path);
        folder.clearFolders();
    }

    public void save(String path) {
        path = getSafePath(path);
        SerializableFolderFS<T> folder = getFolder(path);
        Validate.notNull(folder, "Tried to save null folder at path \"%s\"", path);
        folder.save();
    }

    public void saveAll() {
        modifier.saveAll();
    }

    public T getItem(String path, String name) {
        path = getSafePath(path);
        name = getSafeName(name);
        SerializableFolderFS<T> folder = getFolder(path);
        Validate.notNull(folder, "Tried to get item from null folder at path \"%s\"", path);
        return folder.getItem(name);
    }

    public String getItemName(String path, T item) {
        path = getSafePath(path);
        SerializableFolderFS<T> folder = getFolder(path);
        Validate.notNull(folder, "Tried to get item name from null folder at path \"%s\"", path);
        return folder.getItemName(item);
    }

    public SerializableFolderFS<T> getFolder(String path) {
        path = getSafePath(path);
        if(path == null) return root;
        FolderInfo<T> folderInfo = folderPool.get(path);
        return folderInfo == null ? saveLoad.loadFolder(path) : folderInfo.getOwner();
    }

    public List<SerializableFolderFS<T>> getFolders(String path) {
        path = getSafePath(path);
        SerializableFolderFS<T> folder = getFolder(path);
        Validate.notNull(folder, "Tried to get folders from null folder at path \"%s\"", path);
        return folder.getFolders();
    }

    public Map<String, SerializableFolderFS<T>> getFoldersMap(String path) {
        path = getSafePath(path);
        SerializableFolderFS<T> folder = getFolder(path);
        Validate.notNull(folder, "Tried to get folders from null folder at path \"%s\"", path);
        return folder.getFoldersMap();
    }

    public List<T> getItems(String path) {
        path = getSafePath(path);
        SerializableFolderFS<T> folder = getFolder(path);
        Validate.notNull(folder, "Tried to get items from null folder at path \"%s\"", path);
        return folder.getItems();
    }

    public Map<String, T> getItemsMap(String path) {
        path = getSafePath(path);
        SerializableFolderFS<T> folder = getFolder(path);
        Validate.notNull(folder, "Tried to get items from null folder at path \"%s\"", path);
        return folder.getItemsMap();
    }

    @Override
    public String getName() {
        return root.getName();
    }

    @Override
    public List<? extends SerializableFolder<T>> getFolders() {
        return this.root.getFolders();
    }

    @Override
    public List<T> getItems() {
        return this.root.getItems();
    }

    public boolean isAutoWrite() {
        return autoWrite;
    }

    public void setAutoWrite(boolean autoWrite) {
        this.autoWrite = autoWrite;
        this.modifier = SaveMode.of(fileMode, autoWrite).getSystemModifier(this);
    }

    public String getSavePath() {
        return savePath;
    }

    public FolderPool<T> getFolderPool() {
        return folderPool;
    }

    public FileSystemModifier<T> getModifier() {
        return modifier;
    }

    public FileSystemSaveLoad<T> getSaveLoad() {
        return saveLoad;
    }

    public Class<T> getSerializableClass() {
        return serializableClass;
    }

    public static boolean checkValidFilename(String name) {
        if(name == null) return false;
        try {
            Paths.get(name);
        } catch(InvalidPathException ignored) {
            return false;
        }
        return name.indexOf('/') == -1 && name.indexOf('\\') == -1;
    }

    public static String getSafePath(String path) {
        if(path == null) return null;
        path = path.trim();
        path = path.replace('\\', '/');
        if(path.charAt(0) == '/') path = path.substring(1);
        if(path.charAt(path.length() - 1) == '/') path = path.substring(0, path.length() - 1);
        return path;
    }

    public static String getSafeName(String name) {
        if(name == null) return null;
        name = name.trim();
        name = name.replace('\\', '/');
        name = name.replace("/", "");
        return name;
    }

    public static String getPath(String path, String name) {
        return path == null ? name : path + "/" + name;
    }

    public String getFullPath(String path) {
        return path == null ? savePath : savePath + "/" + path;
    }

    public String getFullPath(String path, String name) {
        return path == null ? savePath + "/" + name : savePath + "/" + path + "/" + name;
    }

    public SerializableFolderFS<T> getRootFolder() {
        return root;
    }

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
        NO_OP(NoOpSystemModifier::new),
        ;

        private final ModifierGetter modifierGetter;

        SaveMode(ModifierGetter modifierGetter) {
            this.modifierGetter = modifierGetter;
        }

        public <T extends ConfigurationSerializable> FileSystemModifier<T> getSystemModifier(SerializableFileSystem<T> system) {
            return modifierGetter.get(system);
        }

        public static SaveMode of(FileMode mode, boolean autoWrite) {
            if(mode == FileMode.NO_OP) return NO_OP;
            return autoWrite ? AUTO_SAVE : MANUAL_SAVE;
        }

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

        private final SaveLoadGetter saveLoadGetter;

        FileMode(SaveLoadGetter saveLoadGetter) {
            this.saveLoadGetter = saveLoadGetter;
        }

        public <T extends ConfigurationSerializable> FileSystemSaveLoad<T> getSaveLoad(SerializableFileSystem<T> system) {
            return saveLoadGetter.get(system.plugin, system);
        }

        @FunctionalInterface
        public interface SaveLoadGetter {
            <T extends ConfigurationSerializable> FileSystemSaveLoad<T> get(BukkitPlugin plugin, SerializableFileSystem<T> system);
        }
    }
}
