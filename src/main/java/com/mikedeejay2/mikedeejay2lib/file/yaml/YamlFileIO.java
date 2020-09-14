package com.mikedeejay2.mikedeejay2lib.file.yaml;

import com.mikedeejay2.mikedeejay2lib.file.FileIO;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.Reader;

/**
 * Util class for saving and loading YAML from and to disk.
 */
public final class YamlFileIO
{
    /**
     * Load a File into a YamlConfiguration
     *
     * @param config YamlConfiguration that will be loaded into
     * @param file File that will be loaded
     * @return Whether load was successful or not
     */
    public static boolean loadIntoYamlConfig(YamlConfiguration config, File file)
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
    public static boolean loadYamlConfigFromJar(YamlConfiguration config, String filePath)
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
}
