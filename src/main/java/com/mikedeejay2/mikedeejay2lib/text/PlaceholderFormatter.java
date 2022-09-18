package com.mikedeejay2.mikedeejay2lib.text;

import org.apache.commons.lang3.Validate;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

/**
 * Formats Strings with configurable and stackable placeholders.
 * <p>
 * Usage: <code>%placeholder% -&gt; replacement</code>
 * <p>
 * Example: <code>"TPS: %tps%, MSPT: %mspt%" -&gt; "TPS: 19.99, MSPT: 10.53</code>
 *
 * @author Mikedeejay2
 */
public final class PlaceholderFormatter implements Cloneable {
    /**
     * The Map of String placeholder keys to {@link Text} replacement values.
     */
    private Map<String, Text> placeholders;

    private PlaceholderFormatter() {
        this.placeholders = new HashMap<>();
    }

    /**
     * Get a <code>PlaceholderFormatter</code> of a specified key and replacement
     *
     * @param key The placeholder key to find
     * @param replacement The replacement {@link Text}
     * @return The new <code>PlaceholderFormatter</code>
     */
    public static PlaceholderFormatter of(String key, Text replacement) {
        return new PlaceholderFormatter().and(key, replacement);
    }

    /**
     * Get a <code>PlaceholderFormatter</code> of a specified key and replacement
     *
     * @param key The placeholder key to find
     * @param replacement The replacement String
     * @return The new <code>PlaceholderFormatter</code>
     */
    public static PlaceholderFormatter of(String key, String replacement) {
        return of(key, Text.of(replacement));
    }

    /**
     * Add a key to this formatter
     *
     * @param key The placeholder key to find
     * @param replacement The replacement {@link Text}
     * @return This <code>PlaceholderFormatter</code>
     */
    public PlaceholderFormatter and(String key, Text replacement) {
        Validate.notNull(key, "Tried to create placeholder of null key");
        Validate.notNull(replacement, "Tried to create placeholder of null replacement");
        placeholders.put(key, replacement);
        return this;
    }

    /**
     * Add all keys from another <code>PlaceholderFormatter</code> to this formatter
     *
     * @param formatter The <code>PlaceholderFormatter</code> to add
     * @return This <code>PlaceholderFormatter</code>
     */
    public PlaceholderFormatter and(PlaceholderFormatter formatter) {
        Validate.notNull(formatter, "Tried to create placeholder from null placeholder");
        placeholders.putAll(formatter.placeholders);
        return this;
    }

    /**
     * Add a key to this formatter
     *
     * @param key The placeholder key to find
     * @param replacement The replacement String
     * @return This <code>PlaceholderFormatter</code>
     */
    public PlaceholderFormatter and(String key, String replacement) {
        return and(key, Text.of(replacement));
    }

    /**
     * Format a String with this formatter
     *
     * @param string The String to be formatted
     * @return The formatted String
     */
    public String format(String string) {
        for(String value : placeholders.keySet()) {
            string = format(string, value, placeholders.get(value).get());
        }
        return string;
    }

    /**
     * Format a String with this formatter
     *
     * @param player The player, for use with translatable text
     * @param string The String to be formatted
     * @return The formatted String
     */
    public String format(Player player, String string) {
        Validate.notNull(player, "Tried to format a String for a null player");
        for(String value : placeholders.keySet()) {
            string = format(string, value, placeholders.get(value).get(player));
        }
        return string;
    }

    /**
     * Format a String with this formatter
     *
     * @param sender The <code>CommandSender</code>, for use with translatable text
     * @param string The String to be formatted
     * @return The formatted String
     */
    public String format(CommandSender sender, String string) {
        Validate.notNull(sender, "Tried to format a String for a null CommandSender");
        for(String value : placeholders.keySet()) {
            string = format(string, value, placeholders.get(value).get(sender));
        }
        return string;
    }

    /**
     * Format a String with this formatter
     *
     * @param locale The locale, for use with translatable text
     * @param string The String to be formatted
     * @return The formatted String
     */
    public String format(String locale, String string) {
        Validate.notNull(locale, "Tried to format a String for a null locale");
        for(String value : placeholders.keySet()) {
            string = format(string, value, placeholders.get(value).get(locale));
        }
        return string;
    }

    /**
     * The internal format method. Find all placeholders named <code>key</code> and replace them.
     *
     * @param string The String to format
     * @param key The key of the placeholders to replace
     * @param replacement The replacement String
     * @return The formatted String
     */
    private String format(String string, String key, String replacement) {
        return string.replaceAll("%" + key + "%", replacement);
    }

    /**
     * Clone this <code>PlaceholderFormatter</code>
     *
     * @return The cloned <code>PlaceholderFormatter</code>
     */
    @Override
    public PlaceholderFormatter clone() {
        PlaceholderFormatter formatter;
        try {
            formatter = (PlaceholderFormatter) super.clone();
        } catch(CloneNotSupportedException e) {
            e.printStackTrace();
            return null;
        }
        formatter.placeholders = new HashMap<>(placeholders);
        return formatter;
    }
}
