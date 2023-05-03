package com.mikedeejay2.mikedeejay2lib.gui.modules.list;

import com.mikedeejay2.mikedeejay2lib.BukkitPlugin;
import com.mikedeejay2.mikedeejay2lib.gui.GUIContainer;
import com.mikedeejay2.mikedeejay2lib.gui.item.GUIItem;
import org.apache.commons.lang3.Validate;
import org.bukkit.entity.Player;
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
     * Whether items currently being added to the list should be unmapped
     */
    protected boolean shouldUnmap;

    /**
     * Construct a new <code>GUIMappedListModule</code>
     *
     * @param plugin       Reference to the <code>BukkitPlugin</code> of the plugin
     * @param viewMode     The {@link ListViewMode} to used
     * @param unmappedList The unmapped list
     * @param mapFunction  The mapping function that takes items from The unmapped list and maps them to
     *                     {@link GUIItem GUIItems}.
     * @param topRow       The top row of the list's bounding box
     * @param bottomRow    The bottom row of the list's bounding box
     * @param leftCol      The left column of the list's bounding box
     * @param rightCol     The right column of the list's bounding box
     * @param layerName    The name of the <code>GUILayer</code> that will be used, useful for if there are multiple lists
     *                     in one GUI
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
     * @param plugin       Reference to the <code>BukkitPlugin</code> of the plugin
     * @param viewMode     The {@link ListViewMode} to used
     * @param unmappedList The unmapped list
     * @param mapFunction  The mapping function that takes items from The unmapped list and maps them to
     *                     {@link GUIItem GUIItems}.
     * @param topRow       The top row of the list's bounding box
     * @param bottomRow    The bottom row of the list's bounding box
     * @param leftCol      The left column of the list's bounding box
     * @param rightCol     The right column of the list's bounding box
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
        this.shouldUnmap = true;
        addListener(new MappingListener<>(this));
    }

    /**
     * Overridden method to map list upon a GUI update
     *
     * @param player The player that is viewing the GUI
     * @param gui    The GUI
     */
    @Override
    public void onOpenHead(Player player, GUIContainer gui) {
        this.mapList();
        super.onOpenHead(player, gui);
    }

    /**
     * Overridden method to map list upon a GUI update
     *
     * @param player The player that is viewing the GUI
     * @param gui    The GUI
     */
    @Override
    public void onUpdateHead(Player player, GUIContainer gui) {
        if(hasChanged()) mapList();
        super.onUpdateHead(player, gui);
    }

    /**
     * Add a {@link GUIItem} to the unmapped list
     *
     * @param index The index being added
     * @param item  The item being added
     */
    private void addToUnmapped(int index, GUIItem item) {
        if(unmapFunction == null) return;
        unmappedList.add(index, unmapFunction.apply(item));
    }

    /**
     * Remove a {@link GUIItem} from the unmapped list
     *
     * @param index The index being removed
     */
    private void removeFromUnmapped(int index) {
        if(unmapFunction == null) return;
        unmappedList.remove(index);
    }

    /**
     * Set a {@link GUIItem} to the unmapped list
     *
     * @param index The index being set
     * @param item  The item being set
     */
    private void setToUnmapped(int index, GUIItem item) {
        if(unmapFunction == null) return;
        unmappedList.set(index, unmapFunction.apply(item));
    }

    /**
     * Map the list of the super {@link GUIListModule} to The unmapped list. This will reset the existing super list, if
     * changes have been made to the {@link GUIItem} list, they will not be reflected upon a mapping update.
     */
    protected void mapList() {
        shouldUnmap = false;
        resetList();
        for(T unmappedObj : unmappedList) {
            super.addItem(mapFunction.apply(unmappedObj));
        }
        this.lastMapHashcode = unmappedList.hashCode();
        shouldUnmap = true;
    }

    /**
     * Get an unmapped item from index
     *
     * @param index The index to get
     * @return The unmapped item at the index
     */
    public T getUnmapped(int index) {
        return unmappedList.get(index);
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

    /**
     * A listener for listening to adding items to the list. This listener unmaps new items to the unmapped list to
     * maintain synchronization between the mapped and unmapped lists.
     *
     * @param <T> The type being maintained by the unmapped list
     * @author Mikedeejay2
     */
    private static final class MappingListener<T> implements Listener {
        /**
         * The mapped list module
         */
        private final GUIMappedListModule<T> list;

        /**
         * Internal constructor
         *
         * @param list The mapped list module
         */
        private MappingListener(GUIMappedListModule<T> list) {
            this.list = list;
        }

        /**
         * Add an item to the unmapped items list
         *
         * @param item  The item being added
         * @param index The index that the item is being added to
         */
        @Override
        public void onAddItem(GUIItem item, int index) {
            if(!list.shouldUnmap) return;
            list.addToUnmapped(index, item);
        }

        /**
         * Remove an item from the unmapped items list
         *
         * @param item  The item being added
         * @param index The index that the item is being added to
         */
        @Override
        public void onRemoveItem(GUIItem item, int index) {
            if(!list.shouldUnmap) return;
            list.removeFromUnmapped(index);
        }

        /**
         * Set an item to the unmapped items list
         *
         * @param item  The item being added
         * @param index The index that the item is being added to
         */
        @Override
        public void onSetItem(GUIItem item, int index) {
            if(!list.shouldUnmap) return;
            list.setToUnmapped(index, item);
        }
    }
}
