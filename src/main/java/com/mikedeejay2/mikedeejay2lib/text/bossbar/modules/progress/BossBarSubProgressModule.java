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
    /**
     * The progress to subtract from the boss bar (between 0.0-1.0)
     */
    protected double progress;

    /**
     * Construct a new <code>BossBarSubProgressModule</code>
     *
     * @param progress The progress to subtract from the boss bar (between 0.0-1.0)
     */
    public BossBarSubProgressModule(double progress)
    {
        this.progress = progress;
    }

    /**
     * {@inheritDoc}
     *
     * @param system The <code>BossBarSystem</code> being ticked
     */
    @Override
    public void onTick(BossBarSystem system)
    {
        double newProgress = Math.abs((system.getProgress() - progress) % 1);
        system.setProgress(newProgress);
    }
}
