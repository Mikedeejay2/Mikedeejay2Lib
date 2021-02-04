package com.mikedeejay2.mikedeejay2lib.util.item;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

/**
 * Consumer-type interface for consuming entries from {@link InventoryIterator}
 * <p>
 * Accepts inventory, item stack, and slot.
 *
 * @author Mikedeejay2
 */
public interface InventorySlotConsumer
{
    void accept(Inventory inventory, ItemStack itemStack, int slot);
}
