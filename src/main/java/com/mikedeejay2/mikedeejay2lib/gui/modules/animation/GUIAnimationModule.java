package com.mikedeejay2.mikedeejay2lib.gui.modules.animation;

import com.mikedeejay2.mikedeejay2lib.PluginBase;
import com.mikedeejay2.mikedeejay2lib.gui.GUIContainer;
import com.mikedeejay2.mikedeejay2lib.gui.animation.AnimationRuntime;
import com.mikedeejay2.mikedeejay2lib.gui.item.AnimatedGUIItem;
import com.mikedeejay2.mikedeejay2lib.gui.item.AnimatedGUIItemProperties;
import com.mikedeejay2.mikedeejay2lib.gui.modules.GUIModule;
import org.bukkit.entity.Player;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * A module that processes <tt>AnimatedGUIItems</tt>.
 * This module is required for the animation of <tt>AnimatedGUIItems</tt>
 *
 * @see AnimatedGUIItem
 *
 * @author Mikedeejay2
 */
public class GUIAnimationModule extends GUIModule
{
    protected final PluginBase plugin;
    // The list of Animated GUI Items to be animated
    protected List<Map.Entry<AnimatedGUIItem, AnimatedGUIItemProperties>> animatedItems;
    // The AnimationRuntime for this module
    protected AnimationRuntime runtime;
    // The period of time between each update
    protected long period;

    public GUIAnimationModule(PluginBase plugin, long period)
    {
        this.plugin = plugin;
        this.period = period == 0 ? 1 : period;
        this.animatedItems = new ArrayList<>();
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
     */
    public void addItem(AnimatedGUIItem item, AnimatedGUIItemProperties properties)
    {
        Map.Entry<AnimatedGUIItem, AnimatedGUIItemProperties> entry = new AbstractMap.SimpleEntry<>(item, properties);
        if(animatedItems.contains(entry)) return;
        animatedItems.add(entry);
    }

    /**
     * Remove an item from the runtime
     *
     * @param item Item to remove
     */
    public void removeItem(AnimatedGUIItem item)
    {
        for(int i = 0; i < animatedItems.size(); ++i)
        {
            Map.Entry<AnimatedGUIItem, AnimatedGUIItemProperties> entry = animatedItems.get(i);
            if(!item.equals(entry.getKey())) continue;
            animatedItems.remove(i);
            return;
        }
    }

    public AnimatedGUIItemProperties getProperties(AnimatedGUIItem item)
    {
        for(Map.Entry<AnimatedGUIItem, AnimatedGUIItemProperties> entry : animatedItems)
        {
            AnimatedGUIItem curItem = entry.getKey();
            if(item != curItem) continue;
            return entry.getValue();
        }
        return null;
    }
}

