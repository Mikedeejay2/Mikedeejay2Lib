package com.mikedeejay2.mikedeejay2lib.gui.event.navigation;

import com.mikedeejay2.mikedeejay2lib.BukkitPlugin;
import com.mikedeejay2.mikedeejay2lib.gui.event.GUIClickEvent;
import com.mikedeejay2.mikedeejay2lib.gui.event.util.GUIAbstractClickEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;

/**
 * Close the player's GUI
 *
 * @author Mikedeejay2
 */
public class GUICloseEvent extends GUIAbstractClickEvent {
    /**
     * The {@link BukkitPlugin} instance
     */
    protected final BukkitPlugin plugin;

    /**
     * Construct a new <code>GUICloseEvent</code>
     *
     * @param plugin The {@link BukkitPlugin} instance
     * @param acceptedClicks The list of {@link ClickType ClickTypes} to accept
     */
    public GUICloseEvent(BukkitPlugin plugin, ClickType... acceptedClicks) {
        super(acceptedClicks);
        this.plugin = plugin;
    }

    /**
     * Construct a new <code>GUICloseEvent</code>
     *
     * @param plugin The {@link BukkitPlugin} instance
     */
    public GUICloseEvent(BukkitPlugin plugin) {
        super();
        this.plugin = plugin;
    }

    @Override
    protected void executeClick(GUIClickEvent info) {
        Player player = info.getPlayer();
        info.getGUI().close(player);
    }
}
