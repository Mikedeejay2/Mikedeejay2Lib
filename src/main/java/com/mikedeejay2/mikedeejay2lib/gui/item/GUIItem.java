package com.mikedeejay2.mikedeejay2lib.gui.item;

import com.mikedeejay2.mikedeejay2lib.gui.GUIContainer;
import com.mikedeejay2.mikedeejay2lib.gui.event.GUIEvent;
import com.mikedeejay2.mikedeejay2lib.gui.event.GUIEventHandler;
import com.mikedeejay2.mikedeejay2lib.item.IItemBuilder;
import com.mikedeejay2.mikedeejay2lib.item.ItemBuilder;
import com.mikedeejay2.mikedeejay2lib.util.chat.Colors;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
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
public class GUIItem implements Cloneable, IItemBuilder<ItemStack, GUIItem>
{
    // The base item builder
    protected ItemBuilder baseItem;
    // The view item builder
    protected ItemBuilder viewItem;
    // Whether or not this item can be moved
    protected boolean movable;
    // GUI Events for this item
    protected GUIEventHandler events;

    public GUIItem(ItemStack item)
    {
        this.baseItem = ItemBuilder.of(item);
        this.viewItem = ItemBuilder.of(item);
        this.movable = false;
        this.events = new GUIEventHandler();
    }

    public GUIItem()
    {
        this(new ItemStack(Material.STONE));
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

    /**
     * Reset the view item to the base item
     *
     * @return A reference to this <tt>GUIItem</tt>
     */
    public GUIItem resetViewItem()
    {
        this.viewItem = ItemBuilder.of(baseItem);
        return this;
    }

    @Override
    public ItemStack get()
    {
        return getItem();
    }

    /**
     * Get the <tt>ItemStack</tt> of this <tt>GUIItem</tt> (view item)
     *
     * @return The requested <tt>ItemStack</tt>
     */
    public ItemStack getItem()
    {
        return viewItem.get();
    }

    /**
     * Get the base <tt>ItemStack</tt> of this <tt>GUIItem</tt>
     *
     * @return The base item
     */
    public ItemStack getItemBase()
    {
        return baseItem.get();
    }

    /**
     * Get the view item of this <tt>GUIItem</tt>
     *
     * @return The view item
     */
    public ItemStack getItemView()
    {
        return viewItem.get();
    }

    @Override
    public GUIItem set(ItemStack item)
    {
        return setItem(item);
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
        setBaseItem(item);
        setViewItem(item);
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
        this.baseItem.set(baseItem);
        return this;
    }

    /**
     * Set the <tt>ItemStack</tt> for the view item of this <tt>GUIItem</tt>
     *
     * @param viewItem Item that will be set to the view item
     * @return A reference to this <tt>GUIItem</tt>
     */
    public GUIItem setViewItem(ItemStack viewItem)
    {
        this.viewItem.set(viewItem);
        return this;
    }

    @Override
    public ItemMeta getMeta()
    {
        return getMetaView();
    }

    public ItemMeta getMetaBase()
    {
        return baseItem.getMeta();
    }

    public ItemMeta getMetaView()
    {
        return viewItem.getMeta();
    }

    @Override
    public GUIItem setMeta(ItemMeta meta)
    {
        setMetaBase(meta);
        setMetaView(meta);
        return this;
    }

    public GUIItem setMetaBase(ItemMeta meta)
    {
        this.baseItem.setMeta(meta);
        return this;
    }

    public GUIItem setMetaView(ItemMeta meta)
    {
        this.viewItem.setMeta(meta);
        return this;
    }

    @Override
    public String getName()
    {
        return getNameView();
    }

    public String getNameBase()
    {
        return baseItem.getName();
    }

    public String getNameView()
    {
        return viewItem.getName();
    }

    @Override
    public GUIItem setName(String name)
    {
        setNameBase(name);
        setNameView(name);
        return this;
    }

    public GUIItem setNameBase(String name)
    {
        baseItem.setName(name);
        return this;
    }

    public GUIItem setNameView(String name)
    {
        viewItem.setName(name);
        return this;
    }

    @Override
    public int getAmount()
    {
        return getAmountView();
    }

    public int getAmountBase()
    {
        return baseItem.getAmount();
    }

    public int getAmountView()
    {
        return viewItem.getAmount();
    }

    @Override
    public GUIItem setAmount(int amount)
    {
        setAmountBase(amount);
        setAmountView(amount);
        return this;
    }

    public GUIItem setAmountBase(int amount)
    {
        baseItem.setAmount(amount);
        return this;
    }

    public GUIItem setAmountView(int amount)
    {
        viewItem.setAmount(amount);
        return this;
    }

    @Override
    public Material getType()
    {
        return getTypeBase();
    }

    public Material getTypeBase()
    {
        return baseItem.getType();
    }

    public Material getTypeView()
    {
        return viewItem.getType();
    }

    @Override
    public GUIItem setType(Material material)
    {
        setTypeBase(material);
        setTypeView(material);
        return this;
    }

    public GUIItem setTypeBase(Material material)
    {
        baseItem.setType(material);
        return this;
    }

    public GUIItem setTypeView(Material material)
    {
        viewItem.setType(material);
        return this;
    }

    @Override
    public List<String> getLore()
    {
        return getLoreView();
    }

    public List<String> getLoreBase()
    {
        return baseItem.getLore();
    }

    public List<String> getLoreView()
    {
        return viewItem.getLore();
    }

    @Override
    public GUIItem setLore(List<String> lore)
    {
        setLoreBase(lore);
        setLoreView(lore);
        return this;
    }

    public GUIItem setLoreBase(List<String> lore)
    {
        baseItem.setLore(lore);
        return this;
    }

    public GUIItem setLoreView(List<String> lore)
    {
        viewItem.setLore(lore);
        return this;
    }

    @Override
    public GUIItem setLore(String... lore)
    {
        setLoreBase(lore);
        setLoreView(lore);
        return this;
    }

    public GUIItem setLoreBase(String... lore)
    {
        baseItem.setLore(lore);
        return this;
    }

    public GUIItem setLoreView(String... lore)
    {
        viewItem.setLore(lore);
        return this;
    }

    @Override
    public GUIItem addLore(List<String> lore)
    {
        addLoreBase(lore);
        addLoreView(lore);
        return this;
    }

    public GUIItem addLoreBase(List<String> lore)
    {
        baseItem.addLore(lore);
        return this;
    }

    public GUIItem addLoreView(List<String> lore)
    {
        viewItem.addLore(lore);
        return this;
    }

    @Override
    public GUIItem addLore(String... lore)
    {
        addLoreBase(lore);
        addLoreView(lore);
        return this;
    }

    public GUIItem addLoreBase(String... lore)
    {
        baseItem.addLore(lore);
        return this;
    }

    public GUIItem addLoreView(String... lore)
    {
        viewItem.addLore(lore);
        return this;
    }

    @Override
    public GUIItem addLore(int index, List<String> lore)
    {
        addLoreBase(index, lore);
        addLoreView(index, lore);
        return this;
    }

    public GUIItem addLoreBase(int index, List<String> lore)
    {
        baseItem.addLore(index, lore);
        return this;
    }

    public GUIItem addLoreView(int index, List<String> lore)
    {
        viewItem.addLore(index, lore);
        return this;
    }

    @Override
    public GUIItem addLore(int index, String... lore)
    {
        addLoreBase(index, lore);
        addLoreView(index, lore);
        return this;
    }

    public GUIItem addLoreBase(int index, String... lore)
    {
        baseItem.addLore(index, lore);
        return this;
    }

    public GUIItem addLoreView(int index, String... lore)
    {
        viewItem.addLore(index, lore);
        return this;
    }

    @Override
    public Map<Enchantment, Integer> getEnchants()
    {
        return getEnchantsView();
    }

    public Map<Enchantment, Integer> getEnchantsBase()
    {
        return baseItem.getEnchants();
    }

    public Map<Enchantment, Integer> getEnchantsView()
    {
        return viewItem.getEnchants();
    }

    @Override
    public boolean hasEnchant(Enchantment enchantment)
    {
        return hasEnchantBase(enchantment);
    }

    public boolean hasEnchantBase(Enchantment enchantment)
    {
        return baseItem.hasEnchant(enchantment);
    }

    public boolean hasEnchantView(Enchantment enchantment)
    {
        return viewItem.hasEnchant(enchantment);
    }

    @Override
    public int getEnchant(Enchantment enchantment)
    {
        return getEnchantView(enchantment);
    }

    public int getEnchantBase(Enchantment enchantment)
    {
        return baseItem.getEnchant(enchantment);
    }

    public int getEnchantView(Enchantment enchantment)
    {
        return viewItem.getEnchant(enchantment);
    }

    @Override
    public GUIItem removeEnchant(Enchantment enchantment)
    {
        removeEnchantBase(enchantment);
        removeEnchantView(enchantment);
        return this;
    }

    public GUIItem removeEnchantBase(Enchantment enchantment)
    {
        baseItem.removeEnchant(enchantment);
        return this;
    }

    public GUIItem removeEnchantView(Enchantment enchantment)
    {
        viewItem.removeEnchant(enchantment);
        return this;
    }

    @Override
    public GUIItem addEnchant(Enchantment enchantment, int level)
    {
        addEnchantBase(enchantment, level);
        addEnchantView(enchantment, level);
        return this;
    }

    public GUIItem addEnchantBase(Enchantment enchantment, int level)
    {
        baseItem.addEnchant(enchantment, level);
        return this;
    }

    public GUIItem addEnchantView(Enchantment enchantment, int level)
    {
        viewItem.addEnchant(enchantment, level);
        return this;
    }

    @Override
    public GUIItem addItemFlags(ItemFlag... flags)
    {
        addItemFlagsBase(flags);
        addItemFlagsView(flags);
        return this;
    }

    public GUIItem addItemFlagsBase(ItemFlag... flags)
    {
        baseItem.addItemFlags(flags);
        return this;
    }

    public GUIItem addItemFlagsView(ItemFlag... flags)
    {
        viewItem.addItemFlags(flags);
        return this;
    }

    @Override
    public GUIItem removeItemFlags(ItemFlag... flags)
    {
        removeItemFlagsBase(flags);
        removeItemFlagsView(flags);
        return this;
    }

    public GUIItem removeItemFlagsBase(ItemFlag... flags)
    {
        baseItem.removeItemFlags(flags);
        return this;
    }

    public GUIItem removeItemFlagsView(ItemFlag... flags)
    {
        viewItem.removeItemFlags(flags);
        return this;
    }

    @Override
    public GUIItem addGlow()
    {
        addGlowBase();
        addGlowView();
        return this;
    }

    public GUIItem addGlowBase()
    {
        baseItem.addGlow();
        return this;
    }

    public GUIItem addGlowView()
    {
        viewItem.addGlow();
        return this;
    }

    @Override
    public GUIItem removeGlow()
    {
        removeGlowBase();
        removeGlowView();
        return this;
    }

    public GUIItem removeGlowBase()
    {
        baseItem.removeGlow();
        return this;
    }

    public GUIItem removeGlowView()
    {
        viewItem.removeGlow();
        return this;
    }

    @Override
    public GUIItem setEmptyName()
    {
        setEmptyNameBase();
        setEmptyNameView();
        return this;
    }

    public GUIItem setEmptyNameBase()
    {
        baseItem.setEmptyName();
        return this;
    }

    public GUIItem setEmptyNameView()
    {
        viewItem.setEmptyName();
        return this;
    }

    @Override
    public GUIItem setUnbreakable(boolean unbreakable)
    {
        setUnbreakableBase(unbreakable);
        setUnbreakableView(unbreakable);
        return this;
    }

    public GUIItem setUnbreakableBase(boolean unbreakable)
    {
        baseItem.setUnbreakable(unbreakable);
        return this;
    }

    public GUIItem setUnbreakableView(boolean unbreakable)
    {
        viewItem.setUnbreakable(unbreakable);
        return this;
    }

    @Override
    public boolean isUnbreakable()
    {
        return isUnbreakableView();
    }

    public boolean isUnbreakableBase()
    {
        return baseItem.isUnbreakable();
    }

    public boolean isUnbreakableView()
    {
        return viewItem.isUnbreakable();
    }

    @Override
    public OfflinePlayer getHeadOwner()
    {
        return getHeadOwnerView();
    }

    public OfflinePlayer getHeadOwnerBase()
    {
        return baseItem.getHeadOwner();
    }

    public OfflinePlayer getHeadOwnerView()
    {
        return viewItem.getHeadOwner();
    }

    @Override
    public GUIItem setHeadOwner(OfflinePlayer player)
    {
        setHeadOwnerBase(player);
        setHeadOwnerView(player);
        return this;
    }

    public GUIItem setHeadOwnerBase(OfflinePlayer player)
    {
        baseItem.setHeadOwner(player);
        return this;
    }

    public GUIItem setHeadOwnerView(OfflinePlayer player)
    {
        viewItem.setHeadOwner(player);
        return this;
    }

    @Override
    public String getHeadBase64()
    {
        return getHeadBase64View();
    }

    public String getHeadBase64Base()
    {
        return baseItem.getHeadBase64();
    }

    public String getHeadBase64View()
    {
        return viewItem.getHeadBase64();
    }

    @Override
    public GUIItem setHeadBase64(String base64)
    {
        setHeadBase64Base(base64);
        setHeadBase64View(base64);
        return this;
    }

    public GUIItem setHeadBase64Base(String base64)
    {
        baseItem.setHeadBase64(base64);
        return this;
    }

    public GUIItem setHeadBase64View(String base64)
    {
        viewItem.setHeadBase64(base64);
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
            newItem.baseItem = ItemBuilder.of(baseItem);
        }
        if(viewItem != null)
        {
            newItem.viewItem = ItemBuilder.of(viewItem);
        }

        if(events != null)
        {
            newItem.events = events.clone();
        }
        return newItem;
    }
}
