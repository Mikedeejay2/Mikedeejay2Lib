package com.mikedeejay2.mikedeejay2lib.data.filesystem.saveload;

import com.mikedeejay2.mikedeejay2lib.BukkitPlugin;
import com.mikedeejay2.mikedeejay2lib.data.filesystem.FolderInfo;
import com.mikedeejay2.mikedeejay2lib.data.filesystem.SerializableFolderFS;
import com.mikedeejay2.mikedeejay2lib.data.filesystem.SerializableFileSystem;
import com.mikedeejay2.mikedeejay2lib.data.json.JsonAccessor;
import com.mikedeejay2.mikedeejay2lib.data.json.JsonFile;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.Validate;
import org.bukkit.Bukkit;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class MultiFileSaveLoad<T extends ConfigurationSerializable> implements FileSystemSaveLoad<T> {
    public static final String KEY_ITEM = "item";

    protected final BukkitPlugin plugin;
    protected final SerializableFileSystem<T> system;
    protected ChangedItems changedItems;
    protected boolean save;

    public MultiFileSaveLoad(BukkitPlugin plugin, SerializableFileSystem<T> system) {
        this.plugin = plugin;
        this.system = system;
        this.save = true;
        this.changedItems = new ChangedItems();
    }

    @Override
    public void saveFolder(String path) {
        changedItems.addedFolders.add(system.getFullPath(path));
        save();
    }

    @Override
    public void saveObject(String path, String name, T item) {
        JsonFile file = getItemFile(path, name);
        file.getAccessor().setSerialized(KEY_ITEM, item);
        changedItems.addedItems.add(file);
        save();
    }

    @Override
    public void deleteFolder(String path) {
        changedItems.removedFolders.add(system.getFullPath(path));
        save();
    }

    @Override
    public void deleteObject(String path, String name) {
        changedItems.removedItems.add(system.getFullPath(path, name));
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
        final ChangedItems finalChanged = changedItems;
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            for(String path : finalChanged.addedFolders) {
                File file = new File(plugin.getDataFolder(), path);
                file.mkdirs();
            }
            for(JsonFile file : finalChanged.addedItems) {
                file.saveToDisk(true);
            }
            for(String path : finalChanged.removedFolders) {
                try {
                    FileUtils.deleteDirectory(new File(plugin.getDataFolder(), path));
                } catch(IOException e) {
                    throw new RuntimeException(e);
                }
            }
            for(String path : finalChanged.removedItems) {
                File file = new File(plugin.getDataFolder(), path + ".json");
                file.delete();
            }
        });
        changedItems = new ChangedItems();
    }

    @Override
    public Map<String, SerializableFolderFS<T>> loadFolders(String path) {
        Map<String, SerializableFolderFS<T>> folders = new LinkedHashMap<>();
        File folderFile = new File(plugin.getDataFolder(), system.getFullPath(path));
        if(!folderFile.exists()) return folders;
        File[] directories = folderFile.listFiles(File::isDirectory);
        if(directories == null) return folders;
        int savePathLen = plugin.getDataFolder().getPath().length() + system.getSavePath().length() + 2;
        for(File file : directories) {
            String curPath = SerializableFileSystem.getSafePath(file.getPath().substring(savePathLen));
            String name = file.getName();
            folders.put(name, loadFolder(curPath));
        }
        return folders;
    }

    @Override
    public SerializableFolderFS<T> loadFolder(String path) {
        FolderInfo<T> curFolderFromPool = system.getFolderPool().get(path);
        if(curFolderFromPool != null) return curFolderFromPool.getOwner();
        File folderFile = new File(plugin.getDataFolder(), system.getFullPath(path));
        if(!folderFile.exists()) return null;
        String name = folderFile.getName();
        name = SerializableFileSystem.getSafeName(name);
        path = SerializableFileSystem.getParentPath(path);
        return new SerializableFolderFS<>(name, path, system);
    }

    @Override
    public Map<String, T> loadObjects(String path) {
        Map<String, T> items = new LinkedHashMap<>();
        File folderFile = new File(plugin.getDataFolder(), system.getFullPath(path));
        if(!folderFile.exists()) return items;
        File[] itemFiles = folderFile.listFiles((f) -> f.isFile() && f.getName().endsWith(".json"));
        if(itemFiles == null) return items;
        for(File file : itemFiles) {
            String name = file.getName();
            name = name.substring(0, name.lastIndexOf('.'));
            T item = loadSerialized(path, name);
            items.put(name, item);
        }
        return items;
    }

    @Override
    public T loadObject(String path, String name) {
        return loadSerialized(path, name);
    }

    private T loadSerialized(String path, String name) {
        JsonFile itemFile = getItemFile(path, name);
        if(!itemFile.fileExists()) return null;
        itemFile.loadFromDisk(true);

        JsonAccessor accessor = itemFile.getAccessor();
        if(!accessor.contains(KEY_ITEM)) {
            plugin.sendSevere(String.format("Unable to load item file \"%s\", no item found", path));
            return null;
        }
        return accessor.getSerialized(KEY_ITEM, system.getSerializableClass());
    }

    private JsonFile getItemFile(String path, String name) {
        return new JsonFile(plugin, String.format("%s.json", system.getFullPath(path, name)));
    }

    private static final class ChangedItems {
        private final List<String> addedFolders = new ArrayList<>();
        private final List<JsonFile> addedItems = new ArrayList<>();
        private final List<String> removedFolders = new ArrayList<>();
        private final List<String> removedItems = new ArrayList<>();
    }
}
