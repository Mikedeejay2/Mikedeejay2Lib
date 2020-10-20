package com.mikedeejay2.mikedeejay2lib.gui.event;

import com.mikedeejay2.mikedeejay2lib.PluginBase;
import com.mikedeejay2.mikedeejay2lib.gui.GUIContainer;
import com.mikedeejay2.mikedeejay2lib.gui.item.GUIItem;
import com.mikedeejay2.mikedeejay2lib.util.PluginInstancer;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;

/**
 * An event that is called when a <tt>GUIItem</tt> is called. <p>
 *
 * When a <tt>GUIEvent</tt> is added to a <tt>GUIItem</tt> through
 * <tt>addEvent(GUIEvent event)</tt>, the <tt>execute()</tt> method
 * will be called for the event. <p>
 *
 * Multiple <tt>GUIEvents</tt> can be added to a <tt>GUIItem</tt>
 * through the use of a <tt>GUIEventHandler</tt> which can hold multiple
 * <tt>GUIEvents</tt>. This is automatically done so you don't have to
 * worry about it.
 *
 * @author Mikedeejay2
 */
public abstract class GUIEvent extends PluginInstancer<PluginBase>
{
    public GUIEvent(PluginBase plugin)
    {
        super(plugin);
    }

    /**
     * Executes when the item that this event has been appended to is clicked on
     *
     * @param player The player that clicked the item
     * @param row The row that the item was clicked on
     * @param col The column that the item was clicked on
     * @param clicked The <tt>GUIItem</tt> that was clicked
     * @param gui The GUI that the event took place in
     * @param action The <tt>InventoryAction</tt> of the click
     * @param clickType The <tt>ClickType</tt> of the click
     */
    public abstract void execute(Player player, int row, int col, GUIItem clicked, GUIContainer gui, InventoryAction action, ClickType clickType);
}
