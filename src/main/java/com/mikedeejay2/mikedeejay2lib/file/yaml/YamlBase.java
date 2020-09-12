package com.mikedeejay2.mikedeejay2lib.file.yaml;

import com.mikedeejay2.mikedeejay2lib.PluginBase;
import com.mikedeejay2.mikedeejay2lib.util.file.FileIO;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.*;
import java.util.logging.Level;

public class YamlBase
{
    protected static final PluginBase plugin = PluginBase.getInstance();

    protected CustomYamlSection configSection;
    protected FileConfiguration file;
    protected String fileName;

    public YamlBase()
    {
        this(null);
    }

    public YamlBase(String fileName)
    {
        this.fileName = fileName;
        if(fileName == null) this.fileName = "config.yml";
        loadFile();
        onEnable();
    }

    private void loadFile()
    {
        this.file = new YamlConfiguration();
        File theFile = FileIO.loadFile(fileName);
        try
        {
            file.load(new InputStreamReader(new FileInputStream(theFile)));
        }
        catch(Exception e) {}
        this.configSection = new CustomYamlSection(this);
    }

    /**
     * Enable the config. This loads all of the values into the above variables.
     */
    public void onEnable()
    {
        file.options().copyDefaults();
        saveFile();
    }

    /**
     * Disable the config. This isn't being used but it might be used in the future.
     */
    public void onDisable()
    {
        //Since we're not modifying values in game, we don't have to save this on disable.
        // This also fixes the bug of the chance that they modify the config.yml while server is running
        // and then when it's restarted their old config.yml gets rolled back.
        //saveFile();
    }

    /**
     * Save the config's file to disk.
     */
    public void saveFile()
    {
        FileIO.saveFile(fileName, false);
    }

    /**
     * Reload the config from the file if it was modified from outside the game.
     */
    public void reload()
    {
        loadFile();
        onEnable();
    }

    /**
     * Reset the config and reload it to a fresh config with default values.
     */
    public void reset()
    {
        File configFile = new File(plugin.getDataFolder(), fileName);
        configFile.delete();
        reload();
    }
}
