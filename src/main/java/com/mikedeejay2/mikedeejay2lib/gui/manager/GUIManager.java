package com.mikedeejay2.mikedeejay2lib.gui.manager;

import com.mikedeejay2.mikedeejay2lib.PluginBase;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class GUIManager
{
    protected HashMap<Player, PlayerGUI> playerGUIs;


    public GUIManager(PluginBase plugin)
    {
        this.playerGUIs = new HashMap<>();
    }

    public PlayerGUI getPlayer(Player player)
    {
        validCheck(player);
        return playerGUIs.get(player);
    }

    public void validCheck(Player player)
    {
        if(playerGUIs.containsKey(player)) return;
        playerGUIs.put(player, new PlayerGUI(player));
    }
}
