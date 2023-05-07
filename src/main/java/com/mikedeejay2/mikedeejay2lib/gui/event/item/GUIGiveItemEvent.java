package com.mikedeejay2.mikedeejay2lib.gui.event.item;

import com.mikedeejay2.mikedeejay2lib.gui.event.GUIClickEvent;
import com.mikedeejay2.mikedeejay2lib.gui.event.util.GUIAbstractClickEvent;
import com.mikedeejay2.mikedeejay2lib.item.ItemBuilder;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import java.util.function.Function;

/**
 * Give the <code>Player</code> viewing the GUI a specified item
 *
 * @author Mikedeejay2
 */
public class GUIGiveItemEvent extends GUIAbstractClickEvent {
    /**
     * The item that will be given to the player upon click
     */
    protected Function<Player, ItemStack> itemStack;

    /**
     * Construct a new <code>GUIGiveItemEvent</code>
     *
     * @param itemStack The item that will be given to the player upon click
     * @param acceptedClicks The list of {@link ClickType ClickTypes} to accept
     */
    public GUIGiveItemEvent(ItemStack itemStack, ClickType... acceptedClicks) {
        super(acceptedClicks);
        this.itemStack = (player) -> itemStack;
    }

    /**
     * Construct a new <code>GUIGiveItemEvent</code>
     *
     * @param itemStack The item that will be given to the player upon click
     */
    public GUIGiveItemEvent(ItemStack itemStack) {
        super();
        this.itemStack = (player) -> itemStack;
    }

    /**
     * Construct a new <code>GUIGiveItemEvent</code>
     *
     * @param builder The item builder that will be given to the player upon click
     */
    public GUIGiveItemEvent(ItemBuilder builder) {
        this.itemStack = (player) -> player == null ? builder.get() : builder.get(player);
    }

    /**
     * Construct a new <code>GUIGiveItemEvent</code>
     *
     * @param function The function that will generate the item that will be given to the player upon click
     */
    public GUIGiveItemEvent(Function<Player, ItemStack> function) {
        this.itemStack = function;
    }

    @Override
    protected void executeClick(GUIClickEvent info) {
        Player player = info.getPlayer();
        player.getInventory().addItem(itemStack.apply(player));
    }

    /**
     * Get the <code>ItemStack</code> that will be given to the player upon click
     *
     * @param player The player retrieving the item
     * @return The <code>ItemStack</code> that will be given to the player upon click
     */
    public ItemStack getItemStack(Player player) {
        return itemStack.apply(player);
    }

    /**
     * Get the <code>ItemStack</code> that will be given to the player upon click
     *
     * @return The <code>ItemStack</code> that will be given to the player upon click
     */
    public ItemStack getItemStack() {
        return itemStack.apply(null);
    }

    /**
     * Set the item that will be given to the player upon click
     *
     * @param itemStack The new <code>ItemStack</code>
     */
    public void setItemStack(ItemStack itemStack) {
        this.itemStack = (player) -> itemStack;
    }

    /**
     * Set the item that will be given to the player upon click
     *
     * @param function The function that will generate the item that will be given to the player upon click
     */
    public void setItemStack(Function<Player, ItemStack> function) {
        this.itemStack = function;
    }
}
