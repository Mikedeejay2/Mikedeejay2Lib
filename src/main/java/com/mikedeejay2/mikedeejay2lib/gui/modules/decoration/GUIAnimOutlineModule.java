package com.mikedeejay2.mikedeejay2lib.gui.modules.decoration;

import com.mikedeejay2.mikedeejay2lib.gui.GUIContainer;
import com.mikedeejay2.mikedeejay2lib.gui.animation.GUIAnimPattern;
import com.mikedeejay2.mikedeejay2lib.gui.item.AnimatedGUIItem;
import com.mikedeejay2.mikedeejay2lib.gui.util.SlotMatcher;
import org.bukkit.entity.Player;

/**
 * Module that adds an outline of an item type to the sides of a GUI.
 * Use to add decoration to a GUI.
 *
 * @author Mikedeejay2
 */
public class GUIAnimOutlineModule extends GUIAnimDecoratorModule {
    /**
     * Construct a new <code>GUIAnimOutlineModule</code>
     *
     * @param outlineItem    The GUI item that will be used for the border
     * @param pattern        The animation pattern to use
     * @param animMultiplier The animation multiplier
     */
    public GUIAnimOutlineModule(AnimatedGUIItem outlineItem, GUIAnimPattern pattern, int animMultiplier) {
        super(null, outlineItem, pattern, animMultiplier);
    }

    /**
     * Construct a new <code>GUIAnimOutlineModule</code>
     *
     * @param outlineItem The GUI item that will be used for the border
     * @param pattern     The animation pattern to use
     */
    public GUIAnimOutlineModule(AnimatedGUIItem outlineItem, GUIAnimPattern pattern) {
        super(null, outlineItem, pattern);
    }

    /**
     * Construct a new <code>GUIAnimOutlineModule</code> with the default top left diagonal {@link GUIAnimPattern}
     *
     * @param outlineItem The GUI item that will be used for the border
     */
    public GUIAnimOutlineModule(AnimatedGUIItem outlineItem) {
        this(outlineItem, GUIAnimPattern.TOP_LEFT_DIAGONAL);
    }

    @Override
    public void onOpenHead(Player player, GUIContainer gui) {
        this.setMatcher(SlotMatcher.not(SlotMatcher.inRange(2, 2, gui.getRows() - 1, gui.getCols() - 1)));
        super.onOpenHead(player, gui);
    }
}
