package com.mikedeejay2.mikedeejay2lib.util.chat;

import com.mikedeejay2.mikedeejay2lib.PluginBase;
import com.mikedeejay2.mikedeejay2lib.util.PluginInstancer;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.*;
import net.md_5.bungee.api.chat.hover.content.Entity;
import net.md_5.bungee.api.chat.hover.content.Item;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * A util class for anything to do with text in Minecraft
 *
 * @author Mikedeejay2
 */
public final class Chat extends PluginInstancer<PluginBase>
{
    private String pluginString;

    public Chat(PluginBase plugin)
    {
        super(plugin);
        this.pluginString = plugin.getName();
    }

    /**
     * Format a message using Minecraft's legacy color codes
     *
     * @param message The input string to be formatted
     * @return The string formatted with Minecraft color codes
     */
    public static String chat(String message)
    {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    /**
     * Send a message in console with formatted chat colors
     *
     * @param message The input string
     */
    public void sendMessage(String message)
    {
        Bukkit.getConsoleSender().sendMessage(chat(getTitleString() + message));
    }

    /**
     * Send a message based off of a lang path
     *
     * @param path The lang path
     */
    public void sendMessageLang(String path)
    {
        sendMessage(plugin.langManager().getText(path));
    }

    /**
     * Send a message based off of a lang path
     *
     * @param prefix A prefix string that will be appended before the lang String
     * @param path The lang path
     */
    public void sendMessageLang(String prefix, String path)
    {
        sendMessage(prefix + plugin.langManager().getText(path));
    }

    /**
     * Send a message in console with formatted chat colors
     * Default color is red so it's more visible
     *
     * @param message The input string
     */
    public void debug(String message)
    {
        Bukkit.getConsoleSender().sendMessage(chat(getTitleString() + "&c" + message));
    }

    /**
     * Sends the player a formatted message
     *
     * @param player Input player that will receive the message
     * @param message The message to be printed (will be formatted with colors)
     */
    public void sendMessage(Player player, String message)
    {
        player.sendMessage(chat(getTitleString() + message));
    }

    /**
     * Sends the player a formatted message based off of a lang path
     *
     * @param player Input player that will receive the message
     * @param path The lang path
     */
    public void sendMessageLang(Player player, String path)
    {
        sendMessage(player, plugin.langManager().getText(path));
    }

    /**
     * Sends the player a formatted message based off of a lang path
     *
     * @param player Input player that will receive the message
     * @param prefix A prefix string that will be appended before the lang String
     * @param path The lang path
     */
    public void sendMessageLang(Player player, String prefix, String path)
    {
        sendMessage(player, prefix + plugin.langManager().getText(path));
    }

    /**
     * Sends the command sender (player or console) a formatted message
     *
     * @param sender Input CommandSender that will receive the message
     * @param message The message to be printed (will be formatted with colors)
     */
    public void sendMessage(CommandSender sender, String message)
    {
        sender.sendMessage(chat(getTitleString() + message));
    }

    /**
     * Sends the player a formatted message based off of a lang path
     *
     * @param sender Input CommandSender that will receive the message
     * @param path The lang path
     */
    public void sendMessageLang(CommandSender sender, String path)
    {
        sendMessage(sender, plugin.langManager().getText(path));
    }

    /**
     * Sends the player a formatted message based off of a lang path
     *
     * @param sender Input CommandSender that will receive the message
     * @param prefix A prefix string that will be appended before the lang String
     * @param path The lang path
     */
    public void sendMessageLang(CommandSender sender, String prefix, String path)
    {
        sendMessage(sender, prefix + plugin.langManager().getText(path));
    }

    /**
     * Gets a title tag that can be added to the beginning of text to define
     * what this text is coming from
     * @return The title string
     */
    public String getTitleString()
    {
        return "&b[&9" + pluginString + "&b] &r";
    }

    /**
     * Converts strings into Bungee API baseComponent arrays
     *
     * @param strings The strings that will be converted to base components
     * @return BaseComponent array of converted strings
     */
    public BaseComponent[] getBaseComponentArray(String... strings)
    {
        ArrayList<BaseComponent> baseComponents = new ArrayList<BaseComponent>();
        for(String str : strings)
        {
            baseComponents.addAll(Arrays.asList(TextComponent.fromLegacyText(chat(str))));
        }
        return baseComponents.toArray(new BaseComponent[0]);
    }

    /**
     * Creates a Bungee API ClickEvent to do something with a command
     *
     * @param action The ClickEvent Action that should happen on click
     * @param command The command to be used on the ClickEvent
     * @return A new click event that can be used with BaseComponents
     */
    public ClickEvent getClickEvent(ClickEvent.Action action, String command)
    {
        return new ClickEvent(action, command);
    }

    /**
     * Creates a Bungee API ClickEvent to do something with a command
     *
     * @param action The HoverEvent Action to be used when cursor is hovered over applied text
     * @param text The string of text that will be used in the hover event
     * @return The HoverEvent that was created
     */
    public HoverEvent getHoverEvent(HoverEvent.Action action, String text)
    {
        return new HoverEvent(action, new Text(chat(text)));
    }

    /**
     * Creates a Bungee API ClickEvent to do something with a command
     *
     * @param action The HoverEvent Action to be used when cursor is hovered over applied text
     * @param item The item that will be displayed
     * @return The HoverEvent that was created
     */
    public HoverEvent getHoverEvent(HoverEvent.Action action, ItemStack item)
    {
        return new HoverEvent(action, getHoverItem(item));
    }

    /**
     * Creates a Bungee API ClickEvent to do something with a command
     *
     * @param action The HoverEvent Action to be used when cursor is hovered over applied text
     * @param entity The entity that will be displayed
     * @return The HoverEvent that was created
     */
    public HoverEvent getHoverEvent(HoverEvent.Action action, org.bukkit.entity.Entity entity)
    {
        return new HoverEvent(action, getHoverEntity(entity));
    }

    /**
     * Creates a Hover Item from an ItemStack
     *
     * @param item Item to be converted
     * @return HoverEvent Item
     */
    public Item getHoverItem(ItemStack item)
    {
        int itemAmount = item.getAmount();
        String itemId = item.getType().toString().toLowerCase();
        return new Item(itemId, itemAmount, null);
    }

    public Entity getHoverEntity(org.bukkit.entity.Entity entity)
    {
        String entityType = entity.getType().toString().toLowerCase();
        String entityId = entity.getUniqueId().toString();
        String entityName = entity.getName();
        return new Entity(entityType, entityId, new TextComponent(entityName));
    }

    /**
     * Applies a ClickEvent to an array of BaseComponents
     *
     * @param components An array of BaseComponents that will have a ClickEvent applied to them
     * @param event The ClickEvent to be added to the components
     * @return The same BaseComponents array but with the click events applied
     */
    public BaseComponent[] setClickEvent(BaseComponent[] components, ClickEvent event)
    {
        for(BaseComponent component : components)
        {
            component.setClickEvent(event);
        }
        return components;
    }

    /**
     * Applies a HoverEvent to an array of BaseComponents
     *
     * @param components An array of BaseComponents that will have a HoverEvent applied to them
     * @param event The HoverEvent to be added to the components
     * @return The same BaseComponents array but with the hover events applied
     */
    public BaseComponent[] setHoverEvent(BaseComponent[] components, HoverEvent event)
    {
        for(BaseComponent component : components)
        {
            component.setHoverEvent(event);
        }
        return components;
    }

    /**
     * Combines multiple BaseComponent arrays and combines them into 1 larger array
     *
     * @param components An array of BaseComponents arrays that will be combined into one base components array
     * @return A combined array of all BaseComponents
     */
    public BaseComponent[] combineComponents(BaseComponent[]... components)
    {
        ArrayList<BaseComponent> componentsArrayList = new ArrayList<BaseComponent>();
        for(BaseComponent[] componentsArr : components)
        {
            componentsArrayList.addAll(Arrays.asList(componentsArr));
        }
        return componentsArrayList.toArray(new BaseComponent[0]);
    }

    /**
     * Print all BaseComponents to a CommandSender
     *
     * @param sender The CommandSender that will receive the message
     * @param components An Array of BaseComponents arrays that will be printed, Each BaseComponent array being 1 line
     */
    public void printComponents(CommandSender sender, BaseComponent[]... components)
    {
        sender.spigot().sendMessage(combineComponents(components));
    }

    /**
     * Print all BaseComponents to a Player
     *
     * @param player The Player that will receive the message
     * @param components An Array of BaseComponents arrays that will be printed, Each BaseComponent array being 1 line
     */
    public void printComponents(Player player, BaseComponent[]... components)
    {
        player.spigot().sendMessage(combineComponents(components));
    }

    /**
     * Print all BaseComponents to a CommandSender
     *
     * @param sender The CommandSender that will receive the message
     * @param type The ChatMessageType to display the components at
     * @param components An Array of BaseComponents arrays that will be printed, Each BaseComponent array being 1 line
     */
    public void printComponents(CommandSender sender, ChatMessageType type, BaseComponent[]... components)
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
     * Print all BaseComponents to a Player
     *
     * @param player The Player that will receive the message
     * @param type The ChatMessageType to display the components at
     * @param components An Array of BaseComponents arrays that will be printed, Each BaseComponent array being 1 line
     */
    public void printComponents(Player player, ChatMessageType type, BaseComponent[]... components)
    {
        player.spigot().sendMessage(type, combineComponents(components));
    }
}
