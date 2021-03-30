package com.mikedeejay2.mikedeejay2lib;

import com.mikedeejay2.mikedeejay2lib.commands.CommandBase;
import com.mikedeejay2.mikedeejay2lib.commands.TabBase;
import com.mikedeejay2.mikedeejay2lib.commands.TabCommandBase;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.Listener;
import org.bukkit.permissions.Permission;
import org.bukkit.plugin.Plugin;

/**
 * Interface for a plugin with extended functionality and helper methods.
 * 
 * @see JavaPluginPlus
 * 
 * @author Mikedeejay2
 */
public interface PluginPlus extends Plugin
{
    /**
     * Get the prefix name of this plugin. This CAN include color formatting,
     * custom names, or really anything since there are no restraints placed
     * on changing the prefix name using {@link JavaPluginPlus#setPrefix(String) setPrefix()}
     *
     * @return The prefix name of this plugin
     */
    String getPrefix();

    /**
     * Set the prefix name of this plugin. Color codes are automatically formatted.
     * All messages logged by this plugin will use this prefix. Brackets are not
     * automatically applied to this prefix.
     *
     * @param prefix The new prefix name of this plugin
     */
    void setPrefix(String prefix);

    /**
     * Send a message in console with formatted chat colors
     *
     * @param message The input string
     */
    void sendMessage(String message);

    /**
     * Sends the player a formatted message
     *
     * @param player  Input player that will receive the message
     * @param message The message to be printed (will be formatted with colors)
     */
    void sendMessage(Player player, String message);

    /**
     * Sends the command sender (player or console) a formatted message
     *
     * @param sender  Input <tt>CommandSender</tt> that will receive the message
     * @param message The message to be printed (will be formatted with colors)
     */
    void sendMessage(CommandSender sender, String message);

    /**
     * Broadcast a message to all players on the server.
     *
     * @param message The message to broadcast to players
     */
    void broadcastMessage(String message);

    void registerEvent(Listener listener);

    void registerEvents(Listener... listeners);

    void registerCommand(String name, CommandExecutor executor, TabCompleter completer);

    void registerCommand(String name, CommandExecutor executor);

    void registerTabCompleter(String name, TabCompleter completer);

    void registerCommand(TabCommandBase command);

    void registerCommand(CommandBase command);

    void registerTabCompleter(TabBase completer);

    void enablePlugin(Plugin plugin);

    void disablePlugin(Plugin plugin);

    Plugin getPlugin(String name);

    Permission getPermission(String name);

    void addPermission(Permission permission);

    void removePermission(Permission permission);

    void removePermission(String name);

    void callEvent(Event event);
}
