package com.mikedeejay2.mikedeejay2lib;

import com.mikedeejay2.mikedeejay2lib.commands.CommandManager;
import com.mikedeejay2.mikedeejay2lib.event.ListenerManager;
import com.mikedeejay2.mikedeejay2lib.file.FileManager;
import com.mikedeejay2.mikedeejay2lib.language.LangManager;
import com.mikedeejay2.mikedeejay2lib.util.chat.Chat;
import com.mikedeejay2.mikedeejay2lib.util.item.ItemCreator;
import com.mikedeejay2.mikedeejay2lib.util.version.MinecraftVersion;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Plugin base handles all of the boiler plate stuff for any plugin that uses it.
 *
 * @author Mikedeejay2
 */
public class PluginBase extends JavaPlugin
{
    // The Minecraft version array that this server is running on
    protected int[] MCVersion;
    // The file manager for this plugin
    protected FileManager fileManager;
    // The lang manager for this plugin
    protected LangManager langManager;
    // The command manager for this plugin
    protected CommandManager commandManager;

    protected Chat chat;
    protected ItemCreator itemCreator;
    protected ListenerManager listeners;

    public PluginBase()
    {}

    @Override
    public void onEnable()
    {
        this.chat = new Chat(this);
        this.itemCreator = new ItemCreator(this);
        this.MCVersion = new MinecraftVersion(this).getMCVersion();
        this.langManager = new LangManager(this);
        this.commandManager = new CommandManager(this);
        this.fileManager = new FileManager(this);
        this.listeners = new ListenerManager(this);
    }

    @Override
    public void onDisable()
    {

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

    public Chat chat()
    {
        return chat;
    }

    public ItemCreator itemCreator()
    {
        return itemCreator;
    }

    public ListenerManager listenerManager()
    {
        return listeners;
    }
}
