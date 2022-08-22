package com.mikedeejay2.mikedeejay2lib.text;

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

    String getText(Player player);
    String getText(String locale);
    String getText();
}
