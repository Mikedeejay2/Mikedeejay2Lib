package com.mikedeejay2.mikedeejay2lib.particle.module.effect.transform;

import com.mikedeejay2.mikedeejay2lib.particle.ParticleEffect;
import com.mikedeejay2.mikedeejay2lib.particle.module.effect.ParticleEModule;
import org.bukkit.util.Vector;

/**
 * Particle effect module for subtracting scale.
 *
 * @author Mikedeejay2
 */
public class ParticleESubScale implements ParticleEModule
{
    protected Vector vector;

    public ParticleESubScale(Vector vector)
    {
        this.vector = vector;
    }

    @Override
    public void onUpdateHead(ParticleEffect effect)
    {
        Vector curVec = effect.getScaleVec();
        curVec.subtract(vector);
        effect.setScaleVec(curVec);
    }
}
