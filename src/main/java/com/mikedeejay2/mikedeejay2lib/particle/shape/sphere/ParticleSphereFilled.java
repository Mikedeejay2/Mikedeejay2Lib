package com.mikedeejay2.mikedeejay2lib.particle.shape.sphere;

import com.mikedeejay2.mikedeejay2lib.particle.shape.ParticleShape;
import com.mikedeejay2.mikedeejay2lib.util.math.MathUtil;
import org.bukkit.Location;
import org.bukkit.util.Vector;

import java.util.List;

/**
 * Particle Shape of a filled sphere.
 *
 * @author Mikedeejay2
 */
public class ParticleSphereFilled implements ParticleShape
{
    // The center location
    protected Location location;
    // The density of particles
    protected double density;
    // The radius
    protected double radius;

    /**
     * @param location The center location
     * @param radius   The radius
     * @param density  The density of particles
     */
    public ParticleSphereFilled(Location location, double radius, double density)
    {
        this.location = location;
        this.density = density;
        this.radius = radius;
    }

    @Override
    public List<Vector> getShape()
    {
        return MathUtil.getSphereFilledVectors(location, radius, density);
    }
}
