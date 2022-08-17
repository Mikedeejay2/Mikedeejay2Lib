package com.mikedeejay2.mikedeejay2lib.text.chat;

import com.mikedeejay2.mikedeejay2lib.BukkitPlugin;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * A class that acts as a container for a single "slide" for a <code>ChatSystem</code>
 *
 * @author Mikedeejay2
 */
public class ChatSlide {
    /**
     * The {@link BukkitPlugin} instance
     */
    protected final BukkitPlugin plugin;

    /**
     * The length of time between this slide and the next slide
     */
    protected long period;

    /**
     * A list of chat sections
     */
    protected List<ChatSection> sections;

    /**
     * Construct a new <code>ChatSlide</code>
     *
     * @param plugin The {@link BukkitPlugin} instance
     */
    public ChatSlide(BukkitPlugin plugin) {
        this.plugin = plugin;
        this.sections = new ArrayList<>();
    }

    /**
     * Construct a new <code>ChatSlide</code>
     *
     * @param plugin The {@link BukkitPlugin} instance
     * @param period The length of time between this slide and the next slide
     */
    public ChatSlide(BukkitPlugin plugin, long period) {
        this(plugin);
        this.period = period;
    }

    /**
     * Add an existing <code>ChatSection</code> to this <code>ChatSlide</code>
     *
     * @param section The <code>ChatSection</code> to add
     * @return The current <code>ChatSlide</code>
     */
    public ChatSlide addSection(ChatSection section) {
        sections.add(section);
        return this;
    }

    /**
     * Add an existing <code>ChatSection</code> to this <code>ChatSlide</code> at a specified index
     *
     * @param section The <code>ChatSection</code> to add
     * @param index   The index to add to
     * @return The current <code>ChatSlide</code>
     */
    public ChatSlide addSection(ChatSection section, int index) {
        sections.add(index, section);
        return this;
    }

    /**
     * Create a new <code>ChatSection</code> and add it to this <code>ChatSlide</code>
     *
     * @return The newly created <code>ChatSection</code>
     */
    public ChatSection createSection() {
        ChatSection section = new ChatSection(plugin);
        sections.add(section);
        return section;
    }

    /**
     * Get a <code>ChatSection</code> based off of the index of the section
     *
     * @param index The index of the section to get
     * @return The requested section
     */
    public ChatSection getSection(int index) {
        return sections.get(index);
    }

    /**
     * Create a new <code>ChatSection</code> and add it to this <code>ChatSlide</code> at a specified index
     *
     * @param index The index to add the new <code>ChatSection</code> to
     * @return The newly created <code>ChatSection</code>
     */
    public ChatSection createSection(int index) {
        ChatSection section = new ChatSection(plugin);
        sections.add(index, section);
        return section;
    }

    /**
     * Get the period of this <code>ChatSlide</code>. <p>
     * Period is the amount of time between this slide and the next slide.
     *
     * @return The period of this <code>ChatSlide</code>
     */
    public long getPeriod() {
        return period;
    }

    /**
     * Set the period of this <code>ChatSlide</code>. <p>
     * Period is the amount of time between this slide and the next slide.
     *
     * @param period The new period to use
     * @return The current <code>ChatSlide</code>
     */
    public ChatSlide setPeriod(long period) {
        this.period = period;
        return this;
    }

    /**
     * Print this <code>ChatSlide</code> to one or more <code>CommandSenders</code>
     *
     * @param receivers The array of <code>CommandSenders</code> that will receive this message
     * @return The current <code>ChatSlide</code>
     */
    public ChatSlide print(CommandSender... receivers) {
        for(ChatSection section : sections) {
            for(CommandSender receiver : receivers) section.print(receiver);
        }
        return this;
    }

    /**
     * Broadcast this <code>ChatSlide</code> to all players on the server
     *
     * @return The current <code>ChatSlide</code>
     */
    public ChatSlide broadcast() {
        this.print(Bukkit.getOnlinePlayers().toArray(new Player[0]));
        return this;
    }

    /**
     * Print this <code>ChatSlide</code> to the console
     *
     * @return The current <code>ChatSlide</code>
     */
    public ChatSlide printToConsole() {
        this.print(Bukkit.getConsoleSender());
        return this;
    }
}
