package com.mikedeejay2.mikedeejay2lib.data.filesystem.modifier;

import com.mikedeejay2.mikedeejay2lib.data.filesystem.SerializableFolderFS;
import com.mikedeejay2.mikedeejay2lib.data.filesystem.SerializableFileSystem;
import com.mikedeejay2.mikedeejay2lib.data.filesystem.saveload.FileSystemSaveLoad;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ManualFileSystemModifier<T extends ConfigurationSerializable> extends BaseSystemModifier<T> {
    protected final Map<String, ChangedItems<T>> changedItems = new LinkedHashMap<>();

    public ManualFileSystemModifier(SerializableFileSystem<T> system) {
        super(system);
    }

    @Override
    public void addItem(SerializableFolderFS<T> owner, String name, T item) {
        super.addItem(owner, name, item);
        getChangedItems(owner.getPath()).addedItems.put(name, item);
        getChangedItems(owner.getPath()).removedItems.add(name);
    }

    @Override
    public void removeItem(SerializableFolderFS<T> owner, String name) {
        super.removeItem(owner, name);
        getChangedItems(owner.getPath()).removedItems.add(name);
        getChangedItems(owner.getPath()).addedItems.remove(name);
    }

    @Override
    public void clearItems(SerializableFolderFS<T> owner) {
        super.clearItems(owner);
        getChangedItems(owner.getPath()).addedItems.clear();
        getChangedItems(owner.getPath()).removedItems.addAll(owner.getItemsRaw().keySet());
    }

    @Override
    public SerializableFolderFS<T> addFolder(SerializableFolderFS<T> owner, String name) {
        final SerializableFolderFS<T> folder = super.addFolder(owner, name);
        getChangedItems(owner.getPath()).addedFolders.add(name);
        getChangedItems(owner.getPath()).removedFolders.remove(name);
        return folder;
    }

    @Override
    public void removeFolder(SerializableFolderFS<T> owner, String name) {
        super.removeFolder(owner, name);
    }

    @Override
    public void clearFolders(SerializableFolderFS<T> owner) {
        super.clearFolders(owner);
    }

    @Override
    protected void removeSingleFolder(SerializableFolderFS<T> owner, String name) {
        super.removeSingleFolder(owner, name);
        getChangedItems(owner.getPath()).removedFolders.add(name);
        getChangedItems(owner.getPath()).addedFolders.remove(name);
    }

    @Override
    public void save(String path) {
        final ChangedItems<T> changed = getChangedItems(path);
        final FileSystemSaveLoad<T> saveLoad = system.getSaveLoad();
        saveLoad.startCommit();
        for(String name : changed.addedFolders) {
            saveLoad.saveFolder(SerializableFileSystem.getPath(path, name));
        }
        for(String name : changed.addedItems.keySet()) {
            saveLoad.saveItem(path, name, changed.addedItems.get(name));
        }
        for(String name : changed.removedFolders) {
            saveLoad.deleteFolder(SerializableFileSystem.getPath(path, name));
        }
        for(String name : changed.removedItems) {
            saveLoad.deleteItem(path, name);
        }
        saveLoad.commit();
        changedItems.remove(path);
    }

    @Override
    public void saveAll() {
        for(String path : changedItems.keySet()) {
            save(path);
        }
    }

    private ChangedItems<T> getChangedItems(String path) {
        if(!changedItems.containsKey(path)) {
            ChangedItems<T> newChangedItems = new ChangedItems<>();
            changedItems.put(path, newChangedItems);
            return newChangedItems;
        }
        return changedItems.get(path);
    }

    private static final class ChangedItems<T extends ConfigurationSerializable> {
        private final List<String> addedFolders = new ArrayList<>();
        private final Map<String, T> addedItems = new LinkedHashMap<>();
        private final List<String> removedFolders = new ArrayList<>();
        private final List<String> removedItems = new ArrayList<>();
    }
}
