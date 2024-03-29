package com.mikedeejay2.mikedeejay2lib.gui.modules.explorer;

import com.mikedeejay2.mikedeejay2lib.BukkitPlugin;
import com.mikedeejay2.mikedeejay2lib.data.filesystem.SerializableFolder;
import com.mikedeejay2.mikedeejay2lib.gui.item.GUIItem;
import com.mikedeejay2.mikedeejay2lib.item.ItemBuilder;
import com.mikedeejay2.mikedeejay2lib.text.Text;
import com.mikedeejay2.mikedeejay2lib.util.chat.Colors;
import com.mikedeejay2.mikedeejay2lib.util.head.Base64Head;
import org.apache.commons.lang3.Validate;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * An object explorer for viewing and interacting with organized serializable objects. This module displays
 * {@link SerializableFolder SerializableFolders} and allows traversing through the file structure.
 *
 * @see SerializableFolder
 * @see com.mikedeejay2.mikedeejay2lib.data.filesystem.SerializableFileSystem SerializableFileSystem
 * @author Mikedeejay2
 */
public class GUISerializableExplorerModule<F extends SerializableFolder<T>, T extends ConfigurationSerializable> extends GUIExplorerBaseModule<F> {
    /**
     * The converter used to convert between the serialized type and a {@link GUIItem}
     */
    protected Function<T, GUIItem> converter;

    /**
     * Construct a new <code>GUISerializableExplorerModule</code>
     *
     * @param plugin    The {@link BukkitPlugin} instance
     * @param folder    The root {@link SerializableFolder} of the explorer
     * @param converter The converter used to convert between the serializable type and a {@link GUIItem}
     * @param viewMode  The viewing mode of the list
     * @param topRow    The top row location of the explorer
     * @param bottomRow The bottom row location of the explorer
     * @param leftCol   The left column location of the explorer
     * @param rightCol  The right column location of the explorer
     * @param layerName The name of the layer that the explorer will exist on
     */
    public GUISerializableExplorerModule(
        BukkitPlugin plugin,
        F folder,
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
     * Construct a new <code>GUISerializableExplorerModule</code>
     *
     * @param plugin    The {@link BukkitPlugin} instance
     * @param folder    The root {@link SerializableFolder} of the explorer
     * @param converter The converter used to convert between the serializable type and a {@link GUIItem}
     * @param viewMode  The viewing mode of the list
     * @param topRow    The top row location of the explorer
     * @param bottomRow The bottom row location of the explorer
     * @param leftCol   The left column location of the explorer
     * @param rightCol  The right column location of the explorer
     */
    public GUISerializableExplorerModule(
        BukkitPlugin plugin,
        F folder,
        Function<T, GUIItem> converter,
        ListViewMode viewMode,
        int topRow,
        int bottomRow,
        int leftCol,
        int rightCol) {
        this(plugin, folder, converter, viewMode, topRow, bottomRow, leftCol, rightCol, "explorer");
    }

    /**
     * Construct a new <code>GUISerializableExplorerModule</code>
     *
     * @param plugin    The {@link BukkitPlugin} instance
     * @param folder    The root {@link SerializableFolder} of the explorer
     * @param converter The converter used to convert between the serializable type and a {@link GUIItem}
     * @param topRow    The top row location of the explorer
     * @param bottomRow The bottom row location of the explorer
     * @param leftCol   The left column location of the explorer
     * @param rightCol  The right column location of the explorer
     */
    public GUISerializableExplorerModule(
        BukkitPlugin plugin,
        F folder,
        Function<T, GUIItem> converter,
        int topRow,
        int bottomRow,
        int leftCol,
        int rightCol) {
        this(plugin, folder, converter, ListViewMode.SCROLL, topRow, bottomRow, leftCol, rightCol);
    }

    /**
     * {@inheritDoc}
     *
     * @param folder The folder to get the name of
     * @return The name of the folder
     */
    @Override
    protected Text getFolderName(F folder) {
        return Text.of("&r").concat(folder.getName())
            .color(Colors.FormatStyle.COLOR_CODES, Colors.FormatStyle.HEX);
    }

    /**
     * {@inheritDoc}
     *
     * @param folder The folder to get the contained folders from
     * @return The contained folders
     */
    @Override
    protected List<? extends F> getContainedFolders(F folder) {
        return (List<? extends F>) folder.getFolders();
    }

    /**
     * {@inheritDoc}
     *
     * @param folder The folder to get the items from
     * @return The contained items
     */
    @Override
    protected List<GUIItem> getFolderItems(F folder) {
        return folder.getObjects().stream()
            .map(converter)
            .collect(Collectors.toList());
    }

    /**
     * Generate a folder item from a folder
     *
     * @param folder The folder to get the item from
     * @return The new {@link GUIItem}
     */
    @Override
    protected @NotNull GUIItem getFolderItem(F folder) {
        ItemStack item = ItemBuilder.of(Base64Head.FOLDER.get())
            .setName(folder.getName())
            .setLore("&9Folder")
            .get();
        Validate.notNull(item, "Folder item can not be null");
        GUIItem guiItem = new GUIItem(item);
        guiItem.setName(folder.getName());
        guiItem.addEvent(new GUISwitchFolderEvent<>(this, folder));
        return guiItem;
    }
}
