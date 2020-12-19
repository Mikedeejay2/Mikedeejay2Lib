package com.mikedeejay2.mikedeejay2lib.particle.module.effect.transform;

import com.mikedeejay2.mikedeejay2lib.particle.ParticleEffect;
import com.mikedeejay2.mikedeejay2lib.particle.module.effect.ParticleEModule;
import org.bukkit.util.Vector;

/**
 * Particle effect module for subtracting rotation.
 *
 * @author Mikedeejay2
 */
public class ParticleESubRotation extends ParticleEModule
{
    protected Vector vector;

    public ParticleESubRotation(Vector vector)
    {
        this.vector = vector;
    }

    @Override
    public void onUpdateHead(ParticleEffect effect)
    {
        Vector curVec = effect.getRotationVec();
        curVec.subtract(vector);
        effect.setRotationVec(curVec);
    }
}
