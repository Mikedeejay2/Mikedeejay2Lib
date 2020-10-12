package com.mikedeejay2.mikedeejay2lib.gui.event.chat;

import com.mikedeejay2.mikedeejay2lib.PluginBase;
import com.mikedeejay2.mikedeejay2lib.gui.GUIContainer;
import com.mikedeejay2.mikedeejay2lib.gui.event.GUIEvent;
import com.mikedeejay2.mikedeejay2lib.gui.item.GUIItem;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;

/**
 * A GUI Event that prints a message to the player
 *
 * @author Mikedeejay2
 */
public class GUIPrintMessageEvent extends GUIEvent
{
    // The message to be printed
    private String message;

    public GUIPrintMessageEvent(PluginBase plugin, String message)
    {
        super(plugin);

        this.message = message;
    }

    @Override
    public void execute(Player player, int row, int col, GUIItem clicked, GUIContainer gui, InventoryAction action, ClickType clickType)
    {
        plugin.chat().sendMessage(player, message);
    }
}
