package com.mikedeejay2.mikedeejay2lib.data.filesystem.modifier;

import com.mikedeejay2.mikedeejay2lib.data.filesystem.SerializableFolderFS;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

public interface FileSystemModifier<T extends ConfigurationSerializable> {
    void addItem(SerializableFolderFS<T> owner, String name, T item);
    void removeItem(SerializableFolderFS<T> owner, String name);
    void clearItems(SerializableFolderFS<T> owner);

    void addFolder(SerializableFolderFS<T> owner, String name, SerializableFolderFS<T> folder);
    void removeFolder(SerializableFolderFS<T> owner, String name);
    void clearFolders(SerializableFolderFS<T> owner);

    void save(SerializableFolderFS<T> owner);
    void saveAll();
}
