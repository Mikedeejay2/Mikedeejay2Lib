package com.mikedeejay2.mikedeejay2lib.util;

import org.bukkit.FluidCollisionMode;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.LivingEntity;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.Vector;

/**
 * A class for dealing with getting locations that an entity is looking at.
 *
 * @author Mikedeejay2
 */
public final class LookUtil
{
    /**
     * Ray trace from an entity's point of view to what they are looking and get that position
     * as a vector
     * <br>
     * Uses default max distance: 256
     * Uses default <tt>FluidCollisionMode</tt>: Never
     * Uses default ignore passable blocks: false
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
     * <br>
     * Uses default <tt>FluidCollisionMode</tt>: Never
     * Uses default ignore passable blocks: false
     *
     * @param entity Entity's view that will be raytraced
     * @param maxDistance The maximum distance that should be raytraced before it stops trying
     * @return The Vector of the raytrace hit, null if the raytrace never hit anything
     */
    public static Vector rayTraceVector(LivingEntity entity, int maxDistance)
    {
        return rayTraceVector(entity, maxDistance, FluidCollisionMode.NEVER, false);
    }

    /**
     * Ray trace from an entity's point of view to what they are looking and get that position
     * as a vector
     * <br>
     * Uses default <tt>FluidCollisionMode</tt>: Never
     *
     * @param entity Entity's view that will be raytraced
     * @param maxDistance The maximum distance that should be raytraced before it stops trying
     * @param ignorePassableBlocks If the raytrace should ignore blocks that are passable (grass, flowers, etc)
     * @return The Vector of the raytrace hit, null if the raytrace never hit anything
     */
    public static Vector rayTraceVector(LivingEntity entity, int maxDistance, boolean ignorePassableBlocks)
    {
        return rayTraceVector(entity, maxDistance, FluidCollisionMode.NEVER, ignorePassableBlocks);
    }

    /**
     * Ray trace from an entity's point of view to what they are looking and get that position
     * as a vector
     * <br>
     * Uses default ignore passable blocks: false
     *
     * @param entity Entity's view that will be raytraced
     * @param maxDistance The maximum distance that should be raytraced before it stops trying
     * @param fluidCollisionMode The fluid collision mode to use
     * @return The Vector of the raytrace hit, null if the raytrace never hit anything
     */
    public static Vector rayTraceVector(LivingEntity entity, int maxDistance, FluidCollisionMode fluidCollisionMode)
    {
        return rayTraceVector(entity, maxDistance, fluidCollisionMode, false);
    }

    /**
     * Ray trace from an entity's point of view to what they are looking and get that position
     * as a vector
     *
     * @param entity Entity's view that will be raytraced
     * @param maxDistance The maximum distance that should be raytraced before it stops trying
     * @param fluidCollisionMode The fluid collision mode to use
     * @param ignorePassableBlocks If the raytrace should ignore blocks that are passable (grass, flowers, etc)
     * @return The Vector of the raytrace hit, null if the raytrace never hit anything
     */
    public static Vector rayTraceVector(LivingEntity entity, int maxDistance, FluidCollisionMode fluidCollisionMode, boolean ignorePassableBlocks)
    {
        World world = entity.getWorld();
        Location startLoc = entity.getEyeLocation();
        Vector lookVec = startLoc.getDirection();
        RayTraceResult result = world.rayTrace(startLoc, lookVec, maxDistance, fluidCollisionMode, ignorePassableBlocks, 0, null);
        if(result == null) return null;
        return result.getHitPosition();
    }

    /**
     * Ray trace from an entity's point of view to what they are looking and get that position
     * as a location
     * <br>
     * Uses default max distance: 256
     * Uses default <tt>FluidCollisionMode</tt>: Never
     * Uses default ignore passable blocks: false
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
     * <br>
     * Uses default <tt>FluidCollisionMode</tt>: Never
     * Uses default ignore passable blocks: false
     *
     * @param entity Entity's view that will be raytraced
     * @param maxDistance The maximum distance that should be raytraced before it stops trying
     * @return The Location of the raytrace hit, null if the raytrace never hit anything
     */
    public static Location rayTraceLocation(LivingEntity entity, int maxDistance)
    {
        return rayTraceLocation(entity, maxDistance, FluidCollisionMode.NEVER, false);
    }

    /**
     * Ray trace from an entity's point of view to what they are looking and get that position
     * as a Location
     * <br>
     * Uses default <tt>FluidCollisionMode</tt>: Never
     *
     * @param entity Entity's view that will be raytraced
     * @param maxDistance The maximum distance that should be raytraced before it stops trying
     * @param ignorePassableBlocks If the raytrace should ignore blocks that are passable (grass, flowers, etc)
     * @return The Location of the raytrace hit, null if the raytrace never hit anything
     */
    public static Location rayTraceLocation(LivingEntity entity, int maxDistance, boolean ignorePassableBlocks)
    {
        return rayTraceLocation(entity, maxDistance, FluidCollisionMode.NEVER, ignorePassableBlocks);
    }

    /**
     * Ray trace from an entity's point of view to what they are looking and get that position
     * as a Location
     * <br>
     * Uses default ignore passable blocks: false
     *
     * @param entity Entity's view that will be raytraced
     * @param maxDistance The maximum distance that should be raytraced before it stops trying
     * @param fluidCollisionMode The fluid collision mode to use
     * @return The Location of the raytrace hit, null if the raytrace never hit anything
     */
    public static Location rayTraceLocation(LivingEntity entity, int maxDistance, FluidCollisionMode fluidCollisionMode)
    {
        return rayTraceLocation(entity, maxDistance, fluidCollisionMode, false);
    }

    /**
     * Ray trace from an entity's point of view to what they are looking and get that position
     * as a Location
     *
     * @param entity Entity's view that will be raytraced
     * @param maxDistance The maximum distance that should be raytraced before it stops trying
     * @param fluidCollisionMode The fluid collision mode to use
     * @param ignorePassableBlocks If the raytrace should ignore blocks that are passable (grass, flowers, etc)
     * @return The Location of the raytrace hit, null if the raytrace never hit anything
     */
    public static Location rayTraceLocation(LivingEntity entity, int maxDistance, FluidCollisionMode fluidCollisionMode, boolean ignorePassableBlocks)
    {
        World world = entity.getWorld();
        Location startLoc = entity.getEyeLocation();
        Vector lookVec = startLoc.getDirection();
        RayTraceResult result = world.rayTrace(startLoc, lookVec, maxDistance, fluidCollisionMode, ignorePassableBlocks, 0, null);
        if(result == null) return null;
        return result.getHitPosition().toLocation(world);
    }
}
