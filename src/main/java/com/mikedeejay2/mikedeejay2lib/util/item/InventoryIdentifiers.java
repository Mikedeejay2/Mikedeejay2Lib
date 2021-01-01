package com.mikedeejay2.mikedeejay2lib.util.item;

import org.bukkit.Material;

public final class InventoryIdentifiers
{
    public static final int OFFHAND_SLOT = 40;
    public static final int HELMET_SLOT = 39;
    public static final int CHESTPLATE_SLOT = 38;
    public static final int LEGGINGS_SLOT = 37;
    public static final int BOOTS_SLOT = 36;
    public static final int FIRST_BOTTOM_RAW_SLOT = 0;
    public static final int FIRST_TOP_RAW_SLOT = 37;

    public static boolean isShulkerBox(Material material)
    {
        switch(material)
        {
            case SHULKER_BOX:
            case WHITE_SHULKER_BOX:
            case ORANGE_SHULKER_BOX:
            case MAGENTA_SHULKER_BOX:
            case LIGHT_BLUE_SHULKER_BOX:
            case YELLOW_SHULKER_BOX:
            case LIME_SHULKER_BOX:
            case PINK_SHULKER_BOX:
            case GRAY_SHULKER_BOX:
            case LIGHT_GRAY_SHULKER_BOX:
            case CYAN_SHULKER_BOX:
            case PURPLE_SHULKER_BOX:
            case BLUE_SHULKER_BOX:
            case BROWN_SHULKER_BOX:
            case GREEN_SHULKER_BOX:
            case RED_SHULKER_BOX:
            case BLACK_SHULKER_BOX:
                return true;
            default:
                return false;
        }
    }

    public static boolean isArmor(Material material)
    {
        return isHelmet(material) || isChestplate(material) || isLeggings(material) || isBoots(material);
    }

    public static boolean isHelmet(Material material)
    {
        switch(material)
        {
            case LEATHER_HELMET:
            case CHAINMAIL_HELMET:
            case IRON_HELMET:
            case GOLDEN_HELMET:
            case DIAMOND_HELMET:
            case NETHERITE_HELMET:
            case TURTLE_HELMET:
            case CREEPER_HEAD:
            case DRAGON_HEAD:
            case PLAYER_HEAD:
            case ZOMBIE_HEAD:
                return true;
            default:
                return false;
        }
    }

    public static boolean isChestplate(Material material)
    {
        switch(material)
        {
            case LEATHER_CHESTPLATE:
            case CHAINMAIL_CHESTPLATE:
            case IRON_CHESTPLATE:
            case GOLDEN_CHESTPLATE:
            case DIAMOND_CHESTPLATE:
            case NETHERITE_CHESTPLATE:
            case ELYTRA:
            default:
                return false;
        }
    }

    public static boolean isLeggings(Material material)
    {
        switch(material)
        {
            case LEATHER_LEGGINGS:
            case CHAINMAIL_LEGGINGS:
            case IRON_LEGGINGS:
            case GOLDEN_LEGGINGS:
            case DIAMOND_LEGGINGS:
            case NETHERITE_LEGGINGS:
                return true;
            default:
                return false;
        }
    }

    public static boolean isBoots(Material material)
    {
        switch(material)
        {
            case LEATHER_BOOTS:
            case CHAINMAIL_BOOTS:
            case IRON_BOOTS:
            case GOLDEN_BOOTS:
            case DIAMOND_BOOTS:
            case NETHERITE_BOOTS:
                return true;
            default:
                return false;
        }
    }

    public static boolean isOffhand(Material material)
    {
        switch(material)
        {
            case SHIELD:
                return true;
            default:
                return false;
        }
    }

    public static boolean isOffhandSlot(int slot)
    {
        return slot == OFFHAND_SLOT;
    }

    public static boolean isArmorSlot(int slot)
    {
        return slot <= HELMET_SLOT && slot >= BOOTS_SLOT;
    }
}
