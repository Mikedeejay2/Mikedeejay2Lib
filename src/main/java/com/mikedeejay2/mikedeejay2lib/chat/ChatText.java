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

    /**
     * Get the click action of this <tt>ChatText</tt>
     *
     * @return The <tt>ClickEvent.Action</tt>, null if not available
     */
    public ClickEvent.Action getClickAction()
    {
        return clickAction;
    }

    /**
     * Set the click action for this <tt>ChatText</tt>
     *
     * @param clickAction The click String to set
     * @return A reference to this <tt>ChatText</tt>
     */
    public ChatText setClickAction(ClickEvent.Action clickAction)
    {
        this.clickAction = clickAction;

        return this;
    }

    /**
     * Get the hover action of this <tt>ChatText</tt>
     *
     * @return The <tt>HoverEvent.Action</tt>, null if not available
     */
    public HoverEvent.Action getHoverAction()
    {
        return hoverAction;
    }

    /**
     * Set the hover action for this <tt>ChatText</tt>
     *
     * @param hoverAction The hover action to set
     * @return A reference to this <tt>ChatText</tt>
     */
    public ChatText setHoverAction(HoverEvent.Action hoverAction)
    {
        this.hoverAction = hoverAction;

        return this;
    }

    /**
     * Get the click String of this <tt>ChatText</tt>
     *
     * @return The click String, null if not available
     */
    public String getClickString()
    {
        return clickString;
    }

    /**
     * Set the click String for this <tt>ChatText</tt>
     *
     * @param clickString The click String to set
     * @return A reference to this <tt>ChatText</tt>
     */
    public ChatText setClickString(String clickString)
    {
        this.clickString = clickString;

        return this;
    }

    /**
     * Get the hover String of this <tt>ChatText</tt>
     *
     * @return The hover String, null if not available
     */
    public String getHoverString()
    {
        return hoverString;
    }

    /**
     * Set the hover String for this <tt>ChatText</tt>
     *
     * @param hoverString The hover String to set
     * @return A reference to this <tt>ChatText</tt>
     */
    public ChatText setHoverString(String hoverString)
    {
        this.hoverString = hoverString;

        return this;
    }

    /**
     * Get the <tt>ChatTextType</tt> that this <tt>ChatText</tt> is
     *
     * @return The <tt>ChatTextType</tt> for this object
     */
    public ChatTextType getType()
    {
        return type;
    }

    /**
     * Update the <tt>ChatTextType</tt> of this text.
     * This should be called if new data was added to this text since
     * the <tt>ChatTextType</tt> is not updated automatically.
     *
     * @return A reference to this <tt>ChatText</tt>
     */
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
