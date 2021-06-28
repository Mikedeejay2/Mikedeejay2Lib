package com.mikedeejay2.mikedeejay2lib.gui.event;

import com.mikedeejay2.mikedeejay2lib.gui.GUIContainer;
import org.bukkit.event.inventory.InventoryClickEvent;

/**
 * An event that is called when a <code>GUIItem</code> is called
 * <p>
 * When a <code>GUIEvent</code> is added to a <code>GUIItem</code> through
 * {@link com.mikedeejay2.mikedeejay2lib.gui.item.GUIItem#addEvent(GUIEvent)}, the <code>execute()</code> method
 * will be called for the event.
 * <p>
 * Multiple <code>GUIEvents</code> can be added to a <code>GUIItem</code>
 * through the use of a <code>GUIEventHandler</code> which can hold multiple
 * <code>GUIEvents</code>. This is automatically done so you don't have to
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
