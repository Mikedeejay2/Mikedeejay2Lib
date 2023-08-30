package com.mikedeejay2.mikedeejay2lib.data.filesystem.modifier;

import com.mikedeejay2.mikedeejay2lib.data.filesystem.SerializableFolderFS;
import com.mikedeejay2.mikedeejay2lib.data.filesystem.SerializableFileSystem;
import com.mikedeejay2.mikedeejay2lib.data.filesystem.saveload.FileSystemSaveLoad;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ManualFileSystemModifier<T extends ConfigurationSerializable> implements FileSystemModifier<T> {
    protected final SerializableFileSystem<T> system;
    protected final Map<SerializableFolderFS<T>, ChangedItems<T>> changedItems = new LinkedHashMap<>();

    public ManualFileSystemModifier(SerializableFileSystem<T> system) {
        this.system = system;
    }

    @Override
    public void addItem(SerializableFolderFS<T> owner, String name, T item) {
        getChangedItems(owner).addedItems.put(name, item);
        getChangedItems(owner).removedItems.add(name);
        owner.getItemsRaw().put(name, item);
    }

    @Override
    public void removeItem(SerializableFolderFS<T> owner, String name) {
        getChangedItems(owner).removedItems.add(name);
        getChangedItems(owner).addedItems.remove(name);
        owner.getItemsRaw().remove(name);
    }

    @Override
    public void clearItems(SerializableFolderFS<T> owner) {
        getChangedItems(owner).addedItems.clear();
        getChangedItems(owner).removedItems.addAll(owner.getItemsRaw().keySet());
        owner.getItemsRaw().clear();
    }

    @Override
    public void addFolder(SerializableFolderFS<T> owner, String name, SerializableFolderFS<T> folder) {
        getChangedItems(owner).addedFolders.put(name, folder);
        getChangedItems(owner).removedFolders.add(name);
        owner.getFoldersRaw().put(name, folder);
    }

    @Override
    public void removeFolder(SerializableFolderFS<T> owner, String name) {
        getChangedItems(owner).removedFolders.add(name);
        getChangedItems(owner).addedFolders.remove(name);
        SerializableFolderFS<T> removed = owner.getFoldersRaw().remove(name);
        recursiveRemove(removed);
    }

    private void recursiveRemove(SerializableFolderFS<T> toRemove) {
        changedItems.remove(toRemove);
        for(SerializableFolderFS<T> folder : toRemove.getFoldersRaw().values()) {
            recursiveRemove(folder);
        }
    }

    @Override
    public void clearFolders(SerializableFolderFS<T> owner) {
        getChangedItems(owner).addedFolders.clear();
        getChangedItems(owner).removedFolders.addAll(owner.getFoldersRaw().keySet());
        owner.getFoldersRaw().clear();
    }

    @Override
    public void save(SerializableFolderFS<T> owner) {
        final ChangedItems<T> changed = getChangedItems(owner);
        final FileSystemSaveLoad<T> saveLoad = system.getSaveLoad();
        saveLoad.startCommit();
        for(String name : changed.addedFolders.keySet()) {
            saveLoad.saveFolder(changed.addedFolders.get(name));
        }
        for(String name : changed.addedItems.keySet()) {
            saveLoad.saveItem(owner, name, changed.addedItems.get(name));
        }
        for(String name : changed.removedFolders) {
            saveLoad.deleteFolder(SerializableFileSystem.getPath(owner.getPath(), name));
        }
        for(String name : changed.removedItems) {
            saveLoad.deleteItem(owner.getPath(), name);
        }
        saveLoad.commit();
        changedItems.remove(owner);
    }

    @Override
    public void saveAll() {
        for(SerializableFolderFS<T> folder : changedItems.keySet()) {
            save(folder);
        }
    }

    private ChangedItems<T> getChangedItems(SerializableFolderFS<T> owner) {
        if(!changedItems.containsKey(owner)) {
            ChangedItems<T> newChangedItems = new ChangedItems<>();
            changedItems.put(owner, newChangedItems);
            return newChangedItems;
        }
        return changedItems.get(owner);
    }

    private static final class ChangedItems<T extends ConfigurationSerializable> {
        private final Map<String, SerializableFolderFS<T>> addedFolders = new LinkedHashMap<>();
        private final Map<String, T> addedItems = new LinkedHashMap<>();
        private final List<String> removedFolders = new ArrayList<>();
        private final List<String> removedItems = new ArrayList<>();
    }
}
