package com.mikedeejay2.mikedeejay2lib.text.actionbar;

import com.mikedeejay2.mikedeejay2lib.util.chat.Chat;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;

/**
 * A frame of an Action Bar that holds text and show time for the frame.
 * Part of the ActionBar system.
 *
 * @see ActionBar
 *
 * @author Mikedeejay2
 */
public class ActionBarFrame
{
    // The String of text that this frame holds
    protected String text;
    // The period (in ticks) of this frame
    protected long period;

    public ActionBarFrame(String text, long period)
    {
        this.text = Chat.chat(text);
        this.period = Math.max(1, period);
    }

    /**
     * Display this frame to a list of players
     *
     * @param players The array of players to display this frame to
     */
    public void display(Player... players)
    {
        BaseComponent[] components = TextComponent.fromLegacyText(text);
        for(Player player : players)
        {
            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, components);
        }
    }

    /**
     * Get the text of this frame
     *
     * @return The text of this frame
     */
    public String getText()
    {
        return text;
    }

    /**
     * Set the text of this frame
     *
     * @param text The new text for this frame
     */
    public void setText(String text)
    {
        this.text = Chat.chat(text);
    }

    /**
     * Get the period (time between this frame and the next frame) of this frame
     *
     * @return The period of this frame
     */
    public long getPeriod()
    {
        return period;
    }

    /**
     * Set the period (time between this frame and the next frame) of this frame
     *
     * @param period The new period of this frame
     */
    public void setPeriod(long period)
    {
        this.period = Math.max(1, period);
    }
}
