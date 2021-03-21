package com.mikedeejay2.mikedeejay2lib;

import com.mikedeejay2.mikedeejay2lib.commands.CommandManager;
import com.mikedeejay2.mikedeejay2lib.event.ListenerManager;
import com.mikedeejay2.mikedeejay2lib.data.DataManager;
import com.mikedeejay2.mikedeejay2lib.gui.listeners.GUIListener;
import com.mikedeejay2.mikedeejay2lib.gui.manager.GUIManager;
import com.mikedeejay2.mikedeejay2lib.nms.NMSHandler;
import com.mikedeejay2.mikedeejay2lib.text.language.LangManager;
import com.mikedeejay2.mikedeejay2lib.util.chat.Colors;
import com.mikedeejay2.mikedeejay2lib.util.enchant.GlowEnchantment;
import com.mikedeejay2.mikedeejay2lib.util.version.MinecraftVersion;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
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
    protected String prefix;
    protected MinecraftVersion minecraftVersion;
    protected DataManager dataManager;
    protected LangManager libLangManager;
    protected CommandManager commandManager;
    protected ListenerManager listenerManager;
    protected GUIManager guiManager;
    protected NMSHandler nms;

    public PluginBase()
    {}

    @Override
    public void onEnable()
    {
        this.prefix = "[" + this.getDescription().getName() + "] ";
        this.minecraftVersion = new MinecraftVersion(this);
        this.libLangManager = new LangManager(this, "lang/lib");
        this.nms = new NMSHandler(this);
        this.commandManager = new CommandManager(this);
        this.dataManager = new DataManager();
        this.listenerManager = new ListenerManager(this);
        this.guiManager = new GUIManager(this);

        this.getServer().getPluginManager().registerEvents(new GUIListener(this), this);

        GlowEnchantment.registerGlow(this);

        this.sendMessage(String.format("&a%s is powered by Mikedeejay2Lib, a collection of open source resources for developers to use.", this.getDescription().getName()));
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
    public DataManager fileManager()
    {
        return dataManager;
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
     * Get this plugin's listener manager
     *
     * @return The listener manager
     */
    public ListenerManager getListenerManager()
    {
        return listenerManager;
    }

    /**
     * Get this plugin's GUI manager
     *
     * @return The GUI manager
     */
    public GUIManager getGUIManager()
    {
        return guiManager;
    }

    /**
     * Gets the Net Minecraft Server handler
     *
     * @return The Net Minecraft Server handler
     */
    public NMSHandler getNMSHandler()
    {
        return nms;
    }

    public LangManager getLibLangManager()
    {
        return libLangManager;
    }

    /**
     * Send a message in console with formatted chat colors
     *
     * @param message The input string
     */
    public void sendMessage(String message)
    {
        Bukkit.getConsoleSender().sendMessage(Colors.format(getPrefix() + message));
    }

    /**
     * Send a message in console with formatted chat colors
     * Default color is red so it's more visible
     *
     * @param message The input string
     */
    public void debug(String message)
    {
        Bukkit.getConsoleSender().sendMessage(Colors.format(getPrefix() + "&c" + message));
    }

    /**
     * Sends the player a formatted message
     *
     * @param player  Input player that will receive the message
     * @param message The message to be printed (will be formatted with colors)
     */
    public void sendMessage(Player player, String message)
    {
        player.sendMessage(Colors.format(getPrefix() + message));
    }

    /**
     * Sends the command sender (player or console) a formatted message
     *
     * @param sender  Input <tt>CommandSender</tt> that will receive the message
     * @param message The message to be printed (will be formatted with colors)
     */
    public void sendMessage(CommandSender sender, String message)
    {
        sender.sendMessage(Colors.format(getPrefix() + message));
    }

    /**
     * Gets a title tag that can be added to the beginning of text to define
     * what this text is coming from
     * @return The title string
     */
    public String getPrefix()
    {
        return prefix;
    }

    /**
     * Broadcast a message to all players on the server.
     *
     * @param message The message to broadcast to players
     */
    public void broadcastMessage(String message)
    {
        Bukkit.broadcastMessage(Colors.format(getPrefix() + message));
    }

    /**
     * Send a title to a player
     *
     * @param player   The player to send the title to
     * @param title    The title to send the player
     * @param subtitle The subtitle to send the player
     * @param fadeIn   The fade in rate of the title
     * @param stay     The stay length of the title
     * @param fadeOut  The fade out rate of the title
     */
    public void sendTitle(Player player, String title, String subtitle, int fadeIn, int stay, int fadeOut)
    {
        player.sendTitle(Colors.format(title), Colors.format(subtitle), fadeIn, stay, fadeOut);
    }
}
