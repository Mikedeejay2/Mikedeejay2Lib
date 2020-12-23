package com.mikedeejay2.mikedeejay2lib.text.bossbar.modules.frames;

import com.mikedeejay2.mikedeejay2lib.text.bossbar.BossBarSystem;
import org.bukkit.boss.BarColor;

public class BBSetColorModule extends BBFrameModule<BarColor>
{
    public BBSetColorModule(boolean loop)
    {
        super(loop);
    }

    @Override
    public void onFrame(BossBarSystem system, BarColor value)
    {
        system.setColor(value);
    }
}
