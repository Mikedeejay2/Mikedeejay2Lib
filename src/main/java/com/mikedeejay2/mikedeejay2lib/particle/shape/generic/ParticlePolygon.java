package com.mikedeejay2.mikedeejay2lib.particle.shape.generic;

import com.mikedeejay2.mikedeejay2lib.particle.shape.ParticleShape;
import com.mikedeejay2.mikedeejay2lib.util.math.MathUtil;
import org.bukkit.Location;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

public class ParticlePolygon implements ParticleShape
{
    protected Location location;
    protected double density;
    protected int edges;
    protected double size;

    public ParticlePolygon(Location location, double size, double density, int edges)
    {
        this.location = location;
        this.density = density;
        this.edges = edges;
        this.size = size;
    }

    @Override
    public List<Vector> getShape()
    {
        return MathUtil.getShapeVectors(location, size, density, edges);
    }
}
