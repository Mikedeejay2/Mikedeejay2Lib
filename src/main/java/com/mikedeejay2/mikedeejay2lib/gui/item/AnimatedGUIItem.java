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

    public AnimatedGUIItem(ItemStack item, boolean loop)
    {
        super(item);

        this.loop = loop;
        this.index = 0;
        this.wait = 0;
        this.firstRun = true;
        this.times = new ArrayList<>();
        this.items = new ArrayList<>();
    }

    public boolean tick(long tickTime)
    {
        if(firstRun)
        {
            firstRun = false;
            processFrame();
            return true;
        }
        if(index >= items.size())
        {
            if(loop) index = 0;
            else return false;
        }
        wait += tickTime;
        long curWait = times.get(index);
        if(wait < curWait) return false;
        wait = 0;
        processFrame();
        return true;
    }

    private void processFrame()
    {
        ItemStack item = items.get(index);
        setViewItem(item);
        ++index;
    }

    public void addFrame(ItemStack item, long period)
    {
        times.add(period);
        items.add(item);
    }
}
