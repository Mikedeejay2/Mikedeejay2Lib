package com.mikedeejay2.mikedeejay2lib.file.yaml;

import com.mikedeejay2.mikedeejay2lib.PluginBase;
import com.mikedeejay2.mikedeejay2lib.file.FileIO;

import java.io.*;
import java.util.logging.Level;

/**
 * Util class for saving and loading YAML from and to disk.
 */
public final class YamlFileIO
{
    private static final PluginBase plugin = PluginBase.getInstance();

    /**
     * Load a File into a YamlConfiguration
     *
     * @param config YamlConfiguration that will be loaded into
     * @param file File that will be loaded
     * @return Whether load was successful or not
     */
    public static boolean loadIntoYamlConfig(EnhancedYaml config, File file)
    {
        try
        {
            config.load(file);
        }
        catch(Exception e)
        {
            FileIO.logFileCouldNotBeLoaded(file.getPath(), e, false);
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
    public static boolean loadYamlConfigFromJar(EnhancedYaml config, String filePath)
    {
        Reader reader = FileIO.getReaderFromJar(filePath);
        try
        {
            config.load(reader);
        }
        catch(Exception e)
        {
            FileIO.logFileCouldNotBeLoaded(filePath, e, true);
            return false;
        }
        return true;
    }

    public static boolean saveYamlConfig(EnhancedYaml config, File file)
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
