package com.mikedeejay2.mikedeejay2lib.text.language;

import com.mikedeejay2.mikedeejay2lib.BukkitPlugin;
import com.mikedeejay2.mikedeejay2lib.data.json.JsonFile;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Manages lang files(json).
 * If lang files are used, this class is used.
 *
 * @author Mikedeejay2
 */
public class LangManager {
    /**
     * The {@link BukkitPlugin} instance
     */
    protected final BukkitPlugin plugin;

    /**
     * The default language locale
     */
    protected static final String ENGLISH = "en_us";

    /**
     * The default language locale. By default, {@link LangManager#ENGLISH}
     */
    private String defaultLang;

    /**
     * Map of lang locales to lang files
     */
    protected Map<String, JsonFile> langFiles;

    /**
     * A List of locales that could not be loaded and should not be attempted to be loaded again
     */
    protected List<String> doNotLoad;

    /**
     * Path to the language files
     */
    protected String filePath;

    /**
     * Create a <code>LangManager</code> and load the default language (if any) and
     * the english language (<code>en_us</code>)
     *
     * @param plugin A reference to the plugin
     * @param filePath The path to where the language files are stored in the plugin's jar
     */
    public LangManager(BukkitPlugin plugin, String filePath) {
        this.plugin = plugin;
        this.langFiles = new HashMap<>();
        this.doNotLoad = new ArrayList<>();
        this.filePath = filePath;
        loadLangFile(ENGLISH);
    }

    /**
     * Set the default lang of the <code>LangManager</code>
     * This only affects the console and depending on the configuration
     * of the operating system the characters won't show up properly
     *
     * @param defaultLang The locale to set the defaultLang to
     */
    public void setDefaultLang(String defaultLang) {
        this.defaultLang = defaultLang;
        loadLangFileDefaultLang(defaultLang);
    }

    /**
     * Attempt to load a lang file based off of a locale
     *
     * @param locale The language that will attempt to be loaded
     * @return If loading was successful or not
     */
    public boolean loadLangFile(String locale) {
        if(locale == null || doNotLoad.contains(locale)) return true;
        JsonFile file = new JsonFile(plugin, filePath + "/" + locale + ".json");

        doNotLoad.add(locale);
        if(file.loadFromJar(false)) {
            langFiles.put(locale, file);
            return true;
        }
        return false;
    }

    /**
     * Attempt to load a lang file based off of a locale.
     * Only to be used with the locale specified in <code>config.yml</code>
     *
     * @param locale The language that will attempt to be loaded
     * @return If loading was successful or not
     */
    public boolean loadLangFileDefaultLang(String locale) {
        boolean loaded = loadLangFile(locale);
        if(!loaded && !locale.equals("en_us")) {
            defaultLang = ENGLISH;
            plugin.getLogger().warning("The default language specified in config.yml is not currently supported by this plugin. English will be used instead.");
        }
        return loaded;
    }

    /**
     * Gets a lang file. If the file isn't loaded it will attempt to load it.
     * If the lang file does not exist the default lang file will be returned.
     *
     * @param locale The lang locale to be loaded
     * @return The specified lang file if it exists or the default lang file
     */
    public JsonFile getLang(String locale) {
        JsonFile file = null;
        if(langFiles.containsKey(locale)) {
            file = langFiles.get(locale);
        } else if(loadLangFile(locale)) {
            file = langFiles.get(locale);
        } else {
            file = langFiles.get(defaultLang);
        }
        if(file == null) {
            file = langFiles.get(ENGLISH);
        }
        return file;
    }

    /**
     * Gets the default lang file. If the default lang file doesn't exist the
     * English file will be loaded instead.
     *
     * @return The default lang file
     */
    public JsonFile getLang() {
        JsonFile file = langFiles.get(defaultLang);
        if(file == null) {
            file = langFiles.get(ENGLISH);
            if(file == null) {
                file = new JsonFile(plugin, filePath + "/" + "en_us.json");
            }
        }
        return file;
    }

    /**
     * Get the lang file based off of a player
     *
     * @param player Player to get the language locale from
     * @return The specified lang file based off of the player
     */
    public JsonFile getLang(Player player) {
        return langFiles.get(player.getLocale().toLowerCase());
    }

    /**
     * Get the lang file based off of a CommandSender
     *
     * @param sender Sender to get the Location locale
     * @return The specified lang file base off of the CommandSender
     */
    public JsonFile getLang(CommandSender sender) {
        if(sender instanceof Player) {
            return langFiles.get(((Player)sender).getLocale().toLowerCase());
        }
        return getLang();
    }

    /**
     * Gets a specific piece of text based off of a language locale and a path
     *
     * @param locale The language locale to be used
     * @param path The path to be used to find the text
     * @return The wanted text, null if text doesn't exist.
     */
    public String getText(String locale, String path) {
        JsonFile file = getLang(locale);
        if(file == null) {
            file = getLang();
        }
        String string = file.getString(path);
        if(string == null) {
            file = getLang();
            string = file.getString(path);
        }
        return string;
    }

    /**
     * Gets the text based only off of a path, uses the default lang locale
     *
     * @param path The path to be used to find the text
     * @return The wanted text, null if text doesn't exist.
     */
    public String getText(String path) {
        String text = getText(defaultLang, path);
        if(text == null) {
            text = getText(ENGLISH, path);
        }
        return text;
    }

    /**
     * Gets text based off of a player and a path
     *
     * @param player Player that will be used to get language locale
     * @param path   The path to be used to find the text
     * @return The wanted text, null if text doesn't exist.
     */
    public String getText(Player player, String path) {
        return getText(player.getLocale().toLowerCase(), path);
    }

    /**
     * Gets text based off of a <code>CommandSender</code> and a path
     *
     * @param sender CommandSender that will be used to get language locale
     * @param path   The path to be used to find the text
     * @return The wanted text, null if text doesn't exist.
     */
    public String getText(CommandSender sender, String path) {
        if(sender instanceof Player) {
            return getText(((Player)sender).getLocale().toLowerCase(), path);
        }
        return getText(path);
    }

    /**
     * Gets text based off of a language locale and a path and processes the string replacing the
     * strings in toReplace with the strings in replacements.
     * It's important that toReplace must be the same length as replacements
     *
     * @param locale       The language locale to use
     * @param path         The path to find the text from
     * @param toReplace    The array of Strings that should be replaced
     * @param replacements The array of Strings that will replace the Strings in toReplace
     * @return The wanted text, processed, null if text doesn't exist
     */
    public String getText(String locale, String path, String[] toReplace, String[] replacements) {
        String text = getText(locale, path);
        if(text == null) return null;
        for(int i = 0; i < toReplace.length; i++) {
            String curToReplace = toReplace[i];
            String curReplacement = replacements[i];
            text = text.replaceAll("\\{" + curToReplace + "\\}", curReplacement);
        }
        return text;
    }

    /**
     * Gets text based off of a path and processes the string replacing the
     * strings in toReplace with the strings in replacements. Uses the default
     * language locale.
     * <p>
     * It's important that <code>toReplace</code> must be the same length as <code>replacements</code>
     *
     * @param path         The path to find the text from
     * @param toReplace    The array of Strings that should be replaced
     * @param replacements The array of Strings that will replace the Strings in toReplace
     * @return The wanted text, processed, null if text doesn't exist
     */
    public String getText(String path, String[] toReplace, String[] replacements) {
        String text = getText(defaultLang, path, toReplace, replacements);
        if(text == null) {
            text = getText(ENGLISH, path, toReplace, replacements);
        }
        return text;
    }

    /**
     * Gets text based off of the CommandSender and a path and processes the string replacing the
     * strings in <code>toReplace</code> with the strings in replacements.
     * <p>
     * It's important that <code>toReplace</code> must be the same length as <code>replacements</code>
     *
     * @param sender       The CommandSender to base the language Locale off of
     * @param path         The path to find the text from
     * @param toReplace    The array of Strings that should be replaced
     * @param replacements The array of Strings that will replace the Strings in toReplace
     * @return The wanted text, processed, null if text doesn't exist
     */
    public String getText(CommandSender sender, String path, String[] toReplace, String[] replacements) {
        if(sender instanceof Player) {
            return getText(((Player)sender).getLocale().toLowerCase(), path, toReplace, replacements);
        }
        return getText(path, toReplace, replacements);
    }

    /**
     * Gets text based off of a player and a path and processes the string replacing the
     * strings in <code>toReplace</code> with the strings in replacements.
     * <p>
     * It's important that <code>toReplace</code> must be the same length as <code>replacements</code>
     *
     * @param player       The player to base the language Locale off of
     * @param path         The path to find the text from
     * @param toReplace    The array of Strings that should be replaced
     * @param replacements The array of Strings that will replace the Strings in toReplace
     * @return The wanted text, processed, null if text doesn't exist
     */
    public String getText(Player player, String path, String[] toReplace, String[] replacements) {
        return getText(player.getLocale().toLowerCase(), path, toReplace, replacements);
    }

    /**
     * Get the default lang locale of this lang manager
     *
     * @return The lang locale (i.e en_us)
     */
    public String getDefaultLang() {
        return defaultLang;
    }

    /**
     * Get the file paths to the language files for this <code>LangManager</code>
     *
     * @return The file path towards the language files
     */
    public String getFilePath() {
        return filePath;
    }

    /**
     * Get the new file path to the language files for this <code>LanguageManager</code>
     *
     * @param filePath The new file path towards the language files
     */
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}
