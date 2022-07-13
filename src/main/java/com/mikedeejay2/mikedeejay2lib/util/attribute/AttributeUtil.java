package com.mikedeejay2.mikedeejay2lib.util.attribute;

import com.google.common.base.Preconditions;
import org.bukkit.Bukkit;
import org.bukkit.attribute.Attributable;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.*;

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
     * @param itemStack The <code>ItemStack</code> to reference
     * @return Whether the {@link AttributeModifier} was found or not
     */
    public static boolean hasAttributeModifier(@NotNull final UUID uuid, ItemStack itemStack)
    {
        Preconditions.checkNotNull(uuid, "UUID can not be null");
        Preconditions.checkNotNull(itemStack, "ItemStack can not be null");
        ItemMeta itemMeta = itemStack.getItemMeta();
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
     * @param itemStack The <code>ItemStack</code> to reference
     * @param slot     The {@link EquipmentSlot} of the modifier
     * @return Whether the {@link AttributeModifier} was found or not
     */
    public static boolean hasAttributeModifier(@NotNull final UUID uuid, ItemStack itemStack, @NotNull final EquipmentSlot slot)
    {
        Preconditions.checkNotNull(uuid, "UUID can not be null");
        Preconditions.checkNotNull(slot, "EquipmentSlot can not be null");
        Preconditions.checkNotNull(itemStack, "ItemStack can not be null");
        ItemMeta itemMeta = itemStack.getItemMeta();
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
     * @param itemStack The <code>ItemStack</code> to reference
     * @param attribute     The {@link AttributeInstance} of the modifier
     * @return Whether the {@link AttributeModifier} was found or not
     */
    public static boolean hasAttributeModifier(@NotNull final UUID uuid, ItemStack itemStack, @NotNull final Attribute attribute)
    {
        Preconditions.checkNotNull(uuid, "UUID can not be null");
        Preconditions.checkNotNull(attribute, "Attribute can not be null");
        Preconditions.checkNotNull(itemStack, "ItemStack can not be null");
        ItemMeta itemMeta = itemStack.getItemMeta();
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
        Preconditions.checkNotNull(uuid, "UUID can not be null");
        Preconditions.checkNotNull(attribute, "Attribute can not be null");
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
     * @param itemStack The <code>ItemStack</code> to reference
     * @return Whether the {@link AttributeModifier} was found or not
     */
    public static boolean hasAttributeModifier(@NotNull final String name, ItemStack itemStack)
    {
        Preconditions.checkNotNull(name, "Modifier name can not be null");
        Preconditions.checkNotNull(itemStack, "ItemStack can not be null");
        ItemMeta itemMeta = itemStack.getItemMeta();
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
     * @param itemStack The <code>ItemStack</code> to reference
     * @param slot     The {@link EquipmentSlot} of the modifier
     * @return Whether the {@link AttributeModifier} was found or not
     */
    public static boolean hasAttributeModifier(@NotNull final String name, ItemStack itemStack, @NotNull final EquipmentSlot slot)
    {
        Preconditions.checkNotNull(name, "Modifier name can not be null");
        Preconditions.checkNotNull(slot, "EquipmentSlot can not be null");
        Preconditions.checkNotNull(itemStack, "ItemStack can not be null");
        ItemMeta itemMeta = itemStack.getItemMeta();
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
     * @param itemStack The <code>ItemStack</code> to reference
     * @param attribute     The {@link AttributeInstance} of the modifier
     * @return Whether the {@link AttributeModifier} was found or not
     */
    public static boolean hasAttributeModifier(@NotNull final String name, ItemStack itemStack, @NotNull final Attribute attribute)
    {
        Preconditions.checkNotNull(name, "Modifier name can not be null");
        Preconditions.checkNotNull(attribute, "Attribute can not be null");
        Preconditions.checkNotNull(itemStack, "ItemStack can not be null");
        ItemMeta itemMeta = itemStack.getItemMeta();
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
        Preconditions.checkNotNull(name, "Modifier name can not be null");
        Preconditions.checkNotNull(attribute, "Attribute can not be null");
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
     * @param itemStack       The <code>ItemStack</code> to reference
     * @return Whether the {@link AttributeModifier} was found or not
     */
    public static boolean hasAttributeModifier(@NotNull final AttributeModifier attribModifier, ItemStack itemStack)
    {
        Preconditions.checkNotNull(attribModifier, "Modifier can not be null");
        Preconditions.checkNotNull(itemStack, "ItemStack can not be null");
        ItemMeta itemMeta = itemStack.getItemMeta();
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
     * @param itemStack       The <code>ItemStack</code> to reference
     * @param slot           The {@link EquipmentSlot} of the modifier
     * @return Whether the {@link AttributeModifier} was found or not
     */
    public static boolean hasAttributeModifier(@NotNull final AttributeModifier attribModifier, ItemStack itemStack, @NotNull final EquipmentSlot slot)
    {
        Preconditions.checkNotNull(attribModifier, "Modifier can not be null");
        Preconditions.checkNotNull(slot, "EquipmentSlot can not be null");
        Preconditions.checkNotNull(itemStack, "ItemStack can not be null");
        ItemMeta itemMeta = itemStack.getItemMeta();
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
     * @param itemStack       The <code>ItemStack</code> to reference
     * @param attribute      The {@link AttributeInstance} of the modifier
     * @return Whether the {@link AttributeModifier} was found or not
     */
    public static boolean hasAttributeModifier(@NotNull final AttributeModifier attribModifier, ItemStack itemStack, @NotNull final Attribute attribute)
    {
        Preconditions.checkNotNull(attribModifier, "Modifier can not be null");
        Preconditions.checkNotNull(attribute, "Attribute can not be null");
        Preconditions.checkNotNull(itemStack, "ItemStack can not be null");
        ItemMeta itemMeta = itemStack.getItemMeta();
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
        Preconditions.checkNotNull(attribModifier, "Modifier can not be null");
        Preconditions.checkNotNull(attribute, "Attribute can not be null");
        for(AttributeModifier modifier : attribute.getModifiers())
        {
            if(attribModifier.equals(modifier)) return true;
        }
        return false;
    }

    /**
     * Get an {@link AttributeModifier} based off of UUID
     *
     * @param uuid     The UUID of the {@link AttributeModifier}
     * @param itemStack The <code>ItemStack</code> to reference
     * @return The found {@link AttributeModifier}, null if it was not found
     */
    public static AttributeModifier getAttributeModifier(@NotNull final UUID uuid, ItemStack itemStack)
    {
        if(!hasAttributeModifier(uuid, itemStack)) return null;
        ItemMeta itemMeta = itemStack.getItemMeta();
        for(AttributeModifier modifier : itemMeta.getAttributeModifiers().values())
        {
            if(uuid.equals(modifier.getUniqueId())) return modifier;
        }
        return null;
    }

    /**
     * Get an {@link AttributeModifier} based off of UUID
     *
     * @param uuid     The UUID of the {@link AttributeModifier}
     * @param itemStack The <code>ItemStack</code> to reference
     * @param slot     The {@link EquipmentSlot} of the modifier
     * @return The found {@link AttributeModifier}, null if it was not found
     */
    public static AttributeModifier getAttributeModifier(@NotNull final UUID uuid, ItemStack itemStack, @NotNull final EquipmentSlot slot)
    {
        if(!hasAttributeModifier(uuid, itemStack, slot)) return null;
        ItemMeta itemMeta = itemStack.getItemMeta();
        for(AttributeModifier modifier : itemMeta.getAttributeModifiers(slot).values())
        {
            if(uuid.equals(modifier.getUniqueId())) return modifier;
        }
        return null;
    }

    /**
     * Get an {@link AttributeModifier} based off of UUID
     *
     * @param uuid     The UUID of the {@link AttributeModifier}
     * @param itemStack The <code>ItemStack</code> to reference
     * @param attribute     The {@link AttributeInstance} of the modifier
     * @return The found {@link AttributeModifier}, null if it was not found
     */
    public static AttributeModifier getAttributeModifier(@NotNull final UUID uuid, ItemStack itemStack, @NotNull final Attribute attribute)
    {
        if(!hasAttributeModifier(uuid, itemStack, attribute)) return null;
        ItemMeta itemMeta = itemStack.getItemMeta();
        for(AttributeModifier modifier : itemMeta.getAttributeModifiers(attribute))
        {
            if(uuid.equals(modifier.getUniqueId())) return modifier;
        }
        return null;
    }

    /**
     * Get an {@link AttributeModifier} based off of UUID
     *
     * @param uuid     The UUID of the {@link AttributeModifier}
     * @param attribute     The {@link AttributeInstance} of the modifier
     * @return The found {@link AttributeModifier}, null if it was not found
     */
    public static AttributeModifier getAttributeModifier(@NotNull final UUID uuid, @NotNull final AttributeInstance attribute)
    {
        if(!hasAttributeModifier(uuid, attribute)) return null;
        for(AttributeModifier modifier : attribute.getModifiers())
        {
            if(uuid.equals(modifier.getUniqueId())) return modifier;
        }
        return null;
    }

    /**
     * Get an {@link AttributeModifier} based off of name
     *
     * @param name     The name of the {@link AttributeModifier}
     * @param itemStack The <code>ItemStack</code> to reference
     * @return The found {@link AttributeModifier}, null if it was not found
     */
    public static AttributeModifier getAttributeModifier(@NotNull final String name, ItemStack itemStack)
    {
        if(!hasAttributeModifier(name, itemStack)) return null;
        ItemMeta itemMeta = itemStack.getItemMeta();
        for(AttributeModifier modifier : itemMeta.getAttributeModifiers().values())
        {
            if(name.equals(modifier.getName())) return modifier;
        }
        return null;
    }

    /**
     * Get an {@link AttributeModifier} based off of name
     *
     * @param name     The name of the {@link AttributeModifier}
     * @param itemStack The <code>ItemStack</code> to reference
     * @param slot     The {@link EquipmentSlot} of the modifier
     * @return The found {@link AttributeModifier}, null if it was not found
     */
    public static AttributeModifier getAttributeModifier(@NotNull final String name, ItemStack itemStack, @NotNull final EquipmentSlot slot)
    {
        if(!hasAttributeModifier(name, itemStack, slot)) return null;
        ItemMeta itemMeta = itemStack.getItemMeta();
        for(AttributeModifier modifier : itemMeta.getAttributeModifiers(slot).values())
        {
            if(name.equals(modifier.getName())) return modifier;
        }
        return null;
    }

    /**
     * Get an {@link AttributeModifier} based off of name
     *
     * @param name     The name of the {@link AttributeModifier}
     * @param itemStack The <code>ItemStack</code> to reference
     * @param attribute     The {@link AttributeInstance} of the modifier
     * @return The found {@link AttributeModifier}, null if it was not found
     */
    public static AttributeModifier getAttributeModifier(@NotNull final String name, ItemStack itemStack, @NotNull final Attribute attribute)
    {
        if(!hasAttributeModifier(name, itemStack, attribute)) return null;
        ItemMeta itemMeta = itemStack.getItemMeta();
        for(AttributeModifier modifier : itemMeta.getAttributeModifiers(attribute))
        {
            if(name.equals(modifier.getName())) return modifier;
        }
        return null;
    }

    /**
     * Get an {@link AttributeModifier} based off of name
     *
     * @param name     The name of the {@link AttributeModifier}
     * @param attribute     The {@link AttributeInstance} of the modifier
     * @return The found {@link AttributeModifier}, null if it was not found
     */
    public static AttributeModifier getAttributeModifier(@NotNull final String name, @NotNull final AttributeInstance attribute)
    {
        if(!hasAttributeModifier(name, attribute)) return null;
        for(AttributeModifier modifier : attribute.getModifiers())
        {
            if(name.equals(modifier.getName())) return modifier;
        }
        return null;
    }

    /**
     * Get an {@link AttributeModifier} based off of the modifier's instance
     *
     * @param attribModifier The {@link AttributeModifier} reference
     * @param itemStack       The <code>ItemStack</code> to reference
     * @return The found {@link AttributeModifier}, null if it was not found
     */
    public static AttributeModifier getAttributeModifier(@NotNull final AttributeModifier attribModifier, ItemStack itemStack)
    {
        if(!hasAttributeModifier(attribModifier, itemStack)) return null;
        ItemMeta itemMeta = itemStack.getItemMeta();
        for(AttributeModifier modifier : itemMeta.getAttributeModifiers().values())
        {
            if(attribModifier.equals(modifier)) return modifier;
        }
        return null;
    }

    /**
     * Get an {@link AttributeModifier} based off of the modifier's instance
     *
     * @param attribModifier The {@link AttributeModifier} reference
     * @param itemStack       The <code>ItemStack</code> to reference
     * @param slot           The {@link EquipmentSlot} of the modifier
     * @return The found {@link AttributeModifier}, null if it was not found
     */
    public static AttributeModifier getAttributeModifier(@NotNull final AttributeModifier attribModifier, ItemStack itemStack, @NotNull final EquipmentSlot slot)
    {
        if(!hasAttributeModifier(attribModifier, itemStack, slot)) return null;
        ItemMeta itemMeta = itemStack.getItemMeta();
        for(AttributeModifier modifier : itemMeta.getAttributeModifiers(slot).values())
        {
            if(attribModifier.equals(modifier)) return modifier;
        }
        return null;
    }

    /**
     * Get an {@link AttributeModifier} based off of the modifier's instance
     *
     * @param attribModifier The {@link AttributeModifier} reference
     * @param itemStack       The <code>ItemStack</code> to reference
     * @param attribute      The {@link AttributeInstance} of the modifier
     * @return The found {@link AttributeModifier}, null if it was not found
     */
    public static AttributeModifier getAttributeModifier(@NotNull final AttributeModifier attribModifier, ItemStack itemStack, @NotNull final Attribute attribute)
    {
        if(!hasAttributeModifier(attribModifier, itemStack, attribute)) return null;
        ItemMeta itemMeta = itemStack.getItemMeta();
        for(AttributeModifier modifier : itemMeta.getAttributeModifiers(attribute))
        {
            if(attribModifier.equals(modifier)) return modifier;
        }
        return null;
    }

    /**
     * Get an {@link AttributeModifier} based off of the modifier's instance
     *
     * @param attribModifier The {@link AttributeModifier} reference
     * @param attribute      The {@link AttributeInstance} of the modifier
     * @return The found {@link AttributeModifier}, null if it was not found
     */
    public static AttributeModifier getAttributeModifier(@NotNull final AttributeModifier attribModifier, @NotNull final AttributeInstance attribute)
    {
        if(!hasAttributeModifier(attribModifier, attribute)) return null;
        for(AttributeModifier modifier : attribute.getModifiers())
        {
            if(attribModifier.equals(modifier)) return modifier;
        }
        return null;
    }

    /**
     * Add an {@link AttributeModifier} to an {@link ItemMeta}.
     * <p>
     * Includes safety checking: If modifier already exists, replace it.
     *
     * @param attribModifier The {@link AttributeModifier} reference
     * @param itemStack       The <code>ItemStack</code> to reference
     * @param attribute      The {@link AttributeInstance} of the modifier
     */
    public static void addAttributeModifier(@NotNull final AttributeModifier attribModifier, @NotNull ItemStack itemStack, @NotNull final Attribute attribute)
    {
        Preconditions.checkNotNull(attribModifier, "Modifier can not be null");
        Preconditions.checkNotNull(itemStack, "ItemStack can not be null");
        ItemMeta itemMeta = itemStack.getItemMeta();
        if(itemMeta == null) itemMeta = Bukkit.getItemFactory().getItemMeta(itemStack.getType());
        if(hasAttributeModifier(attribModifier.getUniqueId(), itemStack))
        {
            removeAttributeModifier(attribModifier.getUniqueId(), itemStack);
            itemMeta = itemStack.getItemMeta();
        }
        itemMeta.addAttributeModifier(attribute, attribModifier);
        itemStack.setItemMeta(itemMeta);
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
        Preconditions.checkNotNull(attribModifier, "Modifier can not be null");
        Preconditions.checkNotNull(attribute, "Attribute can not be null");
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
     * @param itemStack The <code>ItemStack</code> to reference
     * @return Whether the {@link AttributeModifier} was removed
     */
    public static boolean removeAttributeModifier(@NotNull final UUID uuid, ItemStack itemStack)
    {
        Preconditions.checkNotNull(uuid, "UUID can not be null");
        Preconditions.checkNotNull(itemStack, "ItemStack can not be null");
        ItemMeta itemMeta = itemStack.getItemMeta();
        if(itemMeta == null) return false;
        if(!itemMeta.hasAttributeModifiers()) return false;
        for(Map.Entry<Attribute, AttributeModifier> entry : itemMeta.getAttributeModifiers().entries())
        {
            AttributeModifier modifier = entry.getValue();
            Attribute attribute = entry.getKey();
            if(uuid.equals(modifier.getUniqueId())) {
                itemMeta.removeAttributeModifier(attribute, modifier);
                itemStack.setItemMeta(itemMeta);
                return true;
            }
        }
        return false;
    }

    /**
     * Remove an {@link AttributeModifier} from an {@link ItemMeta} UUID
     *
     * @param uuid     The UUID of the {@link AttributeModifier}
     * @param itemStack The <code>ItemStack</code> to reference
     * @param slot     The {@link EquipmentSlot} of the modifier
     * @return Whether the {@link AttributeModifier} was removed
     */
    public static boolean removeAttributeModifier(@NotNull final UUID uuid, ItemStack itemStack, @NotNull final EquipmentSlot slot)
    {
        Preconditions.checkNotNull(uuid, "UUID can not be null");
        Preconditions.checkNotNull(slot, "EquipmentSlot can not be null");
        Preconditions.checkNotNull(itemStack, "ItemStack can not be null");
        ItemMeta itemMeta = itemStack.getItemMeta();
        if(itemMeta == null) return false;
        if(!itemMeta.hasAttributeModifiers()) return false;
        for(Map.Entry<Attribute, AttributeModifier> entry : itemMeta.getAttributeModifiers(slot).entries())
        {
            AttributeModifier modifier = entry.getValue();
            Attribute attribute = entry.getKey();
            if(uuid.equals(modifier.getUniqueId())) {
                itemMeta.removeAttributeModifier(attribute, modifier);
                itemStack.setItemMeta(itemMeta);
                return true;
            }
        }
        return false;
    }

    /**
     * Remove an {@link AttributeModifier} from an {@link ItemMeta} UUID
     *
     * @param uuid      The UUID of the {@link AttributeModifier}
     * @param itemStack  The <code>ItemStack</code> to reference
     * @param attribute The {@link AttributeInstance} of the modifier
     * @return Whether the {@link AttributeModifier} was removed
     */
    public static boolean removeAttributeModifier(@NotNull final UUID uuid, ItemStack itemStack, @NotNull final Attribute attribute)
    {
        Preconditions.checkNotNull(uuid, "UUID can not be null");
        Preconditions.checkNotNull(attribute, "Attribute can not be null");
        Preconditions.checkNotNull(itemStack, "ItemStack can not be null");
        ItemMeta itemMeta = itemStack.getItemMeta();
        if(itemMeta == null) return false;
        if(!itemMeta.hasAttributeModifiers()) return false;
        for(AttributeModifier modifier : itemMeta.getAttributeModifiers(attribute))
        {
            if(uuid.equals(modifier.getUniqueId())) {
                itemMeta.removeAttributeModifier(attribute, modifier);
                itemStack.setItemMeta(itemMeta);
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
        Preconditions.checkNotNull(uuid, "UUID can not be null");
        Preconditions.checkNotNull(attribute, "Attribute can not be null");
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
     * @param itemStack The <code>ItemStack</code> to reference
     * @return Whether the {@link AttributeModifier} was removed
     */
    public static boolean removeAttributeModifier(@NotNull final String name, ItemStack itemStack)
    {
        Preconditions.checkNotNull(name, "Modifier name can not be null");
        Preconditions.checkNotNull(itemStack, "ItemStack can not be null");
        ItemMeta itemMeta = itemStack.getItemMeta();
        if(itemMeta == null) return false;
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
        itemStack.setItemMeta(itemMeta);
        return success;
    }

    /**
     * Remove an {@link AttributeModifier} from an {@link ItemMeta} name
     *
     * @param name     The name of the {@link AttributeModifier}
     * @param itemStack The <code>ItemStack</code> to reference
     * @param slot     The {@link EquipmentSlot} of the modifier
     * @return Whether the {@link AttributeModifier} was removed
     */
    public static boolean removeAttributeModifier(@NotNull final String name, ItemStack itemStack, @NotNull final EquipmentSlot slot)
    {
        Preconditions.checkNotNull(name, "Modifier name can not be null");
        Preconditions.checkNotNull(slot, "EquipmentSlot can not be null");
        Preconditions.checkNotNull(itemStack, "ItemStack can not be null");
        ItemMeta itemMeta = itemStack.getItemMeta();
        if(itemMeta == null) return false;
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
        itemStack.setItemMeta(itemMeta);
        return success;
    }

    /**
     * Remove an {@link AttributeModifier} from an {@link ItemMeta} name
     *
     * @param name      The name of the {@link AttributeModifier}
     * @param itemStack  The <code>ItemStack</code> to reference
     * @param attribute The {@link AttributeInstance} of the modifier
     * @return Whether the {@link AttributeModifier} was removed
     */
    public static boolean removeAttributeModifier(@NotNull final String name, ItemStack itemStack, @NotNull final Attribute attribute)
    {
        Preconditions.checkNotNull(name, "Modifier name can not be null");
        Preconditions.checkNotNull(attribute, "Attribute can not be null");
        Preconditions.checkNotNull(itemStack, "ItemStack can not be null");
        ItemMeta itemMeta = itemStack.getItemMeta();
        if(itemMeta == null) return false;
        if(!itemMeta.hasAttributeModifiers()) return false;
        boolean success = false;
        for(AttributeModifier modifier : itemMeta.getAttributeModifiers(attribute))
        {
            if(name.equals(modifier.getName())) {
                itemMeta.removeAttributeModifier(attribute, modifier);
                success = true;
            }
        }
        itemStack.setItemMeta(itemMeta);
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
        Preconditions.checkNotNull(name, "Modifier name can not be null");
        Preconditions.checkNotNull(attribute, "Attribute can not be null");
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
     * @param itemStack       The <code>ItemStack</code> to reference
     * @return Whether the {@link AttributeModifier} was removed
     */
    public static boolean removeAttributeModifier(@NotNull final AttributeModifier attribModifier, ItemStack itemStack)
    {
        Preconditions.checkNotNull(attribModifier, "Modifier can not be null");
        Preconditions.checkNotNull(itemStack, "ItemStack can not be null");
        ItemMeta itemMeta = itemStack.getItemMeta();
        if(itemMeta == null) return false;
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
        itemStack.setItemMeta(itemMeta);
        return success;
    }

    /**
     * Remove an {@link AttributeModifier} from an {@link ItemMeta} the modifier's instance
     *
     * @param attribModifier The {@link AttributeModifier} reference
     * @param itemStack       The <code>ItemStack</code> to reference
     * @param slot           The {@link EquipmentSlot} of the modifier
     * @return Whether the {@link AttributeModifier} was removed
     */
    public static boolean removeAttributeModifier(@NotNull final AttributeModifier attribModifier, ItemStack itemStack, @NotNull final EquipmentSlot slot)
    {
        Preconditions.checkNotNull(attribModifier, "Modifier can not be null");
        Preconditions.checkNotNull(slot, "EquipmentSlot can not be null");
        Preconditions.checkNotNull(itemStack, "ItemStack can not be null");
        ItemMeta itemMeta = itemStack.getItemMeta();
        if(itemMeta == null) return false;
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
        itemStack.setItemMeta(itemMeta);
        return success;
    }

    /**
     * Remove an {@link AttributeModifier} from an {@link ItemMeta} the modifier's instance
     *
     * @param attribModifier The {@link AttributeModifier} reference
     * @param itemStack       The <code>ItemStack</code> to reference
     * @param attribute      The {@link AttributeInstance} of the modifier
     * @return Whether the {@link AttributeModifier} was removed
     */
    public static boolean removeAttributeModifier(@NotNull final AttributeModifier attribModifier, ItemStack itemStack, @NotNull final Attribute attribute)
    {
        Preconditions.checkNotNull(attribModifier, "Modifier can not be null");
        Preconditions.checkNotNull(attribute, "Attribute can not be null");
        Preconditions.checkNotNull(itemStack, "ItemStack can not be null");
        ItemMeta itemMeta = itemStack.getItemMeta();
        if(itemMeta == null) return false;
        if(!itemMeta.hasAttributeModifiers()) return false;
        boolean success = false;
        for(AttributeModifier modifier : itemMeta.getAttributeModifiers(attribute))
        {
            if(attribModifier.equals(modifier)) {
                itemMeta.removeAttributeModifier(attribute, attribModifier);
                success =  true;
            }
        }
        itemStack.setItemMeta(itemMeta);
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
        Preconditions.checkNotNull(attribModifier, "Modifier can not be null");
        Preconditions.checkNotNull(attribute, "Attribute can not be null");
        attribute.removeModifier(attribModifier);
    }

    /**
     * Remove all {@link AttributeModifier}s off of an {@link ItemStack}
     *
     * @param itemStack The <code>ItemStack</code> to reference
     */
    public static void resetAttributeModifiers(ItemStack itemStack)
    {
        Preconditions.checkNotNull(itemStack, "ItemStack can not be null");
        ItemMeta itemMeta = itemStack.getItemMeta();
        if(itemMeta == null) return;
        if(!itemMeta.hasAttributeModifiers()) return;
        for(Attribute attribute : itemMeta.getAttributeModifiers().keySet())
        {
            itemMeta.removeAttributeModifier(attribute);
        }
        itemStack.setItemMeta(itemMeta);
    }

    /**
     * Remove all {@link AttributeModifier}s off of an {@link AttributeInstance}
     *
     * @param attribute The {@link AttributeInstance} to reference
     */
    public static void resetAttributeModifiers(AttributeInstance attribute)
    {
        Preconditions.checkNotNull(attribute, "Attribute can not be null");
        for(AttributeModifier modifier : attribute.getModifiers())
        {
            attribute.removeModifier(modifier);
        }
    }

    /**
     * Remove all {@link AttributeModifier}s off of an {@link Attributable}
     *
     * @param attributable The {@link Attributable} to reference
     */
    public static void resetAttributeModifiers(Attributable attributable)
    {
        Preconditions.checkNotNull(attributable, "Attributable can not be null");
        for(AttributeInstance attribute : getAllAttributes(attributable))
        {
            if(attribute == null) continue;
            Collection<AttributeModifier> modifiers = attribute.getModifiers();
            modifiers.forEach(attribute::removeModifier);
        }
    }

    /**
     * Get all {@link AttributeInstance}s from an {@link Attributable}
     *
     * @param attributable The {@link Attributable} to reference
     * @return The list of all of the {@link Attributable}'s {@link AttributeInstance}s
     */
    public static List<AttributeInstance> getAllAttributes(Attributable attributable)
    {
        Preconditions.checkNotNull(attributable, "Attributable can not be null");
        List<AttributeInstance> attributes = new ArrayList<>();
        for(Attribute attribute : Attribute.values())
        {
            AttributeInstance cur = attributable.getAttribute(attribute);
            attributes.add(cur);
        }
        return attributes;
    }
}
