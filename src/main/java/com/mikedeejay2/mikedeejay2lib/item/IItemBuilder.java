package com.mikedeejay2.mikedeejay2lib.item;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;
import java.util.Map;

public interface IItemBuilder<I, T>
{
    I get();
    T set(I item);

    ItemMeta getMeta();
    T setMeta(ItemMeta meta);

    String getName();
    T setName(String name);

    int getAmount();
    T setAmount(int amount);

    Material getType();
    T setType(Material material);

    List<String> getLore();
    T setLore(List<String> lore);

    Map<Enchantment, Integer> getEnchants();
    boolean hasEnchant(Enchantment enchantment);
    int getEnchant(Enchantment enchantment);
    T removeEnchant(Enchantment enchantment);
    T addEnchant(Enchantment enchantment, int level);

    T addItemFlags(ItemFlag... flags);
    T removeItemFlags(ItemFlag... flags);

    T addGlow();
    T removeGlow();
}
