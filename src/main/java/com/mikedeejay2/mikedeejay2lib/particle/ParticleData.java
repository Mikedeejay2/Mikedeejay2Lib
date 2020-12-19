package com.mikedeejay2.mikedeejay2lib.particle;

import org.bukkit.Particle;

public class ParticleData
{
    protected Particle particle;
    protected int count;
    protected double offsetX;
    protected double offsetY;
    protected double offsetZ;
    protected double speed;
    protected Object data;
    protected boolean force;


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

    public ParticleData(Particle particle, int count, double offsetX, double offsetY, double offsetZ, double speed, boolean force)
    {
        this(particle, count, offsetX, offsetY, offsetZ, speed, null, force);
    }

    public ParticleData(Particle particle, int count, double offsetX, double offsetY, double offsetZ, double speed, Object data)
    {
        this(particle, count, offsetX, offsetY, offsetZ, speed, data, false);
    }

    public ParticleData(Particle particle, int count, double offsetX, double offsetY, double offsetZ, double speed)
    {
        this(particle, count, offsetX, offsetY, offsetZ, speed, null, false);
    }

    public ParticleData(Particle particle, int count, double offsetX, double offsetY, double offsetZ, boolean force)
    {
        this(particle, count, offsetX, offsetY, offsetZ, 0, null, force);
    }

    public ParticleData(Particle particle, int count, double speed, Object data, boolean force)
    {
        this(particle, count, 0, 0, 0, speed, data, force);
    }

    public ParticleData(Particle particle, int count, double speed, boolean force)
    {
        this(particle, count, 0, 0, 0, speed, null, force);
    }

    public ParticleData(Particle particle, int count, double speed)
    {
        this(particle, count, 0, 0, 0, speed, null, false);
    }

    public ParticleData(Particle particle, int count, boolean force)
    {
        this(particle, count, 0, 0, 0, 0, null, force);
    }

    public ParticleData(Particle particle, int count)
    {
        this(particle, count, 0, 0, 0, 0, null, false);
    }

    public ParticleData(Particle particle)
    {
        this(particle, 1, 0, 0, 0, 0, null, false);
    }

    public Particle getParticle()
    {
        return particle;
    }

    public void setParticle(Particle particle)
    {
        this.particle = particle;
    }

    public int getCount()
    {
        return count;
    }

    public void setCount(int count)
    {
        this.count = count;
    }

    public double getOffsetX()
    {
        return offsetX;
    }

    public void setOffsetX(double offsetX)
    {
        this.offsetX = offsetX;
    }

    public double getOffsetY()
    {
        return offsetY;
    }

    public void setOffsetY(double offsetY)
    {
        this.offsetY = offsetY;
    }

    public double getOffsetZ()
    {
        return offsetZ;
    }

    public void setOffsetZ(double offsetZ)
    {
        this.offsetZ = offsetZ;
    }

    public double getSpeed()
    {
        return speed;
    }

    public void setSpeed(double speed)
    {
        this.speed = speed;
    }

    public Object getData()
    {
        return data;
    }

    public void setData(Object data)
    {
        this.data = data;
    }

    public boolean isForce()
    {
        return force;
    }

    public void setForce(boolean force)
    {
        this.force = force;
    }
}
