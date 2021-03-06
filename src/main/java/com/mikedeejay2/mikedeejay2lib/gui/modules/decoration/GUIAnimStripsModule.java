package com.mikedeejay2.mikedeejay2lib.gui.modules.decoration;

import com.mikedeejay2.mikedeejay2lib.gui.GUIContainer;
import com.mikedeejay2.mikedeejay2lib.gui.GUILayer;
import com.mikedeejay2.mikedeejay2lib.gui.item.AnimatedGUIItem;
import com.mikedeejay2.mikedeejay2lib.gui.modules.GUIModule;
import org.bukkit.entity.Player;

/**
 * Adds animated strips along the sides of a GUI.
 *
 * @author Mikedeejay2
 */
public class GUIAnimStripsModule implements GUIModule
{
    protected AnimatedGUIItem item;

    public GUIAnimStripsModule(AnimatedGUIItem item)
    {
        this.item = item;
    }

    @Override
    public void onOpenHead(Player player, GUIContainer gui)
    {
        GUILayer layer = gui.getLayer(0);
        for(int row = 1; row <= gui.getRows(); ++row)
        {
            AnimatedGUIItem curItem = (AnimatedGUIItem) item.clone();
            curItem.setDelay(gui.getRows() - (row - 1));
            layer.setItem(row, 1, curItem);
            layer.setItem(row, gui.getCols(), curItem);
        }
    }
}
