package com.mikedeejay2.mikedeejay2lib.chat.slide;

import com.mikedeejay2.mikedeejay2lib.PluginBase;
import com.mikedeejay2.mikedeejay2lib.chat.section.ChatSection;

import java.util.ArrayList;
import java.util.List;

public class ChatSlide
{
    protected final PluginBase plugin;
    protected long period;
    protected List<ChatSection> sections;

    public ChatSlide(PluginBase plugin)
    {
        this.plugin = plugin;
        this.sections = new ArrayList<>();
    }

    public ChatSlide addSection(ChatSection section)
    {
        sections.add(section);
        return this;
    }

    public ChatSlide addSection(ChatSection section, int index)
    {
        sections.add(index, section);
        return this;
    }

    public ChatSection createSection()
    {
        ChatSection section = new ChatSection(plugin);
        sections.add(section);
        return section;
    }

    public ChatSection createSection(int index)
    {
        ChatSection section = new ChatSection(plugin);
        sections.add(index, section);
        return section;
    }

    public long getPeriod()
    {
        return period;
    }

    public void setPeriod(long period)
    {
        this.period = period;
    }
}
