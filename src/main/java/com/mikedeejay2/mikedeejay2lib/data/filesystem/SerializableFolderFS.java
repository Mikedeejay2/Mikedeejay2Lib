package com.mikedeejay2.mikedeejay2lib.data.filesystem;


import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import org.apache.commons.lang3.Validate;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;

public class SerializableFolderFS<T extends ConfigurationSerializable> implements SerializableFolder<T> {
    protected final @NotNull SerializableFileSystem<T> fileSystem;
    protected @NotNull String name;
    protected String path;
    protected Map<String, T> items = null;
    protected boolean saved;

    public SerializableFolderFS(@NotNull String name, String path, @NotNull SerializableFileSystem<T> fileSystem, boolean root) {
        Validate.isTrue(SerializableFileSystem.checkValidFilename(name), "File name is invalid, \"%s\"", name);
        this.fileSystem = fileSystem;
        this.name = name;
        this.path = path == null ? name : path.replace('\\', '/') + "/" + name;
        if(root) this.path = null;
        this.saved = false;
        fileSystem.getFolderPool().put(this.getPath(), new FolderInfo<>(null, this));
    }

    public SerializableFolderFS(@NotNull String name, String path, @NotNull SerializableFileSystem<T> fileSystem) {
        this(name, path, fileSystem, false);
    }

    public @NotNull SerializableFileSystem<T> getFileSystem() {
        return fileSystem;
    }

    @Override
    public @NotNull String getName() {
        return name;
    }

    public @Nullable String getPath() {
        return path;
    }

    public @NotNull String getFullPath() {
        return path != null ? fileSystem.getSavePath() + "/" + path : fileSystem.getSavePath();
    }

    public void addItem(String name, T item) {
        fileSystem.getModifier().addItem(this, name, item);
    }

    public void removeItem(String name) {
        fileSystem.getModifier().removeItem(this, name);
    }

    public void clearItems() {
        fileSystem.getModifier().clearItems(this);
    }

    public void addFolder(String name) {
        fileSystem.getModifier().addFolder(this, name, new SerializableFolderFS<>(name, path, fileSystem));
    }

    public void removeFolder(String name) {
        fileSystem.getModifier().removeFolder(this, name);
    }

    public void clearFolders() {
        fileSystem.getModifier().clearFolders(this);
    }

    public void save() {
        fileSystem.getModifier().save(this);
    }

    @Override
    public T getItem(String name) {
        return getItemsRaw().get(name);
    }

    @Override
    public boolean containsFolder(String name) {
        return getFoldersRaw().containsKey(name);
    }

    public boolean containsItem(String name) {
        return getItemsRaw().containsKey(name);
    }

    public String getItemName(T item) {
        for(Map.Entry<String, T> entry : getItemsRaw().entrySet()) {
            if(item.equals(entry.getValue())) return entry.getKey();
        }
        return null;
    }

    @Override
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
        FolderInfo<T> folders = fileSystem.getFolderPool().get(this.getPath());
        if(folders == null || folders.getFolders() == null) {
            return loadFolders();
        }
        return folders.getFolders();
    }

    private Map<String, SerializableFolderFS<T>> loadFolders() {
        Map<String, SerializableFolderFS<T>> folders = fileSystem.getSaveLoad().loadFolders(this);
        fileSystem.getFolderPool().get(path).setFolders(folders);
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
            items = fileSystem.getSaveLoad().loadItems(this);
        }
        return items;
    }

    public boolean isSaved() {
        return saved;
    }

    public void setSaved(boolean saved) {
        this.saved = saved;
    }
}
