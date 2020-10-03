package com.mikedeejay2.mikedeejay2lib.gui.listeners;

import com.mikedeejay2.mikedeejay2lib.PluginBase;
import com.mikedeejay2.mikedeejay2lib.gui.GUIContainer;
import com.mikedeejay2.mikedeejay2lib.gui.manager.PlayerGUI;
import com.mikedeejay2.mikedeejay2lib.util.PluginInstancer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;

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
        Player player = (Player) event.getWhoClicked();
        GUIContainer curGUI = plugin.guiManager().getPlayer(player).getGUI();
        if(curGUI == null) return;

        if(event.getClickedInventory() == curGUI.getInventory())
        {
            if(event.getCurrentItem() == null) return;
            int slot = event.getSlot();

            int row = curGUI.getRowFromSlot(slot);
            int col = curGUI.getColFromSlot(slot);

            if(!curGUI.canSlotBeMoved(row, col))
            {
                event.setCancelled(true);
            }

            curGUI.clicked(player, row, col, curGUI.getItem(row, col));
        }
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
}
