package com.mikedeejay2.mikedeejay2lib;

import com.mikedeejay2.mikedeejay2lib.commands.AbstractCommandManager;
import com.mikedeejay2.mikedeejay2lib.language.LangManager;
import com.mikedeejay2.mikedeejay2lib.version.MinecraftVersion;
import com.mikedeejay2.mikedeejay2lib.yaml.YamlBase;
import org.bukkit.plugin.java.JavaPlugin;

public class PluginBase extends JavaPlugin
{
    protected static PluginBase instance;
    protected double MCVersion;
    protected YamlBase config;
    protected LangManager lang;
    protected AbstractCommandManager commandManager;

    @Override
    public void onEnable()
    {
        setInstance(this);
        this.MCVersion = MinecraftVersion.getMCVersion();
    }

    public void onEnable(YamlBase config, AbstractCommandManager commandManager)
    {
        this.onEnable();
        this.config = config;
        this.lang = new LangManager();
        this.commandManager = commandManager;
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

    public YamlBase config()
    {
        return config;
    }

    public LangManager lang()
    {
        return lang;
    }

    public AbstractCommandManager commandManager()
    {
        return commandManager;
    }
}
