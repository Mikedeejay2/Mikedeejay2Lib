package com.mikedeejay2.mikedeejay2lib.gui.item;

import com.mikedeejay2.mikedeejay2lib.gui.GUILayer;

/**
 * Holds properties relating to an {@link AnimatedGUIItem}, such as its frame index, wait times, position, and layer.
 *
 * @author Mikedeejay2
 */
public class AnimatedGUIItemProperties
{
    /**
     * The current frame index of this item
     */
    protected int index;

    /**
     * The current wait time of this item
     */
    protected long wait;

    /**
     * Whether it is this item's first run or not
     */
    protected boolean firstRun;

    /**
     * The row that this item is located on
     */
    protected int row;

    /**
     * The column that this item is located on
     */
    protected int col;

    /**
     * The name of the layer that the item was added on
     */
    protected GUILayer layer;

    /**
     * Construct a new <code>AnimatedGUIItemProperties</code>
     *
     * @param startingIndex The index that the animation should start on
     * @param row The row that this item is located on
     * @param col The column that this item is located on
     * @param layer The name of the layer that the item was added on
     */
    public AnimatedGUIItemProperties(int startingIndex, int row, int col, GUILayer layer) {
        this.index = startingIndex;
        this.wait = 0;
        this.firstRun = true;
        this.row = row;
        this.col = col;
        this.layer = layer;
    }

    /**
     * Get the current index of this property
     *
     * @return The current index
     */
    public int getIndex() {
        return index;
    }

    /**
     * Set a new index for this property to use
     *
     * @param index The new index to use
     */
    public void setIndex(int index) {
        this.index = index;
    }

    /**
     * Get the current wait time of this property
     *
     * @return The current wait time
     */
    public long getWait() {
        return wait;
    }

    /**
     * Set a new wait time for this property to use
     *
     * @param wait The new wait time
     */
    public void setWait(long wait) {
        this.wait = wait;
    }

    /**
     * Returns whether this property is on its first run or not
     *
     * @return Whether this property is on first run or not
     */
    public boolean isFirstRun() {
        return firstRun;
    }

    /**
     * Set whether this property is on first run or not
     *
     * @param firstRun First run state
     */
    public void setFirstRun(boolean firstRun) {
        this.firstRun = firstRun;
    }

    /**
     * Get the <code>GUILayer</code> of this item
     *
     * @return The layer of this item
     */
    public GUILayer getLayer() {
        return layer;
    }

    /**
     * Set the <code>GUILayer</code> of this item
     *
     * @param layer The new layer of this item
     */
    public void setLayer(GUILayer layer) {
        this.layer = layer;
    }

    /**
     * Set the row that this item is on
     *
     * @param row The row that this item is on
     */
    public void setRow(int row) {
        this.row = row;
    }

    /**
     * Set the column that this item is on
     *
     * @param col the column that this item is on
     */
    public void setCol(int col) {
        this.col = col;
    }

    /**
     * Gets the current row in a GUI that this item is on
     *
     * @return The row
     */
    public int getRow() {
        return row;
    }

    /**
     * Gets the current row in a GUI that this item is on
     *
     * @return The column
     */
    public int getCol() {
        return col;
    }
}
