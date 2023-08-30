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
    public void saveFolder(SerializableFolderFS<T> folder) {
        changedItems.addedFolders.add(folder.getFullPath());
        save();
    }

    @Override
    public void saveItem(SerializableFolderFS<T> owner, String name, T item) {
        JsonFile file = getItemFile(owner, name);
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
    public void deleteItem(String path, String name) {
        changedItems.removedItems.add(system.getFullPath(path, name));
        save();
    }

    private JsonFile getItemFile(SerializableFolderFS<T> folder, String name) {
        return new JsonFile(plugin, String.format("%s/%s.json", folder.getFullPath(), name));
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
                File file = new File(plugin.getDataFolder(), path);
                file.delete();
            }
        });
        changedItems = new ChangedItems();
    }

    @Override
    public Map<String, SerializableFolderFS<T>> loadFolders(SerializableFolderFS<T> folder) {
        Map<String, SerializableFolderFS<T>> folders = new LinkedHashMap<>();
        File folderFile = new File(plugin.getDataFolder(), folder.getFullPath());
        if(!folderFile.exists()) return folders;
        File[] directories = folderFile.listFiles(File::isDirectory);
        if(directories == null) return folders;
        int savePathLen = plugin.getDataFolder().getPath().length() + system.getSavePath().length() + 2;
        for(File file : directories) {
            String path = system.getSafePath(file.getPath().substring(savePathLen));
            String name = file.getName();
            folders.put(name, loadFolder(path));
        }
        return folders;
    }

    @Override
    public SerializableFolderFS<T> loadFolder(String path) {
        FolderInfo<T> curFolderFromPool = system.getFolderPool().get(path);
        if(curFolderFromPool != null) return curFolderFromPool.getOwner();
        File folderFile = new File(plugin.getDataFolder(), system.getFullPath(path));
        Validate.isTrue(folderFile.exists(), "A folder \"%s\" does not exist.", path);
        String name = folderFile.getName();
        name = SerializableFileSystem.getSafeName(name);
        path = path.indexOf('/') != -1 ? path.substring(0, path.lastIndexOf('/')) : null;
        return new SerializableFolderFS<>(name, path, system);
    }

    @Override
    public Map<String, T> loadItems(SerializableFolderFS<T> folder) {
        Map<String, T> items = new LinkedHashMap<>();
        File folderFile = new File(plugin.getDataFolder(), folder.getFullPath());
        if(!folderFile.exists()) return items;
        File[] itemFiles = folderFile.listFiles((f) -> f.isFile() && f.getName().endsWith(".json"));
        if(itemFiles == null) return items;
        for(File file : itemFiles) {
            String name = file.getName();
            name = name.substring(0, name.lastIndexOf('.'));
            T item = loadSerialized(folder, name);
            items.put(name, item);
        }
        return items;
    }

    private T loadSerialized(SerializableFolderFS<T> folder, String name) {
        String path = String.format("%s/%s.json", folder.getFullPath(), name);
        JsonFile itemFile = new JsonFile(plugin, path);
        if(!itemFile.fileExists()) return null;
        itemFile.loadFromDisk(true);

        JsonAccessor accessor = itemFile.getAccessor();
        if(!accessor.contains(KEY_ITEM)) {
            plugin.sendSevere(String.format("Unable to load item file \"%s\", no item found", path));
            return null;
        }
        return accessor.getSerialized(KEY_ITEM, system.getSerializableClass());
    }

    private static final class ChangedItems {
        private final List<String> addedFolders = new ArrayList<>();
        private final List<JsonFile> addedItems = new ArrayList<>();
        private final List<String> removedFolders = new ArrayList<>();
        private final List<String> removedItems = new ArrayList<>();
    }
}
