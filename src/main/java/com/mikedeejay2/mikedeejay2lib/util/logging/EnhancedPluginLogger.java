package com.mikedeejay2.mikedeejay2lib.util.logging;

import org.bukkit.plugin.Plugin;

import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

/**
 * A plugin logger to act similar to a {@link org.bukkit.plugin.PluginLogger} but avoid immutable
 * or invalid prefix names for a Spigot or Paper server.
 *
 * @author Mikedeejay2
 */
public class EnhancedPluginLogger extends Logger
{
    private String prefix;

    /**
     * Creates a new EnhancedPluginLogger that extracts the name from a plugin.
     *
     * @param plugin A reference to the plugin
     */
    public EnhancedPluginLogger(Plugin plugin)
    {
        super("", null);
        String prefix = plugin.getDescription().getPrefix();
        this.prefix = prefix != null ? "[" + prefix + "] " : "[" + plugin.getDescription().getName() + "] ";
        setParent(plugin.getServer().getLogger());
        setLevel(Level.ALL);
    }

    /**
     * Log a <tt>LogRecord</tt> to console
     *
     * @param logRecord The <tt>LogRecord</tt> to log
     */
    @Override
    public void log(LogRecord logRecord)
    {
        logRecord.setMessage(prefix + logRecord.getMessage());
        super.log(logRecord);
    }

    /**
     * Get the prefix of this logger
     *
     * @return The prefix of this logger
     */
    public String getPrefix()
    {
        return prefix;
    }

    /**
     * Set the prefix of this logger. The prefix does not add any spacing, so if
     * spacing is wanted it must be explicitly defined in the String itself.
     *
     * @param prefix The new prefix for the logger to use
     */
    public void setPrefix(String prefix)
    {
        this.prefix = prefix;
    }
}
