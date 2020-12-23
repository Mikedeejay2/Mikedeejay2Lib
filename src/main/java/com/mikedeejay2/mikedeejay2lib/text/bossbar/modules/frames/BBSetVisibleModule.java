package com.mikedeejay2.mikedeejay2lib.text.bossbar.modules.frames;

import com.mikedeejay2.mikedeejay2lib.text.bossbar.BossBarSystem;

/**
 * <tt>BBModule</tt> for setting the visibility of a boss bar through frames.
 *
 * @author Mikedeejay2
 */
public class BBSetVisibleModule extends BBFrameModule<Boolean>
{
    public BBSetVisibleModule(boolean loop)
    {
        super(loop);
    }

    @Override
    public void onFrame(BossBarSystem system, long period, Boolean value)
    {
        system.setVisible(value);
    }
}
