package com.mikedeejay2.mikedeejay2lib.particle.shape.circle;

import com.mikedeejay2.mikedeejay2lib.particle.shape.ParticleShape;
import com.mikedeejay2.mikedeejay2lib.util.math.MathUtil;
import org.bukkit.Location;
import org.bukkit.util.Vector;

import java.util.List;

public class ParticleShapeCircleFilled implements ParticleShape
{
    protected Location location;
    protected double density;
    protected double radius;

    public ParticleShapeCircleFilled(Location location, double radius, double density)
    {
        this.location = location;
        this.density = density;
        this.radius = radius;
    }

    @Override
    public List<Vector> getShape()
    {
        return MathUtil.getCircleFilledVectors(location, radius, density);
    }
}
