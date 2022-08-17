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
public final class RayTracer {
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
    public static Vector rayTraceVector(LivingEntity entity) {
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
    public static Vector rayTraceVector(LivingEntity entity, double maxDistance) {
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
    public static Vector rayTraceVector(LivingEntity entity, double maxDistance, boolean ignorePassableBlocks) {
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
    public static Vector rayTraceVector(LivingEntity entity, double maxDistance, FluidCollisionMode fluidCollisionMode) {
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
    public static Vector rayTraceVector(LivingEntity entity, double maxDistance, FluidCollisionMode fluidCollisionMode, boolean ignorePassableBlocks) {
        RayTraceResult result = rayTraceResult(entity, maxDistance, fluidCollisionMode, ignorePassableBlocks);
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
    public static Location rayTraceLocation(LivingEntity entity) {
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
    public static Location rayTraceLocation(LivingEntity entity, double maxDistance) {
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
    public static Location rayTraceLocation(LivingEntity entity, double maxDistance, boolean ignorePassableBlocks) {
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
    public static Location rayTraceLocation(LivingEntity entity, double maxDistance, FluidCollisionMode fluidCollisionMode) {
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
    public static Location rayTraceLocation(LivingEntity entity, double maxDistance, FluidCollisionMode fluidCollisionMode, boolean ignorePassableBlocks) {
        Vector vector = rayTraceVector(entity, maxDistance, fluidCollisionMode, ignorePassableBlocks);
        if(vector == null) return null;
        Vector direction = entity.getLocation().getDirection();
        return vector.toLocation(entity.getWorld()).setDirection(direction);
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
    public static Vector rayTraceVector(Location location) {
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
    public static Vector rayTraceVector(Location location, double maxDistance) {
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
    public static Vector rayTraceVector(Location location, double maxDistance, FluidCollisionMode fluidCollisionMode) {
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
    public static Vector rayTraceVector(Location location, double maxDistance, boolean ignorePassableBlocks) {
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
    public static Vector rayTraceVector(Location location, double maxDistance, FluidCollisionMode fluidCollisionMode, boolean ignorePassableBlocks) {
        RayTraceResult result = rayTraceResult(location, maxDistance, fluidCollisionMode, ignorePassableBlocks);
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
    public static Location rayTraceLocation(Location location) {
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
    public static Location rayTraceLocation(Location location, double maxDistance) {
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
    public static Location rayTraceLocation(Location location, double maxDistance, FluidCollisionMode fluidCollisionMode) {
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
    public static Location rayTraceLocation(Location location, double maxDistance, boolean ignorePassableBlocks) {
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
    public static Location rayTraceLocation(Location location, double maxDistance, FluidCollisionMode fluidCollisionMode, boolean ignorePassableBlocks) {
        Vector vector = rayTraceVector(location, maxDistance, fluidCollisionMode, ignorePassableBlocks);
        if(vector == null) return null;
        return vector.toLocation(location.getWorld()).setDirection(location.getDirection());
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
    public static Vector rayTraceVector(Location location, Predicate<Entity> filter) {
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
    public static Vector rayTraceVector(Location location, double maxDistance, Predicate<Entity> filter) {
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
    public static Vector rayTraceVector(Location location, double maxDistance, FluidCollisionMode fluidCollisionMode, Predicate<Entity> filter) {
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
    public static Vector rayTraceVector(Location location, double maxDistance, boolean ignorePassableBlocks, Predicate<Entity> filter) {
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
    public static Vector rayTraceVector(Location location, double maxDistance, FluidCollisionMode fluidCollisionMode, boolean ignorePassableBlocks, Predicate<Entity> filter) {
        RayTraceResult result = rayTraceResult(location, maxDistance, fluidCollisionMode, ignorePassableBlocks, filter);
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
    public static Location rayTraceLocation(Location location, Predicate<Entity> filter) {
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
    public static Location rayTraceLocation(Location location, double maxDistance, Predicate<Entity> filter) {
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
    public static Location rayTraceLocation(Location location, double maxDistance, FluidCollisionMode fluidCollisionMode, Predicate<Entity> filter) {
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
    public static Location rayTraceLocation(Location location, double maxDistance, boolean ignorePassableBlocks, Predicate<Entity> filter) {
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
    public static Location rayTraceLocation(Location location, double maxDistance, FluidCollisionMode fluidCollisionMode, boolean ignorePassableBlocks, Predicate<Entity> filter) {
        Vector resultVec = rayTraceVector(location, maxDistance, fluidCollisionMode, ignorePassableBlocks, filter);
        if(resultVec == null) return null;
        return resultVec.toLocation(location.getWorld()).setDirection(location.getDirection());
    }

    /**
     * Ray trace from an entity's point of view to what they are looking and get that position
     * as an <code>Entity</code>
     * <p>
     * Uses default max distance: 256 <br>
     * Uses default <code>FluidCollisionMode</code>: Never <br>
     * Uses default ignore passable blocks: false <br>
     *
     * @param entity Entity's view that will be raytraced
     * @return The <code>Entity</code>, <code>null</code> if no entity was hit
     */
    public static Entity rayTraceEntity(LivingEntity entity) {
        return rayTraceEntity(entity, 256, FluidCollisionMode.NEVER, false);
    }

    /**
     * Ray trace from an entity's point of view to what <code>Entity</code> they are looking at
     * <p>
     * Uses default <code>FluidCollisionMode</code>: Never <br>
     * Uses default ignore passable blocks: false <br>
     *
     * @param entity      Entity's view that will be raytraced
     * @param maxDistance The maximum distance that should be raytraced before it stops trying
     * @return The <code>Entity</code>, <code>null</code> if no entity was hit
     */
    public static Entity rayTraceEntity(LivingEntity entity, double maxDistance) {
        return rayTraceEntity(entity, maxDistance, FluidCollisionMode.NEVER, false);
    }

    /**
     * Ray trace from an entity's point of view to what <code>Entity</code> they are looking at
     * <p>
     * Uses default <code>FluidCollisionMode</code>: Never <br>
     *
     * @param entity               Entity's view that will be raytraced
     * @param maxDistance          The maximum distance that should be raytraced before it stops trying
     * @param ignorePassableBlocks If the raytrace should ignore blocks that are passable (grass, flowers, etc)
     * @return The <code>Entity</code>, <code>null</code> if no entity was hit
     */
    public static Entity rayTraceEntity(LivingEntity entity, double maxDistance, boolean ignorePassableBlocks) {
        return rayTraceEntity(entity, maxDistance, FluidCollisionMode.NEVER, ignorePassableBlocks);
    }

    /**
     * Ray trace from an entity's point of view to what <code>Entity</code> they are looking at
     * <p>
     * Uses default ignore passable blocks: false <br>
     *
     * @param entity             Entity's view that will be raytraced
     * @param maxDistance        The maximum distance that should be raytraced before it stops trying
     * @param fluidCollisionMode The fluid collision mode to use
     * @return The <code>Entity</code>, <code>null</code> if no entity was hit
     */
    public static Entity rayTraceEntity(LivingEntity entity, double maxDistance, FluidCollisionMode fluidCollisionMode) {
        return rayTraceEntity(entity, maxDistance, fluidCollisionMode, false);
    }

    /**
     * Ray trace from an entity's point of view to what <code>Entity</code> they are looking at
     *
     * @param entity               Entity's view that will be raytraced
     * @param maxDistance          The maximum distance that should be raytraced before it stops trying
     * @param fluidCollisionMode   The fluid collision mode to use
     * @param ignorePassableBlocks If the raytrace should ignore blocks that are passable (grass, flowers, etc)
     * @return The <code>Entity</code>, <code>null</code> if no entity was hit
     */
    public static Entity rayTraceEntity(LivingEntity entity, double maxDistance, FluidCollisionMode fluidCollisionMode, boolean ignorePassableBlocks) {
        RayTraceResult result = rayTraceResult(entity, maxDistance, fluidCollisionMode, ignorePassableBlocks);
        if(result == null) return null;
        return result.getHitEntity();
    }

    /**
     * Ray trace from a location to get what <code>Entity</code> they are looking at
     * <p>
     * Uses default max distance: 256 <br>
     * Uses default <code>FluidCollisionMode</code>: Never <br>
     * Uses default ignore passable blocks: false <br>
     *
     * @param location The location that will be raytraced
     * @return The <code>Entity</code>, <code>null</code> if no entity was hit
     */
    public static Entity rayTraceEntity(Location location) {
        return rayTraceEntity(location, 256, FluidCollisionMode.NEVER, false);
    }

    /**
     * Ray trace from a location to get what <code>Entity</code> they are looking at
     * <p>
     * Uses default <code>FluidCollisionMode</code>: Never <br>
     * Uses default ignore passable blocks: false <br>
     *
     * @param location    The location that will be raytraced
     * @param maxDistance The maximum distance that should be raytraced before it stops trying
     * @return The <code>Entity</code>, <code>null</code> if no entity was hit
     */
    public static Entity rayTraceEntity(Location location, double maxDistance) {
        return rayTraceEntity(location, maxDistance, FluidCollisionMode.NEVER, false);
    }

    /**
     * Ray trace from a location to get what <code>Entity</code> they are looking at
     * <p>
     * Uses default ignore passable blocks: false <br>
     *
     * @param location           The location that will be raytraced
     * @param maxDistance        The maximum distance that should be raytraced before it stops trying
     * @param fluidCollisionMode The fluid collision mode to use
     * @return The <code>Entity</code>, <code>null</code> if no entity was hit
     */
    public static Entity rayTraceEntity(Location location, double maxDistance, FluidCollisionMode fluidCollisionMode) {
        return rayTraceEntity(location, maxDistance, fluidCollisionMode, false);
    }

    /**
     * Ray trace from a location to get what <code>Entity</code> they are looking at
     * <p>
     * Uses default <code>FluidCollisionMode</code>: Never <br>
     *
     * @param location             The location that will be raytraced
     * @param maxDistance          The maximum distance that should be raytraced before it stops trying
     * @param ignorePassableBlocks If the raytrace should ignore blocks that are passable (grass, flowers, etc)
     * @return The <code>Entity</code>, <code>null</code> if no entity was hit
     */
    public static Entity rayTraceEntity(Location location, double maxDistance, boolean ignorePassableBlocks) {
        return rayTraceEntity(location, maxDistance, FluidCollisionMode.NEVER, ignorePassableBlocks);
    }

    /**
     * Ray trace from a location to get what <code>Entity</code> they are looking at
     *
     * @param location             The location that will be raytraced
     * @param maxDistance          The maximum distance that should be raytraced before it stops trying
     * @param fluidCollisionMode   The fluid collision mode to use
     * @param ignorePassableBlocks If the raytrace should ignore blocks that are passable (grass, flowers, etc)
     * @return The <code>Entity</code>, <code>null</code> if no entity was hit
     */
    public static Entity rayTraceEntity(Location location, double maxDistance, FluidCollisionMode fluidCollisionMode, boolean ignorePassableBlocks) {
        RayTraceResult result = rayTraceResult(location, maxDistance, fluidCollisionMode, ignorePassableBlocks);
        if(result == null) return null;
        return result.getHitEntity();
    }

    /**
     * Ray trace from a location to get what <code>Entity</code> they are looking at
     * <p>
     * Uses default max distance: 256 <br>
     * Uses default <code>FluidCollisionMode</code>: Never <br>
     * Uses default ignore passable blocks: false <br>
     *
     * @param location The location that will be raytraced
     * @param filter   The entity filter that should be used when raytracing
     * @return The <code>Entity</code>, <code>null</code> if no entity was hit
     */
    public static Entity rayTraceEntity(Location location, Predicate<Entity> filter) {
        return rayTraceEntity(location, 256, FluidCollisionMode.NEVER, false, filter);
    }

    /**
     * Ray trace from a location to get what <code>Entity</code> they are looking at
     * <p>
     * Uses default <code>FluidCollisionMode</code>: Never <br>
     * Uses default ignore passable blocks: false <br>
     *
     * @param location    The location that will be raytraced
     * @param maxDistance The maximum distance that should be raytraced before it stops trying
     * @param filter      The entity filter that should be used when raytracing
     * @return The <code>Entity</code>, <code>null</code> if no entity was hit
     */
    public static Entity rayTraceEntity(Location location, double maxDistance, Predicate<Entity> filter) {
        return rayTraceEntity(location, maxDistance, FluidCollisionMode.NEVER, false, filter);
    }

    /**
     * Ray trace from a location to get what <code>Entity</code> they are looking at
     * <p>
     * Uses default ignore passable blocks: false <br>
     *
     * @param location           The location that will be raytraced
     * @param maxDistance        The maximum distance that should be raytraced before it stops trying
     * @param fluidCollisionMode The fluid collision mode to use
     * @param filter             The entity filter that should be used when raytracing
     * @return The <code>Entity</code>, <code>null</code> if no entity was hit
     */
    public static Entity rayTraceEntity(Location location, double maxDistance, FluidCollisionMode fluidCollisionMode, Predicate<Entity> filter) {
        return rayTraceEntity(location, maxDistance, fluidCollisionMode, false, filter);
    }

    /**
     * Ray trace from a location to get what <code>Entity</code> they are looking at
     * <p>
     * Uses default <code>FluidCollisionMode</code>: Never <br>
     *
     * @param location             The location that will be raytraced
     * @param maxDistance          The maximum distance that should be raytraced before it stops trying
     * @param ignorePassableBlocks If the raytrace should ignore blocks that are passable (grass, flowers, etc)
     * @param filter               The entity filter that should be used when raytracing
     * @return The <code>Entity</code>, <code>null</code> if no entity was hit
     */
    public static Entity rayTraceEntity(Location location, double maxDistance, boolean ignorePassableBlocks, Predicate<Entity> filter) {
        return rayTraceEntity(location, maxDistance, FluidCollisionMode.NEVER, ignorePassableBlocks, filter);
    }

    /**
     * Ray trace from a location to get what <code>Entity</code> they are looking at
     *
     * @param location The location that will be raytraced
     * @param maxDistance The maximum distance that should be raytraced before it stops trying
     * @param fluidCollisionMode The fluid collision mode to use
     * @param ignorePassableBlocks If the raytrace should ignore blocks that are passable (grass, flowers, etc)
     * @param filter The entity filter that should be used when raytracing
     * @return The <code>Entity</code>, <code>null</code> if no entity was hit
     */
    public static Entity rayTraceEntity(Location location, double maxDistance, FluidCollisionMode fluidCollisionMode, boolean ignorePassableBlocks, Predicate<Entity> filter) {
        RayTraceResult result = rayTraceResult(location, maxDistance, fluidCollisionMode, ignorePassableBlocks, filter);
        if(result == null) return null;
        return result.getHitEntity();
    }

    /**
     * Ray trace from an entity's point of view to what they are looking and get that position
     * as a {@link RayTraceResult}
     * <p>
     * Uses default max distance: 256 <br>
     * Uses default <code>FluidCollisionMode</code>: Never <br>
     * Uses default ignore passable blocks: false <br>
     *
     * @param entity Entity's view that will be raytraced
     * @return The {@link RayTraceResult}, <code>null</code> if nothing has been hit
     */
    public static RayTraceResult rayTraceResult(LivingEntity entity) {
        return rayTraceResult(entity, 256, FluidCollisionMode.NEVER, false);
    }

    /**
     * Ray trace from an entity's point of view to what they are looking and get that position
     * as a {@link RayTraceResult}
     * <p>
     * Uses default <code>FluidCollisionMode</code>: Never <br>
     * Uses default ignore passable blocks: false <br>
     *
     * @param entity      Entity's view that will be raytraced
     * @param maxDistance The maximum distance that should be raytraced before it stops trying
     * @return The {@link RayTraceResult}, <code>null</code> if nothing has been hit
     */
    public static RayTraceResult rayTraceResult(LivingEntity entity, double maxDistance) {
        return rayTraceResult(entity, maxDistance, FluidCollisionMode.NEVER, false);
    }

    /**
     * Ray trace from an entity's point of view to what they are looking and get that position
     * as a {@link RayTraceResult}
     * <p>
     * Uses default <code>FluidCollisionMode</code>: Never <br>
     *
     * @param entity               Entity's view that will be raytraced
     * @param maxDistance          The maximum distance that should be raytraced before it stops trying
     * @param ignorePassableBlocks If the raytrace should ignore blocks that are passable (grass, flowers, etc)
     * @return The {@link RayTraceResult}, <code>null</code> if nothing has been hit
     */
    public static RayTraceResult rayTraceResult(LivingEntity entity, double maxDistance, boolean ignorePassableBlocks) {
        return rayTraceResult(entity, maxDistance, FluidCollisionMode.NEVER, ignorePassableBlocks);
    }

    /**
     * Ray trace from an entity's point of view to what they are looking and get that position
     * as a {@link RayTraceResult}
     * <p>
     * Uses default ignore passable blocks: false <br>
     *
     * @param entity             Entity's view that will be raytraced
     * @param maxDistance        The maximum distance that should be raytraced before it stops trying
     * @param fluidCollisionMode The fluid collision mode to use
     * @return The {@link RayTraceResult}, <code>null</code> if nothing has been hit
     */
    public static RayTraceResult rayTraceResult(LivingEntity entity, double maxDistance, FluidCollisionMode fluidCollisionMode) {
        return rayTraceResult(entity, maxDistance, fluidCollisionMode, false);
    }

    /**
     * Ray trace from an entity's point of view to what they are looking and get that position
     * as a {@link RayTraceResult}
     *
     * @param entity               Entity's view that will be raytraced
     * @param maxDistance          The maximum distance that should be raytraced before it stops trying
     * @param fluidCollisionMode   The fluid collision mode to use
     * @param ignorePassableBlocks If the raytrace should ignore blocks that are passable (grass, flowers, etc)
     * @return The {@link RayTraceResult}, <code>null</code> if nothing has been hit
     */
    public static RayTraceResult rayTraceResult(LivingEntity entity, double maxDistance, FluidCollisionMode fluidCollisionMode, boolean ignorePassableBlocks) {
        World world = entity.getWorld();
        Location startLoc = entity.getEyeLocation();
        Vector lookVec = startLoc.getDirection();
        Predicate<Entity> entities = entity1 -> (entity1 != entity);
        return world.rayTrace(startLoc, lookVec, maxDistance, fluidCollisionMode, ignorePassableBlocks, 0, entities);
    }

    /**
     * Ray trace from a location to see if the direction of the location hits something
     * as a {@link RayTraceResult}
     * <p>
     * Uses default max distance: 256 <br>
     * Uses default <code>FluidCollisionMode</code>: Never <br>
     * Uses default ignore passable blocks: false <br>
     *
     * @param location The location that will be raytraced
     * @return The {@link RayTraceResult}, <code>null</code> if nothing has been hit
     */
    public static RayTraceResult rayTraceResult(Location location) {
        return rayTraceResult(location, 256, FluidCollisionMode.NEVER, false);
    }

    /**
     * Ray trace from a location to see if the direction of the location hits something
     * as a {@link RayTraceResult}
     * <p>
     * Uses default <code>FluidCollisionMode</code>: Never <br>
     * Uses default ignore passable blocks: false <br>
     *
     * @param location    The location that will be raytraced
     * @param maxDistance The maximum distance that should be raytraced before it stops trying
     * @return The {@link RayTraceResult}, <code>null</code> if nothing has been hit
     */
    public static RayTraceResult rayTraceResult(Location location, double maxDistance) {
        return rayTraceResult(location, maxDistance, FluidCollisionMode.NEVER, false);
    }

    /**
     * Ray trace from a location to see if the direction of the location hits something
     * as a {@link RayTraceResult}
     * <p>
     * Uses default ignore passable blocks: false <br>
     *
     * @param location           The location that will be raytraced
     * @param maxDistance        The maximum distance that should be raytraced before it stops trying
     * @param fluidCollisionMode The fluid collision mode to use
     * @return The {@link RayTraceResult}, <code>null</code> if nothing has been hit
     */
    public static RayTraceResult rayTraceResult(Location location, double maxDistance, FluidCollisionMode fluidCollisionMode) {
        return rayTraceResult(location, maxDistance, fluidCollisionMode, false);
    }

    /**
     * Ray trace from a location to see if the direction of the location hits something
     * as a {@link RayTraceResult}
     * <p>
     * Uses default <code>FluidCollisionMode</code>: Never <br>
     *
     * @param location             The location that will be raytraced
     * @param maxDistance          The maximum distance that should be raytraced before it stops trying
     * @param ignorePassableBlocks If the raytrace should ignore blocks that are passable (grass, flowers, etc)
     * @return The {@link RayTraceResult}, <code>null</code> if nothing has been hit
     */
    public static RayTraceResult rayTraceResult(Location location, double maxDistance, boolean ignorePassableBlocks) {
        return rayTraceResult(location, maxDistance, FluidCollisionMode.NEVER, ignorePassableBlocks);
    }

    /**
     * Ray trace from a location to see if the direction of the location hits something
     * as a {@link RayTraceResult}
     *
     * @param location             The location that will be raytraced
     * @param maxDistance          The maximum distance that should be raytraced before it stops trying
     * @param fluidCollisionMode   The fluid collision mode to use
     * @param ignorePassableBlocks If the raytrace should ignore blocks that are passable (grass, flowers, etc)
     * @return The {@link RayTraceResult}, <code>null</code> if nothing has been hit
     */
    public static RayTraceResult rayTraceResult(Location location, double maxDistance, FluidCollisionMode fluidCollisionMode, boolean ignorePassableBlocks) {
        World world = location.getWorld();
        Vector lookVec = location.getDirection();
        return world.rayTrace(location, lookVec, maxDistance, fluidCollisionMode, ignorePassableBlocks, 0, null);
    }

    /**
     * Ray trace from a location to see if the direction of the location hits something
     * as a {@link RayTraceResult}
     * <p>
     * Uses default max distance: 256 <br>
     * Uses default <code>FluidCollisionMode</code>: Never <br>
     * Uses default ignore passable blocks: false <br>
     *
     * @param location The location that will be raytraced
     * @param filter   The entity filter that should be used when raytracing
     * @return The {@link RayTraceResult}, <code>null</code> if nothing has been hit
     */
    public static RayTraceResult rayTraceResult(Location location, Predicate<Entity> filter) {
        return rayTraceResult(location, 256, FluidCollisionMode.NEVER, false, filter);
    }

    /**
     * Ray trace from a location to see if the direction of the location hits something
     * as a {@link RayTraceResult}
     * <p>
     * Uses default <code>FluidCollisionMode</code>: Never <br>
     * Uses default ignore passable blocks: false <br>
     *
     * @param location    The location that will be raytraced
     * @param maxDistance The maximum distance that should be raytraced before it stops trying
     * @param filter      The entity filter that should be used when raytracing
     * @return The {@link RayTraceResult}, <code>null</code> if nothing has been hit
     */
    public static RayTraceResult rayTraceResult(Location location, double maxDistance, Predicate<Entity> filter) {
        return rayTraceResult(location, maxDistance, FluidCollisionMode.NEVER, false, filter);
    }

    /**
     * Ray trace from a location to see if the direction of the location hits something
     * as a {@link RayTraceResult}
     * <p>
     * Uses default ignore passable blocks: false <br>
     *
     * @param location           The location that will be raytraced
     * @param maxDistance        The maximum distance that should be raytraced before it stops trying
     * @param fluidCollisionMode The fluid collision mode to use
     * @param filter             The entity filter that should be used when raytracing
     * @return The {@link RayTraceResult}, <code>null</code> if nothing has been hit
     */
    public static RayTraceResult rayTraceResult(Location location, double maxDistance, FluidCollisionMode fluidCollisionMode, Predicate<Entity> filter) {
        return rayTraceResult(location, maxDistance, fluidCollisionMode, false, filter);
    }

    /**
     * Ray trace from a location to see if the direction of the location hits something
     * as a {@link RayTraceResult}
     * <p>
     * Uses default <code>FluidCollisionMode</code>: Never <br>
     *
     * @param location             The location that will be raytraced
     * @param maxDistance          The maximum distance that should be raytraced before it stops trying
     * @param ignorePassableBlocks If the raytrace should ignore blocks that are passable (grass, flowers, etc)
     * @param filter               The entity filter that should be used when raytracing
     * @return The {@link RayTraceResult}, <code>null</code> if nothing has been hit
     */
    public static RayTraceResult rayTraceResult(Location location, double maxDistance, boolean ignorePassableBlocks, Predicate<Entity> filter) {
        return rayTraceResult(location, maxDistance, FluidCollisionMode.NEVER, ignorePassableBlocks, filter);
    }

    /**
     * Ray trace from a location to see if the direction of the location hits something
     * as a {@link RayTraceResult}
     *
     * @param location The location that will be raytraced
     * @param maxDistance The maximum distance that should be raytraced before it stops trying
     * @param fluidCollisionMode The fluid collision mode to use
     * @param ignorePassableBlocks If the raytrace should ignore blocks that are passable (grass, flowers, etc)
     * @param filter The entity filter that should be used when raytracing
     * @return The {@link RayTraceResult}, <code>null</code> if nothing has been hit
     */
    public static RayTraceResult rayTraceResult(Location location, double maxDistance, FluidCollisionMode fluidCollisionMode, boolean ignorePassableBlocks, Predicate<Entity> filter) {
        World world = location.getWorld();
        Vector lookVec = location.getDirection();
        return world.rayTrace(location, lookVec, maxDistance, fluidCollisionMode, ignorePassableBlocks, 0, filter);
    }

    /**
     * Step a Vector forward based off of the location's direction vector
     *
     * @param location The location that will be stepped
     * @param distance The distance that the location should be moved forward
     * @return The Vector of the new position
     */
    public static Vector stepVector(Location location, double distance) {
        Vector direction = location.getDirection();
        direction.multiply(distance);
        location.add(direction);
        return location.toVector();
    }

    /**
     * Step a Vector forward based off of the position vector and the direction vector
     *
     * @param position  The position vector
     * @param direction The direction vector
     * @param distance  The distance that the vector should be moved forward
     * @return The Vector of the new position
     */
    public static Vector stepVector(Vector position, Vector direction, double distance) {
        direction.multiply(distance);
        position.add(direction);
        return position;
    }

    /**
     * Step a Location forward based off of the direction vector
     *
     * @param location The location that will be stepped
     * @param distance The distance that the location should be moved forward
     * @return The Location of the new position
     */
    public static Location stepLocation(Location location, double distance) {
        Vector direction = location.getDirection();
        direction.multiply(distance);
        location.add(direction);
        return location;
    }
}
