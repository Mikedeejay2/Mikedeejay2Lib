package com.mikedeejay2.mikedeejay2lib.particle.module.system.transform;

import com.mikedeejay2.mikedeejay2lib.particle.ParticleSystem;
import com.mikedeejay2.mikedeejay2lib.particle.module.system.ParticleSModule;
import org.bukkit.util.Vector;

/**
 * Particle system module for subtracting translation.
 *
 * @author Mikedeejay2
 */
public class ParticleSSubTranslation implements ParticleSModule
{
    /**
     * The rotation <code>Vector</code>
     */
    protected Vector vector;

    /**
     * Construct a new <code>ParticleSSubTranslation</code>
     *
     * @param vector The rotation <code>Vector</code>
     */
    public ParticleSSubTranslation(Vector vector)
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
        Vector curVec = effect.getTranslationVec();
        curVec.subtract(vector);
        effect.setTranslationVec(curVec);
    }
}
