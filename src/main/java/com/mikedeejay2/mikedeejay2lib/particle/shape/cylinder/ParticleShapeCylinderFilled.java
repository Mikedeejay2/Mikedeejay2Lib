package com.mikedeejay2.mikedeejay2lib.particle.shape.cylinder;

import com.mikedeejay2.mikedeejay2lib.particle.shape.ParticleShape;
import com.mikedeejay2.mikedeejay2lib.util.math.MathUtil;
import org.bukkit.Location;
import org.bukkit.util.Vector;

import java.util.List;

/**
 * Particle Shape of a filled cylinder.
 *
 * @author Mikedeejay2
 */
public class ParticleShapeCylinderFilled implements ParticleShape
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
     * The height
     */
    protected double height;

    /**
     * Construct a new <code>ParticleShapeCylinderFilled</code>
     *
     * @param location The center location
     * @param radius   The radius
     * @param height   The height
     * @param density  The density of particles
     */
    public ParticleShapeCylinderFilled(Location location, double radius, double height, double density)
    {
        this.location = location;
        this.density = density;
        this.radius = radius;
        this.height = height;
    }

    /**
     * {@inheritDoc}
     *
     * @return The vector list
     */
    @Override
    public List<Vector> getShape()
    {
        return MathUtil.getCylinderFilledVectors(location, radius, height, density);
    }
}
