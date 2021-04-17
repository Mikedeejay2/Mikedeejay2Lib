package com.mikedeejay2.mikedeejay2lib;

import com.mikedeejay2.mikedeejay2lib.data.DataManager;
import com.mikedeejay2.mikedeejay2lib.gui.listeners.GUIListener;
import com.mikedeejay2.mikedeejay2lib.gui.manager.GUIManager;
import com.mikedeejay2.mikedeejay2lib.nms.NMSHandler;
import com.mikedeejay2.mikedeejay2lib.text.language.LangManager;
import com.mikedeejay2.mikedeejay2lib.util.enchant.GlowEnchantment;
import com.mikedeejay2.mikedeejay2lib.util.version.MinecraftVersion;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Field;

/**
 * Main class for Mikedeejay2Lib.
 * <p>
 * <tt>BukkitPlugin</tt> is a robust plugin class that extends {@link EnhancedJavaPlugin} to
 * add new functions. Check source code on Github to find all features, planned additions,
 * bugs, etc.
 * <p>
 * Source code: <a href="https://github.com/Mikedeejay2/Mikedeejay2Lib">https://github.com/Mikedeejay2/Mikedeejay2Lib</a>
 *
 * @author Mikedeejay2
 */
public abstract class BukkitPlugin extends EnhancedJavaPlugin
{
    private MinecraftVersion minecraftVersion;
    private LangManager libLangManager;
    private GUIManager guiManager;
    private NMSHandler nms;

    @Override
    public void onEnable()
    {
        super.onEnable();
        this.minecraftVersion = new MinecraftVersion(this);
        this.libLangManager = new LangManager(this, "lang/lib");
        this.nms = new NMSHandler(this);
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
     * Get the Minecraft server's Minecraft version
     *
     * @return The Minecraft version
     */
    public MinecraftVersion getMCVersion()
    {
        return minecraftVersion;
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
     * Gets the Net Minecraft Server handler
     *
     * @return The Net Minecraft Server handler
     */
    public NMSHandler getNMSHandler()
    {
        return nms;
    }

    public LangManager getLibLangManager()
    {
        return libLangManager;
    }
}
