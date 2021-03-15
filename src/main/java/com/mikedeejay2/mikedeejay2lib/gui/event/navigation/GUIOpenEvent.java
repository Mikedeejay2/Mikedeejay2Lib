package com.mikedeejay2.mikedeejay2lib.gui.event.navigation;

import com.mikedeejay2.mikedeejay2lib.PluginBase;
import com.mikedeejay2.mikedeejay2lib.gui.GUIContainer;
import com.mikedeejay2.mikedeejay2lib.gui.event.GUIEvent;
import com.mikedeejay2.mikedeejay2lib.gui.manager.NavigationSystem;
import com.mikedeejay2.mikedeejay2lib.gui.manager.PlayerGUI;
import com.mikedeejay2.mikedeejay2lib.gui.modules.navigation.GUINavigatorModule;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;

/**
 * Open a player's GUI to a specified GUI
 *
 * @author Mikedeejay2
 */
public class GUIOpenEvent implements GUIEvent
{
    protected final PluginBase plugin;

    // The GUI that will be opened
    private GUIContainer guiToOpen;

    public GUIOpenEvent(PluginBase plugin, GUIContainer gui)
    {
        this.plugin = plugin;
        this.guiToOpen = gui;
    }

    @Override
    public void execute(InventoryClickEvent event, GUIContainer gui)
    {
        Player player = (Player) event.getWhoClicked();
        ClickType clickType = event.getClick();
        if(clickType != ClickType.LEFT) return;
        if(gui.equals(guiToOpen)) return;
        guiToOpen.open(player);
    }
}
