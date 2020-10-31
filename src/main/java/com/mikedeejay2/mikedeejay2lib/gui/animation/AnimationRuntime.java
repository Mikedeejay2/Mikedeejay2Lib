package com.mikedeejay2.mikedeejay2lib.gui.animation;

import com.mikedeejay2.mikedeejay2lib.gui.GUIContainer;
import com.mikedeejay2.mikedeejay2lib.gui.item.AnimatedGUIItem;
import com.mikedeejay2.mikedeejay2lib.gui.item.AnimatedGUIItemProperties;
import com.mikedeejay2.mikedeejay2lib.runnable.EnhancedRunnable;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Map;

/**
 * Runtime that times and executed <tt>AnimatedGUIItems</tt>
 *
 * @author Mikedeejay2
 */
public class AnimationRuntime extends EnhancedRunnable
{
    // The list of items to be executed
    protected List<Map.Entry<AnimatedGUIItem, AnimatedGUIItemProperties>> items;
    // The GUIContainer that this AnimationRuntime is a child of
    protected GUIContainer gui;
    // The player that has opened the GUI
    protected Player player;

    /**
     * Set the items of the AnimationRuntime
     * <p>
     * <b>This method MUST be run before the runtime is started, a <tt>NullPointerException</tt>
     * will be thrown otherwise.</b>
     *
     * @param items The list of AnimatedGUIItems that this runtime will iterate through
     */
    public void setItems(List<Map.Entry<AnimatedGUIItem, AnimatedGUIItemProperties>> items)
    {
        this.items = items;
    }

    /**
     * Set the player that has opened the GUI
     * <p>
     * <b>This method MUST be run before the runtime is started, a <tt>NullPointerException</tt>
     * will be thrown otherwise.</b>
     *
     * @param player The player that opened up the GUI
     */
    public void setPlayer(Player player)
    {
        this.player = player;
    }

    /**
     * Set the <tt>GUIContainer</tt> that this <tt>AnimationRuntime</tt> is a child of
     * <p>
     * <b>This method MUST be run before the runtime is started, a <tt>NullPointerException</tt>
     * will be thrown otherwise.</b>
     *
     * @param gui The player that opened up the GUI
     */
    public void setGUI(GUIContainer gui)
    {
        this.gui = gui;
    }

    /**
     * Overridden onRun() method. This method iterates through all items in the items list and runs their
     * tick() method, if the tick method returns true the GUI is updated at the end of the run.
     */
    @Override
    public void onRun()
    {
        System.out.println("Tick " + System.currentTimeMillis() + ", Items size is " + items.size());
        boolean shouldUpdate = false;
        for(int i = 0 ; i < items.size(); ++i)
        {
            Map.Entry<AnimatedGUIItem, AnimatedGUIItemProperties> entry = items.get(i);
            AnimatedGUIItem item = entry.getKey();
            AnimatedGUIItemProperties properties = entry.getValue();
            if(item.tick(period, properties)) shouldUpdate = true;
        }
        if(shouldUpdate) gui.update(player);
    }

    /**
     * Get the list <tt>AnimatedGUIItems</tt> that this runtime holds
     *
     * @return The list of items
     */
    public List<Map.Entry<AnimatedGUIItem, AnimatedGUIItemProperties>> getItems()
    {
        return items;
    }

    /**
     * Get the GUI that this runtime holds
     *
     * @return This runtimes GUI reference
     */
    public GUIContainer getGui()
    {
        return gui;
    }

    /**
     * Get the player that this runtime holds
     *
     * @return This runtimes player reference.
     */
    public Player getPlayer()
    {
        return player;
    }
}
