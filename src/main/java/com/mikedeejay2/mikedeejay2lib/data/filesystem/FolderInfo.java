package com.mikedeejay2.mikedeejay2lib.data.filesystem;

import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

/**
 * Information about a {@link SerializableFolderFS} contained in a {@link SerializableFileSystem}. This info object is
 * used in a {@link FolderPool} for dynamically managing folders in memory.
 *
 * @see FolderPool
 * @see SerializableFileSystem
 * @param <T> The type of {@link ConfigurationSerializable} being stored
 * @author Mikedeejay2
 */
public final class FolderInfo<T extends ConfigurationSerializable> {
    /**
     * The map of path to {@link SerializableFolderFS} pairs. Null if not loaded.
     */
    private @Nullable Map<String, SerializableFolderFS<T>> folders;

    /**
     * The {@link SerializableFolderFS} owner of this information
     */
    private final SerializableFolderFS<T> owner;

    /**
     * Construct a new <code>FolderInfo</code>
     *
     * @param folders The map of path to {@link SerializableFolderFS} pairs. Null if not loaded.
     * @param owner The {@link SerializableFolderFS} owner of this information
     */
    public FolderInfo(@Nullable Map<String, SerializableFolderFS<T>> folders, SerializableFolderFS<T> owner) {
        this.folders = folders;
        this.owner = owner;
    }

    /**
     * Get the map of path to {@link SerializableFolderFS} pairs. Null if not loaded.
     *
     * @return The map of folders
     */
    public @Nullable Map<String, SerializableFolderFS<T>> getFolders() {
        return folders;
    }

    /**
     * Set the map of path to {@link SerializableFolderFS} pairs. Null if not loaded.
     *
     * @param folders The new map of folders. Null to invalidate existing folders
     */
    public void setFolders(Map<String, SerializableFolderFS<T>> folders) {
        if(this.folders != null) {
            for(SerializableFolderFS<T> folder : this.folders.values()) {
                if(folders != null && folders.containsKey(folder.getPath())) continue;
                owner.getFileSystem().getFolderPool().remove(folder.getPath());
            }
        }
        this.folders = folders;
    }

    /**
     * Get the {@link SerializableFolderFS} owner of this information
     *
     * @return The owning folder
     */
    public SerializableFolderFS<T> getOwner() {
        return owner;
    }
}