package com.mikedeejay2.mikedeejay2lib.text.bossbar.modules.frames;

import com.mikedeejay2.mikedeejay2lib.text.bossbar.BossBarSystem;
import org.bukkit.boss.BarColor;

/**
 * <tt>BBModule</tt> for setting the color of a boss bar through frames.
 *
 * @author Mikedeejay2
 */
public class BBSetColorModule extends BBFrameModule<BarColor>
{
    public BBSetColorModule(boolean loop)
    {
        super(loop);
    }

    @Override
    public void onFrame(BossBarSystem system, long period, BarColor value)
    {
        system.setColor(value);
    }
}
