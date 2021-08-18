package com.mikedeejay2.mikedeejay2lib.particle.shape.sphere;

import com.mikedeejay2.mikedeejay2lib.particle.shape.ParticleShape;
import com.mikedeejay2.mikedeejay2lib.util.math.MathUtil;
import org.bukkit.Location;
import org.bukkit.util.Vector;

import java.util.List;

/**
 * Particle Shape of a hollow sphere.
 *
 * @author Mikedeejay2
 */
public class ParticleSphere implements ParticleShape
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
     * The radius
     */
    protected double radius;

    /**
     * Construct a new <code>ParticleSphere</code>
     *
     * @param location The center location
     * @param radius   The radius
     * @param density  The density of particles
     */
    public ParticleSphere(Location location, double radius, double density)
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
        return MathUtil.getSphereHollowVectors(location, radius, density);
    }
}
