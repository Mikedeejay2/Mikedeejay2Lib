package com.mikedeejay2.mikedeejay2lib.text;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Formats text using {@link String#format(String, Object...)} for automatic formatting of text when it's retrieved.
 * <p>
 *  Formatted text can be created with {@link Text#format(Object...)} This is a non-static method.
 *
 * @author Mikedeejay2
 * @see Text
 */
public class TextFormatted implements Text {
    /**
     * The encapsulated text to be formatted
     */
    protected final Text text;
    /**
     * Arguments referenced by the format
     */
    protected final Object[] args;

    /**
     * Construct a new <code>TextFormatted</code>
     *
     * @param text The encapsulated text to be formatted
     * @param args Arguments referenced by the format
     */
    protected TextFormatted(Text text, Object[] args) {
        this.text = text;
        this.args = args;
    }

    @Override
    public String get(Player player) {
        return String.format(text.get(player));
    }

    @Override
    public String get(CommandSender sender) {
        return String.format(text.get(sender));
    }

    @Override
    public String get(String locale) {
        return String.format(text.get(locale));
    }

    @Override
    public String get() {
        return String.format(text.get());
    }

    @Override
    public Text format(Object... args) {
        List<Object> combined = new ArrayList<>(Arrays.asList(this.args));
        combined.addAll(Arrays.asList(args));
        return new TextFormatted(text, combined.toArray());
    }

    /**
     * Get the encapsulated text to be formatted
     *
     * @return The encapsulated text to be formatted
     */
    public Text getText() {
        return text;
    }

    /**
     * Get the arguments referenced by the format
     *
     * @return Arguments referenced by the format
     */
    public Object[] getArgs() {
        return args;
    }

    @Override
    public String toString() {
        return "TextFormatted{" +
            "text=" + text +
            ", args=" + Arrays.toString(args) +
            '}';
    }

    @Override
    public TextFormatted clone() {
        TextFormatted text;
        try {
            text = (TextFormatted) super.clone();
        } catch(CloneNotSupportedException e) {
            e.printStackTrace();
            return null;
        }
        return text;
    }
}
