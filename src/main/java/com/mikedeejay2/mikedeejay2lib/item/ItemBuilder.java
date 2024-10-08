package com.mikedeejay2.mikedeejay2lib.item;

import com.google.common.collect.Multimap;
import com.mikedeejay2.mikedeejay2lib.BukkitPlugin;
import com.mikedeejay2.mikedeejay2lib.text.Text;
import com.mikedeejay2.mikedeejay2lib.util.chat.Colors;
import com.mikedeejay2.mikedeejay2lib.util.head.SkullMetaModifier;
import com.mikedeejay2.mikedeejay2lib.util.item.ItemUtil;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.net.URL;
import java.util.*;

/**
 * A class for building items dynamically. See {@link ItemBuilder#of()} to get started.
 *
 * @author Mikedeejay2
 */
public class ItemBuilder implements IItemBuilder<ItemStack, ItemBuilder>, Cloneable {
    /**
     * An empty name so that items that shouldn't have a title have the smallest title possible for Minecraft
     * (Because you can't specify nothing)
     */
    public static final String EMPTY_NAME = "§7";

    /**
     * The {@link Material} of the built item
     */
    protected Material type;

    /**
     * The stack amount of the built item
     */
    protected int amount;

    /**
     * The {@link ItemMeta} of the built item
     */
    protected ItemMeta meta;

    /**
     * The built item, null if not yet built
     */
    protected ItemStack item;

    /**
     * Whether the item has been changed since last build
     */
    protected boolean changed;

    /**
     * Construct a new <code>ItemBuilder</code>
     *
     * @param item The reference item
     */
    protected ItemBuilder(ItemStack item) {
        set(item);
        this.item = null;
        this.changed = true;
    }

    /**
     * Construct a new <code>ItemBuilder</code>
     *
     * @param material The starting material
     */
    protected ItemBuilder(Material material) {
        this.type = material;
        this.amount = 1;
        this.meta = Bukkit.getItemFactory().getItemMeta(type);
        this.item = null;
        this.changed = true;
    }

    /**
     * Construct a new <code>ItemBuilder</code> with the default material of <code>STONE</code>
     */
    protected ItemBuilder() {
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
    protected ItemBuilder(String base64) {
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
    protected ItemBuilder(OfflinePlayer player) {
        this.type = Material.PLAYER_HEAD;
        this.amount = 1;
        this.meta = Bukkit.getItemFactory().getItemMeta(type);
        this.item = null;
        this.setHeadOwner(player);
        this.changed = true;
    }

    /**
     * Construct a new <code>ItemBuilder</code> for an ItemBuilder
     *
     * @param builder The player reference
     */
    protected ItemBuilder(IItemBuilder<?, ?> builder) {
        this.type = builder.getType();
        this.amount = builder.getAmount();
        this.meta = builder.getMeta();
        this.item = null;
        this.changed = true;
    }

    @Override
    public ItemStack get() {
        if(type == null) return null;
        if(changed || item == null) {
            this.item = new ItemStack(type, amount);
            this.item.setItemMeta(meta);
            this.changed = false;
        }
        return item;
    }

    @Override
    public ItemStack get(Player player) {
        return this.get();
    }

    @Override
    public ItemStack get(CommandSender sender) {
        return this.get();
    }

    @Override
    public ItemStack get(String locale) {
        return this.get();
    }

    @Override
    public ItemBuilder set(ItemStack item) {
        this.type = item != null ? item.getType() : null;
        this.amount = item != null ? item.getAmount() : 0;
        this.meta = null;
        if(item != null) {
            if(item.hasItemMeta()) internalSetMeta(item.getItemMeta());
            else internalSetMeta(Bukkit.getItemFactory().getItemMeta(type));
        }
        this.changed = true;
        return this;
    }

    @Override
    public ItemMeta getMeta() {
        return meta == null ? null : meta.clone();
    }

    @Override
    public ItemBuilder setMeta(ItemMeta meta) {
        internalSetMeta(meta);
        this.changed = true;
        return this;
    }

    @Override
    public String getName() {
        return ItemUtil.getName(meta);
    }

    @Override
    public String getName(Player player) {
        return this.getName();
    }

    @Override
    public String getName(CommandSender sender) {
        return this.getName();
    }

    @Override
    public String getName(String locale) {
        return this.getName();
    }

    @Override
    public ItemBuilder setName(String name) {
        ItemUtil.setName(this.meta, Colors.addReset(Colors.format(name)));
        this.changed = true;
        return this;
    }

    @Override
    public TextItemBuilder setName(Text text) {
        return new TextItemBuilder(this).setName(text);
    }

    @Override
    public int getAmount() {
        return amount;
    }

    @Override
    public ItemBuilder setAmount(int amount) {
        this.amount = amount;
        this.changed = true;
        return this;
    }

    @Override
    public Material getType() {
        return type;
    }

    @Override
    public ItemBuilder setType(Material material) {
        this.type = material;
        if (this.meta != null) {
            this.meta = Bukkit.getItemFactory().asMetaFor(meta, material);
        }
        this.changed = true;
        return this;
    }

    @Override
    public boolean hasLore() {
        return meta != null && meta.hasLore();
    }

    @Override
    public List<String> getLore() {
        return meta.getLore();
    }

    @Override
    public List<String> getLore(Player player) {
        return this.getLore();
    }

    @Override
    public List<String> getLore(CommandSender sender) {
        return this.getLore();
    }

    @Override
    public List<String> getLore(String locale) {
        return this.getLore();
    }

    @Override
    public ItemBuilder setLore(List<String> lore) {
        this.meta.setLore(Colors.addReset(Colors.format(lore)));
        this.changed = true;
        return this;
    }

    @Override
    public ItemBuilder setLore(String... lore) {
        this.meta.setLore(Colors.addReset(Colors.format(Arrays.asList(lore))));
        this.changed = true;
        return this;
    }

    @Override
    public TextItemBuilder setLoreText(List<Text> lore) {
        return new TextItemBuilder(this).setLoreText(lore);
    }

    @Override
    public TextItemBuilder setLore(Text... lore) {
        return new TextItemBuilder(this).setLore(lore);
    }

    @Override
    public ItemBuilder addLore(List<String> lore) {
        List<String> curLore = this.meta.getLore();
        if(curLore == null) curLore = new ArrayList<>();
        curLore.addAll(Colors.addReset(Colors.format(lore)));
        this.meta.setLore(curLore);
        return this;
    }

    @Override
    public ItemBuilder addLore(String... lore) {
        List<String> curLore = this.meta.getLore();
        if(curLore == null) curLore = new ArrayList<>();
        curLore.addAll(Arrays.asList(Colors.addReset(Colors.format(lore))));
        this.meta.setLore(curLore);
        return this;
    }

    @Override
    public ItemBuilder addLore(int index, List<String> lore) {
        List<String> curLore = this.meta.getLore();
        if(curLore == null) curLore = new ArrayList<>();
        curLore.addAll(index, Colors.addReset(Colors.format(lore)));
        this.meta.setLore(curLore);
        return this;
    }

    @Override
    public ItemBuilder addLore(int index, String... lore) {
        List<String> curLore = this.meta.getLore();
        if(curLore == null) curLore = new ArrayList<>();
        curLore.addAll(index, Arrays.asList(Colors.addReset(Colors.format(lore))));
        this.meta.setLore(curLore);
        return this;
    }

    @Override
    public TextItemBuilder addLoreText(List<Text> lore) {
        return new TextItemBuilder(this).addLoreText(lore);
    }

    @Override
    public TextItemBuilder addLore(Text... lore) {
        return new TextItemBuilder(this).addLore(lore);
    }

    @Override
    public TextItemBuilder addLoreText(int index, List<Text> lore) {
        return new TextItemBuilder(this).addLoreText(index, lore);
    }

    @Override
    public TextItemBuilder addLore(int index, Text... lore) {
        return new TextItemBuilder(this).addLore(index, lore);
    }

    @Override
    public Map<Enchantment, Integer> getEnchants() {
        return meta.getEnchants();
    }

    @Override
    public boolean hasEnchant(Enchantment enchantment) {
        return meta.hasEnchant(enchantment);
    }

    @Override
    public int getEnchant(Enchantment enchantment) {
        return meta.getEnchantLevel(enchantment);
    }

    @Override
    public ItemBuilder removeEnchant(Enchantment enchantment) {
        this.meta.removeEnchant(enchantment);
        this.changed = true;
        return this;
    }

    @Override
    public ItemBuilder addEnchant(Enchantment enchantment, int level) {
        this.meta.addEnchant(enchantment, level, true);
        this.changed = true;
        return this;
    }

    @Override
    public boolean hasConflictingEnchant(Enchantment enchantment) {
        return this.meta.hasConflictingEnchant(enchantment);
    }

    @Override
    public ItemBuilder addItemFlags(ItemFlag... flags) {
        this.meta.addItemFlags(flags);
        this.changed = true;
        return this;
    }

    @Override
    public ItemBuilder removeItemFlags(ItemFlag... flags) {
        this.meta.removeItemFlags(flags);
        this.changed = true;
        return this;
    }

    @Override
    public boolean hasItemFlag(ItemFlag flag) {
        return this.meta.hasItemFlag(flag);
    }

    @Override
    public Set<ItemFlag> getItemFlags() {
        return this.meta.getItemFlags();
    }

    @Override
    public ItemBuilder addItemFlag(ItemFlag flag) {
        this.meta.addItemFlags(flag);
        this.changed = true;
        return this;
    }

    @Override
    public ItemBuilder addGlow() {
        this.addEnchant(Enchantment.LURE, 1);
        this.addItemFlag(ItemFlag.HIDE_ENCHANTS);
        this.changed = true;
        return this;
    }

    @Override
    public ItemBuilder removeGlow() {
        this.removeEnchant(Enchantment.LURE);
        this.removeItemFlags(ItemFlag.HIDE_ENCHANTS);
        this.changed = true;
        return this;
    }

    @Override
    public ItemBuilder setEmptyName() {
        ItemUtil.setName(meta, EMPTY_NAME);
        this.changed = true;
        return this;
    }

    @Override
    public ItemBuilder setUnbreakable(boolean unbreakable) {
        this.meta.setUnbreakable(unbreakable);
        this.changed = true;
        return this;
    }

    @Override
    public boolean isUnbreakable() {
        return this.meta.isUnbreakable();
    }

    @Override
    public OfflinePlayer getHeadOwner() {
        SkullMeta skullMeta = (SkullMeta) meta;
        return skullMeta.getOwningPlayer();
    }

    @Override
    public ItemBuilder setHeadOwner(OfflinePlayer player) {
        SkullMeta skullMeta = (SkullMeta) meta;
        skullMeta.setOwningPlayer(player);
        this.changed = true;
        return this;
    }

    @Override
    public String getHeadBase64() {
        SkullMeta skullMeta = (SkullMeta) meta;
        return SkullMetaModifier.getHeadBase64(skullMeta);
    }

    @Override
    public URL getHeadUrl() {
        SkullMeta skullMeta = (SkullMeta) meta;
        return SkullMetaModifier.getHeadUrl(skullMeta);
    }

    @Override
    public String getHeadUrlAsString() {
        SkullMeta skullMeta = (SkullMeta) meta;
        return SkullMetaModifier.getHeadUrlAsString(skullMeta);
    }

    @Override
    public ItemBuilder setHeadBase64(String base64) {
        SkullMeta skullMeta = (SkullMeta) meta;
        SkullMetaModifier.setHeadBase64(skullMeta, base64);
        this.changed = true;
        return this;
    }

    @Override
    public ItemBuilder setHeadUrl(URL url) {
        SkullMeta skullMeta = (SkullMeta) meta;
        SkullMetaModifier.setHeadUrl(skullMeta, url);
        this.changed = true;
        return this;
    }

    @Override
    public ItemBuilder setHeadUrl(String url) {
        SkullMeta skullMeta = (SkullMeta) meta;
        SkullMetaModifier.setHeadUrl(skullMeta, url);
        this.changed = true;
        return this;
    }

    @Override
    public boolean hasAttributeModifiers() {
        return this.meta.hasAttributeModifiers();
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers() {
        return this.meta.getAttributeModifiers();
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot) {
        return this.meta.getAttributeModifiers(slot);
    }

    @Override
    public Collection<AttributeModifier> getAttributeModifiers(Attribute attribute) {
        return this.meta.getAttributeModifiers(attribute);
    }

    @Override
    public ItemBuilder addAttributeModifier(Attribute attribute, AttributeModifier modifier) {
        this.meta.addAttributeModifier(attribute, modifier);
        this.changed = true;
        return this;
    }

    @Override
    public ItemBuilder addAttributeModifiers(Attribute attribute, AttributeModifier... modifiers) {
        for(AttributeModifier modifier : modifiers) {
            this.meta.addAttributeModifier(attribute, modifier);
        }
        this.changed = true;
        return this;
    }

    @Override
    public ItemBuilder setAttributeModifiers(Multimap<Attribute, AttributeModifier> attributeModifiers) {
        this.meta.setAttributeModifiers(attributeModifiers);
        this.changed = true;
        return this;
    }

    @Override
    public ItemBuilder removeAttributeModifier(Attribute attribute) {
        this.meta.removeAttributeModifier(attribute);
        this.changed = true;
        return this;
    }

    @Override
    public ItemBuilder removeAttributeModifiers(Attribute... attributes) {
        for(Attribute attribute : attributes) {
            this.meta.removeAttributeModifier(attribute);
        }
        this.changed = true;
        return this;
    }

    @Override
    public ItemBuilder removeAttributeModifier(EquipmentSlot slot) {
        this.meta.removeAttributeModifier(slot);
        this.changed = true;
        return this;
    }

    @Override
    public ItemBuilder removeAttributeModifiers(EquipmentSlot... slots) {
        for(EquipmentSlot slot : slots) {
            this.meta.removeAttributeModifier(slot);
        }
        this.changed = true;
        return this;
    }

    @Override
    public ItemBuilder removeAttributeModifier(Attribute attribute, AttributeModifier modifier) {
        this.meta.removeAttributeModifier(attribute, modifier);
        this.changed = true;
        return this;
    }

    @Override
    public ItemBuilder removeAttributeModifiers(Attribute attribute, AttributeModifier... modifiers) {
        for(AttributeModifier modifier : modifiers) {
            this.meta.removeAttributeModifier(attribute, modifier);
        }
        this.changed = true;
        return this;
    }

    @Override
    public PersistentDataContainer getPersistentDataContainer() {
        return this.meta.getPersistentDataContainer();
    }

    @Override
    public ItemBuilder setCustomModelData(Integer data) {
        this.meta.setCustomModelData(data);
        this.changed = true;
        return this;
    }

    @Override
    public int getCustomModelData() {
        return this.meta.getCustomModelData();
    }

    @Override
    public boolean hasCustomModelData() {
        return this.meta.hasCustomModelData();
    }

    @Override
    public String getDisplayName() {
        return ItemUtil.getName(this.meta);
    }

    @Override
    public ItemBuilder setDisplayName(String name) {
        this.setName(name);
        return this;
    }

    @Override
    public boolean hasDisplayName() {
        return this.meta != null && this.meta.hasDisplayName();
    }

    @Override
    public int getDurability() {
        return ((Damageable) this.meta).getDamage();
    }

    @Override
    public ItemBuilder setDurability(int durability) {
        ((Damageable) this.meta).setDamage(durability);
        this.changed = true;
        return this;
    }

    @Override
    public int getMaxStackSize() {
        Material material = this.getType();
        if (material != null)  return material.getMaxStackSize();
        return -1;
    }

    @Override
    public boolean hasDurability() {
        return this.meta instanceof Damageable;
    }

    @Override
    public <Y, Z> ItemBuilder setData(NamespacedKey key, PersistentDataType<Y, Z> type, Z value) {
        this.getPersistentDataContainer().set(key, type, value);
        this.changed = true;
        return this;
    }

    @Override
    public <Y, Z> ItemBuilder setData(BukkitPlugin plugin, String key, PersistentDataType<Y, Z> type, Z value) {
        this.setData(new NamespacedKey(plugin, key), type, value);
        return this;
    }

    @Override
    public <Y, Z> boolean hasData(NamespacedKey key, PersistentDataType<Y, Z> type) {
        return this.getPersistentDataContainer().has(key, type);
    }

    @Override
    public <Y, Z> boolean hasData(BukkitPlugin plugin, String key, PersistentDataType<Y, Z> type) {
        return this.hasData(new NamespacedKey(plugin, key), type);
    }

    @Override
    public <Y, Z> Z getData(NamespacedKey key, PersistentDataType<Y, Z> type) {
        return this.getPersistentDataContainer().get(key, type);
    }

    @Override
    public <Y, Z> Z getData(BukkitPlugin plugin, String key, PersistentDataType<Y, Z> type) {
        return this.getData(new NamespacedKey(plugin, key), type);
    }

    @Override
    public <Y, Z> Z getOrDefaultData(NamespacedKey key, PersistentDataType<Y, Z> type, Z defaultValue) {
        return this.getPersistentDataContainer().getOrDefault(key, type, defaultValue);
    }

    @Override
    public <Y, Z> Z getOrDefaultData(BukkitPlugin plugin, String key, PersistentDataType<Y, Z> type, Z defaultValue) {
        return this.getOrDefaultData(new NamespacedKey(plugin, key), type, defaultValue);
    }

    @Override
    public ItemBuilder removeData(NamespacedKey key) {
        this.getPersistentDataContainer().remove(key);
        this.changed = true;
        return this;
    }

    @Override
    public ItemBuilder removeData(BukkitPlugin plugin, String key) {
        this.getPersistentDataContainer().remove(new NamespacedKey(plugin, key));
        this.changed = true;
        return this;
    }

    @Override
    public ItemBuilder removeData(NamespacedKey... keys) {
        PersistentDataContainer container = this.getPersistentDataContainer();
        for(NamespacedKey key : keys) {
            container.remove(key);
        }
        this.changed = true;
        return this;
    }

    @Override
    public ItemBuilder removeData(BukkitPlugin plugin, String... keys) {
        PersistentDataContainer container = this.getPersistentDataContainer();
        for(String key : keys) {
            container.remove(new NamespacedKey(plugin, key));
        }
        this.changed = true;
        return this;
    }

    /**
     * Get whether the item has been changed since last build
     *
     * @return Whether the item has been changed since last build
     */
    public boolean isChanged() {
        return changed;
    }

    @Override
    public boolean isDataEmpty() {
        return this.getPersistentDataContainer().isEmpty();
    }

    /**
     * Internally set the {@link ItemMeta} for the item, taken from {@link ItemStack#setItemMeta0(ItemMeta, Material)}
     *
     * @param meta The reference {@link ItemMeta}
     */
    private void internalSetMeta(ItemMeta meta) {
        if (meta == null) {
            this.meta = null;
            return;
        }
        if (!Bukkit.getItemFactory().isApplicable(meta, type)) {
            return;
        }
        this.meta = Bukkit.getItemFactory().asMetaFor(meta, type);

        if (this.meta == meta) {
            this.meta = meta.clone();
        }
    }

    /**
     * Get an {@link ItemBuilder} of a specified <code>ItemStack</code>
     *
     * @param item The reference <code>ItemStack</code>
     * @return The new {@link ItemBuilder}
     */
    public static ItemBuilder of(ItemStack item) {
        return new ItemBuilder(item);
    }

    /**
     * Get an {@link ItemBuilder} of a specified <code>ItemBuilder</code>
     *
     * @param builder The reference <code>ItemBuilder</code>
     * @return The new {@link ItemBuilder}
     */
    public static ItemBuilder of(IItemBuilder<?, ?> builder) {
        if(builder instanceof TextItemBuilder) {
            return new TextItemBuilder(builder);
        }
        return new ItemBuilder(builder);
    }

    /**
     * Get an {@link ItemBuilder} of a specified <code>Material</code>
     *
     * @param type The Material type to use
     * @return The new {@link ItemBuilder}
     */
    public static ItemBuilder of(Material type) {
        return new ItemBuilder(type);
    }

    /**
     * Get an empty {@link ItemBuilder}
     *
     * @return The new {@link ItemBuilder}
     */
    public static ItemBuilder of() {
        return new ItemBuilder();
    }

    /**
     * Get an {@link ItemBuilder} of a specified base 64 head String
     *
     * @param base64 The base 64 head String to use
     * @return The new {@link ItemBuilder}
     */
    public static ItemBuilder of(String base64) {
        return new ItemBuilder(base64);
    }

    /**
     * Get an {@link ItemBuilder} of a specified {@link OfflinePlayer} for a player head
     *
     * @param player The reference player
     * @return The new {@link ItemBuilder}
     */
    public static ItemBuilder of(OfflinePlayer player) {
        return new ItemBuilder(player);
    }

    /**
     * Clone this <code>ItemBuilder</code>
     *
     * @return The new cloned <code>ItemBuilder</code>
     */
    @Override
    public ItemBuilder clone() {
        ItemBuilder cloned;
        try {
            cloned = (ItemBuilder) super.clone();
        } catch(CloneNotSupportedException e) {
            e.printStackTrace();
            return null;
        }
        cloned.meta = this.meta != null ? this.meta.clone() : null;
        cloned.item = this.item != null ? this.item.clone() : null;
        return cloned;
    }
}
