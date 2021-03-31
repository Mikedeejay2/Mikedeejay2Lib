package com.mikedeejay2.mikedeejay2lib;

import com.mikedeejay2.mikedeejay2lib.commands.CommandBase;
import com.mikedeejay2.mikedeejay2lib.commands.CommandInfo;
import com.mikedeejay2.mikedeejay2lib.commands.TabBase;
import com.mikedeejay2.mikedeejay2lib.commands.TabCommandBase;
import com.mikedeejay2.mikedeejay2lib.util.chat.Colors;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.Listener;
import org.bukkit.permissions.Permission;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginLogger;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Field;

/**
 * An extended form of {@link JavaPlugin} which adds more features and helper methods
 * to quickly develop plugin code.
 * <p>
 * This class <strong>is not</strong> the base of Mikedeejay2Lib, for full functionality
 * {@link BukkitPlugin} should be used instead.
 *
 * @author Mikedeejay2
 */
public abstract class EnhancedJavaPlugin extends JavaPlugin implements EnhancedPlugin
{
    private String prefix;

    @Override
    public void onEnable()
    {
        super.onEnable();
        String prefix = this.getDescription().getPrefix();
        setPrefix(prefix != null ? "[" + prefix + "] " : "[" + this.getDescription().getName() + "] ");
    }

    @Override
    public void onDisable()
    {
        super.onDisable();
    }

    /**
     * Get the prefix name of this plugin. This CAN include color formatting,
     * custom names, or really anything since there are no restraints placed
     * on changing the prefix name using {@link EnhancedJavaPlugin#setPrefix(String) setPrefix()}
     *
     * @return The prefix name of this plugin
     */
    @Override
    public String getPrefix()
    {
        return prefix;
    }

    /**
     * Set the prefix name of this plugin. Color codes are automatically formatted.
     * All messages logged by this plugin will use this prefix. Brackets are not
     * automatically applied to this prefix.
     *
     * @param prefix The new prefix name of this plugin
     */
    @Override
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
    @Override
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
    @Override
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
    @Override
    public void sendMessage(CommandSender sender, String message)
    {
        sender.sendMessage(Colors.format(getPrefix() + message));
    }

    /**
     * Broadcast a message to all players on the server.
     *
     * @param message The message to broadcast to players
     */
    @Override
    public void broadcastMessage(String message)
    {
        Bukkit.broadcastMessage(Colors.format(getPrefix() + message));
    }

    /**
     * Register an event listener to the server
     *
     * @param listener The listener to register
     */
    @Override
    public void registerEvent(Listener listener)
    {
        this.getServer().getPluginManager().registerEvents(listener, this);
    }

    /**
     * Register multiple event listeners to the server
     *
     * @param listeners The variable amount of event listeners to register
     */
    @Override
    public void registerEvents(Listener... listeners)
    {
        for(Listener listener : listeners)
        {
            registerEvent(listener);
        }
    }

    /**
     * Register a command and a tab completer to the server. The executor and
     * tab completer will be applied to the name of the command specified.
     *
     * @param name      The name of the command to register
     * @param executor  The <tt>CommandExecutor</tt> to register to the command
     * @param completer The <tt>TabCompleter</tt> to register to the command
     */
    @Override
    public void registerCommand(String name, CommandExecutor executor, TabCompleter completer)
    {
        this.getCommand(name).setExecutor(executor);
        this.getCommand(name).setTabCompleter(completer);
    }

    /**
     * Register a command to the server. The executor will be applied to the name
     * of the command specified.
     *
     * @param name     The name of the command to register
     * @param executor The <tt>CommandExecutor</tt> to register to the command
     */
    @Override
    public void registerCommand(String name, CommandExecutor executor)
    {
        this.getCommand(name).setExecutor(executor);
    }

    /**
     * Register a tab completer to the server. The tab completer will be applied to
     * the name of the command specified.
     *
     * @param name      The name of the command to register
     * @param completer The <tt>TabCompleter</tt> to register to the command
     */
    @Override
    public void registerTabCompleter(String name, TabCompleter completer)
    {
        this.getCommand(name).setTabCompleter(completer);
    }

    /**
     * Register a {@link TabCommandBase} to the server. Information contained in
     * {@link CommandInfo} will be used to register the command.
     *
     * @param command The <tt>TabCommandBase</tt> to register to the command
     */
    @Override
    public void registerCommand(TabCommandBase command)
    {
        this.getCommand(command.getName()).setExecutor(command);
        this.getCommand(command.getName()).setTabCompleter(command);
    }

    /**
     * Register a {@link CommandBase} to the server. Information contained in
     * {@link CommandInfo} will be used to register the command.
     *
     * @param command The <tt>CommandBase</tt> to register to the command
     */
    @Override
    public void registerCommand(CommandBase command)
    {
        this.getCommand(command.getName()).setExecutor(command);
    }

    /**
     * Register a {@link TabBase} to the server. Information contained in
     * {@link CommandInfo} will be used to register to the command.
     *
     * @param completer The <tt>TabBase</tt> to register to the command
     */
    @Override
    public void registerTabCompleter(TabBase completer)
    {
        this.getCommand(completer.getName()).setTabCompleter(completer);
    }

    /**
     * Enable a plugin on the server
     *
     * @param plugin The plugin to enable
     */
    @Override
    public void enablePlugin(Plugin plugin)
    {
        this.getServer().getPluginManager().enablePlugin(plugin);
    }

    /**
     * Disable a plugin on the server
     *
     * @param plugin The plugin to disable
     */
    @Override
    public void disablePlugin(Plugin plugin)
    {
        this.getServer().getPluginManager().disablePlugin(plugin);
    }

    /**
     * Get a plugin on the server via name
     *
     * @param name The name of the plugin to get
     * @return The plugin's instance, null if no plugin was found
     */
    @Override
    public Plugin getPlugin(String name)
    {
        return this.getServer().getPluginManager().getPlugin(name);
    }

    /**
     * Get a permission from the server via name
     *
     * @param name The name of the permission to get
     * @return The requested permission, null if no permission was found
     */
    @Override
    public Permission getPermission(String name)
    {
        return this.getServer().getPluginManager().getPermission(name);
    }

    /**
     * Add a permission to the server
     *
     * @param permission The permission to add
     */
    @Override
    public void addPermission(Permission permission)
    {
        this.getServer().getPluginManager().addPermission(permission);
    }

    /**
     * Remove a permission from the server
     *
     * @param permission The permission to remove
     */
    @Override
    public void removePermission(Permission permission)
    {
        this.getServer().getPluginManager().removePermission(permission);
    }

    /**
     * Remove a permission from the server via name
     *
     * @param name The name of the permission to remove
     */
    @Override
    public void removePermission(String name)
    {
        this.getServer().getPluginManager().removePermission(name);
    }

    /**
     * Call an event on the server
     *
     * @param event The event to be called
     */
    @Override
    public void callEvent(Event event)
    {
        this.getServer().getPluginManager().callEvent(event);
    }
}
