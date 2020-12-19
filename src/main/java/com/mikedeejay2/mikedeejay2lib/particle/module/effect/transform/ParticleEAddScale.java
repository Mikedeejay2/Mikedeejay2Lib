package com.mikedeejay2.mikedeejay2lib.particle.module.effect.transform;

import com.mikedeejay2.mikedeejay2lib.particle.ParticleEffect;
import com.mikedeejay2.mikedeejay2lib.particle.module.effect.ParticleEModule;
import org.bukkit.util.Vector;

public class ParticleEAddScale extends ParticleEModule
{
    protected Vector vector;

    public ParticleEAddScale(Vector vector)
    {
        this.vector = vector;
    }

    @Override
    public void onUpdateHead(ParticleEffect effect)
    {
        Vector curVec = effect.getScaleVec();
        curVec.add(vector);
        effect.setScaleVec(curVec);
    }
}
