package com.mikedeejay2.mikedeejay2lib.gui.modules.decoration;

import com.mikedeejay2.mikedeejay2lib.gui.GUIContainer;
import com.mikedeejay2.mikedeejay2lib.gui.item.AnimatedGUIItem;
import com.mikedeejay2.mikedeejay2lib.gui.modules.GUIModule;
import org.bukkit.entity.Player;

/**
 * Module that adds an outline of an item type to the sides of a GUI.
 * Use to add decoration to a GUI.
 *
 * @author Mikedeejay2
 */
public class GUIAnimOutlineModule implements GUIModule
{
    // The GUI item that will be used for the border
    private AnimatedGUIItem outlineItem;

    public GUIAnimOutlineModule(AnimatedGUIItem outlineItem)
    {
        this.outlineItem = outlineItem;
    }

    /**
     * Get the item that will be used for the outline item
     *
     * @return The <tt>GUIItem</tt> that will be used
     */
    public AnimatedGUIItem getOutlineItem()
    {
        return outlineItem;
    }

    /**
     * Set the <tt>GUIItem</tt> that the outline will use
     *
     * @param outlineItem GUIItem for the outline to use
     */
    public void setOutlineItem(AnimatedGUIItem outlineItem)
    {
        this.outlineItem = outlineItem;
    }

    /**
     * Method injected into the head of the GUI that adds an outline to the GUI
     *
     * @param player Player that is viewing the GUI
     * @param gui    The GUi
     */
    @Override
    public void onOpenHead(Player player, GUIContainer gui)
    {
        AnimatedGUIItem cloned;
        for(int i = 1; i <= gui.getCols(); i++)
        {
            cloned = outlineItem.clone();
            cloned.setDelay(1 + i);
            gui.setItem(1, i, cloned);
            cloned = outlineItem.clone();
            cloned.setDelay(gui.getRows() + i);
            gui.setItem(gui.getRows(), i, cloned);
        }
        for(int i = 1; i <= gui.getRows() - 1; i++)
        {
            cloned = outlineItem.clone();
            cloned.setDelay(i + 1);
            gui.setItem(i, 1, outlineItem);
            cloned = outlineItem.clone();
            cloned.setDelay(i + gui.getCols());
            gui.setItem(i, gui.getCols(), outlineItem);
        }
    }
}
