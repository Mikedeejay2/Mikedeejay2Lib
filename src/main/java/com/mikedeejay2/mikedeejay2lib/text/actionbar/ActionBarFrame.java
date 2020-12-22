package com.mikedeejay2.mikedeejay2lib.text.actionbar;

import com.mikedeejay2.mikedeejay2lib.util.chat.Chat;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;

public class ActionBarFrame
{
    protected String text;
    protected long period;

    public ActionBarFrame(String text, long period)
    {
        this.text = Chat.chat(text);
        this.period = Math.max(1, period);
    }

    public void display(Player... players)
    {
        BaseComponent[] components = TextComponent.fromLegacyText(text);
        for(Player player : players)
        {
            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, components);
        }
    }

    public String getText()
    {
        return text;
    }

    public void setText(String text)
    {
        this.text = Chat.chat(text);
    }

    public long getPeriod()
    {
        return period;
    }

    public void setPeriod(long period)
    {
        this.period = Math.max(1, period);
    }
}
