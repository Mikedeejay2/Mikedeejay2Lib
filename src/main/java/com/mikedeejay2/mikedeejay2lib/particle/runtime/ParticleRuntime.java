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

        system.getModules().forEach(module -> module.onUpdateHead(system));
        for(ParticleEffect effect : effects)
        {
            if(!effect.isBaked())
            {
                effect.bake();
            }
            if(shouldUpdate)
            {
                effect.update();
                effect.updateSystem(system);
            }
        }
        system.getModules().forEach(module -> module.onUpdateTail(system));
        if(shouldUpdate)
        {
            curUpdateRate = 0;
            system.setUpdated(true);
        }

        Bukkit.getScheduler().runTask(plugin, () -> {
            system.getModules().forEach(module -> module.onDisplayHead(system));
            for(ParticleEffect effect : effects)
            {
                long effectCount = effect.getCount();
                long effectDelay = effect.getDelay();
                if(effectCount > 0 && count >= (effectCount + effectDelay)) continue;
                if(effectDelay > 0 && count >= delay) continue;
                effect.display();
            }
            system.getModules().forEach(module -> module.onDisplayTail(system));
        });
    }
}
