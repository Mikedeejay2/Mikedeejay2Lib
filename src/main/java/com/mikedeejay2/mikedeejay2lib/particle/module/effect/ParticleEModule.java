package com.mikedeejay2.mikedeejay2lib.particle.module.effect;

import com.mikedeejay2.mikedeejay2lib.particle.ParticleEffect;

public abstract class ParticleEModule
{
    public void onDisplayHead(ParticleEffect effect) {}
    public void onDisplayTail(ParticleEffect effect) {}
    public void onBakeHead(ParticleEffect effect) {}
    public void onBakeTail(ParticleEffect effect) {}
    public void onUpdateHead(ParticleEffect effect) {}
    public void onUpdateTail(ParticleEffect effect) {}
}
