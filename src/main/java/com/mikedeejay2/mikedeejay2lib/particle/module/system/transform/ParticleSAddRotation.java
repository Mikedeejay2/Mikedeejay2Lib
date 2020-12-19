package com.mikedeejay2.mikedeejay2lib.particle.module.system.transform;

import com.mikedeejay2.mikedeejay2lib.particle.ParticleSystem;
import com.mikedeejay2.mikedeejay2lib.particle.module.system.ParticleSModule;
import org.bukkit.util.Vector;

/**
 * Particle system module for adding rotation.
 *
 * @author Mikedeejay2
 */
public class ParticleSAddRotation extends ParticleSModule
{
    protected Vector vector;

    public ParticleSAddRotation(Vector vector)
    {
        this.vector = vector;
    }

    @Override
    public void onUpdateHead(ParticleSystem effect)
    {
        Vector curVec = effect.getRotationVec();
        curVec.add(vector);
        effect.setRotationVec(curVec);
    }
}
