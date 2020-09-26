package com.mikedeejay2.mikedeejay2lib.gui.manager;

import com.mikedeejay2.mikedeejay2lib.gui.GUIContainer;
import org.bukkit.entity.Player;

public class PlayerGUI
{
    protected GUIContainer gui;
    protected Player player;

    public PlayerGUI(Player player)
    {
        this.player = player;
        this.gui = null;
    }

    public GUIContainer getGUI()
    {
        return gui;
    }

    public void setGui(GUIContainer gui)
    {
        this.gui = gui;
    }

    public Player getPlayer()
    {
        return player;
    }
}
