package com.mikedeejay2.mikedeejay2lib.gui.manager;

import com.mikedeejay2.mikedeejay2lib.PluginBase;
import com.mikedeejay2.mikedeejay2lib.gui.GUIContainer;
import com.mikedeejay2.mikedeejay2lib.util.PluginInstancer;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class PlayerGUI extends PluginInstancer<PluginBase>
{
    protected GUIContainer gui;
    protected Player player;
    protected boolean guiOpened;

    public PlayerGUI(PluginBase plugin, Player player)
    {
        super(plugin);
        this.player = player;
        this.gui = null;
        this.guiOpened = false;
    }

    public GUIContainer getGUI()
    {
        return gui;
    }

    public void setGUI(GUIContainer gui)
    {
        this.gui = gui;
        openGUI();
    }

    public void openGUI()
    {
        setGUIState(true);
        gui.open(player);
    }

    public void closeGUI()
    {
        setGUIState(false);
        gui.close(player);
    }

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

    public Player getPlayer()
    {
        return player;
    }

    public boolean isGuiOpened()
    {
        return guiOpened;
    }
}
