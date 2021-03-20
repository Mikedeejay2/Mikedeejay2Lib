package com.mikedeejay2.mikedeejay2lib.util.chat;

import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.List;

public final class Colors implements IColorFormatter
{
    public static String format(String message)
    {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public static String[] format(String... message)
    {
        String[] result = new String[message.length];
        for(int i = 0; i < message.length; ++i)
        {
            result[i] = format(message[i]);
        }
        return result;
    }

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
