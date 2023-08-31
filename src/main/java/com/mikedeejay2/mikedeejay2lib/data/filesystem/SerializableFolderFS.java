package com.mikedeejay2.mikedeejay2lib.data.filesystem;


import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import org.apache.commons.lang3.Validate;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;

/**
 * A {@link SerializableFolder} implementation used in {@link SerializableFileSystem}
 *
 * @param <T> The type of {@link ConfigurationSerializable} being stored
 * @author Mikedeejay2
 */
public class SerializableFolderFS<T extends ConfigurationSerializable> implements SerializableFolder<T> {
    /**
     * The parent {@link SerializableFileSystem}
     */
    protected final @NotNull SerializableFileSystem<T> fileSystem;

    /**
     * The name of this folder
     */
    protected @NotNull String name;

    /**
     * The path (including name) of this folder
     */
    protected String path;

    /**
     * The items contained in this folder
     */
    protected @Nullable Map<String, T> items;

    /**
     * Whether this folder has been saved to disk
     */
    protected boolean saved;

    /**
     * Construct a new {@link SerializableFolderFS}
     *
     * @param name       The name of this folder
     * @param path       The path to this folder
     * @param fileSystem The parent {@link SerializableFileSystem}
     * @param root       Whether this folder is the root folder. Will nullify path.
     */
    public SerializableFolderFS(@NotNull String name, String path, @NotNull SerializableFileSystem<T> fileSystem, boolean root) {
        Validate.isTrue(SerializableFileSystem.checkValidFilename(name), "File name is invalid, \"%s\"", name);
        this.fileSystem = fileSystem;
        this.items = null;
        this.name = name;
        this.path = path == null ? name : path.replace('\\', '/') + "/" + name;
        if(root) this.path = null;
        this.saved = false;
        fileSystem.getFolderPool().put(this.getPath(), new FolderInfo<>(null, this));
    }

    /**
     * Construct a new {@link SerializableFolderFS}
     *
     * @param name       The name of this folder
     * @param path       The path to this folder
     * @param fileSystem The parent {@link SerializableFileSystem}
     */
    public SerializableFolderFS(@NotNull String name, String path, @NotNull SerializableFileSystem<T> fileSystem) {
        this(name, path, fileSystem, false);
    }

    /**
     * Get the parent {@link SerializableFileSystem}
     *
     * @return The parent {@link SerializableFileSystem}
     */
    public @NotNull SerializableFileSystem<T> getFileSystem() {
        return fileSystem;
    }

    /**
     * Get the name of this folder
     *
     * @return The name of this folder
     */
    @Override
    public @NotNull String getName() {
        return name;
    }

    /**
     * Get the path (including name) of this folder
     *
     * @return The path of this folder
     */
    public @Nullable String getPath() {
        return path;
    }

    /**
     * Get the full path of this folder. This includes the file system's save path, the path to the folder, and the
     * folder's name.
     *
     * @return The full path of this folder
     */
    public @NotNull String getFullPath() {
        return path != null ? fileSystem.getSavePath() + "/" + path : fileSystem.getSavePath();
    }

    /**
     * Add an object to this folder
     *
     * @param name The name of the object
     * @param obj The object to add
     */
    public void addItem(String name, T obj) {
        fileSystem.getModifier().addItem(this, name, obj);
    }

    /**
     * Remove an object from this folder
     *
     * @param name The name of the object to remove
     */
    public void removeItem(String name) {
        fileSystem.getModifier().removeItem(this, name);
    }

    /**
     * Clear all objects from this folder
     */
    public void clearItems() {
        fileSystem.getModifier().clearItems(this);
    }

    /**
     * Add a new folder to this folder
     *
     * @param name The name of the folder to add
     */
    public void addFolder(String name) {
        fileSystem.getModifier().addFolder(this, name, new SerializableFolderFS<>(name, path, fileSystem));
    }

    /**
     * Remove a folder from this folder
     *
     * @param name The name of the folder to remove
     */
    public void removeFolder(String name) {
        fileSystem.getModifier().removeFolder(this, name);
    }

    /**
     * Clear all folders from this folder
     */
    public void clearFolders() {
        fileSystem.getModifier().clearFolders(this);
    }

    /**
     * Save this folder to disk. Redundant if auto write is on.
     */
    public void save() {
        fileSystem.getModifier().save(this);
    }

    /**
     * Get an object by name from this folder
     *
     * @param name The file name of the object
     * @return The retrieved object, null if not found
     */
    public T getItem(String name) {
        return getItemsRaw().get(name);
    }

    /**
     * Whether this folder contains a folder by a specified name
     *
     * @param name The name of the folder to find
     * @return Whether a folder was found
     */
    public boolean containsFolder(String name) {
        return getFoldersRaw().containsKey(name);
    }

    /**
     * Whether this folder contains an object by a specified name
     *
     * @param name The file name of the object to find
     * @return Whether an object was found
     */
    public boolean containsItem(String name) {
        return getItemsRaw().containsKey(name);
    }

    /**
     *Get the file name of an object based off of an equal object
     *
     * @param obj The object to find the name of
     * @return The retrieved file name, null if not found
     */
    public String getItemName(T obj) {
        for(Map.Entry<String, T> entry : getItemsRaw().entrySet()) {
            if(obj.equals(entry.getValue())) return entry.getKey();
        }
        return null;
    }

    /**
     * Get a folder from this folder by name
     *
     * @param name The name of the folder to get
     * @return The retrieved folder, null if not found
     */
    public SerializableFolderFS<T> getFolder(String name) {
        return getFoldersRaw().get(name);
    }

    /**
     * Get the list of folders contained in this folder
     *
     * @return The list of folders
     */
    @Override
    public List<SerializableFolderFS<T>> getFolders() {
        return ImmutableList.copyOf(getFoldersRaw().values());
    }

    /**
     * Get folders contained in this folder as a map, where the key is the path to the folder
     *
     * @return The map of path to folder
     */
    public Map<String, SerializableFolderFS<T>> getFoldersMap() {
        return ImmutableMap.copyOf(getFoldersRaw());
    }

    /**
     * Get the raw map of folder key values. This should <strong>only be used internally</strong>.
     *
     * @return The raw map of folders
     */
    public Map<String, SerializableFolderFS<T>> getFoldersRaw() {
        FolderInfo<T> folders = fileSystem.getFolderPool().get(this.getPath());
        if(folders == null || folders.getFolders() == null) {
            return loadFolders();
        }
        return folders.getFolders();
    }

    /**
     * Attempt to load folders contained within this folder from disk
     *
     * @return The loaded map of folders
     */
    private Map<String, SerializableFolderFS<T>> loadFolders() {
        Map<String, SerializableFolderFS<T>> folders = fileSystem.getSaveLoad().loadFolders(this);
        fileSystem.getFolderPool().get(path).setFolders(folders);
        return folders;
    }

    /**
     * Get the list of objects contained in this folder
     *
     * @return The list of objects
     */
    @Override
    public List<T> getItems() {
        return ImmutableList.copyOf(getItemsRaw().values());
    }

    /**
     * Get all objects contained in this folder as a map, where the key is the file name of the object
     *
     * @return The map of file name to object
     */
    public Map<String, T> getItemsMap() {
        return ImmutableMap.copyOf(getItemsRaw());
    }

    /**
     * Get the raw items map of this folder. This should <strong>only be used internally</strong>.
     *
     * @return The raw items map
     */
    public Map<String, T> getItemsRaw() {
        if(items == null) {
            items = fileSystem.getSaveLoad().loadItems(this);
        }
        return items;
    }

    /**
     * Get whether this folder has been saved to disk
     *
     * @return Whether this folder has been saved
     */
    public boolean isSaved() {
        return saved;
    }
}
