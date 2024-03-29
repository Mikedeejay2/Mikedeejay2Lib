package com.mikedeejay2.mikedeejay2lib.util.bstats;

import com.mikedeejay2.mikedeejay2lib.BukkitPlugin;
import org.bstats.bukkit.Metrics;

/**
 * Simple encapsulation class of BStats
 *
 * @author Mikedeejay2
 */
public class BStats {
    /**
     * The {@link BukkitPlugin} instance
     */
    protected final BukkitPlugin plugin;

    /**
     * The BStats ID
     */
    protected int id;

    /**
     * The BStats {@link Metrics} object
     */
    protected Metrics bStats;

    /**
     * Construct a new <code>BStats</code>
     *
     * @param plugin The {@link BukkitPlugin} instance
     */
    public BStats(BukkitPlugin plugin) {
        this.plugin = plugin;
        this.id = -1;
    }

    /**
     * Initialize BStats with an ID
     *
     * @param id The ID of the plugin
     */
    public void init(int id) {
        this.id = id;
        this.bStats = new Metrics(plugin, id);
    }

    /**
     * Get the ID of the plugin
     *
     * @return The BStats ID of this plugin
     */
    public int getId() {
        return id;
    }

    /**
     * Get the BStats metrics of this plugin
     *
     * @return BStats metrics
     */
    public Metrics getBStats() {
        return bStats;
    }
}
