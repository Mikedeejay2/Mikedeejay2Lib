package com.mikedeejay2.mikedeejay2lib.gui.manager;

import com.mikedeejay2.mikedeejay2lib.PluginBase;
import com.mikedeejay2.mikedeejay2lib.gui.GUIContainer;
import com.mikedeejay2.mikedeejay2lib.util.PluginInstancer;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * An object that holds a player's relevant GUI information about the
 * current GUI that the player is currently in and whether they are in
 * a GUI or not. Stored in {@link GUIManager}
 *
 * @author Mikedeejay2
 */
public class PlayerGUI extends PluginInstancer<PluginBase>
{
    // The map of NavigationSystems of this player
    protected Map<String, NavigationSystem> naviSystems;
    // The player's current GUIContainer
    protected GUIContainer gui;
    // The player of this PlayerGUI
    protected Player player;
    // Boolean for if the GUI is currently opened or not
    protected boolean guiOpened;

    public PlayerGUI(PluginBase plugin, Player player)
    {
        super(plugin);
        this.player = player;
        this.gui = null;
        this.guiOpened = false;
        this.naviSystems = new HashMap<>();
    }

    /**
     * Get this player's current GUI. This returns the player's last opened GUI,
     * but it's not confirmed that this GUI is currently open. Use {@link PlayerGUI#isGuiOpened()}
     * to get the opened status of the GUI.
     *
     * @return This player's current <tt>GUIContainer</tt>
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
        this.gui = gui;
        openGUI();
    }

    /**
     * Open this player's currently selected GUI
     */
    public void openGUI()
    {
        setGUIState(true);
        gui.open(player);
    }

    /**
     * Close this player's currently opened GUI
     */
    public void closeGUI()
    {
        setGUIState(false);
        gui.close(player);
    }

    /**
     * Set whether the GUI is currently opened or closed
     *
     * @param state GUI state
     */
    public void setGUIState(boolean state)
    {
        new BukkitRunnable()
        {
            @Override
            public void run()
            {
                guiOpened = state;
            }
        }.runTask(plugin);
    }

    /**
     * Get the player that this <tt>PlayerGUI</tt> belongs to
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
     * Get a <tt>NavigationSystem</tt> for this player. This
     * method will never return null, if the <tt>NavigationSystem</tt>
     * doesn't exist it will be made
     *
     * @param navigationID The ID of the <tt>NavigationSystem</tt> to get
     * @return The requested <tt>NavigationSystem</tt>
     */
    public NavigationSystem getNaviSystem(String navigationID)
    {
        naviCheck(navigationID);
        return naviSystems.get(navigationID);
    }

    /**
     * Check whether a navigation system exists for this player.
     * If a system doesn't exist with the requested ID, a system will be
     * created with that ID. {@link PlayerGUI#getNaviSystem(String)} already
     * calls this method.
     *
     * @param navigationID The navigation ID to check for
     */
    public void naviCheck(String navigationID)
    {
        if(naviSystems.containsKey(navigationID)) return;
        naviSystems.put(navigationID, new NavigationSystem(player, navigationID));
    }

    /**
     * Get a list a map of String (Navigation System name) to <tt>NavigationSystem</tt>
     *
     * @return The map of systems
     */
    public Map<String, NavigationSystem> getNaviSystems()
    {
        return naviSystems;
    }
}
