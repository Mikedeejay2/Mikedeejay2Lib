package com.mikedeejay2.mikedeejay2lib.util.block;

import org.bukkit.Location;
import org.bukkit.World;

public class BlockIterateUtil
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
        World world = loc.getWorld();
        int startX = loc.getBlockX() - xWidth;
        int startY = loc.getBlockY() - yWidth;
        int startZ = loc.getBlockZ() - zWidth;
        int endX = loc.getBlockX() + xWidth;
        int endY = loc.getBlockY() + yWidth;
        int endZ = loc.getBlockZ() + zWidth;
        for(int x = startX; x <= endX; x++)
        {
            for(int y = startY; y <= endY; y++)
            {
                for(int z = startZ; z <= endZ; z++)
                {
                    Location location = new Location(world, x, y, z);
                    runnable.run(location, world.getBlockAt(x, y, z));
                }
            }
        }
    }
}
