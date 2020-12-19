package com.mikedeejay2.mikedeejay2lib.particle.runtime;

import com.mikedeejay2.mikedeejay2lib.particle.ParticleEffect;
import com.mikedeejay2.mikedeejay2lib.particle.ParticleSystem;
import com.mikedeejay2.mikedeejay2lib.runnable.EnhancedRunnable;
import com.mikedeejay2.mikedeejay2lib.util.debug.DebugTimer;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

public class ParticleRuntime extends EnhancedRunnable
{
    protected ParticleSystem system;
    protected List<ParticleEffect> effects;
    protected long updateRate;
    protected long curUpdateRate;

    public ParticleRuntime(ParticleSystem system, long updateRate)
    {
        super();
        this.system = system;
        this.effects = system.getEffects();
        this.updateRate = updateRate;
        this.curUpdateRate = 0;
    }

    @Override
    public void onRun()
    {
        curUpdateRate += period;
        boolean shouldUpdate = curUpdateRate >= updateRate;

        for(ParticleEffect effect : effects)
        {
            if(!effect.isBaked())
            {
                effect.bake();
            }
            if(shouldUpdate && !effect.isUpdated())
            {
                effect.update();
            }
        }
        if(shouldUpdate) curUpdateRate = 0;

        Bukkit.getScheduler().runTask(plugin, () -> {
            for(ParticleEffect effect : effects)
            {
                long effectCount = effect.getCount();
                long effectDelay = effect.getDelay();
                if(effectCount > 0 && count >= (effectCount + effectDelay)) continue;
                if(effectDelay > 0 && count >= delay) continue;
                effect.display();
            }
        });
    }
}
