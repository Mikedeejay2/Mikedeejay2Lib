package com.mikedeejay2.mikedeejay2lib.gui.listeners;

import com.mikedeejay2.mikedeejay2lib.PluginBase;
import com.mikedeejay2.mikedeejay2lib.gui.GUIContainer;
import com.mikedeejay2.mikedeejay2lib.gui.manager.GUIManager;
import com.mikedeejay2.mikedeejay2lib.gui.manager.PlayerGUI;
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
        Player player = (Player) event.getWhoClicked();
        GUIManager guiManager = plugin.guiManager();
        if(!guiManager.containsPlayer(player)) return;
        PlayerGUI playerGUI = plugin.guiManager().getPlayer(player);
        if(!playerGUI.isGuiOpened()) return;

        GUIContainer curGUI = playerGUI.getGUI();
        Inventory clickedInventory = event.getClickedInventory();
        Inventory inventory = event.getInventory();
        int slot = event.getSlot();

        if(clickedInventory != inventory)
        {
            event.setCancelled(true);
            curGUI.onPlayerInteract(event);
            curGUI.update(player);
            return;
        }

        int row = curGUI.getRowFromSlot(slot);
        int col = curGUI.getColFromSlot(slot);

        event.setCancelled(true);
        if(curGUI.canSlotBeMoved(row, col))
        {
            curGUI.onPlayerInteract(event);
        }
        curGUI.onClicked(event);
        curGUI.update(player);
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
        System.out.println("OnClose 1");
        Player player = (Player) event.getPlayer();
        GUIManager manager = plugin.guiManager();
        if(!manager.containsPlayer(player)) return;
        PlayerGUI playerGUI = manager.getPlayer(player);
        GUIContainer curGUI = playerGUI.getGUI();
        if(curGUI == null) return;
        playerGUI.onClose();
        playerGUI.setGUIState(false);
        System.out.println("OnClose 2");
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
        System.out.println("OnOpen 1");
        Player player = (Player) event.getPlayer();
        GUIManager manager = plugin.guiManager();
        if(!manager.containsPlayer(player)) return;
        PlayerGUI playerGUI = manager.getPlayer(player);
        Inventory guiInv = playerGUI.getGUI().getInventory();
        Inventory playerInv = event.getInventory();
        if(guiInv != playerInv) return;
        playerGUI.setGUIState(true);
        System.out.println("OnOpen 2");
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
        if(!plugin.guiManager().containsPlayer(player)) return;
        GUIContainer curGUI = plugin.guiManager().getPlayer(player).getGUI();
        if(curGUI == null) return;
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
