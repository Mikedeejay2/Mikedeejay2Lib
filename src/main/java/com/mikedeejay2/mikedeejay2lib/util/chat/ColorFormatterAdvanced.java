package com.mikedeejay2.mikedeejay2lib.util.chat;

import com.mikedeejay2.mikedeejay2lib.PluginBase;
import net.md_5.bungee.api.ChatColor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A more advanced form of {@link Chat#chat(String)} that does the following:
 * <ul>
 *     <li>Convert alternate color codes to their respective colors (<tt>"*AND*c"</tt> = red, <tt>"*AND*1"</tt> = blue)</li>
 *     <li>Convert hex codes to their respective colors if the version if 1.16 or above (<tt>#ff0000</tt> = red, <tt>#0000ff</tt> = blue)</li>
 *     <li>Convert color shortcuts (see {@link ColorShortcut}) to their respective colors (<tt>%red%</tt> = red, <tt>%blue%</tt> = blue)</li>
 *     <li>Ability to use placeholder formatting for hex codes and color shortcuts (<tt>%ff0000%</tt> = red, <tt>%b%</tt> = blue)</li>
 * </ul>
 *
 * @author Mikedeejay2
 */
public final class ColorFormatterAdvanced
{
    private final PluginBase plugin;

    // Pattern to find hex codes in a message
    private static final Pattern pattern1 = Pattern.compile("#[a-fA-f0-9]{6}");
    // Pattern to find hex codes in a placeholder in a message
    private static final Pattern pattern2 = Pattern.compile("%[a-fA-f0-9]{6}%");
    // Pattern to find any text between a placeholder in a message (for getting ColorShortcuts)
    private static final Pattern pattern3 = Pattern.compile("%(.*?)%");

    public ColorFormatterAdvanced(PluginBase plugin)
    {
        this.plugin = plugin;
    }

    /**
     * Format all alternative color codes in the message.
     * <p>
     * For example, <tt>"*AND*c"</tt> will become a red color, <tt>"*AND*1"</tt> will become a blue color.
     * <p>
     * Color codes are converted using {@link ChatColor#translateAlternateColorCodes(char, String)}
     * 
     * @param message The message to have alternate (The AND symbol) color codes translated to their respective colors
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
        Matcher match1 = pattern1.matcher(message);
        while(match1.find())
        {
            String curColor = message.substring(match1.start(), match1.end());
            message = message.replace(curColor, hexSupported ? ChatColor.of(curColor) + "" : "");
            match1 = pattern1.matcher(message);
        }
        Matcher match2 = pattern2.matcher(message);
        while(match2.find())
        {
            String preColor = message.substring(match2.start(), match2.end());
            String curColor = "#" + preColor.substring(1, preColor.length() - 1);
            message = message.replace(preColor, hexSupported ? ChatColor.of(curColor) + "" : "");
            match2 = pattern2.matcher(message);
        }
        return message;
    }

    /**
     * Format all color shortcuts in the message.
     * <p>
     * For example, <tt>%red%</tt> will become a red color, <tt>%b%</tt> will become a blue color.
     * See {@link ColorShortcut} enum for all shortcuts.
     *
     * @see ColorShortcut
     *
     * @param message The message to have color shortcuts translated into their respective colors
     * @return The formatted message
     */
    public static String formatShortcuts(String message)
    {
        Matcher match3 = pattern3.matcher(message);
        while(match3.find())
        {
            String preColor = message.substring(match3.start(), match3.end());
            String curColor = preColor.substring(1, preColor.length() - 1);
            ColorShortcut shortcut = ColorShortcut.getShortcut(curColor);
            if(shortcut == null) continue;
            org.bukkit.ChatColor color = shortcut.getChatColor();
            message = message.replace(preColor, color + "");
            match3 = pattern3.matcher(message);
        }
        return message;
    }

    /**
     * Format hex, alternate color codes, AND color shortcuts.
     * <p>
     * For example, <tt>"*AND*c"</tt> would be an alternate color code that would translate to red,
     * while <tt>#0000ff</tt> would be a hex code that would translate to a blue color.
     * Placeholders can also be used for hex and color shortcuts, such as <tt>%ff0000%</tt> would
     * translate to red while <tt>%blue%</tt> would translate to blue.
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
        message = formatShortcuts(message);
        message = formatAltColorCodes(message);
        return message;
    }
}
