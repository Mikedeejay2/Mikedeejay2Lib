package com.mikedeejay2.mikedeejay2lib.data.filesystem.saveload;

import com.mikedeejay2.mikedeejay2lib.BukkitPlugin;
import com.mikedeejay2.mikedeejay2lib.data.filesystem.SerializableFolderFS;
import com.mikedeejay2.mikedeejay2lib.data.filesystem.SerializableFileSystem;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

import java.util.LinkedHashMap;
import java.util.Map;

public class NoOpSaveLoad<T extends ConfigurationSerializable> implements FileSystemSaveLoad<T> {
    public NoOpSaveLoad(BukkitPlugin plugin, SerializableFileSystem<T> system) {}
    public NoOpSaveLoad() {}

    @Override public void saveFolder(String path) {}
    @Override public void saveItem(String path, String name, T item) {}
    @Override public void deleteFolder(String path) {}
    @Override public void deleteItem(String path, String name) {}
    @Override public void startCommit() {}
    @Override public void commit() {}

    @Override
    public Map<String, SerializableFolderFS<T>> loadFolders(String path) {
        return new LinkedHashMap<>();
    }

    @Override
    public SerializableFolderFS<T> loadFolder(String path) {
        return null;
    }

    @Override
    public Map<String, T> loadItems(String path) {
        return new LinkedHashMap<>();
    }
}
