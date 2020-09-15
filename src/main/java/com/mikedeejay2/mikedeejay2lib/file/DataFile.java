package com.mikedeejay2.mikedeejay2lib.file;

import com.mikedeejay2.mikedeejay2lib.PluginBase;

import java.io.File;

/**
 * A data file is a file of any type that this plugin uses.
 * This class has multiple functions but it's main purpose it to
 * act as a base to structure all file types on.
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

    public boolean reload()
    {
        return loadFromDisk();
    }

    public abstract boolean loadFromDisk();
    public abstract boolean loadFromJar();
    public abstract boolean saveToDisk();
    public abstract boolean delete();
    public abstract boolean updateFromJar();
}
