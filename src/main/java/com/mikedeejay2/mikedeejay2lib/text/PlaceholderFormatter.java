package com.mikedeejay2.mikedeejay2lib.text;

import org.apache.commons.lang3.Validate;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public final class PlaceholderFormatter implements Cloneable {
    private Map<String, Text> placeholders;

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
            string = format(string, value, placeholders.get(value).get());
        }
        return string;
    }

    public String format(Player player, String string) {
        Validate.notNull(player, "Tried to format a String for a null player");
        for(String value : placeholders.keySet()) {
            string = format(string, value, placeholders.get(value).get(player));
        }
        return string;
    }

    public String format(CommandSender sender, String string) {
        Validate.notNull(sender, "Tried to format a String for a null CommandSender");
        for(String value : placeholders.keySet()) {
            string = format(string, value, placeholders.get(value).get(sender));
        }
        return string;
    }

    public String format(String locale, String string) {
        Validate.notNull(locale, "Tried to format a String for a null locale");
        for(String value : placeholders.keySet()) {
            string = format(string, value, placeholders.get(value).get(locale));
        }
        return string;
    }

    private String format(String string, String value, String replacement) {
        return string.replaceAll("%" + value + "%", replacement);
    }

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
