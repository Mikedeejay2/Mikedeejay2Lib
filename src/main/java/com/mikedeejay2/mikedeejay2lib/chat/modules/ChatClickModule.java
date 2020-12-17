package com.mikedeejay2.mikedeejay2lib.chat.modules;

import com.mikedeejay2.mikedeejay2lib.PluginBase;
import com.mikedeejay2.mikedeejay2lib.chat.section.ChatSection;
import com.mikedeejay2.mikedeejay2lib.util.chat.Chat;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;

public class ChatClickModule extends ChatModule
{
    protected final PluginBase plugin;
    protected String clickText;
    protected ClickEvent.Action action;

    public ChatClickModule(String clickText, ClickEvent.Action action, PluginBase plugin)
    {
        this.clickText = clickText;
        this.action = action;
        this.plugin = plugin;
    }

    @Override
    public void onBake(ChatSection section, BaseComponent[] components)
    {
        Chat chat = plugin.chat();
        ClickEvent clickEvent = chat.getClickEvent(action, clickText);
        chat.setClickEvent(components, clickEvent);
    }
}
