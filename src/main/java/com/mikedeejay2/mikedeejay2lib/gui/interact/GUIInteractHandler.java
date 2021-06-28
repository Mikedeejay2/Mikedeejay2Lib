package com.mikedeejay2.mikedeejay2lib.gui.interact;

import com.mikedeejay2.mikedeejay2lib.gui.GUIContainer;
import com.mikedeejay2.mikedeejay2lib.gui.GUILayer;
import com.mikedeejay2.mikedeejay2lib.gui.interact.normal.GUIInteractHandlerDefault;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
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
     * Handle an interaction between a <code>Player</code> and a <code>GUIContainer</code> to properly move an item
     *
     * @param event The event of the interaction
     * @param gui   The GUI that the player interacted with
     */
    public abstract void handleInteraction(InventoryClickEvent event, GUIContainer gui);

    /**
     * Add a <code>GUIInteractExecutor</code> that will be executed upon the handling of an interaction
     *
     * @param executor The <code>GUIInteractExecutor</code> to add
     */
    public void addExecutor(GUIInteractExecutor executor)
    {
        executors.add(executor);
    }

    /**
     * Returns whether this handler executes a <code>GUIInteractExecutor</code> of a class's type
     *
     * @param executorClass The class of the <code>GUIInteractExecutor</code> to search for
     * @return Whether an executor of the specified class was found or not
     */
    public boolean containsExecutor(Class<? extends GUIInteractExecutor> executorClass)
    {
        for(GUIInteractExecutor event : executors)
        {
            if(executorClass == event.getClass()) return true;
        }
        return false;
    }

    /**
     * Returns whether this handler executes a <code>GUIInteractExecutor</code> of an executor's instance
     *
     * @param executor The executor to search for
     * @return Whether an executor of the specified instance was found or not
     */
    public boolean containsExecutor(GUIInteractExecutor executor)
    {
        return executors.contains(executor);
    }

    /**
     * Remove a <code>GUIInteractExecutor</code> from this handler's executor list
     *
     * @param executorClass The <code>GUIInteractExecutor</code> class to find and remove from the list
     */
    public void removeExecutor(Class<? extends GUIInteractExecutor> executorClass)
    {
        for(GUIInteractExecutor executor : executors)
        {
            if(executorClass != executor.getClass()) continue;
            executors.remove(executor);
            return;
        }
    }

    /**
     * Remove a <code>GUIInteractExecutor</code> from this handler's executor list
     *
     * @param executor The <code>GUIInteractExecutor</code> object instance to find and remove from the list
     */
    public void removeExecutor(GUIInteractExecutor executor)
    {
        executors.remove(executor);
    }

    /**
     * Clear all <code>GUIInteractExecutors</code> from this handler's executor list
     */
    public void resetExecutors()
    {
        executors.clear();
    }

    /**
     * Get the list of <code>GUIInteractExecutors</code> that this handler executes
     *
     * @return The list of <code>GUIInteractExecutors</code>
     */
    public List<GUIInteractExecutor> getExecutors()
    {
        return executors;
    }

    /**
     * Get a <code>GUIInteractExecutor</code> from this handlers executor list
     *
     * @param executorClass The <code>GUIInteractExecutor</code> class to find and get from the list
     * @param <T> The type of <code>GUIInteractExecutor</code>, specified from the given class
     * @return The requested <code>GUIInteractExecutor</code>
     */
    public <T extends GUIInteractExecutor> T getExecutor(Class<T> executorClass)
    {
        for(GUIInteractExecutor executor : executors)
        {
            if(executorClass == executor.getClass()) return (T)executor;
        }
        return null;
    }

    /**
     * Executes a specific <code>InventoryAction</code> and sends the action to <code>GUIInteractExecutor</code>
     *
     * @param player The <code>Player</code> performing the action
     * @param inventory The <code>Inventory</code> that the action is being performed in
     * @param slot The slot that the action is being performed on
     * @param action The <code>InventoryAction</code> that is being executed
     * @param event The original <code>InventoryClickEvent</code>
     * @param gui The GUI that the action is being performed in
     * @param layer The <code>GUILayer</code> that the action should be performed on
     */
    public void executeAction(Player player, Inventory inventory, int slot, InventoryAction action, InventoryClickEvent event, GUIContainer gui, GUILayer layer)
    {
        switch(action)
        {
            case NOTHING:                   for(GUIInteractExecutor e : executors) e.executeNothing(player, inventory, slot, event, gui, layer);               break;
            case PICKUP_ALL:                for(GUIInteractExecutor e : executors) e.executePickupAll(player, inventory, slot, event, gui, layer);             break;
            case PICKUP_SOME:               for(GUIInteractExecutor e : executors) e.executePickupSome(player, inventory, slot, event, gui, layer);            break;
            case PICKUP_HALF:               for(GUIInteractExecutor e : executors) e.executePickupHalf(player, inventory, slot, event, gui, layer);            break;
            case PICKUP_ONE:                for(GUIInteractExecutor e : executors) e.executePickupOne(player, inventory, slot, event, gui, layer);             break;
            case PLACE_ALL:                 for(GUIInteractExecutor e : executors) e.executePlaceAll(player, inventory, slot, event, gui, layer);              break;
            case PLACE_SOME:                for(GUIInteractExecutor e : executors) e.executePlaceSome(player, inventory, slot, event, gui, layer);             break;
            case PLACE_ONE:                 for(GUIInteractExecutor e : executors) e.executePlaceOne(player, inventory, slot, event, gui, layer);              break;
            case SWAP_WITH_CURSOR:          for(GUIInteractExecutor e : executors) e.executeSwapWithCursor(player, inventory, slot, event, gui, layer);        break;
            case DROP_ALL_CURSOR:           for(GUIInteractExecutor e : executors) e.executeDropAllCursor(player, inventory, slot, event, gui, layer);         break;
            case DROP_ONE_CURSOR:           for(GUIInteractExecutor e : executors) e.executeDropOneCursor(player, inventory, slot, event, gui, layer);         break;
            case DROP_ALL_SLOT:             for(GUIInteractExecutor e : executors) e.executeDropAllSlot(player, inventory, slot, event, gui, layer);           break;
            case DROP_ONE_SLOT:             for(GUIInteractExecutor e : executors) e.executeDropOneSlot(player, inventory, slot, event, gui, layer);           break;
            case MOVE_TO_OTHER_INVENTORY:   for(GUIInteractExecutor e : executors) e.executeMoveToOtherInventory(player, inventory, slot, event, gui, layer);  break;
            case HOTBAR_MOVE_AND_READD:     for(GUIInteractExecutor e : executors) e.executeHotbarMoveAndReadd(player, inventory, slot, event, gui, layer);    break;
            case HOTBAR_SWAP:               for(GUIInteractExecutor e : executors) e.executeHotbarSwap(player, inventory, slot, event, gui, layer);            break;
            case CLONE_STACK:               for(GUIInteractExecutor e : executors) e.executeCloneStack(player, inventory, slot, event, gui, layer);            break;
            case COLLECT_TO_CURSOR:         for(GUIInteractExecutor e : executors) e.executeCollectToCursor(player, inventory, slot, event, gui, layer);       break;
            default: for(GUIInteractExecutor e : executors) e.executeUnknown(player, inventory, slot, event, gui, layer); break;
        }
    }
}
