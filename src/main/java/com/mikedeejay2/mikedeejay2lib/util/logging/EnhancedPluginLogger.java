package com.mikedeejay2.mikedeejay2lib.util.logging;

import org.apache.logging.log4j.Logger;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginLogger;

import java.util.logging.Level;
import java.util.logging.LogRecord;
import org.apache.logging.log4j.LogManager;

/**
 * A plugin logger to act similar to a {@link org.bukkit.plugin.PluginLogger} but avoid immutable
 * or invalid prefix names for a Spigot or Paper server.
 *
 * @author Mikedeejay2
 */
public class EnhancedPluginLogger extends PluginLogger
{
    private final Logger logger;
    private String prefix;

    /**
     * Creates a new EnhancedPluginLogger that extracts the name from a plugin.
     *
     * @param plugin A reference to the plugin
     */
    public EnhancedPluginLogger(Plugin plugin)
    {
        super(plugin);
        this.logger = LogManager.getRootLogger();
        String prefix = plugin.getDescription().getPrefix();
        this.prefix = prefix != null ? "[" + prefix + "] " : "[" + plugin.getDescription().getName() + "] ";
    }

    /**
     * Log a <code>LogRecord</code> to console
     *
     * @param logRecord The <code>LogRecord</code> to log
     */
    @Override
    public void log(LogRecord logRecord)
    {
        logRecord.setMessage(prefix + logRecord.getMessage());
        Level level = logRecord.getLevel();
        String message = logRecord.getMessage();
        Throwable exception = logRecord.getThrown();

        // Taken from org.bukkit.craftbukkit.util.ForwardLogHandler#publish(LogRecord record)
        if(level == Level.SEVERE)
        {
            logger.error(message, exception);
        }
        else if(level == Level.WARNING)
        {
            logger.warn(message, exception);
        }
        else if(level == Level.INFO)
        {
            logger.info(message, exception);
        }
        else if(level == Level.CONFIG)
        {
            logger.debug(message, exception);
        }
        else
        {
            logger.trace(message, exception);
        }
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
