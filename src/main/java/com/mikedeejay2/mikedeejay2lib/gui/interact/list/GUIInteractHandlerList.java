package com.mikedeejay2.mikedeejay2lib.gui.interact.list;

import com.mikedeejay2.mikedeejay2lib.gui.GUIContainer;
import com.mikedeejay2.mikedeejay2lib.gui.GUILayer;
import com.mikedeejay2.mikedeejay2lib.gui.interact.GUIInteractHandler;
import com.mikedeejay2.mikedeejay2lib.gui.interact.normal.GUIInteractExecutorDefaultInv;
import com.mikedeejay2.mikedeejay2lib.gui.item.GUIItem;
import com.mikedeejay2.mikedeejay2lib.util.item.ItemComparison;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

/**
 * Handles interactions between the player and the GUI when moving items
 * with a custom specified limit.
 *
 * @author Mikedeejay2
 */
public class GUIInteractHandlerList extends GUIInteractHandler
{
    protected int limit;

    public GUIInteractHandlerList(int limit)
    {
        super();
        this.limit = Math.min(limit, 64);
        executors.add(new GUIInteractExecutorDefaultInv(this.limit));
        executors.add(new GUIInteractExecutorList(this.limit));
    }

    public GUIInteractHandlerList()
    {
        super();
        this.limit = -1;
        executors.add(new GUIInteractExecutorDefaultInv(this.limit));
        executors.add(new GUIInteractExecutorList(this.limit));
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
        ClickType clickType = event.getClick();
        InventoryAction action = event.getAction();
        Player player = (Player) event.getWhoClicked();
        int slot = event.getSlot();

        Inventory clickedInv = event.getClickedInventory();
        Inventory playerInv = player.getInventory();
        GUILayer layer = gui.getLayer(0);

        ItemStack cursorItem = player.getItemOnCursor();
        if(cursorItem.getType() == Material.AIR) cursorItem = null;
        ItemStack bottomItem = null;
        if(clickedInv == gui.getInventory())
        {
            int row = gui.getRowFromSlot(slot);
            int col = gui.getColFromSlot(slot);
            GUIItem guiItem = layer.getItem(row, col);
            if(guiItem != null)
            {
                bottomItem = guiItem.getItemBase();
            }
        }

        if(limit == -1)
        {
            executeAction(player, clickedInv, slot, action, event, gui, layer);
            return;
        }

        switch(clickType)
        {
            case LEFT:
            {
                if(clickedInv == null) break;
                if(bottomItem != null && cursorItem != null)
                {
                    int bottomAmount = bottomItem.getAmount();
                    int cursorAmount = cursorItem.getAmount();
                    if(bottomAmount + cursorAmount > limit) action = InventoryAction.PLACE_SOME;
                    else action = InventoryAction.PLACE_ALL;
                }
                else if(cursorItem != null)
                {
                    action = InventoryAction.PLACE_ALL;
                }
                else if(bottomItem != null)
                {
                    action = InventoryAction.PICKUP_ALL;
                }
            }
            break;
            case RIGHT:
            {
                if(clickedInv == null) break;
                if(bottomItem != null && cursorItem != null)
                {
                    action = InventoryAction.PLACE_ONE;
                }
                else if(cursorItem != null)
                {
                    action = InventoryAction.PLACE_ONE;
                }
                else if(bottomItem != null)
                {
                    action = InventoryAction.PICKUP_ALL;
                }
            }
            break;
            case MIDDLE:
            {
                if(clickedInv == null) break;
                action = InventoryAction.CLONE_STACK;
            }
            case SHIFT_LEFT:
            case SHIFT_RIGHT:
            {
                if(clickedInv == null) break;
                if(bottomItem != null) action = InventoryAction.MOVE_TO_OTHER_INVENTORY;
            }
            break;
        }
        executeAction(player, clickedInv, slot, action, event, gui, layer);
    }

    /**
     * Get the limit of this <tt>GUIInteractHandler</tt>
     *
     * @return The limit
     */
    public int getLimit()
    {
        return limit;
    }

    /**
     * Set a new limit for this <tt>GUIInteractHandler</tt>
     *
     * @param limit The new limit to use
     */
    public void setLimit(int limit)
    {
        this.limit = limit;
    }
}