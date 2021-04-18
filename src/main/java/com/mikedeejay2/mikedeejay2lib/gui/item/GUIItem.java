package com.mikedeejay2.mikedeejay2lib.gui.item;

import com.google.common.collect.Multimap;
import com.mikedeejay2.mikedeejay2lib.BukkitPlugin;
import com.mikedeejay2.mikedeejay2lib.gui.GUIContainer;
import com.mikedeejay2.mikedeejay2lib.gui.event.GUIEvent;
import com.mikedeejay2.mikedeejay2lib.gui.event.GUIEventHandler;
import com.mikedeejay2.mikedeejay2lib.item.IItemBuilder;
import com.mikedeejay2.mikedeejay2lib.item.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.OfflinePlayer;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.*;

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
        this.viewItem = baseItem.clone();
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
    public boolean hasConflictingEnchant(Enchantment enchantment)
    {
        return hasConflictingEnchantView(enchantment);
    }

    public boolean hasConflictingEnchantBase(Enchantment enchantment)
    {
        return baseItem.hasConflictingEnchant(enchantment);
    }

    public boolean hasConflictingEnchantView(Enchantment enchantment)
    {
        return viewItem.hasConflictingEnchant(enchantment);
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
    public boolean hasItemFlag(ItemFlag flag)
    {
        return hasItemFlagView(flag);
    }

    public boolean hasItemFlagBase(ItemFlag flag)
    {
        return baseItem.hasItemFlag(flag);
    }

    public boolean hasItemFlagView(ItemFlag flag)
    {
        return viewItem.hasItemFlag(flag);
    }

    @Override
    public Set<ItemFlag> getItemFlags()
    {
        return getItemFlagsView();
    }

    public Set<ItemFlag> getItemFlagsBase()
    {
        return baseItem.getItemFlags();
    }

    public Set<ItemFlag> getItemFlagsView()
    {
        return viewItem.getItemFlags();
    }

    @Override
    public GUIItem addItemFlag(ItemFlag flag)
    {
        return addItemFlagView(flag);
    }

    public GUIItem addItemFlagBase(ItemFlag flag)
    {
        baseItem.addItemFlag(flag);
        return this;
    }

    public GUIItem addItemFlagView(ItemFlag flag)
    {
        viewItem.addItemFlag(flag);
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

    @Override
    public boolean hasAttributeModifiers()
    {
        return hasAttributeModifiersView();
    }

    public boolean hasAttributeModifiersBase()
    {
        return baseItem.hasAttributeModifiers();
    }

    public boolean hasAttributeModifiersView()
    {
        return viewItem.hasAttributeModifiers();
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers()
    {
        return getAttributeModifiersView();
    }

    public Multimap<Attribute, AttributeModifier> getAttributeModifiersBase()
    {
        return baseItem.getAttributeModifiers();
    }

    public Multimap<Attribute, AttributeModifier> getAttributeModifiersView()
    {
        return viewItem.getAttributeModifiers();
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot)
    {
        return getAttributeModifiersView(slot);
    }

    public Multimap<Attribute, AttributeModifier> getAttributeModifiersBase(EquipmentSlot slot)
    {
        return baseItem.getAttributeModifiers(slot);
    }

    public Multimap<Attribute, AttributeModifier> getAttributeModifiersView(EquipmentSlot slot)
    {
        return viewItem.getAttributeModifiers(slot);
    }

    @Override
    public Collection<AttributeModifier> getAttributeModifiers(Attribute attribute)
    {
        return getAttributeModifiersView(attribute);
    }

    public Collection<AttributeModifier> getAttributeModifiersBase(Attribute attribute)
    {
        return baseItem.getAttributeModifiers(attribute);
    }

    public Collection<AttributeModifier> getAttributeModifiersView(Attribute attribute)
    {
        return viewItem.getAttributeModifiers(attribute);
    }

    @Override
    public GUIItem addAttributeModifier(Attribute attribute, AttributeModifier modifier)
    {
        addAttributeModifierBase(attribute, modifier);
        addAttributeModifierView(attribute, modifier);
        return this;
    }

    public GUIItem addAttributeModifierBase(Attribute attribute, AttributeModifier modifier)
    {
        baseItem.addAttributeModifier(attribute, modifier);
        return this;
    }

    public GUIItem addAttributeModifierView(Attribute attribute, AttributeModifier modifier)
    {
        viewItem.addAttributeModifier(attribute, modifier);
        return this;
    }

    @Override
    public GUIItem addAttributeModifiers(Attribute attribute, AttributeModifier... modifiers)
    {
        addAttributeModifiersBase(attribute, modifiers);
        addAttributeModifiersView(attribute, modifiers);
        return this;
    }

    public GUIItem addAttributeModifiersBase(Attribute attribute, AttributeModifier... modifiers)
    {
        baseItem.addAttributeModifiers(attribute, modifiers);
        return this;
    }

    public GUIItem addAttributeModifiersView(Attribute attribute, AttributeModifier... modifiers)
    {
        viewItem.addAttributeModifiers(attribute, modifiers);
        return this;
    }

    @Override
    public GUIItem setAttributeModifiers(Multimap<Attribute, AttributeModifier> attributeModifiers)
    {
        setAttributeModifiersBase(attributeModifiers);
        setAttributeModifiersView(attributeModifiers);
        return this;
    }

    public GUIItem setAttributeModifiersBase(Multimap<Attribute, AttributeModifier> attributeModifiers)
    {
        baseItem.setAttributeModifiers(attributeModifiers);
        return this;
    }

    public GUIItem setAttributeModifiersView(Multimap<Attribute, AttributeModifier> attributeModifiers)
    {
        viewItem.setAttributeModifiers(attributeModifiers);
        return this;
    }

    @Override
    public GUIItem removeAttributeModifier(Attribute attribute)
    {
        removeAttributeModifierBase(attribute);
        removeAttributeModifierView(attribute);
        return this;
    }

    public GUIItem removeAttributeModifierBase(Attribute attribute)
    {
        baseItem.removeAttributeModifier(attribute);
        return this;
    }

    public GUIItem removeAttributeModifierView(Attribute attribute)
    {
        viewItem.removeAttributeModifier(attribute);
        return this;
    }

    @Override
    public GUIItem removeAttributeModifiers(Attribute... attributes)
    {
        removeAttributeModifiersBase(attributes);
        removeAttributeModifiersView(attributes);
        return this;
    }

    public GUIItem removeAttributeModifiersBase(Attribute... attributes)
    {
        baseItem.removeAttributeModifiers(attributes);
        return this;
    }

    public GUIItem removeAttributeModifiersView(Attribute... attributes)
    {
        viewItem.removeAttributeModifiers(attributes);
        return this;
    }

    @Override
    public GUIItem removeAttributeModifier(EquipmentSlot slot)
    {
        removeAttributeModifierBase(slot);
        removeAttributeModifierView(slot);
        return this;
    }

    public GUIItem removeAttributeModifierBase(EquipmentSlot slot)
    {
        baseItem.removeAttributeModifiers(slot);
        return this;
    }

    public GUIItem removeAttributeModifierView(EquipmentSlot slot)
    {
        viewItem.removeAttributeModifiers(slot);
        return this;
    }

    @Override
    public GUIItem removeAttributeModifiers(EquipmentSlot... slots)
    {
        removeAttributeModifiersBase(slots);
        removeAttributeModifiersView(slots);
        return this;
    }

    public GUIItem removeAttributeModifiersBase(EquipmentSlot... slots)
    {
        baseItem.removeAttributeModifiers(slots);
        return this;
    }

    public GUIItem removeAttributeModifiersView(EquipmentSlot... slots)
    {
        viewItem.removeAttributeModifiers(slots);
        return this;
    }

    @Override
    public GUIItem removeAttributeModifier(Attribute attribute, AttributeModifier modifier)
    {
        removeAttributeModifierBase(attribute, modifier);
        removeAttributeModifierView(attribute, modifier);
        return this;
    }

    public GUIItem removeAttributeModifierBase(Attribute attribute, AttributeModifier modifier)
    {
        baseItem.removeAttributeModifier(attribute, modifier);
        return this;
    }

    public GUIItem removeAttributeModifierView(Attribute attribute, AttributeModifier modifier)
    {
        viewItem.removeAttributeModifier(attribute, modifier);
        return this;
    }

    @Override
    public GUIItem removeAttributeModifiers(Attribute attribute, AttributeModifier... modifiers)
    {
        removeAttributeModifiersBase(attribute, modifiers);
        removeAttributeModifiersView(attribute, modifiers);
        return this;
    }

    public GUIItem removeAttributeModifiersBase(Attribute attribute, AttributeModifier... modifiers)
    {
        baseItem.removeAttributeModifiers(attribute, modifiers);
        return this;
    }

    public GUIItem removeAttributeModifiersView(Attribute attribute, AttributeModifier... modifiers)
    {
        viewItem.removeAttributeModifiers(attribute, modifiers);
        return this;
    }

    @Override
    public PersistentDataContainer getPersistentDataContainer()
    {
        return getPersistentDataContainerView();
    }

    public PersistentDataContainer getPersistentDataContainerBase()
    {
        return baseItem.getPersistentDataContainer();
    }

    public PersistentDataContainer getPersistentDataContainerView()
    {
        return viewItem.getPersistentDataContainer();
    }

    @Override
    public GUIItem setCustomModelData(Integer data)
    {
        setCustomModelDataBase(data);
        setCustomModelDataView(data);
        return this;
    }

    public GUIItem setCustomModelDataBase(Integer data)
    {
        baseItem.setCustomModelData(data);
        return this;
    }

    public GUIItem setCustomModelDataView(Integer data)
    {
        viewItem.setCustomModelData(data);
        return this;
    }

    @Override
    public int getCustomModelData()
    {
        return getCustomModelDataView();
    }

    public int getCustomModelDataBase()
    {
        return baseItem.getCustomModelData();
    }

    public int getCustomModelDataView()
    {
        return viewItem.getCustomModelData();
    }

    @Override
    public boolean hasCustomModelData()
    {
        return hasCustomModelDataView();
    }

    public boolean hasCustomModelDataBase()
    {
        return baseItem.hasCustomModelData();
    }

    public boolean hasCustomModelDataView()
    {
        return viewItem.hasCustomModelData();
    }

    @Override
    public GUIItem setLocalizedName(String name)
    {
        setLocalizedNameBase(name);
        setLocalizedNameView(name);
        return this;
    }

    public GUIItem setLocalizedNameBase(String name)
    {
        baseItem.setLocalizedName(name);
        return this;
    }

    public GUIItem setLocalizedNameView(String name)
    {
        viewItem.setLocalizedName(name);
        return this;
    }

    @Override
    public String getLocalizedName()
    {
        return getLocalizedNameView();
    }

    public String getLocalizedNameBase()
    {
        return baseItem.getLocalizedName();
    }

    public String getLocalizedNameView()
    {
        return viewItem.getLocalizedName();
    }

    @Override
    public boolean hasLocalizedName()
    {
        return hasLocalizedNameView();
    }

    public boolean hasLocalizedNameBase()
    {
        return baseItem.hasLocalizedName();
    }

    public boolean hasLocalizedNameView()
    {
        return viewItem.hasLocalizedName();
    }

    @Override
    public String getDisplayName()
    {
        return getDisplayNameView();
    }

    public String getDisplayNameBase()
    {
        return baseItem.getDisplayName();
    }

    public String getDisplayNameView()
    {
        return viewItem.getDisplayName();
    }

    @Override
    public GUIItem setDisplayName(String name)
    {
        setDisplayNameBase(name);
        setDisplayNameView(name);
        return this;
    }

    public GUIItem setDisplayNameBase(String name)
    {
        baseItem.setDisplayName(name);
        return this;
    }

    public GUIItem setDisplayNameView(String name)
    {
        viewItem.setDisplayName(name);
        return this;
    }

    @Override
    public boolean hasDisplayName()
    {
        return hasDisplayNameView();
    }

    public boolean hasDisplayNameBase()
    {
        return baseItem.hasDisplayName();
    }

    public boolean hasDisplayNameView()
    {
        return viewItem.hasDisplayName();
    }

    @Override
    public int getDurability()
    {
        return getDurabilityView();
    }

    public int getDurabilityBase()
    {
        return baseItem.getDurability();
    }

    public int getDurabilityView()
    {
        return viewItem.getDurability();
    }

    @Override
    public GUIItem setDurability(int durability)
    {
        setDurabilityBase(durability);
        setDurabilityView(durability);
        return this;
    }

    public GUIItem setDurabilityBase(int durability)
    {
        baseItem.setDurability(durability);
        return this;
    }

    public GUIItem setDurabilityView(int durability)
    {
        viewItem.setDurability(durability);
        return this;
    }

    @Override
    public int getMaxStackSize()
    {
        return getMaxStackSizeView();
    }

    public int getMaxStackSizeBase()
    {
        return baseItem.getMaxStackSize();
    }

    public int getMaxStackSizeView()
    {
        return viewItem.getMaxStackSize();
    }

    @Override
    public boolean hasDurability()
    {
        return hasDurabilityView();
    }

    public boolean hasDurabilityBase()
    {
        return baseItem.hasDurability();
    }

    public boolean hasDurabilityView()
    {
        return viewItem.hasDurability();
    }

    @Override
    public <Y, Z> GUIItem setData(NamespacedKey key, PersistentDataType<Y, Z> type, Z value)
    {
        setDataBase(key, type, value);
        setDataView(key, type, value);
        return this;
    }

    public <Y, Z> GUIItem setDataBase(NamespacedKey key, PersistentDataType<Y, Z> type, Z value)
    {
        baseItem.setData(key, type, value);
        return this;
    }

    public <Y, Z> GUIItem setDataView(NamespacedKey key, PersistentDataType<Y, Z> type, Z value)
    {
        viewItem.setData(key, type, value);
        return this;
    }

    @Override
    public <Y, Z> GUIItem setData(BukkitPlugin plugin, String key, PersistentDataType<Y, Z> type, Z value)
    {
        setDataBase(plugin, key, type, value);
        setDataView(plugin, key, type, value);
        return this;
    }

    public <Y, Z> GUIItem setDataBase(BukkitPlugin plugin, String key, PersistentDataType<Y, Z> type, Z value)
    {
        baseItem.setData(plugin, key, type, value);
        return this;
    }

    public <Y, Z> GUIItem setDataView(BukkitPlugin plugin, String key, PersistentDataType<Y, Z> type, Z value)
    {
        viewItem.setData(plugin, key, type, value);
        return this;
    }

    @Override
    public <Y, Z> boolean hasData(NamespacedKey key, PersistentDataType<Y, Z> type)
    {
        return hasDataView(key, type);
    }

    public <Y, Z> boolean hasDataBase(NamespacedKey key, PersistentDataType<Y, Z> type)
    {
        return baseItem.hasData(key, type);
    }

    public <Y, Z> boolean hasDataView(NamespacedKey key, PersistentDataType<Y, Z> type)
    {
        return viewItem.hasData(key, type);
    }

    @Override
    public <Y, Z> boolean hasData(BukkitPlugin plugin, String key, PersistentDataType<Y, Z> type)
    {
        return hasDataView(plugin, key, type);
    }

    public <Y, Z> boolean hasDataBase(BukkitPlugin plugin, String key, PersistentDataType<Y, Z> type)
    {
        return baseItem.hasData(plugin, key, type);
    }

    public <Y, Z> boolean hasDataView(BukkitPlugin plugin, String key, PersistentDataType<Y, Z> type)
    {
        return viewItem.hasData(plugin, key, type);
    }

    @Override
    public <Y, Z> Z getData(NamespacedKey key, PersistentDataType<Y, Z> type)
    {
        return getDataView(key, type);
    }

    public <Y, Z> Z getDataBase(NamespacedKey key, PersistentDataType<Y, Z> type)
    {
        return baseItem.getData(key, type);
    }

    public <Y, Z> Z getDataView(NamespacedKey key, PersistentDataType<Y, Z> type)
    {
        return viewItem.getData(key, type);
    }

    @Override
    public <Y, Z> Z getData(BukkitPlugin plugin, String key, PersistentDataType<Y, Z> type)
    {
        return getDataView(plugin, key, type);
    }

    public <Y, Z> Z getDataBase(BukkitPlugin plugin, String key, PersistentDataType<Y, Z> type)
    {
        return baseItem.getData(plugin, key, type);
    }

    public <Y, Z> Z getDataView(BukkitPlugin plugin, String key, PersistentDataType<Y, Z> type)
    {
        return viewItem.getData(plugin, key, type);
    }

    @Override
    public <Y, Z> Z getOrDefaultData(NamespacedKey key, PersistentDataType<Y, Z> type, Z defaultValue)
    {
        return getOrDefaultDataView(key, type, defaultValue);
    }

    public <Y, Z> Z getOrDefaultDataBase(NamespacedKey key, PersistentDataType<Y, Z> type, Z defaultValue)
    {
        return baseItem.getOrDefaultData(key, type, defaultValue);
    }

    public <Y, Z> Z getOrDefaultDataView(NamespacedKey key, PersistentDataType<Y, Z> type, Z defaultValue)
    {
        return viewItem.getOrDefaultData(key, type, defaultValue);
    }

    @Override
    public <Y, Z> Z getOrDefaultData(BukkitPlugin plugin, String key, PersistentDataType<Y, Z> type, Z defaultValue)
    {
        return null;
    }

    public <Y, Z> Z getOrDefaultDataBase(BukkitPlugin plugin, String key, PersistentDataType<Y, Z> type, Z defaultValue)
    {
        return baseItem.getOrDefaultData(plugin, key, type, defaultValue);
    }

    public <Y, Z> Z getOrDefaultDataView(BukkitPlugin plugin, String key, PersistentDataType<Y, Z> type, Z defaultValue)
    {
        return viewItem.getOrDefaultData(plugin, key, type, defaultValue);
    }

    @Override
    public GUIItem removeData(NamespacedKey key)
    {
        removeDataBase(key);
        removeDataView(key);
        return this;
    }

    public GUIItem removeDataBase(NamespacedKey key)
    {
        baseItem.removeData(key);
        return this;
    }

    public GUIItem removeDataView(NamespacedKey key)
    {
        viewItem.removeData(key);
        return this;
    }

    @Override
    public GUIItem removeData(BukkitPlugin plugin, String key)
    {
        removeDataBase(plugin, key);
        removeDataView(plugin, key);
        return this;
    }

    public GUIItem removeDataBase(BukkitPlugin plugin, String key)
    {
        baseItem.removeData(plugin, key);
        return this;
    }

    public GUIItem removeDataView(BukkitPlugin plugin, String key)
    {
        viewItem.removeData(plugin, key);
        return this;
    }

    @Override
    public GUIItem removeData(NamespacedKey... keys)
    {
        removeDataBase(keys);
        removeDataView(keys);
        return this;
    }

    public GUIItem removeDataBase(NamespacedKey... keys)
    {
        baseItem.removeData(keys);
        return this;
    }

    public GUIItem removeDataView(NamespacedKey... keys)
    {
        viewItem.removeData(keys);
        return this;
    }

    @Override
    public GUIItem removeData(BukkitPlugin plugin, String... keys)
    {
        removeDataBase(plugin, keys);
        removeDataView(plugin, keys);
        return this;
    }

    public GUIItem removeDataBase(BukkitPlugin plugin, String... keys)
    {
        baseItem.removeData(plugin, keys);
        return this;
    }

    public GUIItem removeDataView(BukkitPlugin plugin, String... keys)
    {
        viewItem.removeData(plugin, keys);
        return this;
    }

    @Override
    public boolean isDataEmpty()
    {
        return isDataEmptyView();
    }

    public boolean isDataEmptyBase()
    {
        return baseItem.isDataEmpty();
    }

    public boolean isDataEmptyView()
    {
        return viewItem.isDataEmpty();
    }


    @Override
    public GUIItem clone()
    {
        GUIItem newItem;

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
            newItem.baseItem = baseItem.clone();
        }
        if(viewItem != null)
        {
            newItem.viewItem = viewItem.clone();
        }

        if(events != null)
        {
            newItem.events = events.clone();
        }
        return newItem;
    }
}
