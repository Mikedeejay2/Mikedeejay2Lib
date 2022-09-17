package com.mikedeejay2.mikedeejay2lib.text;

import com.mikedeejay2.mikedeejay2lib.text.language.TranslationManager;
import com.mikedeejay2.mikedeejay2lib.util.chat.Colors;
import org.apache.commons.lang3.Validate;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public interface Text extends Cloneable {
    static TextLiteral of(String text) {
        return literal(text);
    }

    static TextLiteral literal(String text) {
        if(text == null || text.isEmpty()) return TextLiteral.EMPTY;
        return new TextLiteral(text);
    }

    static TextTranslatable translatable(String key) {
        return new TextTranslatable(key);
    }

    static TextTranslatable translatable(String key, TranslationManager manager) {
        return new TextTranslatable(key, manager);
    }

    String get(Player player);
    String get(CommandSender sender);
    String get(String locale);
    String get();
    Text clone();

    default Text placeholder(PlaceholderFormatter formatter) {
        Validate.notNull(formatter, "Attempted to add null placeholders");
        return new TextPlaceholderFormatted(this, formatter);
    }

    default Text format(Colors.FormatStyle... styles) {
        return new TextColorFormatted(this, styles);
    }

    default Text format() {
        return new TextColorFormatted(this, Colors.FormatStyle.COLOR_CODES);
    }

    default Text concat(Text other) {
        Validate.notNull(other, "Attempted to concatenate null text");
        return new TextConcatenated(this, other);
    }
}
