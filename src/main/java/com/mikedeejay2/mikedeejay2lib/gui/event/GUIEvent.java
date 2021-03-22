package com.mikedeejay2.mikedeejay2lib.gui.event;

import com.mikedeejay2.mikedeejay2lib.gui.GUIContainer;
import org.bukkit.event.inventory.InventoryClickEvent;

/**
 * An event that is called when a <tt>GUIItem</tt> is called
 * <p>
 * When a <tt>GUIEvent</tt> is added to a <tt>GUIItem</tt> through
 * {@link com.mikedeejay2.mikedeejay2lib.gui.item.GUIItem#addEvent(GUIEvent)}, the <tt>execute()</tt> method
 * will be called for the event.
 * <p>
 * Multiple <tt>GUIEvents</tt> can be added to a <tt>GUIItem</tt>
 * through the use of a <tt>GUIEventHandler</tt> which can hold multiple
 * <tt>GUIEvents</tt>. This is automatically done so you don't have to
 * worry about it.
 *
 * @author Mikedeejay2
 */
@FunctionalInterface
public interface GUIEvent
{
    /**
     * Executes when the item that this event has been appended to is clicked on
     *
     * @param event The event of the click
     * @param gui   The GUI that the event took place in
     */
    void execute(InventoryClickEvent event, GUIContainer gui);
}
