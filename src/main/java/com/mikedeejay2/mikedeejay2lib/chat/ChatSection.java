package com.mikedeejay2.mikedeejay2lib.chat;

import com.mikedeejay2.mikedeejay2lib.PluginBase;
import com.mikedeejay2.mikedeejay2lib.util.chat.Chat;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * A class that holds and manages a section of <tt>ChatText</tt>.
 * Used in {@link ChatCanvas}.
 *
 * @author Mikedeejay2
 */
public class ChatSection
{
    protected final PluginBase plugin;
    protected final Chat chat;
    protected List<ChatText> texts;
    protected boolean converted;
    protected BaseComponent[] components;

    public ChatSection(PluginBase plugin)
    {
        this.plugin = plugin;
        this.chat = plugin.chat();
        this.texts = new ArrayList<>();
        this.converted = false;
    }

    /**
     * Add a <tt>ChatText</tt> to this <tt>ChatSection</tt>
     *
     * @param text The string of text to add
     * @return A reference to the new <tt>ChatText</tt>
     */
    public ChatText addText(String text)
    {
        ChatText chatText = new ChatText(text);
        texts.add(chatText);
        return chatText;
    }

    /**
     * Add a <tt>ChatText</tt> to this <tt>ChatSection</tt>
     *
     * @param text The string of text to add
     * @param clickAction The <tt>ClickEvent.Action</tt> of the text
     * @param clickString The String of text for the clickAction to use
     * @return A reference to the new <tt>ChatText</tt>
     */
    public ChatText addText(String text, ClickEvent.Action clickAction, String clickString)
    {
        ChatText chatText = new ChatText(text, clickAction, clickString);
        texts.add(chatText);
        return chatText;
    }

    /**
     * Add a <tt>ChatText</tt> to this <tt>ChatSection</tt>
     *
     * @param text The string of text to add
     * @param hoverAction The <tt>HoverAction</tt> of the text
     * @param hoverString  The String of text for the hoverAction to use
     * @return A reference to the new <tt>ChatText</tt>
     */
    public ChatText addText(String text, HoverEvent.Action hoverAction, String hoverString)
    {
        ChatText chatText = new ChatText(text, hoverAction, hoverString);
        texts.add(chatText);
        return chatText;
    }

    /**
     * Add a <tt>ChatText</tt> to this <tt>ChatSection</tt>
     *
     * @param text The string of text to add
     * @param hoverAction The <tt>HoverAction</tt> of the text
     * @param hoverString  The String of text for the hoverAction to use
     * @param clickAction The <tt>ClickEvent.Action</tt> of the text
     * @param clickString The String of text for the clickAction to use
     * @return A reference to the new <tt>ChatText</tt>
     */
    public ChatText addText(String text, ClickEvent.Action clickAction, HoverEvent.Action hoverAction, String clickString, String hoverString)
    {
        ChatText chatText = new ChatText(text, clickAction, hoverAction, clickString, hoverString);
        texts.add(chatText);
        return chatText;
    }

    /**
     * Add a <tt>ChatText</tt> to this <tt>ChatSection</tt>
     *
     * @param text The string of text to add
     * @param index The index that the new <tt>ChatText</tt> will be added to
     * @return A reference to the new <tt>ChatText</tt>
     */
    public ChatText addText(String text, int index)
    {
        ChatText chatText = new ChatText(text);
        texts.add(index, chatText);
        return chatText;
    }

    /**
     * Add a <tt>ChatText</tt> to this <tt>ChatSection</tt>
     *
     * @param text The string of text to add
     * @param clickAction The <tt>ClickEvent.Action</tt> of the text
     * @param clickString The String of text for the clickAction to use
     * @param index The index that the new <tt>ChatText</tt> will be added to
     * @return A reference to the new <tt>ChatText</tt>
     */
    public ChatText addText(String text, ClickEvent.Action clickAction, String clickString, int index)
    {
        ChatText chatText = new ChatText(text, clickAction, clickString);
        texts.add(index, chatText);
        return chatText;
    }

    /**
     * Add a <tt>ChatText</tt> to this <tt>ChatSection</tt>
     *
     * @param text The string of text to add
     * @param hoverAction The <tt>HoverAction</tt> of the text
     * @param hoverString  The String of text for the hoverAction to use
     * @param index The index that the new <tt>ChatText</tt> will be added to
     * @return A reference to the new <tt>ChatText</tt>
     */
    public ChatText addText(String text, HoverEvent.Action hoverAction, String hoverString, int index)
    {
        ChatText chatText = new ChatText(text, hoverAction, hoverString);
        texts.add(index, chatText);
        return chatText;
    }

    /**
     * Add a <tt>ChatText</tt> to this <tt>ChatSection</tt>
     *
     * @param text The string of text to add
     * @param hoverAction The <tt>HoverAction</tt> of the text
     * @param hoverString  The String of text for the hoverAction to use
     * @param clickAction The <tt>ClickEvent.Action</tt> of the text
     * @param clickString The String of text for the clickAction to use
     * @param index The index that the new <tt>ChatText</tt> will be added to
     * @return A reference to the new <tt>ChatText</tt>
     */
    public ChatText addText(String text, ClickEvent.Action clickAction, HoverEvent.Action hoverAction, String clickString, String hoverString, int index)
    {
        ChatText chatText = new ChatText(text, clickAction, hoverAction, clickString, hoverString);
        texts.add(index, chatText);
        return chatText;
    }

    /**
     * Add a <tt>ChatText</tt> to this <tt>ChatSection</tt> <p>
     * Text is found through the <tt>LangManager</tt> of the plugin.
     *
     * @param text The string of text to add
     * @return A reference to the new <tt>ChatText</tt>
     */
    public ChatText addTextLang(String text)
    {
        text = plugin.langManager().getText(text);
        ChatText chatText = new ChatText(text);
        texts.add(chatText);
        return chatText;
    }

    /**
     * Add a <tt>ChatText</tt> to this <tt>ChatSection</tt> <p>
     * Text is found through the <tt>LangManager</tt> of the plugin.
     *
     * @param text The string of text to add
     * @param clickAction The <tt>ClickEvent.Action</tt> of the text
     * @param clickString The String of text for the clickAction to use
     * @return A reference to the new <tt>ChatText</tt>
     */
    public ChatText addTextLang(String text, ClickEvent.Action clickAction, String clickString)
    {
        text = plugin.langManager().getText(text);
        ChatText chatText = new ChatText(text, clickAction, clickString);
        texts.add(chatText);
        return chatText;
    }

    /**
     * Add a <tt>ChatText</tt> to this <tt>ChatSection</tt> <p>
     * Text is found through the <tt>LangManager</tt> of the plugin.
     *
     * @param text The string of text to add
     * @param hoverAction The <tt>HoverAction</tt> of the text
     * @param hoverString  The String of text for the hoverAction to use
     * @return A reference to the new <tt>ChatText</tt>
     */
    public ChatText addTextLang(String text, HoverEvent.Action hoverAction, String hoverString)
    {
        text = plugin.langManager().getText(text);
        ChatText chatText = new ChatText(text, hoverAction, hoverString);
        texts.add(chatText);
        return chatText;
    }

    /**
     * Add a <tt>ChatText</tt> to this <tt>ChatSection</tt> <p>
     * Text is found through the <tt>LangManager</tt> of the plugin.
     *
     * @param text The string of text to add
     * @param hoverAction The <tt>HoverAction</tt> of the text
     * @param hoverString  The String of text for the hoverAction to use
     * @param clickAction The <tt>ClickEvent.Action</tt> of the text
     * @param clickString The String of text for the clickAction to use
     * @return A reference to the new <tt>ChatText</tt>
     */
    public ChatText addTextLang(String text, ClickEvent.Action clickAction, HoverEvent.Action hoverAction, String clickString, String hoverString)
    {
        text = plugin.langManager().getText(text);
        ChatText chatText = new ChatText(text, clickAction, hoverAction, clickString, hoverString);
        texts.add(chatText);
        return chatText;
    }

    /**
     * Add a <tt>ChatText</tt> to this <tt>ChatSection</tt> <p>
     * Text is found through the <tt>LangManager</tt> of the plugin.
     *
     * @param text The string of text to add
     * @param index The index that the new <tt>ChatText</tt> will be added to
     * @return A reference to the new <tt>ChatText</tt>
     */
    public ChatText addTextLang(String text, int index)
    {
        text = plugin.langManager().getText(text);
        ChatText chatText = new ChatText(text);
        texts.add(index, chatText);
        return chatText;
    }

    public ChatText addTextLang(String text, ClickEvent.Action clickAction, String clickString, int index)
    {
        text = plugin.langManager().getText(text);
        ChatText chatText = new ChatText(text, clickAction, clickString);
        texts.add(index, chatText);
        return chatText;
    }

    /**
     * Add a <tt>ChatText</tt> to this <tt>ChatSection</tt> <p>
     * Text is found through the <tt>LangManager</tt> of the plugin.
     *
     * @param text The string of text to add
     * @param hoverAction The <tt>HoverAction</tt> of the text
     * @param hoverString  The String of text for the hoverAction to use
     * @param index The index that the new <tt>ChatText</tt> will be added to
     * @return A reference to the new <tt>ChatText</tt>
     */
    public ChatText addTextLang(String text, HoverEvent.Action hoverAction, String hoverString, int index)
    {
        text = plugin.langManager().getText(text);
        ChatText chatText = new ChatText(text, hoverAction, hoverString);
        texts.add(index, chatText);
        return chatText;
    }

    /**
     * Add a <tt>ChatText</tt> to this <tt>ChatSection</tt> <p>
     * Text is found through the <tt>LangManager</tt> of the plugin.
     *
     * @param text The string of text to add
     * @param hoverAction The <tt>HoverAction</tt> of the text
     * @param hoverString  The String of text for the hoverAction to use
     * @param clickAction The <tt>ClickEvent.Action</tt> of the text
     * @param clickString The String of text for the clickAction to use
     * @param index The index that the new <tt>ChatText</tt> will be added to
     * @return A reference to the new <tt>ChatText</tt>
     */
    public ChatText addTextLang(String text, ClickEvent.Action clickAction, HoverEvent.Action hoverAction, String clickString, String hoverString, int index)
    {
        text = plugin.langManager().getText(text);
        ChatText chatText = new ChatText(text, clickAction, hoverAction, clickString, hoverString);
        texts.add(index, chatText);
        return chatText;
    }

    /**
     * Add a <tt>ChatText</tt> to this <tt>ChatSection</tt> <p>
     * Text is found through the <tt>LangManager</tt> of the plugin.
     *
     * @param sender The <tt>CommandSender</tt> to get the locale from
     * @param text The string of text to add
     * @return A reference to the new <tt>ChatText</tt>
     */
    public ChatText addTextLang(CommandSender sender, String text)
    {
        text = plugin.langManager().getText(sender, text);
        ChatText chatText = new ChatText(text);
        texts.add(chatText);
        return chatText;
    }

    /**
     * Add a <tt>ChatText</tt> to this <tt>ChatSection</tt> <p>
     * Text is found through the <tt>LangManager</tt> of the plugin.
     *
     * @param sender The <tt>CommandSender</tt> to get the locale from
     * @param text The string of text to add
     * @param clickAction The <tt>ClickEvent.Action</tt> of the text
     * @param clickString The String of text for the clickAction to use
     * @return A reference to the new <tt>ChatText</tt>
     */
    public ChatText addTextLang(CommandSender sender, String text, ClickEvent.Action clickAction, String clickString)
    {
        text = plugin.langManager().getText(sender, text);
        ChatText chatText = new ChatText(text, clickAction, clickString);
        texts.add(chatText);
        return chatText;
    }

    /**
     * Add a <tt>ChatText</tt> to this <tt>ChatSection</tt> <p>
     * Text is found through the <tt>LangManager</tt> of the plugin.
     *
     * @param sender The <tt>CommandSender</tt> to get the locale from
     * @param text The string of text to add
     * @param hoverAction The <tt>HoverAction</tt> of the text
     * @param hoverString  The String of text for the hoverAction to use
     * @return A reference to the new <tt>ChatText</tt>
     */
    public ChatText addTextLang(CommandSender sender, String text, HoverEvent.Action hoverAction, String hoverString)
    {
        text = plugin.langManager().getText(sender, text);
        ChatText chatText = new ChatText(text, hoverAction, hoverString);
        texts.add(chatText);
        return chatText;
    }

    /**
     * Add a <tt>ChatText</tt> to this <tt>ChatSection</tt> <p>
     * Text is found through the <tt>LangManager</tt> of the plugin.
     *
     * @param sender The <tt>CommandSender</tt> to get the locale from
     * @param text The string of text to add
     * @param hoverAction The <tt>HoverAction</tt> of the text
     * @param hoverString  The String of text for the hoverAction to use
     * @param clickAction The <tt>ClickEvent.Action</tt> of the text
     * @param clickString The String of text for the clickAction to use
     * @return A reference to the new <tt>ChatText</tt>
     */
    public ChatText addTextLang(CommandSender sender, String text, ClickEvent.Action clickAction, HoverEvent.Action hoverAction, String clickString, String hoverString)
    {
        text = plugin.langManager().getText(sender, text);
        ChatText chatText = new ChatText(text, clickAction, hoverAction, clickString, hoverString);
        texts.add(chatText);
        return chatText;
    }

    /**
     * Add a <tt>ChatText</tt> to this <tt>ChatSection</tt> <p>
     * Text is found through the <tt>LangManager</tt> of the plugin.
     *
     * @param sender The <tt>CommandSender</tt> to get the locale from
     * @param text The string of text to add
     * @param index The index that the new <tt>ChatText</tt> will be added to
     * @return A reference to the new <tt>ChatText</tt>
     */
    public ChatText addTextLang(CommandSender sender, String text, int index)
    {
        text = plugin.langManager().getText(sender, text);
        ChatText chatText = new ChatText(text);
        texts.add(index, chatText);
        return chatText;
    }

    /**
     * Add a <tt>ChatText</tt> to this <tt>ChatSection</tt> <p>
     * Text is found through the <tt>LangManager</tt> of the plugin.
     *
     * @param sender The <tt>CommandSender</tt> to get the locale from
     * @param text The string of text to add
     * @param clickAction The <tt>ClickEvent.Action</tt> of the text
     * @param clickString The String of text for the clickAction to use
     * @param index The index that the new <tt>ChatText</tt> will be added to
     * @return A reference to the new <tt>ChatText</tt>
     */
    public ChatText addTextLang(CommandSender sender, String text, ClickEvent.Action clickAction, String clickString, int index)
    {
        text = plugin.langManager().getText(sender, text);
        ChatText chatText = new ChatText(text, clickAction, clickString);
        texts.add(index, chatText);
        return chatText;
    }

    /**
     * Add a <tt>ChatText</tt> to this <tt>ChatSection</tt> <p>
     * Text is found through the <tt>LangManager</tt> of the plugin.
     *
     * @param sender The <tt>CommandSender</tt> to get the locale from
     * @param text The string of text to add
     * @param hoverAction The <tt>HoverAction</tt> of the text
     * @param hoverString  The String of text for the hoverAction to use
     * @param index The index that the new <tt>ChatText</tt> will be added to
     * @return A reference to the new <tt>ChatText</tt>
     */
    public ChatText addTextLang(CommandSender sender, String text, HoverEvent.Action hoverAction, String hoverString, int index)
    {
        text = plugin.langManager().getText(sender, text);
        ChatText chatText = new ChatText(text, hoverAction, hoverString);
        texts.add(index, chatText);
        return chatText;
    }

    /**
     * Add a <tt>ChatText</tt> to this <tt>ChatSection</tt> <p>
     * Text is found through the <tt>LangManager</tt> of the plugin.
     *
     * @param sender The <tt>CommandSender</tt> to get the locale from
     * @param text The string of text to add
     * @param hoverAction The <tt>HoverAction</tt> of the text
     * @param hoverString  The String of text for the hoverAction to use
     * @param clickAction The <tt>ClickEvent.Action</tt> of the text
     * @param clickString The String of text for the clickAction to use
     * @param index The index that the new <tt>ChatText</tt> will be added to
     * @return A reference to the new <tt>ChatText</tt>
     */
    public ChatText addTextLang(CommandSender sender, String text, ClickEvent.Action clickAction, HoverEvent.Action hoverAction, String clickString, String hoverString, int index)
    {
        text = plugin.langManager().getText(sender, text);
        ChatText chatText = new ChatText(text, clickAction, hoverAction, clickString, hoverString);
        texts.add(index, chatText);
        return chatText;
    }

    /**
     * Add a <tt>ChatText</tt> to this <tt>ChatSection</tt> <p>
     * Text is found through the <tt>LangManager</tt> of the plugin.
     *
     * @param player The Player to get the locale from
     * @param text The string of text to add
     * @return A reference to the new <tt>ChatText</tt>
     */
    public ChatText addTextLang(Player player, String text)
    {
        text = plugin.langManager().getText(player, text);
        ChatText chatText = new ChatText(text);
        texts.add(chatText);
        return chatText;
    }

    /**
     * Add a <tt>ChatText</tt> to this <tt>ChatSection</tt> <p>
     * Text is found through the <tt>LangManager</tt> of the plugin.
     *
     * @param player The Player to get the locale from
     * @param text The string of text to add=
     * @param clickAction The <tt>ClickEvent.Action</tt> of the text
     * @param clickString The String of text for the clickAction to use=
     * @return A reference to the new <tt>ChatText</tt>
     */
    public ChatText addTextLang(Player player, String text, ClickEvent.Action clickAction, String clickString)
    {
        text = plugin.langManager().getText(player, text);
        ChatText chatText = new ChatText(text, clickAction, clickString);
        texts.add(chatText);
        return chatText;
    }

    /**
     * Add a <tt>ChatText</tt> to this <tt>ChatSection</tt> <p>
     * Text is found through the <tt>LangManager</tt> of the plugin.
     *
     * @param player The Player to get the locale from
     * @param text The string of text to add
     * @param hoverAction The <tt>HoverAction</tt> of the text
     * @param hoverString  The String of text for the hoverAction to use
     * @return A reference to the new <tt>ChatText</tt>
     */
    public ChatText addTextLang(Player player, String text, HoverEvent.Action hoverAction, String hoverString)
    {
        text = plugin.langManager().getText(player, text);
        ChatText chatText = new ChatText(text, hoverAction, hoverString);
        texts.add(chatText);
        return chatText;
    }

    /**
     * Add a <tt>ChatText</tt> to this <tt>ChatSection</tt> <p>
     * Text is found through the <tt>LangManager</tt> of the plugin.
     *
     * @param player The Player to get the locale from
     * @param text The string of text to add
     * @param hoverAction The <tt>HoverAction</tt> of the text
     * @param hoverString  The String of text for the hoverAction to use
     * @param clickAction The <tt>ClickEvent.Action</tt> of the text
     * @param clickString The String of text for the clickAction to use
     * @return A reference to the new <tt>ChatText</tt>
     */
    public ChatText addTextLang(Player player, String text, ClickEvent.Action clickAction, HoverEvent.Action hoverAction, String clickString, String hoverString)
    {
        text = plugin.langManager().getText(player, text);
        ChatText chatText = new ChatText(text, clickAction, hoverAction, clickString, hoverString);
        texts.add(chatText);
        return chatText;
    }

    /**
     * Add a <tt>ChatText</tt> to this <tt>ChatSection</tt> <p>
     * Text is found through the <tt>LangManager</tt> of the plugin.
     *
     * @param player The Player to get the locale from
     * @param text The string of text to add
     * @param index The index that the new <tt>ChatText</tt> will be added to
     * @return A reference to the new <tt>ChatText</tt>
     */
    public ChatText addTextLang(Player player, String text, int index)
    {
        text = plugin.langManager().getText(player, text);
        ChatText chatText = new ChatText(text);
        texts.add(index, chatText);
        return chatText;
    }

    /**
     * Add a <tt>ChatText</tt> to this <tt>ChatSection</tt> <p>
     * Text is found through the <tt>LangManager</tt> of the plugin.
     *
     * @param player The Player to get the locale from
     * @param text The string of text to add
     * @param clickAction The <tt>ClickEvent.Action</tt> of the text
     * @param clickString The String of text for the clickAction to use
     * @param index The index that the new <tt>ChatText</tt> will be added to
     * @return A reference to the new <tt>ChatText</tt>
     */
    public ChatText addTextLang(Player player, String text, ClickEvent.Action clickAction, String clickString, int index)
    {
        text = plugin.langManager().getText(player, text);
        ChatText chatText = new ChatText(text, clickAction, clickString);
        texts.add(index, chatText);
        return chatText;
    }

    /**
     * Add a <tt>ChatText</tt> to this <tt>ChatSection</tt> <p>
     * Text is found through the <tt>LangManager</tt> of the plugin.
     *
     * @param player The Player to get the locale from
     * @param text The string of text to add
     * @param hoverAction The <tt>HoverAction</tt> of the text
     * @param hoverString  The String of text for the hoverAction to use
     * @param index The index that the new <tt>ChatText</tt> will be added to
     * @return A reference to the new <tt>ChatText</tt>
     */
    public ChatText addTextLang(Player player, String text, HoverEvent.Action hoverAction, String hoverString, int index)
    {
        text = plugin.langManager().getText(player, text);
        ChatText chatText = new ChatText(text, hoverAction, hoverString);
        texts.add(index, chatText);
        return chatText;
    }

    /**
     * Add a <tt>ChatText</tt> to this <tt>ChatSection</tt> <p>
     * Text is found through the <tt>LangManager</tt> of the plugin.
     *
     * @param player The Player to get the locale from
     * @param text The string of text to add
     * @param hoverAction The <tt>HoverAction</tt> of the text
     * @param hoverString  The String of text for the hoverAction to use
     * @param clickAction The <tt>ClickEvent.Action</tt> of the text
     * @param clickString The String of text for the clickAction to use
     * @param index The index that the new <tt>ChatText</tt> will be added to
     * @return A reference to the new <tt>ChatText</tt>
     */
    public ChatText addTextLang(Player player, String text, ClickEvent.Action clickAction, HoverEvent.Action hoverAction, String clickString, String hoverString, int index)
    {
        text = plugin.langManager().getText(player, text);
        ChatText chatText = new ChatText(text, clickAction, hoverAction, clickString, hoverString);
        texts.add(index, chatText);
        return chatText;
    }

    /**
     * Add a <tt>ChatText</tt> to this <tt>ChatSection</tt> <p>
     * Text is found through the <tt>LangManager</tt> of the plugin using the library's
     * lang file.
     *
     * @param text The string of text to add
     * @return A reference to the new <tt>ChatText</tt>
     */
    public ChatText addTextLangLib(String text)
    {
        text = plugin.langManager().getTextLib(text);
        ChatText chatText = new ChatText(text);
        texts.add(chatText);
        return chatText;
    }

    /**
     * Add a <tt>ChatText</tt> to this <tt>ChatSection</tt> <p>
     * Text is found through the <tt>LangManager</tt> of the plugin using the library's
     * lang file.
     *
     * @param text The string of text to add
     * @param clickAction The <tt>ClickEvent.Action</tt> of the text
     * @param clickString The String of text for the clickAction to use
     * @return A reference to the new <tt>ChatText</tt>
     */
    public ChatText addTextLangLib(String text, ClickEvent.Action clickAction, String clickString)
    {
        text = plugin.langManager().getTextLib(text);
        ChatText chatText = new ChatText(text, clickAction, clickString);
        texts.add(chatText);
        return chatText;
    }

    /**
     * Add a <tt>ChatText</tt> to this <tt>ChatSection</tt> <p>
     * Text is found through the <tt>LangManager</tt> of the plugin using the library's
     * lang file.
     *
     * @param text The string of text to add
     * @param hoverAction The <tt>HoverAction</tt> of the text
     * @param hoverString  The String of text for the hoverAction to use
     * @return A reference to the new <tt>ChatText</tt>
     */
    public ChatText addTextLangLib(String text, HoverEvent.Action hoverAction, String hoverString)
    {
        text = plugin.langManager().getTextLib(text);
        ChatText chatText = new ChatText(text, hoverAction, hoverString);
        texts.add(chatText);
        return chatText;
    }

    /**
     * Add a <tt>ChatText</tt> to this <tt>ChatSection</tt> <p>
     * Text is found through the <tt>LangManager</tt> of the plugin using the library's
     * lang file.
     *
     * @param text The string of text to add
     * @param hoverAction The <tt>HoverAction</tt> of the text
     * @param hoverString  The String of text for the hoverAction to use
     * @param clickAction The <tt>ClickEvent.Action</tt> of the text
     * @param clickString The String of text for the clickAction to use
     * @return A reference to the new <tt>ChatText</tt>
     */
    public ChatText addTextLangLib(String text, ClickEvent.Action clickAction, HoverEvent.Action hoverAction, String clickString, String hoverString)
    {
        text = plugin.langManager().getTextLib(text);
        ChatText chatText = new ChatText(text, clickAction, hoverAction, clickString, hoverString);
        texts.add(chatText);
        return chatText;
    }

    /**
     * Add a <tt>ChatText</tt> to this <tt>ChatSection</tt> <p>
     * Text is found through the <tt>LangManager</tt> of the plugin using the library's
     * lang file.
     *
     * @param text The string of text to add
     * @param index The index that the new <tt>ChatText</tt> will be added to
     * @return A reference to the new <tt>ChatText</tt>
     */
    public ChatText addTextLangLib(String text, int index)
    {
        text = plugin.langManager().getTextLib(text);
        ChatText chatText = new ChatText(text);
        texts.add(index, chatText);
        return chatText;
    }

    /**
     * Add a <tt>ChatText</tt> to this <tt>ChatSection</tt> <p>
     * Text is found through the <tt>LangManager</tt> of the plugin using the library's
     * lang file.
     *
     * @param text The string of text to add
     * @param clickAction The <tt>ClickEvent.Action</tt> of the text
     * @param clickString The String of text for the clickAction to use
     * @param index The index that the new <tt>ChatText</tt> will be added to
     * @return A reference to the new <tt>ChatText</tt>
     */
    public ChatText addTextLangLib(String text, ClickEvent.Action clickAction, String clickString, int index)
    {
        text = plugin.langManager().getTextLib(text);
        ChatText chatText = new ChatText(text, clickAction, clickString);
        texts.add(index, chatText);
        return chatText;
    }

    /**
     * Add a <tt>ChatText</tt> to this <tt>ChatSection</tt> <p>
     * Text is found through the <tt>LangManager</tt> of the plugin using the library's
     * lang file.
     *
     * @param text The string of text to add
     * @param hoverAction The <tt>HoverAction</tt> of the text
     * @param hoverString  The String of text for the hoverAction to use
     * @param index The index that the new <tt>ChatText</tt> will be added to
     * @return A reference to the new <tt>ChatText</tt>
     */
    public ChatText addTextLangLib(String text, HoverEvent.Action hoverAction, String hoverString, int index)
    {
        text = plugin.langManager().getTextLib(text);
        ChatText chatText = new ChatText(text, hoverAction, hoverString);
        texts.add(index, chatText);
        return chatText;
    }

    /**
     * Add a <tt>ChatText</tt> to this <tt>ChatSection</tt> <p>
     * Text is found through the <tt>LangManager</tt> of the plugin using the library's
     * lang file.
     *
     * @param text The string of text to add
     * @param hoverAction The <tt>HoverAction</tt> of the text
     * @param hoverString  The String of text for the hoverAction to use
     * @param clickAction The <tt>ClickEvent.Action</tt> of the text
     * @param clickString The String of text for the clickAction to use
     * @param index The index that the new <tt>ChatText</tt> will be added to
     * @return A reference to the new <tt>ChatText</tt>
     */
    public ChatText addTextLangLib(String text, ClickEvent.Action clickAction, HoverEvent.Action hoverAction, String clickString, String hoverString, int index)
    {
        text = plugin.langManager().getTextLib(text);
        ChatText chatText = new ChatText(text, clickAction, hoverAction, clickString, hoverString);
        texts.add(index, chatText);
        return chatText;
    }

    /**
     * Add a <tt>ChatText</tt> to this <tt>ChatSection</tt> <p>
     * Text is found through the <tt>LangManager</tt> of the plugin using the library's
     * lang file.
     *
     * @param sender The <tt>CommandSender</tt> to get the locale from
     * @param text The string of text to add
     * @return A reference to the new <tt>ChatText</tt>
     */
    public ChatText addTextLangLib(CommandSender sender, String text)
    {
        text = plugin.langManager().getTextLib(sender, text);
        ChatText chatText = new ChatText(text);
        texts.add(chatText);
        return chatText;
    }

    /**
     * Add a <tt>ChatText</tt> to this <tt>ChatSection</tt> <p>
     * Text is found through the <tt>LangManager</tt> of the plugin using the library's
     * lang file.
     *
     * @param sender The <tt>CommandSender</tt> to get the locale from
     * @param text The string of text to add
     * @param clickAction The <tt>ClickEvent.Action</tt> of the text
     * @param clickString The String of text for the clickAction to use
     * @return A reference to the new <tt>ChatText</tt>
     */
    public ChatText addTextLangLib(CommandSender sender, String text, ClickEvent.Action clickAction, String clickString)
    {
        text = plugin.langManager().getTextLib(sender, text);
        ChatText chatText = new ChatText(text, clickAction, clickString);
        texts.add(chatText);
        return chatText;
    }

    /**
     * Add a <tt>ChatText</tt> to this <tt>ChatSection</tt> <p>
     * Text is found through the <tt>LangManager</tt> of the plugin using the library's
     * lang file.
     *
     * @param sender The <tt>CommandSender</tt> to get the locale from
     * @param text The string of text to add
     * @param hoverAction The <tt>HoverAction</tt> of the text
     * @param hoverString  The String of text for the hoverAction to use
     * @return A reference to the new <tt>ChatText</tt>
     */
    public ChatText addTextLangLib(CommandSender sender, String text, HoverEvent.Action hoverAction, String hoverString)
    {
        text = plugin.langManager().getTextLib(sender, text);
        ChatText chatText = new ChatText(text, hoverAction, hoverString);
        texts.add(chatText);
        return chatText;
    }

    /**
     * Add a <tt>ChatText</tt> to this <tt>ChatSection</tt> <p>
     * Text is found through the <tt>LangManager</tt> of the plugin using the library's
     * lang file.
     *
     * @param sender The <tt>CommandSender</tt> to get the locale from
     * @param text The string of text to add
     * @param hoverAction The <tt>HoverAction</tt> of the text
     * @param hoverString  The String of text for the hoverAction to use
     * @param clickAction The <tt>ClickEvent.Action</tt> of the text
     * @param clickString The String of text for the clickAction to use
     * @return A reference to the new <tt>ChatText</tt>
     */
    public ChatText addTextLangLib(CommandSender sender, String text, ClickEvent.Action clickAction, HoverEvent.Action hoverAction, String clickString, String hoverString)
    {
        text = plugin.langManager().getTextLib(sender, text);
        ChatText chatText = new ChatText(text, clickAction, hoverAction, clickString, hoverString);
        texts.add(chatText);
        return chatText;
    }

    /**
     * Add a <tt>ChatText</tt> to this <tt>ChatSection</tt> <p>
     * Text is found through the <tt>LangManager</tt> of the plugin using the library's
     * lang file.
     *
     * @param sender The <tt>CommandSender</tt> to get the locale from
     * @param text The string of text to add
     * @param index The index that the new <tt>ChatText</tt> will be added to
     * @return A reference to the new <tt>ChatText</tt>
     */
    public ChatText addTextLangLib(CommandSender sender, String text, int index)
    {
        text = plugin.langManager().getTextLib(sender, text);
        ChatText chatText = new ChatText(text);
        texts.add(index, chatText);
        return chatText;
    }

    /**
     * Add a <tt>ChatText</tt> to this <tt>ChatSection</tt> <p>
     * Text is found through the <tt>LangManager</tt> of the plugin using the library's
     * lang file.
     *
     * @param sender The <tt>CommandSender</tt> to get the locale from
     * @param text The string of text to add
     * @param clickAction The <tt>ClickEvent.Action</tt> of the text
     * @param clickString The String of text for the clickAction to use
     * @param index The index that the new <tt>ChatText</tt> will be added to
     * @return A reference to the new <tt>ChatText</tt>
     */
    public ChatText addTextLangLib(CommandSender sender, String text, ClickEvent.Action clickAction, String clickString, int index)
    {
        text = plugin.langManager().getTextLib(sender, text);
        ChatText chatText = new ChatText(text, clickAction, clickString);
        texts.add(index, chatText);
        return chatText;
    }

    /**
     * Add a <tt>ChatText</tt> to this <tt>ChatSection</tt> <p>
     * Text is found through the <tt>LangManager</tt> of the plugin using the library's
     * lang file.
     *
     * @param sender The <tt>CommandSender</tt> to get the locale from
     * @param text The string of text to add
     * @param hoverAction The <tt>HoverAction</tt> of the text
     * @param hoverString  The String of text for the hoverAction to use
     * @param index The index that the new <tt>ChatText</tt> will be added to
     * @return A reference to the new <tt>ChatText</tt>
     */
    public ChatText addTextLangLib(CommandSender sender, String text, HoverEvent.Action hoverAction, String hoverString, int index)
    {
        text = plugin.langManager().getTextLib(sender, text);
        ChatText chatText = new ChatText(text, hoverAction, hoverString);
        texts.add(index, chatText);
        return chatText;
    }

    /**
     * Add a <tt>ChatText</tt> to this <tt>ChatSection</tt> <p>
     * Text is found through the <tt>LangManager</tt> of the plugin using the library's
     * lang file.
     *
     * @param sender The <tt>CommandSender</tt> to get the locale from
     * @param text The string of text to add
     * @param hoverAction The <tt>HoverAction</tt> of the text
     * @param hoverString  The String of text for the hoverAction to use
     * @param clickAction The <tt>ClickEvent.Action</tt> of the text
     * @param clickString The String of text for the clickAction to use
     * @param index The index that the new <tt>ChatText</tt> will be added to
     * @return A reference to the new <tt>ChatText</tt>
     */
    public ChatText addTextLangLib(CommandSender sender, String text, ClickEvent.Action clickAction, HoverEvent.Action hoverAction, String clickString, String hoverString, int index)
    {
        text = plugin.langManager().getTextLib(sender, text);
        ChatText chatText = new ChatText(text, clickAction, hoverAction, clickString, hoverString);
        texts.add(index, chatText);
        return chatText;
    }

    /**
     * Add a <tt>ChatText</tt> to this <tt>ChatSection</tt> <p>
     * Text is found through the <tt>LangManager</tt> of the plugin using the library's
     * lang file.
     *
     * @param player The Player to get the locale from
     * @param text The string of text to add
     * @return A reference to the new <tt>ChatText</tt>
     */
    public ChatText addTextLangLib(Player player, String text)
    {
        text = plugin.langManager().getTextLib(player, text);
        ChatText chatText = new ChatText(text);
        texts.add(chatText);
        return chatText;
    }

    /**
     * Add a <tt>ChatText</tt> to this <tt>ChatSection</tt> <p>
     * Text is found through the <tt>LangManager</tt> of the plugin using the library's
     * lang file.
     *
     * @param player The Player to get the locale from
     * @param text The string of text to add
     * @param clickAction The <tt>ClickEvent.Action</tt> of the text
     * @param clickString The String of text for the clickAction to use
     * @return A reference to the new <tt>ChatText</tt>
     */
    public ChatText addTextLangLib(Player player, String text, ClickEvent.Action clickAction, String clickString)
    {
        text = plugin.langManager().getTextLib(player, text);
        ChatText chatText = new ChatText(text, clickAction, clickString);
        texts.add(chatText);
        return chatText;
    }

    /**
     * Add a <tt>ChatText</tt> to this <tt>ChatSection</tt> <p>
     * Text is found through the <tt>LangManager</tt> of the plugin using the library's
     * lang file.
     *
     * @param player The Player to get the locale from
     * @param text The string of text to add
     * @param hoverAction The <tt>HoverAction</tt> of the text
     * @param hoverString  The String of text for the hoverAction to use
     * @return A reference to the new <tt>ChatText</tt>
     */
    public ChatText addTextLangLib(Player player, String text, HoverEvent.Action hoverAction, String hoverString)
    {
        text = plugin.langManager().getTextLib(player, text);
        ChatText chatText = new ChatText(text, hoverAction, hoverString);
        texts.add(chatText);
        return chatText;
    }

    /**
     * Add a <tt>ChatText</tt> to this <tt>ChatSection</tt> <p>
     * Text is found through the <tt>LangManager</tt> of the plugin using the library's
     * lang file.
     *
     * @param player The Player to get the locale from
     * @param text The string of text to add
     * @param hoverAction The <tt>HoverAction</tt> of the text
     * @param hoverString  The String of text for the hoverAction to use
     * @param clickAction The <tt>ClickEvent.Action</tt> of the text
     * @param clickString The String of text for the clickAction to use
     * @return A reference to the new <tt>ChatText</tt>
     */
    public ChatText addTextLangLib(Player player, String text, ClickEvent.Action clickAction, HoverEvent.Action hoverAction, String clickString, String hoverString)
    {
        text = plugin.langManager().getTextLib(player, text);
        ChatText chatText = new ChatText(text, clickAction, hoverAction, clickString, hoverString);
        texts.add(chatText);
        return chatText;
    }

    /**
     * Add a <tt>ChatText</tt> to this <tt>ChatSection</tt> <p>
     * Text is found through the <tt>LangManager</tt> of the plugin using the library's
     * lang file.
     *
     * @param player The Player to get the locale from
     * @param text The string of text to add
     * @param index The index that the new <tt>ChatText</tt> will be added to
     * @return A reference to the new <tt>ChatText</tt>
     */
    public ChatText addTextLangLib(Player player, String text, int index)
    {
        text = plugin.langManager().getTextLib(player, text);
        ChatText chatText = new ChatText(text);
        texts.add(index, chatText);
        return chatText;
    }

    /**
     * Add a <tt>ChatText</tt> to this <tt>ChatSection</tt> <p>
     * Text is found through the <tt>LangManager</tt> of the plugin using the library's
     * lang file.
     *
     * @param player The Player to get the locale from
     * @param text The string of text to add
     * @param clickAction The <tt>ClickEvent.Action</tt> of the text
     * @param clickString The String of text for the clickAction to use
     * @param index The index that the new <tt>ChatText</tt> will be added to
     * @return A reference to the new <tt>ChatText</tt>
     */
    public ChatText addTextLangLib(Player player, String text, ClickEvent.Action clickAction, String clickString, int index)
    {
        text = plugin.langManager().getTextLib(player, text);
        ChatText chatText = new ChatText(text, clickAction, clickString);
        texts.add(index, chatText);
        return chatText;
    }

    /**
     * Add a <tt>ChatText</tt> to this <tt>ChatSection</tt> <p>
     * Text is found through the <tt>LangManager</tt> of the plugin using the library's
     * lang file.
     *
     * @param player The Player to get the locale from
     * @param text The string of text to add
     * @param hoverAction The <tt>HoverAction</tt> of the text
     * @param hoverString  The String of text for the hoverAction to use
     * @param index The index that the new <tt>ChatText</tt> will be added to
     * @return A reference to the new <tt>ChatText</tt>
     */
    public ChatText addTextLangLib(Player player, String text, HoverEvent.Action hoverAction, String hoverString, int index)
    {
        text = plugin.langManager().getTextLib(player, text);
        ChatText chatText = new ChatText(text, hoverAction, hoverString);
        texts.add(index, chatText);
        return chatText;
    }

    /**
     * Add a <tt>ChatText</tt> to this <tt>ChatSection</tt> <p>
     * Text is found through the <tt>LangManager</tt> of the plugin using the library's
     * lang file.
     *
     * @param player The Player to get the locale from
     * @param text The string of text to add
     * @param hoverAction The <tt>HoverAction</tt> of the text
     * @param hoverString  The String of text for the hoverAction to use
     * @param clickAction The <tt>ClickEvent.Action</tt> of the text
     * @param clickString The String of text for the clickAction to use
     * @param index The index that the new <tt>ChatText</tt> will be added to
     * @return A reference to the new <tt>ChatText</tt>
     */
    public ChatText addTextLangLib(Player player, String text, ClickEvent.Action clickAction, HoverEvent.Action hoverAction, String clickString, String hoverString, int index)
    {
        text = plugin.langManager().getTextLib(player, text);
        ChatText chatText = new ChatText(text, clickAction, hoverAction, clickString, hoverString);
        texts.add(index, chatText);
        return chatText;
    }

    /**
     * Remove a <tt>ChatText</tt> from this <tt>ChatSection</tt>
     *
     * @param text The <tt>ChatText</tt> to find and remove
     */
    public void removeText(ChatText text)
    {
        texts.remove(text);
    }

    /**
     * Remove a string of text from this <tt>ChatSection</tt>
     *
     * @param text The string to find and remove
     */
    public void removeText(String text)
    {
        for(int i = 0; i < texts.size(); ++i)
        {
            ChatText chatText = texts.get(i);
            if(chatText == null) continue;
            String curText = chatText.getText();
            if(!text.equals(curText)) continue;
            texts.remove(i);
            return;
        }
    }

    /**
     * Returns whether this <tt>ChatSection</tt> contains a
     * String of text in it.
     *
     * @param text The string of text to search for
     * @return Whether the string was found or not
     */
    public boolean containsText(String text)
    {
        for(int i = 0; i < texts.size(); ++i)
        {
            ChatText chatText = texts.get(i);
            if(chatText == null) continue;
            String curText = chatText.getText();
            if(!text.equals(curText)) continue;
            return true;
        }
        return false;
    }

    /**
     * Returns whether this <tt>ChatSection</tt> contains a specific
     * <tt>ChatText</tt> in it.
     *
     * @param text The <tt>ChatText</tt> to search for
     * @return Whether the <tt>ChatText</tt> was found or not
     */
    public boolean containsText(ChatText text)
    {
        return texts.contains(text);
    }

    /**
     * Returns whether this <tt>ChatSection</tt> has been converted or not
     *
     * @return Whether this section has been converted or not
     */
    public boolean isConverted()
    {
        return converted;
    }

    /**
     * Convert this <tt>ChatSection</tt> into the Bungee chat API's <tt>BaseComponent</tt>s
     * for printing to chat. <p>
     * This method does the heavy lifting of converting all of the text from this section
     * to a different API that does the heavy lifting itself.
     *
     * @return A reference to this <tt>ChatSection</tt>
     */
    public ChatSection convert()
    {
        List<BaseComponent[]> baseComponents = new ArrayList<>();
        for(ChatText chatText : texts)
        {
            String text = chatText.getText();
            ChatTextType type = chatText.getType();
            switch(type)
            {
                case NORMAL:
                {
                    BaseComponent[] components = chat.getBaseComponentArray(text);
                    baseComponents.add(components);
                } break;
                case CLICKABLE:
                {
                    BaseComponent[] components = chat.getBaseComponentArray(text);
                    ClickEvent.Action clickAction = chatText.getClickAction();
                    String clickString = chatText.getClickString();
                    ClickEvent event = chat.getClickEvent(clickAction, clickString);
                    chat.setClickEvent(components, event);
                    baseComponents.add(components);
                } break;
                case HOVERABLE:
                {
                    BaseComponent[] components = chat.getBaseComponentArray(text);
                    HoverEvent.Action hoverAction = chatText.getHoverAction();
                    String hoverString = chatText.getHoverString();
                    HoverEvent event = chat.getHoverEvent(hoverAction, hoverString);
                    chat.setHoverEvent(components, event);
                    baseComponents.add(components);
                } break;
                case HOVERCLICKABLE:
                {
                    BaseComponent[] components = chat.getBaseComponentArray(text);
                    HoverEvent.Action hoverAction = chatText.getHoverAction();
                    ClickEvent.Action clickAction = chatText.getClickAction();
                    String hoverString = chatText.getHoverString();
                    String clickString = chatText.getClickString();
                    HoverEvent hoverEvent = chat.getHoverEvent(hoverAction, hoverString);
                    ClickEvent clickEvent = chat.getClickEvent(clickAction, clickString);
                    chat.setHoverEvent(components, hoverEvent);
                    chat.setClickEvent(components, clickEvent);
                    baseComponents.add(components);
                } break;
            }
        }
        BaseComponent[][] newComponents = baseComponents.toArray(new BaseComponent[0][]);
        components = chat.combineComponents(newComponents);
        converted = true;
        return this;
    }

    /**
     * Print this <tt>ChatSection</tt> to console
     *
     * @return A reference to this <tt>ChatSection</tt>
     */
    public ChatSection printToConsole()
    {
        if(!converted) convert();
        Bukkit.getConsoleSender().spigot().sendMessage(components);
        return this;
    }

    /**
     * Broadcast this <tt>ChatSection</tt> to the server
     *
     * @return A reference to this <tt>ChatSection</tt>
     */
    public ChatSection broadcast()
    {
        if(!converted) convert();
        Bukkit.spigot().broadcast(components);
        return this;
    }

    /**
     * Print this <tt>ChatSection</tt> to a <tt>CommandSender</tt>
     *
     * @param sender The <tt>CommandSender</tt> to send this section to
     * @return A reference to this <tt>ChatSection</tt>
     */
    public ChatSection print(CommandSender sender)
    {
        if(!converted) convert();
        sender.spigot().sendMessage(components);
        return this;
    }

    /**
     * Print this <tt>ChatSection</tt> to a Player
     *
     * @param player The Player to send this section to
     * @return A reference to this <tt>ChatSection</tt>
     */
    public ChatSection print(Player player)
    {
        if(!converted) convert();
        player.spigot().sendMessage(components);
        return this;
    }
}
