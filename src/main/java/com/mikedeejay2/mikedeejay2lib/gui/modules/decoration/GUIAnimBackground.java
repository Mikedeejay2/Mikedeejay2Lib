package com.mikedeejay2.mikedeejay2lib.gui.modules.decoration;

import com.mikedeejay2.mikedeejay2lib.gui.GUIContainer;
import com.mikedeejay2.mikedeejay2lib.gui.item.AnimatedGUIItem;
import com.mikedeejay2.mikedeejay2lib.gui.modules.GUIModule;
import org.bukkit.entity.Player;

/**
 * Module that adds an animated background to a GUI
 * Use to add decoration to a GUI.
 *
 * @author Mikedeejay2
 */
public class GUIAnimBackground implements GUIModule
{
    // The GUI item that will be used for the border
    private AnimatedGUIItem backgroundItem;

    public GUIAnimBackground(AnimatedGUIItem backgroundItem)
    {
        this.backgroundItem = backgroundItem;
    }

    /**
     * Get the item that will be used for the background item
     *
     * @return The <tt>GUIItem</tt> that will be used
     */
    public AnimatedGUIItem getBackgroundItem()
    {
        return backgroundItem;
    }

    /**
     * Set the <tt>GUIItem</tt> that the background will use
     *
     * @param backgroundItem GUIItem for the background to use
     */
    public void setBackgroundItem(AnimatedGUIItem backgroundItem)
    {
        this.backgroundItem = backgroundItem;
    }

    /**
     * Method injected into the head of the GUI that adds an background to the GUI
     *
     * @param player Player that is viewing the GUI
     * @param gui    The GUi
     */
    @Override
    public void onOpenHead(Player player, GUIContainer gui)
    {
        AnimatedGUIItem cloned;

        for(int row = 1; row <= gui.getRows(); row++)
        {
            for(int col = 1; col <= gui.getCols(); col++)
            {
                cloned = backgroundItem.clone();
                cloned.setDelay(row + col);
                gui.setItem(row, col, cloned);
                System.out.println("Row: " + row + ", Col: " + col);
            }
        }
    }
}
