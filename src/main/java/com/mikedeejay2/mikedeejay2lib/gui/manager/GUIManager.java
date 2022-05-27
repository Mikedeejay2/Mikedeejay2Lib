package com.mikedeejay2.mikedeejay2lib.gui.manager;

import com.mikedeejay2.mikedeejay2lib.BukkitPlugin;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

/**
 * A manager for managing players' GUIs. Needed for getting the GUI that the player is currently in and other relevant
 * information.
 *
 * @author Mikedeejay2
 */
public class GUIManager
{
    /**
     * The {@link BukkitPlugin} instance
     */
    protected final BukkitPlugin plugin;

    /**
     * The <code>Map</code> that stores the Player's GUIs
     */
    protected Map<Player, PlayerGUI> playerGUIs;

    /**
     * Construct a new <code>GUIManager</code>
     *
     * @param plugin The {@link BukkitPlugin} instance
     */
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

    /**
     * Get whether this manager contains a specific player
     *
     * @param player The player to check for
     * @return Whether the player exists in this manager or not
     */
    public boolean containsPlayer(Player player)
    {
        return playerGUIs.containsKey(player);
    }

    /**
     * Method to close all currently open GUIs. This method is called on plugin disable to ensure that a GUI doesn't
     * exist after a server reload.
     */
    public void closeAllGuis() {
        for(Map.Entry<Player, PlayerGUI> entry : playerGUIs.entrySet()) {
            Player player = entry.getKey();
            PlayerGUI playerGUI = entry.getValue();
            if(!playerGUI.isGuiOpened()) continue;
            if(playerGUI.getGUI() == null) continue;
            playerGUI.getGUI().close(player);
        }
    }
}
