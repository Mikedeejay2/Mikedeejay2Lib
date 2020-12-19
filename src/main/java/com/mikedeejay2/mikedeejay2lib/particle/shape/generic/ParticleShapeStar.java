package com.mikedeejay2.mikedeejay2lib.particle.shape.generic;

import com.mikedeejay2.mikedeejay2lib.particle.shape.ParticleShape;
import com.mikedeejay2.mikedeejay2lib.util.math.MathUtil;
import org.bukkit.Location;
import org.bukkit.util.Vector;

import java.util.List;

public class ParticleShapeStar implements ParticleShape
{
    protected Location location;
    protected double density;
    protected int points;
    protected double radius;

    public ParticleShapeStar(Location location, double radius, double density, int points)
    {
        this.location = location;
        this.density = density;
        this.points = points;
        this.radius = radius;
    }

    @Override
    public List<Vector> getShape()
    {
        return MathUtil.getStarVectors(location, radius, density, points);
    }
}
