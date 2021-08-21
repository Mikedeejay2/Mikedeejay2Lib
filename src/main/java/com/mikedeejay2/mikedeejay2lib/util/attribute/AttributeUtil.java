package com.mikedeejay2.mikedeejay2lib.util.attribute;

import com.google.common.base.Preconditions;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.UUID;

/**
 * Static utility class for modifying and getting data of {@link AttributeInstance} and {@link AttributeModifier}
 *
 * @author Mikedeejay2
 */
public final class AttributeUtil
{
    /**
     * Get whether an {@link ItemMeta} has a specified {@link AttributeModifier} based off of UUID
     *
     * @param uuid     The UUID of the {@link AttributeModifier}
     * @param itemMeta The <code>ItemMeta</code> to reference
     * @return Whether the {@link AttributeModifier} was found or not
     */
    public static boolean hasAttributeModifier(@NotNull final UUID uuid, ItemMeta itemMeta)
    {
        Preconditions.checkNotNull(uuid, "UUID cannot be null");
        if(itemMeta == null) return false;
        if(!itemMeta.hasAttributeModifiers()) return false;
        for(AttributeModifier modifier : itemMeta.getAttributeModifiers().values())
        {
            if(uuid.equals(modifier.getUniqueId())) return true;
        }
        return false;
    }

    /**
     * Get whether an {@link ItemMeta} has a specified {@link AttributeModifier} based off of UUID
     *
     * @param uuid     The UUID of the {@link AttributeModifier}
     * @param itemMeta The <code>ItemMeta</code> to reference
     * @param slot     The {@link EquipmentSlot} of the modifier
     * @return Whether the {@link AttributeModifier} was found or not
     */
    public static boolean hasAttributeModifier(@NotNull final UUID uuid, ItemMeta itemMeta, @NotNull final EquipmentSlot slot)
    {
        Preconditions.checkNotNull(uuid, "UUID cannot be null");
        Preconditions.checkNotNull(slot, "EquipmentSlot cannot be null");
        if(itemMeta == null) return false;
        if(!itemMeta.hasAttributeModifiers()) return false;
        for(AttributeModifier modifier : itemMeta.getAttributeModifiers(slot).values())
        {
            if(uuid.equals(modifier.getUniqueId())) return true;
        }
        return false;
    }

    /**
     * Get whether an {@link ItemMeta} has a specified {@link AttributeModifier} based off of UUID
     *
     * @param uuid     The UUID of the {@link AttributeModifier}
     * @param itemMeta The <code>ItemMeta</code> to reference
     * @param attribute     The {@link AttributeInstance} of the modifier
     * @return Whether the {@link AttributeModifier} was found or not
     */
    public static boolean hasAttributeModifier(@NotNull final UUID uuid, ItemMeta itemMeta, @NotNull final Attribute attribute)
    {
        Preconditions.checkNotNull(uuid, "UUID cannot be null");
        Preconditions.checkNotNull(attribute, "Attribute cannot be null");
        if(itemMeta == null) return false;
        if(!itemMeta.hasAttributeModifiers()) return false;
        for(AttributeModifier modifier : itemMeta.getAttributeModifiers(attribute))
        {
            if(uuid.equals(modifier.getUniqueId())) return true;
        }
        return false;
    }

    /**
     * Get whether an {@link AttributeInstance} has a specified {@link AttributeModifier} based off of UUID
     *
     * @param uuid     The UUID of the {@link AttributeModifier}
     * @param attribute     The {@link AttributeInstance} of the modifier
     * @return Whether the {@link AttributeModifier} was found or not
     */
    public static boolean hasAttributeModifier(@NotNull final UUID uuid, @NotNull final AttributeInstance attribute)
    {
        Preconditions.checkNotNull(uuid, "UUID cannot be null");
        Preconditions.checkNotNull(attribute, "Attribute cannot be null");
        for(AttributeModifier modifier : attribute.getModifiers())
        {
            if(uuid.equals(modifier.getUniqueId())) return true;
        }
        return false;
    }

    /**
     * Get whether an {@link ItemMeta} has a specified {@link AttributeModifier} based off of name
     *
     * @param name     The name of the {@link AttributeModifier}
     * @param itemMeta The <code>ItemMeta</code> to reference
     * @return Whether the {@link AttributeModifier} was found or not
     */
    public static boolean hasAttributeModifier(@NotNull final String name, ItemMeta itemMeta)
    {
        Preconditions.checkNotNull(name, "Modifier name cannot be null");
        if(itemMeta == null) return false;
        if(!itemMeta.hasAttributeModifiers()) return false;
        for(AttributeModifier modifier : itemMeta.getAttributeModifiers().values())
        {
            if(name.equals(modifier.getName())) return true;
        }
        return false;
    }

    /**
     * Get whether an {@link ItemMeta} has a specified {@link AttributeModifier} based off of name
     *
     * @param name     The name of the {@link AttributeModifier}
     * @param itemMeta The <code>ItemMeta</code> to reference
     * @param slot     The {@link EquipmentSlot} of the modifier
     * @return Whether the {@link AttributeModifier} was found or not
     */
    public static boolean hasAttributeModifier(@NotNull final String name, ItemMeta itemMeta, @NotNull final EquipmentSlot slot)
    {
        Preconditions.checkNotNull(name, "Modifier name cannot be null");
        Preconditions.checkNotNull(slot, "EquipmentSlot cannot be null");
        if(itemMeta == null) return false;
        if(!itemMeta.hasAttributeModifiers()) return false;
        for(AttributeModifier modifier : itemMeta.getAttributeModifiers(slot).values())
        {
            if(name.equals(modifier.getName())) return true;
        }
        return false;
    }

    /**
     * Get whether an {@link ItemMeta} has a specified {@link AttributeModifier} based off of name
     *
     * @param name     The name of the {@link AttributeModifier}
     * @param itemMeta The <code>ItemMeta</code> to reference
     * @param attribute     The {@link AttributeInstance} of the modifier
     * @return Whether the {@link AttributeModifier} was found or not
     */
    public static boolean hasAttributeModifier(@NotNull final String name, ItemMeta itemMeta, @NotNull final Attribute attribute)
    {
        Preconditions.checkNotNull(name, "Modifier name cannot be null");
        Preconditions.checkNotNull(attribute, "Attribute cannot be null");
        if(itemMeta == null) return false;
        if(!itemMeta.hasAttributeModifiers()) return false;
        for(AttributeModifier modifier : itemMeta.getAttributeModifiers(attribute))
        {
            if(name.equals(modifier.getName())) return true;
        }
        return false;
    }

    /**
     * Get whether an {@link AttributeInstance} has a specified {@link AttributeModifier} based off of name
     *
     * @param name     The name of the {@link AttributeModifier}
     * @param attribute     The {@link AttributeInstance} of the modifier
     * @return Whether the {@link AttributeModifier} was found or not
     */
    public static boolean hasAttributeModifier(@NotNull final String name, @NotNull final AttributeInstance attribute)
    {
        Preconditions.checkNotNull(name, "Modifier name cannot be null");
        Preconditions.checkNotNull(attribute, "Attribute cannot be null");
        for(AttributeModifier modifier : attribute.getModifiers())
        {
            if(name.equals(modifier.getName())) return true;
        }
        return false;
    }

    /**
     * Get whether an {@link ItemMeta} has a specified {@link AttributeModifier} based off of the modifier's instance
     *
     * @param attribModifier The {@link AttributeModifier} reference
     * @param itemMeta       The <code>ItemMeta</code> to reference
     * @return Whether the {@link AttributeModifier} was found or not
     */
    public static boolean hasAttributeModifier(@NotNull final AttributeModifier attribModifier, ItemMeta itemMeta)
    {
        Preconditions.checkNotNull(attribModifier, "Modifier cannot be null");
        if(itemMeta == null) return false;
        if(!itemMeta.hasAttributeModifiers()) return false;
        for(AttributeModifier modifier : itemMeta.getAttributeModifiers().values())
        {
            if(attribModifier.equals(modifier)) return true;
        }
        return false;
    }

    /**
     * Get whether an {@link ItemMeta} has a specified {@link AttributeModifier} based off of the modifier's instance
     *
     * @param attribModifier The {@link AttributeModifier} reference
     * @param itemMeta       The <code>ItemMeta</code> to reference
     * @param slot           The {@link EquipmentSlot} of the modifier
     * @return Whether the {@link AttributeModifier} was found or not
     */
    public static boolean hasAttributeModifier(@NotNull final AttributeModifier attribModifier, ItemMeta itemMeta, @NotNull final EquipmentSlot slot)
    {
        Preconditions.checkNotNull(attribModifier, "Modifier cannot be null");
        Preconditions.checkNotNull(slot, "EquipmentSlot cannot be null");
        if(itemMeta == null) return false;
        if(!itemMeta.hasAttributeModifiers()) return false;
        for(AttributeModifier modifier : itemMeta.getAttributeModifiers(slot).values())
        {
            if(attribModifier.equals(modifier)) return true;
        }
        return false;
    }

    /**
     * Get whether an {@link ItemMeta} has a specified {@link AttributeModifier} based off of the modifier's instance
     *
     * @param attribModifier The {@link AttributeModifier} reference
     * @param itemMeta       The <code>ItemMeta</code> to reference
     * @param attribute      The {@link AttributeInstance} of the modifier
     * @return Whether the {@link AttributeModifier} was found or not
     */
    public static boolean hasAttributeModifier(@NotNull final AttributeModifier attribModifier, ItemMeta itemMeta, @NotNull final Attribute attribute)
    {
        Preconditions.checkNotNull(attribModifier, "Modifier cannot be null");
        Preconditions.checkNotNull(attribute, "Attribute cannot be null");
        if(itemMeta == null) return false;
        if(!itemMeta.hasAttributeModifiers()) return false;
        for(AttributeModifier modifier : itemMeta.getAttributeModifiers(attribute))
        {
            if(attribModifier.equals(modifier)) return true;
        }
        return false;
    }

    /**
     * Get whether an {@link AttributeInstance} has a specified {@link AttributeModifier} based off of the modifier's instance
     *
     * @param attribModifier The {@link AttributeModifier} reference
     * @param attribute      The {@link AttributeInstance} of the modifier
     * @return Whether the {@link AttributeModifier} was found or not
     */
    public static boolean hasAttributeModifier(@NotNull final AttributeModifier attribModifier, @NotNull final AttributeInstance attribute)
    {
        Preconditions.checkNotNull(attribModifier, "Modifier cannot be null");
        Preconditions.checkNotNull(attribute, "Attribute cannot be null");
        for(AttributeModifier modifier : attribute.getModifiers())
        {
            if(attribModifier.equals(modifier)) return true;
        }
        return false;
    }

    /**
     * Add an {@link AttributeModifier} to an {@link ItemMeta}.
     * <p>
     * Includes safety checking: If modifier already exists, replace it.
     *
     * @param attribModifier The {@link AttributeModifier} reference
     * @param itemMeta       The <code>ItemMeta</code> to reference
     * @param attribute      The {@link AttributeInstance} of the modifier
     */
    public static void addAttributeModifier(@NotNull final AttributeModifier attribModifier, @NotNull ItemMeta itemMeta, @NotNull final Attribute attribute)
    {
        Preconditions.checkNotNull(attribModifier, "Modifier cannot be null");
        Preconditions.checkNotNull(itemMeta, "ItemMeta cannot be null");
        if(hasAttributeModifier(attribModifier.getUniqueId(), itemMeta))
        {
            removeAttributeModifier(attribModifier.getUniqueId(), itemMeta);
        }
        itemMeta.addAttributeModifier(attribute, attribModifier);
    }

    /**
     * Add an {@link AttributeModifier} to an {@link AttributeInstance}.
     * <p>
     * Includes safety checking: If modifier already exists, replace it.
     *
     * @param attribModifier The {@link AttributeModifier} reference
     * @param attribute      The {@link AttributeInstance} of the modifier
     */
    public static void addAttributeModifier(@NotNull final AttributeModifier attribModifier, @NotNull final AttributeInstance attribute)
    {
        Preconditions.checkNotNull(attribModifier, "Modifier cannot be null");
        Preconditions.checkNotNull(attribute, "Attribute cannot be null");
        if(hasAttributeModifier(attribModifier.getUniqueId(), attribute))
        {
            removeAttributeModifier(attribModifier.getUniqueId(), attribute);
        }
        attribute.addModifier(attribModifier);
    }

    /**
     * Remove an {@link AttributeModifier} from an {@link ItemMeta} UUID
     *
     * @param uuid     The UUID of the {@link AttributeModifier}
     * @param itemMeta The <code>ItemMeta</code> to reference
     * @return Whether the {@link AttributeModifier} was removed
     */
    public static boolean removeAttributeModifier(@NotNull final UUID uuid, ItemMeta itemMeta)
    {
        Preconditions.checkNotNull(uuid, "UUID cannot be null");
        Preconditions.checkNotNull(itemMeta, "ItemMeta cannot be null");
        if(!itemMeta.hasAttributeModifiers()) return false;
        for(Map.Entry<Attribute, AttributeModifier> entry : itemMeta.getAttributeModifiers().entries())
        {
            AttributeModifier modifier = entry.getValue();
            Attribute attribute = entry.getKey();
            if(uuid.equals(modifier.getUniqueId())) {
                itemMeta.removeAttributeModifier(attribute, modifier);
                return true;
            }
        }
        return false;
    }

    /**
     * Remove an {@link AttributeModifier} from an {@link ItemMeta} UUID
     *
     * @param uuid     The UUID of the {@link AttributeModifier}
     * @param itemMeta The <code>ItemMeta</code> to reference
     * @param slot     The {@link EquipmentSlot} of the modifier
     * @return Whether the {@link AttributeModifier} was removed
     */
    public static boolean removeAttributeModifier(@NotNull final UUID uuid, ItemMeta itemMeta, @NotNull final EquipmentSlot slot)
    {
        Preconditions.checkNotNull(uuid, "UUID cannot be null");
        Preconditions.checkNotNull(slot, "EquipmentSlot cannot be null");
        Preconditions.checkNotNull(itemMeta, "ItemMeta cannot be null");
        if(!itemMeta.hasAttributeModifiers()) return false;
        for(Map.Entry<Attribute, AttributeModifier> entry : itemMeta.getAttributeModifiers(slot).entries())
        {
            AttributeModifier modifier = entry.getValue();
            Attribute attribute = entry.getKey();
            if(uuid.equals(modifier.getUniqueId())) {
                itemMeta.removeAttributeModifier(attribute, modifier);
                return true;
            }
        }
        return false;
    }

    /**
     * Remove an {@link AttributeModifier} from an {@link ItemMeta} UUID
     *
     * @param uuid      The UUID of the {@link AttributeModifier}
     * @param itemMeta  The <code>ItemMeta</code> to reference
     * @param attribute The {@link AttributeInstance} of the modifier
     * @return Whether the {@link AttributeModifier} was removed
     */
    public static boolean removeAttributeModifier(@NotNull final UUID uuid, ItemMeta itemMeta, @NotNull final Attribute attribute)
    {
        Preconditions.checkNotNull(uuid, "UUID cannot be null");
        Preconditions.checkNotNull(attribute, "Attribute cannot be null");
        Preconditions.checkNotNull(itemMeta, "ItemMeta cannot be null");
        if(!itemMeta.hasAttributeModifiers()) return false;
        for(AttributeModifier modifier : itemMeta.getAttributeModifiers(attribute))
        {
            if(uuid.equals(modifier.getUniqueId())) {
                itemMeta.removeAttributeModifier(attribute, modifier);
                return true;
            }
        }
        return false;
    }

    /**
     * Remove an {@link AttributeModifier} from an {@link AttributeInstance} UUID
     *
     * @param uuid      The UUID of the {@link AttributeModifier}
     * @param attribute The {@link AttributeInstance} of the modifier
     * @return Whether the {@link AttributeModifier} was removed
     */
    public static boolean removeAttributeModifier(@NotNull final UUID uuid, @NotNull final AttributeInstance attribute)
    {
        Preconditions.checkNotNull(uuid, "UUID cannot be null");
        Preconditions.checkNotNull(attribute, "Attribute cannot be null");
        for(AttributeModifier modifier : attribute.getModifiers())
        {
            if(uuid.equals(modifier.getUniqueId())) {
                attribute.removeModifier(modifier);
                return true;
            }
        }
        return false;
    }

    /**
     * Remove an {@link AttributeModifier} from an {@link ItemMeta} name
     *
     * @param name     The name of the {@link AttributeModifier}
     * @param itemMeta The <code>ItemMeta</code> to reference
     * @return Whether the {@link AttributeModifier} was removed
     */
    public static boolean removeAttributeModifier(@NotNull final String name, ItemMeta itemMeta)
    {
        Preconditions.checkNotNull(name, "Modifier name cannot be null");
        Preconditions.checkNotNull(itemMeta, "ItemMeta cannot be null");
        if(!itemMeta.hasAttributeModifiers()) return false;
        boolean success = false;
        for(Map.Entry<Attribute, AttributeModifier> entry : itemMeta.getAttributeModifiers().entries())
        {
            AttributeModifier modifier = entry.getValue();
            Attribute attribute = entry.getKey();
            if(name.equals(modifier.getName())) {
                itemMeta.removeAttributeModifier(attribute, modifier);
                success = true;
            }
        }
        return success;
    }

    /**
     * Remove an {@link AttributeModifier} from an {@link ItemMeta} name
     *
     * @param name     The name of the {@link AttributeModifier}
     * @param itemMeta The <code>ItemMeta</code> to reference
     * @param slot     The {@link EquipmentSlot} of the modifier
     * @return Whether the {@link AttributeModifier} was removed
     */
    public static boolean removeAttributeModifier(@NotNull final String name, ItemMeta itemMeta, @NotNull final EquipmentSlot slot)
    {
        Preconditions.checkNotNull(name, "Modifier name cannot be null");
        Preconditions.checkNotNull(slot, "EquipmentSlot cannot be null");
        Preconditions.checkNotNull(itemMeta, "ItemMeta cannot be null");
        if(!itemMeta.hasAttributeModifiers()) return false;
        boolean success = false;
        for(Map.Entry<Attribute, AttributeModifier> entry : itemMeta.getAttributeModifiers(slot).entries())
        {
            AttributeModifier modifier = entry.getValue();
            Attribute attribute = entry.getKey();
            if(name.equals(modifier.getName())) {
                itemMeta.removeAttributeModifier(attribute, modifier);
                success = true;
            }
        }
        return success;
    }

    /**
     * Remove an {@link AttributeModifier} from an {@link ItemMeta} name
     *
     * @param name      The name of the {@link AttributeModifier}
     * @param itemMeta  The <code>ItemMeta</code> to reference
     * @param attribute The {@link AttributeInstance} of the modifier
     * @return Whether the {@link AttributeModifier} was removed
     */
    public static boolean removeAttributeModifier(@NotNull final String name, ItemMeta itemMeta, @NotNull final Attribute attribute)
    {
        Preconditions.checkNotNull(name, "Modifier name cannot be null");
        Preconditions.checkNotNull(attribute, "Attribute cannot be null");
        Preconditions.checkNotNull(itemMeta, "ItemMeta cannot be null");
        if(!itemMeta.hasAttributeModifiers()) return false;
        boolean success = false;
        for(AttributeModifier modifier : itemMeta.getAttributeModifiers(attribute))
        {
            if(name.equals(modifier.getName())) {
                itemMeta.removeAttributeModifier(attribute, modifier);
                success = true;
            }
        }
        return success;
    }

    /**
     * Remove an {@link AttributeModifier} from an {@link AttributeInstance} name
     *
     * @param name      The name of the {@link AttributeModifier}
     * @param attribute The {@link AttributeInstance} of the modifier
     * @return Whether the {@link AttributeModifier} was removed
     */
    public static boolean removeAttributeModifier(@NotNull final String name, @NotNull final AttributeInstance attribute)
    {
        Preconditions.checkNotNull(name, "Modifier name cannot be null");
        Preconditions.checkNotNull(attribute, "Attribute cannot be null");
        boolean success = false;
        for(AttributeModifier modifier : attribute.getModifiers())
        {
            if(name.equals(modifier.getName())) {
                attribute.removeModifier(modifier);
                success = true;
            }
        }
        return success;
    }

    /**
     * Remove an {@link AttributeModifier} from an {@link ItemMeta} the modifier's instance
     *
     * @param attribModifier The {@link AttributeModifier} reference
     * @param itemMeta       The <code>ItemMeta</code> to reference
     * @return Whether the {@link AttributeModifier} was removed
     */
    public static boolean removeAttributeModifier(@NotNull final AttributeModifier attribModifier, ItemMeta itemMeta)
    {
        Preconditions.checkNotNull(attribModifier, "Modifier cannot be null");
        Preconditions.checkNotNull(itemMeta, "ItemMeta cannot be null");
        if(!itemMeta.hasAttributeModifiers()) return false;
        boolean success = false;
        for(Map.Entry<Attribute, AttributeModifier> entry : itemMeta.getAttributeModifiers().entries())
        {
            AttributeModifier modifier = entry.getValue();
            Attribute attribute = entry.getKey();
            if(attribModifier.equals(modifier)) {
                itemMeta.removeAttributeModifier(attribute, attribModifier);
                success =  true;
            }
        }
        return success;
    }

    /**
     * Remove an {@link AttributeModifier} from an {@link ItemMeta} the modifier's instance
     *
     * @param attribModifier The {@link AttributeModifier} reference
     * @param itemMeta       The <code>ItemMeta</code> to reference
     * @param slot           The {@link EquipmentSlot} of the modifier
     * @return Whether the {@link AttributeModifier} was removed
     */
    public static boolean removeAttributeModifier(@NotNull final AttributeModifier attribModifier, ItemMeta itemMeta, @NotNull final EquipmentSlot slot)
    {
        Preconditions.checkNotNull(attribModifier, "Modifier cannot be null");
        Preconditions.checkNotNull(slot, "EquipmentSlot cannot be null");
        Preconditions.checkNotNull(itemMeta, "ItemMeta cannot be null");
        if(!itemMeta.hasAttributeModifiers()) return false;
        boolean success = false;
        for(Map.Entry<Attribute, AttributeModifier> entry : itemMeta.getAttributeModifiers(slot).entries())
        {
            AttributeModifier modifier = entry.getValue();
            Attribute attribute = entry.getKey();
            if(attribModifier.equals(modifier)) {
                itemMeta.removeAttributeModifier(attribute, attribModifier);
                success =  true;
            }
        }
        return success;
    }

    /**
     * Remove an {@link AttributeModifier} from an {@link ItemMeta} the modifier's instance
     *
     * @param attribModifier The {@link AttributeModifier} reference
     * @param itemMeta       The <code>ItemMeta</code> to reference
     * @param attribute      The {@link AttributeInstance} of the modifier
     * @return Whether the {@link AttributeModifier} was removed
     */
    public static boolean removeAttributeModifier(@NotNull final AttributeModifier attribModifier, ItemMeta itemMeta, @NotNull final Attribute attribute)
    {
        Preconditions.checkNotNull(attribModifier, "Modifier cannot be null");
        Preconditions.checkNotNull(attribute, "Attribute cannot be null");
        Preconditions.checkNotNull(itemMeta, "ItemMeta cannot be null");
        if(!itemMeta.hasAttributeModifiers()) return false;
        boolean success = false;
        for(AttributeModifier modifier : itemMeta.getAttributeModifiers(attribute))
        {
            if(attribModifier.equals(modifier)) {
                itemMeta.removeAttributeModifier(attribute, attribModifier);
                success =  true;
            }
        }
        return success;
    }

    /**
     * Remove an {@link AttributeModifier} from an {@link AttributeInstance} the modifier's instance
     *
     * @param attribModifier The {@link AttributeModifier} reference
     * @param attribute      The {@link AttributeInstance} of the modifier
     */
    public static void removeAttributeModifier(@NotNull final AttributeModifier attribModifier, @NotNull final AttributeInstance attribute)
    {
        Preconditions.checkNotNull(attribModifier, "Modifier cannot be null");
        Preconditions.checkNotNull(attribute, "Attribute cannot be null");
        attribute.removeModifier(attribModifier);
    }
}
