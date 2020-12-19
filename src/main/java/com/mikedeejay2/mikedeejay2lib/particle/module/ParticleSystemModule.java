package com.mikedeejay2.mikedeejay2lib.particle.module;

import com.mikedeejay2.mikedeejay2lib.particle.ParticleSystem;

public abstract class ParticleSystemModule
{
    public void onDisplayHead(ParticleSystem system) {}
    public void onDisplayTail(ParticleSystem system) {}
    public void onBakeHead(ParticleSystem system) {}
    public void onBakeTail(ParticleSystem system) {}
    public void onUpdateHead(ParticleSystem system) {}
    public void onUpdateTail(ParticleSystem system) {}
}
