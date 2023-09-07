package com.mikedeejay2.mikedeejay2lib.data.filesystem.saveload;

import com.mikedeejay2.mikedeejay2lib.data.filesystem.SerializableFolderFS;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

import java.util.Map;

public interface FileSystemSaveLoad<T extends ConfigurationSerializable> {
    void saveFolder(String path);
    void saveObject(String path, String name, T item);
    void deleteFolder(String path);
    void deleteObject(String path, String name);
    void startCommit();
    void commit();

    Map<String, SerializableFolderFS<T>> loadFolders(String path);
    SerializableFolderFS<T> loadFolder(String path);
    Map<String, T> loadObjects(String path);
}
