package com.mikedeejay2.mikedeejay2lib.item;

import com.google.common.collect.Multimap;
import com.mikedeejay2.mikedeejay2lib.BukkitPlugin;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.OfflinePlayer;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Interface of an item builder, specifies parameters that should be used for building items.
 *
 * @param <I> The default item return type, the rational thing to put here is <tt>ItemStack</tt>
 * @param <T> The subclass used to return this in set methods
 *
 * @author Mikedeejay2
 */
public interface IItemBuilder<I, T>
{
    /**
     * Get the item
     *
     * @return The item of type <tt>I</tt>
     */
    I get();

    /**
     * Set the item to a new item
     *
     * @param item The new item of type I
     * @return A reference to this object
     */
    T set(I item);

    /**
     * Get the <tt>ItemMeta</tt>
     *
     * @return The <tt>ItemMeta</tt> of the item
     */
    ItemMeta getMeta();

    /**
     * Set the new <tt>ItemMeta</tt>
     *
     * @param meta The new <tt>ItemMeta</tt>
     * @return A reference to this object
     */
    T setMeta(ItemMeta meta);

    /**
     * Get the name of the item
     *
     * @return The name of the item
     */
    String getName();

    /**
     * Set the new name of the item
     *
     * @param name The new name of the item
     * @return A reference to this object
     */
    T setName(String name);

    /**
     * Get the stack amount of the item
     *
     * @return The stack amount of the item
     */
    int getAmount();

    /**
     * Set the new stack amount of the item
     *
     * @param amount The new stack amount of the item
     * @return A reference to this object
     */
    T setAmount(int amount);

    /**
     * Get the material type of the item
     *
     * @return The material type of the item
     */
    Material getType();

    /**
     * Set the new material type of the item
     *
     * @param material The new material type of the item
     * @return A reference to this object
     */
    T setType(Material material);

    /**
     * Get the String list of lore of the item
     *
     * @return The String list of lore of the item
     */
    List<String> getLore();

    /**
     * Set the new String list of lore of the item
     *
     * @param lore The new String list of lore of the item
     * @return A reference to this object
     */
    T setLore(List<String> lore);

    /**
     * Set the new String array of lore of the item
     *
     * @param lore The new String array of lore of the item
     * @return A reference to this object
     */
    T setLore(String... lore);

    /**
     * Add new String list of lore of the item
     *
     * @param lore The new String list to add to the item
     * @return A reference to this object
     */
    T addLore(List<String> lore);

    /**
     * Add new String array of lore of the item
     *
     * @param lore The new String array to add to the item
     * @return A reference to this object
     */
    T addLore(String... lore);

    /**
     * Add new String list of lore of the item
     *
     * @param lore The new String list to add to the item
     * @param index The index to add the lore at
     * @return A reference to this object
     */
    T addLore(int index, List<String> lore);

    /**
     * Add new String array of lore of the item
     *
     * @param lore The new String array to add to the item
     * @param index The index to add the lore at
     * @return A reference to this object
     */
    T addLore(int index, String... lore);

    /**
     * Get the map of enchantments of the item
     *
     * @return The map of enchantments of the item
     */
    Map<Enchantment, Integer> getEnchants();

    /**
     * Get whether the item has an enchantment applied to it
     *
     * @param enchantment The enchantment to check for
     * @return True if the item has the enchantment, false if it doesn't
     */
    boolean hasEnchant(Enchantment enchantment);

    /**
     * Get the level of an enchantment applied to the item
     *
     * @param enchantment The enchantment to get the level for
     * @return The level of the specified enchantment of the item
     */
    int getEnchant(Enchantment enchantment);

    /**
     * Remove an enchantment from the item
     *
     * @param enchantment The enchantment to remove from the item
     * @return A reference to this object
     */
    T removeEnchant(Enchantment enchantment);

    /**
     * Add an enchantment to the item
     *
     * @param enchantment The enchantment to add to the item
     * @param level The level of enchantment to add to the item
     * @return A reference to this object
     */
    T addEnchant(Enchantment enchantment, int level);

    boolean hasConflictingEnchant(Enchantment enchantment);

    /**
     * Add item flags to the item
     *
     * @param flags The item flags to add to the item
     * @return A reference to this object
     */
    T addItemFlags(ItemFlag... flags);

    /**
     * Remove item flags from the item
     *
     * @param flags The item flags to remove from the item
     * @return A reference to this object
     */
    T removeItemFlags(ItemFlag... flags);

    /**
     * Get whether the item has a specified item flag or not
     * 
     * @param flag The item flag to test for
     * @return Whether the item has the item flag or not
     */
    boolean hasItemFlag(ItemFlag flag);

    /**
     * Get a <tt>Set</tt> of all of the item flags for the item
     *
     * @return The <tt>ItemFlags</tt> set
     */
    Set<ItemFlag> getItemFlags();

    /**
     * Add a single <tt>ItemFlag</tt> to the item
     *
     * @param flag The <tt>ItemFlag</tt> to add to the item
     * @return A reference to this object
     */
    T addItemFlag(ItemFlag flag);

    /**
     * Add an enchant glow effect to the item
     *
     * @return A reference to this object
     */
    T addGlow();

    /**
     * Remove an enchant glow effect from the item
     *
     * @return A reference to this object
     */
    T removeGlow();

    /**
     * Set a completely empty name to the item. This name is shorter than a single
     * space because it's interpreting the light gray color code as an infinitely
     * small String of text, therefore only rendering about 3 pixels of hover box.
     *
     * @return A reference to this object
     */
    T setEmptyName();

    /**
     * Set whether the item is unbreakable or not
     *
     * @param unbreakable The new unbreakable state of the item
     * @return A reference to this object
     */
    T setUnbreakable(boolean unbreakable);

    /**
     * Get whether the item is unbreakable or not
     *
     * @return Whether the item is unbreakable or not
     */
    boolean isUnbreakable();

    /**
     * Get the head owner of the head item
     * <p>
     * Note: The item must be of type <tt>PLAYER_HEAD</tt> and must have a head owner
     * set from {@link IItemBuilder#setHeadOwner(OfflinePlayer)}
     *
     * @return The current head owner of the head item
     */
    OfflinePlayer getHeadOwner();

    /**
     * Set the new head owner of the head item
     * <p>
     * Note: The item must be of type <tt>PLAYER_HEAD</tt>
     *
     * @param player The new head owner of the head item
     * @return A reference to this object
     */
    T setHeadOwner(OfflinePlayer player);

    /**
     * Get the base 64 string of the item
     * <p>
     * Note: The item must be of type <tt>PLAYER_HEAD</tt> and must have a base 64 string
     * set from {@link IItemBuilder#setHeadBase64(String)}
     *
     * @return The current base 64 String of the head item
     */
    String getHeadBase64();

    /**
     * Set the new base 64 String of the head item.
     * <p>
     * Note: The item must be of type <tt>PLAYER_HEAD</tt>
     *
     * @param base64 The new base 64 String of the head item
     * @return A reference to this object
     */
    T setHeadBase64(String base64);

    /**
     * Get whether the item has attribute modifiers or not
     *
     * @return Whether the item has attribute modifiers
     */
    boolean hasAttributeModifiers();

    /**
     * Get a <tt>MultiMap</tt> of <tt>Attribute</tt> to <tt>AttributeModifiers</tt> for the item
     *
     * @return A map of attributes for the item
     */
    Multimap<Attribute, AttributeModifier> getAttributeModifiers();

    /**
     * Get a <tt>MultiMap</tt> of <tt>Attribute</tt> to <tt>AttributeModifiers</tt> for the item based off of
     * the equipment slot
     *
     * @param slot The <tt>EquipmentSlot</tt> to get
     * @return A map of attributes for the item and equipment slot
     */
    Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot);

    /**
     * Get a <tt>Collection</tt> of <tt>AttributeModifiers</tt> for the item based off of the requested
     * <tt>Attribute</tt>
     *
     * @param attribute The <tt>Attribute</tt> to get
     * @return The collection of <tt>AttributeModifiers</tt>
     */
    Collection<AttributeModifier> getAttributeModifiers(Attribute attribute);

    /**
     * Add an <tt>Attribute</tt> to the item
     *
     * @param attribute The <tt>Attribute</tt> to add to the item
     * @param modifier The <tt>AttributeModifier</tt> to add
     * @return A reference to this object
     */
    T addAttributeModifier(Attribute attribute, AttributeModifier modifier);

    /**
     * Add multiple <tt>Attribute</tt> with multiple <tt>AttributeModifiers</tt> to the item
     *
     * @param attribute The <tt>Attribute</tt> to add to the item
     * @param modifiers The <tt>AttributeModifiers</tt> to add
     * @return A reference to this object
     */
    T addAttributeModifiers(Attribute attribute, AttributeModifier... modifiers);

    /**
     * Set a new <tt>MultiMap</tt> of <tt>Attribute</tt> <tt>AttributeModifiers</tt> to the item
     *
     * @param attributeModifiers The new map of attributes
     * @return A reference to this object
     */
    T setAttributeModifiers(Multimap<Attribute, AttributeModifier> attributeModifiers);

    /**
     * Remove an <tt>Attribute</tt> from the item
     *
     * @param attribute The <tt>Attribute</tt> to remove
     * @return A reference to this object
     */
    T removeAttributeModifier(Attribute attribute);

    /**
     * Remove multiple <tt>Attributes</tt> from the item
     *
     * @param attributes The <tt>Attribute</tt> to remove
     * @return A reference to this object
     */
    T removeAttributeModifiers(Attribute... attributes);

    /**
     * Remove attribute modifiers of the specified <tt>EquipmentSlot</tt>
     *
     * @param slot The <tt>EquipmentSlot</tt> to remove attributes from
     * @return A reference to this object
     */
    T removeAttributeModifier(EquipmentSlot slot);

    /**
     * Remove multiple attribute modifiers of the specified <tt>EquipmentSlots</tt>
     *
     * @param slots The <tt>EquipmentSlots</tt> to remove attributes from
     * @return A reference to this object
     */
    T removeAttributeModifiers(EquipmentSlot... slots);

    /**
     * Remove an <tt>Attribute's</tt> <tt>AttributeModifier</tt> from the item
     *
     * @param attribute The <tt>Attribute</tt> to remove from
     * @param modifier The <tt>AttributeModifier</tt> to remove
     * @return A reference to this object
     */
    T removeAttributeModifier(Attribute attribute, AttributeModifier modifier);

    /**
     * Remove an <tt>Attribute</tt> <tt>AttributeModifiers</tt> from the item
     *
     * @param attribute The <tt>Attribute</tt> to remove from
     * @param modifiers The <tt>AttributeModifiers</tt> to remove
     * @return A reference to this object
     */
    T removeAttributeModifiers(Attribute attribute, AttributeModifier... modifiers);

    /**
     * Get the {@link PersistentDataContainer} for the <tt>ItemMeta</tt>
     *
     * @return The <tt>PersistentDataContainer</tt>
     */
    PersistentDataContainer getPersistentDataContainer();

    /**
     * Set the custom model data integer of the item
     *
     * @param data The new custom model data
     * @return A reference to this object
     */
    T setCustomModelData(Integer data);

    /**
     * Get the custom model data of the item
     * <p>
     * <tt>hasCustomModelData()</tt> should first be checked before executing this method
     *
     * @return The custom model data for the item
     */
    int getCustomModelData();

    /**
     * Get whether the item has custom model data or not
     *
     * @return Whether the item has custom model data
     */
    boolean hasCustomModelData();

    /**
     * Set the localized name of the item
     *
     * @param name The new localized name for the item
     * @return A reference to this object
     */
    T setLocalizedName(String name);

    /**
     * Get the localized name of the item
     * <p>
     * <tt>hasLocalizedName()</tt> should first be checked before executing this method
     *
     * @return The localized name of the item
     */
    String getLocalizedName();

    /**
     * Whether the item has a localized name or not
     *
     * @return Whether the item has a localized name
     */
    boolean hasLocalizedName();

    /**
     * Get the display name of the item
     * <p>
     * <tt>hasDisplayName()</tt> should first be checked before executing this method
     *
     * @return The display name of the item
     */
    String getDisplayName();

    /**
     * Set the display name of the item
     *
     * @param name The new display name of the item
     * @return A reference to this object
     */
    T setDisplayName(String name);

    /**
     * Whether the item has a display name or not
     *
     * @return Whether the item has a display name
     */
    boolean hasDisplayName();

    /**
     * Get the durability of the item.
     * <p>
     * It must be ensured that the item has a durability with <tt>hasDurability()</tt>
     *
     * @return The durability of the item
     */
    int getDurability();

    /**
     * Set the durability of the item.
     * <p>
     * It must be ensured that the item has a durability with <tt>hasDurability()</tt>
     *
     * @param durability The new durability of the item
     * @return A reference to this object
     */
    T setDurability(int durability);

    /**
     * Get the maximum stacking size of the item
     *
     * @return The maximum stack size
     */
    int getMaxStackSize();

    /**
     * Whether the item has a durability or not
     *
     * @return Whether the item has a durability
     */
    boolean hasDurability();

    /**
     * Set data in the item's <tt>PersistentDataContainer</tt>
     *
     * @param key   The <tt>NamespacedKey</tt> to set the data in
     * @param type  The <tt>PersistentDataContainer</tt> to use
     * @param value The value to set
     * @param <Y>   The main object type stored by the tag
     * @param <Z>   The data type of the retrieved object
     * @return A reference to this object
     */
    <Y, Z> T setData(NamespacedKey key, PersistentDataType<Y, Z> type, Z value);

    /**
     * Set data in the item's <tt>PersistentDataContainer</tt>
     *
     * @param plugin The plugin's reference for namespace
     * @param key    The String key to set the data in
     * @param type   The <tt>PersistentDataContainer</tt> to use
     * @param value  The value to set
     * @param <Y>    The main object type stored by the tag
     * @param <Z>    The data type of the retrieved object
     * @return A reference to this object
     */
    <Y, Z> T setData(BukkitPlugin plugin, String key, PersistentDataType<Y, Z> type, Z value);

    /**
     * Get whether there is specific data in the item's <tt>PersistentDataContainer</tt>
     *
     * @param key  The <tt>NamespacedKey</tt> to check for
     * @param type The <tt>PersistentDataContainer</tt> to use
     * @param <Y>  The main object type stored by the tag
     * @param <Z>  The data type of the retrieved object
     * @return A reference to this object
     */
    <Y, Z> boolean hasData(NamespacedKey key, PersistentDataType<Y, Z> type);

    /**
     * Get whether there is specific data in the item's <tt>PersistentDataContainer</tt>
     *
     * @param plugin The plugin's reference for namespace
     * @param key    The String key to check for
     * @param type   The <tt>PersistentDataContainer</tt> to use
     * @param <Y>    The main object type stored by the tag
     * @param <Z>    The data type of the retrieved object
     * @return A reference to this object
     */
    <Y, Z> boolean hasData(BukkitPlugin plugin, String key, PersistentDataType<Y, Z> type);

    /**
     * Get data from the item's <tt>PersistentDataContainer</tt>
     *
     * @param key  The <tt>NamespacedKey</tt> to get the data from
     * @param type The <tt>PersistentDataContainer</tt> to use
     * @param <Y>  The main object type stored by the tag
     * @param <Z>  The data type of the retrieved object
     * @return A reference to this object
     */
    <Y, Z> Z getData(NamespacedKey key, PersistentDataType<Y, Z> type);

    /**
     * Get data from the item's <tt>PersistentDataContainer</tt>
     *
     * @param plugin The plugin's reference for namespace
     * @param key    The String key to get
     * @param type   The <tt>PersistentDataContainer</tt> to use
     * @param <Y>    The main object type stored by the tag
     * @param <Z>    The data type of the retrieved object
     * @return A reference to this object
     */
    <Y, Z> Z getData(BukkitPlugin plugin, String key, PersistentDataType<Y, Z> type);

    /**
     * Get data from the item's <tt>PersistentDataContainer</tt>. If the data does not exist, get a
     * default value instead.
     *
     * @param key          The <tt>NamespacedKey</tt> to get the data from
     * @param type         The <tt>PersistentDataContainer</tt> to use
     * @param defaultValue The default value to get if no value currently exists
     * @param <Y>          The main object type stored by the tag
     * @param <Z>          The data type of the retrieved object
     * @return A reference to this object
     */
    <Y, Z> Z getOrDefaultData(NamespacedKey key, PersistentDataType<Y, Z> type, Z defaultValue);

    /**
     * Get data from the item's <tt>PersistentDataContainer</tt>. If the data does not exist, get a
     * default value instead.
     *
     * @param plugin       The plugin's reference for namespace
     * @param key          The String key to get
     * @param type         The <tt>PersistentDataContainer</tt> to use
     * @param defaultValue The default value to get if no value currently exists
     * @param <Y>          The main object type stored by the tag
     * @param <Z>          The data type of the retrieved object
     * @return A reference to this object
     */
    <Y, Z> Z getOrDefaultData(BukkitPlugin plugin, String key, PersistentDataType<Y, Z> type, Z defaultValue);

    /**
     * Remove data from the item's <tt>PersistentDataContainer</tt>
     *
     * @param key The <tt>NamespacedKey</tt> to remove
     * @return A reference to this object
     */
    T removeData(NamespacedKey key);

    /**
     * Remove data from the item's <tt>PersistentDataContainer</tt>
     *
     * @param plugin The plugin's reference for namespace
     * @param key    The String key to remove
     * @return A reference to this object
     */
    T removeData(BukkitPlugin plugin, String key);

    /**
     * Remove data from the item's <tt>PersistentDataContainer</tt>
     *
     * @param keys The <tt>NamespacedKeys</tt> to remove
     * @return A reference to this object
     */
    T removeData(NamespacedKey... keys);

    /**
     * Remove data from the item's <tt>PersistentDataContainer</tt>
     *
     * @param plugin The plugin's reference for namespace
     * @param keys   The String keys to remove
     * @return A reference to this object
     */
    T removeData(BukkitPlugin plugin, String... keys);

    /**
     * Get whether the item's <tt>PersistentDataContainer</tt> is empty or not
     *
     * @return Whether the item's data is empty
     */
    boolean isDataEmpty();
}
