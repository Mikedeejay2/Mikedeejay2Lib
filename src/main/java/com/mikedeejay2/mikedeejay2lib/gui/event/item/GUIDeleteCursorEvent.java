package com.mikedeejay2.mikedeejay2lib.gui.event.item;

import com.mikedeejay2.mikedeejay2lib.gui.event.GUIEvent;
import com.mikedeejay2.mikedeejay2lib.gui.event.GUIEventInfo;
import org.bukkit.entity.Player;
import org.bukkit.inventory.InventoryView;

/**
 * Event that deletes the item in the player's cursor
 *
 * @author Mikedeejay2
 */
public class GUIDeleteCursorEvent implements GUIEvent {
    /**
     * {@inheritDoc}
     *
     * @param info {@link GUIEventInfo} of the event
     */
    @Override
    public void execute(GUIEventInfo info) {
        Player player = info.getPlayer();
        InventoryView inventory = player.getOpenInventory();
        inventory.setCursor(null);
    }
}
