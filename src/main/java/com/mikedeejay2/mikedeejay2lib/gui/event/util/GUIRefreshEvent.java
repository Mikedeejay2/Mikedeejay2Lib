package com.mikedeejay2.mikedeejay2lib.gui.event.util;

import com.mikedeejay2.mikedeejay2lib.gui.GUIContainer;
import com.mikedeejay2.mikedeejay2lib.gui.event.GUIEvent;
import com.mikedeejay2.mikedeejay2lib.gui.event.GUIClickEvent;
import org.bukkit.entity.Player;

/**
 * Refresh a GUI. This event calls {@link GUIContainer#open(Player)} to
 * "refresh" a player's GUI for any packet-based changes. (Name change, size change, etc)
 *
 * @author Mikedeejay2
 */
public class GUIRefreshEvent implements GUIEvent {
    /**
     * {@inheritDoc}
     *
     * @param info {@link GUIClickEvent} of the event
     */
    @Override
    public void execute(GUIClickEvent info) {
        Player player = info.getPlayer();
        GUIContainer gui = info.getGUI();
        gui.open(player);
    }
}
