package com.mikedeejay2.mikedeejay2lib.gui.event;

import com.mikedeejay2.mikedeejay2lib.gui.item.GUIItem;

/**
 * An event that is called when a {@link GUIItem} is called
 * <p>
 * When a {@link GUIEvent} is added to a {@link GUIItem} through
 * {@link GUIItem#addEvent(GUIEvent)}, the {@link GUIEvent#execute(GUIClickEvent)} method
 * will be called for the event.
 * <p>
 * Multiple <code>GUIEvents</code> can be added to a <code>GUIItem</code> through the use of a {@link GUIEventHandler}
 * which can hold multiple <code>GUIEvents</code>. This is automatically done so you don't have to worry about it.
 *
 * @author Mikedeejay2
 */
@FunctionalInterface
public interface GUIEvent {
    /**
     * Executes when the item that this event has been appended to is clicked on
     *
     * @param info {@link GUIClickEvent} of the event
     */
    void execute(GUIClickEvent info);
}
