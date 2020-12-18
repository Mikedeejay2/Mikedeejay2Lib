package com.mikedeejay2.mikedeejay2lib.particle.shape.generic;

import com.mikedeejay2.mikedeejay2lib.particle.shape.ParticleShape;
import com.mikedeejay2.mikedeejay2lib.util.math.MathUtil;
import org.bukkit.Location;

import java.util.List;

public class ParticleStar implements ParticleShape
{
    protected Location location;
    protected double density;
    protected int points;
    protected double radius;

    public ParticleStar(Location location, double radius, double density, int points)
    {
        this.location = location;
        this.density = density;
        this.points = points;
        this.radius = radius;
    }

    @Override
    public List<Location> getShape()
    {
        return MathUtil.getStarLocations(location, radius, density, points);
    }
}
