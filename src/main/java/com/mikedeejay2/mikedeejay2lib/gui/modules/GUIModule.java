package com.mikedeejay2.mikedeejay2lib.gui.modules;

import com.mikedeejay2.mikedeejay2lib.PluginBase;
import com.mikedeejay2.mikedeejay2lib.gui.GUIContainer;
import com.mikedeejay2.mikedeejay2lib.gui.item.GUIItem;
import com.mikedeejay2.mikedeejay2lib.util.PluginInstancer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public abstract class GUIModule extends PluginInstancer<PluginBase>
{
    GUIContainer gui;

    public GUIModule(PluginBase plugin, GUIContainer gui)
    {
        super(plugin);
        this.gui = gui;
    }

    public abstract void onOpen(Player player);
    public abstract void onUpdate(Player player);
    public abstract void onClicked(Player player, int row, int col, GUIItem clicked);
}
