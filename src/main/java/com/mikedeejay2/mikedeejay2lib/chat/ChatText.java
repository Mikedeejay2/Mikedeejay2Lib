package com.mikedeejay2.mikedeejay2lib.chat;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;

/**
 * A class for holding a single piece of chat text.
 * This class is used in {@link ChatCanvas}
 *
 * @author Mikedeejay2
 */
public class ChatText
{
    protected String text;
    protected ChatTextType type;
    protected ClickEvent.Action clickAction;
    protected HoverEvent.Action hoverAction;
    protected String clickString;
    protected String hoverString;

    public ChatText(String text)
    {
        this.text = text;
        this.clickAction = null;
        this.hoverAction = null;
        this.clickString = null;
        this.hoverString = null;
        this.type = ChatTextType.NORMAL;
    }

    public ChatText(String text, ClickEvent.Action clickAction, String clickString)
    {
        this.text = text;
        this.clickAction = clickAction;
        this.hoverAction = null;
        this.clickString = clickString;
        this.hoverString = null;
        this.type = ChatTextType.CLICKABLE;
    }

    public ChatText(String text, HoverEvent.Action hoverAction, String hoverString)
    {
        this.text = text;
        this.clickAction = null;
        this.hoverAction = hoverAction;
        this.clickString = null;
        this.hoverString = hoverString;
        this.type = ChatTextType.HOVERABLE;
    }

    public ChatText(String text, ClickEvent.Action clickAction, HoverEvent.Action hoverAction, String clickString, String hoverString)
    {
        this.text = text;
        this.clickAction = clickAction;
        this.hoverAction = hoverAction;
        this.clickString = clickString;
        this.hoverString = hoverString;
        this.type = ChatTextType.HOVERCLICKABLE;
    }

    /**
     * Get the text String held in this object
     *
     * @return The text String
     */
    public String getText()
    {
        return text;
    }

    /**
     * Set the text String held in this object
     *
     * @param text The new text String
     */
    public ChatText setText(String text)
    {
        this.text = text;

        return this;
    }

    public ClickEvent.Action getClickAction()
    {
        return clickAction;
    }

    public ChatText setClickAction(ClickEvent.Action clickAction)
    {
        this.clickAction = clickAction;

        return this;
    }

    public HoverEvent.Action getHoverAction()
    {
        return hoverAction;
    }

    public ChatText setHoverAction(HoverEvent.Action hoverAction)
    {
        this.hoverAction = hoverAction;

        return this;
    }

    public String getClickString()
    {
        return clickString;
    }

    public ChatText setClickString(String clickString)
    {
        this.clickString = clickString;

        return this;
    }

    public String getHoverString()
    {
        return hoverString;
    }

    public ChatText setHoverString(String hoverString)
    {
        this.hoverString = hoverString;

        return this;
    }

    public ChatTextType getType()
    {
        return type;
    }

    public ChatText updateType()
    {
        if(clickAction == null && hoverAction == null)
        {
            type = ChatTextType.NORMAL;
        }
        else if(clickAction != null && hoverAction != null)
        {
            type = ChatTextType.HOVERCLICKABLE;
        }
        else if(clickAction != null)
        {
            type = ChatTextType.CLICKABLE;
        }
        else
        {
            type = ChatTextType.HOVERABLE;
        }

        return this;
    }
}
