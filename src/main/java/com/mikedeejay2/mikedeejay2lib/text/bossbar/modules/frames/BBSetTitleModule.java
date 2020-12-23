package com.mikedeejay2.mikedeejay2lib.text.bossbar.modules.frames;

import com.mikedeejay2.mikedeejay2lib.text.bossbar.BossBarSystem;

public class BBSetTitleModule extends BBFrameModule<String>
{
    public BBSetTitleModule(boolean loop)
    {
        super(loop);
    }

    @Override
    public void onFrame(BossBarSystem system, String value)
    {
        system.setTitle(value);
    }
}
