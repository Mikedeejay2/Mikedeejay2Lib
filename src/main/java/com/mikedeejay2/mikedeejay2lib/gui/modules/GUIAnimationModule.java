package com.mikedeejay2.mikedeejay2lib.gui.modules;

import com.mikedeejay2.mikedeejay2lib.PluginBase;
import com.mikedeejay2.mikedeejay2lib.gui.GUIContainer;
import com.mikedeejay2.mikedeejay2lib.gui.animation.AnimationRuntime;
import com.mikedeejay2.mikedeejay2lib.gui.item.AnimatedGUIItem;
import com.mikedeejay2.mikedeejay2lib.gui.item.GUIItem;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class GUIAnimationModule extends GUIModule
{
    protected List<AnimatedGUIItem> animatedItems;
    protected AnimationRuntime runtime;
    protected long period;

    public GUIAnimationModule(PluginBase plugin, long period)
    {
        super(plugin);

        this.period = period == 0 ? 1 : period;
        this.animatedItems = new ArrayList<>();
    }

    @Override
    public void onOpenHead(Player player, GUIContainer gui)
    {
        this.runtime = new AnimationRuntime();
        runtime.setPlayer(player);
        runtime.setGUI(gui);
        runtime.setItems(animatedItems);
        runtime.runTaskTimerAsynchronously(plugin, period);
    }

    @Override
    public void onUpdateTail(Player player, GUIContainer gui)
    {
        GUIItem[][] items = gui.getItemsAsArray();
        List<AnimatedGUIItem> newAnimatedItems = new ArrayList<>();
        for(GUIItem[] subArray : items)
        {
            for(GUIItem item : subArray)
            {
                if(item == null || item.getClass() != AnimatedGUIItem.class) continue;
                newAnimatedItems.add((AnimatedGUIItem) item);
            }
        }
        animatedItems.clear();
        animatedItems.addAll(newAnimatedItems);
    }

    @Override
    public void onCloseHead(Player player, GUIContainer gui)
    {
        runtime.cancel();
    }
}
