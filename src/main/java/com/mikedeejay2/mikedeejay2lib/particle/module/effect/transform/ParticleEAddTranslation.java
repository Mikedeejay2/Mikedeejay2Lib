package com.mikedeejay2.mikedeejay2lib.particle.module.effect.transform;

import com.mikedeejay2.mikedeejay2lib.particle.ParticleEffect;
import com.mikedeejay2.mikedeejay2lib.particle.module.effect.ParticleEModule;
import org.bukkit.util.Vector;

public class ParticleEAddTranslation extends ParticleEModule
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
