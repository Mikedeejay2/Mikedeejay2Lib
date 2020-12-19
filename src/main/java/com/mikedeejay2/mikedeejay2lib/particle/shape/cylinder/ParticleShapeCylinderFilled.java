package com.mikedeejay2.mikedeejay2lib.particle.shape.cylinder;

import com.mikedeejay2.mikedeejay2lib.particle.shape.ParticleShape;
import com.mikedeejay2.mikedeejay2lib.util.math.MathUtil;
import org.bukkit.Location;
import org.bukkit.util.Vector;

import java.util.List;

public class ParticleShapeCylinderFilled implements ParticleShape
{
    protected Location location;
    protected double density;
    protected double radius;
    protected double height;

    public ParticleShapeCylinderFilled(Location location, double radius, double height, double density)
    {
        this.location = location;
        this.density = density;
        this.radius = radius;
        this.height = height;
    }

    @Override
    public List<Vector> getShape()
    {
        return MathUtil.getCylinderFilledVectors(location, radius, height, density);
    }
}
