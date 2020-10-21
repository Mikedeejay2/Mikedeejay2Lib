package com.mikedeejay2.mikedeejay2lib.gui.interact.limit;

import com.mikedeejay2.mikedeejay2lib.gui.GUIContainer;
import com.mikedeejay2.mikedeejay2lib.gui.GUILayer;
import com.mikedeejay2.mikedeejay2lib.gui.interact.GUIInteractExecutor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

/**
 * A <tt>GUIInteractExecutor</tt> that moves items with a custom limit specified on construction.
 *
 * @author Mikedeejay2
 */
public class GUIInteractExecutorLimit implements GUIInteractExecutor
{
    protected int limit;

    public GUIInteractExecutorLimit(int limit)
    {
        this.limit = Math.min(limit, 64);
    }

    @Override
    public void executeNothing(Player player, Inventory inventory, int slot, GUIContainer gui, GUILayer layer)
    {

    }

    @Override
    public void executePickupAll(Player player, Inventory inventory, int slot, GUIContainer gui, GUILayer layer)
    {

    }

    @Override
    public void executePickupSome(Player player, Inventory inventory, int slot, GUIContainer gui, GUILayer layer)
    {

    }

    @Override
    public void executePickupHalf(Player player, Inventory inventory, int slot, GUIContainer gui, GUILayer layer)
    {

    }

    @Override
    public void executePickupOne(Player player, Inventory inventory, int slot, GUIContainer gui, GUILayer layer)
    {

    }

    @Override
    public void executePlaceAll(Player player, Inventory inventory, int slot, GUIContainer gui, GUILayer layer)
    {

    }

    @Override
    public void executePlaceSome(Player player, Inventory inventory, int slot, GUIContainer gui, GUILayer layer)
    {

    }

    @Override
    public void executePlaceOne(Player player, Inventory inventory, int slot, GUIContainer gui, GUILayer layer)
    {

    }

    @Override
    public void executeSwapWithCursor(Player player, Inventory inventory, int slot, GUIContainer gui, GUILayer layer)
    {

    }

    @Override
    public void executeDropAllCursor(Player player, Inventory inventory, int slot, GUIContainer gui, GUILayer layer)
    {

    }

    @Override
    public void executeDropOneCursor(Player player, Inventory inventory, int slot, GUIContainer gui, GUILayer layer)
    {

    }

    @Override
    public void executeDropAllSlot(Player player, Inventory inventory, int slot, GUIContainer gui, GUILayer layer)
    {

    }

    @Override
    public void executeDropOneSlot(Player player, Inventory inventory, int slot, GUIContainer gui, GUILayer layer)
    {

    }

    @Override
    public void executeMoveToOtherInventory(Player player, Inventory inventory, int slot, GUIContainer gui, GUILayer layer)
    {

    }

    @Override
    public void executeHotbarMoveAndReadd(Player player, Inventory inventory, int slot, GUIContainer gui, GUILayer layer)
    {

    }

    @Override
    public void executeHotbarSwap(Player player, Inventory inventory, int slot, GUIContainer gui, GUILayer layer)
    {

    }

    @Override
    public void executeCloneStack(Player player, Inventory inventory, int slot, GUIContainer gui, GUILayer layer)
    {

    }

    @Override
    public void executeCollectToCursor(Player player, Inventory inventory, int slot, GUIContainer gui, GUILayer layer)
    {

    }

    @Override
    public void executeUnknown(Player player, Inventory inventory, int slot, GUIContainer gui, GUILayer layer)
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
