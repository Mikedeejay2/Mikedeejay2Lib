package com.mikedeejay2.mikedeejay2lib.gui.event.navigation;

import com.mikedeejay2.mikedeejay2lib.PluginBase;
import com.mikedeejay2.mikedeejay2lib.gui.GUIConstructor;
import com.mikedeejay2.mikedeejay2lib.gui.GUIContainer;
import com.mikedeejay2.mikedeejay2lib.gui.event.GUIEvent;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;

/**
 * Open a player's GUI to a NEW specified GUI.
 * Instead opening an already constructed GUI, this event
 * creates a new GUI upon request.
 *
 * @author Mikedeejay2
 */
public class GUIOpenNewEvent implements GUIEvent
{
    protected final PluginBase plugin;
    protected GUIConstructor constructor;
    protected GUIContainer gui;
    protected GUIOpenEvent event;

    public GUIOpenNewEvent(PluginBase plugin, GUIConstructor constructor)
    {
        this.plugin = plugin;
        this.constructor = constructor;
        this.gui = null;
        this.event = null;
    }

    @Override
    public void execute(InventoryClickEvent event, GUIContainer gui)
    {
        ClickType clickType = event.getClick();
        if(clickType != ClickType.LEFT) return;
        if(this.event == null)
        {
            this.gui = constructor.get();
            this.event = new GUIOpenEvent(plugin, this.gui);
        }
        this.event.execute(event, gui);
    }
}
