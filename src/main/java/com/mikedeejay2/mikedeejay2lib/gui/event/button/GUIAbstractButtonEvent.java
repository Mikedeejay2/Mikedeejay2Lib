package com.mikedeejay2.mikedeejay2lib.gui.event.button;

import com.mikedeejay2.mikedeejay2lib.gui.event.GUIEvent;
import org.bukkit.event.inventory.ClickType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Abstract GUI Button event for button types to extend.
 *
 * @author Mikedeejay2
 */
public abstract class GUIAbstractButtonEvent implements GUIEvent
{
    /**
     * A List of the accepted {@link ClickType}s that will activate the button.
     */
    protected final List<ClickType> acceptedClicks;

    /**
     * Constructor for {@link GUIAbstractButtonEvent}
     *
     * @param acceptedClicks The accepted {@link ClickType}s of the button
     */
    public GUIAbstractButtonEvent(ClickType... acceptedClicks)
    {
        this.acceptedClicks = new ArrayList<>(Arrays.asList(acceptedClicks));
    }

    /**
     * Get List of the accepted {@link ClickType}s that will activate the button.
     *
     * @return The list of <code>ClickTypes</code>
     */
    public List<ClickType> getAcceptedClicks()
    {
        return acceptedClicks;
    }

    /**
     * Add a {@link ClickType} to this button
     *
     * @param click The <code>ClickType</code> to add
     */
    public void addClick(ClickType click)
    {
        acceptedClicks.add(click);
    }

    /**
     * Add multiple {@link ClickType}s to this button
     *
     * @param clicks The <code>ClickTypes</code> to add
     */
    public void addClicks(ClickType... clicks)
    {
        acceptedClicks.addAll(Arrays.asList(clicks));
    }

    /**
     * Remove a {@link ClickType} from this button
     *
     * @param click The <code>ClickType</code> to remove
     */
    public void removeClick(ClickType click)
    {
        acceptedClicks.remove(click);
    }

    /**
     * Remove multiple {@link ClickType}s from this button
     *
     * @param clicks The <code>ClickTypes</code> to remove
     */
    public void removeClicks(ClickType... clicks)
    {
        acceptedClicks.removeAll(Arrays.asList(clicks));
    }

    /**
     * Get whether a specific {@link ClickType} is an accepted click for this button
     *
     * @param click The <code>ClickType</code> to test
     * @return Whether the <code>ClickType</code> is an accepted click
     */
    public boolean isValidClick(ClickType click)
    {
        return acceptedClicks.contains(click);
    }
}
