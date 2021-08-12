package com.mikedeejay2.mikedeejay2lib.gui.modules.decoration;

import com.mikedeejay2.mikedeejay2lib.gui.GUIContainer;
import com.mikedeejay2.mikedeejay2lib.gui.item.GUIItem;
import com.mikedeejay2.mikedeejay2lib.gui.modules.GUIModule;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * Module that adds an outline of an item type to the sides of a GUI.
 * Use to add decoration to a GUI.
 *
 * @author Mikedeejay2
 */
public class GUIOutlineModule implements GUIModule
{

    /**
     * The GUI item that will be used for the border
     */
    private GUIItem outlineItem;

    /**
     * Construct a new <code>GUIOutlineModule</code>
     *
     * @param outlineItem The <code>GUIItem</code> to use
     */
    public GUIOutlineModule(GUIItem outlineItem)
    {
        this.outlineItem = outlineItem;
    }

    /**
     * Get the item that will be used for the outline item
     *
     * @return The <code>GUIItem</code> that will be used
     */
    public GUIItem getOutlineItem()
    {
        return outlineItem;
    }

    /**
     * Set the <code>GUIItem</code> that the outline will use
     *
     * @param outlineItem GUIItem for the outline to use
     */
    public void setOutlineItem(GUIItem outlineItem)
    {
        this.outlineItem = outlineItem;
    }

    /**
     * Set the <code>GUIItem</code> that the outline will use
     *
     * @param outlineItem ItemStack for the outline to use
     */
    public void setOutlineItem(ItemStack outlineItem)
    {
        this.outlineItem = new GUIItem(outlineItem);
    }

    /**
     * Method injected into the head of the GUI that adds an outline to the GUI
     *
     * @param player Player that is viewing the GUI
     * @param gui    The GUI
     */
    @Override
    public void onOpenHead(Player player, GUIContainer gui)
    {
        for(int i = 1; i <= gui.getCols(); i++)
        {
            gui.setItem(1, i, outlineItem);
            gui.setItem(gui.getRows(), i, outlineItem);
        }
        for(int i = 1; i <= gui.getRows() - 1; i++)
        {
            gui.setItem(i, 1, outlineItem);
            gui.setItem(i, gui.getCols(), outlineItem);
        }
    }
}
