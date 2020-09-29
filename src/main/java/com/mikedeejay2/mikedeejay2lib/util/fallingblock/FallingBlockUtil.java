package com.mikedeejay2.mikedeejay2lib.util.fallingblock;

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
                    FallingBlock fallingBlock = world.spawnFallingBlock(location, blockData);
                    Vector randomVec = new Vector(random.nextFloat()-0.5f, random.nextFloat()-0.5f, random.nextFloat()-0.5f);
                    randomVec.multiply(explosiveness);
                    fallingBlock.setRotation((random.nextFloat()*360)-180, (random.nextFloat()*180)-90);
                    fallingBlock.setVelocity(randomVec);
                }
            }
        }
    }
}
