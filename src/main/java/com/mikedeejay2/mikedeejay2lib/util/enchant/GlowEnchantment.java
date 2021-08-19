package com.mikedeejay2.mikedeejay2lib.util.enchant;

import com.mikedeejay2.mikedeejay2lib.BukkitPlugin;
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
    /**
     * The existing <code>GlowEnchantment</code> instance
     */
    private static GlowEnchantment enchant;

    /**
     * Construct a new <code>GlowEnchantment</code>
     */
    private GlowEnchantment()
    {
        super(new NamespacedKey("mikedeejay2lib", "glow"));
    }

    /**
     * {@inheritDoc}
     *
     * @return null
     */
    @Override
    public String getName()
    {
        return null;
    }

    /**
     * {@inheritDoc}
     *
     * @return 0
     */
    @Override
    public int getMaxLevel()
    {
        return 0;
    }

    /**
     * {@inheritDoc}
     *
     * @return 0
     */
    @Override
    public int getStartLevel()
    {
        return 0;
    }

    /**
     * {@inheritDoc}
     *
     * @return null
     */
    @Override
    public EnchantmentTarget getItemTarget()
    {
        return null;
    }

    /**
     * {@inheritDoc}
     *
     * @return false
     */
    @Override
    public boolean isTreasure()
    {
        return false;
    }

    /**
     * {@inheritDoc}
     *
     * @return false
     */
    @Override
    public boolean isCursed()
    {
        return false;
    }

    /**
     * {@inheritDoc}
     *
     * @return false
     */
    @Override
    public boolean conflictsWith(Enchantment other)
    {
        return false;
    }

    /**
     * {@inheritDoc}
     *
     * @return false
     */
    @Override
    public boolean canEnchantItem(ItemStack item)
    {
        return false;
    }

    /**
     * Register the <code>GlowEnchantment</code> to the server
     *
     * @param plugin A reference to the <code>BukkitPlugin</code> instance
     */
    public static void registerGlow(BukkitPlugin plugin)
    {
        try
        {
            Field acceptingNew = Enchantment.class.getDeclaredField("acceptingNew");
            acceptingNew.setAccessible(true);
            acceptingNew.set(null, true);
        }
        catch(IllegalAccessException | NoSuchFieldException exception)
        {
            plugin.getLogger().severe("Unable to register glow enchantment: Illegal or invalid field exception has occurred.");
        }

        try
        {
            GlowEnchantment glow = new GlowEnchantment();
            enchant = glow;
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

    /**
     * Get the {@link GlowEnchantment} instance
     *
     * @return The <code>GlowEnchant</code> instance
     */
    public static GlowEnchantment get()
    {
        return enchant;
    }
}
