package com.mikedeejay2.mikedeejay2lib.util.chat;

import com.mikedeejay2.mikedeejay2lib.BukkitPlugin;
import com.mikedeejay2.mikedeejay2lib.util.version.MinecraftVersion;
import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A utility class for converting legacy color codes over to their color values.
 * <p>
 * This class does the following:
 * <ul>
 *     <li>Convert alternate color codes to their respective colors
 *     (<code>"&amp;c"</code> -&gt; red, <code>"&amp;1"</code> -&gt; blue)</li>
 *     <li>Convert hex codes to their respective colors if the version if 1.16 or above
 *     (<code>#ff0000</code> -&gt; red, <code>#0000ff</code> -&gt; blue)</li>
 *     <li>Convert color shortcuts (see {@link ColorShortcut}) to their respective colors
 *     (<code>%red%</code> -&gt; red, <code>%blue%</code> -&gt; blue)</li>
 *     <li>Ability to use placeholder formatting for hex codes and color shortcuts
 *     (<code>%ff0000%</code> -&gt; red, <code>%b%</code> -&gt; blue)</li>
 *     <li>Ability to format entire arrays or Lists at one time</li>
 * </ul>
 * 
 * @author Mikedeejay2
 */
public final class Colors
{
    /**
     * Pattern to find hex codes in a message
     */
    private static final Pattern pattern1 = Pattern.compile("#[a-fA-f0-9]{6}");

    /**
     * Pattern to find hex codes in a placeholder in a message
     */
    private static final Pattern pattern2 = Pattern.compile("%[a-fA-f0-9]{6}%");

    /**
     * Pattern to find any text between a placeholder in a message (for getting ColorShortcuts)
     */
    private static final Pattern pattern3 = Pattern.compile("%(.*?)%");

    /**
     * Format a message using Minecraft's legacy color codes
     * <p>
     * For example, <code>"&amp;c"</code> will become a red color, <code>"&amp;1"</code> will become a blue color.
     *
     * @param message The input string to be formatted
     * @return The string formatted with Minecraft color codes
     */
    public static String format(String message)
    {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    /**
     * Format an array of messages using Minecraft's legacy color codes
     * <p>
     * For example, <code>"&amp;c"</code> will become a red color, <code>"&amp;1"</code> will become a blue color.
     *
     * @param messages The input array of strings to be formatted
     * @return The string array formatted with Minecraft color codes
     */
    public static String[] format(String... messages)
    {
        String[] result = new String[messages.length];
        for(int i = 0; i < messages.length; ++i)
        {
            result[i] = format(messages[i]);
        }
        return result;
    }

    /**
     * Format a list of messages using Minecraft's legacy color codes
     * <p>
     * For example, <code>"&amp;c"</code> will become a red color, <code>"&amp;1"</code> will become a blue color.
     *
     * @param messages The input list of strings to be formatted
     * @return The string list formatted with Minecraft color codes
     */
    public static List<String> format(List<String> messages)
    {
        List<String> result = new ArrayList<>();
        for(String message : messages)
        {
            result.add(format(message));
        }
        return result;
    }

    /**
     * Format a message using Minecraft's legacy color codes
     * <p>
     * For example, <code>"&amp;c"</code> will become a red color, <code>"&amp;1"</code> will become a blue color.
     * <p>
     * Also add a reset to the beginning of the text. Used for negating Minecraft's default lore italics
     *
     * @param message The input string to be formatted
     * @return The string formatted with Minecraft color codes
     */
    public static String formatR(String message)
    {
        return ChatColor.RESET + format(message);
    }

    /**
     * Format an array of messages using Minecraft's legacy color codes
     * <p>
     * For example, <code>"&amp;c"</code> will become a red color, <code>"&amp;1"</code> will become a blue color.
     *
     * @param messages The input array of strings to be formatted
     * @return The string array formatted with Minecraft color codes
     */
    public static String[] formatR(String... messages)
    {
        String[] result = new String[messages.length];
        for(int i = 0; i < messages.length; ++i)
        {
            result[i] = formatR(messages[i]);
        }
        return result;
    }

    /**
     * Format a list of messages using Minecraft's legacy color codes
     * <p>
     * For example, <code>"&amp;c"</code> will become a red color, <code>"&amp;1"</code> will become a blue color.
     * <p>
     * Also add a reset to the beginning of the text. Used for negating Minecraft's default lore italics
     *
     * @param messages The input list of strings to be formatted
     * @return The string list formatted with Minecraft color codes
     */
    public static List<String> formatR(List<String> messages)
    {
        List<String> result = new ArrayList<>();
        for(String message : messages)
        {
            result.add(formatR(message));
        }
        return result;
    }

    /**
     * Format all hex color codes in the message.
     * <p>
     * For example, <code>#ff0000</code> will become a red color, <code>#0000ff</code> will become a blue color.
     * <p>
     * Color codes are converted using {@link net.md_5.bungee.api.ChatColor#of(String)}
     * <p>
     * If the Minecraft version is less than 1.16 (aka unsupported), hex colors will only be removed but formatting
     * will not be applied, as there was no alternative below 1.16.
     *
     * @param message The message to have hex codes translated into their respective colors
     * @return The formatted message
     */
    public static String formatHexCodes(String message)
    {
        boolean hexSupported = MinecraftVersion.getVersionShort() >= 16;
        Matcher match1 = pattern1.matcher(message);
        while(match1.find())
        {
            String curColor = message.substring(match1.start(), match1.end());
            message = message.replace(curColor, hexSupported ? net.md_5.bungee.api.ChatColor.of(curColor) + "" : "");
            match1 = pattern1.matcher(message);
        }
        Matcher match2 = pattern2.matcher(message);
        while(match2.find())
        {
            String preColor = message.substring(match2.start(), match2.end());
            String curColor = "#" + preColor.substring(1, preColor.length() - 1);
            message = message.replace(preColor, hexSupported ? net.md_5.bungee.api.ChatColor.of(curColor) + "" : "");
            match2 = pattern2.matcher(message);
        }
        return message;
    }

    /**
     * Format all color shortcuts in the message.
     * <p>
     * For example, <code>%red%</code> will become a red color, <code>%b%</code> will become a blue color.
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
     * For example, <code>"&amp;c"</code> would be an alternate color code that would translate to red,
     * while <code>#0000ff</code> would be a hex code that would translate to a blue color.
     * Placeholders can also be used for hex and color shortcuts, such as <code>%ff0000%</code> would
     * translate to red while <code>%blue%</code> would translate to blue.
     * <p>
     * If the Minecraft version is less than 1.16 (aka unsupported), hex colors will only be removed but formatting
     * will not be applied, as there was no alternative below 1.16.
     *
     * @param message The message to have all color codes translated into their respective colors
     * @return The formatted message
     */
    public static String formatAll(String message)
    {
        message = formatHexCodes(message);
        message = formatShortcuts(message);
        message = format(message);
        return message;
    }

    /**
     * Format hex, alternate color codes, AND color shortcuts.
     * <p>
     * For example, <code>"&amp;c"</code> would be an alternate color code that would translate to red,
     * while <code>#0000ff</code> would be a hex code that would translate to a blue color.
     * Placeholders can also be used for hex and color shortcuts, such as <code>%ff0000%</code> would
     * translate to red while <code>%blue%</code> would translate to blue.
     * <p>
     * If the Minecraft version is less than 1.16 (aka unsupported), hex colors will only be removed but formatting
     * will not be applied, as there was no alternative below 1.16.
     *
     * @param messages The array of messages to have all color codes translated into their respective colors
     * @return The formatted array
     */
    public static String[] formatAll(String... messages)
    {
        String[] result = new String[messages.length];
        for(int i = 0; i < messages.length; ++i)
        {
            result[i] = formatAll(messages[i]);
        }
        return result;
    }

    /**
     * Format hex, alternate color codes, AND color shortcuts.
     * <p>
     * For example, <code>"&amp;c"</code> would be an alternate color code that would translate to red,
     * while <code>#0000ff</code> would be a hex code that would translate to a blue color.
     * Placeholders can also be used for hex and color shortcuts, such as <code>%ff0000%</code> would
     * translate to red while <code>%blue%</code> would translate to blue.
     * <p>
     * If the Minecraft version is less than 1.16 (aka unsupported), hex colors will only be removed but formatting
     * will not be applied, as there was no alternative below 1.16.
     *
     * @param messages The array of messages to have all color codes translated into their respective colors
     * @return The formatted array
     */
    public static List<String> formatAll(List<String> messages)
    {
        List<String> result = new ArrayList<>();
        for(String message : messages)
        {
            result.add(formatAll(message));
        }
        return result;
    }
}
