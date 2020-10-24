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
public class GUICloseEvent implements GUIEvent
{
    protected final PluginBase plugin;

    public GUICloseEvent(PluginBase plugin)
    {
        this.plugin = plugin;
    }

    @Override
    public void execute(Player player, int row, int col, GUIItem clicked, GUIContainer gui, InventoryAction action, ClickType clickType)
    {
        if(clickType != ClickType.LEFT) return;
        plugin.guiManager().getPlayer(player).onClose();
        player.closeInventory();
    }
}
