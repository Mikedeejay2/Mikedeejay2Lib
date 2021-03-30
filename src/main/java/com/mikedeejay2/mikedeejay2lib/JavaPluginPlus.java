package com.mikedeejay2.mikedeejay2lib;

import com.mikedeejay2.mikedeejay2lib.commands.CommandBase;
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
public abstract class JavaPluginPlus extends JavaPlugin implements PluginPlus
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
     * on changing the prefix name using {@link JavaPluginPlus#setPrefix(String) setPrefix()}
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

    @Override
    public void registerEvent(Listener listener)
    {
        this.getServer().getPluginManager().registerEvents(listener, this);
    }

    @Override
    public void registerEvents(Listener... listeners)
    {
        for(Listener listener : listeners)
        {
            registerEvent(listener);
        }
    }

    @Override
    public void registerCommand(String name, CommandExecutor executor, TabCompleter completer)
    {
        this.getCommand(name).setExecutor(executor);
        this.getCommand(name).setTabCompleter(completer);
    }

    @Override
    public void registerCommand(String name, CommandExecutor executor)
    {
        this.getCommand(name).setExecutor(executor);
    }

    @Override
    public void registerTabCompleter(String name, TabCompleter completer)
    {
        this.getCommand(name).setTabCompleter(completer);
    }

    @Override
    public void registerCommand(TabCommandBase command)
    {
        this.getCommand(command.getName()).setExecutor(command);
        this.getCommand(command.getName()).setTabCompleter(command);
    }

    @Override
    public void registerCommand(CommandBase command)
    {
        this.getCommand(command.getName()).setExecutor(command);
    }

    @Override
    public void registerTabCompleter(TabBase completer)
    {
        this.getCommand(completer.getName()).setTabCompleter(completer);
    }

    @Override
    public void enablePlugin(Plugin plugin)
    {
        this.getServer().getPluginManager().enablePlugin(plugin);
    }

    @Override
    public void disablePlugin(Plugin plugin)
    {
        this.getServer().getPluginManager().disablePlugin(plugin);
    }

    @Override
    public Plugin getPlugin(String name)
    {
        return this.getServer().getPluginManager().getPlugin(name);
    }

    @Override
    public Permission getPermission(String name)
    {
        return this.getServer().getPluginManager().getPermission(name);
    }

    @Override
    public void addPermission(Permission permission)
    {
        this.getServer().getPluginManager().addPermission(permission);
    }

    @Override
    public void removePermission(Permission permission)
    {
        this.getServer().getPluginManager().removePermission(permission);
    }

    @Override
    public void removePermission(String name)
    {
        this.getServer().getPluginManager().removePermission(name);
    }

    @Override
    public void callEvent(Event event)
    {
        this.getServer().getPluginManager().callEvent(event);
    }
}
