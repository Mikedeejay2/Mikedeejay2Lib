package com.mikedeejay2.mikedeejay2lib.gui.event.navigation;

import com.mikedeejay2.mikedeejay2lib.BukkitPlugin;
import com.mikedeejay2.mikedeejay2lib.gui.GUIContainer;
import com.mikedeejay2.mikedeejay2lib.gui.event.GUIEvent;
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
    protected final BukkitPlugin plugin;

    // The GUI that will be opened
    private GUIContainer guiToOpen;

    public GUIOpenEvent(BukkitPlugin plugin, GUIContainer gui)
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
