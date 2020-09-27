package com.mikedeejay2.mikedeejay2lib.gui.modules;

import com.mikedeejay2.mikedeejay2lib.PluginBase;
import com.mikedeejay2.mikedeejay2lib.gui.GUIContainer;
import com.mikedeejay2.mikedeejay2lib.gui.item.GUIItem;
import com.mikedeejay2.mikedeejay2lib.util.PluginInstancer;
import org.bukkit.entity.Player;

public abstract class GUIModule extends PluginInstancer<PluginBase>
{
    public GUIModule(PluginBase plugin)
    {
        super(plugin);
    }

    public abstract void onUpdate(Player player, GUIContainer gui);
    public abstract void onOpen(Player player, GUIContainer gui);
    public abstract void onClicked(Player player, int row, int col, GUIItem clicked, GUIContainer gui);
}
