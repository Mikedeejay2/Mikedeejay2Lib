package com.mikedeejay2.mikedeejay2lib.gui.manager;

import com.mikedeejay2.mikedeejay2lib.BukkitPlugin;
import com.mikedeejay2.mikedeejay2lib.gui.GUIContainer;
import com.mikedeejay2.mikedeejay2lib.util.structure.NavigationHolder;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

/**
 * An object that holds a player's relevant GUI information about the
 * current GUI that the player is currently in and whether they are in
 * a GUI or not. Stored in {@link GUIManager}
 *
 * @author Mikedeejay2
 */
public class PlayerGUI
{
    /**
     * The {@link BukkitPlugin} instance
     */
    protected final BukkitPlugin plugin;
    // The map of NavigationHolders of this player
    protected Map<String, NavigationHolder<GUIContainer>> naviSystems;
    // The player's current GUIContainer
    protected GUIContainer gui;
    // The player of this PlayerGUI
    protected Player player;
    // Boolean for if the GUI is currently opened or not
    protected boolean guiOpened;
    protected boolean guiChange;

    public PlayerGUI(BukkitPlugin plugin, Player player)
    {
        this.plugin = plugin;
        this.player = player;
        this.gui = null;
        this.guiOpened = false;
        this.guiChange = false;
        this.naviSystems = new HashMap<>();
    }

    /**
     * Get this player's current GUI. This returns the player's last opened GUI,
     * but it's not confirmed that this GUI is currently open. Use {@link PlayerGUI#isGuiOpened()}
     * to get the opened status of the GUI.
     *
     * @return This player's current <code>GUIContainer</code>
     */
    public GUIContainer getGUI()
    {
        return gui;
    }

    /**
     * Set this player's current GUI to a new GUI. This method also opens the player's
     * GUI using {@link PlayerGUI#openGUI()}
     *
     * @param gui GUI that the player will view
     */
    public void setGUI(GUIContainer gui)
    {
        if(this.guiOpened)
        {
            this.guiChange = true;
            onClose();
        }
        this.gui = gui;
    }

    /**
     * Open this player's currently selected GUI
     */
    private void openGUI()
    {
        gui.onOpen(player);
    }

    /**
     * Close this player's currently opened GUI
     */
    private void onClose()
    {
        gui.onClose(player);
    }

    /**
     * Set whether the GUI is currently opened or closed
     *
     * @param state GUI state
     */
    public void setGUIState(boolean state)
    {
        guiOpened = state;
    }

    /**
     * Get the player that this <code>PlayerGUI</code> belongs to
     *
     * @return The player of this player GUI
     */
    public Player getPlayer()
    {
        return player;
    }

    /**
     * Does the player have a GUI opened?
     *
     * @return GUI opened state
     */
    public boolean isGuiOpened()
    {
        return guiOpened;
    }

    /**
     * Get a <code>NavigationHolder</code> for this player. This
     * method will never return null, if the <code>NavigationHolder</code>
     * doesn't exist it will be made
     *
     * @param navigationID The ID of the <code>NavigationHolder</code> to get
     * @return The requested <code>NavigationHolder</code>
     */
    public NavigationHolder<GUIContainer> getNavigation(String navigationID)
    {
        naviCheck(navigationID);
        return naviSystems.get(navigationID);
    }

    /**
     * Check whether a navigation system exists for this player.
     * If a system doesn't exist with the requested ID, a system will be
     * created with that ID. {@link PlayerGUI#getNavigation(String)} already
     * calls this method.
     *
     * @param navigationID The navigation ID to check for
     */
    public void naviCheck(String navigationID)
    {
        if(naviSystems.containsKey(navigationID)) return;
        naviSystems.put(navigationID, new NavigationHolder<>(navigationID));
    }

    /**
     * Get a list a map of String (Navigation Holder name) to <code>NavigationHolder</code>
     *
     * @return The map of systems
     */
    public Map<String, NavigationHolder<GUIContainer>> getNaviSystems()
    {
        return naviSystems;
    }

    /**
     * Internal value for safeness when closing GUIs
     *
     * @return Whether GUI has changed or not
     */
    public boolean isGuiChange()
    {
        return guiChange;
    }

    /**
     * Set the internal value for safeness when closing GUIs
     *
     * @param guiChange The new GUI change state
     */
    public void setGuiChange(boolean guiChange)
    {
        this.guiChange = guiChange;
    }
}
