package com.mikedeejay2.mikedeejay2lib.gui.event.util;

import com.mikedeejay2.mikedeejay2lib.gui.GUIContainer;
import com.mikedeejay2.mikedeejay2lib.gui.event.GUIEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

/**
 * Refresh a GUI. This event calls {@link GUIContainer#onClose(Player)} and {@link GUIContainer#open(Player)} to
 * "refresh" a player's GUI for any packet-based changes. (Name change, size change, etc)
 *
 * @author Mikedeejay2
 */
public class GUIRefreshEvent implements GUIEvent
{
    /**
     * {@inheritDoc}
     *
     * @param event The event of the click
     * @param gui   The GUI that the event took place in
     */
    @Override
    public void execute(InventoryClickEvent event, GUIContainer gui)
    {
        Player player = (Player) event.getWhoClicked();
        gui.onClose(player);
        gui.open(player);
    }
}
