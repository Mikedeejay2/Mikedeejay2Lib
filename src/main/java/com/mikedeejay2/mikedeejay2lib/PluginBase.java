package com.mikedeejay2.mikedeejay2lib;

import com.mikedeejay2.mikedeejay2lib.commands.AbstractCommandManager;
import com.mikedeejay2.mikedeejay2lib.file.FileManager;
import com.mikedeejay2.mikedeejay2lib.language.LangManager;
import com.mikedeejay2.mikedeejay2lib.util.version.MinecraftVersion;
import org.bukkit.plugin.java.JavaPlugin;

public class PluginBase extends JavaPlugin
{
    protected static PluginBase instance;
    protected int[] MCVersion;
    protected FileManager fileManager;
    protected LangManager langManager;
    protected AbstractCommandManager commandManager;

    public PluginBase()
    {}

    @Override
    public void onEnable()
    {
        setInstance(this);
        this.MCVersion = MinecraftVersion.getMCVersion();
        this.langManager = new LangManager();
        this.fileManager = new FileManager();
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

    public FileManager fileManager()
    {
        return fileManager;
    }

    public LangManager langManager()
    {
        return langManager;
    }

    public AbstractCommandManager commandManager()
    {
        return commandManager;
    }

    protected void setCommandManager(AbstractCommandManager commandManager, String commandName)
    {
        this.commandManager = commandManager;
        commandManager.setCommandName(commandName);
    }
}
