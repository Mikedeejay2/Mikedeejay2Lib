package com.mikedeejay2.mikedeejay2lib.text;

import org.apache.commons.lang3.Validate;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class LiteralText implements Text {
    public static final LiteralText EMPTY = new LiteralText("");

    protected final String text;
    protected PlaceholderFormatter formatter;

    protected LiteralText(String text) {
        this.text = text;
    }

    @Override
    public String get(Player player) {
        return get();
    }

    @Override
    public String get(CommandSender sender) {
        return get();
    }

    @Override
    public String get(String locale) {
        return get();
    }

    @Override
    public String get() {
        if(formatter != null) return formatter.format(text);
        return text;
    }

    @Override
    public LiteralText placeholder(PlaceholderFormatter formatter) {
        Validate.notNull(formatter, "Attempted to add null placeholders");
        if(this.formatter == null) this.formatter = formatter;
        else this.formatter.and(formatter);
        return this;
    }

    @Override
    public String toString() {
        return get();
    }
}
