package com.mikedeejay2.mikedeejay2lib.data.filesystem;


import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import org.apache.commons.lang3.Validate;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;

public class SerializableFolderFS<T extends ConfigurationSerializable> implements SerializableFolder<T> {
    protected final @NotNull SerializableFileSystem<T> fileSystem;
    protected @NotNull String name;
    protected @NotNull String path;
    protected Map<String, T> items = null;

    public SerializableFolderFS(@NotNull String name, String path, @NotNull SerializableFileSystem<T> fileSystem) {
        Validate.isTrue(SerializableFileSystem.checkValidFilename(name), "File name is invalid, \"%s\"", name);
        this.fileSystem = fileSystem;
        this.name = name;
        this.path = path == null ? name : path.replace('\\', '/') + "/" + name;
        Validate.isTrue(fileSystem.folderPool.getIfPresent(this.getPath()) == null,
                        "A folder with the path \"%s\" already exists", this.getPath());
        fileSystem.folderPool.put(this.getPath(), new FolderInfo<>(null, this));
    }

    public @NotNull SerializableFileSystem<T> getFileSystem() {
        return fileSystem;
    }

    @Override
    public @NotNull String getName() {
        return name;
    }

    public @NotNull String getPath() {
        return path;
    }

    public @NotNull String getFullPath() {
        return fileSystem.getSavePath() + "/" + path;
    }

    public void addItem(String name, T item) {
        fileSystem.modifier.addItem(this, name, item);
    }

    public void removeItem(String name) {
        fileSystem.modifier.removeItem(this, name);
    }

    public void clearItems() {
        fileSystem.modifier.clearItems(this);
    }

    public void addFolder(String name) {
        fileSystem.modifier.addFolder(this, name, new SerializableFolderFS<>(name, path, fileSystem));
    }

    public void removeFolder(String name) {
        fileSystem.modifier.removeFolder(this, name);
    }

    public void clearFolders() {
        fileSystem.modifier.clearFolders(this);
    }

    public void save() {
        fileSystem.modifier.save(this);
    }

    public T getItem(String name) {
        return getItemsRaw().get(name);
    }

    public String getItemName(T item) {
        for(Map.Entry<String, T> entry : getItemsRaw().entrySet()) {
            if(item.equals(entry.getValue())) return entry.getKey();
        }
        return null;
    }

    public SerializableFolderFS<T> getFolder(String name) {
        return getFoldersRaw().get(name);
    }

    @Override
    public List<SerializableFolderFS<T>> getFolders() {
        return ImmutableList.copyOf(getFoldersRaw().values());
    }

    public Map<String, SerializableFolderFS<T>> getFoldersMap() {
        return ImmutableMap.copyOf(getFoldersRaw());
    }

    public Map<String, SerializableFolderFS<T>> getFoldersRaw() {
        FolderInfo<T> folders = fileSystem.folderPool.getIfPresent(this.getPath());
        if(folders == null || folders.folders == null) {
            return loadFolders();
        }
        return folders.folders;
    }

    private Map<String, SerializableFolderFS<T>> loadFolders() {
        Map<String, SerializableFolderFS<T>> folders = fileSystem.saveLoad.loadFolders(this);
        fileSystem.folderPool.put(this.getPath(), new FolderInfo<>(folders, this));
        return folders;
    }

    @Override
    public List<T> getItems() {
        return ImmutableList.copyOf(getItemsRaw().values());
    }

    public Map<String, T> getItemsMap() {
        return ImmutableMap.copyOf(getItemsRaw());
    }

    public Map<String, T> getItemsRaw() {
        if(items == null) {
            items = fileSystem.saveLoad.loadItems(this);
        }
        return items;
    }
}
