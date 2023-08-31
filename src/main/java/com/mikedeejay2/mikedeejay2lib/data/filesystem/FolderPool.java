package com.mikedeejay2.mikedeejay2lib.data.filesystem;

import org.apache.commons.lang3.Validate;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

import java.util.*;

public final class FolderPool<T extends ConfigurationSerializable> {
    private final Map<String, FolderInfo<T>> folderPool;
    private final Queue<String> lastRetrieved;
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
        access(path);
        return folderPool.get(path);
    }

    public void put(String path, FolderInfo<T> info) {
        Validate.isTrue(get(path) == null,
                        "A folder with the path \"%s\" already exists", path);
        access(path);
        folderPool.put(path, info);
    }

    public void remove(String path) {
        lastRetrieved.remove(path);
        FolderInfo<T> info = folderPool.remove(path);
        String parentPath = path.substring(0, path.lastIndexOf('/'));
        if(folderPool.containsKey(parentPath)) {
            folderPool.get(parentPath).setFolders(null); // Invalidate parent folder
        }
        recurRemove(info);
    }

    public boolean contains(String path) {
        return folderPool.containsKey(path);
    }

    private void checkAndTrim() {
        if(folderPool.size() <= maxSize) return;
        while(folderPool.size() - maxSize > 0) {
            remove(lastRetrieved.peek());
        }
    }

    private void recurRemove(FolderInfo<T> info) {
        for(String path : info.getFolders().keySet()) {
            FolderInfo<T> cur = folderPool.remove(path);
            if(cur != null) recurRemove(cur);
        }
    }

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

    public int getMaxSize() {
        return maxSize;
    }

    public void setMaxSize(int maxSize) {
        this.maxSize = maxSize;
    }
}
