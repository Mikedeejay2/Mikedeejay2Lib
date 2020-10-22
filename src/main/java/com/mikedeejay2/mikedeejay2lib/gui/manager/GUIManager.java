package com.mikedeejay2.mikedeejay2lib.gui.manager;

import com.mikedeejay2.mikedeejay2lib.PluginBase;
import org.bukkit.entity.Player;

import java.util.HashMap;

/**
 * A manager for managing players' GUIs. Needed for getting the GUI
 * that the player is currently in and other relevant information.
 *
 * @author Mikedeejay2
 */
public class GUIManager
{
    protected final PluginBase plugin;
    // The HashMap that stores the Player's GUIs
    protected HashMap<Player, PlayerGUI> playerGUIs;

    public GUIManager(PluginBase plugin)
    {
        this.plugin = plugin;
        this.playerGUIs = new HashMap<>();
    }

    /**
     * Get a player from the <tt>GUIManager</tt>.
     * This method will never be returned null. If this manager is not
     * storing the player, it will begin storing the player upon access.
     *
     * @param player Player to get
     * @return The player's <tt>PlayerGUI</tt>
     */
    public PlayerGUI getPlayer(Player player)
    {
        validCheck(player);
        return playerGUIs.get(player);
    }

    /**
     * Check whether a player is currently in the GUIManager.
     * {@link GUIManager#getPlayer(Player)} automatically runs this method,
     * so you don't have to run this yourself.
     *
     * @param player
     */
    public void validCheck(Player player)
    {
        if(playerGUIs.containsKey(player)) return;
        playerGUIs.put(player, new PlayerGUI(plugin, player));
    }

    /**
     * Remove a player from the <tt>GUIManager</tt>.
     *
     * @param player The player to remove
     */
    public void removePlayer(Player player)
    {
        if(!playerGUIs.containsKey(player)) return;
        PlayerGUI gui = playerGUIs.get(player);
        gui.closeGUI();
        playerGUIs.remove(player);
    }
}
