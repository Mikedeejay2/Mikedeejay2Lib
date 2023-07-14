package com.mikedeejay2.mikedeejay2lib.data.filesystem;

import org.apache.commons.lang3.Validate;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

import java.util.*;

public final class FolderPool<T extends ConfigurationSerializable> {
    private final Map<String, FolderInfo<T>> folderPool;
    private final LinkedList<String> lastRetrieved;
    private final SerializableFileSystem<T> system;
    private int maxSize;

    public FolderPool(SerializableFileSystem<T> system, int maxSize) {
        this.system = system;
        this.folderPool = new HashMap<>();
        this.lastRetrieved = new LinkedList<>();
        this.maxSize = maxSize;
    }

    public FolderInfo<T> get(String path) {
        if(!folderPool.containsKey(path)) return null;
        lastRetrieved.add(path);
        return folderPool.get(path);
    }

    public void put(String path, FolderInfo<T> info) {
        Validate.isTrue(get(path) == null,
                        "A folder with the path \"%s\" already exists", path);
        lastRetrieved.add(path);
        folderPool.put(path, info);
    }

    private void checkAndTrim() {
        if(folderPool.size() <= maxSize) return;

    }

    public int getMaxSize() {
        return maxSize;
    }

    public void setMaxSize(int maxSize) {
        this.maxSize = maxSize;
    }
}
