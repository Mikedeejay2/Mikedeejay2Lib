package com.mikedeejay2.mikedeejay2lib.particle.module.effect.transform;

import com.mikedeejay2.mikedeejay2lib.particle.ParticleEffect;
import com.mikedeejay2.mikedeejay2lib.particle.module.effect.ParticleEModule;
import org.bukkit.util.Vector;

/**
 * Particle effect module for adding translation.
 *
 * @author Mikedeejay2
 */
public class ParticleEAddTranslation implements ParticleEModule
{
    protected Vector vector;

    public ParticleEAddTranslation(Vector vector)
    {
        this.vector = vector;
    }

    @Override
    public void onUpdateHead(ParticleEffect effect)
    {
        Vector curVec = effect.getTranslationVec();
        curVec.add(vector);
        effect.setTranslationVec(curVec);
    }
}
