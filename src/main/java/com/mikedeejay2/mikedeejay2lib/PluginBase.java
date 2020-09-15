package com.mikedeejay2.mikedeejay2lib;

import com.mikedeejay2.mikedeejay2lib.commands.AbstractCommandManager;
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

    /**
     * Sets the command manager and the command for that command manager. When calling this
     * command, no other setup is required to use the command as long as the command manager
     * has been configured properly.
     *
     * @param commandManager Command manager to set to
     * @param commandName The command to make the command manager use
     */
    protected void setCommandManager(AbstractCommandManager commandManager, String commandName)
    {
        this.commandManager = commandManager;
        commandManager.setCommandName(commandName);
        commandManager.setup();
    }
}
