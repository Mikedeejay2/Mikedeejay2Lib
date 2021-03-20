package com.mikedeejay2.mikedeejay2lib.util.item;

import com.mikedeejay2.mikedeejay2lib.item.ItemBuilder;
import com.mikedeejay2.mikedeejay2lib.util.chat.Colors;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * A util class for creating items.
 * <p>
 * This is not recommended for use and is deprecated. Instead of using <tt>ItemCreator</tt>,
 * please instead use {@link ItemBuilder}, as it's much more flexible and thought out.
 * <p>
 * This is only being kept here for legacy plugins or if for some reason this class is preferred
 * over <tt>ItemBuilder</tt>
 *
 * @deprecated Use {@link ItemBuilder} instead, see note above.
 * @see ItemBuilder
 *
 * @author Mikedeejay2
 */
@Deprecated
public final class ItemCreator
{
    /**
     * Create an item based off of several arguments
     *
     * @param material    Material of item
     * @param amount      Amount of item
     * @param displayName The display name of the item
     * @param loreString  Any lore that the item might have
     * @return The new ItemStack
     */
    @Deprecated
    public static ItemStack createItem(Material material, int amount, String displayName, String... loreString)
    {
        ItemStack item;
        List<String> lore = new ArrayList<>();
        item = new ItemStack(material, amount);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(Colors.format(displayName));
        for(String s : loreString)
        {
            lore.add(Colors.format(s));
        }
        meta.setLore(lore);
        item.setItemMeta(meta);
        return item;
    }

    /**
     * Create a Base64 head based off of several arguments
     *
     * @param base64Head  The base 64 string of the head's texture
     * @param amount      Amount of item
     * @param displayName The display name of the head
     * @param loreString  Any lore that the head might have
     * @return The new head ItemStack
     */
    @Deprecated
    public static ItemStack createHeadItem(String base64Head, int amount, String displayName, String... loreString)
    {
        ItemStack item;
        List<String> lore = new ArrayList<>();
        item = new ItemStack(Material.PLAYER_HEAD, amount);
        SkullMeta skullMeta = (SkullMeta)item.getItemMeta();
        GameProfile profile = new GameProfile(UUID.randomUUID(), "");
        profile.getProperties().put("textures", new Property("textures", base64Head));
        Field profileField = null;
        try
        {
            profileField = skullMeta.getClass().getDeclaredField("profile");
            profileField.setAccessible(true);
            profileField.set(skullMeta, profile);
        }
        catch(IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e)
        {
            e.printStackTrace();
        }
        for(String s : loreString)
        {
            lore.add(Colors.format(s));
        }
        item.setItemMeta(skullMeta);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(Colors.format(displayName));
        meta.setLore(lore);
        item.setItemMeta(meta);
        return item;
    }
}
