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
    protected Vector vector;

    public ParticleEAddRotation(Vector vector)
    {
        this.vector = vector;
    }

    @Override
    public void onUpdateHead(ParticleEffect effect)
    {
        Vector curVec = effect.getRotationVec();
        curVec.add(vector);
        effect.setRotationVec(curVec);
    }
}
