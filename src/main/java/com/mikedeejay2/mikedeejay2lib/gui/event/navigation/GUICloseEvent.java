package com.mikedeejay2.mikedeejay2lib.gui.event.navigation;

import com.mikedeejay2.mikedeejay2lib.PluginBase;
import com.mikedeejay2.mikedeejay2lib.gui.GUIContainer;
import com.mikedeejay2.mikedeejay2lib.gui.event.GUIEvent;
import com.mikedeejay2.mikedeejay2lib.gui.item.GUIItem;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;

/**
 * Close the player's GUI
 *
 * @author Mikedeejay2
 */
public class GUICloseEvent extends GUIEvent
{
    public GUICloseEvent(PluginBase plugin)
    {
        super(plugin);
    }

    @Override
    public void execute(Player player, int row, int col, GUIItem clicked, GUIContainer gui, InventoryAction action, ClickType clickType)
    {
        plugin.guiManager().getPlayer(player).closeGUI();
        player.closeInventory();
    }
}
