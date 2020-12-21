package com.mikedeejay2.mikedeejay2lib.gui.interact.normal;

import com.mikedeejay2.mikedeejay2lib.gui.GUIContainer;
import com.mikedeejay2.mikedeejay2lib.gui.GUILayer;
import com.mikedeejay2.mikedeejay2lib.gui.interact.GUIInteractExecutor;
import com.mikedeejay2.mikedeejay2lib.gui.interact.GUIInteractType;
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
public class GUIInteractExecutorDefaultGUI implements GUIInteractExecutor
{
    protected GUIInteractType interactType;
    protected int limit;
    protected boolean consume;

    public GUIInteractExecutorDefaultGUI(int limit)
    {
        this.limit = Math.min(limit, 64);
        this.interactType = GUIInteractType.DEFAULT;
        this.consume = true;
    }

    public GUIInteractExecutorDefaultGUI()
    {
        this.limit = -1;
        this.interactType = GUIInteractType.DEFAULT;
        this.consume = true;
    }

    public GUIInteractExecutorDefaultGUI(int limit, boolean consume)
    {
        this.limit = Math.min(limit, 64);
        this.interactType = GUIInteractType.DEFAULT;
        this.consume = consume;
    }

    public GUIInteractExecutorDefaultGUI(boolean consume)
    {
        this.limit = -1;
        this.interactType = GUIInteractType.DEFAULT;
        this.consume = consume;
    }

    public GUIInteractExecutorDefaultGUI(GUIInteractType type, int limit)
    {
        this.limit = Math.min(limit, 64);
        this.interactType = type;
        this.consume = true;
    }

    public GUIInteractExecutorDefaultGUI(GUIInteractType type)
    {
        this.limit = -1;
        this.interactType = type;
        this.consume = true;
    }

    public GUIInteractExecutorDefaultGUI(GUIInteractType type, int limit, boolean consume)
    {
        this.limit = Math.min(limit, 64);
        this.interactType = type;
        this.consume = consume;
    }

    public GUIInteractExecutorDefaultGUI(GUIInteractType type, boolean consume)
    {
        this.limit = -1;
        this.interactType = type;
        this.consume = consume;
    }

    @Override
    public void executePickupAll(Player player, Inventory inventory, int slot, InventoryClickEvent event, GUIContainer gui, GUILayer layer)
    {
        if(inventory != gui.getInventory()) return;
        ItemStack cursorItem;
        int       row        = layer.getRowFromSlot(slot);
        int       col        = layer.getColFromSlot(slot);
        GUIItem   guiItem    = layer.getItem(row, col);
        ItemStack bottomItem = guiItem.getItemBase();
        int       curAmount  = bottomItem.getAmount();
        int       maxAmount  = limit == -1 ? bottomItem.getMaxStackSize() : limit;
        if(curAmount > maxAmount)
        {
            guiItem.setAmount(curAmount - maxAmount);
            curAmount = maxAmount;
        }
        else layer.removeItem(row, col);
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
        ItemStack cursorItem   = player.getItemOnCursor();
        int       cursorAmount = cursorItem.getAmount();
        int       row          = layer.getRowFromSlot(slot);
        int       col          = layer.getColFromSlot(slot);
        GUIItem   guiItem      = layer.getItem(row, col);
        ItemStack bottomItem   = guiItem.getItemBase();
        int       bottomAmount = bottomItem.getAmount();
        int       maxAmount    = limit == -1 ? cursorItem.getMaxStackSize() : limit;
        if(consume)
        {
            cursorItem.setAmount(maxAmount);
            guiItem.setAmount(bottomAmount - (maxAmount - cursorAmount));
            player.setItemOnCursor(cursorItem);
        }
    }

    @Override
    public void executePickupHalf(Player player, Inventory inventory, int slot, InventoryClickEvent event, GUIContainer gui, GUILayer layer)
    {
        if(inventory != gui.getInventory()) return;
        ItemStack cursorItem;
        int       row          = layer.getRowFromSlot(slot);
        int       col          = layer.getColFromSlot(slot);
        GUIItem   guiItem      = layer.getItem(row, col);
        ItemStack bottomItem   = guiItem.getItemBase();
        int       bottomAmount = bottomItem.getAmount();
        int       halfTop      = (int) Math.ceil(bottomAmount / 2.0);
        int       halfBottom   = (int) Math.floor(bottomAmount / 2.0);
        int       maxAmount    = limit == -1 ? bottomItem.getMaxStackSize() : limit;
        if(halfTop > maxAmount)
        {
            halfBottom += halfTop - maxAmount;
            halfTop = maxAmount;
        }
        if(consume)
        {
            cursorItem = bottomItem.clone();
            cursorItem.setAmount(halfTop);
            player.setItemOnCursor(cursorItem);
        }
        guiItem.setAmount(halfBottom);
    }

    @Override
    public void executePickupOne(Player player, Inventory inventory, int slot, InventoryClickEvent event, GUIContainer gui, GUILayer layer)
    {
        if(inventory != gui.getInventory()) return;
        ItemStack cursorItem   = player.getItemOnCursor();
        int       cursorAmount = cursorItem.getAmount();
        int       row          = layer.getRowFromSlot(slot);
        int       col          = layer.getColFromSlot(slot);
        GUIItem   guiItem      = layer.getItem(row, col);
        ItemStack bottomItem   = guiItem.getItemBase();
        int       bottomAmount = bottomItem.getAmount();
        if(cursorItem.getType() == Material.AIR && consume)
        {
            cursorItem = bottomItem.clone();
            cursorItem.setAmount(1);
        }
        else if(consume)
        {
            cursorItem.setAmount(cursorAmount + 1);
        }
        guiItem.setAmount(bottomAmount - 1);
        if(consume) player.setItemOnCursor(cursorItem);
    }

    @Override
    public void executePlaceAll(Player player, Inventory inventory, int slot, InventoryClickEvent event, GUIContainer gui, GUILayer layer)
    {
        if(inventory != gui.getInventory()) return;
        ItemStack cursorItem   = player.getItemOnCursor();
        int       cursorAmount = cursorItem.getAmount();
        int       row          = layer.getRowFromSlot(slot);
        int       col          = layer.getColFromSlot(slot);
        int       bottomAmount = 0;
        GUIItem   curItem      = layer.getItem(row, col);
        if(curItem != null)
        {
            bottomAmount = curItem.getAmountBase();
        }
        else
        {
            if(interactType == GUIInteractType.SINGLE_ITEM && layer.containsItem(cursorItem)) return;
            if(interactType == GUIInteractType.SINGLE_MATERIAL && layer.containsMaterial(cursorItem.getType())) return;
        }
        GUIItem newItem = new GUIItem(cursorItem.clone());
        newItem.setMovable(true);
        int curAmount   = cursorAmount + bottomAmount;
        int newAmount   = curAmount;
        int extraAmount = 0;
        int maxAmount   = limit == -1 ? cursorItem.getMaxStackSize() : limit;
        if(curAmount > maxAmount)
        {
            extraAmount = curAmount - maxAmount;
            newAmount = maxAmount;
        }
        newItem.setAmount(newAmount);
        layer.setItem(row, col, newItem);
        if(consume)
        {
            cursorItem.setAmount(extraAmount);
            player.setItemOnCursor(cursorItem);
        }
    }

    @Override
    public void executePlaceSome(Player player, Inventory inventory, int slot, InventoryClickEvent event, GUIContainer gui, GUILayer layer)
    {
        if(inventory != gui.getInventory()) return;
        ItemStack cursorItem = player.getItemOnCursor();
        if(interactType == GUIInteractType.SINGLE_ITEM && layer.containsItem(cursorItem)) return;
        if(interactType == GUIInteractType.SINGLE_MATERIAL && layer.containsMaterial(cursorItem.getType())) return;
        int       cursorAmount = cursorItem.getAmount();
        int       row          = layer.getRowFromSlot(slot);
        int       col          = layer.getColFromSlot(slot);
        GUIItem   guiItem      = layer.getItem(row, col);
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
        ItemStack cursorItem   = player.getItemOnCursor();
        int       cursorAmount = cursorItem.getAmount();
        int       row          = layer.getRowFromSlot(slot);
        int       col          = layer.getColFromSlot(slot);
        GUIItem   guiItem      = layer.getItem(row, col);
        if(guiItem == null)
        {
            if(interactType == GUIInteractType.SINGLE_ITEM && layer.containsItem(cursorItem)) return;
            if(interactType == GUIInteractType.SINGLE_MATERIAL && layer.containsMaterial(cursorItem.getType())) return;
            guiItem = new GUIItem(cursorItem.clone());
            guiItem.setMovable(true);
            guiItem.setAmount(1);
            layer.setItem(row, col, guiItem);
        }
        else
        {
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
        ItemStack cursorItem = player.getItemOnCursor();
        if(interactType == GUIInteractType.SINGLE_ITEM && layer.containsItem(cursorItem)) return;
        if(interactType == GUIInteractType.SINGLE_MATERIAL && layer.containsMaterial(cursorItem.getType())) return;
        int maxAmount = limit == -1 ? cursorItem.getMaxStackSize() : limit;
        if(cursorItem.getAmount() > maxAmount) return;
        int       row        = layer.getRowFromSlot(slot);
        int       col        = layer.getColFromSlot(slot);
        GUIItem   guiItem    = layer.getItem(row, col);
        ItemStack bottomItem = guiItem.getItemBase();
        guiItem.setItem(consume ? cursorItem : cursorItem.clone());
        if(consume) player.setItemOnCursor(bottomItem);
    }

    @Override
    public void executeDropAllSlot(Player player, Inventory inventory, int slot, InventoryClickEvent event, GUIContainer gui, GUILayer layer)
    {
        if(inventory != gui.getInventory()) return;
        ItemStack itemToDrop;
        int       row              = layer.getRowFromSlot(slot);
        int       col              = layer.getColFromSlot(slot);
        GUIItem   guiItem          = layer.getItem(row, col);
        ItemStack stack            = guiItem.getItemBase();
        int       curAmount        = stack.getAmount();
        int       itemToDropAmount = curAmount;
        int       maxAmount        = limit == -1 ? stack.getMaxStackSize() : limit;
        if(curAmount > maxAmount)
        {
            guiItem.setAmount(curAmount - maxAmount);
            itemToDropAmount = maxAmount;
        }
        else layer.removeItem(row, col);
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
        ItemStack itemToDrop;
        int       row     = layer.getRowFromSlot(slot);
        int       col     = layer.getColFromSlot(slot);
        GUIItem   guiItem = layer.getItem(row, col);
        itemToDrop = guiItem.getItemBase().clone();
        itemToDrop.setAmount(1);
        guiItem.setAmount(guiItem.getAmount() - 1);
        Location location = player.getEyeLocation();
        World    world    = location.getWorld();
        Item     item     = world.dropItem(location, itemToDrop);
        item.setVelocity(location.getDirection().multiply(1.0 / 3.0));
    }

    @Override
    public void executeMoveToOtherInventory(Player player, Inventory inventory, int slot, InventoryClickEvent event, GUIContainer gui, GUILayer layer)
    {
        if(inventory == gui.getInventory())
        {
            int       row        = layer.getRowFromSlot(slot);
            int       col        = layer.getColFromSlot(slot);
            GUIItem   guiItem    = layer.getItem(row, col);
            ItemStack itemToMove = guiItem.getItemBase();
            Inventory playerInv  = player.getInventory();
            if(!consume)
            {
                layer.removeItem(row, col);
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
                    layer.removeItem(row, col);
                    return;
                }
            }
        }
        else
        {
            ItemStack itemToMove    = inventory.getItem(slot);
            int       itemToMoveAmt = itemToMove.getAmount();
            for(int row = 1; row <= layer.getRows(); ++row)
            {
                for(int col = 1; col <= layer.getCols(); ++col)
                {
                    GUIItem   curGUIItem = layer.getItem(row, col);
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
            }
            if(itemToMoveAmt <= 0 || !layer.getDefaultMoveState()) return;
            if(interactType == GUIInteractType.SINGLE_ITEM && layer.containsItem(itemToMove)) return;
            if(interactType == GUIInteractType.SINGLE_MATERIAL && layer.containsMaterial(itemToMove.getType())) return;
            for(int row = 1; row <= layer.getRows(); ++row)
            {
                for(int col = 1; col <= layer.getCols(); ++col)
                {
                    GUIItem   curGUIItem = layer.getItem(row, col);
                    ItemStack curItem    = curGUIItem == null ? null : curGUIItem.getItemBase();
                    if(curItem != null || (curGUIItem != null && !curGUIItem.isMovable())) continue;
                    if(curGUIItem == null)
                    {
                        curGUIItem = new GUIItem(itemToMove.clone());
                        curGUIItem.setAmount(itemToMoveAmt);
                        curGUIItem.setMovable(true);
                        layer.setItem(row, col, curGUIItem);
                    }
                    int newAmount   = itemToMoveAmt;
                    int extraAmount = 0;
                    int maxAmount   = limit == -1 ? itemToMove.getMaxStackSize() : limit;
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
            }
        }
    }

    @Override
    public void executeHotbarMoveAndReadd(Player player, Inventory inventory, int slot, InventoryClickEvent event, GUIContainer gui, GUILayer layer)
    {
        int       row        = layer.getRowFromSlot(slot);
        int       col        = layer.getColFromSlot(slot);
        int       hotbarSlot = event.getHotbarButton();
        Inventory playerInv  = player.getInventory();
        ItemStack hotbarItem = playerInv.getItem(hotbarSlot);
        if(interactType == GUIInteractType.SINGLE_ITEM && layer.containsItem(hotbarItem)) return;
        if(interactType == GUIInteractType.SINGLE_MATERIAL && layer.containsMaterial(hotbarItem.getType())) return;
        GUIItem   guiItem   = layer.getItem(row, col);
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
        int       hotbarSlot = event.getHotbarButton();
        Inventory playerInv  = player.getInventory();
        int       row        = layer.getRowFromSlot(slot);
        int       col        = layer.getColFromSlot(slot);
        GUIItem   guiItem    = layer.getItem(row, col);
        ItemStack curItem    = playerInv.getItem(hotbarSlot);
        if(!consume) curItem = curItem.clone();
        if(interactType == GUIInteractType.SINGLE_ITEM && layer.containsItem(curItem)) return;
        if(interactType == GUIInteractType.SINGLE_MATERIAL && layer.containsMaterial(curItem.getType())) return;
        if(guiItem == null)
        {
            guiItem = new GUIItem(null);
            guiItem.setMovable(true);
            layer.setItem(row, col, guiItem);
        }
        ItemStack item = guiItem.getItemBase();
        if(consume) playerInv.setItem(hotbarSlot, item);
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
                if(consume)
                {
                    ItemStack extraItem = curItem.clone();
                    extraItem.setAmount(extraAmount);
                    playerInv.setItem(hotbarSlot, extraItem);
                }
            }
        }
        guiItem.setItem(curItem);
    }

    @Override
    public void executeCloneStack(Player player, Inventory inventory, int slot, InventoryClickEvent event, GUIContainer gui, GUILayer layer)
    {
        if(inventory != gui.getInventory()) return;
        int       row       = layer.getRowFromSlot(slot);
        int       col       = layer.getColFromSlot(slot);
        GUIItem   guiItem   = layer.getItem(row, col);
        ItemStack item      = guiItem.getItemBase().clone();
        int       maxAmount = limit == -1 ? item.getMaxStackSize() : limit;
        item.setAmount(maxAmount);
        player.setItemOnCursor(item);
    }

    @Override
    public void executeCollectToCursor(Player player, Inventory inventory, int slot, InventoryClickEvent event, GUIContainer gui, GUILayer layer)
    {
        if(!consume) return;
        ItemStack cursorItem = player.getItemOnCursor();
        int       maxAmount  = limit == -1 ? cursorItem.getMaxStackSize() : limit;
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
                    if(extraAmount <= 0) layer.removeItem(row, col);
                    if(cursorItem.getAmount() == maxAmount) return;
                }
            }
        }
    }

    @Override
    public void executeNothing(Player player, Inventory inventory, int slot, InventoryClickEvent event, GUIContainer gui, GUILayer layer)       {}

    @Override
    public void executeDropAllCursor(Player player, Inventory inventory, int slot, InventoryClickEvent event, GUIContainer gui, GUILayer layer) {}

    @Override
    public void executeDropOneCursor(Player player, Inventory inventory, int slot, InventoryClickEvent event, GUIContainer gui, GUILayer layer) {}

    @Override
    public void executeUnknown(Player player, Inventory inventory, int slot, InventoryClickEvent event, GUIContainer gui, GUILayer layer)       {}

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