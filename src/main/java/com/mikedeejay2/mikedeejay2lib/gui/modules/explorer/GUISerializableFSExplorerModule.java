package com.mikedeejay2.mikedeejay2lib.gui.modules.explorer;

import com.mikedeejay2.mikedeejay2lib.BukkitPlugin;
import com.mikedeejay2.mikedeejay2lib.data.filesystem.SerializableFileSystem;
import com.mikedeejay2.mikedeejay2lib.data.filesystem.SerializableFolderFS;
import com.mikedeejay2.mikedeejay2lib.gui.item.GUIItem;
import com.mikedeejay2.mikedeejay2lib.item.ItemBuilder;
import com.mikedeejay2.mikedeejay2lib.util.head.Base64Head;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.inventory.ItemFlag;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * An object explorer for viewing and interacting with a {@link SerializableFileSystem}
 *
 * @see SerializableFileSystem
 * @author Mikedeejay2
 */
public class GUISerializableFSExplorerModule<T extends ConfigurationSerializable> extends GUISerializableExplorerModule<SerializableFolderFS<T>, T> {
    /**
     * The key associated with the serializable object used as the icon of the folder, if any
     */
    public static final String ICON_KEY = "_icon";

    /**
     * The item flags to apply to folder icons
     */
    private static final ItemFlag[] PREVIEW_ITEM_FLAGS = new ItemFlag[] {
        ItemFlag.HIDE_POTION_EFFECTS,
        ItemFlag.HIDE_ATTRIBUTES,
        ItemFlag.HIDE_UNBREAKABLE,
        ItemFlag.HIDE_ENCHANTS,
        ItemFlag.HIDE_DESTROYS,
        ItemFlag.HIDE_PLACED_ON
    };

    /**
     * Construct a new <code>GUISerializableFSExplorerModule</code>
     *
     * @param plugin     The {@link BukkitPlugin} instance
     * @param fileSystem The {@link SerializableFileSystem}
     * @param converter  The converter used to convert between the serializable type and a {@link GUIItem}
     * @param viewMode   The viewing mode of the list
     * @param topRow     The top row location of the explorer
     * @param bottomRow  The bottom row location of the explorer
     * @param leftCol    The left column location of the explorer
     * @param rightCol   The right column location of the explorer
     * @param layerName  The name of the layer that the explorer will exist on
     */
    public GUISerializableFSExplorerModule(
        BukkitPlugin plugin,
        SerializableFileSystem<T> fileSystem,
        Function<T, GUIItem> converter,
        ListViewMode viewMode,
        int topRow,
        int bottomRow,
        int leftCol,
        int rightCol,
        String layerName) {
        super(plugin, fileSystem.getRootFolder(), converter, viewMode, topRow, bottomRow, leftCol, rightCol, layerName);
    }

    /**
     * Construct a new <code>GUISerializableFSExplorerModule</code>
     *
     * @param plugin     The {@link BukkitPlugin} instance
     * @param fileSystem The {@link SerializableFileSystem}
     * @param converter  The converter used to convert between the serializable type and a {@link GUIItem}
     * @param viewMode   The viewing mode of the list
     * @param topRow     The top row location of the explorer
     * @param bottomRow  The bottom row location of the explorer
     * @param leftCol    The left column location of the explorer
     * @param rightCol   The right column location of the explorer
     */
    public GUISerializableFSExplorerModule(
        BukkitPlugin plugin,
        SerializableFileSystem<T> fileSystem,
        Function<T, GUIItem> converter,
        ListViewMode viewMode,
        int topRow,
        int bottomRow,
        int leftCol,
        int rightCol) {
        this(plugin, fileSystem, converter, viewMode, topRow, bottomRow, leftCol, rightCol, "explorer");
    }

    /**
     * Construct a new <code>GUISerializableFSExplorerModule</code>
     *
     * @param plugin     The {@link BukkitPlugin} instance
     * @param fileSystem The {@link SerializableFileSystem}
     * @param converter  The converter used to convert between the serializable type and a {@link GUIItem}
     * @param topRow     The top row location of the explorer
     * @param bottomRow  The bottom row location of the explorer
     * @param leftCol    The left column location of the explorer
     * @param rightCol   The right column location of the explorer
     */
    public GUISerializableFSExplorerModule(
        BukkitPlugin plugin,
        SerializableFileSystem<T> fileSystem,
        Function<T, GUIItem> converter,
        int topRow,
        int bottomRow,
        int leftCol,
        int rightCol) {
        this(plugin, fileSystem, converter, ListViewMode.SCROLL, topRow, bottomRow, leftCol, rightCol);
    }

    /**
     * {@inheritDoc}
     *
     * @param folder The folder to get the items from
     * @return The contained items
     */
    @Override
    protected List<GUIItem> getFolderItems(SerializableFolderFS<T> folder) {
        return folder.getItemsMap().entrySet().stream()
            .filter(e -> !e.getKey().equals(ICON_KEY))
            .map(Map.Entry::getValue)
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
    protected @NotNull GUIItem getFolderItem(SerializableFolderFS<T> folder) {
        GUIItem guiItem =
            folder.containsItem(ICON_KEY) ?
            converter.apply(folder.getItem(ICON_KEY)) :
            new GUIItem(ItemBuilder.of(Base64Head.FOLDER.get()).get());
        guiItem.setName(folder.getName())
            .addItemFlags(PREVIEW_ITEM_FLAGS)
            .addLore("&7Folder")
            .addEvent(new GUISwitchFolderEvent<>(this, folder));
        return guiItem;
    }
}
