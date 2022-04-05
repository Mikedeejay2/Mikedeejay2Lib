package com.mikedeejay2.mikedeejay2lib.util.logging;

import org.bukkit.ChatColor;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginLogger;
import org.fusesource.jansi.Ansi;

import java.awt.*;
import java.util.EnumMap;
import java.util.Map;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A plugin logger to act similar to a {@link org.bukkit.plugin.PluginLogger} but avoid immutable
 * or invalid prefix names for a Spigot or Paper server.
 *
 * Largely based off of ColouredConsoleSender
 *
 * @author CraftBukkit, Mikedeejay2
 */
public class EnhancedPluginLogger extends PluginLogger
{
    /**
     * The global logger
     */
    private final Logger logger;

    /**
     * The list of ANSI replacements for <code>ChatColors</code>
     */
    private final Map<ChatColor, String> replacements;

    /**
     * Array of all <code>ChatColor</code> values
     */
    private final ChatColor[] colors = ChatColor.values();

    /**
     * Pattern to translate RGB values
     */
    private static final Pattern RBG_TRANSLATE = Pattern.compile(ChatColor.COLOR_CHAR + "x(" + ChatColor.COLOR_CHAR + "[A-F0-9]){6}", Pattern.CASE_INSENSITIVE);

    /**
     * The RGB formatting String
     */
    private static final String RGB_STRING = "\u001B[38;2;%d;%d;%dm";

    /**
     * The prefix of this plugin logger
     */
    private String prefix;

    /**
     * Creates a new EnhancedPluginLogger that extracts the name from a plugin.
     *
     * @param plugin A reference to the plugin
     */
    public EnhancedPluginLogger(Plugin plugin)
    {
        super(plugin);
        this.logger = Logger.getGlobal();
        this.replacements = new EnumMap<>(ChatColor.class);
        String prefix = plugin.getDescription().getPrefix();
        this.prefix = prefix != null ? "[" + prefix + "] " : "[" + plugin.getDescription().getName() + "] ";

        // CraftBukkit ColouredConsoleSender
        replacements.put(ChatColor.BLACK, Ansi.ansi().a(Ansi.Attribute.RESET).fg(Ansi.Color.BLACK).boldOff().toString());
        replacements.put(ChatColor.DARK_BLUE, Ansi.ansi().a(Ansi.Attribute.RESET).fg(Ansi.Color.BLUE).boldOff().toString());
        replacements.put(ChatColor.DARK_GREEN, Ansi.ansi().a(Ansi.Attribute.RESET).fg(Ansi.Color.GREEN).boldOff().toString());
        replacements.put(ChatColor.DARK_AQUA, Ansi.ansi().a(Ansi.Attribute.RESET).fg(Ansi.Color.CYAN).boldOff().toString());
        replacements.put(ChatColor.DARK_RED, Ansi.ansi().a(Ansi.Attribute.RESET).fg(Ansi.Color.RED).boldOff().toString());
        replacements.put(ChatColor.DARK_PURPLE, Ansi.ansi().a(Ansi.Attribute.RESET).fg(Ansi.Color.MAGENTA).boldOff().toString());
        replacements.put(ChatColor.GOLD, Ansi.ansi().a(Ansi.Attribute.RESET).fg(Ansi.Color.YELLOW).boldOff().toString());
        replacements.put(ChatColor.GRAY, Ansi.ansi().a(Ansi.Attribute.RESET).fg(Ansi.Color.WHITE).boldOff().toString());
        replacements.put(ChatColor.DARK_GRAY, Ansi.ansi().a(Ansi.Attribute.RESET).fg(Ansi.Color.BLACK).bold().toString());
        replacements.put(ChatColor.BLUE, Ansi.ansi().a(Ansi.Attribute.RESET).fg(Ansi.Color.BLUE).bold().toString());
        replacements.put(ChatColor.GREEN, Ansi.ansi().a(Ansi.Attribute.RESET).fg(Ansi.Color.GREEN).bold().toString());
        replacements.put(ChatColor.AQUA, Ansi.ansi().a(Ansi.Attribute.RESET).fg(Ansi.Color.CYAN).bold().toString());
        replacements.put(ChatColor.RED, Ansi.ansi().a(Ansi.Attribute.RESET).fg(Ansi.Color.RED).bold().toString());
        replacements.put(ChatColor.LIGHT_PURPLE, Ansi.ansi().a(Ansi.Attribute.RESET).fg(Ansi.Color.MAGENTA).bold().toString());
        replacements.put(ChatColor.YELLOW, Ansi.ansi().a(Ansi.Attribute.RESET).fg(Ansi.Color.YELLOW).bold().toString());
        replacements.put(ChatColor.WHITE, Ansi.ansi().a(Ansi.Attribute.RESET).fg(Ansi.Color.WHITE).bold().toString());
        replacements.put(ChatColor.MAGIC, Ansi.ansi().a(Ansi.Attribute.BLINK_SLOW).toString());
        replacements.put(ChatColor.BOLD, Ansi.ansi().a(Ansi.Attribute.UNDERLINE_DOUBLE).toString());
        replacements.put(ChatColor.STRIKETHROUGH, Ansi.ansi().a(Ansi.Attribute.STRIKETHROUGH_ON).toString());
        replacements.put(ChatColor.UNDERLINE, Ansi.ansi().a(Ansi.Attribute.UNDERLINE).toString());
        replacements.put(ChatColor.ITALIC, Ansi.ansi().a(Ansi.Attribute.ITALIC).toString());
        replacements.put(ChatColor.RESET, Ansi.ansi().a(Ansi.Attribute.RESET).toString());
    }

    /**
     * Log a <code>LogRecord</code> to console
     *
     * @param logRecord The <code>LogRecord</code> to log
     */
    @Override
    public void log(LogRecord logRecord)
    {
        String message = prefix + logRecord.getMessage();
        String result = convertRGBColors(message);
        result = convertColorCodes(result);
        logRecord.setLoggerName("");
        logRecord.setMessage(result + Ansi.ansi().reset().toString());
        logger.log(logRecord);
    }

    /**
     * Convert Minecraft's color codes to RGB colors. From CraftBukkit ColouredConsoleSender
     *
     * @param input The original message input
     * @return The converted output
     */
    private String convertColorCodes(String input) {
        for (ChatColor color : colors) {
            input = input.replaceAll(
                "(?i)" + color.toString(),
                replacements.getOrDefault(color, ""));
        }
        return input;
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

    /**
     * Convert Minecraft's RGB color codes to RGB colors. From CraftBukkit ColouredConsoleSender
     *
     * @param input The original message input
     * @return The converted output
     */
    private static String convertRGBColors(String input) {
        Matcher matcher = RBG_TRANSLATE.matcher(input);
        StringBuffer buffer = new StringBuffer();
        while (matcher.find()) {
            String s = matcher.group().replace("§", "").replace('x', '#');
            Color color = Color.decode(s);
            int red = color.getRed();
            int blue = color.getBlue();
            int green = color.getGreen();
            String replacement = String.format(RGB_STRING, red, green, blue);
            matcher.appendReplacement(buffer, replacement);
        }
        matcher.appendTail(buffer);
        return buffer.toString();
    }
}
