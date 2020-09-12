package com.mikedeejay2.mikedeejay2lib;

import com.mikedeejay2.mikedeejay2lib.commands.AbstractCommandManager;
import com.mikedeejay2.mikedeejay2lib.language.LangManager;
import com.mikedeejay2.mikedeejay2lib.version.MinecraftVersion;
import com.mikedeejay2.mikedeejay2lib.yaml.YamlBase;
import org.bukkit.plugin.java.JavaPlugin;

public class PluginBase extends JavaPlugin
{
    protected static PluginBase instance;
    protected int[] MCVersion;
    protected YamlBase config;
    protected LangManager lang;
    protected AbstractCommandManager commandManager;

    public PluginBase()
    {
        setInstance(this);
    }

    public void onEnable(YamlBase config, AbstractCommandManager commandManager, String baseCommand)
    {
        this.MCVersion = MinecraftVersion.getMCVersion();
        this.config = config;
        this.lang = new LangManager();
        this.commandManager = commandManager;
        this.commandManager.setCommandName(baseCommand);
        commandManager.setup();
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

    public int[] getMCVersion()
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
