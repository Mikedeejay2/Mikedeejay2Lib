package com.mikedeejay2.mikedeejay2lib.nms.merchant;

import net.minecraft.server.v1_16_R3.*;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftAbstractVillager;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftVillager;
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
    public void postProcess(org.bukkit.entity.Villager villager, org.bukkit.inventory.MerchantRecipe recipe)
    {
        EntityVillager nmsVillager = ((CraftVillager) villager).getHandle();
        int i = 3 + nmsVillager.getRandom().nextInt(4);

        villager.setVillagerExperience(villager.getVillagerExperience() + recipe.getVillagerExperience());
        try
        {
            Field lastTraded = EntityVillager.class.getDeclaredField("bv");
            lastTraded.setAccessible(true);
            lastTraded.set(nmsVillager, nmsVillager.getTrader());
        }
        catch(NoSuchFieldException | IllegalAccessException e)
        {
            e.printStackTrace();
        }
        if (shouldLevelUp(villager)) {
            try
            {
                // nmsVillager.bt = 40;
                // nmsVillager.bu = true;
                Field villagerField1 = EntityVillager.class.getDeclaredField("bt");
                villagerField1.setAccessible(true);
                villagerField1.set(nmsVillager, 40);
                Field villagerField2 = EntityVillager.class.getDeclaredField("bu");
                villagerField2.setAccessible(true);
                villagerField2.set(nmsVillager, true);
            }
            catch(NoSuchFieldException | IllegalAccessException e)
            {
                e.printStackTrace();
            }
            i += 5;
        }

        if (recipe.hasExperienceReward()) {
            nmsVillager.world.addEntity(new EntityExperienceOrb(nmsVillager.world, nmsVillager.locX(), nmsVillager.locY() + 0.5D, nmsVillager.locZ(), i));
        }
    }

    private boolean shouldLevelUp(org.bukkit.entity.Villager villager)
    {
        EntityVillager nmsVillager = ((CraftVillager) villager).getHandle();
        int level = nmsVillager.getVillagerData().getLevel();

        return VillagerData.d(level) && nmsVillager.getExperience() >= VillagerData.c(level);
    }
}
