package com.mikedeejay2.mikedeejay2lib.util.block;

import com.mikedeejay2.mikedeejay2lib.util.math.MathUtil;
import org.bukkit.Location;
import org.bukkit.World;

import java.util.List;

public class BlockIterator
{
    /**
     * Iterate through all blocks around a location in a square.
     *
     * @param loc The location in which to iterate blocks around
     * @param xWidth The width of the iteration in the X direction
     * @param yWidth The width of the iteration in the Y direction
     * @param zWidth The width of the iteration in the Z direction
     * @param runnable The <tt>BlockRunnable</tt> that will be ran at each block
     */
    public static void iterateBlocks(Location loc, int xWidth, int yWidth, int zWidth, BlockRunnable runnable)
    {
        List<Location> locs = MathUtil.getCubeFilledLocations(loc, xWidth, yWidth, zWidth, 1);
        locs.forEach(location -> runnable.run(location, location.getBlock()));
    }
    /**
     * Iterate through all blocks around a location in a hollow square.
     *
     * @param loc The location in which to iterate blocks around
     * @param xWidth The width of the iteration in the X direction
     * @param yWidth The width of the iteration in the Y direction
     * @param zWidth The width of the iteration in the Z direction
     * @param runnable The <tt>BlockRunnable</tt> that will be ran at each block
     */
    public static void iterateBlocksHollow(Location loc, int xWidth, int yWidth, int zWidth, BlockRunnable runnable)
    {
        List<Location> locs = MathUtil.getCubeHollowLocations(loc, xWidth, yWidth, zWidth, 1);
        locs.forEach(location -> runnable.run(location, location.getBlock()));
    }

    /**
     * Iterate through all blocks around a location in a square outline.
     *
     * @param loc The location in which to iterate blocks around
     * @param xWidth The width of the iteration in the X direction
     * @param yWidth The width of the iteration in the Y direction
     * @param zWidth The width of the iteration in the Z direction
     * @param runnable The <tt>BlockRunnable</tt> that will be ran at each block
     */
    public static void iterateBlocksOutline(Location loc, int xWidth, int yWidth, int zWidth, BlockRunnable runnable)
    {
        List<Location> locs = MathUtil.getCubeOutlineLocations(loc, xWidth, yWidth, zWidth, 1);
        locs.forEach(location -> runnable.run(location, location.getBlock()));
    }

    /**
     * Iterate through all blocks in a sphere.
     *
     * @param loc The location in which to iterate blocks around
     * @param radius The radius of the sphere to iterate through
     * @param runnable The <tt>BlockRunnable</tt> that will be ran at each block
     */
    public static void iterateBlocksSphere(Location loc, double radius, BlockRunnable runnable)
    {
        List<Location> locs = MathUtil.getSphereFilledLocations(loc, radius, 1);
        locs.forEach(location -> runnable.run(location, location.getBlock()));
    }

    /**
     * Iterate through all blocks in a hollow sphere.
     *
     * @param loc The location in which to iterate blocks around
     * @param radius The radius of the sphere to iterate through
     * @param runnable The <tt>BlockRunnable</tt> that will be ran at each block
     */
    public static void iterateBlocksSphereHollow(Location loc, double radius, BlockRunnable runnable)
    {
        List<Location> locs = MathUtil.getSphereHollowLocations(loc, radius, 1);
        locs.forEach(location -> runnable.run(location, location.getBlock()));
    }
}
