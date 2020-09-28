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

    public void onUpdateHead(Player player, GUIContainer gui) {}
    public void onUpdateTail(Player player, GUIContainer gui) {}
    public void onOpenHead(Player player, GUIContainer gui) {}
    public void onOpenTail(Player player, GUIContainer gui) {}
    public void onCloseHead(Player player, GUIContainer gui) {}
    public void onCloseTail(Player player, GUIContainer gui) {}
    public void onClickedHead(Player player, int row, int col, GUIItem clicked, GUIContainer gui) {}
    public void onClickedTail(Player player, int row, int col, GUIItem clicked, GUIContainer gui) {}
}
