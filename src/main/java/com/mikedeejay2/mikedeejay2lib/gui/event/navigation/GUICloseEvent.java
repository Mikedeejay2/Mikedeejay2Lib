package com.mikedeejay2.mikedeejay2lib.gui.event.navigation;

import com.mikedeejay2.mikedeejay2lib.BukkitPlugin;
import com.mikedeejay2.mikedeejay2lib.gui.GUIContainer;
import com.mikedeejay2.mikedeejay2lib.gui.event.GUIEvent;
import com.mikedeejay2.mikedeejay2lib.gui.event.GUIEventInfo;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;

/**
 * Close the player's GUI
 *
 * @author Mikedeejay2
 */
public class GUICloseEvent implements GUIEvent
{
    /**
     * The {@link BukkitPlugin} instance
     */
    protected final BukkitPlugin plugin;

    /**
     * Construct a new <code>GUICloseEvent</code>
     *
     * @param plugin The {@link BukkitPlugin} instance
     */
    public GUICloseEvent(BukkitPlugin plugin)
    {
        this.plugin = plugin;
    }

    /**
     * {@inheritDoc}
     *
     * @param info {@link GUIEventInfo} of the event
     */
    @Override
    public void execute(GUIEventInfo info)
    {
        Player player = info.getPlayer();
        ClickType clickType = info.getClick();
        if(clickType != ClickType.LEFT) return;
        info.getGUI().close(player);
    }
}
