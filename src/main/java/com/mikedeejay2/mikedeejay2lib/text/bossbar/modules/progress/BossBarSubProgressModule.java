package com.mikedeejay2.mikedeejay2lib.text.bossbar.modules.progress;

import com.mikedeejay2.mikedeejay2lib.text.bossbar.BossBarSystem;
import com.mikedeejay2.mikedeejay2lib.text.bossbar.modules.BossBarModule;

/**
 * <code>BossBarModule</code> for constantly subtracting an amount of progress to a boss bar.
 *
 * @author Mikedeejay2
 */
public class BossBarSubProgressModule implements BossBarModule
{
    protected double progress;

    public BossBarSubProgressModule(double progress)
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
