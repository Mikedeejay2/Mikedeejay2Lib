package com.mikedeejay2.mikedeejay2lib.gui.modules;

import com.mikedeejay2.mikedeejay2lib.PluginBase;
import com.mikedeejay2.mikedeejay2lib.gui.GUIContainer;
import com.mikedeejay2.mikedeejay2lib.gui.item.GUIItem;
import com.mikedeejay2.mikedeejay2lib.util.PluginInstancer;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;

/**
 * A method that acts as an injection into a <tt>GUIContainer</tt> to add features to the
 * GUI.
 *
 * @see GUIModule
 *
 * @author Mikedeejay2
 */
public abstract class GUIModule extends PluginInstancer<PluginBase>
{
    public GUIModule(PluginBase plugin)
    {
        super(plugin);
    }

    /**
     * Called on the head of the update method in <tt>GUIContainer</tt>
     *
     * @param player The player that is viewing the GUI
     * @param gui The GUI
     */
    public void onUpdateHead(Player player, GUIContainer gui) {}

    /**
     * Called on the tail of the update method in <tt>GUIContainer</tt>
     *
     * @param player The player that is viewing the GUI
     * @param gui The GUI
     */
    public void onUpdateTail(Player player, GUIContainer gui) {}

    /**
     * Called on the head of the open method in <tt>GUIContainer</tt>
     *
     * @param player The player that is viewing the GUI
     * @param gui The GUI
     */
    public void onOpenHead(Player player, GUIContainer gui) {}

    /**
     * Called on the tail of the open method in <tt>GUIContainer</tt>
     *
     * @param player The player that is viewing the GUI
     * @param gui The GUI
     */
    public void onOpenTail(Player player, GUIContainer gui) {}

    /**
     * Called in the close method in <tt>GUIContainer</tt>
     *
     * @param player The player that is viewing the GUI
     * @param gui The GUI
     */
    public void onClose(Player player, GUIContainer gui) {}

    /**
     * Called when the GUI is clicked, called in the head of the click method in <tt>GUIContainer</tt>
     *
     * @param player The player that is viewing the GUI
     * @param row The row that was clicked on
     * @param col The column that was clicked on
     * @param clicked The <tt>GUIItem</tt> that was clicked
     * @param action The <tt>InventoryAction</tt> of the click
     * @param clickType The <tt>ClickType</tt> of the click
     * @param gui The GUI
     */
    public void onClickedHead(Player player, int row, int col, GUIItem clicked, InventoryAction action, ClickType clickType, GUIContainer gui) {}

    /**
     * Called when the GUI is clicked, called in the tail of the click method in <tt>GUIContainer</tt>
     *
     * @param player The player that is viewing the GUI
     * @param row The row that was clicked on
     * @param col The column that was clicked on
     * @param clicked The <tt>GUIItem</tt> that was clicked
     * @param action The <tt>InventoryAction</tt> of the click
     * @param clickType The <tt>ClickType</tt> of the click
     * @param gui The GUI
     */
    public void onClickedTail(Player player, int row, int col, GUIItem clicked, InventoryAction action, ClickType clickType, GUIContainer gui) {}

    /**
     * Called when a <tt>Player</tt> interacts (adds or removes an item) with the GUI, called in the head of the onPlayerInteract method in<tt>GUIContainer</tt>
     *
     * @param player The player interacting with the GUI
     * @param row The row that the player interacted on
     * @param col The column that the player interacted on
     * @param action The <tt>InventoryAction</tt> performed by the player
     * @param type The <tt>ClickType</tt> performed by the player
     * @param rawSlot The raw slot of the inventory that was clicked
     * @param gui The GUI
     */
    public void onPlayerInteractHead(Player player, int row, int col, InventoryAction action, ClickType type, int rawSlot, GUIContainer gui) {}

    /**
     * Called when a <tt>Player</tt> interacts (adds or removes an item) with the GUI, called in the tail of the onPlayerInteract method in<tt>GUIContainer</tt>
     *
     * @param player The player interacting with the GUI
     * @param row The row that the player interacted on
     * @param col The column that the player interacted on
     * @param action The <tt>InventoryAction</tt> performed by the player
     * @param type The <tt>ClickType</tt> performed by the player
     * @param rawSlot The raw slot of the inventory that was clicked
     * @param gui The GUI
     */
    public void onPlayerInteractTail(Player player, int row, int col, InventoryAction action, ClickType type, int rawSlot, GUIContainer gui) {}
}
