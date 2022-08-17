package com.mikedeejay2.mikedeejay2lib.event;

import com.mikedeejay2.mikedeejay2lib.BukkitPlugin;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;

import java.util.ArrayList;
import java.util.List;

/**
 * A class that manages the registration and referencing of Listeners for a plugin
 *
 * @author Mikedeejay2
 */
public class ListenerManager {
    /**
     * The {@link BukkitPlugin} instance
     */
    protected final BukkitPlugin plugin;

    /**
     * The List of {@link Listener} that this manager holds
     */
    private final List<Listener> listeners;

    /**
     * Construct a new <code>ListenerManager</code>
     *
     * @param plugin The {@link BukkitPlugin} instance
     */
    public ListenerManager(BukkitPlugin plugin) {
        this.plugin = plugin;
        listeners = new ArrayList<>();
    }

    /**
     * Add a <code>Listener</code> to this manager
     *
     * @param listener The listener to add
     */
    public void addListener(Listener listener) {
        listeners.add(listener);
    }

    /**
     * Register all added listeners. <p>
     *
     * This should only be called once! Add all listeners before running
     * this method.
     */
    public void registerAll() {
        PluginManager manager = plugin.getServer().getPluginManager();
        listeners.forEach(listener -> manager.registerEvents(listener, plugin));
    }

    /**
     * Get the List of {@link Listener}
     *
     * @return All Listeners that this manager holds
     */
    public List<Listener> getListeners() {
        return listeners;
    }
}