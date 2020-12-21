package com.mikedeejay2.mikedeejay2lib.particle.module.effect;

import com.mikedeejay2.mikedeejay2lib.particle.ParticleEffect;

/**
 * Module base for particle effects
 *
 * @author Mikedeejay2
 * @see ParticleEffect
 */
public abstract class ParticleEModule
{
    /**
     * Called on the head of display of particles in {@link ParticleEffect#display()}
     *
     * @param effect The <tt>ParticleEffect</tt>
     */
    public void onDisplayHead(ParticleEffect effect) {}

    /**
     * Called on the tail of display of particles in {@link ParticleEffect#display()}
     *
     * @param effect The <tt>ParticleEffect</tt>
     */
    public void onDisplayTail(ParticleEffect effect) {}

    /**
     * Called on the head of baking in {@link ParticleEffect#bake()}
     *
     * @param effect The <tt>ParticleEffect</tt>
     */
    public void onBakeHead(ParticleEffect effect) {}

    /**
     * Called on the tail of baking in {@link ParticleEffect#bake()}
     *
     * @param effect The <tt>ParticleEffect</tt>
     */
    public void onBakeTail(ParticleEffect effect) {}

    /**
     * Called on the head of update in {@link ParticleEffect#update()}
     *
     * @param effect The <tt>ParticleEffect</tt>
     */
    public void onUpdateHead(ParticleEffect effect) {}

    /**
     * Called on the tail of update in {@link ParticleEffect#update()}
     *
     * @param effect The <tt>ParticleEffect</tt>
     */
    public void onUpdateTail(ParticleEffect effect) {}
}
