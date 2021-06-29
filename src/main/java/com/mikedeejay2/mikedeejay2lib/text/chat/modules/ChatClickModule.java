package com.mikedeejay2.mikedeejay2lib.text.chat.modules;

import com.mikedeejay2.mikedeejay2lib.BukkitPlugin;
import com.mikedeejay2.mikedeejay2lib.text.chat.ChatSection;
import com.mikedeejay2.mikedeejay2lib.util.chat.ChatConverter;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;

/**
 * A <code>ChatModule</code> that adds click functionality to the <code>ChatSection</code>
 *
 * @author Mikedeejay2
 */
public class ChatClickModule implements ChatModule
{
    /**
     * The {@link BukkitPlugin} instance
     */
    protected final BukkitPlugin plugin;
    // The text that the click event will use
    protected String clickText;
    // The action of the click event
    protected ClickEvent.Action action;

    public ChatClickModule(String clickText, ClickEvent.Action action, BukkitPlugin plugin)
    {
        this.clickText = clickText;
        this.action = action;
        this.plugin = plugin;
    }

    /**
     * Overridden <code>onBake()</code> method that adds click functionality to the current <code>BaseComponents</code>
     *
     * @param section    The <code>ChatSection</code> that is being baked
     * @param components The current array of <code>BaseComonents</code> that have been baked
     */
    @Override
    public void onBake(ChatSection section, BaseComponent[] components)
    {
        ClickEvent clickEvent = ChatConverter.getClickEvent(action, clickText);
        ChatConverter.setClickEvent(components, clickEvent);
    }
}
