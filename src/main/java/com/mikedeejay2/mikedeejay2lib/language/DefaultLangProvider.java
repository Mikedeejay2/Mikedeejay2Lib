package com.mikedeejay2.mikedeejay2lib.language;

/**
 * Simple Interface that adds getDefaultLang() method to a config
 *
 * @author Mikedeejay2
 */
public interface DefaultLangProvider
{
    /**
     * Get the default lang of this plugin
     *
     * @return The lang locale
     */
    public String getDefaultLang();
}
