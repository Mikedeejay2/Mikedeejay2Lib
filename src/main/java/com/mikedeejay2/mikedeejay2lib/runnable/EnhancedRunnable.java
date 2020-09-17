package com.mikedeejay2.mikedeejay2lib.runnable;

import com.mikedeejay2.mikedeejay2lib.PluginBase;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;

/**
 * EnhancedRunnable does several things on top of BukkitRunnable.
 * <br>
 * The features that this class adds is:
 * <ul>
 *     <li>A counter that increments by 1 every run, useful for seeing if this runnable is at a certain amount of runs</li>
 *     <li>Add a counted timer that stops the runnable once it reaches a certain count</li>
 *     <li>Add a bunch of methods that let you .runTask() without having to specify all parameters.</li>
 *     <li>Add an optional methods for the first run and the last run of this runnable that can be overridden so functionality
 *     can be implemented specifically at the start and end of this runnable</li>
 * </ul>
 */
public abstract class EnhancedRunnable extends BukkitRunnable
{
    // Current count of the amount of times that this runnable has been run
    private long count;
    // The end count, the count that the runnable should stop at. If 0, don't stop.
    private long endCount;

    public EnhancedRunnable()
    {
        this.count = 0;
        this.endCount = 0;
    }

    /**
     * Run method. Marked as final because it should not be overridden because it contains functionality
     * that is required for other parts of this runnable to function. Use onRun() for the same functionality
     * of a standard BukkitRunnable.
     * @see EnhancedRunnable#onRun()
     */
    @Override
    public final void run()
    {
        if(endCount > 0)
        {
            if(this.count >= endCount)
            {
                this.cancel();
                return;
            }
        }
        if(count == 0) onFirstRun();
        onRun();
        ++count;
    }

    /**
     * This method is called in the run() method and is the main entry point of code
     * for developers.
     */
    public abstract void onRun();

    /**
     * If something should be run only on the first run of this runnable, this method can
     * be overridden to add that functionality here.
     */
    public void onFirstRun() {}

    /**
     * If something should be run only on the last run of this runnable, this method can
     * be overridden to add that functionality here.
     */
    public void onLastRun() {}

    /**
     * Attempts to cancel this task. Overridden to add functionality to onLastRun()
     * @see BukkitRunnable#cancel()
     * @see EnhancedRunnable#onLastRun()
     */
    @Override
    public synchronized void cancel()
    {
        super.cancel();
        onLastRun();
    }

    /**
     * Schedules this in the Bukkit scheduler to run on next tick.
     *
     * @param plugin the reference to the plugin scheduling task
     * @return a BukkitTask that contains the id number
     */
    @Override
    public synchronized BukkitTask runTask(Plugin plugin)
    {
        return super.runTask(plugin);
    }

    /**
     * Schedules this in the Bukkit scheduler to run asynchronously.
     *
     * @param plugin the reference to the plugin scheduling task
     * @return a BukkitTask that contains the id number
     */
    @Override
    public synchronized BukkitTask runTaskAsynchronously(Plugin plugin)
    {
        return super.runTaskAsynchronously(plugin);
    }

    /**
     * Schedules this to run after the specified number of server ticks.
     *
     * @param plugin the reference to the plugin scheduling task
     * @param delay the ticks to wait before running the task
     * @return a BukkitTask that contains the id number
     */
    @Override
    public synchronized BukkitTask runTaskLater(Plugin plugin, long delay)
    {
        return super.runTaskLater(plugin, delay);
    }

    /**
     * Schedules this to run after the specified number of server ticks.
     *
     * @return a BukkitTask that contains the id number
     */
    public synchronized BukkitTask runTaskLater(Plugin plugin)
    {
        return this.runTaskLater(plugin, 0);
    }

    /**
     * Schedules this to run asynchronously after the specified number of server ticks.
     *
     * @param plugin the reference to the plugin scheduling task
     * @param delay the ticks to wait before running the task
     * @return a BukkitTask that contains the id number
     */
    @Override
    public synchronized BukkitTask runTaskLaterAsynchronously(Plugin plugin, long delay)
    {
        return super.runTaskLaterAsynchronously(plugin, delay);
    }

    /**
     * Schedules this to run asynchronously after the specified number of server ticks.
     *
     * @return a BukkitTask that contains the id number
     */
    public synchronized BukkitTask runTaskLaterAsynchronously(Plugin plugin)
    {
        return this.runTaskLaterAsynchronously(plugin, 0);
    }

    /**
     * Schedules this to repeatedly run until cancelled, starting after the specified number of server ticks.
     *
     * @param plugin the reference to the plugin scheduling task
     * @param delay the ticks to wait before running the task
     * @param period the ticks to wait between runs
     * @return a BukkitTask that contains the id number
     */
    @Override
    public synchronized BukkitTask runTaskTimer(Plugin plugin, long delay, long period)
    {
        return super.runTaskTimer(plugin, delay, period);
    }

    /**
     * Schedules this to repeatedly run until cancelled, starting after the specified number of server ticks.
     *
     * @return a BukkitTask that contains the id number
     */
    public synchronized BukkitTask runTaskTimer(Plugin plugin)
    {
        return this.runTaskTimer(plugin, 0, 0);
    }

    /**
     * Schedules this to repeatedly run until cancelled, starting after the specified number of server ticks.
     *
     * @param period the ticks to wait between runs
     * @return a BukkitTask that contains the id number
     */
    public synchronized BukkitTask runTaskTimer(Plugin plugin, long period)
    {
        return this.runTaskTimer(plugin, 0, period);
    }

    /**
     * Schedules this to repeatedly run asynchronously until cancelled, starting after the specified number of server ticks.
     *
     * @param plugin the reference to the plugin scheduling task
     * @param delay the ticks to wait before running the task
     * @param period the ticks to wait between runs
     * @return a BukkitTask that contains the id number
     */
    @Override
    public synchronized BukkitTask runTaskTimerAsynchronously(Plugin plugin, long delay, long period)
    {
        return super.runTaskTimerAsynchronously(plugin, delay, period);
    }

    /**
     * Schedules this to repeatedly run asynchronously until cancelled, starting after the specified number of server ticks.
     *
     * @return a BukkitTask that contains the id number
     */
    public synchronized BukkitTask runTaskTimerAsynchronously(Plugin plugin)
    {
        return this.runTaskTimerAsynchronously(plugin, 0, 0);
    }

    /**
     * Schedules this to repeatedly run asynchronously until cancelled, starting after the specified number of server ticks.
     *
     * @param period the ticks to wait between runs
     * @return a BukkitTask that contains the id number
     */
    public synchronized BukkitTask runTaskTimerAsynchronously(Plugin plugin, long period)
    {
        return this.runTaskTimerAsynchronously(plugin, 0, period);
    }

    /**
     * Schedules this to repeatedly run until cancelled, starting after the specified number of server ticks.
     * Stops when the runnable has been run for count times
     *
     * @param plugin the reference to the plugin scheduling task
     * @param delay the ticks to wait before running the task
     * @param period the ticks to wait between runs
     * @param count the amount of times that this runnable should be run before it is stopped
     * @return a BukkitTask that contains the id number
     */
    public synchronized BukkitTask runTaskTimerCounted(Plugin plugin, long delay, long period, long count)
    {
        this.endCount = count;
        return super.runTaskTimer(plugin, delay, period);
    }

    /**
     * Schedules this to repeatedly run until cancelled, starting after the specified number of server ticks.
     * Stops when the runnable has been run for count times
     *
     * @return a BukkitTask that contains the id number
     */
    public synchronized BukkitTask runTaskTimerCounted(Plugin plugin)
    {
        return this.runTaskTimerCounted(plugin, 0, 0, 0);
    }

    /**
     * Schedules this to repeatedly run until cancelled, starting after the specified number of server ticks.
     * Stops when the runnable has been run for count times
     *
     * @param count the amount of times that this runnable should be run before it is stopped
     * @return a BukkitTask that contains the id number
     */
    public synchronized BukkitTask runTaskTimerCounted(Plugin plugin, long count)
    {
        return this.runTaskTimerCounted(plugin, 0, 0, count);
    }

    /**
     * Schedules this to repeatedly run until cancelled, starting after the specified number of server ticks.
     * Stops when the runnable has been run for count times
     *
     * @param period the ticks to wait between runs
     * @param count the amount of times that this runnable should be run before it is stopped
     * @return a BukkitTask that contains the id number
     */
    public synchronized BukkitTask runTaskTimerCounted(Plugin plugin, long period, long count)
    {
        return this.runTaskTimerCounted(plugin, 0, period, count);
    }

    /**
     * Schedules this to repeatedly run asynchronously until cancelled, starting after the specified number of server ticks.
     * Stops when the runnable has been run for count times
     *
     * @param plugin the reference to the plugin scheduling task
     * @param delay the ticks to wait before running the task
     * @param period the ticks to wait between runs
     * @param count the amount of times that this runnable should be run before it is stopped
     * @return a BukkitTask that contains the id number
     */
    public synchronized BukkitTask runTaskTimerCountedAsynchronously(Plugin plugin, long delay, long period, long count)
    {
        this.endCount = count;
        return super.runTaskTimerAsynchronously(plugin, delay, period);
    }

    /**
     * Schedules this to repeatedly run asynchronously until cancelled, starting after the specified number of server ticks.
     * Stops when the runnable has been run for count times
     *
     * @return a BukkitTask that contains the id number
     */
    public synchronized BukkitTask runTaskTimerCountedAsynchronously(Plugin plugin)
    {
        return this.runTaskTimerCountedAsynchronously(plugin, 0, 0, 0);
    }

    /**
     * Schedules this to repeatedly run asynchronously until cancelled, starting after the specified number of server ticks.
     * Stops when the runnable has been run for count times
     *
     * @param count the amount of times that this runnable should be run before it is stopped
     * @return a BukkitTask that contains the id number
     */
    public synchronized BukkitTask runTaskTimerCountedAsynchronously(Plugin plugin, long count)
    {
        return this.runTaskTimerCountedAsynchronously(plugin, 0, 0, count);
    }

    /**
     * Schedules this to repeatedly run asynchronously until cancelled, starting after the specified number of server ticks.
     * Stops when the runnable has been run for count times
     *
     * @param period the ticks to wait between runs
     * @param count the amount of times that this runnable should be run before it is stopped
     * @return a BukkitTask that contains the id number
     */
    public synchronized BukkitTask runTaskTimerCountedAsynchronously(Plugin plugin, long period, long count)
    {
        return this.runTaskTimerCountedAsynchronously(plugin, 0, period, count);
    }

    /**
     * Get the current amount of times that this runnable has been run
     *
     * @return The current runnable run count
     */
    public long getCount()
    {
        return count;
    }

    /**
     * Get the count that this runnable should stop at
     *
     * @return The end count of this runnable
     */
    public long getEndCount()
    {
        return endCount;
    }

    /**
     * Set the count that this runnable should stop at
     *
     * @param endCount The count that this runnable should stop at
     */
    public void setEndCount(long endCount)
    {
        this.endCount = endCount;
    }
}