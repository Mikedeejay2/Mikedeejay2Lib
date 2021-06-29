package com.mikedeejay2.mikedeejay2lib.commands;

import com.google.common.collect.ImmutableList;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * The base information for a command.
 *
 * @author Mikedeejay2
 */
public interface CommandInfo
{
    /**
     * The name of the command
     *
     * @return The name of the command
     */
    String getName();

    /**
     * Get the list of aliases that this command has
     *
     * @return All aliases of command
     */
    default List<String> getAliases() { return ImmutableList.of(); }

    /**
     * Any info that the command has
     *
     * @param sender The CommandSender that requested the info. If null, use default lang.
     * @return Any info that the command has
     */
    default @Nullable String getInfo(CommandSender sender) { return null; }

    /**
     * Get the permission that is required to run this command
     *
     * @return The permission that is required to run this command, null if none
     */
    default @Nullable String getPermission() { return null; }

    /**
     * Returns whether this command requires a player or not.
     *
     * @return Whether a player is required to run this command
     */
    default boolean isPlayerRequired() { return false; }
}
