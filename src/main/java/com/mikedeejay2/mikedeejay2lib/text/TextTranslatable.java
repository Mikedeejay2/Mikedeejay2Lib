package com.mikedeejay2.mikedeejay2lib.text;

import com.mikedeejay2.mikedeejay2lib.text.language.TranslationManager;
import com.mikedeejay2.mikedeejay2lib.util.debug.DebugTimer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Stores a translation key, used to resolve a translation upon retrieval. Translatable Text uses the global
 * {@link TranslationManager} to retrieve translations.
 * <p>
 * To add translations for use with translatable text, use the following code:
 * <pre>
 *  TranslationManager.GLOBAL.registerDirectory("path", true);
 * </pre>
 * "path" should be replaced with "lang/the name of your plugin". The second parameter, true, means that this directory
 * is within the plugin's jar file. If the directory should be outside the jar file, set this to false instead.
 * <p>
 * Translatable text can be created with {@link Text#translatable(String)}
 *
 * @author Mikedeejay2
 * @see Text
 */
public class TextTranslatable implements Text {
    /**
     * The translation key
     */
    private final String key;

    /**
     * The {@link TranslationManager} to retrieve translations from
     */
    private final TranslationManager manager;

    /**
     * Construct a new <code>TextTranslatable</code>
     *
     * @param key     The translation key
     * @param manager The {@link TranslationManager} to retrieve translations from
     */
    protected TextTranslatable(String key, TranslationManager manager) {
        this.key = key;
        this.manager = manager;
    }

    /**
     * Construct a new <code>TextTranslatable</code> using the global {@link TranslationManager}
     *
     * @param key     The translation key
     */
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

    /**
     * Get the translation key
     *
     * @return The translation key
     */
    public String getKey() {
        return key;
    }

    /**
     * Returns the translation key
     *
     * @return The translation key
     */
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
