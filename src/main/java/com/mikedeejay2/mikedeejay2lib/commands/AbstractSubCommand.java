package com.mikedeejay2.mikedeejay2lib.commands;

import org.bukkit.command.CommandSender;

/**
 * A base to all of the subcommands of all subcommands
 */
public abstract class AbstractSubCommand
{
    public AbstractSubCommand() {}
    public abstract void onCommand(CommandSender sender, String[] args);
    public abstract String name();
    public abstract String info();
    public abstract String info(CommandSender sender);
    public abstract String[] aliases();
}
