package com.mikedeejay2.mikedeejay2lib.gui.event.chat;

import com.mikedeejay2.mikedeejay2lib.BukkitPlugin;
import com.mikedeejay2.mikedeejay2lib.gui.event.GUIClickEvent;
import com.mikedeejay2.mikedeejay2lib.gui.event.util.GUIAbstractClickEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;

/**
 * A GUI Event that prints a message to the player
 *
 * @author Mikedeejay2
 */
public class GUIPrintMessageEvent extends GUIAbstractClickEvent {
    /**
     * The {@link BukkitPlugin} instance
     */
    protected final BukkitPlugin plugin;
    /**
     * The message to be printed
     */
    private String message;

    /**
     * Construct a new <code>GUIPrintMessageEvent</code>
     *
     * @param plugin         The {@link BukkitPlugin} instance
     * @param message        The message to be printed
     * @param acceptedClicks The list of {@link ClickType ClickTypes} to accept
     */
    public GUIPrintMessageEvent(BukkitPlugin plugin, String message, ClickType... acceptedClicks) {
        super(acceptedClicks);
        this.plugin = plugin;
        this.message = message;
    }

    /**
     * Construct a new <code>GUIPrintMessageEvent</code>
     *
     * @param plugin  The {@link BukkitPlugin} instance
     * @param message The message to be printed
     */
    public GUIPrintMessageEvent(BukkitPlugin plugin, String message) {
        super();
        this.plugin = plugin;
        this.message = message;
    }

    @Override
    protected void executeClick(GUIClickEvent info) {
        Player player = info.getPlayer();
        plugin.sendMessage(player, message);
    }

    /**
     * Get the message to be printed
     *
     * @return The message to be printed
     */
    public String getMessage() {
        return message;
    }

    /**
     * Set the message to be printed
     *
     * @param message The new message to be printed
     */
    public void setMessage(String message) {
        this.message = message;
    }
}
