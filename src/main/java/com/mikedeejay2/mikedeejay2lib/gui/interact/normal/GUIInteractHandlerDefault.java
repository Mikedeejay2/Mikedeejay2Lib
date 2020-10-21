package com.mikedeejay2.mikedeejay2lib.gui.interact.normal;

import com.mikedeejay2.mikedeejay2lib.gui.GUIContainer;
import com.mikedeejay2.mikedeejay2lib.gui.interact.GUIInteractHandler;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.inventory.Inventory;

/**
 * Handles interactions between the player and the GUI when moving items
 *
 * @author Mikedeejay2
 */
public class GUIInteractHandlerDefault extends GUIInteractHandler
{
    public GUIInteractHandlerDefault()
    {
        super();
    }

    /**
     * Handle an interaction between a <tt>Player</tt> and a <tt>GUIContainer</tt> to properly move an item
     * <p>
     *
     * @param player The player interacting with the GUI
     * @param inventory The inventory that was clicked
     * @param slot The original slot that was interacted with
     * @param action The original <tt>InventoryAction</tt> that Minecraft suggests should happen
     * @param type The original <tt>ClickType</tt> that Minecraft suggests should happen
     * @param gui The GUI that the player interacted with
     */
    @Override
    public void handleInteraction(Player player, Inventory inventory, int slot, InventoryAction action, ClickType type, GUIContainer gui)
    {

    }
}
