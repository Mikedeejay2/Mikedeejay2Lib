package com.mikedeejay2.mikedeejay2lib.text.bossbar.modules.progress;

import com.mikedeejay2.mikedeejay2lib.text.bossbar.BossBarSystem;
import com.mikedeejay2.mikedeejay2lib.text.bossbar.modules.BBModule;

/**
 * <tt>BBModule</tt> for constantly adding an amount of progress to a boss bar.
 *
 * @author Mikedeejay2
 */
public class BBAddProgressModule extends BBModule
{
    protected double progress;

    public BBAddProgressModule(double progress)
    {
        this.progress = progress;
    }

    @Override
    public void onTick(BossBarSystem system)
    {
        double newProgress = Math.abs((system.getProgress() + progress) % 1);
        system.setProgress(newProgress);
    }
}
