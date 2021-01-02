package com.mikedeejay2.mikedeejay2lib.util.item;

import com.mikedeejay2.mikedeejay2lib.util.recipe.RecipeUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.Recipe;

import java.util.AbstractMap;
import java.util.Iterator;
import java.util.Map;

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
                return true;
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

    public static Map.Entry<Boolean, Boolean> applicableForSlot(int rawSlot, InventoryView invView, Material material)
    {
        InventoryType.SlotType slotType = invView.getSlotType(rawSlot);
        InventoryType          invType  = invView.getType();
        int                    slot     = invView.convertSlot(rawSlot);

        switch(invType)
        {
            case LOOM:
            {
                switch(rawSlot)
                {
                    case 0:
                        return new AbstractMap.SimpleEntry<>(isBanner(material), false);
                    case 1:
                        return new AbstractMap.SimpleEntry<>(isDye(material), false);
                    case 2:
                        return new AbstractMap.SimpleEntry<>(isPattern(material), false);
                }
            } break;
            case BREWING:
            {
                switch(rawSlot)
                {
                    case 0:
                    case 1:
                    case 2:
                        return new AbstractMap.SimpleEntry<>(isBottle(material), false);
                    case 4:
                        return new AbstractMap.SimpleEntry<>(material == Material.BLAZE_POWDER, false);
                    case 3:
                        return new AbstractMap.SimpleEntry<>(isApplicableForBrewing(material), false);
                }
            } break;
            case CARTOGRAPHY:
            {
                switch(rawSlot)
                {
                    case 0:
                        return new AbstractMap.SimpleEntry<>(material == Material.FILLED_MAP, false);
                    case 1:
                        return new AbstractMap.SimpleEntry<>(material == Material.PAPER, false);
                }
            } break;
            case ENCHANTING:
            {
                if(rawSlot == 1)
                {
                    return new AbstractMap.SimpleEntry<>(material == Material.LAPIS_LAZULI, false);
                }
            } break;
            case GRINDSTONE:
            {
                return new AbstractMap.SimpleEntry<>((rawSlot == 0 || rawSlot == 1) && isTool(material), false);
            }
            case SHULKER_BOX:
            {
                return new AbstractMap.SimpleEntry<>(!isShulkerBox(material), false);
            }
            case FURNACE:
            {
                if(rawSlot == 0)
                {
                    return new AbstractMap.SimpleEntry<>(RecipeUtil.isFurnaceInput(material), true);
                }
            }
            case BLAST_FURNACE:
            {
                if(rawSlot == 0)
                {
                    return new AbstractMap.SimpleEntry<>(RecipeUtil.isBlastingInput(material), true);
                }
            }
            case SMOKER:
            {
                if(rawSlot == 0)
                {
                    return new AbstractMap.SimpleEntry<>(RecipeUtil.isSmokingInput(material), true);
                }
            }
        }

        switch(slotType)
        {
            case RESULT:
                return new AbstractMap.SimpleEntry<>(false, false);
            case ARMOR:
            {
                if(slot == BOOTS_SLOT && !isBoots(material)) return new AbstractMap.SimpleEntry<>(true, false);
                if(slot == LEGGINGS_SLOT && !isLeggings(material)) return new AbstractMap.SimpleEntry<>(true, false);
                if(slot == CHESTPLATE_SLOT && !isChestplate(material)) return new AbstractMap.SimpleEntry<>(true, false);
                if(slot == HELMET_SLOT && !isHelmet(material)) return new AbstractMap.SimpleEntry<>(true, false);
                return new AbstractMap.SimpleEntry<>(false, false);
            }
            case FUEL:
            {
                return new AbstractMap.SimpleEntry<>(material.isFuel(), true);
            }
            case QUICKBAR:
            case CONTAINER:
            case OUTSIDE:
            case CRAFTING:
                return new AbstractMap.SimpleEntry<>(true, false);
        }
        return new AbstractMap.SimpleEntry<>(false, false);
    }

    public static boolean isBanner(Material material)
    {
        switch(material)
        {
            case WHITE_BANNER:
            case ORANGE_BANNER:
            case MAGENTA_BANNER:
            case LIGHT_BLUE_BANNER:
            case YELLOW_BANNER:
            case LIME_BANNER:
            case PINK_BANNER:
            case GRAY_BANNER:
            case LIGHT_GRAY_BANNER:
            case CYAN_BANNER:
            case PURPLE_BANNER:
            case BLUE_BANNER:
            case BROWN_BANNER:
            case GREEN_BANNER:
            case RED_BANNER:
            case BLACK_BANNER:
                return true;
            default:
                return false;
        }
    }

    public static boolean isDye(Material material)
    {
        switch(material)
        {
            case WHITE_DYE:
            case ORANGE_DYE:
            case MAGENTA_DYE:
            case LIGHT_BLUE_DYE:
            case YELLOW_DYE:
            case LIME_DYE:
            case PINK_DYE:
            case GRAY_DYE:
            case LIGHT_GRAY_DYE:
            case CYAN_DYE:
            case PURPLE_DYE:
            case BLUE_DYE:
            case BROWN_DYE:
            case GREEN_DYE:
            case RED_DYE:
            case BLACK_DYE:
                return true;
            default:
                return false;
        }
    }

    public static boolean isPattern(Material material)
    {
        switch(material)
        {
            case FLOWER_BANNER_PATTERN:
            case CREEPER_BANNER_PATTERN:
            case SKULL_BANNER_PATTERN:
            case MOJANG_BANNER_PATTERN:
            case GLOBE_BANNER_PATTERN:
            case PIGLIN_BANNER_PATTERN:
                return true;
            default:
                return false;
        }
    }

    public static boolean isTool(Material material)
    {
        return isWeapon(material) ||
                isArmor(material) ||
                isPickaxe(material) ||
                isShovel(material) ||
                isHoe(material) ||
                isAxe(material) ||
                material == Material.FISHING_ROD ||
                material == Material.ENCHANTED_BOOK;
    }

    public static boolean isWeapon(Material material)
    {
        return isBow(material) || isSword(material);
    }

    public static boolean isBow(Material material)
    {
        switch(material)
        {
            case BOW:
            case CROSSBOW:
                return true;
            default:
                return false;
        }
    }

    public static boolean isSword(Material material)
    {
        switch(material)
        {
            case WOODEN_SWORD:
            case STONE_SWORD:
            case IRON_SWORD:
            case GOLDEN_SWORD:
            case DIAMOND_SWORD:
            case NETHERITE_SWORD:
                return true;
            default:
                return false;
        }
    }

    public static boolean isPickaxe(Material material)
    {
        switch(material)
        {
            case WOODEN_PICKAXE:
            case STONE_PICKAXE:
            case IRON_PICKAXE:
            case GOLDEN_PICKAXE:
            case DIAMOND_PICKAXE:
            case NETHERITE_PICKAXE:
                return true;
            default:
                return false;
        }
    }

    public static boolean isHoe(Material material)
    {
        switch(material)
        {
            case WOODEN_HOE:
            case STONE_HOE:
            case IRON_HOE:
            case GOLDEN_HOE:
            case DIAMOND_HOE:
            case NETHERITE_HOE:
                return true;
            default:
                return false;
        }
    }

    public static boolean isShovel(Material material)
    {
        switch(material)
        {
            case WOODEN_SHOVEL:
            case STONE_SHOVEL:
            case IRON_SHOVEL:
            case GOLDEN_SHOVEL:
            case DIAMOND_SHOVEL:
            case NETHERITE_SHOVEL:
                return true;
            default:
                return false;
        }
    }

    public static boolean isAxe(Material material)
    {
        switch(material)
        {
            case WOODEN_AXE:
            case STONE_AXE:
            case IRON_AXE:
            case GOLDEN_AXE:
            case DIAMOND_AXE:
            case NETHERITE_AXE:
                return true;
            default:
                return false;
        }
    }

    public static boolean isApplicableForBeacon(Material material)
    {
        switch(material)
        {
            case IRON_INGOT:
            case GOLD_INGOT:
            case DIAMOND:
            case EMERALD:
            case NETHERITE_INGOT:
                return true;
            default:
                return false;
        }
    }

    public static boolean isBottle(Material material)
    {
        switch(material)
        {
            case GLASS_BOTTLE:
            case POTION:
            case LINGERING_POTION:
            case SPLASH_POTION:
                return true;
            default:
                return true;
        }
    }

    public static boolean isApplicableForBrewing(Material material)
    {
        switch(material)
        {
            case NETHER_WART:
            case GUNPOWDER:
            case SPIDER_EYE:
            case GHAST_TEAR:
            case RABBIT_FOOT:
            case BLAZE_POWDER:
            case GLISTERING_MELON_SLICE:
            case SUGAR:
            case MAGMA_CREAM:
            case REDSTONE:
            case GLOWSTONE_DUST:
            case PUFFERFISH:
            case GOLDEN_CARROT:
            case TURTLE_HELMET:
            case PHANTOM_MEMBRANE:
            case FERMENTED_SPIDER_EYE:
                return true;
            default:
                return true;
        }
    }
}
