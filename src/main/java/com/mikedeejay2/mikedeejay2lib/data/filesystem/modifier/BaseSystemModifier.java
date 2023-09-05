package com.mikedeejay2.mikedeejay2lib.data.filesystem.modifier;

import com.google.common.collect.ImmutableSet;
import com.mikedeejay2.mikedeejay2lib.data.filesystem.SerializableFolderFS;
import com.mikedeejay2.mikedeejay2lib.data.filesystem.SerializableFileSystem;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

public class BaseSystemModifier<T extends ConfigurationSerializable> implements FileSystemModifier<T> {
    protected final SerializableFileSystem<T> system;

    public BaseSystemModifier(SerializableFileSystem<T> system) {
        this.system = system;
    }

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
        for(String name : ImmutableSet.copyOf(owner.getItemsRaw().keySet())) {
            removeItem(owner, name);
        }
    }

    @Override
    public void renameItem(SerializableFolderFS<T> owner, String name, String newName) {
        moveItem(owner, name, owner, newName);
    }

    @Override
    public void moveItem(SerializableFolderFS<T> owner, String name, SerializableFolderFS<T> newOwner, String newName) {
        addItem(newOwner, newName, owner.getItem(name));
        removeItem(owner, name);
    }

    @Override
    public SerializableFolderFS<T> addFolder(SerializableFolderFS<T> owner, String name) {
        final SerializableFolderFS<T> folder = new SerializableFolderFS<>(name, owner.getPath(), system);
        owner.getFoldersRaw().put(name, folder);
        return folder;
    }

    @Override
    public void removeFolder(SerializableFolderFS<T> owner, String name) {
        recursiveRemoveFolder(owner, name);
        system.getFolderPool().get(owner.getPath()).setFolders(null);
    }

    protected void recursiveRemoveFolder(SerializableFolderFS<T> owner, String name) {
        SerializableFolderFS<T> folder = owner.getFolder(name);
        for(String curName : ImmutableSet.copyOf(folder.getFoldersRaw().keySet())) {
            recursiveRemoveFolder(folder, curName);
        }
        removeSingleFolder(owner, name);
    }

    protected void removeSingleFolder(SerializableFolderFS<T> owner, String name) {
        owner.getFoldersRaw().remove(name);
        system.getFolderPool().remove(SerializableFileSystem.getPath(owner.getPath(), name));
    }

    @Override
    public void clearFolders(SerializableFolderFS<T> owner) {
        for(String name : ImmutableSet.copyOf(owner.getFoldersRaw().keySet())) {
            recursiveRemoveFolder(owner, name);
        }
    }

    @Override
    public void renameFolder(SerializableFolderFS<T> folder, String newName) {
        moveFolder(folder, folder, newName);
    }

    @Override
    public void moveFolder(SerializableFolderFS<T> folder, SerializableFolderFS<T> newOwner, String newName) {
        recursiveMoveFolder(folder, newOwner, newName);
        removeFolder(folder.getParentFolder(), folder.getName());
    }

    protected void recursiveMoveFolder(SerializableFolderFS<T> folder, SerializableFolderFS<T> newOwner, String newName) {
        final SerializableFolderFS<T> newFolder = addFolder(newOwner, newName);
        for(String itemName : folder.getItemsRaw().keySet()) {
            addItem(newFolder, itemName, folder.getItem(itemName));
        }
        for(String folderName : folder.getFoldersRaw().keySet()) {
            recursiveMoveFolder(folder.getFolder(folderName), newFolder, folderName);
        }
    }

    @Override
    public void save(String path) {}

    @Override
    public void saveAll() {}
}
