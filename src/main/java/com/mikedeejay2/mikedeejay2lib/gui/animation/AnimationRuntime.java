package com.mikedeejay2.mikedeejay2lib.gui.animation;

import com.mikedeejay2.mikedeejay2lib.gui.GUIContainer;
import com.mikedeejay2.mikedeejay2lib.gui.item.AnimatedGUIItem;
import com.mikedeejay2.mikedeejay2lib.runnable.EnhancedRunnable;
import org.bukkit.entity.Player;

import java.util.List;

public class AnimationRuntime extends EnhancedRunnable
{
    protected List<AnimatedGUIItem> items;
    protected GUIContainer gui;
    protected Player player;

    public void setItems(List<AnimatedGUIItem> items)
    {
        this.items = items;
    }

    public void setPlayer(Player player)
    {
        this.player = player;
    }

    public void setGUI(GUIContainer gui)
    {
        this.gui = gui;
    }

    @Override
    public void onRun()
    {
        boolean shouldUpdate = false;
        for(AnimatedGUIItem item : items)
        {
            if(item.tick(period)) shouldUpdate = true;
        }
        if(shouldUpdate) gui.update(player);
    }

    @Override
    public void onFirstRun()
    {

    }

    @Override
    public void onLastRun()
    {

    }

    public List<AnimatedGUIItem> getItems()
    {
        return items;
    }

    public GUIContainer getGui()
    {
        return gui;
    }

    public Player getPlayer()
    {
        return player;
    }
}
