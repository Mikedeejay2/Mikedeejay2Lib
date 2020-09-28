package com.mikedeejay2.mikedeejay2lib.gui.item;

import com.mikedeejay2.mikedeejay2lib.gui.GUIContainer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AnimatedGUIItem extends GUIItem
{
    protected List<Long> times;
    protected List<ItemStack> items;
    protected int index;
    protected long wait;
    protected boolean loop;
    protected boolean firstRun;
    protected long delay;

    public AnimatedGUIItem(ItemStack item, boolean loop)
    {
        this(item, loop, 0);
    }

    public AnimatedGUIItem(ItemStack item, boolean loop, long delay)
    {
        super(item);

        this.delay = delay;
        this.loop = loop;
        this.index = 0;
        this.wait = 0;
        this.firstRun = true;
        this.times = new ArrayList<>();
        this.items = new ArrayList<>();
    }

    public boolean tick(long tickTime)
    {
        wait += tickTime;
        if(firstRun)
        {
            if(wait > delay)
            {
                firstRun = false;
                processFrame(1);
                wait = 0;
            }
            return true;
        }
        if(index >= items.size())
        {
            if(loop) index = index - items.size();
            else return false;
        }
        long curWait = times.get(index);
        if(wait < curWait) return false;
        int framePass = (int)(wait / curWait);
        wait = 0;
        processFrame(framePass);
        return true;
    }

    private void processFrame(int framePass)
    {
        ItemStack item = items.get(index);
        setViewItem(item);
        index += framePass;
    }

    public void addFrame(ItemStack item, long period)
    {
        times.add(period == 0 ? 1 : period);
        items.add(item);
    }
}
