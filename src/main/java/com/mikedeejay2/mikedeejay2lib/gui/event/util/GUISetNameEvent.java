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
    private String name;

    public GUISetNameEvent(String newName)
    {
        this.name = newName;
    }

    public GUISetNameEvent()
    {
        this("");
    }

    @Override
    public void execute(InventoryClickEvent event, GUIContainer gui)
    {
        Player player = (Player) event.getWhoClicked();
        gui.setInventoryName(name);
        gui.onClose(player);
        gui.open(player);
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }
}
