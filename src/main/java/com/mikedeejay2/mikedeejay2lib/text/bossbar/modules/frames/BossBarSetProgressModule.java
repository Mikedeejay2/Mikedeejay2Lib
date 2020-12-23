package com.mikedeejay2.mikedeejay2lib.text.bossbar.modules.frames;

import com.mikedeejay2.mikedeejay2lib.text.bossbar.BossBarSystem;

/**
 * <tt>BBModule</tt> for setting the progress of a boss bar through frames.
 *
 * @author Mikedeejay2
 */
public class BossBarSetProgressModule extends BossBarFrameModule<Double>
{
    public BossBarSetProgressModule(boolean loop)
    {
        super(loop);
    }

    @Override
    public void onFrame(BossBarSystem system, long period, Double value)
    {
        system.setProgress(value);
    }
}
