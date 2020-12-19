package com.mikedeejay2.mikedeejay2lib.particle.module;

import com.mikedeejay2.mikedeejay2lib.particle.ParticleEffect;
import org.bukkit.util.Vector;

public class ParticleERotateModule extends ParticleEModule
{
    protected Vector rotationVec;

    public ParticleERotateModule(Vector rotationVec)
    {
        this.rotationVec = rotationVec;
    }

    @Override
    public void onUpdateHead(ParticleEffect effect)
    {
        Vector curRotationVec = effect.getRotationVec();
        curRotationVec.add(rotationVec);
        effect.setRotationVec(curRotationVec);
    }
}
