package com.mikedeejay2.mikedeejay2lib.particle.runtime;

import com.mikedeejay2.mikedeejay2lib.particle.ParticleEffect;
import com.mikedeejay2.mikedeejay2lib.particle.ParticleSystem;
import com.mikedeejay2.mikedeejay2lib.runnable.EnhancedRunnable;
import org.bukkit.Bukkit;

import java.util.List;

/**
 * The runtime which runs and manages displaying particles for <code>ParticleSystems</code>.
 * <p>
 * This runtime should not be declared outside a <code>ParticleSystem</code>.
 * <p>
 * To create this runtime internally {@link ParticleSystem#display()} should be called which
 * creates this runtime automatically and properly configures this runtime.
 *
 * @see ParticleSystem
 *
 * @author Mikedeejay2
 */
public class ParticleRuntime extends EnhancedRunnable {
    /**
     * The particle system that this runtime is controlling
     */
    protected ParticleSystem system;

    /**
     * The list of particle effects (from the particle system)
     */
    protected List<ParticleEffect> effects;

    /**
     * The rate at which updates occur at the runtime
     */
    protected long updateRate;

    /**
     * The current update rate (run-time temporary variable for tracking)
     */
    protected long curUpdateRate;

    /**
     * Construct a new <code>ParticleRuntime</code>
     *
     * @param system     The particle system that this runtime is controlling
     * @param updateRate The rate at which updates occur at the runtime
     */
    public ParticleRuntime(ParticleSystem system, long updateRate) {
        super();
        this.system = system;
        this.effects = system.getEffects();
        this.updateRate = updateRate;
        this.curUpdateRate = 0;
    }

    /**
     * Overridden <code>onRun()</code> method that processes all the logic for baking, updating, and
     * displaying a <code>ParticleSystem</code>. This should run asynchronously.
     */
    @Override
    public void onRun() {
        curUpdateRate += period;
        boolean shouldUpdate = curUpdateRate >= updateRate;

        system.getModules().forEach(module -> module.onUpdateHead(system));
        for(ParticleEffect effect : effects) {
            if(!effect.isBaked()) {
                effect.bake();
            }
            if(shouldUpdate) {
                effect.update();
                effect.updateSystem(system);
            }
        }
        system.getModules().forEach(module -> module.onUpdateTail(system));
        if(shouldUpdate) {
            curUpdateRate = 0;
            system.setUpdated(true);
        }

        Bukkit.getScheduler().runTask(plugin, () -> {
            system.getModules().forEach(module -> module.onDisplayHead(system));
            for(ParticleEffect effect : effects) {
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
