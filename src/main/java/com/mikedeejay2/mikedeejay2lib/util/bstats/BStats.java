package com.mikedeejay2.mikedeejay2lib.util.bstats;

import com.mikedeejay2.mikedeejay2lib.PluginBase;
import org.bstats.bukkit.Metrics;

/**
 * Simple encapsulation class of BStats
 *
 * @author Mikedeejay2
 */
public class BStats
{
    protected final PluginBase plugin;
    protected int id;
    protected Metrics bStats;

    public BStats(PluginBase plugin)
    {
        this.plugin = plugin;
        this.id = -1;
    }

    /**
     * Initialize BStats with an ID
     *
     * @param id The ID of the plugin
     */
    public void init(int id)
    {
        this.id = id;
        this.bStats = new Metrics(plugin, id);
    }

    /**
     * Get the ID of the plugin
     *
     * @return The BStats ID of this plugin
     */
    public int getId()
    {
        return id;
    }

    /**
     * Get the BStats metrics of this plugin
     *
     * @return BStats metrics
     */
    public Metrics getBStats()
    {
        return bStats;
    }
}
