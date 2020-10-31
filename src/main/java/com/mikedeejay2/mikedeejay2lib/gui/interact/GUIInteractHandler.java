package com.mikedeejay2.mikedeejay2lib.gui.interact;

import com.mikedeejay2.mikedeejay2lib.gui.GUIContainer;
import com.mikedeejay2.mikedeejay2lib.gui.GUILayer;
import com.mikedeejay2.mikedeejay2lib.gui.interact.normal.GUIInteractHandlerDefault;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
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
     * Handle an interaction between a <tt>Player</tt> and a <tt>GUIContainer</tt> to properly move an item
     *
     * @param event The event of the interaction
     * @param gui The GUI that the player interacted with
     */
    public abstract void handleInteraction(InventoryClickEvent event, GUIContainer gui);

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
     * Remove a <tt>GUIInteractExecutor</tt> from this handler's executor list
     *
     * @param executor The <tt>GUIInteractExecutor</tt> object instance to find and remove from the list
     */
    public void removeExecutor(GUIInteractExecutor executor)
    {
        executors.remove(executor);
    }

    /**
     * Clear all <tt>GUIInteractExecutors</tt> from this handler's executor list
     */
    public void resetExecutors()
    {
        executors.clear();
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
