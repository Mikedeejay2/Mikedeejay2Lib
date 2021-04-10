package com.mikedeejay2.mikedeejay2lib.gui.modules.decoration;

import com.mikedeejay2.mikedeejay2lib.gui.GUIContainer;
import com.mikedeejay2.mikedeejay2lib.gui.animation.GUIAnimPattern;
import com.mikedeejay2.mikedeejay2lib.gui.item.AnimatedGUIItem;
import com.mikedeejay2.mikedeejay2lib.gui.modules.GUIModule;
import org.bukkit.entity.Player;

/**
 * Module that adds an animated background to a GUI
 * Use to add decoration to a GUI.
 *
 * @author Mikedeejay2
 */
public class GUIAnimBackgroundModule implements GUIModule
{
    // The animation pattern to use
    private GUIAnimPattern pattern;
    // The GUI item that will be used for the border
    private AnimatedGUIItem backgroundItem;

    public GUIAnimBackgroundModule(AnimatedGUIItem backgroundItem, GUIAnimPattern pattern)
    {
        this.pattern = pattern;
        this.backgroundItem = backgroundItem;
    }

    public GUIAnimBackgroundModule(AnimatedGUIItem backgroundItem)
    {
        this(backgroundItem, GUIAnimPattern.TOP_LEFT_DIAGONAL);
    }

    /**
     * Method injected into the head of the GUI that adds an background to the GUI
     *
     * @param player Player that is viewing the GUI
     * @param gui    The GUI
     */
    @Override
    public void onOpenHead(Player player, GUIContainer gui)
    {
        int maxRow = gui.getRows();
        int maxCol = gui.getCols();
        for(int row = 1; row <= gui.getRows(); row++)
        {
            for(int col = 1; col <= gui.getCols(); col++)
            {
                gui.setItem(row, col,  pattern.getItemFor(backgroundItem, row, col, maxRow, maxCol));
            }
        }
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
     * Get the {@link GUIAnimPattern} used for animating the items
     *
     * @return The current animation pattern
     */
    public GUIAnimPattern getPattern()
    {
        return pattern;
    }

    /**
     * Set the {@link GUIAnimPattern} used for animating the items
     *
     * @param pattern The new animation pattern
     */
    public void setPattern(GUIAnimPattern pattern)
    {
        this.pattern = pattern;
    }
}
