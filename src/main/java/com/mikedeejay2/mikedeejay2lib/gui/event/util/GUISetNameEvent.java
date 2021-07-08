package com.mikedeejay2.mikedeejay2lib.gui.event.util;

import com.mikedeejay2.mikedeejay2lib.gui.GUIContainer;
import com.mikedeejay2.mikedeejay2lib.gui.event.GUIEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

/**
 * Set the name of a GUI on click
 *
 * @author Mikedeejay2
 */
public class GUISetNameEvent implements GUIEvent
{
    /**
     * The name to set the GUI to
     */
    private String name;

    /**
     * Construct a new <code>GUISetNameEvent</code>
     *
     * @param newName The name to set the GUI to
     */
    public GUISetNameEvent(String newName)
    {
        this.name = newName;
    }

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
        gui.setInventoryName(name);
        gui.onClose(player);
        gui.open(player);
    }

    /**
     * Get the name that this event will set the GUI to
     *
     * @return The name
     */
    public String getName()
    {
        return name;
    }

    /**
     * Set the new name that this event will set the GUI to
     *
     * @param name The new name
     */
    public void setName(String name)
    {
        this.name = name;
    }
}
