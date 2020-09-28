package com.mikedeejay2.mikedeejay2lib.gui.animation;

import org.bukkit.inventory.ItemStack;

public class AnimationFrame
{
    protected ItemStack item;
    protected MovementType movementType;
    protected int row;
    protected int col;
    protected long period;
    protected FrameType type;
    protected boolean relativeMovement;

    public AnimationFrame(ItemStack item, long period)
    {
        this.item = item;
        this.period = period == 0 ? 1 : period;
        this.type = FrameType.ITEM;
    }

    public AnimationFrame(int row, int col, MovementType movement, boolean relativeMovement, long period)
    {
        this.row = row;
        this.col = col;
        this.period = period == 0 ? 1 : period;
        this.movementType = movement;
        this.relativeMovement = relativeMovement;
        this.type = FrameType.MOVEMENT;
    }

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

    public ItemStack getItem()
    {
        return item;
    }

    public void setItem(ItemStack item)
    {
        this.item = item;
    }

    public MovementType getMovementType()
    {
        return movementType;
    }

    public void setMovementType(MovementType movementType)
    {
        this.movementType = movementType;
    }

    public int getRow()
    {
        return row;
    }

    public void setRow(int row)
    {
        this.row = row;
    }

    public int getCol()
    {
        return col;
    }

    public void setCol(int col)
    {
        this.col = col;
    }

    public void setRowCol(int row, int col)
    {
        this.row = row;
        this.col = col;
    }

    public long getPeriod()
    {
        return period;
    }

    public void setPeriod(long period)
    {
        this.period = period;
    }

    public FrameType getType()
    {
        return type;
    }

    public void setType(FrameType type)
    {
        this.type = type;
    }

    public boolean moveRelative()
    {
        return relativeMovement;
    }

    public void setRelative(boolean relativeMovement)
    {
        this.relativeMovement = relativeMovement;
    }
}
