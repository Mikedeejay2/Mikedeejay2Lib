package com.mikedeejay2.mikedeejay2lib.yaml;

import com.mikedeejay2.mikedeejay2lib.PluginBase;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.File;

public class YamlBase
{
    protected static final PluginBase plugin = PluginBase.getInstance();

    protected CustomYamlSection configSection;
    protected FileConfiguration config;
    protected String fileName;

    public YamlBase()
    {
        this(null);
    }

    public YamlBase(String fileName)
    {
        this.fileName = fileName;
        if(fileName == null) fileName = "config.yml";
        this.configSection = new CustomYamlSection();
        this.config = configSection.getCurrentFile();
    }

    /**
     * Enable the config. This loads all of the values into the above variables.
     */
    public void onEnable()
    {
        config.options().copyDefaults();
        plugin.saveDefaultConfig();
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
        plugin.saveConfig();
    }

    /**
     * Reload the config from the file if it was modified from outside the game.
     */
    public void reload()
    {
        plugin.reloadConfig();
        this.config = plugin.getConfig();
        this.configSection = new CustomYamlSection();
        onEnable();
    }

    /**
     * Reset the config and reload it to a fresh config with default values.
     */
    public void reset()
    {
        plugin.getResource(fileName);
        File configFile = new File(plugin.getDataFolder(), fileName);
        configFile.delete();
        reload();
    }
}
