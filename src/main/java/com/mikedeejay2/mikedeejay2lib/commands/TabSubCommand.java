package com.mikedeejay2.mikedeejay2lib.commands;

import org.bukkit.command.CommandSender;

import java.util.List;

/**
 * The base of a subcommand including tab completion
 *
 * @author Mikedeejay2
 */
public interface TabSubCommand extends SubCommand {
    /**
     * When this subcommand is run, onCommand is run
     *
     * @param sender The CommandSender that sent the command
     * @param args   The arguments sent with the command
     * @return List of tab complete Strings
     */
    List<String> onTabComplete(CommandSender sender, String[] args);
}
