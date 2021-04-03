package com.mikedeejay2.mikedeejay2lib.nms.merchant;

import net.minecraft.server.v1_16_R3.*;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftAbstractVillager;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_16_R3.inventory.CraftInventoryMerchant;
import org.bukkit.craftbukkit.v1_16_R3.inventory.CraftMerchant;

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
    public void forceTrade(
            org.bukkit.entity.AbstractVillager villager,
            org.bukkit.inventory.MerchantRecipe recipe,
            org.bukkit.entity.Player player,
            org.bukkit.inventory.Inventory inventory)
    {
        CraftAbstractVillager craftVillager = (CraftAbstractVillager) villager;
        EntityVillagerAbstract nmsVillager = craftVillager.getHandle();
        EntityHuman human = ((CraftPlayer)player).getHandle();
        InventoryMerchant merchInv = ((CraftInventoryMerchant) inventory).getInventory();
        SlotMerchantResult slot = new SlotMerchantResult(human, nmsVillager, merchInv, 2, 220, 37);
        slot.a(human, merchInv.getItem(2));
        nmsVillager.getWorld().a(nmsVillager.locX(), nmsVillager.locY(), nmsVillager.locZ(), nmsVillager.getTradeSound(), SoundCategory.NEUTRAL, 1.0F, 1.0F, false);
    }
}
