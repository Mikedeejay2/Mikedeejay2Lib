package com.mikedeejay2.mikedeejay2lib.nms.merchant;

import net.minecraft.server.v1_16_R3.EntityVillager;
import net.minecraft.server.v1_16_R3.EntityVillagerAbstract;
import net.minecraft.server.v1_16_R3.IMerchant;
import net.minecraft.server.v1_16_R3.VillagerData;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftAbstractVillager;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_16_R3.inventory.CraftMerchant;

import java.lang.reflect.Field;

public class NMS_Merchant_v1_16_R3 implements NMS_Merchant
{
    @Override
    public org.bukkit.entity.AbstractVillager getVillager(org.bukkit.inventory.Merchant merchant)
    {
        CraftMerchant cMerchant = (CraftMerchant) merchant;
        IMerchant nmsMerchant = cMerchant.getMerchant();
        if(!(nmsMerchant instanceof EntityVillagerAbstract)) return null;
        EntityVillagerAbstract nmsAVillager = (EntityVillagerAbstract) nmsMerchant;
        CraftAbstractVillager cVillager = (CraftAbstractVillager) CraftEntity.getEntity(nmsMerchant.getWorld().getServer(), nmsAVillager);
        return cVillager;
    }

    @Override
    public void forceTradeUpdate(org.bukkit.entity.AbstractVillager villager, org.bukkit.inventory.MerchantRecipe recipe)
    {
        CraftAbstractVillager craftVillager = (CraftAbstractVillager) villager;
        EntityVillagerAbstract nmsVillager = craftVillager.getHandle();
        EntityVillager villager1 = (EntityVillager) nmsVillager;
        villager1.setExperience(villager1.getExperience() + recipe.getVillagerExperience());
        try
        {
            // Force currently trading player
            Field fieldTrader = villager1.getClass().getDeclaredField("bv");
            fieldTrader.setAccessible(true);
            fieldTrader.set(fieldTrader, nmsVillager.getTrader());
        }
        catch(NoSuchFieldException | IllegalAccessException e)
        {
            e.printStackTrace();
        }
//            this.bv = nmsVillager.getTrader();
        if(this.isLevel(villager1))
        {
            try
            {
                // Get int for refill time
                Field field1 = villager1.getClass().getDeclaredField("bt");
                // Get boolean for whether trades need repopulating
                Field field2 = villager1.getClass().getDeclaredField("bu");
                field1.setAccessible(true);
                field2.setAccessible(true);
                // Set refill time to 40
                field1.setInt(villager1, 40);
                // Set trades need repopulating to true
                field2.setBoolean(villager1, true);
            }
            catch(IllegalAccessException | NoSuchFieldException e)
            {
                e.printStackTrace();
            }
//            villager1.bt = 40;
//            villager1.bu = true;
        }
    }

    private boolean isLevel(EntityVillager villager)
    {
        int level = villager.getVillagerData().getLevel();

        return VillagerData.d(level) && villager.getExperience() >= VillagerData.c(level);
    }
}
