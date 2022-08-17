package com.mikedeejay2.mikedeejay2lib.commands;

import org.bukkit.command.CommandSender;

/**
 * The base of a subcommand. Extends {@link CommandInfo}
 *
 * @author Mikedeejay2
 */
public interface SubCommand extends CommandInfo {
    /**
     * When this subcommand is run, onCommand is run
     *
     * @param sender The CommandSender that sent the command
     * @param args   The arguments sent with the command
     */
    void onCommand(CommandSender sender, String[] args);
}
