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

    public PluginBase(YamlBase config, AbstractCommandManager commandManager)
    {
        this.config = config;
        this.commandManager = commandManager;
        this.MCVersion = MinecraftVersion.getMCVersion();
        this.lang = new LangManager();
    }

    @Override
    public void onEnable()
    {
        setInstance(this);
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
