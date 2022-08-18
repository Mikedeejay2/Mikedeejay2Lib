package com.mikedeejay2.mikedeejay2lib.gui.modules.decoration;

import com.mikedeejay2.mikedeejay2lib.gui.animation.GUIAnimPattern;
import com.mikedeejay2.mikedeejay2lib.gui.item.AnimatedGUIItem;
import com.mikedeejay2.mikedeejay2lib.gui.util.SlotMatcher;
import org.jetbrains.annotations.Nullable;

/**
 * A GUI module made easily specify where {@link AnimatedGUIItem AnimatedGUIItems} should be placed in a GUI, i.e.
 * <i>decorating</i> the GUI. This module uses the helper system {@link SlotMatcher} to match the slots that should be
 * decorated.
 *
 * @author Mikedeejay2
 */
public class GUIAnimDecoratorModule extends GUIAbstractDecoratorModule {
    /**
     * The animation pattern to use
     */
    protected GUIAnimPattern pattern;

    /**
     * The animated item used for decorating the GUI
     */
    protected AnimatedGUIItem guiItem;

    /**
     * Construct a new <code>GUIAnimDecoratorModule</code>
     *
     * @param matcher The {@link SlotMatcher} to use
     * @param guiItem The animated item used for decorating the GUI
     * @param layerName The name of the layer to decorate
     */
    public GUIAnimDecoratorModule(SlotMatcher matcher, AnimatedGUIItem guiItem, GUIAnimPattern pattern, @Nullable String layerName) {
        super(matcher, layerName);
        this.guiItem = guiItem;
        this.pattern = pattern;
    }

    /**
     * Construct a new <code>GUIAnimDecoratorModule</code>
     *
     * @param matcher The {@link SlotMatcher} to use
     * @param guiItem The animated item used for decorating the GUI
     */
    public GUIAnimDecoratorModule(SlotMatcher matcher, AnimatedGUIItem guiItem, GUIAnimPattern pattern) {
        this(matcher, guiItem, pattern, null);
    }

    /**
     * Get the item on a successful slot match
     *
     * @param matchData The provided {@link SlotMatcher.MatchData}
     * @return The animated item to be used
     */
    @Override
    protected AnimatedGUIItem getItem(SlotMatcher.MatchData matchData) {
        int maxRow = matchData.gui.getRows();
        int maxCol = matchData.gui.getCols();
        return pattern.getItemFor(guiItem, matchData.row, matchData.col, maxRow, maxCol);
    }

    /**
     * Get the animated item used for decorating the GUI
     *
     * @return The animated item used for decorating the GUI
     */
    public AnimatedGUIItem getGuiItem() {
        return guiItem;
    }

    /**
     * Set the animated item used for decorating the GUI
     *
     * @param guiItem The new animated item
     */
    public void setGuiItem(AnimatedGUIItem guiItem) {
        this.guiItem = guiItem;
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
