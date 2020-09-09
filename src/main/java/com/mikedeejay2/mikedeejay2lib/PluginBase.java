package com.mikedeejay2.mikedeejay2lib;

import com.mikedeejay2.mikedeejay2lib.config.ConfigBase;
import com.mikedeejay2.mikedeejay2lib.language.LangManager;
import com.mikedeejay2.mikedeejay2lib.version.MinecraftVersion;
import org.bukkit.plugin.java.JavaPlugin;

public final class PluginBase extends JavaPlugin
{
    private static PluginBase instance;
    private double MCVersion;
    private ConfigBase config;
    private LangManager lang;

    @Override
    public void onEnable()
    {
        setInstance(this);
        MCVersion = MinecraftVersion.getMCVersion();
        config = new ConfigBase();
        lang = new LangManager();
    }

    @Override
    public void onDisable()
    {

    }

    public static PluginBase getInstance()
    {
        return instance;
    }

    public static void setInstance(PluginBase instance)
    {
        PluginBase.instance = instance;
    }

    public double getMCVersion()
    {
        return MCVersion;
    }

    public ConfigBase config()
    {
        return config;
    }

    public LangManager lang()
    {
        return lang;
    }
}
