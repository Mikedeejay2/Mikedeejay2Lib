package com.mikedeejay2.mikedeejay2lib.chat.section;

import com.mikedeejay2.mikedeejay2lib.PluginBase;
import com.mikedeejay2.mikedeejay2lib.chat.modules.ChatModule;

import java.util.ArrayList;
import java.util.List;

public class ChatSection
{
    protected final PluginBase plugin;
    protected List<ChatModule> features;

    public ChatSection(PluginBase plugin)
    {
        this.plugin = plugin;
        this.features = new ArrayList<>();
    }

    public ChatSection addFeature(ChatModule feature)
    {
        features.add(feature);
        return this;
    }
}
