package com.mikedeejay2.mikedeejay2lib.gui.interact.normal;

import com.mikedeejay2.mikedeejay2lib.gui.GUIContainer;
import com.mikedeejay2.mikedeejay2lib.gui.GUILayer;
import com.mikedeejay2.mikedeejay2lib.gui.event.GUIClickEvent;
import com.mikedeejay2.mikedeejay2lib.gui.interact.GUIInteractExecutor;
import com.mikedeejay2.mikedeejay2lib.gui.interact.GUIInteractType;
import com.mikedeejay2.mikedeejay2lib.gui.item.GUIItem;
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
public class GUIInteractExecutorDefaultGUI implements GUIInteractExecutor {
    /**
     * The {@link GUIInteractType} of this interact executor
     */
    protected GUIInteractType interactType;

    /**
     * The item stack limit, <code>-1</code> is default stack limit
     */
    protected int limit;

    /**
     * Whether to consume the items when moving them
     */
    protected boolean consume;

    /**
     * Construct a new <code>GUIInteractExecutorDefaultGUI</code>
     *
     * @param limit The item stack limit, <code>-1</code> is default stack limit
     */
    public GUIInteractExecutorDefaultGUI(int limit) {
        this.limit = Math.min(limit, 64);
        this.interactType = GUIInteractType.DEFAULT;
        this.consume = true;
    }

    /**
     * Construct a new <code>GUIInteractExecutorDefaultGUI</code>
     */
    public GUIInteractExecutorDefaultGUI() {
        this.limit = -1;
        this.interactType = GUIInteractType.DEFAULT;
        this.consume = true;
    }

    /**
     * Construct a new <code>GUIInteractExecutorDefaultGUI</code>
     *
     * @param limit   The item stack limit, <code>-1</code> is default stack limit
     * @param consume Whether to consume the items when moving them
     */
    public GUIInteractExecutorDefaultGUI(int limit, boolean consume) {
        this.limit = Math.min(limit, 64);
        this.interactType = GUIInteractType.DEFAULT;
        this.consume = consume;
    }

    /**
     * Construct a new <code>GUIInteractExecutorDefaultGUI</code>
     *
     * @param consume Whether to consume the items when moving them
     */
    public GUIInteractExecutorDefaultGUI(boolean consume) {
        this.limit = -1;
        this.interactType = GUIInteractType.DEFAULT;
        this.consume = consume;
    }

    /**
     * Construct a new <code>GUIInteractExecutorDefaultGUI</code>
     *
     * @param type  The {@link GUIInteractType} of this interact executor
     * @param limit The item stack limit, <code>-1</code> is default stack limit
     */
    public GUIInteractExecutorDefaultGUI(GUIInteractType type, int limit) {
        this.limit = Math.min(limit, 64);
        this.interactType = type;
        this.consume = true;
    }

    /**
     * Construct a new <code>GUIInteractExecutorDefaultGUI</code>
     *
     * @param type The {@link GUIInteractType} of this interact executor
     */
    public GUIInteractExecutorDefaultGUI(GUIInteractType type) {
        this.limit = -1;
        this.interactType = type;
        this.consume = true;
    }

    /**
     * Construct a new <code>GUIInteractExecutorDefaultGUI</code>
     *
     * @param type    The {@link GUIInteractType} of this interact executor
     * @param limit   The item stack limit, <code>-1</code> is default stack limit
     * @param consume Whether to consume the items when moving them
     */
    public GUIInteractExecutorDefaultGUI(GUIInteractType type, int limit, boolean consume) {
        this.limit = Math.min(limit, 64);
        this.interactType = type;
        this.consume = consume;
    }

    /**
     * Construct a new <code>GUIInteractExecutorDefaultGUI</code>
     *
     * @param type    The {@link GUIInteractType} of this interact executor
     * @param consume Whether to consume the items when moving them into the list
     */
    public GUIInteractExecutorDefaultGUI(GUIInteractType type, boolean consume) {
        this.limit = -1;
        this.interactType = type;
        this.consume = consume;
    }

    /**
     * {@inheritDoc}
     *
     * @param event The {@link GUIClickEvent} of the click
     */
    @Override
    public void executePickupAll(GUIClickEvent event) {
        final GUIContainer gui = event.getGUI();
        if(event.getClickedInventory() != gui.getInventory()) return;
        final Player player = event.getPlayer();
        final GUILayer layer = event.getLayer();
        final int row = event.getRow();
        final int col = event.getColumn();

        final GUIItem guiItem = layer.getItem(row, col);
        final ItemStack bottomItem = guiItem.get(player);
        final int maxAmount = limit == -1 ? bottomItem.getMaxStackSize() : limit;
        int curAmount = bottomItem.getAmount();

        if(curAmount > maxAmount) {
            guiItem.setAmount(curAmount - maxAmount);
            curAmount = maxAmount;
        } else layer.removeItem(row, col);
        if(consume) {
            ItemStack cursorItem = bottomItem.clone();
            cursorItem.setAmount(curAmount);
            player.setItemOnCursor(cursorItem);
        }
    }

    /**
     * {@inheritDoc}
     *
     * @param event The {@link GUIClickEvent} of the click
     */
    @Override
    public void executePickupSome(GUIClickEvent event) {
        final GUIContainer gui = event.getGUI();
        if(event.getClickedInventory() != gui.getInventory()) return;
        final Player player = event.getPlayer();
        final GUIItem guiItem = event.getLayer().getItem(event.getRow(), event.getColumn());

        final ItemStack cursorItem = player.getItemOnCursor();
        final int cursorAmount = cursorItem.getAmount();
        final ItemStack bottomItem = guiItem.get(player);
        final int bottomAmount = bottomItem.getAmount();
        final int maxAmount = limit == -1 ? cursorItem.getMaxStackSize() : limit;

        if(consume) {
            cursorItem.setAmount(maxAmount);
            guiItem.setAmount(bottomAmount - (maxAmount - cursorAmount));
            player.setItemOnCursor(cursorItem);
        }
    }

    /**
     * {@inheritDoc}
     *
     * @param event The {@link GUIClickEvent} of the click
     */
    @Override
    public void executePickupHalf(GUIClickEvent event) {
        final GUIContainer gui = event.getGUI();
        if(event.getClickedInventory() != gui.getInventory()) return;
        final Player player = event.getPlayer();
        final GUIItem guiItem = event.getLayer().getItem(event.getRow(), event.getColumn());

        final ItemStack bottomItem = guiItem.get(player);
        final int bottomAmount = bottomItem.getAmount();
        final int maxAmount = limit == -1 ? bottomItem.getMaxStackSize() : limit;
        int halfTop = (int) Math.ceil(bottomAmount / 2.0);
        int halfBottom = (int) Math.floor(bottomAmount / 2.0);

        if(halfTop > maxAmount) {
            halfBottom += halfTop - maxAmount;
            halfTop = maxAmount;
        }
        if(consume) {
            ItemStack cursorItem = bottomItem.clone();
            cursorItem.setAmount(halfTop);
            player.setItemOnCursor(cursorItem);
        }
        guiItem.setAmount(halfBottom);
    }

    /**
     * {@inheritDoc}
     *
     * @param event The {@link GUIClickEvent} of the click
     */
    @Override
    public void executePickupOne(GUIClickEvent event) {
        final GUIContainer gui = event.getGUI();
        if(event.getClickedInventory() != gui.getInventory()) return;
        final Player player = event.getPlayer();
        final GUIItem guiItem = event.getLayer().getItem(event.getRow(), event.getColumn());

        ItemStack cursorItem = player.getItemOnCursor();
        final int cursorAmount = cursorItem.getAmount();
        final ItemStack bottomItem = guiItem.get(player);
        final int bottomAmount = bottomItem.getAmount();

        if(cursorItem.getType() == Material.AIR && consume) {
            cursorItem = bottomItem.clone();
            cursorItem.setAmount(1);
        } else if(consume) {
            cursorItem.setAmount(cursorAmount + 1);
        }
        guiItem.setAmount(bottomAmount - 1);
        if(consume) player.setItemOnCursor(cursorItem);
    }

    /**
     * {@inheritDoc}
     *
     * @param event The {@link GUIClickEvent} of the click
     */
    @Override
    public void executePlaceAll(GUIClickEvent event) {
        final GUIContainer gui = event.getGUI();
        if(event.getClickedInventory() != gui.getInventory()) return;
        final Player player = event.getPlayer();
        final GUILayer layer = event.getLayer();
        final int row = event.getRow();
        final int col = event.getColumn();
        final GUIItem curItem = layer.getItem(row, col);

        final ItemStack cursorItem = player.getItemOnCursor();
        final int cursorAmount = cursorItem.getAmount();
        int bottomAmount = 0;
        if(curItem != null) {
            bottomAmount = curItem.getAmount();
        } else {
            if(interactType == GUIInteractType.SINGLE_ITEM && layer.containsItem(cursorItem)) return;
            if(interactType == GUIInteractType.SINGLE_MATERIAL && layer.containsMaterial(cursorItem.getType())) return;
        }
        final GUIItem newItem = new GUIItem(cursorItem.clone());
        newItem.setMovable(true);
        final int curAmount = cursorAmount + bottomAmount;
        final int maxAmount = limit == -1 ? cursorItem.getMaxStackSize() : limit;
        int newAmount = curAmount;
        int extraAmount = 0;

        if(curAmount > maxAmount) {
            extraAmount = curAmount - maxAmount;
            newAmount = maxAmount;
        }
        newItem.setAmount(newAmount);
        layer.setItem(row, col, newItem);
        if(consume) {
            cursorItem.setAmount(extraAmount);
            player.setItemOnCursor(cursorItem);
        }
    }

    /**
     * {@inheritDoc}
     *
     * @param event The {@link GUIClickEvent} of the click
     */
    @Override
    public void executePlaceSome(GUIClickEvent event) {
        final GUIContainer gui = event.getGUI();
        if(event.getClickedInventory() != gui.getInventory()) return;
        final Player player = event.getPlayer();
        final GUILayer layer = event.getLayer();
        final GUIItem guiItem = layer.getItem(event.getRow(), event.getColumn());

        final ItemStack cursorItem = player.getItemOnCursor();
        if(interactType == GUIInteractType.SINGLE_ITEM && layer.containsItem(cursorItem)) return;
        if(interactType == GUIInteractType.SINGLE_MATERIAL && layer.containsMaterial(cursorItem.getType())) return;
        final int cursorAmount = cursorItem.getAmount();
        final ItemStack bottomItem = guiItem.get(player);
        final int bottomAmount = bottomItem.getAmount();
        final int maxAmount = limit == -1 ? cursorItem.getMaxStackSize() : limit;

        guiItem.setAmount(maxAmount);
        if(consume) {
            cursorItem.setAmount(bottomAmount - (maxAmount - cursorAmount));
            player.setItemOnCursor(cursorItem);
        }
    }

    /**
     * {@inheritDoc}
     *
     * @param event The {@link GUIClickEvent} of the click
     */
    @Override
    public void executePlaceOne(GUIClickEvent event) {
        final GUIContainer gui = event.getGUI();
        if(event.getClickedInventory() != gui.getInventory()) return;
        final Player player = event.getPlayer();
        final GUILayer layer = event.getLayer();
        final int row = event.getRow();
        final int col = event.getColumn();

        final ItemStack cursorItem = player.getItemOnCursor();
        final int cursorAmount = cursorItem.getAmount();
        GUIItem guiItem = layer.getItem(row, col);

        if(guiItem == null) {
            if(interactType == GUIInteractType.SINGLE_ITEM && layer.containsItem(cursorItem)) return;
            if(interactType == GUIInteractType.SINGLE_MATERIAL && layer.containsMaterial(cursorItem.getType())) return;
            guiItem = new GUIItem(cursorItem.clone());
            guiItem.setMovable(true);
            guiItem.setAmount(1);
            layer.setItem(row, col, guiItem);
        } else {
            int maxAmount = limit == -1 ? guiItem.get(player).getMaxStackSize() : limit;
            if(guiItem.getAmount() >= maxAmount) return;
            guiItem.setAmount(guiItem.getAmount() + 1);
        }
        if(consume) {
            cursorItem.setAmount(cursorAmount - 1);
            player.setItemOnCursor(cursorItem);
        }
    }

    /**
     * {@inheritDoc}
     *
     * @param event The {@link GUIClickEvent} of the click
     */
    @Override
    public void executeSwapWithCursor(GUIClickEvent event) {
        final GUIContainer gui = event.getGUI();
        if(event.getClickedInventory() != gui.getInventory()) return;
        final Player player = event.getPlayer();
        final GUILayer layer = event.getLayer();
        final GUIItem guiItem = layer.getItem(event.getRow(), event.getColumn());

        final ItemStack cursorItem = player.getItemOnCursor();
        if(interactType == GUIInteractType.SINGLE_ITEM && layer.containsItem(cursorItem)) return;
        if(interactType == GUIInteractType.SINGLE_MATERIAL && layer.containsMaterial(cursorItem.getType())) return;
        final int maxAmount = limit == -1 ? cursorItem.getMaxStackSize() : limit;
        if(cursorItem.getAmount() > maxAmount) return;
        final ItemStack bottomItem = guiItem.get(player);

        guiItem.set(consume ? cursorItem : cursorItem.clone());
        if(consume) player.setItemOnCursor(bottomItem);
    }

    /**
     * {@inheritDoc}
     *
     * @param event The {@link GUIClickEvent} of the click
     */
    @Override
    public void executeDropAllSlot(GUIClickEvent event) {
        final GUIContainer gui = event.getGUI();
        if(event.getClickedInventory() != gui.getInventory()) return;
        final Player player = event.getPlayer();
        final GUILayer layer = event.getLayer();
        final int row = event.getRow();
        final int col = event.getColumn();

        final GUIItem guiItem = layer.getItem(row, col);
        final ItemStack stack = guiItem.get(player);
        final int curAmount = stack.getAmount();
        final int maxAmount = limit == -1 ? stack.getMaxStackSize() : limit;
        final Location location = player.getEyeLocation();
        final World world = location.getWorld();
        int itemToDropAmount = curAmount;

        if(curAmount > maxAmount) {
            guiItem.setAmount(curAmount - maxAmount);
            itemToDropAmount = maxAmount;
        } else layer.removeItem(row, col);
        final ItemStack itemToDrop = stack.clone();
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
        final GUIContainer gui = event.getGUI();
        if(event.getClickedInventory() != gui.getInventory()) return;
        final Player player = event.getPlayer();
        final GUIItem guiItem = event.getLayer().getItem(event.getRow(), event.getColumn());
        final ItemStack itemToDrop = guiItem.get(player).clone();
        final Location location = player.getEyeLocation();
        final World world = location.getWorld();

        itemToDrop.setAmount(1);
        guiItem.setAmount(guiItem.getAmount() - 1);
        Item item = world.dropItem(location, itemToDrop);
        item.setVelocity(location.getDirection().multiply(1.0 / 3.0));
    }

    /**
     * {@inheritDoc}
     *
     * @param event The {@link GUIClickEvent} of the click
     */
    @Override
    public void executeMoveToOtherInventory(GUIClickEvent event) {
        final Inventory inventory = event.getClickedInventory();
        final GUILayer layer = event.getLayer();
        final Player player = event.getPlayer();
        if(inventory == event.getGUI().getInventory()) {
            final int row = event.getRow();
            final int col = event.getColumn();

            final GUIItem guiItem = layer.getItem(row, col);
            final ItemStack itemToMove = guiItem.get(player);
            final Inventory playerInv = player.getInventory();
            if(!consume) {
                layer.removeItem(row, col);
                return;
            }
            for(int i = 0; i < playerInv.getStorageContents().length; ++i) {
                ItemStack curItem = playerInv.getItem(i);
                if(curItem == null) continue;
                int maxAmount = limit == -1 ? curItem.getMaxStackSize() : limit;
                if(curItem.getAmount() >= maxAmount) continue;
                if(!ItemComparison.equalsEachOther(curItem, itemToMove)) continue;
                int newAmount = curItem.getAmount() + itemToMove.getAmount();
                int extraAmount = 0;
                if(newAmount > maxAmount) {
                    extraAmount = newAmount - maxAmount;
                    newAmount = maxAmount;
                }
                itemToMove.setAmount(extraAmount);
                curItem.setAmount(newAmount);
                if(itemToMove.getAmount() <= 0) {
                    layer.removeItem(row, col);
                    return;
                }
            }
            if(itemToMove.getAmount() <= 0) {
                layer.removeItem(row, col);
                return;
            }
            for(int i = 0; i < playerInv.getStorageContents().length; ++i) {
                ItemStack curItem = playerInv.getItem(i);
                if(curItem != null) continue;
                curItem = itemToMove.clone();
                int newAmount = itemToMove.getAmount();
                int extraAmount = 0;
                final int maxAmount = limit == -1 ? itemToMove.getMaxStackSize() : limit;
                if(newAmount > maxAmount) {
                    extraAmount = newAmount - maxAmount;
                    newAmount = maxAmount;
                }
                itemToMove.setAmount(extraAmount);
                curItem.setAmount(newAmount);
                playerInv.setItem(i, curItem);
                if(itemToMove.getAmount() <= 0) {
                    layer.removeItem(row, col);
                    return;
                }
            }
        } else {
            final ItemStack itemToMove = inventory.getItem(event.getSlot());
            int itemToMoveAmt = itemToMove.getAmount();
            for(int row = 1; row <= layer.getRows(); ++row) {
                for(int col = 1; col <= layer.getCols(); ++col) {
                    final GUIItem curGUIItem = layer.getItem(row, col);
                    final ItemStack curItem = curGUIItem == null ? null : curGUIItem.get(player);
                    if(curItem == null || !curGUIItem.isMovable()) continue;
                    if(!ItemComparison.equalsEachOther(curItem, itemToMove)) continue;
                    int newAmount = curGUIItem.getAmount() + itemToMoveAmt;
                    int extraAmount = 0;
                    final int maxAmount = limit == -1 ? curItem.getMaxStackSize() : limit;
                    if(newAmount > maxAmount) {
                        extraAmount = newAmount - maxAmount;
                        newAmount = maxAmount;
                    }
                    if(consume) itemToMove.setAmount(extraAmount);
                    itemToMoveAmt = extraAmount;
                    curGUIItem.setAmount(newAmount);
                    if(itemToMoveAmt <= 0) return;
                }
            }
            if(itemToMoveAmt <= 0 || !layer.getDefaultMoveState()) return;
            if(interactType == GUIInteractType.SINGLE_ITEM && layer.containsItem(itemToMove)) return;
            if(interactType == GUIInteractType.SINGLE_MATERIAL && layer.containsMaterial(itemToMove.getType())) return;
            for(int row = 1; row <= layer.getRows(); ++row) {
                for(int col = 1; col <= layer.getCols(); ++col) {
                    GUIItem curGUIItem = layer.getItem(row, col);
                    final ItemStack curItem = curGUIItem == null ? null : curGUIItem.get(player);
                    if(curItem != null || (curGUIItem != null && !curGUIItem.isMovable())) continue;
                    if(curGUIItem == null) {
                        curGUIItem = new GUIItem(itemToMove.clone());
                        curGUIItem.setAmount(itemToMoveAmt);
                        curGUIItem.setMovable(true);
                        layer.setItem(row, col, curGUIItem);
                    }
                    int newAmount = itemToMoveAmt;
                    int extraAmount = 0;
                    final int maxAmount = limit == -1 ? itemToMove.getMaxStackSize() : limit;
                    if(newAmount > maxAmount) {
                        extraAmount = newAmount - maxAmount;
                        newAmount = maxAmount;
                    }
                    if(consume) itemToMove.setAmount(extraAmount);
                    itemToMoveAmt = extraAmount;
                    curGUIItem.setAmount(newAmount);
                    if(itemToMoveAmt <= 0) return;
                }
            }
        }
    }

    /**
     * {@inheritDoc}
     *
     * @param event The {@link GUIClickEvent} of the click
     */
    @Override
    public void executeHotbarMoveAndReadd(GUIClickEvent event) {
        final Player player = event.getPlayer();
        final GUILayer layer = event.getLayer();
        final int row = event.getRow();
        final int col = event.getColumn();
        final int hotbarSlot = event.getHotbarButton();

        final Inventory playerInv = player.getInventory();
        final ItemStack hotbarItem = playerInv.getItem(hotbarSlot);
        if(interactType == GUIInteractType.SINGLE_ITEM && layer.containsItem(hotbarItem)) return;
        if(interactType == GUIInteractType.SINGLE_MATERIAL && layer.containsMaterial(hotbarItem.getType())) return;
        final GUIItem guiItem = layer.getItem(row, col);
        final ItemStack topItem = guiItem.get(player);
        final int maxAmount = limit == -1 ? hotbarItem.getMaxStackSize() : limit;

        if(hotbarItem.getAmount() > maxAmount) return;
        guiItem.set(hotbarItem);
        if(consume) playerInv.setItem(hotbarSlot, topItem);
    }

    /**
     * {@inheritDoc}
     *
     * @param event The {@link GUIClickEvent} of the click
     */
    @Override
    public void executeHotbarSwap(GUIClickEvent event) {
        final GUIContainer gui = event.getGUI();
        if(event.getClickedInventory() != gui.getInventory()) return;
        final Player player = event.getPlayer();
        final GUILayer layer = event.getLayer();
        final int hotbarSlot = event.getHotbarButton();
        final int row = event.getRow();
        final int col = event.getColumn();

        final Inventory playerInv = player.getInventory();
        GUIItem guiItem = layer.getItem(row, col);
        ItemStack curItem = playerInv.getItem(hotbarSlot);
        if(!consume) curItem = curItem.clone();
        if(interactType == GUIInteractType.SINGLE_ITEM && layer.containsItem(curItem)) return;
        if(interactType == GUIInteractType.SINGLE_MATERIAL && layer.containsMaterial(curItem.getType())) return;
        if(guiItem == null) {
            guiItem = new GUIItem((ItemStack) null);
            guiItem.setMovable(true);
            layer.setItem(row, col, guiItem);
        }
        ItemStack item = guiItem.get(player);
        if(consume) playerInv.setItem(hotbarSlot, item);
        if(curItem == null) {
            layer.removeItem(row, col);
        } else {
            int curAmount = curItem.getAmount();
            final int maxAmount = limit == -1 ? curItem.getMaxStackSize() : limit;
            if(curAmount > maxAmount) {
                int extraAmount = curAmount - maxAmount;
                curAmount = maxAmount;
                curItem.setAmount(curAmount);
                if(consume) {
                    ItemStack extraItem = curItem.clone();
                    extraItem.setAmount(extraAmount);
                    playerInv.setItem(hotbarSlot, extraItem);
                }
            }
        }
        guiItem.set(curItem);
    }

    /**
     * {@inheritDoc}
     *
     * @param event The {@link GUIClickEvent} of the click
     */
    @Override
    public void executeCloneStack(GUIClickEvent event) {
        final GUIContainer gui = event.getGUI();
        if(event.getClickedInventory() != gui.getInventory()) return;
        final Player player = event.getPlayer();
        final GUIItem guiItem = event.getLayer().getItem(event.getRow(), event.getColumn());
        if(guiItem == null) return;

        final ItemStack item = guiItem.get(player).clone();
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
        if(!consume) return;
        final Player player = event.getPlayer();
        final GUILayer layer = event.getLayer();

        final ItemStack cursorItem = player.getItemOnCursor();
        final int maxAmount = limit == -1 ? cursorItem.getMaxStackSize() : limit;

        if(cursorItem.getAmount() >= maxAmount) return;
        for(int amount = 1; amount <= maxAmount; ++amount) {
            for(int row = 1; row <= layer.getRows(); ++row) {
                for(int col = 1; col <= layer.getCols(); ++col) {
                    final GUIItem curGuiItem = layer.getItem(row, col);
                    if(curGuiItem == null) continue;
                    if(!curGuiItem.isMovable()) continue;
                    final ItemStack curItem = curGuiItem.get(player);
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
                    curGuiItem.setAmount(extraAmount);
                    if(extraAmount <= 0) layer.removeItem(row, col);
                    if(cursorItem.getAmount() == maxAmount) return;
                }
            }
        }
    }

    /**
     * Not a GUI execution, therefore not implemented.
     *
     * @param event The {@link GUIClickEvent} of the click
     */
    @Override
    public void executeNothing(GUIClickEvent event) {}

    /**
     * Not a GUI execution, therefore not implemented.
     *
     * @param event The {@link GUIClickEvent} of the click
     */
    @Override
    public void executeDropAllCursor(GUIClickEvent event) {}

    /**
     * Not a GUI execution, therefore not implemented.
     *
     * @param event The {@link GUIClickEvent} of the click
     */
    @Override
    public void executeDropOneCursor(GUIClickEvent event) {}

    /**
     * Not a GUI execution, therefore not implemented.
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

    /**
     * Whether to consume the items
     *
     * @return Whether to consume items
     */
    public boolean consumesItems() {
        return consume;
    }

    /**
     * Set whether to consume the items
     *
     * @param consume The new consume state
     */
    public void setConsume(boolean consume) {
        this.consume = consume;
    }

    /**
     * Get the {@link GUIInteractType} of this interact executor
     *
     * @return The <code>GUIInteractType</code>
     */
    public GUIInteractType getInteractType() {
        return interactType;
    }

    /**
     * Set the {@link GUIInteractType} of this interact executor
     *
     * @param interactType The new <code>GUIInteractType</code>
     */
    public void setInteractType(GUIInteractType interactType) {
        this.interactType = interactType;
    }
}