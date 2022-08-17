package com.mikedeejay2.mikedeejay2lib.particle.module.system.transform;

import com.mikedeejay2.mikedeejay2lib.particle.ParticleSystem;
import com.mikedeejay2.mikedeejay2lib.particle.module.system.ParticleSModule;
import org.bukkit.util.Vector;

/**
 * Particle system module for adding rotation.
 *
 * @author Mikedeejay2
 */
public class ParticleSAddRotation implements ParticleSModule {
    /**
     * The rotation <code>Vector</code>
     */
    protected Vector vector;

    /**
     * Construct a new <code>ParticleSAddRotation</code>
     *
     * @param vector The rotation <code>Vector</code>
     */
    public ParticleSAddRotation(Vector vector) {
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
        curVec.add(vector);
        effect.setRotationVec(curVec);
    }
}
