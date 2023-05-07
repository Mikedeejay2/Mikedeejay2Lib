package com.mikedeejay2.mikedeejay2lib.gui.interact;

import com.mikedeejay2.mikedeejay2lib.gui.GUIContainer;
import com.mikedeejay2.mikedeejay2lib.gui.event.GUIClickEvent;
import com.mikedeejay2.mikedeejay2lib.gui.interact.normal.GUIInteractHandlerDefault;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * Handles interactions between the player and the GUI when moving items
 * <p>
 * For default item movement, use {@link GUIInteractHandlerDefault}
 *
 * @author Mikedeejay2
 */
public abstract class GUIInteractHandler {
    /**
     * A list of the GUIInteractionExecutors that this handler executes
     */
    protected List<GUIInteractExecutor> executors;

    /**
     * Construct a new <code>GUIInteractHandler</code>
     */
    public GUIInteractHandler() {
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
    public void addExecutor(GUIInteractExecutor executor) {
        executors.add(executor);
    }

    /**
     * Returns whether this handler executes a {@link GUIInteractExecutor} of a class's type
     *
     * @param executorClass The class of the <code>GUIInteractExecutor</code> to search for
     * @return Whether an executor of the specified class was found or not
     */
    public boolean containsExecutor(Class<? extends GUIInteractExecutor> executorClass) {
        for(GUIInteractExecutor event : executors) {
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
    public boolean containsExecutor(GUIInteractExecutor executor) {
        return executors.contains(executor);
    }

    /**
     * Remove a {@link GUIInteractExecutor} from this handler's executor list
     *
     * @param executorClass The <code>GUIInteractExecutor</code> class to find and remove from the list
     */
    public void removeExecutor(Class<? extends GUIInteractExecutor> executorClass) {
        for(GUIInteractExecutor executor : executors) {
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
    public void removeExecutor(GUIInteractExecutor executor) {
        executors.remove(executor);
    }

    /**
     * Clear all {@link GUIInteractExecutor}s from this handler's executor list
     */
    public void resetExecutors() {
        executors.clear();
    }

    /**
     * Get the list of {@link GUIInteractExecutor}s that this handler executes
     *
     * @return The list of <code>GUIInteractExecutors</code>
     */
    public List<GUIInteractExecutor> getExecutors() {
        return executors;
    }

    /**
     * Get a {@link GUIInteractExecutor} from this handlers executor list
     *
     * @param executorClass The <code>GUIInteractExecutor</code> class to find and get from the list
     * @param <T>           The type of <code>GUIInteractExecutor</code>, specified from the given class
     * @return The requested <code>GUIInteractExecutor</code>
     */
    public <T extends GUIInteractExecutor> T getExecutor(Class<T> executorClass) {
        for(GUIInteractExecutor executor : executors) {
            if(executorClass == executor.getClass()) return executorClass.cast(executor);
        }
        return null;
    }

    /**
     * Executes a specific {@link InventoryAction} and sends the action to {@link GUIInteractExecutor}
     *
     * @param info The {@link GUIClickEvent} of the click
     */
    public void executeAction(GUIClickEvent info) {
        switch(info.getAction()) {
            case NOTHING:                   executors.forEach(e -> e.executeNothing             (info)); break;
            case PICKUP_ALL:                executors.forEach(e -> e.executePickupAll           (info)); break;
            case PICKUP_SOME:               executors.forEach(e -> e.executePickupSome          (info)); break;
            case PICKUP_HALF:               executors.forEach(e -> e.executePickupHalf          (info)); break;
            case PICKUP_ONE:                executors.forEach(e -> e.executePickupOne           (info)); break;
            case PLACE_ALL:                 executors.forEach(e -> e.executePlaceAll            (info)); break;
            case PLACE_SOME:                executors.forEach(e -> e.executePlaceSome           (info)); break;
            case PLACE_ONE:                 executors.forEach(e -> e.executePlaceOne            (info)); break;
            case SWAP_WITH_CURSOR:          executors.forEach(e -> e.executeSwapWithCursor      (info)); break;
            case DROP_ALL_CURSOR:           executors.forEach(e -> e.executeDropAllCursor       (info)); break;
            case DROP_ONE_CURSOR:           executors.forEach(e -> e.executeDropOneCursor       (info)); break;
            case DROP_ALL_SLOT:             executors.forEach(e -> e.executeDropAllSlot         (info)); break;
            case DROP_ONE_SLOT:             executors.forEach(e -> e.executeDropOneSlot         (info)); break;
            case MOVE_TO_OTHER_INVENTORY:   executors.forEach(e -> e.executeMoveToOtherInventory(info)); break;
            case HOTBAR_MOVE_AND_READD:     executors.forEach(e -> e.executeHotbarMoveAndReadd  (info)); break;
            case HOTBAR_SWAP:               executors.forEach(e -> e.executeHotbarSwap          (info)); break;
            case CLONE_STACK:               executors.forEach(e -> e.executeCloneStack          (info)); break;
            case COLLECT_TO_CURSOR:         executors.forEach(e -> e.executeCollectToCursor     (info)); break;
            default:                        executors.forEach(e -> e.executeUnknown             (info)); break;
        }
    }
}
