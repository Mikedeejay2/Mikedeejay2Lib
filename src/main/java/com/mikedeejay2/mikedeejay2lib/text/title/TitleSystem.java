package com.mikedeejay2.mikedeejay2lib.text.title;

import com.mikedeejay2.mikedeejay2lib.PluginBase;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class TitleSystem
{
    protected final PluginBase plugin;
    protected List<TitleFrame> frames;
    protected TitleRuntime runtime;

    public TitleSystem(PluginBase plugin)
    {
        this.plugin = plugin;
        this.frames = new ArrayList<>();
    }

    public void display(long period, long delay, Player... players)
    {
        this.runtime = new TitleRuntime(this, players);
        runtime.runTaskTimerAsynchronously(plugin, delay, period);
    }

    public void display(long period, Player... players)
    {
        this.display(period, 0, players);
    }

    public void display(Player... players)
    {
        this.display(1, 0, players);
    }

    public List<TitleFrame> getFrames()
    {
        return frames;
    }

    public void stop()
    {
        if(runtime == null || runtime.isCancelled()) return;
        runtime.cancel();
    }

    public TitleSystem addFrame(TitleFrame frame)
    {
        frames.add(frame);
        return this;
    }

    public TitleSystem addFrame(String title, String subtitle, int fadeIn, int stay, int fadeOut, long period)
    {
        TitleFrame frame = new TitleFrame(title, subtitle, fadeIn, stay, fadeOut, period);
        frames.add(frame);
        return this;
    }

    public boolean containsFrame(TitleFrame frame)
    {
        return frames.contains(frame);
    }

    public TitleSystem removeFrame(TitleFrame frame)
    {
        frames.remove(frame);
        return this;
    }

    public TitleSystem removeFrame(int index)
    {
        frames.remove(index);
        return this;
    }

    public TitleFrame getFrame(int index)
    {
        return frames.get(index);
    }
}
