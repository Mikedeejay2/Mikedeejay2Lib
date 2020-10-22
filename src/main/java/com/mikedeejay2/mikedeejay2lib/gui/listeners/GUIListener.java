package com.mikedeejay2.mikedeejay2lib.gui.listeners;

import com.mikedeejay2.mikedeejay2lib.PluginBase;
import com.mikedeejay2.mikedeejay2lib.gui.GUIContainer;
import com.mikedeejay2.mikedeejay2lib.gui.manager.GUIManager;
import com.mikedeejay2.mikedeejay2lib.gui.manager.PlayerGUI;
import com.mikedeejay2.mikedeejay2lib.util.debug.DebugTimer;
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
public class GUIListener implements Listener
{
    protected final PluginBase plugin;

    public GUIListener(PluginBase plugin)
    {
        this.plugin = plugin;
    }

    /**
     * The on click event to detect when a player clicks on a GUI
     *
     * @param event The inventory click event to be processed
     */
    @EventHandler
    public void onClick(InventoryClickEvent event)
    {
//        DebugTimer timer = new DebugTimer("onClick");
        InventoryAction action = event.getAction();
        ClickType clickType = event.getClick();
        Player player = (Player) event.getWhoClicked();
        PlayerGUI playerGUI = plugin.guiManager().getPlayer(player);
        if(!playerGUI.isGuiOpened())
        {
//            timer.printReport();
            return;
        }

        GUIContainer curGUI = playerGUI.getGUI();
        Inventory clickedInventory = event.getClickedInventory();
        Inventory inventory = event.getInventory();
        int slot = event.getSlot();
//        timer.addPrintPoint("Initialize variables");

        if(clickedInventory != inventory)
        {
            event.setCancelled(true);
            curGUI.onPlayerInteract(player, clickedInventory, slot, action, clickType);
            curGUI.update(player);
//            timer.printReport();
            return;
        }

        int row = curGUI.getRowFromSlot(slot);
        int col = curGUI.getColFromSlot(slot);

//        timer.addPrintPoint("Check validity");

        event.setCancelled(true);
        if(curGUI.canSlotBeMoved(row, col))
        {
            curGUI.onPlayerInteract(player, clickedInventory, slot, action, clickType);
        }
//        timer.addPrintPoint("Slot Move");
        curGUI.onClicked(player, row, col, curGUI.getItem(row, col), action, clickType);
//        timer.addPrintPoint("Clicked");
        curGUI.update(player);
//        timer.addPrintPoint("Update");
//        timer.printReport();
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
        GUIManager manager = plugin.guiManager();
        if(!manager.containsPlayer(player)) return;
        PlayerGUI playerGUI = manager.getPlayer(player);
        Inventory guiInv = playerGUI.getGUI().getInventory();
        Inventory playerInv = event.getInventory();
        if(guiInv != playerInv) return;
        playerGUI.closeGUI();
    }

    /**
     * On inventory open. This listener exists to detect whether a player
     * is in a GUI or not.
     *
     * @param event The event to be processed
     */
    @EventHandler
    public void onOpen(InventoryOpenEvent event)
    {
        Player player = (Player) event.getPlayer();
        GUIManager manager = plugin.guiManager();
        if(!manager.containsPlayer(player)) return;
        PlayerGUI playerGUI = manager.getPlayer(player);
        Inventory guiInv = playerGUI.getGUI().getInventory();
        Inventory playerInv = event.getInventory();
        if(guiInv != playerInv) return;
        playerGUI.setGUIState(true);
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
