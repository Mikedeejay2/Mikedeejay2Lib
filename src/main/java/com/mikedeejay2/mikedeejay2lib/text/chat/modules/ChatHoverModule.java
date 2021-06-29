package com.mikedeejay2.mikedeejay2lib.text.chat.modules;

import com.mikedeejay2.mikedeejay2lib.BukkitPlugin;
import com.mikedeejay2.mikedeejay2lib.text.chat.ChatSection;
import com.mikedeejay2.mikedeejay2lib.util.chat.ChatConverter;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.HoverEvent;

/**
 * A <code>ChatModule</code> that adds hover functionality to the <code>ChatSection</code>
 *
 * @author Mikedeejay2
 */
public class ChatHoverModule implements ChatModule
{
    /**
     * The {@link BukkitPlugin} instance
     */
    protected final BukkitPlugin plugin;
    // The text that the hover event will use
    protected String hoverText;
    // The action of the hover event
    protected HoverEvent.Action action;

    public ChatHoverModule(String hoverText, HoverEvent.Action action, BukkitPlugin plugin)
    {
        this.hoverText = hoverText;
        this.action = action;
        this.plugin = plugin;
    }

    /**
     * Overridden <code>onBake()</code> method that adds hover functionality to the current <code>BaseComponents</code>
     *
     * @param section    The <code>ChatSection</code> that is being baked
     * @param components The current array of <code>BaseComponents</code> that have been baked
     */
    @Override
    public void onBake(ChatSection section, BaseComponent[] components)
    {
        HoverEvent hoverEvent = ChatConverter.getHoverEvent(action, hoverText);
        ChatConverter.setHoverEvent(components, hoverEvent);
    }
}
