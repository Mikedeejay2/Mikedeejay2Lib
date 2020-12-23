package com.mikedeejay2.mikedeejay2lib.text.title;

import com.mikedeejay2.mikedeejay2lib.runnable.EnhancedRunnable;
import org.bukkit.entity.Player;

import java.util.List;

public class TitleRuntime extends EnhancedRunnable
{
    protected TitleSystem system;
    protected long wait;
    protected boolean firstRun;
    protected int curIndex;
    protected Player[] players;


    public TitleRuntime(TitleSystem system, Player... players)
    {
        this.system = system;
        this.firstRun = true;
        this.curIndex = 0;
        this.wait = 0;
        this.players = players;
    }

    @Override
    public void onRun()
    {
        List<TitleFrame> frames = system.getFrames();
        if(frames.size() == 0) return;
        wait += period;
        if(firstRun)
        {
            if(wait > delay)
            {
                if(curIndex < frames.size()) curIndex = 0;
                firstRun = false;
                TitleFrame curFrame = frames.get(curIndex);
                curFrame.display(players);
                ++curIndex;
                wait = 0;
            }
            return;
        }
        if(curIndex >= frames.size())
        {
            this.cancel();
            return;
        }
        long curWait = frames.get(curIndex - 1 < 0 ? frames.size() - 1 : curIndex - 1).getPeriod();
        if(wait < curWait) return;
        int framePass = (int) (wait / curWait);
        wait = 0;
        TitleFrame curFrame = frames.get(curIndex);
        curFrame.display(players);
        curIndex += framePass;
    }
}
