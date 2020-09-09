package com.mikedeejay2.mikedeejay2lib.config;

import com.mikedeejay2.mikedeejay2lib.yaml.YamlBase;

public class ConfigBase extends YamlBase
{

    public ConfigBase()
    {
        super();
    }

    public ConfigBase(String fileName)
    {
        super(fileName);
    }

    //Variables
    public String LANG_LOCALE;

    /**
     * Enable the config. This loads all of the values into the above variables.
     */
    @Override
    public void onEnable()
    {
        super.onEnable();

        LANG_LOCALE = configSection.loadString("Language");
    }

    /**
     * Disable the config. This isn't being used but it might be used in the future.
     */
    public void onDisable()
    {
        super.onDisable();
    }

    /**
     * Reload the config from the file if it was modified from outside the game.
     */
    public void reload()
    {
        super.reload();
    }

    /**
     * Reset the config and reload it to a fresh config with default values.
     */
    public void reset()
    {
        super.reset();
    }
}
