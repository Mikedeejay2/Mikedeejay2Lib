package com.mikedeejay2.mikedeejay2lib.particle.shape.generic;

import com.mikedeejay2.mikedeejay2lib.particle.shape.ParticleShape;
import com.mikedeejay2.mikedeejay2lib.util.math.MathUtil;
import org.bukkit.Location;
import org.bukkit.util.Vector;

import java.util.List;

/**
 * Particle Shape of a polygon. Facing down by default.
 *
 * @author Mikedeejay2
 */
public class ParticleShapePolygon implements ParticleShape
{
    /**
     * The center location
     */
    protected Location location;

    /**
     * The density of particles
     */
    protected double density;

    /**
     * The amount of edges of the shape
     */
    protected int edges;

    /**
     * The size (radius)
     */
    protected double size;

    /**
     * Construct a new <code>ParticleShapePolygon</code>
     *
     * @param location The center location
     * @param size     The size (radius)
     * @param density  The density of particles
     * @param edges    The amount of edges of the shape
     */
    public ParticleShapePolygon(Location location, double size, double density, int edges)
    {
        this.location = location;
        this.density = density;
        this.edges = edges;
        this.size = size;
    }

    /**
     * {@inheritDoc}
     *
     * @return The vector list
     */
    @Override
    public List<Vector> getShape()
    {
        return MathUtil.getShapeVectors(location, size, density, edges);
    }
}
