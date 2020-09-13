package com.mikedeejay2.mikedeejay2lib.util.item;

import com.mikedeejay2.mikedeejay2lib.util.chat.Chat;
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

// Item Creator creates items
// It should not be instantiated, therefore it's abstract.
public class ItemCreator
{
    /**
     * Create an item based off of several arguments.
     *
     * @param material Material of item
     * @param amount Amount of item
     * @param displayName The display name of the item
     * @param loreString Any lore that the item might have
     * @return The new ItemStack
     */
    public static ItemStack createItem(Material material, int amount, String displayName, String... loreString)
    {
        ItemStack item;

        List<String> lore = new ArrayList();

        item = new ItemStack(material, amount);

        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(Chat.chat(displayName));

        for(String s : loreString)
        {
            lore.add(Chat.chat(s));
        }

        meta.setLore(lore);
        item.setItemMeta(meta);

        return item;
    }

    /**
     * Create a Base64 head based off of several arguments.
     *
     * @param base64Head The base 64 string of the head's texture
     * @param amount Amount of item
     * @param displayName The display name of the head
     * @param loreString Any lore that the head might have
     * @return The new head ItemStack
     */
    public static ItemStack createHeadItem(String base64Head, int amount, String displayName, String... loreString)
    {
        ItemStack item;

        List<String> lore = new ArrayList();

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
            lore.add(Chat.chat(s));
        }

        item.setItemMeta(skullMeta);

        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(Chat.chat(displayName));

        meta.setLore(lore);
        item.setItemMeta(meta);

        return item;
    }
}
