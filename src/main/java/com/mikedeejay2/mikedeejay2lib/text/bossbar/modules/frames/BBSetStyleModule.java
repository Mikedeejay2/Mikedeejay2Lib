package com.mikedeejay2.mikedeejay2lib.text.bossbar.modules.frames;

import com.mikedeejay2.mikedeejay2lib.text.bossbar.BossBarSystem;
import org.bukkit.boss.BarStyle;

public class BBSetStyleModule extends BBFrameModule<BarStyle>
{
    public BBSetStyleModule(boolean loop)
    {
        super(loop);
    }

    @Override
    public void onFrame(BossBarSystem system, BarStyle value)
    {
        system.setStyle(value);
    }
}
