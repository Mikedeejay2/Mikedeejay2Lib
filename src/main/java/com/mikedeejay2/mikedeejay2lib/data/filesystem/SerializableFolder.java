package com.mikedeejay2.mikedeejay2lib.data.filesystem;

import org.bukkit.configuration.serialization.ConfigurationSerializable;

import java.util.List;

/**
 * A folder interface for storing {@link ConfigurationSerializable} data types.
 *
 * @see SerializableFileSystem
 * @param <T> The type of {@link ConfigurationSerializable} being stored
 * @author Mikedeejay2
 */
public interface SerializableFolder<T extends ConfigurationSerializable> {
    /**
     * Get the name of the folder
     *
     * @return The folder name
     */
    String getName();

    /**
     * Get all folders contained in this folder
     *
     * @return The list of folders
     */
    List<? extends SerializableFolder<T>> getFolders();

    /**
     * Get all objects contained in this folder
     *
     * @return The list of objects
     */
    List<T> getItems();
}
