package com.mikedeejay2.mikedeejay2lib.particle.shape.cube;

import com.mikedeejay2.mikedeejay2lib.particle.shape.ParticleShape;
import com.mikedeejay2.mikedeejay2lib.util.math.MathUtil;
import org.bukkit.Location;
import org.bukkit.util.Vector;

import java.util.List;

/**
 * Particle Shape of a filled cube.
 *
 * @author Mikedeejay2
 */
public class ParticleShapeCubeFilled implements ParticleShape
{
    // The center location
    protected Location location;
    // The density of particles
    protected double density;
    // The X width
    protected double xWidth;
    // The Y width
    protected double yWidth;
    // The Z width
    protected double zWidth;
    // First location
    protected Location loc1;
    // Second location
    protected Location loc2;
    // The mode of the cube
    // true - Dual location method
    // false - Widths method
    boolean mode;

    /**
     * @param location The center location
     * @param xWidth The X width
     * @param yWidth The Y width
     * @param zWidth The Z width
     * @param density The density of particles
     */
    public ParticleShapeCubeFilled(Location location, double xWidth, double yWidth, double zWidth, double density)
    {
        this.location = location;
        this.density = density;
        this.xWidth = xWidth;
        this.yWidth = yWidth;
        this.zWidth = zWidth;
        this.mode = false;
    }

    /**
     * @param loc1 First location
     * @param loc2 Second location
     * @param density The density of particles
     */
    public ParticleShapeCubeFilled(Location loc1, Location loc2, double density)
    {
        this.loc1 = loc1;
        this.loc2 = loc2;
        this.density = density;
        this.mode = true;
    }

    @Override
    public List<Vector> getShape()
    {
        if(mode)
        {
            return MathUtil.getCubeFilledVectors(loc1, loc2, density);
        }
        return MathUtil.getCubeFilledVectors(location, xWidth, yWidth, zWidth, density);
    }
}
