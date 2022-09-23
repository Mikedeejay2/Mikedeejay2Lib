package com.mikedeejay2.mikedeejay2lib.gui.animation;

import com.mikedeejay2.mikedeejay2lib.gui.GUIContainer;
import com.mikedeejay2.mikedeejay2lib.gui.item.AnimatedGUIItem;
import com.mikedeejay2.mikedeejay2lib.util.math.MathUtil;

import java.util.function.IntBinaryOperator;

/**
 * Defines an animation for animated GUI elements. For use, see
 * {@link com.mikedeejay2.mikedeejay2lib.gui.modules.decoration.GUIAnimDecoratorModule}
 *
 * @author Mikedeejay2
 */
public class AnimationSpecification {
    /**
     * The {@link Position} of this specification
     */
    protected final Position position;
    /**
     * The {@link Style} of this specification
     */
    protected final Style style;

    /**
     * Construct a new <code>AnimationSpecification</code>
     *
     * @param position The {@link Position}, the center of the animation
     * @param style    The {@link Style}, how the animation moves
     */
    public AnimationSpecification(Position position, Style style) {
        this.position = position;
        this.style = style;
    }

    /**
     * Get the {@link Position} of this specification
     *
     * @return The {@link Position}
     */
    public Position getPosition() {
        return position;
    }

    /**
     * Get the {@link Style} of this specification
     *
     * @return The {@link Style}
     */
    public Style getStyle() {
        return style;
    }

    /**
     * Get a clone of the <code>original</code> {@link AnimatedGUIItem} based off of the row and column
     * of the item
     *
     * @param original   The original item
     * @param row        The item's current row
     * @param col        The item's current column
     * @param maxRow     The maximum row for the GUI, defined in {@link GUIContainer#getRows()}.
     * @param maxCol     The maximum column for the GUI, defined in {@link GUIContainer#getCols()}
     * @param multiplier The multiplier of the delay
     * @return The newly cloned item with proper delay for the slot
     */
    public AnimatedGUIItem getItemFor(AnimatedGUIItem original, int row, int col, int maxRow, int maxCol, int multiplier) {
        AnimatedGUIItem clone = original.clone();
        int delay = style.getDelayFor(position, row, col, maxRow, maxCol, multiplier);
        clone.setDelay(clone.getDelay() + delay);
        return clone;
    }

    /**
     * Get a clone of the <code>original</code> {@link AnimatedGUIItem} based off of the row and column
     * of the item
     *
     * @param original   The original item
     * @param row        The item's current row
     * @param col        The item's current column
     * @param maxRow     The maximum row for the GUI, defined in {@link GUIContainer#getRows()}.
     * @param maxCol     The maximum column for the GUI, defined in {@link GUIContainer#getCols()}
     * @return The newly cloned item with proper delay for the slot
     */
    public AnimatedGUIItem getItemFor(AnimatedGUIItem original, int row, int col, int maxRow, int maxCol) {
        return getItemFor(original, row, col, maxRow, maxCol, 1);
    }

    /**
     * Holds the position of an {@link AnimationSpecification}
     *
     * @author Mikedeejay2
     */
    public static final class Position {
        /**
         * Refers to the maximum row or column of a GUI
         */
        public static final int MAX = -1;

        /**
         * The top left of a GUI
         */
        public static final Position TOP_LEFT = new Position(1, 1);
        /**
         * The top right of a GUI
         */
        public static final Position TOP_RIGHT = new Position(1, MAX);
        /**
         * The bottom left of a GUI
         */
        public static final Position BOTTOM_LEFT = new Position(MAX, 1);
        /**
         * The bottom right of a GUI
         */
        public static final Position BOTTOM_RIGHT = new Position(MAX, MAX);

        /**
         * The row of this position
         */
        private final int row;
        /**
         * The column of this position
         */
        private final int col;

        /**
         * Construct a new <code>Position</code>
         *
         * @param row The row of this position
         * @param col The column of this position
         */
        private Position(int row, int col) {
            this.row = row;
            this.col = col;
        }

        /**
         * Get the row of this position
         *
         * @return The row
         */
        public int getRow() {
            return row;
        }

        /**
         * Get the column of this position
         *
         * @return The column
         */
        public int getCol() {
            return col;
        }

        /**
         * Gets how far away <code>curRow</code> is from this position's row.
         *
         * @param curRow The row to check
         * @param maxRow The maximum row of the GUI
         * @return The row offset
         */
        public int getRowOffset(int curRow, int maxRow) {
            return this.row == MAX ? Math.abs(maxRow - curRow) : Math.abs(this.row - curRow);
        }

        /**
         * Gets how far away <code>curCol</code> is from this position's column.
         *
         * @param curCol The column to check
         * @param maxCol The maximum column of the GUI
         * @return The column offset
         */
        public int getColOffset(int curCol, int maxCol) {
            return this.col == MAX ? Math.abs(maxCol - curCol) : Math.abs(this.col - curCol);
        }

        /**
         * Get a position of a specified row and column
         *
         * @param row The row
         * @param col The column
         * @return The new position
         */
        public static Position of(int row, int col) {
            return new Position(row, col);
        }
    }

    /**
     * The animation style of an {@link AnimationSpecification}
     *
     * @author Mikedeejay2
     */
    public enum Style {
        /**
         * Linear animation style. Also known as a diagonal style.
         */
        LINEAR((row, col) -> row + col),
        /**
         * Circular animation style. This algorithm has the potential to affect performance on large GUIs as it uses
         * {@link Math#sqrt(double)}.
         */
        CIRCULAR((row, col) -> (int) (Math.sqrt(MathUtil.square(row) + MathUtil.square(col)) * 2)),
        /**
         * Square animation style
         */
        SQUARE((row, col) -> Math.max(row, col)),
        /**
         * Row-only animation style. Refers to this style only using rows to generate the animation.
         */
        ROW((row, col) -> row),
        /**
         * Column-only animation style. Refers to this style only using columns to generate the animation.
         */
        COL((row, col) -> col),
        ;

        /**
         * The operator used to apply the style
         */
        private final IntBinaryOperator operator;

        Style(IntBinaryOperator operator) {
            this.operator = operator;
        }

        /**
         * Get the delay for a row and column of a GUI
         *
         * @param position   The {@link Position} of the animation
         * @param row        The row to get
         * @param col        The column to get
         * @param maxRow     The maximum row for the GUI, defined in {@link GUIContainer#getRows()}.
         * @param maxCol     The maximum column for the GUI, defined in {@link GUIContainer#getRows()}
         * @param multiplier The multiplier of the delay
         * @return The delay for the specified slot
         */
        public int getDelayFor(Position position, int row, int col, int maxRow, int maxCol, int multiplier) {
            return operator.applyAsInt(position.getRowOffset(row, maxRow), position.getColOffset(col, maxCol)) * multiplier;
        }
    }
}
