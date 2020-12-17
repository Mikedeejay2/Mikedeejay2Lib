package com.mikedeejay2.mikedeejay2lib.chat.modules;

import com.mikedeejay2.mikedeejay2lib.PluginBase;
import com.mikedeejay2.mikedeejay2lib.chat.section.ChatSection;
import com.mikedeejay2.mikedeejay2lib.util.chat.Chat;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.HoverEvent;

public class ChatHoverModule extends ChatModule
{
    protected final PluginBase plugin;
    protected String hoverText;
    protected HoverEvent.Action action;

    public ChatHoverModule(String hoverText, HoverEvent.Action action, PluginBase plugin)
    {
        this.hoverText = hoverText;
        this.action = action;
        this.plugin = plugin;
    }

    @Override
    public void onBake(ChatSection section, BaseComponent[] components)
    {
        Chat chat = plugin.chat();
        HoverEvent hoverEvent = chat.getHoverEvent(action, hoverText);
        chat.setHoverEvent(components, hoverEvent);
    }
}
