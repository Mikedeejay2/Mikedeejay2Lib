package com.mikedeejay2.mikedeejay2lib.gui.event.item;

import com.mikedeejay2.mikedeejay2lib.gui.event.GUIEvent;
import com.mikedeejay2.mikedeejay2lib.gui.event.GUIEventInfo;
import com.mikedeejay2.mikedeejay2lib.item.ItemBuilder;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;

import java.util.function.Supplier;

/**
 * Copy an item to the player's cursor on click
 *
 * @author Mikedeejay2
 */
public class GUICreativeMoveEvent implements GUIEvent {
    /**
     * The item that will be copied to the player's cursor upon click
     */
    protected Supplier<ItemStack> itemStack;

    /**
     * Construct a new <code>GUIGiveItemEvent</code>
     *
     * @param itemStack The item that will be copied to the player's cursor upon click
     */
    public GUICreativeMoveEvent(ItemStack itemStack) {
        this.itemStack = () -> itemStack;
    }

    /**
     * Construct a new <code>GUIGiveItemEvent</code>
     *
     * @param builder The item that will be copied to the player's cursor upon click
     */
    public GUICreativeMoveEvent(ItemBuilder builder) {
        this.itemStack = builder::get;
    }

    /**
     * Construct a new <code>GUIGiveItemEvent</code>
     *
     * @param supplier The supplier that will generate the item that will be copied to the player's cursor upon click
     */
    public GUICreativeMoveEvent(Supplier<ItemStack> supplier) {
        this.itemStack = supplier;
    }

    /**
     * {@inheritDoc}
     *
     * @param info {@link GUIEventInfo} of the event
     */
    @Override
    public void execute(GUIEventInfo info) {
        Player player = info.getPlayer();
        InventoryView inventory = player.getOpenInventory();
        ItemStack item = itemStack.get().clone();
        switch(info.getAction()) {
            case PICKUP_ALL:
                inventory.setCursor(item);
                break;
            case PICKUP_HALF:
                item.setAmount((int) Math.ceil(item.getAmount() / 2.0));
                inventory.setCursor(item);
                break;
            case PICKUP_ONE:
                item.setAmount(1);
                inventory.setCursor(item);
                break;
            case CLONE_STACK:
            case PICKUP_SOME:
            case MOVE_TO_OTHER_INVENTORY:
                item.setAmount(item.getMaxStackSize());
                inventory.setCursor(item);
                break;
            case HOTBAR_MOVE_AND_READD:
            case HOTBAR_SWAP:
                item.setAmount(item.getMaxStackSize());
                inventory.getBottomInventory().setItem(info.getHotbarButton(), item);
                break;
            case DROP_ALL_SLOT: {
                item.setAmount(item.getMaxStackSize());
                Location location = player.getEyeLocation();
                World world = location.getWorld();
                Item droppedItem = world.dropItem(location, item);
                droppedItem.setVelocity(location.getDirection().multiply(1.0 / 3.0));
                break;
            }
            case DROP_ONE_SLOT: {
                item.setAmount(1);
                Location location = player.getEyeLocation();
                World world = location.getWorld();
                Item droppedItem = world.dropItem(location, item);
                droppedItem.setVelocity(location.getDirection().multiply(1.0 / 3.0));
                break;
            }
            default:
                inventory.setCursor(null);
                break;
        }
    }

    /**
     * Get the <code>ItemStack</code> that will copied to the player's cursor upon click
     *
     * @return The <code>ItemStack</code> that will be copied to the player's cursor upon click
     */
    public ItemStack getItemStack() {
        return itemStack.get();
    }

    /**
     * Set the item that will be copied to the player's cursor upon click
     *
     * @param itemStack The new <code>ItemStack</code>
     */
    public void setItemStack(ItemStack itemStack) {
        this.itemStack = () -> itemStack;
    }

    /**
     * Set the item that will be copied to the player's cursor upon click
     *
     * @param function The function that will generate the item that will be copied to the player's cursor upon click
     */
    public void setItemStack(Supplier<ItemStack> function) {
        this.itemStack = function;
    }
}
