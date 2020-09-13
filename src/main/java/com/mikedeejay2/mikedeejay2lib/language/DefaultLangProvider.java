package com.mikedeejay2.mikedeejay2lib.language;

/**
 * Simple Interface that adds getDefaultLang() method to a config
 */
public interface DefaultLangProvider
{
    /**
     * Get the default lang of this plugin
     *
     * @return
     */
    public String getDefaultLang();
}
