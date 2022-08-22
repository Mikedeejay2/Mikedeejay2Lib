package com.mikedeejay2.mikedeejay2lib;

import com.mikedeejay2.mikedeejay2lib.gui.listeners.GUIListener;
import com.mikedeejay2.mikedeejay2lib.gui.manager.GUIManager;
import com.mikedeejay2.mikedeejay2lib.text.language.LangManager;
import com.mikedeejay2.mikedeejay2lib.text.language.TranslationManager;
import com.mikedeejay2.mikedeejay2lib.util.enchant.GlowEnchantment;

/**
 * Main class for Mikedeejay2Lib.
 * <p>
 * <code>BukkitPlugin</code> is a robust plugin class that extends {@link EnhancedJavaPlugin} to
 * add new functions. Check source code on GitHub to find all features, planned additions,
 * bugs, etc.
 * <p>
 * Source code: <a href="https://github.com/Mikedeejay2/Mikedeejay2Lib">https://github.com/Mikedeejay2/Mikedeejay2Lib</a>
 *
 * @author Mikedeejay2
 */
public abstract class BukkitPlugin extends EnhancedJavaPlugin {
    /**
     * The Mikedeejay2Lib {@link LangManager}. Used for some miscellaneous texts.
     */
    private LangManager libLangManager;

    /**
     * The {@link GUIManager} of this plugin. Used when a GUI is in use.
     */
    private GUIManager guiManager;

    /**
     * {@inheritDoc}
     */
    @Override
    public void onEnable() {
        super.onEnable();
        this.libLangManager = new LangManager(this, "lang/lib");
        this.guiManager = new GUIManager(this);

        this.getServer().getPluginManager().registerEvents(new GUIListener(this), this);

        GlowEnchantment.registerGlow(this);
        TranslationManager.GLOBAL.setPlugin(this);
        TranslationManager.GLOBAL.registerDirectory("lang/mikedeejay2lib", true);

        this.sendMessage(String.format("&a%s is powered by Mikedeejay2Lib, a collection of open source resources for developers to use.", this.getDescription().getName()));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onDisable() {
        this.guiManager.closeAllGuis();
        super.onDisable();
    }

    /**
     * Get this plugin's GUI manager
     *
     * @return The GUI manager
     */
    public GUIManager getGUIManager() {
        return guiManager;
    }

    /**
     * Get the <code>LangManager</code> for Mikedeejay2Lib
     *
     * @return Mikedeejay2Lib lang manager
     */
    public LangManager getLibLangManager() {
        return libLangManager;
    }
}
