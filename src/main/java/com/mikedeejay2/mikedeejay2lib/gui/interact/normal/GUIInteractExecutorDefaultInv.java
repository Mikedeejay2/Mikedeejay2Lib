package com.mikedeejay2.mikedeejay2lib.gui.interact.normal;

import com.mikedeejay2.mikedeejay2lib.gui.event.GUIClickEvent;
import com.mikedeejay2.mikedeejay2lib.gui.interact.GUIInteractExecutor;
import com.mikedeejay2.mikedeejay2lib.util.item.ItemComparison;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

/**
 * A <code>GUIInteractExecutor</code> that moves items with a custom limit specified on construction.
 *
 * @author Mikedeejay2
 */
public class GUIInteractExecutorDefaultInv implements GUIInteractExecutor {
    /**
     * The item stack limit, <code>-1</code> is default stack limit
     */
    protected int limit;

    /**
     * Construct a new <code>GUIInteractExecutorDefaultInv</code>
     *
     * @param limit The item stack limit, <code>-1</code> is default stack limit
     */
    public GUIInteractExecutorDefaultInv(int limit) {
        this.limit = Math.min(limit, 64);
    }

    /**
     * Construct a new <code>GUIInteractExecutorDefaultInv</code>
     */
    public GUIInteractExecutorDefaultInv() {
        this.limit = -1;
    }

    /**
     * {@inheritDoc}
     *
     * @param event The {@link GUIClickEvent} of the click
     */
    @Override
    public void executePickupAll(GUIClickEvent event) {
        final Player player = event.getPlayer();
        if(event.getClickedInventory() != player.getInventory()) return;
        final Inventory inventory = event.getClickedInventory();
        final int slot = event.getSlot();

        final ItemStack bottomItem = inventory.getItem(slot);
        final int maxAmount = limit == -1 ? bottomItem.getMaxStackSize() : limit;
        int curAmount = bottomItem.getAmount();

        if(curAmount > maxAmount) {
            bottomItem.setAmount(curAmount - maxAmount);
            curAmount = maxAmount;
        } else inventory.setItem(slot, null);
        ItemStack cursorItem = bottomItem.clone();
        cursorItem.setAmount(curAmount);
        player.setItemOnCursor(cursorItem);
    }

    /**
     * {@inheritDoc}
     *
     * @param event The {@link GUIClickEvent} of the click
     */
    @Override
    public void executePickupSome(GUIClickEvent event) {
        final Player player = event.getPlayer();
        if(event.getClickedInventory() != player.getInventory()) return;
        final ItemStack bottomItem = event.getClickedInventory().getItem(event.getSlot());

        final ItemStack cursorItem = player.getItemOnCursor();
        final int cursorAmount = cursorItem.getAmount();
        final int bottomAmount = bottomItem.getAmount();
        final int maxAmount = limit == -1 ? cursorItem.getMaxStackSize() : limit;

        cursorItem.setAmount(maxAmount);
        bottomItem.setAmount(bottomAmount - (maxAmount - cursorAmount));
        player.setItemOnCursor(cursorItem);
    }

    /**
     * {@inheritDoc}
     *
     * @param event The {@link GUIClickEvent} of the click
     */
    @Override
    public void executePickupHalf(GUIClickEvent event) {
        final Player player = event.getPlayer();
        if(event.getClickedInventory() != player.getInventory()) return;
        final ItemStack bottomItem = event.getClickedInventory().getItem(event.getSlot());

        final int bottomAmount = bottomItem.getAmount();
        final int maxAmount = limit == -1 ? bottomItem.getMaxStackSize() : limit;
        int halfTop = (int) Math.ceil(bottomAmount / 2.0);
        int halfBottom = (int) Math.floor(bottomAmount / 2.0);

        if(halfTop > maxAmount) {
            halfBottom += halfTop - maxAmount;
            halfTop = maxAmount;
        }
        ItemStack cursorItem = bottomItem.clone();
        cursorItem.setAmount(halfTop);
        player.setItemOnCursor(cursorItem);
        bottomItem.setAmount(halfBottom);
        player.setItemOnCursor(cursorItem);
    }

    /**
     * {@inheritDoc}
     *
     * @param event The {@link GUIClickEvent} of the click
     */
    @Override
    public void executePickupOne(GUIClickEvent event) {
        final Player player = event.getPlayer();
        if(event.getClickedInventory() != player.getInventory()) return;
        final ItemStack bottomItem = event.getClickedInventory().getItem(event.getSlot());

        ItemStack cursorItem = player.getItemOnCursor();
        final int cursorAmount = cursorItem.getAmount();
        final int bottomAmount = bottomItem.getAmount();

        if(cursorItem.getType() == Material.AIR) {
            cursorItem = bottomItem.clone();
            cursorItem.setAmount(1);
            player.setItemOnCursor(cursorItem);
        } else {
            cursorItem.setAmount(cursorAmount + 1);
        }
        bottomItem.setAmount(bottomAmount - 1);
        player.setItemOnCursor(cursorItem);
    }

    /**
     * {@inheritDoc}
     *
     * @param event The {@link GUIClickEvent} of the click
     */
    @Override
    public void executePlaceAll(GUIClickEvent event) {
        final Player player = event.getPlayer();
        if(event.getClickedInventory() != player.getInventory()) return;
        final Inventory inventory = event.getClickedInventory();
        final int slot = event.getSlot();

        final ItemStack cursorItem = player.getItemOnCursor();
        final int cursorAmount = cursorItem.getAmount();
        final ItemStack curItem = inventory.getItem(slot);
        int bottomAmount = 0;
        if(curItem != null) bottomAmount = curItem.getAmount();
        final ItemStack bottomItem = cursorItem.clone();
        int extraAmount = 0;
        int newAmount = cursorAmount + bottomAmount;
        final int maxAmount = limit == -1 ? cursorItem.getMaxStackSize() : limit;

        if(newAmount > maxAmount) {
            extraAmount = newAmount - maxAmount;
            newAmount = maxAmount;
        }
        bottomItem.setAmount(newAmount);
        cursorItem.setAmount(extraAmount);
        inventory.setItem(slot, bottomItem);
        player.setItemOnCursor(cursorItem);
    }

    /**
     * {@inheritDoc}
     *
     * @param event The {@link GUIClickEvent} of the click
     */
    @Override
    public void executePlaceSome(GUIClickEvent event) {
        final Player player = event.getPlayer();
        if(event.getClickedInventory() != player.getInventory()) return;
        ItemStack bottomItem = event.getClickedInventory().getItem(event.getSlot());

        final ItemStack cursorItem = player.getItemOnCursor();
        final int cursorAmount = cursorItem.getAmount();
        final int bottomAmount = bottomItem.getAmount();
        final int maxAmount = limit == -1 ? cursorItem.getMaxStackSize() : limit;

        cursorItem.setAmount(bottomAmount - (maxAmount - cursorAmount));
        bottomItem.setAmount(maxAmount);
        player.setItemOnCursor(cursorItem);
    }

    /**
     * {@inheritDoc}
     *
     * @param event The {@link GUIClickEvent} of the click
     */
    @Override
    public void executePlaceOne(GUIClickEvent event) {
        final Player player = event.getPlayer();
        if(event.getClickedInventory() != player.getInventory()) return;
        final Inventory inventory = event.getClickedInventory();
        final int slot = event.getSlot();

        final ItemStack cursorItem = player.getItemOnCursor();
        final int cursorAmount = cursorItem.getAmount();
        ItemStack bottomItem = inventory.getItem(slot);

        if(bottomItem == null) {
            bottomItem = cursorItem.clone();
            bottomItem.setAmount(1);
            inventory.setItem(slot, bottomItem);
        } else {
            bottomItem.setAmount(bottomItem.getAmount() + 1);
        }
        cursorItem.setAmount(cursorAmount - 1);
        player.setItemOnCursor(cursorItem);
    }

    /**
     * {@inheritDoc}
     *
     * @param event The {@link GUIClickEvent} of the click
     */
    @Override
    public void executeSwapWithCursor(GUIClickEvent event) {
        final Player player = event.getPlayer();
        if(event.getClickedInventory() != player.getInventory()) return;
        final Inventory inventory = event.getClickedInventory();
        final int slot = event.getSlot();

        final ItemStack cursorItem = player.getItemOnCursor();
        final int maxAmountCursor = limit == -1 ? cursorItem.getAmount() : limit;
        final ItemStack bottomItem = inventory.getItem(slot);
        final int maxAmountBottom = limit == -1 ? bottomItem.getAmount() : limit;

        if(cursorItem.getAmount() > maxAmountCursor || bottomItem.getAmount() > maxAmountBottom) return;
        inventory.setItem(slot, cursorItem);
        player.setItemOnCursor(bottomItem);
    }

    /**
     * {@inheritDoc}
     *
     * @param event The {@link GUIClickEvent} of the click
     */
    @Override
    public void executeDropAllCursor(GUIClickEvent event) {
        final Player player = event.getPlayer();

        final ItemStack cursorItem = player.getItemOnCursor();
        final Location location = player.getEyeLocation();
        final World world = location.getWorld();
        final ItemStack itemToDrop = cursorItem.clone();
        final int curAmount = cursorItem.getAmount();
        final int maxAmount = limit == -1 ? cursorItem.getMaxStackSize() : limit;

        if(curAmount > maxAmount) {
            itemToDrop.setAmount(maxAmount);
            cursorItem.setAmount(curAmount - maxAmount);
        } else {
            player.setItemOnCursor(null);
        }
        Item item = world.dropItem(location, itemToDrop);
        item.setVelocity(location.getDirection().multiply(1.0 / 3.0));
    }

    /**
     * {@inheritDoc}
     *
     * @param event The {@link GUIClickEvent} of the click
     */
    @Override
    public void executeDropOneCursor(GUIClickEvent event) {
        final Player player = event.getPlayer();

        final ItemStack cursorItem = player.getItemOnCursor();
        final ItemStack itemToDrop = cursorItem.clone();
        final Location location = player.getEyeLocation();
        final World world = location.getWorld();

        itemToDrop.setAmount(1);
        cursorItem.setAmount(cursorItem.getAmount() - 1);
        Item item = world.dropItem(location, itemToDrop);
        item.setVelocity(location.getDirection().multiply(1.0 / 3.0));
        player.setItemOnCursor(cursorItem);
    }

    /**
     * {@inheritDoc}
     *
     * @param event The {@link GUIClickEvent} of the click
     */
    @Override
    public void executeDropAllSlot(GUIClickEvent event) {
        final Player player = event.getPlayer();
        if(event.getClickedInventory() != player.getInventory()) return;
        final Inventory inventory = event.getClickedInventory();
        final int slot = event.getSlot();

        final ItemStack stack = inventory.getItem(slot);
        final int curAmount = stack.getAmount();
        final int maxAmount = limit == -1 ? stack.getMaxStackSize() : limit;
        final Location location = player.getEyeLocation();
        final World world = location.getWorld();
        int itemToDropAmount = curAmount;

        if(curAmount > maxAmount) {
            stack.setAmount(curAmount - maxAmount);
            itemToDropAmount = maxAmount;
        } else inventory.setItem(slot, null);
        ItemStack itemToDrop = stack.clone();
        itemToDrop.setAmount(itemToDropAmount);
        Item item = world.dropItem(location, itemToDrop);
        item.setVelocity(location.getDirection().multiply(1.0 / 3.0));
    }

    /**
     * {@inheritDoc}
     *
     * @param event The {@link GUIClickEvent} of the click
     */
    @Override
    public void executeDropOneSlot(GUIClickEvent event) {
        final Player player = event.getPlayer();
        if(event.getClickedInventory() != player.getInventory()) return;
        final Inventory inventory = event.getClickedInventory();
        final int slot = event.getSlot();

        final ItemStack origItem = inventory.getItem(slot);
        final ItemStack itemToDrop = origItem.clone();
        final Location location = player.getEyeLocation();
        final World world = location.getWorld();

        itemToDrop.setAmount(1);
        origItem.setAmount(origItem.getAmount() - 1);
        Item item = world.dropItem(location, itemToDrop);
        item.setVelocity(location.getDirection().multiply(1.0 / 3.0));
    }

    /**
     * {@inheritDoc}
     *
     * @param event The {@link GUIClickEvent} of the click
     */
    @Override
    public void executeHotbarSwap(GUIClickEvent event) {
        final Player player = event.getPlayer();
        if(event.getClickedInventory() != player.getInventory()) return;
        final int hotbarSlot = event.getHotbarButton();
        if(hotbarSlot < 0) return;
        final int slot = event.getSlot();

        final Inventory playerInv = player.getInventory();
        final ItemStack curItem = playerInv.getItem(hotbarSlot);
        final ItemStack item = playerInv.getItem(slot);

        playerInv.setItem(hotbarSlot, item);
        playerInv.setItem(slot, curItem);
    }

    /**
     * {@inheritDoc}
     *
     * @param event The {@link GUIClickEvent} of the click
     */
    @Override
    public void executeCloneStack(GUIClickEvent event) {
        final Player player = event.getPlayer();
        if(event.getClickedInventory() != player.getInventory()) return;
        final Inventory playerInv = player.getInventory();
        ItemStack item = playerInv.getItem(event.getSlot());
        if(item == null) return;
        item = item.clone();
        final int maxAmount = limit == -1 ? item.getMaxStackSize() : limit;

        item.setAmount(maxAmount);
        player.setItemOnCursor(item);
    }

    /**
     * {@inheritDoc}
     *
     * @param event The {@link GUIClickEvent} of the click
     */
    @Override
    public void executeCollectToCursor(GUIClickEvent event) {
        final Player player = event.getPlayer();

        final ItemStack cursorItem = player.getItemOnCursor();
        final Inventory playerInv = player.getInventory();
        final int maxAmount = limit == -1 ? cursorItem.getMaxStackSize() : limit;

        if(cursorItem.getAmount() >= maxAmount) return;
        for(int amount = 1; amount <= maxAmount; ++amount) {
            for(int i = 0; i < playerInv.getStorageContents().length; ++i) {
                ItemStack curItem = playerInv.getItem(i);
                if(curItem == null) continue;
                if(curItem.getAmount() != amount) continue;
                if(!ItemComparison.equalsEachOther(cursorItem, curItem)) continue;
                int newAmount = curItem.getAmount() + cursorItem.getAmount();
                int extraAmount = 0;
                if(newAmount > maxAmount) {
                    extraAmount = newAmount - maxAmount;
                    newAmount = maxAmount;
                }
                cursorItem.setAmount(newAmount);
                curItem.setAmount(extraAmount);
            }
        }
    }

    /**
     * Not an inventory execution, therefore not implemented.
     *
     * @param event The {@link GUIClickEvent} of the click
     */
    @Override
    public void executeNothing(GUIClickEvent event) {}

    /**
     * Not an inventory execution, therefore not implemented.
     *
     * @param event The {@link GUIClickEvent} of the click
     */
    @Override
    public void executeMoveToOtherInventory(GUIClickEvent event) {}

    /**
     * Not an inventory execution, therefore not implemented.
     *
     * @param event The {@link GUIClickEvent} of the click
     */
    @Override
    public void executeHotbarMoveAndReadd(GUIClickEvent event) {}

    /**
     * Not an inventory execution, therefore not implemented.
     *
     * @param event The {@link GUIClickEvent} of the click
     */
    @Override
    public void executeUnknown(GUIClickEvent event) {}

    /**
     * Get the item stack limit, <code>-1</code> is default stack limit
     *
     * @return The item stack limit
     */
    public int getLimit() {
        return limit;
    }

    /**
     * Set the item stack limit, <code>-1</code> is default stack limit
     *
     * @param limit The new item stack limit
     */
    public void setLimit(int limit) {
        this.limit = limit;
    }
}
