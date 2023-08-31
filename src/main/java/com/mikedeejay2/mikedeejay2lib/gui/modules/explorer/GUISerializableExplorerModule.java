package com.mikedeejay2.mikedeejay2lib.gui.modules.explorer;

import com.mikedeejay2.mikedeejay2lib.BukkitPlugin;
import com.mikedeejay2.mikedeejay2lib.data.filesystem.SerializableFolder;
import com.mikedeejay2.mikedeejay2lib.gui.item.GUIItem;
import com.mikedeejay2.mikedeejay2lib.item.ItemBuilder;
import com.mikedeejay2.mikedeejay2lib.text.Text;
import com.mikedeejay2.mikedeejay2lib.util.head.Base64Head;
import org.apache.commons.lang3.Validate;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * An item explorer for viewing and interacting with organized items.
 *
 * @author Mikedeejay2
 */
public class GUISerializableExplorerModule<T extends ConfigurationSerializable> extends GUIExplorerBaseModule<SerializableFolder<T>> {
    protected Function<T, GUIItem> converter;

    /**
     * Construct a new <code>GUIItemExplorerModule</code>
     *
     * @param plugin    The {@link BukkitPlugin} instance
     * @param folder    The root {@link SerializableFolder} of the explorer
     * @param converter The converter used to convert between the serialized type and a GUIItem
     * @param viewMode  The viewing mode of the list
     * @param topRow    The top row location of the explorer
     * @param bottomRow The bottom row location of the explorer
     * @param leftCol   The left column location of the explorer
     * @param rightCol  The right column location of the explorer
     * @param layerName The name of the layer that the explorer will exist on
     */
    public GUISerializableExplorerModule(
        BukkitPlugin plugin,
        SerializableFolder<T> folder,
        Function<T, GUIItem> converter,
        ListViewMode viewMode,
        int topRow,
        int bottomRow,
        int leftCol,
        int rightCol,
        String layerName) {
        super(plugin, folder, viewMode, topRow, bottomRow, leftCol, rightCol, layerName);
        Validate.notNull(converter);
        this.converter = converter;
    }

    /**
     * Construct a new <code>GUIItemExplorerModule</code>
     *
     * @param plugin    The {@link BukkitPlugin} instance
     * @param folder    The root {@link SerializableFolder} of the explorer
     * @param converter The converter used to convert between the serialized type and a GUIItem
     * @param viewMode  The viewing mode of the list
     * @param topRow    The top row location of the explorer
     * @param bottomRow The bottom row location of the explorer
     * @param leftCol   The left column location of the explorer
     * @param rightCol  The right column location of the explorer
     */
    public GUISerializableExplorerModule(
        BukkitPlugin plugin,
        SerializableFolder<T> folder,
        Function<T, GUIItem> converter,
        ListViewMode viewMode,
        int topRow,
        int bottomRow,
        int leftCol,
        int rightCol) {
        this(plugin, folder, converter, viewMode, topRow, bottomRow, leftCol, rightCol, "explorer");
    }

    /**
     * Construct a new <code>GUIItemExplorerModule</code>
     *
     * @param plugin    The {@link BukkitPlugin} instance
     * @param folder    The root {@link SerializableFolder} of the explorer
     * @param converter The converter used to convert between the serialized type and a GUIItem
     * @param topRow    The top row location of the explorer
     * @param bottomRow The bottom row location of the explorer
     * @param leftCol   The left column location of the explorer
     * @param rightCol  The right column location of the explorer
     */
    public GUISerializableExplorerModule(
        BukkitPlugin plugin,
        SerializableFolder<T> folder,
        Function<T, GUIItem> converter,
        int topRow,
        int bottomRow,
        int leftCol,
        int rightCol) {
        this(plugin, folder, converter, ListViewMode.SCROLL, topRow, bottomRow, leftCol, rightCol);
    }

    @Override
    protected Text getFolderName(SerializableFolder<T> folder) {
        return Text.of(folder.getName());
    }

    @Override
    protected List<? extends SerializableFolder<T>> getContainedFolders(SerializableFolder<T> folder) {
        return folder.getFolders();
    }

    @Override
    protected List<GUIItem> getFolderItems(SerializableFolder<T> folder) {
        return folder.getItems().stream()
            .map(converter)
            .collect(Collectors.toList());
    }

    @Override
    protected @NotNull GUIItem getFolderItem(SerializableFolder<T> folder) {
        ItemStack item = ItemBuilder.of(Base64Head.FOLDER.get())
            .setName(folder.getName()).get();
        Validate.notNull(item, "Folder item can not be null");
        GUIItem guiItem = new GUIItem(item);
        guiItem.setName(folder.getName());
        guiItem.addEvent(new GUISwitchFolderEvent<>(this, folder));
        return guiItem;
    }
}
