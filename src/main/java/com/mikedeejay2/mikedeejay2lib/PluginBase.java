package com.mikedeejay2.mikedeejay2lib;

import com.mikedeejay2.mikedeejay2lib.commands.CommandManager;
import com.mikedeejay2.mikedeejay2lib.event.ListenerManager;
import com.mikedeejay2.mikedeejay2lib.data.DataManager;
import com.mikedeejay2.mikedeejay2lib.gui.listeners.GUIListener;
import com.mikedeejay2.mikedeejay2lib.gui.manager.GUIManager;
import com.mikedeejay2.mikedeejay2lib.text.language.LangManager;
import com.mikedeejay2.mikedeejay2lib.nms.NMSHandler;
import com.mikedeejay2.mikedeejay2lib.util.bstats.BStats;
import com.mikedeejay2.mikedeejay2lib.util.chat.Colors;
import com.mikedeejay2.mikedeejay2lib.util.debug.DebugTimer;
import com.mikedeejay2.mikedeejay2lib.util.enchant.GlowEnchantment;
import com.mikedeejay2.mikedeejay2lib.util.update.UpdateChecker;
import com.mikedeejay2.mikedeejay2lib.util.version.MinecraftVersion;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Item;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Main class for Mikedeejay2Lib.
 * <p>
 * <tt>PluginBase</tt> is a robust plugin class that extends {@link JavaPlugin} to
 * add new functions. Check source code on Github to find all features, planned additions,
 * bugs, etc.
 * <p>
 * Source code: <a href="https://github.com/Mikedeejay2/Mikedeejay2Lib">https://github.com/Mikedeejay2/Mikedeejay2Lib</a>
 *
 * @author Mikedeejay2
 */
public class PluginBase extends JavaPlugin
{
    protected String prefix;
    protected MinecraftVersion minecraftVersion;
    protected DataManager dataManager;
    protected LangManager langManager;
    protected CommandManager commandManager;
    protected ListenerManager listenerManager;
    protected GUIManager guiManager;
    protected BStats bStats;
    protected UpdateChecker updateChecker;
    protected NMSHandler nms;

    public PluginBase()
    {}

    @Override
    public void onEnable()
    {
        this.prefix = "[" + this.getDescription().getName() + "] ";
        this.minecraftVersion = new MinecraftVersion(this);
        this.langManager = new LangManager(this);
        this.nms = new NMSHandler(this);
        this.commandManager = new CommandManager(this);
        this.dataManager = new DataManager();
        this.listenerManager = new ListenerManager(this);
        this.guiManager = new GUIManager(this);
        this.bStats = new BStats(this);
        this.updateChecker = new UpdateChecker(this);

        this.getServer().getPluginManager().registerEvents(new GUIListener(this), this);

        GlowEnchantment.registerGlow(this);

        this.sendMessage(String.format("&a%s is powered by Mikedeejay2Lib, a collection of open source resources for developers to use.", this.getDescription().getName()));
    }

    @Override
    public void onDisable()
    {

    }

    /**
     * Get the Minecraft server's Minecraft version
     *
     * @return The Minecraft version
     */
    public MinecraftVersion getMCVersion()
    {
        return minecraftVersion;
    }

    /**
     * Get this plugin's file manager
     *
     * @return The file manager
     */
    public DataManager fileManager()
    {
        return dataManager;
    }

    /**
     * Get this plugin's language manager
     *
     * @return The language manager
     */
    public LangManager langManager()
    {
        return langManager;
    }

    /**
     * Get this plugin's command manager
     *
     * @return The command manager
     */
    public CommandManager commandManager()
    {
        return commandManager;
    }

    /**
     * Get this plugin's listener manager
     *
     * @return The listener manager
     */
    public ListenerManager listenerManager()
    {
        return listenerManager;
    }

    /**
     * Get this plugin's GUI manager
     *
     * @return The GUI manager
     */
    public GUIManager guiManager()
    {
        return guiManager;
    }

    /**
     * Get this plugin's BStats manager
     *
     * @return The BStats manager
     */
    public BStats bStats()
    {
        return bStats;
    }

    /**
     * Get this plugin's Update Checker
     *
     * @return The Update Checker
     */
    public UpdateChecker updateChecker()
    {
        return updateChecker;
    }

    /**
     * Gets the Net Minecraft Server handler
     *
     * @return The Net Minecraft Server handler
     */
    public NMSHandler NMS()
    {
        return nms;
    }

    /**
     * Send a message in console with formatted chat colors
     *
     * @param message The input string
     */
    public void sendMessage(String message)
    {
        Bukkit.getConsoleSender().sendMessage(Colors.format(getPrefix() + message));
    }

    /**
     * Send a message based off of a lang path
     *
     * @param path The lang path
     */
    public void sendMessageLang(String path)
    {
        sendMessage(this.langManager().getText(path));
    }

    /**
     * Send a message based off of a lang path
     *
     * @param prefix A prefix string that will be appended before the lang String
     * @param path   The lang path
     */
    public void sendMessageLang(String prefix, String path)
    {
        sendMessage(prefix + this.langManager().getText(path));
    }

    /**
     * Send a message in console with formatted chat colors
     * Default color is red so it's more visible
     *
     * @param message The input string
     */
    public void debug(String message)
    {
        Bukkit.getConsoleSender().sendMessage(Colors.format(getPrefix() + "&c" + message));
    }

    /**
     * Sends the player a formatted message
     *
     * @param player  Input player that will receive the message
     * @param message The message to be printed (will be formatted with colors)
     */
    public void sendMessage(Player player, String message)
    {
        player.sendMessage(Colors.format(getPrefix() + message));
    }

    /**
     * Sends the player a formatted message based off of a lang path
     *
     * @param player Input player that will receive the message
     * @param path   The lang path
     */
    public void sendMessageLang(Player player, String path)
    {
        sendMessage(player, this.langManager().getText(path));
    }

    /**
     * Sends the player a formatted message based off of a lang path
     *
     * @param player Input player that will receive the message
     * @param prefix A prefix string that will be appended before the lang String
     * @param path   The lang path
     */
    public void sendMessageLang(Player player, String prefix, String path)
    {
        sendMessage(player, prefix + this.langManager().getText(path));
    }

    /**
     * Sends the command sender (player or console) a formatted message
     *
     * @param sender  Input <tt>CommandSender</tt> that will receive the message
     * @param message The message to be printed (will be formatted with colors)
     */
    public void sendMessage(CommandSender sender, String message)
    {
        sender.sendMessage(Colors.format(getPrefix() + message));
    }

    /**
     * Sends the player a formatted message based off of a lang path
     *
     * @param sender Input <tt>CommandSender</tt> that will receive the message
     * @param path   The lang path
     */
    public void sendMessageLang(CommandSender sender, String path)
    {
        sendMessage(sender, this.langManager().getText(path));
    }

    /**
     * Sends the player a formatted message based off of a lang path
     *
     * @param sender Input <tt>CommandSender</tt> that will receive the message
     * @param prefix A prefix string that will be appended before the lang String
     * @param path   The lang path
     */
    public void sendMessageLang(CommandSender sender, String prefix, String path)
    {
        sendMessage(sender, prefix + this.langManager().getText(path));
    }

    /**
     * Gets a title tag that can be added to the beginning of text to define
     * what this text is coming from
     * @return The title string
     */
    public String getPrefix()
    {
        return prefix;
    }

    /**
     * Converts strings into Bungee API <tt>BaseComponent</tt> arrays
     *
     * @param strings The strings that will be converted to base components
     * @return <tt>BaseComponent</tt> array of converted strings
     */
    public BaseComponent[] getBaseComponentArray(String... strings)
    {
        ArrayList<BaseComponent> baseComponents = new ArrayList<>();
        for(String str : strings)
        {
            baseComponents.addAll(Arrays.asList(TextComponent.fromLegacyText(Colors.format(str))));
        }
        return baseComponents.toArray(new BaseComponent[0]);
    }

    /**
     * Creates a Bungee API <tt>ClickEvent</tt> to do something with a command
     *
     * @param action  The <tt>ClickEvent</tt> Action that should happen on click
     * @param command The command to be used on the <tt>ClickEvent</tt>
     * @return A new <tt>ClickEvent</tt> that can be used with <tt>BaseComponents</tt>
     */
    public ClickEvent getClickEvent(ClickEvent.Action action, String command)
    {
        return new ClickEvent(action, command);
    }

    /**
     * Creates a Bungee API <tt>HoverEvent</tt> to do something with a command
     *
     * @param action The <tt>HoverEvent</tt> Action to be used when cursor is hovered over applied text
     * @param text   The string of text that will be used in the hover event
     * @return The <tt>HoverEvent</tt> that was created
     */
    public HoverEvent getHoverEvent(HoverEvent.Action action, String text)
    {
        return new HoverEvent(action, new Text(Colors.format(text)));
    }

    /**
     * Creates a Bungee API <tt>HoverEvent</tt> to do something with a command
     *
     * @param action The <tt>HoverEvent</tt> Action to be used when cursor is hovered over applied text
     * @param item   The item that will be displayed
     * @return The <tt>HoverEvent</tt> that was created
     */
    public HoverEvent getHoverEvent(HoverEvent.Action action, ItemStack item)
    {
        return new HoverEvent(action, getHoverItem(item));
    }

    /**
     * Creates a Hover Item from an <tt>ItemStack</tt>
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

    /**
     * Applies a <tt>ClickEvent</tt> to an array of <tt>BaseComponents</tt>
     *
     * @param components An array of <tt>BaseComponents</tt> that will have a <tt>ClickEvent</tt> applied to them
     * @param event      The <tt>ClickEvent</tt> to be added to the components
     * @return The same <tt>BaseComponents</tt> array but with the click events applied
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
     * Applies a <tt>HoverEvent</tt> to an array of <tt>BaseComponents</tt>
     *
     * @param components An array of <tt>BaseComponents</tt> that will have a <tt>HoverEvent</tt> applied to them
     * @param event      The <tt>HoverEvent</tt> to be added to the components
     * @return The same <tt>BaseComponents</tt> array but with the hover events applied
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
     * Combines multiple <tt>BaseComponent</tt> arrays and combines them into 1 larger array
     *
     * @param components An array of <tt>BaseComponents</tt> arrays that will be combined into one base components array
     * @return A combined array of all <tt>BaseComponents</tt>
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
     * Print all <tt>BaseComponents</tt> to a CommandSender
     *
     * @param sender     The CommandSender that will receive the message
     * @param components An Array of BaseComponents arrays that will be printed, Each BaseComponent array being 1 line
     */
    public void printComponents(CommandSender sender, BaseComponent[]... components)
    {
        sender.spigot().sendMessage(combineComponents(components));
    }

    /**
     * Print all <tt>BaseComponents</tt> to a Player
     *
     * @param player     The Player that will receive the message
     * @param components An Array of BaseComponents arrays that will be printed, Each BaseComponent array being 1 line
     */
    public void printComponents(Player player, BaseComponent[]... components)
    {
        player.spigot().sendMessage(combineComponents(components));
    }

    /**
     * Print all <tt>BaseComponents</tt> to a CommandSender
     *
     * @param sender     The CommandSender that will receive the message
     * @param type       The ChatMessageType to display the components at
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
     * Print all <tt>BaseComponents</tt> to a Player
     *
     * @param player     The Player that will receive the message
     * @param type       The ChatMessageType to display the components at
     * @param components An Array of BaseComponents arrays that will be printed, Each BaseComponent array being 1 line
     */
    public void printComponents(Player player, ChatMessageType type, BaseComponent[]... components)
    {
        player.spigot().sendMessage(type, combineComponents(components));
    }

    /**
     * Broadcast a message to all players on the server.
     *
     * @param message The message to broadcast to players
     */
    public void broadcastMessage(String message)
    {
        Bukkit.broadcastMessage(Colors.format(getPrefix() + message));
    }

    /**
     * Send a title to a player
     *
     * @param player   The player to send the title to
     * @param title    The title to send the player
     * @param subtitle The subtitle to send the player
     * @param fadeIn   The fade in rate of the title
     * @param stay     The stay length of the title
     * @param fadeOut  The fade out rate of the title
     */
    public void sendTitle(Player player, String title, String subtitle, int fadeIn, int stay, int fadeOut)
    {
        player.sendTitle(Colors.format(title), Colors.format(subtitle), fadeIn, stay, fadeOut);
    }
}
