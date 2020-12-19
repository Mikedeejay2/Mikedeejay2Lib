package com.mikedeejay2.mikedeejay2lib.particle.module.system.transform;

import com.mikedeejay2.mikedeejay2lib.particle.ParticleEffect;
import com.mikedeejay2.mikedeejay2lib.particle.ParticleSystem;
import com.mikedeejay2.mikedeejay2lib.particle.module.effect.ParticleEModule;
import com.mikedeejay2.mikedeejay2lib.particle.module.system.ParticleSModule;
import org.bukkit.util.Vector;

public class ParticleSSubScale extends ParticleSModule
{
    protected Vector vector;

    public ParticleSSubScale(Vector vector)
    {
        this.vector = vector;
    }

    @Override
    public void onUpdateHead(ParticleSystem effect)
    {
        Vector curVec = effect.getScaleVec();
        curVec.subtract(vector);
        effect.setScaleVec(curVec);
    }
}
