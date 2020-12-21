package com.mikedeejay2.mikedeejay2lib.gui.modules.decoration;

import com.mikedeejay2.mikedeejay2lib.gui.GUIContainer;
import com.mikedeejay2.mikedeejay2lib.gui.item.GUIItem;
import com.mikedeejay2.mikedeejay2lib.gui.modules.GUIModule;
import com.mikedeejay2.mikedeejay2lib.util.item.ItemCreator;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * Module that adds an outline of an item type to the top and bottom row of a GUI.
 * Use to add decoration to a GUI.
 *
 * @author Mikedeejay2
 */
public class GUIOutlineModule extends GUIModule
{
    // The GUI item that will be used for the border
    private GUIItem outlineItem;

    public GUIOutlineModule()
    {
        this.outlineItem = new GUIItem(ItemCreator.createItem(Material.GRAY_STAINED_GLASS_PANE, 1, GUIContainer.EMPTY_NAME));
    }

    /**
     * Get the item that will be used for the outline item
     *
     * @return The <tt>GUIItem</tt> that will be used
     */
    public GUIItem getOutlineItem()
    {
        return outlineItem;
    }

    /**
     * Set the <tt>GUIItem</tt> that the outline will use
     *
     * @param outlineItem GUIItem for the outline to use
     */
    public void setOutlineItem(GUIItem outlineItem)
    {
        this.outlineItem = outlineItem;
    }

    /**
     * Set the <tt>GUIItem</tt> that the outline will use
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
     * @param gui    The GUi
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
