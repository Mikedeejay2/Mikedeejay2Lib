package com.mikedeejay2.mikedeejay2lib.commands;

import com.mikedeejay2.mikedeejay2lib.PluginBase;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.*;

/**
 * Manages a command with subcommands
 *
 * @author Mikedeejay2 (Originally from a tutorial)
 */
public class CommandManager implements CommandExecutor
{
    protected final PluginBase plugin;
    protected ArrayList<AbstractSubCommand> commands = new ArrayList<>();
    private CustomTabCompleter completer;

    public String main;

    public CommandManager(PluginBase plugin)
    {
        this.plugin = plugin;
    }

    // Add new subcommands here:
    // example: public String help = "help";

    /**
     * Setup the command manager by initializing all subcommands
     * This should only be called after AbstractCommandManager#setCommandName() has been called
     */
    public void setup(String commandName)
    {
        main = commandName;
        plugin.getCommand(main).setExecutor(this);

        completer = new CustomTabCompleter(plugin);
        plugin.getCommand(main).setTabCompleter(completer);

        // Add new subcommands here:
        // example: this.commands.add(new HelpCommand());
    }

    /**
     * Receive a command
     *
     * @param sender The CommandSender that sent the command
     * @param command The command that was sent
     * @param label The label
     * @param args The command arguments (subcommands)
     * @return Successful or not
     */
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        if(command.getName().equalsIgnoreCase(main))
        {
            if(args.length == 0)
            {
                args = new String[1];
                args[0] = "help";
            }

            AbstractSubCommand target = this.getSubcommand(args[0]);

            if(target == null)
            {
                plugin.chat().sendMessage(sender, "&c" + plugin.langManager().getTextLib(sender, "command.errors.invalid_subcommand"));
                return false;
            }

            ArrayList<String> arrayList = new ArrayList<String>(Arrays.asList(args));

            arrayList.remove(0);

            if(target.permission() != null && !sender.hasPermission(target.permission()))
            {
                plugin.chat().sendMessage(sender, "&c" + plugin.langManager().getTextLib(sender, "errors.permission.nopermission"));
                return false;
            }
            if(!(sender instanceof Player) && target.playerRequired())
            {
                plugin.chat().sendMessage(sender, "&c" + plugin.langManager().getTextLib(sender, "errors.player_required"));
                return false;
            }

            try
            {
                target.onCommand(sender, args);
            }
            catch(Exception e)
            {
                plugin.chat().sendMessage(sender, "&c" + plugin.langManager().getTextLib(sender, "command.errors.general"));
                e.printStackTrace();
            }
        }

        return true;
    }

    /**
     * Get a subcommand according to it's name.
     *
     * @param name Name of subcommand to get
     * @return The subcommand that corresponds to the name
     */
    public AbstractSubCommand getSubcommand(String name)
    {
        for(AbstractSubCommand sc : this.commands)
        {
            if(sc.name().equalsIgnoreCase(name))
            {
                return sc;
            }

            String[] aliases;
            int length = (aliases = sc.aliases()).length;

            for(int var5 = 0; var5 < length; ++var5)
            {
                String alias = aliases[var5];

                if(name.equalsIgnoreCase(alias))
                {
                    return sc;
                }
            }
        }
        return null;
    }

    /**
     * Gets all subcommand strings for tab completion
     *
     * @param aliases Specify whether aliases are wanted or not
     * @return An arraylist of strings containing the subcommands.
     */
    public String[] getAllCommandStrings(boolean aliases)
    {
        ArrayList<String> strings = new ArrayList<>();
        for(AbstractSubCommand command : commands)
        {
            strings.add(command.name());
            if(aliases) Collections.addAll(strings, command.aliases());
        }
        return strings.toArray(new String[0]);
    }

    /**
     * Add a subcommand to the command manager
     *
     * @param subCommand The subcommand to add
     */
    public void addSubcommand(AbstractSubCommand subCommand)
    {
        this.commands.add(subCommand);
    }

    /**
     * Remove a subcommand from the command manager based off of the subcommand's name
     *
     * @param name Name of the subcommand
     */
    public void removeSubCommand(String name)
    {
        commands.remove(getSubcommand(name));
    }

    /**
     * Remove a subcommand from the command manager based off of the subcommand object
     *
     * @param subCommand The subcommand to remove
     */
    public void removeSubCommand(AbstractSubCommand subCommand)
    {
        commands.remove(subCommand);
    }

    public String getBaseCommand()
    {
        return main;
    }
}
