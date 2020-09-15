package com.mikedeejay2.mikedeejay2lib.file;

import com.mikedeejay2.mikedeejay2lib.PluginBase;

import java.io.File;

/**
 * A data file is a file of any type that this plugin uses.
 * This class has multiple functions but it's main purpose it to
 * act as a base to structure all file types on.
 *
 * @author Mikedeejay2
 */
public abstract class DataFile
{
    protected static final PluginBase plugin = PluginBase.getInstance();

    // Path from the plugins folder to the file. (This DOES NOT include plugin.getDataFolder())
    protected String filePath;
    // The file to to this file on the disk (This does not ensure that the file is on the disk)
    protected File file;
    // If the file is loaded
    protected boolean isLoaded;

    public DataFile(String filePath)
    {
        this.filePath = filePath;
        this.file = new File(plugin.getDataFolder(), filePath);
        this.isLoaded = false;
    }

    /**
     * Get this file's path (NOT including plugin.getDataFolder())
     *
     * @return This file's filePath
     */
    public String getFilePath()
    {
        return filePath;
    }

    /**
     * Gets the java.io.File of this file
     *
     * @return This file's java.io.File
     */
    public File getFile()
    {
        return file;
    }

    /**
     * Return if this file is on disk or not
     *
     * @return If this file is on disk or not
     */
    public boolean fileExists()
    {
        return file.exists();
    }

    /**
     * Return if the data for this file is loaded and ready to be used or not.
     *
     * @return If this file is loaded
     */
    public boolean isLoaded()
    {
        return isLoaded;
    }

    /**
     * Resets this file's data to the default file specified in the jar.
     * DO NOT use this method if this file does not exist in this plugin's jar.
     * <br><br>
     * This method does the following in order:
     * <ol>
     *      <li>Delete the file from the disk</li>
     *      <li>Load the new file from jar</li>
     *      <li>Save the new file to disk</li>
     * </ol>
     *
     * @return If this operation was successful or not
     */
    public boolean resetFromJar()
    {
        if(!delete()) return false;
        if(!loadFromJar()) return false;
        if(!saveToDisk()) return false;
        return true;
    }

    /**
     * Resets this file's data to nothing.
     * <br><br>
     * This method does the following in order:
     * <ol>
     *      <li>Delete the file from the disk</li>
     *      <li>Load from disk which creates a new file</li>
     *      <li>Save the new file to disk</li>
     * </ol>
     *
     * @return If this operation was successful or not
     */
    public boolean resetFromNew()
    {
        if(!delete()) return false;
        if(!loadFromDisk()) return false;
        if(!saveToDisk()) return false;
        return true;
    }

    /**
     * Reload this file.
     * Unless this method is overloaded, this will only
     * load from disk replacing the current information.
     *
     * @return If this operation was successful or not
     */
    public boolean reload()
    {
        return loadFromDisk();
    }

    /**
     * Load this file from disk. The location that would be loaded is
     * the plugin's data folder + the file path of this file
     *
     * @return If this operation was successful or not
     */
    public abstract boolean loadFromDisk();

    /**
     * Load this file from this plugin's jar. The file MUST exist on
     * the jar for this to work! This method does NOT save to disk, you
     * must do that by calling saveToDisk();
     *
     * @return If this operation was successful or not
     */
    public abstract boolean loadFromJar();

    /**
     * Save this file to disk. The file will be saved to this
     * plugin's data folder + the file path.
     *
     * @return If this operation was successful or not
     */
    public abstract boolean saveToDisk();

    /**
     * Delete this file from the disk. You should be checking that the
     * file is on the disk before calling this method.
     *
     * @return If this operation was successful or not
     */
    public abstract boolean delete();

    /**
     * Update this file from the jar, keeping existing values but adding any
     * new values that the jar file's version has but the disk's version does
     * not.
     * <br>
     * This is useful for configs, especially when a config is updated so that the
     * user does not have to manually add new keys to the config.
     *
     * @return If this operation was successful or not
     */
    public abstract boolean updateFromJar();
}
