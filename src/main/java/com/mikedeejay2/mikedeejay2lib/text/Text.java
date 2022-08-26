package com.mikedeejay2.mikedeejay2lib.text;

import com.mikedeejay2.mikedeejay2lib.text.language.TranslationManager;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public interface Text {
    static LiteralText of(String text) {
        return literal(text);
    }

    static LiteralText literal(String text) {
        if(text == null || text.isEmpty()) return LiteralText.EMPTY;
        return new LiteralText(text);
    }

    static TranslatableText translatable(String key) {
        return new TranslatableText(key);
    }

    static TranslatableText translatable(String key, TranslationManager manager) {
        return new TranslatableText(key, manager);
    }

    String get(Player player);
    String get(CommandSender sender);
    String get(String locale);
    String get();
    Text placeholder(PlaceholderFormatter formatter);

    default String get(Player player, PlaceholderFormatter formatter) {
        return formatter.format(get(player));
    }

    default String get(String locale, PlaceholderFormatter formatter) {
        return formatter.format(get(locale));
    }

    default String get(PlaceholderFormatter formatter) {
        return formatter.format(get());
    }
}
