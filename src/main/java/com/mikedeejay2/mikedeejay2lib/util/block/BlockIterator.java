package com.mikedeejay2.mikedeejay2lib.util.block;

import com.mikedeejay2.mikedeejay2lib.BukkitPlugin;
import com.mikedeejay2.mikedeejay2lib.runnable.EnhancedRunnable;
import com.mikedeejay2.mikedeejay2lib.util.math.MathUtil;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;

import java.util.List;
import java.util.function.Predicate;

/**
 * Utility class used for iterating through blocks of a <code>World</code>. Allows for different shapes and type
 * predicates during iteration with the use of a {@link BlockConsumer}
 *
 * @author Mikedeejay2
 */
public class BlockIterator
{
    /**
     * Iterate through all blocks around a location in a square.
     *
     * @param loc      The location in which to iterate blocks around
     * @param xWidth   The width of the iteration in the X direction
     * @param yWidth   The width of the iteration in the Y direction
     * @param zWidth   The width of the iteration in the Z direction
     * @param consumer The <code>BlockConsumer</code> that will be ran at each block
     * @param plugin   The plugin's instance to use
     * @param typePredicate The <code>Predicate</code> condition
     */
    public static void iterateBlocks(Location loc, int xWidth, int yWidth, int zWidth, BlockConsumer consumer, BukkitPlugin plugin, Predicate<Material> typePredicate)
    {
        EnhancedRunnable iteration = new EnhancedRunnable()
        {
            @Override
            public void onRun()
            {
                List<Location> locs = MathUtil.getCubeFilledLocations(loc, xWidth, yWidth, zWidth, 1);
                for(Location location : locs)
                {
                    Block block = location.getBlock();
                    Material material = block.getType();
                    if(typePredicate != null && !typePredicate.test(material)) continue;
                    consumer.accept(location, location.getBlock());
                }
            }
        };
        iteration.runTask(plugin);
    }

    /**
     * Iterate through all blocks around a location in a square.
     *
     * @param loc      The location in which to iterate blocks around
     * @param xWidth   The width of the iteration in the X direction
     * @param yWidth   The width of the iteration in the Y direction
     * @param zWidth   The width of the iteration in the Z direction
     * @param consumer The <code>BlockConsumer</code> that will be ran at each block
     * @param plugin   The plugin's instance to use
     */
    public static void iterateBlocks(Location loc, int xWidth, int yWidth, int zWidth, BlockConsumer consumer, BukkitPlugin plugin)
    {
        iterateBlocks(loc, xWidth, yWidth, zWidth, consumer, plugin, null);
    }

    /**
     * Iterate through all blocks around a location in a hollow square.
     *
     * @param loc1     The first location of the cube
     * @param loc2     The second location of the cube
     * @param consumer The <code>BlockConsumer</code> that will be ran at each block
     * @param plugin   The plugin's instance to use
     * @param typePredicate The <code>Predicate</code> condition
     */
    public static void iterateBlocksHollow(Location loc1, Location loc2, BlockConsumer consumer, BukkitPlugin plugin, Predicate<Material> typePredicate)
    {
        EnhancedRunnable iteration = new EnhancedRunnable()
        {
            @Override
            public void onRun()
            {
                List<Location> locs = MathUtil.getCubeHollowLocations(loc1, loc2, 1);
                for(Location location : locs)
                {
                    Block block = location.getBlock();
                    Material material = block.getType();
                    if(typePredicate != null && !typePredicate.test(material)) continue;
                    consumer.accept(location, location.getBlock());
                }
            }
        };
        iteration.runTask(plugin);
    }

    /**
     * Iterate through all blocks around a location in a hollow square.
     *
     * @param loc1     The first location of the cube
     * @param loc2     The second location of the cube
     * @param consumer The <code>BlockConsumer</code> that will be ran at each block
     * @param plugin   The plugin's instance to use
     */
    public static void iterateBlocksHollow(Location loc1, Location loc2, BlockConsumer consumer, BukkitPlugin plugin)
    {
        iterateBlocksHollow(loc1, loc2, consumer, plugin, null);
    }

    /**
     * Iterate through all blocks around a location in a square.
     *
     * @param loc1     The first location of the cube
     * @param loc2     The second location of the cube
     * @param consumer The <code>BlockConsumer</code> that will be ran at each block
     * @param plugin   The plugin's instance to use
     * @param typePredicate The <code>Predicate</code> condition
     */
    public static void iterateBlocks(Location loc1, Location loc2, BlockConsumer consumer, BukkitPlugin plugin, Predicate<Material> typePredicate)
    {
        EnhancedRunnable iteration = new EnhancedRunnable()
        {
            @Override
            public void onRun()
            {
                List<Location> locs = MathUtil.getCubeFilledLocations(loc1, loc2, 1);
                for(Location location : locs)
                {
                    Block block = location.getBlock();
                    Material material = block.getType();
                    if(typePredicate != null && !typePredicate.test(material)) continue;
                    consumer.accept(location, location.getBlock());
                }
            }
        };
        iteration.runTask(plugin);
    }

    /**
     * Iterate through all blocks around a location in a square.
     *
     * @param loc1     The first location of the cube
     * @param loc2     The second location of the cube
     * @param consumer The <code>BlockConsumer</code> that will be ran at each block
     * @param plugin   The plugin's instance to use
     */
    public static void iterateBlocks(Location loc1, Location loc2, BlockConsumer consumer, BukkitPlugin plugin)
    {
        iterateBlocks(loc1, loc2, consumer, plugin, null);
    }

    /**
     * Iterate through all blocks around a location in a hollow square.
     *
     * @param loc      The location in which to iterate blocks around
     * @param xWidth   The width of the iteration in the X direction
     * @param yWidth   The width of the iteration in the Y direction
     * @param zWidth   The width of the iteration in the Z direction
     * @param consumer The <code>BlockConsumer</code> that will be ran at each block
     * @param plugin   The plugin's instance to use
     * @param typePredicate The <code>Predicate</code> condition
     */
    public static void iterateBlocksHollow(Location loc, int xWidth, int yWidth, int zWidth, BlockConsumer consumer, BukkitPlugin plugin, Predicate<Material> typePredicate)
    {
        EnhancedRunnable iteration = new EnhancedRunnable()
        {
            @Override
            public void onRun()
            {
                List<Location> locs = MathUtil.getCubeHollowLocations(loc, xWidth, yWidth, zWidth, 1);
                for(Location location : locs)
                {
                    Block block = location.getBlock();
                    Material material = block.getType();
                    if(typePredicate != null && !typePredicate.test(material)) continue;
                    consumer.accept(location, location.getBlock());
                }
            }
        };
        iteration.runTask(plugin);
    }

    /**
     * Iterate through all blocks around a location in a hollow square.
     *
     * @param loc      The location in which to iterate blocks around
     * @param xWidth   The width of the iteration in the X direction
     * @param yWidth   The width of the iteration in the Y direction
     * @param zWidth   The width of the iteration in the Z direction
     * @param consumer The <code>BlockConsumer</code> that will be ran at each block
     * @param plugin   The plugin's instance to use
     */
    public static void iterateBlocksHollow(Location loc, int xWidth, int yWidth, int zWidth, BlockConsumer consumer, BukkitPlugin plugin)
    {
        iterateBlocksHollow(loc, xWidth, yWidth, zWidth, consumer, plugin, null);
    }

    /**
     * Iterate through all blocks around a location in a square outline.
     *
     * @param loc      The location in which to iterate blocks around
     * @param xWidth   The width of the iteration in the X direction
     * @param yWidth   The width of the iteration in the Y direction
     * @param zWidth   The width of the iteration in the Z direction
     * @param consumer The <code>BlockConsumer</code> that will be ran at each block
     * @param plugin   The plugin's instance to use
     * @param typePredicate The <code>Predicate</code> condition
     */
    public static void iterateBlocksOutline(Location loc, int xWidth, int yWidth, int zWidth, BlockConsumer consumer, BukkitPlugin plugin, Predicate<Material> typePredicate)
    {
        EnhancedRunnable iteration = new EnhancedRunnable()
        {
            @Override
            public void onRun()
            {
                List<Location> locs = MathUtil.getCubeOutlineLocations(loc, xWidth, yWidth, zWidth, 1);
                for(Location location : locs)
                {
                    Block block = location.getBlock();
                    Material material = block.getType();
                    if(typePredicate != null && !typePredicate.test(material)) continue;
                    consumer.accept(location, location.getBlock());
                }
            }
        };
        iteration.runTask(plugin);
    }

    /**
     * Iterate through all blocks around a location in a square outline.
     *
     * @param loc      The location in which to iterate blocks around
     * @param xWidth   The width of the iteration in the X direction
     * @param yWidth   The width of the iteration in the Y direction
     * @param zWidth   The width of the iteration in the Z direction
     * @param consumer The <code>BlockConsumer</code> that will be ran at each block
     * @param plugin   The plugin's instance to use
     */
    public static void iterateBlocksOutline(Location loc, int xWidth, int yWidth, int zWidth, BlockConsumer consumer, BukkitPlugin plugin)
    {
        iterateBlocksOutline(loc, xWidth, yWidth, zWidth, consumer, plugin, null);
    }


    /**
     * Iterate through all blocks around a location in a square outline.
     *
     * @param loc1     The first location of the cube
     * @param loc2     The second location of the cube
     * @param consumer The <code>BlockConsumer</code> that will be ran at each block
     * @param plugin   The plugin's instance to use
     * @param typePredicate The <code>Predicate</code> condition
     */
    public static void iterateBlocksOutline(Location loc1, Location loc2, BlockConsumer consumer, BukkitPlugin plugin, Predicate<Material> typePredicate)
    {
        EnhancedRunnable iteration = new EnhancedRunnable()
        {
            @Override
            public void onRun()
            {
                List<Location> locs = MathUtil.getCubeOutlineLocations(loc1, loc2, 1);
                for(Location location : locs)
                {
                    Block block = location.getBlock();
                    Material material = block.getType();
                    if(typePredicate != null && !typePredicate.test(material)) continue;
                    consumer.accept(location, location.getBlock());
                }
            }
        };
        iteration.runTask(plugin);
    }

    /**
     * Iterate through all blocks around a location in a square outline.
     *
     * @param loc1     The first location of the cube
     * @param loc2     The second location of the cube
     * @param consumer The <code>BlockConsumer</code> that will be ran at each block
     * @param plugin   The plugin's instance to use
     */
    public static void iterateBlocksOutline(Location loc1, Location loc2, BlockConsumer consumer, BukkitPlugin plugin)
    {
        iterateBlocksOutline(loc1, loc2, consumer, plugin, null);
    }

    /**
     * Iterate through all blocks in a sphere.
     *
     * @param loc      The location in which to iterate blocks around
     * @param radius   The radius of the sphere to iterate through
     * @param consumer The <code>BlockConsumer</code> that will be ran at each block
     * @param plugin   The plugin's instance to use
     * @param typePredicate The <code>Predicate</code> condition
     */
    public static void iterateBlocksSphere(Location loc, double radius, BlockConsumer consumer, BukkitPlugin plugin, Predicate<Material> typePredicate)
    {
        EnhancedRunnable iteration = new EnhancedRunnable()
        {
            @Override
            public void onRun()
            {
                List<Location> locs = MathUtil.getSphereFilledLocations(loc, radius, 1);
                for(Location location : locs)
                {
                    Block block = location.getBlock();
                    Material material = block.getType();
                    if(typePredicate != null && !typePredicate.test(material)) continue;
                    consumer.accept(location, location.getBlock());
                }
            }
        };
        iteration.runTask(plugin);
    }

    /**
     * Iterate through all blocks in a sphere.
     *
     * @param loc      The location in which to iterate blocks around
     * @param radius   The radius of the sphere to iterate through
     * @param consumer The <code>BlockConsumer</code> that will be ran at each block
     * @param plugin   The plugin's instance to use
     */
    public static void iterateBlocksSphere(Location loc, double radius, BlockConsumer consumer, BukkitPlugin plugin)
    {
        iterateBlocksSphere(loc, radius, consumer, plugin, null);
    }

    /**
     * Iterate through all blocks in a hollow sphere.
     *
     * @param loc      The location in which to iterate blocks around
     * @param radius   The radius of the sphere to iterate through
     * @param consumer The <code>BlockConsumer</code> that will be ran at each block
     * @param plugin   The plugin's instance to use
     * @param typePredicate The <code>Predicate</code> condition
     */
    public static void iterateBlocksSphereHollow(Location loc, double radius, BlockConsumer consumer, BukkitPlugin plugin, Predicate<Material> typePredicate)
    {
        EnhancedRunnable iteration = new EnhancedRunnable()
        {
            @Override
            public void onRun()
            {
                List<Location> locs = MathUtil.getSphereHollowLocations(loc, radius, 1);
                for(Location location : locs)
                {
                    Block block = location.getBlock();
                    Material material = block.getType();
                    if(typePredicate != null && !typePredicate.test(material)) continue;
                    consumer.accept(location, location.getBlock());
                }
            }
        };
        iteration.runTask(plugin);
    }

    /**
     * Iterate through all blocks in a hollow sphere.
     *
     * @param loc      The location in which to iterate blocks around
     * @param radius   The radius of the sphere to iterate through
     * @param consumer The <code>BlockConsumer</code> that will be ran at each block
     * @param plugin   The plugin's instance to use
     */
    public static void iterateBlocksSphereHollow(Location loc, double radius, BlockConsumer consumer, BukkitPlugin plugin)
    {
        iterateBlocksSphereHollow(loc, radius, consumer, plugin, null);
    }

    /**
     * Iterate through all blocks of a list of Locations.
     *
     * @param locs The List of Locations to iterate through
     * @param consumer The <code>BlockConsumer</code> that will be ran at each block
     * @param plugin   The plugin's instance to use
     * @param typePredicate The <code>Predicate</code> condition
     */
    public static void iterateBlocks(List<Location> locs, BlockConsumer consumer, BukkitPlugin plugin, Predicate<Material> typePredicate)
    {
        EnhancedRunnable iteration = new EnhancedRunnable()
        {
            @Override
            public void onRun()
            {
                for(Location location : locs)
                {
                    Block block = location.getBlock();
                    Material material = block.getType();
                    if(typePredicate != null && !typePredicate.test(material)) continue;
                    consumer.accept(location, location.getBlock());
                }
            }
        };
        iteration.runTask(plugin);
    }

    /**
     * Iterate through all blocks of a list of Locations.
     *
     * @param locs The List of Locations to iterate through
     * @param consumer The <code>BlockConsumer</code> that will be ran at each block
     * @param plugin   The plugin's instance to use
     */
    public static void iterateBlocks(List<Location> locs, BlockConsumer consumer, BukkitPlugin plugin)
    {
        iterateBlocks(locs, consumer, plugin, null);
    }
}
