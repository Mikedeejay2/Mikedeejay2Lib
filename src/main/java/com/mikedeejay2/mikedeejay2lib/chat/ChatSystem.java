package com.mikedeejay2.mikedeejay2lib.chat;

import com.mikedeejay2.mikedeejay2lib.PluginBase;
import com.mikedeejay2.mikedeejay2lib.chat.animation.ChatAnimRuntime;
import com.mikedeejay2.mikedeejay2lib.chat.slide.ChatSlide;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * <tt>ChatSystem</tt> is a container of objects that help to make printing and
 * animating text easier
 *
 * @author Mikedeejay2
 */
public class ChatSystem
{
    protected final PluginBase plugin;
    // The chat slides that this system contains
    protected List<ChatSlide> slides;
    // The animation runtime for this system. Nullable.
    protected ChatAnimRuntime runtime;

    public ChatSystem(PluginBase plugin)
    {
        this.plugin = plugin;
        this.slides = new ArrayList<>();
    }

    /**
     * Add an existing <tt>ChatSlide</tt> to this <tt>ChatSystem</tt>
     *
     * @param slide The slide to add
     * @return The current <tt>ChatSystem</tt>
     */
    public ChatSystem addSlide(ChatSlide slide)
    {
        slides.add(slide);
        return this;
    }

    /**
     * Add an existing <tt>ChatSlide</tt> to this <tt>ChatSystem</tt> at a specific index
     *
     * @param slide The slide to add
     * @param index the index to add the <tt>ChatSlide</tt> at
     * @return The current <tt>ChatSystem</tt>
     */
    public ChatSystem addSlide(ChatSlide slide, int index)
    {
        slides.add(index, slide);
        return this;
    }

    /**
     * Add a new <tt>ChatSlide</tt> to this <tt>ChatSystem</tt>
     *
     * @return The newly created <tt>ChatSlide</tt>
     */
    public ChatSlide createSlide()
    {
        ChatSlide slide = new ChatSlide(plugin);
        slides.add(slide);
        return slide;
    }

    /**
     * Add a new <tt>ChatSlide</tt> to this <tt>ChatSystem</tt> at a specific index
     *
     * @return The newly created <tt>ChatSlide</tt>
     */
    public ChatSlide createSlide(int index)
    {
        ChatSlide slide = new ChatSlide(plugin);
        slides.add(index, slide);
        return slide;
    }

    /**
     * Remove a <tt>ChatSlide</tt> at a specified index
     *
     * @param index The index to remove from this <tt>ChatSystem</tt>
     * @return The current <tt>ChatSystem</tt>
     */
    public ChatSystem removeSlide(int index)
    {
        slides.remove(index);
        return this;
    }

    /**
     * Remove a <tt>ChatSlide</tt> based off of an instance of the slide
     *
     * @param slide A reference to the <tt>ChatSlide</tt> to remove
     * @return The current <tt>ChatSystem</tt>
     */
    public ChatSystem removeSlide(ChatSlide slide)
    {
        slides.remove(slide);
        return this;
    }

    /**
     * See whether this <tt>ChatSystem</tt> contains a specified <tt>ChatSlide</tt> instance
     *
     * @param slide The <tt>ChatSlide</tt> instance to search for
     * @return Whether the instance was found or not
     */
    public boolean containsSlide(ChatSlide slide)
    {
        return slides.contains(slide);
    }

    /**
     * Get a <tt>ChatSlide</tt> based off of a list index
     *
     * @param index The index to get
     * @return The <tt>ChatSlide</tt> at the specified index
     */
    public ChatSlide getSlide(int index)
    {
        return slides.get(index);
    }

    /**
     * Reset the slides list in this <tt>ChatSystem</tt>
     *
     * @return The current <tt>ChatSystem</tt>
     */
    public ChatSystem resetSlides()
    {
        slides.clear();
        return this;
    }

    /**
     * Print this <tt>ChatSystem</tt> to one or more <tt>CommandSenders</tt>
     *
     * @param receivers The <tt>CommandSenders</tt> that this <tt>ChatSystem</tt> should be printed to
     * @return The current <tt>ChatSystem</tt>
     */
    public ChatSystem print(CommandSender... receivers)
    {
        this.runtime = new ChatAnimRuntime(this, receivers);
        runtime.runTaskTimerAsynchronously(plugin, 1);
        return this;
    }

    /**
     * Print this <tt>ChatSystem</tt> to console
     *
     * @return The current <tt>ChatSystem</tt>
     */
    public ChatSystem printToConsole()
    {
        return this.print(Bukkit.getConsoleSender());
    }

    /**
     * Print this <tt>ChatSystem</tt> to all players on the server
     *
     * @return The current <tt>ChatSystem</tt>
     */
    public ChatSystem broadcast()
    {
        return this.print(Bukkit.getOnlinePlayers().toArray(new Player[0]));
    }

    /**
     * Get all of the <tt>ChatSlides</tt> that are added to this <tt>ChatSystem</tt>
     *
     * @return A list of <tt>ChatSlides</tt>
     */
    public List<ChatSlide> getSlides()
    {
        return slides;
    }

    /**
     * See whether this <tt>ChatSystem</tt> is currently printing or not
     *
     * @return Whether this <tt>ChatSystem</tt> is currently printing
     */
    public boolean isPrinting()
    {
        if(runtime == null) return false;
        return !runtime.isCancelled();
    }

    /**
     * Cancel the current print if a print is in progress
     *
     * @return Whether this <tt>ChatSystem</tt> is currently printing
     */
    public ChatSystem cancelPrint()
    {
        if(runtime == null) return this;
        runtime.cancel();
        return this;
    }
}
