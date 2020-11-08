package com.mikedeejay2.mikedeejay2lib.util.item;

import com.google.common.collect.Multimap;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.*;

import java.util.Map;

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
     * @param stack1 First <tt>ItemStack</tt> to check
     * @param stack2 Second <tt>ItemStack</tt> to compare with
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
