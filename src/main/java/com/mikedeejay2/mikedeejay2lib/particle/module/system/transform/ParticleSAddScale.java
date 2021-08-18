package com.mikedeejay2.mikedeejay2lib.particle.module.system.transform;

import com.mikedeejay2.mikedeejay2lib.particle.ParticleSystem;
import com.mikedeejay2.mikedeejay2lib.particle.module.system.ParticleSModule;
import org.bukkit.util.Vector;

/**
 * Particle system module for adding scale.
 *
 * @author Mikedeejay2
 */
public class ParticleSAddScale implements ParticleSModule
{
    /**
     * The rotation <code>Vector</code>
     */
    protected Vector vector;

    /**
     * Construct a new <code>ParticleSAddScale</code>
     *
     * @param vector The rotation <code>Vector</code>
     */
    public ParticleSAddScale(Vector vector)
    {
        this.vector = vector;
    }

    /**
     * {@inheritDoc}
     *
     * @param effect The <code>ParticleSystem</code>
     */
    @Override
    public void onUpdateHead(ParticleSystem effect)
    {
        Vector curVec = effect.getScaleVec();
        curVec.add(vector);
        effect.setScaleVec(curVec);
    }
}
