package com.mikedeejay2.mikedeejay2lib.gui.item;

import com.mikedeejay2.mikedeejay2lib.gui.GUIContainer;
import com.mikedeejay2.mikedeejay2lib.gui.GUILayer;
import com.mikedeejay2.mikedeejay2lib.gui.animation.AnimationFrame;
import com.mikedeejay2.mikedeejay2lib.gui.animation.FrameType;
import com.mikedeejay2.mikedeejay2lib.gui.animation.MovementType;
import com.mikedeejay2.mikedeejay2lib.gui.modules.animation.GUIAnimationModule;
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
    // Whether this item's animation will loop or not
    protected boolean loop;
    // The delay that this item has before its animation begins
    protected long delay;
    // Whether to reset the animation of this item on click
    protected boolean resetOnClick;
    // Starting frame index
    protected int startingIndex;

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
        this.frames = new ArrayList<>();
        this.resetOnClick = resetAnimOnClick;
        this.startingIndex = 0;
    }

    /**
     * Tick this item's animation. Calling this method does not mean that the
     * animation will progress, it just checks to see whether the animation should
     * progress. If the animation should progress, it will calculate the frame and
     * process to land on and animate the current frame.
     *
     * @param tickTime The time between the last tick and this tick. Used for calculating framerate
     * @param properties Reference to the item's properties
     * @return Whether the tick updated the animation or not
     */
    public boolean tick(long tickTime, AnimatedGUIItemProperties properties)
    {
        properties.wait += tickTime;
        if(properties.firstRun)
        {
            properties.index = startingIndex;
            if(properties.wait > delay)
            {
                if(properties.index < frames.size()) properties.index = 0;
                properties.firstRun = false;
                processFrame(1, properties);
                properties.wait = 0;
            }
            return true;
        }
        if(properties.index >= frames.size())
        {
            if(loop) properties.index -= frames.size();
            else return false;
        }
        long curWait = frames.get(properties.index).getPeriod();
        if(properties.wait < curWait) return false;
        int framePass = (int)(properties.wait / curWait);
        properties.wait = 0;
        processFrame(framePass, properties);
        return true;
    }

    /**
     * When a new frame should be called, this method runs.
     * This method does the work for modifying the item to the next frame.
     *
     * @param framePass The amount of frames forward the animation to go to
     * @param properties Reference to the item's properties
     */
    private void processFrame(int framePass, AnimatedGUIItemProperties properties)
    {
        AnimationFrame frame = frames.get(properties.index);
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
                processMovement(frame, properties);
                break;
            }
            case BOTH:
            {
                processItem(frame);
                processMovement(frame, properties);
                break;
            }
        }
        properties.index += framePass;
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
     * @param properties Reference to the item's properties
     */
    private void processMovement(AnimationFrame frame, AnimatedGUIItemProperties properties)
    {
        GUILayer layer = properties.getLocation().getLayer();
        boolean moveRelatively = frame.moveRelative();
        int frameRow = frame.getRow();
        int frameCol = frame.getCol();
        int currentRow = properties.getLocation().getRow();
        int currentCol = properties.getLocation().getCol();
        int newRow = moveRelatively ? currentRow + frameRow : frameRow;
        int newCol = moveRelatively ? currentCol + frameCol : currentCol;
        if(!validCheck(newRow, newCol, properties))
        {
            layer.removeItem(currentRow, currentCol);
            return;
        }
        GUIItem previousItem = layer.getItem(newRow, newCol);

        MovementType movementType = frame.getMovementType();
        GUIItem[][] items = layer.getItemsAsArray();
        GUIItemLocation location = properties.getLocation();
        switch(movementType)
        {
            case SWAP_ITEM:
            {
                items[newRow - 1][newCol - 1] = this;
                items[currentRow - 1][currentCol - 1] = previousItem;
                location.setRow(newRow);
                location.setCol(newCol);
                break;
            }
            case OVERRIDE_ITEM:
            {
                items[currentRow - 1][currentCol - 1] = null;
                items[newRow - 1][newCol - 1] = this;
                location.setRow(newRow);
                location.setCol(newCol);
                break;
            }
            case PUSH_ITEM_UP:
            {
                int pushRow = newRow-1;
                int pushCol = newCol;
                if(!validCheck(pushRow, pushCol, properties)) break;
                items[currentRow - 1][currentCol - 1] = null;
                items[pushRow - 1][pushCol - 1] = previousItem;
                location.setRow(pushRow);
                location.setCol(pushCol);
                break;
            }
            case PUSH_ITEM_DOWN:
            {
                int pushRow = newRow+1;
                int pushCol = newCol;
                if(!validCheck(pushRow, pushCol, properties)) break;
                items[currentRow - 1][currentCol - 1] = null;
                items[pushRow - 1][pushCol - 1] = previousItem;
                break;
            }
            case PUSH_ITEM_LEFT:
            {
                int pushRow = newRow;
                int pushCol = newCol-1;
                if(!validCheck(pushRow, pushCol, properties)) break;
                items[currentRow - 1][currentCol - 1] = null;
                items[pushRow - 1][pushCol - 1] = previousItem;
                break;
            }
            case PUSH_ITEM_RIGHT:
            {
                int pushRow = newRow;
                int pushCol = newCol+1;
                if(!validCheck(pushRow, pushCol, properties)) break;
                items[currentRow - 1][currentCol - 1] = null;
                items[pushRow - 1][pushCol - 1] = previousItem;
                break;
            }
        }
    }

    /**
     * Check to see whether a row and column is a valid position in a GUI
     *
     * @param row The row to check
     * @param col The column to check
     * @param properties Reference to the item's properties
     * @return Whether the position is valid or not
     */
    private boolean validCheck(int row, int col, final AnimatedGUIItemProperties properties)
    {
        return !(row < 1 || col < 1 || row > properties.getLocation().getLayer().getRows() || col > properties.getLocation().getLayer().getCols());
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
     * Helper method to get the item of a frame
     *
     * @param index The frame index to get
     * @return The <tt>ItemStack</tt> of the frame
     */
    public ItemStack getFrameItem(int index)
    {
        return frames.get(index).getItem();
    }

    public AnimationFrame getFrame(int index)
    {
        return frames.get(index);
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
            int slot = event.getSlot();
            GUIAnimationModule module = gui.getModule(GUIAnimationModule.class);
            AnimatedGUIItemProperties properties = module.getProperties(this);
            properties.index = 0;
            properties.wait = 0;
        }
    }

    /**
     * Get the starting index for this <tt>AnimatedGUIItem</tt>
     *
     * @return The starting index
     */
    public int getStartingIndex()
    {
        return startingIndex;
    }

    /**
     * Set the starting index for this <tt>AnimatedGUIItem</tt>
     *
     * @param startingIndex The new starting index
     */
    public void setStartingIndex(int startingIndex)
    {
        this.startingIndex = startingIndex;
    }
}
