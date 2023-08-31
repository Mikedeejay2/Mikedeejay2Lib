package com.mikedeejay2.mikedeejay2lib.item;

import org.bukkit.inventory.ItemStack;

import java.util.List;

/**
 * A folder that holds items.
 *
 * @author Mikedeejay2
 */
public interface ItemFolder {
    /**
     * Get the name of the folder (Color codes allowed)
     *
     * @return The name of the folder
     */
    String getName();

    /**
     * Get the display item of the folder
     *
     * @return The display item of the folder
     */
    ItemStack getFolderItem();

    /**
     * Get the {@link ItemFolder}s that this folder contains.
     * <p>
     * <strong>This list should be generated only when the method is invoked.</strong> Early generation of this list
     * could lead to a cascading item generation and subsequently increased memory uses and generation time.
     *
     * @return The list of {@link ItemFolder}s
     */
    List<ItemFolder> getFolders();

    /**
     * Get the {@link ItemStack ItemStacks} that this folder contains.
     * <p>
     * <strong>This list should be generated only when the method is invoked.</strong> Early generation of this list
     * could lead to a cascading item generation and subsequently increased memory uses and generation time.
     *
     * @return The list of {@link ItemStack ItemStacks}
     */
    List<ItemStack> getItems();
}
