package com.mikedeejay2.mikedeejay2lib.util.block;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.FallingBlock;
import org.bukkit.util.Vector;

import java.util.Random;

public final class FallingBlockUtil
{
    private static Random random = new Random();

    public static void makeBlocksExplode(Location location, int distanceX, int distanceY, int distanceZ, float explosiveness)
    {
        World world = location.getWorld();
        int startDistX = (int) (location.getX() - distanceX);
        int startDistY = (int) (location.getY() - distanceY);
        int startDistZ = (int) (location.getZ() - distanceZ);
        int endDistX = (int) (location.getX() + distanceX);
        int endDistY = (int) (location.getY() + distanceY);
        int endDistZ = (int) (location.getZ() + distanceZ);
        for(int x = startDistX; x <= endDistX; x++)
        {
            for(int y = startDistY; y <= endDistY; y++)
            {
                for(int z = startDistZ; z <= endDistZ; z++)
                {
                    Block block = world.getBlockAt(x, y, z);
                    BlockData blockData = block.getBlockData().clone();
                    block.setType(Material.AIR);
                    FallingBlock fallingBlock = makeBlockFall(new Location(world, x, y, z));
                    Vector randomVec = new Vector(random.nextFloat()-0.5f, random.nextFloat()-0.5f, random.nextFloat()-0.5f);
                    randomVec.multiply(explosiveness);
                    fallingBlock.setRotation((random.nextFloat()*360)-180, (random.nextFloat()*180)-90);
                    fallingBlock.setVelocity(randomVec);
                }
            }
        }
    }

    /**
     * Make a block fall based off of a location
     *
     * @param location Location to make block fall
     * @return The falling block entity
     */
    public static FallingBlock makeBlockFall(Location location)
    {
        World world = location.getWorld();
        Block block = location.getBlock();
        BlockData blockData = block.getBlockData();
        Location newLocation = new Location(world, location.getBlockX() + 0.5f, location.getBlockY() + 0.5f, location.getBlockZ() + 0.5f);
        block.setType(Material.AIR);
        return world.spawnFallingBlock(newLocation, blockData);
    }
}
