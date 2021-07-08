package com.mikedeejay2.mikedeejay2lib.gui.event.navigation;

import com.mikedeejay2.mikedeejay2lib.BukkitPlugin;
import com.mikedeejay2.mikedeejay2lib.gui.GUIConstructor;
import com.mikedeejay2.mikedeejay2lib.gui.GUIContainer;
import com.mikedeejay2.mikedeejay2lib.gui.event.GUIEvent;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.jetbrains.annotations.Nullable;

/**
 * Open a player's GUI to a NEW specified GUI.
 * <p>
 * Instead opening an already constructed GUI, this event creates a new GUI upon request.
 *
 * @author Mikedeejay2
 */
public class GUIOpenNewEvent implements GUIEvent
{
    /**
     * The {@link BukkitPlugin} instance
     */
    protected final BukkitPlugin plugin;

    /**
     * The {@link GUIConstructor} used to construct a new {@link GUIContainer}
     */
    protected GUIConstructor constructor;
    /**
     * The constructed {@link GUIContainer}, may be null if not previously constructed
     */
    protected @Nullable GUIContainer gui;
    /**
     * The {@link GUIOpenEvent} created after construction of the GUI, may be null if not previously constructed
     */
    protected @Nullable GUIOpenEvent event;

    /**
     * Construct a new <code>GUIOpenNewEvent</code>
     *
     * @param plugin      The {@link BukkitPlugin} instance
     * @param constructor The {@link GUIConstructor} used for constructing the new GUI
     */
    public GUIOpenNewEvent(BukkitPlugin plugin, GUIConstructor constructor)
    {
        this.plugin = plugin;
        this.constructor = constructor;
        this.gui = null;
        this.event = null;
    }

    /**
     * {@inheritDoc}
     *
     * @param event The event of the click
     * @param gui   The GUI that the event took place in
     */
    @Override
    public void execute(InventoryClickEvent event, GUIContainer gui)
    {
        ClickType clickType = event.getClick();
        if(clickType != ClickType.LEFT) return;
        construct();
        this.event.execute(event, gui);
    }

    /**
     * Get the {@link GUIConstructor} used to construct a new {@link GUIContainer}
     *
     * @return The <code>GUIConstructor</code>
     */
    public GUIConstructor getConstructor()
    {
        return constructor;
    }

    /**
     * Set the {@link GUIConstructor} used to construct a new {@link GUIContainer}
     *
     * @param constructor The new <code>GUIConstructor</code>
     */
    public void setConstructor(GUIConstructor constructor)
    {
        this.constructor = constructor;
    }

    /**
     * Get the {@link GUIContainer}
     *
     * @return The <code>GUIContainer</code>
     */
    public GUIContainer getGUI()
    {
        construct();
        return gui;
    }

    /**
     * Force the construction of the {@link GUIContainer}
     */
    public void construct()
    {
        if(this.event == null)
        {
            this.gui = constructor.get();
            this.event = new GUIOpenEvent(plugin, this.gui);
        }
    }
}
