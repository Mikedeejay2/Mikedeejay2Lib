package com.mikedeejay2.mikedeejay2lib.text.bossbar.modules.frames;

import com.mikedeejay2.mikedeejay2lib.text.bossbar.BossBarSystem;
import org.bukkit.boss.BarColor;

public class BBSetProgressModule extends BBFrameModule<Double>
{
    public BBSetProgressModule(boolean loop)
    {
        super(loop);
    }

    @Override
    public void onFrame(BossBarSystem system, Double value)
    {
        system.setProgress(value);
    }
}
