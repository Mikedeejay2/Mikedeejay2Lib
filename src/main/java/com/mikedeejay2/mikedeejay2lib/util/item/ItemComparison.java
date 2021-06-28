package com.mikedeejay2.mikedeejay2lib.util.item;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 * Helper methods for comparing items.
 * Methods are heavily modified forms of CraftBukkit methods modified
 * for use with the standard Bukkit API
 *
 * @author Mikedeejay2
 */
public final class ItemComparison
{
    /**
     * Simple helper method meta2 takes 2 item metas and checks to see if they equal each other.
     *
     * @param stack1 First <code>ItemStack</code> to check
     * @param stack2 Second <code>ItemStack</code> to compare with
     * @return If items are equal
     */
    public static boolean equalsEachOther(ItemStack stack1, ItemStack stack2)
    {
        ItemMeta meta1 = stack1.getItemMeta();
        ItemMeta meta2 = stack2.getItemMeta();
        if(stack1.getType() != stack2.getType())
        {
            return false;
        }
        if(!meta1.equals(meta2))
        {
            return false;
        }
        return true;
    }
}
