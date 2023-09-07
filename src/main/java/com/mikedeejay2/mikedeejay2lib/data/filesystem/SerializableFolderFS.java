package com.mikedeejay2.mikedeejay2lib.data.filesystem;


import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import org.apache.commons.lang3.Validate;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;

import static com.mikedeejay2.mikedeejay2lib.data.filesystem.SerializableFileSystem.getSafeName;

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
     * The objects contained in this folder
     */
    protected @Nullable Map<String, T> objects;

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
        this.objects = null;
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
        this(getSafeName(name), path, fileSystem, false);
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
    public void addObject(String name, T obj) {
        fileSystem.getModifier().addObject(this, getSafeName(name), obj);
    }

    /**
     * Remove an object from this folder
     *
     * @param name The name of the object to remove
     */
    public void removeObject(String name) {
        fileSystem.getModifier().removeObject(this, getSafeName(name));
    }

    /**
     * Clear all objects from this folder
     */
    public void clearObjects() {
        fileSystem.getModifier().clearObjects(this);
    }

    /**
     * Rename an object in this folder
     *
     * @param name    The name of the object to rename
     * @param newName The new name of the object
     */
    public void renameObject(String name, String newName) {
        fileSystem.getModifier().renameObject(
            this, getSafeName(name), getSafeName(newName));
    }

    /**
     * Move an object in this folder
     *
     * @param destination The folder that the object will be moved to
     * @param name        The name of the object that will be moved
     * @param newName     The new name of the object
     */
    public void moveObject(SerializableFolderFS<T> destination, String name, String newName) {
        fileSystem.getModifier().moveObject(
            this, getSafeName(name),
            destination, getSafeName(newName));
    }

    /**
     * Add a new folder to this folder
     *
     * @param name The name of the folder to add
     * @return The created folder
     */
    public SerializableFolderFS<T> addFolder(String name) {
        return fileSystem.getModifier().addFolder(this, getSafeName(name));
    }

    /**
     * Remove a folder from this folder
     *
     * @param name The name of the folder to remove
     */
    public void removeFolder(String name) {
        fileSystem.getModifier().removeFolder(getFolder(name));
    }

    /**
     * Clear all folders from this folder
     */
    public void clearFolders() {
        fileSystem.getModifier().clearFolders(this);
    }

    /**
     * Get the parent folder of this folder
     *
     * @return The parent folder, null if current folder is the root folder
     */
    public SerializableFolderFS<T> getParentFolder() {
        if(path == null) return null;
        if(!path.contains("/")) return fileSystem.getRootFolder();
        return fileSystem.getFolder(path.substring(0, path.lastIndexOf('/')));
    }

    /**
     * Save this folder to disk. Redundant if auto write is on.
     */
    public void save() {
        fileSystem.getModifier().save(path);
    }

    /**
     * Get an object by name from this folder
     *
     * @param name The file name of the object
     * @return The retrieved object, null if not found
     */
    public T getObject(String name) {
        return getObjectsRaw().get(getSafeName(name));
    }

    /**
     * Whether this folder contains a folder by a specified name
     *
     * @param name The name of the folder to find
     * @return Whether a folder was found
     */
    public boolean containsFolder(String name) {
        return getFoldersRaw().containsKey(getSafeName(name));
    }

    /**
     * Whether this folder contains an object by a specified name
     *
     * @param name The file name of the object to find
     * @return Whether an object was found
     */
    public boolean containsObject(String name) {
        return getObjectsRaw().containsKey(getSafeName(name));
    }

    /**
     *Get the file name of an object based off of an equal object
     *
     * @param obj The object to find the name of
     * @return The retrieved file name, null if not found
     */
    public String getObjectName(T obj) {
        for(Map.Entry<String, T> entry : getObjectsRaw().entrySet()) {
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
        return getFoldersRaw().get(getSafeName(name));
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
        Map<String, SerializableFolderFS<T>> folders = fileSystem.getSaveLoad().loadFolders(path);
        fileSystem.getFolderPool().get(path).setFolders(folders);
        return folders;
    }

    /**
     * Get the list of objects contained in this folder
     *
     * @return The list of objects
     */
    @Override
    public List<T> getObjects() {
        return ImmutableList.copyOf(getObjectsRaw().values());
    }

    /**
     * Get all objects contained in this folder as a map, where the key is the file name of the object
     *
     * @return The map of file name to object
     */
    public Map<String, T> getObjectsMap() {
        return ImmutableMap.copyOf(getObjectsRaw());
    }

    /**
     * Get the raw objects map of this folder. This should <strong>only be used internally</strong>.
     *
     * @return The raw objects map
     */
    public Map<String, T> getObjectsRaw() {
        if(objects == null) {
            objects = fileSystem.getSaveLoad().loadObjects(path);
        }
        return objects;
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
