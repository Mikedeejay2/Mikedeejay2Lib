package com.mikedeejay2.mikedeejay2lib.gui.event.list;

import com.mikedeejay2.mikedeejay2lib.PluginBase;
import com.mikedeejay2.mikedeejay2lib.gui.GUIContainer;
import com.mikedeejay2.mikedeejay2lib.gui.event.GUIEvent;
import com.mikedeejay2.mikedeejay2lib.gui.item.GUIItem;
import com.mikedeejay2.mikedeejay2lib.gui.modules.GUIListModule;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class GUIListSearchOffEvent extends GUIEvent
{
    public GUIListSearchOffEvent(PluginBase plugin)
    {
        super(plugin);
    }

    @Override
    public void execute(Player player, int row, int col, GUIItem clicked, GUIContainer gui)
    {
        gui.getModule(GUIListModule.class).disableSearchMode();
        gui.update(player);
    }
}
