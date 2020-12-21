package com.mikedeejay2.mikedeejay2lib.gui.interact.list;

import com.mikedeejay2.mikedeejay2lib.gui.GUIContainer;
import com.mikedeejay2.mikedeejay2lib.gui.GUILayer;
import com.mikedeejay2.mikedeejay2lib.gui.interact.GUIInteractExecutor;
import com.mikedeejay2.mikedeejay2lib.gui.interact.GUIInteractType;
import com.mikedeejay2.mikedeejay2lib.gui.item.GUIItem;
import com.mikedeejay2.mikedeejay2lib.gui.modules.GUIListModule;
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
 * This executor is specifically for interacting with list modules. Do no attempt to use this
 * executor on a non-list GUI.
 *
 * @author Mikedeejay2
 */
public class GUIInteractExecutorList implements GUIInteractExecutor
{
    protected GUIInteractType interactType;
    protected int limit;
    protected boolean consume;

    public GUIInteractExecutorList(int limit)
    {
        this.limit = Math.min(limit, 64);
        this.interactType = GUIInteractType.DEFAULT;
        this.consume = true;
    }

    public GUIInteractExecutorList()
    {
        this.limit = -1;
        this.interactType = GUIInteractType.DEFAULT;
        this.consume = true;
    }

    public GUIInteractExecutorList(int limit, boolean consume)
    {
        this.limit = Math.min(limit, 64);
        this.interactType = GUIInteractType.DEFAULT;
        this.consume = consume;
    }

    public GUIInteractExecutorList(boolean consume)
    {
        this.limit = -1;
        this.interactType = GUIInteractType.DEFAULT;
        this.consume = consume;
    }

    public GUIInteractExecutorList(GUIInteractType type, int limit)
    {
        this.limit = Math.min(limit, 64);
        this.interactType = type;
        this.consume = true;
    }

    public GUIInteractExecutorList(GUIInteractType type)
    {
        this.limit = -1;
        this.interactType = type;
        this.consume = true;
    }

    public GUIInteractExecutorList(GUIInteractType type, int limit, boolean consume)
    {
        this.limit = Math.min(limit, 64);
        this.interactType = type;
        this.consume = consume;
    }

    public GUIInteractExecutorList(GUIInteractType type, boolean consume)
    {
        this.limit = -1;
        this.interactType = type;
        this.consume = consume;
    }

    @Override
    public void executePickupAll(Player player, Inventory inventory, int slot, InventoryClickEvent event, GUIContainer gui, GUILayer layer)
    {
        if(inventory != gui.getInventory()) return;
        GUIListModule list       = gui.getModule(GUIListModule.class);
        ItemStack     cursorItem;
        int           row        = layer.getRowFromSlot(slot);
        int           col        = layer.getColFromSlot(slot);
        GUIItem       guiItem    = list.getItem(row, col, gui);
        ItemStack     bottomItem = guiItem.getItemBase();
        int           curAmount  = bottomItem.getAmount();
        int           maxAmount  = limit == -1 ? bottomItem.getMaxStackSize() : limit;
        if(curAmount > maxAmount)
        {
            guiItem.setAmount(curAmount - maxAmount);
            curAmount = maxAmount;
        }
        else list.removeListItem(row, col, gui);
        if(consume)
        {
            cursorItem = bottomItem.clone();
            cursorItem.setAmount(curAmount);
            player.setItemOnCursor(cursorItem);
        }
    }

    @Override
    public void executePickupSome(Player player, Inventory inventory, int slot, InventoryClickEvent event, GUIContainer gui, GUILayer layer)
    {
        if(inventory != gui.getInventory()) return;
        GUIListModule list         = gui.getModule(GUIListModule.class);
        ItemStack     cursorItem   = player.getItemOnCursor();
        int           cursorAmount = cursorItem.getAmount();
        int           row          = layer.getRowFromSlot(slot);
        int           col          = layer.getColFromSlot(slot);
        GUIItem       guiItem      = list.getItem(row, col, gui);
        ItemStack     bottomItem   = guiItem.getItemBase();
        int           bottomAmount = bottomItem.getAmount();
        int           maxAmount    = limit == -1 ? cursorItem.getMaxStackSize() : limit;
        guiItem.setAmount(bottomAmount - (maxAmount - cursorAmount));
        if(guiItem.getAmount() <= 0) list.removeListItem(row, col, gui);
        if(consume)
        {
            cursorItem.setAmount(maxAmount);
            player.setItemOnCursor(cursorItem);
        }
    }

    @Override
    public void executePickupHalf(Player player, Inventory inventory, int slot, InventoryClickEvent event, GUIContainer gui, GUILayer layer)
    {
        if(inventory != gui.getInventory()) return;
        GUIListModule list         = gui.getModule(GUIListModule.class);
        ItemStack     cursorItem;
        int           row          = layer.getRowFromSlot(slot);
        int           col          = layer.getColFromSlot(slot);
        GUIItem       guiItem      = list.getItem(row, col, gui);
        ItemStack     bottomItem   = guiItem.getItemBase();
        int           bottomAmount = bottomItem.getAmount();
        int           halfTop      = (int) Math.ceil(bottomAmount / 2.0);
        int           halfBottom   = (int) Math.floor(bottomAmount / 2.0);
        int           maxAmount    = limit == -1 ? bottomItem.getMaxStackSize() : limit;
        if(halfTop > maxAmount)
        {
            halfBottom += halfTop - maxAmount;
            halfTop = maxAmount;
        }
        guiItem.setAmount(halfBottom);
        if(halfBottom <= 0) list.removeListItem(row, col, gui);
        if(consume)
        {
            cursorItem = bottomItem.clone();
            cursorItem.setAmount(halfTop);
            player.setItemOnCursor(cursorItem);
        }
    }

    @Override
    public void executePickupOne(Player player, Inventory inventory, int slot, InventoryClickEvent event, GUIContainer gui, GUILayer layer)
    {
        if(inventory != gui.getInventory()) return;
        GUIListModule list         = gui.getModule(GUIListModule.class);
        ItemStack     cursorItem   = player.getItemOnCursor();
        int           cursorAmount = cursorItem.getAmount();
        int           row          = layer.getRowFromSlot(slot);
        int           col          = layer.getColFromSlot(slot);
        GUIItem       guiItem      = list.getItem(row, col, gui);
        ItemStack     bottomItem   = guiItem.getItemBase();
        int           bottomAmount = bottomItem.getAmount();
        if(cursorItem.getType() == Material.AIR && consume)
        {
            cursorItem = bottomItem.clone();
            cursorItem.setAmount(1);
            player.setItemOnCursor(cursorItem);
        }
        else if(consume)
        {
            cursorItem.setAmount(cursorAmount + 1);
        }
        guiItem.setAmount(bottomAmount - 1);
        if(bottomAmount - 1 <= 0) list.removeListItem(row, col, gui);
        if(consume) player.setItemOnCursor(cursorItem);
    }

    @Override
    public void executePlaceAll(Player player, Inventory inventory, int slot, InventoryClickEvent event, GUIContainer gui, GUILayer layer)
    {
        if(inventory != gui.getInventory()) return;
        GUIListModule list       = gui.getModule(GUIListModule.class);
        ItemStack     cursorItem = player.getItemOnCursor();
        int           row        = layer.getRowFromSlot(slot);
        int           col        = layer.getColFromSlot(slot);
        GUIItem       curItem    = list.getItem(row, col, gui);
        if(
                curItem == null ||
                        !ItemComparison.equalsEachOther(curItem.getItemBase(), cursorItem) ||
                        curItem.getItemBase().getAmount() == (limit == -1 ? curItem.getItemBase().getMaxStackSize() : limit)
        )
        {
            if(interactType == GUIInteractType.SINGLE_ITEM && list.containsItem(cursorItem)) return;
            if(interactType == GUIInteractType.SINGLE_MATERIAL && list.containsMaterial(cursorItem.getType())) return;
            GUIItem newItem = new GUIItem(cursorItem.clone());
            newItem.setMovable(true);
            int newAmount   = newItem.getAmount();
            int extraAmount = 0;
            int maxAmount   = limit == -1 ? cursorItem.getMaxStackSize() : limit;
            if(newAmount > maxAmount)
            {
                extraAmount = newAmount - maxAmount;
                newAmount = maxAmount;
            }
            newItem.setAmount(newAmount);
            if(consume) cursorItem.setAmount(extraAmount);
            int index = list.getListItemIndex(row, col, gui);
            int size  = list.getSize();
            if(index >= size)
            {
                list.addListItem(newItem);
            }
            else
            {
                list.addListItem(row, col, gui, newItem);
            }
        }
        else
        {
            int cursorAmount = cursorItem.getAmount();
            int bottomAmount = curItem.getAmountBase();
            int newAmount    = cursorAmount + bottomAmount;
            int extraAmount  = 0;
            int maxAmount    = limit == -1 ? cursorItem.getMaxStackSize() : limit;
            if(newAmount > maxAmount)
            {
                extraAmount = newAmount - maxAmount;
                newAmount = maxAmount;
            }
            curItem.setAmount(newAmount);
            if(consume) cursorItem.setAmount(extraAmount);
        }
        if(consume) player.setItemOnCursor(cursorItem);
    }

    @Override
    public void executePlaceSome(Player player, Inventory inventory, int slot, InventoryClickEvent event, GUIContainer gui, GUILayer layer)
    {
        if(inventory != gui.getInventory()) return;
        GUIListModule list       = gui.getModule(GUIListModule.class);
        ItemStack     cursorItem = player.getItemOnCursor();
        if(interactType == GUIInteractType.SINGLE_ITEM && list.containsItem(cursorItem)) return;
        if(interactType == GUIInteractType.SINGLE_MATERIAL && list.containsMaterial(cursorItem.getType())) return;
        int       cursorAmount = cursorItem.getAmount();
        int       row          = layer.getRowFromSlot(slot);
        int       col          = layer.getColFromSlot(slot);
        GUIItem   guiItem      = list.getItem(row, col, gui);
        ItemStack bottomItem   = guiItem.getItemBase();
        int       bottomAmount = bottomItem.getAmount();
        int       maxAmount    = limit == -1 ? cursorItem.getMaxStackSize() : limit;
        guiItem.setAmount(maxAmount);
        if(consume)
        {
            cursorItem.setAmount(bottomAmount - (maxAmount - cursorAmount));
            player.setItemOnCursor(cursorItem);
        }
    }

    @Override
    public void executePlaceOne(Player player, Inventory inventory, int slot, InventoryClickEvent event, GUIContainer gui, GUILayer layer)
    {
        if(inventory != gui.getInventory()) return;
        GUIListModule list         = gui.getModule(GUIListModule.class);
        ItemStack     cursorItem   = player.getItemOnCursor();
        int           cursorAmount = cursorItem.getAmount();
        int           row          = layer.getRowFromSlot(slot);
        int           col          = layer.getColFromSlot(slot);
        GUIItem       guiItem      = list.getItem(row, col, gui);
        if(guiItem == null)
        {
            if(interactType == GUIInteractType.SINGLE_ITEM && list.containsItem(cursorItem)) return;
            if(interactType == GUIInteractType.SINGLE_MATERIAL && list.containsMaterial(cursorItem.getType())) return;
            guiItem = new GUIItem(cursorItem.clone());
            guiItem.setMovable(true);
            guiItem.setAmount(1);
            int index = list.getListItemIndex(row, col, gui);
            int size  = list.getList().size();
            if(index > size)
            {
                list.addListItem(guiItem);
            }
            else
            {
                list.addListItem(row, col, gui, guiItem);
            }
        }
        else
        {
            if(!ItemComparison.equalsEachOther(guiItem.getItemBase(), cursorItem)) return;
            int maxAmount = limit == -1 ? guiItem.getItemBase().getMaxStackSize() : limit;
            if(guiItem.getAmount() >= maxAmount) return;
            guiItem.setAmount(guiItem.getAmount() + 1);
        }
        if(consume)
        {
            cursorItem.setAmount(cursorAmount - 1);
            player.setItemOnCursor(cursorItem);
        }
    }

    @Override
    public void executeSwapWithCursor(Player player, Inventory inventory, int slot, InventoryClickEvent event, GUIContainer gui, GUILayer layer)
    {
        if(inventory != gui.getInventory()) return;
        GUIListModule list       = gui.getModule(GUIListModule.class);
        ItemStack     cursorItem = player.getItemOnCursor();
        if(interactType == GUIInteractType.SINGLE_ITEM && list.containsItem(cursorItem)) return;
        if(interactType == GUIInteractType.SINGLE_MATERIAL && list.containsMaterial(cursorItem.getType())) return;
        int maxAmount = limit == -1 ? cursorItem.getMaxStackSize() : limit;
        if(cursorItem.getAmount() > maxAmount) return;
        int       row        = layer.getRowFromSlot(slot);
        int       col        = layer.getColFromSlot(slot);
        GUIItem   guiItem    = list.getItem(row, col, gui);
        ItemStack bottomItem = guiItem.getItemBase();
        guiItem.setItem(consume ? cursorItem : cursorItem.clone());
        if(consume) player.setItemOnCursor(bottomItem);
    }

    @Override
    public void executeDropAllSlot(Player player, Inventory inventory, int slot, InventoryClickEvent event, GUIContainer gui, GUILayer layer)
    {
        if(inventory != gui.getInventory()) return;
        GUIListModule list             = gui.getModule(GUIListModule.class);
        ItemStack     itemToDrop;
        int           row              = layer.getRowFromSlot(slot);
        int           col              = layer.getColFromSlot(slot);
        GUIItem       guiItem          = list.getItem(row, col, gui);
        ItemStack     stack            = guiItem.getItemBase();
        int           curAmount        = stack.getAmount();
        int           itemToDropAmount = curAmount;
        int           maxAmount        = limit == -1 ? stack.getMaxStackSize() : limit;
        if(curAmount > maxAmount)
        {
            guiItem.setAmount(curAmount - maxAmount);
            itemToDropAmount = maxAmount;
        }
        else list.removeListItem(row, col, gui);
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
        if(inventory != gui.getInventory()) return;
        GUIListModule list    = gui.getModule(GUIListModule.class);
        ItemStack     itemToDrop;
        int           row     = layer.getRowFromSlot(slot);
        int           col     = layer.getColFromSlot(slot);
        GUIItem       guiItem = list.getItem(row, col, gui);
        itemToDrop = guiItem.getItemBase().clone();
        itemToDrop.setAmount(1);
        guiItem.setAmount(guiItem.getAmount() - 1);
        if(guiItem.getAmount() <= 0) list.removeListItem(row, col, gui);
        Location location = player.getEyeLocation();
        World    world    = location.getWorld();
        Item     item     = world.dropItem(location, itemToDrop);
        item.setVelocity(location.getDirection().multiply(1.0 / 3.0));
    }

    @Override
    public void executeMoveToOtherInventory(Player player, Inventory inventory, int slot, InventoryClickEvent event, GUIContainer gui, GUILayer layer)
    {
        GUIListModule list = gui.getModule(GUIListModule.class);
        if(inventory == gui.getInventory())
        {
            int       row        = layer.getRowFromSlot(slot);
            int       col        = layer.getColFromSlot(slot);
            GUIItem   guiItem    = list.getItem(row, col, gui);
            ItemStack itemToMove = guiItem.getItemBase();
            Inventory playerInv  = player.getInventory();
            if(!consume)
            {
                list.removeListItem(row, col, gui);
                return;
            }
            for(int i = 0; i < playerInv.getStorageContents().length; ++i)
            {
                ItemStack curItem = playerInv.getItem(i);
                if(curItem == null) continue;
                int maxAmount = limit == -1 ? curItem.getMaxStackSize() : limit;
                if(curItem.getAmount() >= maxAmount) continue;
                if(!ItemComparison.equalsEachOther(curItem, itemToMove)) continue;
                int newAmount   = curItem.getAmount() + itemToMove.getAmount();
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
                    list.removeListItem(row, col, gui);
                    return;
                }
            }
            if(itemToMove.getAmount() <= 0)
            {
                list.removeListItem(row, col, gui);
                return;
            }
            for(int i = 0; i < playerInv.getStorageContents().length; ++i)
            {
                ItemStack curItem = playerInv.getItem(i);
                if(curItem != null) continue;
                curItem = itemToMove.clone();
                int newAmount   = itemToMove.getAmount();
                int extraAmount = 0;
                int maxAmount   = limit == -1 ? itemToMove.getMaxStackSize() : limit;
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
                    list.removeListItem(row, col, gui);
                    return;
                }
            }
        }
        else
        {
            ItemStack itemToMove    = inventory.getItem(slot);
            int       itemToMoveAmt = itemToMove.getAmount();
            for(int i = 0; i < list.getSize(); ++i)
            {
                GUIItem   curGUIItem = list.getItem(i);
                ItemStack curItem    = curGUIItem == null ? null : curGUIItem.getItemBase();
                if(curItem == null || !curGUIItem.isMovable()) continue;
                if(!ItemComparison.equalsEachOther(curItem, itemToMove)) continue;
                int newAmount   = curGUIItem.getAmount() + itemToMoveAmt;
                int extraAmount = 0;
                int maxAmount   = limit == -1 ? curItem.getMaxStackSize() : limit;
                if(newAmount > maxAmount)
                {
                    extraAmount = newAmount - maxAmount;
                    newAmount = maxAmount;
                }
                if(consume) itemToMove.setAmount(extraAmount);
                itemToMoveAmt = extraAmount;
                curGUIItem.setAmount(newAmount);
                if(itemToMoveAmt <= 0) return;
            }
            if(itemToMoveAmt <= 0 || !layer.getDefaultMoveState()) return;
            if(interactType == GUIInteractType.SINGLE_ITEM && list.containsItem(itemToMove)) return;
            if(interactType == GUIInteractType.SINGLE_MATERIAL && list.containsMaterial(itemToMove.getType())) return;
            GUIItem guiItem = new GUIItem(itemToMove.clone());
            guiItem.setMovable(true);
            list.addListItem(guiItem);
            int newAmount   = itemToMoveAmt;
            int extraAmount = 0;
            int maxAmount   = limit == -1 ? itemToMove.getMaxStackSize() : limit;
            if(newAmount > maxAmount)
            {
                extraAmount = newAmount - maxAmount;
                newAmount = maxAmount;
            }
            if(consume) itemToMove.setAmount(extraAmount);
            guiItem.setAmount(newAmount);
        }
    }

    @Override
    public void executeHotbarMoveAndReadd(Player player, Inventory inventory, int slot, InventoryClickEvent event, GUIContainer gui, GUILayer layer)
    {
        GUIListModule list       = gui.getModule(GUIListModule.class);
        int           row        = layer.getRowFromSlot(slot);
        int           col        = layer.getColFromSlot(slot);
        int           hotbarSlot = event.getHotbarButton();
        Inventory     playerInv  = player.getInventory();
        ItemStack     hotbarItem = playerInv.getItem(hotbarSlot);
        if(interactType == GUIInteractType.SINGLE_ITEM && list.containsItem(hotbarItem)) return;
        if(interactType == GUIInteractType.SINGLE_MATERIAL && list.containsMaterial(hotbarItem.getType())) return;
        GUIItem   guiItem   = list.getItem(row, col, gui);
        ItemStack topItem   = guiItem.getItemBase();
        int       maxAmount = limit == -1 ? hotbarItem.getMaxStackSize() : limit;
        if(hotbarItem.getAmount() > maxAmount) return;
        guiItem.setItem(hotbarItem);
        if(consume) playerInv.setItem(hotbarSlot, topItem);
    }

    @Override
    public void executeHotbarSwap(Player player, Inventory inventory, int slot, InventoryClickEvent event, GUIContainer gui, GUILayer layer)
    {
        if(inventory != gui.getInventory()) return;
        GUIListModule list       = gui.getModule(GUIListModule.class);
        int           hotbarSlot = event.getHotbarButton();
        Inventory     playerInv  = player.getInventory();
        int           row        = layer.getRowFromSlot(slot);
        int           col        = layer.getColFromSlot(slot);
        ItemStack     curItem    = playerInv.getItem(hotbarSlot);
        GUIItem       guiItem    = list.getItem(row, col, gui);
        if(interactType == GUIInteractType.SINGLE_ITEM && list.containsItem(curItem)) return;
        if(interactType == GUIInteractType.SINGLE_MATERIAL && list.containsMaterial(curItem.getType())) return;
        if(guiItem == null)
        {
            guiItem = new GUIItem(null);
            guiItem.setMovable(true);
            list.addListItem(guiItem);
        }
        ItemStack item = guiItem.getItemBase();
        if(consume) playerInv.setItem(hotbarSlot, item);
        if(curItem == null)
        {
            list.removeListItem(row, col, gui);
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
                if(consume)
                {
                    ItemStack extraItem = curItem.clone();
                    extraItem.setAmount(extraAmount);
                    playerInv.setItem(hotbarSlot, extraItem);
                }
            }
            guiItem.setItem(curItem);
        }
    }

    @Override
    public void executeCloneStack(Player player, Inventory inventory, int slot, InventoryClickEvent event, GUIContainer gui, GUILayer layer)
    {
        GUIListModule list = gui.getModule(GUIListModule.class);
        if(inventory != gui.getInventory()) return;
        Inventory playerInv = player.getInventory();
        int       row       = layer.getRowFromSlot(slot);
        int       col       = layer.getColFromSlot(slot);
        GUIItem   guiItem   = list.getItem(row, col, gui);
        ItemStack item      = guiItem.getItemBase().clone();
        int       maxAmount = limit == -1 ? item.getMaxStackSize() : limit;
        item.setAmount(maxAmount);
        player.setItemOnCursor(item);
    }

    @Override
    public void executeCollectToCursor(Player player, Inventory inventory, int slot, InventoryClickEvent event, GUIContainer gui, GUILayer layer)
    {
        if(!consume) return;
        ItemStack     cursorItem = player.getItemOnCursor();
        GUIListModule list       = gui.getModule(GUIListModule.class);
        int           maxAmount  = limit == -1 ? cursorItem.getMaxStackSize() : limit;
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
                    int newAmount   = curItem.getAmount() + cursorItem.getAmount();
                    int extraAmount = 0;
                    if(newAmount > maxAmount)
                    {
                        extraAmount = newAmount - maxAmount;
                        newAmount = maxAmount;
                    }
                    cursorItem.setAmount(newAmount);
                    curGuiItem.setAmount(extraAmount);
                    if(extraAmount <= 0) list.removeListItem(row, col, gui);
                    if(cursorItem.getAmount() == maxAmount) return;
                }
            }
        }
    }

    @Override
    public void executeNothing(Player player, Inventory inventory, int slot, InventoryClickEvent event, GUIContainer gui, GUILayer layer) {}

    @Override
    public void executeDropAllCursor(Player player, Inventory inventory, int slot, InventoryClickEvent event, GUIContainer gui, GUILayer layer) {}

    @Override
    public void executeDropOneCursor(Player player, Inventory inventory, int slot, InventoryClickEvent event, GUIContainer gui, GUILayer layer) {}

    @Override
    public void executeUnknown(Player player, Inventory inventory, int slot, InventoryClickEvent event, GUIContainer gui, GUILayer layer) {}

    public int getLimit()
    {
        return limit;
    }

    public void setLimit(int limit)
    {
        this.limit = limit;
    }

    public boolean consumesItems()
    {
        return consume;
    }

    public void setConsume(boolean consume)
    {
        this.consume = consume;
    }

    public GUIInteractType getInteractType()
    {
        return interactType;
    }

    public void setInteractType(GUIInteractType interactType)
    {
        this.interactType = interactType;
    }
}