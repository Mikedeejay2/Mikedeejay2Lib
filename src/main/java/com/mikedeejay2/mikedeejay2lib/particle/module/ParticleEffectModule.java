package com.mikedeejay2.mikedeejay2lib.particle.module;

import com.mikedeejay2.mikedeejay2lib.particle.ParticleEffect;

public abstract class ParticleEffectModule
{
    public void onDisplayHead(ParticleEffect effect) {}
    public void onDisplayTail(ParticleEffect effect) {}
    public void onBakeHead(ParticleEffect effect) {}
    public void onBakeTail(ParticleEffect effect) {}
    public void onUpdateHead(ParticleEffect effect) {}
    public void onUpdateTail(ParticleEffect effect) {}
}
