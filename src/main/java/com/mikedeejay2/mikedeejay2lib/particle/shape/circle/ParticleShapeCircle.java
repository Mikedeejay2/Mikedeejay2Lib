package com.mikedeejay2.mikedeejay2lib.particle.shape.circle;

import com.mikedeejay2.mikedeejay2lib.particle.shape.ParticleShape;
import com.mikedeejay2.mikedeejay2lib.util.math.MathUtil;
import org.bukkit.Location;
import org.bukkit.util.Vector;

import java.util.List;

/**
 * Particle Shape of the outline of a circle. Facing down by default.
 *
 * @author Mikedeejay2
 */
public class ParticleShapeCircle implements ParticleShape
{
    /**
     * The center location
     */
    protected Location location;

    /**
     * The radius
     */
    protected double radius;

    /**
     * The density of particles
     */
    protected double density;

    /**
     * Construct a new <code>ParticleShapeCircle</code>
     *
     * @param location The center location
     * @param radius   The radius
     * @param density  The density of particles
     */
    public ParticleShapeCircle(Location location, double radius, double density)
    {
        this.location = location;
        this.density = density;
        this.radius = radius;
    }

    /**
     * {@inheritDoc}
     *
     * @return The vector list
     */
    @Override
    public List<Vector> getShape()
    {
        return MathUtil.getCircleVectors(location, radius, density);
    }
}
