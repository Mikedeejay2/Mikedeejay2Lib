package com.mikedeejay2.mikedeejay2lib.commands;

import com.mikedeejay2.mikedeejay2lib.PluginBase;
import com.mikedeejay2.mikedeejay2lib.util.chat.Chat;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.*;

/**
 * Manages a command with subcommands
 */
public abstract class AbstractCommandManager implements CommandExecutor
{
    protected static final PluginBase plugin = PluginBase.getInstance();

    protected ArrayList<AbstractSubCommand> commands = new ArrayList<>();
    private CustomTabCompleter completer;

    public String main;

    public AbstractCommandManager() {}

    // Add new subcommands here:
    // example: public String help = "help";

    /**
     * Setup the command manager by initializing all subcommands
     */
    public void setup()
    {
        plugin.getCommand(main).setExecutor(this);

        completer = new CustomTabCompleter();
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

            AbstractSubCommand target = this.get(args[0]);

            if(target == null)
            {
                Chat.sendMessage(sender, "&c" + plugin.langManager().getTextLib(sender, "command.errors.invalid_subcommand"));
                return true;
            }

            ArrayList<String> arrayList = new ArrayList<String>();

            arrayList.addAll(Arrays.asList(args));

            arrayList.remove(0);

            try
            {
                target.onCommand(sender, args);
            }
            catch(Exception e)
            {
                Chat.sendMessage(sender, "&c" + plugin.langManager().getTextLib(sender, "command.errors.general"));
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
    public AbstractSubCommand get(String name)
    {
        Iterator<AbstractSubCommand> subcommands = this.commands.iterator();

        while(subcommands.hasNext())
        {
            AbstractSubCommand sc = (AbstractSubCommand)subcommands.next();

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

    public void setCommandName(String name)
    {
        main = name;
    }
}
