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
        return String.format(text.get(player), getArgs_(player));
    }

    @Override
    public String get(CommandSender sender) {
        return String.format(text.get(sender), getArgs_(sender));
    }

    @Override
    public String get(String locale) {
        return String.format(text.get(locale), getArgs_(locale));
    }

    @Override
    public String get() {
        return String.format(text.get(), getArgs_());
    }

    @Override
    public Text format(Object... args) {
        List<Object> combined = new ArrayList<>(Arrays.asList(this.args));
        combined.addAll(Arrays.asList(args));
        return new TextFormatted(text, combined.toArray());
    }

    /**
     * Internal method to fix the format arguments if they have text contained within them. This method will process the
     * text to be Strings so that {@link String#format(String, Object...)} can use it accordingly
     *
     * @param player The player for the locale
     * @return The fixed arguments
     */
    private Object[] getArgs_(Player player) {
        final Object[] result = new Object[args.length];
        for(int i = 0; i < args.length; ++i) {
            result[i] = args[i] instanceof Text ? ((Text) args[i]).get(player) : args[i];
        }
        return result;
    }

    /**
     * Internal method to fix the format arguments if they have text contained within them. This method will process the
     * text to be Strings so that {@link String#format(String, Object...)} can use it accordingly
     *
     * @param sender The <code>CommandSender</code> for the locale
     * @return The fixed arguments
     */
    private Object[] getArgs_(CommandSender sender) {
        final Object[] result = new Object[args.length];
        for(int i = 0; i < args.length; ++i) {
            result[i] = args[i] instanceof Text ? ((Text) args[i]).get(sender) : args[i];
        }
        return result;
    }

    /**
     * Internal method to fix the format arguments if they have text contained within them. This method will process the
     * text to be Strings so that {@link String#format(String, Object...)} can use it accordingly
     *
     * @param locale The locale
     * @return The fixed arguments
     */
    private Object[] getArgs_(String locale) {
        final Object[] result = new Object[args.length];
        for(int i = 0; i < args.length; ++i) {
            result[i] = args[i] instanceof Text ? ((Text) args[i]).get(locale) : args[i];
        }
        return result;
    }

    /**
     * Internal method to fix the format arguments if they have text contained within them. This method will process the
     * text to be Strings so that {@link String#format(String, Object...)} can use it accordingly
     *
     * @return The fixed arguments
     */
    private Object[] getArgs_() {
        final Object[] result = new Object[args.length];
        for(int i = 0; i < args.length; ++i) {
            result[i] = args[i] instanceof Text ? ((Text) args[i]).get() : args[i];
        }
        return result;
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
