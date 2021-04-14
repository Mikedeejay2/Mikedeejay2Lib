package com.mikedeejay2.mikedeejay2lib.item;

import com.google.common.collect.Lists;
import com.mikedeejay2.mikedeejay2lib.gui.GUIContainer;
import com.mikedeejay2.mikedeejay2lib.util.chat.Colors;
import com.mikedeejay2.mikedeejay2lib.util.enchant.GlowEnchantment;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.lang.reflect.Field;
import java.util.*;

public final class ItemBuilder implements IItemBuilder<ItemStack, ItemBuilder>
{
    private Material type;
    private int amount;
    private ItemMeta meta;
    private ItemStack item;
    private boolean changed;

    private ItemBuilder(ItemStack item)
    {
        this.type = item.getType();
        this.amount = item.getAmount();
        this.meta = null;
        if(item.hasItemMeta())
        {
            internalSetMeta(item.getItemMeta());
        }
        this.item = null;
        this.changed = true;
    }

    private ItemBuilder(Material material)
    {
        this.type = material;
        this.amount = 1;
        this.meta = Bukkit.getItemFactory().getItemMeta(type);
        this.item = null;
        this.changed = true;
    }

    private ItemBuilder()
    {
        this.type = Material.STONE;
        this.amount = 1;
        this.meta = Bukkit.getItemFactory().getItemMeta(type);
        this.item = null;
        this.changed = true;
    }

    private ItemBuilder(String base64)
    {
        this.type = Material.PLAYER_HEAD;
        this.amount = 1;
        this.meta = Bukkit.getItemFactory().getItemMeta(type);
        this.item = null;
        this.setHeadBase64(base64);
        this.changed = true;
    }

    private ItemBuilder(OfflinePlayer player)
    {
        this.type = Material.PLAYER_HEAD;
        this.amount = 1;
        this.meta = Bukkit.getItemFactory().getItemMeta(type);
        this.item = null;
        this.setHeadOwner(player);
        this.changed = true;
    }

    @Override
    public ItemStack get()
    {
        if(changed || item == null)
        {
            this.item = new ItemStack(type, amount);
            this.item.setItemMeta(meta);
            this.changed = false;
        }
        return item;
    }

    @Override
    public ItemBuilder set(ItemStack item)
    {
        this.type = item.getType();
        this.amount = item.getAmount();
        this.meta = item.getItemMeta();
        this.changed = true;
        return this;
    }

    @Override
    public ItemMeta getMeta()
    {
        return meta == null ? null : meta.clone();
    }

    @Override
    public ItemBuilder setMeta(ItemMeta meta)
    {
        internalSetMeta(meta);
        this.changed = true;
        return this;
    }

    @Override
    public String getName()
    {
        return meta.getDisplayName();
    }

    @Override
    public ItemBuilder setName(String name)
    {
        this.meta.setDisplayName(Colors.format(name));
        this.changed = true;
        return this;
    }

    @Override
    public int getAmount()
    {
        return amount;
    }

    @Override
    public ItemBuilder setAmount(int amount)
    {
        this.amount = amount;
        this.changed = true;
        return this;
    }

    @Override
    public Material getType()
    {
        return type;
    }

    @Override
    public ItemBuilder setType(Material type)
    {
        this.type = type;
        if (this.meta != null) {
            this.meta = Bukkit.getItemFactory().asMetaFor(meta, type);
        }
        this.changed = true;
        return this;
    }

    @Override
    public List<String> getLore()
    {
        return meta.getLore();
    }

    @Override
    public ItemBuilder setLore(List<String> lore)
    {
        this.meta.setLore(Colors.format(lore));
        this.changed = true;
        return this;
    }

    @Override
    public ItemBuilder setLore(String... lore)
    {
        this.meta.setLore(Colors.format(Arrays.asList(lore)));
        this.changed = true;
        return this;
    }

    @Override
    public ItemBuilder addLore(List<String> lore)
    {
        List<String> curLore = this.meta.getLore();
        if(curLore == null) curLore = new ArrayList<>();
        curLore.addAll(Colors.format(lore));
        this.meta.setLore(curLore);
        return this;
    }

    @Override
    public ItemBuilder addLore(String... lore)
    {
        List<String> curLore = this.meta.getLore();
        if(curLore == null) curLore = new ArrayList<>();
        curLore.addAll(Arrays.asList(Colors.format(lore)));
        this.meta.setLore(curLore);
        return this;
    }

    @Override
    public ItemBuilder addLore(int index, List<String> lore)
    {
        List<String> curLore = this.meta.getLore();
        if(curLore == null) curLore = new ArrayList<>();
        curLore.addAll(index, Colors.format(lore));
        this.meta.setLore(curLore);
        return this;
    }

    @Override
    public ItemBuilder addLore(int index, String... lore)
    {
        List<String> curLore = this.meta.getLore();
        if(curLore == null) curLore = new ArrayList<>();
        curLore.addAll(index, Arrays.asList(Colors.format(lore)));
        this.meta.setLore(curLore);
        return this;
    }

    @Override
    public Map<Enchantment, Integer> getEnchants()
    {
        return meta.getEnchants();
    }

    @Override
    public boolean hasEnchant(Enchantment enchantment)
    {
        return meta.hasEnchant(enchantment);
    }

    @Override
    public int getEnchant(Enchantment enchantment)
    {
        return meta.getEnchantLevel(enchantment);
    }

    @Override
    public ItemBuilder removeEnchant(Enchantment enchantment)
    {
        this.meta.removeEnchant(enchantment);
        this.changed = true;
        return this;
    }

    @Override
    public ItemBuilder addEnchant(Enchantment enchantment, int level)
    {
        this.meta.addEnchant(enchantment, level, true);
        this.changed = true;
        return this;
    }

    @Override
    public ItemBuilder addItemFlags(ItemFlag... flags)
    {
        this.meta.addItemFlags(flags);
        this.changed = true;
        return this;
    }

    @Override
    public ItemBuilder removeItemFlags(ItemFlag... flags)
    {
        this.meta.removeItemFlags(flags);
        this.changed = true;
        return this;
    }

    @Override
    public ItemBuilder addGlow()
    {

        this.addEnchant(GlowEnchantment.get(), 1);
        this.changed = true;
        return this;
    }

    @Override
    public ItemBuilder removeGlow()
    {
        this.removeEnchant(GlowEnchantment.get());
        this.changed = true;
        return this;
    }

    @Override
    public ItemBuilder setEmptyName()
    {
        this.meta.setDisplayName(GUIContainer.EMPTY_NAME);
        this.changed = true;
        return this;
    }

    @Override
    public ItemBuilder setUnbreakable(boolean unbreakable)
    {
        this.meta.setUnbreakable(unbreakable);
        this.changed = true;
        return this;
    }

    @Override
    public boolean isUnbreakable()
    {
        return this.meta.isUnbreakable();
    }

    @Override
    public OfflinePlayer getHeadOwner()
    {
        SkullMeta skullMeta = (SkullMeta) meta;
        return skullMeta.getOwningPlayer();
    }

    @Override
    public ItemBuilder setHeadOwner(OfflinePlayer player)
    {
        SkullMeta skullMeta = (SkullMeta) meta;
        skullMeta.setOwningPlayer(player);
        this.changed = true;
        return this;
    }

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

    public static ItemBuilder of(ItemStack item)
    {
        return new ItemBuilder(item);
    }

    public static ItemBuilder of(Material type)
    {
        return new ItemBuilder(type);
    }

    public static ItemBuilder of()
    {
        return new ItemBuilder();
    }

    public static ItemBuilder of(String base64)
    {
        return new ItemBuilder(base64);
    }

    public static ItemBuilder of(OfflinePlayer player)
    {
        return new ItemBuilder(player);
    }
}
