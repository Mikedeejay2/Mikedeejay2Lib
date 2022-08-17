package com.mikedeejay2.mikedeejay2lib.particle.shape.generic;

import com.mikedeejay2.mikedeejay2lib.particle.shape.ParticleShape;
import com.mikedeejay2.mikedeejay2lib.util.math.MathUtil;
import org.bukkit.Location;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Particle Shape of a point.
 *
 * @author Mikedeejay2
 */
public class ParticleShapePoint implements ParticleShape {
    /**
     * The location
     */
    protected Location location;

    /**
     * Construct a new <code>ParticleShapePoint</code>
     *
     * @param location The location
     */
    public ParticleShapePoint(Location location) {
        this.location = location;
    }

    /**
     * {@inheritDoc}
     *
     * @return The vector list
     */
    @Override
    public List<Vector> getShape() {
        List<Vector> list = new ArrayList<>();
        list.add(location.toVector());
        return list;
    }
}
