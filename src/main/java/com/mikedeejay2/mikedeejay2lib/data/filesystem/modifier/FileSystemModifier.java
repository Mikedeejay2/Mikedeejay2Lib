package com.mikedeejay2.mikedeejay2lib.data.filesystem.modifier;

import com.mikedeejay2.mikedeejay2lib.data.filesystem.SerializableFolderFS;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

public interface FileSystemModifier<T extends ConfigurationSerializable> {
    void addItem(SerializableFolderFS<T> owner, String name, T item);
    void removeItem(SerializableFolderFS<T> owner, String name);
    void clearItems(SerializableFolderFS<T> owner);
    void renameItem(SerializableFolderFS<T> owner, String name, String newName);
    void moveItem(SerializableFolderFS<T> owner, String name, SerializableFolderFS<T> newOwner, String newName);

    SerializableFolderFS<T> addFolder(SerializableFolderFS<T> owner, String name);
    void removeFolder(SerializableFolderFS<T> folder);
    void clearFolders(SerializableFolderFS<T> folder);
    void renameFolder(SerializableFolderFS<T> folder, String newName);
    void moveFolder(SerializableFolderFS<T> folder, SerializableFolderFS<T> newOwner, String newName);

    void save(String path);
    void saveAll();
}
