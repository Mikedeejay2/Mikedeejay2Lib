package com.mikedeejay2.mikedeejay2lib.util.item;

/**
 * Utility class for processing enchantments.
 *
 * @author Mikedeejay2
 */
public final class EnchantUtil
{
    /**
     * Get an enchantment's field name from the original enchantment name. Used for reflection and code generation.
     *
     * @param enchantName The name of the enchantment to get in field form
     * @return The field name of the enchantment, null if custom enchant.
     */
    public static String getFieldEnchantName(String enchantName)
    {
        switch(enchantName.toLowerCase())
        {
            case "protection":              return "PROTECTION_ENVIRONMENTAL";
            case "fire_protection":         return "PROTECTION_FIRE";
            case "feather_falling":         return "PROTECTION_FALL";
            case "blast_protection":        return "PROTECTION_EXPLOSIONS";
            case "projectile_protection":   return "PROTECTION_PROJECTILE";
            case "respiration":             return "OXYGEN";
            case "aqua_affinity":           return "WATER_WORKER";
            case "thorns":                  return "THORNS";
            case "depth_strider":           return "DEPTH_STRIDER";
            case "frost_walker":            return "FROST_WALKER";
            case "binding_curse":           return "BINDING_CURSE";
            case "sharpness":               return "DAMAGE_ALL";
            case "smite":                   return "DAMAGE_UNDEAD";
            case "bane_of_arthropods":      return "DAMAGE_ARTHROPODS";
            case "knockback":               return "KNOCKBACK";
            case "fire_aspect":             return "FIRE_ASPECT";
            case "looting":                 return "LOOT_BONUS_MOBS";
            case "sweeping":                return "SWEEPING_EDGE";
            case "efficiency":              return "DIG_SPEED";
            case "silk_touch":              return "SILK_TOUCH";
            case "unbreaking":              return "DURABILITY";
            case "fortune":                 return "LOOT_BONUS_BLOCKS";
            case "power":                   return "ARROW_DAMAGE";
            case "punch":                   return "ARROW_KNOCKBACK";
            case "flame":                   return "ARROW_FIRE";
            case "infinity":                return "ARROW_INFINITE";
            case "luck_of_the_sea":         return "LUCK";
            case "lure":                    return "LURE";
            case "loyalty":                 return "LOYALTY";
            case "impaling":                return "IMPALING";
            case "riptide":                 return "RIPTIDE";
            case "channeling":              return "CHANNELING";
            case "multishot":               return "MULTISHOT";
            case "quick_charge":            return "QUICK_CHARGE";
            case "piercing":                return "PIERCING";
            case "mending":                 return "MENDING";
            case "vanishing_curse":         return "VANISHING_CURSE";
            case "soul_speed":              return "SOUL_SPEED";
            default:
                return null;
        }
    }
}
