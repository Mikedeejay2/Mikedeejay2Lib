package com.mikedeejay2.mikedeejay2lib;

import com.mikedeejay2.mikedeejay2lib.commands.CommandManager;
import com.mikedeejay2.mikedeejay2lib.event.ListenerManager;
import com.mikedeejay2.mikedeejay2lib.data.FileManager;
import com.mikedeejay2.mikedeejay2lib.gui.listeners.GUIListener;
import com.mikedeejay2.mikedeejay2lib.gui.manager.GUIManager;
import com.mikedeejay2.mikedeejay2lib.text.language.LangManager;
import com.mikedeejay2.mikedeejay2lib.nms.NMSHandler;
import com.mikedeejay2.mikedeejay2lib.util.bstats.BStats;
import com.mikedeejay2.mikedeejay2lib.util.chat.Chat;
import com.mikedeejay2.mikedeejay2lib.util.update.UpdateChecker;
import com.mikedeejay2.mikedeejay2lib.util.version.MinecraftVersion;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Main class for Mikedeejay2Lib.
 * <p>
 * <tt>PluginBase</tt> is a robust plugin class that extends {@link JavaPlugin} to
 * add new functions. Check source code on Github to find all features, planned additions,
 * bugs, etc.
 * <p>
 * Source code: <a href="https://github.com/Mikedeejay2/Mikedeejay2Lib">https://github.com/Mikedeejay2/Mikedeejay2Lib</a>
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
    protected NMSHandler nms;

    public PluginBase()
    {}

    @Override
    public void onEnable()
    {
        this.chat = new Chat(this);
        this.minecraftVersion = new MinecraftVersion(this);
        this.langManager = new LangManager(this);
        this.nms = new NMSHandler(this);
        this.commandManager = new CommandManager(this);
        this.fileManager = new FileManager();
        this.listenerManager = new ListenerManager(this);
        this.guiManager = new GUIManager(this);
        this.bStats = new BStats(this);
        this.updateChecker = new UpdateChecker(this);

        this.getServer().getPluginManager().registerEvents(new GUIListener(this), this);

        chat.sendMessage("&a" + langManager.getTextLib("generic.on_enable_message", new String[]{"PLUGIN"}, new String[]{this.getDescription().getName()}));
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

    /**
     * Gets the Net Minecraft Server handler
     *
     * @return The Net Minecraft Server handler
     */
    public NMSHandler NMS()
    {
        return nms;
    }
}
