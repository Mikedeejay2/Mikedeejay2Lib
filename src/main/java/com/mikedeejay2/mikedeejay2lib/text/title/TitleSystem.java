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
    /**
     * The {@link BukkitPlugin} instance
     */
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
     * Get the list of <code>TitleFrames</code> of this <code>TitleSystem</code>
     *
     * @return Get the list of <code>TitleFrames</code>
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
     * Add a <code>TitleFrame</code> to this <code>TitleSystem</code>
     *
     * @param frame The <code>TitleFrame</code> to add
     * @return The current <code>TitleSystem</code>
     */
    public TitleSystem addFrame(TitleFrame frame)
    {
        frames.add(frame);
        return this;
    }

    /**
     * Add a new <code>TitleFrame</code> to this <code>TitleSystem</code>
     *
     * @param title    The title String of the title
     * @param subtitle The subtitle String of the title
     * @param fadeIn   The fadeIn length (in ticks)
     * @param stay     The stay length (in ticks)
     * @param fadeOut  The fadeOut length (in ticks)
     * @param period   The total period of time that the title has to display
     *                 (for full time do fadeIn + stay + fadeOut)
     * @return The current <code>TitleSystem</code>
     */
    public TitleSystem addFrame(String title, String subtitle, int fadeIn, int stay, int fadeOut, long period)
    {
        TitleFrame frame = new TitleFrame(title, subtitle, fadeIn, stay, fadeOut, period);
        frames.add(frame);
        return this;
    }

    /**
     * See whether this <code>TitleSystem</code> contains a specific <code>TitleFrame</code>
     *
     * @param frame The <code>TitleFrame</code> to search for
     * @return Whether the <code>TitleFrame</code> was found or not
     */
    public boolean containsFrame(TitleFrame frame)
    {
        return frames.contains(frame);
    }

    /**
     * Remove a <code>TitleFrame</code> from this <code>TitleSystem</code> based off of a reference to the frame
     *
     * @param frame The <code>TitleFrame</code> to remove
     * @return The current <code>TitleSystem</code>
     */
    public TitleSystem removeFrame(TitleFrame frame)
    {
        frames.remove(frame);
        return this;
    }

    /**
     * Remove a <code>TitleFrame</code> from this <code>TitleSystem</code> based off of the index of the frame
     *
     * @param index The index to remove the <code>TitleFrame</code> at
     * @return The current <code>TitleSystem</code>
     */
    public TitleSystem removeFrame(int index)
    {
        frames.remove(index);
        return this;
    }

    /**
     * Get a <code>TitleFrame</code> from this <code>TitleSystem</code> based off of the index of the frame
     *
     * @param index The index to get the <code>TitleFrame</code> from
     * @return The requested <code>TitleFrame</code>
     */
    public TitleFrame getFrame(int index)
    {
        return frames.get(index);
    }
}
