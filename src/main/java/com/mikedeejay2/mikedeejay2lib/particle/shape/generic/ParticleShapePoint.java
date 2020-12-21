package com.mikedeejay2.mikedeejay2lib.particle.shape.generic;

import com.mikedeejay2.mikedeejay2lib.particle.shape.ParticleShape;
import org.bukkit.Location;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

/**
 * Particle Shape of a point.
 *
 * @author Mikedeejay2
 */
public class ParticleShapePoint implements ParticleShape
{
    // The location
    protected Location location;

    /**
     * @param location The location
     */
    public ParticleShapePoint(Location location)
    {
        this.location = location;
    }

    @Override
    public List<Vector> getShape()
    {
        List<Vector> list = new ArrayList<>();
        list.add(location.toVector());
        return list;
    }
}
