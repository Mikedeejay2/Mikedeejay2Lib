package com.mikedeejay2.mikedeejay2lib.gui.listeners;

import com.mikedeejay2.mikedeejay2lib.PluginBase;
import com.mikedeejay2.mikedeejay2lib.gui.GUIContainer;
import com.mikedeejay2.mikedeejay2lib.gui.manager.PlayerGUI;
import com.mikedeejay2.mikedeejay2lib.util.PluginInstancer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.*;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;

/**
 * A listener class that listens for GUI interactions with a <tt>GUIContainer</tt>
 *
 * @author Mikedeejay2
 */
public class GUIListener extends PluginInstancer<PluginBase> implements Listener
{
    public GUIListener(PluginBase plugin)
    {
        super(plugin);
    }

    /**
     * The on click event to detect when a player clicks on a GUI
     *
     * @param event The inventory click event to be processed
     */
    @EventHandler
    public void onClick(InventoryClickEvent event)
    {
        InventoryAction action = event.getAction();
        ClickType clickType = event.getClick();
        Player player = (Player) event.getWhoClicked();
        PlayerGUI playerGUI = plugin.guiManager().getPlayer(player);
        if(!playerGUI.isGuiOpened()) return;

        GUIContainer curGUI = playerGUI.getGUI();
        Inventory clickedInventory = event.getClickedInventory();
        Inventory inventory = event.getInventory();
        int slot = event.getSlot();

        if(clickedInventory != inventory)
        {
            event.setCancelled(true);
            curGUI.onPlayerInteract(player, inventory, slot, action, clickType);
            return;
        }

        if(event.getCurrentItem() == null && event.getCursor() == null) return;

        int row = curGUI.getRowFromSlot(slot);
        int col = curGUI.getColFromSlot(slot);

        event.setCancelled(true);
        if(curGUI.canSlotBeMoved(row, col))
        {
            curGUI.onPlayerInteract(player, inventory, slot, action, clickType);
        }

        curGUI.onClicked(player, row, col, curGUI.getItem(row, col), action, clickType);
    }

    /**
     * On inventory close. This listener exists to detect whether a player
     * is in a GUI or not.
     *
     * @param event The event to be processed
     */
    @EventHandler
    public void onClose(InventoryCloseEvent event)
    {
        Player player = (Player) event.getPlayer();
        PlayerGUI gui = plugin.guiManager().getPlayer(player);
        if(gui.isGuiOpened()) gui.closeGUI();
    }

    /**
     * The inventory drag event for handling the dragging of items in the GUI
     *
     * @param event The event to be processed
     */
    @EventHandler
    public void onDrag(InventoryDragEvent event)
    {
        Player player = (Player) event.getWhoClicked();
        GUIContainer curGUI = plugin.guiManager().getPlayer(player).getGUI();
        Inventory inventory = event.getInventory();
        Inventory guiInventory = curGUI.getInventory();
        if(inventory != guiInventory) return;
        event.setCancelled(true);
    }

    /**
     * Removes the <tt>Player</tt> from the <tt>GUIManager</tt> when they leave to prevent memory leaks
     *
     * @param event The event to be processed
     */
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event)
    {
        Player player = event.getPlayer();
        plugin.guiManager().removePlayer(player);
    }
}
