package com.mikedeejay2.mikedeejay2lib.util.chat;

import com.mikedeejay2.mikedeejay2lib.PluginBase;
import net.md_5.bungee.api.ChatColor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A 1.16 version of color formatting which also formats hex codes to colors.
 * <p>
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

    public String formatAltColorCodes(String message)
    {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

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

    public String formatAll(String message)
    {
        message = formatHexCodes(message);
        return formatAltColorCodes(message);
    }
}
