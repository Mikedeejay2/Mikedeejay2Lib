package com.mikedeejay2.mikedeejay2lib.text.actionbar;

import com.mikedeejay2.mikedeejay2lib.PluginBase;
import com.mikedeejay2.mikedeejay2lib.text.actionbar.animation.ActionBarRuntime;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * Allows easy creation, display, and animation of action bar text.
 *
 * @author Mikedeejay2
 */
public class ActionBar
{
    protected final PluginBase plugin;
    // Whether the frames of the action bar will loop or not
    protected boolean loop;
    // The list of frames of the action bar
    protected List<ActionBarFrame> frames;
    // The action bar runtime, null if not displayed yet
    protected ActionBarRuntime runtime;

    public ActionBar(PluginBase plugin)
    {
        this.plugin = plugin;
        this.frames = new ArrayList<>();
    }

    public ActionBar(PluginBase plugin, boolean loop)
    {
        this.plugin = plugin;
        this.loop = loop;
        this.frames = new ArrayList<>();
    }

    /**
     * Display the action bar to an array of players
     *
     * @param period  The update period (in ticks) of the action bar
     * @param players The players to display the action bar to
     */
    public void display(long period, Player... players)
    {
        this.runtime = new ActionBarRuntime(this, players);
        runtime.runTaskTimerAsynchronously(plugin, period);
    }

    /**
     * Display the action bar to an array of players
     *
     * @param players The players to display the action bar to
     */
    public void display(Player... players)
    {
        this.display(1, players);
    }

    /**
     * Display the action bar to an array of players
     *
     * @param period  The update period (in ticks) of the action bar
     * @param count   The amount of ticks before the action bar stops playing
     * @param players The players to display the action bar to
     */
    public void displayCount(long period, long count, Player... players)
    {
        this.runtime = new ActionBarRuntime(this, players);
        runtime.runTaskTimerCounted(plugin, period, count);
    }

    /**
     * Display the action bar to an array of players
     *
     * @param count   The amount of ticks before the action bar stops playing
     * @param players The players to display the action bar to
     */
    public void displayCount(long count, Player... players)
    {
        this.displayCount(1, count, players);
    }

    /**
     * Add a new <tt>ActionBarFrame</tt> to this <tt>ActionBar</tt>
     *
     * @param text  The String of text for the frame to display
     * @param delay The delay between this frame and the next frame
     * @return The current <tt>ActionBar</tt>
     */
    public ActionBar addFrame(String text, long delay)
    {
        ActionBarFrame frame = new ActionBarFrame(text, delay);
        frames.add(frame);
        return this;
    }

    /**
     * Add an existing <tt>ActionBarFrame</tt> to this <tt>ActionBar</tt>
     *
     * @param frame The <tt>ActionBarFrame</tt> to add
     * @return The current <tt>ActionBar</tt>
     */
    public ActionBar addFrame(ActionBarFrame frame)
    {
        frames.add(frame);
        return this;
    }

    /**
     * Remove a <tt>ActionBarFrame</tt> from this <tt>ActionBar</tt> with the index of the frame
     *
     * @param index The index to remove the frame from
     * @return The current <tt>ActionBar</tt>
     */
    public ActionBar removeFrame(int index)
    {
        frames.remove(index);
        return this;
    }

    /**
     * Remove a <tt>ActionBarFrame</tt> from this <tt>ActionBar</tt> with a reference to the frame
     *
     * @param frame The <tt>ActionBarFrame</tt> to remove
     * @return The current <tt>ActionBar</tt>
     */
    public ActionBar removeFrame(ActionBarFrame frame)
    {
        frames.remove(frame);
        return this;
    }

    /**
     * See whether this <tt>ActionBar</tt> contains a specific <tt>ActionBarFrame</tt>
     *
     * @param frame The <tt>ActionBarFrame</tt> to search for
     * @return Whether the specified frame was found or not
     */
    public boolean containsFrame(ActionBarFrame frame)
    {
        return frames.contains(frame);
    }

    /**
     * Reset (clear) all frames in this <tt>ActionBar</tt>
     *
     * @return The current <tt>ActionBar</tt>
     */
    public ActionBar resetFrames()
    {
        frames.clear();
        return this;
    }

    /**
     * Get a <tt>ActionBarFrame</tt> based off of its index
     *
     * @param index The index to get the frame from
     * @return The requested frame
     */
    public ActionBarFrame getFrame(int index)
    {
        return frames.get(index);
    }

    /**
     * Get the list of all frames in this <tt>ActionBar</tt>
     *
     * @return The list of all frames
     */
    public List<ActionBarFrame> getFrames()
    {
        return frames;
    }

    /**
     * Whether this <tt>ActionBar</tt> should loop its frames or not
     *
     * @return Loop state
     */
    public boolean shouldLoop()
    {
        return loop;
    }

    /**
     * Set whether this <tt>ActionBar</tt> should loop its frames or not
     *
     * @param loop The new loop state
     */
    public void setLoop(boolean loop)
    {
        this.loop = loop;
    }

    /**
     * Cancel the displaying of this <tt>ActionBar</tt>. This will do nothing
     * if no runtime is currently running.
     */
    public void cancelDisplay()
    {
        if(runtime == null || runtime.isCancelled()) return;
        runtime.cancel();
    }
}
