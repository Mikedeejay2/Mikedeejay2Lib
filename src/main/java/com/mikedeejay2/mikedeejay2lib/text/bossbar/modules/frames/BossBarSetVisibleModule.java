package com.mikedeejay2.mikedeejay2lib.text.bossbar.modules.frames;

import com.mikedeejay2.mikedeejay2lib.text.bossbar.BossBarSystem;

/**
 * <code>BossBarModule</code> for setting the visibility of a boss bar through frames.
 *
 * @author Mikedeejay2
 */
public class BossBarSetVisibleModule extends BossBarFrameModule<Boolean>
{
    public BossBarSetVisibleModule(boolean loop)
    {
        super(loop);
    }

    @Override
    public void onFrame(BossBarSystem system, long period, Boolean value)
    {
        system.setVisible(value);
    }
}
