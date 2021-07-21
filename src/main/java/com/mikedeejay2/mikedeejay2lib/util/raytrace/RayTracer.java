package com.mikedeejay2.mikedeejay2lib.util.raytrace;

import org.bukkit.FluidCollisionMode;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.Vector;

import java.util.function.Predicate;

/**
 * A class for dealing with getting locations that an entity is looking at.
 *
 * @author Mikedeejay2
 */
public final class RayTracer
{
    /**
     * Ray trace from an entity's point of view to what they are looking and get that position
     * as a vector
     * <p>
     * Uses default max distance: 256 <br>
     * Uses default <code>FluidCollisionMode</code>: Never <br>
     * Uses default ignore passable blocks: false <br>
     *
     * @param entity Entity's view that will be raytraced
     * @return The Vector of the raytrace hit, null if the raytrace never hit anything
     */
    public static Vector rayTraceVector(LivingEntity entity)
    {
        return rayTraceVector(entity, 256, FluidCollisionMode.NEVER, false);
    }

    /**
     * Ray trace from an entity's point of view to what they are looking and get that position
     * as a vector
     * <p>
     * Uses default <code>FluidCollisionMode</code>: Never <br>
     * Uses default ignore passable blocks: false <br>
     *
     * @param entity      Entity's view that will be raytraced
     * @param maxDistance The maximum distance that should be raytraced before it stops trying
     * @return The Vector of the raytrace hit, null if the raytrace never hit anything
     */
    public static Vector rayTraceVector(LivingEntity entity, double maxDistance)
    {
        return rayTraceVector(entity, maxDistance, FluidCollisionMode.NEVER, false);
    }

    /**
     * Ray trace from an entity's point of view to what they are looking and get that position
     * as a vector
     * <p>
     * Uses default <code>FluidCollisionMode</code>: Never <br>
     *
     * @param entity               Entity's view that will be raytraced
     * @param maxDistance          The maximum distance that should be raytraced before it stops trying
     * @param ignorePassableBlocks If the raytrace should ignore blocks that are passable (grass, flowers, etc)
     * @return The Vector of the raytrace hit, null if the raytrace never hit anything
     */
    public static Vector rayTraceVector(LivingEntity entity, double maxDistance, boolean ignorePassableBlocks)
    {
        return rayTraceVector(entity, maxDistance, FluidCollisionMode.NEVER, ignorePassableBlocks);
    }

    /**
     * Ray trace from an entity's point of view to what they are looking and get that position
     * as a vector
     * <p>
     * Uses default ignore passable blocks: false <br>
     *
     * @param entity             Entity's view that will be raytraced
     * @param maxDistance        The maximum distance that should be raytraced before it stops trying
     * @param fluidCollisionMode The fluid collision mode to use
     * @return The Vector of the raytrace hit, null if the raytrace never hit anything
     */
    public static Vector rayTraceVector(LivingEntity entity, double maxDistance, FluidCollisionMode fluidCollisionMode)
    {
        return rayTraceVector(entity, maxDistance, fluidCollisionMode, false);
    }

    /**
     * Ray trace from an entity's point of view to what they are looking and get that position
     * as a vector
     *
     * @param entity               Entity's view that will be raytraced
     * @param maxDistance          The maximum distance that should be raytraced before it stops trying
     * @param fluidCollisionMode   The fluid collision mode to use
     * @param ignorePassableBlocks If the raytrace should ignore blocks that are passable (grass, flowers, etc)
     * @return The Vector of the raytrace hit, null if the raytrace never hit anything
     */
    public static Vector rayTraceVector(LivingEntity entity, double maxDistance, FluidCollisionMode fluidCollisionMode, boolean ignorePassableBlocks)
    {
        World world = entity.getWorld();
        Location startLoc = entity.getEyeLocation();
        Vector lookVec = startLoc.getDirection();
        Predicate<Entity> entities = entity1 -> (entity1 != entity);
        RayTraceResult result = world.rayTrace(startLoc, lookVec, maxDistance, fluidCollisionMode, ignorePassableBlocks, 0, entities);
        if(result == null) return null;
        return result.getHitPosition();
    }

    /**
     * Ray trace from an entity's point of view to what they are looking and get that position
     * as a location
     * <p>
     * Uses default max distance: 256 <br>
     * Uses default <code>FluidCollisionMode</code>: Never <br>
     * Uses default ignore passable blocks: false <br>
     *
     * @param entity Entity's view that will be raytraced
     * @return The Location of the raytrace hit, null if the raytrace never hit anything
     */
    public static Location rayTraceLocation(LivingEntity entity)
    {
        return rayTraceLocation(entity, 256, FluidCollisionMode.NEVER, false);
    }

    /**
     * Ray trace from an entity's point of view to what they are looking and get that position
     * as a Location
     * <p>
     * Uses default <code>FluidCollisionMode</code>: Never <br>
     * Uses default ignore passable blocks: false <br>
     *
     * @param entity      Entity's view that will be raytraced
     * @param maxDistance The maximum distance that should be raytraced before it stops trying
     * @return The Location of the raytrace hit, null if the raytrace never hit anything
     */
    public static Location rayTraceLocation(LivingEntity entity, double maxDistance)
    {
        return rayTraceLocation(entity, maxDistance, FluidCollisionMode.NEVER, false);
    }

    /**
     * Ray trace from an entity's point of view to what they are looking and get that position
     * as a Location
     * <p>
     * Uses default <code>FluidCollisionMode</code>: Never <br>
     *
     * @param entity               Entity's view that will be raytraced
     * @param maxDistance          The maximum distance that should be raytraced before it stops trying
     * @param ignorePassableBlocks If the raytrace should ignore blocks that are passable (grass, flowers, etc)
     * @return The Location of the raytrace hit, null if the raytrace never hit anything
     */
    public static Location rayTraceLocation(LivingEntity entity, double maxDistance, boolean ignorePassableBlocks)
    {
        return rayTraceLocation(entity, maxDistance, FluidCollisionMode.NEVER, ignorePassableBlocks);
    }

    /**
     * Ray trace from an entity's point of view to what they are looking and get that position
     * as a Location
     * <p>
     * Uses default ignore passable blocks: false <br>
     *
     * @param entity             Entity's view that will be raytraced
     * @param maxDistance        The maximum distance that should be raytraced before it stops trying
     * @param fluidCollisionMode The fluid collision mode to use
     * @return The Location of the raytrace hit, null if the raytrace never hit anything
     */
    public static Location rayTraceLocation(LivingEntity entity, double maxDistance, FluidCollisionMode fluidCollisionMode)
    {
        return rayTraceLocation(entity, maxDistance, fluidCollisionMode, false);
    }

    /**
     * Ray trace from an entity's point of view to what they are looking and get that position
     * as a Location
     *
     * @param entity               Entity's view that will be raytraced
     * @param maxDistance          The maximum distance that should be raytraced before it stops trying
     * @param fluidCollisionMode   The fluid collision mode to use
     * @param ignorePassableBlocks If the raytrace should ignore blocks that are passable (grass, flowers, etc)
     * @return The Location of the raytrace hit, null if the raytrace never hit anything
     */
    public static Location rayTraceLocation(LivingEntity entity, double maxDistance, FluidCollisionMode fluidCollisionMode, boolean ignorePassableBlocks)
    {
        World world = entity.getWorld();
        Location entityLoc = entity.getEyeLocation();
        Vector lookVec = entityLoc.getDirection();
        Vector resultVec = rayTraceVector(entity, maxDistance, fluidCollisionMode, ignorePassableBlocks);
        if(resultVec == null) return null;
        Location location = resultVec.toLocation(world);
        location.setDirection(lookVec);
        return location;
    }

    /**
     * Ray trace from a location to see if the direction of the location hits something
     * as a Location
     * <p>
     * Uses default max distance: 256 <br>
     * Uses default <code>FluidCollisionMode</code>: Never <br>
     * Uses default ignore passable blocks: false <br>
     *
     * @param location The location that will be raytraced
     * @return The Location of the raytrace hit, null if the raytrace never hit anything
     */
    public static Vector rayTraceVector(Location location)
    {
        return rayTraceVector(location, 256, FluidCollisionMode.NEVER, false);
    }

    /**
     * Ray trace from a location to see if the direction of the location hits something
     * as a Location
     * <p>
     * Uses default <code>FluidCollisionMode</code>: Never <br>
     * Uses default ignore passable blocks: false <br>
     *
     * @param location    The location that will be raytraced
     * @param maxDistance The maximum distance that should be raytraced before it stops trying
     * @return The Location of the raytrace hit, null if the raytrace never hit anything
     */
    public static Vector rayTraceVector(Location location, double maxDistance)
    {
        return rayTraceVector(location, maxDistance, FluidCollisionMode.NEVER, false);
    }

    /**
     * Ray trace from a location to see if the direction of the location hits something
     * as a Location
     * <p>
     * Uses default ignore passable blocks: false <br>
     *
     * @param location           The location that will be raytraced
     * @param maxDistance        The maximum distance that should be raytraced before it stops trying
     * @param fluidCollisionMode The fluid collision mode to use
     * @return The Location of the raytrace hit, null if the raytrace never hit anything
     */
    public static Vector rayTraceVector(Location location, double maxDistance, FluidCollisionMode fluidCollisionMode)
    {
        return rayTraceVector(location, maxDistance, fluidCollisionMode, false);
    }

    /**
     * Ray trace from a location to see if the direction of the location hits something
     * as a Location
     * <p>
     * Uses default <code>FluidCollisionMode</code>: Never <br>
     *
     * @param location             The location that will be raytraced
     * @param maxDistance          The maximum distance that should be raytraced before it stops trying
     * @param ignorePassableBlocks If the raytrace should ignore blocks that are passable (grass, flowers, etc)
     * @return The Location of the raytrace hit, null if the raytrace never hit anything
     */
    public static Vector rayTraceVector(Location location, double maxDistance, boolean ignorePassableBlocks)
    {
        return rayTraceVector(location, maxDistance, FluidCollisionMode.NEVER, ignorePassableBlocks);
    }

    /**
     * Ray trace from a location to see if the direction of the location hits something
     * as a Location
     *
     * @param location             The location that will be raytraced
     * @param maxDistance          The maximum distance that should be raytraced before it stops trying
     * @param fluidCollisionMode   The fluid collision mode to use
     * @param ignorePassableBlocks If the raytrace should ignore blocks that are passable (grass, flowers, etc)
     * @return The Location of the raytrace hit, null if the raytrace never hit anything
     */
    public static Vector rayTraceVector(Location location, double maxDistance, FluidCollisionMode fluidCollisionMode, boolean ignorePassableBlocks)
    {
        World world = location.getWorld();
        Vector lookVec = location.getDirection();
        RayTraceResult result = world.rayTrace(location, lookVec, maxDistance, fluidCollisionMode, ignorePassableBlocks, 0, null);
        if(result == null) return null;
        return result.getHitPosition();
    }

    /**
     * Ray trace from a location to see if the direction of the location hits something
     * as a Location
     * <p>
     * Uses default max distance: 256 <br>
     * Uses default <code>FluidCollisionMode</code>: Never <br>
     * Uses default ignore passable blocks: false <br>
     *
     * @param location The location that will be raytraced
     * @return The Location of the raytrace hit, null if the raytrace never hit anything
     */
    public static Location rayTraceLocation(Location location)
    {
        return rayTraceLocation(location, 256, FluidCollisionMode.NEVER, false);
    }

    /**
     * Ray trace from a location to see if the direction of the location hits something
     * as a Location
     * <p>
     * Uses default <code>FluidCollisionMode</code>: Never <br>
     * Uses default ignore passable blocks: false <br>
     *
     * @param location    The location that will be raytraced
     * @param maxDistance The maximum distance that should be raytraced before it stops trying
     * @return The Location of the raytrace hit, null if the raytrace never hit anything
     */
    public static Location rayTraceLocation(Location location, double maxDistance)
    {
        return rayTraceLocation(location, maxDistance, FluidCollisionMode.NEVER, false);
    }

    /**
     * Ray trace from a location to see if the direction of the location hits something
     * as a Location
     * <p>
     * Uses default ignore passable blocks: false <br>
     *
     * @param location           The location that will be raytraced
     * @param maxDistance        The maximum distance that should be raytraced before it stops trying
     * @param fluidCollisionMode The fluid collision mode to use
     * @return The Location of the raytrace hit, null if the raytrace never hit anything
     */
    public static Location rayTraceLocation(Location location, double maxDistance, FluidCollisionMode fluidCollisionMode)
    {
        return rayTraceLocation(location, maxDistance, fluidCollisionMode, false);
    }

    /**
     * Ray trace from a location to see if the direction of the location hits something
     * as a Location
     * <p>
     * Uses default <code>FluidCollisionMode</code>: Never <br>
     *
     * @param location             The location that will be raytraced
     * @param maxDistance          The maximum distance that should be raytraced before it stops trying
     * @param ignorePassableBlocks If the raytrace should ignore blocks that are passable (grass, flowers, etc)
     * @return The Location of the raytrace hit, null if the raytrace never hit anything
     */
    public static Location rayTraceLocation(Location location, double maxDistance, boolean ignorePassableBlocks)
    {
        return rayTraceLocation(location, maxDistance, FluidCollisionMode.NEVER, ignorePassableBlocks);
    }

    /**
     * Ray trace from a location to see if the direction of the location hits something
     * as a Location
     *
     * @param location             The location that will be raytraced
     * @param maxDistance          The maximum distance that should be raytraced before it stops trying
     * @param fluidCollisionMode   The fluid collision mode to use
     * @param ignorePassableBlocks If the raytrace should ignore blocks that are passable (grass, flowers, etc)
     * @return The Location of the raytrace hit, null if the raytrace never hit anything
     */
    public static Location rayTraceLocation(Location location, double maxDistance, FluidCollisionMode fluidCollisionMode, boolean ignorePassableBlocks)
    {
        World world = location.getWorld();
        Vector lookVec = location.getDirection();
        Vector resultVec = rayTraceVector(location, maxDistance, fluidCollisionMode, ignorePassableBlocks);
        if(resultVec == null) return null;
        Location newLoc = resultVec.toLocation(world);
        newLoc.setDirection(lookVec);
        return newLoc;
    }

    /**
     * Ray trace from a location to see if the direction of the location hits something
     * as a Location
     * <p>
     * Uses default max distance: 256 <br>
     * Uses default <code>FluidCollisionMode</code>: Never <br>
     * Uses default ignore passable blocks: false <br>
     *
     * @param location The location that will be raytraced
     * @param filter   The entity filter that should be used when raytracing
     * @return The Location of the raytrace hit, null if the raytrace never hit anything
     */
    public static Vector rayTraceVector(Location location, Predicate<Entity> filter)
    {
        return rayTraceVector(location, 256, FluidCollisionMode.NEVER, false, filter);
    }

    /**
     * Ray trace from a location to see if the direction of the location hits something
     * as a Location
     * <p>
     * Uses default <code>FluidCollisionMode</code>: Never <br>
     * Uses default ignore passable blocks: false <br>
     *
     * @param location    The location that will be raytraced
     * @param maxDistance The maximum distance that should be raytraced before it stops trying
     * @param filter      The entity filter that should be used when raytracing
     * @return The Location of the raytrace hit, null if the raytrace never hit anything
     */
    public static Vector rayTraceVector(Location location, double maxDistance, Predicate<Entity> filter)
    {
        return rayTraceVector(location, maxDistance, FluidCollisionMode.NEVER, false, filter);
    }

    /**
     * Ray trace from a location to see if the direction of the location hits something
     * as a Location
     * <p>
     * Uses default ignore passable blocks: false <br>
     *
     * @param location           The location that will be raytraced
     * @param maxDistance        The maximum distance that should be raytraced before it stops trying
     * @param fluidCollisionMode The fluid collision mode to use
     * @param filter             The entity filter that should be used when raytracing
     * @return The Location of the raytrace hit, null if the raytrace never hit anything
     */
    public static Vector rayTraceVector(Location location, double maxDistance, FluidCollisionMode fluidCollisionMode, Predicate<Entity> filter)
    {
        return rayTraceVector(location, maxDistance, fluidCollisionMode, false, filter);
    }

    /**
     * Ray trace from a location to see if the direction of the location hits something
     * as a Location
     * <p>
     * Uses default <code>FluidCollisionMode</code>: Never <br>
     *
     * @param location             The location that will be raytraced
     * @param maxDistance          The maximum distance that should be raytraced before it stops trying
     * @param ignorePassableBlocks If the raytrace should ignore blocks that are passable (grass, flowers, etc)
     * @param filter               The entity filter that should be used when raytracing
     * @return The Location of the raytrace hit, null if the raytrace never hit anything
     */
    public static Vector rayTraceVector(Location location, double maxDistance, boolean ignorePassableBlocks, Predicate<Entity> filter)
    {
        return rayTraceVector(location, maxDistance, FluidCollisionMode.NEVER, ignorePassableBlocks, filter);
    }

    /**
     * Ray trace from a location to see if the direction of the location hits something
     * as a Location
     *
     * @param location The location that will be raytraced
     * @param maxDistance The maximum distance that should be raytraced before it stops trying
     * @param fluidCollisionMode The fluid collision mode to use
     * @param ignorePassableBlocks If the raytrace should ignore blocks that are passable (grass, flowers, etc)
     * @param filter The entity filter that should be used when raytracing
     * @return The Location of the raytrace hit, null if the raytrace never hit anything
     */
    public static Vector rayTraceVector(Location location, double maxDistance, FluidCollisionMode fluidCollisionMode, boolean ignorePassableBlocks, Predicate<Entity> filter)
    {
        World world = location.getWorld();
        Vector lookVec = location.getDirection();
        RayTraceResult result = world.rayTrace(location, lookVec, maxDistance, fluidCollisionMode, ignorePassableBlocks, 0, filter);
        if(result == null) return null;
        return result.getHitPosition();
    }

    /**
     * Ray trace from a location to see if the direction of the location hits something
     * as a Location
     * <p>
     * Uses default max distance: 256 <br>
     * Uses default <code>FluidCollisionMode</code>: Never <br>
     * Uses default ignore passable blocks: false <br>
     *
     * @param location The location that will be raytraced
     * @param filter   The entity filter that should be used when raytracing
     * @return The Location of the raytrace hit, null if the raytrace never hit anything
     */
    public static Location rayTraceLocation(Location location, Predicate<Entity> filter)
    {
        return rayTraceLocation(location, 256, FluidCollisionMode.NEVER, false, filter);
    }

    /**
     * Ray trace from a location to see if the direction of the location hits something
     * as a Location
     * <p>
     * Uses default <code>FluidCollisionMode</code>: Never <br>
     * Uses default ignore passable blocks: false <br>
     *
     * @param location    The location that will be raytraced
     * @param maxDistance The maximum distance that should be raytraced before it stops trying
     * @param filter      The entity filter that should be used when raytracing
     * @return The Location of the raytrace hit, null if the raytrace never hit anything
     */
    public static Location rayTraceLocation(Location location, double maxDistance, Predicate<Entity> filter)
    {
        return rayTraceLocation(location, maxDistance, FluidCollisionMode.NEVER, false, filter);
    }

    /**
     * Ray trace from a location to see if the direction of the location hits something
     * as a Location
     * <p>
     * Uses default ignore passable blocks: false <br>
     *
     * @param location           The location that will be raytraced
     * @param maxDistance        The maximum distance that should be raytraced before it stops trying
     * @param fluidCollisionMode The fluid collision mode to use
     * @param filter             The entity filter that should be used when raytracing
     * @return The Location of the raytrace hit, null if the raytrace never hit anything
     */
    public static Location rayTraceLocation(Location location, double maxDistance, FluidCollisionMode fluidCollisionMode, Predicate<Entity> filter)
    {
        return rayTraceLocation(location, maxDistance, fluidCollisionMode, false, filter);
    }

    /**
     * Ray trace from a location to see if the direction of the location hits something
     * as a Location
     * <p>
     * Uses default <code>FluidCollisionMode</code>: Never <br>
     *
     * @param location             The location that will be raytraced
     * @param maxDistance          The maximum distance that should be raytraced before it stops trying
     * @param ignorePassableBlocks If the raytrace should ignore blocks that are passable (grass, flowers, etc)
     * @param filter               The entity filter that should be used when raytracing
     * @return The Location of the raytrace hit, null if the raytrace never hit anything
     */
    public static Location rayTraceLocation(Location location, double maxDistance, boolean ignorePassableBlocks, Predicate<Entity> filter)
    {
        return rayTraceLocation(location, maxDistance, FluidCollisionMode.NEVER, ignorePassableBlocks, filter);
    }

    /**
     * Ray trace from a location to see if the direction of the location hits something
     * as a Location
     *
     * @param location             The location that will be raytraced
     * @param maxDistance          The maximum distance that should be raytraced before it stops trying
     * @param fluidCollisionMode   The fluid collision mode to use
     * @param ignorePassableBlocks If the raytrace should ignore blocks that are passable (grass, flowers, etc)
     * @param filter               The entity filter that should be used when raytracing
     * @return The Location of the raytrace hit, null if the raytrace never hit anything
     */
    public static Location rayTraceLocation(Location location, double maxDistance, FluidCollisionMode fluidCollisionMode, boolean ignorePassableBlocks, Predicate<Entity> filter)
    {
        World world = location.getWorld();
        Vector lookVec = location.getDirection();
        Vector resultVec = rayTraceVector(location, maxDistance, fluidCollisionMode, ignorePassableBlocks, filter);
        if(resultVec == null) return null;
        Location newLoc = resultVec.toLocation(world);
        newLoc.setDirection(lookVec);
        return newLoc;
    }
}
