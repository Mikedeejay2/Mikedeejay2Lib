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
    /**
     * A list of the GUIInteractionExecutors that this handler executes
     */
    protected List<GUIInteractExecutor> executors;

    /**
     * Construct a new <code>GUIInteractHandler</code>
     */
    public GUIInteractHandler()
    {
        this.executors = new ArrayList<>();
    }

    /**
     * Handle an interaction between a {@link Player} and a {@link GUIContainer} to properly move an item
     *
     * @param event The event of the interaction
     * @param gui   The GUI that the player interacted with
     */
    public abstract void handleInteraction(InventoryClickEvent event, GUIContainer gui);

    /**
     * Add a {@link GUIInteractExecutor} that will be executed upon the handling of an interaction
     *
     * @param executor The <code>GUIInteractExecutor</code> to add
     */
    public void addExecutor(GUIInteractExecutor executor)
    {
        executors.add(executor);
    }

    /**
     * Returns whether this handler executes a {@link GUIInteractExecutor} of a class's type
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
     * Returns whether this handler executes a {@link GUIInteractExecutor} of an executor's instance
     *
     * @param executor The executor to search for
     * @return Whether an executor of the specified instance was found or not
     */
    public boolean containsExecutor(GUIInteractExecutor executor)
    {
        return executors.contains(executor);
    }

    /**
     * Remove a {@link GUIInteractExecutor} from this handler's executor list
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
     * Remove a {@link GUIInteractExecutor} from this handler's executor list
     *
     * @param executor The <code>GUIInteractExecutor</code> object instance to find and remove from the list
     */
    public void removeExecutor(GUIInteractExecutor executor)
    {
        executors.remove(executor);
    }

    /**
     * Clear all {@link GUIInteractExecutor}s from this handler's executor list
     */
    public void resetExecutors()
    {
        executors.clear();
    }

    /**
     * Get the list of {@link GUIInteractExecutor}s that this handler executes
     *
     * @return The list of <code>GUIInteractExecutors</code>
     */
    public List<GUIInteractExecutor> getExecutors()
    {
        return executors;
    }

    /**
     * Get a {@link GUIInteractExecutor} from this handlers executor list
     *
     * @param executorClass The <code>GUIInteractExecutor</code> class to find and get from the list
     * @param <T>           The type of <code>GUIInteractExecutor</code>, specified from the given class
     * @return The requested <code>GUIInteractExecutor</code>
     */
    public <T extends GUIInteractExecutor> T getExecutor(Class<T> executorClass)
    {
        for(GUIInteractExecutor executor : executors)
        {
            if(executorClass == executor.getClass()) return executorClass.cast(executor);
        }
        return null;
    }

    /**
     * Executes a specific {@link InventoryAction} and sends the action to {@link GUIInteractExecutor}
     *
     * @param player    The <code>Player</code> performing the action
     * @param inventory The <code>Inventory</code> that the action is being performed in
     * @param slot      The slot that the action is being performed on
     * @param action    The <code>InventoryAction</code> that is being executed
     * @param event     The original <code>InventoryClickEvent</code>
     * @param gui       The GUI that the action is being performed in
     * @param layer     The <code>GUILayer</code> that the action should be performed on
     */
    public void executeAction(Player player, Inventory inventory, int slot, InventoryAction action, InventoryClickEvent event, GUIContainer gui, GUILayer layer)
    {
        switch(action)
        {
            case NOTHING:                   executors.forEach(e -> e.executeNothing             (player, inventory, slot, event, gui, layer)); break;
            case PICKUP_ALL:                executors.forEach(e -> e.executePickupAll           (player, inventory, slot, event, gui, layer)); break;
            case PICKUP_SOME:               executors.forEach(e -> e.executePickupSome          (player, inventory, slot, event, gui, layer)); break;
            case PICKUP_HALF:               executors.forEach(e -> e.executePickupHalf          (player, inventory, slot, event, gui, layer)); break;
            case PICKUP_ONE:                executors.forEach(e -> e.executePickupOne           (player, inventory, slot, event, gui, layer)); break;
            case PLACE_ALL:                 executors.forEach(e -> e.executePlaceAll            (player, inventory, slot, event, gui, layer)); break;
            case PLACE_SOME:                executors.forEach(e -> e.executePlaceSome           (player, inventory, slot, event, gui, layer)); break;
            case PLACE_ONE:                 executors.forEach(e -> e.executePlaceOne            (player, inventory, slot, event, gui, layer)); break;
            case SWAP_WITH_CURSOR:          executors.forEach(e -> e.executeSwapWithCursor      (player, inventory, slot, event, gui, layer)); break;
            case DROP_ALL_CURSOR:           executors.forEach(e -> e.executeDropAllCursor       (player, inventory, slot, event, gui, layer)); break;
            case DROP_ONE_CURSOR:           executors.forEach(e -> e.executeDropOneCursor       (player, inventory, slot, event, gui, layer)); break;
            case DROP_ALL_SLOT:             executors.forEach(e -> e.executeDropAllSlot         (player, inventory, slot, event, gui, layer)); break;
            case DROP_ONE_SLOT:             executors.forEach(e -> e.executeDropOneSlot         (player, inventory, slot, event, gui, layer)); break;
            case MOVE_TO_OTHER_INVENTORY:   executors.forEach(e -> e.executeMoveToOtherInventory(player, inventory, slot, event, gui, layer)); break;
            case HOTBAR_MOVE_AND_READD:     executors.forEach(e -> e.executeHotbarMoveAndReadd  (player, inventory, slot, event, gui, layer)); break;
            case HOTBAR_SWAP:               executors.forEach(e -> e.executeHotbarSwap          (player, inventory, slot, event, gui, layer)); break;
            case CLONE_STACK:               executors.forEach(e -> e.executeCloneStack          (player, inventory, slot, event, gui, layer)); break;
            case COLLECT_TO_CURSOR:         executors.forEach(e -> e.executeCollectToCursor     (player, inventory, slot, event, gui, layer)); break;
            default:                        executors.forEach(e -> e.executeUnknown             (player, inventory, slot, event, gui, layer)); break;
        }
    }
}
