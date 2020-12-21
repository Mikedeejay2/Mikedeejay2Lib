package com.mikedeejay2.mikedeejay2lib.particle.runtime;

import com.mikedeejay2.mikedeejay2lib.particle.ParticleEffect;
import com.mikedeejay2.mikedeejay2lib.particle.ParticleSystem;
import com.mikedeejay2.mikedeejay2lib.runnable.EnhancedRunnable;
import org.bukkit.Bukkit;

import java.util.List;

/**
 * The runtime which runs and manages displaying particles for <tt>ParticleSystems</tt>. <p>
 * This runtime should not be declared outside of a <tt>ParticleSystem</tt>. <p>
 * To create this runtime internally {@link ParticleSystem#display()} should be called which
 * creates this runtime automatically and properly configures this runtime.
 *
 * @author Mikedeejay2
 * @see ParticleSystem
 */
public class ParticleRuntime extends EnhancedRunnable
{
    // The particle system that this runtime is controlling
    protected ParticleSystem system;
    // The list of particle effects (from the particle system)
    protected List<ParticleEffect> effects;
    // The rate at which updates occur at the runtime
    protected long updateRate;
    // The current update rate (run-time temporary variable for tracking)
    protected long curUpdateRate;

    public ParticleRuntime(ParticleSystem system, long updateRate)
    {
        super();
        this.system = system;
        this.effects = system.getEffects();
        this.updateRate = updateRate;
        this.curUpdateRate = 0;
    }

    /**
     * Overridden <tt>onRun()</tt> method that processes all of the logic for baking, updating, and
     * displaying a <tt>ParticleSystem</tt>. This should run asynchronously.
     */
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
