package com.mikedeejay2.mikedeejay2lib;

import com.mikedeejay2.mikedeejay2lib.commands.CommandBase;
import com.mikedeejay2.mikedeejay2lib.commands.CommandInfo;
import com.mikedeejay2.mikedeejay2lib.commands.TabBase;
import com.mikedeejay2.mikedeejay2lib.commands.TabCommandBase;
import com.mikedeejay2.mikedeejay2lib.util.chat.Colors;
import com.mikedeejay2.mikedeejay2lib.util.logging.EnhancedPluginLogger;
import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.permissions.Permission;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginLogger;
import org.bukkit.plugin.RegisteredListener;
import org.bukkit.plugin.SimplePluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

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
    private ClassLoader classLoader;

    /**
     * Force Paper server types to allow this plugin to have a colored logger
     * prefix. This is done by replacing the logger in {@link JavaPlugin} with
     * the legacy Bukkit {@link PluginLogger}. The name of the logger must also
     * be set to an empty String in order to remove the class prefix.
     */
    private void forceColorfulLogger()
    {
        Logger logger = new EnhancedPluginLogger(this);
        try
        {
            Field logField = JavaPlugin.class.getDeclaredField("logger");
            logField.setAccessible(true);
            logField.set(this, logger);
        }
        catch(IllegalAccessException | NoSuchFieldException e)
        {
            getLogger().warning("Could not create a new plugin logger for " + this.getDescription().getName());
            e.printStackTrace();
        }
    }

    @Override
    public void onEnable()
    {
        super.onEnable();
        forceColorfulLogger();
        this.classLoader =  retrieveClassLoader();

        String prefix = this.getDescription().getPrefix();
        setPrefix(prefix != null ? "[" + prefix + "]" : "[" + this.getDescription().getName() + "]");
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
        this.prefix = Colors.format(prefix) + " ";
        EnhancedPluginLogger logger = (EnhancedPluginLogger) this.getLogger();
        logger.setPrefix(this.prefix);
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
     * @param sender  Input <code>CommandSender</code> that will receive the message
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
     * Send an info message from this plugin's logger
     *
     * @param message The message to be logged
     */
    @Override
    public void sendInfo(String message)
    {
        this.getLogger().info(Colors.format(message));
    }

    /**
     * Send a warning message from this plugin's logger
     *
     * @param message The message to be logged
     */
    @Override
    public void sendWarning(String message)
    {
        this.getLogger().warning(Colors.format(message));
    }

    /**
     * Send a severe message from this plugin's logger
     *
     * @param message The message to be logged
     */
    @Override
    public void sendSevere(String message)
    {
        this.getLogger().severe(Colors.format(message));
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
     * Register an event listener to the server at a specific index in the listeners list
     *
     * @param index The index to register the listeners in
     * @param listener The listener to register
     */
    @Override
    public void registerEvent(int index, Listener listener)
    {
        try
        {
            SimplePluginManager pluginManager = (SimplePluginManager) this.getServer().getPluginManager();
            Method getRegistrationClass = SimplePluginManager.class.getDeclaredMethod("getRegistrationClass", Class.class);
            getRegistrationClass.setAccessible(true);
            Method getEventListeners = SimplePluginManager.class.getDeclaredMethod("getEventListeners", Class.class);
            getEventListeners.setAccessible(true);
            Field handlers = HandlerList.class.getDeclaredField("handlers");
            handlers.setAccessible(true);
            Field handlerSlotsField = HandlerList.class.getDeclaredField("handlerslots");
            handlerSlotsField.setAccessible(true);

            for (Map.Entry<Class<? extends Event>, Set<RegisteredListener>> entry : this.getPluginLoader().createRegisteredListeners(listener, this).entrySet())
            {
                Class<? extends Event> registrationClass = (Class<? extends Event>) getRegistrationClass.invoke(pluginManager, entry.getKey());
                HandlerList handlerList = (HandlerList) getEventListeners.invoke(pluginManager, registrationClass);
                handlers.set(handlerList, null);
                EnumMap<EventPriority, ArrayList<RegisteredListener>> handlerSlots = (EnumMap<EventPriority, ArrayList<RegisteredListener>>) handlerSlotsField.get(handlerList);
                for(RegisteredListener registeredListener : entry.getValue())
                {
                    handlerSlots.get(registeredListener.getPriority()).add(index, registeredListener);
                }
            }
        }
        catch(NoSuchMethodException | InvocationTargetException | IllegalAccessException | NoSuchFieldException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Register multiple event listeners to the server at a specific index in the listeners list
     *
     * @param index The index to register the listeners in
     * @param listeners The variable amount of event listeners to register
     */
    @Override
    public void registerEvents(int index, Listener... listeners)
    {
        for(Listener listener : listeners)
        {
            registerEvent(index, listener);
        }
    }

    /**
     * Register an event listener to the server at the first index of the listeners list
     *
     * @param listener The listener to register
     */
    @Override
    public void registerEventFirst(Listener listener)
    {
        registerEvent(0, listener);
    }

    /**
     * Register multiple event listeners to the server at the first index of the listeners list
     *
     * @param listeners The variable amount of event listeners to register
     */
    @Override
    public void registerEventsFirst(Listener... listeners)
    {
        registerEvents(0, listeners);
    }

    /**
     * Register an event listener to the server in roughly the end of the listeners list
     *
     * @param listener The listener to register
     */
    @Override
    public void registerEventLast(Listener listener)
    {
        Bukkit.getScheduler().runTaskLater(this, () -> registerEvent(listener), 1);
    }

    /**
     * Register multiple event listeners to the server in roughly the end of the listeners list
     *
     * @param listeners The variable amount of event listeners to register
     */
    @Override
    public void registerEventsLast(Listener... listeners)
    {
        Bukkit.getScheduler().runTaskLater(this, () -> registerEventsLast(listeners), 1);
    }

    /**
     * Register a command and a tab completer to the server. The executor and
     * tab completer will be applied to the name of the command specified.
     *
     * @param name      The name of the command to register
     * @param executor  The <code>CommandExecutor</code> to register to the command
     * @param completer The <code>TabCompleter</code> to register to the command
     */
    @Override
    public void registerCommand(String name, CommandExecutor executor, TabCompleter completer)
    {
        PluginCommand cmd = this.getCommand(name);
        Validate.notNull(cmd, String.format("Command with name %s could not be found", name));
        cmd.setExecutor(executor);
        cmd.setTabCompleter(completer);
    }

    /**
     * Register a tab executor command to the server. The executor will be
     * applied to the name of the command specified.
     *
     * @param name      The name of the command to register
     * @param executor  The <code>TabExecutor</code> to register to the command
     */
    @Override
    public void registerCommand(String name, TabExecutor executor)
    {
        PluginCommand cmd = this.getCommand(name);
        Validate.notNull(cmd, String.format("Command with name %s could not be found", name));
        cmd.setExecutor(executor);
        cmd.setTabCompleter(executor);
    }

    /**
     * Register a command to the server. The executor will be applied to the name
     * of the command specified.
     *
     * @param name     The name of the command to register
     * @param executor The <code>CommandExecutor</code> to register to the command
     */
    @Override
    public void registerCommand(String name, CommandExecutor executor)
    {
        PluginCommand cmd = this.getCommand(name);
        Validate.notNull(cmd, String.format("Command with name %s could not be found", name));
        cmd.setExecutor(executor);
    }

    /**
     * Register a tab completer to the server. The tab completer will be applied to
     * the name of the command specified.
     *
     * @param name      The name of the command to register
     * @param completer The <code>TabCompleter</code> to register to the command
     */
    @Override
    public void registerTabCompleter(String name, TabCompleter completer)
    {
        PluginCommand cmd = this.getCommand(name);
        Validate.notNull(cmd, String.format("Command with name %s could not be found", name));
        cmd.setTabCompleter(completer);
    }

    /**
     * Register a {@link TabCommandBase} to the server. Information contained in
     * {@link CommandInfo} will be used to register the command.
     *
     * @param command The <code>TabCommandBase</code> to register to the command
     */
    @Override
    public void registerCommand(TabCommandBase command)
    {
        String name = command.getName();
        PluginCommand cmd = this.getCommand(name);
        Validate.notNull(cmd, String.format("Command with name %s could not be found", name));
        cmd.setExecutor(command);
        cmd.setTabCompleter(command);
    }

    /**
     * Register a {@link CommandBase} to the server. Information contained in
     * {@link CommandInfo} will be used to register the command.
     *
     * @param command The <code>CommandBase</code> to register to the command
     */
    @Override
    public void registerCommand(CommandBase command)
    {
        String name = command.getName();
        PluginCommand cmd = this.getCommand(name);
        Validate.notNull(cmd, String.format("Command with name %s could not be found", name));
        cmd.setExecutor(command);
    }

    /**
     * Register a {@link TabBase} to the server. Information contained in
     * {@link CommandInfo} will be used to register to the command.
     *
     * @param completer The <code>TabBase</code> to register to the command
     */
    @Override
    public void registerTabCompleter(TabBase completer)
    {
        String name = completer.getName();
        PluginCommand cmd = this.getCommand(name);
        Validate.notNull(cmd, String.format("Command with name %s could not be found", name));
        cmd.setTabCompleter(completer);
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

    /**
     * Method to retrieve the class loader from the original <code>JavaPlugin</code>.
     *
     * @return The retrieved class loader
     */
    private ClassLoader retrieveClassLoader()
    {
        try
        {
            Field field = JavaPlugin.class.getDeclaredField("classLoader");
            field.setAccessible(true);
            return (ClassLoader) field.get(this);
        }
        catch(NoSuchFieldException | IllegalAccessException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Get the <code>ClassLoader</code> for this plugin
     *
     * @return The <code>ClassLoader</code> for this plugin
     */
    public ClassLoader classLoader()
    {
        return classLoader;
    }
}
