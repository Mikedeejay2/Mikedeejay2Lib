package com.mikedeejay2.mikedeejay2lib.particle;

import org.bukkit.Particle;

/**
 * Simple container for containing all possible information for a particle.
 *
 * @author Mikedeejay2
 */
public class ParticleData
{
    // The particle enum
    protected Particle particle;
    // The count of particles to spawn
    protected int count;
    // The X offset of the particles
    protected double offsetX;
    // The Y offset of the particles
    protected double offsetY;
    // The Z offset of the particles
    protected double offsetZ;
    // The speed of the particles
    protected double speed;
    // The data of the particles (if any)
    protected Object data;
    // Whether or not to force the particles to render or not
    protected boolean force;


    /**
     * @param particle The particle enum
     * @param count    The count of particles to spawn
     * @param offsetX  The X offset of the particles
     * @param offsetY  The Y offset of the particles
     * @param offsetZ  The Z offset of the particles
     * @param speed    The speed of the particles
     * @param data     The data of the particles (if any)
     * @param force    Whether or not to force the particles to render or not
     */
    public ParticleData(Particle particle, int count, double offsetX, double offsetY, double offsetZ, double speed, Object data, boolean force)
    {
        this.particle = particle;
        this.count = count;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        this.offsetZ = offsetZ;
        this.speed = speed;
        this.data = data;
        this.force = force;
    }

    /**
     * @param particle The particle enum
     * @param count    The count of particles to spawn
     * @param offsetX  The X offset of the particles
     * @param offsetY  The Y offset of the particles
     * @param offsetZ  The Z offset of the particles
     * @param speed    The speed of the particles
     * @param force    Whether or not to force the particles to render or not
     */
    public ParticleData(Particle particle, int count, double offsetX, double offsetY, double offsetZ, double speed, boolean force)
    {
        this(particle, count, offsetX, offsetY, offsetZ, speed, null, force);
    }

    /**
     * @param particle The particle enum
     * @param count    The count of particles to spawn
     * @param offsetX  The X offset of the particles
     * @param offsetY  The Y offset of the particles
     * @param offsetZ  The Z offset of the particles
     * @param speed    The speed of the particles
     * @param data     The data of the particles (if any)
     */
    public ParticleData(Particle particle, int count, double offsetX, double offsetY, double offsetZ, double speed, Object data)
    {
        this(particle, count, offsetX, offsetY, offsetZ, speed, data, false);
    }

    /**
     * @param particle The particle enum
     * @param count    The count of particles to spawn
     * @param offsetX  The X offset of the particles
     * @param offsetY  The Y offset of the particles
     * @param offsetZ  The Z offset of the particles
     * @param speed    The speed of the particles
     */
    public ParticleData(Particle particle, int count, double offsetX, double offsetY, double offsetZ, double speed)
    {
        this(particle, count, offsetX, offsetY, offsetZ, speed, null, false);
    }

    /**
     * @param particle The particle enum
     * @param count    The count of particles to spawn
     * @param offsetX  The X offset of the particles
     * @param offsetY  The Y offset of the particles
     * @param offsetZ  The Z offset of the particles
     * @param force    Whether or not to force the particles to render or not
     */
    public ParticleData(Particle particle, int count, double offsetX, double offsetY, double offsetZ, boolean force)
    {
        this(particle, count, offsetX, offsetY, offsetZ, 0, null, force);
    }

    /**
     * @param particle The particle enum
     * @param count    The count of particles to spawn
     * @param speed    The speed of the particles
     * @param data     The data of the particles (if any)
     * @param force    Whether or not to force the particles to render or not
     */
    public ParticleData(Particle particle, int count, double speed, Object data, boolean force)
    {
        this(particle, count, 0, 0, 0, speed, data, force);
    }

    /**
     * @param particle The particle enum
     * @param count    The count of particles to spawn
     * @param speed    The speed of the particles
     * @param force    Whether or not to force the particles to render or not
     */
    public ParticleData(Particle particle, int count, double speed, boolean force)
    {
        this(particle, count, 0, 0, 0, speed, null, force);
    }

    /**
     * @param particle The particle enum
     * @param count    The count of particles to spawn
     * @param speed    The speed of the particles
     */
    public ParticleData(Particle particle, int count, double speed)
    {
        this(particle, count, 0, 0, 0, speed, null, false);
    }

    /**
     * @param particle The particle enum
     * @param count    The count of particles to spawn
     * @param force    Whether or not to force the particles to render or not
     */
    public ParticleData(Particle particle, int count, boolean force)
    {
        this(particle, count, 0, 0, 0, 0, null, force);
    }

    /**
     * @param particle The particle enum
     * @param count    The count of particles to spawn
     */
    public ParticleData(Particle particle, int count)
    {
        this(particle, count, 0, 0, 0, 0, null, false);
    }

    /**
     * @param particle The particle enum
     */
    public ParticleData(Particle particle)
    {
        this(particle, 1, 0, 0, 0, 0, null, false);
    }

    /**
     * Get the <tt>Particle</tt> (enum)
     *
     * @return The particle
     */
    public Particle getParticle()
    {
        return particle;
    }

    /**
     * Set the particle
     *
     * @param particle The new particle
     */
    public void setParticle(Particle particle)
    {
        this.particle = particle;
    }

    /**
     * Get the count (Amount of particles that are displayed)
     *
     * @return The count
     */
    public int getCount()
    {
        return count;
    }

    /**
     * Set the count (Amount of particles that are displayed)
     *
     * @param count The new count
     */
    public void setCount(int count)
    {
        this.count = count;
    }

    /**
     * Get the X offset
     *
     * @return The X offset
     */
    public double getOffsetX()
    {
        return offsetX;
    }

    /**
     * Set the X offset
     *
     * @param offsetX The new X offset
     */
    public void setOffsetX(double offsetX)
    {
        this.offsetX = offsetX;
    }

    /**
     * Get the Y offset
     *
     * @return The Y offset
     */
    public double getOffsetY()
    {
        return offsetY;
    }

    /**
     * Set the Y offset
     *
     * @param offsetY The new Y offset
     */
    public void setOffsetY(double offsetY)
    {
        this.offsetY = offsetY;
    }

    /**
     * Get the Z offset
     *
     * @return The Z offset
     */
    public double getOffsetZ()
    {
        return offsetZ;
    }

    /**
     * Set the Z offset
     *
     * @param offsetZ The new Z offset
     */
    public void setOffsetZ(double offsetZ)
    {
        this.offsetZ = offsetZ;
    }

    /**
     * Get the speed (The speed at which the particles travel)
     *
     * @return The speed
     */
    public double getSpeed()
    {
        return speed;
    }

    /**
     * Set the speed (The speed at which the particles travel)
     *
     * @param speed The new speed
     */
    public void setSpeed(double speed)
    {
        this.speed = speed;
    }

    /**
     * Get the particle data
     *
     * @return The particle data
     */
    public Object getData()
    {
        return data;
    }

    /**
     * Set the particle data
     *
     * @param data The new particle data
     */
    public void setData(Object data)
    {
        this.data = data;
    }

    /**
     * Get whether the particle is force rendered or not
     *
     * @return The force
     */
    public boolean isForce()
    {
        return force;
    }

    /**
     * Set whether the particle is force rendered or not
     *
     * @param force The new force
     */
    public void setForce(boolean force)
    {
        this.force = force;
    }
}
