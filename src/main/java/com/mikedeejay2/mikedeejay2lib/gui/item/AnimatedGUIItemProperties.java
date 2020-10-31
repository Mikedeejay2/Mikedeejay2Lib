package com.mikedeejay2.mikedeejay2lib.gui.item;

import com.mikedeejay2.mikedeejay2lib.gui.GUILayer;

public class AnimatedGUIItemProperties
{
    // The current frame index of this item
    protected int index;
    // The current wait time of this item
    protected long wait;
    // Whether or not it is this item's first run or not
    protected boolean firstRun;
    // The location of the item
    protected GUIItemLocation location;

    public AnimatedGUIItemProperties(int startingIndex, int row, int col, GUILayer layer)
    {
        this.index = startingIndex;
        this.wait = 0;
        this.firstRun = true;
        this.location = new GUIItemLocation(row, col, layer);
    }

    /**
     * Get the current index of this property
     *
     * @return The current index
     */
    public int getIndex()
    {
        return index;
    }

    /**
     * Set a new index for this property to use
     *
     * @param index The new index to use
     */
    public void setIndex(int index)
    {
        this.index = index;
    }

    /**
     * Get the current wait time of this property
     *
     * @return The current wait time
     */
    public long getWait()
    {
        return wait;
    }

    /**
     * Set a new wait time for this property to use
     *
     * @param wait The new wait time
     */
    public void setWait(long wait)
    {
        this.wait = wait;
    }

    /**
     * Returns whether this property is on its first run or not
     *
     * @return Whether this property is on first run or not
     */
    public boolean isFirstRun()
    {
        return firstRun;
    }

    /**
     * Set whether this property is on first run or not
     *
     * @param firstRun First run state
     */
    public void setFirstRun(boolean firstRun)
    {
        this.firstRun = firstRun;
    }

    /**
     * Get the <tt>GUIItemLocation</tt> of this property
     *
     * @return The <tt>GUIItemLocation</tt>
     */
    public GUIItemLocation getLocation()
    {
        return location;
    }

    /**
     * Set a new <tt>GUIItemLocation</tt> of this property
     *
     * @param location The new location to use
     */
    public void setLocation(GUIItemLocation location)
    {
        this.location = location;
    }
}
