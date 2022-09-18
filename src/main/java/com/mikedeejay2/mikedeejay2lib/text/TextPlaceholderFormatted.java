package com.mikedeejay2.mikedeejay2lib.text;

import org.apache.commons.lang3.Validate;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Formats text with a {@link PlaceholderFormatter} for easy replacement of placeholders in the text. Encapsulates an
 * existing text.
 * <p>
 * Placeholder formatted text can be created with {@link Text#placeholder(PlaceholderFormatter)}. This is a non-static
 * method.
 *
 * @author Mikedeejay2
 * @see Text
 */
public class TextPlaceholderFormatted implements Text {
    /**
     * The encapsulated text to be placeholder formatted
     */
    protected final Text text;

    /**
     * The {@link PlaceholderFormatter} to be used
     */
    protected PlaceholderFormatter formatter;

    /**
     * Construct a new <code>TextPlaceholderFormatted</code>
     *
     * @param text      The encapsulated text to be placeholder formatted
     * @param formatter The {@link PlaceholderFormatter} to be used
     */
    protected TextPlaceholderFormatted(Text text, PlaceholderFormatter formatter) {
        this.text = text;
        this.formatter = formatter;
    }

    @Override
    public String get(Player player) {
        return formatter.format(player, text.get(player));
    }

    @Override
    public String get(CommandSender sender) {
        return formatter.format(sender, text.get(sender));
    }

    @Override
    public String get(String locale) {
        return formatter.format(locale, text.get(locale));
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
