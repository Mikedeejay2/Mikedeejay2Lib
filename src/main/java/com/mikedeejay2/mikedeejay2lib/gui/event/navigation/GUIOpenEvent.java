package com.mikedeejay2.mikedeejay2lib.gui.event.navigation;

import com.mikedeejay2.mikedeejay2lib.BukkitPlugin;
import com.mikedeejay2.mikedeejay2lib.gui.GUIContainer;
import com.mikedeejay2.mikedeejay2lib.gui.event.GUIClickEvent;
import com.mikedeejay2.mikedeejay2lib.gui.event.util.GUIAbstractClickEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;

/**
 * Open a player's GUI to a specified GUI
 *
 * @author Mikedeejay2
 */
public class GUIOpenEvent extends GUIAbstractClickEvent {
    /**
     * The {@link BukkitPlugin} instance
     */
    protected final BukkitPlugin plugin;
    /**
     * The GUI that will be opened
     */
    protected GUIContainer guiToOpen;

    /**
     * Construct a new <code>GUIOpenEvent</code>
     *
     * @param plugin         The {@link BukkitPlugin} instance
     * @param gui            The {@link GUIContainer} to open
     * @param acceptedClicks The list of {@link ClickType ClickTypes} to accept
     */
    public GUIOpenEvent(BukkitPlugin plugin, GUIContainer gui, ClickType... acceptedClicks) {
        super(acceptedClicks);
        this.plugin = plugin;
        this.guiToOpen = gui;
    }

    /**
     * Construct a new <code>GUIOpenEvent</code>
     *
     * @param plugin         The {@link BukkitPlugin} instance
     * @param gui            The {@link GUIContainer} to open
     */
    public GUIOpenEvent(BukkitPlugin plugin, GUIContainer gui) {
        super();
        this.plugin = plugin;
        this.guiToOpen = gui;
    }

    @Override
    protected void executeClick(GUIClickEvent info) {
        Player player = info.getPlayer();
        if(info.getGUI().equals(guiToOpen)) return;
        guiToOpen.open(player);
    }

    /**
     * Get the GUI that will be opened
     *
     * @return The GUI that will be opened
     */
    public GUIContainer getGUI() {
        return guiToOpen;
    }

    /**
     * Set the GUI that will be opened
     *
     * @param guiToOpen The new GUI
     */
    public void setGUI(GUIContainer guiToOpen) {
        this.guiToOpen = guiToOpen;
    }
}
