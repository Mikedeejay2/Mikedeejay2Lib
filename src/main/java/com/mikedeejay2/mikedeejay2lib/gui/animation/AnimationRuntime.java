package com.mikedeejay2.mikedeejay2lib.gui.animation;

import com.mikedeejay2.mikedeejay2lib.gui.GUIContainer;
import com.mikedeejay2.mikedeejay2lib.gui.item.AnimatedGUIItem;
import com.mikedeejay2.mikedeejay2lib.runnable.EnhancedRunnable;
import com.mikedeejay2.mikedeejay2lib.util.debug.DebugTimer;
import org.bukkit.entity.Player;

import java.util.List;

/**
 * Runtime that times and executed <tt>AnimatedGUIItems</tt>
 *
 * @author Mikedeejay2
 */
public class AnimationRuntime extends EnhancedRunnable
{
    // The list of items to be executed
    protected List<AnimatedGUIItem> items;
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
    public void setItems(List<AnimatedGUIItem> items)
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
        boolean shouldUpdate = false;
        for(AnimatedGUIItem item : items)
        {
            if(item.tick(period)) shouldUpdate = true;
        }
        if(shouldUpdate) gui.update(player);
    }

    /**
     * Get the list <tt>AnimatedGUIItems</tt> that this runtime holds
     *
     * @return The list of items
     */
    public List<AnimatedGUIItem> getItems()
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
