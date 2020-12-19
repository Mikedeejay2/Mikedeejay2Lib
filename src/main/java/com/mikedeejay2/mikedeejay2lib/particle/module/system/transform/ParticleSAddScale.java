package com.mikedeejay2.mikedeejay2lib.particle.module.system.transform;

import com.mikedeejay2.mikedeejay2lib.particle.ParticleSystem;
import com.mikedeejay2.mikedeejay2lib.particle.module.system.ParticleSModule;
import org.bukkit.util.Vector;

/**
 * Particle system module for adding scale.
 *
 * @author Mikedeejay2
 */
public class ParticleSAddScale extends ParticleSModule
{
    protected Vector vector;

    public ParticleSAddScale(Vector vector)
    {
        this.vector = vector;
    }

    @Override
    public void onUpdateHead(ParticleSystem effect)
    {
        Vector curVec = effect.getScaleVec();
        curVec.add(vector);
        effect.setScaleVec(curVec);
    }
}
