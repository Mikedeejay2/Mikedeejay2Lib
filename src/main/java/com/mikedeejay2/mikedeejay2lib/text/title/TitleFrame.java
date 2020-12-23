package com.mikedeejay2.mikedeejay2lib.text.title;

import com.mikedeejay2.mikedeejay2lib.util.chat.Chat;
import org.bukkit.entity.Player;

public class TitleFrame
{
    protected String title;
    protected String subtitle;
    protected int fadeIn;
    protected int stay;
    protected int fadeOut;
    protected long period;

    public TitleFrame(String title, String subtitle, int fadeIn, int stay, int fadeOut, long period)
    {
        this.title = Chat.chat(title);
        this.subtitle = Chat.chat(subtitle);
        this.fadeIn = fadeIn;
        this.stay = stay;
        this.fadeOut = fadeOut;
        this.period = period;
    }

    public void display(Player... players)
    {
        for(Player player : players)
        {
            player.sendTitle(title, subtitle, fadeIn, stay, fadeOut);
        }
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getSubtitle()
    {
        return subtitle;
    }

    public void setSubtitle(String subtitle)
    {
        this.subtitle = subtitle;
    }

    public int getFadeIn()
    {
        return fadeIn;
    }

    public void setFadeIn(int fadeIn)
    {
        this.fadeIn = fadeIn;
    }

    public int getStay()
    {
        return stay;
    }

    public void setStay(int stay)
    {
        this.stay = stay;
    }

    public int getFadeOut()
    {
        return fadeOut;
    }

    public void setFadeOut(int fadeOut)
    {
        this.fadeOut = fadeOut;
    }

    public long getPeriod()
    {
        return period;
    }

    public void setPeriod(long period)
    {
        this.period = period;
    }
}
