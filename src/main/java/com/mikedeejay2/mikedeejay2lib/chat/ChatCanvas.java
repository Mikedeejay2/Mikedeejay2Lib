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
 * A chat canvas is a text structure for Minecraft's chat system to make
 * messages look good and make using localization, hover events, and other
 * hard to manage text features easier to use.
 *
 * @author Mikedeejay2
 */
public class ChatCanvas
{
    protected final PluginBase plugin;
    protected final Chat chat;
    protected List<ChatText> texts;
    protected BaseComponent[] components;
    protected boolean converted;

    public ChatCanvas(PluginBase plugin)
    {
        this.plugin = plugin;
        this.chat = plugin.chat();
        this.texts = new ArrayList<>();
        this.converted = false;
    }

    public ChatText addText(String text)
    {
        ChatText chatText = new ChatText(text);
        texts.add(chatText);
        return chatText;
    }

    public ChatText addText(String text, ClickEvent.Action clickAction, String clickString)
    {
        ChatText chatText = new ChatText(text, clickAction, clickString);
        texts.add(chatText);
        return chatText;
    }

    public ChatText addText(String text, HoverEvent.Action hoverAction, String hoverString)
    {
        ChatText chatText = new ChatText(text, hoverAction, hoverString);
        texts.add(chatText);
        return chatText;
    }

    public ChatText addText(String text, ClickEvent.Action clickAction, HoverEvent.Action hoverAction, String clickString, String hoverString)
    {
        ChatText chatText = new ChatText(text, clickAction, hoverAction, clickString, hoverString);
        texts.add(chatText);
        return chatText;
    }

    public ChatText addText(String text, int index)
    {
        ChatText chatText = new ChatText(text);
        texts.add(index, chatText);
        return chatText;
    }

    public ChatText addText(String text, ClickEvent.Action clickAction, String clickString, int index)
    {
        ChatText chatText = new ChatText(text, clickAction, clickString);
        texts.add(index, chatText);
        return chatText;
    }

    public ChatText addText(String text, HoverEvent.Action hoverAction, String hoverString, int index)
    {
        ChatText chatText = new ChatText(text, hoverAction, hoverString);
        texts.add(index, chatText);
        return chatText;
    }

    public ChatText addText(String text, ClickEvent.Action clickAction, HoverEvent.Action hoverAction, String clickString, String hoverString, int index)
    {
        ChatText chatText = new ChatText(text, clickAction, hoverAction, clickString, hoverString);
        texts.add(index, chatText);
        return chatText;
    }

    public ChatText addTextLang(String text)
    {
        text = plugin.langManager().getText(text);
        ChatText chatText = new ChatText(text);
        texts.add(chatText);
        return chatText;
    }

    public ChatText addTextLang(String text, ClickEvent.Action clickAction, String clickString)
    {
        text = plugin.langManager().getText(text);
        ChatText chatText = new ChatText(text, clickAction, clickString);
        texts.add(chatText);
        return chatText;
    }

    public ChatText addTextLang(String text, HoverEvent.Action hoverAction, String hoverString)
    {
        text = plugin.langManager().getText(text);
        ChatText chatText = new ChatText(text, hoverAction, hoverString);
        texts.add(chatText);
        return chatText;
    }

    public ChatText addTextLang(String text, ClickEvent.Action clickAction, HoverEvent.Action hoverAction, String clickString, String hoverString)
    {
        text = plugin.langManager().getText(text);
        ChatText chatText = new ChatText(text, clickAction, hoverAction, clickString, hoverString);
        texts.add(chatText);
        return chatText;
    }

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

    public ChatText addTextLang(String text, HoverEvent.Action hoverAction, String hoverString, int index)
    {
        text = plugin.langManager().getText(text);
        ChatText chatText = new ChatText(text, hoverAction, hoverString);
        texts.add(index, chatText);
        return chatText;
    }

    public ChatText addTextLang(String text, ClickEvent.Action clickAction, HoverEvent.Action hoverAction, String clickString, String hoverString, int index)
    {
        text = plugin.langManager().getText(text);
        ChatText chatText = new ChatText(text, clickAction, hoverAction, clickString, hoverString);
        texts.add(index, chatText);
        return chatText;
    }

    public ChatText addTextLang(CommandSender sender, String text)
    {
        text = plugin.langManager().getText(sender, text);
        ChatText chatText = new ChatText(text);
        texts.add(chatText);
        return chatText;
    }

    public ChatText addTextLang(CommandSender sender, String text, ClickEvent.Action clickAction, String clickString)
    {
        text = plugin.langManager().getText(sender, text);
        ChatText chatText = new ChatText(text, clickAction, clickString);
        texts.add(chatText);
        return chatText;
    }

    public ChatText addTextLang(CommandSender sender, String text, HoverEvent.Action hoverAction, String hoverString)
    {
        text = plugin.langManager().getText(sender, text);
        ChatText chatText = new ChatText(text, hoverAction, hoverString);
        texts.add(chatText);
        return chatText;
    }

    public ChatText addTextLang(CommandSender sender, String text, ClickEvent.Action clickAction, HoverEvent.Action hoverAction, String clickString, String hoverString)
    {
        text = plugin.langManager().getText(sender, text);
        ChatText chatText = new ChatText(text, clickAction, hoverAction, clickString, hoverString);
        texts.add(chatText);
        return chatText;
    }

    public ChatText addTextLang(CommandSender sender, String text, int index)
    {
        text = plugin.langManager().getText(sender, text);
        ChatText chatText = new ChatText(text);
        texts.add(index, chatText);
        return chatText;
    }

    public ChatText addTextLang(CommandSender sender, String text, ClickEvent.Action clickAction, String clickString, int index)
    {
        text = plugin.langManager().getText(sender, text);
        ChatText chatText = new ChatText(text, clickAction, clickString);
        texts.add(index, chatText);
        return chatText;
    }

    public ChatText addTextLang(CommandSender sender, String text, HoverEvent.Action hoverAction, String hoverString, int index)
    {
        text = plugin.langManager().getText(sender, text);
        ChatText chatText = new ChatText(text, hoverAction, hoverString);
        texts.add(index, chatText);
        return chatText;
    }

    public ChatText addTextLang(CommandSender sender, String text, ClickEvent.Action clickAction, HoverEvent.Action hoverAction, String clickString, String hoverString, int index)
    {
        text = plugin.langManager().getText(sender, text);
        ChatText chatText = new ChatText(text, clickAction, hoverAction, clickString, hoverString);
        texts.add(index, chatText);
        return chatText;
    }

    public ChatText addTextLang(Player player, String text)
    {
        text = plugin.langManager().getText(player, text);
        ChatText chatText = new ChatText(text);
        texts.add(chatText);
        return chatText;
    }

    public ChatText addTextLang(Player player, String text, ClickEvent.Action clickAction, String clickString)
    {
        text = plugin.langManager().getText(player, text);
        ChatText chatText = new ChatText(text, clickAction, clickString);
        texts.add(chatText);
        return chatText;
    }

    public ChatText addTextLang(Player player, String text, HoverEvent.Action hoverAction, String hoverString)
    {
        text = plugin.langManager().getText(player, text);
        ChatText chatText = new ChatText(text, hoverAction, hoverString);
        texts.add(chatText);
        return chatText;
    }

    public ChatText addTextLang(Player player, String text, ClickEvent.Action clickAction, HoverEvent.Action hoverAction, String clickString, String hoverString)
    {
        text = plugin.langManager().getText(player, text);
        ChatText chatText = new ChatText(text, clickAction, hoverAction, clickString, hoverString);
        texts.add(chatText);
        return chatText;
    }

    public ChatText addTextLang(Player player, String text, int index)
    {
        text = plugin.langManager().getText(player, text);
        ChatText chatText = new ChatText(text);
        texts.add(index, chatText);
        return chatText;
    }

    public ChatText addTextLang(Player player, String text, ClickEvent.Action clickAction, String clickString, int index)
    {
        text = plugin.langManager().getText(player, text);
        ChatText chatText = new ChatText(text, clickAction, clickString);
        texts.add(index, chatText);
        return chatText;
    }

    public ChatText addTextLang(Player player, String text, HoverEvent.Action hoverAction, String hoverString, int index)
    {
        text = plugin.langManager().getText(player, text);
        ChatText chatText = new ChatText(text, hoverAction, hoverString);
        texts.add(index, chatText);
        return chatText;
    }

    public ChatText addTextLang(Player player, String text, ClickEvent.Action clickAction, HoverEvent.Action hoverAction, String clickString, String hoverString, int index)
    {
        text = plugin.langManager().getText(player, text);
        ChatText chatText = new ChatText(text, clickAction, hoverAction, clickString, hoverString);
        texts.add(index, chatText);
        return chatText;
    }

    public ChatText addTextLangLib(String text)
    {
        text = plugin.langManager().getTextLib(text);
        ChatText chatText = new ChatText(text);
        texts.add(chatText);
        return chatText;
    }

    public ChatText addTextLangLib(String text, ClickEvent.Action clickAction, String clickString)
    {
        text = plugin.langManager().getTextLib(text);
        ChatText chatText = new ChatText(text, clickAction, clickString);
        texts.add(chatText);
        return chatText;
    }

    public ChatText addTextLangLib(String text, HoverEvent.Action hoverAction, String hoverString)
    {
        text = plugin.langManager().getTextLib(text);
        ChatText chatText = new ChatText(text, hoverAction, hoverString);
        texts.add(chatText);
        return chatText;
    }

    public ChatText addTextLangLib(String text, ClickEvent.Action clickAction, HoverEvent.Action hoverAction, String clickString, String hoverString)
    {
        text = plugin.langManager().getTextLib(text);
        ChatText chatText = new ChatText(text, clickAction, hoverAction, clickString, hoverString);
        texts.add(chatText);
        return chatText;
    }

    public ChatText addTextLangLib(String text, int index)
    {
        text = plugin.langManager().getTextLib(text);
        ChatText chatText = new ChatText(text);
        texts.add(index, chatText);
        return chatText;
    }

    public ChatText addTextLangLib(String text, ClickEvent.Action clickAction, String clickString, int index)
    {
        text = plugin.langManager().getTextLib(text);
        ChatText chatText = new ChatText(text, clickAction, clickString);
        texts.add(index, chatText);
        return chatText;
    }

    public ChatText addTextLangLib(String text, HoverEvent.Action hoverAction, String hoverString, int index)
    {
        text = plugin.langManager().getTextLib(text);
        ChatText chatText = new ChatText(text, hoverAction, hoverString);
        texts.add(index, chatText);
        return chatText;
    }

    public ChatText addTextLangLib(String text, ClickEvent.Action clickAction, HoverEvent.Action hoverAction, String clickString, String hoverString, int index)
    {
        text = plugin.langManager().getTextLib(text);
        ChatText chatText = new ChatText(text, clickAction, hoverAction, clickString, hoverString);
        texts.add(index, chatText);
        return chatText;
    }

    public ChatText addTextLangLib(CommandSender sender, String text)
    {
        text = plugin.langManager().getTextLib(sender, text);
        ChatText chatText = new ChatText(text);
        texts.add(chatText);
        return chatText;
    }

    public ChatText addTextLangLib(CommandSender sender, String text, ClickEvent.Action clickAction, String clickString)
    {
        text = plugin.langManager().getTextLib(sender, text);
        ChatText chatText = new ChatText(text, clickAction, clickString);
        texts.add(chatText);
        return chatText;
    }

    public ChatText addTextLangLib(CommandSender sender, String text, HoverEvent.Action hoverAction, String hoverString)
    {
        text = plugin.langManager().getTextLib(sender, text);
        ChatText chatText = new ChatText(text, hoverAction, hoverString);
        texts.add(chatText);
        return chatText;
    }

    public ChatText addTextLangLib(CommandSender sender, String text, ClickEvent.Action clickAction, HoverEvent.Action hoverAction, String clickString, String hoverString)
    {
        text = plugin.langManager().getTextLib(sender, text);
        ChatText chatText = new ChatText(text, clickAction, hoverAction, clickString, hoverString);
        texts.add(chatText);
        return chatText;
    }

    public ChatText addTextLangLib(CommandSender sender, String text, int index)
    {
        text = plugin.langManager().getTextLib(sender, text);
        ChatText chatText = new ChatText(text);
        texts.add(index, chatText);
        return chatText;
    }

    public ChatText addTextLangLib(CommandSender sender, String text, ClickEvent.Action clickAction, String clickString, int index)
    {
        text = plugin.langManager().getTextLib(sender, text);
        ChatText chatText = new ChatText(text, clickAction, clickString);
        texts.add(index, chatText);
        return chatText;
    }

    public ChatText addTextLangLib(CommandSender sender, String text, HoverEvent.Action hoverAction, String hoverString, int index)
    {
        text = plugin.langManager().getTextLib(sender, text);
        ChatText chatText = new ChatText(text, hoverAction, hoverString);
        texts.add(index, chatText);
        return chatText;
    }

    public ChatText addTextLangLib(CommandSender sender, String text, ClickEvent.Action clickAction, HoverEvent.Action hoverAction, String clickString, String hoverString, int index)
    {
        text = plugin.langManager().getTextLib(sender, text);
        ChatText chatText = new ChatText(text, clickAction, hoverAction, clickString, hoverString);
        texts.add(index, chatText);
        return chatText;
    }

    public ChatText addTextLangLib(Player player, String text)
    {
        text = plugin.langManager().getTextLib(player, text);
        ChatText chatText = new ChatText(text);
        texts.add(chatText);
        return chatText;
    }

    public ChatText addTextLangLib(Player player, String text, ClickEvent.Action clickAction, String clickString)
    {
        text = plugin.langManager().getTextLib(player, text);
        ChatText chatText = new ChatText(text, clickAction, clickString);
        texts.add(chatText);
        return chatText;
    }

    public ChatText addTextLangLib(Player player, String text, HoverEvent.Action hoverAction, String hoverString)
    {
        text = plugin.langManager().getTextLib(player, text);
        ChatText chatText = new ChatText(text, hoverAction, hoverString);
        texts.add(chatText);
        return chatText;
    }

    public ChatText addTextLangLib(Player player, String text, ClickEvent.Action clickAction, HoverEvent.Action hoverAction, String clickString, String hoverString)
    {
        text = plugin.langManager().getTextLib(player, text);
        ChatText chatText = new ChatText(text, clickAction, hoverAction, clickString, hoverString);
        texts.add(chatText);
        return chatText;
    }

    public ChatText addTextLangLib(Player player, String text, int index)
    {
        text = plugin.langManager().getTextLib(player, text);
        ChatText chatText = new ChatText(text);
        texts.add(index, chatText);
        return chatText;
    }

    public ChatText addTextLangLib(Player player, String text, ClickEvent.Action clickAction, String clickString, int index)
    {
        text = plugin.langManager().getTextLib(player, text);
        ChatText chatText = new ChatText(text, clickAction, clickString);
        texts.add(index, chatText);
        return chatText;
    }

    public ChatText addTextLangLib(Player player, String text, HoverEvent.Action hoverAction, String hoverString, int index)
    {
        text = plugin.langManager().getTextLib(player, text);
        ChatText chatText = new ChatText(text, hoverAction, hoverString);
        texts.add(index, chatText);
        return chatText;
    }

    public ChatText addTextLangLib(Player player, String text, ClickEvent.Action clickAction, HoverEvent.Action hoverAction, String clickString, String hoverString, int index)
    {
        text = plugin.langManager().getTextLib(player, text);
        ChatText chatText = new ChatText(text, clickAction, hoverAction, clickString, hoverString);
        texts.add(index, chatText);
        return chatText;
    }

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

    public void removeText(ChatText text)
    {
        texts.remove(text);
    }

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

    public boolean containsText(ChatText text)
    {
        return texts.contains(text);
    }

    public boolean isConverted()
    {
        return converted;
    }

    public ChatCanvas convert()
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

    public ChatCanvas printToConsole()
    {
        if(!converted) convert();
        Bukkit.getConsoleSender().spigot().sendMessage(components);
        return this;
    }

    public ChatCanvas broadcast()
    {
        if(!converted) convert();
        Bukkit.spigot().broadcast(components);
        return this;
    }

    public ChatCanvas print(CommandSender sender)
    {
        if(!converted) convert();
        sender.spigot().sendMessage(components);
        return this;
    }

    public ChatCanvas print(Player player)
    {
        if(!converted) convert();
        player.spigot().sendMessage(components);
        return this;
    }
}
