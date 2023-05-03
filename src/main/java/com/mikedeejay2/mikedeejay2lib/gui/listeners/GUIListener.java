package com.mikedeejay2.mikedeejay2lib.gui.listeners;

import com.mikedeejay2.mikedeejay2lib.BukkitPlugin;
import com.mikedeejay2.mikedeejay2lib.gui.GUIContainer;
import com.mikedeejay2.mikedeejay2lib.gui.manager.GUIManager;
import com.mikedeejay2.mikedeejay2lib.gui.manager.PlayerGUI;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;

/**
 * A listener class that listens for GUI interactions with a <code>GUIContainer</code>
 *
 * @author Mikedeejay2
 */
public class GUIListener implements Listener {
    /**
     * The {@link BukkitPlugin} instance
     */
    protected final BukkitPlugin plugin;

    /**
     * Construct a new <code>GUIListener</code>
     *
     * @param plugin The {@link BukkitPlugin} instance
     */
    public GUIListener(BukkitPlugin plugin) {
        this.plugin = plugin;
    }

    /**
     * The on click event to detect when a player clicks on a GUI
     *
     * @param event The inventory click event to be processed
     */
    @EventHandler
    public void onClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        GUIManager guiManager = plugin.getGUIManager();
        if(!guiManager.containsPlayer(player)) return;
        PlayerGUI playerGUI = plugin.getGUIManager().getPlayer(player);
        if(!playerGUI.isGuiOpened()) return;

        GUIContainer curGUI           = playerGUI.getGUI();
        Inventory    clickedInventory = event.getClickedInventory();
        Inventory    inventory        = event.getInventory();
        int          slot             = event.getSlot();

        if(clickedInventory != inventory) {
            event.setCancelled(true);
            curGUI.onPlayerInteract(event);
            curGUI.update(player);
            return;
        }

        int row = curGUI.getRow(slot);
        int col = curGUI.getColumn(slot);

        event.setCancelled(true);
        if(curGUI.isMovable(row, col)) {
            curGUI.onPlayerInteract(event);
        }
        curGUI.onClicked(event);
        curGUI.update(player);
    }

    /**
     * On inventory close. This listener exists to detect whether a player is in a GUI or not.
     *
     * @param event The event to be processed
     */
    @EventHandler
    public void onClose(InventoryCloseEvent event) {
        Player player = (Player) event.getPlayer();
        GUIManager manager = plugin.getGUIManager();
        if(!manager.containsPlayer(player)) return;
        PlayerGUI playerGUI = manager.getPlayer(player);
        if(!playerGUI.isGuiOpened()) return;
        GUIContainer curGUI = playerGUI.getGUI();
        if(curGUI == null) return;
        if(playerGUI.isGuiChange()) {
            playerGUI.setGuiChange(false);
            return;
        }
        curGUI.onClose(player);
        playerGUI.setGUIState(false);
    }

    /**
     * On inventory open. This listener exists to detect whether a player is in a GUI or not.
     *
     * @param event The event to be processed
     */
    @EventHandler
    public void onOpen(InventoryOpenEvent event) {
        Player     player    = (Player) event.getPlayer();
        GUIManager manager   = plugin.getGUIManager();
        if(!manager.containsPlayer(player)) return;
        PlayerGUI  playerGUI = manager.getPlayer(player);
        Inventory  guiInv    = playerGUI.getGUI().getInventory();
        Inventory  playerInv = event.getInventory();
        if(guiInv != playerInv) return;
        playerGUI.setGUIState(true);
    }

    /**
     * The inventory drag event for handling the dragging of items in the GUI
     *
     * @param event The event to be processed
     */
    @EventHandler
    public void onDrag(InventoryDragEvent event) {
        Player player = (Player) event.getWhoClicked();
        if(!plugin.getGUIManager().containsPlayer(player)) return;
        GUIContainer curGUI = plugin.getGUIManager().getPlayer(player).getGUI();
        if(curGUI == null) return;
        Inventory inventory = event.getInventory();
        Inventory guiInventory = curGUI.getInventory();
        if(inventory != guiInventory) return;
        event.setCancelled(true);
    }

    /**
     * Removes the {@link Player} from the {@link GUIManager} when they leave to prevent memory leaks
     *
     * @param event The event to be processed
     */
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        plugin.getGUIManager().removePlayer(player);
    }
}
