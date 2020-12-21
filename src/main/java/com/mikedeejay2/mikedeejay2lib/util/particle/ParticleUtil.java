package com.mikedeejay2.mikedeejay2lib.util.particle;

import com.mikedeejay2.mikedeejay2lib.PluginBase;
import com.mikedeejay2.mikedeejay2lib.runnable.EnhancedRunnable;
import com.mikedeejay2.mikedeejay2lib.util.math.MathUtil;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.util.Vector;

import java.util.List;

/**
 * A util for making particles do specific things
 *
 * @author Mikedeejay2
 */
public final class ParticleUtil
{
    /**
     * Create a line of particles based on two locations
     *
     * @param start The start location of the line
     * @param end The end location of the line
     * @param particle The particle that should be used
     * @param count The count of particles per 0.5 blocks
     * @param speed The speed of the particles
     * @param offsetX The offset in the X direction of the particle
     * @param offsetY The offset in the Y direction of the particle
     * @param offsetZ The offset in the Z direction of the particle
     * @param force Force the particles to be rendered even when outside of view distance
     */
    public static void particleLine(Location start, Location end, Particle particle, int count, float speed, double offsetX, double offsetY, double offsetZ, double density, boolean force)
    {
        List<Vector> lineLocs = MathUtil.getLine(start.toVector(), end.toVector(), density);
        World world = start.getWorld();

        for(Vector location : lineLocs)
        {
            world.spawnParticle(particle, location.toLocation(world), count, offsetX, offsetY, offsetZ, speed, null, force);
        }
    }

    /**
     * Create a line of particles based on two locations
     *
     * @param start The start location of the line
     * @param end The end location of the line
     * @param particle The particle that should be used
     * @param count The count of particles per 0.5 blocks
     * @param speed The speed of the particles
     * @param force Force the particles to be rendered even when outside of view distance
     */
    public static void particleLine(Location start, Location end, Particle particle, int count, float speed, double density, boolean force)
    {
        particleLine(start, end, particle, count, speed, 0, 0, 0, density, force);
    }

    /**
     * Create a line of particles based on two locations
     *
     * @param start The start location of the line
     * @param end The end location of the line
     * @param particle The particle that should be used
     * @param count The count of particles per 0.5 blocks
     * @param speed The speed of the particles
     */
    public static void particleLine(Location start, Location end, Particle particle, int count, float speed, double density)
    {
        particleLine(start, end, particle, count, speed, 0, 0, 0, density, false);
    }

    /**
     * Add a particle to an entity that will track the entity for the lifetime of the entity or the lifetime of the server
     *
     * @param entity Entity to add the particle to
     * @param particle The particle type to add
     * @param timeToLive The time for the particle to exist on the entity
     * @param particleCount The count of particles per tick
     * @param speed The speed of the particles
     * @param offsetX The offset in the X direction of the particle
     * @param offsetY The offset in the Y direction of the particle
     * @param offsetZ The offset in the Z direction of the particle
     * @param force Force the particles to be rendered even when outside of view distance
     */
    public static void addParticleToEntity(PluginBase plugin, Entity entity, Particle particle, long timeToLive, int particleCount, float speed, double offsetX, double offsetY, double offsetZ, boolean force)
    {
        if(particle == null) return;
        new EnhancedRunnable()
        {
            @Override
            public void onRun()
            {
                if(entity.isDead()) this.cancel();

                entity.getWorld().spawnParticle(particle, entity.getLocation(), particleCount, offsetX, offsetY, offsetZ, speed, null, force);
            }
        }.runTaskTimerCounted(plugin, 0, 0, timeToLive);
    }

    /**
     * Create a circle of particles at a specified location
     *
     * @param location The center of the circle
     * @param particle The particle type to use
     * @param radius The radius of the circle
     * @param count The count of particles per point
     * @param speed The speed that the particles move at
     * @param offsetX The offset in the X direction of the particle
     * @param offsetY The offset in the Y direction of the particle
     * @param offsetZ The offset in the Z direction of the particle
     * @param density The density (Amount of points) of the circle
     * @param force Whether the particles should be force rendered or not
     */
    public static void particleCircle(Location location, Particle particle, int radius, int count, float speed, double offsetX, double offsetY, double offsetZ, double density, boolean force)
    {
        List<Location> circle = MathUtil.getCircleLocations(location, radius, density);
        World world = location.getWorld();
        for(Location curLoc : circle)
        {
            world.spawnParticle(particle, curLoc, count, offsetX, offsetY, offsetZ, speed, null, force);
        }
    }

    /**
     * Create a circle of particles at a specified location
     *
     * @param location The center of the circle
     * @param particle The particle type to use
     * @param radius The radius of the circle
     * @param count The count of particles per point
     * @param speed The speed that the particles move at
     * @param density The density (Amount of points) of the circle
     * @param force Whether the particles should be force rendered or not
     */
    public static void particleCircle(Location location, Particle particle, int radius, int count, float speed, double density, boolean force)
    {
        particleCircle(location, particle, radius, count, speed, 0, 0, 0, density, force);
    }

    /**
     * Create a circle of particles at a specified location
     *
     * @param location The center of the circle
     * @param particle The particle type to use
     * @param radius The radius of the circle
     * @param count The count of particles per point
     * @param speed The speed that the particles move at
     * @param density The density (Amount of points) of the circle
     */
    public static void particleCircle(Location location, Particle particle, int radius, int count, float speed, double density)
    {
        particleCircle(location, particle, radius, count, speed, 0, 0, 0, density, false);
    }

    /**
     * Create a filled circle of particles at a specified location
     *
     * @param location The center of the circle
     * @param particle The particle type to use
     * @param radius The radius of the circle
     * @param count The count of particles per point
     * @param speed The speed that the particles move at
     * @param offsetX The offset in the X direction of the particle
     * @param offsetY The offset in the Y direction of the particle
     * @param offsetZ The offset in the Z direction of the particle
     * @param density The density (Amount of points) of the circle
     * @param force Whether the particles should be force rendered or not
     */
    public static void particleCircleFilled(Location location, Particle particle, int radius, int count, float speed, double offsetX, double offsetY, double offsetZ, double density, boolean force)
    {
        List<Location> circle = MathUtil.getCircleFilledLocations(location, radius, density);
        World world = location.getWorld();
        for(Location curLoc : circle)
        {
            world.spawnParticle(particle, curLoc, count, offsetX, offsetY, offsetZ, speed, null, force);
        }
    }

    /**
     * Create a filled circle of particles at a specified location
     *
     * @param location The center of the circle
     * @param particle The particle type to use
     * @param radius The radius of the circle
     * @param count The count of particles per point
     * @param speed The speed that the particles move at
     * @param density The density (Amount of points) of the circle
     * @param force Whether the particles should be force rendered or not
     */
    public static void particleCircleFilled(Location location, Particle particle, int radius, int count, float speed, double density, boolean force)
    {
        particleCircleFilled(location, particle, radius, count, speed, 0, 0, 0, density, force);
    }

    /**
     * Create a filled circle of particles at a specified location
     *
     * @param location The center of the circle
     * @param particle The particle type to use
     * @param radius The radius of the circle
     * @param count The count of particles per point
     * @param speed The speed that the particles move at
     * @param density The density (Amount of points) of the circle
     */
    public static void particleCircleFilled(Location location, Particle particle, int radius, int count, float speed, double density)
    {
        particleCircleFilled(location, particle, radius, count, speed, 0, 0, 0, density, false);
    }

    /**
     * Create a sphere of particles at a specified location
     *
     * @param location The center of the circle
     * @param particle The particle type to use
     * @param radius The radius of the circle
     * @param count The count of particles per point
     * @param speed The speed that the particles move at
     * @param offsetX The offset in the X direction of the particle
     * @param offsetY The offset in the Y direction of the particle
     * @param offsetZ The offset in the Z direction of the particle
     * @param density The density (Amount of points) of the circle
     * @param force Whether the particles should be force rendered or not
     */
    public static void particleSphereHollow(Location location, Particle particle, int radius, int count, float speed, double offsetX, double offsetY, double offsetZ, double density, boolean force)
    {
        List<Location> sphere = MathUtil.getSphereHollowLocations(location, radius, density);
        World world = location.getWorld();

        for(Location curLoc : sphere)
        {
            world.spawnParticle(particle, curLoc, count, offsetX, offsetY, offsetZ, speed, null, force);
        }
    }

    /**
     * Create a sphere of particles at a specified location
     *
     * @param location The center of the circle
     * @param particle The particle type to use
     * @param radius The radius of the circle
     * @param count The count of particles per point
     * @param speed The speed that the particles move at
     * @param density The density (Amount of points) of the circle
     * @param force Whether the particles should be force rendered or not
     */
    public static void particleSphereHollow(Location location, Particle particle, int radius, int count, float speed, double density, boolean force)
    {
        particleSphereHollow(location, particle, radius, count, speed, 0, 0, 0, density, force);
    }

    /**
     * Create a sphere of particles at a specified location
     *
     * @param location The center of the circle
     * @param particle The particle type to use
     * @param radius The radius of the circle
     * @param count The count of particles per point
     * @param speed The speed that the particles move at
     * @param density The density (Amount of points) of the circle
     */
    public static void particleSphereHollow(Location location, Particle particle, int radius, int count, float speed, double density)
    {
        particleSphereHollow(location, particle, radius, count, speed, 0, 0, 0, density, false);
    }

    /**
     * Create a sphere of particles at a specified location
     *
     * @param location The center of the circle
     * @param particle The particle type to use
     * @param radius The radius of the circle
     * @param count The count of particles per point
     * @param speed The speed that the particles move at
     * @param offsetX The offset in the X direction of the particle
     * @param offsetY The offset in the Y direction of the particle
     * @param offsetZ The offset in the Z direction of the particle
     * @param density The density (Amount of points) of the circle
     * @param force Whether the particles should be force rendered or not
     */
    public static void particleSphereFilled(Location location, Particle particle, int radius, int count, float speed, double offsetX, double offsetY, double offsetZ, double density, boolean force)
    {
        List<Location> sphere = MathUtil.getSphereFilledLocations(location, radius, density);
        World world = location.getWorld();

        for(Location curLoc : sphere)
        {
            world.spawnParticle(particle, curLoc, count, offsetX, offsetY, offsetZ, speed, null, force);
        }
    }

    /**
     * Create a sphere of particles at a specified location
     *
     * @param location The center of the circle
     * @param particle The particle type to use
     * @param radius The radius of the circle
     * @param count The count of particles per point
     * @param speed The speed that the particles move at
     * @param density The density (Amount of points) of the circle
     * @param force Whether the particles should be force rendered or not
     */
    public static void particleSphereFilled(Location location, Particle particle, int radius, int count, float speed, double density, boolean force)
    {
        particleSphereFilled(location, particle, radius, count, speed, 0, 0, 0, density, force);
    }

    /**
     * Create a sphere of particles at a specified location
     *
     * @param location The center of the circle
     * @param particle The particle type to use
     * @param radius The radius of the circle
     * @param count The count of particles per point
     * @param speed The speed that the particles move at
     * @param density The density (Amount of points) of the circle
     */
    public static void particleSphereFilled(Location location, Particle particle, int radius, int count, float speed, double density)
    {
        particleSphereFilled(location, particle, radius, count, speed, 0, 0, 0, density, false);
    }

    /**
     * Create a star of particles at a specified location
     *
     * @param location The center of the star
     * @param particle The particle type to use
     * @param size The size of the star
     * @param points The amount of points of the star
     * @param count The count of particles per point
     * @param speed The speed that the particles move at
     * @param offsetX The offset in the X direction of the particle
     * @param offsetY The offset in the Y direction of the particle
     * @param offsetZ The offset in the Z direction of the particle
     * @param density The density (Amount of points)
     * @param force Whether the particles should be force rendered or not
     */
    public static void particleStar(Location location, Particle particle, int size, int points, int count, float speed, double offsetX, double offsetY, double offsetZ, double density, boolean force)
    {
        List<Location> star = MathUtil.getStarLocations(location, size, density, points);
        World world = location.getWorld();

        for(Location curLoc : star)
        {
            world.spawnParticle(particle, curLoc, count, offsetX, offsetY, offsetZ, speed, null, force);
        }
    }

    /**
     * Create a star of particles at a specified location
     *
     * @param location The center of the star
     * @param particle The particle type to use
     * @param size The size of the star
     * @param points The amount of points of the star
     * @param count The count of particles per point
     * @param speed The speed that the particles move at
     * @param density The density (Amount of points)
     * @param force Whether the particles should be force rendered or not
     */
    public static void particleStar(Location location, Particle particle, int size, int points, int count, float speed, double density, boolean force)
    {
        particleStar(location, particle, size, points, count, speed, 0, 0, 0, density, force);
    }

    /**
     * Create a star of particles at a specified location
     *
     * @param location The center of the star
     * @param particle The particle type to use
     * @param size The size of the star
     * @param points The amount of points of the star
     * @param count The count of particles per point
     * @param speed The speed that the particles move at
     * @param density The density (Amount of points)
     */
    public static void particleStar(Location location, Particle particle, int size, int points, int count, float speed, double density)
    {
        particleStar(location, particle, size, points, count, speed, 0, 0, 0, density, false);
    }

    /**
     * Create a cylinder of particles at a specified location
     *
     * @param location The center of the cylinder
     * @param particle The particle type to use
     * @param height The height of the cylinder
     * @param radius The radius of the cylinder
     * @param count The count of particles per point
     * @param speed The speed that the particles move at
     * @param offsetX The offset X of particles
     * @param offsetY The offset Y of particles
     * @param offsetZ The offset Z of particles
     * @param density The density (Amount of points)
     * @param force Whether the particles should be force rendered or not
     */
    public static void particleCylinderHollow(Location location, Particle particle, double height, double radius, int count, float speed, double offsetX, double offsetY, double offsetZ, double density, boolean force)
    {
        List<Location> cylinder = MathUtil.getCylinderHollowLocations(location, height, radius, density);
        World world = location.getWorld();

        for(Location curLoc : cylinder)
        {
            world.spawnParticle(particle, curLoc, count, offsetX, offsetY, offsetZ, speed, null, force);
        }
    }

    /**
     * Create a cylinder of particles at a specified location
     *
     * @param location The center of the cylinder
     * @param particle The particle type to use
     * @param height The height of the cylinder
     * @param radius The radius of the cylinder
     * @param count The count of particles per point
     * @param speed The speed that the particles move at
     * @param density The density (Amount of points)
     * @param force Whether the particles should be force rendered or not
     */
    public static void particleCylinderHollow(Location location, Particle particle, double height, double radius, int count, float speed, double density, boolean force)
    {
        particleCylinderHollow(location, particle, height, radius, count, speed, 0, 0, 0, density, force);
    }

    /**
     * Create a cylinder of particles at a specified location
     *
     * @param location The center of the cylinder
     * @param particle The particle type to use
     * @param height The height of the cylinder
     * @param radius The radius of the cylinder
     * @param count The count of particles per point
     * @param speed The speed that the particles move at
     * @param density The density (Amount of points)
     */
    public static void particleCylinderHollow(Location location, Particle particle, double height, double radius, int count, float speed, double density)
    {
        particleCylinderHollow(location, particle, height, radius, count, speed, 0, 0, 0, density, false);
    }

    /**
     * Create a filled cylinder of particles at a specified location
     *
     * @param location The center of the cylinder
     * @param particle The particle type to use
     * @param height The height of the cylinder
     * @param radius The radius of the cylinder
     * @param count The count of particles per point
     * @param speed The speed that the particles move at
     * @param offsetX The offset X of particles
     * @param offsetY The offset Y of particles
     * @param offsetZ The offset Z of particles
     * @param density The density (Amount of points)
     * @param force Whether the particles should be force rendered or not
     */
    public static void particleCylinderFilled(Location location, Particle particle, double height, double radius, int count, float speed, double offsetX, double offsetY, double offsetZ, double density, boolean force)
    {
        List<Location> cylinder = MathUtil.getCylinderFilledLocations(location, height, radius, density);
        World world = location.getWorld();

        for(Location curLoc : cylinder)
        {
            world.spawnParticle(particle, curLoc, count, offsetX, offsetY, offsetZ, speed, null, force);
        }
    }

    /**
     * Create a filled cylinder of particles at a specified location
     *
     * @param location The center of the cylinder
     * @param particle The particle type to use
     * @param height The height of the cylinder
     * @param radius The radius of the cylinder
     * @param count The count of particles per point
     * @param speed The speed that the particles move at
     * @param density The density (Amount of points)
     * @param force Whether the particles should be force rendered or not
     */
    public static void particleCylinderFilled(Location location, Particle particle, double height, double radius, int count, float speed, double density, boolean force)
    {
        particleCylinderFilled(location, particle, height, radius, count, speed, 0, 0, 0, density, force);
    }

    /**
     * Create a filled cylinder of particles at a specified location
     *
     * @param location The center of the cylinder
     * @param particle The particle type to use
     * @param height The height of the cylinder
     * @param radius The radius of the cylinder
     * @param count The count of particles per point
     * @param speed The speed that the particles move at
     * @param density The density (Amount of points)
     */
    public static void particleCylinderFilled(Location location, Particle particle, double height, double radius, int count, float speed, double density)
    {
        particleCylinderFilled(location, particle, height, radius, count, speed, 0, 0, 0, density, false);
    }

    /**
     * Create a shape of particles at a specified location with a specified amount of edges
     *
     * @param location The center of the shape
     * @param particle The particle type to use
     * @param size The size of the shape
     * @param edges The amount of points of the shape
     * @param count The count of particles per point
     * @param speed The speed that the particles move at
     * @param offsetX The offset in the X direction of the particle
     * @param offsetY The offset in the Y direction of the particle
     * @param offsetZ The offset in the Z direction of the particle
     * @param density The density (Amount of points)
     * @param force Whether the particles should be force rendered or not
     */
    public static void particleShape(Location location, Particle particle, int size, int edges, int count, float speed, double offsetX, double offsetY, double offsetZ, double density, boolean force)
    {
        List<Location> star = MathUtil.getShapeLocations(location, size, density, edges);
        World world = location.getWorld();

        for(Location curLoc : star)
        {
            world.spawnParticle(particle, curLoc, count, offsetX, offsetY, offsetZ, speed, null, force);
        }
    }

    /**
     * Create a shape of particles at a specified location with a specified amount of edges
     *
     * @param location The center of the shape
     * @param particle The particle type to use
     * @param size The size of the shape
     * @param edges The amount of points of the shape
     * @param count The count of particles per point
     * @param speed The speed that the particles move at
     * @param density The density (Amount of points)
     * @param force Whether the particles should be force rendered or not
     */
    public static void particleShape(Location location, Particle particle, int size, int edges, int count, float speed, double density, boolean force)
    {
        particleShape(location, particle, size, edges, count, speed, 0, 0, 0, density, force);
    }

    /**
     * Create a shape of particles at a specified location with a specified amount of edges
     *
     * @param location The center of the shape
     * @param particle The particle type to use
     * @param size The size of the shape
     * @param edges The amount of points of the shape
     * @param count The count of particles per point
     * @param speed The speed that the particles move at
     * @param density The density (Amount of points)
     */
    public static void particleShape(Location location, Particle particle, int size, int edges, int count, float speed, double density)
    {
        particleShape(location, particle, size, edges, count, speed, 0, 0, 0, density, false);
    }
}
