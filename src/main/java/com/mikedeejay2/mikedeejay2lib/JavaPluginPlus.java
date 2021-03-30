package com.mikedeejay2.mikedeejay2lib;

import com.mikedeejay2.mikedeejay2lib.util.chat.Colors;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginLogger;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Field;

public abstract class JavaPluginPlus extends JavaPlugin
{
    private String prefix;

    @Override
    public void onEnable()
    {
        super.onEnable();
        String prefix = this.getDescription().getPrefix();
        this.prefix = prefix != null ? "[" + prefix + "] " : "[" + this.getDescription().getName() + "] ";
    }

    @Override
    public void onDisable()
    {
        super.onDisable();
    }

    public String getPrefix()
    {
        return prefix;
    }

    public void setPrefix(String prefix)
    {
        this.prefix = Colors.format(prefix);
        PluginLogger logger = (PluginLogger) this.getLogger();
        try
        {
            Field field = logger.getClass().getDeclaredField("pluginName");
            field.setAccessible(true);
            field.set(logger, this.prefix);
        }
        catch(NoSuchFieldException | IllegalAccessException e)
        {
            logger.warning("Could not change PluginLogger prefix to " + this.prefix);
            e.printStackTrace();
        }
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
