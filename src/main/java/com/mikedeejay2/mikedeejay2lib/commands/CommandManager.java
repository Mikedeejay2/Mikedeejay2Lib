package com.mikedeejay2.mikedeejay2lib.commands;

import com.mikedeejay2.mikedeejay2lib.BukkitPlugin;
import org.apache.commons.lang3.Validate;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Manages a command with subcommands
 *
 * @author Mikedeejay2
 */
public class CommandManager implements TabCommandBase
{
    /**
     * The {@link BukkitPlugin} instance
     */
    protected final BukkitPlugin plugin;

    /**
     * The list of {@link SubCommand}s that this <code>CommandManager</code> manages
     */
    protected List<SubCommand> commands;

    /**
     * The default subcommand if none is specified
     */
    protected @Nullable String defaultSubCommand;

    /**
     * The name of the command of this <code>CommandManager</code>
     */
    private String commandName;

    /**
     * Constructs a new <code>CommandManager</code>
     *
     * @param plugin      The {@link BukkitPlugin} instance
     * @param commandName The name of the command
     */
    public CommandManager(@NotNull BukkitPlugin plugin, @NotNull String commandName)
    {
        Validate.notNull(plugin, "Plugin instance cannot be null");
        Validate.notNull(commandName, "Command name cannot be null");
        this.plugin = plugin;
        this.commandName = commandName;
        this.commands = new ArrayList<>();
        this.defaultSubCommand = "";
    }

    /**
     * Receive a command
     *
     * @param sender  The CommandSender that sent the command
     * @param command The command that was sent
     * @param label   The label
     * @param args    The command arguments (subcommands)
     * @return Successful or not
     */
    @Override
    public boolean onCommand(@NotNull CommandSender sender, Command command, @NotNull String label, String[] args)
    {
        if(command.getName().equalsIgnoreCase(commandName))
        {
            if(args.length == 0)
            {
                args = new String[1];
                args[0] = defaultSubCommand;
            }

            SubCommand target = this.getSubcommand(args[0]);

            if(target == null)
            {
                plugin.sendMessage(sender, "&c" + plugin.getLibLangManager().getText(sender, "command.errors.invalid_subcommand"));
                return false;
            }

            // TODO: Unused ArrayList?
            ArrayList<String> arrayList = new ArrayList<>(Arrays.asList(args));

            arrayList.remove(0);

            if(target.getPermission() != null && !sender.hasPermission(target.getPermission()))
            {
                plugin.sendMessage(sender, "&c" + plugin.getLibLangManager().getText(sender, "errors.permission.nopermission"));
                return false;
            }
            if(!(sender instanceof Player) && target.isPlayerRequired())
            {
                plugin.sendMessage(sender, "&c" + plugin.getLibLangManager().getText(sender, "errors.player_required"));
                return false;
            }

            try
            {
                target.onCommand(sender, args);
            }
            catch(Exception exception)
            {
                plugin.sendMessage(sender, "&c" + plugin.getLibLangManager().getText(sender, "command.errors.general"));
                exception.printStackTrace();
            }
        }

        return true;
    }

    /**
     * Get a subcommand according to its name
     *
     * @param name Name of subcommand to get
     * @return The subcommand that corresponds to the name
     */
    public SubCommand getSubcommand(final String name)
    {
        for(SubCommand subcommand : this.commands)
        {
            if(subcommand.getName().equalsIgnoreCase(name))
            {
                return subcommand;
            }

            List<String> aliases = subcommand.getAliases();

            for(String alias : aliases)
            {
                if(name.equalsIgnoreCase(alias))
                {
                    return subcommand;
                }
            }
        }
        return null;
    }

    /**
     * Gets all subcommand strings for tab completion
     *
     * @param aliases Specify whether aliases are wanted or not
     * @return An <code>ArrayList</code> of strings containing the subcommands.
     */
    public String[] getAllCommandStrings(boolean aliases)
    {
        List<String> strings = new ArrayList<>();
        for(SubCommand command : commands)
        {
            strings.add(command.getName());
            if(aliases) Collections.addAll(strings, command.getAliases().toArray(new String[0]));
        }
        return strings.toArray(new String[0]);
    }

    /**
     * Add a subcommand to the command manager
     *
     * @param subCommand The subcommand to add
     */
    public void addSubcommand(@NotNull final SubCommand subCommand)
    {
        Validate.notNull(subCommand, "Subcommand cannot be null");
        this.commands.add(subCommand);
    }

    /**
     * Remove a subcommand from the command manager based off of the subcommand's name
     *
     * @param name Name of the subcommand
     */
    public void removeSubCommand(@NotNull final String name)
    {
        Validate.notNull(name, "Cannot remove subcommand of null name");
        commands.remove(getSubcommand(name));
    }

    /**
     * Remove a subcommand from the command manager based off of the subcommand object
     *
     * @param subCommand The subcommand to remove
     */
    public void removeSubCommand(SubCommand subCommand)
    {
        Validate.notNull(subCommand, "Cannot remove null subcommand");
        commands.remove(subCommand);
    }

    /**
     * Get the base command
     *
     * @return The base command
     */
    public String getBaseCommand()
    {
        return commandName;
    }

    /**
     * Get the default subcommand
     *
     * @return The default subcommand
     */
    public @Nullable String getDefaultSubCommand()
    {
        return defaultSubCommand;
    }

    /**
     * Set the default subcommand
     *
     * @param defaultSubCommand The new default subcommand
     */
    public void setDefaultSubCommand(@Nullable String defaultSubCommand)
    {
        this.defaultSubCommand = defaultSubCommand;
    }

    /**
     * Automatically fill out the first argument of the command
     *
     * @param sender  The CommandSender that sent the command
     * @param command The command that was sent
     * @param alias   The alias of the command
     * @param args    The command's arguments (subcommands)
     * @return Return a list of autocomplete strings
     */
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, Command command, @NotNull String alias, String[] args)
    {
        if(!command.getName().equalsIgnoreCase(this.commandName)) return null;
        ArrayList<String> commands = new ArrayList<>();
        if(args.length == 1)
        {
            String[] arg1Strings = this.getAllCommandStrings(true);
            Collections.addAll(commands, arg1Strings);
        }
        return commands;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getName()
    {
        return commandName;
    }
}
