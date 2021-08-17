package com.mikedeejay2.mikedeejay2lib.text.bossbar.modules.frames;

import com.mikedeejay2.mikedeejay2lib.text.bossbar.BossBarSystem;

/**
 * <code>BossBarModule</code> for setting the title of a boss bar through frames.
 *
 * @author Mikedeejay2
 */
public class BossBarSetTitleModule extends BossBarFrameModule<String>
{
    /**
     * Construct a new <code>BossBarSetTitleModule</code>
     *
     * @param loop Whether this module should loop the frames or not
     */
    public BossBarSetTitleModule(boolean loop)
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
    public void onFrame(BossBarSystem system, long period, String value)
    {
        system.setTitle(value);
    }
}
