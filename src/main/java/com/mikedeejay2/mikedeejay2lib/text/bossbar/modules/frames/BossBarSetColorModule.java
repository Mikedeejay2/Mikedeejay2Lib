package com.mikedeejay2.mikedeejay2lib.text.bossbar.modules.frames;

import com.mikedeejay2.mikedeejay2lib.text.bossbar.BossBarSystem;
import org.bukkit.boss.BarColor;

/**
 * <tt>BBModule</tt> for setting the color of a boss bar through frames.
 *
 * @author Mikedeejay2
 */
public class BossBarSetColorModule extends BossBarFrameModule<BarColor>
{
    public BossBarSetColorModule(boolean loop)
    {
        super(loop);
    }

    @Override
    public void onFrame(BossBarSystem system, long period, BarColor value)
    {
        system.setColor(value);
    }
}
