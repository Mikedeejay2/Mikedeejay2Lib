package com.mikedeejay2.mikedeejay2lib.util;

import com.mikedeejay2.mikedeejay2lib.PluginBase;
import com.mikedeejay2.mikedeejay2lib.util.particle.ParticleUtil;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Bat;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * This is a really weird helper method for turning entities into floating arrows.
 * Please, don't ask why because I don't know either.
 *
 * @author Mikedeejay2
 */
public final class MakeConfusedArrows
{
    /**
     * This method spawns an arrow riding a bat to make a floating arrow illusion
     *
     * @param entity The entity that will be turned into an arrow
     * @param velocity The velocity of entity
     * @param time The amount of time before the arrow stops floating and drops to the ground
     */
    public static void makeConfusedArrow(Plugin plugin, Entity entity, int velocity, int time)
    {
        makeConfusedArrow(plugin, entity, null, velocity, time);
    }

    /**
     * This method spawns an arrow riding a bat to make a floating arrow illusion
     *
     * @param entity The entity that will be turned into an arrow
     * @param particle Add a particle to the arrow if wanted
     * @param velocity The velocity of entity
     * @param time The amount of time before the arrow stops floating and drops to the ground
     */
    public static void makeConfusedArrow(Plugin plugin, Entity entity, Particle particle, int velocity, int time)
    {
        World world = entity.getWorld();
        Bat bat = (Bat) world.spawnEntity(entity.getLocation(), EntityType.BAT);
        bat.setVelocity(entity.getVelocity().multiply(3).multiply(velocity));
        bat.setSilent(true);
        bat.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 1000000, 0, false, false));
        Arrow arrow = world.spawnArrow(bat.getLocation(), entity.getVelocity().multiply(3).multiply(velocity), 0, 0);
        if(particle != null)
            ParticleUtil.addParticleToEntity(plugin, arrow, particle, 600, 5, 0.05f, 0.05f, false);
        bat.addPassenger(arrow);
        arrow.setRotation(entity.getLocation().getYaw(), entity.getLocation().getPitch());
        entity.remove();
        new BukkitRunnable()
        {
            @Override
            public void run()
            {
                // After a delay time, the bat will be removed
                bat.remove();
            }
        }.runTaskLater(plugin, time);
    }
}
