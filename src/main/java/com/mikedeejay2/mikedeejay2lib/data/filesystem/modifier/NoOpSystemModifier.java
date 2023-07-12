package com.mikedeejay2.mikedeejay2lib.data.filesystem.modifier;

import com.mikedeejay2.mikedeejay2lib.data.filesystem.SerializableFolderFS;
import com.mikedeejay2.mikedeejay2lib.data.filesystem.SerializableFileSystem;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

public class NoOpSystemModifier<T extends ConfigurationSerializable> implements FileSystemModifier<T> {
    public NoOpSystemModifier(SerializableFileSystem<T> unused) {}
    public NoOpSystemModifier() {}

    @Override
    public void addItem(SerializableFolderFS<T> owner, String name, T item) {
        owner.getItemsRaw().put(name, item);
    }

    @Override
    public void removeItem(SerializableFolderFS<T> owner, String name) {
        owner.getItemsRaw().remove(name);
    }

    @Override
    public void clearItems(SerializableFolderFS<T> owner) {
        owner.getItemsRaw().clear();
    }

    @Override
    public void addFolder(SerializableFolderFS<T> owner, String name, SerializableFolderFS<T> folder) {
        owner.getFoldersRaw().put(name, folder);
    }

    @Override
    public void removeFolder(SerializableFolderFS<T> owner, String name) {
        owner.getFoldersRaw().remove(name);
    }

    @Override
    public void clearFolders(SerializableFolderFS<T> owner) {
        owner.getFoldersRaw().clear();
    }

    @Override
    public void save(SerializableFolderFS<T> owner) {}

    @Override
    public void saveAll() {}
}
