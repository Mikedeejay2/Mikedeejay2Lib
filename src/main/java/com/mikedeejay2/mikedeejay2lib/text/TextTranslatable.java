package com.mikedeejay2.mikedeejay2lib.text;

import com.mikedeejay2.mikedeejay2lib.text.language.TranslationManager;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TextTranslatable implements Text {
    private final String key;
    private final TranslationManager manager;

    protected TextTranslatable(String key, TranslationManager manager) {
        this.key = key;
        this.manager = manager;
    }

    protected TextTranslatable(String key) {
        this(key, TranslationManager.GLOBAL);
    }

    @Override
    public String get(Player player) {
        return manager.getTranslation(player, key);
    }

    @Override
    public String get(CommandSender sender) {
        if(sender instanceof Player) return get((Player) sender);
        return get();
    }

    @Override
    public String get(String locale) {
        if(locale == null) return get();
        return manager.getTranslation(locale, key);
    }

    @Override
    public String get() {
        return manager.getTranslation(key);
    }

    public String getKey() {
        return key;
    }

    @Override
    public String toString() {
        return getKey();
    }

    @Override
    public TextTranslatable clone() {
        TextTranslatable text;
        try {
            text = (TextTranslatable) super.clone();
        } catch(CloneNotSupportedException e) {
            e.printStackTrace();
            return null;
        }
        return text;
    }
}
