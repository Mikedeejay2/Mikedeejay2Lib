package com.mikedeejay2.mikedeejay2lib.gui.event.util;

import com.mikedeejay2.mikedeejay2lib.gui.event.GUIEvent;
import com.mikedeejay2.mikedeejay2lib.gui.event.GUIEventInfo;
import org.bukkit.event.inventory.ClickType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * An abstract event that only calls {@link GUIAbstractClickEvent#executeClick(GUIEventInfo)} if the click type matches
 * a specified click type.
 *
 * @author Mikedeejay2
 */
public abstract class GUIAbstractClickEvent implements GUIEvent {
    /**
     * A List of the accepted {@link ClickType ClickTypes}
     */
    protected final List<ClickType> acceptedClicks;

    /**
     * Construct a new <code>GUIAbstractClickEvent</code>
     *
     * @param acceptedClicks The list of {@link ClickType ClickTypes} to accept
     */
    public GUIAbstractClickEvent(ClickType... acceptedClicks) {
        this.acceptedClicks = new ArrayList<>(Arrays.asList(acceptedClicks));
    }

    /**
     * Construct a new <code>GUIAbstractClickEvent</code> with default clicks
     */
    public GUIAbstractClickEvent() {
        this(ClickType.LEFT, ClickType.RIGHT, ClickType.MIDDLE, ClickType.SHIFT_LEFT, ClickType.SHIFT_RIGHT);
    }

    /**
     * Calls {@link GUIAbstractClickEvent#executeClick(GUIEventInfo)} if the click type is valid
     *
     * @param info {@link GUIEventInfo} of the event
     */
    @Override
    public void execute(GUIEventInfo info) {
        if(!isValidClick(info.getClick())) return;
        executeClick(info);
    }

    /**
     * Only called if click type is valid
     *
     * @param info {@link GUIEventInfo} of the event
     */
    protected abstract void executeClick(GUIEventInfo info);

    /**
     * Get List of the accepted {@link ClickType}s that will activate the event.
     *
     * @return The list of <code>ClickTypes</code>
     */
    public List<ClickType> getAcceptedClicks() {
        return acceptedClicks;
    }

    /**
     * Add a {@link ClickType} to this event
     *
     * @param click The <code>ClickType</code> to add
     */
    public void addClick(ClickType click) {
        acceptedClicks.add(click);
    }

    /**
     * Add multiple {@link ClickType}s to this event
     *
     * @param clicks The <code>ClickTypes</code> to add
     */
    public void addClicks(ClickType... clicks) {
        acceptedClicks.addAll(Arrays.asList(clicks));
    }

    /**
     * Remove a {@link ClickType} from this event
     *
     * @param click The <code>ClickType</code> to remove
     */
    public void removeClick(ClickType click) {
        acceptedClicks.remove(click);
    }

    /**
     * Remove multiple {@link ClickType}s from this event
     *
     * @param clicks The <code>ClickTypes</code> to remove
     */
    public void removeClicks(ClickType... clicks) {
        acceptedClicks.removeAll(Arrays.asList(clicks));
    }

    /**
     * Set the click types for this event
     *
     * @param clicks The <code>ClickTypes</code> to use
     */
    public void setClicks(ClickType... clicks) {
        acceptedClicks.clear();
        addClicks(clicks);
    }

    /**
     * Get whether a specific {@link ClickType} is an accepted click for this event
     *
     * @param click The <code>ClickType</code> to test
     * @return Whether the <code>ClickType</code> is an accepted click
     */
    public boolean isValidClick(ClickType click) {
        return acceptedClicks.contains(click);
    }

}
