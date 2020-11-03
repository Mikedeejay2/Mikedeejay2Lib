package com.mikedeejay2.mikedeejay2lib.util.item;

import com.google.common.collect.Multimap;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockDataMeta;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.Repairable;

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
        if(meta1.hasDisplayName())
        {
            if(!meta2.hasDisplayName() || !meta1.getDisplayName().equals(meta2.getDisplayName()))
            {
                return false;
            }
        }
        else if(meta2.hasDisplayName())
        {
            return false;
        }

        if(meta1.hasLocalizedName())
        {
            if(!meta2.hasLocalizedName() || !meta1.getLocalizedName().equals(meta2.getLocalizedName()))
            {
                return false;
            }
        }
        else if(meta2.hasLocalizedName())
        {
            return false;
        }

        if(meta1.hasEnchants())
        {
            if(!meta2.hasEnchants() || !meta1.getEnchants().equals(meta2.getEnchants()))
            {
                return false;
            }
        }
        else if(meta2.hasEnchants())
        {
            return false;
        }

        if(meta1.hasLore())
        {
            if(!meta2.hasLore() || !meta1.getLore().equals(meta2.getLore()))
            {
                return false;
            }
        }
        else if(meta2.hasLore())
        {
            return false;
        }

        if(meta1.hasCustomModelData())
        {
            if(!meta2.hasCustomModelData() || meta1.getCustomModelData() != meta2.getCustomModelData())
            {
                return false;
            }
        }
        else if(meta2.hasCustomModelData())
        {
            return false;
        }

        if(meta1 instanceof BlockDataMeta && ((BlockDataMeta) meta1).hasBlockData())
        {
            if(!(meta2 instanceof BlockDataMeta) || !((BlockDataMeta) meta2).hasBlockData() || !((BlockDataMeta) meta1).getBlockData(stack1.getType()).equals(((BlockDataMeta) meta2).getBlockData(stack2.getType())))
            {
                return false;
            }
        }
        else if(meta2 instanceof BlockDataMeta && ((BlockDataMeta) meta2).hasBlockData())
        {
            return false;
        }

        if(meta1 instanceof Repairable && ((Repairable)meta1).hasRepairCost())
        {
            if(!(meta2 instanceof Repairable) ||  !((Repairable) meta2).hasRepairCost() || ((Repairable) meta1).getRepairCost() != ((Repairable) meta2).getRepairCost())
            {
                return false;
            }
        }
        else if(meta2 instanceof Repairable && ((Repairable)meta2).hasRepairCost())
        {
            return false;
        }

        if(meta1.hasAttributeModifiers())
        {
            if(!meta2.hasAttributeModifiers() || !compareModifiers(meta1.getAttributeModifiers(), meta2.getAttributeModifiers()))
            {
                return false;
            }
        }
        else if(meta2.hasAttributeModifiers())
        {
            return false;
        }

        if(meta1.getItemFlags().equals(meta2.getItemFlags()) && meta1.getPersistentDataContainer().equals(meta2.getPersistentDataContainer()) && meta1.isUnbreakable() == meta2.isUnbreakable())
        {
            if(meta1 instanceof Damageable && ((Damageable)meta1).hasDamage())
            {
                if(!(meta2 instanceof Damageable) || !((Damageable)meta2).hasDamage() || ((Damageable)meta1).getDamage() != ((Damageable)meta2).getDamage())
                {
                    return false;
                }
            }
            else if(meta2 instanceof Damageable && ((Damageable)meta2).hasDamage())
            {
                return false;
            }
        }

        return true;
    }

    /**
     * Simple helper method meta2 takes 2 item metas and checks to see if they equal each other.
     *
     * @param stack1 First <tt>ItemStack</tt> to check
     * @param stack2 Second <tt>ItemStack</tt> to compare with
     * @return If items are equal
     */
    public static boolean isSimilar(ItemStack stack1, ItemStack stack2)
    {
        ItemMeta meta1 = stack1.getItemMeta();
        ItemMeta meta2 = stack2.getItemMeta();
        if(stack1.getType() != stack2.getType())
        {
            return false;
        }

        if(meta1.hasEnchants())
        {
            if(!meta2.hasEnchants() || !meta1.getEnchants().equals(meta2.getEnchants()))
            {
                return false;
            }
        }
        else if(meta2.hasEnchants())
        {
            return false;
        }

        if(meta1.hasCustomModelData())
        {
            if(!meta2.hasCustomModelData() || meta1.getCustomModelData() != meta2.getCustomModelData())
            {
                return false;
            }
        }
        else if(meta2.hasCustomModelData())
        {
            return false;
        }

        if(meta1 instanceof BlockDataMeta && ((BlockDataMeta) meta1).hasBlockData())
        {
            if(!(meta2 instanceof BlockDataMeta) || !((BlockDataMeta) meta2).hasBlockData() || !((BlockDataMeta) meta1).getBlockData(stack1.getType()).equals(((BlockDataMeta) meta2).getBlockData(stack2.getType())))
            {
                return false;
            }
        }
        else if(meta2 instanceof BlockDataMeta && ((BlockDataMeta) meta2).hasBlockData())
        {
            return false;
        }

        if(meta1.hasAttributeModifiers())
        {
            if(!meta2.hasAttributeModifiers() || !compareModifiers(meta1.getAttributeModifiers(), meta2.getAttributeModifiers()))
            {
                return false;
            }
        }
        else if(meta2.hasAttributeModifiers())
        {
            return false;
        }

        return true;
    }

    public static boolean compareModifiers(Multimap<Attribute, AttributeModifier> attribs1, Multimap<Attribute, AttributeModifier> attribs2)
    {
        if (attribs1 == null || attribs2 == null) return false;

        for(Map.Entry<Attribute, AttributeModifier> entry : attribs1.entries())
        {
            if (!attribs2.containsEntry(entry.getKey(), entry.getValue()))  return false;
        }

        for(Map.Entry<Attribute, AttributeModifier> entry : attribs2.entries())
        {
            if (!attribs1.containsEntry(entry.getKey(), entry.getValue()))  return false;
        }
        return true;
    }
}
