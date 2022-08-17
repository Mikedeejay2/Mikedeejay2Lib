package com.mikedeejay2.mikedeejay2lib.text.bossbar.modules.frames;

import com.mikedeejay2.mikedeejay2lib.text.bossbar.BossBarSystem;
import org.bukkit.boss.BarColor;

/**
 * <code>BossBarModule</code> for setting the color of a boss bar through frames.
 *
 * @author Mikedeejay2
 */
public class BossBarSetColorModule extends BossBarFrameModule<BarColor> {
    /**
     * Construct a new <code>BossBarSetColorModule</code>
     *
     * @param loop Whether this module should loop the frames or not
     */
    public BossBarSetColorModule(boolean loop) {
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
    public void onFrame(BossBarSystem system, long period, BarColor value) {
        system.setColor(value);
    }
}
