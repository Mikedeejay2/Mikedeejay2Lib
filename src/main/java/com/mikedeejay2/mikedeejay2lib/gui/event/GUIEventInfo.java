package com.mikedeejay2.mikedeejay2lib.gui.event;

import com.mikedeejay2.mikedeejay2lib.gui.GUIContainer;
import com.mikedeejay2.mikedeejay2lib.gui.GUILayer;
import com.mikedeejay2.mikedeejay2lib.gui.item.GUIItem;
import com.mikedeejay2.mikedeejay2lib.item.ItemBuilder;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Information about a {@link GUIEvent}, used in {@link GUIEvent#execute(GUIEventInfo)}
 *
 * @author Mikedeejay2
 */
public final class GUIEventInfo {
    /**
     * The original {@link InventoryClickEvent} called
     */
    private final InventoryClickEvent event;

    /**
     * The {@link Player} viewing the GUI
     */
    private final Player player;

    /**
     * The {@link GUIContainer} instance
     */
    private final GUIContainer gui;

    /**
     * The {@link GUILayer} that was clicked (The top most clicked layer)
     */
    private final GUILayer layer;

    /**
     * The {@link GUIItem} that was clicked
     */
    private final GUIItem item;

    /**
     * The row of the click
     */
    private final int row;

    /**
     * The column of the click
     */
    private final int column;

    /**
     * Construct a new <code>GUIEventInfo</code>
     *
     * @param event The original {@link InventoryClickEvent} called
     * @param gui   The {@link GUIContainer} instance
     */
    public GUIEventInfo(InventoryClickEvent event, GUIContainer gui) {
        this.event = event;
        this.player = (Player) event.getWhoClicked();
        this.gui = gui;
        this.row = gui.getRow(event.getSlot());
        this.column = gui.getColumn(event.getSlot());
        this.layer = gui.getTopLayer(row, column);
        this.item = layer.getItem(row, column);
    }

    /**
     * Get the {@link Player} who clicked the GUI
     *
     * @return The <code>Player</code>
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Get the {@link Player} who clicked the GUI
     *
     * @return The <code>Player</code>
     */
    public Player getWhoClicked() {
        return player;
    }

    /**
     * Get the {@link GUIContainer} instance
     *
     * @return The GUI instance
     */
    public GUIContainer getGUI() {
        return gui;
    }

    /**
     * Get The {@link GUILayer} that was clicked (The top most clicked layer)
     *
     * @return The GUI layer
     */
    public GUILayer getLayer() {
        return layer;
    }

    /**
     * Get the {@link GUIItem} that was clicked
     *
     * @return The GUI item
     */
    public GUIItem getGUIItem() {
        return item;
    }

    /**
     * Get the current item in the slot that was clicked
     *
     * @return The current item
     */
    public ItemStack getCurrentItem() {
        return item.get(player);
    }

    /**
     * Get the item in the cursor
     *
     * @return The cursor item
     */
    public ItemStack getCursor() {
        return event.getCursor();
    }

    /**
     * Set the item on the cursor
     *
     * @param item The <code>ItemStack</code> to set
     */
    @Deprecated
    public void setCursor(ItemStack item) {
        event.setCursor(item);
    }

    /**
     * Set the item in the slot that was clicked
     *
     * @param item The <code>ItemStack</code> to use
     */
    public void setCurrentItem(ItemStack item) {
        this.item.set(item);
    }

    /**
     * Set the item in the slot that was clicked
     *
     * @param builder The <code>ItemBuilder</code> to use
     */
    public void setCurrentItem(ItemBuilder builder) {
        this.item.set(builder);
    }

    /**
     * Set the {@link GUIItem} in the slot that was clicked
     *
     * @param item The <code>GUIItem</code> to use
     */
    public void setGUIItem(GUIItem item) {
        this.layer.setItem(row, column, item);
    }

    /**
     * Get the inventory slot that was clicked
     *
     * @return The inventory slot
     */
    public int getSlot() {
        return event.getSlot();
    }

    /**
     * Get the row of the click
     *
     * @return The row
     */
    public int getRow() {
        return row;
    }

    /**
     * Get the column of the click
     *
     * @return The column
     */
    public int getColumn() {
        return column;
    }

    /**
     * Whether the click type is a right click
     *
     * @return If right click or not
     */
    public boolean isRightClick() {
        return event.isRightClick();
    }

    /**
     * Whether the click type is a left click
     *
     * @return If left click or not
     */
    public boolean isLeftClick() {
        return event.isLeftClick();
    }

    /**
     * Whether the click type is a shift click
     *
     * @return If shift click or not
     */
    public boolean isShiftClick() {
        return event.isShiftClick();
    }

    /**
     * Whether the click type is a keyboard click
     *
     * @return If keyboard click or not
     */
    public boolean isKeyboardClick() {
        return event.getClick().isKeyboardClick();
    }

    /**
     * Get the hotbar button if used. -1 if not used
     *
     * @return The hotbar button
     */
    public int getHotbarButton() {
        return event.getHotbarButton();
    }

    /**
     * Get the {@link InventoryAction} of the click
     *
     * @return The <code>InventoryAction</code>
     */
    public InventoryAction getAction() {
        return event.getAction();
    }

    /**
     * Get the {@link ClickType} of the click
     *
     * @return The <code>ClickType</code>
     */
    public ClickType getClick() {
        return event.getClick();
    }

    /**
     * Get the original {@link InventoryClickEvent} called
     *
     * @return The underlying click event
     */
    public InventoryClickEvent getEvent() {
        return event;
    }
}
