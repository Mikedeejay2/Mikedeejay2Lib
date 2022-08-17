package com.mikedeejay2.mikedeejay2lib.gui.animation;

import com.mikedeejay2.mikedeejay2lib.gui.GUIContainer;
import com.mikedeejay2.mikedeejay2lib.gui.item.AnimatedGUIItem;

/**
 * Enum of different animation patterns. Has helper methods for getting the delay of a specific
 * row and column for an animation.
 *
 * @author Mikedeejay2
 */
public enum GUIAnimPattern {
    /**
     * Diagonal animation starting from the top left
     */
    TOP_LEFT_DIAGONAL((row, col, maxRow, maxCol) -> (row - 1) + (col - 1)),
    /**
     * Diagonal animation starting from the top right
     */
    TOP_RIGHT_DIAGONAL((row, col, maxRow, maxCol) -> (row - 1) + (maxCol - (col - 1))),
    /**
     * Diagonal animation starting from the bottom left
     */
    BOTTOM_LEFT_DIAGONAL((row, col, maxRow, maxCol) -> (maxRow - (row - 1)) + (col - 1)),
    /**
     * Diagonal animation starting from the bottom right
     */
    BOTTOM_RIGHT_DIAGONAL((row, col, maxRow, maxCol) -> (maxRow - (row - 1)) + (maxCol - (col - 1))),
    /**
     * Animation starting from the top and animating down
     */
    TOP_DOWN((row, col, maxRow, maxCol) -> row - 1),
    /**
     * Animation starting from the bottom and animating up
     */
    DOWN_UP((row, col, maxRow, maxCol) -> maxRow - (row - 1)),
    /**
     * Animation starting from the left and animating to the right
     */
    LEFT_RIGHT((row, col, maxRow, maxCol) -> col - 1),
    /**
     * Animation starting from the right and animating to the left
     */
    RIGHT_LEFT((row, col, maxRow, maxCol) -> maxCol - (col - 1)),
    ;

    /**
     * The {@link PatternApplier} for getting the delay for an item's position
     */
    private final PatternApplier applier;

    /**
     * Enum <code>GUIAnimPattern</code> constructor
     *
     * @param applier The {@link PatternApplier} of the pattern
     */
    GUIAnimPattern(PatternApplier applier) {
        this.applier = applier;
    }

    /**
     * Get a clone of the <code>original</code> {@link AnimatedGUIItem} based off of the row and column
     * of the item
     *
     * @param original The original item
     * @param row      The item's current row
     * @param col      The item's current column
     * @param maxRow   The maximum row for the GUI, defined in {@link GUIContainer#getRows()}.
     * @param maxCol   The maximum column for the GUI, defined in {@link GUIContainer#getRows()}
     * @return The newly cloned item with proper delay for the slot
     */
    public AnimatedGUIItem getItemFor(AnimatedGUIItem original, int row, int col, int maxRow, int maxCol) {
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
    public int getDelayFor(int row, int col, int maxRow, int maxCol) {
        return applier.getDelayFor(row, col, maxRow, maxCol);
    }

    /**
     * Simple functional interface for applying a pattern to a row and column
     */
    @FunctionalInterface
    private interface PatternApplier {
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
