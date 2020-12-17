package com.mikedeejay2.mikedeejay2lib.chat.slide;

import com.mikedeejay2.mikedeejay2lib.PluginBase;
import com.mikedeejay2.mikedeejay2lib.chat.section.ChatSection;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

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

    public ChatSlide(PluginBase plugin, long period)
    {
        this(plugin);
        this.period = period;
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

    public ChatSlide setPeriod(long period)
    {
        this.period = period;
        return this;
    }

    public void print(CommandSender... receivers)
    {
        for(ChatSection section : sections)
        {
            for(CommandSender receiver : receivers) section.print(receiver);
        }
    }

    public void broadcast()
    {
        this.print(Bukkit.getOnlinePlayers().toArray(new Player[0]));
    }

    public void printToConsole()
    {
        this.print(Bukkit.getConsoleSender());
    }
}
