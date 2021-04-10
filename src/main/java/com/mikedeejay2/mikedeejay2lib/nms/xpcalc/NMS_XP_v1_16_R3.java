package com.mikedeejay2.mikedeejay2lib.nms.xpcalc;

import net.minecraft.server.v1_16_R3.*;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftAbstractVillager;
import org.bukkit.craftbukkit.v1_16_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_16_R3.inventory.CraftItemStack;
import org.bukkit.craftbukkit.v1_16_R3.inventory.CraftMerchantRecipe;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Random;

public final class NMS_XP_v1_16_R3 implements NMS_XP
{
    @Override
    public int calculateXP(org.bukkit.World world, org.bukkit.inventory.ItemStack... items)
    {
        CraftWorld craftWorld = (CraftWorld) world;
        World nmsWorld = craftWorld.getHandle();
        ItemStack[] nmsItems = new ItemStack[items.length];
        for(int i = 0; i < nmsItems.length; ++i)
        {
            org.bukkit.inventory.ItemStack curItem = items[i];
            ItemStack converted = CraftItemStack.asNMSCopy(curItem);
            nmsItems[i] = converted;
        }
        return this.internalCalc(nmsWorld, nmsItems);
    }

    private int internalCalc(World world, ItemStack... items)
    {
        int xpAmt = 0;
        for(ItemStack item : items)
        {
            xpAmt += this.calcItem(item);
        }
        if (xpAmt > 0) {
            int xpAmt2 = (int)Math.ceil((double)xpAmt / 2.0D);
            return xpAmt2 + world.random.nextInt(xpAmt2);
        } else {
            return 0;
        }
    }

    private int calcItem(ItemStack itemstack)
    {
        int xpAmt = 0;
        Map<Enchantment, Integer> enchantMap = EnchantmentManager.a(itemstack);

        for(Map.Entry<Enchantment, Integer> entry : enchantMap.entrySet())
        {
            Enchantment enchantment = entry.getKey();
            Integer level = entry.getValue();
            if(!enchantment.c())
            {
                xpAmt += enchantment.a(level);
            }
        }

        return xpAmt;
    }

    @Override
    public void spawnXP(int amount, org.bukkit.Location location)
    {
        org.bukkit.World bWorld = location.getWorld();
        CraftWorld cWorld = (CraftWorld) bWorld;
        World world = cWorld.getHandle();
        double x = location.getX();
        double y = location.getY();
        double z = location.getZ();
        internalSpawnXP(amount, world, x, y, z);
    }

    private void internalSpawnXP(int amount, World world, double x, double y, double z)
    {
        while(amount > 0) {
            int k = EntityExperienceOrb.getOrbValue(amount);
            amount -= k;
            world.addEntity(new EntityExperienceOrb(world, x, y, z, k));
        }
    }
}
