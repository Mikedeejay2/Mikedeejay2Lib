package com.mikedeejay2.mikedeejay2lib.gui.item;

import com.mikedeejay2.mikedeejay2lib.gui.GUILayer;

/**
 * Stores the location of a <code>GUIItem</code> inside of a <code>GUIContainer</code>.
 *
 * @author Mikedeejay2
 */
public class GUIItemLocation
{
    // The row that this item is located on
    protected int row;
    // The column that this item is located on
    protected int col;
    // The name of the layer that the item was added on
    protected GUILayer layer;

    public GUIItemLocation(int row, int col, GUILayer layer)
    {
        this.row = row;
        this.col = col;
        this.layer = layer;
    }

    /**
     * Get the <code>GUILayer</code> of this item
     *
     * @return The layer of this item
     */
    public GUILayer getLayer()
    {
        return layer;
    }

    /**
     * Set the <code>GUILayer</code> of this item
     *
     * @param layer The new layer of this item
     */
    public void setLayer(GUILayer layer)
    {
        this.layer = layer;
    }

    /**
     * Set the row that this item is on
     *
     * @param row The row that this item is on
     */
    public void setRow(int row)
    {
        this.row = row;
    }

    /**
     * Set the column that this item is on
     *
     * @param col the column that this item is on
     */
    public void setCol(int col)
    {
        this.col = col;
    }

    /**
     * Gets the current row in a GUI that this item is on
     *
     * @return The row
     */
    public int getRow()
    {
        return row;
    }

    /**
     * Gets the current row in a GUI that this item is on
     *
     * @return The column
     */
    public int getCol()
    {
        return col;
    }
}
