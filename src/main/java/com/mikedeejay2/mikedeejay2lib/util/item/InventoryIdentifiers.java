package com.mikedeejay2.mikedeejay2lib.util.item;

import com.mikedeejay2.mikedeejay2lib.util.recipe.RecipeUtil;
import com.mikedeejay2.mikedeejay2lib.util.structure.tuple.MutablePair;
import com.mikedeejay2.mikedeejay2lib.util.structure.tuple.Pair;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.*;

import java.util.AbstractMap;
import java.util.Map;

/**
 * Utility class of inventory identifiers and detection methods for fast item checking
 *
 * @author Mikedeejay2
 */
public final class InventoryIdentifiers {
    /**
     * The slot of the offhand (Regular slot)
     */
    public static final int OFFHAND_SLOT = 40;

    /**
     * The helmet armor slot (Regular slot)
     */
    public static final int HELMET_SLOT = 39;

    /**
     * The chestplate armor slot (Regular slot)
     */
    public static final int CHESTPLATE_SLOT = 38;

    /**
     * The leggings armor slot (Regular slot)
     */
    public static final int LEGGINGS_SLOT = 37;

    /**
     * The boots armor slot (Regular slot)
     */
    public static final int BOOTS_SLOT = 36;

    /**
     * Returns whether the material is a shulker box or not
     *
     * @param material The material to check
     * @return Whether the material is a shulker box or not
     */
    public static boolean isShulkerBox(Material material) {
        switch(material) {
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

    /**
     * Returns whether the material is an armor piece or not
     *
     * @param material The material to check
     * @return Whether the material is an armor piece or not
     */
    public static boolean isArmor(Material material) {
        return isHelmet(material) || isChestplate(material) || isLeggings(material) || isBoots(material);
    }

    /**
     * Returns whether the material is a helmet or not
     *
     * @param material The material to check
     * @return Whether the material is a helmet or not
     */
    public static boolean isHelmet(Material material) {
        switch(material) {
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

    /**
     * Returns whether the material is a chestplate or not
     *
     * @param material The material to check
     * @return Whether the material is a chestplate or not
     */
    public static boolean isChestplate(Material material) {
        switch(material) {
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

    /**
     * Returns whether the material is leggings or not
     *
     * @param material The material to check
     * @return Whether the material is leggings or not
     */
    public static boolean isLeggings(Material material) {
        switch(material) {
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

    /**
     * Returns whether the material is boots or not
     *
     * @param material The material to check
     * @return Whether the material is boots or not
     */
    public static boolean isBoots(Material material) {
        switch(material) {
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

    /**
     * Returns whether the material shift clicks into the offhand or not
     *
     * @param material The material to check
     * @return Whether the material shift clicks into the offhand or not
     */
    public static boolean isOffhand(Material material) {
        switch(material) {
            case SHIELD:
                return true;
            default:
                return false;
        }
    }

    /**
     * Returns whether the specified slot is a slot of the off hand
     *
     * @param slot The slot to check
     * @return Whether the slot is the off hand or not
     */
    public static boolean isOffhandSlot(int slot) {
        return slot == OFFHAND_SLOT;
    }

    /**
     * Returns whether the specified slot is an armor slot or not
     *
     * @param slot The slot to check
     * @return Whether the slot is an armor slot or not
     */
    public static boolean isArmorSlot(int slot) {
        return slot <= HELMET_SLOT && slot >= BOOTS_SLOT;
    }

    /**
     * See whether a specified material is applicable to be in a specific raw slot in an inventory
     *
     * @param rawSlot  The raw slot to test
     * @param invView  The <code>InventoryView</code> to use as a reference
     * @param material The material to test
     * @return A <code>Pair</code> containing two booleans:
     * <ol>
     *     <li>Boolean (Left) - Whether the material should be in that slot</li>
     *     <li>Boolean (Right) - Whether the item has the ability to be in that slot, regardless of whether it should be</li>
     * </ol>
     */
    public static Pair<Boolean, Boolean> applicableForSlot(int rawSlot, InventoryView invView, Material material) {
        InventoryType.SlotType slotType = invView.getSlotType(rawSlot);
        InventoryType          invType  = invView.getType();
        int                    slot     = invView.convertSlot(rawSlot);

        switch(invType) {
            case LOOM: {
                switch(rawSlot) {
                    case 0:
                        return new MutablePair<>(isBanner(material), false);
                    case 1:
                        return new MutablePair<>(isDye(material), false);
                    case 2:
                        return new MutablePair<>(isPattern(material), false);
                }
            }
            break;
            case BREWING: {
                switch(rawSlot) {
                    case 0:
                    case 1:
                    case 2:
                        return new MutablePair<>(isBottle(material), false);
                    case 4:
                        return new MutablePair<>(material == Material.BLAZE_POWDER, false);
                    case 3:
                        return new MutablePair<>(isApplicableForBrewing(material), false);
                }
            }
            break;
            case CARTOGRAPHY: {
                switch(rawSlot) {
                    case 0:
                        return new MutablePair<>(material == Material.FILLED_MAP, false);
                    case 1:
                        return new MutablePair<>(material == Material.PAPER, false);
                }
            }
            break;
            case ENCHANTING: {
                switch(rawSlot) {
                    case 1: return new MutablePair<>(material == Material.LAPIS_LAZULI, false);
                    case 0:return new MutablePair<>(material != Material.LAPIS_LAZULI, true);
                }
            }
            break;
            case GRINDSTONE: {
                if(rawSlot == 0 || rawSlot == 1) {
                    return new MutablePair<>(isTool(material), false);
                }
            }
            break;
            case SHULKER_BOX: {
                return new MutablePair<>(!isShulkerBox(material), false);
            }
            case FURNACE: {
                switch(rawSlot) {
                    case 0:
                        return new MutablePair<>(RecipeUtil.isFurnaceInput(material), true);
                    case 1:
                        return new MutablePair<>(material.isFuel(), false);
                }

            }
            case BLAST_FURNACE: {
                if(rawSlot == 0) {
                    return new MutablePair<>(RecipeUtil.isBlastingInput(material), true);
                }
            }
            case SMOKER: {
                if(rawSlot == 0) {
                    return new MutablePair<>(RecipeUtil.isSmokingInput(material), true);
                }
            }
            case BEACON: {
                if(rawSlot == 0) {
                    return new MutablePair<>(isApplicableForBeacon(material), false);
                }
            }
        }

        Inventory topInv = invView.getTopInventory();
        if(topInv instanceof HorseInventory) {
            switch(rawSlot) {
                case 0:
                    return new MutablePair<>(material == Material.SADDLE, false);
                case 1:
                    return new MutablePair<>(isHorseArmor(material), false);
            }
        }
        else if(topInv instanceof LlamaInventory) {
            switch(rawSlot) {
                case 0:
                    return new MutablePair<>(false, false);
                case 1:
                    return new MutablePair<>(isCarpet(material), false);
            }
        }
        else if(topInv instanceof AbstractHorseInventory) {
            switch(rawSlot) {
                case 0:
                    return new MutablePair<>(material == Material.SADDLE, false);
                case 1:
                    return new MutablePair<>(false, false);
            }
        }

        switch(slotType) {
            case RESULT:
                return new MutablePair<>(false, false);
            case ARMOR: {
                if(slot == BOOTS_SLOT && !isBoots(material)) return new MutablePair<>(true, false);
                if(slot == LEGGINGS_SLOT && !isLeggings(material)) return new MutablePair<>(true, false);
                if(slot == CHESTPLATE_SLOT && !isChestplate(material)) return new MutablePair<>(true, false);
                if(slot == HELMET_SLOT && !isHelmet(material)) return new MutablePair<>(true, false);
                return new MutablePair<>(false, false);
            }
            case FUEL: {
                return new MutablePair<>(material.isFuel(), true);
            }
            case QUICKBAR:
            case CONTAINER:
            case OUTSIDE:
            case CRAFTING:
                return new MutablePair<>(true, true);
        }
        return new MutablePair<>(false, false);
    }

    /**
     * See whether a raw slot is a singleton slot wherein it can only hold a single item
     *
     * @param rawSlot  The raw slot to test
     * @param invView  The <code>InventoryView</code> to use as a reference
     * @return A boolean representing whether the slot is or is not a singleton slot
     */
    public static boolean singletonSlot(int rawSlot, InventoryView invView) {
        InventoryType invType = invView.getType();

        switch(invType) {
            case LOOM: {
                switch(rawSlot) {
                    case 0:
                    case 1:
                        return false;
                    case 2:
                        return true;
                }
            }
            break;
            case BREWING: {
                switch(rawSlot) {
                    case 0:
                    case 1:
                    case 2:
                        return true;
                    case 4:
                    case 3:
                        return false;
                }
            }
            break;
            case CARTOGRAPHY: {
                if(rawSlot == 0 || rawSlot == 1) {
                    return false;
                }
            }
            break;
            case GRINDSTONE: {
                return rawSlot == 0 || rawSlot == 1;
            }
            case ENCHANTING:
            case BEACON: {
                return rawSlot == 0;
            }
        }

        Inventory topInv = invView.getTopInventory();
        if(topInv instanceof AbstractHorseInventory) {
            return rawSlot == 0 || rawSlot == 1;
        }

        return false;
    }

    /**
     * Returns whether the material is a banner or not
     *
     * @param material The material to check
     * @return Whether the material is a banner or not
     */
    public static boolean isBanner(Material material) {
        switch(material) {
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

    /**
     * Returns whether the material is a dye or not
     *
     * @param material The material to check
     * @return Whether the material is a dye or not
     */
    public static boolean isDye(Material material) {
        switch(material) {
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

    /**
     * Returns whether the material is a pattern or not
     *
     * @param material The material to check
     * @return Whether the material is a pattern or not
     */
    public static boolean isPattern(Material material) {
        switch(material) {
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

    /**
     * Returns whether the material is a tool or not
     *
     * @param material The material to check
     * @return Whether the material is a tool or not
     */
    public static boolean isTool(Material material) {
        return isWeapon(material) ||
                isArmor(material) ||
                isPickaxe(material) ||
                isShovel(material) ||
                isHoe(material) ||
                isAxe(material) ||
                material == Material.FISHING_ROD ||
                material == Material.ENCHANTED_BOOK ||
                material == Material.SHEARS ||
                material == Material.FLINT_AND_STEEL;
    }

    /**
     * Returns whether the material is a weapon or not
     *
     * @param material The material to check
     * @return Whether the material is a weapon or not
     */
    public static boolean isWeapon(Material material) {
        return isBow(material) || isSword(material) || material == Material.TRIDENT;
    }

    /**
     * Returns whether the material is a bow or not
     *
     * @param material The material to check
     * @return Whether the material is a bow or not
     */
    public static boolean isBow(Material material) {
        switch(material) {
            case BOW:
            case CROSSBOW:
                return true;
            default:
                return false;
        }
    }

    /**
     * Returns whether the material is a sword or not
     *
     * @param material The material to check
     * @return Whether the material is a sword or not
     */
    public static boolean isSword(Material material) {
        switch(material) {
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

    /**
     * Returns whether the material is a pickaxe or not
     *
     * @param material The material to check
     * @return Whether the material is a pickaxe or not
     */
    public static boolean isPickaxe(Material material) {
        switch(material) {
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

    /**
     * Returns whether the material is a hoe or not
     *
     * @param material The material to check
     * @return Whether the material is a hoe or not
     */
    public static boolean isHoe(Material material) {
        switch(material) {
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

    /**
     * Returns whether the material is a shovel or not
     *
     * @param material The material to check
     * @return Whether the material is a shovel or not
     */
    public static boolean isShovel(Material material) {
        switch(material) {
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

    /**
     * Returns whether the material is an axe or not
     *
     * @param material The material to check
     * @return Whether the material is an axe or not
     */
    public static boolean isAxe(Material material) {
        switch(material) {
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

    /**
     * Returns whether the material is applicable for a beacon or not
     *
     * @param material The material to check
     * @return Whether the material is applicable for a beacon or not
     */
    public static boolean isApplicableForBeacon(Material material) {
        switch(material) {
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

    /**
     * Returns whether the material is a bottle or not
     *
     * @param material The material to check
     * @return Whether the material is a bottle or not
     */
    public static boolean isBottle(Material material) {
        switch(material) {
            case GLASS_BOTTLE:
            case POTION:
            case LINGERING_POTION:
            case SPLASH_POTION:
                return true;
            default:
                return false;
        }
    }

    /**
     * Returns whether the material is applicable for brewing or not
     *
     * @param material The material to check
     * @return Whether the material is applicable for brewing or not
     */
    public static boolean isApplicableForBrewing(Material material) {
        switch(material) {
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
                return false;
        }
    }

    /**
     * Returns whether the material is horse armor or not
     *
     * @param material The material to check
     * @return Whether the material is horse armor or not
     */
    public static boolean isHorseArmor(Material material) {
        switch(material) {
            case LEATHER_HORSE_ARMOR:
            case IRON_HORSE_ARMOR:
            case GOLDEN_HORSE_ARMOR:
            case DIAMOND_HORSE_ARMOR:
                return true;
            default:
                return false;
        }
    }

    /**
     * Returns whether the material is carpet or not
     *
     * @param material The material to check
     * @return Whether the material is carpet or not
     */
    public static boolean isCarpet(Material material) {
        switch(material) {
            case WHITE_CARPET:
            case ORANGE_CARPET:
            case MAGENTA_CARPET:
            case LIGHT_BLUE_CARPET:
            case YELLOW_CARPET:
            case LIME_CARPET:
            case PINK_CARPET:
            case GRAY_CARPET:
            case LIGHT_GRAY_CARPET:
            case CYAN_CARPET:
            case PURPLE_CARPET:
            case BLUE_CARPET:
            case BROWN_CARPET:
            case GREEN_CARPET:
            case RED_CARPET:
            case BLACK_CARPET:
                return true;
            default:
                return false;
        }
    }

    /**
     * Returns whether the <code>InventoryAction</code> will take the item in a result slot or not
     *
     * @param action The action to check
     * @return Whether the item will be taken or not
     */
    public static boolean takeResult(InventoryAction action) {
        switch(action) {
            case PICKUP_ALL:
            case PICKUP_HALF:
            case PICKUP_ONE:
            case PICKUP_SOME:
            case MOVE_TO_OTHER_INVENTORY:
                return true;
            default:
                return false;
        }
    }

    /**
     * Returns whether the material is a soup or stew or not
     *
     * @param material The material to check
     * @return Whether the material is a type of soup or stew
     */
    public static boolean isSoup(Material material) {
        switch(material) {
            case BEETROOT_SOUP:
            case SUSPICIOUS_STEW:
            case MUSHROOM_STEW:
            case RABBIT_STEW:
                return true;
            default:
                return false;
        }
    }

    /**
     * Returns whether the material is a type of bucket or not
     *
     * @param material The material to check
     * @return Whether the material is a type of bucket or not
     */
    public static boolean isBucket(Material material) {
        switch(material) {
            case BUCKET:
            case COD_BUCKET:
            case LAVA_BUCKET:
            case MILK_BUCKET:
            case PUFFERFISH_BUCKET:
            case SALMON_BUCKET:
            case TROPICAL_FISH_BUCKET:
            case WATER_BUCKET:
                return true;
            default:
                return false;
        }
    }
}
