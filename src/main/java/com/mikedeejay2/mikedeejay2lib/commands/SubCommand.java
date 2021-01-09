package com.mikedeejay2.mikedeejay2lib.commands;

import org.bukkit.command.CommandSender;

/**
 * A base to all of the subcommands of all subcommands
 *
 * @author Mikedeejay2
 */
public interface SubCommand
{
    /**
     * When this subcommand is run, onCommand is run
     *
     * @param sender The CommandSender that sent the command
     * @param args   The arguments sent with the command
     */
    void onCommand(CommandSender sender, String[] args);

    /**
     * The name of the subcommand
     *
     * @return The name of the subcommand
     */
    String name();

    /**
     * Any info that the subcommand has
     *
     * @param sender The CommandSender that requested the info. If null, use default lang.
     * @return Any info that the subcommand has
     */
    String info(CommandSender sender);

    /**
     * Get the list of aliases that this subcommand has
     *
     * @return All aliases of subcommand
     */
    String[] aliases();

    /**
     * Get the permission that is required to run this command
     *
     * @return The permission that is required to run this command, null if none
     */
    String permission();

    /**
     * Returns whether this subcommand requires a player or not.
     *
     * @return Whether a player is required to run this command
     */
    boolean playerRequired();
}
