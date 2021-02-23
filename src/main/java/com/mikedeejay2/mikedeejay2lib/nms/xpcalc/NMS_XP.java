package com.mikedeejay2.mikedeejay2lib.nms.xpcalc;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.inventory.ItemStack;

public interface NMS_XP
{
    int calculateXP(ItemStack item, World world);
    void spawnXP(int amount, Location location);
}
