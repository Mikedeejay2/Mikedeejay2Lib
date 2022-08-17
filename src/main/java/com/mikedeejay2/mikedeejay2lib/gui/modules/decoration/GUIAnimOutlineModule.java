package com.mikedeejay2.mikedeejay2lib.gui.modules.decoration;

import com.mikedeejay2.mikedeejay2lib.gui.GUIContainer;
import com.mikedeejay2.mikedeejay2lib.gui.animation.GUIAnimPattern;
import com.mikedeejay2.mikedeejay2lib.gui.item.AnimatedGUIItem;
import com.mikedeejay2.mikedeejay2lib.gui.modules.GUIModule;
import org.bukkit.entity.Player;

/**
 * Module that adds an outline of an item type to the sides of a GUI.
 * Use to add decoration to a GUI.
 *
 * @author Mikedeejay2
 */
public class GUIAnimOutlineModule implements GUIModule {
    /**
     * The animation pattern to use
     */
    private GUIAnimPattern pattern;

    /**
     * The GUI item that will be used for the border
     */
    private AnimatedGUIItem outlineItem;

    /**
     * Construct a new <code>GUIAnimOutlineModule</code>
     *
     * @param outlineItem The GUI item that will be used for the border
     * @param pattern     The animation pattern to use
     */
    public GUIAnimOutlineModule(AnimatedGUIItem outlineItem, GUIAnimPattern pattern) {
        this.pattern = pattern;
        this.outlineItem = outlineItem;
    }

    /**
     * Construct a new <code>GUIAnimOutlineModule</code> with the default top left diagonal {@link GUIAnimPattern}
     *
     * @param outlineItem The GUI item that will be used for the border
     */
    public GUIAnimOutlineModule(AnimatedGUIItem outlineItem) {
        this(outlineItem, GUIAnimPattern.TOP_LEFT_DIAGONAL);
    }

    /**
     * Method injected into the head of the GUI that adds an outline to the GUI
     *
     * @param player Player that is viewing the GUI
     * @param gui    The GUI
     */
    @Override
    public void onOpenHead(Player player, GUIContainer gui) {
        int maxRow = gui.getRows();
        int maxCol = gui.getCols();
        for(int i = 1; i <= gui.getCols(); i++) {
            gui.setItem(1, i, pattern.getItemFor(outlineItem, 1, i, maxRow, maxCol));
            gui.setItem(gui.getRows(), i, pattern.getItemFor(outlineItem, gui.getRows(), i, maxRow, maxCol));
        }
        for(int i = 1; i <= gui.getRows() - 1; i++) {
            gui.setItem(i, 1,  pattern.getItemFor(outlineItem, i, 1, maxRow, maxCol));
            gui.setItem(i, gui.getCols(),  pattern.getItemFor(outlineItem, i, gui.getCols(), maxRow, maxCol));
        }
    }

    /**
     * Get the item that will be used for the outline item
     *
     * @return The <code>GUIItem</code> that will be used
     */
    public AnimatedGUIItem getOutlineItem() {
        return outlineItem;
    }

    /**
     * Set the <code>GUIItem</code> that the outline will use
     *
     * @param outlineItem GUIItem for the outline to use
     */
    public void setOutlineItem(AnimatedGUIItem outlineItem) {
        this.outlineItem = outlineItem;
    }

    /**
     * Get the {@link GUIAnimPattern} used for animating the items
     *
     * @return The current animation pattern
     */
    public GUIAnimPattern getPattern() {
        return pattern;
    }

    /**
     * Set the {@link GUIAnimPattern} used for animating the items
     *
     * @param pattern The new animation pattern
     */
    public void setPattern(GUIAnimPattern pattern) {
        this.pattern = pattern;
    }
}
