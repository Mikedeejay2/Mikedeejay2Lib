package com.mikedeejay2.mikedeejay2lib.gui.event.item;

import com.mikedeejay2.mikedeejay2lib.gui.GUIContainer;
import com.mikedeejay2.mikedeejay2lib.gui.event.GUIEvent;
import com.mikedeejay2.mikedeejay2lib.gui.event.GUIEventInfo;
import com.mikedeejay2.mikedeejay2lib.item.ItemBuilder;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;

import java.util.function.Supplier;

/**
 * Event that deletes the item in the player's cursor
 *
 * @author Mikedeejay2
 */
public class GUIDeleteCursorEvent implements GUIEvent
{
    /**
     * {@inheritDoc}
     *
     * @param info {@link GUIEventInfo} of the event
     */
    @Override
    public void execute(GUIEventInfo info)
    {
        Player player = info.getPlayer();
        InventoryView inventory = player.getOpenInventory();
        inventory.setCursor(null);
    }
}
