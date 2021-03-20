package com.mikedeejay2.mikedeejay2lib.util.chat;

import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.List;

/**
 * A utility class for converting legacy color codes over to their color values.
 * <p>
 * Basically, this is just a fancy encapsulator for {@link ChatColor#translateAlternateColorCodes(char, String)}
 * 
 * @author Mikedeejay2
 */
public final class Colors implements IColorFormatter
{
    /**
     * Format a message using Minecraft's legacy color codes
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
}
