package com.mikedeejay2.mikedeejay2lib.data.filesystem;

import org.bukkit.configuration.serialization.ConfigurationSerializable;

import java.util.Map;

public final class FolderInfo<T extends ConfigurationSerializable> {
    private Map<String, SerializableFolderFS<T>> folders;
    private final SerializableFolderFS<T> owner;

    public FolderInfo(Map<String, SerializableFolderFS<T>> folders, SerializableFolderFS<T> owner) {
        this.folders = folders;
        this.owner = owner;
    }

    public Map<String, SerializableFolderFS<T>> getFolders() {
        return folders;
    }

    public void setFolders(Map<String, SerializableFolderFS<T>> folders) {
        if(this.folders != null) {
            for(SerializableFolderFS<T> folder : this.folders.values()) {
                if(folders != null && folders.containsKey(folder.getPath())) continue;
                owner.getFileSystem().getFolderPool().remove(folder.getPath());
            }
        }
        this.folders = folders;
    }

    public SerializableFolderFS<T> getOwner() {
        return owner;
    }
}