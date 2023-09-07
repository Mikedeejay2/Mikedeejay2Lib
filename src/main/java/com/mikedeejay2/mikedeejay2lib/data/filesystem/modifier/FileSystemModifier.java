package com.mikedeejay2.mikedeejay2lib.data.filesystem.modifier;

import com.mikedeejay2.mikedeejay2lib.data.filesystem.SerializableFolderFS;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

public interface FileSystemModifier<T extends ConfigurationSerializable> {
    void addObject(SerializableFolderFS<T> owner, String name, T item);
    void removeObject(SerializableFolderFS<T> owner, String name);
    void clearObjects(SerializableFolderFS<T> owner);
    void renameObject(SerializableFolderFS<T> owner, String name, String newName);
    void moveObject(SerializableFolderFS<T> owner, String name, SerializableFolderFS<T> newOwner, String newName);

    SerializableFolderFS<T> addFolder(SerializableFolderFS<T> owner, String name);
    void removeFolder(SerializableFolderFS<T> folder);
    void clearFolders(SerializableFolderFS<T> folder);
    void renameFolder(SerializableFolderFS<T> folder, String newName);
    void moveFolder(SerializableFolderFS<T> folder, SerializableFolderFS<T> newOwner, String newName);

    void save(String path);
    void saveAll();
}
