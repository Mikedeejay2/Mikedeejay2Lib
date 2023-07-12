package com.mikedeejay2.mikedeejay2lib.data.filesystem;

import org.bukkit.configuration.serialization.ConfigurationSerializable;

import java.util.Map;

public final class FolderInfo<T extends ConfigurationSerializable> {
    public final Map<String, SerializableFolderFS<T>> folders;
    public final SerializableFolderFS<T> owner;

    public FolderInfo(Map<String, SerializableFolderFS<T>> folders, SerializableFolderFS<T> owner) {
        this.folders = folders;
        this.owner = owner;
    }
}