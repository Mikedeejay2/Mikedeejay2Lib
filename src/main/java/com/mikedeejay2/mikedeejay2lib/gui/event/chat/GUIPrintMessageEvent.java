package com.mikedeejay2.mikedeejay2lib.gui.event.chat;

import com.mikedeejay2.mikedeejay2lib.BukkitPlugin;
import com.mikedeejay2.mikedeejay2lib.gui.GUIContainer;
import com.mikedeejay2.mikedeejay2lib.gui.event.GUIEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;

/**
 * A GUI Event that prints a message to the player
 *
 * @author Mikedeejay2
 */
public class GUIPrintMessageEvent implements GUIEvent
{
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
     * @param plugin  The {@link BukkitPlugin} instance
     * @param message The message to be printed
     */
    public GUIPrintMessageEvent(BukkitPlugin plugin, String message)
    {
        this.plugin = plugin;
        this.message = message;
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
        ClickType clickType = event.getClick();
        if(clickType != ClickType.LEFT) return;
        plugin.sendMessage(player, message);
    }

    /**
     * Get the message to be printed
     *
     * @return The message to be printed
     */
    public String getMessage()
    {
        return message;
    }

    /**
     * Set the message to be printed
     *
     * @param message The new message to be printed
     */
    public void setMessage(String message)
    {
        this.message = message;
    }
}
