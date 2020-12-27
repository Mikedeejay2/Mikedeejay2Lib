package com.mikedeejay2.mikedeejay2lib.particle.module.effect;

import com.mikedeejay2.mikedeejay2lib.particle.ParticleEffect;

/**
 * Module base for particle effects
 *
 * @see ParticleEffect
 *
 * @author Mikedeejay2
 */
public interface ParticleEModule
{
    /**
     * Called on the head of display of particles in {@link ParticleEffect#display()}
     *
     * @param effect The <tt>ParticleEffect</tt>
     */
    default void onDisplayHead(ParticleEffect effect) {}

    /**
     * Called on the tail of display of particles in {@link ParticleEffect#display()}
     *
     * @param effect The <tt>ParticleEffect</tt>
     */
    default void onDisplayTail(ParticleEffect effect) {}

    /**
     * Called on the head of baking in {@link ParticleEffect#bake()}
     *
     * @param effect The <tt>ParticleEffect</tt>
     */
    default void onBakeHead(ParticleEffect effect) {}

    /**
     * Called on the tail of baking in {@link ParticleEffect#bake()}
     *
     * @param effect The <tt>ParticleEffect</tt>
     */
    default void onBakeTail(ParticleEffect effect) {}

    /**
     * Called on the head of update in {@link ParticleEffect#update()}
     *
     * @param effect The <tt>ParticleEffect</tt>
     */
    default void onUpdateHead(ParticleEffect effect) {}

    /**
     * Called on the tail of update in {@link ParticleEffect#update()}
     *
     * @param effect The <tt>ParticleEffect</tt>
     */
    default void onUpdateTail(ParticleEffect effect) {}
}
