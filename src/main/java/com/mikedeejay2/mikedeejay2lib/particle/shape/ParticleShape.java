package com.mikedeejay2.mikedeejay2lib.particle.shape;

import org.bukkit.util.Vector;

import java.util.List;

/**
 * Interface for all particle shapes to get a list of vectors.
 *
 * @author Mikedeejay2
 */
@FunctionalInterface
public interface ParticleShape
{
    /**
     * Get a list of vectors that represent the selected shape
     *
     * @return The vector list
     */
    List<Vector> getShape();
}
