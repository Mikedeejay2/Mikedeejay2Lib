package com.mikedeejay2.mikedeejay2lib.data.filesystem.saveload;

import com.mikedeejay2.mikedeejay2lib.data.filesystem.SerializableFolderFS;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

import java.util.Map;

public interface FileSystemSaveLoad<T extends ConfigurationSerializable> {
    void saveFolder(SerializableFolderFS<T> folder);
    void saveItem(SerializableFolderFS<T> owner, String name, T item);
    void deleteFolder(String path);
    void deleteItem(String path, String name);
    void startCommit();
    void commit();

    Map<String, SerializableFolderFS<T>> loadFolders(SerializableFolderFS<T> folder);
    SerializableFolderFS<T> loadFolder(String path);
    Map<String, T> loadItems(SerializableFolderFS<T> folder);
}
