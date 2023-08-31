package com.mikedeejay2.mikedeejay2lib.gui.modules.explorer;

import com.mikedeejay2.mikedeejay2lib.BukkitPlugin;
import com.mikedeejay2.mikedeejay2lib.gui.item.GUIItem;
import com.mikedeejay2.mikedeejay2lib.item.ItemFolder;
import com.mikedeejay2.mikedeejay2lib.text.Text;
import org.apache.commons.lang3.Validate;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.stream.Collectors;

/**
 * An item explorer for viewing and interacting with organized items.
 *
 * @author Mikedeejay2
 */
public class GUIItemExplorerModule extends GUIExplorerBaseModule<ItemFolder> {
    /**
     * Construct a new <code>GUIItemExplorerModule</code>
     *
     * @param plugin    The {@link BukkitPlugin} instance
     * @param folder    The root {@link ItemFolder} of the explorer
     * @param viewMode  The viewing mode of the list
     * @param topRow    The top row location of the explorer
     * @param bottomRow The bottom row location of the explorer
     * @param leftCol   The left column location of the explorer
     * @param rightCol  The right column location of the explorer
     * @param layerName The name of the layer that the explorer will exist on
     */
    public GUIItemExplorerModule(BukkitPlugin plugin, ItemFolder folder, ListViewMode viewMode, int topRow, int bottomRow, int leftCol, int rightCol, String layerName) {
        super(plugin, folder, viewMode, topRow, bottomRow, leftCol, rightCol, layerName);
    }

    /**
     * Construct a new <code>GUIItemExplorerModule</code>
     *
     * @param plugin    The {@link BukkitPlugin} instance
     * @param folder    The root {@link ItemFolder} of the explorer
     * @param viewMode  The viewing mode of the list
     * @param topRow    The top row location of the explorer
     * @param bottomRow The bottom row location of the explorer
     * @param leftCol   The left column location of the explorer
     * @param rightCol  The right column location of the explorer
     */
    public GUIItemExplorerModule(BukkitPlugin plugin, ItemFolder folder, ListViewMode viewMode, int topRow, int bottomRow, int leftCol, int rightCol) {
        this(plugin, folder, viewMode, topRow, bottomRow, leftCol, rightCol, "explorer");
    }

    /**
     * Construct a new <code>GUIItemExplorerModule</code>
     *
     * @param plugin    The {@link BukkitPlugin} instance
     * @param folder    The root {@link ItemFolder} of the explorer
     * @param topRow    The top row location of the explorer
     * @param bottomRow The bottom row location of the explorer
     * @param leftCol   The left column location of the explorer
     * @param rightCol  The right column location of the explorer
     */
    public GUIItemExplorerModule(BukkitPlugin plugin, ItemFolder folder, int topRow, int bottomRow, int leftCol, int rightCol) {
        this(plugin, folder, ListViewMode.SCROLL, topRow, bottomRow, leftCol, rightCol);
    }

    @Override
    protected Text getFolderName(ItemFolder folder) {
        return Text.of(folder.getName());
    }

    @Override
    protected List<ItemFolder> getContainedFolders(ItemFolder folder) {
        return folder.getFolders();
    }

    @Override
    protected List<GUIItem> getFolderItems(ItemFolder folder) {
        return folder.getItems().stream()
            .map(GUIItem::new)
            .collect(Collectors.toList());
    }

    @Override
    protected @NotNull GUIItem getFolderItem(ItemFolder folder) {
        ItemStack item = folder.getFolderItem();
        Validate.notNull(item, "Folder item can not be null");
        GUIItem guiItem = new GUIItem(item);
        guiItem.setName(folder.getName());
        guiItem.addEvent(new GUISwitchFolderEvent<>(this, folder));
        return guiItem;
    }
}
