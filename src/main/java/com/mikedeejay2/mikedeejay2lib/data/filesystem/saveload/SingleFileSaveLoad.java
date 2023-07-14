package com.mikedeejay2.mikedeejay2lib.data.filesystem.saveload;

import com.mikedeejay2.mikedeejay2lib.BukkitPlugin;
import com.mikedeejay2.mikedeejay2lib.data.filesystem.FolderInfo;
import com.mikedeejay2.mikedeejay2lib.data.filesystem.SerializableFolderFS;
import com.mikedeejay2.mikedeejay2lib.data.filesystem.SerializableFileSystem;
import com.mikedeejay2.mikedeejay2lib.data.json.JsonAccessor;
import com.mikedeejay2.mikedeejay2lib.data.json.JsonFile;
import org.apache.commons.lang3.Validate;
import org.bukkit.Bukkit;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class SingleFileSaveLoad<T extends ConfigurationSerializable> implements FileSystemSaveLoad<T> {
    public static final String KEY_FOLDERS = "folders";
    public static final String KEY_ITEMS = "items";
    public static final String KEY_NAME = "name";

    protected final BukkitPlugin plugin;
    protected final SerializableFileSystem<T> system;
    protected final JsonFile file;
    protected final JsonAccessor accessor;
    protected boolean save;

    public SingleFileSaveLoad(BukkitPlugin plugin, SerializableFileSystem<T> system) {
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
    public void saveFolder(SerializableFolderFS<T> folder) {
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
    public void saveItem(SerializableFolderFS<T> owner, String name, T item) {
        getFolderSection(owner, KEY_ITEMS).setSerialized(name, item);
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
    public Map<String, SerializableFolderFS<T>> loadFolders(SerializableFolderFS<T> folder) {
        Map<String, SerializableFolderFS<T>> folders = new LinkedHashMap<>();
        List<String> folderPaths = getFolderSection(folder).getStringList(KEY_FOLDERS);
        for(String path : folderPaths) {
            path = folder.getPath() + "/" + path;
            SerializableFolderFS<T> curFolder = loadFolder(path);
            folders.put(curFolder.getName(), curFolder);
        }
        return folders;
    }

    @Override
    public SerializableFolderFS<T> loadFolder(String path) {
        FolderInfo<T> curFolderFromPool = system.getFolderPool().get(path);
        if(curFolderFromPool != null) return curFolderFromPool.owner;
        Validate.isTrue(accessor.contains(path), "A folder \"%s\" does not exist.", path);
        JsonAccessor curFolder = accessor.getSection(path);
        path = path.contains("/") ? path.substring(0, path.lastIndexOf('/')) : null;
        String name = curFolder.getString(KEY_NAME);
        return new SerializableFolderFS<>(name, path, system);
    }

    @Override
    public Map<String, T> loadItems(SerializableFolderFS<T> folder) {
        Map<String, T> items = new LinkedHashMap<>();
        JsonAccessor section = getFolderSection(folder, KEY_ITEMS);
        for(String name : section.getKeys(false)) {
            T item = section.getSerialized(name, system.getSerializableClass());
            items.put(name, item);
        }
        return items;
    }

    protected JsonAccessor getFolderSection(SerializableFolderFS<T> folder) {
        return this.accessor.getSection(folder.getPath());
    }

    protected JsonAccessor getFolderSection(SerializableFolderFS<T> folder, String key) {
        return getFolderSection(folder).getSection(key);
    }
}
