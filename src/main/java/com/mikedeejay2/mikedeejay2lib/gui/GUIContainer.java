package com.mikedeejay2.mikedeejay2lib.gui;

import com.mikedeejay2.mikedeejay2lib.PluginBase;
import com.mikedeejay2.mikedeejay2lib.gui.event.GUIEvent;
import com.mikedeejay2.mikedeejay2lib.gui.event.GUIEventHandler;
import com.mikedeejay2.mikedeejay2lib.gui.interact.GUIInteractHandler;
import com.mikedeejay2.mikedeejay2lib.gui.interact.normal.GUIInteractHandlerDefault;
import com.mikedeejay2.mikedeejay2lib.gui.item.GUIItem;
import com.mikedeejay2.mikedeejay2lib.gui.manager.PlayerGUI;
import com.mikedeejay2.mikedeejay2lib.gui.modules.GUIModule;
import com.mikedeejay2.mikedeejay2lib.util.chat.Chat;
import com.mikedeejay2.mikedeejay2lib.util.item.ItemCreator;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
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
public class GUIContainer
{
    protected final PluginBase plugin;
    // The default (Minecraft) amount of inventory rows
    public static final int MAX_INVENTORY_ROWS = 6;
    // The default (Minecraft) amount of inventory columns
    public static final int MAX_INVENTORY_COLS = 9;
    // An empty name so that items that shouldn't have a title have the smallest
    // title possible for Minecraft (Because you can't specify no specify)
    public static final String EMPTY_NAME = "§7";
    // An empty stack, usually a light gray stained glass pane so it's nearly invisible
    protected ItemStack backgroundItem;
    // The inventory of this GUI
    protected Inventory inventory;
    // The name of this GUI's inventory
    protected String inventoryName;
    // The amount of slots in this GUI
    protected int inventorySlots;
    // The amount of rows in this GUI
    protected int inventoryRows;
    // The amount of columns in this GUI
    protected int inventoryCols;
    // The layers of this GUI
    protected List<GUILayer> layers;
    // The list of GUIModules that this GUI contains
    protected List<GUIModule> modules;
    // The default move state of this GUI
    protected boolean defaultMoveState;
    // The offset of the row
    protected int rowOffset;
    // The offset of the column
    protected int colOffset;
    // The item interaction handler
    protected GUIInteractHandler interactionHandler;
    // Boolean for whether non-filled items should be filled in the GUI
    protected boolean fillEmpty;

    /**
     * Create a GUI of a regular size.
     *
     * @param plugin        The plugin that this GUI is created by
     * @param inventoryName The name of this GUI
     * @param inventoryRows The amount of inventory rows of this GUI. Max amount: 6
     */
    public GUIContainer(PluginBase plugin, String inventoryName, int inventoryRows)
    {
        this.plugin = plugin;
        this.backgroundItem = ItemCreator.createItem(Material.LIGHT_GRAY_STAINED_GLASS_PANE, 1 , EMPTY_NAME);
        this.inventoryName = Chat.chat(inventoryName);
        if(inventoryRows > MAX_INVENTORY_ROWS) inventoryRows = MAX_INVENTORY_ROWS;
        this.inventorySlots = inventoryRows * MAX_INVENTORY_COLS;
        this.inventoryRows = inventoryRows;
        this.inventoryCols = MAX_INVENTORY_COLS;
        this.layers = new ArrayList<>();
        this.modules = new ArrayList<>();
        this.defaultMoveState = false;
        this.fillEmpty = false;
        rowOffset = 0;
        colOffset = 0;
        layers.add(new GUILayer(this, "base", false, defaultMoveState));
        this.interactionHandler = new GUIInteractHandlerDefault();

        inventory = Bukkit.createInventory(null, inventorySlots, this.inventoryName);
    }

    /**
     * Create a GUI of a large size. Note that to travel the GUI you must use a <tt>GUIScrollerModule</tt>
     *
     * @param plugin        The plugin that this GUI is created by
     * @param inventoryName The name of this GUI
     * @param inventoryRows The amount of inventory rows of this GUI
     * @param inventoryCols The amount of inventory columns of this GUI
     */
    public GUIContainer(PluginBase plugin, String inventoryName, int inventoryRows, int inventoryCols)
    {
        this.plugin = plugin;
        this.backgroundItem = ItemCreator.createItem(Material.LIGHT_GRAY_STAINED_GLASS_PANE, 1 , EMPTY_NAME);
        this.inventoryName = Chat.chat(inventoryName);
        this.inventorySlots = Math.min(inventoryRows * MAX_INVENTORY_COLS, MAX_INVENTORY_ROWS * MAX_INVENTORY_COLS);
        this.inventoryRows = inventoryRows;
        this.inventoryCols = inventoryCols;
        this.layers = new ArrayList<>();
        this.modules = new ArrayList<>();
        this.defaultMoveState = false;
        this.fillEmpty = false;
        rowOffset = 0;
        colOffset = 0;
        layers.add(new GUILayer(this, "base", false, defaultMoveState));
        this.interactionHandler = new GUIInteractHandlerDefault();

        inventory = Bukkit.createInventory(null, inventorySlots, this.inventoryName);
    }

    /**
     * Method to open this GUI for a player.
     * <p>
     * <b>NOTE: THIS DOES NOT OPEN THE GUI THE PROPER WAY!</b>
     * <p>
     * To open the GUI, use {@link PlayerGUI#setGUI(GUIContainer)}
     * like <tt>plugin.guiManager().getPlayer(player).setGUI(gui)</tt>
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
     * <p>
     * <b>NOTE: THIS DOES NOT CLOSE THE GUI THE PROPER WAY!</b>
     * <p>
     * To close the GUI, use {@link PlayerGUI#onClose()}
     * like <tt>plugin.guiManager().getPlayer(player).onClose()</tt>
     *
     * @param player The player that was viewing the GUI
     */
    public void onClose(Player player)
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
        int newInvRows = Math.min(inventoryRows, MAX_INVENTORY_ROWS);

        if(fillEmpty)
        {
            int fillSlot = -1;
            GUILayer bottomLayer = layers.get(0);
            for(int row = 1; row <= newInvRows; ++row)
            {
                for(int col = 1; col <= MAX_INVENTORY_COLS; ++col)
                {
                    GUIItem item = bottomLayer.getItem(row, col);
                    ++fillSlot;
                    if((item != null && item.isMovable()) || (item == null && bottomLayer.getDefaultMoveState()))
                    {
                        if(item == null) inventory.setItem(fillSlot, null);
                        continue;
                    }
                    inventory.setItem(fillSlot, backgroundItem);
                }
            }
        }
        else
        {
            for(int i = 0; i < inventorySlots; ++i)
            {
                inventory.setItem(i, null);
            }
        }

        for(GUILayer layer : layers)
        {
            if(!layer.isVisible()) continue;
            int curRowOffset = rowOffset;
            int curColOffset = colOffset;
            int invSlot = -1;
            for(int row = 0; row < newInvRows; ++row)
            {
                for(int col = 0; col < MAX_INVENTORY_COLS; ++col)
                {
                    ++invSlot;
                    GUIItem guiItem = layer.getItem(row + 1 + curRowOffset, col + 1 + curColOffset);
                    if(guiItem == null)
                    {
                        continue;
                    }
                    ItemStack itemStack = guiItem.getItem();

                    if(itemStack != null)
                    {
                        inventory.setItem(invSlot, itemStack);
                    }
                }
            }
        }
        modules.forEach(module -> module.onUpdateTail(player, this));
    }

    /**
     * Called when a <tt>Player</tt> interacts (adds or removes an item) with the GUI.
     *
     * @param event The event of the click
     */
    public void onPlayerInteract(InventoryClickEvent event)
    {
        modules.forEach(module -> module.onPlayerInteractHead(event, this));
        interactionHandler.handleInteraction(event, this);
        modules.forEach(module -> module.onPlayerInteractTail(event, this));
    }

    /**
     * Called when this GUI is clicked on
     *
     * @param event The event of the click
     */
    public void onClicked(InventoryClickEvent event)
    {
        int slot = event.getSlot();
        int row = getRowFromSlot(slot);
        int col = getColFromSlot(slot);
        modules.forEach(module -> module.onClickedHead(event, this));
        GUIItem item = getItem(row, col);
        if(item != null) item.onClick(event, this);
        modules.forEach(module -> module.onClickedTail(event, this));
    }

    /**
     * Remove an item from the GUI based on a row and a column
     *
     * @param row Row that should be removed
     * @param col Column that should be remove
     */
    public void removeItem(int row, int col)
    {
        setItem(row, col, null);
    }

    /**
     * Remove an item based off of the instance of that item
     *
     * @param item The item that should be removed
     */
    public void removeItem(GUIItem item)
    {
        GUILayer layer = getLayer(0);
        layer.removeItem(item);
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
        GUILayer layer = getLayer(0);
        layer.setItem(row, col, item);
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
        for(int i = layers.size() - 1; i >= 0; i--)
        {
            GUILayer layer = getLayer(i);
            GUIItem item = layer.getItem(row, col);
            if(item != null)
            {
                item.setMovable(true);
                return;
            }
        }
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
        for(int i = layers.size() - 1; i >= 0; --i)
        {
            GUILayer layer = getLayer(i);
            GUIItem item = layer.getItem(row, col);
            if(item != null) return item.getEvents();
        }
        return null;
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
        for(int i = layers.size() - 1; i >= 0; i--)
        {
            GUILayer layer = getLayer(i);
            GUIItem item = layer.getItem(row, col);
            if(item != null)
            {
                item.setEvents(events);
                return;
            }
        }
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
        for(int i = layers.size() - 1; i >= 0; i--)
        {
            GUILayer layer = getLayer(i);
            GUIItem item = layer.getItem(row, col);
            if(item != null)
            {
                item.addEvent(event);
                return;
            }
        }
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
        for(int i = layers.size() - 1; i >= 0; i--)
        {
            GUILayer layer = getLayer(i);
            GUIItem item = layer.getItem(row, col);
            if(item != null)
            {
                item.removeEvent(event);
                return;
            }
        }
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
        for(int i = layers.size() - 1; i >= 0; i--)
        {
            GUILayer layer = getLayer(i);
            GUIItem item = layer.getItem(row, col);
            if(item != null)
            {
                item.removeEvent(eventClass);
                return;
            }
        }
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
        for(int i = layers.size() - 1; i >= 0; i--)
        {
            GUILayer layer = getLayer(i);
            GUIItem item = layer.getItem(row, col);
            if(item != null) return item.containsEvent(event);
        }
        return false;
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
        for(int i = layers.size() - 1; i >= 0; i--)
        {
            GUILayer layer = getLayer(i);
            GUIItem item = layer.getItem(row, col);
            if(item != null) return item.containsEvent(eventClass);
        }
        return false;
    }

    /**
     * Remove all <tt>GUIEvents</tt> from a slot
     *
     * @param row Row to remove events from
     * @param col Column to remove events from
     */
    public void removeEventHandler(int row, int col)
    {
        for(int i = layers.size() - 1; i >= 0; i--)
        {
            GUILayer layer = getLayer(i);
            GUIItem item = layer.getItem(row, col);
            if(item != null)
            {
                item.resetEvents();
                return;
            }
        }
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
        for(int i = layers.size() - 1; i >= 0; i--)
        {
            GUILayer layer = getLayer(i);
            GUIItem item = layer.getItem(row, col);
            if(item != null) return item.isMovable();
        }
        return defaultMoveState;
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
        for(int i = layers.size() - 1; i >= 0; i--)
        {
            GUILayer layer = getLayer(i);
            GUIItem item = layer.getItem(row, col);
            if(item != null) return true;
        }
        return false;
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
        for(int i = layers.size() - 1; i >= 0; i--)
        {
            GUILayer layer = getLayer(i);
            GUIItem item = layer.getItem(row, col);
            if(item != null) return item;
        }
        return null;
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
        return inventoryCols;
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
        for(GUIModule module : modules)
        {
            if(moduleClass != module.getClass()) continue;
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
        for(GUIModule module : modules)
        {
            if(moduleClass != module.getClass()) continue;
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
        for(GUIModule module : modules)
        {
            if(moduleClass != module.getClass()) continue;
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
        layers.get(0).setDefaultMoveState(defaultMoveState);
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
        return (row * GUIContainer.MAX_INVENTORY_COLS) + col;
    }

    /**
     * Get the row based off of a slot
     *
     * @param slot The slot to use
     * @return The row based off of the slot
     */
    public int getRowFromSlot(int slot)
    {
        return (slot / MAX_INVENTORY_COLS) + 1 + rowOffset;
    }

    /**
     * Get the column based off of a slot
     *
     * @param slot The slot to use
     * @return The column based off of the slot
     */
    public int getColFromSlot(int slot)
    {
        return (slot % MAX_INVENTORY_COLS) + 1 + colOffset;
    }

    /**
     * Get the current offset of the row
     *
     * @return The row offset
     */
    public int getRowOffset()
    {
        return rowOffset;
    }

    /**
     * Set the current offset of the row
     *
     * @param rowOffset The new row offset
     */
    public void setRowOffset(int rowOffset)
    {
        this.rowOffset = rowOffset;
    }

    /**
     * Get the current offset of the column
     *
     * @return The column offset
     */
    public int getColOffset()
    {
        return colOffset;
    }

    /**
     * Set the current offset of the column
     *
     * @param colOffset The new column offset
     */
    public void setColOffset(int colOffset)
    {
        this.colOffset = colOffset;
    }

    /**
     * Adds an amount to the row offset
     *
     * @param amount The amount to add
     */
    public void addRowOffset(int amount)
    {
        rowOffset += amount;
    }

    /**
     * Adds an amount to the column offset
     *
     * @param amount The amount to add
     */
    public void addColOffset(int amount)
    {
        colOffset += amount;
    }

    /**
     * Get a <tt>GUILayer</tt> from this GUI
     *
     * @param layerName The name of the new layer
     * @return The requested layer
     */
    public GUILayer getLayer(String layerName, boolean overlay)
    {
        if(!containsLayer(layerName))
        {
            GUILayer newLayer = new GUILayer(this, layerName, overlay, defaultMoveState);
            layers.add(newLayer);
        }
        return getLayer(layerName);
    }

    /**
     * Remove a <tt>GUILayer</tt> from this GUI based off of its name
     *
     * @param layerName The name of the layer to remove
     */
    public void removeLayer(String layerName)
    {
        layers.removeIf(guiLayer -> guiLayer.getName().equals(layerName));
    }

    /**
     * Remove a <tt>GUILayer</tt> from the layer's instance
     *
     * @param layer The layer to remove
     */
    public void removeLayer(GUILayer layer)
    {
        layers.remove(layer);
    }

    /**
     * See if this GUI contains a <tt>GUILayer</tt> based off of an instance
     *
     * @param layer The layer to search for
     * @return Whether this GUI contains the layer or not
     */
    public boolean containsLayer(GUILayer layer)
    {
        return layers.contains(layer);
    }

    /**
     * See if this GUI contains a <tt>GUILayer</tt> based off of the name of the layer
     *
     * @param layerName The name of the layer to search for
     * @return Whether this GUI contains the layer or not
     */
    public boolean containsLayer(String layerName)
    {
        for(GUILayer layer : layers)
        {
            if(layer.getName().equals(layerName)) return true;
        }
        return false;
    }

    /**
     * Get a <tt>GUILayer</tt> based off of the GUILayer's name
     *
     * @param layerName The name of the layer to get
     * @return The <tt>GUILayer</tt>, null if not found
     */
    public GUILayer getLayer(String layerName)
    {
        if(!containsLayer(layerName))
        {
            GUILayer layer = new GUILayer(this, layerName, false, defaultMoveState);
            layers.add(layer);
            return layer;
        }
        for(GUILayer layer : layers)
        {
            if(layer.getName().equals(layerName)) return layer;
        }
        return null;
    }

    /**
     * Get a <tt>GUILayer</tt> based off of the GUILayer's index
     *
     * @param index The index of the layer to get
     * @return The <tt>GUILayer</tt>
     */
    public GUILayer getLayer(int index)
    {
        return layers.get(index);
    }

    /**
     * Get the top <tt>GUILayer</tt> of a specific slot based off of the top visible item in that slot
     *
     * @param row The row to get from
     * @param col The column to get from
     * @return The highest level visible <tt>GUILayer</tt>
     */
    public GUILayer getTopLayer(int row, int col)
    {
        for(int i = layers.size() - 1; i <= 0; --i)
        {
            GUILayer layer = layers.get(i);
            GUIItem item = layer.getItem(row, col);
            if(item == null) continue;
            return layer;
        }
        return layers.get(0);
    }

    /**
     * Force add a layer at a specific index
     *
     * @param index     The index to add the layer at
     * @param layerName The name of the layer
     * @param overlay   Whether the layer is an overlay or not
     * @return The new layer
     */
    public GUILayer addLayer(int index, String layerName, boolean overlay)
    {
        if(containsLayer(layerName)) return getLayer(layerName);
        GUILayer layer = new GUILayer(this, layerName, overlay, defaultMoveState);
        layers.add(index, layer);
        return layer;
    }

    /**
     * Gets a list of all layers in this <tt>GUIContainer</tt>
     *
     * @return The list of layers
     */
    public List<GUILayer> getAllLayers()
    {
        return layers;
    }

    /**
     * Get the amount of slots of the viewable <tt>Inventory</tt>.
     * This does not account for large GUIs.
     *
     * @return The total amount of slots of the viewable inventory
     */
    public int getSlotAmount()
    {
        return inventorySlots;
    }

    /**
     * Get this GUIs <tt>GUIInteractHandler</tt>
     *
     * @return The <tt>GUIInteractHandler</tt> for this GUI
     */
    public GUIInteractHandler getInteractionHandler()
    {
        return interactionHandler;
    }

    /**
     * Set this GUIs <tt>GUIInteractHandler</tt>
     *
     * @param interactionHandler The new <tt>GUIInteractHandler</tt> for this GUI
     */
    public void setInteractionHandler(GUIInteractHandler interactionHandler)
    {
        this.interactionHandler = interactionHandler;
    }

    /**
     * Get the background item for this GUI
     *
     * @return The background item for this GUI
     */
    public ItemStack getBackgroundItem()
    {
        return backgroundItem;
    }

    /**
     * Get the background item for this GUI
     *
     * @param backgroundItem The new background item for this GUI
     */
    public void setBackgroundItem(ItemStack backgroundItem)
    {
        this.backgroundItem = backgroundItem;
    }

    /**
     * Returns whether this GUI should fill its empty GUI slots
     *
     * @return Empty fill state
     */
    public boolean shouldFillEmpty()
    {
        return fillEmpty;
    }

    /**
     * Set whether this GUI should fill its empty slots.
     * This slightly decreases performance (~0.15ms on full size inventory)
     *
     * @param fillEmpty The new fill empty state
     */
    public void setFillEmpty(boolean fillEmpty)
    {
        this.fillEmpty = fillEmpty;
    }

    /**
     * See whether this GUI contains an item matching an <tt>ItemStack</tt>
     *
     * @param item The item to search for
     * @return Whether the item was found in the GUI or not
     */
    public boolean containsItem(ItemStack item)
    {
        for(GUILayer layer : layers)
        {
            if(layer.containsItem(item)) return true;
        }
        return false;
    }

    /**
     * See whether this GUI contains a material matching another material
     *
     * @param material The material to search for
     * @return Whether the material was found in the GUI or not
     */
    public boolean containsMaterial(Material material)
    {
        for(GUILayer layer : layers)
        {
            if(layer.containsMaterial(material)) return true;
        }
        return false;
    }
}
