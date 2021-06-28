package com.mikedeejay2.mikedeejay2lib.gui.manager;

import com.mikedeejay2.mikedeejay2lib.BukkitPlugin;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

/**
 * A manager for managing players' GUIs. Needed for getting the GUI
 * that the player is currently in and other relevant information.
 *
 * @author Mikedeejay2
 */
public class GUIManager
{
    protected final BukkitPlugin plugin;
    // The HashMap that stores the Player's GUIs
    protected Map<Player, PlayerGUI> playerGUIs;

    public GUIManager(BukkitPlugin plugin)
    {
        this.plugin = plugin;
        this.playerGUIs = new HashMap<>();
    }

    /**
     * Get a player from the <code>GUIManager</code>.
     * This method will never be returned null. If this manager is not
     * storing the player, it will begin storing the player upon access.
     *
     * @param player Player to get
     * @return The player's <code>PlayerGUI</code>
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
     * @param player The player to check for
     */
    public void validCheck(Player player)
    {
        if(containsPlayer(player)) return;
        playerGUIs.put(player, new PlayerGUI(plugin, player));
    }

    /**
     * Remove a player from the <code>GUIManager</code>.
     *
     * @param player The player to remove
     */
    public void removePlayer(Player player)
    {
        if(!containsPlayer(player)) return;
        PlayerGUI gui = playerGUIs.get(player);
        gui.getGUI().close(player);
        playerGUIs.remove(player);
    }

    public boolean containsPlayer(Player player)
    {
        return playerGUIs.containsKey(player);
    }
}
