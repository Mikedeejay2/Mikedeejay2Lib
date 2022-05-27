package com.mikedeejay2.mikedeejay2lib.gui.modules.explorer;

import com.mikedeejay2.mikedeejay2lib.gui.item.GUIItem;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.function.Supplier;

/**
 * Implementation of {@link ItemFolder} where <code>Suppliers</code> are passed to the constructor to be used when for
 * just-in-time generation of the items upon the folder being viewed. This approach removes the need to create a new
 * <code>ItemFolder</code> class for every folder while still being a just-in-time folder.
 *
 * @author Mikedeejay2
 */
public class ItemFolderSupplied implements ItemFolder
{
    /**
     * The name of the folder
     */
    protected String name;

    /**
     * The display item of the folder
     */
    protected ItemStack folderItem;

    /**
     * The supplier used to generate the list of {@link ItemFolder ItemFolders} inside of this folder
     */
    protected Supplier<List<ItemFolder>> folderGenerator;

    /**
     * The supplier used to generate the list of {@link GUIItem GUIItems} inside of this folder
     */
    protected Supplier<List<GUIItem>> itemGenerator;

    /**
     * Construct a new <code>ItemFolderSupplied</code>
     *
     * @param name            The name of the folder
     * @param folderItem      The display item of the folder
     * @param folderGenerator The supplier used to generate the list of {@link ItemFolder ItemFolders} inside of this
     *                        folder
     * @param itemGenerator   The supplier used to generate the list of {@link GUIItem GUIItems} inside of this folder
     */
    public ItemFolderSupplied(String name, ItemStack folderItem, Supplier<List<ItemFolder>> folderGenerator, Supplier<List<GUIItem>> itemGenerator)
    {
        this.name = name;
        this.folderItem = folderItem;
        this.folderGenerator = folderGenerator;
        this.itemGenerator = itemGenerator;
    }

    /**
     * {@inheritDoc}
     *
     * @return The name of the folder
     */
    @Override
    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    /**
     * {@inheritDoc}
     *
     * @return The display item of the folder
     */
    @Override
    public ItemStack getFolderItem()
    {
        return folderItem;
    }

    public void setFolderItem(ItemStack folderItem)
    {
        this.folderItem = folderItem;
    }

    /**
     * Get the list of {@link ItemFolder ItemFolders} from the folder generator. This method generates the list of
     * folders to prevent construction of the entire file structure.
     *
     * @return The list of {@link ItemFolder}s
     */
    @Override
    public List<ItemFolder> getFolders()
    {
        return folderGenerator.get();
    }

    /**
     * Get the list of {@link GUIItem GUIItems} from the item generator. This method generates the list of
     * items to prevent construction of the entire file structure.
     *
     * @return The list of {@link ItemFolder}s
     */
    @Override
    public List<GUIItem> getItems()
    {
        return itemGenerator.get();
    }
}
