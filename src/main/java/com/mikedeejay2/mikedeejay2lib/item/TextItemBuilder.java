package com.mikedeejay2.mikedeejay2lib.item;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Multimap;
import com.mikedeejay2.mikedeejay2lib.BukkitPlugin;
import com.mikedeejay2.mikedeejay2lib.text.Text;
import com.mikedeejay2.mikedeejay2lib.util.chat.Colors;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.OfflinePlayer;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.*;

public class TextItemBuilder extends ItemBuilder {
    protected Text name;
    protected List<Text> lore;

    protected TextItemBuilder(IItemBuilder<?, ?> builder) {
        super(builder);
        this.name = null;
        this.lore = new ArrayList<>();
    }

    @Override
    public ItemStack get() {
        updateMeta();
        return super.get();
    }

    @Override
    public ItemStack get(Player player) {
        updateMeta(player);
        return super.get(player);
    }

    @Override
    public ItemStack get(CommandSender sender) {
        updateMeta(sender);
        return super.get(sender);
    }

    @Override
    public ItemStack get(String locale) {
        updateMeta(locale);
        return super.get(locale);
    }

    @Override
    public TextItemBuilder set(ItemStack item) {
        super.set(item);
        updateTextFromMeta();
        return this;
    }

    @Override
    public ItemMeta getMeta() {
        return super.getMeta();
    }

    @Override
    public TextItemBuilder setMeta(ItemMeta meta) {
        super.setMeta(meta);
        updateTextFromMeta();
        return this;
    }

    @Override
    public String getName() {
        return name.get();
    }

    public String getName(Player player) {
        return name.get(player);
    }

    public String getName(CommandSender sender) {
        return name.get(sender);
    }

    public String getName(String locale) {
        return name.get(locale);
    }

    public Text getNameText() {
        return name;
    }

    @Override
    public TextItemBuilder setName(String name) {
        this.setName(Text.of(name));
        return this;
    }

    public TextItemBuilder setName(Text text) {
        this.name = text;
        this.changed = true;
        return this;
    }

    @Override
    public TextItemBuilder setAmount(int amount) {
        super.setAmount(amount);
        return this;
    }

    @Override
    public TextItemBuilder setType(Material material) {
        super.setType(material);
        return this;
    }

    @Override
    public List<String> getLore() {
        return loreToString();
    }

    public List<String> getLore(Player player) {
        return loreToString(player);
    }

    public List<String> getLore(CommandSender sender) {
        return loreToString(sender);
    }

    public List<String> getLore(String locale) {
        return loreToString(locale);
    }

    public List<Text> getLoreText() {
        return ImmutableList.copyOf(lore);
    }

    @Override
    public TextItemBuilder setLore(List<String> lore) {
        return this.setLore(lore.toArray(new String[0]));
    }

    @Override
    public TextItemBuilder setLore(String... lore) {
        this.lore.clear();
        this.addLore(lore);
        return this;
    }

    @Override
    public TextItemBuilder addLore(List<String> lore) {
        return this.addLore(lore.toArray(new String[0]));
    }

    @Override
    public TextItemBuilder addLore(String... lore) {
        for(String cur : lore) {
            this.lore.add(Text.of(Colors.addReset(Colors.format(cur))));
        }
        this.changed = true;
        return this;
    }

    @Override
    public TextItemBuilder addLore(int index, List<String> lore) {
        return this.addLore(index, lore.toArray(new String[0]));
    }

    @Override
    public TextItemBuilder addLore(int index, String... lore) {
        int curIndex = index;
        for(String cur : lore) {
            this.lore.add(curIndex++, Text.of(Colors.addReset(Colors.format(cur))));
        }
        this.changed = true;
        return this;
    }

    @Override
    public Map<Enchantment, Integer> getEnchants() {
        return super.getEnchants();
    }

    @Override
    public boolean hasEnchant(Enchantment enchantment) {
        return super.hasEnchant(enchantment);
    }

    @Override
    public int getEnchant(Enchantment enchantment) {
        return super.getEnchant(enchantment);
    }

    @Override
    public TextItemBuilder removeEnchant(Enchantment enchantment) {
        super.removeEnchant(enchantment);
        return this;
    }

    @Override
    public TextItemBuilder addEnchant(Enchantment enchantment, int level) {
        super.addEnchant(enchantment, level);
        return this;
    }

    @Override
    public boolean hasConflictingEnchant(Enchantment enchantment) {
        return super.hasConflictingEnchant(enchantment);
    }

    @Override
    public TextItemBuilder addItemFlags(ItemFlag... flags) {
        super.addItemFlags(flags);
        return this;
    }

    @Override
    public TextItemBuilder removeItemFlags(ItemFlag... flags) {
        super.removeItemFlags(flags);
        return this;
    }

    @Override
    public boolean hasItemFlag(ItemFlag flag) {
        return super.hasItemFlag(flag);
    }

    @Override
    public Set<ItemFlag> getItemFlags() {
        return super.getItemFlags();
    }

    @Override
    public TextItemBuilder addItemFlag(ItemFlag flag) {
        super.addItemFlag(flag);
        return this;
    }

    @Override
    public TextItemBuilder addGlow() {
        super.addGlow();
        return this;
    }

    @Override
    public TextItemBuilder removeGlow() {
        super.removeGlow();
        return this;
    }

    @Override
    public TextItemBuilder setEmptyName() {
        super.setEmptyName();
        return this;
    }

    @Override
    public TextItemBuilder setUnbreakable(boolean unbreakable) {
        super.setUnbreakable(unbreakable);
        return this;
    }

    @Override
    public boolean isUnbreakable() {
        return super.isUnbreakable();
    }

    @Override
    public OfflinePlayer getHeadOwner() {
        return super.getHeadOwner();
    }

    @Override
    public TextItemBuilder setHeadOwner(OfflinePlayer player) {
        super.setHeadOwner(player);
        return this;
    }

    @Override
    public String getHeadBase64() {
        return super.getHeadBase64();
    }

    @Override
    public TextItemBuilder setHeadBase64(String base64) {
        super.setHeadBase64(base64);
        return this;
    }

    @Override
    public boolean hasAttributeModifiers() {
        return super.hasAttributeModifiers();
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers() {
        return super.getAttributeModifiers();
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot) {
        return super.getAttributeModifiers(slot);
    }

    @Override
    public Collection<AttributeModifier> getAttributeModifiers(Attribute attribute) {
        return super.getAttributeModifiers(attribute);
    }

    @Override
    public TextItemBuilder addAttributeModifier(Attribute attribute, AttributeModifier modifier) {
        super.addAttributeModifier(attribute, modifier);
        return this;
    }

    @Override
    public TextItemBuilder addAttributeModifiers(Attribute attribute, AttributeModifier... modifiers) {
        super.addAttributeModifiers(attribute, modifiers);
        return this;
    }

    @Override
    public TextItemBuilder setAttributeModifiers(Multimap<Attribute, AttributeModifier> attributeModifiers) {
        super.setAttributeModifiers(attributeModifiers);
        return this;
    }

    @Override
    public TextItemBuilder removeAttributeModifier(Attribute attribute) {
        super.removeAttributeModifier(attribute);
        return this;
    }

    @Override
    public TextItemBuilder removeAttributeModifiers(Attribute... attributes) {
        super.removeAttributeModifiers(attributes);
        return this;
    }

    @Override
    public TextItemBuilder removeAttributeModifier(EquipmentSlot slot) {
        super.removeAttributeModifier(slot);
        return this;
    }

    @Override
    public TextItemBuilder removeAttributeModifiers(EquipmentSlot... slots) {
        super.removeAttributeModifiers(slots);
        return this;
    }

    @Override
    public TextItemBuilder removeAttributeModifier(Attribute attribute, AttributeModifier modifier) {
        super.removeAttributeModifier(attribute, modifier);
        return this;
    }

    @Override
    public TextItemBuilder removeAttributeModifiers(Attribute attribute, AttributeModifier... modifiers) {
        super.removeAttributeModifiers(attribute, modifiers);
        return this;
    }

    @Override
    public PersistentDataContainer getPersistentDataContainer() {
        return super.getPersistentDataContainer();
    }

    @Override
    public TextItemBuilder setCustomModelData(Integer data) {
        super.setCustomModelData(data);
        return this;
    }

    @Override
    public int getCustomModelData() {
        return super.getCustomModelData();
    }

    @Override
    public boolean hasCustomModelData() {
        return super.hasCustomModelData();
    }

    @Override
    public TextItemBuilder setLocalizedName(String name) {
        super.setLocalizedName(name);
        return this;
    }

    @Override
    public String getLocalizedName() {
        return super.getLocalizedName();
    }

    @Override
    public boolean hasLocalizedName() {
        return super.hasLocalizedName();
    }

    @Override
    public String getDisplayName() {
        return super.getDisplayName();
    }

    @Override
    public TextItemBuilder setDisplayName(String name) {
        super.setDisplayName(name);
        return this;
    }

    @Override
    public boolean hasDisplayName() {
        return super.hasDisplayName();
    }

    @Override
    public int getDurability() {
        return super.getDurability();
    }

    @Override
    public TextItemBuilder setDurability(int durability) {
        super.setDurability(durability);
        return this;
    }

    @Override
    public int getMaxStackSize() {
        return super.getMaxStackSize();
    }

    @Override
    public boolean hasDurability() {
        return super.hasDurability();
    }

    @Override
    public <Y, Z> ItemBuilder setData(NamespacedKey key, PersistentDataType<Y, Z> type, Z value) {
        return super.setData(key, type, value);
    }

    @Override
    public <Y, Z> ItemBuilder setData(BukkitPlugin plugin, String key, PersistentDataType<Y, Z> type, Z value) {
        return super.setData(plugin, key, type, value);
    }

    @Override
    public <Y, Z> boolean hasData(NamespacedKey key, PersistentDataType<Y, Z> type) {
        return super.hasData(key, type);
    }

    @Override
    public <Y, Z> boolean hasData(BukkitPlugin plugin, String key, PersistentDataType<Y, Z> type) {
        return super.hasData(plugin, key, type);
    }

    @Override
    public <Y, Z> Z getData(NamespacedKey key, PersistentDataType<Y, Z> type) {
        return super.getData(key, type);
    }

    @Override
    public <Y, Z> Z getData(BukkitPlugin plugin, String key, PersistentDataType<Y, Z> type) {
        return super.getData(plugin, key, type);
    }

    @Override
    public <Y, Z> Z getOrDefaultData(NamespacedKey key, PersistentDataType<Y, Z> type, Z defaultValue) {
        return super.getOrDefaultData(key, type, defaultValue);
    }

    @Override
    public <Y, Z> Z getOrDefaultData(BukkitPlugin plugin, String key, PersistentDataType<Y, Z> type, Z defaultValue) {
        return super.getOrDefaultData(plugin, key, type, defaultValue);
    }

    @Override
    public TextItemBuilder removeData(NamespacedKey key) {
        super.removeData(key);
        return this;
    }

    @Override
    public TextItemBuilder removeData(BukkitPlugin plugin, String key) {
        super.removeData(plugin, key);
        return this;
    }

    @Override
    public TextItemBuilder removeData(NamespacedKey... keys) {
        super.removeData(keys);
        return this;
    }

    @Override
    public TextItemBuilder removeData(BukkitPlugin plugin, String... keys) {
        super.removeData(plugin, keys);
        return this;
    }

    @Override
    public boolean isDataEmpty() {
        return super.isDataEmpty();
    }

    @Override
    public TextItemBuilder clone() {
        TextItemBuilder builder = (TextItemBuilder) super.clone();
        return builder;
    }

    private void updateTextFromMeta() {
        this.setName(this.meta.getDisplayName());
        this.setLore(this.meta.getLore());
    }

    private void updateMeta(Player player) {
        final List<String> strLore = loreToString(player);
        this.meta.setLore(strLore);
        this.meta.setDisplayName(name == null ? null : name.get(player));
    }

    private void updateMeta(CommandSender sender) {
        final List<String> strLore = loreToString(sender);
        this.meta.setLore(strLore);
        this.meta.setDisplayName(name == null ? null : name.get(sender));
    }

    private void updateMeta(String locale) {
        final List<String> strLore = loreToString(locale);
        this.meta.setLore(strLore);
        this.meta.setDisplayName(name == null ? null : name.get(locale));
    }

    private void updateMeta() {
        final List<String> strLore = loreToString();
        this.meta.setLore(strLore);
        this.meta.setDisplayName(name == null ? null : name.get());
    }

    private List<String> loreToString(Player player) {
        final List<String> strLore = new ArrayList<>();
        lore.forEach(cur -> strLore.add(cur.get(player)));
        return strLore;
    }

    private List<String> loreToString(CommandSender sender) {
        final List<String> strLore = new ArrayList<>();
        lore.forEach(cur -> strLore.add(cur.get(sender)));
        return strLore;
    }

    private List<String> loreToString(String locale) {
        final List<String> strLore = new ArrayList<>();
        lore.forEach(cur -> strLore.add(cur.get(locale)));
        return strLore;
    }

    private List<String> loreToString() {
        final List<String> strLore = new ArrayList<>();
        lore.forEach(cur -> strLore.add(cur.get()));
        return strLore;
    }
}
