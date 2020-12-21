package com.mikedeejay2.mikedeejay2lib.gui.interact.normal;

import com.mikedeejay2.mikedeejay2lib.gui.GUIContainer;
import com.mikedeejay2.mikedeejay2lib.gui.GUILayer;
import com.mikedeejay2.mikedeejay2lib.gui.interact.GUIInteractExecutor;
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
public class GUIInteractExecutorDefaultInv implements GUIInteractExecutor
{
    protected int limit;

    public GUIInteractExecutorDefaultInv(int limit)
    {
        this.limit = Math.min(limit, 64);
    }

    public GUIInteractExecutorDefaultInv()
    {
        this.limit = -1;
    }

    @Override
    public void executePickupAll(Player player, Inventory inventory, int slot, InventoryClickEvent event, GUIContainer gui, GUILayer layer)
    {
        if(inventory != player.getInventory()) return;
        ItemStack bottomItem = inventory.getItem(slot);
        int       curAmount  = bottomItem.getAmount();
        int       maxAmount  = limit == -1 ? bottomItem.getMaxStackSize() : limit;
        if(curAmount > maxAmount)
        {
            bottomItem.setAmount(curAmount - maxAmount);
            curAmount = maxAmount;
        }
        else inventory.setItem(slot, null);
        ItemStack cursorItem = bottomItem.clone();
        cursorItem.setAmount(curAmount);
        player.setItemOnCursor(cursorItem);
    }

    @Override
    public void executePickupSome(Player player, Inventory inventory, int slot, InventoryClickEvent event, GUIContainer gui, GUILayer layer)
    {
        if(inventory != player.getInventory()) return;
        ItemStack cursorItem   = player.getItemOnCursor();
        int       cursorAmount = cursorItem.getAmount();
        ItemStack bottomItem   = inventory.getItem(slot);
        int       bottomAmount = bottomItem.getAmount();
        int       maxAmount    = limit == -1 ? cursorItem.getMaxStackSize() : limit;
        cursorItem.setAmount(maxAmount);
        bottomItem.setAmount(bottomAmount - (maxAmount - cursorAmount));
        player.setItemOnCursor(cursorItem);
    }

    @Override
    public void executePickupHalf(Player player, Inventory inventory, int slot, InventoryClickEvent event, GUIContainer gui, GUILayer layer)
    {
        if(inventory != player.getInventory()) return;
        ItemStack bottomItem   = inventory.getItem(slot);
        int       bottomAmount = bottomItem.getAmount();
        int       maxAmount    = limit == -1 ? bottomItem.getMaxStackSize() : limit;
        int       halfTop      = (int) Math.ceil(bottomAmount / 2.0);
        int       halfBottom   = (int) Math.floor(bottomAmount / 2.0);
        if(halfTop > maxAmount)
        {
            halfBottom += halfTop - maxAmount;
            halfTop = maxAmount;
        }
        ItemStack cursorItem = bottomItem.clone();
        cursorItem.setAmount(halfTop);
        player.setItemOnCursor(cursorItem);
        bottomItem.setAmount(halfBottom);
        player.setItemOnCursor(cursorItem);
    }

    @Override
    public void executePickupOne(Player player, Inventory inventory, int slot, InventoryClickEvent event, GUIContainer gui, GUILayer layer)
    {
        if(inventory != player.getInventory()) return;
        ItemStack cursorItem   = player.getItemOnCursor();
        int       cursorAmount = cursorItem.getAmount();
        ItemStack bottomItem   = inventory.getItem(slot);
        int       bottomAmount = bottomItem.getAmount();
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
        player.setItemOnCursor(cursorItem);
    }

    @Override
    public void executePlaceAll(Player player, Inventory inventory, int slot, InventoryClickEvent event, GUIContainer gui, GUILayer layer)
    {
        if(inventory != player.getInventory()) return;
        ItemStack cursorItem   = player.getItemOnCursor();
        int       cursorAmount = cursorItem.getAmount();
        ItemStack curItem      = inventory.getItem(slot);
        int       bottomAmount = 0;
        if(curItem != null) bottomAmount = curItem.getAmount();
        ItemStack bottomItem  = cursorItem.clone();
        int       extraAmount = 0;
        int       newAmount   = cursorAmount + bottomAmount;
        int       maxAmount   = limit == -1 ? cursorItem.getMaxStackSize() : limit;
        if(newAmount > maxAmount)
        {
            extraAmount = newAmount - maxAmount;
            newAmount = maxAmount;
        }
        bottomItem.setAmount(newAmount);
        cursorItem.setAmount(extraAmount);
        inventory.setItem(slot, bottomItem);
        player.setItemOnCursor(cursorItem);
    }

    @Override
    public void executePlaceSome(Player player, Inventory inventory, int slot, InventoryClickEvent event, GUIContainer gui, GUILayer layer)
    {
        if(inventory != player.getInventory()) return;
        ItemStack cursorItem   = player.getItemOnCursor();
        int       cursorAmount = cursorItem.getAmount();
        ItemStack bottomItem   = inventory.getItem(slot);
        int       bottomAmount = bottomItem.getAmount();
        int       maxAmount    = limit == -1 ? cursorItem.getMaxStackSize() : limit;
        cursorItem.setAmount(bottomAmount - (maxAmount - cursorAmount));
        bottomItem.setAmount(maxAmount);
        player.setItemOnCursor(cursorItem);
    }

    @Override
    public void executePlaceOne(Player player, Inventory inventory, int slot, InventoryClickEvent event, GUIContainer gui, GUILayer layer)
    {
        if(inventory != player.getInventory()) return;
        ItemStack cursorItem   = player.getItemOnCursor();
        int       cursorAmount = cursorItem.getAmount();
        ItemStack bottomItem   = inventory.getItem(slot);
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
        cursorItem.setAmount(cursorAmount - 1);
        player.setItemOnCursor(cursorItem);
    }

    @Override
    public void executeSwapWithCursor(Player player, Inventory inventory, int slot, InventoryClickEvent event, GUIContainer gui, GUILayer layer)
    {
        if(inventory != player.getInventory()) return;
        ItemStack cursorItem      = player.getItemOnCursor();
        int       maxAmountCursor = limit == -1 ? cursorItem.getAmount() : limit;
        ItemStack bottomItem      = inventory.getItem(slot);
        int       maxAmountBottom = limit == -1 ? bottomItem.getAmount() : limit;
        if(cursorItem.getAmount() > maxAmountCursor || bottomItem.getAmount() > maxAmountBottom) return;
        inventory.setItem(slot, cursorItem);
        player.setItemOnCursor(bottomItem);
    }

    @Override
    public void executeDropAllCursor(Player player, Inventory inventory, int slot, InventoryClickEvent event, GUIContainer gui, GUILayer layer)
    {
        ItemStack cursorItem = player.getItemOnCursor();
        Location  location   = player.getEyeLocation();
        World     world      = location.getWorld();
        ItemStack itemToDrop = cursorItem.clone();
        int       curAmount  = cursorItem.getAmount();
        int       maxAmount  = limit == -1 ? cursorItem.getMaxStackSize() : limit;
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
        item.setVelocity(location.getDirection().multiply(1.0 / 3.0));
    }

    @Override
    public void executeDropOneCursor(Player player, Inventory inventory, int slot, InventoryClickEvent event, GUIContainer gui, GUILayer layer)
    {
        ItemStack cursorItem = player.getItemOnCursor();
        ItemStack itemToDrop = cursorItem.clone();
        itemToDrop.setAmount(1);
        cursorItem.setAmount(cursorItem.getAmount() - 1);
        Location location = player.getEyeLocation();
        World    world    = location.getWorld();
        Item     item     = world.dropItem(location, itemToDrop);
        item.setVelocity(location.getDirection().multiply(1.0 / 3.0));
        player.setItemOnCursor(cursorItem);
    }

    @Override
    public void executeDropAllSlot(Player player, Inventory inventory, int slot, InventoryClickEvent event, GUIContainer gui, GUILayer layer)
    {
        if(inventory != player.getInventory()) return;
        ItemStack itemToDrop;
        ItemStack stack            = inventory.getItem(slot);
        int       curAmount        = stack.getAmount();
        int       itemToDropAmount = curAmount;
        int       maxAmount        = limit == -1 ? stack.getMaxStackSize() : limit;
        if(curAmount > maxAmount)
        {
            stack.setAmount(curAmount - maxAmount);
            itemToDropAmount = maxAmount;
        }
        else inventory.setItem(slot, null);
        itemToDrop = stack.clone();
        itemToDrop.setAmount(itemToDropAmount);
        Location location = player.getEyeLocation();
        World    world    = location.getWorld();
        Item     item     = world.dropItem(location, itemToDrop);
        item.setVelocity(location.getDirection().multiply(1.0 / 3.0));
    }

    @Override
    public void executeDropOneSlot(Player player, Inventory inventory, int slot, InventoryClickEvent event, GUIContainer gui, GUILayer layer)
    {
        if(inventory != player.getInventory()) return;
        ItemStack origItem   = inventory.getItem(slot);
        ItemStack itemToDrop = origItem.clone();
        itemToDrop.setAmount(1);
        origItem.setAmount(origItem.getAmount() - 1);
        Location location = player.getEyeLocation();
        World    world    = location.getWorld();
        Item     item     = world.dropItem(location, itemToDrop);
        item.setVelocity(location.getDirection().multiply(1.0 / 3.0));
    }

    @Override
    public void executeHotbarSwap(Player player, Inventory inventory, int slot, InventoryClickEvent event, GUIContainer gui, GUILayer layer)
    {
        if(inventory != player.getInventory()) return;
        int       hotbarSlot = event.getHotbarButton();
        Inventory playerInv  = player.getInventory();
        ItemStack curItem    = playerInv.getItem(hotbarSlot);
        ItemStack item       = playerInv.getItem(slot);
        playerInv.setItem(hotbarSlot, item);
        playerInv.setItem(slot, curItem);
    }

    @Override
    public void executeCloneStack(Player player, Inventory inventory, int slot, InventoryClickEvent event, GUIContainer gui, GUILayer layer)
    {
        if(inventory != player.getInventory()) return;
        Inventory playerInv = player.getInventory();
        ItemStack item      = playerInv.getItem(slot).clone();
        int       maxAmount = limit == -1 ? item.getMaxStackSize() : limit;
        item.setAmount(maxAmount);
        player.setItemOnCursor(item);
    }

    @Override
    public void executeCollectToCursor(Player player, Inventory inventory, int slot, InventoryClickEvent event, GUIContainer gui, GUILayer layer)
    {
        ItemStack cursorItem = player.getItemOnCursor();
        Inventory playerInv  = player.getInventory();
        int       maxAmount  = limit == -1 ? cursorItem.getMaxStackSize() : limit;
        if(cursorItem.getAmount() >= maxAmount) return;
        for(int amount = 1; amount <= maxAmount; ++amount)
        {
            for(int i = 0; i < playerInv.getStorageContents().length; ++i)
            {
                ItemStack curItem = playerInv.getItem(i);
                if(curItem == null) continue;
                if(curItem.getAmount() != amount) continue;
                if(!ItemComparison.equalsEachOther(cursorItem, curItem)) continue;
                int newAmount   = curItem.getAmount() + cursorItem.getAmount();
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
    public void executeNothing(Player player, Inventory inventory, int slot, InventoryClickEvent event, GUIContainer gui, GUILayer layer)              {}

    @Override
    public void executeMoveToOtherInventory(Player player, Inventory inventory, int slot, InventoryClickEvent event, GUIContainer gui, GUILayer layer) {}

    @Override
    public void executeHotbarMoveAndReadd(Player player, Inventory inventory, int slot, InventoryClickEvent event, GUIContainer gui, GUILayer layer)   {}

    @Override
    public void executeUnknown(Player player, Inventory inventory, int slot, InventoryClickEvent event, GUIContainer gui, GUILayer layer)              {}

    public int getLimit()
    {
        return limit;
    }

    public void setLimit(int limit)
    {
        this.limit = limit;
    }
}
