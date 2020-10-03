package com.mikedeejay2.mikedeejay2lib.gui;

import com.mikedeejay2.mikedeejay2lib.PluginBase;
import com.mikedeejay2.mikedeejay2lib.gui.event.GUIEvent;
import com.mikedeejay2.mikedeejay2lib.gui.event.GUIItemEvent;
import com.mikedeejay2.mikedeejay2lib.gui.item.GUIItem;
import com.mikedeejay2.mikedeejay2lib.gui.modules.GUIModule;
import com.mikedeejay2.mikedeejay2lib.util.PluginInstancer;
import com.mikedeejay2.mikedeejay2lib.util.array.ArrayUtil;
import com.mikedeejay2.mikedeejay2lib.util.chat.Chat;
import com.mikedeejay2.mikedeejay2lib.util.item.ItemCreator;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

/**
 * A flexible, over engineered GUI. The main way of adding onto this GUI
 * is by creating or using a {@link GUIModule} so that this GUIContainer
 * does more than nothing.
 *
 * @author Mikedeejay2
 */
public class GUIContainer extends PluginInstancer<PluginBase>
{
    // The default (Minecraft) amount of inventory columns
    protected static final int INVENTORY_COLS = 9;
    // An empty name so that items that shouldn't have a title have the smallest
    // title possible for Minecraft (Because you can't specify no specify)
    public static final String EMPTY_NAME = "§7";
    // An empty stack, usually a light gray stained glass pane so it's nearly invisible
    protected final ItemStack EMPTY_STACK;
    // The inventory of this GUI
    protected Inventory inventory;
    // The name of this GUI's inventory
    protected String inventoryName;
    // The amount of slots in this GUI
    protected int inventorySlots;
    // The amount of rows in this GUI
    protected int inventoryRows;
    // The 2D array of items that this GUI displays
    protected GUIItem[][] items;
    // The list of GUIModules that this GUI contains
    protected List<GUIModule> modules;
    // The default move state of this GUI
    protected boolean defaultMoveState;

    public GUIContainer(PluginBase plugin, String inventoryName, int inventoryRows)
    {
        super(plugin);
        this.EMPTY_STACK = ItemCreator.createItem(Material.LIGHT_GRAY_STAINED_GLASS_PANE, 1 , EMPTY_NAME);
        this.inventoryName = Chat.chat(inventoryName);
        this.inventorySlots = inventoryRows * INVENTORY_COLS;
        this.inventoryRows = inventoryRows;
        this.items = new GUIItem[inventoryRows][INVENTORY_COLS];
        this.modules = new ArrayList<>();
        this.defaultMoveState = false;

        inventory = Bukkit.createInventory(null, inventorySlots, this.inventoryName);
    }

    /**
     * Method to open this GUI for a player
     *
     * @param player The player that this GUI will open to
     */
    public void open(Player player)
    {
        modules.forEach(module -> module.onOpenHead(player, this));
        update(player);
        player.openInventory(inventory);
        modules.forEach(module -> module.onOpenTail(player, this));
    }

    /**
     * Calls the close for all modules of this GUI, does not close the
     * player's GUI however.
     *
     * @param player The player that was viewing the GUI
     */
    public void close(Player player)
    {
        modules.forEach(module -> module.onClose(player, this));
    }

    /**
     * Updates this GUI and displays it to the player
     *
     * @param player The player viewing this GUI
     */
    public void update(Player player)
    {
        modules.forEach(module -> module.onUpdateHead(player, this));
        for(int row = 0; row < inventoryRows; row++)
        {
            for(int col = 0; col < INVENTORY_COLS; col++)
            {
                int invSlot = getSlotFromRowCol(row, col);
                GUIItem guiItem = items[row][col];
                if(guiItem == null)
                {
                    if(!defaultMoveState) inventory.setItem(invSlot, EMPTY_STACK);
                    continue;
                }
                ItemStack itemStack = guiItem.getItem();
                boolean movable = guiItem.isMovable();

                if(itemStack != null)
                {
                    inventory.setItem(invSlot, itemStack);
                }
                else if(!movable)
                {
                    inventory.setItem(invSlot, EMPTY_STACK);
                }
            }
        }
        modules.forEach(module -> module.onUpdateTail(player, this));
    }

    /**
     * Called when this GUI is clicked on
     *
     * @param player The player that clicked on the GUI
     * @param row The row that was clicked on
     * @param col The column that was clicked on
     * @param clicked The GUIItem that was clicked on
     */
    public void clicked(Player player, int row, int col, GUIItem clicked)
    {
        modules.forEach(module -> module.onClickedHead(player, row, col, clicked, this));
        GUIItemEvent manager = getItemEvents(row, col);
        if(manager == null) return;
        manager.execute(player, row, col, clicked, this);
        modules.forEach(module -> module.onClickedTail(player, row, col, clicked, this));
    }

    /**
     * Set an item to a new item in the GUI
     *
     * @param row Row that should be set
     * @param col Column that should be set
     * @param material The material of the item
     * @param amount The amount of the item
     * @param displayName The display of the new item
     * @param loreString The lores of the item
     */
    public void setItem(int row, int col, Material material, int amount, String displayName, String... loreString)
    {
        setItem(row, col, ItemCreator.createItem(material, amount, displayName, loreString));
    }

    /**
     * Remove an item from the GUI based on a row and a column
     *
     * @param row Row that should be removed
     * @param col Column that should be remove
     */
    public void removeItem(int row, int col)
    {
        setItem(row, col, (GUIItem)null);
    }

    /**
     * Remove an item based off of the instance of that item
     *
     * @param item The item that should be removed
     */
    public void removeItem(GUIItem item)
    {
        int row = item.getRow();
        int col = item.getCol();
        removeItem(row, col);
    }

    /**
     * Set an item using a base64 head string
     *
     * @param row Row that should be set
     * @param col Column that should be set
     * @param headStr The base 64 head string that should be used
     * @param amount The amount of the item
     * @param displayName The display name of the item
     * @param loreString The lore strings of the item
     */
    public void setItem(int row, int col, String headStr, int amount, String displayName, String... loreString)
    {
        setItem(row, col, ItemCreator.createHeadItem(headStr, amount, displayName, loreString));
    }

    /**
     * Set an item from an <tt>ItemStack</tt>
     *
     * @param row Row that should be set
     * @param col Column that should be set
     * @param stack The <tt>ItemStack</tt> to set the slot to
     */
    public void setItem(int row, int col, ItemStack stack)
    {
        setItem(row, col, new GUIItem(stack));
    }

    /**
     * Set an item from a <tt>GUIItem</tt>
     *
     * @param row The row that should be set
     * @param col The column that should be set
     * @param item The GUIItem to use
     */
    public void setItem(int row, int col, GUIItem item)
    {
        if(item != null)
        {
            item.setRow(row);
            item.setCol(col);
        }
        items[--row][--col] = item;
    }

    /**
     * Set the move state of an item
     *
     * @param row The row to set
     * @param col The column to set
     * @param movable Whether the item is movable or not
     */
    public void setMoveState(int row, int col, boolean movable)
    {
        GUIItem item = items[--row][--col];
        item.setMovable(movable);
    }

    /**
     * Get the <tt>GUIItemEvent</tt> from a slot
     *
     * @param row The row to get
     * @param col The column to get
     * @return The events of the slot
     */
    public GUIItemEvent getItemEvents(int row, int col)
    {
        GUIItem item = items[--row][--col];
        return item == null ? null : item.getEvents();
    }

    /**
     * Set the <tt>GUIItemEvent</tt> for a slot
     *
     * @param row The row to set
     * @param col The column to set
     * @param events The events to set the slot to
     */
    public void setItemEvents(int row, int col, GUIItemEvent events)
    {
        GUIItem item = items[--row][--col];
        item.setEvents(events);
    }

    /**
     * Add a <tt>GUIEvent</tt> to a slot
     *
     * @param row The row to set
     * @param col The column to set
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
     * @param row The row to remove
     * @param col The column to remove
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
     * @param row The row to remove
     * @param col The column to remove
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
     * @param row The row to search in
     * @param col The column to search in
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
     * @param row The row to search in
     * @param col The column to search in
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
    public void removeItemEvents(int row, int col)
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
     * @param row Row 1
     * @param col Column 1
     * @param newRow Row 2
     * @param newCol Column 2
     */
    public void swapItems(int row, int col, int newRow, int newCol)
    {
        --newRow; --newCol; --row; --col;
        GUIItem itemToMove = items[row][col];
        items[row][col] = items[newRow][newCol];
        items[newRow][newCol] = itemToMove;
    }

    /**
     * Set the inventory name for this GUI
     *
     * @param newName The new name of the inventory
     */
    public void setInventoryName(String newName)
    {
        this.inventoryName = Chat.chat(newName);
        this.inventory = Bukkit.createInventory(null, inventorySlots, inventoryName);
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
     * Get the name of this GUI
     *
     * @return The name of this GUI
     */
    public String getName()
    {
        return inventoryName;
    }

    /**
     * Get the amount of rows of this GUI
     *
     * @return The amount of rows of this GUI
     */
    public int getRows()
    {
        return inventoryRows;
    }

    /**
     * Get the amount of columns of this GUI
     *
     * @return The amount of columns of this GUI
     */
    public int getCols()
    {
        return INVENTORY_COLS;
    }

    /**
     * Add a <tt>GUIModule</tt> to this GUI
     *
     * @param module Module that will be added to this GUI
     */
    public void addModule(GUIModule module)
    {
        modules.add(module);
    }

    /**
     * Remove a <tt>GUIModule</tt> based off of an instance
     *
     * @param module The module to remove from this GUI
     */
    public void removeModule(GUIModule module)
    {
        modules.remove(module);
    }

    /**
     * Remove a <tt>GUIModule</tt> based off of the class type of the module
     *
     * @param moduleClass The module class to remove from this GUI
     */
    public void removeModule(Class<? extends GUIModule> moduleClass)
    {
        String className = moduleClass.getName();
        for(GUIModule module : modules)
        {
            Class<? extends GUIModule> curClass = module.getClass();
            String curClassName = curClass.getName();
            if(!className.equals(curClassName)) continue;
            modules.remove(module);
            break;
        }
    }

    /**
     * Returns whether this GUI contains a certain <tt>GUIModule</tt> via instance of that module
     *
     * @param module The module to search for
     * @return Whether this GUI contains the module
     */
    public boolean containsModule(GUIModule module)
    {
        return modules.contains(module);
    }

    /**
     * Returns whether this GUI contains a certain <tt>GUIModule</tt> via instance of that module
     *
     * @param moduleClass The module class to search for
     * @return Whether this GUI contains the module
     */
    public boolean containsModule(Class<? extends GUIModule> moduleClass)
    {
        String className = moduleClass.getName();
        for(GUIModule module : modules)
        {
            Class<? extends GUIModule> curClass = module.getClass();
            String curClassName = curClass.getName();
            if(!className.equals(curClassName)) continue;
            return true;
        }
        return false;
    }

    /**
     * Get a <tt>GUIModule</tt> based off of the module's class
     *
     * @param moduleClass The class of the module to get
     * @param <T> The module type that will be returned
     * @return The module, null if not found
     */
    public <T extends GUIModule> T getModule(Class<T> moduleClass)
    {
        String className = moduleClass.getName();
        for(GUIModule module : modules)
        {
            Class<? extends GUIModule> curClass = module.getClass();
            String curClassName = curClass.getName();
            if(!className.equals(curClassName)) continue;
            return (T) module;
        }
        return null;
    }

    /**
     * Get the inventory of this GUI.
     * Not recommended because modification of this inventory will not
     * reflect to the GUI itself, but still included just in case.
     *
     * @return The inventory of this GUI
     */
    public Inventory getInventory()
    {
        return inventory;
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
        return items[--row][--col];
    }

    /**
     * Get the default move state of this GUI
     *
     * @return The default move state
     */
    public boolean getDefaultMoveState()
    {
        return defaultMoveState;
    }

    /**
     * Set the default move state of this GUI
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
     * Convert a row and a column to a slot
     *
     * @param row The row to use
     * @param col The column to use
     * @return The slot based off of the row and the column
     */
    public int getSlotFromRowCol(int row, int col)
    {
        return (row * INVENTORY_COLS) + col;
    }

    /**
     * Get the row based off of a slot
     *
     * @param slot The slot to use
     * @return The row based off of the slot
     */
    public int getRowFromSlot(int slot)
    {
        return (slot / INVENTORY_COLS) + 1;
    }

    /**
     * Get the column based off of a slot
     *
     * @param slot The slot to use
     * @return The column based off of the slot
     */
    public int getColFromSlot(int slot)
    {
        return (slot % INVENTORY_COLS) + 1;
    }
}
