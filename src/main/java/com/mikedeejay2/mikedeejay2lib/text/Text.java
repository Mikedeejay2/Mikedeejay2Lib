package com.mikedeejay2.mikedeejay2lib.text;

import com.mikedeejay2.mikedeejay2lib.text.language.TranslationManager;
import com.mikedeejay2.mikedeejay2lib.util.chat.Colors;
import org.apache.commons.lang3.Validate;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * The Text API. The goal of this API is to allow for simplistic creation of different text types.
 * <p>
 * The text types that are supported are:
 * <ul>
 *     <li>
 *         Primary text types:
 *         <ul>
 *             <li>
 *                 <strong>Literal Text</strong> -
 *                 Encapsulates a String. Has no additional functionality over a String.
 *                 <p>
 *                 Literal text can be created with {@link Text#of(String)} or {@link Text#literal(String)}
 *             </li>
 *             <li>
 *                 <strong>Translatable Text</strong> -
 *                 Stores a translation key, used to resolve a translation upon retrieval. Translatable Text uses the
 *                 global {@link TranslationManager} to retrieve translations.
 *                 <p>
 *                 To add translations for use with translatable text, use the following code:
 *                 <pre>
 *  TranslationManager.GLOBAL.registerDirectory("path", true);
 *                 </pre>
 *                 "path" should be replaced with "lang/the name of your plugin". The second parameter, true, means that
 *                 this directory is within the plugin's jar file. If the directory should be outside the jar file, set
 *                 this to false instead.
 *                 <p>
 *                 Translatable text can be created with {@link Text#translatable(String)}
 *             </li>
 *         </ul>
 *     </li>
 *     <li>
 *         Secondary text types:
 *         <ul>
 *             <li>
 *                 <strong>Concatenated Text</strong> -
 *                 Two texts that are concatenated (combined into one). Should be used to combine two separated texts
 *                 that need to be one. For example, a literal text and a translatable text.
 *                 <p>
 *                 Concatenated text can be created with {@link Text#concat(Text)}. This is a non-static method.
 *             </li>
 *             <li>
 *                 <strong>Placeholder Formatted Text</strong> -
 *                 Formats text with a {@link PlaceholderFormatter} for easy replacement of placeholders
 *                 in the text. Encapsulates an existing text.
 *                 <p>
 *                 Placeholder formatted text can be created with {@link Text#placeholder(PlaceholderFormatter)}. This
 *                 is a non-static method.
 *             </li>
 *             <li>
 *                 <strong>Color Formatted Text</strong> -
 *                 Formats text with a {@link Colors.FormatStyle} to format the color codes within the text.
 *                 In some cases this isn't required as color codes may already be translated. However, if color codes
 *                 aren't translated or additional functionality is wanted such as translating hex codes or color
 *                 shortcuts, that functionality can be specified here.
 *                 <p>
 *                 Color formatted text can be created with {@link Text#color()} for
 *                 {@link Colors.FormatStyle#COLOR_CODES} or {@link Text#color(Colors.FormatStyle...)} for multiple
 *                 styles. These methods are non-static methods.
 *             </li>
 *             <li>
 *                 <strong>Formatted Text</strong> -
 *                 Formats text using {@link String#format(String, Object...)} for automatic formatting of text when
 *                 it's retrieved.
 *                 <p>
 *                 Formatted text can be created with {@link Text#format(Object...)} This is a non-static method.
 *             </li>
 *         </ul>
 *     </li>
 * </ul>
 *
 * @author Mikedeejay2
 */
public interface Text extends Cloneable {

    /**
     * Get literal text of the specified String
     *
     * @param text The String to create text of
     * @return The new text
     * @see Text
     */
    static TextLiteral of(String text) {
        return literal(text);
    }

    /**
     * Get literal text of the specified String
     *
     * @param text The String to create text of
     * @return The new text
     * @see Text
     */
    static TextLiteral literal(String text) {
        if(text == null || text.isEmpty()) return TextLiteral.EMPTY;
        return new TextLiteral(text);
    }

    /**
     * Get translatable text of the specified translation key
     *
     * @param key The translation key to create text of
     * @return The new text
     * @see Text
     */
    static TextTranslatable translatable(String key) {
        return new TextTranslatable(key);
    }

    /**
     * Get translatable text of the specified translation key and a custom {@link TranslationManager}.
     * <p>
     * This method is only for non-global translation managers. If using the global translation manager, use
     * {@link Text#translatable(String)} instead.
     *
     * @param key     The translation key to create text of
     * @param manager The {@link TranslationManager} to use
     * @return The new text
     * @see Text
     */
    static TextTranslatable translatable(String key, TranslationManager manager) {
        return new TextTranslatable(key, manager);
    }

    /**
     * Get the String of this text using a <code>Player</code> for the locale.
     *
     * @param player The player for the locale
     * @return The retrieved String
     */
    String get(Player player);

    /**
     * Get the String of this text using a <code>CommandSender</code> for the locale.
     *
     * @param sender The <code>CommandSender</code> for the locale
     * @return The retrieved String
     */
    String get(CommandSender sender);

    /**
     * Get the String of this text using a locale. This locale should be all lowercase, like <code>"en_us"</code>
     *
     * @param locale The locale
     * @return The retrieved String
     */
    String get(String locale);

    /**
     * Get the String of this text without using any locale. This method will use the global locale by default. Read
     * about what this means at {@link TranslationManager#globalLocale}.
     *
     * @return The retrieved String
     */
    String get();

    /**
     * Clone this text.
     *
     * @return The cloned text
     */
    Text clone();

    /**
     * Add a placeholder formatter to this text. Formats text with a {@link PlaceholderFormatter} for easy replacement of
     * placeholders in the text.
     *
     * @param formatter The {@link PlaceholderFormatter} to use
     * @return The new formatted text
     */
    default Text placeholder(PlaceholderFormatter formatter) {
        Validate.notNull(formatter, "Attempted to add null placeholders");
        return new TextPlaceholderFormatted(this, formatter);
    }

    /**
     * Add a color formatter to this text. Formats text with a {@link Colors.FormatStyle} to format the color codes
     * within the text. In some cases this isn't required as color codes may already be translated. However, if color
     * codes aren't translated or additional functionality is wanted such as translating hex codes or color shortcuts,
     * that functionality can be specified here.
     *
     * @param styles The {@link Colors.FormatStyle} to be used
     * @return The new formatted text
     */
    default Text color(Colors.FormatStyle... styles) {
        return new TextColorFormatted(this, styles);
    }

    /**
     * Add a color formatter to this text. Formats text with a {@link Colors.FormatStyle} to format the color codes
     * within the text. In some cases this isn't required as color codes may already be translated. However, if color
     * codes aren't translated or additional functionality is wanted such as translating hex codes or color shortcuts,
     * that functionality can be specified here.
     * <p>
     * This method only applies color code formatting, {@link Colors.FormatStyle#COLOR_CODES}, for additional formatting
     * types, use {@link Text#color(Colors.FormatStyle...)}
     *
     * @return The new formatted text
     */
    default Text color() {
        return new TextColorFormatted(this, Colors.FormatStyle.COLOR_CODES);
    }

    /**
     * Concatenate (combine into one) this text with another text. Should be used to combine two separated texts that
     * need to be one. For example, a literal text and a translatable text.
     *
     * @param other The other text to concatenate with
     * @return The concatenated text
     */
    default Text concat(Text other) {
        Validate.notNull(other, "Attempted to concatenate null text");
        return new TextConcatenated(this, other);
    }

    /**
     * Concatenate (combine into one) this text with another String. Should be used to combine two separated texts that
     * need to be one. For example, a literal text and a translatable text.
     *
     * @param other The other String to concatenate with
     * @return The concatenated text
     */
    default Text concat(String other) {
        Validate.notNull(other, "Attempted to concatenate null text");
        return new TextConcatenated(this, Text.of(other));
    }

    /**
     * Formats text using {@link String#format(String, Object...)} for automatic formatting of text when it's retrieved.
     *
     * @param args Arguments referenced by the format
     * @return The formatted text
     */
    default Text format(Object... args) {
        return new TextFormatted(this, args);
    }
}
