package com.mikedeejay2.mikedeejay2lib.item;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Multimap;
import com.mikedeejay2.mikedeejay2lib.BukkitPlugin;
import com.mikedeejay2.mikedeejay2lib.text.Text;
import com.mikedeejay2.mikedeejay2lib.util.chat.Colors;
import com.mikedeejay2.mikedeejay2lib.util.item.ItemUtil;
import org.bukkit.ChatColor;
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
import org.bukkit.persistence.PersistentDataType;

import java.net.URL;
import java.util.*;

/**
 * An extension of {@link ItemBuilder} for use of {@link Text} features for item display name and lore.
 * An {@link ItemBuilder} is automatically converted to a <code>TextItemBuilder</code> upon invocation of a method that
 * takes a {@link Text} parameter. See {@link ItemBuilder#of()} to get started.
 *
 * @author Mikedeejay2
 */
public class TextItemBuilder extends ItemBuilder {
    private static final Text RESET_TEXT = Text.of(ChatColor.RESET.toString());

    protected Text name;
    protected List<Text> lore;
    protected String previousLocale;

    protected TextItemBuilder(IItemBuilder<?, ?> builder) {
        super(builder);
        this.name = null;
        this.lore = new ArrayList<>();
        this.previousLocale = null;
        if(builder instanceof TextItemBuilder) {
            TextItemBuilder textBuilder = (TextItemBuilder) builder;
            setName(textBuilder.getNameText());
            setLoreText(textBuilder.getLoreText());
        } else {
            if(builder.hasDisplayName()) {
                setName(builder.getName());
            }
            if(builder.hasLore()) {
                setLore(builder.getLore());
            }
        }
    }

    @Override
    public ItemStack get() {
        updateMeta();
        return super.get();
    }

    @Override
    public ItemStack get(Player player) {
        updateMeta(player);
        return super.get();
    }

    @Override
    public ItemStack get(CommandSender sender) {
        updateMeta(sender);
        return super.get();
    }

    @Override
    public ItemStack get(String locale) {
        updateMeta(locale);
        return super.get();
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

    @Override
    public String getName(Player player) {
        return name.get(player);
    }

    @Override
    public String getName(CommandSender sender) {
        return name.get(sender);
    }

    @Override
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

    @Override
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

    @Override
    public List<String> getLore(Player player) {
        return loreToString(player);
    }

    @Override
    public List<String> getLore(CommandSender sender) {
        return loreToString(sender);
    }

    @Override
    public List<String> getLore(String locale) {
        return loreToString(locale);
    }

    public List<Text> getLoreText() {
        return ImmutableList.copyOf(lore);
    }

    @Override
    public TextItemBuilder setLore(List<String> lore) {
        return this.setLore(lore == null ? null : lore.toArray(new String[0]));
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
    public TextItemBuilder setLoreText(List<Text> lore) {
        return this.setLore(lore.toArray(new Text[0]));
    }

    @Override
    public TextItemBuilder setLore(Text... lore) {
        this.lore.clear();
        this.addLore(lore);
        return this;
    }

    @Override
    public TextItemBuilder addLoreText(List<Text> lore) {
        return this.addLore(lore.toArray(new Text[0]));
    }

    @Override
    public TextItemBuilder addLore(Text... lore) {
        for(Text cur : lore) {
            this.lore.add(RESET_TEXT.concat(cur));
        }
        this.changed = true;
        return this;
    }

    @Override
    public TextItemBuilder addLoreText(int index, List<Text> lore) {
        return this.addLore(index, lore.toArray(new Text[0]));
    }

    @Override
    public TextItemBuilder addLore(int index, Text... lore) {
        int curIndex = index;
        for(Text cur : lore) {
            this.lore.add(curIndex++, RESET_TEXT.concat(cur));
        }
        this.changed = true;
        return this;
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
    public TextItemBuilder setHeadOwner(OfflinePlayer player) {
        super.setHeadOwner(player);
        return this;
    }

    @Override
    public TextItemBuilder setHeadBase64(String base64) {
        super.setHeadBase64(base64);
        return this;
    }

    @Override
    public TextItemBuilder setHeadUrl(URL url) {
        super.setHeadUrl(url);
        return this;
    }

    @Override
    public TextItemBuilder setHeadUrl(String url) {
        super.setHeadUrl(url);
        return this;
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
    public TextItemBuilder setCustomModelData(Integer data) {
        super.setCustomModelData(data);
        return this;
    }

    @Override
    public TextItemBuilder setDisplayName(String name) {
        super.setDisplayName(name);
        return this;
    }

    @Override
    public TextItemBuilder setDurability(int durability) {
        super.setDurability(durability);
        return this;
    }

    @Override
    public <Y, Z> TextItemBuilder setData(NamespacedKey key, PersistentDataType<Y, Z> type, Z value) {
        super.setData(key, type, value);
        return this;
    }

    @Override
    public <Y, Z> TextItemBuilder setData(BukkitPlugin plugin, String key, PersistentDataType<Y, Z> type, Z value) {
        super.setData(plugin, key, type, value);
        return this;
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
    public TextItemBuilder clone() {
        TextItemBuilder builder = (TextItemBuilder) super.clone();
        builder.lore = new ArrayList<>(this.lore);
        return builder;
    }

    private void updateTextFromMeta() {
        this.setName(ItemUtil.hasName(this.meta) ? ItemUtil.getName(this.meta) : null);
        this.setLore(this.meta.getLore() == null ? Collections.emptyList() : this.meta.getLore());
    }

    private void updateMeta(Player player) {
        String locale = player.getLocale();
        if(isCacheValid(locale)) return;
        checkAndUpdateMeta(name == null ? null : Colors.format(name.get(player)), loreToString(player));
        this.previousLocale = player.getLocale();
    }

    private void updateMeta(CommandSender sender) {
        String locale = sender instanceof Player ? ((Player) sender).getLocale() : null;
        if(isCacheValid(locale)) return;
        checkAndUpdateMeta(name == null ? null : Colors.format(name.get(sender)), loreToString(sender));
        this.previousLocale = locale;
    }

    private void updateMeta(String locale) {
        if(isCacheValid(locale)) return;
        checkAndUpdateMeta(name == null ? null : Colors.format(name.get(locale)), loreToString(locale));
        this.previousLocale = locale;
    }

    private void updateMeta() {
        if(isCacheValid(null)) return;
        checkAndUpdateMeta(name == null ? null : Colors.format(name.get()), loreToString());
        this.previousLocale = null;
    }

    private void checkAndUpdateMeta(String name, List<String> lore) {
        ItemUtil.setName(this.meta, name);
        this.meta.setLore(lore);
        this.changed = true;
    }

    private List<String> loreToString(Player player) {
        final List<String> strLore = new ArrayList<>();
        lore.forEach(cur -> strLore.add(Colors.format(cur.get(player))));
        return strLore;
    }

    private List<String> loreToString(CommandSender sender) {
        final List<String> strLore = new ArrayList<>();
        lore.forEach(cur -> strLore.add(Colors.format(cur.get(sender))));
        return strLore;
    }

    private List<String> loreToString(String locale) {
        final List<String> strLore = new ArrayList<>();
        lore.forEach(cur -> strLore.add(Colors.format(cur.get(locale))));
        return strLore;
    }

    private List<String> loreToString() {
        final List<String> strLore = new ArrayList<>();
        lore.forEach(cur -> strLore.add(Colors.format(cur.get())));
        return strLore;
    }

    private boolean isCacheValid(String locale) {
        return ((item != null) && ((locale == null && previousLocale == null) ||
            (locale != null && locale.equals(previousLocale)))) && !changed;
    }
}
