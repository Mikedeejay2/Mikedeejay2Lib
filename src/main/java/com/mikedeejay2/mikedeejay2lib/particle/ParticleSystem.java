package com.mikedeejay2.mikedeejay2lib.particle;

import com.mikedeejay2.mikedeejay2lib.BukkitPlugin;
import com.mikedeejay2.mikedeejay2lib.particle.module.system.ParticleSModule;
import com.mikedeejay2.mikedeejay2lib.particle.runtime.ParticleRuntime;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

/**
 * The main containing class for a system of particles.
 * <p>
 * Particle effects can be added with {@link ParticleSystem#addParticleEffect(ParticleEffect)}
 * <p>
 * This system serves as an easy way to initialize, animate, and efficiently work with
 * particles.
 *
 * @see ParticleEffect
 *
 * @author Mikedeejay2
 */
public class ParticleSystem
{
    /**
     * The {@link BukkitPlugin} instance
     */
    protected final BukkitPlugin plugin;
    // The list of particle effects
    protected List<ParticleEffect> effects;
    // The global scale vector for this system
    protected Vector scaleVec;
    // The global rotation vector for this system
    protected Vector rotationVec;
    // The global translation vector for this system
    protected Vector translationVec;
    // The origin location of this system (For global transformations)
    protected Location origin;
    // The world that this system is located in (From origin location)
    protected World world;
    // The amount of ticks that the particle will play for
    protected long playTicks;
    // The tick rate (refresh rate) of the particle system
    protected long tickRate;
    // The update rate (rate at which transformations are updated) of the particle system
    protected long updateRate;
    // This particle system's particle runtime for displaying the particles
    protected ParticleRuntime runtime;
    // The list of global particle system modules
    protected List<ParticleSModule> modules;
    // Whether the transformations are up to date on the display
    protected boolean updated;

    public ParticleSystem(BukkitPlugin plugin, Location origin, long playTicks, long tickRate, long updateRate)
    {
        this.plugin = plugin;
        this.effects = new ArrayList<>();
        this.scaleVec = new Vector(1, 1, 1);
        this.rotationVec = new Vector(0, 0, 0);
        this.translationVec = new Vector(0, 0, 0);
        this.origin = origin;
        this.playTicks = playTicks;
        this.tickRate = tickRate;
        this.updateRate = updateRate;
        this.modules = new ArrayList<>();
        this.updated = true;
    }

    public ParticleSystem(BukkitPlugin plugin, Location origin, long playTicks, long tickRate)
    {
        this(plugin, origin, playTicks, tickRate, 0);
    }

    public ParticleSystem(BukkitPlugin plugin, Location origin, long playTicks)
    {
        this(plugin, origin, playTicks, 0, 0);
    }

    /**
     * Display these particles with the current runtime settings.
     * All effects, transformations, shapes, etc can still be modified at run-time however.
     *
     * @return A reference to this <code>ParticleSystem</code>
     */
    public ParticleSystem display()
    {
        this.runtime = new ParticleRuntime(this, updateRate);
        this.runtime.runTaskTimerCountedAsynchronously(plugin, tickRate, playTicks);
        return this;
    }

    /**
     * Add a <code>ParticleSModule</code> to this <code>ParticleSystem</code>
     *
     * @see ParticleSModule
     *
     * @param module The module to add
     * @return A reference to this <code>ParticleSystem</code>
     */
    public ParticleSystem addModule(ParticleSModule module)
    {
        modules.add(module);
        return this;
    }

    /**
     * Add a <code>ParticleSModule</code> to this <code>ParticleSystem</code> at a specified index
     *
     * @param module The module to add
     * @param index The index to add the module at
     * @return A reference to this <code>ParticleSystem</code>
     */
    public ParticleSystem addModule(ParticleSModule module, int index)
    {
        modules.add(index, module);
        return this;
    }

    /**
     * See whether this <code>ParticleSystem</code> contains a specific module
     *
     * @param module The module to search for
     * @return Whether the module was found or not
     */
    public boolean containsModule(ParticleSModule module)
    {
        return modules.contains(module);
    }

    /**
     * See whether this <code>ParticleSystem</code> contains a specific module based off of the module's class
     *
     * @param moduleClass The module class to search for
     * @return Whether a module with that class was found or not
     */
    public boolean containsModule(Class<? extends ParticleSModule> moduleClass)
    {
        for(ParticleSModule module : modules)
        {
            if(moduleClass == module.getClass()) return true;
        }
        return false;
    }

    /**
     * Remove a module from this <code>ParticleSystem</code> by the module's index in the list
     *
     * @param index The index to remove from the list
     * @return A reference to this <code>ParticleSystem</code>
     */
    public ParticleSystem removeModule(int index)
    {
        modules.remove(index);
        return this;
    }

    /**
     * Remove a module from this <code>ParticleSystem</code> by a reference to the module
     *
     * @param module The module to remove
     * @return A reference to this <code>ParticleSystem</code>
     */
    public ParticleSystem removeModule(ParticleSModule module)
    {
        modules.remove(module);
        return this;
    }

    /**
     * Remove a module from this <code>ParticleSystem</code> by the module's class
     *
     * @param moduleClass The class of the module to remove
     * @return A reference to this <code>ParticleSystem</code>
     */
    public ParticleSystem removeModule(Class<? extends ParticleSModule> moduleClass)
    {
        for(ParticleSModule module : modules)
        {
            if(moduleClass != module.getClass()) continue;
            modules.remove(module);
            return this;
        }
        return this;
    }

    /**
     * Get a module from this <code>ParticleSystem</code> by the index of the module
     *
     * @param index The module's index in the list
     * @return The requested <code>ParticleSModule</code>
     */
    public ParticleSModule getModule(int index)
    {
        return modules.get(index);
    }

    /**
     * Get a module from this <code>ParticleSystem</code> based off of the class of the module
     *
     * @param moduleClass The class of the module to get
     * @param <T>         The module type
     * @return The requested <code>ParticleSModule</code>, null if not found
     */
    public <T extends ParticleSModule> T getModule(Class<T> moduleClass)
    {
        for(ParticleSModule module : modules)
        {
            if(moduleClass == module.getClass()) return (T) module;
        }
        return null;
    }

    /**
     * Reset this system's module list to be empty
     *
     * @return A reference to this <code>ParticleSystem</code>
     */
    public ParticleSystem resetModules()
    {
        modules.clear();
        return this;
    }

    /**
     * Add a <code>ParticleEffect</code> to this system
     *
     * @param effect The effect to add
     * @return A reference to this <code>ParticleSystem</code>
     */
    public ParticleSystem addParticleEffect(ParticleEffect effect)
    {
        effects.add(effect);
        return this;
    }

    /**
     * Add a <code>ParticleEffect</code> to this system at a specific index
     *
     * @param effect The effect to add
     * @param index  The index to add the effect to
     * @return A reference to this <code>ParticleSystem</code>
     */
    public ParticleSystem addParticleEffect(ParticleEffect effect, int index)
    {
        effects.add(index, effect);
        return this;
    }

    /**
     * See whether this <code>ParticleSystem</code> contains a <code>ParticleEffect</code>
     *
     * @param effect The effect to search for
     * @return Whether the <code>ParticleEffect</code> was found or not
     */
    public boolean containsParticleEffect(ParticleEffect effect)
    {
        return effects.contains(effect);
    }

    /**
     * See whether this <code>ParticleSystem</code> contains a <code>ParticleEffect</code> by the effect's class
     *
     * @param effectClass The class of the effect to search for
     * @return Whether a <code>ParticleEffect</code> of the class was found
     */
    public boolean containsParticleEffect(Class<? extends ParticleEffect> effectClass)
    {
        for(ParticleEffect effect : effects)
        {
            if(effectClass == effect.getClass()) return true;
        }
        return false;
    }

    /**
     * Remove a <code>ParticleEffect</code> from this system by the index of the effect
     *
     * @param index The index of the effect to remove
     * @return A reference to this <code>ParticleSystem</code>
     */
    public ParticleSystem removeParticleEffect(int index)
    {
        effects.remove(index);
        return this;
    }

    /**
     * Remove a <code>ParticleEffect</code> from this system by a reference
     *
     * @param effect The effect to remove
     * @return A reference to this <code>ParticleSystem</code>
     */
    public ParticleSystem removeParticleEffect(ParticleEffect effect)
    {
        effects.remove(effect);
        return this;
    }

    /**
     * Remove a <code>ParticleEffect</code> from this system by its class type
     *
     * @param effectClass The class of the effect to remove
     * @return A reference to this <code>ParticleSystem</code>
     */
    public ParticleSystem removeParticleEffect(Class<? extends ParticleEffect> effectClass)
    {
        for(ParticleEffect effect : effects)
        {
            if(effectClass != effect.getClass()) continue;
            effects.remove(effect);
            return this;
        }
        return this;
    }

    /**
     * Get a <code>ParticleEffect</code> from this system by its index
     *
     * @param index The index to get the effect at
     * @return The requested <code>ParticleEffect</code>
     */
    public ParticleEffect getParticleEffect(int index)
    {
        return effects.get(index);
    }

    /**
     * Get a <code>ParticleEffect</code> from this system by the effect's class
     *
     * @param effectClass The class of the effect to get
     * @param <T>         The particle effect type
     * @return The requested <code>ParticleEffect</code>, null if not found
     */
    public <T extends ParticleEffect> T getParticleEffect(Class<T> effectClass)
    {
        for(ParticleEffect effect : effects)
        {
            if(effectClass == effect.getClass()) return (T) effect;
        }
        return null;
    }

    /**
     * Reset (clear) the list of <code>ParticleEffects</code> of this <code>ParticleSystem</code>
     *
     * @return A reference to this <code>ParticleSystem</code>
     */
    public ParticleSystem resetEffects()
    {
        effects.clear();
        return this;
    }

    /**
     * Get the entire list of <code>ParticleEffects</code> of this system
     *
     * @return The list of <code>ParticleEffects</code>
     */
    public List<ParticleEffect> getEffects()
    {
        return effects;
    }

    /**
     * Get the global scaling vector of this system
     *
     * @return The global scaling vector
     */
    public Vector getScaleVec()
    {
        return scaleVec;
    }

    /**
     * Get the global rotation vector of this system
     *
     * @return The global rotation vector
     */
    public Vector getRotationVec()
    {
        return rotationVec;
    }

    /**
     * Get the global translation vector of this system
     *
     * @return The global translation vector
     */
    public Vector getTranslationVec()
    {
        return translationVec;
    }

    /**
     * Get this system's global origin location
     *
     * @return The global origin location
     */
    public Location getOrigin()
    {
        return origin;
    }

    /**
     * Set the scale vector of this system.
     * <p>
     * Even if the vector has only been modified and not replaced this method still should be called
     * in order to propagate updates.
     *
     * @param scaleVec The new scale vector
     */
    public void setScaleVec(Vector scaleVec)
    {
        this.scaleVec = scaleVec;
        this.updated = false;
    }

    /**
     * Set the rotation vector of this system.
     * <p>
     * Even if the vector has only been modified and not replaced this method still should be called
     * in order to propagate updates.
     *
     * @param rotationVec The new rotation vector
     */
    public void setRotationVec(Vector rotationVec)
    {
        this.rotationVec = rotationVec;
        this.updated = false;
    }

    /**
     * Set the translation vector of this system.
     * <p>
     * Even if the vector has only been modified and not replaced this method still should be called
     * in order to propagate updates.
     *
     * @param translationVec The new translation vector
     */
    public void setTranslationVec(Vector translationVec)
    {
        this.translationVec = translationVec;
        this.updated = false;
    }

    /**
     * Set the global origin location of this system.
     * <p>
     * Even if the location has only been modified and not replaced this method still should be called
     * in order to propagate updates.
     *
     * @param origin The new origin location
     */
    public void setOrigin(Location origin)
    {
        this.origin = origin;
        this.updated = false;
    }

    /**
     * Get the amount of ticks that this <code>ParticleSystem</code> will be played
     *
     * @return Play time (in ticks)
     */
    public long getPlayTicks()
    {
        return playTicks;
    }

    /**
     * Set the play time of this <code>ParticleSystem</code>
     * <p>
     * This value will not be updated during runtime, it must be set before runtime.
     *
     * @param playTicks The new play time (in ticks)
     */
    public void setPlayTicks(long playTicks)
    {
        this.playTicks = playTicks;
    }

    /**
     * Get the tick rate of this <code>ParticleSystem</code>
     *
     * @return The tick rate (amount of ticks between displays/updates)
     */
    public long getTickRate()
    {
        return tickRate;
    }

    /**
     * Set the tick rate of this <code>ParticleSystem</code>
     * <p>
     * This value will not be updated during runtime, it must be set before runtime.
     *
     * @param tickRate The new tick rate (amount of ticks between displays/updates)
     */
    public void setTickRate(long tickRate)
    {
        this.tickRate = tickRate;
    }

    /**
     * Get the update rate of this <code>ParticleSystem</code>
     *
     * @return The update rate (amount of time between transformation updates are displayed)
     */
    public long getUpdateRate()
    {
        return updateRate;
    }

    /**
     * Set the update rate of this <code>ParticleSystem</code>
     * <p>
     * This value will not be updated during runtime, it must be set before runtime.
     *
     * @param updateRate The new update rate (amount of time between transformation updates are displayed)
     */
    public void setUpdateRate(long updateRate)
    {
        this.updateRate = updateRate;
    }

    /**
     * Get the list of <code>ParticleSModules</code> of this <code>ParticleSystem</code>
     *
     * @return The list of <code>ParticleSModules</code>
     */
    public List<ParticleSModule> getModules()
    {
        return modules;
    }

    /**
     * Boolean of whether this particle system is currently updated and displaying the latest information
     *
     * @return Whether this system is updated
     */
    public boolean isUpdated()
    {
        return updated;
    }

    /**
     * Set whether this system is updated to the latest information.
     * <p>
     * Only for internal use. This WILL NOT force update the system.
     *
     * @param updated The new updated state
     */
    public void setUpdated(boolean updated)
    {
        this.updated = updated;
    }
}
