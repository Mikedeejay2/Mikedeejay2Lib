package com.mikedeejay2.mikedeejay2lib.gui.modules;

import com.mikedeejay2.mikedeejay2lib.PluginBase;
import com.mikedeejay2.mikedeejay2lib.gui.GUIContainer;
import com.mikedeejay2.mikedeejay2lib.gui.animation.AnimationRuntime;
import com.mikedeejay2.mikedeejay2lib.gui.item.AnimatedGUIItem;
import com.mikedeejay2.mikedeejay2lib.gui.item.GUIItem;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

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
    // The list of Animated GUI Items to be animated
    protected List<AnimatedGUIItem> animatedItems;
    // The AnimationRuntime for this module
    protected AnimationRuntime runtime;
    // The period of time between each update
    protected long period;

    public GUIAnimationModule(PluginBase plugin, long period)
    {
        super(plugin);

        this.period = period == 0 ? 1 : period;
        this.animatedItems = new ArrayList<>();
    }

    /**
     * On open injection. This method creates an <tt>AnimationRuntime</tt> that
     * animates the GUI Items.
     *
     * @param player The player that opened the GUI
     * @param gui The GUI
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
     * Iterate through the items of the GUI and get the new list of GUI Items to be animated.
     * This occurs because there is a chance that an item has been added / removed since the
     * last update
     *
     * @param player The player that requires the GUI update
     * @param gui The GUI
     */
    @Override
    public void onUpdateTail(Player player, GUIContainer gui)
    {
        GUIItem[][] items = gui.getItemsAsArray();
        List<AnimatedGUIItem> newAnimatedItems = new ArrayList<>();
        for(GUIItem[] subArray : items)
        {
            for(GUIItem item : subArray)
            {
                if(item == null || item.getClass() != AnimatedGUIItem.class) continue;
                newAnimatedItems.add((AnimatedGUIItem) item);
            }
        }
        animatedItems.clear();
        animatedItems.addAll(newAnimatedItems);
    }

    /**
     * When the GUI is closed, stop the <tt>AnimationRuntime</tt>
     *
     * @param player The player that closed the GUI
     * @param gui The GUI
     */
    @Override
    public void onClose(Player player, GUIContainer gui)
    {
        runtime.cancel();
    }
}
