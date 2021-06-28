package com.mikedeejay2.mikedeejay2lib.text.bossbar.modules.frames;

import com.mikedeejay2.mikedeejay2lib.text.bossbar.BossBarSystem;
import org.bukkit.boss.BarStyle;

/**
 * <code>BossBarModule</code> for setting the style of a boss bar through frames.
 *
 * @author Mikedeejay2
 */
public class BossBarSetStyleModule extends BossBarFrameModule<BarStyle>
{
    public BossBarSetStyleModule(boolean loop)
    {
        super(loop);
    }

    @Override
    public void onFrame(BossBarSystem system, long period, BarStyle value)
    {
        system.setStyle(value);
    }
}
