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
public class ParticleShapeCylinderFilled implements ParticleShape {
    /**
     * The center location
     */
    protected Location location;

    /**
     * The radius
     */
    protected double radius;

    /**
     * The density of particles in relation to the radius
     */
    protected double radiusDensity;

    /**
     * The density of particles in relation to the height
     */
    protected double heightDensity;

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
     * @param radiusDensity The density of particles in relation to the radius
     * @param heightDensity The density of particles in relation to the height
     */
    public ParticleShapeCylinderFilled(Location location, double radius, double height, double radiusDensity, double heightDensity) {
        this.location = location;
        this.radiusDensity = radiusDensity;
        this.heightDensity = heightDensity;
        this.radius = radius;
        this.height = height;
    }

    /**
     * {@inheritDoc}
     *
     * @return The vector list
     */
    @Override
    public List<Vector> getShape() {
        return MathUtil.getCylinderFilledVectors(location, radius, height, radiusDensity, heightDensity);
    }
}
