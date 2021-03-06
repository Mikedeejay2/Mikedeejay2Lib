package com.mikedeejay2.mikedeejay2lib.util.enchant;

import com.mikedeejay2.mikedeejay2lib.PluginBase;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.Field;

/**
 * A glow enchantment. It's only purpose is to make the item that it's put on glow.
 * This is a workaround to using NMS code since NMS code changes with almost every
 * update.
 *
 * @author Mikedeejay2 (Original idea from Spigot/Bukkit forums)
 */
public final class GlowEnchantment extends Enchantment
{
    public GlowEnchantment(PluginBase plugin)
    {
        super(new NamespacedKey(plugin, "mdlglow"));
    }

    @Override
    public String getName()
    {
        return null;
    }

    @Override
    public int getMaxLevel()
    {
        return 0;
    }

    @Override
    public int getStartLevel()
    {
        return 0;
    }

    @Override
    public EnchantmentTarget getItemTarget()
    {
        return null;
    }

    @Override
    public boolean isTreasure()
    {
        return false;
    }

    @Override
    public boolean isCursed()
    {
        return false;
    }

    @Override
    public boolean conflictsWith(Enchantment other)
    {
        return false;
    }

    @Override
    public boolean canEnchantItem(ItemStack item)
    {
        return false;
    }

    /**
     * Register the <tt>GlowEnchantment</tt> to the server
     *
     * @param plugin A reference to the <tt>PluginBase</tt> instance
     */
    public static void registerGlow(PluginBase plugin)
    {
        try
        {
            Field f = Enchantment.class.getDeclaredField("acceptingNew");
            f.setAccessible(true);
            f.set(null, true);
        }
        catch(IllegalAccessException | NoSuchFieldException exception)
        {
            plugin.getLogger().severe("Unable to register glow enchantment: Illegal or invalid field exception has occurred.");
        }

        try
        {
            GlowEnchantment glow = new GlowEnchantment(plugin);
            Enchantment.registerEnchantment(glow);
        }
        catch(IllegalArgumentException exception)
        {
            // IllegalArgumentException is always ignored here as it is always thrown.
        }
        catch(Exception exception)
        {
            plugin.getLogger().severe("Unable to register glow enchantment: Unknown exception occurred, stack trace below:");
            exception.printStackTrace();
        }
    }
}
