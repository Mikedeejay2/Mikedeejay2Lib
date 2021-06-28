package com.mikedeejay2.mikedeejay2lib.event;

import com.mikedeejay2.mikedeejay2lib.BukkitPlugin;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;

import java.util.ArrayList;

/**
 * A class that manages the registration and referencing of Listeners for a plugin
 *
 * @author Mikedeejay2
 */
public class ListenerManager
{
    protected final BukkitPlugin plugin;
    private final ArrayList<Listener> listeners;

    public ListenerManager(BukkitPlugin plugin)
    {
        this.plugin = plugin;
        listeners = new ArrayList<>();
    }

    /**
     * Add a <code>Listener</code> to this manager
     *
     * @param listener The listener to add
     */
    public void addListener(Listener listener)
    {
        listeners.add(listener);
    }

    /**
     * Register all added listeners. <p>
     *
     * This should only be called once! Add all listeners before running
     * this method.
     */
    public void registerAll()
    {
        PluginManager manager = plugin.getServer().getPluginManager();
        listeners.forEach(listener -> manager.registerEvents(listener, plugin));
    }

    /**
     * Get the <code>ArrayList</code> of <code>Listeners</code>
     *
     * @return All <code>Listeners</code> that this manager holds
     */
    public ArrayList<Listener> getListeners()
    {
        return listeners;
    }
}