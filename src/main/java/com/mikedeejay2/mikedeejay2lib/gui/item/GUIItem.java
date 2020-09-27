package com.mikedeejay2.mikedeejay2lib.gui.item;

import com.mikedeejay2.mikedeejay2lib.gui.event.GUIEvent;
import com.mikedeejay2.mikedeejay2lib.gui.event.GUISlotEvent;
import com.mikedeejay2.mikedeejay2lib.util.chat.Chat;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;
import java.util.Map;

public class GUIItem
{
    protected ItemStack baseItem;
    protected ItemStack viewItem;
    protected boolean movable;
    protected GUISlotEvent events;

    public GUIItem(ItemStack item)
    {
        this.baseItem = item;
        this.viewItem = item;
        this.movable = false;
        this.events = new GUISlotEvent();
    }

    public ItemStack getItemBase()
    {
        return baseItem;
    }

    public ItemStack getItem()
    {
        return viewItem;
    }

    public void setItem(ItemStack item)
    {
        this.baseItem = item;
        this.viewItem = item;
    }

    public void setBaseItem(ItemStack baseItem)
    {
        this.baseItem = baseItem;
    }

    public ItemStack getItemView()
    {
        return viewItem;
    }

    public void setViewItem(ItemStack viewItem)
    {
        this.viewItem = viewItem;
    }

    public void resetViewItem()
    {
        this.viewItem = baseItem;
    }

    public boolean isMovable()
    {
        return movable;
    }

    public void setMovable(boolean movable)
    {
        this.movable = movable;
    }

    public GUISlotEvent getEvents()
    {
        return events;
    }

    public void setEvents(GUISlotEvent events)
    {
        this.events = events;
    }

    public void addEvent(GUIEvent event)
    {
        events.addEvent(event);
    }

    public void removeEvent(GUIEvent event)
    {
        events.removeEvent(event);
    }

    public boolean containsEvent(GUIEvent event)
    {
        return events.containsEvent(event);
    }

    public void resetEvents()
    {
        this.events = new GUISlotEvent();
    }

    public ItemMeta getMetaView()
    {
        return viewItem.getItemMeta();
    }

    public void setMetaView(ItemMeta meta)
    {
        viewItem.setItemMeta(meta);
    }

    public ItemMeta getMetaBase()
    {
        return baseItem.getItemMeta();
    }

    public void setMetaBase(ItemMeta meta)
    {
        baseItem.setItemMeta(meta);
    }

    public String getNameView()
    {
        return getMetaView().getDisplayName();
    }

    public void setNameView(String name)
    {
        ItemMeta meta = getMetaView();
        meta.setDisplayName(Chat.chat(name));
        setMetaView(meta);
    }

    public String getNameBase()
    {
        return baseItem.getItemMeta().getDisplayName();
    }

    public void setNameBase(String name)
    {
        ItemMeta meta = getMetaBase();
        meta.setDisplayName(Chat.chat(name));
        setMetaBase(meta);
    }

    public int getAmountView()
    {
        return viewItem.getAmount();
    }

    public void setAmountView(int amount)
    {
        viewItem.setAmount(amount);
    }

    public int getAmountBase()
    {
        return baseItem.getAmount();
    }

    public void setAmountBase(int amount)
    {
        baseItem.setAmount(amount);
    }

    public Material getMatView()
    {
        return viewItem.getType();
    }

    public void setMatView(Material material)
    {
        viewItem.setType(material);
    }

    public Material getMatBase()
    {
        return baseItem.getType();
    }

    public void setMatBase(Material material)
    {
        baseItem.setType(material);
    }

    public List<String> getLoreView()
    {
        return getMetaView().getLore();
    }

    public void setLoreView(List<String> lore)
    {
        ItemMeta meta = getMetaView();
        meta.setLore(lore);
        setMetaView(meta);
    }

    public List<String> getLoreBase()
    {
        return getMetaBase().getLore();
    }

    public void setLoreBase(List<String> lore)
    {
        ItemMeta meta = getMetaBase();
        meta.setLore(lore);
        setMetaBase(meta);
    }

    public Map<Enchantment, Integer> getEnchantsView()
    {
        return viewItem.getEnchantments();
    }

    public Map<Enchantment, Integer> getEnchantsBase()
    {
        return baseItem.getEnchantments();
    }

    public boolean hasEnchantView(Enchantment enchantment)
    {
        return getMetaView().hasEnchant(enchantment);
    }

    public boolean hasEnchantBase(Enchantment enchantment)
    {
        return getMetaBase().hasEnchant(enchantment);
    }

    public int getEnchantView(Enchantment enchantment)
    {
        return getMetaView().getEnchantLevel(enchantment);
    }

    public int getEnchantBase(Enchantment enchantment)
    {
        return getMetaBase().getEnchantLevel(enchantment);
    }

    public void removeEnchantView(Enchantment enchantment)
    {
        ItemMeta meta = getMetaView();
        meta.removeEnchant(enchantment);
        setMetaView(meta);
    }

    public void removeEnchantBase(Enchantment enchantment)
    {
        ItemMeta meta = getMetaBase();
        meta.removeEnchant(enchantment);
        setMetaBase(meta);
    }

    public void addEnchantView(Enchantment enchantment, int level)
    {
        ItemMeta meta = getMetaView();
        meta.addEnchant(enchantment, level, true);
        setMetaView(meta);
    }

    public void addEnchantBase(Enchantment enchantment, int level)
    {
        ItemMeta meta = getMetaBase();
        meta.addEnchant(enchantment, level, true);
        setMetaBase(meta);
    }
}
