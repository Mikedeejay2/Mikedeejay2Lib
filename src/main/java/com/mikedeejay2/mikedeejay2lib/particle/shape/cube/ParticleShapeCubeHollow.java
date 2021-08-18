package com.mikedeejay2.mikedeejay2lib.particle.shape.cube;

import com.mikedeejay2.mikedeejay2lib.particle.shape.ParticleShape;
import com.mikedeejay2.mikedeejay2lib.util.math.MathUtil;
import org.bukkit.Location;
import org.bukkit.util.Vector;

import java.util.List;

/**
 * Particle Shape of a hollow cube.
 *
 * @author Mikedeejay2
 */
public class ParticleShapeCubeHollow implements ParticleShape
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
     * The X width
     */
    protected double xWidth;

    /**
     * The Y width
     */
    protected double yWidth;

    /**
     * The Z width
     */
    protected double zWidth;

    /**
     * First location
     */
    protected Location loc1;

    /**
     * Second location
     */
    protected Location loc2;

    /**
     * The mode of the cube
     * <code>true</code> - Dual location method
     * <code>false</code> - Widths method
     */
    boolean mode;

    /**
     * Construct a new <code>ParticleShapeCubeHollow</code>
     *
     * @param location The center location
     * @param xWidth   The X width
     * @param yWidth   The Y width
     * @param zWidth   The Z width
     * @param density  The density of particles
     */
    public ParticleShapeCubeHollow(Location location, double xWidth, double yWidth, double zWidth, double density)
    {
        this.location = location;
        this.density = density;
        this.xWidth = xWidth;
        this.yWidth = yWidth;
        this.zWidth = zWidth;
        this.mode = false;
    }

    /**
     * Construct a new <code>ParticleShapeCubeHollow</code>
     *
     * @param loc1    First location
     * @param loc2    Second location
     * @param density The density of particles
     */
    public ParticleShapeCubeHollow(Location loc1, Location loc2, double density)
    {
        this.loc1 = loc1;
        this.loc2 = loc2;
        this.density = density;
        this.mode = true;
    }

    /**
     * {@inheritDoc}
     *
     * @return The vector list
     */
    @Override
    public List<Vector> getShape()
    {
        if(mode)
        {
            return MathUtil.getCubeHollowVectors(loc1, loc2, density);
        }
        return MathUtil.getCubeHollowVectors(location, xWidth, yWidth, zWidth, density);
    }
}
