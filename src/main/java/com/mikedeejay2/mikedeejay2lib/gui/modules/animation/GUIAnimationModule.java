package com.mikedeejay2.mikedeejay2lib.gui.modules.animation;

import com.mikedeejay2.mikedeejay2lib.BukkitPlugin;
import com.mikedeejay2.mikedeejay2lib.gui.GUIContainer;
import com.mikedeejay2.mikedeejay2lib.gui.GUILayer;
import com.mikedeejay2.mikedeejay2lib.gui.animation.AnimationRuntime;
import com.mikedeejay2.mikedeejay2lib.gui.item.AnimatedGUIItem;
import com.mikedeejay2.mikedeejay2lib.gui.item.AnimatedGUIItemProperties;
import com.mikedeejay2.mikedeejay2lib.gui.item.GUIItem;
import com.mikedeejay2.mikedeejay2lib.gui.modules.GUIModule;
import org.bukkit.entity.Player;

import java.util.*;

/**
 * A module that processes <tt>AnimatedGUIItems</tt>.
 * This module is required for the animation of <tt>AnimatedGUIItems</tt>
 *
 * @see AnimatedGUIItem
 *
 * @author Mikedeejay2
 */
public class GUIAnimationModule implements GUIModule
{
    protected final BukkitPlugin plugin;
    // The list of Animated GUI Items to be animated
    protected final Map<AnimatedGUIItem, AnimatedGUIItemProperties> animatedItems;
    // The AnimationRuntime for this module
    protected AnimationRuntime runtime;
    // The period of time between each update
    protected long period;

    public GUIAnimationModule(BukkitPlugin plugin, long period)
    {
        this.plugin = plugin;
        this.period = period == 0 ? 1 : period;
        this.animatedItems = new HashMap<>();
    }

    /**
     * On open injection. This method creates an <tt>AnimationRuntime</tt> that
     * animates the GUI Items.
     *
     * @param player The player that opened the GUI
     * @param gui    The GUI
     */
    @Override
    public void onOpenHead(Player player, GUIContainer gui)
    {
        this.runtime = new AnimationRuntime();
        runtime.setPlayer(player);
        runtime.setGUI(gui);
        runtime.setItems(animatedItems);
        runtime.runTaskTimerAsynchronously(plugin, period);
    }

    /**
     * When the GUI is closed, stop the <tt>AnimationRuntime</tt>
     *
     * @param player The player that closed the GUI
     * @param gui    The GUI
     */
    @Override
    public void onClose(Player player, GUIContainer gui)
    {
        runtime.cancel();
    }

    /**
     * Add an item to the runtime
     *
     * @param item Item to add
     * @param properties The <tt>AnimatedGUIItemProperties</tt> of the item
     */
    public void addItem(AnimatedGUIItem item, AnimatedGUIItemProperties properties)
    {
        if(animatedItems.containsKey(item) && animatedItems.get(item).equals(properties)) return;
        animatedItems.put(item, properties);
    }

    /**
     * Remove an item from the runtime
     *
     * @param item Item to remove
     */
    public void removeItem(AnimatedGUIItem item)
    {
        animatedItems.remove(item);
    }

    public AnimatedGUIItemProperties getProperties(AnimatedGUIItem item)
    {
        return animatedItems.get(item);
    }

    @Override
    public void onItemSet(GUIContainer gui, GUILayer layer, int row, int col, GUIItem item)
    {
        if(item == null) return;
        if(item instanceof AnimatedGUIItem)
        {
            addItem((AnimatedGUIItem) item, new AnimatedGUIItemProperties(0, row, col, layer));
        }
    }

    @Override
    public void onItemRemove(GUIContainer gui, GUILayer layer, int row, int col, GUIItem item)
    {
        if(item == null) return;
        if(item instanceof AnimatedGUIItem)
        {
            removeItem((AnimatedGUIItem) item);
        }
    }
}

