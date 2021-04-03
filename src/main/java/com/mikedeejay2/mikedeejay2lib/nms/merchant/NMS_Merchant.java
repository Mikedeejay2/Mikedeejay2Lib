package com.mikedeejay2.mikedeejay2lib.nms.merchant;

import org.bukkit.entity.AbstractVillager;
import org.bukkit.inventory.Merchant;
import org.bukkit.inventory.MerchantRecipe;

public interface NMS_Merchant
{
    AbstractVillager getVillager(Merchant merchant);
    void forceTrade(
            org.bukkit.entity.AbstractVillager villager,
            org.bukkit.inventory.MerchantRecipe recipe,
            org.bukkit.entity.Player player,
            org.bukkit.inventory.Inventory inventory);
}
