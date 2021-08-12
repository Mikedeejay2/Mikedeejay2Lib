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
    /**
     * The item that will be used for the strips
     */
    protected AnimatedGUIItem item;

    /**
     * Construct a new <code>GUIAnimStripsModule</code>
     *
     * @param item The item that will be used for the strips
     */
    public GUIAnimStripsModule(AnimatedGUIItem item)
    {
        this.item = item;
    }

    /**
     * Method injected into the head of the GUI that adds the strips to the GUI
     *
     * @param player The player that is viewing the GUI
     * @param gui    The GUI
     */
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

    /**
     * Get the item that will be used for the strips
     *
     * @return The {@link AnimatedGUIItem} being used
     */
    public AnimatedGUIItem getItem()
    {
        return item;
    }

    /**
     * Set the item that will be used for the strips
     *
     * @param item The new item
     */
    public void setItem(AnimatedGUIItem item)
    {
        this.item = item;
    }
}
