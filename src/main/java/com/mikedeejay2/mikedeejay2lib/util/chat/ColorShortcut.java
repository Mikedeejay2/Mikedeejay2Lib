package com.mikedeejay2.mikedeejay2lib.util.chat;

import org.bukkit.ChatColor;

/**
 * An enum that stores "shortcut" names to standard Minecraft color codes.
 * For use, see {@link ColorFormatterAdvanced#formatShortcuts(String)}
 *
 * @author Mikedeejay2
 */
public enum ColorShortcut
{
    DARK_RED("dark_red", "darkred", "dred", "dr"),
    RED("red", "r"),
    GOLD("gold", "gld", "gd"),
    YELLOW("yellow", "y", "ylw"),
    DARK_GREEN("dark_green", "darkgreen", "dg"),
    GREEN("green", "g"),
    AQUA("aqua", "a"),
    DARK_AQUA("dark_aqua", "darkaqua", "daqua", "da"),
    DARK_BLUE("dark_blue", "darkblue", "dblue", "db"),
    BLUE("blue", "b"),
    LIGHT_PURPLE("light_purple", "lightpurple", "lpurple", "lp"),
    DARK_PURPLE("dark_purple", "darkpurple", "dpurple", "dp"),
    WHITE("white", "w"),
    GRAY("gray", "gr"),
    DARK_GRAY("dark_gray", "darkgray", "dgray", "dgr"),
    BLACK("black", "blc", "bl"),
    MAGIC("magic", "mgc", "mc"),
    BOLD("bold", "bld"),
    STRIKETHROUGH("strikethrough", "sthrough", "sth", "sthru", "st", "strike"),
    UNDERLINE("underline", "ul", "under", "underl"),
    ITALIC("italic", "itlc", "it"),
    RESET("reset", "cls", "rs"),
    ;

    private final String[] names;

    ColorShortcut(String... allNames)
    {
        this.names = allNames;
    }

    public String[] getNames()
    {
        return names;
    }

    public ChatColor getChatColor()
    {
        return ChatColor.valueOf(this.toString());
    }

    public static ColorShortcut getShortcut(String shortcut)
    {
        for(ColorShortcut cur : ColorShortcut.values())
        {
            String[] aliases = cur.getNames();
            for(String alias : aliases)
            {
                if(shortcut.equals(alias)) return cur;
            }
        }
        return null;
    }
}
