package com.mikedeejay2.mikedeejay2lib.event;

import com.mikedeejay2.mikedeejay2lib.PluginBase;
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
    protected final PluginBase plugin;
    private final ArrayList<Listener> listeners;

    public ListenerManager(PluginBase plugin)
    {
        this.plugin = plugin;
        listeners = new ArrayList<>();
    }

    /**
     * Add a <tt>Listener</tt> to this manager
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
     * Get the <tt>ArrayList</tt> of <tt>Listeners</tt>
     *
     * @return All <tt>Listeners</tt> that this manager holds
     */
    public ArrayList<Listener> getListeners()
    {
        return listeners;
    }
}
