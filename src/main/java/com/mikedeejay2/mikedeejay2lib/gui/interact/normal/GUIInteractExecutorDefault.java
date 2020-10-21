package com.mikedeejay2.mikedeejay2lib.gui.interact.normal;

import com.mikedeejay2.mikedeejay2lib.gui.GUIContainer;
import com.mikedeejay2.mikedeejay2lib.gui.GUILayer;
import com.mikedeejay2.mikedeejay2lib.gui.interact.GUIInteractExecutor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

/**
 * The default <tt>GUIInteractExecutor</tt>. Moves items in a semi-vanilla way
 * without breaking the GUI.
 *
 * @author Mikedeejay2
 */
public class GUIInteractExecutorDefault implements GUIInteractExecutor
{
    @Override
    public void executeNothing(Player player, Inventory inventory, int slot, GUIContainer gui, GUILayer layer)
    {
        player.sendMessage("Executed Nothing");
    }

    @Override
    public void executePickupAll(Player player, Inventory inventory, int slot, GUIContainer gui, GUILayer layer)
    {
        player.sendMessage("Executed PickupAll");
    }

    @Override
    public void executePickupSome(Player player, Inventory inventory, int slot, GUIContainer gui, GUILayer layer)
    {
        player.sendMessage("Executed PickupSome");
    }

    @Override
    public void executePickupHalf(Player player, Inventory inventory, int slot, GUIContainer gui, GUILayer layer)
    {
        player.sendMessage("Executed Pickup Half");
    }

    @Override
    public void executePickupOne(Player player, Inventory inventory, int slot, GUIContainer gui, GUILayer layer)
    {
        player.sendMessage("Executed Pickup One");
    }

    @Override
    public void executePlaceAll(Player player, Inventory inventory, int slot, GUIContainer gui, GUILayer layer)
    {
        player.sendMessage("Executed Place All");
    }

    @Override
    public void executePlaceSome(Player player, Inventory inventory, int slot, GUIContainer gui, GUILayer layer)
    {
        player.sendMessage("Executed Place Some");
    }

    @Override
    public void executePlaceOne(Player player, Inventory inventory, int slot, GUIContainer gui, GUILayer layer)
    {
        player.sendMessage("Executed Place One");
    }

    @Override
    public void executeSwapWithCursor(Player player, Inventory inventory, int slot, GUIContainer gui, GUILayer layer)
    {
        player.sendMessage("Executed Swap With Cursor");
    }

    @Override
    public void executeDropAllCursor(Player player, Inventory inventory, int slot, GUIContainer gui, GUILayer layer)
    {
        player.sendMessage("Executed Drop All Cursor");
    }

    @Override
    public void executeDropOneCursor(Player player, Inventory inventory, int slot, GUIContainer gui, GUILayer layer)
    {
        player.sendMessage("Executed Drop One Cursor");
    }

    @Override
    public void executeDropAllSlot(Player player, Inventory inventory, int slot, GUIContainer gui, GUILayer layer)
    {
        player.sendMessage("Executed Drop All Slot");
    }

    @Override
    public void executeDropOneSlot(Player player, Inventory inventory, int slot, GUIContainer gui, GUILayer layer)
    {
        player.sendMessage("Executed Drop One Slot");
    }

    @Override
    public void executeMoveToOtherInventory(Player player, Inventory inventory, int slot, GUIContainer gui, GUILayer layer)
    {
        player.sendMessage("Executed Move To Other Inventory");
    }

    @Override
    public void executeHotbarMoveAndReadd(Player player, Inventory inventory, int slot, GUIContainer gui, GUILayer layer)
    {
        player.sendMessage("Executed Move And Readd");
    }

    @Override
    public void executeHotbarSwap(Player player, Inventory inventory, int slot, GUIContainer gui, GUILayer layer)
    {
        player.sendMessage("Executed Hotbar Swap");
    }

    @Override
    public void executeCloneStack(Player player, Inventory inventory, int slot, GUIContainer gui, GUILayer layer)
    {
        player.sendMessage("Executed Clone Stack");
    }

    @Override
    public void executeCollectToCursor(Player player, Inventory inventory, int slot, GUIContainer gui, GUILayer layer)
    {
        player.sendMessage("Executed Collect To Cursor");
    }

    @Override
    public void executeUnknown(Player player, Inventory inventory, int slot, GUIContainer gui, GUILayer layer)
    {
        player.sendMessage("Executed Unknown");
    }
}
