package com.mikedeejay2.mikedeejay2lib.text.actionbar;

import com.mikedeejay2.mikedeejay2lib.PluginBase;
import com.mikedeejay2.mikedeejay2lib.text.actionbar.animation.ActionBarRuntime;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class ActionBar
{
    protected final PluginBase plugin;
    protected boolean loop;
    protected List<ActionBarFrame> frames;
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

    public void display(long period, Player... players)
    {
        this.runtime = new ActionBarRuntime(this, players);
        runtime.runTaskTimerAsynchronously(plugin, period);
    }

    public void display(Player... players)
    {
        this.display(1, players);
    }

    public void displayCount(long period, long count, Player... players)
    {
        this.runtime = new ActionBarRuntime(this, players);
        runtime.runTaskTimerAsynchronously(plugin, period);
    }

    public void displayCount(long count, Player... players)
    {
        this.displayCount(1, count, players);
    }

    public ActionBar addFrame(String text, long delay)
    {
        ActionBarFrame frame = new ActionBarFrame(text, delay);
        frames.add(frame);
        return this;
    }

    public ActionBar addFrame(ActionBarFrame frame)
    {
        frames.add(frame);
        return this;
    }

    public ActionBar removeFrame(int index)
    {
        frames.remove(index);
        return this;
    }

    public ActionBar resetFrames()
    {
        frames.clear();
        return this;
    }

    public ActionBarFrame getFrame(int index)
    {
        return frames.get(index);
    }

    public List<ActionBarFrame> getFrames()
    {
        return frames;
    }

    public boolean shouldLoop()
    {
        return loop;
    }

    public void setLoop(boolean loop)
    {
        this.loop = loop;
    }

    public void cancelDisplay()
    {
        if(runtime == null || runtime.isCancelled()) return;
        runtime.cancel();
    }
}
