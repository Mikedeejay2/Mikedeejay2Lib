package com.mikedeejay2.mikedeejay2lib.particle.shape.sphere;

import com.mikedeejay2.mikedeejay2lib.particle.shape.ParticleShape;
import com.mikedeejay2.mikedeejay2lib.util.math.MathUtil;
import org.bukkit.Location;
import org.bukkit.util.Vector;

import java.util.List;

public class ParticleSphereFilled implements ParticleShape
{
    protected Location location;
    protected double density;
    protected double radius;

    public ParticleSphereFilled(Location location, double radius, double density)
    {
        this.location = location;
        this.density = density;
        this.radius = radius;
    }

    @Override
    public List<Vector> getShape()
    {
        return MathUtil.getSphereFilledVectors(location, radius, density);
    }
}
