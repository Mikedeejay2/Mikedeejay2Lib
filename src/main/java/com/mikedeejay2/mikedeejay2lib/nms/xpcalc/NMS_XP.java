package com.mikedeejay2.mikedeejay2lib.nms.xpcalc;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.AbstractVillager;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.MerchantRecipe;

public interface NMS_XP
{
    int calculateXP(World world, ItemStack... items);
    int calculateXP(AbstractVillager villager);
    void spawnXP(int amount, Location location);
}
