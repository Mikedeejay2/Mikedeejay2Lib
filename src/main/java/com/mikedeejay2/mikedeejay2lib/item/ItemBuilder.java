package com.mikedeejay2.mikedeejay2lib.item;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;
import java.util.Map;

public final class ItemBuilder implements IItemBuilder<ItemStack, ItemBuilder>
{
    private Material type;
    private int amount;
    private ItemMeta meta;
    private ItemStack item;
    private boolean changed;

    public ItemBuilder(ItemStack item)
    {
        this.type = item.getType();
        this.amount = item.getAmount();
        this.meta = item.getItemMeta();
        this.item = null;
        this.changed = true;
    }

    public ItemBuilder(Material material)
    {
        this.type = material;
        this.amount = 1;
        this.meta = Bukkit.getItemFactory().getItemMeta(material);
        this.item = null;
        this.changed = true;
    }

    public ItemBuilder()
    {
        this.type = Material.STONE;
        this.amount = 1;
        this.meta = Bukkit.getItemFactory().getItemMeta(Material.STONE);
        this.item = null;
        this.changed = true;
    }

    @Override
    public ItemStack get()
    {
        if(changed || item == null)
        {
            this.item = new ItemStack(type, amount);
            this.item.setItemMeta(meta);
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
        return meta;
    }

    @Override
    public ItemBuilder setMeta(ItemMeta meta)
    {
        this.meta = meta;
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
        this.meta.setDisplayName(name);
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
        return null;
    }

    @Override
    public ItemBuilder setType(Material material)
    {
        return null;
    }

    @Override
    public List<String> getLore()
    {
        return null;
    }

    @Override
    public ItemBuilder setLore(List<String> lore)
    {
        return null;
    }

    @Override
    public Map<Enchantment, Integer> getEnchants()
    {
        return null;
    }

    @Override
    public boolean hasEnchant(Enchantment enchantment)
    {
        return false;
    }

    @Override
    public int getEnchant(Enchantment enchantment)
    {
        return 0;
    }

    @Override
    public ItemBuilder removeEnchant(Enchantment enchantment)
    {
        return null;
    }

    @Override
    public ItemBuilder addEnchant(Enchantment enchantment, int level)
    {
        return null;
    }

    @Override
    public ItemBuilder addItemFlags(ItemFlag... flags)
    {
        return null;
    }

    @Override
    public ItemBuilder removeItemFlags(ItemFlag... flags)
    {
        return null;
    }

    @Override
    public ItemBuilder addGlow()
    {
        return null;
    }

    @Override
    public ItemBuilder removeGlow()
    {
        return null;
    }
}
