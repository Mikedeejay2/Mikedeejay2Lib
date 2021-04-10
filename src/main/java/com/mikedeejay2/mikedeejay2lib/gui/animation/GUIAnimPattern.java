package com.mikedeejay2.mikedeejay2lib.gui.animation;

import com.mikedeejay2.mikedeejay2lib.gui.GUIContainer;
import com.mikedeejay2.mikedeejay2lib.gui.item.AnimatedGUIItem;

/**
 * Enum of different animation patterns. Has helper methods for getting the delay of a specific
 * row and column for an animation.
 *
 * @author Mikedeejay2
 */
public enum GUIAnimPattern
{
    TOP_LEFT_DIAGONAL((row, col, maxRow, maxCol) -> (row - 1) + (col - 1)),
    TOP_RIGHT_DIAGONAL((row, col, maxRow, maxCol) -> (row - 1) + (maxCol - (col - 1))),
    BOTTOM_LEFT_DIAGONAL((row, col, maxRow, maxCol) -> (maxRow - (row - 1)) + (col - 1)),
    BOTTOM_RIGHT_DIAGONAL((row, col, maxRow, maxCol) -> (maxRow - (row - 1)) + (maxCol - (col - 1))),
    TOP_DOWN((row, col, maxRow, maxCol) -> row - 1),
    DOWN_UP((row, col, maxRow, maxCol) -> maxRow - (row - 1)),
    LEFT_RIGHT((row, col, maxRow, maxCol) -> col - 1),
    RIGHT_LEFT((row, col, maxRow, maxCol) -> maxCol - (col - 1)),
    ;

    private final PatternApplier applier;

    GUIAnimPattern(PatternApplier applier)
    {
        this.applier = applier;
    }

    /**
     * Get a clone of the <tt>original</tt> {@link AnimatedGUIItem} based off of the row and column
     * of the item
     *
     * @param original The original item
     * @param row      The item's current row
     * @param col      The item's current column
     * @param maxRow   The maximum row for the GUI, defined in {@link GUIContainer#getRows()}.
     * @param maxCol   The maximum column for the GUI, defined in {@link GUIContainer#getRows()}
     * @return The newly cloned item with proper delay for the slot
     */
    public AnimatedGUIItem getItemFor(AnimatedGUIItem original, int row, int col, int maxRow, int maxCol)
    {
        AnimatedGUIItem clone = original.clone();
        int delay = getDelayFor(row, col, maxRow, maxCol);
        clone.setDelay(delay);
        return clone;
    }

    /**
     * Get the delay for a row and column of a GUI
     *
     * @param row    The row to get
     * @param col    The column to get
     * @param maxRow The maximum row for the GUI, defined in {@link GUIContainer#getRows()}.
     * @param maxCol The maximum column for the GUI, defined in {@link GUIContainer#getRows()}
     * @return The delay for the specified slot
     */
    public int getDelayFor(int row, int col, int maxRow, int maxCol)
    {
        return applier.getDelayFor(row, col, maxRow, maxCol);
    }

    /**
     * Simple functional interface for applying a pattern to a row and column
     */
    @FunctionalInterface
    private interface PatternApplier
    {
        /**
         * Get the delay for a row and column of a GUI
         *
         * @param row    The row to get
         * @param col    The column to get
         * @param maxRow The maximum row for the GUI, defined in {@link GUIContainer#getRows()}.
         * @param maxCol The maximum column for the GUI, defined in {@link GUIContainer#getRows()}
         * @return The delay for the specified slot
         */
        int getDelayFor(int row, int col, int maxRow, int maxCol);
    }
}
