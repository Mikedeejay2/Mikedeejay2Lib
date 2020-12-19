package com.mikedeejay2.mikedeejay2lib.particle.shape.circle;

import com.mikedeejay2.mikedeejay2lib.particle.shape.ParticleShape;
import com.mikedeejay2.mikedeejay2lib.util.math.MathUtil;
import org.bukkit.Location;
import org.bukkit.util.Vector;

import java.util.List;

public class ParticleShapeCircle implements ParticleShape
{
    protected Location location;
    protected double density;
    protected double radius;

    public ParticleShapeCircle(Location location, double radius, double density)
    {
        this.location = location;
        this.density = density;
        this.radius = radius;
    }

    @Override
    public List<Vector> getShape()
    {
        return MathUtil.getCircleVectors(location, radius, density);
    }
}
