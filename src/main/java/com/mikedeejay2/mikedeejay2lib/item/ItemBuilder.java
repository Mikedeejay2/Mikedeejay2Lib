package com.mikedeejay2.mikedeejay2lib.item;

import com.google.common.collect.Multimap;
import com.mikedeejay2.mikedeejay2lib.BukkitPlugin;
import com.mikedeejay2.mikedeejay2lib.gui.GUIContainer;
import com.mikedeejay2.mikedeejay2lib.util.chat.Colors;
import com.mikedeejay2.mikedeejay2lib.util.enchant.GlowEnchantment;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.OfflinePlayer;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.lang.reflect.Field;
import java.util.*;

/**
 * A class for building items dynamically. See {@link ItemBuilder#of()} to get started.
 *
 * @author Mikedeejay2
 */
public final class ItemBuilder implements IItemBuilder<ItemStack, ItemBuilder>, Cloneable
{
    /**
     * The {@link Material} of the built item
     */
    private Material type;

    /**
     * The stack amount of the built item
     */
    private int amount;

    /**
     * The {@link ItemMeta} of the built item
     */
    private ItemMeta meta;

    /**
     * The built item, null if not yet built
     */
    private ItemStack item;

    /**
     * Whether the item has been changed since last build
     */
    private boolean changed;

    /**
     * Construct a new <code>ItemBuilder</code>
     *
     * @param item The reference item
     */
    private ItemBuilder(ItemStack item)
    {
        set(item);
        this.item = null;
        this.changed = true;
    }

    /**
     * Construct a new <code>ItemBuilder</code>
     *
     * @param material The starting material
     */
    private ItemBuilder(Material material)
    {
        this.type = material;
        this.amount = 1;
        this.meta = Bukkit.getItemFactory().getItemMeta(type);
        this.item = null;
        this.changed = true;
    }

    /**
     * Construct a new <code>ItemBuilder</code> with the default material of <code>STONE</code>
     */
    private ItemBuilder()
    {
        this.type = Material.STONE;
        this.amount = 1;
        this.meta = Bukkit.getItemFactory().getItemMeta(type);
        this.item = null;
        this.changed = true;
    }

    /**
     * Construct a new <code>ItemBuilder</code> for a player head
     *
     * @param base64 The base64 of the head
     */
    private ItemBuilder(String base64)
    {
        this.type = Material.PLAYER_HEAD;
        this.amount = 1;
        this.meta = Bukkit.getItemFactory().getItemMeta(type);
        this.item = null;
        this.setHeadBase64(base64);
        this.changed = true;
    }

    /**
     * Construct a new <code>ItemBuilder</code> for a player head
     *
     * @param player The player reference
     */
    private ItemBuilder(OfflinePlayer player)
    {
        this.type = Material.PLAYER_HEAD;
        this.amount = 1;
        this.meta = Bukkit.getItemFactory().getItemMeta(type);
        this.item = null;
        this.setHeadOwner(player);
        this.changed = true;
    }

    /**
     * {@inheritDoc}
     *
     * @return The <code>ItemStack</code>
     */
    @Override
    public ItemStack get()
    {
        if(type == null) return null;
        if(changed || item == null)
        {
            this.item = new ItemStack(type, amount);
            this.item.setItemMeta(meta);
            this.changed = false;
        }
        return item;
    }

    /**
     * {@inheritDoc}
     *
     * @param item The new item
     * @return A reference to this object
     */
    @Override
    public ItemBuilder set(ItemStack item)
    {
        this.type = item != null ? item.getType() : null;
        this.amount = item != null ? item.getAmount() : 0;
        this.meta = null;
        if(item != null)
        {
            if(item.hasItemMeta()) internalSetMeta(item.getItemMeta());
            else internalSetMeta(Bukkit.getItemFactory().getItemMeta(type));
        }
        this.changed = true;
        return this;
    }

    /**
     * {@inheritDoc}
     *
     * @return The <code>ItemMeta</code> of the item
     */
    @Override
    public ItemMeta getMeta()
    {
        return meta == null ? null : meta.clone();
    }

    /**
     * {@inheritDoc}
     *
     * @param meta The new <code>ItemMeta</code>
     * @return A reference to this object
     */
    @Override
    public ItemBuilder setMeta(ItemMeta meta)
    {
        internalSetMeta(meta);
        this.changed = true;
        return this;
    }

    /**
     * {@inheritDoc}
     *
     * @return The name of the item
     */
    @Override
    public String getName()
    {
        return meta.getDisplayName();
    }

    /**
     * {@inheritDoc}
     *
     * @param name The new name of the item
     * @return A reference to this object
     */
    @Override
    public ItemBuilder setName(String name)
    {
        this.meta.setDisplayName(Colors.formatR(name));
        this.changed = true;
        return this;
    }

    /**
     * {@inheritDoc}
     *
     * @return The stack amount of the item
     */
    @Override
    public int getAmount()
    {
        return amount;
    }

    /**
     * {@inheritDoc}
     *
     * @param amount The new stack amount of the item
     * @return A reference to this object
     */
    @Override
    public ItemBuilder setAmount(int amount)
    {
        this.amount = amount;
        this.changed = true;
        return this;
    }

    /**
     * {@inheritDoc}
     *
     * @return The material type of the item
     */
    @Override
    public Material getType()
    {
        return type;
    }

    /**
     * {@inheritDoc}
     *
     * @param material The new material type of the item
     * @return A reference to this object
     */
    @Override
    public ItemBuilder setType(Material material)
    {
        this.type = material;
        if (this.meta != null) {
            this.meta = Bukkit.getItemFactory().asMetaFor(meta, material);
        }
        this.changed = true;
        return this;
    }

    /**
     * {@inheritDoc}
     *
     * @return The String list of lore of the item
     */
    @Override
    public List<String> getLore()
    {
        return meta.getLore();
    }

    /**
     * {@inheritDoc}
     *
     * @param lore The new String list of lore of the item
     * @return A reference to this object
     */
    @Override
    public ItemBuilder setLore(List<String> lore)
    {
        this.meta.setLore(Colors.formatR(lore));
        this.changed = true;
        return this;
    }

    /**
     * {@inheritDoc}
     *
     * @param lore The new String array of lore of the item
     * @return A reference to this object
     */
    @Override
    public ItemBuilder setLore(String... lore)
    {
        this.meta.setLore(Colors.formatR(Arrays.asList(lore)));
        this.changed = true;
        return this;
    }

    /**
     * {@inheritDoc}
     *
     * @param lore The new String list to add to the item
     * @return A reference to this object
     */
    @Override
    public ItemBuilder addLore(List<String> lore)
    {
        List<String> curLore = this.meta.getLore();
        if(curLore == null) curLore = new ArrayList<>();
        curLore.addAll(Colors.formatR(lore));
        this.meta.setLore(curLore);
        return this;
    }

    /**
     * {@inheritDoc}
     *
     * @param lore The new String array to add to the item
     * @return A reference to this object
     */
    @Override
    public ItemBuilder addLore(String... lore)
    {
        List<String> curLore = this.meta.getLore();
        if(curLore == null) curLore = new ArrayList<>();
        curLore.addAll(Arrays.asList(Colors.formatR(lore)));
        this.meta.setLore(curLore);
        return this;
    }

    /**
     * {@inheritDoc}
     *
     * @param index The index to add the lore at
     * @param lore The new String list to add to the item
     * @return A reference to this object
     */
    @Override
    public ItemBuilder addLore(int index, List<String> lore)
    {
        List<String> curLore = this.meta.getLore();
        if(curLore == null) curLore = new ArrayList<>();
        curLore.addAll(index, Colors.formatR(lore));
        this.meta.setLore(curLore);
        return this;
    }

    /**
     * {@inheritDoc}
     *
     * @param index The index to add the lore at
     * @param lore The new String array to add to the item
     * @return A reference to this object
     */
    @Override
    public ItemBuilder addLore(int index, String... lore)
    {
        List<String> curLore = this.meta.getLore();
        if(curLore == null) curLore = new ArrayList<>();
        curLore.addAll(index, Arrays.asList(Colors.formatR(lore)));
        this.meta.setLore(curLore);
        return this;
    }

    /**
     * {@inheritDoc}
     *
     * @return The map of enchantments of the item
     */
    @Override
    public Map<Enchantment, Integer> getEnchants()
    {
        return meta.getEnchants();
    }

    /**
     * {@inheritDoc}
     *
     * @param enchantment The enchantment to check for
     * @return True if the item has the enchantment, false if it doesn't
     */
    @Override
    public boolean hasEnchant(Enchantment enchantment)
    {
        return meta.hasEnchant(enchantment);
    }

    /**
     * {@inheritDoc}
     *
     * @param enchantment The enchantment to get the level for
     * @return The level of the specified enchantment of the item
     */
    @Override
    public int getEnchant(Enchantment enchantment)
    {
        return meta.getEnchantLevel(enchantment);
    }

    /**
     * {@inheritDoc}
     *
     * @param enchantment The enchantment to remove from the item
     * @return A reference to this object
     */
    @Override
    public ItemBuilder removeEnchant(Enchantment enchantment)
    {
        this.meta.removeEnchant(enchantment);
        this.changed = true;
        return this;
    }

    /**
     * {@inheritDoc}
     *
     * @param enchantment The enchantment to add to the item
     * @param level The level of enchantment to add to the item
     * @return A reference to this object
     */
    @Override
    public ItemBuilder addEnchant(Enchantment enchantment, int level)
    {
        this.meta.addEnchant(enchantment, level, true);
        this.changed = true;
        return this;
    }

    /**
     * {@inheritDoc}
     *
     * @param enchantment The enchantment to check for
     * @return True if the item has a conflicting enchantment, false if it doesn't
     */
    @Override
    public boolean hasConflictingEnchant(Enchantment enchantment)
    {
        return this.meta.hasConflictingEnchant(enchantment);
    }

    /**
     * {@inheritDoc}
     *
     * @param flags The item flags to add to the item
     * @return A reference to this object
     */
    @Override
    public ItemBuilder addItemFlags(ItemFlag... flags)
    {
        this.meta.addItemFlags(flags);
        this.changed = true;
        return this;
    }

    /**
     * {@inheritDoc}
     *
     * @param flags The item flags to remove from the item
     * @return A reference to this object
     */
    @Override
    public ItemBuilder removeItemFlags(ItemFlag... flags)
    {
        this.meta.removeItemFlags(flags);
        this.changed = true;
        return this;
    }

    /**
     * {@inheritDoc}
     *
     * @param flag The item flag to test for
     * @return Whether the item has the item flag or not
     */
    @Override
    public boolean hasItemFlag(ItemFlag flag)
    {
        return this.meta.hasItemFlag(flag);
    }

    /**
     * {@inheritDoc}
     *
     * @return The {@link ItemFlag}s set
     */
    @Override
    public Set<ItemFlag> getItemFlags()
    {
        return this.meta.getItemFlags();
    }

    /**
     * {@inheritDoc}
     *
     * @param flag The <code>ItemFlag</code> to add to the item
     * @return A reference to this object
     */
    @Override
    public ItemBuilder addItemFlag(ItemFlag flag)
    {
        this.meta.addItemFlags(flag);
        this.changed = true;
        return this;
    }

    /**
     * {@inheritDoc}
     *
     * @return A reference to this object
     */
    @Override
    public ItemBuilder addGlow()
    {

        this.addEnchant(GlowEnchantment.get(), 1);
        this.changed = true;
        return this;
    }

    /**
     * {@inheritDoc}
     *
     * @return A reference to this object
     */
    @Override
    public ItemBuilder removeGlow()
    {
        this.removeEnchant(GlowEnchantment.get());
        this.changed = true;
        return this;
    }

    /**
     * {@inheritDoc}
     *
     * @return A reference to this object
     */
    @Override
    public ItemBuilder setEmptyName()
    {
        this.meta.setDisplayName(GUIContainer.EMPTY_NAME);
        this.changed = true;
        return this;
    }

    /**
     * {@inheritDoc}
     *
     * @param unbreakable The new unbreakable state of the item
     * @return A reference to this object
     */
    @Override
    public ItemBuilder setUnbreakable(boolean unbreakable)
    {
        this.meta.setUnbreakable(unbreakable);
        this.changed = true;
        return this;
    }

    /**
     * {@inheritDoc}
     *
     * @return Whether the item is unbreakable or not
     */
    @Override
    public boolean isUnbreakable()
    {
        return this.meta.isUnbreakable();
    }

    /**
     * {@inheritDoc}
     *
     * @return The current head owner of the head item
     */
    @Override
    public OfflinePlayer getHeadOwner()
    {
        SkullMeta skullMeta = (SkullMeta) meta;
        return skullMeta.getOwningPlayer();
    }

    /**
     * {@inheritDoc}
     *
     * @param player The new head owner of the head item
     * @return A reference to this object
     */
    @Override
    public ItemBuilder setHeadOwner(OfflinePlayer player)
    {
        SkullMeta skullMeta = (SkullMeta) meta;
        skullMeta.setOwningPlayer(player);
        this.changed = true;
        return this;
    }

    /**
     * {@inheritDoc}
     *
     * @return The current base 64 String of the head item
     */
    @Override
    public String getHeadBase64()
    {
        SkullMeta skullMeta = (SkullMeta) meta;
        GameProfile profile;
        try
        {
            Field profileField = skullMeta.getClass().getDeclaredField("profile");
            profileField.setAccessible(true);
            profile = (GameProfile) profileField.get(skullMeta);
        }
        catch(IllegalArgumentException | IllegalAccessException | NoSuchFieldException exception)
        {
            exception.printStackTrace();
            return null;
        }
        Collection<Property> properties = profile.getProperties().get("textures");
        for(Property property : properties)
        {
            String value = property.getValue();
            if(value.equals("textures"))
            {
                return property.getName();
            }
        }
        return null;
    }

    /**
     * {@inheritDoc}
     *
     * @param base64 The new base 64 String of the head item
     * @return A reference to this object
     */
    @Override
    public ItemBuilder setHeadBase64(String base64)
    {
        SkullMeta skullMeta = (SkullMeta) meta;
        GameProfile profile = new GameProfile(UUID.randomUUID(), "");
        profile.getProperties().put("textures", new Property("textures", base64));
        try
        {
            Field profileField = skullMeta.getClass().getDeclaredField("profile");
            profileField.setAccessible(true);
            profileField.set(skullMeta, profile);
        }
        catch(IllegalArgumentException | IllegalAccessException | NoSuchFieldException exception)
        {
            exception.printStackTrace();
            return this;
        }
        this.changed = true;
        return this;
    }

    /**
     * {@inheritDoc}
     *
     * @return Whether the item has attribute modifiers
     */
    @Override
    public boolean hasAttributeModifiers()
    {
        return this.meta.hasAttributeModifiers();
    }

    /**
     * {@inheritDoc}
     *
     * @return A map of attributes for the item
     */
    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers()
    {
        return this.meta.getAttributeModifiers();
    }

    /**
     * {@inheritDoc}
     *
     * @param slot The <code>EquipmentSlot</code> to get
     * @return A map of attributes for the item and equipment slot
     */
    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot)
    {
        return this.meta.getAttributeModifiers(slot);
    }

    /**
     * {@inheritDoc}
     *
     * @param attribute The <code>Attribute</code> to get
     * @return The collection of <code>AttributeModifiers</code>
     */
    @Override
    public Collection<AttributeModifier> getAttributeModifiers(Attribute attribute)
    {
        return this.meta.getAttributeModifiers(attribute);
    }

    /**
     * {@inheritDoc}
     *
     * @param attribute The <code>Attribute</code> to add to the item
     * @param modifier The <code>AttributeModifier</code> to add
     * @return A reference to this object
     */
    @Override
    public ItemBuilder addAttributeModifier(Attribute attribute, AttributeModifier modifier)
    {
        this.meta.addAttributeModifier(attribute, modifier);
        this.changed = true;
        return this;
    }

    /**
     * {@inheritDoc}
     *
     * @param attribute The <code>Attribute</code> to add to the item
     * @param modifiers The <code>AttributeModifiers</code> to add
     * @return A reference to this object
     */
    @Override
    public ItemBuilder addAttributeModifiers(Attribute attribute, AttributeModifier... modifiers)
    {
        for(AttributeModifier modifier : modifiers)
        {
            this.meta.addAttributeModifier(attribute, modifier);
        }
        this.changed = true;
        return this;
    }

    /**
     * {@inheritDoc}
     *
     * @param attributeModifiers The new map of attributes
     * @return A reference to this object
     */
    @Override
    public ItemBuilder setAttributeModifiers(Multimap<Attribute, AttributeModifier> attributeModifiers)
    {
        this.meta.setAttributeModifiers(attributeModifiers);
        this.changed = true;
        return this;
    }

    /**
     * {@inheritDoc}
     *
     * @param attribute The <code>Attribute</code> to remove
     * @return A reference to this object
     */
    @Override
    public ItemBuilder removeAttributeModifier(Attribute attribute)
    {
        this.meta.removeAttributeModifier(attribute);
        this.changed = true;
        return this;
    }

    /**
     * {@inheritDoc}
     *
     * @param attributes The {@link Attribute} to remove
     * @return A reference to this object
     */
    @Override
    public ItemBuilder removeAttributeModifiers(Attribute... attributes)
    {
        for(Attribute attribute : attributes)
        {
            this.meta.removeAttributeModifier(attribute);
        }
        this.changed = true;
        return this;
    }

    /**
     * {@inheritDoc}
     *
     * @param slot The <code>EquipmentSlot</code> to remove attributes from
     * @return A reference to this object
     */
    @Override
    public ItemBuilder removeAttributeModifier(EquipmentSlot slot)
    {
        this.meta.removeAttributeModifier(slot);
        this.changed = true;
        return this;
    }

    /**
     * {@inheritDoc}
     *
     * @param slots The <code>EquipmentSlots</code> to remove attributes from
     * @return A reference to this object
     */
    @Override
    public ItemBuilder removeAttributeModifiers(EquipmentSlot... slots)
    {
        for(EquipmentSlot slot : slots)
        {
            this.meta.removeAttributeModifier(slot);
        }
        this.changed = true;
        return this;
    }

    /**
     * {@inheritDoc}
     *
     * @param attribute The <code>Attribute</code> to remove from
     * @param modifier The <code>AttributeModifier</code> to remove
     * @return A reference to this object
     */
    @Override
    public ItemBuilder removeAttributeModifier(Attribute attribute, AttributeModifier modifier)
    {
        this.meta.removeAttributeModifier(attribute, modifier);
        this.changed = true;
        return this;
    }

    /**
     * {@inheritDoc}
     *
     * @param attribute The <code>Attribute</code> to remove from
     * @param modifiers The <code>AttributeModifiers</code> to remove
     * @return A reference to this object
     */
    @Override
    public ItemBuilder removeAttributeModifiers(Attribute attribute, AttributeModifier... modifiers)
    {
        for(AttributeModifier modifier : modifiers)
        {
            this.meta.removeAttributeModifier(attribute, modifier);
        }
        this.changed = true;
        return this;
    }

    /**
     * {@inheritDoc}
     *
     * @return The <code>PersistentDataContainer</code>
     */
    @Override
    public PersistentDataContainer getPersistentDataContainer()
    {
        return this.meta.getPersistentDataContainer();
    }

    /**
     * {@inheritDoc}
     *
     * @param data The new custom model data
     * @return A reference to this object
     */
    @Override
    public ItemBuilder setCustomModelData(Integer data)
    {
        this.meta.setCustomModelData(data);
        this.changed = true;
        return this;
    }

    /**
     * {@inheritDoc}
     *
     * @return The custom model data for the item
     */
    @Override
    public int getCustomModelData()
    {
        return this.meta.getCustomModelData();
    }

    /**
     * {@inheritDoc}
     *
     * @return Whether the item has custom model data
     */
    @Override
    public boolean hasCustomModelData()
    {
        return this.meta.hasCustomModelData();
    }

    /**
     * {@inheritDoc}
     *
     * @param name The new localized name for the item
     * @return A reference to this object
     */
    @Override
    public ItemBuilder setLocalizedName(String name)
    {
        this.meta.setLocalizedName(Colors.formatR(name));
        this.changed = true;
        return this;
    }

    /**
     * {@inheritDoc}
     *
     * @return The localized name of the item
     */
    @Override
    public String getLocalizedName()
    {
        return this.meta.getLocalizedName();
    }

    /**
     * {@inheritDoc}
     *
     * @return Whether the item has a localized name
     */
    @Override
    public boolean hasLocalizedName()
    {
        return this.meta.hasLocalizedName();
    }

    /**
     * {@inheritDoc}
     *
     * @return The display name of the item
     */
    @Override
    public String getDisplayName()
    {
        return this.meta.getDisplayName();
    }

    /**
     * {@inheritDoc}
     *
     * @param name The new display name of the item
     * @return A reference to this object
     */
    @Override
    public ItemBuilder setDisplayName(String name)
    {
        this.setName(name);
        return this;
    }

    /**
     * {@inheritDoc}
     *
     * @return Whether the item has a display name
     */
    @Override
    public boolean hasDisplayName()
    {
        return this.meta.hasDisplayName();
    }

    /**
     * {@inheritDoc}
     *
     * @return The durability of the item
     */
    @Override
    public int getDurability()
    {
        return ((Damageable) this.meta).getDamage();
    }

    /**
     * {@inheritDoc}
     *
     * @param durability The new durability of the item
     * @return A reference to this object
     */
    @Override
    public ItemBuilder setDurability(int durability)
    {
        ((Damageable) this.meta).setDamage(durability);
        this.changed = true;
        return this;
    }

    /**
     * {@inheritDoc}
     *
     * @return The maximum stack size
     */
    @Override
    public int getMaxStackSize()
    {
        Material material = this.getType();
        if (material != null)  return material.getMaxStackSize();
        return -1;
    }

    /**
     * {@inheritDoc}
     *
     * @return Whether the item has a durability
     */
    @Override
    public boolean hasDurability()
    {
        return this.meta instanceof Damageable;
    }

    /**
     * {@inheritDoc}
     *
     * @param key   The <code>NamespacedKey</code> to set the data in
     * @param type  The <code>PersistentDataContainer</code> to use
     * @param value The value to set
     * @param <Y>   The main object type stored by the tag
     * @param <Z>   The data type of the retrieved object
     * @return A reference to this object
     */
    @Override
    public <Y, Z> ItemBuilder setData(NamespacedKey key, PersistentDataType<Y, Z> type, Z value)
    {
        this.getPersistentDataContainer().set(key, type, value);
        this.changed = true;
        return this;
    }

    /**
     * {@inheritDoc}
     *
     * @param plugin The plugin's reference for namespace
     * @param key    The String key to set the data in
     * @param type   The <code>PersistentDataContainer</code> to use
     * @param value  The value to set
     * @param <Y>    The main object type stored by the tag
     * @param <Z>    The data type of the retrieved object
     * @return A reference to this object
     */
    @Override
    public <Y, Z> ItemBuilder setData(BukkitPlugin plugin, String key, PersistentDataType<Y, Z> type, Z value)
    {
        this.setData(new NamespacedKey(plugin, key), type, value);
        return this;
    }

    /**
     * {@inheritDoc}
     *
     * @param key  The <code>NamespacedKey</code> to check for
     * @param type The <code>PersistentDataContainer</code> to use
     * @param <Y>  The main object type stored by the tag
     * @param <Z>  The data type of the retrieved object
     * @return A reference to this object
     */
    @Override
    public <Y, Z> boolean hasData(NamespacedKey key, PersistentDataType<Y, Z> type)
    {
        return this.getPersistentDataContainer().has(key, type);
    }

    /**
     * {@inheritDoc}
     *
     * @param plugin The plugin's reference for namespace
     * @param key    The String key to check for
     * @param type   The <code>PersistentDataContainer</code> to use
     * @param <Y>    The main object type stored by the tag
     * @param <Z>    The data type of the retrieved object
     * @return A reference to this object
     */
    @Override
    public <Y, Z> boolean hasData(BukkitPlugin plugin, String key, PersistentDataType<Y, Z> type)
    {
        return this.hasData(new NamespacedKey(plugin, key), type);
    }

    /**
     * {@inheritDoc}
     *
     * @param key  The <code>NamespacedKey</code> to get the data from
     * @param type The <code>PersistentDataContainer</code> to use
     * @param <Y>  The main object type stored by the tag
     * @param <Z>  The data type of the retrieved object
     * @return A reference to this object
     */
    @Override
    public <Y, Z> Z getData(NamespacedKey key, PersistentDataType<Y, Z> type)
    {
        return this.getPersistentDataContainer().get(key, type);
    }

    /**
     * {@inheritDoc}
     *
     * @param plugin The plugin's reference for namespace
     * @param key    The String key to get
     * @param type   The <code>PersistentDataContainer</code> to use
     * @param <Y>    The main object type stored by the tag
     * @param <Z>    The data type of the retrieved object
     * @return A reference to this object
     */
    @Override
    public <Y, Z> Z getData(BukkitPlugin plugin, String key, PersistentDataType<Y, Z> type)
    {
        return this.getData(new NamespacedKey(plugin, key), type);
    }

    /**
     * {@inheritDoc}
     *
     * @param key          The <code>NamespacedKey</code> to get the data from
     * @param type         The <code>PersistentDataContainer</code> to use
     * @param defaultValue The default value to get if no value currently exists
     * @param <Y>          The main object type stored by the tag
     * @param <Z>          The data type of the retrieved object
     * @return A reference to this object
     */
    @Override
    public <Y, Z> Z getOrDefaultData(NamespacedKey key, PersistentDataType<Y, Z> type, Z defaultValue)
    {
        return this.getPersistentDataContainer().getOrDefault(key, type, defaultValue);
    }

    /**
     * {@inheritDoc}
     *
     * @param plugin       The plugin's reference for namespace
     * @param key          The String key to get
     * @param type         The <code>PersistentDataContainer</code> to use
     * @param defaultValue The default value to get if no value currently exists
     * @param <Y>          The main object type stored by the tag
     * @param <Z>          The data type of the retrieved object
     * @return A reference to this object
     */
    @Override
    public <Y, Z> Z getOrDefaultData(BukkitPlugin plugin, String key, PersistentDataType<Y, Z> type, Z defaultValue)
    {
        return this.getOrDefaultData(new NamespacedKey(plugin, key), type, defaultValue);
    }

    /**
     * {@inheritDoc}
     *
     * @param key The <code>NamespacedKey</code> to remove
     * @return  A reference to this object
     */
    @Override
    public ItemBuilder removeData(NamespacedKey key)
    {
        this.getPersistentDataContainer().remove(key);
        this.changed = true;
        return this;
    }

    /**
     * {@inheritDoc}
     *
     * @param plugin The plugin's reference for namespace
     * @param key    The String key to remove
     * @return A reference to this object
     */
    @Override
    public ItemBuilder removeData(BukkitPlugin plugin, String key)
    {
        this.getPersistentDataContainer().remove(new NamespacedKey(plugin, key));
        this.changed = true;
        return this;
    }

    /**
     * {@inheritDoc}
     *
     * @param keys The <code>NamespacedKeys</code> to remove
     * @return A reference to this object
     */
    @Override
    public ItemBuilder removeData(NamespacedKey... keys)
    {
        PersistentDataContainer container = this.getPersistentDataContainer();
        for(NamespacedKey key : keys)
        {
            container.remove(key);
        }
        this.changed = true;
        return this;
    }

    /**
     * {@inheritDoc}
     *
     * @param plugin The plugin's reference for namespace
     * @param keys   The String keys to remove
     * @return A reference to this object
     */
    @Override
    public ItemBuilder removeData(BukkitPlugin plugin, String... keys)
    {
        PersistentDataContainer container = this.getPersistentDataContainer();
        for(String key : keys)
        {
            container.remove(new NamespacedKey(plugin, key));
        }
        this.changed = true;
        return this;
    }

    /**
     * {@inheritDoc}
     *
     * @return Whether the item's data is empty
     */
    @Override
    public boolean isDataEmpty()
    {
        return this.getPersistentDataContainer().isEmpty();
    }

    /**
     * Internally set the {@link ItemMeta} for the item, taken from {@link ItemStack#setItemMeta0(ItemMeta, Material)}
     *
     * @param meta The reference {@link ItemMeta}
     */
    private void internalSetMeta(ItemMeta meta) {
        if (meta == null)
        {
            this.meta = null;
            return;
        }
        if (!Bukkit.getItemFactory().isApplicable(meta, type))
        {
            return;
        }
        this.meta = Bukkit.getItemFactory().asMetaFor(meta, type);

        Material newType = Bukkit.getItemFactory().updateMaterial(this.meta, type);
        if (this.type != newType)
        {
            this.type = newType;
        }

        if (this.meta == meta)
        {
            this.meta = meta.clone();
        }
    }

    /**
     * Get an {@link ItemBuilder} of a specified <code>ItemStack</code>
     *
     * @param item The reference <code>ItemStack</code>
     * @return The new {@link ItemBuilder}
     */
    public static ItemBuilder of(ItemStack item)
    {
        return new ItemBuilder(item);
    }

    /**
     * Get an {@link ItemBuilder} of a specified <code>Material</code>
     *
     * @param type The Material type to use
     * @return The new {@link ItemBuilder}
     */
    public static ItemBuilder of(Material type)
    {
        return new ItemBuilder(type);
    }

    /**
     * Get an empty {@link ItemBuilder}
     *
     * @return The new {@link ItemBuilder}
     */
    public static ItemBuilder of()
    {
        return new ItemBuilder();
    }

    /**
     * Get an {@link ItemBuilder} of a specified base 64 head String
     *
     * @param base64 The base 64 head String to use
     * @return The new {@link ItemBuilder}
     */
    public static ItemBuilder of(String base64)
    {
        return new ItemBuilder(base64);
    }

    /**
     * Get an {@link ItemBuilder} of a specified {@link OfflinePlayer} for a player head
     *
     * @param player The reference player
     * @return The new {@link ItemBuilder}
     */
    public static ItemBuilder of(OfflinePlayer player)
    {
        return new ItemBuilder(player);
    }

    /**
     * Clone this <code>ItemBuilder</code>
     *
     * @return The new cloned <code>ItemBuilder</code>
     */
    @Override
    public ItemBuilder clone()
    {
        ItemBuilder cloned = null;
        try
        {
            cloned = (ItemBuilder) super.clone();
        }
        catch(CloneNotSupportedException e)
        {
            e.printStackTrace();
            return null;
        }
        cloned.meta = this.meta != null ? this.meta.clone() : null;
        cloned.item = this.item != null ? this.item.clone() : null;
        return cloned;
    }
}
