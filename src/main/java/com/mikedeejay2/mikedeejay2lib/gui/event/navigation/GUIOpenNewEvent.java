package com.mikedeejay2.mikedeejay2lib.gui.event.navigation;

import com.mikedeejay2.mikedeejay2lib.BukkitPlugin;
import com.mikedeejay2.mikedeejay2lib.gui.GUIConstructor;
import com.mikedeejay2.mikedeejay2lib.gui.GUIContainer;
import com.mikedeejay2.mikedeejay2lib.gui.event.GUIEventInfo;
import org.bukkit.event.inventory.ClickType;
import org.jetbrains.annotations.Nullable;

/**
 * Open a player's GUI to a NEW specified GUI.
 * <p>
 * Instead of opening an already constructed GUI, this event creates a new GUI upon request.
 *
 * @author Mikedeejay2
 */
public class GUIOpenNewEvent extends GUIOpenEvent {
    /**
     * The {@link BukkitPlugin} instance
     */
    protected final BukkitPlugin plugin;

    /**
     * The {@link GUIConstructor} used to construct a new {@link GUIContainer}
     */
    protected GUIConstructor constructor;

    /**
     * Construct a new <code>GUIOpenNewEvent</code>
     *
     * @param plugin         The {@link BukkitPlugin} instance
     * @param constructor    The {@link GUIConstructor} used for constructing the new GUI
     * @param acceptedClicks The list of {@link ClickType ClickTypes} to accept
     */
    public GUIOpenNewEvent(BukkitPlugin plugin, GUIConstructor constructor, ClickType... acceptedClicks) {
        super(plugin, null, acceptedClicks);
        this.plugin = plugin;
        this.constructor = constructor;
    }

    /**
     * Construct a new <code>GUIOpenNewEvent</code>
     *
     * @param plugin      The {@link BukkitPlugin} instance
     * @param constructor The {@link GUIConstructor} used for constructing the new GUI
     */
    public GUIOpenNewEvent(BukkitPlugin plugin, GUIConstructor constructor) {
        super(plugin, null);
        this.plugin = plugin;
        this.constructor = constructor;
    }

    @Override
    protected void executeClick(GUIEventInfo info) {
        construct();
        super.executeClick(info);
    }

    /**
     * Get the {@link GUIConstructor} used to construct a new {@link GUIContainer}
     *
     * @return The <code>GUIConstructor</code>
     */
    public GUIConstructor getConstructor() {
        return constructor;
    }

    /**
     * Set the {@link GUIConstructor} used to construct a new {@link GUIContainer}
     *
     * @param constructor The new <code>GUIConstructor</code>
     */
    public void setConstructor(GUIConstructor constructor) {
        this.constructor = constructor;
    }

    /**
     * Get the {@link GUIContainer}
     *
     * @return The <code>GUIContainer</code>
     */
    public GUIContainer getGUI() {
        construct();
        return guiToOpen;
    }

    /**
     * Force the construction of the {@link GUIContainer}
     */
    public void construct() {
        if(this.guiToOpen == null) {
            this.guiToOpen = constructor.get();
        }
    }
}
