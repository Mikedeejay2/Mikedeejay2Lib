package com.mikedeejay2.mikedeejay2lib.item;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.mikedeejay2.mikedeejay2lib.BukkitPlugin;
import com.mikedeejay2.mikedeejay2lib.data.json.JsonAccessor;
import com.mikedeejay2.mikedeejay2lib.data.json.JsonFile;
import com.mikedeejay2.mikedeejay2lib.util.head.Base64Head;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.Validate;
import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.nio.file.InvalidPathException;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;

public class ItemFileSystem implements ItemFolder {
    protected final BukkitPlugin plugin;

    protected final ItemFolderFS root;
    protected final String savePath;
    protected boolean autoWrite;
    protected final FileMode fileMode;
    protected FileSystemModifier modifier;
    protected FileSystemSaveLoad saveLoad;

    protected final Cache<String, FolderInfo> folderPool;

    public ItemFileSystem(BukkitPlugin plugin, String name, String initialPath, FileMode fileMode, boolean autoWrite) {
        this.folderPool = CacheBuilder.newBuilder().maximumSize(10).build();
        this.plugin = plugin;
        this.root = new ItemFolderFS(name, null, this);
        this.autoWrite = autoWrite;
        this.fileMode = fileMode;
        this.savePath = initialPath.replace('\\', '/');

        this.modifier = SaveMode.of(fileMode, autoWrite).getSystemModifier(this);
        this.saveLoad = fileMode.getSaveLoad(this);
    }

    public ItemFileSystem(BukkitPlugin plugin, String name, String initialPath, FileMode fileMode) {
        this(plugin, name, initialPath, fileMode, false);
    }

    public ItemFileSystem(BukkitPlugin plugin, String name, String initialPath) {
        this(plugin, name, initialPath, FileMode.SINGLE_FILE);
    }

    public void addItem(String path, String name, ItemStack item) {
        ItemFolderFS folder = getFolder(path);
        Validate.notNull(folder, "Tried to add item to null folder at path \"%s\"", path);
        folder.addItem(name, item);
    }

    public void removeItem(String path, String name) {
        ItemFolderFS folder = getFolder(path);
        Validate.notNull(folder, "Tried to remove item from null folder at path \"%s\"", path);
        folder.removeItem(name);
    }

    public void clearItems(String path) {
        ItemFolderFS folder = getFolder(path);
        Validate.notNull(folder, "Tried to clear items from null folder at path \"%s\"", path);
        folder.clearItems();
    }

    public void createFolder(String path, String name) {
        ItemFolderFS folder = getFolder(path);
        Validate.notNull(folder, "Tried to create folder into null folder at path \"%s\"", path);
        folder.addFolder(name);
    }

    public void removeFolder(String path, String name) {
        ItemFolderFS folder = getFolder(path);
        Validate.notNull(folder, "Tried to remove folder from null folder at path \"%s\"", path);
        folder.removeFolder(name);
    }

    public void clearFolders(String path) {
        ItemFolderFS folder = getFolder(path);
        Validate.notNull(folder, "Tried to clear folders from null folder at path \"%s\"", path);
        folder.clearFolders();
    }

    public void setFolderItem(String path, ItemStack item) {
        ItemFolderFS folder = getFolder(path);
        Validate.notNull(folder, "Tried to set folder item for null folder at path \"%s\"", path);
        folder.setFolderItem(item);
    }

    public void save(String path) {
        ItemFolderFS folder = getFolder(path);
        Validate.notNull(folder, "Tried to save null folder at path \"%s\"", path);
        folder.save();
    }

    public void saveAll() {
        modifier.saveAll();
    }

    public ItemStack getItem(String path, String name) {
        ItemFolderFS folder = getFolder(path);
        Validate.notNull(folder, "Tried to get item from null folder at path \"%s\"", path);
        return folder.getItem(name);
    }

    public String getItemName(String path, ItemStack item) {
        ItemFolderFS folder = getFolder(path);
        Validate.notNull(folder, "Tried to get item name from null folder at path \"%s\"", path);
        return folder.getItemName(item);
    }

    public ItemFolderFS getFolder(String path) {
        Validate.notNull(path, "Tried to get folder from null folder of null path");
        System.out.println("ATTEMPT: " + path);
        for(String cur : folderPool.asMap().keySet()) {
            System.out.println("CACHED: " + cur);
        }
        if(path.equals(root.getPath())) return root;
        FolderInfo folderInfo = folderPool.getIfPresent(path);
        if(folderInfo == null) {
            saveLoad.loadFolder(path);
        }
        return folderInfo == null ? null : folderInfo.owner;
    }

    public List<ItemFolderFS> getFolders(String path) {
        ItemFolderFS folder = getFolder(path);
        Validate.notNull(folder, "Tried to get folders from null folder at path \"%s\"", path);
        return folder.getFolders();
    }

    public Map<String, ItemFolderFS> getFoldersMap(String path) {
        ItemFolderFS folder = getFolder(path);
        Validate.notNull(folder, "Tried to get folders from null folder at path \"%s\"", path);
        return folder.getFoldersMap();
    }

    public List<ItemStack> getItems(String path) {
        ItemFolderFS folder = getFolder(path);
        Validate.notNull(folder, "Tried to get items from null folder at path \"%s\"", path);
        return folder.getItems();
    }

    public Map<String, ItemStack> getItemsMap(String path) {
        ItemFolderFS folder = getFolder(path);
        Validate.notNull(folder, "Tried to get items from null folder at path \"%s\"", path);
        return folder.getItemsMap();
    }

    @Override
    public String getName() {
        return root.getName();
    }

    @Override
    public ItemStack getFolderItem() {
        return this.root.getFolderItem();
    }

    @Override
    public List<? extends ItemFolder> getFolders() {
        return this.root.getFolders();
    }

    @Override
    public List<ItemStack> getItems() {
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

    protected static boolean checkValidFilename(String name) {
        if(name == null) return false;
        try {
            Paths.get(name);
        } catch(InvalidPathException ignored) {
            return false;
        }
        return true;
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

        private final Function<ItemFileSystem, FileSystemModifier> saveLoadFunction;

        SaveMode(Function<ItemFileSystem, FileSystemModifier> saveLoadFunction) {
            this.saveLoadFunction = saveLoadFunction;
        }

        public FileSystemModifier getSystemModifier(ItemFileSystem system) {
            return saveLoadFunction.apply(system);
        }

        public static SaveMode of(FileMode mode, boolean autoWrite) {
            if(mode == FileMode.NO_OP) return NO_OP;
            return autoWrite ? AUTO_SAVE : MANUAL_SAVE;
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

        private final BiFunction<BukkitPlugin, ItemFileSystem, FileSystemSaveLoad> saveLoadFunction;

        FileMode(BiFunction<BukkitPlugin, ItemFileSystem, FileSystemSaveLoad> saveLoadFunction) {
            this.saveLoadFunction = saveLoadFunction;
        }

        public FileSystemSaveLoad getSaveLoad(ItemFileSystem system) {
            return saveLoadFunction.apply(system.plugin, system);
        }
    }

    protected static final class FolderInfo {
        public final Map<String, ItemFolderFS> folders;
        public final ItemFolderFS owner;

        public FolderInfo(Map<String, ItemFolderFS> folders, ItemFolderFS owner) {
            this.folders = folders;
            this.owner = owner;
        }
    }

    public static class ItemFolderFS implements ItemFolder {
        protected final @NotNull ItemFileSystem fileSystem;
        protected @NotNull String name;
        protected @NotNull String path;
        protected ItemStack folderItem = null;
        protected Map<String, ItemStack> items = null;

        public ItemFolderFS(@NotNull String name, String path, @NotNull ItemFileSystem fileSystem) {
            Validate.isTrue(checkValidFilename(name), "File name is invalid, \"%s\"", name);
            this.fileSystem = fileSystem;
            this.name = name;
            this.path = path == null ? name : path.replace('\\', '/') + "/" + name;
            Validate.isTrue(fileSystem.folderPool.getIfPresent(this.getPath()) == null,
                            "A folder with the path \"%s\" already exists", this.getPath());
            fileSystem.folderPool.put(this.getPath(), new FolderInfo(null, this));
        }

        public @NotNull ItemFileSystem getFileSystem() {
            return fileSystem;
        }

        @Override
        public @NotNull String getName() {
            return name;
        }

        public @NotNull String getPath() {
            return path;
        }

        public @NotNull String getFullPath() {
            return fileSystem.getSavePath() + "/" + path;
        }

        @Override
        public ItemStack getFolderItem() {
            if(folderItem == null) {
                folderItem = fileSystem.saveLoad.loadFolderItem(this);
                if(folderItem == null) {
                    folderItem = ItemBuilder
                        .of(Base64Head.FOLDER.get())
                        .setName(name)
                        .setLore("Folder")
                        .get();
                }
            }
            return folderItem;
        }

        public void addItem(String name, ItemStack item) {
            fileSystem.modifier.addItem(this, name, item);
        }

        public void removeItem(String name) {
            fileSystem.modifier.removeItem(this, name);
        }

        public void clearItems() {
            fileSystem.modifier.clearItems(this);
        }

        public void addFolder(String name) {
            fileSystem.modifier.addFolder(this, name, new ItemFolderFS(name, path, fileSystem));
        }

        public void removeFolder(String name) {
            fileSystem.modifier.removeFolder(this, name);
        }

        public void clearFolders() {
            fileSystem.modifier.clearFolders(this);
        }

        public void setFolderItem(ItemStack item) {
            fileSystem.modifier.setFolderItem(this, item);
        }

        public void save() {
            fileSystem.modifier.save(this);
        }

        public ItemStack getItem(String name) {
            return getItemsRaw().get(name);
        }

        public String getItemName(ItemStack item) {
            for(Map.Entry<String, ItemStack> entry : getItemsRaw().entrySet()) {
                if(item.equals(entry.getValue())) return entry.getKey();
            }
            return null;
        }

        public ItemFolderFS getFolder(String name) {
            return getFoldersRaw().get(name);
        }

        @Override
        public List<ItemFolderFS> getFolders() {
            return ImmutableList.copyOf(getFoldersRaw().values());
        }

        public Map<String, ItemFolderFS> getFoldersMap() {
            return ImmutableMap.copyOf(getFoldersRaw());
        }

        protected Map<String, ItemFolderFS> getFoldersRaw() {
            FolderInfo folders = fileSystem.folderPool.getIfPresent(this.getPath());
            if(folders == null || folders.folders == null) {
                return loadFolders();
            }
            return folders.folders;
        }

        private Map<String, ItemFolderFS> loadFolders() {
            Map<String, ItemFolderFS> folders = fileSystem.saveLoad.loadFolders(this);
            fileSystem.folderPool.put(this.getPath(), new FolderInfo(folders, this));
            return folders;
        }

        @Override
        public List<ItemStack> getItems() {
            return ImmutableList.copyOf(getItemsRaw().values());
        }

        public Map<String, ItemStack> getItemsMap() {
            return ImmutableMap.copyOf(getItemsRaw());
        }

        protected Map<String, ItemStack> getItemsRaw() {
            if(items == null) {
                items = fileSystem.saveLoad.loadItems(this);
            }
            return items;
        }
    }

    protected interface FileSystemModifier {
        void addItem(ItemFolderFS owner, String name, ItemStack item);
        void removeItem(ItemFolderFS owner, String name);
        void clearItems(ItemFolderFS owner);

        void addFolder(ItemFolderFS owner, String name, ItemFolderFS folder);
        void removeFolder(ItemFolderFS owner, String name);
        void clearFolders(ItemFolderFS owner);

        void setFolderItem(ItemFolderFS owner, ItemStack item);

        void save(ItemFolderFS owner);
        void saveAll();
    }

    protected static class ManualFileSystemModifier implements FileSystemModifier {
        protected final ItemFileSystem system;
        protected final Map<ItemFolderFS, ChangedItems> changedItems = new LinkedHashMap<>();

        protected ManualFileSystemModifier(ItemFileSystem system) {
            this.system = system;
        }

        @Override
        public void addItem(ItemFolderFS owner, String name, ItemStack item) {
            getChangedItems(owner).addedItems.put(name, item);
            getChangedItems(owner).removedItems.add(name);
            owner.getItemsRaw().put(name, item);
        }

        @Override
        public void removeItem(ItemFolderFS owner, String name) {
            getChangedItems(owner).removedItems.add(name);
            getChangedItems(owner).addedItems.remove(name);
            owner.getItemsRaw().remove(name);
        }

        @Override
        public void clearItems(ItemFolderFS owner) {
            getChangedItems(owner).addedItems.clear();
            getChangedItems(owner).removedItems.addAll(owner.getItemsRaw().keySet());
            owner.getItemsRaw().clear();
        }

        @Override
        public void addFolder(ItemFolderFS owner, String name, ItemFolderFS folder) {
            getChangedItems(owner).addedFolders.put(name, folder);
            getChangedItems(owner).removedFolders.add(name);
            owner.getFoldersRaw().put(name, folder);
        }

        @Override
        public void removeFolder(ItemFolderFS owner, String name) {
            getChangedItems(owner).removedFolders.add(name);
            getChangedItems(owner).addedFolders.remove(name);
            ItemFolderFS removed = owner.getFoldersRaw().remove(name);
            recursiveRemove(removed);
        }

        private void recursiveRemove(ItemFolderFS toRemove) {
            changedItems.remove(toRemove);
            for(ItemFolderFS folder : toRemove.getFoldersRaw().values()) {
                recursiveRemove(folder);
            }
        }

        @Override
        public void clearFolders(ItemFolderFS owner) {
            getChangedItems(owner).addedFolders.clear();
            getChangedItems(owner).removedFolders.addAll(owner.getFoldersRaw().keySet());
            owner.getFoldersRaw().clear();
        }

        @Override
        public void setFolderItem(ItemFolderFS owner, ItemStack item) {
            getChangedItems(owner).folderItem = item;
            owner.folderItem = item;
        }

        @Override
        public void save(ItemFolderFS owner) {
            final ChangedItems changed = getChangedItems(owner);
            final FileSystemSaveLoad saveLoad = system.saveLoad;
            saveLoad.startCommit();
            for(String name : changed.addedFolders.keySet()) {
                saveLoad.saveFolder(changed.addedFolders.get(name));
            }
            for(String name : changed.addedItems.keySet()) {
                saveLoad.saveItem(owner, name, changed.addedItems.get(name));
            }
            for(String name : changed.removedFolders) {
                saveLoad.deleteFolder(owner.getPath() + "/" + name);
            }
            for(String name : changed.removedItems) {
                saveLoad.deleteItem(owner.getPath(), name);
            }
            if(changed.folderItem != null) {
                saveLoad.saveFolderItem(owner, changed.folderItem);
            }
            saveLoad.commit();
            changedItems.remove(owner);
        }

        @Override
        public void saveAll() {
            for(ItemFolderFS folder : changedItems.keySet()) {
                save(folder);
            }
        }

        private ChangedItems getChangedItems(ItemFolderFS owner) {
            if(!changedItems.containsKey(owner)) {
                ChangedItems newChangedItems = new ChangedItems();
                changedItems.put(owner, newChangedItems);
                return newChangedItems;
            }
            return changedItems.get(owner);
        }

        private static final class ChangedItems {
            private final Map<String, ItemFolderFS> addedFolders = new LinkedHashMap<>();
            private final Map<String, ItemStack> addedItems = new LinkedHashMap<>();
            private final List<String> removedFolders = new ArrayList<>();
            private final List<String> removedItems = new ArrayList<>();
            private ItemStack folderItem = null;
        }
    }

    protected static class AutoFileSystemModifier implements FileSystemModifier {
        protected final ItemFileSystem system;

        protected AutoFileSystemModifier(ItemFileSystem system) {
            this.system = system;
        }

        @Override
        public void addItem(ItemFolderFS owner, String name, ItemStack item) {
            owner.getItemsRaw().put(name, item);
            system.saveLoad.saveItem(owner, name, item);
        }

        @Override
        public void removeItem(ItemFolderFS owner, String name) {
            owner.getItemsRaw().remove(name);
            system.saveLoad.deleteItem(owner.getPath(), name);
        }

        @Override
        public void clearItems(ItemFolderFS owner) {
            final List<String> names = ImmutableList.copyOf(owner.getItemsRaw().keySet());
            owner.getItemsRaw().clear();
            system.saveLoad.startCommit();
            for(String name : names) {
                system.saveLoad.deleteItem(owner.getPath(), name);
            }
            system.saveLoad.commit();
        }

        @Override
        public void addFolder(ItemFolderFS owner, String name, ItemFolderFS folder) {
            owner.getFoldersRaw().put(name, folder);
            system.saveLoad.saveFolder(folder);
        }

        @Override
        public void removeFolder(ItemFolderFS owner, String name) {
            owner.getFoldersRaw().remove(name);
            system.saveLoad.deleteFolder(owner.getPath() + "/" + name);
        }

        @Override
        public void clearFolders(ItemFolderFS owner) {
            final List<String> names = ImmutableList.copyOf(owner.getFoldersRaw().keySet());
            owner.getFoldersRaw().clear();
            system.saveLoad.startCommit();
            for(String name : names) {
                system.saveLoad.deleteFolder(owner.getPath() + "/" + name);
            }
            system.saveLoad.commit();
        }

        @Override
        public void setFolderItem(ItemFolderFS owner, ItemStack item) {
            owner.folderItem = item;
            system.saveLoad.saveFolderItem(owner, item);
        }

        @Override
        public void save(ItemFolderFS owner) {}

        @Override
        public void saveAll() {}
    }

    protected static class NoOpSystemModifier implements FileSystemModifier {
        public NoOpSystemModifier(ItemFileSystem unused) {}
        public NoOpSystemModifier() {}

        @Override
        public void addItem(ItemFolderFS owner, String name, ItemStack item) {
            owner.getItemsRaw().put(name, item);
        }

        @Override
        public void removeItem(ItemFolderFS owner, String name) {
            owner.getItemsRaw().remove(name);
        }

        @Override
        public void clearItems(ItemFolderFS owner) {
            owner.getItemsRaw().clear();
        }

        @Override
        public void addFolder(ItemFolderFS owner, String name, ItemFolderFS folder) {
            owner.getFoldersRaw().put(name, folder);
        }

        @Override
        public void removeFolder(ItemFolderFS owner, String name) {
            owner.getFoldersRaw().remove(name);
        }

        @Override
        public void clearFolders(ItemFolderFS owner) {
            owner.getFoldersRaw().clear();
        }

        @Override
        public void setFolderItem(ItemFolderFS owner, ItemStack item) {
            owner.folderItem = item;
        }

        @Override
        public void save(ItemFolderFS owner) {}

        @Override
        public void saveAll() {}
    }



    protected interface FileSystemSaveLoad {
        void saveFolderItem(ItemFolderFS owner, ItemStack item);
        void saveFolder(ItemFolderFS folder);
        void saveItem(ItemFolderFS owner, String name, ItemStack item);
        void deleteFolder(String path);
        void deleteItem(String path, String name);
        void startCommit();
        void commit();

        ItemStack loadFolderItem(ItemFolderFS folder);
        Map<String, ItemFolderFS> loadFolders(ItemFolderFS folder);
        ItemFolderFS loadFolder(String path);
        Map<String, ItemStack> loadItems(ItemFolderFS folder);
    }

    protected static class SingleFileSaveLoad implements FileSystemSaveLoad {
        public static final String KEY_ICON = "icon";
        public static final String KEY_FOLDERS = "folders";
        public static final String KEY_ITEMS = "items";
        public static final String KEY_NAME = "name";

        protected final BukkitPlugin plugin;
        protected final ItemFileSystem system;
        protected final JsonFile file;
        protected final JsonAccessor accessor;
        protected boolean save;

        public SingleFileSaveLoad(BukkitPlugin plugin, ItemFileSystem system) {
            this.plugin = plugin;
            this.system = system;
            this.file = new JsonFile(plugin, system.getSavePath() + ".json");
            if(file.fileExists()) {
                file.loadFromDisk(true);
            }
            this.accessor = file.getAccessor();
            this.save = true;
        }

        @Override
        public void saveFolderItem(ItemFolderFS owner, ItemStack item) {
            getFolderSection(owner).setItemStack(KEY_ICON, item);
            save();
        }

        @Override
        public void saveFolder(ItemFolderFS folder) {
            getFolderSection(folder).setString(KEY_NAME, folder.getName());
            String path = folder.getPath();
            if(path.contains("/")) {
                JsonAccessor parentAccessor = getParentAccessor(path);
                List<String> curFolders = parentAccessor.getStringList(KEY_FOLDERS);
                String name = path.substring(path.lastIndexOf('/') + 1);
                if(!curFolders.contains(name)) curFolders.add(name);
                parentAccessor.setStringList(KEY_FOLDERS, curFolders);
            }
            save();
        }

        protected JsonAccessor getParentAccessor(String path) {
            String parentPath = path.substring(0, path.lastIndexOf('/'));
            return this.accessor.getSection(parentPath);
        }

        @Override
        public void saveItem(ItemFolderFS owner, String name, ItemStack item) {
            getFolderSection(owner, KEY_ITEMS).setItemStack(name, item);
            save();
        }

        @Override
        public void deleteFolder(String path) {
            this.accessor.delete(path);
            if(path.contains("/")) {
                JsonAccessor parentAccessor = getParentAccessor(path);
                List<String> curFolders = parentAccessor.getStringList(KEY_FOLDERS);
                String name = path.substring(path.lastIndexOf('/') + 1);
                curFolders.remove(name);
                parentAccessor.setStringList(KEY_FOLDERS, curFolders);
            }
            save();
        }

        @Override
        public void deleteItem(String path, String name) {
            this.accessor.getSection(path).getSection(KEY_ITEMS).delete(name);
            save();
        }

        @Override
        public void startCommit() {
            this.save = false;
        }

        @Override
        public void commit() {
            if(save) return;
            this.save = true;
            save();
        }

        protected void save() {
            if(!this.save) return;
            Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> file.saveToDisk(true));
        }

        @Override
        public ItemStack loadFolderItem(ItemFolderFS folder) {
            JsonAccessor section = getFolderSection(folder);
            if(!section.contains(KEY_ICON)) return null;
            return section.getItemStack(KEY_ICON);
        }

        @Override
        public Map<String, ItemFolderFS> loadFolders(ItemFolderFS folder) {
            Map<String, ItemFolderFS> folders = new LinkedHashMap<>();
            List<String> folderPaths = getFolderSection(folder).getStringList(KEY_FOLDERS);
            for(String path : folderPaths) {
                path = folder.getPath() + "/" + path;
                ItemFolderFS curFolder = loadFolder(path);
                folders.put(curFolder.getName(), curFolder);
            }
            return folders;
        }

        @Override
        public ItemFolderFS loadFolder(String path) {
            FolderInfo curFolderFromPool = system.folderPool.getIfPresent(path);
            if(curFolderFromPool != null) return curFolderFromPool.owner;
            JsonAccessor curFolder = accessor.getSection(path);
            path = path.contains("/") ? path.substring(0, path.lastIndexOf('/')) : null;
            String name = curFolder.getString(KEY_NAME);
            return new ItemFolderFS(name, path, system);
        }

        @Override
        public Map<String, ItemStack> loadItems(ItemFolderFS folder) {
            Map<String, ItemStack> items = new LinkedHashMap<>();
            JsonAccessor section = getFolderSection(folder, KEY_ITEMS);
            for(String name : section.getKeys(false)) {
                ItemStack item = section.getItemStack(name);
                items.put(name, item);
            }
            return items;
        }

        protected JsonAccessor getFolderSection(ItemFolderFS folder) {
            return this.accessor.getSection(folder.getPath());
        }

        protected JsonAccessor getFolderSection(ItemFolderFS folder, String key) {
            return getFolderSection(folder).getSection(key);
        }
    }

    protected static class MultiFileSaveLoad implements FileSystemSaveLoad {
        public static final String RESERVED_ICON = "_icon";
        public static final String KEY_ITEM = "item";

        protected final BukkitPlugin plugin;
        protected final ItemFileSystem system;
        protected ChangedItems changedItems;
        protected boolean save;

        public MultiFileSaveLoad(BukkitPlugin plugin, ItemFileSystem system) {
            this.plugin = plugin;
            this.system = system;
            this.save = true;
            this.changedItems = new ChangedItems();
        }

        @Override
        public void saveFolderItem(ItemFolderFS owner, ItemStack item) {
            saveItem(owner, RESERVED_ICON, item);
            save();
        }

        @Override
        public void saveFolder(ItemFolderFS folder) {
            changedItems.addedFolders.add(folder.getPath());
            save();
        }

        @Override
        public void saveItem(ItemFolderFS owner, String name, ItemStack item) {
            JsonFile file = getItemFile(owner, name);
            file.getAccessor().setItemStack(KEY_ITEM, item);
            changedItems.addedItems.add(file);
            save();
        }

        @Override
        public void deleteFolder(String path) {
            changedItems.removedFolders.add(path);
            save();
        }

        @Override
        public void deleteItem(String path, String name) {
            changedItems.removedItems.add(String.format("%s%s%s.json", path, File.separator, name));
            save();
        }

        private JsonFile getItemFile(ItemFolderFS folder, String name) {
            return new JsonFile(plugin, String.format("%s%s%s.json", folder.getFullPath(), File.separator, name));
        }

        @Override
        public void startCommit() {
            this.save = false;
        }

        @Override
        public void commit() {
            if(save) return;
            this.save = true;
            save();
        }

        protected void save() {
            if(!this.save) return;
            final ChangedItems finalChanged = changedItems;
            Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
                for(String path : finalChanged.addedFolders) {
                    File file = new File(path);
                    file.mkdirs();
                }
                for(JsonFile file : finalChanged.addedItems) {
                    file.saveToDisk(true);
                }
                for(String path : finalChanged.removedFolders) {
                    try {
                        FileUtils.deleteDirectory(new File(path));
                    } catch(IOException e) {
                        throw new RuntimeException(e);
                    }
                }
                for(String path : finalChanged.removedItems) {
                    File file = new File(path);
                    file.delete();
                }
            });
            changedItems = new ChangedItems();
        }

        @Override
        public ItemStack loadFolderItem(ItemFolderFS folder) {
            return loadItemStack(folder, RESERVED_ICON);
        }

        @Override
        public Map<String, ItemFolderFS> loadFolders(ItemFolderFS folder) {
            Map<String, ItemFolderFS> folders = new LinkedHashMap<>();
            File folderFile = new File(plugin.getDataFolder(), folder.getPath());
            if(!folderFile.exists()) return folders;
            File[] directories = folderFile.listFiles(File::isDirectory);
            if(directories == null) return folders;
            int pluginPathLen = plugin.getDataFolder().getPath().length() + 1;
            for(File file : directories) {
                String path = file.getPath();
                path = path.substring(pluginPathLen, path.lastIndexOf(File.separatorChar));
                String name = file.getName();
                name = name.substring(0, name.lastIndexOf('.'));
                folders.put(name, new ItemFolderFS(name, path, folder.getFileSystem()));
            }
            return folders;
        }

        @Override
        public ItemFolderFS loadFolder(String path) {
            File folderFile = new File(plugin.getDataFolder(), path);
            if(!folderFile.exists()) return null;
            String name = folderFile.getName();
            name = name.substring(0, name.lastIndexOf('.'));
            return new ItemFolderFS(name, path, system);
        }

        @Override
        public Map<String, ItemStack> loadItems(ItemFolderFS folder) {
            Map<String, ItemStack> items = new LinkedHashMap<>();
            File folderFile = new File(plugin.getDataFolder(), folder.getPath());
            if(!folderFile.exists()) return items;
            File[] itemFiles = folderFile.listFiles((f) -> f.isFile() && f.getName().endsWith(".json"));
            if(itemFiles == null) return items;
            for(File file : itemFiles) {
                String name = file.getName();
                name = name.substring(0, name.lastIndexOf('.'));
                ItemStack item = loadItemStack(folder, name);
                items.put(name, item);
            }
            return items;
        }

        private ItemStack loadItemStack(ItemFolderFS folder, String name) {
            String path = String.format("%s%s%s.json", folder.getFullPath(), File.separator, name);
            JsonFile itemFile = new JsonFile(plugin, path);
            if(!itemFile.fileExists()) return null;

            JsonAccessor accessor = itemFile.getAccessor();
            if(!accessor.contains(KEY_ITEM)) {
                plugin.sendSevere(String.format("Unable to load item file \"%s\", no item found", path));
                return null;
            }
            return accessor.getItemStack(KEY_ITEM);
        }

        private static final class ChangedItems {
            private final List<String> addedFolders = new ArrayList<>();
            private final List<JsonFile> addedItems = new ArrayList<>();
            private final List<String> removedFolders = new ArrayList<>();
            private final List<String> removedItems = new ArrayList<>();
        }
    }

    protected static class NoOpSaveLoad implements FileSystemSaveLoad {
        public NoOpSaveLoad(BukkitPlugin plugin, ItemFileSystem system) {}
        public NoOpSaveLoad() {}

        @Override public void saveFolderItem(ItemFolderFS owner, ItemStack item) {}
        @Override public void saveFolder(ItemFolderFS folder) {}
        @Override public void saveItem(ItemFolderFS owner, String name, ItemStack item) {}
        @Override public void deleteFolder(String path) {}
        @Override public void deleteItem(String path, String name) {}
        @Override public void startCommit() {}
        @Override public void commit() {}

        @Override public ItemStack loadFolderItem(ItemFolderFS folder) {
            return null;
        }

        @Override
        public Map<String, ItemFolderFS> loadFolders(ItemFolderFS folder) {
            return new LinkedHashMap<>();
        }

        @Override
        public ItemFolderFS loadFolder(String path) {
            return null;
        }

        @Override
        public Map<String, ItemStack> loadItems(ItemFolderFS folder) {
            return new LinkedHashMap<>();
        }
    }
}
