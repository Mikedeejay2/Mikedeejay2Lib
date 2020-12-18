package com.mikedeejay2.mikedeejay2lib;

import com.mikedeejay2.mikedeejay2lib.commands.CommandManager;
import com.mikedeejay2.mikedeejay2lib.event.ListenerManager;
import com.mikedeejay2.mikedeejay2lib.file.FileManager;
import com.mikedeejay2.mikedeejay2lib.gui.listeners.GUIListener;
import com.mikedeejay2.mikedeejay2lib.gui.manager.GUIManager;
import com.mikedeejay2.mikedeejay2lib.language.LangManager;
import com.mikedeejay2.mikedeejay2lib.util.bstats.BStats;
import com.mikedeejay2.mikedeejay2lib.util.chat.Chat;
import com.mikedeejay2.mikedeejay2lib.util.update.UpdateChecker;
import com.mikedeejay2.mikedeejay2lib.util.version.MinecraftVersion;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * The plugin base for plugins that use this library plugin.
 * This plugin base must be used if this library plugin is being used
 * because many classes in the library require a reference to a PluginBase.
 *
 * @author Mikedeejay2
 */
public class PluginBase extends JavaPlugin
{
    protected MinecraftVersion minecraftVersion;
    protected FileManager fileManager;
    protected LangManager langManager;
    protected CommandManager commandManager;
    protected Chat chat;
    protected ListenerManager listenerManager;
    protected GUIManager guiManager;
    protected BStats bStats;
    protected UpdateChecker updateChecker;

    public PluginBase()
    {}

    @Override
    public void onEnable()
    {
        this.chat = new Chat(this);
        this.minecraftVersion = new MinecraftVersion(this);
        this.langManager = new LangManager(this);
        this.commandManager = new CommandManager(this);
        this.fileManager = new FileManager();
        this.listenerManager = new ListenerManager(this);
        this.guiManager = new GUIManager(this);
        this.bStats = new BStats(this);
        this.updateChecker = new UpdateChecker(this);

        this.getServer().getPluginManager().registerEvents(new GUIListener(this), this);
    }

    @Override
    public void onDisable()
    {

    }

    /**
     * Get the Minecraft server's Minecraft version
     *
     * @return The Minecraft version
     */
    public MinecraftVersion getMCVersion()
    {
        return minecraftVersion;
    }

    /**
     * Get this plugin's file manager
     *
     * @return The file manager
     */
    public FileManager fileManager()
    {
        return fileManager;
    }

    /**
     * Get this plugin's language manager
     *
     * @return The language manager
     */
    public LangManager langManager()
    {
        return langManager;
    }

    /**
     * Get this plugin's command manager
     *
     * @return The command manager
     */
    public CommandManager commandManager()
    {
        return commandManager;
    }

    /**
     * Get this plugin's chat processor
     *
     * @return The chat util
     */
    public Chat chat()
    {
        return chat;
    }

    /**
     * Get this plugin's listener manager
     *
     * @return The listener manager
     */
    public ListenerManager listenerManager()
    {
        return listenerManager;
    }

    /**
     * Get this plugin's GUI manager
     *
     * @return The GUI manager
     */
    public GUIManager guiManager()
    {
        return guiManager;
    }

    /**
     * Get this plugin's BStats manager
     *
     * @return The BStats manager
     */
    public BStats bStats()
    {
        return bStats;
    }

    /**
     * Get this plugin's Update Checker
     *
     * @return The Update Checker
     */
    public UpdateChecker updateChecker()
    {
        return updateChecker;
    }
}
