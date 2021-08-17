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
    /**
     * Construct a new <code>BossBarSetStyleModule</code>
     *
     * @param loop Whether this module should loop the frames or not
     */
    public BossBarSetStyleModule(boolean loop)
    {
        super(loop);
    }

    /**
     * {@inheritDoc}
     *
     * @param system The <code>BossBarSystem</code> that should be modified
     * @param period The period of the current frame
     * @param value  The value of the frame
     */
    @Override
    public void onFrame(BossBarSystem system, long period, BarStyle value)
    {
        system.setStyle(value);
    }
}
