package com.mikedeejay2.mikedeejay2lib.particle;

import com.mikedeejay2.mikedeejay2lib.PluginBase;
import com.mikedeejay2.mikedeejay2lib.particle.runtime.ParticleRuntime;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

public class ParticleSystem
{
    protected final PluginBase plugin;
    protected List<ParticleEffect> effects;
    protected Vector scaleVec;
    protected Vector rotationVec;
    protected Vector translationVec;
    protected Location origin;
    protected World world;
    protected long playTicks;
    protected long tickRate;
    protected long updateRate;
    protected ParticleRuntime runtime;

    public ParticleSystem(PluginBase plugin, Location origin, long playTicks, long tickRate, long updateRate)
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
    }

    public ParticleSystem(PluginBase plugin, Location origin, long playTicks, long tickRate)
    {
        this(plugin, origin, playTicks, tickRate, 0);
    }

    public ParticleSystem(PluginBase plugin, Location origin, long playTicks)
    {
        this(plugin, origin, playTicks, 0, 0);
    }

    public ParticleSystem display()
    {
        this.runtime = new ParticleRuntime(this, updateRate);
        this.runtime.runTaskTimerCountedAsynchronously(plugin, tickRate, playTicks);
        return this;
    }

    public ParticleSystem addParticleEffect(ParticleEffect effect)
    {
        effects.add(effect);
        return this;
    }

    public ParticleSystem addParticleEffect(ParticleEffect effect, int index)
    {
        effects.add(index, effect);
        return this;
    }

    public boolean containsParticleEffect(ParticleEffect effect)
    {
        return effects.contains(effect);
    }

    public ParticleSystem removeParticleEffect(int index)
    {
        effects.remove(index);
        return this;
    }

    public ParticleSystem removeParticleEffect(ParticleEffect effect)
    {
        effects.remove(effect);
        return this;
    }

    public ParticleSystem resetEffects()
    {
        effects.clear();
        return this;
    }

    public List<ParticleEffect> getEffects()
    {
        return effects;
    }

    public Vector getScaleVec()
    {
        return scaleVec;
    }

    public Vector getRotationVec()
    {
        return rotationVec;
    }

    public Vector getTranslationVec()
    {
        return translationVec;
    }

    public Location getOrigin()
    {
        return origin;
    }

    public void setScaleVec(Vector scaleVec)
    {
        this.scaleVec = scaleVec;
    }

    public void setRotationVec(Vector rotationVec)
    {
        this.rotationVec = rotationVec;
    }

    public void setTranslationVec(Vector translationVec)
    {
        this.translationVec = translationVec;
    }

    public void setOrigin(Location origin)
    {
        this.origin = origin;
    }

    public long getPlayTicks()
    {
        return playTicks;
    }

    public void setPlayTicks(long playTicks)
    {
        this.playTicks = playTicks;
    }

    public long getTickRate()
    {
        return tickRate;
    }

    public void setTickRate(long tickRate)
    {
        this.tickRate = tickRate;
    }

    public long getUpdateRate()
    {
        return updateRate;
    }

    public void setUpdateRate(long updateRate)
    {
        this.updateRate = updateRate;
    }
}
