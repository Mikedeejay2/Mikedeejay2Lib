package com.mikedeejay2.mikedeejay2lib.text;

import org.apache.commons.lang3.Validate;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TextPlaceholderFormatted implements Text {
    protected final Text text;
    protected PlaceholderFormatter formatter;

    protected TextPlaceholderFormatted(Text text, PlaceholderFormatter formatter) {
        this.text = text;
        this.formatter = formatter;
    }

    @Override
    public String get(Player player) {
        return formatter.format(text.get(player));
    }

    @Override
    public String get(CommandSender sender) {
        return formatter.format(text.get(sender));
    }

    @Override
    public String get(String locale) {
        return formatter.format(text.get(locale));
    }

    @Override
    public String get() {
        return formatter.format(text.get());
    }

    @Override
    public Text placeholder(PlaceholderFormatter formatter) {
        Validate.notNull(formatter, "Attempted to add null placeholders");
        return new TextPlaceholderFormatted(text, this.formatter.clone().and(formatter));
    }

    @Override
    public TextPlaceholderFormatted clone() {
        TextPlaceholderFormatted text;
        try {
            text = (TextPlaceholderFormatted) super.clone();
        } catch(CloneNotSupportedException e) {
            e.printStackTrace();
            return null;
        }
        text.formatter = this.formatter.clone();
        return text;
    }
}
