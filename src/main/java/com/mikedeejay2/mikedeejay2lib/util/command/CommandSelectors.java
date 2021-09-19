package com.mikedeejay2.mikedeejay2lib.util.command;

import com.mikedeejay2.mikedeejay2lib.util.math.MathUtil;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Utility class for processing selectors in a command's arguments
 *
 * @author Mikedeejay2
 */
public final class CommandSelectors
{
    /**
     * The random being used for @r
     */
    private static final Random random = new Random();

    /**
     * This class can not be constructed
     */
    private CommandSelectors()
    {
        throw new UnsupportedOperationException("PlayerSelectorUtil can not be initialized as an object");
    }

    /**
     * Get the list of players based off of an input value from a command.
     * <p>
     * This command allows for the use of vanilla Minecraft's player selectors, <code>&amp;a</code>,
     * <code>&amp;p</code>, and <code>&amp;r</code>.
     *
     * @param value The input value, either a player's name or a player selector
     * @return The list of retrieved players
     */
    public static List<Player> getPlayers(String value, CommandSender sender)
    {
        switch(value.toLowerCase())
        {
            case "@p":
                Location location;
                if(sender instanceof Entity)
                {
                    location = ((LivingEntity) sender).getLocation();
                }
                else if(sender instanceof BlockCommandSender)
                {
                    location = ((BlockCommandSender) sender).getBlock().getLocation();
                }
                else
                {
                    location = new Location(Bukkit.getWorlds().get(0), 0, 0, 0);
                }
                return Collections.singletonList(MathUtil.getNearestPlayer(location));
            case "*":
            case "@a":
                return new ArrayList<>(Bukkit.getOnlinePlayers());
            case "@r":
                List<Player> list = new ArrayList<>(Bukkit.getOnlinePlayers());
                int rand = random.nextInt(list.size());
                return Collections.singletonList(list.get(rand));
        }
        Player player = Bukkit.getPlayer(value);
        if(player == null) return Collections.emptyList();
        return Collections.singletonList(player);
    }
}
