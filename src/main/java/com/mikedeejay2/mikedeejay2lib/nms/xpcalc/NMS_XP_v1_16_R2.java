package com.mikedeejay2.mikedeejay2lib.nms.xpcalc;

import net.minecraft.server.v1_16_R2.*;
import org.bukkit.craftbukkit.v1_16_R2.CraftWorld;
import org.bukkit.craftbukkit.v1_16_R2.inventory.CraftItemStack;

import java.util.Map;

public final class NMS_XP_v1_16_R2 implements NMS_XP
{
    @Override
    public int calculateXP(org.bukkit.inventory.ItemStack item, org.bukkit.World world)
    {
        CraftWorld craftWorld = (CraftWorld) world;
        World nmsWorld = craftWorld.getHandle();
        ItemStack nmsItem = CraftItemStack.asNMSCopy(item);
        return this.internalCalc(nmsItem, nmsWorld);
    }

    private int internalCalc(ItemStack item, World world)
    {
        byte init = 0;
        int xpAmt = init + this.calcItem(item);
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
        while(amount > 0) {
            int k = EntityExperienceOrb.getOrbValue(amount);
            amount -= k;
            world.addEntity(new EntityExperienceOrb(world, x, y, z, k));
        }
    }
}
