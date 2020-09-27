package com.mikedeejay2.mikedeejay2lib.gui.event;

import com.mikedeejay2.mikedeejay2lib.PluginBase;
import com.mikedeejay2.mikedeejay2lib.gui.GUIContainer;
import com.mikedeejay2.mikedeejay2lib.gui.item.GUIItem;
import com.mikedeejay2.mikedeejay2lib.util.PluginInstancer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public abstract class GUIEvent extends PluginInstancer<PluginBase>
{
    public GUIEvent(PluginBase plugin)
    {
        super(plugin);
    }

    public abstract void execute(Player player, int row, int col, GUIItem clicked, GUIContainer gui);
}
