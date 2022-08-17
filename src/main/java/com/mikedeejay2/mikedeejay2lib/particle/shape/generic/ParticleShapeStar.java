package com.mikedeejay2.mikedeejay2lib.particle.shape.generic;

import com.mikedeejay2.mikedeejay2lib.particle.shape.ParticleShape;
import com.mikedeejay2.mikedeejay2lib.util.math.MathUtil;
import org.bukkit.Location;
import org.bukkit.util.Vector;

import java.util.List;
/**
 * Particle Shape of a star. Facing down by default.
 *
 * @author Mikedeejay2
 */
public class ParticleShapeStar implements ParticleShape {
    /**
     * The center location
     */
    protected Location location;

    /**
     * The density of particles
     */
    protected double density;

    /**
     * The amount of points on the star
     */
    protected int points;

    /**
     * The radius
     */
    protected double radius;

    /**
     * Construct a new <code>ParticleShapeStar</code>
     *
     * @param location The center location
     * @param radius   The radius
     * @param density  The density of particles
     * @param points   The amount of points on the star
     */
    public ParticleShapeStar(Location location, double radius, double density, int points) {
        this.location = location;
        this.density = density;
        this.points = points;
        this.radius = radius;
    }

    /**
     * {@inheritDoc}
     *
     * @return The vector list
     */
    @Override
    public List<Vector> getShape() {
        return MathUtil.getStarVectors(location, radius, density, points);
    }
}
