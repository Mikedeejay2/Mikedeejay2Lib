package com.mikedeejay2.mikedeejay2lib.text.actionbar;

import com.mikedeejay2.mikedeejay2lib.BukkitPlugin;
import com.mikedeejay2.mikedeejay2lib.text.actionbar.animation.ActionBarRuntime;
import com.mikedeejay2.mikedeejay2lib.text.actionbar.modules.ActionBarModule;
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
    protected final BukkitPlugin plugin;
    // Whether the frames of the action bar will loop or not
    protected boolean loop;
    // The list of frames of the action bar
    protected List<ActionBarFrame> frames;
    // The action bar runtime, null if not displayed yet
    protected ActionBarRuntime runtime;
    // The list of action bar modules of the action bar
    protected List<ActionBarModule> modules;

    public ActionBar(BukkitPlugin plugin)
    {
        this.plugin = plugin;
        this.frames = new ArrayList<>();
        this.modules = new ArrayList<>();
    }

    public ActionBar(BukkitPlugin plugin, boolean loop)
    {
        this.plugin = plugin;
        this.loop = loop;
        this.frames = new ArrayList<>();
        this.modules = new ArrayList<>();
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
     * Add a new <code>ActionBarFrame</code> to this <code>ActionBar</code>
     *
     * @param text  The String of text for the frame to display
     * @param delay The delay between this frame and the next frame
     * @return The current <code>ActionBar</code>
     */
    public ActionBar addFrame(String text, long delay)
    {
        ActionBarFrame frame = new ActionBarFrame(text, delay);
        frames.add(frame);
        return this;
    }

    /**
     * Add an existing <code>ActionBarFrame</code> to this <code>ActionBar</code>
     *
     * @param frame The <code>ActionBarFrame</code> to add
     * @return The current <code>ActionBar</code>
     */
    public ActionBar addFrame(ActionBarFrame frame)
    {
        frames.add(frame);
        return this;
    }

    /**
     * Remove a <code>ActionBarFrame</code> from this <code>ActionBar</code> with the index of the frame
     *
     * @param index The index to remove the frame from
     * @return The current <code>ActionBar</code>
     */
    public ActionBar removeFrame(int index)
    {
        frames.remove(index);
        return this;
    }

    /**
     * Remove a <code>ActionBarFrame</code> from this <code>ActionBar</code> with a reference to the frame
     *
     * @param frame The <code>ActionBarFrame</code> to remove
     * @return The current <code>ActionBar</code>
     */
    public ActionBar removeFrame(ActionBarFrame frame)
    {
        frames.remove(frame);
        return this;
    }

    /**
     * See whether this <code>ActionBar</code> contains a specific <code>ActionBarFrame</code>
     *
     * @param frame The <code>ActionBarFrame</code> to search for
     * @return Whether the specified frame was found or not
     */
    public boolean containsFrame(ActionBarFrame frame)
    {
        return frames.contains(frame);
    }

    /**
     * Reset (clear) all frames in this <code>ActionBar</code>
     *
     * @return The current <code>ActionBar</code>
     */
    public ActionBar resetFrames()
    {
        frames.clear();
        return this;
    }

    /**
     * Get a <code>ActionBarFrame</code> based off of its index
     *
     * @param index The index to get the frame from
     * @return The requested frame
     */
    public ActionBarFrame getFrame(int index)
    {
        return frames.get(index);
    }

    /**
     * Get the list of all frames in this <code>ActionBar</code>
     *
     * @return The list of all frames
     */
    public List<ActionBarFrame> getFrames()
    {
        return frames;
    }

    /**
     * Whether this <code>ActionBar</code> should loop its frames or not
     *
     * @return Loop state
     */
    public boolean shouldLoop()
    {
        return loop;
    }

    /**
     * Set whether this <code>ActionBar</code> should loop its frames or not
     *
     * @param loop The new loop state
     */
    public void setLoop(boolean loop)
    {
        this.loop = loop;
    }

    /**
     * Cancel the displaying of this <code>ActionBar</code>. This will do nothing
     * if no runtime is currently running.
     */
    public void cancelDisplay()
    {
        if(runtime == null || runtime.isCancelled()) return;
        runtime.cancel();
    }

    /**
     * Add an <code>ActionBarModule</code> to this action bar
     *
     * @param module The <code>ActionBarModule</code> to add
     * @return The current <code>ActionBar</code>
     */
    public ActionBar addModule(ActionBarModule module)
    {
        modules.add(module);
        return this;
    }

    /**
     * Add a <code>ActionBarModule</code> to this action bar at a specified index
     *
     * @param module The <code>ActionBarModule</code> to add
     * @param index  The index to add the module at
     * @return The current <code>ActionBar</code>
     */
    public ActionBar addModule(ActionBarModule module, int index)
    {
        modules.add(index, module);
        return this;
    }

    /**
     * Remove an <code>ActionBarModule</code> from this action bar based off of a reference to the module
     *
     * @param module The module to remove
     * @return The current <code>ActionBar</code>
     */
    public ActionBar removeModule(ActionBarModule module)
    {
        modules.remove(module);
        return this;
    }

    /**
     * Remove an <code>ActionBarModule</code> from this action bar based off of the module's index
     *
     * @param index The index of the module to remove
     * @return The current <code>ActionBar</code>
     */
    public ActionBar removeModule(int index)
    {
        modules.remove(index);
        return this;
    }

    /**
     * See whether this <code>ActionBar</code> contains a specific <code>ActionBarModule</code>
     *
     * @param module The <code>ActionBarModule</code> to search for
     * @return Whether the <code>ActionBarModule</code> was found or not
     */
    public boolean containsModule(ActionBarModule module)
    {
        return modules.contains(module);
    }

    /**
     * See whether this <code>ActionBar</code> contains the class of an <code>ActionBarModule</code>
     *
     * @param moduleClass The class of the module to search for
     * @return Whether the <code>ActionBarModule</code> was found or not
     */
    public boolean containsModule(Class<? extends ActionBarModule> moduleClass)
    {
        for(ActionBarModule module : modules)
        {
            if(moduleClass == module.getClass()) return true;
        }
        return false;
    }

    /**
     * Get an <code>ActionBarModule</code> based off of the index of the module
     *
     * @param index The index to get the module from
     * @return The requested <code>ActionBarModule</code>
     */
    public ActionBarModule getModule(int index)
    {
        return modules.get(index);
    }

    /**
     * Get an <code>ActionBarModule</code> based off of the class type of the module
     *
     * @param moduleClass The class of the module to get
     * @param <T>         The class type of the module
     * @return The a <code>ActionBarModule</code> of the specified class, null if not found
     */
    public <T extends ActionBarModule> T getModule(Class<? extends ActionBarModule> moduleClass)
    {
        for(ActionBarModule module : modules)
        {
            if(moduleClass == module.getClass()) return (T) module;
        }
        return null;
    }

    /**
     * Get the list of all <code>ActionBarModules</code> in this action bar
     *
     * @return The list of all <code>ActionBarModules</code>
     */
    public List<ActionBarModule> getModules()
    {
        return modules;
    }

    /**
     * Reset (clear) the <code>ActionBarModules</code> from this action bar
     *
     * @return The current <code>ActionBar</code>
     */
    public ActionBar resetModules()
    {
        modules.clear();
        return this;
    }
}
