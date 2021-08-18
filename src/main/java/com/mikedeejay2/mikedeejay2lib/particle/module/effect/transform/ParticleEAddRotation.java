package com.mikedeejay2.mikedeejay2lib.particle.module.effect.transform;

import com.mikedeejay2.mikedeejay2lib.particle.ParticleEffect;
import com.mikedeejay2.mikedeejay2lib.particle.module.effect.ParticleEModule;
import org.bukkit.util.Vector;

/**
 * Particle effect module for adding rotation.
 *
 * @author Mikedeejay2
 */
public class ParticleEAddRotation implements ParticleEModule
{
    /**
     * The rotation <code>Vector</code>
     */
    protected Vector vector;

    /**
     * Construct a new <code>ParticleEAddRotation</code>
     *
     * @param vector The rotation <code>Vector</code>
     */
    public ParticleEAddRotation(Vector vector)
    {
        this.vector = vector;
    }

    /**
     * {@inheritDoc}
     *
     * @param effect The <code>ParticleEffect</code>
     */
    @Override
    public void onUpdateHead(ParticleEffect effect)
    {
        Vector curVec = effect.getRotationVec();
        curVec.add(vector);
        effect.setRotationVec(curVec);
    }
}
