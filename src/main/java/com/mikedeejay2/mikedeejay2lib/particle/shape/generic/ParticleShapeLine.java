package com.mikedeejay2.mikedeejay2lib.particle.shape.generic;

import com.mikedeejay2.mikedeejay2lib.particle.shape.ParticleShape;
import com.mikedeejay2.mikedeejay2lib.util.math.MathUtil;
import org.bukkit.Location;
import org.bukkit.util.Vector;

import java.util.List;

/**
 * Particle Shape of a line.
 *
 * @author Mikedeejay2
 */
public class ParticleShapeLine implements ParticleShape {
    /**
     * The starting location
     */
    protected Location start;

    /**
     * The ending location
     */
    protected Location end;

    /**
     * The density of particles
     */
    protected double density;

    /**
     * Construct a new <code>ParticleShapeLine</code>
     *
     * @param start   The starting location
     * @param end     The ending location
     * @param density The density of particles
     */
    public ParticleShapeLine(Location start, Location end, double density) {
        this.start = start;
        this.end = end;
        this.density = density;
    }

    /**
     * {@inheritDoc}
     *
     * @return The vector list
     */
    @Override
    public List<Vector> getShape() {
        return MathUtil.getLine(start.toVector(), end.toVector(), density);
    }
}
