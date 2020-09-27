package com.mikedeejay2.mikedeejay2lib.gui.listeners;

import com.mikedeejay2.mikedeejay2lib.PluginBase;
import com.mikedeejay2.mikedeejay2lib.gui.GUIContainer;
import com.mikedeejay2.mikedeejay2lib.gui.manager.PlayerGUI;
import com.mikedeejay2.mikedeejay2lib.gui.modules.GUIModule;
import com.mikedeejay2.mikedeejay2lib.gui.util.GUIMath;
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
        GUIContainer curGUI = plugin.guiManager().getPlayer(player).getGUI();
        if(curGUI == null) return;

        if(event.getClickedInventory() == curGUI.getInventory())
        {
            if(event.getCurrentItem() == null) return;
            int slot = event.getSlot();

            int row = GUIMath.getRowFromSlot(slot);
            int col = GUIMath.getColFromSlot(slot);

            if(!curGUI.canSlotBeMoved(row, col))
            {
                event.setCancelled(true);
            }

            curGUI.clicked(player, row, col, curGUI.getItem(row, col));
        }
    }
}
