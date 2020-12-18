package com.mikedeejay2.mikedeejay2lib.particle.shape.circle;

import com.mikedeejay2.mikedeejay2lib.particle.shape.ParticleShape;
import com.mikedeejay2.mikedeejay2lib.util.math.MathUtil;
import org.bukkit.Location;

import java.util.List;

public class ParticleCircle implements ParticleShape
{
    protected Location location;
    protected double density;
    protected double radius;

    public ParticleCircle(Location location, double radius, double density)
    {
        this.location = location;
        this.density = density;
        this.radius = radius;
    }

    @Override
    public List<Location> getShape()
    {
        return MathUtil.getCircleLocations(location, radius, density);
    }
}
