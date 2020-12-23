package com.mikedeejay2.mikedeejay2lib.text.title;

import com.mikedeejay2.mikedeejay2lib.util.chat.Chat;
import org.bukkit.entity.Player;

/**
 * A single frame of a <tt>TitleSystem</tt>
 *
 * @author Mikedeejay2
 */
public class TitleFrame
{
    // The title String of the frame
    protected String title;
    // The subtitle String of the frame
    protected String subtitle;
    // The fade in time of the frame
    protected int fadeIn;
    // The stay time of frame
    protected int stay;
    // The fade out time of the frame
    protected int fadeOut;
    // The maximum period of ticks that the frame is displayed
    protected long period;

    /**
     * @param title The title String of the frame
     * @param subtitle The subtitle String of the frame
     * @param fadeIn The fade in time of the frame
     * @param stay The stay time of frame
     * @param fadeOut The fade out time of the frame
     * @param period The maximum period of ticks that the frame is displayed
     */
    public TitleFrame(String title, String subtitle, int fadeIn, int stay, int fadeOut, long period)
    {
        this.title = Chat.chat(title);
        this.subtitle = Chat.chat(subtitle);
        this.fadeIn = fadeIn;
        this.stay = stay;
        this.fadeOut = fadeOut;
        this.period = period;
    }

    /**
     * Display this frame to a variable amount of players
     *
     * @param players The players to display this frame to
     */
    public void display(Player... players)
    {
        for(Player player : players)
        {
            player.sendTitle(title, subtitle, fadeIn, stay, fadeOut);
        }
    }

    /**
     * Get the title String of this frame
     *
     * @return The title String
     */
    public String getTitle()
    {
        return title;
    }

    /**
     * Set the title String of this frame
     *
     * @param title The new title String
     */
    public void setTitle(String title)
    {
        this.title = title;
    }

    /**
     * Get the subtitle String of this frame
     *
     * @return The subtitle String
     */
    public String getSubtitle()
    {
        return subtitle;
    }

    /**
     * Set the subtitle String of this frame
     *
     * @param subtitle The new subtitle String
     */
    public void setSubtitle(String subtitle)
    {
        this.subtitle = subtitle;
    }

    /**
     * Get the fade in length of this frame
     *
     * @return The fade in length of this frame
     */
    public int getFadeIn()
    {
        return fadeIn;
    }

    /**
     * Set the fade in length of this frame
     *
     * @param fadeIn The new fade in length
     */
    public void setFadeIn(int fadeIn)
    {
        this.fadeIn = fadeIn;
    }

    /**
     * Get the stay length of this frame
     *
     * @return The stay length
     */
    public int getStay()
    {
        return stay;
    }

    /**
     * Set the stay length of this frame
     *
     * @param stay The new stay length
     */
    public void setStay(int stay)
    {
        this.stay = stay;
    }

    /**
     * Get the fade out length of this frame
     *
     * @return The fade out length
     */
    public int getFadeOut()
    {
        return fadeOut;
    }

    /**
     * Set the fade out length of this frame
     *
     * @param fadeOut The new fade out length
     */
    public void setFadeOut(int fadeOut)
    {
        this.fadeOut = fadeOut;
    }

    /**
     * Get the period of this frame
     *
     * @return The period
     */
    public long getPeriod()
    {
        return period;
    }

    /**
     * Set the period of this frame
     *
     * @param period The new period
     */
    public void setPeriod(long period)
    {
        this.period = period;
    }
}
