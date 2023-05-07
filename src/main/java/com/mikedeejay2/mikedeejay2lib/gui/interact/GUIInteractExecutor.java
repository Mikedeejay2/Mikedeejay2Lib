package com.mikedeejay2.mikedeejay2lib.gui.interact;

import com.mikedeejay2.mikedeejay2lib.gui.event.GUIClickEvent;

/**
 * An interface to be the basis of any item interaction executor.
 * This interface gives a base for all the methods required for all the
 * possible item movement types.
 *
 * @author Mikedeejay2
 */
public interface GUIInteractExecutor {
    /**
     * Called when nothing should happen
     *
     * @param event The {@link GUIClickEvent} of the click
     */
    void executeNothing(GUIClickEvent event);

    /**
     * Called when all items of a slot should be picked up into the player's cursor
     *
     * @param event The {@link GUIClickEvent} of the click
     */
    void executePickupAll(GUIClickEvent event);

    /**
     * Called when some of the items of a slot should be picked up into the player's cursor
     *
     * @param event The {@link GUIClickEvent} of the click
     */
    void executePickupSome(GUIClickEvent event);

    /**
     * Called when half of the items should be picked up from a slot into the player's cursor
     *
     * @param event The {@link GUIClickEvent} of the click
     */
    void executePickupHalf(GUIClickEvent event);

    /**
     * Called when one of the items of a slot should be picked up into a player's cursor
     *
     * @param event The {@link GUIClickEvent} of the click
     */
    void executePickupOne(GUIClickEvent event);

    /**
     * Called when everything in a player's cursor should be dropped into a slot
     *
     * @param event The {@link GUIClickEvent} of the click
     */
    void executePlaceAll(GUIClickEvent event);

    /**
     * Called when some of the player's cursor should be dropped into a slot
     *
     * @param event The {@link GUIClickEvent} of the click
     */
    void executePlaceSome(GUIClickEvent event);

    /**
     * Called when one of the player's cursor should be dropped into a slot
     *
     * @param event The {@link GUIClickEvent} of the click
     */
    void executePlaceOne(GUIClickEvent event);

    /**
     * Called when the item in the GUI should be swapped with the item in the player's cursor
     *
     * @param event The {@link GUIClickEvent} of the click
     */
    void executeSwapWithCursor(GUIClickEvent event);

    /**
     * Called when the item that the player interacted with should be dropped to the ground
     *
     * @param event The {@link GUIClickEvent} of the click
     */
    void executeDropAllCursor(GUIClickEvent event);

    /**
     * Called when one of the item that the player interacted with should be dropped to the ground
     *
     * @param event The {@link GUIClickEvent} of the click
     */
    void executeDropOneCursor(GUIClickEvent event);

    /**
     * Called when the item that the player interacted with should be dropped to the ground
     *
     * @param event The {@link GUIClickEvent} of the click
     */
    void executeDropAllSlot(GUIClickEvent event);

    /**
     * Called when one of the item that the player interacted with should be dropped to ground
     *
     * @param event The {@link GUIClickEvent} of the click
     */
    void executeDropOneSlot(GUIClickEvent event);

    /**
     * Called when the item that the player interacted with should be moved to the other inventory
     *
     * @param event The {@link GUIClickEvent} of the click
     */
    void executeMoveToOtherInventory(GUIClickEvent event);

    /**
     * Called when an item in the hotbar is moved and re-added to a different slot
     *
     * @param event The {@link GUIClickEvent} of the click
     */
    void executeHotbarMoveAndReadd(GUIClickEvent event);

    /**
     * Called when an item should be added to the hotbar and the item that is currently there is moved into the player's inventory
     *
     * @param event The {@link GUIClickEvent} of the click
     */
    void executeHotbarSwap(GUIClickEvent event);

    /**
     * Called when the player clones an <code>ItemStack</code> into their cursor
     *
     * @param event The {@link GUIClickEvent} of the click
     */
    void executeCloneStack(GUIClickEvent event);

    /**
     * Called when the player collects up to max stack size to their cursor
     *
     * @param event The {@link GUIClickEvent} of the click
     */
    void executeCollectToCursor(GUIClickEvent event);

    /**
     * Called when the player executes an unknown interaction type.
     *
     * @param event The {@link GUIClickEvent} of the click
     */
    void executeUnknown(GUIClickEvent event);
}
