package com.mikedeejay2.mikedeejay2lib;

import com.mikedeejay2.mikedeejay2lib.commands.CommandManager;
import com.mikedeejay2.mikedeejay2lib.file.FileManager;
import com.mikedeejay2.mikedeejay2lib.language.LangManager;
import com.mikedeejay2.mikedeejay2lib.util.version.MinecraftVersion;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Plugin base handles all of the boiler plate stuff for any plugin that uses it.
 *
 * @author Mikedeejay2
 */
public class PluginBase extends JavaPlugin
{
    // Instance to this plugin
    protected static PluginBase instance;
    // The Minecraft version array that this server is running on
    protected int[] MCVersion;
    // The file manager for this plugin
    protected FileManager fileManager;
    // The lang manager for this plugin
    protected LangManager langManager;
    // The command manager for this plugin
    protected CommandManager commandManager;

    public PluginBase()
    {}

    @Override
    public void onEnable()
    {
        setInstance(this);
        this.MCVersion = MinecraftVersion.getMCVersion();
        this.langManager = new LangManager();
        this.commandManager = new CommandManager();
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

    public CommandManager commandManager()
    {
        return commandManager;
    }
}
