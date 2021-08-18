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
 * A <code>GUIItem</code> with the added ability of having animation capabilities. <p>
 *
 * <strong>IMPORTANT: To use the animation capabilities of this class, {@link GUIAnimationModule}
 * must be a module that has been appended to the {@link GUIContainer}. This is because the
 * {@link GUIAnimationModule} adds animation functionality to the GUI while this object is
 * just some added information to a regular {@link GUIItem} that gives the information needed
 * to animate this item.</strong>
 *
 * @see GUIAnimationModule
 *
 * @author Mikedeejay2
 */
public class AnimatedGUIItem extends GUIItem
{
    /**
     * The list of AnimationFrames of this item
     */
    protected List<AnimationFrame> frames;
    /**
     * Whether this item's animation will loop or not
     */
    protected boolean loop;
    /**
     * The delay that this item has before its animation begins
     */
    protected long delay;
    /**
     * Whether to reset the animation of this item on click
     */
    protected boolean resetOnClick;
    /**
     * Starting frame index
     */
    protected int startingIndex;

    /**
     * Construct a new <code>AnimatedGUIItem</code>
     *
     * @param item The reference base item
     * @param loop Whether this item's animation will loop or not
     */
    public AnimatedGUIItem(ItemStack item, boolean loop)
    {
        this(item, loop, 0);
    }

    /**
     * Construct a new <code>AnimatedGUIItem</code>
     *
     * @param item  The reference base item
     * @param loop  Whether this item's animation will loop or not
     * @param delay The delay that this item has before its animation begins
     */
    public AnimatedGUIItem(ItemStack item, boolean loop, long delay)
    {
        this(item, loop, delay, false);
    }

    /**
     * Construct a new <code>AnimatedGUIItem</code>
     *
     * @param item             The reference base item
     * @param loop             Whether this item's animation will loop or not
     * @param delay            The delay that this item has before its animation begins
     * @param resetAnimOnClick Whether to reset the animation of this item on click
     */
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
     * @param tickTime   The time between the last tick and this tick. Used for calculating framerate
     * @param properties Reference to the item's properties
     * @return Whether the tick updated the animation or not
     */
    public boolean tick(long tickTime, AnimatedGUIItemProperties properties)
    {
        if(frames.size() == 0) return false;
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
        long curWait = frames.get(properties.index - 1 < 0 ? frames.size() - 1 : properties.index - 1).getPeriod();
        if(properties.wait < curWait) return false;
        int framePass = (int) (properties.wait / curWait);
        properties.wait = 0;
        processFrame(framePass, properties);
        return true;
    }

    /**
     * When a new frame should be called, this method runs.
     * This method does the work for modifying the item to the next frame.
     *
     * @param framePass  The amount of frames forward the animation to go to
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
     * @param frame      The AnimationFrame that will be processed
     * @param properties Reference to the item's properties
     */
    private void processMovement(AnimationFrame frame, AnimatedGUIItemProperties properties)
    {
        GUILayer layer          = properties.getLayer();
        boolean  moveRelatively = frame.moveRelative();
        int      frameRow       = frame.getRow();
        int      frameCol       = frame.getCol();
        int      currentRow     = properties.getRow();
        int      currentCol     = properties.getCol();
        int      newRow         = moveRelatively ? currentRow + frameRow : frameRow;
        int      newCol         = moveRelatively ? currentCol + frameCol : currentCol;
        if(!validCheck(newRow, newCol, properties))
        {
            layer.removeItem(currentRow, currentCol);
            return;
        }
        GUIItem previousItem = layer.getItem(newRow, newCol);

        MovementType    movementType = frame.getMovementType();
        GUIItem[][]     items        = layer.getItemsAsArray();
        switch(movementType)
        {
            case SWAP_ITEM:
            {
                items[newRow - 1][newCol - 1] = this;
                items[currentRow - 1][currentCol - 1] = previousItem;
                properties.setRow(newRow);
                properties.setCol(newCol);
                break;
            }
            case OVERRIDE_ITEM:
            {
                items[currentRow - 1][currentCol - 1] = null;
                items[newRow - 1][newCol - 1] = this;
                properties.setRow(newRow);
                properties.setCol(newCol);
                break;
            }
            case PUSH_ITEM_UP:
            {
                int pushRow = newRow-1;
                int pushCol = newCol;
                if(!validCheck(pushRow, pushCol, properties)) break;
                items[currentRow - 1][currentCol - 1] = null;
                items[pushRow - 1][pushCol - 1] = previousItem;
                properties.setRow(pushRow);
                properties.setCol(pushCol);
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
     * @param row        The row to check
     * @param col        The column to check
     * @param properties Reference to the item's properties
     * @return Whether the position is valid or not
     */
    private boolean validCheck(int row, int col, final AnimatedGUIItemProperties properties)
    {
        return !(row < 1 || col < 1 || row > properties.getLayer().getRows() || col > properties.getLayer().getCols());
    }

    /**
     * Add an item frame to this item
     *
     * @param item   The item to add to the frame
     * @param period The time to wait between this frame and the frame after it
     * @return Reference to this <code>AnimatedGUIItem</code>
     */
    public AnimatedGUIItem addFrame(ItemStack item, long period)
    {
        frames.add(new AnimationFrame(item, period));
        return this;
    }

    /**
     * Add a movement frame to this item
     *
     * @param row              The row to move the item to
     * @param col              The column to move the item to
     * @param movementType     The type of movement that will be performed when the item is moved
     * @param relativeMovement Whether or not the movement should move relatively (locally)
     * @param period           The time to wait between this frame and the frame after it
     * @return Reference to this <code>AnimatedGUIItem</code>
     */
    public AnimatedGUIItem addFrame(int row, int col, MovementType movementType, boolean relativeMovement, long period)
    {
        frames.add(new AnimationFrame(row, col, movementType, relativeMovement, period));
        return this;
    }

    /**
     * Add a movement + item frame to this item
     *
     * @param item             The item to add to the frame
     * @param row              The row to move the item to
     * @param col              The column to move the item to
     * @param movementType     The type of movement that will be performed when the item is moved
     * @param relativeMovement Whether or not the movement should move relatively (locally)
     * @param period           The time to wait between this frame and the frame after it
     * @return Reference to this <code>AnimatedGUIItem</code>
     */
    public AnimatedGUIItem addFrame(ItemStack item, int row, int col, MovementType movementType, boolean relativeMovement, long period)
    {
        frames.add(new AnimationFrame(item, row, col, movementType, relativeMovement, period));
        return this;
    }

    /**
     * Helper method to get the item of a frame
     *
     * @param index The frame index to get
     * @return The <code>ItemStack</code> of the frame
     */
    public ItemStack getFrameItem(int index)
    {
        return frames.get(index).getItem();
    }

    /**
     * Get the {@link AnimationFrame} of the specified index
     *
     * @param index The index to get the frame from
     * @return The {@link AnimationFrame} of the specified index
     */
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
     * @return Reference to this <code>AnimatedGUIItem</code>
     */
    public AnimatedGUIItem setLoop(boolean loop)
    {
        this.loop = loop;
        return this;
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
     * @return Reference to this <code>AnimatedGUIItem</code>
     */
    public AnimatedGUIItem setDelay(long delay)
    {
        this.delay = delay;
        return this;
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
     * @return Reference to this <code>AnimatedGUIItem</code>
     */
    public AnimatedGUIItem setResetOnClick(boolean resetOnClick)
    {
        this.resetOnClick = resetOnClick;
        return this;
    }

    /**
     * Called when this <code>AnimatedGUIItem</code> is clicked
     * <p>
     * This method also checks whether the animation requires a reset if {@link AnimatedGUIItem#resetOnClick}
     *
     * @param event The event of the click
     * @param gui   The GUI that was clicked on
     */
    @Override
    public void onClick(InventoryClickEvent event, GUIContainer gui)
    {
        super.onClick(event, gui);
        if(resetOnClick && gui.containsModule(GUIAnimationModule.class))
        {
            GUIAnimationModule module = gui.getModule(GUIAnimationModule.class);
            AnimatedGUIItemProperties properties = module.getProperties(this);
            properties.index = 0;
            properties.wait = 0;
        }
    }

    /**
     * Get the starting index for this <code>AnimatedGUIItem</code>
     *
     * @return The starting index
     */
    public int getStartingIndex()
    {
        return startingIndex;
    }

    /**
     * Set the starting index for this <code>AnimatedGUIItem</code>
     *
     * @param startingIndex The new starting index
     * @return Reference to this <code>AnimatedGUIItem</code>
     */
    public AnimatedGUIItem setStartingIndex(int startingIndex)
    {
        this.startingIndex = startingIndex;
        return this;
    }

    /**
     * Clone this <code>AnimatedGUIItem</code>
     *
     * @return The new cloned <code>AnimatedGUIItem</code>
     */
    public AnimatedGUIItem clone()
    {
        AnimatedGUIItem clone = (AnimatedGUIItem) super.clone();
        clone.frames = frames;
        clone.loop = loop;
        clone.delay = delay;
        clone.resetOnClick = resetOnClick;
        clone.startingIndex = startingIndex;
        return clone;
    }
}
