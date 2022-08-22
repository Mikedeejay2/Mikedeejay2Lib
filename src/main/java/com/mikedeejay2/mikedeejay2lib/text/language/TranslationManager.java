package com.mikedeejay2.mikedeejay2lib.text.language;

import com.google.gson.JsonElement;
import com.mikedeejay2.mikedeejay2lib.BukkitPlugin;
import com.mikedeejay2.mikedeejay2lib.data.json.JsonFile;
import com.mikedeejay2.mikedeejay2lib.text.PlaceholderFormatter;
import com.mikedeejay2.mikedeejay2lib.util.structure.tuple.ImmutablePair;
import com.mikedeejay2.mikedeejay2lib.util.structure.tuple.Pair;
import org.apache.commons.lang3.Validate;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public final class TranslationManager {
    public static final TranslationManager GLOBAL = new TranslationManager(null, "en_us");

    private final Map<String, Map<String, String>> translations;
    private final Set<Pair<String, Boolean>> directories;
    private BukkitPlugin plugin;
    private String baseLocale;
    private String globalLocale;

    private TranslationManager(BukkitPlugin plugin, String baseLocale) {
        this.plugin = plugin;
        this.translations = new HashMap<>();
        this.directories = new HashSet<>();
        this.baseLocale = baseLocale;
        this.globalLocale = baseLocale;
    }

    public void registerDirectory(String directory, boolean jarFile) {
        this.directories.add(new ImmutablePair<>(directory, jarFile));
        clearTranslations();
    }

    public String getTranslation(Player player, String key, PlaceholderFormatter formatter) {
        return formatter.format(getTranslation(player, key));
    }

    public String getTranslation(String locale, String key, PlaceholderFormatter formatter) {
        return formatter.format(getTranslation(locale, key));
    }

    public String getTranslation(Player player, String key) {
        Validate.notNull(player, "Player cannot be null");
        String locale = player.getLocale().toLowerCase();
        return getTranslation(locale, key);
    }

    public String getTranslation(String locale, String key) {
        Validate.notNull(plugin, "Attempted to get translations, but plugin instance is null");
        Validate.notNull(locale, "Locale cannot be null");
        Validate.notNull(key, "Key cannot be null");
        if(containsKey(locale, key)) return getTranslationInternal(locale, key);
        return getTranslation(key);
    }

    public String getTranslation(String key) {
        Validate.notNull(plugin, "Attempted to get translations, but plugin instance is null");
        Validate.notNull(key, "Key cannot be null");
        if(containsKey(globalLocale, key)) return getTranslationInternal(globalLocale, key);
        if(containsKey(baseLocale, key)) return getTranslationInternal(baseLocale, key);
        throw new IllegalArgumentException(String.format("Could not find locale of key \"%s\"", key));
    }

    private boolean containsKey(String locale, String key) {
        Map<String, String> translationMap = getTranslationMap(locale);
        return translationMap != null && translationMap.containsKey(key);
    }

    private String getTranslationInternal(String locale, String key) {
        Map<String, String> translationMap = getTranslationMap(locale);
        return translationMap.get(key);
    }

    private Map<String, String> getTranslationMap(String locale) {
        return translations.getOrDefault(locale, loadTranslations(locale));
    }

    private boolean localeIsNull(String locale) {
        return translations.containsKey(locale) && translations.get(locale) == null;
    }

    private Map<String, String> loadTranslations(String locale) {
        Map<String, String> newTranslations = new HashMap<>();
        for(Pair<String, Boolean> pair : directories) {
            addTranslations(pair.getLeft(), pair.getRight(), locale, newTranslations);
        }
        if(newTranslations.isEmpty()) {
            Validate.isTrue(!locale.equals(baseLocale), "Base locale was null");
            this.translations.put(locale, null);
            return null;
        }
        this.translations.put(locale, newTranslations);
        return newTranslations;
    }

    private void addTranslations(String directory, boolean jarFile, String locale,  Map<String, String> translations) {
        JsonFile jsonFile = new JsonFile(plugin, String.format("%s/%s.json", directory, locale));
        if((jarFile && !jsonFile.loadFromJar(false)) || (!jarFile && !jsonFile.loadFromDisk(false))) {
            return;
        }
        for(Map.Entry<String, JsonElement> entry : jsonFile.getAccessor().getKeyValuePairs(false).entrySet()) {
            translations.put(entry.getKey(), entry.getValue().getAsString());
        }
    }

    private Map<String, String> getGlobalTranslations() {
        return getTranslationMap(globalLocale);
    }

    private Map<String, String> getBaseTranslations() {
        return getTranslationMap(baseLocale);
    }

    private void clearTranslations() {
        this.translations.clear();
    }

    public void setPlugin(BukkitPlugin plugin) {
        Validate.notNull(plugin, "Cannot set plugin to null");
        this.plugin = plugin;
    }

    public String getBaseLocale() {
        return baseLocale;
    }

    public void setBaseLocale(String baseLocale) {
        this.baseLocale = baseLocale;
    }

    public String getGlobalLocale() {
        return globalLocale;
    }

    public void setGlobalLocale(String globalLocale) {
        this.globalLocale = globalLocale;
    }
}
