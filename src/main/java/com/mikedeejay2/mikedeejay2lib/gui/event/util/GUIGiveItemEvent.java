package com.mikedeejay2.mikedeejay2lib.gui.event.util;

import com.mikedeejay2.mikedeejay2lib.gui.GUIContainer;
import com.mikedeejay2.mikedeejay2lib.gui.event.GUIEvent;
import com.mikedeejay2.mikedeejay2lib.gui.item.GUIItem;
import com.mikedeejay2.mikedeejay2lib.item.ItemBuilder;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Give the <code>Player</code> viewing the GUI a specified item
 *
 * @author Mikedeejay2
 */
public class GUIGiveItemEvent implements GUIEvent
{
    /**
     * The item that will be given to the player upon click
     */
    protected Function<InventoryClickEvent, ItemStack> itemStack;

    /**
     * Construct a new <code>GUIGiveItemEvent</code>
     *
     * @param itemStack The item that will be given to the player upon click
     */
    public GUIGiveItemEvent(ItemStack itemStack)
    {
        this.itemStack = (event) -> itemStack;
    }

    /**
     * Construct a new <code>GUIGiveItemEvent</code>
     *
     * @param builder The item builder that will be given to the player upon click
     */
    public GUIGiveItemEvent(ItemBuilder builder)
    {
        this.itemStack = (event) -> builder.get();
    }

    /**
     * Construct a new <code>GUIGiveItemEvent</code>
     *
     * @param function The function that will generate the item that will be given to the player upon click
     */
    public GUIGiveItemEvent(Function<InventoryClickEvent, ItemStack> function)
    {
        this.itemStack = function;
    }

    /**
     * {@inheritDoc}
     *
     * @param event The event of the click
     * @param gui   The GUI that the event took place in
     */
    @Override
    public void execute(InventoryClickEvent event, GUIContainer gui)
    {
        Player player = (Player) event.getWhoClicked();
        player.getInventory().addItem(itemStack.apply(event));
    }

    /**
     * Set the item that will be given to the player upon click
     *
     * @param itemStack The new <code>ItemStack</code>
     */
    public void setItemStack(ItemStack itemStack)
    {
        this.itemStack = (clickType) -> itemStack;
    }

    /**
     * Set the item that will be given to the player upon click
     *
     * @param function The function that will generate the item that will be given to the player upon click
     */
    public void setItemStack(Function<InventoryClickEvent, ItemStack> function)
    {
        this.itemStack = function;
    }
}
