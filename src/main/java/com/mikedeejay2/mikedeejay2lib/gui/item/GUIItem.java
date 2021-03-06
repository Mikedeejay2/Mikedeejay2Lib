package com.mikedeejay2.mikedeejay2lib.gui.item;

import com.mikedeejay2.mikedeejay2lib.PluginBase;
import com.mikedeejay2.mikedeejay2lib.gui.GUIContainer;
import com.mikedeejay2.mikedeejay2lib.gui.event.GUIEvent;
import com.mikedeejay2.mikedeejay2lib.gui.event.GUIEventHandler;
import com.mikedeejay2.mikedeejay2lib.util.chat.Chat;
import com.mikedeejay2.mikedeejay2lib.util.enchant.GlowEnchantment;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemFlag;
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
     * @param gui   The GUI that was clicked on
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
     * @return A reference to this <tt>GUIItem</tt>
     */
    public GUIItem setItem(ItemStack item)
    {
        this.baseItem = item;
        this.viewItem = item;
        return this;
    }

    /**
     * Set the <tt>ItemStack</tt> for the base item of this <tt>GUIItem</tt>
     *
     * @param baseItem Item that will be set to the base item
     * @return A reference to this <tt>GUIItem</tt>
     */
    public GUIItem setBaseItem(ItemStack baseItem)
    {
        this.baseItem = baseItem;
        return this;
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
     * @return A reference to this <tt>GUIItem</tt>
     */
    public GUIItem setViewItem(ItemStack viewItem)
    {
        this.viewItem = viewItem;
        return this;
    }

    /**
     * Reset the view item to the base item
     */
    public GUIItem resetViewItem()
    {
        this.viewItem = baseItem;
        return this;
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
     * @return A reference to this <tt>GUIItem</tt>
     */
    public GUIItem setMovable(boolean movable)
    {
        this.movable = movable;
        return this;
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
     * Get a <tt>GUIEvent</tt> based off of the event's class
     *
     * @param eventClass The class of the <tt>GUIEvent</tt> to get
     * @param <T>        The class type
     * @return The requested <tt>GUIEvent</tt>
     */
    public <T extends GUIEvent> T getEvent(Class<T> eventClass)
    {
        return events.getEvent(eventClass);
    }

    /**
     * Get a <tt>GUIEvent</tt> based off of the index of the event
     *
     * @param index The index that the <tt>GUIEvent</tt> is located at in the list
     * @return The requested event
     */
    public GUIEvent getEvent(int index)
    {
        return events.getEvent(index);
    }

    /**
     * Set the <tt>GUIEventHandler</tt> for this item
     *
     * @param events Events to set this item to use
     * @return A reference to this <tt>GUIItem</tt>
     */
    public GUIItem setEvents(GUIEventHandler events)
    {
        this.events = events;
        return this;
    }

    /**
     * Add an event to this <tt>GUIItem</tt>
     *
     * @param event Event to add
     * @return A reference to this <tt>GUIItem</tt>
     */
    public GUIItem addEvent(GUIEvent event)
    {
        events.addEvent(event);
        return this;
    }

    /**
     * Remove an event via instance
     *
     * @param event Event to remove
     * @return A reference to this <tt>GUIItem</tt>
     */
    public GUIItem removeEvent(GUIEvent event)
    {
        events.removeEvent(event);
        return this;
    }

    /**
     * Remove an event via the event's class
     *
     * @param eventClass The class of the event to remove
     * @return A reference to this <tt>GUIItem</tt>
     */
    public GUIItem removeEvent(Class<? extends GUIEvent> eventClass)
    {
        events.removeEvent(eventClass);
        return this;
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
     *
     * @return A reference to this <tt>GUIItem</tt>
     */
    public GUIItem resetEvents()
    {
        this.events = new GUIEventHandler();
        return this;
    }

    public ItemMeta getMetaView()
    {
        return viewItem.getItemMeta();
    }

    public GUIItem setMetaView(ItemMeta meta)
    {
        viewItem.setItemMeta(meta);
        return this;
    }

    public ItemMeta getMetaBase()
    {
        return baseItem.getItemMeta();
    }

    public GUIItem setMetaBase(ItemMeta meta)
    {
        baseItem.setItemMeta(meta);
        return this;
    }

    public ItemMeta getMeta()
    {
        return getMetaView();
    }

    public GUIItem setMeta(ItemMeta meta)
    {
        setMetaView(meta);
        setMetaBase(meta);
        return this;
    }

    public String getNameView()
    {
        return getMetaView().getDisplayName();
    }

    public GUIItem setNameView(String name)
    {
        ItemMeta meta = getMetaView();
        meta.setDisplayName(Chat.chat(name));
        setMetaView(meta);
        return this;
    }

    public String getNameBase()
    {
        return baseItem.getItemMeta().getDisplayName();
    }

    public GUIItem setNameBase(String name)
    {
        ItemMeta meta = getMetaBase();
        meta.setDisplayName(Chat.chat(name));
        setMetaBase(meta);
        return this;
    }

    public String getName()
    {
        return getNameView();
    }

    public GUIItem setName(String name)
    {
        setNameView(name);
        setNameBase(name);
        return this;
    }

    public int getAmountView()
    {
        return viewItem.getAmount();
    }

    public GUIItem setAmountView(int amount)
    {
        viewItem.setAmount(amount);
        return this;
    }

    public int getAmountBase()
    {
        return baseItem.getAmount();
    }

    public GUIItem setAmountBase(int amount)
    {
        baseItem.setAmount(amount);
        return this;
    }

    public int getAmount()
    {
        return getAmountView();
    }

    public GUIItem setAmount(int amount)
    {
        setAmountView(amount);
        setAmountBase(amount);
        return this;
    }

    public Material getMatView()
    {
        return viewItem.getType();
    }

    public GUIItem setMatView(Material material)
    {
        viewItem.setType(material);
        return this;
    }

    public Material getMatBase()
    {
        return baseItem.getType();
    }

    public GUIItem setMatBase(Material material)
    {
        baseItem.setType(material);
        return this;
    }

    public Material getMat()
    {
        return getMatView();
    }

    public GUIItem setMat(Material material)
    {
        setMatView(material);
        setMatBase(material);
        return this;
    }

    public List<String> getLoreView()
    {
        return getMetaView().getLore();
    }

    public GUIItem setLoreView(List<String> lore)
    {
        ItemMeta meta = getMetaView();
        meta.setLore(lore);
        setMetaView(meta);
        return this;
    }

    public List<String> getLoreBase()
    {
        return getMetaBase().getLore();
    }

    public GUIItem setLoreBase(List<String> lore)
    {
        ItemMeta meta = getMetaBase();
        meta.setLore(lore);
        setMetaBase(meta);
        return this;
    }

    public List<String> getLore()
    {
        return getLoreView();
    }

    public GUIItem setLore(List<String> lore)
    {
        setLoreView(lore);
        setLoreBase(lore);
        return this;
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

    public GUIItem removeEnchantView(Enchantment enchantment)
    {
        ItemMeta meta = getMetaView();
        meta.removeEnchant(enchantment);
        setMetaView(meta);
        return this;
    }

    public GUIItem removeEnchantBase(Enchantment enchantment)
    {
        ItemMeta meta = getMetaBase();
        meta.removeEnchant(enchantment);
        setMetaBase(meta);
        return this;
    }

    public GUIItem removeEnchant(Enchantment enchantment)
    {
        removeEnchantView(enchantment);
        removeEnchantBase(enchantment);
        return this;
    }

    public GUIItem addEnchantView(Enchantment enchantment, int level)
    {
        ItemMeta meta = getMetaView();
        meta.addEnchant(enchantment, level, true);
        setMetaView(meta);
        return this;
    }

    public GUIItem addEnchantBase(Enchantment enchantment, int level)
    {
        ItemMeta meta = getMetaBase();
        meta.addEnchant(enchantment, level, true);
        setMetaBase(meta);
        return this;
    }

    public GUIItem addEnchant(Enchantment enchantment, int level)
    {
        addEnchantView(enchantment, level);
        addEnchantBase(enchantment, level);
        return this;
    }

    public GUIItem addItemFlagsBase(ItemFlag... flags)
    {
        ItemMeta meta = getMetaBase();
        meta.addItemFlags(flags);
        setMetaBase(meta);
        return this;
    }

    public GUIItem addItemFlagsView(ItemFlag... flags)
    {
        ItemMeta meta = getMetaView();
        meta.addItemFlags(flags);
        setMetaView(meta);
        return this;
    }

    public GUIItem addItemFlags(ItemFlag... flags)
    {
        addItemFlagsBase(flags);
        addItemFlagsView(flags);
        return this;
    }

    public GUIItem removeItemFlagsBase(ItemFlag... flags)
    {
        ItemMeta meta = getMetaBase();
        meta.removeItemFlags(flags);
        setMetaBase(meta);
        return this;
    }

    public GUIItem removeItemFlagsView(ItemFlag... flags)
    {
        ItemMeta meta = getMetaView();
        meta.removeItemFlags(flags);
        setMetaView(meta);
        return this;
    }

    public GUIItem removeItemFlags(ItemFlag... flags)
    {
        removeItemFlagsBase(flags);
        removeItemFlagsView(flags);
        return this;
    }

    public GUIItem addGlowBase()
    {
        addEnchantBase(new GlowEnchantment(), 0);
        return this;
    }

    public GUIItem removeGlowBase()
    {
        removeEnchantBase(new GlowEnchantment());
        return this;
    }

    public GUIItem addGlowView()
    {
        addEnchantView(new GlowEnchantment(), 0);
        return this;
    }

    public GUIItem removeGlowView()
    {
        removeEnchantView(new GlowEnchantment());
        return this;
    }

    public GUIItem addGlow()
    {
        addEnchant(new GlowEnchantment(), 0);
        return this;
    }

    public GUIItem removeGlow()
    {
        removeEnchant(new GlowEnchantment());
        return this;
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
            return null;
        }
        if(baseItem != null)
        {
            newItem.setBaseItem(this.getItemBase().clone());
        }
        if(viewItem != null)
        {
            newItem.setViewItem(this.getItemView().clone());
        }

        if(events != null)
        {
            newItem.setEvents(this.getEvents().clone());
        }
        return newItem;
    }
}
