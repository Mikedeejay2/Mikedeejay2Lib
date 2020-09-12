package com.mikedeejay2.mikedeejay2lib.util;

import com.mikedeejay2.mikedeejay2lib.PluginBase;
import com.mikedeejay2.mikedeejay2lib.util.particle.ParticleUtil;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Bat;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

public final class MakeBats
{
    private static final PluginBase plugin = PluginBase.getInstance();

    // This method spawns an arrow riding a bat to make a floating arrow illusion
    public static void makeBat(Entity proj, int velocity, int time)
    {
        makeBat(proj, null, velocity, time);
    }

    // This method spawns an arrow riding a bat to make a floating arrow illusion
    public static void makeBat(Entity proj, Particle particle, int velocity, int time)
    {
        World world = proj.getWorld();
        Bat bat = (Bat) world.spawnEntity(proj.getLocation(), EntityType.BAT);
        bat.setVelocity(proj.getVelocity().multiply(3).multiply(velocity));
        bat.setSilent(true);
        bat.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 1000000, 0, false, false));
        Arrow arrow = world.spawnArrow(bat.getLocation(), proj.getVelocity().multiply(3).multiply(velocity), 0, 0);
        if(particle != null)
            ParticleUtil.addParticleToEntity(arrow, particle, 600, 5, 0.05f, 0.05f, false);
        bat.addPassenger(arrow);
        arrow.setRotation(proj.getLocation().getYaw(), proj.getLocation().getPitch());
        proj.remove();
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
