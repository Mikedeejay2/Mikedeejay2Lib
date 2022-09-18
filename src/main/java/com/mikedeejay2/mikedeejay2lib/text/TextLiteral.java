package com.mikedeejay2.mikedeejay2lib.text;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Encapsulates a String. Has no additional functionality over a String.
 * <p>
 * Literal text can be created with {@link Text#of(String)} or {@link Text#literal(String)}
 *
 * @author Mikedeejay2
 * @see Text
 */
public class TextLiteral implements Text {
    /**
     * Empty Text, used to avoid multiple empty text objects
     */
    public static final TextLiteral EMPTY = new TextLiteral("");

    /**
     * the encapsulated String
     */
    protected final String text;

    /**
     * Construct a new <code>TextLiteral</code>
     *
     * @param text The encapsulated String
     */
    protected TextLiteral(String text) {
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
        return text;
    }

    /**
     * Returns the encapsulated String
     *
     * @return The encapsulated String
     */
    @Override
    public String toString() {
        return get();
    }

    @Override
    public TextLiteral clone() {
        TextLiteral text;
        try {
            text = (TextLiteral) super.clone();
        } catch(CloneNotSupportedException e) {
            e.printStackTrace();
            return null;
        }
        return text;
    }
}
