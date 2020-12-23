package com.mikedeejay2.mikedeejay2lib.text.bossbar;

import com.mikedeejay2.mikedeejay2lib.runnable.EnhancedRunnable;
import com.mikedeejay2.mikedeejay2lib.text.bossbar.modules.BBModule;
import org.bukkit.boss.BossBar;

import java.util.List;

public class BossBarRuntime extends EnhancedRunnable
{
    protected BossBarSystem system;
    protected BossBar bar;
    protected List<BBModule> modules;

    public BossBarRuntime(BossBarSystem bar)
    {
        this.system = bar;
        this.bar = system.getBar();
        this.modules = system.getModules();
    }

    @Override
    public void onRun()
    {
        modules.forEach(module -> module.onTick(system));
    }

    @Override
    public void onLastRun()
    {
        system.setVisible(false);
    }
}
