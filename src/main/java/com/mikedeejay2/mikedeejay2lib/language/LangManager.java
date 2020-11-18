package com.mikedeejay2.mikedeejay2lib.language;

import com.mikedeejay2.mikedeejay2lib.PluginBase;
import com.mikedeejay2.mikedeejay2lib.file.json.JsonFile;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Manages lang files(json).
 * If lang files are used, this class is used.
 *
 * @author Mikedeejay2
 */
public class LangManager
{
    protected final PluginBase plugin;
    // The default language locale
    protected final String englishLang = "en_us";
    private String defaultLang;

    // Hash map of lang locales to lang files
    protected HashMap<String, JsonFile> langFiles = new HashMap<>();
    protected ArrayList<String> doNotLoad = new ArrayList<>();

    /**
     * Create a LangManager and load the default language (if any) and
     * the english language (en_us)
     */
    public LangManager(PluginBase plugin)
    {
        this.plugin = plugin;
        loadLangFile(englishLang);
    }

    /**
     * Set the default lang of the <tt>LangManager</tt>
     * This only affects the console and depending on the configuration
     * of the operating system the characters won't show up properly
     *
     * @param defaultLang The locale to set the defaultLang to
     */
    public void setDefaultLang(String defaultLang)
    {
        this.defaultLang = defaultLang;
        loadLangFileDefaultLang(defaultLang);
    }

    /**
     * Attempt to load a lang file based off of a locale
     *
     * @param locale The language that will attempt to be loaded
     * @return If loading was successful or not
     */
    public boolean loadLangFile(String locale)
    {
        if(locale == null || doNotLoad.contains(locale)) return true;
        JsonFile file = new JsonFile(plugin, locale + ".json");

        doNotLoad.add(locale);
        if(file.loadFromJar(false))
        {
            langFiles.put(locale, file);
            return true;
        }
        return false;
    }

    /**
     * Attempt to load a lang file based off of a locale.
     * Only to be used with the locale specified in config.yml
     *
     * @param locale The language that will attempt to be loaded
     * @return If loading was successful or not
     */
    public boolean loadLangFileDefaultLang(String locale)
    {
        boolean loaded = loadLangFile(locale);
        if(!loaded && !locale.equals("en_us"))
        {
            defaultLang = englishLang;
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
    public JsonFile getLang(String locale)
    {
        JsonFile file = null;
        if(langFiles.containsKey(locale))
        {
            file = langFiles.get(locale);
        }
        else if(loadLangFile(locale))
        {
            file = langFiles.get(locale);
        }
        else
        {
            file = langFiles.get(defaultLang);
        }
        if(file == null)
        {
            file = langFiles.get(englishLang);
        }
        return file;
    }

    /**
     * Gets the default lang file. If the default lang file doesn't exist the
     * English file will be loaded instead.
     *
     * @return The default lang file
     */
    public JsonFile getLang()
    {
        JsonFile file = langFiles.get(defaultLang);
        if(file == null)
        {
            file = langFiles.get(englishLang);
        }
        return file;
    }

    /**
     * Get the lang file based off of a player
     *
     * @param player Player to get the language locale from
     * @return The specified lang file based off of the player
     */
    public JsonFile getLang(Player player)
    {
        return langFiles.get(player.getLocale().toLowerCase());
    }

    /**
     * Get the lang file based off of a CommandSender
     *
     * @param sender Sender to get the Location locale
     * @return The specified lang file base off of the CommandSender
     */
    public JsonFile getLang(CommandSender sender)
    {
        if(sender instanceof Player)
        {
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
    public String getText(String locale, String path)
    {
        JsonFile file = getLang(locale);
        if(file == null)
        {
            file = getLang();
        }
        String string = file.getString(path);
        if(string == null)
        {
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
    public String getText(String path)
    {
        String text = getText(defaultLang, path);
        if(text == null)
        {
            text = getText(englishLang, path);
        }
        return text;
    }

    /**
     * Gets text based off of a player and a path
     *
     * @param player Player that will be used to get language locale
     * @param path The path to be used to find the text
     * @return The wanted text, null if text doesn't exist.
     */
    public String getText(Player player, String path)
    {
        return getText(player.getLocale().toLowerCase(), path);
    }

    /**
     * Gets text based off of a CommandSender and a path
     *
     * @param sender CommandSender that will be used to get language locale
     * @param path The path to be used to find the text
     * @return The wanted text, null if text doesn't exist.
     */
    public String getText(CommandSender sender, String path)
    {
        if(sender instanceof Player)
        {
            return getText(((Player)sender).getLocale().toLowerCase(), path);
        }
        return getText(path);
    }

    /**
     * Gets text based off of a language locale and a path and processes the string replacing the
     * strings in toReplace with the strings in replacements.
     * It's important that toReplace must be the same length as replacements
     *
     * @param locale The language locale to use
     * @param path The path to find the text from
     * @param toReplace The array of Strings that should be replaced
     * @param replacements The array of Strings that will replace the Strings in toReplace
     * @return The wanted text, processed, null if text doesn't exist
     */
    public String getText(String locale, String path, String[] toReplace, String[] replacements)
    {
        String text = getText(locale, path);
        if(text == null) return null;
        for(int i = 0; i < toReplace.length; i++)
        {
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
     * It's important that toReplace must be the same length as replacements
     *
     * @param path The path to find the text from
     * @param toReplace The array of Strings that should be replaced
     * @param replacements The array of Strings that will replace the Strings in toReplace
     * @return The wanted text, processed, null if text doesn't exist
     */
    public String getText(String path, String[] toReplace, String[] replacements)
    {
        String text = getText(defaultLang, path, toReplace, replacements);
        if(text == null)
        {
            text = getText(englishLang, path, toReplace, replacements);
        }
        return text;
    }

    /**
     * Gets text based off of the CommandSender and a path and processes the string replacing the
     * strings in toReplace with the strings in replacements.
     * It's important that toReplace must be the same length as replacements
     *
     * @param sender The CommandSender to base the language Locale off of
     * @param path The path to find the text from
     * @param toReplace The array of Strings that should be replaced
     * @param replacements The array of Strings that will replace the Strings in toReplace
     * @return The wanted text, processed, null if text doesn't exist
     */
    public String getText(CommandSender sender, String path, String[] toReplace, String[] replacements)
    {
        if(sender instanceof Player)
        {
            return getText(((Player)sender).getLocale().toLowerCase(), path, toReplace, replacements);
        }
        return getText(path, toReplace, replacements);
    }

    /**
     * Gets text based off of a player and a path and processes the string replacing the
     * strings in toReplace with the strings in replacements.
     * It's important that toReplace must be the same length as replacements
     *
     * @param player The player to base the language Locale off of
     * @param path The path to find the text from
     * @param toReplace The array of Strings that should be replaced
     * @param replacements The array of Strings that will replace the Strings in toReplace
     * @return The wanted text, processed, null if text doesn't exist
     */
    public String getText(Player player, String path, String[] toReplace, String[] replacements)
    {
        return getText(player.getLocale().toLowerCase(), path, toReplace, replacements);
    }

    public String getTextLib(String path)
    {
        String text = getText("lib_" + defaultLang, path);
        if(text == null)
        {
            text = getText("lib_" + englishLang, path);
        }
        return text;
    }

    /**
     * Gets the text based only off of a path, uses the default lang locale
     * Gets the text from Mikedeejay2Lib
     *
     * @param path The path to be used to find the text
     * @return The wanted text, null if text doesn't exist.
     */
    public String getTextLib(Player player, String path)
    {
        String text = getText("lib_" + player.getLocale().toLowerCase(), path);
        if(text == null)
        {
            text = getText("lib_" + englishLang, path);
        }
        return text;
    }

    /**
     * Gets text based off of a CommandSender and a path
     * Gets the text from Mikedeejay2Lib
     *
     * @param sender CommandSender that will be used to get language locale
     * @param path The path to be used to find the text
     * @return The wanted text, null if text doesn't exist.
     */
    public String getTextLib(CommandSender sender, String path)
    {
        if(sender instanceof Player)
        {
            String text = getText("lib_" + ((Player)sender).getLocale().toLowerCase(), path);
            if(text == null)
            {
                text = getText("lib_" + englishLang, path);
            }
            return text;
        }
        return getTextLib(path);
    }

    /**
     * Gets text based off of a path and processes the string replacing the
     * strings in toReplace with the strings in replacements. Uses the default
     * language locale.
     * It's important that toReplace must be the same length as replacements
     * Gets the text from Mikedeejay2Lib
     *
     * @param path The path to find the text from
     * @param toReplace The array of Strings that should be replaced
     * @param replacements The array of Strings that will replace the Strings in toReplace
     * @return The wanted text, processed, null if text doesn't exist
     */
    public String getTextLib(String path, String[] toReplace, String[] replacements)
    {
        String text = getText("lib_" + defaultLang, path, toReplace, replacements);
        if(text == null)
        {
            text = getText("lib_" + englishLang, path, toReplace, replacements);
        }
        return text;
    }

    /**
     * Gets text based off of the CommandSender and a path and processes the string replacing the
     * strings in toReplace with the strings in replacements.
     * It's important that toReplace must be the same length as replacements
     * Gets the text from Mikedeejay2Lib
     *
     * @param sender The CommandSender to base the language Locale off of
     * @param path The path to find the text from
     * @param toReplace The array of Strings that should be replaced
     * @param replacements The array of Strings that will replace the Strings in toReplace
     * @return The wanted text, processed, null if text doesn't exist
     */
    public String getTextLib(CommandSender sender, String path, String[] toReplace, String[] replacements)
    {
        if(sender instanceof Player)
        {
            String text = getText("lib_" + ((Player)sender).getLocale().toLowerCase(), path, toReplace, replacements);
            if(text == null)
            {
                text = getText("lib_" + englishLang, path);
            }
            return text;
        }
        return getTextLib(path, toReplace, replacements);
    }

    /**
     * Gets text based off of a player and a path and processes the string replacing the
     * strings in toReplace with the strings in replacements.
     * It's important that toReplace must be the same length as replacements
     * Gets the text from Mikedeejay2Lib
     *
     * @param player The player to base the language Locale off of
     * @param path The path to find the text from
     * @param toReplace The array of Strings that should be replaced
     * @param replacements The array of Strings that will replace the Strings in toReplace
     * @return The wanted text, processed, null if text doesn't exist
     */
    public String getTextLib(Player player, String path, String[] toReplace, String[] replacements)
    {
        String text = getText("lib_" + player.getLocale().toLowerCase(), path, toReplace, replacements);
        if(text == null)
        {
            text = getText("lib_" + englishLang, path);
        }
        return text;
    }

    /**
     * Get the default lang locale of this lang manager
     *
     * @return The lang locale (i.e en_us)
     */
    public String getDefaultLang()
    {
        return defaultLang;
    }
}
