package com.mikedeejay2.mikedeejay2lib.text;

import com.mikedeejay2.mikedeejay2lib.text.language.TranslationManager;
import org.bukkit.entity.Player;

public class TranslatableText implements Text {
    private final String key;
    private final TranslationManager manager;

    protected TranslatableText(String key, TranslationManager manager) {
        this.key = key;
        this.manager = manager;
    }

    protected TranslatableText(String key) {
        this(key, TranslationManager.GLOBAL);
    }

    @Override
    public String getText(Player player) {
        return manager.getTranslation(player, key);
    }

    @Override
    public String getText(String locale) {
        return manager.getTranslation(locale, key);
    }

    @Override
    public String getText() {
        return manager.getTranslation(key);
    }

    public String getKey() {
        return key;
    }

    @Override
    public String toString() {
        return getKey();
    }
}
