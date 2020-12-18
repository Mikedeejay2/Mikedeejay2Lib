package com.mikedeejay2.mikedeejay2lib.particle.shape.sphere;

import com.mikedeejay2.mikedeejay2lib.particle.shape.ParticleShape;
import com.mikedeejay2.mikedeejay2lib.util.math.MathUtil;
import org.bukkit.Location;

import java.util.List;

public class ParticleSphere implements ParticleShape
{
    protected Location location;
    protected double density;
    protected double radius;

    public ParticleSphere(Location location, double radius, double density)
    {
        this.location = location;
        this.density = density;
        this.radius = radius;
    }

    @Override
    public List<Location> getShape()
    {
        return MathUtil.getSphereHollowLocations(location, radius, density);
    }
}
