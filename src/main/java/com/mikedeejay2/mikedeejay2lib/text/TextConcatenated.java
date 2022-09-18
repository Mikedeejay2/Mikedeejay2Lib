package com.mikedeejay2.mikedeejay2lib.text;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Two texts that are concatenated (combined into one). Should be used to combine two separated texts that need to be
 * one. For example, a literal text and a translatable text.
 * <p>
 * Concatenated text can be created with {@link Text#concat(Text)}. This is a non-static method.
 *
 * @author Mikedeejay2
 * @see Text
 */
public class TextConcatenated implements Text {
    /**
     * The text on the left of the concatenation
     */
    protected final Text leftText;

    /**
     * The text on the right of the concatenation
     */
    protected final Text rightText;

    /**
     * Construct a new <code>TextConcatenated</code>
     *
     * @param leftText  The text on the left of the concatenation
     * @param rightText The text on the right of the concatenation
     */
    protected TextConcatenated(Text leftText, Text rightText) {
        this.leftText = leftText;
        this.rightText = rightText;
    }

    @Override
    public String get(Player player) {
        return leftText.get(player) + rightText.get(player);
    }

    @Override
    public String get(CommandSender sender) {
        return leftText.get(sender) + rightText.get(sender);
    }

    @Override
    public String get(String locale) {
        return leftText.get(locale) + rightText.get(locale);
    }

    @Override
    public String get() {
        return leftText.get() + rightText.get();
    }

    /**
     * Get the text on the left of the concatenation
     *
     * @return The left text
     */
    public Text getLeftText() {
        return leftText;
    }

    /**
     * Get the text on the right of the concatenation
     *
     * @return The right text
     */
    public Text getRightText() {
        return rightText;
    }

    @Override
    public TextConcatenated clone() {
        TextConcatenated text;
        try {
            text = (TextConcatenated) super.clone();
        } catch(CloneNotSupportedException e) {
            e.printStackTrace();
            return null;
        }
        return text;
    }
}
