package com.mikedeejay2.mikedeejay2lib.chat;

import com.mikedeejay2.mikedeejay2lib.PluginBase;
import com.mikedeejay2.mikedeejay2lib.util.chat.Chat;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

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
    protected List<ChatSection> sections;

    public ChatCanvas(PluginBase plugin)
    {
        this.plugin = plugin;
        this.chat = plugin.chat();
        this.sections = new ArrayList<>();
    }

    /**
     * Add a <tt>ChatSection</tt> to this <tt>ChatCanvas</tt>.
     *
     * @param section The section to add
     * @return A reference to this <tt>ChatCanvas</tt>
     */
    public ChatCanvas addSection(ChatSection section)
    {
        sections.add(section);
        return this;
    }

    /**
     * Add a <tt>ChatSection</tt> to this <tt>ChatCanvas</tt>.
     *
     * @param section The section to add
     * @param index The index to add the section at
     * @return A reference to this <tt>ChatCanvas</tt>
     */
    public ChatCanvas addSection(ChatSection section, int index)
    {
        sections.add(index, section);
        return this;
    }

    /**
     * Print this <tt>ChatCanvas</tt> to the console.
     *
     * @return A reference to this <tt>ChatCanvas</tt>
     */
    public ChatCanvas printToConsole()
    {
        for(ChatSection section : sections)
        {
            if(!section.isConverted()) section.convert();
            section.printToConsole();
        }
        return this;
    }

    /**
     * Broadcast this <tt>ChatCanvas</tt> to the entire server
     *
     * @return A reference to this <tt>ChatCanvas</tt>
     */
    public ChatCanvas broadcast()
    {
        for(ChatSection section : sections)
        {
            if(!section.isConverted()) section.convert();
            section.broadcast();
        }
        return this;
    }

    /**
     * Print this <tt>ChatCanvas</tt> to a <tt>CommandSender</tt>
     *
     * @param sender The <tt>CommandSender</tt> to send this chat to
     * @return A reference to this <tt>ChatCanvas</tt>
     */
    public ChatCanvas print(CommandSender sender)
    {
        for(ChatSection section : sections)
        {
            if(!section.isConverted()) section.convert();
            section.print(sender);
        }
        return this;
    }

    /**
     * Print this <tt>ChatCanvas</tt> to a Player
     *
     * @param player The Player to send this chat to
     * @return A reference to this <tt>ChatCanvas</tt>
     */
    public ChatCanvas print(Player player)
    {
        for(ChatSection section : sections)
        {
            if(!section.isConverted()) section.convert();
            section.print(player);
        }
        return this;
    }
}
