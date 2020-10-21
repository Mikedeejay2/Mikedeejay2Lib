package com.mikedeejay2.mikedeejay2lib.gui.interact;

import com.mikedeejay2.mikedeejay2lib.gui.GUIContainer;
import com.mikedeejay2.mikedeejay2lib.gui.GUILayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

/**
 * An interface to be the basis of any item interaction executor.
 * This interface gives a base for all of the methods required for all of the
 * possible item movement types.
 *
 * @author Mikedeejay2
 */
public interface GUIInteractExecutor
{
    /**
     * Called when nothing should happen
     *
     * @param player The <tt>Player</tt> interacting with the GUI
     * @param inventory The <tt>Inventory</tt> that was interacted with
     * @param slot The slot that was interacted with
     * @param gui The <tt>GUIContainer</tt> that was interacted with
     * @param layer The <tt>GUILayer</tt> that items should be placed on
     */
    void executeNothing(Player player, Inventory inventory, int slot, GUIContainer gui, GUILayer layer);

    /**
     * Called when all items of a slot should be picked up into the player's cursor
     *
     * @param player The <tt>Player</tt> interacting with the GUI
     * @param inventory The <tt>Inventory</tt> that was interacted with
     * @param slot The slot that was interacted with
     * @param gui The <tt>GUIContainer</tt> that was interacted with
     * @param layer The <tt>GUILayer</tt> that items should be placed on
     */
    void executePickupAll(Player player, Inventory inventory, int slot, GUIContainer gui, GUILayer layer);

    /**
     * Called when some of the items of a slot should be picked up into the player's cursor
     *
     * @param player The <tt>Player</tt> interacting with the GUI
     * @param inventory The <tt>Inventory</tt> that was interacted with
     * @param slot The slot that was interacted with
     * @param gui The <tt>GUIContainer</tt> that was interacted with
     * @param layer The <tt>GUILayer</tt> that items should be placed on
     */
    void executePickupSome(Player player, Inventory inventory, int slot, GUIContainer gui, GUILayer layer);

    /**
     * Called when half of the items should be picked up from a slot into the player's cursor
     *
     * @param player The <tt>Player</tt> interacting with the GUI
     * @param inventory The <tt>Inventory</tt> that was interacted with
     * @param slot The slot that was interacted with
     * @param gui The <tt>GUIContainer</tt> that was interacted with
     * @param layer The <tt>GUILayer</tt> that items should be placed on
     */
    void executePickupHalf(Player player, Inventory inventory, int slot, GUIContainer gui, GUILayer layer);

    /**
     * Called when one of the items of a slot should be picked up into a player's cursor
     *
     * @param player The <tt>Player</tt> interacting with the GUI
     * @param inventory The <tt>Inventory</tt> that was interacted with
     * @param slot The slot that was interacted with
     * @param gui The <tt>GUIContainer</tt> that was interacted with
     * @param layer The <tt>GUILayer</tt> that items should be placed on
     */
    void executePickupOne(Player player, Inventory inventory, int slot, GUIContainer gui, GUILayer layer);

    /**
     * Called when everything in a player's cursor should be dropped into a slot
     *
     * @param player The <tt>Player</tt> interacting with the GUI
     * @param inventory The <tt>Inventory</tt> that was interacted with
     * @param slot The slot that was interacted with
     * @param gui The <tt>GUIContainer</tt> that was interacted with
     * @param layer The <tt>GUILayer</tt> that items should be placed on
     */
    void executePlaceAll(Player player, Inventory inventory, int slot, GUIContainer gui, GUILayer layer);

    /**
     * Called when some of the player's cursor should be dropped into a slot
     *
     * @param player The <tt>Player</tt> interacting with the GUI
     * @param inventory The <tt>Inventory</tt> that was interacted with
     * @param slot The slot that was interacted with
     * @param gui The <tt>GUIContainer</tt> that was interacted with
     * @param layer The <tt>GUILayer</tt> that items should be placed on
     */
    void executePlaceSome(Player player, Inventory inventory, int slot, GUIContainer gui, GUILayer layer);

    /**
     * Called when one of the player's cursor should be dropped into a slot
     *
     * @param player The <tt>Player</tt> interacting with the GUI
     * @param inventory The <tt>Inventory</tt> that was interacted with
     * @param slot The slot that was interacted with
     * @param gui The <tt>GUIContainer</tt> that was interacted with
     * @param layer The <tt>GUILayer</tt> that items should be placed on
     */
    void executePlaceOne(Player player, Inventory inventory, int slot, GUIContainer gui, GUILayer layer);

    /**
     * Called when the item in the GUI should be swapped with the item in the player's cursor
     *
     * @param player The <tt>Player</tt> interacting with the GUI
     * @param inventory The <tt>Inventory</tt> that was interacted with
     * @param slot The slot that was interacted with
     * @param gui The <tt>GUIContainer</tt> that was interacted with
     * @param layer The <tt>GUILayer</tt> that items should be placed on
     */
    void executeSwapWithCursor(Player player, Inventory inventory, int slot, GUIContainer gui, GUILayer layer);

    /**
     * Called when the item that the player interacted with should be dropped to the ground
     *
     * @param player The <tt>Player</tt> interacting with the GUI
     * @param inventory The <tt>Inventory</tt> that was interacted with
     * @param slot The slot that was interacted with
     * @param gui The <tt>GUIContainer</tt> that was interacted with
     * @param layer The <tt>GUILayer</tt> that items should be placed on
     */
    void executeDropAllCursor(Player player, Inventory inventory, int slot, GUIContainer gui, GUILayer layer);

    /**
     * Called when one of the item that the player interacted with should be dropped to the ground
     *
     * @param player The <tt>Player</tt> interacting with the GUI
     * @param inventory The <tt>Inventory</tt> that was interacted with
     * @param slot The slot that was interacted with
     * @param gui The <tt>GUIContainer</tt> that was interacted with
     * @param layer The <tt>GUILayer</tt> that items should be placed on
     */
    void executeDropOneCursor(Player player, Inventory inventory, int slot, GUIContainer gui, GUILayer layer);

    /**
     * Called when the item that the player interacted with should be dropped to the ground
     *
     * @param player The <tt>Player</tt> interacting with the GUI
     * @param inventory The <tt>Inventory</tt> that was interacted with
     * @param slot The slot that was interacted with
     * @param gui The <tt>GUIContainer</tt> that was interacted with
     * @param layer The <tt>GUILayer</tt> that items should be placed on
     */
    void executeDropAllSlot(Player player, Inventory inventory, int slot, GUIContainer gui, GUILayer layer);

    /**
     * Called when one of the item that the player interacted with should be dropped to ground
     *
     * @param player The <tt>Player</tt> interacting with the GUI
     * @param inventory The <tt>Inventory</tt> that was interacted with
     * @param slot The slot that was interacted with
     * @param gui The <tt>GUIContainer</tt> that was interacted with
     * @param layer The <tt>GUILayer</tt> that items should be placed on
     */
    void executeDropOneSlot(Player player, Inventory inventory, int slot, GUIContainer gui, GUILayer layer);

    /**
     * Called when the item that the player interacted with should be moved to the other inventory
     *
     * @param player The <tt>Player</tt> interacting with the GUI
     * @param inventory The <tt>Inventory</tt> that was interacted with
     * @param slot The slot that was interacted with
     * @param gui The <tt>GUIContainer</tt> that was interacted with
     * @param layer The <tt>GUILayer</tt> that items should be placed on
     */
    void executeMoveToOtherInventory(Player player, Inventory inventory, int slot, GUIContainer gui, GUILayer layer);

    /**
     * Called when an item in the hotbar is moved and readded to a different slot
     *
     * @param player The <tt>Player</tt> interacting with the GUI
     * @param inventory The <tt>Inventory</tt> that was interacted with
     * @param slot The slot that was interacted with
     * @param gui The <tt>GUIContainer</tt> that was interacted with
     * @param layer The <tt>GUILayer</tt> that items should be placed on
     */
    void executeHotbarMoveAndReadd(Player player, Inventory inventory, int slot, GUIContainer gui, GUILayer layer);

    /**
     * Called when an item should be added to the hotbar and the item that is currently there is moved into the player's inventory
     *
     * @param player The <tt>Player</tt> interacting with the GUI
     * @param inventory The <tt>Inventory</tt> that was interacted with
     * @param slot The slot that was interacted with
     * @param gui The <tt>GUIContainer</tt> that was interacted with
     * @param layer The <tt>GUILayer</tt> that items should be placed on
     */
    void executeHotbarSwap(Player player, Inventory inventory, int slot, GUIContainer gui, GUILayer layer);

    /**
     * Called when the player clones an <tt>ItemStack</tt> into their cursor
     *
     * @param player The <tt>Player</tt> interacting with the GUI
     * @param inventory The <tt>Inventory</tt> that was interacted with
     * @param slot The slot that was interacted with
     * @param gui The <tt>GUIContainer</tt> that was interacted with
     * @param layer The <tt>GUILayer</tt> that items should be placed on
     */
    void executeCloneStack(Player player, Inventory inventory, int slot, GUIContainer gui, GUILayer layer);

    /**
     * Called when the player collects up to max stack size to their cursor
     *
     * @param player The <tt>Player</tt> interacting with the GUI
     * @param inventory The <tt>Inventory</tt> that was interacted with
     * @param slot The slot that was interacted with
     * @param gui The <tt>GUIContainer</tt> that was interacted with
     * @param layer The <tt>GUILayer</tt> that items should be placed on
     */
    void executeCollectToCursor(Player player, Inventory inventory, int slot, GUIContainer gui, GUILayer layer);

    /**
     * Called when the player executes an unknown interaction type.
     *
     * @param player The <tt>Player</tt> interacting with the GUI
     * @param inventory The <tt>Inventory</tt> that was interacted with
     * @param slot The slot that was interacted with
     * @param gui The <tt>GUIContainer</tt> that was interacted with
     * @param layer The <tt>GUILayer</tt> that items should be placed on
     */
    void executeUnknown(Player player, Inventory inventory, int slot, GUIContainer gui, GUILayer layer);
}
