package com.mikedeejay2.mikedeejay2lib.particle.module.effect;

import com.mikedeejay2.mikedeejay2lib.particle.ParticleEffect;

/**
 * Module base for particle effects
 *
 * @see ParticleEffect
 *
 * @author Mikedeejay2
 */
public interface ParticleEModule {
    /**
     * Called on the head of display of particles in {@link ParticleEffect#display()}
     *
     * @param effect The <code>ParticleEffect</code>
     */
    default void onDisplayHead(ParticleEffect effect) {}

    /**
     * Called on the tail of display of particles in {@link ParticleEffect#display()}
     *
     * @param effect The <code>ParticleEffect</code>
     */
    default void onDisplayTail(ParticleEffect effect) {}

    /**
     * Called on the head of baking in {@link ParticleEffect#bake()}
     *
     * @param effect The <code>ParticleEffect</code>
     */
    default void onBakeHead(ParticleEffect effect) {}

    /**
     * Called on the tail of baking in {@link ParticleEffect#bake()}
     *
     * @param effect The <code>ParticleEffect</code>
     */
    default void onBakeTail(ParticleEffect effect) {}

    /**
     * Called on the head of update in {@link ParticleEffect#update()}
     *
     * @param effect The <code>ParticleEffect</code>
     */
    default void onUpdateHead(ParticleEffect effect) {}

    /**
     * Called on the tail of update in {@link ParticleEffect#update()}
     *
     * @param effect The <code>ParticleEffect</code>
     */
    default void onUpdateTail(ParticleEffect effect) {}
}
