package com.mikedeejay2.mikedeejay2lib.particle.module.effect.transform;

import com.mikedeejay2.mikedeejay2lib.particle.ParticleEffect;
import com.mikedeejay2.mikedeejay2lib.particle.module.effect.ParticleEModule;
import org.bukkit.util.Vector;

/**
 * Particle effect module for subtracting scale.
 *
 * @author Mikedeejay2
 */
public class ParticleESubScale implements ParticleEModule {
    /**
     * The rotation <code>Vector</code>
     */
    protected Vector vector;

    /**
     * Construct a new <code>ParticleESubScale</code>
     *
     * @param vector The rotation <code>Vector</code>
     */
    public ParticleESubScale(Vector vector) {
        this.vector = vector;
    }

    /**
     * {@inheritDoc}
     *
     * @param effect The <code>ParticleEffect</code>
     */
    @Override
    public void onUpdateHead(ParticleEffect effect) {
        Vector curVec = effect.getScaleVec();
        curVec.subtract(vector);
        effect.setScaleVec(curVec);
    }
}
