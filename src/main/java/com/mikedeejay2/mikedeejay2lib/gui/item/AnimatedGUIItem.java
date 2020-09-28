package com.mikedeejay2.mikedeejay2lib.gui.item;

import com.mikedeejay2.mikedeejay2lib.gui.GUIContainer;
import com.mikedeejay2.mikedeejay2lib.gui.animation.AnimationFrame;
import com.mikedeejay2.mikedeejay2lib.gui.animation.FrameType;
import com.mikedeejay2.mikedeejay2lib.gui.animation.MovementType;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class AnimatedGUIItem extends GUIItem
{
    protected List<AnimationFrame> frames;
    protected int index;
    protected long wait;
    protected boolean loop;
    protected boolean firstRun;
    protected long delay;

    public AnimatedGUIItem(ItemStack item, boolean loop)
    {
        this(item, loop, 0);
    }

    public AnimatedGUIItem(ItemStack item, boolean loop, long delay)
    {
        super(item);

        this.delay = delay;
        this.loop = loop;
        this.index = 0;
        this.wait = 0;
        this.firstRun = true;
        this.frames = new ArrayList<>();
    }

    public boolean tick(long tickTime, GUIContainer gui)
    {
        wait += tickTime;
        if(firstRun)
        {
            if(wait > delay)
            {
                firstRun = false;
                processFrame(1, gui);
                wait = 0;
            }
            return true;
        }
        if(index >= frames.size())
        {
            if(loop) index = index - frames.size();
            else return false;
        }
        long curWait = frames.get(index).getPeriod();
        if(wait < curWait) return false;
        int framePass = (int)(wait / curWait);
        wait = 0;
        processFrame(framePass, gui);
        return true;
    }

    private void processFrame(int framePass, GUIContainer gui)
    {
        AnimationFrame frame = frames.get(index);
        FrameType type = frame.getType();
        switch(type)
        {
            case ITEM:
            {
                processItem(frame);
                break;
            }
            case MOVEMENT:
            {
                processMovement(frame, gui);
                break;
            }
            case BOTH:
            {
                processItem(frame);
                processMovement(frame, gui);
                break;
            }
        }
        index += framePass;
    }

    private void processItem(AnimationFrame frame)
    {
        setViewItem(frame.getItem());
    }

    private void processMovement(AnimationFrame frame, GUIContainer gui)
    {
        boolean moveRelatively = frame.moveRelative();
        int frameRow = frame.getRow();
        int frameCol = frame.getCol();
        int currentRow = this.getRow();
        int currentCol = this.getCol();
        int newRow = moveRelatively ? currentRow + frameRow : frameRow;
        int newCol = moveRelatively ? currentCol + frameCol : currentCol;
        if(!validCheck(newRow, newCol, gui))
        {
            gui.removeItem(currentRow, currentCol);
            return;
        }
        GUIItem previousItem = gui.getItem(newRow, newCol);

        MovementType movementType = frame.getMovementType();
        switch(movementType)
        {
            case SWAP_ITEM:
            {
                gui.setItem(newRow, newCol, this);
                gui.setItem(currentRow, currentCol, previousItem);
                break;
            }
            case OVERRIDE_ITEM:
            {
                gui.removeItem(currentRow, currentCol);
                gui.setItem(newRow, newCol, this);
                break;
            }
            case PUSH_ITEM_UP:
            {
                int pushRow = newRow-1;
                int pushCol = newCol;
                if(!validCheck(pushRow, pushCol, gui)) break;
                gui.removeItem(currentRow, currentCol);
                gui.setItem(pushRow, pushCol, previousItem);
                break;
            }
            case PUSH_ITEM_DOWN:
            {
                int pushRow = newRow+1;
                int pushCol = newCol;
                if(!validCheck(pushRow, pushCol, gui)) break;
                gui.removeItem(currentRow, currentCol);
                gui.setItem(pushRow, pushCol, previousItem);
                break;
            }
            case PUSH_ITEM_LEFT:
            {
                int pushRow = newRow;
                int pushCol = newCol-1;
                if(!validCheck(pushRow, pushCol, gui)) break;
                gui.removeItem(currentRow, currentCol);
                gui.setItem(pushRow, pushCol, previousItem);
                break;
            }
            case PUSH_ITEM_RIGHT:
            {
                int pushRow = newRow;
                int pushCol = newCol+1;
                if(!validCheck(pushRow, pushCol, gui)) break;
                gui.removeItem(currentRow, currentCol);
                gui.setItem(pushRow, pushCol, previousItem);
                break;
            }
        }
    }

    private boolean validCheck(int row, int col, GUIContainer gui)
    {
        return !(row < 1 || col < 1 || row > gui.getRows() || col > GUIContainer.COLUMN_SIZE);
    }

    public void addFrame(ItemStack item, long period)
    {
        frames.add(new AnimationFrame(item, period));
    }

    public void addFrame(int row, int col, MovementType movementType, boolean relativeMovement, long period)
    {
        frames.add(new AnimationFrame(row, col, movementType, relativeMovement, period));
    }

    public void addFrame(ItemStack item, int row, int col, MovementType movementType, boolean relativeMovement, long period)
    {
        frames.add(new AnimationFrame(item, row, col, movementType, relativeMovement, period));
    }
}
