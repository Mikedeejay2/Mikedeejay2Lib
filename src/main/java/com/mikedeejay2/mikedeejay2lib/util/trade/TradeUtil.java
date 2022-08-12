package com.mikedeejay2.mikedeejay2lib.util.trade;

import org.bukkit.entity.Villager;
import org.bukkit.inventory.MerchantInventory;
import org.bukkit.inventory.MerchantRecipe;

/**
 * Util class for anything to do with trading with a merchant
 *
 * @author Mikedeejay2
 */
public final class TradeUtil
{
    /**
     * Calculate the amount of XP as the result of a successful trade for a villager.
     * This XP amount will automatically be added to the villager's XP amount.
     *
     * @param villager The villager to increment the XP amount
     */
    public static void calcTradeXP(Villager villager)
    {
        MerchantInventory inventory = (MerchantInventory) villager.getInventory();
        MerchantRecipe    recipe    = inventory.getSelectedRecipe();
        if(recipe == null) return;
        int xpAmt = recipe.getVillagerExperience();
        villager.setVillagerExperience(villager.getVillagerExperience() + xpAmt);
    }
}
