package com.mikedeejay2.mikedeejay2lib.particle.module.system.transform;

import com.mikedeejay2.mikedeejay2lib.particle.ParticleSystem;
import com.mikedeejay2.mikedeejay2lib.particle.module.system.ParticleSModule;
import org.bukkit.util.Vector;

/**
 * Particle system module for subtracting rotation.
 *
 * @author Mikedeejay2
 */
public class ParticleSSubRotation extends ParticleSModule
{
    protected Vector vector;

    public ParticleSSubRotation(Vector vector)
    {
        this.vector = vector;
    }

    @Override
    public void onUpdateHead(ParticleSystem effect)
    {
        Vector curVec = effect.getRotationVec();
        curVec.subtract(vector);
        effect.setRotationVec(curVec);
    }
}
