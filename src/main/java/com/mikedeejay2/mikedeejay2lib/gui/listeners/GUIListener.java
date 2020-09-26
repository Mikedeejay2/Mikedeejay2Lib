package com.mikedeejay2.mikedeejay2lib.gui.listeners;

import com.mikedeejay2.mikedeejay2lib.PluginBase;
import com.mikedeejay2.mikedeejay2lib.gui.GUIContainer;
import com.mikedeejay2.mikedeejay2lib.gui.manager.PlayerGUI;
import com.mikedeejay2.mikedeejay2lib.util.PluginInstancer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class GUIListener extends PluginInstancer<PluginBase> implements Listener
{
    public GUIListener(PluginBase plugin)
    {
        super(plugin);
    }

    @EventHandler
    public void onClick(InventoryClickEvent event)
    {
        Player player = (Player) event.getWhoClicked();
        PlayerGUI playerGUI = plugin.guiManager().getPlayer(player);
        GUIContainer curGUI = playerGUI.getGUI();
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

            curGUI.clicked(player, row, col, event.getCurrentItem());
        }
    }
}
