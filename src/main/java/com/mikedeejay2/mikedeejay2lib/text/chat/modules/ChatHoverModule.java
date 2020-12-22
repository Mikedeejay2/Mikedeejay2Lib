package com.mikedeejay2.mikedeejay2lib.text.chat.modules;

import com.mikedeejay2.mikedeejay2lib.PluginBase;
import com.mikedeejay2.mikedeejay2lib.text.chat.ChatSection;
import com.mikedeejay2.mikedeejay2lib.util.chat.Chat;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.HoverEvent;

/**
 * A <tt>ChatModule</tt> that adds hover functionality to the <tt>ChatSection</tt>
 *
 * @author Mikedeejay2
 */
public class ChatHoverModule extends ChatModule
{
    protected final PluginBase plugin;
    // The text that the hover event will use
    protected String hoverText;
    // The action of the hover event
    protected HoverEvent.Action action;

    public ChatHoverModule(String hoverText, HoverEvent.Action action, PluginBase plugin)
    {
        this.hoverText = hoverText;
        this.action = action;
        this.plugin = plugin;
    }

    /**
     * Overridden <tt>onBake()</tt> method that adds hover functionality to the current <tt>BaseComponents</tt>
     *
     * @param section    The <tt>ChatSection</tt> that is being baked
     * @param components The current array of <tt>BaseComponents</tt> that have been baked
     */
    @Override
    public void onBake(ChatSection section, BaseComponent[] components)
    {
        Chat chat = plugin.chat();
        HoverEvent hoverEvent = chat.getHoverEvent(action, hoverText);
        chat.setHoverEvent(components, hoverEvent);
    }
}
