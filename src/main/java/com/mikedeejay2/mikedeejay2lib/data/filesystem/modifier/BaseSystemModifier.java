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
    public void addObject(SerializableFolderFS<T> owner, String name, T item) {
        owner.getObjectsRaw().put(name, item);
    }

    @Override
    public void removeObject(SerializableFolderFS<T> owner, String name) {
        owner.getObjectsRaw().remove(name);
    }

    @Override
    public void clearObjects(SerializableFolderFS<T> owner) {
        for(String name : ImmutableSet.copyOf(owner.getObjectsRaw().keySet())) {
            removeObject(owner, name);
        }
    }

    @Override
    public void renameObject(SerializableFolderFS<T> owner, String name, String newName) {
        moveObject(owner, name, owner, newName);
    }

    @Override
    public void moveObject(SerializableFolderFS<T> owner, String name, SerializableFolderFS<T> newOwner, String newName) {
        addObject(newOwner, newName, owner.getObject(name));
        removeObject(owner, name);
    }

    @Override
    public SerializableFolderFS<T> addFolder(SerializableFolderFS<T> owner, String name) {
        final SerializableFolderFS<T> folder = new SerializableFolderFS<>(name, owner.getPath(), system);
        owner.getFoldersRaw().put(name, folder);
        return folder;
    }

    @Override
    public void removeFolder(SerializableFolderFS<T> folder) {
        recursiveRemoveFolder(folder);
        system.getFolderPool().get(folder.getParentFolder().getPath()).setFolders(null);
    }

    protected void recursiveRemoveFolder(SerializableFolderFS<T> folder) {
        for(String curName : ImmutableSet.copyOf(folder.getFoldersRaw().keySet())) {
            recursiveRemoveFolder(folder.getFolder(curName));
        }
        removeSingleFolder(folder);
    }

    protected void removeSingleFolder(SerializableFolderFS<T> folder) {
        folder.getParentFolder().getFoldersRaw().remove(folder.getName());
        system.getFolderPool().remove(folder.getPath());
    }

    @Override
    public void clearFolders(SerializableFolderFS<T> owner) {
        for(String name : ImmutableSet.copyOf(owner.getFoldersRaw().keySet())) {
            recursiveRemoveFolder(owner.getFolder(name));
        }
    }

    @Override
    public void renameFolder(SerializableFolderFS<T> folder, String newName) {
        moveFolder(folder, folder, newName);
    }

    @Override
    public void moveFolder(SerializableFolderFS<T> folder, SerializableFolderFS<T> newOwner, String newName) {
        recursiveMoveFolder(folder, newOwner, newName);
        removeFolder(folder);
    }

    protected void recursiveMoveFolder(SerializableFolderFS<T> folder, SerializableFolderFS<T> newOwner, String newName) {
        final SerializableFolderFS<T> newFolder = addFolder(newOwner, newName);
        for(String itemName : folder.getObjectsRaw().keySet()) {
            addObject(newFolder, itemName, folder.getObject(itemName));
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
