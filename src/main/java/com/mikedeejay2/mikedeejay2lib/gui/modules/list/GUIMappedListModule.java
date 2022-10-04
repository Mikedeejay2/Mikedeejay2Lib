package com.mikedeejay2.mikedeejay2lib.gui.modules.list;

import com.mikedeejay2.mikedeejay2lib.BukkitPlugin;
import com.mikedeejay2.mikedeejay2lib.gui.GUIContainer;
import com.mikedeejay2.mikedeejay2lib.gui.item.GUIItem;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.function.Function;

/**
 * An extension of {@link GUIListModule} that allows visualizing lists of non-{@link GUIItem} objects via a mapper
 * function.
 *
 * @param <T> The type to map from
 */
public class GUIMappedListModule<T> extends GUIListModule {
    /**
     * The unmapped collection
     */
    protected Collection<T> unmappedCollection;
    /**
     * The mapping function that takes items from The unmapped collection and maps them to {@link GUIItem GUIItems}.
     */
    protected Function<T, GUIItem> mapFunction;
    /**
     * Hash code of {@link GUIMappedListModule#unmappedCollection} at the time of the previous mapping operation
     */
    protected int lastMapHashcode;

    /**
     * Construct a new <code>GUIMappedListModule</code>
     *
     * @param plugin             Reference to the <code>BukkitPlugin</code> of the plugin
     * @param viewMode           The {@link ListViewMode} to used
     * @param unmappedCollection The unmapped collection
     * @param mapFunction        The mapping function that takes items from The unmapped collection and maps them to
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
        Collection<T> unmappedCollection,
        Function<T, GUIItem> mapFunction,
        int topRow,
        int bottomRow,
        int leftCol,
        int rightCol,
        String layerName) {
        super(plugin, viewMode, topRow, bottomRow, leftCol, rightCol, layerName);
        setMapperFields(unmappedCollection, mapFunction);
    }

    /**
     * Construct a new <code>GUIMappedListModule</code>
     *
     * @param plugin             Reference to the <code>BukkitPlugin</code> of the plugin
     * @param viewMode           The {@link ListViewMode} to used
     * @param unmappedCollection The unmapped collection
     * @param mapFunction        The mapping function that takes items from The unmapped collection and maps them to
     *                           {@link GUIItem GUIItems}.
     * @param topRow             The top row of the list's bounding box
     * @param bottomRow          The bottom row of the list's bounding box
     * @param leftCol            The left column of the list's bounding box
     * @param rightCol           The right column of the list's bounding box
     */
    public GUIMappedListModule(
        BukkitPlugin plugin,
        ListViewMode viewMode,
        Collection<T> unmappedCollection,
        Function<T, GUIItem> mapFunction,
        int topRow,
        int bottomRow,
        int leftCol,
        int rightCol) {
        super(plugin, viewMode, topRow, bottomRow, leftCol, rightCol);
        setMapperFields(unmappedCollection, mapFunction);
    }

    /**
     * Construct a new <code>GUIMappedListModule</code>
     *
     * @param plugin             Reference to the <code>BukkitPlugin</code> of the plugin
     * @param unmappedCollection The unmapped collection
     * @param mapFunction        The mapping function that takes items from The unmapped collection and maps them to
     *                           {@link GUIItem GUIItems}.
     * @param topRow             The top row of the list's bounding box
     * @param bottomRow          The bottom row of the list's bounding box
     * @param leftCol            The left column of the list's bounding box
     * @param rightCol           The right column of the list's bounding box
     */
    public GUIMappedListModule(
        BukkitPlugin plugin,
        Collection<T> unmappedCollection,
        Function<T, GUIItem> mapFunction,
        int topRow,
        int bottomRow,
        int leftCol,
        int rightCol) {
        super(plugin, topRow, bottomRow, leftCol, rightCol);
        setMapperFields(unmappedCollection, mapFunction);
    }

    /**
     * Internal constructor called method for setting the mapping fields
     *
     * @param unmappedCollection The unmapped collection
     * @param mapFunction        The mapping function that takes items from The unmapped collection and maps them to
     *                           {@link GUIItem GUIItems}.
     */
    private void setMapperFields(Collection<T> unmappedCollection, Function<T, GUIItem> mapFunction) {
        this.unmappedCollection = unmappedCollection;
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

    /**
     * Map the list of the super {@link GUIListModule} to The unmapped collection. This will reset the existing super list, if
     * changes have been made to the {@link GUIItem} list, they will not be reflected upon a mapping update.
     */
    protected void mapList() {
        resetList();
        for(T unmappedObj : unmappedCollection) {
            list.add(mapFunction.apply(unmappedObj));
        }
        this.lastMapHashcode = unmappedCollection.hashCode();
    }

    /**
     * Whether The unmapped collection has updated since last mapping update to the list.
     *
     * @return Whether The unmapped collection has changed since last update
     */
    public boolean hasChanged() {
        return lastMapHashcode != unmappedCollection.hashCode();
    }
}
