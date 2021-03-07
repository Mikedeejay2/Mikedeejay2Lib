package com.mikedeejay2.mikedeejay2lib.gui.modules;

import com.mikedeejay2.mikedeejay2lib.gui.GUIContainer;
import com.mikedeejay2.mikedeejay2lib.gui.GUILayer;
import com.mikedeejay2.mikedeejay2lib.gui.item.GUIItem;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

/**
 * A method that acts as an injection into a <tt>GUIContainer</tt> to add features to the
 * GUI.
 *
 * @author Mikedeejay2
 * @see GUIModule
 */
public interface GUIModule
{
    /**
     * Called on the head of the update method in <tt>GUIContainer</tt>
     *
     * @param player The player that is viewing the GUI
     * @param gui    The GUI
     */
    default void onUpdateHead(Player player, GUIContainer gui) {}

    /**
     * Called on the tail of the update method in <tt>GUIContainer</tt>
     *
     * @param player The player that is viewing the GUI
     * @param gui    The GUI
     */
    default void onUpdateTail(Player player, GUIContainer gui) {}

    /**
     * Called on the head of the open method in <tt>GUIContainer</tt>
     *
     * @param player The player that is viewing the GUI
     * @param gui    The GUI
     */
    default void onOpenHead(Player player, GUIContainer gui) {}

    /**
     * Called on the tail of the open method in <tt>GUIContainer</tt>
     *
     * @param player The player that is viewing the GUI
     * @param gui    The GUI
     */
    default void onOpenTail(Player player, GUIContainer gui) {}

    /**
     * Called in the close method in <tt>GUIContainer</tt>
     *
     * @param player The player that is viewing the GUI
     * @param gui    The GUI
     */
    default void onClose(Player player, GUIContainer gui) {}

    /**
     * Called when the GUI is clicked, called in the head of the click method in <tt>GUIContainer</tt>
     *
     * @param event The event of the click
     * @param gui   The GUI
     */
    default void onClickedHead(InventoryClickEvent event, GUIContainer gui) {}

    /**
     * Called when the GUI is clicked, called in the tail of the click method in <tt>GUIContainer</tt>
     *
     * @param event The event of the click
     * @param gui   The GUI
     */
    default void onClickedTail(InventoryClickEvent event, GUIContainer gui) {}

    /**
     * Called when a <tt>Player</tt> interacts (adds or removes an item) with the GUI, called in the head of the onPlayerInteract method in<tt>GUIContainer</tt>
     *
     * @param event The event of the interaction
     * @param gui   The GUI
     */
    default void onPlayerInteractHead(InventoryClickEvent event, GUIContainer gui) {}

    /**
     * Called when a <tt>Player</tt> interacts (adds or removes an item) with the GUI, called in the tail of the onPlayerInteract method in<tt>GUIContainer</tt>
     *
     * @param event The event of the interaction
     * @param gui   The GUI
     */
    default void onPlayerInteractTail(InventoryClickEvent event, GUIContainer gui) {}

    /**
     * Called when {@link GUILayer#setItem(int, int, GUIItem)} is used
     *
     * @param gui   The GUI
     * @param layer The <tt>GUILayer</tt> that the item was set on
     * @param row   The row that the item was set on
     * @param col   The column that the item was set on
     * @param item  The <tt>GUIItem</tt> that is being set
     */
    default void onItemSet(GUIContainer gui, GUILayer layer, int row, int col, GUIItem item) {}

    /**
     * Called when {@link GUILayer#removeItem(int, int)} is used
     *
     * @param gui   The GUI
     * @param layer The <tt>GUILayer</tt> that the item was removed on
     * @param row   The row that the item was set on
     * @param col   The column that the item was set on
     * @param item  The <tt>GUIItem</tt> that is being removed
     */
    default void onItemRemove(GUIContainer gui, GUILayer layer, int row, int col, GUIItem item) {}
}
