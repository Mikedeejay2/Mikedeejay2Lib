package com.mikedeejay2.mikedeejay2lib.text.chat.modules;

import com.mikedeejay2.mikedeejay2lib.PluginBase;
import com.mikedeejay2.mikedeejay2lib.text.chat.ChatSection;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;

/**
 * A <tt>ChatModule</tt> that adds click functionality to the <tt>ChatSection</tt>
 *
 * @author Mikedeejay2
 */
public class ChatClickModule implements ChatModule
{
    protected final PluginBase plugin;
    // The text that the click event will use
    protected String clickText;
    // The action of the click event
    protected ClickEvent.Action action;

    public ChatClickModule(String clickText, ClickEvent.Action action, PluginBase plugin)
    {
        this.clickText = clickText;
        this.action = action;
        this.plugin = plugin;
    }

    /**
     * Overridden <tt>onBake()</tt> method that adds click functionality to the current <tt>BaseComponents</tt>
     *
     * @param section    The <tt>ChatSection</tt> that is being baked
     * @param components The current array of <tt>BaseComonents</tt> that have been baked
     */
    @Override
    public void onBake(ChatSection section, BaseComponent[] components)
    {
        ClickEvent clickEvent = plugin.getClickEvent(action, clickText);
        plugin.setClickEvent(components, clickEvent);
    }
}
