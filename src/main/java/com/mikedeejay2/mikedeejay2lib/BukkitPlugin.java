package com.mikedeejay2.mikedeejay2lib;

import com.mikedeejay2.mikedeejay2lib.gui.listeners.GUIListener;
import com.mikedeejay2.mikedeejay2lib.gui.manager.GUIManager;
import com.mikedeejay2.mikedeejay2lib.text.language.LangManager;
import com.mikedeejay2.mikedeejay2lib.util.enchant.GlowEnchantment;
import com.mikedeejay2.mikedeejay2lib.util.version.MinecraftVersion;

/**
 * Main class for Mikedeejay2Lib.
 * <p>
 * <code>BukkitPlugin</code> is a robust plugin class that extends {@link EnhancedJavaPlugin} to
 * add new functions. Check source code on Github to find all features, planned additions,
 * bugs, etc.
 * <p>
 * Source code: <a href="https://github.com/Mikedeejay2/Mikedeejay2Lib">https://github.com/Mikedeejay2/Mikedeejay2Lib</a>
 *
 * @author Mikedeejay2
 */
public abstract class BukkitPlugin extends EnhancedJavaPlugin
{
    private LangManager libLangManager;
    private GUIManager guiManager;

    @Override
    public void onEnable()
    {
        super.onEnable();
        this.libLangManager = new LangManager(this, "lang/lib");
        this.guiManager = new GUIManager(this);

        this.getServer().getPluginManager().registerEvents(new GUIListener(this), this);

        GlowEnchantment.registerGlow(this);

        this.sendMessage(String.format("&a%s is powered by Mikedeejay2Lib, a collection of open source resources for developers to use.", this.getDescription().getName()));
    }

    @Override
    public void onDisable()
    {
        super.onDisable();
    }

    /**
     * Get this plugin's GUI manager
     *
     * @return The GUI manager
     */
    public GUIManager getGUIManager()
    {
        return guiManager;
    }

    /**
     * Get the <code>LangManager</code> for Mikedeejay2Lib
     *
     * @return Mikedeejay2Lib lang manager
     */
    public LangManager getLibLangManager()
    {
        return libLangManager;
    }
}
