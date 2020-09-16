package com.mikedeejay2.mikedeejay2lib.runnable;

import com.mikedeejay2.mikedeejay2lib.PluginBase;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitTask;

public class EnhancedRunnable extends BukkitRunnable
{
    private static final PluginBase plugin = PluginBase.getInstance();
    private Runnable runnable;
    private Runnable cancelRunnable;
    private long count;
    private long endCount;

    public EnhancedRunnable(Runnable runnable)
    {
        this.runnable = runnable;
        this.cancelRunnable = null;
        this.count = 0;
        this.endCount = 0;
    }

    public EnhancedRunnable(Runnable runnable, Runnable cancelRunnable)
    {
        this.runnable = runnable;
        this.cancelRunnable = cancelRunnable;
        this.count = 0;
        this.endCount = 0;
    }

    @Override
    public void run()
    {
        if(endCount > 0)
        {
            if(this.count >= endCount) this.cancel();
        }
        ++count;
        runnable.run();
    }

    @Override
    public synchronized void cancel() throws IllegalStateException
    {
        super.cancel();
        cancelRunnable.run();
    }

    @Override
    public synchronized BukkitTask runTask(Plugin plugin)
    {
        return super.runTask(plugin);
    }

    public synchronized BukkitTask runTask()
    {
        return this.runTask(plugin);
    }

    @Override
    public synchronized BukkitTask runTaskAsynchronously(Plugin plugin)
    {
        return super.runTaskAsynchronously(plugin);
    }

    public synchronized BukkitTask runTaskAsynchronously()
    {
        return this.runTaskAsynchronously(plugin);
    }

    @Override
    public synchronized BukkitTask runTaskLater(Plugin plugin, long delay)
    {
        return super.runTaskLater(plugin, delay);
    }

    public synchronized BukkitTask runTaskLater()
    {
        return this.runTaskLater(plugin, 0);
    }

    public synchronized BukkitTask runTaskLater(long delay)
    {
        return this.runTaskLater(plugin, delay);
    }

    @Override
    public synchronized BukkitTask runTaskLaterAsynchronously(Plugin plugin, long delay)
    {
        return super.runTaskLaterAsynchronously(plugin, delay);
    }

    public synchronized BukkitTask runTaskLaterAsynchronously()
    {
        return this.runTaskLaterAsynchronously(plugin, 0);
    }

    public synchronized BukkitTask runTaskLaterAsynchronously(long delay)
    {
        return this.runTaskLaterAsynchronously(plugin, delay);
    }

    @Override
    public synchronized BukkitTask runTaskTimer(Plugin plugin, long delay, long period)
    {
        return super.runTaskTimer(plugin, delay, period);
    }

    public synchronized BukkitTask runTaskTimer()
    {
        return this.runTaskTimer(plugin, 0, 0);
    }

    public synchronized BukkitTask runTaskTimer(long period)
    {
        return this.runTaskTimer(plugin, 0, period);
    }

    public synchronized BukkitTask runTaskTimer(long delay, long period)
    {
        return this.runTaskTimer(plugin, delay, period);
    }

    @Override
    public synchronized BukkitTask runTaskTimerAsynchronously(Plugin plugin, long delay, long period)
    {
        return super.runTaskTimerAsynchronously(plugin, delay, period);
    }

    public synchronized BukkitTask runTaskTimerAsynchronously()
    {
        return this.runTaskTimerAsynchronously(plugin, 0, 0);
    }

    public synchronized BukkitTask runTaskTimerAsynchronously(long period)
    {
        return this.runTaskTimerAsynchronously(plugin, 0, period);
    }

    public synchronized BukkitTask runTaskTimerAsynchronously(long delay, long period)
    {
        return this.runTaskTimerAsynchronously(plugin, delay, period);
    }

    public synchronized BukkitTask runTaskTimerCounted(Plugin plugin, long delay, long period, long count)
    {
        this.endCount = count;
        return super.runTaskTimer(plugin, delay, period);
    }

    public synchronized BukkitTask runTaskTimerCounted()
    {
        return this.runTaskTimerCounted(plugin, 0, 0, 0);
    }

    public synchronized BukkitTask runTaskTimerCounted(long count)
    {
        return this.runTaskTimerCounted(plugin, 0, 0, count);
    }

    public synchronized BukkitTask runTaskTimerCounted(long period, long count)
    {
        return this.runTaskTimerCounted(plugin, 0, period, count);
    }

    public synchronized BukkitTask runTaskTimerCounted(long delay, long period, long count)
    {
        return this.runTaskTimerCounted(plugin, delay, period, count);
    }

    public synchronized BukkitTask runTaskTimerCountedAsynchronously(Plugin plugin, long delay, long period, long count)
    {
        this.endCount = count;
        return super.runTaskTimerAsynchronously(plugin, delay, period);
    }

    public synchronized BukkitTask runTaskTimerCountedAsynchronously()
    {
        return this.runTaskTimerCountedAsynchronously(plugin, 0, 0, 0);
    }

    public synchronized BukkitTask runTaskTimerCountedAsynchronously(long count)
    {
        return this.runTaskTimerCountedAsynchronously(plugin, 0, 0, count);
    }

    public synchronized BukkitTask runTaskTimerCountedAsynchronously(long period, long count)
    {
        return this.runTaskTimerCountedAsynchronously(plugin, 0, period, count);
    }

    public synchronized BukkitTask runTaskTimerCountedAsynchronously(long delay, long period, long count)
    {
        return this.runTaskTimerCountedAsynchronously(plugin, delay, period, count);
    }

    public long getCount()
    {
        return count;
    }

    public long getEndCount()
    {
        return endCount;
    }

    public void setEndCount(long endCount)
    {
        this.endCount = endCount;
    }
}