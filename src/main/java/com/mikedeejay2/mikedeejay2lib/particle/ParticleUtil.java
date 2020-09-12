package com.mikedeejay2.mikedeejay2lib.particle;

import com.mikedeejay2.mikedeejay2lib.PluginBase;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public final class ParticleUtil
{
    private static PluginBase plugin = PluginBase.getInstance();

    public static void particleLine(Location start, Location end, Particle particle, int count, float speed, float variation, boolean force)
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

            world.spawnParticle(particle, location, count, variation, variation, variation, speed, null, force);

            location.subtract(newVec);
        }
    }

    public static void addParticleToEntity(Entity entity, Particle particle, long timeToLive, int count, float speed, float variation, boolean force)
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

                entity.getWorld().spawnParticle(particle, entity.getLocation(), count, variation, variation, variation, speed, null, force);
            }
        }.runTaskTimer(plugin, 0, 0);
    }
}
