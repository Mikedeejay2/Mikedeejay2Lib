package com.mikedeejay2.mikedeejay2lib.util.item;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

/**
 * Utility class for iterating through slots of an inventory.
 * See {@link InventoryIterator#iterateInventory(Inventory, InventorySlotConsumer, boolean, Predicate, InventoryOrder...)}
 * for more information.
 *
 * @author Mikedeejay2
 */
public final class InventoryIterator
{
    /**
     * Iterate through slots of an inventory
     *
     * @param inventory The inventory to iterate through
     * @param consumer The <tt>InventorySlotConsumer</tt> to accept on a slot
     * @param storageOnly Whether only storage slots of the inventory be iterated through
     * @param predicate The <tt>Predicate</tt> that is checked before accepting the slot
     * @param orders The array of <tt>InventoryOrders</tt> that will be used
     * @param startIndex The starting index of the iteration
     * @param endIndex The ending index of the iteration
     * @param increment Whether to increment of decrement the operation
     */
    public static void iterateInventory(Inventory inventory, InventorySlotConsumer consumer, boolean storageOnly, Predicate<ItemStack> predicate, int startIndex, int endIndex, boolean increment)
    {
        ItemStack[] items = storageOnly ? inventory.getStorageContents() : inventory.getContents();
        if(increment)
        {
            for(int i = startIndex; i <= endIndex; ++i)
            {
                if(i >= items.length || i < 0) continue;
                ItemStack stack = items[i];
                if(predicate != null && !predicate.test(stack)) return;
                consumer.accept(inventory, stack, i);
            }
        }
        else
        {
            for(int i = startIndex; i >= endIndex; --i)
            {
                if(i >= items.length || i < 0) continue;
                ItemStack stack = items[i];
                if(predicate != null && !predicate.test(stack)) return;
                consumer.accept(inventory, stack, i);
            }
        }
    }

    /**
     * Iterate through slots of an inventory
     *
     * @param inventory The inventory to iterate through
     * @param consumer The <tt>InventorySlotConsumer</tt> to accept on a slot
     * @param storageOnly Whether only storage slots of the inventory be iterated through
     * @param predicate The <tt>Predicate</tt> that is checked before accepting the slot
     * @param orders The array of <tt>InventoryOrders</tt> that will be used
     */
    public static void iterateInventory(Inventory inventory, InventorySlotConsumer consumer, boolean storageOnly, Predicate<ItemStack> predicate, InventoryOrder... orders)
    {
        ItemStack[] items = storageOnly ? inventory.getStorageContents() : inventory.getContents();
        for(InventoryOrder order : orders)
        {
            if(!(inventory instanceof PlayerInventory) && order.isPlayerOnly()) continue;
            int start = order.getStart();
            int end = order.getEnd();
            boolean increment = order.isIncrement();
            iterateInventory(inventory, consumer, storageOnly, predicate, start, end, increment);
        }
    }
}
