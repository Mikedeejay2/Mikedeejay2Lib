package com.mikedeejay2.mikedeejay2lib.text.actionbar.animation;

import com.mikedeejay2.mikedeejay2lib.runnable.EnhancedRunnable;
import com.mikedeejay2.mikedeejay2lib.text.actionbar.ActionBar;
import com.mikedeejay2.mikedeejay2lib.text.actionbar.ActionBarFrame;
import org.bukkit.entity.Player;

import java.util.List;

public class ActionBarRuntime extends EnhancedRunnable
{
    protected Player[] players;
    protected ActionBar actionBar;
    protected int curIndex;
    protected int wait;
    protected boolean firstRun;

    public ActionBarRuntime(ActionBar actionBar, Player... players)
    {
        this.players = players;
        this.actionBar = actionBar;
        this.curIndex = 0;
        this.wait = 0;
        this.firstRun = true;
    }

    @Override
    public void onRun()
    {
        List<ActionBarFrame> frames = actionBar.getFrames();
        if(frames.size() == 0) return;
        wait += period;
        if(firstRun)
        {
            if(wait > delay)
            {
                if(curIndex < frames.size()) curIndex = 0;
                firstRun = false;
                ActionBarFrame curFrame = frames.get(curIndex);
                curFrame.display(players);
                ++curIndex;
                wait = 0;
            }
            return;
        }
        if(curIndex >= frames.size())
        {
            if(actionBar.shouldLoop())
            {
                curIndex -= frames.size();
            }
            else
            {
                this.cancel();
                return;
            }
        }
        long curWait = frames.get(curIndex - 1 < 0 ? frames.size() - 1 : curIndex - 1).getPeriod();
        if(wait < curWait) return;
        int framePass = (int) (wait / curWait);
        wait = 0;
        ActionBarFrame curFrame = frames.get(curIndex);
        curFrame.display(players);
        curIndex += framePass;
    }
}
