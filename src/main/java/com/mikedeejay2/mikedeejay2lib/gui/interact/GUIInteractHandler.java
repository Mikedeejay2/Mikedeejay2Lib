package com.mikedeejay2.mikedeejay2lib.gui.interact;

import com.mikedeejay2.mikedeejay2lib.gui.GUIContainer;
import com.mikedeejay2.mikedeejay2lib.gui.interact.normal.GUIInteractHandlerDefault;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;
import java.util.List;

/**
 * Handles interactions between the player and the GUI when moving items
 * <p>
 * For default item movement, use {@link GUIInteractHandlerDefault}
 *
 * @author Mikedeejay2
 */
public abstract class GUIInteractHandler
{
    // A list of the GUIInteractionExecutors that this handler executes
    protected List<GUIInteractExecutor> executors;

    public GUIInteractHandler()
    {
        this.executors = new ArrayList<>();
    }

    /**
     * Handle an interaction between a <tt>Player</tt> and a <tt>GUIContainer</tt> to properly move an item
     *
     * @param player The player interacting with the GUI
     * @param inventory The inventory that was clicked
     * @param slot The original slot that was interacted with
     * @param action The original <tt>InventoryAction</tt> that Minecraft suggests should happen
     * @param type The original <tt>ClickType</tt> that Minecraft suggests should happen
     * @param gui The GUI that the player interacted with
     */
    public abstract void handleInteraction(Player player, Inventory inventory, int slot, InventoryAction action, ClickType type, GUIContainer gui);

    /**
     * Add a <tt>GUIInteractExecutor</tt> that will be executed upon the handling of an interaction
     *
     * @param executor The <tt>GUIInteractExecutor</tt> to add
     */
    public void addExecutor(GUIInteractExecutor executor)
    {
        executors.add(executor);
    }

    /**
     * Returns whether this handler executes a <tt>GUIInteractExecutor</tt> of a class's type
     *
     * @param executorClass The class of the <tt>GUIInteractExecutor</tt> to search for
     * @return Whether an executor of the specified class was found or not
     */
    public boolean containsExecutor(Class<? extends GUIInteractExecutor> executorClass)
    {
        String className = executorClass.getName();
        for(GUIInteractExecutor event : executors)
        {
            Class<? extends GUIInteractExecutor> curClass = event.getClass();
            String curClassName = curClass.getName();
            if(!className.equals(curClassName)) continue;
            return true;
        }
        return false;
    }

    /**
     * Returns whether this handler executes a <tt>GUIInteractExecutor</tt> of an executor's instance
     *
     * @param executor The executor to search for
     * @return Whether an executor of the specified instance was found or not
     */
    public boolean containsExecutor(GUIInteractExecutor executor)
    {
        return executors.contains(executor);
    }

    /**
     * Remove a <tt>GUIInteractExecutor</tt> from this handler's executor list
     *
     * @param executorClass The <tt>GUIInteractExecutor</tt> class to find and remove from the list
     */
    public void removeExecutor(Class<? extends GUIInteractExecutor> executorClass)
    {
        String className = executorClass.getName();
        for(GUIInteractExecutor executor : executors)
        {
            Class<? extends GUIInteractExecutor> curClass = executor.getClass();
            String curClassName = curClass.getName();
            if(!className.equals(curClassName)) continue;
            executors.remove(executor);
            return;
        }
    }

    /**
     * Reomve a <tt>GUIInteractExecutor</tt> from this handler's executor list
     *
     * @param executor The <tt>GUIInteractExecutor</tt> object instance to find and remove from the list
     */
    public void removeExecutor(GUIInteractExecutor executor)
    {
        executors.remove(executor);
    }

    /**
     * Get the list of <tt>GUIInteractExecutors</tt> that this handler executes
     *
     * @return The list of <tt>GUIInteractExecutors</tt>
     */
    public List<GUIInteractExecutor> getExecutors()
    {
        return executors;
    }

    /**
     * Get a <tt>GUIInteractExecutor</tt> from this handlers executor list
     *
     * @param executorClass The <tt>GUIInteractExecutor</tt> class to find and get from the list
     * @return The requested <tt>GUIInteractExecutor</tt>
     */
    public GUIInteractExecutor getExecutor(Class<? extends GUIInteractExecutor> executorClass)
    {
        String className = executorClass.getName();
        for(GUIInteractExecutor executor : executors)
        {
            Class<? extends GUIInteractExecutor> curClass = executor.getClass();
            String curClassName = curClass.getName();
            if(!className.equals(curClassName)) continue;
            return executor;
        }
        return null;
    }
}
