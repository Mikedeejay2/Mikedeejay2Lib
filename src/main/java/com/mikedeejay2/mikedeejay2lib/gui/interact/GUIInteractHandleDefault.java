package com.mikedeejay2.mikedeejay2lib.gui.interact;

import com.mikedeejay2.mikedeejay2lib.gui.GUIContainer;
import com.mikedeejay2.mikedeejay2lib.gui.GUILayer;
import com.mikedeejay2.mikedeejay2lib.gui.item.GUIItem;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;

/**
 * Handles interactions between the player and the GUI when moving items
 *
 * @author Mikedeejay2
 */
public class GUIInteractHandleDefault extends GUIInteractHandler
{
    // Follow standard limits of Minecraft or not
    protected boolean standardLimits;

    public GUIInteractHandleDefault()
    {
        super();
        this.standardLimits = true;
    }

    /**
     * Handle an interaction between a <tt>Player</tt> and a <tt>GUIContainer</tt> to properly move an item
     * <p>
     * This method override handles the default state of item movement where items move like default Minecraft.
     * However, the default movement can be modified and the item cap can be modified by using
     * {@link GUIInteractHandleDefault#useStandardLimits(boolean)}
     *
     * @param player The player interacting with the GUI
     * @param inventory The inventory that was clicked
     * @param row The row that was interacted with
     * @param col The column that was interacted with
     * @param slot The original slot that was interacted with
     * @param action The original <tt>InventoryAction</tt> that Minecraft suggests should happen
     * @param type The original <tt>ClickType</tt> that Minecraft suggests should happen
     * @param gui The GUI that the player interacted with
     */
    @Override
    public void handleInteraction(Player player, Inventory inventory, int row, int col, int slot, InventoryAction action, ClickType type, GUIContainer gui)
    {
        player.sendMessage("Moved: " + action);
    }

    public boolean usesStandardLimits()
    {
        return standardLimits;
    }

    public void useStandardLimits(boolean standardLimits)
    {
        this.standardLimits = standardLimits;
    }
}
