package com.mikedeejay2.mikedeejay2lib.data.filesystem;

import org.apache.commons.lang3.Validate;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

import java.util.*;

/**
 * A pool of folder information for dynamically storing {@link SerializableFolderFS} objects and related information in
 * memory as {@link FolderInfo} objects. This class has the ability to track last accessed folders, trim old folders,
 * and manage the maximum amount of loaded folders in memory.
 * <p>
 * The maximum amount of folders that are stored in memory by default is 10. That means, no more than 10 folders can be
 * loaded in memory at once. To change this amount, use the {@link FolderPool#setMaxSize(int)} method.
 *
 * @see SerializableFolderFS
 * @see FolderInfo
 * @see SerializableFileSystem
 * @param <T> The type of {@link ConfigurationSerializable} being stored
 * @author Mikedeejay2
 */
public final class FolderPool<T extends ConfigurationSerializable> {
    /**
     * The map of paths to {@link FolderInfo}
     */
    private final Map<String, FolderInfo<T>> folderPool;

    /**
     * A queue of the last retrieved folders (used during trimming operations)
     */
    private final Queue<String> lastRetrieved;

    /**
     * The maximum size of the folder pool. If above this size, the pool will be trimmed, and data will be unloaded.
     */
    private int maxSize;

    /**
     * Construct a new <code>FolderPool</code>
     *
     * @param maxSize The maximum size of the folder pool. If above this size, the pool will be trimmed, and data will
     *                be unloaded.
     */
    public FolderPool(int maxSize) {
        this.folderPool = new HashMap<>();
        this.lastRetrieved = new LinkedList<>();
        this.maxSize = maxSize;
    }

    /**
     * Get a {@link FolderInfo} from the pool
     *
     * @param path The path to retrieve
     * @return The retrieved {@link FolderInfo}, null if not found
     */
    public FolderInfo<T> get(String path) {
        if(!folderPool.containsKey(path)) return null;
        access(path);
        return folderPool.get(path);
    }

    /**
     * Put a {@link FolderInfo} into the pool
     *
     * @param path The path to the info
     * @param info The folder info
     * @throws IllegalArgumentException If the folder pool already has information for that path
     */
    public void put(String path, FolderInfo<T> info) {
        Validate.isTrue(get(path) == null,
                        "A folder with the path \"%s\" already exists", path);
        access(path);
        folderPool.put(path, info);
    }

    /**
     * Remove information about a path from the folder pool
     *
     * @param path The path to remove
     */
    public void remove(String path) {
        lastRetrieved.remove(path);
        folderPool.remove(path);
        String parentPath = SerializableFileSystem.getParentPath(path);
        if(folderPool.containsKey(parentPath)) {
            folderPool.get(parentPath).setFolders(null); // Invalidate parent folder
        }
    }

    /**
     * Get whether the folder pool contains a path to folder info
     *
     * @param path The path to find
     * @return Whether information about the path exists
     */
    public boolean contains(String path) {
        return folderPool.containsKey(path);
    }

    /**
     * Trim the oldest information from the folder pool
     */
    private void checkAndTrim() {
        if(folderPool.size() <= maxSize) return;
        while(folderPool.size() - maxSize > 0) {
            remove(lastRetrieved.peek());
        }
    }

    /**
     * Called upon access to the folder pool. Updates {@link FolderPool#lastRetrieved} and trims if necessary.
     *
     * @param path The path being accessed
     */
    private void access(String path) {
        if(path == null) return;
        while(path.contains("/")) {
            lastRetrieved.remove(path);
            lastRetrieved.offer(path);
            path = path.substring(0, path.lastIndexOf('/'));
        }
        lastRetrieved.remove(path);
        lastRetrieved.offer(path);
        checkAndTrim();
    }

    /**
     * Get the maximum size of the folder pool. If above this size, the pool will be trimmed, and data will be unloaded.
     *
     * @return The maximum size of the folder pool
     */
    public int getMaxSize() {
        return maxSize;
    }

    /**
     * Set the maximum size of the folder pool. If above this size, the pool will be trimmed, and data will be unloaded.
     *
     * @param maxSize The new maximum pool size
     */
    public void setMaxSize(int maxSize) {
        this.maxSize = maxSize;
    }
}
