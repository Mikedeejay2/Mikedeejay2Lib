package com.mikedeejay2.mikedeejay2lib.text.bossbar.modules.frames;

import com.mikedeejay2.mikedeejay2lib.text.bossbar.BossBarSystem;

/**
 * <tt>BBModule</tt> for setting the title of a boss bar through frames.
 *
 * @author Mikedeejay2
 */
public class BBSetTitleModule extends BBFrameModule<String>
{
    public BBSetTitleModule(boolean loop)
    {
        super(loop);
    }

    @Override
    public void onFrame(BossBarSystem system, long period, String value)
    {
        system.setTitle(value);
    }
}
