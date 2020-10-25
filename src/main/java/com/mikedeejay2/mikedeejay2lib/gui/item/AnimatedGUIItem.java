package com.mikedeejay2.mikedeejay2lib.gui.item;

import com.mikedeejay2.mikedeejay2lib.gui.GUIContainer;
import com.mikedeejay2.mikedeejay2lib.gui.animation.AnimationFrame;
import com.mikedeejay2.mikedeejay2lib.gui.animation.FrameType;
import com.mikedeejay2.mikedeejay2lib.gui.animation.MovementType;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

/**
 * A <tt>GUIItem</tt> with the added ability of having animation capabilities. <p>
 *
 * <b>IMPORTANT: To use the animation capabilities of this class, <tt>GUIAnimationModule</tt>
 * must be a module that has been appended to the <tt>GUIContainer</tt>. This is because the
 * <tt>GUIAnimationModule</tt> adds animation functionality to the GUI while this object is
 * just some added information to a regular <tt>GUIItem</tt> that gives the information needed
 * to animate this item.</b>
 *
 * @author Mikedeejay2
 */
public class AnimatedGUIItem extends GUIItem
{
    // The list of AnimationFrames of this item
    protected List<AnimationFrame> frames;
    // The current frame index of this item
    protected int index;
    // The current wait time of this item
    protected long wait;
    // Whether this item's animation will loop or not
    protected boolean loop;
    // Whether or not it is this item's first run or not
    protected boolean firstRun;
    // The delay that this item has before its animation begins
    protected long delay;
    // Whether to reset the animation of this item on click
    protected boolean resetOnClick;

    public AnimatedGUIItem(ItemStack item, boolean loop)
    {
        this(item, loop, 0);
    }

    public AnimatedGUIItem(ItemStack item, boolean loop, long delay)
    {
        this(item, loop, delay, false);
    }

    public AnimatedGUIItem(ItemStack item, boolean loop, long delay, boolean resetAnimOnClick)
    {
        super(item);

        this.delay = delay;
        this.loop = loop;
        this.index = 0;
        this.wait = 0;
        this.firstRun = true;
        this.frames = new ArrayList<>();
        this.resetOnClick = resetAnimOnClick;
    }

    /**
     * Tick this item's animation. Calling this method does not mean that the
     * animation will progress, it just checks to see whether the animation should
     * progress. If the animation should progress, it will calculate the frame and
     * process to land on and animate the current frame.
     *
     * @param tickTime The time between the last tick and this tick. Used for calculating framerate
     * @return Whether the tick updated the animation or not
     */
    public boolean tick(long tickTime)
    {
        wait += tickTime;
        if(firstRun)
        {
            if(wait > delay)
            {
                firstRun = false;
                processFrame(1);
                wait = 0;
            }
            return true;
        }
        if(index >= frames.size())
        {
            if(loop) index -= frames.size();
            else return false;
        }
        long curWait = frames.get(index).getPeriod();
        if(wait < curWait) return false;
        int framePass = (int)(wait / curWait);
        wait = 0;
        processFrame(framePass);
        return true;
    }

    /**
     * When a new frame should be called, this method runs.
     * This method does the work for modifying the item to the next frame.
     *
     * @param framePass The amount of frames forward the animation to go to
     */
    private void processFrame(int framePass)
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
                processMovement(frame);
                break;
            }
            case BOTH:
            {
                processItem(frame);
                processMovement(frame);
                break;
            }
        }
        index += framePass;
    }

    /**
     * Process an item frame
     *
     * @param frame The AnimationFrame that will be processed
     */
    private void processItem(AnimationFrame frame)
    {
        setViewItem(frame.getItem());
    }

    /**
     * Process the movement for a movement frame
     *
     * @param frame The AnimationFrame that will be processed
     */
    private void processMovement(AnimationFrame frame)
    {
        boolean moveRelatively = frame.moveRelative();
        int frameRow = frame.getRow();
        int frameCol = frame.getCol();
        int currentRow = this.getRow();
        int currentCol = this.getCol();
        int newRow = moveRelatively ? currentRow + frameRow : frameRow;
        int newCol = moveRelatively ? currentCol + frameCol : currentCol;
        if(!validCheck(newRow, newCol))
        {
            layer.removeItem(currentRow, currentCol);
            return;
        }
        GUIItem previousItem = layer.getItem(newRow, newCol);

        MovementType movementType = frame.getMovementType();
        switch(movementType)
        {
            case SWAP_ITEM:
            {
                layer.setItem(newRow, newCol, this);
                layer.setItem(currentRow, currentCol, previousItem);
                break;
            }
            case OVERRIDE_ITEM:
            {
                layer.removeItem(currentRow, currentCol);
                layer.setItem(newRow, newCol, this);
                break;
            }
            case PUSH_ITEM_UP:
            {
                int pushRow = newRow-1;
                int pushCol = newCol;
                if(!validCheck(pushRow, pushCol)) break;
                layer.removeItem(currentRow, currentCol);
                layer.setItem(pushRow, pushCol, previousItem);
                break;
            }
            case PUSH_ITEM_DOWN:
            {
                int pushRow = newRow+1;
                int pushCol = newCol;
                if(!validCheck(pushRow, pushCol)) break;
                layer.removeItem(currentRow, currentCol);
                layer.setItem(pushRow, pushCol, previousItem);
                break;
            }
            case PUSH_ITEM_LEFT:
            {
                int pushRow = newRow;
                int pushCol = newCol-1;
                if(!validCheck(pushRow, pushCol)) break;
                layer.removeItem(currentRow, currentCol);
                layer.setItem(pushRow, pushCol, previousItem);
                break;
            }
            case PUSH_ITEM_RIGHT:
            {
                int pushRow = newRow;
                int pushCol = newCol+1;
                if(!validCheck(pushRow, pushCol)) break;
                layer.removeItem(currentRow, currentCol);
                layer.setItem(pushRow, pushCol, previousItem);
                break;
            }
        }
    }

    /**
     * Check to see whether a row and column is a valid position in a GUI
     *
     * @param row The row to check
     * @param col The column to check
     * @return Whether the position is valid or not
     */
    private boolean validCheck(int row, int col)
    {
        return !(row < 1 || col < 1 || row > layer.getRows() || col > layer.getCols());
    }

    /**
     * Add an item frame to this item
     *
     * @param item The item to add to the frame
     * @param period The time to wait between this frame and the frame after it
     */
    public void addFrame(ItemStack item, long period)
    {
        frames.add(new AnimationFrame(item, period));
    }

    /**
     * Add a movement frame to this item
     *
     * @param row The row to move the item to
     * @param col The column to move the item to
     * @param movementType The type of movement that will be performed when the item is moved
     * @param relativeMovement Whether or not the movement should move relatively (locally)
     * @param period The time to wait between this frame and the frame after it
     */
    public void addFrame(int row, int col, MovementType movementType, boolean relativeMovement, long period)
    {
        frames.add(new AnimationFrame(row, col, movementType, relativeMovement, period));
    }

    /**
     * Add a movement + item frame to this item
     *
     * @param item The item to add to the frame
     * @param row The row to move the item to
     * @param col The column to move the item to
     * @param movementType The type of movement that will be performed when the item is moved
     * @param relativeMovement Whether or not the movement should move relatively (locally)
     * @param period The time to wait between this frame and the frame after it
     */
    public void addFrame(ItemStack item, int row, int col, MovementType movementType, boolean relativeMovement, long period)
    {
        frames.add(new AnimationFrame(item, row, col, movementType, relativeMovement, period));
    }

    /**
     * The current frame index of the animation
     *
     * @return The frame index
     */
    public int getIndex()
    {
        return index;
    }

    /**
     * Set the current frame index of the animation
     *
     * @param index The new index
     */
    public void setIndex(int index)
    {
        this.index = index;
    }

    /**
     * Whether the animation for this item should loop or not
     *
     * @return Whether to loop or not
     */
    public boolean shouldLoop()
    {
        return loop;
    }

    /**
     * Set whether this animation should loop or not
     *
     * @param loop New loop state
     */
    public void setLoop(boolean loop)
    {
        this.loop = loop;
    }

    /**
     * Get the delay of this animation
     *
     * @return The delay
     */
    public long getDelay()
    {
        return delay;
    }

    /**
     * Set the delay of this animation
     *
     * @param delay The new delay
     */
    public void setDelay(long delay)
    {
        this.delay = delay;
    }

    /**
     * Whether the animation should reset on click or not
     *
     * @return Whether the animation should reset on click or not
     */
    public boolean shouldResetOnClick()
    {
        return resetOnClick;
    }

    /**
     * Set the reset on click of this item
     *
     * @param resetOnClick The new reset on click state
     */
    public void setResetOnClick(boolean resetOnClick)
    {
        this.resetOnClick = resetOnClick;
    }

    @Override
    public void onClick(InventoryClickEvent event, GUIContainer gui)
    {
        super.onClick(event, gui);
        if(resetOnClick)
        {
            index = 0;
            wait = 0;
        }
    }
}
