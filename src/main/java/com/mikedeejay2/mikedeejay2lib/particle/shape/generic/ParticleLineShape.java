package com.mikedeejay2.mikedeejay2lib.particle.shape.generic;

import com.mikedeejay2.mikedeejay2lib.particle.shape.ParticleShape;
import com.mikedeejay2.mikedeejay2lib.util.math.MathUtil;
import org.bukkit.Location;

import java.util.List;

public class ParticleLineShape implements ParticleShape
{
    protected Location start;
    protected Location end;
    protected double density;

    public ParticleLineShape(Location start, Location end, double density)
    {
        this.start = start;
        this.end = end;
        this.density = density;
    }

    @Override
    public List<Location> getShape()
    {
        return MathUtil.getLine(start, end, density);
    }
}
