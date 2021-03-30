package com.mikedeejay2.mikedeejay2lib.text.chat;

import com.mikedeejay2.mikedeejay2lib.BukkitPlugin;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * A class that acts as a container for a single "slide" for a <tt>ChatSystem</tt>
 *
 * @author Mikedeejay2
 */
public class ChatSlide
{
    protected final BukkitPlugin plugin;
    // The length of time between this slide and the next slide
    protected long period;
    // A list of chat sections
    protected List<ChatSection> sections;

    public ChatSlide(BukkitPlugin plugin)
    {
        this.plugin = plugin;
        this.sections = new ArrayList<>();
    }

    public ChatSlide(BukkitPlugin plugin, long period)
    {
        this(plugin);
        this.period = period;
    }

    /**
     * Add an existing <tt>ChatSection</tt> to this <tt>ChatSlide</tt>
     *
     * @param section The <tt>ChatSection</tt> to add
     * @return The current <tt>ChatSlide</tt>
     */
    public ChatSlide addSection(ChatSection section)
    {
        sections.add(section);
        return this;
    }

    /**
     * Add an existing <tt>ChatSection</tt> to this <tt>ChatSlide</tt> at a specified index
     *
     * @param section The <tt>ChatSection</tt> to add
     * @param index   The index to add to
     * @return The current <tt>ChatSlide</tt>
     */
    public ChatSlide addSection(ChatSection section, int index)
    {
        sections.add(index, section);
        return this;
    }

    /**
     * Create a new <tt>ChatSection</tt> and add it to this <tt>ChatSlide</tt>
     *
     * @return The newly created <tt>ChatSection</tt>
     */
    public ChatSection createSection()
    {
        ChatSection section = new ChatSection(plugin);
        sections.add(section);
        return section;
    }

    /**
     * Get a <tt>ChatSection</tt> based off of the index of the section
     *
     * @param index The index of the section to get
     * @return The requested section
     */
    public ChatSection getSection(int index)
    {
        return sections.get(index);
    }

    /**
     * Create a new <tt>ChatSection</tt> and add it to this <tt>ChatSlide</tt> at a specified index
     *
     * @param index The index to add the new <tt>ChatSection</tt> to
     * @return The newly created <tt>ChatSection</tt>
     */
    public ChatSection createSection(int index)
    {
        ChatSection section = new ChatSection(plugin);
        sections.add(index, section);
        return section;
    }

    /**
     * Get the period of this <tt>ChatSlide</tt>. <p>
     * Period is the amount of time between this slide and the next slide.
     *
     * @return The period of this <tt>ChatSlide</tt>
     */
    public long getPeriod()
    {
        return period;
    }

    /**
     * Set the period of this <tt>ChatSlide</tt>. <p>
     * Period is the amount of time between this slide and the next slide.
     *
     * @param period The new period to use
     * @return The current <tt>ChatSlide</tt>
     */
    public ChatSlide setPeriod(long period)
    {
        this.period = period;
        return this;
    }

    /**
     * Print this <tt>ChatSlide</tt> to one or more <tt>CommandSenders</tt>
     *
     * @param receivers The array of <tt>CommandSenders</tt> that will receive this message
     * @return The current <tt>ChatSlide</tt>
     */
    public ChatSlide print(CommandSender... receivers)
    {
        for(ChatSection section : sections)
        {
            for(CommandSender receiver : receivers) section.print(receiver);
        }
        return this;
    }

    /**
     * Broadcast this <tt>ChatSlide</tt> to all players on the server
     *
     * @return The current <tt>ChatSlide</tt>
     */
    public ChatSlide broadcast()
    {
        this.print(Bukkit.getOnlinePlayers().toArray(new Player[0]));
        return this;
    }

    /**
     * Print this <tt>ChatSlide</tt> to the console
     *
     * @return The current <tt>ChatSlide</tt>
     */
    public ChatSlide printToConsole()
    {
        this.print(Bukkit.getConsoleSender());
        return this;
    }
}
