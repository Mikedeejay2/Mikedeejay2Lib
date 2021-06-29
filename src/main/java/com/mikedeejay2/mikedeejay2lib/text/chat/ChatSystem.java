package com.mikedeejay2.mikedeejay2lib.text.chat;

import com.mikedeejay2.mikedeejay2lib.BukkitPlugin;
import com.mikedeejay2.mikedeejay2lib.text.chat.animation.ChatAnimRuntime;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * <code>ChatSystem</code> is a container of objects that help to make printing and
 * animating text easier
 *
 * @author Mikedeejay2
 */
public class ChatSystem
{
    /**
     * The {@link BukkitPlugin} instance
     */
    protected final BukkitPlugin plugin;
    // The chat slides that this system contains
    protected List<ChatSlide> slides;
    // The animation runtime for this system. Nullable.
    protected ChatAnimRuntime runtime;

    public ChatSystem(BukkitPlugin plugin)
    {
        this.plugin = plugin;
        this.slides = new ArrayList<>();
    }

    /**
     * Add an existing <code>ChatSlide</code> to this <code>ChatSystem</code>
     *
     * @param slide The slide to add
     * @return The current <code>ChatSystem</code>
     */
    public ChatSystem addSlide(ChatSlide slide)
    {
        slides.add(slide);
        return this;
    }

    /**
     * Add an existing <code>ChatSlide</code> to this <code>ChatSystem</code> at a specific index
     *
     * @param slide The slide to add
     * @param index the index to add the <code>ChatSlide</code> at
     * @return The current <code>ChatSystem</code>
     */
    public ChatSystem addSlide(ChatSlide slide, int index)
    {
        slides.add(index, slide);
        return this;
    }

    /**
     * Add a new <code>ChatSlide</code> to this <code>ChatSystem</code>
     *
     * @return The newly created <code>ChatSlide</code>
     */
    public ChatSlide createSlide()
    {
        ChatSlide slide = new ChatSlide(plugin);
        slides.add(slide);
        return slide;
    }

    /**
     * Add a new <code>ChatSlide</code> to this <code>ChatSystem</code> at a specific index
     *
     * @param index The index to add the <code>ChatSlide</code> to
     * @return The newly created <code>ChatSlide</code>
     */
    public ChatSlide createSlide(int index)
    {
        ChatSlide slide = new ChatSlide(plugin);
        slides.add(index, slide);
        return slide;
    }

    /**
     * Remove a <code>ChatSlide</code> at a specified index
     *
     * @param index The index to remove from this <code>ChatSystem</code>
     * @return The current <code>ChatSystem</code>
     */
    public ChatSystem removeSlide(int index)
    {
        slides.remove(index);
        return this;
    }

    /**
     * Remove a <code>ChatSlide</code> based off of an instance of the slide
     *
     * @param slide A reference to the <code>ChatSlide</code> to remove
     * @return The current <code>ChatSystem</code>
     */
    public ChatSystem removeSlide(ChatSlide slide)
    {
        slides.remove(slide);
        return this;
    }

    /**
     * See whether this <code>ChatSystem</code> contains a specified <code>ChatSlide</code> instance
     *
     * @param slide The <code>ChatSlide</code> instance to search for
     * @return Whether the instance was found or not
     */
    public boolean containsSlide(ChatSlide slide)
    {
        return slides.contains(slide);
    }

    /**
     * Get a <code>ChatSlide</code> based off of a list index
     *
     * @param index The index to get
     * @return The <code>ChatSlide</code> at the specified index
     */
    public ChatSlide getSlide(int index)
    {
        return slides.get(index);
    }

    /**
     * Reset the slides list in this <code>ChatSystem</code>
     *
     * @return The current <code>ChatSystem</code>
     */
    public ChatSystem resetSlides()
    {
        slides.clear();
        return this;
    }

    /**
     * Print this <code>ChatSystem</code> to one or more <code>CommandSenders</code>
     *
     * @param receivers The <code>CommandSenders</code> that this <code>ChatSystem</code> should be printed to
     * @return The current <code>ChatSystem</code>
     */
    public ChatSystem print(CommandSender... receivers)
    {
        this.runtime = new ChatAnimRuntime(this, receivers);
        runtime.runTaskTimerAsynchronously(plugin, 1);
        return this;
    }

    /**
     * Print this <code>ChatSystem</code> to console
     *
     * @return The current <code>ChatSystem</code>
     */
    public ChatSystem printToConsole()
    {
        return this.print(Bukkit.getConsoleSender());
    }

    /**
     * Print this <code>ChatSystem</code> to all players on the server
     *
     * @return The current <code>ChatSystem</code>
     */
    public ChatSystem broadcast()
    {
        return this.print(Bukkit.getOnlinePlayers().toArray(new Player[0]));
    }

    /**
     * Get all of the <code>ChatSlides</code> that are added to this <code>ChatSystem</code>
     *
     * @return A list of <code>ChatSlides</code>
     */
    public List<ChatSlide> getSlides()
    {
        return slides;
    }

    /**
     * See whether this <code>ChatSystem</code> is currently printing or not
     *
     * @return Whether this <code>ChatSystem</code> is currently printing
     */
    public boolean isPrinting()
    {
        if(runtime == null) return false;
        return !runtime.isCancelled();
    }

    /**
     * Cancel the current print if a print is in progress
     *
     * @return Whether this <code>ChatSystem</code> is currently printing
     */
    public ChatSystem cancelPrint()
    {
        if(runtime == null) return this;
        runtime.cancel();
        return this;
    }
}
