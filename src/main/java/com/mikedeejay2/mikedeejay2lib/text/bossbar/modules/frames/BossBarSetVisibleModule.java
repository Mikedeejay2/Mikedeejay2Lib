package com.mikedeejay2.mikedeejay2lib.text.bossbar.modules.frames;

import com.mikedeejay2.mikedeejay2lib.text.bossbar.BossBarSystem;

/**
 * <code>BossBarModule</code> for setting the visibility of a boss bar through frames.
 *
 * @author Mikedeejay2
 */
public class BossBarSetVisibleModule extends BossBarFrameModule<Boolean> {
    /**
     * Construct a new <code>BossBarSetVisibleModule</code>
     *
     * @param loop Whether this module should loop the frames or not
     */
    public BossBarSetVisibleModule(boolean loop) {
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
    public void onFrame(BossBarSystem system, long period, Boolean value) {
        system.setVisible(value);
    }
}
