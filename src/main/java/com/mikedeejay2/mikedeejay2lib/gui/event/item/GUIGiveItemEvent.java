package com.mikedeejay2.mikedeejay2lib.gui.event.item;

import com.mikedeejay2.mikedeejay2lib.gui.GUIContainer;
import com.mikedeejay2.mikedeejay2lib.gui.event.GUIEvent;
import com.mikedeejay2.mikedeejay2lib.gui.event.GUIEventInfo;
import com.mikedeejay2.mikedeejay2lib.gui.item.GUIItem;
import com.mikedeejay2.mikedeejay2lib.item.ItemBuilder;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.function.Consumer;
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
    protected Supplier<ItemStack> itemStack;

    /**
     * Construct a new <code>GUIGiveItemEvent</code>
     *
     * @param itemStack The item that will be given to the player upon click
     */
    public GUIGiveItemEvent(ItemStack itemStack)
    {
        this.itemStack = () -> itemStack;
    }

    /**
     * Construct a new <code>GUIGiveItemEvent</code>
     *
     * @param builder The item builder that will be given to the player upon click
     */
    public GUIGiveItemEvent(ItemBuilder builder)
    {
        this.itemStack = builder::get;
    }

    /**
     * Construct a new <code>GUIGiveItemEvent</code>
     *
     * @param supplier The supplier that will generate the item that will be given to the player upon click
     */
    public GUIGiveItemEvent(Supplier<ItemStack> supplier)
    {
        this.itemStack = supplier;
    }

    /**
     * {@inheritDoc}
     *
     * @param info {@link GUIEventInfo} of the event
     */
    @Override
    public void execute(GUIEventInfo info)
    {
        Player player = info.getPlayer();
        player.getInventory().addItem(itemStack.get());
    }

    /**
     * Get the <code>ItemStack</code> that will be given to the player upon click
     *
     * @return The <code>ItemStack</code> that will be given to the player upon click
     */
    public ItemStack getItemStack()
    {
        return itemStack.get();
    }

    /**
     * Set the item that will be given to the player upon click
     *
     * @param itemStack The new <code>ItemStack</code>
     */
    public void setItemStack(ItemStack itemStack)
    {
        this.itemStack = () -> itemStack;
    }

    /**
     * Set the item that will be given to the player upon click
     *
     * @param function The function that will generate the item that will be given to the player upon click
     */
    public void setItemStack(Supplier<ItemStack> function)
    {
        this.itemStack = function;
    }
}
