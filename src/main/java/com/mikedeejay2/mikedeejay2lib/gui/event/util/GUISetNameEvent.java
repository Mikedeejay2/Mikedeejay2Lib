package com.mikedeejay2.mikedeejay2lib.gui.event.util;

import com.mikedeejay2.mikedeejay2lib.gui.GUIContainer;
import com.mikedeejay2.mikedeejay2lib.gui.event.GUIEvent;
import com.mikedeejay2.mikedeejay2lib.gui.event.GUIEventInfo;
import org.bukkit.entity.Player;

/**
 * Set the name of a GUI on click
 *
 * @author Mikedeejay2
 */
public class GUISetNameEvent implements GUIEvent {
    /**
     * The name to set the GUI to
     */
    private String name;

    /**
     * Construct a new <code>GUISetNameEvent</code>
     *
     * @param newName The name to set the GUI to
     */
    public GUISetNameEvent(String newName) {
        this.name = newName;
    }

    /**
     * {@inheritDoc}
     *
     * @param info {@link GUIEventInfo} of the event
     */
    @Override
    public void execute(GUIEventInfo info) {
        Player player = info.getPlayer();
        GUIContainer gui = info.getGUI();
        gui.setInventoryName(name);
        gui.onClose(player);
        gui.open(player);
    }

    /**
     * Get the name that this event will set the GUI to
     *
     * @return The name
     */
    public String getName() {
        return name;
    }

    /**
     * Set the new name that this event will set the GUI to
     *
     * @param name The new name
     */
    public void setName(String name) {
        this.name = name;
    }
}
