package com.mikedeejay2.mikedeejay2lib.gui;

import com.mikedeejay2.mikedeejay2lib.gui.event.GUIEvent;
import com.mikedeejay2.mikedeejay2lib.gui.event.GUIEventHandler;
import com.mikedeejay2.mikedeejay2lib.gui.item.AnimatedGUIItem;
import com.mikedeejay2.mikedeejay2lib.gui.item.AnimatedGUIItemProperties;
import com.mikedeejay2.mikedeejay2lib.gui.item.GUIItem;
import com.mikedeejay2.mikedeejay2lib.gui.modules.animation.GUIAnimationModule;
import com.mikedeejay2.mikedeejay2lib.util.array.ArrayUtil;
import com.mikedeejay2.mikedeejay2lib.util.item.ItemComparison;
import com.mikedeejay2.mikedeejay2lib.util.item.ItemCreator;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.List;

/**
 * A layer of a GUI that stores items and other miscellaneous data.
 *
 * @author Mikedeejay2
 */
public class GUILayer
{
    // The 2D array of items that this layer stores
    protected GUIItem[][] items;
    // The amount of rows that this layer has
    protected int inventoryRows;
    // The amount of columns that this layer has
    protected int inventoryCols;
    // This layer's name
    protected String layerName;
    // The default move state of this layer
    protected boolean defaultMoveState;
    // Whether this layer is visible or not
    protected boolean visible;
    // Whether this layer is an overlay layer or not
    protected boolean overlay;
    // A reference to the parent GUI
    protected GUIContainer gui;

    public GUILayer(GUIContainer gui, String layerName, boolean overlay, boolean defaultMoveState)
    {
        this.gui = gui;
        this.overlay = overlay;
        if(!overlay)
        {
            this.inventoryRows = gui.getRows();
            this.inventoryCols = gui.getCols();
        }
        else
        {
            this.inventoryRows = GUIContainer.MAX_INVENTORY_ROWS;
            this.inventoryCols = GUIContainer.MAX_INVENTORY_COLS;
        }
        this.layerName = layerName;
        this.items = new GUIItem[inventoryRows][inventoryCols];
        this.defaultMoveState = defaultMoveState;
        this.visible = true;
    }

    /**
     * Set an item to a new item in the GUI
     *
     * @param row         Row that should be set
     * @param col         Column that should be set
     * @param material    The material of the item
     * @param amount      The amount of the item
     * @param displayName The display of the new item
     * @param loreString  The lores of the item
     */
    public void setItem(int row, int col, Material material, int amount, String displayName, String... loreString)
    {
        setItem(row, col, ItemCreator.createItem(material, amount, displayName, loreString));
    }

    /**
     * Get an item from a slot
     *
     * @param row The row to get
     * @param col The column to get
     * @return The <tt>GUIItem</tt> that is contained in that slot
     */
    public GUIItem getItem(int row, int col)
    {
        if(overlay)
        {
            row -= gui.getRowOffset();
            col -= gui.getColOffset();
        }
        return items[--row][--col];
    }

    /**
     * Remove an item from the GUI based on a row and a column
     *
     * @param row Row that should be removed
     * @param col Column that should be remove
     */
    public void removeItem(int row, int col)
    {
        GUIItem item = getItem(row, col);
        setItem(row, col, (GUIItem) null);
        if(item == null) return;
        if(item instanceof AnimatedGUIItem)
        {
            AnimatedGUIItem animItem = (AnimatedGUIItem) item;
            if(gui.containsModule(GUIAnimationModule.class))
            {
                gui.getModule(GUIAnimationModule.class).removeItem(animItem);
            }
        }
    }

    /**
     * Remove an item based off of the instance of that item
     *
     * @param item The item that should be removed
     */
    public void removeItem(GUIItem item)
    {
        for(int row = 1; row <= inventoryRows; ++row)
        {
            for(int col = 1; col <= inventoryCols; ++col)
            {
                if(getItem(row, col) != item) continue;
                removeItem(row, col);
                return;
            }
        }
    }

    /**
     * Set an item using a base64 head string
     *
     * @param row         Row that should be set
     * @param col         Column that should be set
     * @param headStr     The base 64 head string that should be used
     * @param amount      The amount of the item
     * @param displayName The display name of the item
     * @param loreString  The lore strings of the item
     */
    public void setItem(int row, int col, String headStr, int amount, String displayName, String... loreString)
    {
        setItem(row, col, ItemCreator.createHeadItem(headStr, amount, displayName, loreString));
    }

    /**
     * Set an item from an <tt>ItemStack</tt>
     *
     * @param row   Row that should be set
     * @param col   Column that should be set
     * @param stack The <tt>ItemStack</tt> to set the slot to
     */
    public void setItem(int row, int col, ItemStack stack)
    {
        GUIItem newItem = new GUIItem(stack);
        newItem.setMovable(defaultMoveState);
        setItem(row, col, newItem);
    }

    /**
     * Set an item from a <tt>GUIItem</tt>
     *
     * @param row  The row that should be set
     * @param col  The column that should be set
     * @param item The GUIItem to use
     */
    public void setItem(int row, int col, GUIItem item)
    {
        if(items[row - 1][col - 1] == item) return;
        items[row - 1][col - 1] = item;
        if(item == null) return;
        if(item instanceof AnimatedGUIItem)
        {
            AnimatedGUIItem animItem = (AnimatedGUIItem) item;
            if(gui.containsModule(GUIAnimationModule.class))
            {
                gui.getModule(GUIAnimationModule.class).addItem(animItem, new AnimatedGUIItemProperties(0, row, col, this));
            }
        }
    }

    /**
     * Set the move state of an item
     *
     * @param row     The row to set
     * @param col     The column to set
     * @param movable Whether the item is movable or not
     */
    public void setMoveState(int row, int col, boolean movable)
    {
        GUIItem item = items[--row][--col];
        item.setMovable(movable);
    }

    /**
     * Get the <tt>GUIEventHandler</tt> from a slot
     *
     * @param row The row to get
     * @param col The column to get
     * @return The events of the slot
     */
    public GUIEventHandler getEventHandler(int row, int col)
    {
        GUIItem item = items[--row][--col];
        return item == null ? null : item.getEvents();
    }

    /**
     * Set the <tt>GUIEventHandler</tt> for a slot
     *
     * @param row    The row to set
     * @param col    The column to set
     * @param events The events to set the slot to
     */
    public void setEventHandler(int row, int col, GUIEventHandler events)
    {
        GUIItem item = items[--row][--col];
        item.setEvents(events);
    }

    /**
     * Add a <tt>GUIEvent</tt> to a slot
     *
     * @param row   The row to set
     * @param col   The column to set
     * @param event The GUIEvent to add
     */
    public void addEvent(int row, int col, GUIEvent event)
    {
        GUIItem item = items[--row][--col];
        item.addEvent(event);
    }

    /**
     * Remove a <tt>GUIEvent</tt> from a slot based off of instance
     *
     * @param row   The row to remove
     * @param col   The column to remove
     * @param event The GUIEvent to remove
     */
    public void removeEvent(int row, int col, GUIEvent event)
    {
        GUIItem item = items[--row][--col];
        if(item != null) item.removeEvent(event);
    }

    /**
     * Remove a <tt>GUIEvent</tt> from a slot based off of the event's class
     *
     * @param row        The row to remove
     * @param col        The column to remove
     * @param eventClass The class of the GUIEvent that should be removed
     */
    public void removeEvent(int row, int col, Class<? extends GUIEvent> eventClass)
    {
        GUIItem item = items[--row][--col];
        if(item != null) item.removeEvent(eventClass);
    }

    /**
     * Returns whether a row and a column contains a <tt>GUIEvent</tt> based off of instance
     *
     * @param row   The row to search in
     * @param col   The column to search in
     * @param event The event that should be searched for
     * @return Whether the slot contains the event
     */
    public boolean doesSlotContainEvent(int row, int col, GUIEvent event)
    {
        GUIItem item = items[--row][--col];
        return item != null && item.containsEvent(event);
    }

    /**
     * Returns whether a row and a column contains a <tt>GUIEvent</tt> based off of the class of that event
     *
     * @param row        The row to search in
     * @param col        The column to search in
     * @param eventClass The class of the event that should be searched for
     * @return Whether the slot contains the event
     */
    public boolean doesSlotContainEvent(int row, int col, Class<? extends GUIEvent> eventClass)
    {
        GUIItem item = items[--row][--col];
        return item != null && item.containsEvent(eventClass);
    }

    /**
     * Remove all <tt>GUIEvents</tt> from a slot
     *
     * @param row Row to remove events from
     * @param col Column to remove events from
     */
    public void removeEventHandler(int row, int col)
    {
        GUIItem item = items[--row][--col];
        if(item != null) item.resetEvents();
    }

    /**
     * Returns whether a slot can be moved or not
     *
     * @param row The row to get
     * @param col The column to get
     * @return Whether the slot is movable
     */
    public boolean canSlotBeMoved(int row, int col)
    {
        GUIItem item = items[--row][--col];
        return item == null ? defaultMoveState : item.isMovable();
    }

    /**
     * Swap the items between the first and second slot
     *
     * @param row    Row 1
     * @param col    Column 1
     * @param newRow Row 2
     * @param newCol Column 2
     */
    public void swapItems(int row, int col, int newRow, int newCol)
    {
        --newRow;
        --newCol;
        --row;
        --col;
        GUIItem itemToMove = items[row][col];
        items[row][col] = items[newRow][newCol];
        items[newRow][newCol] = itemToMove;
    }

    /**
     * Returns whether an item exists in a slot
     *
     * @param row The row to check
     * @param col The column to check
     * @return Whether the item exists or not
     */
    public boolean itemExists(int row, int col)
    {
        return items[--row][--col] != null;
    }

    /**
     * Get the amount of rows of this layer
     *
     * @return The amount of rows of this GUI
     */
    public int getRows()
    {
        return inventoryRows;
    }

    /**
     * Get the amount of columns of this layer
     *
     * @return The amount of columns of this GUI
     */
    public int getCols()
    {
        return inventoryCols;
    }

    /**
     * Get the default move state of this layer
     *
     * @return The default move state
     */
    public boolean getDefaultMoveState()
    {
        return defaultMoveState;
    }

    /**
     * Set the default move state of this layer
     *
     * @param defaultMoveState The new default move state
     */
    public void setDefaultMoveState(boolean defaultMoveState)
    {
        this.defaultMoveState = defaultMoveState;
    }

    /**
     * Get all <tt>GUIItems</tt> in list form
     *
     * @return A list of all GUI items
     */
    public List<GUIItem> getItemsAsList()
    {
        return ArrayUtil.toList(items);
    }

    /**
     * Get all <tt>GUIItems</tt> as a 2D array
     *
     * @return The 2D array of GUI Items
     */
    public GUIItem[][] getItemsAsArray()
    {
        return items;
    }

    /**
     * Get the name of this layer
     *
     * @return The name of this layer
     */
    public String getName()
    {
        return layerName;
    }

    /**
     * Returns whether this layer should be visible in the GUI
     *
     * @return The visibility of this layer
     */
    public boolean isVisible()
    {
        return visible;
    }

    /**
     * Set the visibility of this layer. This DOES NOT updat the GUI, therefore
     * the GUI needs to be manually updated after this.
     *
     * @param visibility Visibility to set this layer to
     */
    public void setVisibility(boolean visibility)
    {
        this.visible = visibility;
    }

    /**
     * Returns whether this layer is an overlay or not
     *
     * @return Whether this layer is an overlay or not
     */
    public boolean isOverlay()
    {
        return overlay;
    }

    /**
     * Convert a row and a column to a slot
     *
     * @param row The row to use
     * @param col The column to use
     * @return The slot based off of the row and the column
     */
    public int getSlotFromRowCol(int row, int col)
    {
        return (row * inventoryCols) + col;
    }

    /**
     * Get the row based off of a slot
     *
     * @param slot The slot to use
     * @return The row based off of the slot
     */
    public int getRowFromSlot(int slot)
    {
        if(overlay)
        {
            return (slot / GUIContainer.MAX_INVENTORY_COLS) + 1;
        }
        return (slot / GUIContainer.MAX_INVENTORY_COLS) + 1 + gui.getRowOffset();
    }

    /**
     * Get the column based off of a slot
     *
     * @param slot The slot to use
     * @return The column based off of the slot
     */
    public int getColFromSlot(int slot)
    {
        if(overlay)
        {
            return (slot % GUIContainer.MAX_INVENTORY_COLS) + 1;
        }
        return (slot % GUIContainer.MAX_INVENTORY_COLS) + 1 + gui.getColOffset();
    }

    public GUIContainer getGUI()
    {
        return gui;
    }

    /**
     * See whether this <tt>GUILayer</tt> contains an item matching an <tt>ItemStack</tt>
     *
     * @param item The item to search for
     * @return Whether the item was found in the GUI or not
     */
    public boolean containsItem(ItemStack item)
    {
        for(GUIItem[] arr : items)
        {
            for(GUIItem guiItem : arr)
            {
                if(guiItem == null) continue;
                ItemStack curItem = guiItem.getItemBase();
                if(curItem == null) continue;
                if(!ItemComparison.equalsEachOther(item, curItem)) continue;
                return true;
            }
        }
        return false;
    }

    /**
     * See whether this <tt>GUILayer</tt> contains a material matching another material
     *
     * @param material The material to search for
     * @return Whether the material was found in the GUI or not
     */
    public boolean containsMaterial(Material material)
    {
        for(GUIItem[] arr : items)
        {
            for(GUIItem guiItem : arr)
            {
                if(guiItem == null) continue;
                ItemStack curItem = guiItem.getItemBase();
                if(curItem == null) continue;
                if(curItem.getType() != material) continue;
                return true;
            }
        }
        return false;
    }

}
