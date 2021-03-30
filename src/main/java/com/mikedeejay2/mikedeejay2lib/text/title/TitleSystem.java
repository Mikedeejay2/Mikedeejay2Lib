package com.mikedeejay2.mikedeejay2lib.text.title;

import com.mikedeejay2.mikedeejay2lib.BukkitPlugin;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * System for displaying titles and animating titles easily.
 *
 * @author Mikedeejay2
 */
public class TitleSystem
{
    protected final BukkitPlugin plugin;
    // The list of title frames
    protected List<TitleFrame> frames;
    // The title frame, will be null if hasn't been displayed yet
    protected TitleRuntime runtime;

    public TitleSystem(BukkitPlugin plugin)
    {
        this.plugin = plugin;
        this.frames = new ArrayList<>();
    }

    /**
     * Display the title to a variable amount of players
     *
     * @param period  The period between updates of the title
     * @param delay   The delay before the title begins displaying
     * @param players The variable array of players
     */
    public void display(long period, long delay, Player... players)
    {
        this.runtime = new TitleRuntime(this, players);
        runtime.runTaskTimerAsynchronously(plugin, delay, period);
    }

    /**
     * Display the title to a variable amount of players
     *
     * @param period  The period between updates of the title
     * @param players The variable array of players
     */
    public void display(long period, Player... players)
    {
        this.display(period, 0, players);
    }

    /**
     * Display the title to a variable amount of players
     *
     * @param players The variable array of players
     */
    public void display(Player... players)
    {
        this.display(1, 0, players);
    }

    /**
     * Get the list of <tt>TitleFrames</tt> of this <tt>TitleSystem</tt>
     *
     * @return Get the list of <tt>TitleFrames</tt>
     */
    public List<TitleFrame> getFrames()
    {
        return frames;
    }

    /**
     * Stop the currently displaying title. This method won't do anything if titles aren't
     * currently being displayed.
     */
    public void stop()
    {
        if(runtime == null || runtime.isCancelled()) return;
        runtime.cancel();
    }

    /**
     * Add a <tt>TitleFrame</tt> to this <tt>TitleSystem</tt>
     *
     * @param frame The <tt>TitleFrame</tt> to add
     * @return The current <tt>TitleSystem</tt>
     */
    public TitleSystem addFrame(TitleFrame frame)
    {
        frames.add(frame);
        return this;
    }

    /**
     * Add a new <tt>TitleFrame</tt> to this <tt>TitleSystem</tt>
     *
     * @param title    The title String of the title
     * @param subtitle The subtitle String of the title
     * @param fadeIn   The fadeIn length (in ticks)
     * @param stay     The stay length (in ticks)
     * @param fadeOut  The fadeOut length (in ticks)
     * @param period   The total period of time that the title has to display
     *                 (for full time do fadeIn + stay + fadeOut)
     * @return The current <tt>TitleSystem</tt>
     */
    public TitleSystem addFrame(String title, String subtitle, int fadeIn, int stay, int fadeOut, long period)
    {
        TitleFrame frame = new TitleFrame(title, subtitle, fadeIn, stay, fadeOut, period);
        frames.add(frame);
        return this;
    }

    /**
     * See whether this <tt>TitleSystem</tt> contains a specific <tt>TitleFrame</tt>
     *
     * @param frame The <tt>TitleFrame</tt> to search for
     * @return Whether the <tt>TitleFrame</tt> was found or not
     */
    public boolean containsFrame(TitleFrame frame)
    {
        return frames.contains(frame);
    }

    /**
     * Remove a <tt>TitleFrame</tt> from this <tt>TitleSystem</tt> based off of a reference to the frame
     *
     * @param frame The <tt>TitleFrame</tt> to remove
     * @return The current <tt>TitleSystem</tt>
     */
    public TitleSystem removeFrame(TitleFrame frame)
    {
        frames.remove(frame);
        return this;
    }

    /**
     * Remove a <tt>TitleFrame</tt> from this <tt>TitleSystem</tt> based off of the index of the frame
     *
     * @param index The index to remove the <tt>TitleFrame</tt> at
     * @return The current <tt>TitleSystem</tt>
     */
    public TitleSystem removeFrame(int index)
    {
        frames.remove(index);
        return this;
    }

    /**
     * Get a <tt>TitleFrame</tt> from this <tt>TitleSystem</tt> based off of the index of the frame
     *
     * @param index The index to get the <tt>TitleFrame</tt> from
     * @return The requested <tt>TitleFrame</tt>
     */
    public TitleFrame getFrame(int index)
    {
        return frames.get(index);
    }
}
