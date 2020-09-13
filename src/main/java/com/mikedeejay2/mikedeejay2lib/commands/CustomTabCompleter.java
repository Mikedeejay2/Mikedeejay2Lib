package com.mikedeejay2.mikedeejay2lib.commands;

import com.mikedeejay2.mikedeejay2lib.PluginBase;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Automatically tab completes any subcommand that has been added to the command manager
 */
public class CustomTabCompleter implements TabCompleter
{
    private static final PluginBase plugin = PluginBase.getInstance();

    /**
     * This class is meant to autocomplete the /commandname command with options to make
     * typing out commands easier.
     *
     * @param sender The CommandSender that sent the command
     * @param command The command that was sent
     * @param alias The alias of the command
     * @param args The command's arguments (subcommands)
     * @return Return a list of autocomplete strings
     */
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args)
    {
        AbstractCommandManager manager = plugin.commandManager();
        if(!command.getName().equalsIgnoreCase(manager.main)) return null;
        ArrayList<String> commands = new ArrayList<>();
        switch(args.length)
        {
            case 1:
                String[] arg1Strings = manager.getAllCommandStrings(true);
                Collections.addAll(commands, arg1Strings);
                break;
        }
        return commands;
    }
}
