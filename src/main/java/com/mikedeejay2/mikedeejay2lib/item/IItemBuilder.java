package com.mikedeejay2.mikedeejay2lib.item;

import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;
import java.util.Map;

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
}
