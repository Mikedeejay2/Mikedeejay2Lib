package com.mikedeejay2.mikedeejay2lib.text.bossbar.modules.frames;

import com.mikedeejay2.mikedeejay2lib.text.bossbar.BossBarSystem;
import com.mikedeejay2.mikedeejay2lib.text.bossbar.modules.BBModule;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class BBFrameModule<T> extends BBModule
{
    protected List<Map.Entry<Long, T>> frames;
    protected int curIndex;
    protected int wait;
    protected boolean loop;

    public BBFrameModule(boolean loop)
    {
        this.frames = new ArrayList<>();
        this.loop = loop;
    }

    @Override
    public void onTick(BossBarSystem system)
    {
        ++wait;
        if(curIndex >= frames.size())
        {
            if(loop)
            {
                curIndex = 0;
            }
            else return;
        }
        long curWait = frames.get(curIndex - 1 < 0 ? frames.size() - 1 : curIndex - 1).getKey();
        if(wait < curWait) return;
        T value = frames.get(curIndex).getValue();
        onFrame(system, value);
        ++curIndex;
        wait -= curWait;
    }

    public abstract void onFrame(BossBarSystem system, T value);

    public BBFrameModule<T> addFrame(long period, T value, int index)
    {
        frames.add(index, new AbstractMap.SimpleEntry<>(period, value));
        return this;
    }

    public BBFrameModule<T> addFrame(long period, T value)
    {
        frames.add(new AbstractMap.SimpleEntry<>(period, value));
        return this;
    }

    public BBFrameModule<T> removeFrame(int index)
    {
        frames.remove(index);
        return this;
    }

    public BBFrameModule<T> removeFrame(String text)
    {
        for(Map.Entry<Long, T> entry : frames)
        {
            T value = entry.getValue();
            if(!value.equals(text)) continue;
            frames.remove(entry);
            break;
        }
        return this;
    }

    public List<Map.Entry<Long, T>> getFrames()
    {
        return frames;
    }
}
