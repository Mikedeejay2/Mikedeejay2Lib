package com.mikedeejay2.mikedeejay2lib.gui.interact.normal;

import com.mikedeejay2.mikedeejay2lib.gui.GUIContainer;
import com.mikedeejay2.mikedeejay2lib.gui.GUILayer;
import com.mikedeejay2.mikedeejay2lib.gui.interact.GUIInteractExecutor;
import com.mikedeejay2.mikedeejay2lib.gui.item.GUIItem;
import com.mikedeejay2.mikedeejay2lib.util.item.ItemComparison;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

/**
 * A <tt>GUIInteractExecutor</tt> that moves items with a custom limit specified on construction.
 *
 * @author Mikedeejay2
 */
public class GUIInteractExecutorDefault implements GUIInteractExecutor
{
    protected int limit;

    public GUIInteractExecutorDefault(int limit)
    {
        this.limit = Math.min(limit, 64);
    }

    public GUIInteractExecutorDefault()
    {
        this.limit = -1;
    }

    @Override
    public void executeNothing(Player player, Inventory inventory, int slot, InventoryClickEvent event, GUIContainer gui, GUILayer layer)
    {

    }

    @Override
    public void executePickupAll(Player player, Inventory inventory, int slot, InventoryClickEvent event, GUIContainer gui, GUILayer layer)
    {
        ItemStack cursorItem;
        if(inventory == gui.getInventory())
        {
            int row = layer.getRowFromSlot(slot);
            int col = layer.getColFromSlot(slot);
            GUIItem guiItem = layer.getItem(row, col);
            ItemStack bottomItem = guiItem.getItemBase();
            int curAmount = bottomItem.getAmount();
            int maxAmount = limit == -1 ? bottomItem.getMaxStackSize() : limit;
            if(curAmount > maxAmount)
            {
                guiItem.setAmount(curAmount - maxAmount);
                curAmount = maxAmount;
            }
            else layer.removeItem(row, col);
            cursorItem = bottomItem.clone();
            cursorItem.setAmount(curAmount);
        }
        else
        {
            ItemStack bottomItem = inventory.getItem(slot);
            int curAmount = bottomItem.getAmount();
            int maxAmount = limit == -1 ? bottomItem.getMaxStackSize() : limit;
            if(curAmount > maxAmount)
            {
                bottomItem.setAmount(curAmount - maxAmount);
                curAmount = maxAmount;
            }
            else inventory.setItem(slot, null);
            cursorItem = bottomItem.clone();
            cursorItem.setAmount(curAmount);
        }
        player.setItemOnCursor(cursorItem);
    }

    @Override
    public void executePickupSome(Player player, Inventory inventory, int slot, InventoryClickEvent event, GUIContainer gui, GUILayer layer)
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
            int maxAmount = limit == -1 ? cursorItem.getMaxStackSize() : limit;
            cursorItem.setAmount(maxAmount);
            guiItem.setAmount(bottomAmount - (maxAmount - cursorAmount));
        }
        else
        {
            ItemStack bottomItem = inventory.getItem(slot);
            int bottomAmount = bottomItem.getAmount();
            int maxAmount = limit == -1 ? cursorItem.getMaxStackSize() : limit;
            cursorItem.setAmount(maxAmount);
            bottomItem.setAmount(bottomAmount - (maxAmount - cursorAmount));
        }
        player.setItemOnCursor(cursorItem);
    }

    @Override
    public void executePickupHalf(Player player, Inventory inventory, int slot, InventoryClickEvent event, GUIContainer gui, GUILayer layer)
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
            int maxAmount = limit == -1 ? bottomItem.getMaxStackSize() : limit;
            if(halfTop > maxAmount)
            {
                halfBottom += halfTop - maxAmount;
                halfTop = maxAmount;
            }
            cursorItem = bottomItem.clone();
            cursorItem.setAmount(halfTop);
            player.setItemOnCursor(cursorItem);
            guiItem.setAmount(halfBottom);
        }
        else
        {
            ItemStack bottomItem = inventory.getItem(slot);
            int bottomAmount = bottomItem.getAmount();
            int maxAmount = limit == -1 ? bottomItem.getMaxStackSize() : limit;
            int halfTop = (int) Math.ceil(bottomAmount / 2.0);
            int halfBottom = (int) Math.floor(bottomAmount / 2.0);
            if(halfTop > maxAmount)
            {
                halfBottom += halfTop - maxAmount;
                halfTop = maxAmount;
            }
            cursorItem = bottomItem.clone();
            cursorItem.setAmount(halfTop);
            player.setItemOnCursor(cursorItem);
            bottomItem.setAmount(halfBottom);
        }
        player.setItemOnCursor(cursorItem);
    }

    @Override
    public void executePickupOne(Player player, Inventory inventory, int slot, InventoryClickEvent event, GUIContainer gui, GUILayer layer)
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
        player.setItemOnCursor(cursorItem);
    }

    @Override
    public void executePlaceAll(Player player, Inventory inventory, int slot, InventoryClickEvent event, GUIContainer gui, GUILayer layer)
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
            GUIItem newItem = new GUIItem(cursorItem.clone());
            newItem.setMovable(true);
            int curAmount = cursorAmount + bottomAmount;
            int newAmount = curAmount;
            int extraAmount = 0;
            int maxAmount = limit == -1 ? cursorItem.getMaxStackSize() : limit;
            if(curAmount > maxAmount)
            {
                extraAmount = curAmount - maxAmount;
                newAmount = maxAmount;
            }
            cursorItem.setAmount(extraAmount);
            newItem.setAmount(newAmount);
            layer.setItem(row, col, newItem);
        }
        else
        {
            ItemStack curItem = inventory.getItem(slot);
            int bottomAmount = 0;
            if(curItem != null) bottomAmount = curItem.getAmount();
            ItemStack bottomItem = cursorItem.clone();
            int extraAmount = 0;
            int newAmount = cursorAmount + bottomAmount;
            int maxAmount = limit == -1 ? cursorItem.getMaxStackSize() : limit;
            if(newAmount > maxAmount)
            {
                extraAmount = newAmount - maxAmount;
                newAmount = maxAmount;
            }
            bottomItem.setAmount(newAmount);
            cursorItem.setAmount(extraAmount);
            inventory.setItem(slot, bottomItem);
        }
        player.setItemOnCursor(cursorItem);
    }

    @Override
    public void executePlaceSome(Player player, Inventory inventory, int slot, InventoryClickEvent event, GUIContainer gui, GUILayer layer)
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
            int maxAmount = limit == -1 ? cursorItem.getMaxStackSize() : limit;
            cursorItem.setAmount(bottomAmount - (maxAmount - cursorAmount));
            guiItem.setAmount(maxAmount);
        }
        else
        {
            ItemStack bottomItem = inventory.getItem(slot);
            int bottomAmount = bottomItem.getAmount();
            int maxAmount = limit == -1 ? cursorItem.getMaxStackSize() : limit;
            cursorItem.setAmount(bottomAmount - (maxAmount - cursorAmount));
            bottomItem.setAmount(maxAmount);
        }
        player.setItemOnCursor(cursorItem);
    }

    @Override
    public void executePlaceOne(Player player, Inventory inventory, int slot, InventoryClickEvent event, GUIContainer gui, GUILayer layer)
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
        player.setItemOnCursor(cursorItem);
    }

    @Override
    public void executeSwapWithCursor(Player player, Inventory inventory, int slot, InventoryClickEvent event, GUIContainer gui, GUILayer layer)
    {
        ItemStack cursorItem = player.getItemOnCursor();
        if(inventory == gui.getInventory())
        {
            int maxAmount = limit == -1 ? cursorItem.getMaxStackSize() : limit;
            if(cursorItem.getAmount() > maxAmount) return;
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
    }

    @Override
    public void executeDropAllCursor(Player player, Inventory inventory, int slot, InventoryClickEvent event, GUIContainer gui, GUILayer layer)
    {
        ItemStack cursorItem = player.getItemOnCursor();
        Location location = player.getEyeLocation();
        World world = location.getWorld();
        ItemStack itemToDrop = cursorItem.clone();
        int curAmount = cursorItem.getAmount();
        int maxAmount = limit == -1 ? cursorItem.getMaxStackSize() : limit;
        if(curAmount > maxAmount)
        {
            itemToDrop.setAmount(maxAmount);
            cursorItem.setAmount(curAmount - maxAmount);
        }
        else
        {
            player.setItemOnCursor(null);
        }
        Item item = world.dropItem(location, itemToDrop);
        item.setVelocity(location.getDirection().multiply(1.0/3.0));
    }

    @Override
    public void executeDropOneCursor(Player player, Inventory inventory, int slot, InventoryClickEvent event, GUIContainer gui, GUILayer layer)
    {
        ItemStack cursorItem = player.getItemOnCursor();
        ItemStack itemToDrop = cursorItem.clone();
        itemToDrop.setAmount(1);
        cursorItem.setAmount(cursorItem.getAmount() - 1);
        Location location = player.getEyeLocation();
        World world = location.getWorld();
        Item item = world.dropItem(location, itemToDrop);
        item.setVelocity(location.getDirection().multiply(1.0/3.0));
        player.setItemOnCursor(cursorItem);
    }

    @Override
    public void executeDropAllSlot(Player player, Inventory inventory, int slot, InventoryClickEvent event, GUIContainer gui, GUILayer layer)
    {
        ItemStack itemToDrop;
        if(inventory == gui.getInventory())
        {
            int row = layer.getRowFromSlot(slot);
            int col = layer.getColFromSlot(slot);
            GUIItem guiItem = layer.getItem(row, col);
            ItemStack item = guiItem.getItemBase();
            int curAmount = item.getAmount();
            int itemToDropAmount = curAmount;
            int maxAmount = limit == -1 ? item.getMaxStackSize() : limit;
            if(curAmount > maxAmount)
            {
                guiItem.setAmount(curAmount - maxAmount);
                itemToDropAmount = maxAmount;
            }
            else layer.removeItem(row, col);
            itemToDrop = item.clone();
            itemToDrop.setAmount(itemToDropAmount);
        }
        else
        {
            ItemStack item = inventory.getItem(slot);
            int curAmount = item.getAmount();
            int itemToDropAmount = curAmount;
            int maxAmount = limit == -1 ? item.getMaxStackSize() : limit;
            if(curAmount > maxAmount)
            {
                item.setAmount(curAmount - maxAmount);
                itemToDropAmount = maxAmount;
            }
            else inventory.setItem(slot, null);
            itemToDrop = item.clone();
            itemToDrop.setAmount(itemToDropAmount);
        }
        Location location = player.getEyeLocation();
        World world = location.getWorld();
        Item item = world.dropItem(location, itemToDrop);
        item.setVelocity(location.getDirection().multiply(1.0/3.0));
    }

    @Override
    public void executeDropOneSlot(Player player, Inventory inventory, int slot, InventoryClickEvent event, GUIContainer gui, GUILayer layer)
    {
        ItemStack itemToDrop;
        if(inventory == gui.getInventory())
        {
            int row = layer.getRowFromSlot(slot);
            int col = layer.getColFromSlot(slot);
            GUIItem guiItem = layer.getItem(row, col);
            itemToDrop = guiItem.getItemBase().clone();
            itemToDrop.setAmount(1);
            guiItem.setAmount(guiItem.getAmount() - 1);
        }
        else
        {
            ItemStack origItem = inventory.getItem(slot);
            itemToDrop = origItem.clone();
            itemToDrop.setAmount(1);
            origItem.setAmount(origItem.getAmount() - 1);
        }
        Location location = player.getEyeLocation();
        World world = location.getWorld();
        Item item = world.dropItem(location, itemToDrop);
        item.setVelocity(location.getDirection().multiply(1.0/3.0));
    }

    @Override
    public void executeMoveToOtherInventory(Player player, Inventory inventory, int slot, InventoryClickEvent event, GUIContainer gui, GUILayer layer)
    {
        if(inventory == gui.getInventory())
        {
            int row = layer.getRowFromSlot(slot);
            int col = layer.getColFromSlot(slot);
            GUIItem guiItem = layer.getItem(row, col);
            ItemStack itemToMove = guiItem.getItemBase();
            Inventory playerInv = player.getInventory();
            for(int i = 0; i < playerInv.getStorageContents().length; ++i)
            {
                ItemStack curItem = playerInv.getItem(i);
                if(curItem == null) continue;
                int maxAmount = limit == -1 ? curItem.getMaxStackSize() : limit;
                if(curItem.getAmount() >= maxAmount) continue;
                if(!ItemComparison.equalsEachOther(curItem, itemToMove)) continue;
                int newAmount = curItem.getAmount() + itemToMove.getAmount();
                int extraAmount = 0;
                if(newAmount > maxAmount)
                {
                    extraAmount = newAmount - maxAmount;
                    newAmount = maxAmount;
                }
                itemToMove.setAmount(extraAmount);
                curItem.setAmount(newAmount);
                if(itemToMove.getAmount() <= 0)
                {
                    layer.removeItem(row, col);
                    return;
                }
            }
            if(itemToMove.getAmount() <= 0)
            {
                layer.removeItem(row, col);
                return;
            }
            for(int i = 0; i < playerInv.getStorageContents().length; ++i)
            {
                ItemStack curItem = playerInv.getItem(i);
                if(curItem != null) continue;
                curItem = itemToMove.clone();
                int newAmount = itemToMove.getAmount();
                int extraAmount = 0;
                int maxAmount = limit == -1 ? itemToMove.getMaxStackSize() : limit;
                if(newAmount > maxAmount)
                {
                    extraAmount = newAmount - maxAmount;
                    newAmount = maxAmount;
                }
                itemToMove.setAmount(extraAmount);
                curItem.setAmount(newAmount);
                playerInv.setItem(i, curItem);
                if(itemToMove.getAmount() <= 0)
                {
                    layer.removeItem(row, col);
                    return;
                }
            }
        }
        else
        {
            ItemStack itemToMove = inventory.getItem(slot);
            for(int row = 1; row <= layer.getRows(); ++row)
            {
                for(int col = 1; col <= layer.getCols(); ++col)
                {
                    GUIItem curGUIItem = layer.getItem(row, col);
                    ItemStack curItem = curGUIItem == null ? null : curGUIItem.getItemBase();
                    if(curItem == null || !curGUIItem.isMovable()) continue;
                    if(!ItemComparison.equalsEachOther(curItem, itemToMove)) continue;
                    int newAmount = curGUIItem.getAmount() + itemToMove.getAmount();
                    int extraAmount = 0;
                    int maxAmount = limit == -1 ? curItem.getMaxStackSize() : limit;
                    if(newAmount > maxAmount)
                    {
                        extraAmount = newAmount - maxAmount;
                        newAmount = maxAmount;
                    }
                    itemToMove.setAmount(extraAmount);
                    curGUIItem.setAmount(newAmount);
                    if(itemToMove.getAmount() <= 0) return;
                }
            }
            if(itemToMove.getAmount() <= 0 || !layer.getDefaultMoveState()) return;
            for(int row = 1; row <= layer.getRows(); ++row)
            {
                for(int col = 1; col <= layer.getCols(); ++col)
                {
                    GUIItem curGUIItem = layer.getItem(row, col);
                    ItemStack curItem = curGUIItem == null ? null : curGUIItem.getItemBase();
                    if(curItem != null || (curGUIItem != null && !curGUIItem.isMovable())) continue;
                    if(curGUIItem == null)
                    {
                        curGUIItem = new GUIItem(itemToMove.clone());
                        curGUIItem.setMovable(true);
                        layer.setItem(row, col, curGUIItem);
                    }
                    int newAmount = itemToMove.getAmount();
                    int extraAmount = 0;
                    int maxAmount = limit == -1 ? itemToMove.getMaxStackSize() : limit;
                    if(newAmount > maxAmount)
                    {
                        extraAmount = newAmount - maxAmount;
                        newAmount = maxAmount;
                    }
                    itemToMove.setAmount(extraAmount);
                    curGUIItem.setAmount(newAmount);
                    if(itemToMove.getAmount() <= 0) return;
                }
            }
        }
    }

    @Override
    public void executeHotbarMoveAndReadd(Player player, Inventory inventory, int slot, InventoryClickEvent event, GUIContainer gui, GUILayer layer)
    {
        int row = layer.getRowFromSlot(slot);
        int col = layer.getColFromSlot(slot);
        int hotbarSlot = event.getHotbarButton();
        Inventory playerInv = player.getInventory();
        ItemStack hotbarItem = playerInv.getItem(hotbarSlot);
        GUIItem guiItem = layer.getItem(row, col);
        ItemStack topItem = guiItem.getItemBase();
        int maxAmount = limit == -1 ? hotbarItem.getMaxStackSize() : limit;
        if(hotbarItem.getAmount() > maxAmount) return;
        guiItem.setItem(hotbarItem);
        playerInv.setItem(hotbarSlot, topItem);
    }

    @Override
    public void executeHotbarSwap(Player player, Inventory inventory, int slot, InventoryClickEvent event, GUIContainer gui, GUILayer layer)
    {
        int hotbarSlot = event.getHotbarButton();
        Inventory playerInv = player.getInventory();
        if(inventory == gui.getInventory())
        {
            int row = layer.getRowFromSlot(slot);
            int col = layer.getColFromSlot(slot);
            GUIItem guiItem = layer.getItem(row, col);
            if(guiItem == null)
            {
                guiItem = new GUIItem(null);
                guiItem.setMovable(true);
                layer.setItem(row, col, guiItem);
            }
            ItemStack item = guiItem.getItemBase();
            ItemStack curItem = playerInv.getItem(hotbarSlot);
            playerInv.setItem(hotbarSlot, item);
            if(curItem == null)
            {
                layer.removeItem(row, col);
            }
            else
            {
                int curAmount = curItem.getAmount();
                int maxAmount = limit == -1 ? curItem.getMaxStackSize() : limit;
                if(curAmount > maxAmount)
                {
                    int extraAmount = curAmount - maxAmount;
                    curAmount = maxAmount;
                    curItem.setAmount(curAmount);
                    ItemStack extraItem = curItem.clone();
                    extraItem.setAmount(extraAmount);
                    playerInv.setItem(hotbarSlot, extraItem);
                }
                guiItem.setItem(curItem);
            }
        }
        else
        {
            ItemStack curItem = playerInv.getItem(hotbarSlot);
            ItemStack item = playerInv.getItem(slot);
            playerInv.setItem(hotbarSlot, item);
            playerInv.setItem(slot, curItem);
        }
    }

    @Override
    public void executeCloneStack(Player player, Inventory inventory, int slot, InventoryClickEvent event, GUIContainer gui, GUILayer layer)
    {
        Inventory playerInv = player.getInventory();
        if(inventory == gui.getInventory())
        {
            int row = layer.getRowFromSlot(slot);
            int col = layer.getColFromSlot(slot);
            GUIItem guiItem = layer.getItem(row, col);
            ItemStack item = guiItem.getItemBase().clone();
            int maxAmount = limit == -1 ? item.getMaxStackSize() : limit;
            item.setAmount(maxAmount);
            player.setItemOnCursor(item);
        }
        else
        {
            ItemStack item = playerInv.getItem(slot).clone();
            int maxAmount = limit == -1 ? item.getMaxStackSize() : limit;
            item.setAmount(maxAmount);
            player.setItemOnCursor(item);
        }
    }

    @Override
    public void executeCollectToCursor(Player player, Inventory inventory, int slot, InventoryClickEvent event, GUIContainer gui, GUILayer layer)
    {
        ItemStack cursorItem = player.getItemOnCursor();
        Inventory playerInv = player.getInventory();
        int maxAmount = limit == -1 ? cursorItem.getMaxStackSize() : limit;
        if(cursorItem.getAmount() >= maxAmount) return;
        for(int amount = 1; amount <= maxAmount; ++amount)
        {
            for(int row = 1; row <= layer.getRows(); ++row)
            {
                for(int col = 1; col <= layer.getCols(); ++col)
                {
                    GUIItem curGuiItem = layer.getItem(row, col);
                    if(curGuiItem == null) continue;
                    if(!curGuiItem.isMovable()) continue;
                    ItemStack curItem = curGuiItem.getItemBase();
                    if(curItem == null) continue;
                    if(curItem.getAmount() != amount) continue;
                    if(!ItemComparison.equalsEachOther(cursorItem, curItem)) continue;
                    int newAmount = curItem.getAmount() + cursorItem.getAmount();
                    int extraAmount = 0;
                    if(newAmount > maxAmount)
                    {
                        extraAmount = newAmount - maxAmount;
                        newAmount = maxAmount;
                    }
                    cursorItem.setAmount(newAmount);
                    curGuiItem.setAmount(extraAmount);
                    if(extraAmount <= 0) layer.removeItem(row, col);
                    if(cursorItem.getAmount() == maxAmount) return;
                }
            }

            for(int i = 0; i < playerInv.getStorageContents().length; ++i)
            {
                ItemStack curItem = playerInv.getItem(i);
                if(curItem == null) continue;
                if(curItem.getAmount() != amount) continue;
                if(!ItemComparison.equalsEachOther(cursorItem, curItem)) continue;
                int newAmount = curItem.getAmount() + cursorItem.getAmount();
                int extraAmount = 0;
                if(newAmount > maxAmount)
                {
                    extraAmount = newAmount - maxAmount;
                    newAmount = maxAmount;
                }
                cursorItem.setAmount(newAmount);
                curItem.setAmount(extraAmount);
            }
        }
    }

    @Override
    public void executeUnknown(Player player, Inventory inventory, int slot, InventoryClickEvent event, GUIContainer gui, GUILayer layer)
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
