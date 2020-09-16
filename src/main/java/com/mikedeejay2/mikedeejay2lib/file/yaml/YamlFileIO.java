package com.mikedeejay2.mikedeejay2lib.file.yaml;

import com.mikedeejay2.mikedeejay2lib.PluginBase;
import com.mikedeejay2.mikedeejay2lib.file.FileIO;
import com.mikedeejay2.mikedeejay2lib.util.PluginInstancer;

import java.io.*;
import java.util.logging.Level;

/**
 * Util class for saving and loading YAML from and to disk.
 *
 * @author Mikedeejay2
 */
public final class YamlFileIO extends PluginInstancer<PluginBase>
{
    private final FileIO fileIO;

    public YamlFileIO(PluginBase plugin)
    {
        super(plugin);
        this.fileIO = new FileIO(plugin);
    }

    /**
     * Load a File into a YamlConfiguration
     *
     * @param config YamlConfiguration that will be loaded into
     * @param file File that will be loaded
     * @return Whether load was successful or not
     */
    public boolean loadIntoYamlConfig(EnhancedYaml config, File file)
    {
        try
        {
            config.load(file);
        }
        catch(Exception e)
        {
            fileIO.logFileCouldNotBeLoaded(file.getPath(), e, false);
            return false;
        }
        return true;
    }

    /**
     * Load a file in the plugin's jar into a YamlConfiguration
     *
     * @param config YamlConfiguration that will be loaded into
     * @param filePath Path to the file. This should NOT include plugin.getDataFolder()
     * @return Whether load was successful or not
     */
    public boolean loadYamlConfigFromJar(EnhancedYaml config, String filePath)
    {
        Reader reader = fileIO.getReaderFromJar(filePath);
        try
        {
            config.load(reader);
        }
        catch(Exception e)
        {
            fileIO.logFileCouldNotBeLoaded(filePath, e, false);
            return false;
        }
        return true;
    }

    /**
     * Save a yaml file to disk.
     *
     * @param config The EnhancedYaml file being saved
     * @param file The file to save to
     * @return Whether load was successful or not
     */
    public boolean saveYamlConfig(EnhancedYaml config, File file)
    {
        try
        {
            config.save(file);
        }
        catch(Exception e)
        {
            plugin.getLogger().log(Level.SEVERE, "Could not save " + file.getName() + " to " + file.getPath(), e);
            return false;
        }
        return true;
    }
}
