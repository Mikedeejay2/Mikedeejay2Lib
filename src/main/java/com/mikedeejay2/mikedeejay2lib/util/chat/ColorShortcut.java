package com.mikedeejay2.mikedeejay2lib.util.chat;

import org.bukkit.ChatColor;

/**
 * An enum that stores "shortcut" names to standard Minecraft color codes.
 * For use, see {@link Colors#formatShortcuts(String)}
 *
 * @author Mikedeejay2
 */
public enum ColorShortcut {
    /**
     * Dark Red:
     * <ul>
     *     <li><code>dark_red</code></li>
     *     <li><code>darkred</code></li>
     *     <li><code>dred</code></li>
     *     <li><code>dr</code></li>
     * </ul>
     */
    DARK_RED(ChatColor.DARK_RED, "dark_red", "darkred", "dred", "dr"),

    /**
     * Red:
     * <ul>
     *     <li><code>red</code></li>
     *     <li><code>r</code></li>
     * </ul>
     */
    RED(ChatColor.RED, "red", "r"),

    /**
     * Gold:
     * <ul>
     *     <li><code>gold</code></li>
     *     <li><code>gld</code></li>
     *     <li><code>gd</code></li>
     * </ul>
     */
    GOLD(ChatColor.GOLD, "gold", "gld", "gd"),

    /**
     * Yellow:
     * <ul>
     *     <li><code>yellow</code></li>
     *     <li><code>y</code></li>
     *     <li><code>ylw</code></li>
     * </ul>
     */
    YELLOW(ChatColor.YELLOW, "yellow", "y", "ylw"),

    /**
     * Dark Green:
     * <ul>
     *     <li><code>dark_green</code></li>
     *     <li><code>darkgreen</code></li>
     *     <li><code>dg</code></li>
     * </ul>
     */
    DARK_GREEN(ChatColor.DARK_GREEN, "dark_green", "darkgreen", "dg"),

    /**
     * Green:
     * <ul>
     *     <li><code>green</code></li>
     *     <li><code>g</code></li>
     * </ul>
     */
    GREEN(ChatColor.GREEN, "green", "g"),

    /**
     * Aqua:
     * <ul>
     *     <li><code>aqua</code></li>
     *     <li><code>a</code></li>
     * </ul>
     */
    AQUA(ChatColor.AQUA, "aqua", "a"),

    /**
     * Dark Aqua:
     * <ul>
     *     <li><code>dark_aqua</code></li>
     *     <li><code>darkaqua</code></li>
     *     <li><code>daqua</code></li>
     *     <li><code>da</code></li>
     * </ul>
     */
    DARK_AQUA(ChatColor.DARK_AQUA, "dark_aqua", "darkaqua", "daqua", "da"),

    /**
     * Dark Blue:
     * <ul>
     *     <li><code>dark_blue</code></li>
     *     <li><code>darkblue</code></li>
     *     <li><code>dblue</code></li>
     *     <li><code>db</code></li>
     * </ul>
     */
    DARK_BLUE(ChatColor.DARK_BLUE, "dark_blue", "darkblue", "dblue", "db"),

    /**
     * Blue:
     * <ul>
     *     <li><code>blue</code></li>
     *     <li><code>b</code></li>
     * </ul>
     */
    BLUE(ChatColor.BLUE, "blue", "b"),

    /**
     * Light Purple:
     * <ul>
     *     <li><code>light_purple</code></li>
     *     <li><code>lightpurple</code></li>
     *     <li><code>lpurple</code></li>
     *     <li><code>lp</code></li>
     * </ul>
     */
    LIGHT_PURPLE(ChatColor.LIGHT_PURPLE, "light_purple", "lightpurple", "lpurple", "lp"),

    /**
     * Dark Purple:
     * <ul>
     *     <li><code>dark_purple</code></li>
     *     <li><code>darkpurple</code></li>
     *     <li><code>dpurple</code></li>
     *     <li><code>dp</code></li>
     * </ul>
     */
    DARK_PURPLE(ChatColor.DARK_PURPLE, "dark_purple", "darkpurple", "dpurple", "dp"),

    /**
     * White:
     * <ul>
     *     <li><code>white</code></li>
     *     <li><code>w</code></li>
     * </ul>
     */
    WHITE(ChatColor.WHITE, "white", "w"),

    /**
     * Gray:
     * <ul>
     *     <li><code>gray</code></li>
     *     <li><code>gr</code></li>
     * </ul>
     */
    GRAY(ChatColor.GRAY, "gray", "gr"),

    /**
     * Dark Gray:
     * <ul>
     *     <li><code>dark_gray</code></li>
     *     <li><code>darkgray</code></li>
     *     <li><code>dgray</code></li>
     *     <li><code>dgr</code></li>
     * </ul>
     */
    DARK_GRAY(ChatColor.DARK_GRAY, "dark_gray", "darkgray", "dgray", "dgr"),

    /**
     * Black:
     * <ul>
     *     <li><code>black</code></li>
     *     <li><code>blc</code></li>
     *     <li><code>bl</code></li>
     * </ul>
     */
    BLACK(ChatColor.BLACK, "black", "blc", "bl"),

    /**
     * Magic:
     * <ul>
     *     <li><code>magic</code></li>
     *     <li><code>mgc</code></li>
     *     <li><code>mc</code></li>
     * </ul>
     */
    MAGIC(ChatColor.MAGIC, "magic", "mgc", "mc"),

    /**
     * Bold:
     * <ul>
     *     <li><code>bold</code></li>
     *     <li><code>bld</code></li>
     *     <li><code>bd</code></li>
     * </ul>
     */
    BOLD(ChatColor.BOLD, "bold", "bld", "bd"),

    /**
     * Bold:
     * <ul>
     *     <li><code>strikethrough</code></li>
     *     <li><code>sthrough</code></li>
     *     <li><code>sth</code></li>
     *     <li><code>sthru</code></li>
     *     <li><code>st</code></li>
     *     <li><code>strike</code></li>
     * </ul>
     */
    STRIKETHROUGH(ChatColor.STRIKETHROUGH, "strikethrough", "sthrough", "sth", "sthru", "st", "strike"),

    /**
     * Underline:
     * <ul>
     *     <li><code>underline</code></li>
     *     <li><code>ul</code></li>
     *     <li><code>under</code></li>
     *     <li><code>underl</code></li>
     * </ul>
     */
    UNDERLINE(ChatColor.UNDERLINE, "underline", "ul", "under", "underl"),

    /**
     * Italic:
     * <ul>
     *     <li><code>italic</code></li>
     *     <li><code>itlc</code></li>
     *     <li><code>it</code></li>
     * </ul>
     */
    ITALIC(ChatColor.ITALIC, "italic", "itlc", "it"),

    /**
     * Reset:
     * <ul>
     *     <li><code>reset</code></li>
     *     <li><code>cls</code></li>
     *     <li><code>rs</code></li>
     * </ul>
     */
    RESET(ChatColor.RESET, "reset", "cls", "rs"),
    ;

    /**
     * All shortcut names associated with this color
     */
    private final String[] names;

    /**
     * The {@link ChatColor} associated with this color
     */
    private final ChatColor chatColor;

    /**
     * @param allNames All shortcut names associated with this color
     * @param chatColor The {@link ChatColor} associated with this color
     */
    ColorShortcut(ChatColor chatColor, String... allNames) {
        this.names = allNames;
        this.chatColor = chatColor;
    }

    /**
     * Get all shortcut names associated with this color
     *
     * @return All shortcut names associated with this color
     */
    public String[] getNames() {
        return names;
    }

    /**
     * Get the {@link ChatColor} associated with this color
     *
     * @return The {@link ChatColor} associated with this color
     */
    public ChatColor getChatColor() {
        return chatColor;
    }

    /**
     * Get the {@link ColorShortcut} associated with a shortcut String
     *
     * @param shortcut The shortcut name to find
     * @return The located {@link ColorShortcut}, null if not found
     */
    public static ColorShortcut getShortcut(String shortcut) {
        for(ColorShortcut cur : ColorShortcut.values()) {
            String[] aliases = cur.getNames();
            for(String alias : aliases) {
                if(shortcut.equals(alias)) return cur;
            }
        }
        return null;
    }
}
