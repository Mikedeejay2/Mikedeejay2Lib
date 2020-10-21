package com.mikedeejay2.mikedeejay2lib.gui.interact.normal;

import com.mikedeejay2.mikedeejay2lib.gui.GUIContainer;
import com.mikedeejay2.mikedeejay2lib.gui.GUILayer;
import com.mikedeejay2.mikedeejay2lib.gui.interact.GUIInteractExecutor;
import com.mikedeejay2.mikedeejay2lib.gui.item.GUIItem;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

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
        ItemStack bottomItem;
        if(inventory == gui.getInventory())
        {
            int row = layer.getRowFromSlot(slot);
            int col = layer.getColFromSlot(slot);
            bottomItem = layer.getItem(row, col).getItemBase();
            layer.removeItem(row, col);
        }
        else
        {
            bottomItem = inventory.getItem(slot);
            inventory.setItem(slot, null);
        }
        player.setItemOnCursor(bottomItem);
        player.sendMessage("Executed PickupAll");
    }

    @Override
    public void executePickupSome(Player player, Inventory inventory, int slot, GUIContainer gui, GUILayer layer)
    {
        ItemStack cursorItem = player.getItemOnCursor();
        int maxAmount = cursorItem.getMaxStackSize();
        int cursorAmount = cursorItem.getAmount();
        if(inventory == gui.getInventory())
        {
            int row = layer.getRowFromSlot(slot);
            int col = layer.getColFromSlot(slot);
            GUIItem guiItem = layer.getItem(row, col);
            ItemStack bottomItem = guiItem.getItemBase();
            int bottomAmount = bottomItem.getAmount();
            cursorItem.setAmount(maxAmount);
            guiItem.setAmount(bottomAmount - (maxAmount - cursorAmount));
        }
        else
        {
            ItemStack bottomItem = inventory.getItem(slot);
            int bottomAmount = bottomItem.getAmount();
            cursorItem.setAmount(maxAmount);
            bottomItem.setAmount(bottomAmount - (maxAmount - cursorAmount));
        }
        player.sendMessage("Executed PickupSome");
    }

    @Override
    public void executePickupHalf(Player player, Inventory inventory, int slot, GUIContainer gui, GUILayer layer)
    {
        ItemStack cursorItem;
        if(inventory == gui.getInventory())
        {
            int row = layer.getRowFromSlot(slot);
            int col = layer.getColFromSlot(slot);
            GUIItem guiItem = layer.getItem(row, col);
            ItemStack bottomItem = guiItem.getItemBase();
            int bottomAmount = bottomItem.getAmount();
            int halfTop = (int) Math.ceil(bottomAmount / 2.0);
            int halfBottom = (int) Math.floor(bottomAmount / 2.0);
            cursorItem = bottomItem.clone();
            cursorItem.setAmount(halfTop);
            player.setItemOnCursor(cursorItem);
            guiItem.setAmount(halfBottom);
        }
        else
        {
            ItemStack bottomItem = inventory.getItem(slot);
            int bottomAmount = bottomItem.getAmount();
            int halfTop = (int) Math.ceil(bottomAmount / 2.0);
            int halfBottom = (int) Math.floor(bottomAmount / 2.0);
            cursorItem = bottomItem.clone();
            cursorItem.setAmount(halfTop);
            player.setItemOnCursor(cursorItem);
            bottomItem.setAmount(halfBottom);
        }
        player.sendMessage("Executed Pickup Half");
    }

    @Override
    public void executePickupOne(Player player, Inventory inventory, int slot, GUIContainer gui, GUILayer layer)
    {
        ItemStack cursorItem = player.getItemOnCursor();
        int cursorAmount = cursorItem.getAmount();
        if(inventory == gui.getInventory())
        {
            int row = layer.getRowFromSlot(slot);
            int col = layer.getColFromSlot(slot);
            GUIItem guiItem = layer.getItem(row, col);
            ItemStack bottomItem = guiItem.getItemBase();
            int bottomAmount = bottomItem.getAmount();
            if(cursorItem.getType() == Material.AIR)
            {
                cursorItem = bottomItem.clone();
                cursorItem.setAmount(1);
                player.setItemOnCursor(cursorItem);
            }
            else
            {
                cursorItem.setAmount(cursorAmount + 1);
            }
            guiItem.setAmount(bottomAmount - 1);
        }
        else
        {
            ItemStack bottomItem = inventory.getItem(slot);
            int bottomAmount = bottomItem.getAmount();
            if(cursorItem.getType() == Material.AIR)
            {
                cursorItem = bottomItem.clone();
                cursorItem.setAmount(1);
                player.setItemOnCursor(cursorItem);
            }
            else
            {
                cursorItem.setAmount(cursorAmount + 1);
            }
            bottomItem.setAmount(bottomAmount - 1);
        }
        player.sendMessage("Executed Pickup One");
    }

    @Override
    public void executePlaceAll(Player player, Inventory inventory, int slot, GUIContainer gui, GUILayer layer)
    {
        ItemStack cursorItem = player.getItemOnCursor();
        int cursorAmount = cursorItem.getAmount();
        if(inventory == gui.getInventory())
        {
            int row = layer.getRowFromSlot(slot);
            int col = layer.getColFromSlot(slot);
            int bottomAmount = 0;
            GUIItem curItem = layer.getItem(row, col);
            if(curItem != null) bottomAmount = curItem.getAmountBase();
            GUIItem newItem = new GUIItem(cursorItem);
            newItem.setMovable(true);
            newItem.setAmount(cursorAmount + bottomAmount);
            layer.setItem(row, col, newItem);
        }
        else
        {
            ItemStack curItem = inventory.getItem(slot);
            int bottomAmount = 0;
            if(curItem != null) bottomAmount = curItem.getAmount();
            cursorItem.setAmount(cursorAmount + bottomAmount);
            inventory.setItem(slot, cursorItem);
        }
        player.setItemOnCursor(null);
        player.sendMessage("Executed Place All");
    }

    @Override
    public void executePlaceSome(Player player, Inventory inventory, int slot, GUIContainer gui, GUILayer layer)
    {
        ItemStack cursorItem = player.getItemOnCursor();
        int maxAmount = cursorItem.getMaxStackSize();
        int cursorAmount = cursorItem.getAmount();
        if(inventory == gui.getInventory())
        {
            int row = layer.getRowFromSlot(slot);
            int col = layer.getColFromSlot(slot);
            GUIItem guiItem = layer.getItem(row, col);
            ItemStack bottomItem = guiItem.getItemBase();
            int bottomAmount = bottomItem.getAmount();
            cursorItem.setAmount(bottomAmount - (maxAmount - cursorAmount));
            guiItem.setAmount(maxAmount);
        }
        else
        {
            ItemStack bottomItem = inventory.getItem(slot);
            int bottomAmount = bottomItem.getAmount();
            cursorItem.setAmount(bottomAmount - (maxAmount - cursorAmount));
            bottomItem.setAmount(maxAmount);
        }
        player.sendMessage("Executed Place Some");
    }

    @Override
    public void executePlaceOne(Player player, Inventory inventory, int slot, GUIContainer gui, GUILayer layer)
    {
        ItemStack cursorItem = player.getItemOnCursor();
        int cursorAmount = cursorItem.getAmount();
        if(inventory == gui.getInventory())
        {
            int row = layer.getRowFromSlot(slot);
            int col = layer.getColFromSlot(slot);
            GUIItem guiItem = layer.getItem(row, col);
            if(guiItem == null)
            {
                guiItem = new GUIItem(cursorItem.clone());
                guiItem.setMovable(true);
                guiItem.setAmount(1);
                layer.setItem(row, col, guiItem);
            }
            else
            {
                guiItem.setAmount(guiItem.getAmount() + 1);
            }
        }
        else
        {
            ItemStack bottomItem = inventory.getItem(slot);
            if(bottomItem == null)
            {
                bottomItem = cursorItem.clone();
                bottomItem.setAmount(1);
                inventory.setItem(slot, bottomItem);
            }
            else
            {
                bottomItem.setAmount(bottomItem.getAmount() + 1);
            }
        }
        cursorItem.setAmount(cursorAmount - 1);
        player.sendMessage("Executed Place One");
    }

    @Override
    public void executeSwapWithCursor(Player player, Inventory inventory, int slot, GUIContainer gui, GUILayer layer)
    {
        ItemStack cursorItem = player.getItemOnCursor();
        if(inventory == gui.getInventory())
        {
            int row = layer.getRowFromSlot(slot);
            int col = layer.getColFromSlot(slot);
            GUIItem guiItem = layer.getItem(row, col);
            ItemStack bottomItem = guiItem.getItemBase();
            guiItem.setItem(cursorItem);
            player.setItemOnCursor(bottomItem);
        }
        else
        {
            ItemStack bottomItem = inventory.getItem(slot);
            inventory.setItem(slot, cursorItem);
            player.setItemOnCursor(bottomItem);
        }
        player.sendMessage("Executed Swap With Cursor");
    }

    @Override
    public void executeDropAllCursor(Player player, Inventory inventory, int slot, GUIContainer gui, GUILayer layer)
    {
        ItemStack cursorItem = player.getItemOnCursor();
        Location location = player.getEyeLocation();
        World world = location.getWorld();
        Item item = world.dropItem(location, cursorItem);
        item.setVelocity(location.getDirection());
        player.setItemOnCursor(null);
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
