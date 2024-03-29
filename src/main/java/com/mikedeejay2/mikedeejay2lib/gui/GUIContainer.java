package com.mikedeejay2.mikedeejay2lib.gui;

import com.mikedeejay2.mikedeejay2lib.BukkitPlugin;
import com.mikedeejay2.mikedeejay2lib.gui.event.GUIEvent;
import com.mikedeejay2.mikedeejay2lib.gui.event.GUIEventHandler;
import com.mikedeejay2.mikedeejay2lib.gui.interact.GUIInteractHandler;
import com.mikedeejay2.mikedeejay2lib.gui.interact.normal.GUIInteractHandlerDefault;
import com.mikedeejay2.mikedeejay2lib.gui.item.GUIItem;
import com.mikedeejay2.mikedeejay2lib.gui.manager.PlayerGUI;
import com.mikedeejay2.mikedeejay2lib.gui.modules.GUIModule;
import com.mikedeejay2.mikedeejay2lib.gui.util.SlotMatcher;
import com.mikedeejay2.mikedeejay2lib.item.ItemBuilder;
import com.mikedeejay2.mikedeejay2lib.text.Text;
import com.mikedeejay2.mikedeejay2lib.util.chat.Colors;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

/**
 * A flexible, over-engineered GUI. The main way of adding onto this GUI
 * is by creating or using a {@link GUIModule} so that this GUIContainer
 * does more than nothing.
 *
 * @author Mikedeejay2
 */
public class GUIContainer {
    /**
     * The {@link BukkitPlugin} instance
     */
    protected final BukkitPlugin plugin;

    /**
     * The default (Minecraft) amount of inventory rows
     */
    public static final int MAX_INVENTORY_ROWS = 6;

    /**
     * The default (Minecraft) amount of inventory columns
     */
    public static final int MAX_INVENTORY_COLS = 9;

    /**
     * An empty stack, usually a light gray stained glass pane so it's nearly invisible
     */
    protected ItemStack backgroundItem;

    /**
     * The inventory of this GUI
     */
    protected Inventory inventory;

    /**
     * The name of this GUI's inventory
     */
    protected Text inventoryName;

    /**
     * The amount of slots in this GUI
     */
    protected int inventorySlots;

    /**
     * The amount of rows in this GUI
     */
    protected int inventoryRows;

    /**
     * The amount of columns in this GUI
     */
    protected int inventoryCols;

    /**
     * The layers of this GUI
     */
    protected List<GUILayer> layers;

    /**
     * The list of GUIModules that this GUI contains
     */
    protected List<GUIModule> modules;

    /**
     * The default move state of this GUI
     */
    protected boolean defaultMoveState;

    /**
     * The offset of the row
     */
    protected int rowOffset;

    /**
     * The offset of the column
     */
    protected int colOffset;

    /**
     * The {@link GUIInteractHandler}
     */
    protected GUIInteractHandler interactionHandler;

    /**
     * Boolean for whether non-filled items should be filled in the GUI
     */
    protected boolean fillEmpty;

    /**
     * Create a GUI of a large size. Note that to travel the GUI you must use a <code>GUIScrollerModule</code>
     *
     * @param plugin        The plugin that this GUI is created by
     * @param inventoryName The name of this GUI
     * @param inventoryRows The amount of inventory rows of this GUI
     * @param inventoryCols The amount of inventory columns of this GUI
     */
    public GUIContainer(BukkitPlugin plugin, Text inventoryName, int inventoryRows, int inventoryCols) {
        this.plugin = plugin;
        this.backgroundItem = ItemBuilder.of(Material.LIGHT_GRAY_STAINED_GLASS_PANE).setEmptyName().get();
        this.inventoryName = inventoryName;
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
    }

    /**
     * Create a GUI of a regular size.
     *
     * @param plugin        The plugin that this GUI is created by
     * @param inventoryName The name of this GUI
     * @param inventoryRows The amount of inventory rows of this GUI. Max amount: 6
     */
    public GUIContainer(BukkitPlugin plugin, Text inventoryName, int inventoryRows) {
        this(plugin, inventoryName, inventoryRows, MAX_INVENTORY_COLS);
    }

    /**
     * Create a GUI of a large size. Note that to travel the GUI you must use a <code>GUIScrollerModule</code>
     *
     * @param plugin        The plugin that this GUI is created by
     * @param inventoryName The name of this GUI
     * @param inventoryRows The amount of inventory rows of this GUI
     * @param inventoryCols The amount of inventory columns of this GUI
     */
    public GUIContainer(BukkitPlugin plugin, String inventoryName, int inventoryRows, int inventoryCols) {
        this(plugin, Text.of(Colors.format(inventoryName)), inventoryRows, inventoryCols);
    }

    /**
     * Create a GUI of a regular size.
     *
     * @param plugin        The plugin that this GUI is created by
     * @param inventoryName The name of this GUI
     * @param inventoryRows The amount of inventory rows of this GUI. Max amount: 6
     */
    public GUIContainer(BukkitPlugin plugin, String inventoryName, int inventoryRows) {
        this(plugin, Text.of(Colors.format(inventoryName)), inventoryRows);
    }

    /**
     * Calls the open for all modules of this GUI, does not close the
     * player's GUI, however.
     *
     * @param player The player that this GUI will open to
     */
    public void onOpen(Player player) {
        inventory = Bukkit.createInventory(null, inventorySlots, this.inventoryName.get(player));
        modules.forEach(module -> module.onOpenHead(player, this));
        changeAllItems();
        update(player);
        modules.forEach(module -> module.onOpenTail(player, this));
    }

    /**
     * Ensure that all items in the GUI know that they need to be changed upon opening the GUI.
     */
    private void changeAllItems() {
        for(GUILayer layer : layers) {
            for(GUIItem[] items : layer.getItemsAsArray()) {
                for(GUIItem item : items) {
                    if(item != null) item.setChanged(true);
                }
            }
        }
    }

    /**
     * Calls the close for all modules of this GUI, does not close the
     * player's GUI, however.
     *
     * @param player The player that was viewing the GUI
     */
    public void onClose(Player player) {
        modules.forEach(module -> module.onClose(player, this));
    }

    /**
     * Open this GUI for a specified player
     *
     * @param player The player to open the GUI for
     */
    public void open(Player player) {
        PlayerGUI playerGUI = plugin.getGUIManager().getPlayer(player);
        playerGUI.setGUI(this);
        onOpen(player);
        player.openInventory(inventory);
    }

    /**
     * Close the GUI for a player
     *
     * @param player The player who is currently viewing the GUI
     */
    public void close(Player player) {
        player.closeInventory();
    }

    /**
     * Updates this GUI and displays it to the player
     *
     * @param player The player viewing this GUI
     */
    public void update(Player player) {
        modules.forEach(module -> module.onUpdateHead(player, this));
        int newInvRows = Math.min(inventoryRows, MAX_INVENTORY_ROWS);
        boolean[][] changedArr = new boolean[newInvRows][MAX_INVENTORY_COLS];
        Set<GUIItem> changedItems = new HashSet<>();

        // Fill empty slots, if enabled
        if(fillEmpty) {
            int fillSlot = -1;
            GUILayer bottomLayer = layers.get(0);
            for(int row = 1; row <= newInvRows; ++row) {
                for(int col = 1; col <= MAX_INVENTORY_COLS; ++col) {
                    GUIItem item = bottomLayer.getItem(row, col);
                    ++fillSlot;
                    if(item != null || bottomLayer.getDefaultMoveState()) {
                        continue;
                    }
                    inventory.setItem(fillSlot, backgroundItem);
                    changedArr[row-1][col-1] = true;
                }
            }
        }

        // Find out what slots require an update
        for(GUILayer layer : layers) {
            if(!layer.isVisible()) continue;
            for(int row = 1; row <= newInvRows; ++row) {
                for(int col = 1; col <= MAX_INVENTORY_COLS; ++col) {
                    if(changedArr[row-1][col-1]) continue; // If already true, don't check again
                    GUIItem guiItem = layer.getItem(row + rowOffset, col + colOffset);
                    if(guiItem == null) continue;
                    changedArr[row-1][col-1] = guiItem.isChanged();
                }
            }
        }

        // Fill items
        for(GUILayer layer : layers) {
            if(!layer.isVisible()) continue;
            int invSlot = -1;
            for(int row = 1; row <= newInvRows; ++row) {
                for(int col = 1; col <= MAX_INVENTORY_COLS; ++col) {
                    ++invSlot;
                    if(!changedArr[row-1][col-1]) continue;

                    GUIItem guiItem = layer.getItem(row + rowOffset, col + colOffset);
                    if(guiItem == null) continue;
                    ItemStack itemStack = guiItem.get(player);

                    if(itemStack != null) {
                        inventory.setItem(invSlot, itemStack);
                    }
                    changedItems.add(guiItem);
                }
            }
        }

        // Clean up remnants of previous frame
        int invSlot = -1;
        for(int row = 1; row <= newInvRows; ++row) {
            for(int col = 1; col <= MAX_INVENTORY_COLS; ++col) {
                GUIItem item = null;
                for(int i = layers.size() - 1; i >= 0; i--) {
                    GUILayer layer = getLayer(i);
                    GUIItem curItem = layer.getItem(row + rowOffset, col + colOffset);
                    if(curItem != null && curItem.getType() != null && curItem.getType() != Material.AIR) {
                        item = curItem;
                        break;
                    }
                }
                ++invSlot;
                if(item != null) continue;
                inventory.setItem(invSlot, null);
            }
        }
        for(GUIItem item : changedItems) {
            item.setChanged(false);
        }

        modules.forEach(module -> module.onUpdateTail(player, this));
    }

    /**
     * Called when a <code>Player</code> interacts (adds or removes an item) with the GUI.
     *
     * @param event The event of the click
     */
    public void onPlayerInteract(InventoryClickEvent event) {
        modules.forEach(module -> module.onPlayerInteractHead(event, this));
        interactionHandler.handleInteraction(event, this);
        modules.forEach(module -> module.onPlayerInteractTail(event, this));
    }

    /**
     * Called when this GUI is clicked on
     *
     * @param event The event of the click
     */
    public void onClicked(InventoryClickEvent event) {
        int slot = event.getSlot();
        int row = getRow(slot);
        int col = getColumn(slot);
        modules.forEach(module -> module.onClickedHead(event, this));
        GUIItem item = getItem(row, col);
        if(item != null) item.onClick(event, this);
        modules.forEach(module -> module.onClickedTail(event, this));
    }

    /**
     * Remove an item from the GUI based on a row and a column
     *
     * @param row Row that should be removed
     * @param col Column that should be removed
     */
    public void removeItem(int row, int col) {
        setItem(row, col, null);
    }

    /**
     * Remove an item based off of the instance of that item
     *
     * @param item The item that should be removed
     */
    public void removeItem(GUIItem item) {
        GUILayer layer = getLayer(0);
        layer.removeItem(item);
    }

    /**
     * Remove any items that match the provided {@link SlotMatcher} from all layers
     *
     * @param matcher The {@link SlotMatcher}
     */
    public void removeMatchedItems(SlotMatcher matcher) {
        for(GUILayer layer : layers) {
            layer.removeMatchedItems(matcher);
            if(matcher.shouldStop()) break;
        }
    }

    /**
     * Set an item from a <code>GUIItem</code>
     *
     * @param row  The row that should be set
     * @param col  The column that should be set
     * @param item The GUIItem to use
     */
    public void setItem(int row, int col, GUIItem item) {
        GUILayer layer = getLayer(0);
        layer.setItem(row, col, item);
    }

    /**
     * Remove any items that match the provided {@link SlotMatcher} from all layers
     *
     * @param matcher The {@link SlotMatcher}
     * @param item The GUIItem to use
     */
    public void setItem(SlotMatcher matcher, GUIItem item) {
        for(GUILayer layer : layers) {
            layer.setItem(matcher, item);
            if(matcher.shouldStop()) break;
        }
    }

    /**
     * Remove any items that match the provided {@link SlotMatcher} from all layers
     *
     * @param matcher The {@link SlotMatcher}
     * @param stack The <code>ItemStack</code> to set the slot to
     */
    public void setItem(SlotMatcher matcher, ItemStack stack) {
        for(GUILayer layer : layers) {
            layer.setItem(matcher, stack);
            if(matcher.shouldStop()) break;
        }
    }

    /**
     * Set the move state of an item
     *
     * @param row     The row to set
     * @param col     The column to set
     * @param movable Whether the item is movable or not
     */
    public void setMovable(int row, int col, boolean movable) {
        for(int i = layers.size() - 1; i >= 0; i--) {
            GUILayer layer = getLayer(i);
            GUIItem item = layer.getItem(row, col);
            if(item != null) {
                item.setMovable(true);
                return;
            }
        }
    }

    /**
     * Get the <code>GUIEventHandler</code> from a slot
     *
     * @param row The row to get
     * @param col The column to get
     * @return The events of the slot
     */
    public GUIEventHandler getEventHandler(int row, int col) {
        for(int i = layers.size() - 1; i >= 0; --i) {
            GUILayer layer = getLayer(i);
            GUIItem item = layer.getItem(row, col);
            if(item != null) return item.getEvents();
        }
        return null;
    }

    /**
     * Set the <code>GUIEventHandler</code> for a slot
     *
     * @param row    The row to set
     * @param col    The column to set
     * @param events The events to set the slot to
     */
    public void setEventHandler(int row, int col, GUIEventHandler events) {
        for(int i = layers.size() - 1; i >= 0; i--) {
            GUILayer layer = getLayer(i);
            GUIItem item = layer.getItem(row, col);
            if(item != null) {
                item.setEvents(events);
                return;
            }
        }
    }

    /**
     * Add a <code>GUIEvent</code> to a slot
     *
     * @param row   The row to set
     * @param col   The column to set
     * @param event The GUIEvent to add
     */
    public void addEvent(int row, int col, GUIEvent event) {
        for(int i = layers.size() - 1; i >= 0; i--) {
            GUILayer layer = getLayer(i);
            GUIItem item = layer.getItem(row, col);
            if(item != null) {
                item.addEvent(event);
                return;
            }
        }
    }

    /**
     * Remove a <code>GUIEvent</code> from a slot based off of instance
     *
     * @param row   The row to remove
     * @param col   The column to remove
     * @param event The GUIEvent to remove
     */
    public void removeEvent(int row, int col, GUIEvent event) {
        for(int i = layers.size() - 1; i >= 0; i--) {
            GUILayer layer = getLayer(i);
            GUIItem item = layer.getItem(row, col);
            if(item != null) {
                item.removeEvent(event);
                return;
            }
        }
    }

    /**
     * Remove a <code>GUIEvent</code> from a slot based off of the event's class
     *
     * @param row        The row to remove
     * @param col        The column to remove
     * @param eventClass The class of the GUIEvent that should be removed
     */
    public void removeEvent(int row, int col, Class<? extends GUIEvent> eventClass) {
        for(int i = layers.size() - 1; i >= 0; i--) {
            GUILayer layer = getLayer(i);
            GUIItem item = layer.getItem(row, col);
            if(item != null) {
                item.removeEvent(eventClass);
                return;
            }
        }
    }

    /**
     * Returns whether a row and a column contains a <code>GUIEvent</code> based off of instance
     *
     * @param row   The row to search in
     * @param col   The column to search in
     * @param event The event that should be searched for
     * @return Whether the slot contains the event
     */
    public boolean containsEvent(int row, int col, GUIEvent event) {
        for(int i = layers.size() - 1; i >= 0; i--) {
            GUILayer layer = getLayer(i);
            GUIItem item = layer.getItem(row, col);
            if(item != null) return item.containsEvent(event);
        }
        return false;
    }

    /**
     * Returns whether a row and a column contains a <code>GUIEvent</code> based off of the class of that event
     *
     * @param row        The row to search in
     * @param col        The column to search in
     * @param eventClass The class of the event that should be searched for
     * @return Whether the slot contains the event
     */
    public boolean containsEvent(int row, int col, Class<? extends GUIEvent> eventClass) {
        for(int i = layers.size() - 1; i >= 0; i--) {
            GUILayer layer = getLayer(i);
            GUIItem item = layer.getItem(row, col);
            if(item != null) return item.containsEvent(eventClass);
        }
        return false;
    }

    /**
     * Remove all <code>GUIEvents</code> from a slot
     *
     * @param row Row to remove events from
     * @param col Column to remove events from
     */
    public void resetEvents(int row, int col) {
        for(int i = layers.size() - 1; i >= 0; i--) {
            GUILayer layer = getLayer(i);
            GUIItem item = layer.getItem(row, col);
            if(item != null) {
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
    public boolean isMovable(int row, int col) {
        for(int i = layers.size() - 1; i >= 0; i--) {
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
    public boolean itemExists(int row, int col) {
        for(int i = layers.size() - 1; i >= 0; i--) {
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
     * @return The <code>GUIItem</code> that is contained in that slot
     */
    public GUIItem getItem(int row, int col) {
        for(int i = layers.size() - 1; i >= 0; i--) {
            GUILayer layer = getLayer(i);
            GUIItem item = layer.getItem(row, col);
            if(item != null) return item;
        }
        return null;
    }

    /**
     * For each slot in each layer of this GUI, check whether the slot matches with the provided {@link SlotMatcher}.
     * If the slot matches, the provided <code>Consumer</code> will be called with the generated
     * {@link SlotMatcher.MatchData}.
     *
     * @param matcher The matcher
     * @param player The player viewing the GUI (optional)
     * @param consumer The consumer to be called upon a match
     */
    public void forMatchedSlots(SlotMatcher matcher, Player player, Consumer<SlotMatcher.MatchData> consumer) {
        for(GUILayer layer : getAllLayers()) {
            layer.forMatchedSlots(matcher, player, consumer);
            if(matcher.shouldStop()) break;
        }
    }

    /**
     * For each slot in each layer of this GUI, check whether the slot matches with the provided {@link SlotMatcher}.
     * If the slot matches, the provided <code>Consumer</code> will be called with the generated
     * {@link SlotMatcher.MatchData}.
     *
     * @param matcher The matcher
     * @param consumer The consumer to be called upon a match
     */
    public void forMatchedSlots(SlotMatcher matcher, Consumer<SlotMatcher.MatchData> consumer) {
        this.forMatchedSlots(matcher, null, consumer);
    }

    /**
     * Set the inventory name for this GUI
     *
     * @param newName The new name of the inventory
     */
    public void setInventoryName(String newName) {
        this.inventoryName = Text.of(Colors.format(newName));
    }

    /**
     * Set the inventory name for this GUI
     *
     * @param newName The new name of the inventory
     */
    public void setInventoryName(Text newName) {
        this.inventoryName = newName;
    }

    /**
     * Get the name of this GUI
     *
     * @return The name of this GUI
     */
    public Text getName() {
        return inventoryName;
    }

    /**
     * Get the amount of rows of this GUI
     *
     * @return The amount of rows of this GUI
     */
    public int getRows() {
        return inventoryRows;
    }

    /**
     * Get the amount of columns of this GUI
     *
     * @return The amount of columns of this GUI
     */
    public int getCols() {
        return inventoryCols;
    }

    /**
     * Add a <code>GUIModule</code> to this GUI
     *
     * @param module Module that will be added to this GUI
     */
    public void addModule(GUIModule module) {
        modules.add(module);
    }

    /**
     * Remove a <code>GUIModule</code> based off of an instance
     *
     * @param module The module to remove from this GUI
     */
    public void removeModule(GUIModule module) {
        modules.remove(module);
    }

    /**
     * Remove a <code>GUIModule</code> based off of the class type of the module
     *
     * @param moduleClass The module class to remove from this GUI
     */
    public void removeModule(Class<? extends GUIModule> moduleClass) {
        for(GUIModule module : modules) {
            if(!moduleClass.isAssignableFrom(module.getClass())) continue;
            modules.remove(module);
            break;
        }
    }

    /**
     * Returns whether this GUI contains a certain <code>GUIModule</code> via instance of that module
     *
     * @param module The module to search for
     * @return Whether this GUI contains the module
     */
    public boolean containsModule(GUIModule module) {
        return modules.contains(module);
    }

    /**
     * Returns whether this GUI contains a certain <code>GUIModule</code> via instance of that module
     *
     * @param moduleClass The module class to search for
     * @return Whether this GUI contains the module
     */
    public boolean containsModule(Class<? extends GUIModule> moduleClass) {
        for(GUIModule module : modules) {
            if(!moduleClass.isAssignableFrom(module.getClass())) continue;
            return true;
        }
        return false;
    }

    /**
     * Get a <code>GUIModule</code> based off of the module's class
     *
     * @param moduleClass The class of the module to get
     * @param <T> The module type that will be returned
     * @return The module, null if not found
     */
    public <T extends GUIModule> T getModule(Class<T> moduleClass) {
        for(GUIModule module : modules) {
            if(!moduleClass.isAssignableFrom(module.getClass())) continue;
            return moduleClass.cast(module);
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
    public Inventory getInventory() {
        return inventory;
    }

    /**
     * Get the default move state of this GUI
     *
     * @return The default move state
     */
    public boolean getDefaultMoveState() {
        return defaultMoveState;
    }

    /**
     * Set the default move state of this GUI
     *
     * @param defaultMoveState The new default move state
     */
    public void setDefaultMoveState(boolean defaultMoveState) {
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
    public int getSlot(int row, int col) {
        return (row * GUIContainer.MAX_INVENTORY_COLS) + col;
    }

    /**
     * Get the row based off of a slot
     *
     * @param slot The slot to use
     * @return The row based off of the slot
     */
    public int getRow(int slot) {
        return (slot / MAX_INVENTORY_COLS) + 1 + rowOffset;
    }

    /**
     * Get the column based off of a slot
     *
     * @param slot The slot to use
     * @return The column based off of the slot
     */
    public int getColumn(int slot) {
        return (slot % MAX_INVENTORY_COLS) + 1 + colOffset;
    }

    /**
     * Get the current offset of the row
     *
     * @return The row offset
     */
    public int getRowOffset() {
        return rowOffset;
    }

    /**
     * Set the current offset of the row
     *
     * @param rowOffset The new row offset
     */
    public void setRowOffset(int rowOffset) {
        if(this.rowOffset == rowOffset) return;
        this.rowOffset = rowOffset;
        changeAllItems();
    }

    /**
     * Get the current offset of the column
     *
     * @return The column offset
     */
    public int getColumnOffset() {
        return colOffset;
    }

    /**
     * Set the current offset of the column
     *
     * @param colOffset The new column offset
     */
    public void setColumnOffset(int colOffset) {
        if(this.colOffset == colOffset) return;
        this.colOffset = colOffset;
        changeAllItems();
    }

    /**
     * Adds an amount to the row offset
     *
     * @param amount The amount to add
     */
    public void addRowOffset(int amount) {
        if(amount == 0) return;
        rowOffset += amount;
        changeAllItems();
    }

    /**
     * Adds an amount to the column offset
     *
     * @param amount The amount to add
     */
    public void addColumnOffset(int amount) {
        if(amount == 0) return;
        colOffset += amount;
        changeAllItems();
    }

    /**
     * Get a <code>GUILayer</code> from this GUI
     *
     * @param layerName The name of the new layer
     * @param overlay Whether the layer should be of an overlay type or not
     * @return The requested layer
     */
    public GUILayer getLayer(String layerName, boolean overlay) {
        if(!containsLayer(layerName)) {
            GUILayer newLayer = new GUILayer(this, layerName, overlay, defaultMoveState);
            layers.add(newLayer);
        }
        return getLayer(layerName);
    }

    /**
     * Remove a <code>GUILayer</code> from this GUI based off of its name
     *
     * @param layerName The name of the layer to remove
     */
    public void removeLayer(String layerName) {
        layers.removeIf(guiLayer -> guiLayer.getName().equals(layerName));
    }

    /**
     * Remove a <code>GUILayer</code> from the layer's instance
     *
     * @param layer The layer to remove
     */
    public void removeLayer(GUILayer layer) {
        layers.remove(layer);
    }

    /**
     * See if this GUI contains a <code>GUILayer</code> based off of an instance
     *
     * @param layer The layer to search for
     * @return Whether this GUI contains the layer or not
     */
    public boolean containsLayer(GUILayer layer) {
        return layers.contains(layer);
    }

    /**
     * See if this GUI contains a <code>GUILayer</code> based off of the name of the layer
     *
     * @param layerName The name of the layer to search for
     * @return Whether this GUI contains the layer or not
     */
    public boolean containsLayer(String layerName) {
        for(GUILayer layer : layers) {
            if(layer.getName().equals(layerName)) return true;
        }
        return false;
    }

    /**
     * Get a <code>GUILayer</code> based off of the GUILayer's name
     *
     * @param layerName The name of the layer to get
     * @return The <code>GUILayer</code>, null if not found
     */
    public GUILayer getLayer(String layerName) {
        if(!containsLayer(layerName)) {
            GUILayer layer = new GUILayer(this, layerName, false, defaultMoveState);
            layers.add(layer);
            return layer;
        }
        for(GUILayer layer : layers) {
            if(layer.getName().equals(layerName)) return layer;
        }
        return null;
    }

    /**
     * Get a <code>GUILayer</code> based off of the GUILayer's index
     *
     * @param index The index of the layer to get
     * @return The <code>GUILayer</code>
     */
    public GUILayer getLayer(int index) {
        return layers.get(index);
    }

    /**
     * Get the top <code>GUILayer</code> of a specific slot based off of the top visible item in that slot
     *
     * @param row The row to get from
     * @param col The column to get from
     * @return The highest level visible <code>GUILayer</code>
     */
    public GUILayer getTopLayer(int row, int col) {
        for(int i = layers.size() - 1; i >= 0; --i) {
            GUILayer layer = layers.get(i);
            GUIItem item = layer.getItem(row, col);
            if(item != null) return layer;
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
    public GUILayer addLayer(int index, String layerName, boolean overlay) {
        if(containsLayer(layerName)) return getLayer(layerName);
        GUILayer layer = new GUILayer(this, layerName, overlay, defaultMoveState);
        layers.add(index, layer);
        return layer;
    }

    /**
     * Gets a list of all layers in this <code>GUIContainer</code>
     *
     * @return The list of layers
     */
    public List<GUILayer> getAllLayers() {
        return layers;
    }

    /**
     * Get the amount of slots of the viewable <code>Inventory</code>.
     * This does not account for large GUIs.
     *
     * @return The total amount of slots of the viewable inventory
     */
    public int getSlotAmount() {
        return inventorySlots;
    }

    /**
     * Get this GUIs <code>GUIInteractHandler</code>
     *
     * @return The <code>GUIInteractHandler</code> for this GUI
     */
    public GUIInteractHandler getInteractionHandler() {
        return interactionHandler;
    }

    /**
     * Set this GUIs <code>GUIInteractHandler</code>
     *
     * @param interactionHandler The new <code>GUIInteractHandler</code> for this GUI
     */
    public void setInteractionHandler(GUIInteractHandler interactionHandler) {
        this.interactionHandler = interactionHandler;
    }

    /**
     * Get the background item for this GUI
     *
     * @return The background item for this GUI
     */
    public ItemStack getBackgroundItem() {
        return backgroundItem;
    }

    /**
     * Get the background item for this GUI
     *
     * @param backgroundItem The new background item for this GUI
     */
    public void setBackgroundItem(ItemStack backgroundItem) {
        this.backgroundItem = backgroundItem;
    }

    /**
     * Returns whether this GUI should fill its empty GUI slots
     *
     * @return Empty fill state
     */
    public boolean shouldFillEmpty() {
        return fillEmpty;
    }

    /**
     * Set whether this GUI should fill its empty slots.
     * This slightly decreases performance (~0.15ms on full size inventory)
     *
     * @param fillEmpty The new fill empty state
     */
    public void setFillEmpty(boolean fillEmpty) {
        this.fillEmpty = fillEmpty;
    }

    /**
     * See whether this GUI contains an item matching an <code>ItemStack</code>
     *
     * @param item The item to search for
     * @return Whether the item was found in the GUI or not
     */
    public boolean containsItem(ItemStack item) {
        for(GUILayer layer : layers) {
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
    public boolean containsMaterial(Material material) {
        for(GUILayer layer : layers) {
            if(layer.containsMaterial(material)) return true;
        }
        return false;
    }

    /**
     * Method to get all <code>GUIModules</code> in this <code>GUIContainer</code>
     *
     * @return A list of all <code>GUIModules</code>
     */
    protected List<GUIModule> getModules() {
        return modules;
    }
}
