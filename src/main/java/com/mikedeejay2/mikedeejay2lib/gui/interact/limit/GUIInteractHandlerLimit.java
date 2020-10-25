package com.mikedeejay2.mikedeejay2lib.gui.interact.limit;

import com.mikedeejay2.mikedeejay2lib.gui.GUIContainer;
import com.mikedeejay2.mikedeejay2lib.gui.interact.GUIInteractHandler;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

/**
 * Handles interactions between the player and the GUI when moving items
 * with a custom specified limit.
 *
 * @author Mikedeejay2
 */
public class GUIInteractHandlerLimit extends GUIInteractHandler
{
    protected int limit;

    public GUIInteractHandlerLimit(int limit)
    {
        super();
        this.limit = Math.min(limit, 64);
    }

    /**
     * Handle an interaction between a <tt>Player</tt> and a <tt>GUIContainer</tt> to properly move an item
     * <p>
     * This method override handles the default state of item movement where items move like default Minecraft.
     *
     * @param event The event of the click
     * @param gui The GUI that the player interacted with
     */
    @Override
    public void handleInteraction(InventoryClickEvent event, GUIContainer gui)
    {

    }

    public int getLimit()
    {
        return limit;
    }

    public void setLimit(int limit)
    {
        this.limit = limit;
    }
}
