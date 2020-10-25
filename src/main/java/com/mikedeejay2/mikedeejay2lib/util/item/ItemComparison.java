package com.mikedeejay2.mikedeejay2lib.util.item;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public final class ItemComparison
{
    /**
     * Simple helper method that takes 2 item metas and checks to see if they equal each other.
     *
     * @param stack1 First <tt>ItemStack</tt> to check
     * @param stack2 Second <tt>ItemStack</tt> to compare with
     * @return If items are equal
     */
    public static boolean equalsEachOther(ItemStack stack1, ItemStack stack2)
    {
        ItemMeta meta1 = stack1.getItemMeta();
        ItemMeta meta2 = stack2.getItemMeta();
        if(meta1 == null || meta2 == null) return false;
        if(!meta1.equals(meta2)) return false;
        if(stack1.getType() != stack2.getType()) return false;
        return true;
    }
}
