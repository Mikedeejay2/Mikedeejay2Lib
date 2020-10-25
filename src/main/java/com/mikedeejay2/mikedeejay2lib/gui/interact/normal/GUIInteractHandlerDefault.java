package com.mikedeejay2.mikedeejay2lib.gui.interact.normal;

import com.mikedeejay2.mikedeejay2lib.gui.GUIContainer;
import com.mikedeejay2.mikedeejay2lib.gui.GUILayer;
import com.mikedeejay2.mikedeejay2lib.gui.interact.GUIInteractExecutor;
import com.mikedeejay2.mikedeejay2lib.gui.interact.GUIInteractHandler;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
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
        executors.add(new GUIInteractExecutorDefault());
    }

    /**
     * Handle an interaction between a <tt>Player</tt> and a <tt>GUIContainer</tt> to properly move an item
     * <p>
     *
     * @param event The event of the click
     * @param gui The GUI that the player interacted with
     */
    @Override
    public void handleInteraction(InventoryClickEvent event, GUIContainer gui)
    {
        GUILayer layer = gui.getLayer(0);
        Inventory inventory = event.getClickedInventory();
        int slot = event.getSlot();
        Player player = (Player) event.getWhoClicked();
        InventoryAction action = event.getAction();
        executeAction(player, inventory, slot, action, event, gui, layer);
    }
}
