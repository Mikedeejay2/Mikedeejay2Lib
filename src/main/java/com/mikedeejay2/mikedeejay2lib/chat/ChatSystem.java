package com.mikedeejay2.mikedeejay2lib.chat;

import com.mikedeejay2.mikedeejay2lib.PluginBase;
import com.mikedeejay2.mikedeejay2lib.chat.slide.ChatSlide;

import java.util.ArrayList;
import java.util.List;

public class ChatSystem
{
    protected final PluginBase plugin;
    protected List<ChatSlide> slides;
    protected ChatPresentMode mode;

    public ChatSystem(PluginBase plugin)
    {
        this.plugin = plugin;
        this.slides = new ArrayList<>();
        this.mode = null;
    }

    public ChatSlide addSlide()
    {
        ChatSlide slide = new ChatSlide(plugin);
        slides.add(slide);
        return slide;
    }

    public ChatSlide getSlide(int index)
    {
        return slides.get(index);
    }

    public ChatSystem resetSlides()
    {
        slides.clear();
        return this;
    }

    public ChatPresentMode getMode()
    {
        return mode;
    }

    public void setMode(ChatPresentMode mode)
    {
        this.mode = mode;
    }
}
