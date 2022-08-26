package com.mikedeejay2.mikedeejay2lib.text;

import org.apache.commons.lang3.Validate;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public final class PlaceholderFormatter {
    private final Map<String, Text> placeholders;

    private PlaceholderFormatter() {
        this.placeholders = new HashMap<>();
    }

    public static PlaceholderFormatter of(String value, Text replacement) {
        return new PlaceholderFormatter().and(value, replacement);
    }

    public static PlaceholderFormatter of(String value, String replacement) {
        return of(value, Text.of(replacement));
    }

    public PlaceholderFormatter and(String value, Text replacement) {
        Validate.notNull(value, "Tried to create placeholder of null value");
        Validate.notNull(replacement, "Tried to create placeholder of null replacement");
        placeholders.put(value, replacement);
        return this;
    }

    public PlaceholderFormatter and(PlaceholderFormatter formatter) {
        Validate.notNull(formatter, "Tried to create placeholder from null placeholder");
        placeholders.putAll(formatter.placeholders);
        return this;
    }

    public PlaceholderFormatter and(String value, String replacement) {
        return and(value, Text.of(replacement));
    }

    public String format(String string) {
        for(String value : placeholders.keySet()) {
            Text replacement = placeholders.get(value);
            string = format(string, value, replacement.get());
        }
        return string;
    }

    public String format(Player player, String string) {
        Validate.notNull(player, "Tried to format a String for a null player");
        String locale = player.getLocale().toLowerCase();
        return format(locale, string);
    }

    public String format(String locale, String string) {
        Validate.notNull(locale, "Tried to format a String for a null locale");
        for(String value : placeholders.keySet()) {
            Text replacement = placeholders.get(value);
            string = format(string, value, replacement.get(locale));
        }
        return string;
    }

    private String format(String string, String value, String replacement) {
        return string.replaceAll("%" + value + "%", replacement);
    }
}
