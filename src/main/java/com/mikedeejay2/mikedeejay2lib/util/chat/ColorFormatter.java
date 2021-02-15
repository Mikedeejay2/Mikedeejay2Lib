package com.mikedeejay2.mikedeejay2lib.util.chat;

import com.mikedeejay2.mikedeejay2lib.PluginBase;
import net.md_5.bungee.api.ChatColor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A more advanced form of {@link Chat#chat(String)} that does the following:
 * <ul>
 *     <li>Convert alternate color codes to their respective colors</li>
 *     <li>Convert hex codes to their respective colors if the version if 1.16 or above</li>
 * </ul>
 *
 * @author Mikedeejay2
 */
public final class ColorFormatter
{
    private final PluginBase plugin;
    private final Pattern pattern = Pattern.compile("#[a-fA-f0-9]{6}");

    public ColorFormatter(PluginBase plugin)
    {
        this.plugin = plugin;
    }

    /**
     * Format all alternative color codes in the message.
     * <p>
     * For example, <tt>&c</tt> will become a red color, <tt>&1</tt> will become a blue color.
     * <p>
     * Color codes are converted using {@link ChatColor#translateAlternateColorCodes(char, String)}
     * 
     * @param message The message to have alternate (&) color codes translated to their respective colors
     * @return The formatted message
     */
    public String formatAltColorCodes(String message)
    {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    /**
     * Format all hex color codes in the message.
     * <p>
     * For example, <tt>#ff0000</tt> will become a red color, <tt>#0000ff</tt> will become a blue color.
     * <p>
     * Color codes are converted using {@link ChatColor#of(String)}
     * <p>
     * If the Minecraft version is less than 1.16 (aka unsupported), hex colors will only be removed but formatting
     * will not be applied, as there was no alternative below 1.16.
     * 
     * @param message The message to have hex codes translated into their respective colors
     * @return The formatted message
     */
    public String formatHexCodes(String message)
    {
        boolean hexSupported = plugin.getMCVersion().getVersionShort() >= 16;
        Matcher match = pattern.matcher(message);
        while(match.find())
        {
            String curColor = message.substring(match.start(), match.end());
            message = message.replace(curColor, hexSupported ? ChatColor.of(curColor) + "" : "");
        }
        return message;
    }

    /**
     * Format both hex and alternate color codes in the message.
     * <p>
     * For example, <tt>&c</tt> would be an alternate color code that would translate to red,
     * while <tt>#0000ff</tt> would be a hex code that would translate to a blue color.
     * <p>
     * If the Minecraft version is less than 1.16 (aka unsupported), hex colors will only be removed but formatting
     * will not be applied, as there was no alternative below 1.16.
     *
     * @param message The message to have all color codes translated into their respective colors
     * @return The formatted message
     */
    public String formatAll(String message)
    {
        message = formatHexCodes(message);
        return formatAltColorCodes(message);
    }
}
