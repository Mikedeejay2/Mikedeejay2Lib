package com.mikedeejay2.mikedeejay2lib.particle.shape.cylinder;

import com.mikedeejay2.mikedeejay2lib.particle.shape.ParticleShape;
import com.mikedeejay2.mikedeejay2lib.util.math.MathUtil;
import org.bukkit.Location;

import java.util.List;

public class ParticleCylinder implements ParticleShape
{
    protected Location location;
    protected double density;
    protected double radius;
    protected double height;

    public ParticleCylinder(Location location, double radius, double height, double density)
    {
        this.location = location;
        this.density = density;
        this.radius = radius;
        this.height = height;
    }

    @Override
    public List<Location> getShape()
    {
        return MathUtil.getCylinderHollowLocations(location, radius, height, density);
    }
}
