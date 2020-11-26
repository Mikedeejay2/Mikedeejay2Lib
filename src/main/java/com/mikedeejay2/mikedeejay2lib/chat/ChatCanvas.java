package com.mikedeejay2.mikedeejay2lib.chat;

import com.mikedeejay2.mikedeejay2lib.PluginBase;
import com.mikedeejay2.mikedeejay2lib.util.chat.Chat;

import java.util.ArrayList;
import java.util.List;

/**
 * A chat canvas is a text structure for Minecraft's chat system to make
 * messages look good and make using localization, hover events, and other
 * hard to manage text features easier to use.
 *
 * @author Mikedeejay2
 */
public class ChatCanvas
{
    protected final PluginBase plugin;
    protected final Chat chat;
    protected List<ChatLine> lines;

    public ChatCanvas(PluginBase plugin)
    {
        this.plugin = plugin;
        this.chat = plugin.chat();
        this.lines = new ArrayList<>();
    }
}
