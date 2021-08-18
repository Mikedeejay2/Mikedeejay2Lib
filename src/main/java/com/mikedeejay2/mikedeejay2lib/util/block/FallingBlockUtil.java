package com.mikedeejay2.mikedeejay2lib.util.block;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.FallingBlock;
import org.bukkit.util.Vector;

import java.util.Random;

/**
 * Small utility class used for converting existing blocks into {@link FallingBlock}s.
 *
 * @author Mikedeejay2
 */
public final class FallingBlockUtil
{
    private static final Random random = new Random();

    /**
     * Make a block fall based off of a location
     *
     * @param block Block to make fall
     * @return The falling block entity
     */
    public static FallingBlock makeBlockFall(Block block)
    {
        World world = block.getWorld();
        BlockData blockData = block.getBlockData();
        Location newLocation = new Location(world, block.getX() + 0.5f, block.getY(), block.getZ() + 0.5f);
        block.setType(Material.AIR);
        return world.spawnFallingBlock(newLocation, blockData);
    }

    /**
     * Make a block explode and turn into a falling block with a random velocity
     *
     * @param block         The block to explode
     * @param explosiveness The explosiveness (velocity) of the block
     * @return The falling block instance
     */
    public static FallingBlock makeBlockExplode(Block block, float explosiveness)
    {
        BlockData blockData = block.getBlockData().clone();
        FallingBlock fallingBlock = makeBlockFall(block);
        Vector randomVec = new Vector(random.nextFloat()-0.5f, random.nextFloat()-0.5f, random.nextFloat()-0.5f);
        randomVec.multiply(explosiveness);
        fallingBlock.setRotation((random.nextFloat()*360)-180, (random.nextFloat()*180)-90);
        fallingBlock.setVelocity(randomVec);
        return fallingBlock;
    }
}
