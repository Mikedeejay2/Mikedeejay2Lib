package com.mikedeejay2.mikedeejay2lib.particle.module.system.transform;

import com.mikedeejay2.mikedeejay2lib.particle.ParticleSystem;
import com.mikedeejay2.mikedeejay2lib.particle.module.system.ParticleSModule;
import org.bukkit.util.Vector;

/**
 * Particle system module for subtracting rotation.
 *
 * @author Mikedeejay2
 */
public class ParticleSSubRotation implements ParticleSModule {
    /**
     * The rotation <code>Vector</code>
     */
    protected Vector vector;

    /**
     * Construct a new <code>ParticleSSubRotation</code>
     *
     * @param vector The rotation <code>Vector</code>
     */
    public ParticleSSubRotation(Vector vector) {
        this.vector = vector;
    }

    /**
     * {@inheritDoc}
     *
     * @param effect The <code>ParticleSystem</code>
     */
    @Override
    public void onUpdateHead(ParticleSystem effect) {
        Vector curVec = effect.getRotationVec();
        curVec.subtract(vector);
        effect.setRotationVec(curVec);
    }
}
