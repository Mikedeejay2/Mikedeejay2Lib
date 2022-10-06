package com.mikedeejay2.mikedeejay2lib.gui.event.item;

import com.mikedeejay2.mikedeejay2lib.gui.event.GUIEvent;
import com.mikedeejay2.mikedeejay2lib.gui.event.GUIEventInfo;
import com.mikedeejay2.mikedeejay2lib.gui.event.util.GUIAbstractClickEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.InventoryView;

/**
 * Event that deletes the item in the player's cursor
 *
 * @author Mikedeejay2
 */
public class GUIDeleteCursorEvent extends GUIAbstractClickEvent {
    /**
     * Construct a new <code>GUIDeleteCursorEvent</code>
     *
     * @param acceptedClicks The list of {@link ClickType ClickTypes} to accept
     */
    public GUIDeleteCursorEvent(ClickType... acceptedClicks) {
        super(acceptedClicks);
    }

    /**
     * Construct a new <code>GUIDeleteCursorEvent</code>
     */
    public GUIDeleteCursorEvent() {
        super();
    }

    @Override
    protected void executeClick(GUIEventInfo info) {
        Player player = info.getPlayer();
        InventoryView inventory = player.getOpenInventory();
        inventory.setCursor(null);
    }
}
