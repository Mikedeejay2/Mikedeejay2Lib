package com.mikedeejay2.mikedeejay2lib.data.yaml;

import com.mikedeejay2.mikedeejay2lib.PluginBase;
import com.mikedeejay2.mikedeejay2lib.data.FileIO;

import java.io.File;
import java.io.Reader;

/**
 * Util class for saving and loading YAML from and to disk.
 *
 * @author Mikedeejay2
 */
public final class YamlFileIO
{
    protected final PluginBase plugin;
    private final FileIO fileIO;

    public YamlFileIO(PluginBase plugin)
    {
        this.plugin = plugin;
        this.fileIO = new FileIO(plugin);
    }

    /**
     * Load a File into a YamlConfiguration
     *
     * @param config      YamlConfiguration that will be loaded into
     * @param file        File that will be loaded
     * @param throwErrors Whether this method should throw errors if something goes wrong or not
     * @return Whether load was successful or not
     */
    public boolean loadIntoYamlConfig(EnhancedYaml config, File file, boolean throwErrors)
    {
        try
        {
            config.load(file);
        }
        catch(Exception e)
        {
            if(throwErrors) fileIO.logFileCouldNotBeLoaded(file.getPath(), e);
            return false;
        }
        return true;
    }

    /**
     * Load a file in the plugin's jar into a YamlConfiguration
     *
     * @param config      YamlConfiguration that will be loaded into
     * @param filePath    Path to the file. This should NOT include plugin.getDataFolder()
     * @param throwErrors Whether this method should throw errors if something goes wrong or not
     * @return Whether load was successful or not
     */
    public boolean loadYamlConfigFromJar(EnhancedYaml config, String filePath, boolean throwErrors)
    {
        Reader reader = fileIO.getReaderFromJar(filePath, throwErrors);
        try
        {
            config.load(reader);
        }
        catch(Exception e)
        {
            if(throwErrors) fileIO.logFileCouldNotBeLoaded(filePath, e);
            return false;
        }
        return true;
    }

    /**
     * Save a yaml file to disk.
     *
     * @param config      The EnhancedYaml file being saved
     * @param file        The file to save to
     * @param throwErrors Whether this method should throw errors if something goes wrong or not
     * @return Whether load was successful or not
     */
    public boolean saveYamlConfig(EnhancedYaml config, File file, boolean throwErrors)
    {
        try
        {
            config.save(file);
        }
        catch(Exception e)
        {
            if(throwErrors) fileIO.logFileCouldNotBeSaved(file.getPath(), e);
            return false;
        }
        return true;
    }
}