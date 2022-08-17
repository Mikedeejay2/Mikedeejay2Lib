package com.mikedeejay2.mikedeejay2lib.data;

import com.mikedeejay2.mikedeejay2lib.BukkitPlugin;
import com.mikedeejay2.mikedeejay2lib.util.file.FileIO;

import java.io.File;

/**
 * A data file is a file of any type that this plugin uses.
 * This class has multiple functions but its main purpose it to
 * act as a base to structure all file types on.
 *
 * @author Mikedeejay2
 */
public abstract class DataFile implements DataObject {
    /**
     * The {@link BukkitPlugin} instance
     */
    protected final BukkitPlugin plugin;
    /**
     * Path from the plugins folder to the file. (This DOES NOT include <code>plugin.getDataFolder()</code>)
     */
    protected String filePath;
    /**
     * The file to to this file on the disk (This does not ensure that the file is on the disk)
     */
    protected File file;
    /**
     * If the file is loaded
     */
    protected boolean isLoaded;

    /**
     * Construct a new <code>DataFile</code>
     *
     * @param plugin The {@link BukkitPlugin} instance
     * @param filePath Path from the plugins folder to the file.
     *                 (This DOES NOT include <code>plugin.getDataFolder()</code>)
     */
    public DataFile(BukkitPlugin plugin, String filePath) {
        this.plugin = plugin;
        this.filePath = filePath;
        this.file = new File(plugin.getDataFolder(), filePath);
        this.isLoaded = false;
    }

    /**
     * Get this file's path (NOT including plugin.getDataFolder())
     *
     * @return This file's filePath
     */
    public String getFilePath() {
        return filePath;
    }

    /**
     * Gets the java.io.File of this file
     *
     * @return This file's java.io.File
     */
    public File getFile() {
        return file;
    }

    /**
     * Return if this file is on disk or not
     *
     * @return If this file is on disk or not
     */
    public boolean fileExists() {
        return file.exists();
    }

    /**
     * Return if the data for this file is loaded and ready to be used or not.
     *
     * @return If this file is loaded
     */
    public boolean isLoaded() {
        return isLoaded;
    }

    /**
     * Resets this file's data to the default file specified in the jar.
     * <p>
     * <strong>DO NOT</strong> use this method if this file does not exist in this plugin's jar.
     * <p>
     * This method does the following in order:
     * <ol>
     *      <li>Delete the file from the disk</li>
     *      <li>Load the new file from jar</li>
     *      <li>Save the new file to disk</li>
     * </ol>
     *
     * @param throwErrors Silence any errors that this operation might produce
     * @return If this operation was successful or not
     */
    public boolean resetFromJar(boolean throwErrors) {
        if(!delete(throwErrors)) return false;
        if(!loadFromJar(throwErrors)) return false;
        if(!saveToDisk(throwErrors)) return false;
        return true;
    }

    /**
     * Resets this file's data to nothing.
     * <p>
     * This method does the following in order:
     * <ol>
     *      <li>Delete the file from the disk</li>
     *      <li>Load from disk which creates a new file</li>
     *      <li>Save the new file to disk</li>
     * </ol>
     *
     * @param throwErrors Silence any errors that this operation might produce
     * @return If this operation was successful or not
     */
    public boolean resetFromNew(boolean throwErrors) {
        if(!delete(throwErrors)) return false;
        if(!loadFromDisk(throwErrors)) return false;
        if(!saveToDisk(throwErrors)) return false;
        return true;
    }

    /**
     * Reload this file. Unless this method is overloaded, this will only load from disk replacing the current
     * information.
     *
     * @param throwErrors Silence any errors that this operation might produce
     * @return If this operation was successful or not
     */
    public boolean reload(boolean throwErrors) {
        return loadFromDisk(throwErrors);
    }

    /**
     * Load this file from disk. The location that would be loaded is the plugin's data folder + the file path of this
     * file
     *
     * @param throwErrors Silence any errors that this operation might produce
     * @return If this operation was successful or not
     */
    public abstract boolean loadFromDisk(boolean throwErrors);

    /**
     * Load this file from this plugin's jar. The file <strong>MUST</strong> exist on the jar for this to work! This
     * method does NOT save to disk, you must do that by calling {@link DataFile#saveToDisk(boolean)}
     *
     * @param throwErrors Silence any errors that this operation might produce
     * @return If this operation was successful or not
     */
    public abstract boolean loadFromJar(boolean throwErrors);

    /**
     * Save this file to disk. The file will be saved to this plugin's data folder + the file path.
     *
     * @param throwErrors Silence any errors that this operation might produce
     * @return If this operation was successful or not
     */
    public abstract boolean saveToDisk(boolean throwErrors);

    /**
     * Delete this file from the disk. You should be checking that the file is on the disk before calling this method.
     *
     * @param throwErrors Silence any errors that this operation might produce
     * @return If this operation was successful or not
     */
    public boolean delete(boolean throwErrors) {
        isLoaded = !FileIO.deleteFile(file);
        return !isLoaded;
    }

    /**
     * Update this file from the jar, keeping existing values but adding any new values that the jar file's version has
     * but the disk's version does not.
     * <p>
     * This is useful for configs, especially when a config is updated so that the
     * user does not have to manually add new keys to the config.
     * <p>
     * Note that this method <strong>does not</strong> remove old keys from the config, as the values to be removed can
     * not be accurately determined given that custom config values can create keys that don't exist in the base config
     * but should exist anyways.
     *
     * @param throwErrors Silence any errors that this operation might produce
     * @return If this operation was successful or not
     */
    public abstract boolean updateFromJar(boolean throwErrors);
}
