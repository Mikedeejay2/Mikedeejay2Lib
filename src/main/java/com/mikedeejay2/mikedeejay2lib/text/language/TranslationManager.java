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

/**
 * Manages translations loaded from JSON files, either from plugin jar or a file. For the global
 * <code>TranslationManager</code>, use {@link TranslationManager#GLOBAL}.
 * <p>
 * To add a location for translations, use {@link TranslationManager#registerDirectory(String, boolean)}
 *
 * @author Mikedeejay2
 */
public final class TranslationManager {
    /**
     * The global <code>TranslationManager</code>. This should be used in most cases.
     */
    public static final TranslationManager GLOBAL = new TranslationManager(null, "en_us");

    /**
     * The Map of locale to translations List. After a JSON file is loaded its translations are added to this map
     * for quick access.
     */
    private final Map<String, Map<String, String>> translations;

    /**
     * A Set of directories to search for translations along with a boolean, true if inside of plugin jar, false if a
     * file in the plugin's directory.
     */
    private final Set<Pair<String, Boolean>> directories;

    /**
     * The <code>BukkitPlugin</code> reference for loading JSON files.
     */
    private BukkitPlugin plugin;

    /**
     * The base locale. This should be the locale used for developing the plugin; it is the locale that is ensured to
     * have all translations.
     * <p>
     * The base locale is only used as a fallback for if the global locale doesn't have a translation.
     */
    private String baseLocale;

    /**
     * The global locale, by default this is the same as the base locale but can be configured to be different. This is
     * the locale that should be used by default is no other locale is specified. For example, getting a translation for
     * not a specific player or getting a translation to send to console.
     * <p>
     * The global locale does not need to have all translations. If a global translation is not found, the base locale
     * is instead used.
     */
    private String globalLocale;

    /**
     * Construct a new <code>TranslationManager</code>.
     * <p>
     * <strong>This should probably not be constructed!</strong> See {@link TranslationManager#GLOBAL} for the global
     * translation manager.
     *
     * @param plugin     The <code>BukkitPlugin</code> reference for loading JSON files.
     * @param baseLocale The base locale
     */
    public TranslationManager(BukkitPlugin plugin, String baseLocale) {
        this.plugin = plugin;
        this.translations = new HashMap<>();
        this.directories = new HashSet<>();
        this.baseLocale = baseLocale;
        this.globalLocale = baseLocale;
    }

    /**
     * Register a directory with this <code>TranslationManager</code>. This directory will be used when loading
     * translations.
     * <p>
     * Note that this method clears all currently loaded translations. It is recommended that this method only be called
     * upon plugin initialization, however it can be called after with the cost of losing currently loaded translations.
     *
     * @param directory The directory to add
     * @param jarFile   Whether this directory is in the plugin's jar file
     */
    public void registerDirectory(String directory, boolean jarFile) {
        this.directories.add(new ImmutablePair<>(directory, jarFile));
        clearTranslations();
    }

    /**
     * Get a translation using a Player as the locale
     *
     * @param player    The player to get the translation for
     * @param key       The key of the translation
     * @param formatter The {@link PlaceholderFormatter} to use when retrieving the message
     * @return The retrieved translation
     */
    public String getTranslation(Player player, String key, PlaceholderFormatter formatter) {
        return formatter.format(getTranslation(player, key));
    }

    /**
     * Get a translation using a specified locale. This locale should be all lowercase, like <code>"en_us"</code>
     *
     * @param locale    The player to get the translation for
     * @param key       The key of the translation
     * @param formatter The {@link PlaceholderFormatter} to use when retrieving the message
     * @return The retrieved translation
     */
    public String getTranslation(String locale, String key, PlaceholderFormatter formatter) {
        return formatter.format(getTranslation(locale, key));
    }

    /**
     * Get a translation using a Player as the locale
     *
     * @param player The player to get the translation for
     * @param key    The key of the translation
     * @return The retrieved translation
     */
    public String getTranslation(Player player, String key) {
        Validate.notNull(player, "Player cannot be null");
        String locale = player.getLocale().toLowerCase();
        return getTranslation(locale, key);
    }

    /**
     * Get a translation using a specified locale. This locale should be all lowercase, like <code>"en_us"</code>
     *
     * @param locale The player to get the translation for
     * @param key    The key of the translation
     * @return The retrieved translation
     */
    public String getTranslation(String locale, String key) {
        Validate.notNull(plugin, "Attempted to get translations, but plugin instance is null");
        Validate.notNull(locale, "Locale cannot be null");
        Validate.notNull(key, "Key cannot be null");
        if(containsKey(locale, key)) return getTranslationInternal(locale, key);
        return getTranslation(key);
    }

    /**
     * Get a translation using the global locale.
     *
     * @param key The key of the translation
     * @return The retrieved translation
     */
    public String getTranslation(String key) {
        Validate.notNull(plugin, "Attempted to get translations, but plugin instance is null");
        Validate.notNull(key, "Key cannot be null");
        if(containsKey(globalLocale, key)) return getTranslationInternal(globalLocale, key);
        if(containsKey(baseLocale, key)) return getTranslationInternal(baseLocale, key);
        throw new IllegalArgumentException(String.format("Could not find locale of key \"%s\"", key));
    }

    /**
     * Get whether a translation exists in the base locale
     *
     * @param key The key to search for
     * @return Whether the translation was found in the base locale
     */
    public boolean containsKey(String key) {
        return containsKey(baseLocale, key);
    }

    /**
     * Get whether a translation exists in the specified locale
     *
     * @param locale The locale to check in
     * @param key    The translation key to check for
     * @return Whether the translation was found in the locale
     */
    public boolean containsKey(String locale, String key) {
        if(key == null || key.isEmpty()) return false;
        Map<String, String> translationMap = getTranslationMap(locale);
        return translationMap != null && translationMap.containsKey(key);
    }

    /**
     * Internal method for retrieving the translation from the translation map.
     * {@link TranslationManager#containsKey(String, String)} should be called before the invocation of this method.
     *
     * @param locale The locale to get the translation from
     * @param key    The key of the translation
     * @return The retrieved translation
     */
    private String getTranslationInternal(String locale, String key) {
        Map<String, String> translationMap = getTranslationMap(locale);
        return translationMap.get(key);
    }

    /**
     * Get the internal translation map for a specified locale. If the map is not found, it will be loaded.
     *
     * @param locale The locale to get the map from
     * @return The retrieved map, null if not found
     */
    private Map<String, String> getTranslationMap(String locale) {
        if(!translations.containsKey(locale)) {
            return loadTranslations(locale);
        }
        return translations.get(locale);
    }

    /**
     * Load the translations of a specified locale from all registered directories. This method will populate
     * {@link TranslationManager#translations} with the loaded translations.
     *
     * @param locale The locale to load
     * @return The loaded translations Map
     */
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

    /**
     * Attempt to load the JSON file in the specified directory of the locale's name. If loaded successfully,
     * translations are stored in the translations map argument.
     *
     * @param directory    The directory to find the JSON file in
     * @param jarFile      Whether the file should be loaded from the plugin's jar file
     * @param locale       The locale to load
     * @param translations The translations map to be added to
     */
    private void addTranslations(String directory, boolean jarFile, String locale,  Map<String, String> translations) {
        JsonFile jsonFile = new JsonFile(plugin, String.format("%s/%s.json", directory, locale));
        if((jarFile && !jsonFile.loadFromJar(false)) || (!jarFile && !jsonFile.loadFromDisk(false))) {
            return;
        }
        for(Map.Entry<String, JsonElement> entry : jsonFile.getAccessor().getKeyValuePairs(false).entrySet()) {
            translations.put(entry.getKey(), entry.getValue().getAsString());
        }
    }

    /**
     * Clear all currently loaded translations. These translations will be loaded without issue on the next request.
     * This method is mainly called when registering a new directory, as existing translations must be refreshed to
     * include the new directory.
     */
    private void clearTranslations() {
        this.translations.clear();
    }

    /**
     * Set the <code>BukkitPlugin</code> after the construction of this manager. <strong>This method should not be
     * needed for most cases.</strong> The only default use of this method is for setting the plugin of the global
     * <code>TranslationManager</code> after its initialization.
     *
     * @param plugin The new <code>BukkitPlugin</code>
     */
    public void setPlugin(BukkitPlugin plugin) {
        Validate.notNull(plugin, "Cannot set plugin to null");
        this.plugin = plugin;
    }

    /**
     * Get the base locale. This should be the locale used for developing the plugin; it is the locale that is ensured
     * to have all translations.
     * <p>
     * The base locale is only used as a fallback for if the global locale doesn't have a translation.
     *
     * @return The base locale
     */
    public String getBaseLocale() {
        return baseLocale;
    }

    /**
     * Set the base locale. This should be the locale used for developing the plugin; it is the locale that is ensured
     * to have all translations.
     * <p>
     * The base locale is only used as a fallback for if the global locale doesn't have a translation.
     *
     * @param baseLocale The new base locale
     */
    public void setBaseLocale(String baseLocale) {
        this.baseLocale = baseLocale;
    }

    /**
     * Get the global locale, by default this is the same as the base locale but can be configured to be different. This
     * is the locale that should be used by default is no other locale is specified. For example, getting a translation
     * for not a specific player or getting a translation to send to console.
     * <p>
     * The global locale does not need to have all translations. If a global translation is not found, the base locale
     * is instead used.
     *
     * @return The global locale
     */
    public String getGlobalLocale() {
        return globalLocale;
    }

    /**
     * Set the global locale. This is the locale that should be used by default is no other locale is specified. For
     * example, getting a translation for not a specific player or getting a translation to send to console.
     * <p>
     * The global locale does not need to have all translations. If a global translation is not found, the base locale
     * is instead used.
     *
     * @param globalLocale The new global locale
     */
    public void setGlobalLocale(String globalLocale) {
        this.globalLocale = globalLocale;
    }
}
