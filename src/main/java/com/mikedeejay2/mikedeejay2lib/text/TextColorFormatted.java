package com.mikedeejay2.mikedeejay2lib.text;

import com.mikedeejay2.mikedeejay2lib.util.chat.Colors;
import org.apache.commons.lang3.Validate;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.*;

/**
 * Formats text with a {@link Colors.FormatStyle} to format the color codes within the text. In some cases this isn't
 * required as color codes may already be translated. However, if color codes aren't translated or additional
 * functionality is wanted such as translating hex codes or color shortcuts, that functionality can be specified here.
 * <p>
 * Color formatted text can be created with {@link Text#color()} for {@link Colors.FormatStyle#COLOR_CODES} or
 * {@link Text#color(Colors.FormatStyle...)} for multiple styles. These methods are non-static methods.
 *
 * @author Mikedeejay2
 * @see Text
 */
public class TextColorFormatted implements Text {
    /**
     * The encapsulated text to be color formatted
     */
    protected final Text text;

    /**
     * The list of {@link Colors.FormatStyle FormatStyles} to be used when formatting
     */
    protected final Set<Colors.FormatStyle> formatStyles;

    /**
     * Construct a new <code>TextColorFormatted</code>
     *
     * @param text         The encapsulated text to be color formatted
     * @param formatStyles The list of {@link Colors.FormatStyle FormatStyles} to be used when formatting
     */
    protected TextColorFormatted(Text text, Colors.FormatStyle... formatStyles) {
        this.text = text;
        this.formatStyles = new HashSet<>();
        this.formatStyles.addAll(Arrays.asList(formatStyles));
        Validate.isTrue(!(this.formatStyles.contains(Colors.FormatStyle.ALL) && this.formatStyles.size() > 1),
                        "Developer error, it is redundant to have more than Colors.FormatStyle.ALL");
    }

    @Override
    public String get(Player player) {
        return color(text.get(player));
    }

    @Override
    public String get(CommandSender sender) {
        return color(text.get(sender));
    }

    @Override
    public String get(String locale) {
        return color(text.get(locale));
    }

    @Override
    public String get() {
        return color(text.get());
    }

    @Override
    public Text color(Colors.FormatStyle... styles) {
        Set<Colors.FormatStyle> newStyles = new HashSet<>(this.formatStyles);
        newStyles.addAll(Arrays.asList(styles));
        return new TextColorFormatted(text, newStyles.toArray(new Colors.FormatStyle[0]));
    }

    /**
     * Internal method to format
     *
     * @param input The input String
     * @return The formatted String
     */
    private String color(String input) {
        String result = input;
        for(Colors.FormatStyle formatStyle : formatStyles) {
            result = formatStyle.format(result);
        }
        return result;
    }

    @Override
    public TextColorFormatted clone() {
        TextColorFormatted text;
        try {
            text = (TextColorFormatted) super.clone();
        } catch(CloneNotSupportedException e) {
            e.printStackTrace();
            return null;
        }
        return text;
    }
}
