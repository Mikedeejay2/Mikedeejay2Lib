package com.mikedeejay2.mikedeejay2lib.gui.listeners;

import com.mikedeejay2.mikedeejay2lib.PluginBase;
import com.mikedeejay2.mikedeejay2lib.gui.GUIContainer;
import com.mikedeejay2.mikedeejay2lib.gui.item.GUIItem;
import com.mikedeejay2.mikedeejay2lib.gui.manager.PlayerGUI;
import com.mikedeejay2.mikedeejay2lib.util.PluginInstancer;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.*;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

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
        if(action == InventoryAction.NOTHING || action == InventoryAction.CLONE_STACK) return;
        Player player = (Player) event.getWhoClicked();
        PlayerGUI playerGUI = plugin.guiManager().getPlayer(player);
        GUIContainer curGUI = playerGUI.getGUI();
        Inventory clickedInventory = event.getClickedInventory();
        Inventory inventory = event.getInventory();
        int rawSlot = event.getRawSlot();
        if(clickedInventory != inventory)
        {
            if(playerGUI.isGuiOpened())
            {
                if(action == InventoryAction.COLLECT_TO_CURSOR)
                {
                    event.setCancelled(true);
                    return;
                }
                else if(clickType.isShiftClick())
                {
                    event.setCancelled(true);
                    for(int i = 0; i < inventory.getSize(); i++)
                    {
                        ItemStack curItem = inventory.getItem(i);
                        if(curItem != null) continue;

                        int row = curGUI.getRowFromSlot(i);
                        int col = curGUI.getColFromSlot(i);
                        curGUI.onPlayerInteract(player, row, col, action, clickType, rawSlot);
                        return;
                    }
                }
            }
            return;
        }

        if(event.getCurrentItem() == null && event.getCursor() == null) return;
        int slot = event.getSlot();

        int row = curGUI.getRowFromSlot(slot);
        int col = curGUI.getColFromSlot(slot);

        event.setCancelled(true);
        if(curGUI.canSlotBeMoved(row, col))
        {
            GUIItem guiItem = curGUI.getItem(row, col);
            ItemStack guiStack = guiItem == null ? null : guiItem.getItem();
            ItemStack cursorStack = event.getCursor();
            if(!(guiStack == null || cursorStack.getType() == Material.AIR)) return;
            curGUI.onPlayerInteract(player, row, col, action, clickType, rawSlot);
            return;
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

    @EventHandler
    public void onDrag(InventoryDragEvent event)
    {
        Player player = (Player) event.getWhoClicked();
        GUIContainer curGUI = plugin.guiManager().getPlayer(player).getGUI();
        if(event.getInventory() != curGUI.getInventory()) return;
        event.setCancelled(true);
    }
}
