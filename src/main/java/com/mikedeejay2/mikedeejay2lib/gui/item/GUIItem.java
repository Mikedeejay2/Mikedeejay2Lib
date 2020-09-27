package com.mikedeejay2.mikedeejay2lib.gui.item;

import com.mikedeejay2.mikedeejay2lib.gui.event.GUIEvent;
import com.mikedeejay2.mikedeejay2lib.gui.event.GUISlotEvent;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

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

    public ItemStack getBaseItem()
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

    public ItemStack getViewItem()
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

    public String getName()
    {
        return viewItem.getItemMeta().getDisplayName();
    }

    public ItemMeta getItemMeta()
    {
        return viewItem.getItemMeta();
    }

    public int getAmount()
    {
        return viewItem.getAmount();
    }

    public Material getType()
    {
        return viewItem.getType();
    }

    public List<String> getLore()
    {
        return viewItem.getItemMeta().getLore();
    }
}
