package com.mikedeejay2.mikedeejay2lib.text.bossbar.modules.progress;

import com.mikedeejay2.mikedeejay2lib.text.bossbar.BossBarSystem;
import com.mikedeejay2.mikedeejay2lib.text.bossbar.modules.BBModule;

public class BBSubProgressModule extends BBModule
{
    protected double progress;

    public BBSubProgressModule(double progress)
    {
        this.progress = progress;
    }

    @Override
    public void onTick(BossBarSystem system)
    {
        double newProgress = Math.abs((system.getProgress() + progress) % 1);
        system.setProgress(newProgress);
    }
}
