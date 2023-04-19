package com.mikedeejay2.mikedeejay2lib.gui.modules.list;

import com.mikedeejay2.mikedeejay2lib.BukkitPlugin;
import com.mikedeejay2.mikedeejay2lib.gui.GUIContainer;
import com.mikedeejay2.mikedeejay2lib.gui.GUILayer;
import com.mikedeejay2.mikedeejay2lib.gui.item.GUIItem;
import org.apache.commons.lang3.Validate;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Function;

/**
 * An extension of {@link GUIListModule} that allows visualizing lists of non-{@link GUIItem} objects via a mapper
 * function.
 *
 * @param <T> The type to map from
 */
public class GUIMappedListModule<T> extends GUIListModule {
    /**
     * The unmapped list
     */
    protected List<T> unmappedList;
    /**
     * The mapping function that takes items from The unmapped list and maps them to {@link GUIItem GUIItems}.
     */
    protected Function<T, GUIItem> mapFunction;
    /**
     * The unmapping function. Only used when a {@link GUIItem} is added to the list and the item should also be mapped
     * back to its unmapped state.
     */
    protected @Nullable Function<GUIItem, T> unmapFunction;
    /**
     * Hash code of {@link GUIMappedListModule#unmappedList} at the time of the previous mapping operation
     */
    protected int lastMapHashcode;

    /**
     * Construct a new <code>GUIMappedListModule</code>
     *
     * @param plugin             Reference to the <code>BukkitPlugin</code> of the plugin
     * @param viewMode           The {@link ListViewMode} to used
     * @param unmappedList The unmapped list
     * @param mapFunction        The mapping function that takes items from The unmapped list and maps them to
     *                           {@link GUIItem GUIItems}.
     * @param topRow             The top row of the list's bounding box
     * @param bottomRow          The bottom row of the list's bounding box
     * @param leftCol            The left column of the list's bounding box
     * @param rightCol           The right column of the list's bounding box
     * @param layerName          The name of the <code>GUILayer</code> that will be used, useful for if there are multiple lists
     *                           in one GUI
     */
    public GUIMappedListModule(
        BukkitPlugin plugin,
        ListViewMode viewMode,
        List<T> unmappedList,
        Function<T, GUIItem> mapFunction,
        int topRow,
        int bottomRow,
        int leftCol,
        int rightCol,
        String layerName) {
        super(plugin, viewMode, topRow, bottomRow, leftCol, rightCol, layerName);
        setMapperFields(unmappedList, mapFunction);
    }

    /**
     * Construct a new <code>GUIMappedListModule</code>
     *
     * @param plugin             Reference to the <code>BukkitPlugin</code> of the plugin
     * @param viewMode           The {@link ListViewMode} to used
     * @param unmappedList The unmapped list
     * @param mapFunction        The mapping function that takes items from The unmapped list and maps them to
     *                           {@link GUIItem GUIItems}.
     * @param topRow             The top row of the list's bounding box
     * @param bottomRow          The bottom row of the list's bounding box
     * @param leftCol            The left column of the list's bounding box
     * @param rightCol           The right column of the list's bounding box
     */
    public GUIMappedListModule(
        BukkitPlugin plugin,
        ListViewMode viewMode,
        List<T> unmappedList,
        Function<T, GUIItem> mapFunction,
        int topRow,
        int bottomRow,
        int leftCol,
        int rightCol) {
        super(plugin, viewMode, topRow, bottomRow, leftCol, rightCol);
        setMapperFields(unmappedList, mapFunction);
    }

    /**
     * Construct a new <code>GUIMappedListModule</code>
     *
     * @param plugin             Reference to the <code>BukkitPlugin</code> of the plugin
     * @param unmappedList The unmapped list
     * @param mapFunction        The mapping function that takes items from The unmapped list and maps them to
     *                           {@link GUIItem GUIItems}.
     * @param topRow             The top row of the list's bounding box
     * @param bottomRow          The bottom row of the list's bounding box
     * @param leftCol            The left column of the list's bounding box
     * @param rightCol           The right column of the list's bounding box
     */
    public GUIMappedListModule(
        BukkitPlugin plugin,
        List<T> unmappedList,
        Function<T, GUIItem> mapFunction,
        int topRow,
        int bottomRow,
        int leftCol,
        int rightCol) {
        super(plugin, topRow, bottomRow, leftCol, rightCol);
        setMapperFields(unmappedList, mapFunction);
    }

    /**
     * Internal constructor called method for setting the mapping fields
     *
     * @param unmappedCollection The unmapped list
     * @param mapFunction        The mapping function that takes items from The unmapped list and maps them to
     *                           {@link GUIItem GUIItems}.
     */
    private void setMapperFields(List<T> unmappedCollection, Function<T, GUIItem> mapFunction) {
        Validate.notNull(unmappedCollection, "unmapped list cannot be null");
        Validate.notNull(mapFunction, "Map Function cannot be null");
        this.unmappedList = unmappedCollection;
        this.mapFunction = mapFunction;
        this.lastMapHashcode = unmappedCollection.hashCode();
    }

    @Override
    public void onOpenHead(Player player, GUIContainer gui) {
        this.mapList();
        super.onOpenHead(player, gui);
    }

    @Override
    public void onUpdateHead(Player player, GUIContainer gui) {
        if(hasChanged()) mapList();
        super.onUpdateHead(player, gui);
    }

    @Override
    public void onClickedTail(InventoryClickEvent event, GUIContainer gui) {
        if(event.getClickedInventory() != event.getInventory()) return;
        GUILayer layer = gui.getLayer(layerName);
        int slot = event.getSlot();
        int row = layer.getRowFromSlot(slot);
        int col = layer.getColFromSlot(slot);
        GUIItem item = getItem(row, col, gui);
        if(item == null) return;
        int index = getListItemIndex(row, col, gui);
        setFromUnmapped(index, item);
    }

    @Override
    public void addListItem(GUIItem item) {
        addToUnmapped(list.size(), item);
        super.addListItem(item);
    }

    @Override
    public void addListItem(int row, int col, GUIContainer gui, GUIItem item) {
        int index = getListItemIndex(row, col, gui);
        addToUnmapped(index, item);
        super.addListItem(row, col, gui, item);
    }

    @Override
    public void addListItem(int index, GUIItem item) {
        addToUnmapped(index, item);
        super.addListItem(index, item);
    }

    @Override
    public void changeGUIItem(GUIItem item, int row, int col, GUIContainer gui) {
        int index = getListItemIndex(row, col, gui);
        setFromUnmapped(index, item);
        super.changeGUIItem(item, row, col, gui);
    }

    @Override
    public void setGUIItems(List<GUIItem> items) {
        for(int i = 0; i < list.size(); ++i) {
            removeFromUnmapped(i, list.get(i));
        }
        for(int i = 0; i < items.size(); ++i) {
            addToUnmapped(i, items.get(i));
        }
        super.setGUIItems(items);
    }

    @Override
    public void removeListItem(int index) {
        removeFromUnmapped(index, getItem(index));
        super.removeListItem(index);
    }

    @Override
    public void removeListItem(GUIItem item) {
        if(!list.contains(item)) return;
        int index = list.indexOf(item);
        removeFromUnmapped(index, item);
        super.removeListItem(item);
    }

    @Override
    public void removeListItem(int row, int col, GUIContainer gui) {
        int index = getListItemIndex(row, col, gui);
        GUIItem item = getItem(index);
        removeFromUnmapped(index, item);
        super.removeListItem(row, col, gui);
    }

    private void addToUnmapped(int index, GUIItem item) {
        if(unmapFunction == null) return;
        unmappedList.add(index, unmapFunction.apply(item));
    }

    private void removeFromUnmapped(int index, GUIItem item) {
        if(unmapFunction == null) return;
        unmappedList.remove(index);
    }

    private void setFromUnmapped(int index, GUIItem item) {
        if(unmapFunction == null) return;
        unmappedList.set(index, unmapFunction.apply(item));
    }

    /**
     * Map the list of the super {@link GUIListModule} to The unmapped list. This will reset the existing super list, if
     * changes have been made to the {@link GUIItem} list, they will not be reflected upon a mapping update.
     */
    protected void mapList() {
        resetList();
        for(T unmappedObj : unmappedList) {
            super.addListItem(mapFunction.apply(unmappedObj));
        }
        this.lastMapHashcode = unmappedList.hashCode();
    }

    /**
     * Whether The unmapped list has updated since last mapping update to the list.
     *
     * @return Whether The unmapped list has changed since last update
     */
    public boolean hasChanged() {
        return lastMapHashcode != unmappedList.hashCode();
    }

    /**
     * Get the unmapped list
     *
     * @return The unmapped list
     */
    public List<T> getUnmappedList() {
        return unmappedList;
    }

    /**
     * Set the unmapped list
     *
     * @param unmappedList The new unmapped list
     */
    public void setUnmappedList(List<T> unmappedList) {
        this.unmappedList = unmappedList;
    }

    /**
     * Get the mapping function that takes items from The unmapped list and maps them to {@link GUIItem GUIItems}
     *
     * @return The mapping function
     */
    public Function<T, GUIItem> getMapFunction() {
        return mapFunction;
    }

    /**
     * Set the mapping function that takes items from The unmapped list and maps them to {@link GUIItem GUIItems}
     *
     * @param mapFunction The new mapping function
     */
    public void setMapFunction(Function<T, GUIItem> mapFunction) {
        this.mapFunction = mapFunction;
    }

    /**
     * Get the unmapping function. Only used when a {@link GUIItem} is added to the list and the item should also be mapped
     * back to its unmapped state.
     *
     * @return The unmapping function
     */
    public @Nullable Function<GUIItem, T> getUnmapFunction() {
        return unmapFunction;
    }

    /**
     * Set the unmapping function. Only used when a {@link GUIItem} is added to the list and the item should also be mapped
     * back to its unmapped state.
     *
     * @param unmapFunction The new unmapping function
     */
    public void setUnmapFunction(@Nullable Function<GUIItem, T> unmapFunction) {
        this.unmapFunction = unmapFunction;
    }
}
