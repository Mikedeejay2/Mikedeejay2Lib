package com.mikedeejay2.mikedeejay2lib.gui.modules;

import com.mikedeejay2.mikedeejay2lib.gui.GUIContainer;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

/**
 * A method that acts as an injection into a <tt>GUIContainer</tt> to add features to the
 * GUI.
 *
 * @author Mikedeejay2
 * @see GUIModule
 */
public abstract class GUIModule
{
    /**
     * Called on the head of the update method in <tt>GUIContainer</tt>
     *
     * @param player The player that is viewing the GUI
     * @param gui    The GUI
     */
    public void onUpdateHead(Player player, GUIContainer gui) {}

    /**
     * Called on the tail of the update method in <tt>GUIContainer</tt>
     *
     * @param player The player that is viewing the GUI
     * @param gui    The GUI
     */
    public void onUpdateTail(Player player, GUIContainer gui) {}

    /**
     * Called on the head of the open method in <tt>GUIContainer</tt>
     *
     * @param player The player that is viewing the GUI
     * @param gui    The GUI
     */
    public void onOpenHead(Player player, GUIContainer gui) {}

    /**
     * Called on the tail of the open method in <tt>GUIContainer</tt>
     *
     * @param player The player that is viewing the GUI
     * @param gui    The GUI
     */
    public void onOpenTail(Player player, GUIContainer gui) {}

    /**
     * Called in the close method in <tt>GUIContainer</tt>
     *
     * @param player The player that is viewing the GUI
     * @param gui    The GUI
     */
    public void onClose(Player player, GUIContainer gui) {}

    /**
     * Called when the GUI is clicked, called in the head of the click method in <tt>GUIContainer</tt>
     *
     * @param event The event of the click
     * @param gui   The GUI
     */
    public void onClickedHead(InventoryClickEvent event, GUIContainer gui) {}

    /**
     * Called when the GUI is clicked, called in the tail of the click method in <tt>GUIContainer</tt>
     *
     * @param event The event of the click
     * @param gui   The GUI
     */
    public void onClickedTail(InventoryClickEvent event, GUIContainer gui) {}

    /**
     * Called when a <tt>Player</tt> interacts (adds or removes an item) with the GUI, called in the head of the onPlayerInteract method in<tt>GUIContainer</tt>
     *
     * @param event The event of the interaction
     * @param gui   The GUI
     */
    public void onPlayerInteractHead(InventoryClickEvent event, GUIContainer gui) {}

    /**
     * Called when a <tt>Player</tt> interacts (adds or removes an item) with the GUI, called in the tail of the onPlayerInteract method in<tt>GUIContainer</tt>
     *
     * @param event The event of the interaction
     * @param gui   The GUI
     */
    public void onPlayerInteractTail(InventoryClickEvent event, GUIContainer gui) {}
}
