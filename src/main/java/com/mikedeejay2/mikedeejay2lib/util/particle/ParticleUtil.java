package com.mikedeejay2.mikedeejay2lib.util.particle;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

/**
 * A util for making particles do specific things
 *
 * @author Mikedeejay2 (I think, I looked at a lot of Spigot resources though)
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
     * @param width The width that the particles spawn at
     * @param force Force the particles to be rendered even when outside of view distance
     */
    public static void particleLine(Location start, Location end, Particle particle, int count, float speed, float width, boolean force)
    {
        Location location = start.clone();
        double length = Math.abs(start.distance(end));
        Vector lookVec = start.getDirection().clone();
        World world = start.getWorld();

        for(double i = 0; i < length; i += 0.5f)
        {
            Vector newVec = lookVec.clone();
            newVec.multiply(i);
            location.add(newVec);

            world.spawnParticle(particle, location, count, width, width, width, speed, null, force);

            location.subtract(newVec);
        }
    }

    /**
     * Add a particle to an entity that will track the entity for the lifetime of the entity or the lifetime of the server
     *
     * @param entity Entity to add the particle to
     * @param particle The particle type to add
     * @param timeToLive The time for the particle to exist on the entity
     * @param count The count of particles per tick
     * @param speed The speed of the particles
     * @param width The width that the particles spawn at
     * @param force Force the particles to be rendered even when outside of view distance
     */
    public static void addParticleToEntity(Plugin plugin, Entity entity, Particle particle, long timeToLive, int count, float speed, float width, boolean force)
    {
        if(particle == null) return;
        new BukkitRunnable()
        {
            int tick = 0;

            @Override
            public void run()
            {
                if(timeToLive > 0)
                {
                    if(tick >= timeToLive) this.cancel();
                    tick++;
                }
                if(entity.isDead()) this.cancel();

                entity.getWorld().spawnParticle(particle, entity.getLocation(), count, width, width, width, speed, null, force);
            }
        }.runTaskTimer(plugin, 0, 0);
    }
}
