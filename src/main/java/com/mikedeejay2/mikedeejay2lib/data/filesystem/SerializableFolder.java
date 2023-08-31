package com.mikedeejay2.mikedeejay2lib.data.filesystem;

import org.bukkit.configuration.serialization.ConfigurationSerializable;

import java.util.List;

public interface SerializableFolder<T extends ConfigurationSerializable> {
    String getName();
    List<? extends SerializableFolder<T>> getFolders();
    List<T> getItems();
    SerializableFolder<T> getFolder(String name);
    T getItem(String name);
    boolean containsFolder(String name);
}
