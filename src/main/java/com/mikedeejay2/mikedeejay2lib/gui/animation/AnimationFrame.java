package com.mikedeejay2.mikedeejay2lib.gui.animation;

import org.bukkit.inventory.ItemStack;

/**
 * Holds a frame of animation in a GUI, whether that be item movement, item changes, or both.
 *
 * @author Mikedeejay2
 */
public class AnimationFrame
{
    // The item of the animation frame, null if not an item frame.
    protected ItemStack item;
    // The type of movement that this frame processes, null if not movement frame.
    protected MovementType movementType;
    // The row of this frame, null if not movement frame.
    protected int row;
    // The column of this frame, null if not movement frame.
    protected int col;
    // The period of this frame, aka the time between this frame and the next one
    protected long period;
    // The FrameType, either ITEM, MOVEMENT, or BOTH;
    protected FrameType type;
    // Whether or not relative (local) movement is use, null if not a movement frame.
    protected boolean relativeMovement;

    /**
     * Constructor for constructing an <tt>ITEM</tt> frame
     *
     * @param item The new item that this frame will apply
     * @param period The period between this frame and the next frame
     */
    public AnimationFrame(ItemStack item, long period)
    {
        this.item = item;
        this.period = period == 0 ? 1 : period;
        this.type = FrameType.ITEM;
    }

    /**
     * Constructor for constructing a <tt>MOVEMENT</tt> frame
     *
     * @param row The row that the item will move to
     * @param col The column that the item will move to
     * @param movement The <tt>MovementType</tt> of this frame
     * @param relativeMovement Whether or not to use relative (local) movement
     * @param period The period between this frame and the next frame
     */
    public AnimationFrame(int row, int col, MovementType movement, boolean relativeMovement, long period)
    {
        this.row = row;
        this.col = col;
        this.period = period == 0 ? 1 : period;
        this.movementType = movement;
        this.relativeMovement = relativeMovement;
        this.type = FrameType.MOVEMENT;
    }

    /**
     * Constructor for constructing a <tt>BOTH</tt> (Item and movement) frame
     *
     * @param item The new item that this frame will apply
     * @param row The row that the item will move to
     * @param col The column that the item will move to
     * @param movement The <tt>MovementType</tt> of this frame
     * @param relativeMovement Whether or not to use relative (local) movement
     * @param period The period between this frame and the next frame
     */
    public AnimationFrame(ItemStack item, int row, int col, MovementType movement, boolean relativeMovement, long period)
    {
        this.row = row;
        this.col = col;
        this.period = period == 0 ? 1 : period;
        this.movementType = movement;
        this.item = item;
        this.relativeMovement = relativeMovement;
        this.type = FrameType.BOTH;
    }

    /**
     * Get the item of this frame, null if the <tt>FrameType</tt> is not <tt>ITEM</tt> or <tt>BOTH</tt>
     *
     * @return The item of this frame
     */
    public ItemStack getItem()
    {
        return item;
    }

    /**
     * Set the item of this frame.
     * <p>
     * Note: The item will only be used if the <tt>FrameType</tt> for this frame is <tt>ITEM</tt> or <tt>BOTH</tt>
     *
     * @param item The item to set this frame's item to
     */
    public void setItem(ItemStack item)
    {
        this.item = item;
    }

    /**
     * Get the <tt>MovementType</tt> of this frame, null if the <tt>FrameType</tt> is not <tt>MOVEMENT</tt> or <tt>BOTH</tt>
     *
     * @return The movement type of this frame
     */
    public MovementType getMovementType()
    {
        return movementType;
    }

    /**
     * Set the <tt>MovementType</tt> of this frame.
     * <p>
     * Note: The <tt>MovementType</tt> will only be used if the <tt>FrameType</tt> for this frame is <tt>MOVEMENT</tt> or <tt>BOTH</tt>
     *
     * @param movementType The movement type to set this frame to
     */
    public void setMovementType(MovementType movementType)
    {
        this.movementType = movementType;
    }

    /**
     * Get the row of this frame, null if the <tt>FrameType</tt> is not <tt>MOVEMENT</tt> or <tt>BOTH</tt>
     *
     * @return The row of this frame
     */
    public int getRow()
    {
        return row;
    }


    /**
     * Set the row of this frame.
     * <p>
     * Note: This will only be used if the <tt>FrameType</tt> for this frame is <tt>MOVEMENT</tt> or <tt>BOTH</tt>
     *
     * @param row The row to set this frame to
     */
    public void setRow(int row)
    {
        this.row = row;
    }

    /**
     * Get the column of this frame, null if the <tt>FrameType</tt> is not <tt>MOVEMENT</tt> or <tt>BOTH</tt>
     *
     * @return The column of this frame
     */
    public int getCol()
    {
        return col;
    }

    /**
     * Set the column of this frame.
     * <p>
     * Note: This will only be used if the <tt>FrameType</tt> for this frame is <tt>MOVEMENT</tt> or <tt>BOTH</tt>
     *
     * @param col The column to set this frame to
     */
    public void setCol(int col)
    {
        this.col = col;
    }

    /**
     * Set the row and column of this frame.
     * <p>
     * Note: This will only be used if the <tt>FrameType</tt> for this frame is <tt>MOVEMENT</tt> or <tt>BOTH</tt>
     *
     * @param row The row to set this frame to
     * @param col The column to set this frame to
     */
    public void setRowCol(int row, int col)
    {
        this.row = row;
        this.col = col;
    }

    /**
     * Get the period of this frame (The wait time between this frame and the next frame in ticks)
     *
     * @return The period
     */
    public long getPeriod()
    {
        return period;
    }

    /**
     * Set the period of this frame (The wait time between this frame and the next frame in ticks)
     *
     * @param period The period that this frame will be set to
     */
    public void setPeriod(long period)
    {
        this.period = period;
    }

    /**
     * Get the <tt>FrameType</tt> of this frame
     *
     * @return This frame's type
     */
    public FrameType getType()
    {
        return type;
    }

    /**
     * Set the <tt>FrameType</tt> of this frame.
     * <p>
     * Note: When using this method, it is important that the type of frame that you are setting
     * has all values set in this frame. If data is missing in this frame that the <tt>FrameType</tt>
     * requires, a <tt>NullPointerException</tt> will be thrown.
     *
     * @param type The type to set this frame to.
     */
    public void setType(FrameType type)
    {
        this.type = type;
    }

    /**
     * Whether or not this frame moves relatively (locally)
     *
     * @return Whether this frame moves relatively
     */
    public boolean moveRelative()
    {
        return relativeMovement;
    }

    /**
     * Set whether this frame uses relative (local) movement or not
     *
     * @param relativeMovement The relative movement to set this frame to
     */
    public void setRelative(boolean relativeMovement)
    {
        this.relativeMovement = relativeMovement;
    }
}
