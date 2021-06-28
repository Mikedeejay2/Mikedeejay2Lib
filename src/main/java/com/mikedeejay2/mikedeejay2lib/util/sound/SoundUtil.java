package com.mikedeejay2.mikedeejay2lib.util.sound;

import org.bukkit.*;
import org.bukkit.entity.Player;

/**
 * Utility class for playing sounds in game.
 * The main purpose of this class is to encapsulate the ability to
 * broadcast a sound to all players on a server.
 *
 * @author Mikedeejay2
 */
public final class SoundUtil
{
    /**
     * Play a sound to a player based off of different parameters
     *
     * @param player   The player to play the sound to
     * @param sound    The sound the play to the player
     * @param volume   The volume to play the sound at
     * @param pitch    The pitch to play the sound at
     * @param category The <code>SoundCategory</code> to play the sound in
     */
    public static void playSound(Player player, Sound sound, float volume, float pitch, SoundCategory category)
    {
        player.playSound(player.getLocation(), sound, category, volume, pitch);
    }

    /**
     * Play a sound to a player based off of different parameters
     *
     * @param player The player to play the sound to
     * @param sound  The sound the play to the player
     * @param volume The volume to play the sound at
     * @param pitch  The pitch to play the sound at
     */
    public static void playSound(Player player, Sound sound, float volume, float pitch)
    {
        playSound(player, sound, volume, pitch, SoundCategory.MASTER);
    }

    /**
     * Play a sound to a player based off of different parameters
     *
     * @param player The player to play the sound to
     * @param sound  The sound the play to the player
     * @param volume The volume to play the sound at
     */
    public static void playSound(Player player, Sound sound, float volume)
    {
        playSound(player, sound, volume, 1.0f, SoundCategory.MASTER);
    }

    /**
     * Play a sound to a player based off of different parameters
     *
     * @param player The player to play the sound to
     * @param sound  The sound the play to the player
     */
    public static void playSound(Player player, Sound sound)
    {
        playSound(player, sound, 1.0f, 1.0f, SoundCategory.MASTER);
    }

    /**
     * Play a sound at a location based off of different parameters
     *
     * @param location The location to play the sound at
     * @param sound    The sound to play at the location
     * @param volume   The volume to play the sound at
     * @param pitch    The pitch to play the sound at
     * @param category The <code>SoundCategory</code> to play the sound in
     */
    public static void playSound(Location location, Sound sound, float volume, float pitch, SoundCategory category)
    {
        World world = location.getWorld();
        world.playSound(location, sound, category, volume, pitch);
    }

    /**
     * Play a sound at a location based off of different parameters
     *
     * @param location The location to play the sound at
     * @param sound    The sound to play at the location
     * @param volume   The volume to play the sound at
     * @param pitch    The pitch to play the sound at
     */
    public static void playSound(Location location, Sound sound, float volume, float pitch)
    {
        playSound(location, sound, volume, pitch, SoundCategory.MASTER);
    }

    /**
     * Play a sound at a location based off of different parameters
     *
     * @param location The location to play the sound at
     * @param sound    The sound to play at the location
     * @param volume   The volume to play the sound at
     */
    public static void playSound(Location location, Sound sound, float volume)
    {
        playSound(location, sound, volume, 1.0f, SoundCategory.MASTER);
    }

    /**
     * Play a sound at a location based off of different parameters
     *
     * @param location The location to play the sound at
     * @param sound    The sound to play at the location
     */
    public static void playSound(Location location, Sound sound)
    {
        playSound(location, sound, 1.0f, 1.0f, SoundCategory.MASTER);
    }

    /**
     * Broadcast a sound to all players on the server.
     * Works similarly to {@link Bukkit#broadcastMessage(String)} where
     * all players, regardless of their current world, can hear the
     * sound
     *
     * @param sound    The sound to play at the location
     * @param volume   The volume to play the sound at
     * @param pitch    The pitch to play the sound at
     * @param category The <code>SoundCategory</code> to play the sound in
     */
    public static void broadcastSound(Sound sound, float volume, float pitch, SoundCategory category)
    {
        for(Player player : Bukkit.getOnlinePlayers())
        {
            playSound(player, sound, volume, pitch, category);
        }
    }

    /**
     * Broadcast a sound to all players on the server.
     * Works similarly to {@link Bukkit#broadcastMessage(String)} where
     * all players, regardless of their current world, can hear the
     * sound
     *
     * @param sound  The sound to play at the location
     * @param volume The volume to play the sound at
     * @param pitch  The pitch to play the sound at
     */
    public static void broadcastSound(Sound sound, float volume, float pitch)
    {
        broadcastSound(sound, volume, pitch, SoundCategory.MASTER);
    }

    /**
     * Broadcast a sound to all players on the server.
     * Works similarly to {@link Bukkit#broadcastMessage(String)} where
     * all players, regardless of their current world, can hear the
     * sound
     *
     * @param sound  The sound to play at the location
     * @param volume The volume to play the sound at
     */
    public static void broadcastSound(Sound sound, float volume)
    {
        broadcastSound(sound, volume, 1.0f, SoundCategory.MASTER);
    }

    /**
     * Broadcast a sound to all players on the server.
     * Works similarly to {@link Bukkit#broadcastMessage(String)} where
     * all players, regardless of their current world, can hear the
     * sound
     *
     * @param sound The sound to play at the location
     */
    public static void broadcastSound(Sound sound)
    {
        broadcastSound(sound, 1.0f, 1.0f, SoundCategory.MASTER);
    }
}
