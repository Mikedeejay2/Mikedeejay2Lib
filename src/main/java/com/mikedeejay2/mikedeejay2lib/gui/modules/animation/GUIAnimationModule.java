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
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * A module that processes {@link AnimatedGUIItem}s.
 * <p>
 * This module is required for the animation of {@link AnimatedGUIItem}s.
 *
 * @see AnimatedGUIItem
 *
 * @author Mikedeejay2
 */
public class GUIAnimationModule implements GUIModule
{
    /**
     * The {@link BukkitPlugin} instance
     */
    protected final BukkitPlugin plugin;

    /**
     * The list of Animated GUI Items to be animated
     */
    protected final Map<AnimatedGUIItem, AnimatedGUIItemProperties> animatedItems;

    /**
     * The AnimationRuntime for this module
     */
    protected AnimationRuntime runtime;

    /**
     * The period of time between each update
     */
    protected long period;

    /**
     * Construct a new <code>GUIAnimationModule</code>
     *
     * @param plugin The {@link BukkitPlugin} instance
     * @param period The period of time between each update
     */
    public GUIAnimationModule(BukkitPlugin plugin, long period)
    {
        this.plugin = plugin;
        this.period = period == 0 ? 1 : period;
        this.animatedItems = new ConcurrentHashMap<>();
    }

    /**
     * On open injection. This method creates an <code>AnimationRuntime</code> that
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
     * When the GUI is closed, stop the <code>AnimationRuntime</code>
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
     * @param properties The <code>AnimatedGUIItemProperties</code> of the item
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

    /**
     * Get an existing {@link AnimatedGUIItemProperties} from an {@link AnimatedGUIItem}. Might be null if the item
     * has not been processed yet.
     *
     * @param item The item to get the properties from
     * @return The requested <code>AnimatedGUIItemProperties</code>
     */
    public AnimatedGUIItemProperties getProperties(AnimatedGUIItem item)
    {
        return animatedItems.get(item);
    }

    /**
     * {@inheritDoc}
     * <p>
     * Adds the item to the animation module if the item is an {@link AnimatedGUIItem} instance
     *
     * @param gui   The GUI
     * @param layer The <code>GUILayer</code> that the item was set on
     * @param row   The row that the item was set on
     * @param col   The column that the item was set on
     * @param item  The <code>GUIItem</code> that is being set
     */
    @Override
    public void onItemSet(GUIContainer gui, GUILayer layer, int row, int col, GUIItem item)
    {
        if(item == null) return;
        if(item instanceof AnimatedGUIItem)
        {
            addItem((AnimatedGUIItem) item, new AnimatedGUIItemProperties(0, row, col, layer));
        }
    }

    /**
     * {@inheritDoc}
     * <p>
     * Removes the item from the animation module if the item is an {@link AnimatedGUIItem} instance
     *
     * @param gui   The GUI
     * @param layer The <code>GUILayer</code> that the item was removed on
     * @param row   The row that the item was set on
     * @param col   The column that the item was set on
     * @param item  The <code>GUIItem</code> that is being removed
     */
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

