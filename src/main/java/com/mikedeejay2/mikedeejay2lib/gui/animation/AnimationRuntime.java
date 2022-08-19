package com.mikedeejay2.mikedeejay2lib.gui.animation;

import com.mikedeejay2.mikedeejay2lib.gui.GUIContainer;
import com.mikedeejay2.mikedeejay2lib.gui.item.AnimatedGUIItem;
import com.mikedeejay2.mikedeejay2lib.gui.item.AnimatedGUIItemProperties;
import com.mikedeejay2.mikedeejay2lib.runnable.EnhancedRunnable;
import org.bukkit.entity.Player;

import java.util.*;

/**
 * Runtime that times and executed <code>AnimatedGUIItems</code>
 *
 * @author Mikedeejay2
 */
public class AnimationRuntime extends EnhancedRunnable {
    /**
     * The map of items to be executed
     */
    protected Map<AnimatedGUIItem, AnimatedGUIItemProperties> items;
    /**
     * The GUIContainer that this AnimationRuntime is a child of
     */
    protected GUIContainer gui;
    /**
     * The player that has opened the GUI
     */
    protected Player player;

    /**
     * Set the items of the AnimationRuntime
     * <p>
     * <b>This method MUST be run before the runtime is started, a <code>NullPointerException</code>
     * will be thrown otherwise.</b>
     *
     * @param items The list of AnimatedGUIItems that this runtime will iterate through
     */
    public void setItems(Map<AnimatedGUIItem, AnimatedGUIItemProperties> items) {
        this.items = items;
    }

    /**
     * Set the player that has opened the GUI
     * <p>
     * <b>This method MUST be run before the runtime is started, a <code>NullPointerException</code>
     * will be thrown otherwise.</b>
     *
     * @param player The player that opened up the GUI
     */
    public void setPlayer(Player player) {
        this.player = player;
    }

    /**
     * Set the {@link GUIContainer} that this <code>AnimationRuntime</code> is a child of
     * <p>
     * <b>This method MUST be run before the runtime is started, a <code>NullPointerException</code>
     * will be thrown otherwise.</b>
     *
     * @param gui The player that opened up the GUI
     */
    public void setGUI(GUIContainer gui) {
        this.gui = gui;
    }

    /**
     * Overridden {@link EnhancedRunnable#onRun()} method. This method iterates through all items in the items list and
     * runs their {@link AnimatedGUIItem#tick(long, AnimatedGUIItemProperties)} method, if the tick method returns true
     * the GUI is updated at the end of the run.
     */
    @Override
    public void onRun() {
        boolean shouldUpdate = false;

//        Set<Map.Entry<AnimatedGUIItem, AnimatedGUIItemProperties>> copy = new HashSet<>(items.entrySet());
        Map.Entry<AnimatedGUIItem, AnimatedGUIItemProperties>[] copy = items.entrySet()
            .toArray((Map.Entry<AnimatedGUIItem, AnimatedGUIItemProperties>[]) new Map.Entry[0]);

        for(Map.Entry<AnimatedGUIItem, AnimatedGUIItemProperties> entry : copy) {
            AnimatedGUIItem item = entry.getKey();
            AnimatedGUIItemProperties properties = entry.getValue();
            if(item.tick(period, properties)) shouldUpdate = true;
        }
        if(shouldUpdate) gui.update(player);
    }

    /**
     * Get the map {@link AnimatedGUIItem} that this runtime holds
     *
     * @return The map of items
     */
    public Map<AnimatedGUIItem, AnimatedGUIItemProperties> getItems() {
        return items;
    }

    /**
     * Get the GUI that this runtime holds
     *
     * @return This runtimes GUI reference
     */
    public GUIContainer getGui() {
        return gui;
    }

    /**
     * Get the player that this runtime holds
     *
     * @return This runtimes player reference.
     */
    public Player getPlayer() {
        return player;
    }
}
