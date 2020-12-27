package com.mikedeejay2.mikedeejay2lib.particle.module.system;

import com.mikedeejay2.mikedeejay2lib.particle.ParticleSystem;
import com.mikedeejay2.mikedeejay2lib.particle.runtime.ParticleRuntime;

/**
 * Module base for particle systems
 *
 * @see ParticleSystem
 *
 * @author Mikedeejay2
 */
public interface ParticleSModule
{
    /**
     * Called on the head of display of particles in {@link ParticleRuntime#onRun()}
     *
     * @param system The <tt>ParticleSystem</tt>
     */
    default void onDisplayHead(ParticleSystem system) {}

    /**
     * Called on the tail of display of particles in {@link ParticleRuntime#onRun()}
     *
     * @param system The <tt>ParticleSystem</tt>
     */
    default void onDisplayTail(ParticleSystem system) {}

    /**
     * Called on the head of update in {@link ParticleRuntime#onRun()}
     *
     * @param system The <tt>ParticleSystem</tt>
     */
    default void onUpdateHead(ParticleSystem system) {}

    /**
     * Called on the tail of update in {@link ParticleRuntime#onRun()}
     *
     * @param system The <tt>ParticleSystem</tt>
     */
    default void onUpdateTail(ParticleSystem system) {}
}
