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
    public static final String KEY_ROOT = "_root";

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
    public void saveFolder(String path) {
        getFolderSection(path); // Add folder section
        if(path != null) { // Saves relation to parent, not needed on root folder
            JsonAccessor parentAccessor = getParentAccessor(path);
            List<String> curFolders = parentAccessor.getStringList(KEY_FOLDERS);
            String name = SerializableFileSystem.getNameFromPath(path);
            if(!curFolders.contains(name)) curFolders.add(name);
            parentAccessor.setStringList(KEY_FOLDERS, curFolders);
        }
        save();
    }

    protected JsonAccessor getParentAccessor(String path) {
        if(!path.contains("/")) return this.accessor.getSection(KEY_ROOT);
        String parentPath = path.substring(0, path.lastIndexOf('/'));
        return this.accessor.getSection(parentPath);
    }

    @Override
    public void saveObject(String path, String name, T item) {
        getFolderSection(path, KEY_ITEMS).setSerialized(name, item);
        save();
    }

    @Override
    public void deleteFolder(String path) {
        this.accessor.delete(path);
        if(path != null) { // Deletes relation to parent, not needed on root folder
            JsonAccessor parentAccessor = getParentAccessor(path);
            List<String> curFolders = parentAccessor.getStringList(KEY_FOLDERS);
            String name = SerializableFileSystem.getNameFromPath(path);
            curFolders.remove(name);
            parentAccessor.setStringList(KEY_FOLDERS, curFolders);
        }
        save();
    }

    @Override
    public void deleteObject(String path, String name) {
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
    public Map<String, SerializableFolderFS<T>> loadFolders(String path) {
        Map<String, SerializableFolderFS<T>> folders = new LinkedHashMap<>();
        List<String> folderNames = getFolderSection(path).getStringList(KEY_FOLDERS);
        for(String name : folderNames) {
            String curPath = SerializableFileSystem.getPath(path, name);
            SerializableFolderFS<T> curFolder = loadFolder(curPath);
            folders.put(curFolder.getName(), curFolder);
        }
        return folders;
    }

    @Override
    public SerializableFolderFS<T> loadFolder(String path) {
        FolderInfo<T> curFolderFromPool = system.getFolderPool().get(path);
        if(curFolderFromPool != null) return curFolderFromPool.getOwner();
        Validate.isTrue(accessor.contains(path), "A folder \"%s\" does not exist.", path);
        String newPath = SerializableFileSystem.getParentPath(path);
        String name = SerializableFileSystem.getNameFromPath(path);
        return new SerializableFolderFS<>(name, newPath, system);
    }

    @Override
    public Map<String, T> loadObjects(String path) {
        Map<String, T> items = new LinkedHashMap<>();
        JsonAccessor section = getFolderSection(path, KEY_ITEMS);
        for(String name : section.getKeys(false)) {
            T item = section.getSerialized(name, system.getSerializableClass());
            items.put(name, item);
        }
        return items;
    }

    @Override
    public T loadObject(String path, String name) {
        JsonAccessor section = getFolderSection(path, KEY_ITEMS);
        return section.getSerialized(name, system.getSerializableClass());
    }

    protected JsonAccessor getFolderSection(String path) {
        if(path == null) return this.accessor.getSection(KEY_ROOT);
        return this.accessor.getSection(path);
    }

    protected JsonAccessor getFolderSection(String path, String key) {
        return getFolderSection(path).getSection(key);
    }
}
