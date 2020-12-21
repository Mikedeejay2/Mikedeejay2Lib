package com.mikedeejay2.mikedeejay2lib.particle.shape.circle;

import com.mikedeejay2.mikedeejay2lib.particle.shape.ParticleShape;
import com.mikedeejay2.mikedeejay2lib.util.math.MathUtil;
import org.bukkit.Location;
import org.bukkit.util.Vector;

import java.util.List;

/**
 * Particle Shape of a filled circle. Facing down by default.
 *
 * @author Mikedeejay2
 */
public class ParticleShapeCircleFilled implements ParticleShape
{
    // The center location
    protected Location location;
    // The radius
    protected double radius;
    // The density of particles
    protected double density;

    /**
     * @param location The center location
     * @param radius   The radius
     * @param density  The density of particles
     */
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
