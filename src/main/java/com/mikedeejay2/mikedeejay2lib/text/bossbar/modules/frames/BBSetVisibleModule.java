package com.mikedeejay2.mikedeejay2lib.text.bossbar.modules.frames;

import com.mikedeejay2.mikedeejay2lib.text.bossbar.BossBarSystem;

public class BBSetVisibleModule extends BBFrameModule<Boolean>
{
    public BBSetVisibleModule(boolean loop)
    {
        super(loop);
    }

    @Override
    public void onFrame(BossBarSystem system, Boolean value)
    {
        system.setVisible(value);
    }
}
