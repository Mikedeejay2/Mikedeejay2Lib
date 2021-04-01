package com.mikedeejay2.mikedeejay2lib;

import com.mikedeejay2.mikedeejay2lib.commands.CommandBase;
import com.mikedeejay2.mikedeejay2lib.commands.CommandInfo;
import com.mikedeejay2.mikedeejay2lib.commands.TabBase;
import com.mikedeejay2.mikedeejay2lib.commands.TabCommandBase;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.Listener;
import org.bukkit.permissions.Permission;
import org.bukkit.plugin.Plugin;

/**
 * Interface for a plugin with extended functionality and helper methods.
 * 
 * @see EnhancedJavaPlugin
 * 
 * @author Mikedeejay2
 */
public interface EnhancedPlugin extends Plugin
{
    /**
     * Get the prefix name of this plugin. This CAN include color formatting,
     * custom names, or really anything since there are no restraints placed
     * on changing the prefix name using {@link EnhancedJavaPlugin#setPrefix(String) setPrefix()}
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

    /**
     * Send an info message from this plugin's logger
     *
     * @param message The message to be logged
     */
    void sendInfo(String message);

    /**
     * Send a warning message from this plugin's logger
     *
     * @param message The message to be logged
     */
    void sendWarning(String message);

    /**
     * Send a severe message from this plugin's logger
     *
     * @param message The message to be logged
     */
    void sendSevere(String message);

    /**
     * Register an event listener to the server
     *
     * @param listener The listener to register
     */
    void registerEvent(Listener listener);

    /**
     * Register multiple event listeners to the server
     *
     * @param listeners The variable amount of event listeners to register
     */
    void registerEvents(Listener... listeners);

    /**
     * Register a command and a tab completer to the server. The executor and
     * tab completer will be applied to the name of the command specified.
     *
     * @param name      The name of the command to register
     * @param executor  The <tt>CommandExecutor</tt> to register to the command
     * @param completer The <tt>TabCompleter</tt> to register to the command
     */
    void registerCommand(String name, CommandExecutor executor, TabCompleter completer);

    /**
     * Register a tab executor command to the server. The executor will be
     * applied to the name of the command specified.
     *
     * @param name      The name of the command to register
     * @param executor  The <tt>TabExecutor</tt> to register to the command
     */
    void registerCommand(String name, TabExecutor executor);

    /**
     * Register a command to the server. The executor will be applied to the name
     * of the command specified.
     *
     * @param name     The name of the command to register
     * @param executor The <tt>CommandExecutor</tt> to register to the command
     */
    void registerCommand(String name, CommandExecutor executor);

    /**
     * Register a tab completer to the server. The tab completer will be applied to
     * the name of the command specified.
     *
     * @param name      The name of the command to register
     * @param completer The <tt>TabCompleter</tt> to register to the command
     */
    void registerTabCompleter(String name, TabCompleter completer);

    /**
     * Register a {@link TabCommandBase} to the server. Information contained in
     * {@link CommandInfo} will be used to register the command.
     *
     * @param command The <tt>TabCommandBase</tt> to register to the command
     */
    void registerCommand(TabCommandBase command);

    /**
     * Register a {@link CommandBase} to the server. Information contained in
     * {@link CommandInfo} will be used to register the command.
     *
     * @param command The <tt>CommandBase</tt> to register to the command
     */
    void registerCommand(CommandBase command);

    /**
     * Register a {@link TabBase} to the server. Information contained in
     * {@link CommandInfo} will be used to register to the command.
     *
     * @param completer The <tt>TabBase</tt> to register to the command
     */
    void registerTabCompleter(TabBase completer);

    /**
     * Enable a plugin on the server
     *
     * @param plugin The plugin to enable
     */
    void enablePlugin(Plugin plugin);

    /**
     * Disable a plugin on the server
     *
     * @param plugin The plugin to disable
     */
    void disablePlugin(Plugin plugin);

    /**
     * Get a plugin on the server via name
     *
     * @param name The name of the plugin to get
     * @return The plugin's instance, null if no plugin was found
     */
    Plugin getPlugin(String name);

    /**
     * Get a permission from the server via name
     *
     * @param name The name of the permission to get
     * @return The requested permission, null if no permission was found
     */
    Permission getPermission(String name);

    /**
     * Add a permission to the server
     *
     * @param permission The permission to add
     */
    void addPermission(Permission permission);

    /**
     * Remove a permission from the server
     *
     * @param permission The permission to remove
     */
    void removePermission(Permission permission);

    /**
     * Remove a permission from the server via name
     *
     * @param name The name of the permission to remove
     */
    void removePermission(String name);

    /**
     * Call an event on the server
     *
     * @param event The event to be called
     */
    void callEvent(Event event);
}
