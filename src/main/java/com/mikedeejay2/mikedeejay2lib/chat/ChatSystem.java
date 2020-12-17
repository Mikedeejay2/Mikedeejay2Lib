package com.mikedeejay2.mikedeejay2lib.chat;

import com.mikedeejay2.mikedeejay2lib.PluginBase;
import com.mikedeejay2.mikedeejay2lib.chat.animation.ChatAnimRuntime;
import com.mikedeejay2.mikedeejay2lib.chat.slide.ChatSlide;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class ChatSystem
{
    protected final PluginBase plugin;
    protected List<ChatSlide> slides;
    protected ChatPresentMode mode;
    protected ChatAnimRuntime runtime;

    public ChatSystem(PluginBase plugin)
    {
        this.plugin = plugin;
        this.slides = new ArrayList<>();
        this.mode = null;
    }

    public ChatSystem addSlide(ChatSlide slide)
    {
        slides.add(slide);
        return this;
    }

    public ChatSystem addSlide(ChatSlide slide, int index)
    {
        slides.add(index, slide);
        return this;
    }

    public ChatSlide createSlide()
    {
        ChatSlide slide = new ChatSlide(plugin);
        slides.add(slide);
        return slide;
    }

    public ChatSlide createSlide(int index)
    {
        ChatSlide slide = new ChatSlide(plugin);
        slides.add(index, slide);
        return slide;
    }

    public ChatSystem removeSlide(int index)
    {
        slides.remove(index);
        return this;
    }

    public ChatSystem removeSlide(ChatSlide slide)
    {
        slides.remove(slide);
        return this;
    }

    public boolean containsSlide(ChatSlide slide)
    {
        return slides.contains(slide);
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

    public ChatSystem print(CommandSender... receivers)
    {
        this.runtime = new ChatAnimRuntime(this, receivers);
        runtime.runTaskTimerAsynchronously(plugin, 1);
        return this;
    }

    public void printToConsole()
    {
        this.print(Bukkit.getConsoleSender());
    }

    public void broadcast()
    {
        this.print(Bukkit.getOnlinePlayers().toArray(new Player[0]));
    }

    public List<ChatSlide> getSlides()
    {
        return slides;
    }

    public boolean isPrinting()
    {
        if(runtime == null) return false;
        return !runtime.isCancelled();
    }

    public void cancelPrint()
    {
        if(runtime == null) return;
        runtime.cancel();
    }
}
