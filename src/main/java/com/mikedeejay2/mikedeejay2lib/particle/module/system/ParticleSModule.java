package com.mikedeejay2.mikedeejay2lib.particle.module.system;

import com.mikedeejay2.mikedeejay2lib.particle.ParticleSystem;

public abstract class ParticleSModule
{
    public void onDisplayHead(ParticleSystem system) {}
    public void onDisplayTail(ParticleSystem system) {}
    public void onUpdateHead(ParticleSystem system) {}
    public void onUpdateTail(ParticleSystem system) {}
}
