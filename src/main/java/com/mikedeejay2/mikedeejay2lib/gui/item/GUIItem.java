package com.mikedeejay2.mikedeejay2lib.gui.item;

import com.mikedeejay2.mikedeejay2lib.gui.GUIContainer;
import com.mikedeejay2.mikedeejay2lib.gui.event.GUIEvent;
import com.mikedeejay2.mikedeejay2lib.gui.event.GUIEventHandler;
import com.mikedeejay2.mikedeejay2lib.util.chat.Chat;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;
import java.util.Map;

/**
 * Represents an item in a GUI. Holds a few useful things such as:
 * <ul>
 *     <li>A base item</li>
 *     <li>A view item</li>
 *     <li>Whether or not it's movable in the GUI or not</li>
 *     <li>GUI Events</li>
 * </ul>
 *
 * @author Mikedeejay2
 */
public class GUIItem implements Cloneable
{
    // The base item
    protected ItemStack baseItem;
    // The view item
    protected ItemStack viewItem;
    // Whether or not this item can be moved
    protected boolean movable;
    // GUI Events for this item
    protected GUIEventHandler events;

    public GUIItem(ItemStack item)
    {
        this.baseItem = item;
        this.viewItem = item;
        this.movable = false;
        this.events = new GUIEventHandler();
    }

    /**
     * Called when this <tt>GUIItem</tt> is clicked
     *
     * @param event The event of the click
     * @param gui The GUI that was clicked on
     */
    public void onClick(InventoryClickEvent event, GUIContainer gui)
    {
        if(events == null) return;
        events.execute(event, gui);
    }

    /**
     * Get the base <tt>ItemStack</tt> of this <tt>GUIItem</tt>
     *
     * @return The base item
     */
    public ItemStack getItemBase()
    {
        return baseItem;
    }

    /**
     * Get the <tt>ItemStack</tt> of this <tt>GUIItem</tt> (view item)
     *
     * @return The requested <tt>ItemStack</tt>
     */
    public ItemStack getItem()
    {
        return viewItem;
    }

    /**
     * Set the <tt>ItemStack</tt> that this <tt>GUIItem</tt> stores (modifies
     * both the base item and the view item)
     *
     * @param item The item to set
     */
    public void setItem(ItemStack item)
    {
        this.baseItem = item;
        this.viewItem = item;
    }

    /**
     * Set the <tt>ItemStack</tt> for the base item of this <tt>GUIItem</tt>
     *
     * @param baseItem Item that will be set to the base item
     */
    public void setBaseItem(ItemStack baseItem)
    {
        this.baseItem = baseItem;
    }

    /**
     * Get the view item of this <tt>GUIItem</tt>
     *
     * @return The view item
     */
    public ItemStack getItemView()
    {
        return viewItem;
    }

    /**
     * Set the <tt>ItemStack</tt> for the view item of this <tt>GUIItem</tt>
     *
     * @param viewItem Item that will be set to the view item
     */
    public void setViewItem(ItemStack viewItem)
    {
        this.viewItem = viewItem;
    }

    /**
     * Reset the view item to the base item
     */
    public void resetViewItem()
    {
        this.viewItem = baseItem;
    }

    /**
     * Returns whether this <tt>GUIItem</tt> is movable or not
     *
     * @return Move state
     */
    public boolean isMovable()
    {
        return movable;
    }

    /**
     * Set whether this <tt>GUIItem</tt> is movable or not
     *
     * @param movable Move state to set this item to
     */
    public void setMovable(boolean movable)
    {
        this.movable = movable;
    }

    /**
     * Get the <tt>GUIEventHandler</tt> of this item
     *
     * @return the item event of this item
     */
    public GUIEventHandler getEvents()
    {
        return events;
    }

    /**
     * Set the <tt>GUIEventHandler</tt> for this item
     *
     * @param events Events to set this item to use
     */
    public void setEvents(GUIEventHandler events)
    {
        this.events = events;
    }

    /**
     * Add an event to this <tt>GUIItem</tt>
     *
     * @param event Event to add
     */
    public void addEvent(GUIEvent event)
    {
        events.addEvent(event);
    }

    /**
     * Remove an event via instance
     *
     * @param event Event to remove
     */
    public void removeEvent(GUIEvent event)
    {
        events.removeEvent(event);
    }

    /**
     * Remove an event via the event's class
     *
     * @param eventClass The class of the event to remove
     */
    public void removeEvent(Class<? extends GUIEvent> eventClass)
    {
        events.removeEvent(eventClass);
    }

    /**
     * Returns whether the event instance exists in this <tt>GUIEvent</tt>
     *
     * @param event The event to search for
     * @return Whether the event exists in this item
     */
    public boolean containsEvent(GUIEvent event)
    {
        return events.containsEvent(event);
    }

    /**
     * Returns whether an event of a class exists in this <tt>GUIEvent</tt>
     *
     * @param eventClass The event class to search for
     * @return Whether the event exists in this item
     */
    public boolean containsEvent(Class<? extends GUIEvent> eventClass)
    {
        return events.containsEvent(eventClass);
    }

    /**
     * Reset the GUI Events for this item
     */
    public void resetEvents()
    {
        this.events = new GUIEventHandler();
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

    public ItemMeta getMeta()
    {
        return getMetaView();
    }

    public void setMeta(ItemMeta meta)
    {
        setMetaView(meta);
        setMetaBase(meta);
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

    public String getName()
    {
        return getNameView();
    }

    public void setName(String name)
    {
        setNameView(name);
        setNameBase(name);
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

    public int getAmount()
    {
        return getAmountView();
    }

    public void setAmount(int amount)
    {
        setAmountView(amount);
        setAmountBase(amount);
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

    public Material getMat()
    {
        return getMatView();
    }

    public void setMat(Material material)
    {
        setMatView(material);
        setMatBase(material);
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

    public List<String> getLore()
    {
        return getLoreView();
    }

    public void setLore(List<String> lore)
    {
        setLoreView(lore);
        setLoreBase(lore);
    }

    public Map<Enchantment, Integer> getEnchantsView()
    {
        return viewItem.getEnchantments();
    }

    public Map<Enchantment, Integer> getEnchantsBase()
    {
        return baseItem.getEnchantments();
    }

    public Map<Enchantment, Integer> getEnchants()
    {
        return getEnchantsView();
    }

    public boolean hasEnchantView(Enchantment enchantment)
    {
        return getMetaView().hasEnchant(enchantment);
    }

    public boolean hasEnchantBase(Enchantment enchantment)
    {
        return getMetaBase().hasEnchant(enchantment);
    }

    public boolean hasEnchant(Enchantment enchantment)
    {
        return hasEnchantView(enchantment);
    }

    public int getEnchantView(Enchantment enchantment)
    {
        return getMetaView().getEnchantLevel(enchantment);
    }

    public int getEnchantBase(Enchantment enchantment)
    {
        return getMetaBase().getEnchantLevel(enchantment);
    }

    public int getEnchant(Enchantment enchantment)
    {
        return getEnchantView(enchantment);
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

    public void removeEnchant(Enchantment enchantment)
    {
        removeEnchantView(enchantment);
        removeEnchantBase(enchantment);
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

    public void addEnchant(Enchantment enchantment, int level)
    {
        addEnchantView(enchantment, level);
        addEnchantBase(enchantment, level);
    }

    public GUIItem clone()
    {
        GUIItem newItem = null;

        try
        {
            newItem = (GUIItem) super.clone();
        }
        catch(CloneNotSupportedException e)
        {
            e.printStackTrace();
        }
        newItem.setBaseItem(newItem.getItemBase().clone());
        newItem.setViewItem(newItem.getItemView().clone());

        newItem.setEvents(newItem.getEvents().clone());
        return newItem;
    }
}
