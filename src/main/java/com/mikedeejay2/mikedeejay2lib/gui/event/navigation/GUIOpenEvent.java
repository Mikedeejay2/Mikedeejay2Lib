package com.mikedeejay2.mikedeejay2lib.gui.event.navigation;

import com.mikedeejay2.mikedeejay2lib.BukkitPlugin;
import com.mikedeejay2.mikedeejay2lib.gui.GUIContainer;
import com.mikedeejay2.mikedeejay2lib.gui.event.GUIEvent;
import com.mikedeejay2.mikedeejay2lib.gui.event.GUIEventInfo;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;

/**
 * Open a player's GUI to a specified GUI
 *
 * @author Mikedeejay2
 */
public class GUIOpenEvent implements GUIEvent {
    /**
     * The {@link BukkitPlugin} instance
     */
    protected final BukkitPlugin plugin;

    // The GUI that will be opened
    private GUIContainer guiToOpen;

    /**
     * Construct a new <code>GUIOpenEvent</code>
     *
     * @param plugin The {@link BukkitPlugin} instance
     * @param gui    The {@link GUIContainer} to open
     */
    public GUIOpenEvent(BukkitPlugin plugin, GUIContainer gui) {
        this.plugin = plugin;
        this.guiToOpen = gui;
    }

    /**
     * {@inheritDoc}
     *
     * @param info {@link GUIEventInfo} of the event
     */
    @Override
    public void execute(GUIEventInfo info) {
        Player player = info.getPlayer();
        ClickType clickType = info.getClick();
        if(clickType != ClickType.LEFT) return;
        if(info.getGUI().equals(guiToOpen)) return;
        guiToOpen.open(player);
    }
}
