package com.mikedeejay2.mikedeejay2lib.util;

import org.bukkit.FluidCollisionMode;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.LivingEntity;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.Vector;

public final class LookUtil
{
    public Vector rayTraceVector(LivingEntity entity)
    {
        return rayTraceVector(entity, 256, FluidCollisionMode.NEVER, false);
    }

    public Vector rayTraceVector(LivingEntity entity, int maxDistance)
    {
        return rayTraceVector(entity, maxDistance, FluidCollisionMode.NEVER, false);
    }

    public Vector rayTraceVector(LivingEntity entity, int maxDistance, boolean ignorePassableBlocks)
    {
        return rayTraceVector(entity, maxDistance, FluidCollisionMode.NEVER, ignorePassableBlocks);
    }

    public Vector rayTraceVector(LivingEntity entity, int maxDistance, FluidCollisionMode fluidCollisionMode)
    {
        return rayTraceVector(entity, maxDistance, fluidCollisionMode, false);
    }

    public Vector rayTraceVector(LivingEntity entity, int maxDistance, FluidCollisionMode fluidCollisionMode, boolean ignorePassableBlocks)
    {
        World world = entity.getWorld();
        Location startLoc = entity.getEyeLocation();
        Vector lookVec = startLoc.getDirection();
        RayTraceResult result = world.rayTrace(startLoc, lookVec, maxDistance, fluidCollisionMode, ignorePassableBlocks, 0, null);
        if(result == null) return null;
        return result.getHitPosition();
    }

    public Location rayTraceLocation(LivingEntity entity)
    {
        return rayTraceLocation(entity, 256, FluidCollisionMode.NEVER, false);
    }

    public Location rayTraceLocation(LivingEntity entity, int maxDistance)
    {
        return rayTraceLocation(entity, maxDistance, FluidCollisionMode.NEVER, false);
    }

    public Location rayTraceLocation(LivingEntity entity, int maxDistance, boolean ignorePassableBlocks)
    {
        return rayTraceLocation(entity, maxDistance, FluidCollisionMode.NEVER, ignorePassableBlocks);
    }

    public Location rayTraceLocation(LivingEntity entity, int maxDistance, FluidCollisionMode fluidCollisionMode)
    {
        return rayTraceLocation(entity, maxDistance, fluidCollisionMode, false);
    }

    public Location rayTraceLocation(LivingEntity entity, int maxDistance, FluidCollisionMode fluidCollisionMode, boolean ignorePassableBlocks)
    {
        World world = entity.getWorld();
        Location startLoc = entity.getEyeLocation();
        Vector lookVec = startLoc.getDirection();
        RayTraceResult result = world.rayTrace(startLoc, lookVec, maxDistance, fluidCollisionMode, ignorePassableBlocks, 0, null);
        if(result == null) return null;
        return result.getHitPosition().toLocation(world);
    }
}
