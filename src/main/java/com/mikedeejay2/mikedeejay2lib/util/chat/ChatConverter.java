package com.mikedeejay2.mikedeejay2lib.util.chat;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Item;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Utility class for converting and creating Bungee API chat objects.
 *
 * @author Mikedeejay2
 */
public final class ChatConverter
{
    /**
     * Converts strings into Bungee API <code>BaseComponent</code> arrays
     *
     * @param strings The strings that will be converted to base components
     * @return <code>BaseComponent</code> array of converted strings
     */
    public static BaseComponent[] getBaseComponentArray(String... strings)
    {
        ArrayList<BaseComponent> baseComponents = new ArrayList<>();
        for(String str : strings)
        {
            baseComponents.addAll(Arrays.asList(TextComponent.fromLegacyText(Colors.format(str))));
        }
        return baseComponents.toArray(new BaseComponent[0]);
    }

    /**
     * Creates a Bungee API <code>ClickEvent</code> to do something with a command
     *
     * @param action  The <code>ClickEvent</code> Action that should happen on click
     * @param command The command to be used on the <code>ClickEvent</code>
     * @return A new <code>ClickEvent</code> that can be used with <code>BaseComponents</code>
     */
    public static ClickEvent getClickEvent(ClickEvent.Action action, String command)
    {
        return new ClickEvent(action, command);
    }

    /**
     * Creates a Bungee API <code>HoverEvent</code> to do something with a command
     *
     * @param action The <code>HoverEvent</code> Action to be used when cursor is hovered over applied text
     * @param text   The string of text that will be used in the hover event
     * @return The <code>HoverEvent</code> that was created
     */
    public static HoverEvent getHoverEvent(HoverEvent.Action action, String text)
    {
        return new HoverEvent(action, getBaseComponentArray(text));
    }

    /**
     * Creates a Bungee API <code>HoverEvent</code> to do something with a command
     *
     * @param action The <code>HoverEvent</code> Action to be used when cursor is hovered over applied text
     * @param item   The item that will be displayed
     * @return The <code>HoverEvent</code> that was created
     */
    public static HoverEvent getHoverEvent(HoverEvent.Action action, ItemStack item)
    {
        return new HoverEvent(action, getHoverItem(item));
    }

    /**
     * Creates a Hover Item from an <code>ItemStack</code>
     *
     * @param item Item to be converted
     * @return HoverEvent Item
     */
    public static Item getHoverItem(ItemStack item)
    {
        int itemAmount = item.getAmount();
        String itemId = item.getType().toString().toLowerCase();
        return new Item(itemId, itemAmount, null);
    }

    /**
     * Applies a <code>ClickEvent</code> to an array of <code>BaseComponents</code>
     *
     * @param components An array of <code>BaseComponents</code> that will have a <code>ClickEvent</code> applied to them
     * @param event      The <code>ClickEvent</code> to be added to the components
     * @return The same <code>BaseComponents</code> array but with the click events applied
     */
    public static BaseComponent[] setClickEvent(BaseComponent[] components, ClickEvent event)
    {
        for(BaseComponent component : components)
        {
            component.setClickEvent(event);
        }
        return components;
    }

    /**
     * Applies a <code>HoverEvent</code> to an array of <code>BaseComponents</code>
     *
     * @param components An array of <code>BaseComponents</code> that will have a <code>HoverEvent</code> applied to them
     * @param event      The <code>HoverEvent</code> to be added to the components
     * @return The same <code>BaseComponents</code> array but with the hover events applied
     */
    public static BaseComponent[] setHoverEvent(BaseComponent[] components, HoverEvent event)
    {
        for(BaseComponent component : components)
        {
            component.setHoverEvent(event);
        }
        return components;
    }

    /**
     * Combines multiple <code>BaseComponent</code> arrays and combines them into 1 larger array
     *
     * @param components An array of <code>BaseComponents</code> arrays that will be combined into one base components array
     * @return A combined array of all <code>BaseComponents</code>
     */
    public static BaseComponent[] combineComponents(BaseComponent[]... components)
    {
        ArrayList<BaseComponent> componentsArrayList = new ArrayList<BaseComponent>();
        for(BaseComponent[] componentsArr : components)
        {
            componentsArrayList.addAll(Arrays.asList(componentsArr));
        }
        return componentsArrayList.toArray(new BaseComponent[0]);
    }

    /**
     * Print all <code>BaseComponents</code> to a CommandSender
     *
     * @param sender     The CommandSender that will receive the message
     * @param components An Array of BaseComponents arrays that will be printed, Each BaseComponent array being 1 line
     */
    public static void printComponents(CommandSender sender, BaseComponent[]... components)
    {
        sender.spigot().sendMessage(combineComponents(components));
    }

    /**
     * Print all <code>BaseComponents</code> to a Player
     *
     * @param player     The Player that will receive the message
     * @param components An Array of BaseComponents arrays that will be printed, Each BaseComponent array being 1 line
     */
    public static void printComponents(Player player, BaseComponent[]... components)
    {
        player.spigot().sendMessage(combineComponents(components));
    }

    /**
     * Print all <code>BaseComponents</code> to a CommandSender
     *
     * @param sender     The CommandSender that will receive the message
     * @param type       The ChatMessageType to display the components at
     * @param components An Array of BaseComponents arrays that will be printed, Each BaseComponent array being 1 line
     */
    public static void printComponents(CommandSender sender, ChatMessageType type, BaseComponent[]... components)
    {
        if(sender instanceof Player)
        {
            ((Player)sender).spigot().sendMessage(type, combineComponents(components));
        }
        else
        {
            sender.spigot().sendMessage(combineComponents(components));
        }
    }

    /**
     * Print all <code>BaseComponents</code> to a Player
     *
     * @param player     The Player that will receive the message
     * @param type       The ChatMessageType to display the components at
     * @param components An Array of BaseComponents arrays that will be printed, Each BaseComponent array being 1 line
     */
    public static void printComponents(Player player, ChatMessageType type, BaseComponent[]... components)
    {
        player.spigot().sendMessage(type, combineComponents(components));
    }
}
