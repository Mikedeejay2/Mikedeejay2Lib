package com.mikedeejay2.mikedeejay2lib.text;

import com.mikedeejay2.mikedeejay2lib.text.language.TranslationManager;
import org.apache.commons.lang3.Validate;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TranslatableText implements Text {
    private final String key;
    private final TranslationManager manager;
    protected PlaceholderFormatter formatter;

    protected TranslatableText(String key, TranslationManager manager) {
        this.key = key;
        this.manager = manager;
    }

    protected TranslatableText(String key) {
        this(key, TranslationManager.GLOBAL);
    }

    @Override
    public String get(Player player) {
        String translation = manager.getTranslation(player, key);
        if(formatter != null) return formatter.format(translation);
        return translation;
    }

    @Override
    public String get(CommandSender sender) {
        if(sender instanceof Player) return get((Player) sender);
        return get();
    }

    @Override
    public String get(String locale) {
        String translation = manager.getTranslation(locale, key);
        if(formatter != null) return formatter.format(translation);
        return translation;
    }

    @Override
    public String get() {
        String translation = manager.getTranslation(key);
        if(formatter != null) return formatter.format(translation);
        return translation;
    }

    @Override
    public TranslatableText placeholder(PlaceholderFormatter formatter) {
        Validate.notNull(formatter, "Attempted to add null placeholders");
        if(this.formatter == null) this.formatter = formatter;
        else this.formatter.and(formatter);
        return this;
    }

    public String getKey() {
        return key;
    }

    @Override
    public String toString() {
        return getKey();
    }
}
