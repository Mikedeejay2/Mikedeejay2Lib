package com.mikedeejay2.mikedeejay2lib.util.chat;

import net.md_5.bungee.api.ChatColor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A 1.16 version of color formatting which also formats hex codes to colors.
 * <p>
 * <b>WARNING: </b> This class does not check the Minecraft version! The version
 * must be checked before using <tt>formatAll()</tt> or <tt>formatHexCodes()</tt>
 *
 * @author Mikedeejay2
 */
public final class ColorFormatter
{
    private static final Pattern pattern = Pattern.compile("#[a-fA-f0-9]{6}");

    public static String formatAltColorCodes(String message)
    {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public static String formatHexCodes(String message)
    {
        Matcher match = pattern.matcher(message);
        while(match.find())
        {
            String curColor = message.substring(match.start(), match.end());
            message = message.replace(curColor, ChatColor.of(curColor) + "");
        }
        return message;
    }

    public static String formatAll(String message)
    {
        message = formatHexCodes(message);
        return formatAltColorCodes(message);
    }
}
