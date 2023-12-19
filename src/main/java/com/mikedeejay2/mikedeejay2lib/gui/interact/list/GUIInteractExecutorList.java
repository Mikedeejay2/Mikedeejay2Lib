package com.mikedeejay2.mikedeejay2lib.gui.interact.list;

import com.mikedeejay2.mikedeejay2lib.gui.GUIContainer;
import com.mikedeejay2.mikedeejay2lib.gui.GUILayer;
import com.mikedeejay2.mikedeejay2lib.gui.event.GUIClickEvent;
import com.mikedeejay2.mikedeejay2lib.gui.interact.GUIInteractExecutor;
import com.mikedeejay2.mikedeejay2lib.gui.interact.GUIInteractType;
import com.mikedeejay2.mikedeejay2lib.gui.item.GUIItem;
import com.mikedeejay2.mikedeejay2lib.gui.modules.list.GUIListModule;
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
 * This executor is specifically for interacting with list modules. Do no attempt to use this
 * executor on a non-list GUI.
 *
 * @author Mikedeejay2
 */
public class GUIInteractExecutorList implements GUIInteractExecutor {
    /**
     * The {@link GUIInteractType} of this interact executor
     */
    protected GUIInteractType interactType;

    /**
     * The item stack limit, <code>-1</code> is default stack limit
     */
    protected int limit;

    /**
     * Whether or not to consume the items
     */
    protected boolean consume;

    /**
     * Construct a new <code>GUIInteractExecutorList</code>
     *
     * @param limit The item stack limit, <code>-1</code> is default stack limit
     */
    public GUIInteractExecutorList(int limit) {
        this.limit = Math.min(limit, 64);
        this.interactType = GUIInteractType.DEFAULT;
        this.consume = true;
    }

    /**
     * Construct a new <code>GUIInteractExecutorList</code>
     */
    public GUIInteractExecutorList() {
        this.limit = -1;
        this.interactType = GUIInteractType.DEFAULT;
        this.consume = true;
    }

    /**
     * Construct a new <code>GUIInteractExecutorList</code>
     *
     * @param limit   The item stack limit, <code>-1</code> is default stack limit
     * @param consume Whether or not to consume the items
     */
    public GUIInteractExecutorList(int limit, boolean consume) {
        this.limit = Math.min(limit, 64);
        this.interactType = GUIInteractType.DEFAULT;
        this.consume = consume;
    }

    /**
     * Construct a new <code>GUIInteractExecutorList</code>
     *
     * @param consume Whether or not to consume the items
     */
    public GUIInteractExecutorList(boolean consume) {
        this.limit = -1;
        this.interactType = GUIInteractType.DEFAULT;
        this.consume = consume;
    }

    /**
     * Construct a new <code>GUIInteractExecutorList</code>
     *
     * @param type  The {@link GUIInteractType} of this interact executor
     * @param limit The item stack limit, <code>-1</code> is default stack limit
     */
    public GUIInteractExecutorList(GUIInteractType type, int limit) {
        this.limit = Math.min(limit, 64);
        this.interactType = type;
        this.consume = true;
    }

    /**
     * Construct a new <code>GUIInteractExecutorList</code>
     *
     * @param type The {@link GUIInteractType} of this interact executor
     */
    public GUIInteractExecutorList(GUIInteractType type) {
        this.limit = -1;
        this.interactType = type;
        this.consume = true;
    }

    /**
     * Construct a new <code>GUIInteractExecutorList</code>
     *
     * @param type    The {@link GUIInteractType} of this interact executor
     * @param limit   The item stack limit, <code>-1</code> is default stack limit
     * @param consume Whether or not to consume the items
     */
    public GUIInteractExecutorList(GUIInteractType type, int limit, boolean consume) {
        this.limit = Math.min(limit, 64);
        this.interactType = type;
        this.consume = consume;
    }

    /**
     * Construct a new <code>GUIInteractExecutorList</code>
     *
     * @param type    The {@link GUIInteractType} of this interact executor
     * @param consume Whether or not to consume the items
     */
    public GUIInteractExecutorList(GUIInteractType type, boolean consume) {
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
        final int row = event.getRow();
        final int col = event.getColumn();
        final Player player = event.getPlayer();

        final GUIListModule list = gui.getModule(GUIListModule.class);
        final GUIItem guiItem = list.getItem(row, col, gui);
        final ItemStack bottomItem = guiItem.get(player);
        final int maxAmount = limit == -1 ? bottomItem.getMaxStackSize() : limit;
        int curAmount = bottomItem.getAmount();

        if(curAmount > maxAmount) {
            guiItem.setAmount(curAmount - maxAmount);
            list.setItem(row, col, gui, guiItem);
            curAmount = maxAmount;
        } else list.removeItem(row, col, gui);
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
        final GUIListModule list = gui.getModule(GUIListModule.class);
        final Player player = event.getPlayer();
        final int row = event.getRow();
        final int col = event.getColumn();

        final ItemStack cursorItem = player.getItemOnCursor();
        final int cursorAmount = cursorItem.getAmount();
        final GUIItem guiItem = list.getItem(row, col, gui);
        final int bottomAmount = guiItem.get(player).getAmount();
        final int maxAmount = limit == -1 ? cursorItem.getMaxStackSize() : limit;

        guiItem.setAmount(bottomAmount - (maxAmount - cursorAmount));
        if(guiItem.getAmount() <= 0) list.removeItem(row, col, gui);
        else list.setItem(row, col, gui, guiItem);
        if(consume) {
            cursorItem.setAmount(maxAmount);
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
        final int row = event.getRow();
        final int col = event.getColumn();
        final Player player = event.getPlayer();

        final GUIListModule list = gui.getModule(GUIListModule.class);
        final GUIItem guiItem = list.getItem(row, col, gui);
        final ItemStack bottomItem = guiItem.get(player);
        final int bottomAmount = bottomItem.getAmount();
        final int maxAmount = limit == -1 ? bottomItem.getMaxStackSize() : limit;
        int halfTop = (int) Math.ceil(bottomAmount / 2.0);
        int halfBottom = (int) Math.floor(bottomAmount / 2.0);

        if(halfTop > maxAmount) {
            halfBottom += halfTop - maxAmount;
            halfTop = maxAmount;
        }
        guiItem.setAmount(halfBottom);
        if(halfBottom <= 0) list.removeItem(row, col, gui);
        else list.setItem(row, col, gui, guiItem);
        if(consume) {
            ItemStack cursorItem = bottomItem.clone();
            cursorItem.setAmount(halfTop);
            player.setItemOnCursor(cursorItem);
        }
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
        final GUIListModule list = gui.getModule(GUIListModule.class);
        final Player player = event.getPlayer();

        ItemStack cursorItem = player.getItemOnCursor();
        final int cursorAmount = cursorItem.getAmount();
        final int row = event.getRow();
        final int col = event.getColumn();
        final GUIItem guiItem = list.getItem(row, col, gui);
        final ItemStack bottomItem = guiItem.get(player);
        final int bottomAmount = bottomItem.getAmount();

        if(cursorItem.getType() == Material.AIR && consume) {
            cursorItem = bottomItem.clone();
            cursorItem.setAmount(1);
            player.setItemOnCursor(cursorItem);
        } else if(consume) {
            cursorItem.setAmount(cursorAmount + 1);
        }
        guiItem.setAmount(bottomAmount - 1);
        if(bottomAmount - 1 <= 0) list.removeItem(row, col, gui);
        else list.setItem(row, col, gui, guiItem);
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
        final GUIListModule list = gui.getModule(GUIListModule.class);
        final Player player = event.getPlayer();
        final int row = event.getRow();
        final int col = event.getColumn();
        final ItemStack cursorItem = player.getItemOnCursor();
        final GUIItem curItem = list.getItem(row, col, gui);


        if(
            curItem == null ||
                !ItemComparison.equalsEachOther(curItem.get(player), cursorItem) ||
                curItem.get(player).getAmount() == (limit == -1 ? curItem.get(player).getMaxStackSize() : limit)
        ) {
            if(interactType == GUIInteractType.SINGLE_ITEM && list.containsItem(cursorItem)) return;
            if(interactType == GUIInteractType.SINGLE_MATERIAL && list.containsMaterial(cursorItem.getType())) return;
            final GUIItem newItem = new GUIItem(cursorItem.clone()).setMovable(true);
            int newAmount = newItem.getAmount();
            int extraAmount = 0;
            int maxAmount = limit == -1 ? cursorItem.getMaxStackSize() : limit;
            if(newAmount > maxAmount) {
                extraAmount = newAmount - maxAmount;
                newAmount = maxAmount;
            }
            newItem.setAmount(newAmount);
            if(consume) cursorItem.setAmount(extraAmount);
            int index = list.getItemIndex(row, col, gui);
            int size = list.getSize();
            if(index >= size || index < 0) {
                list.addItem(newItem);
            } else {
                list.addItem(row, col, gui, newItem);
            }
        } else {
            int newAmount = cursorItem.getAmount() + curItem.getAmount();
            int extraAmount = 0;
            int maxAmount = limit == -1 ? cursorItem.getMaxStackSize() : limit;
            if(newAmount > maxAmount) {
                extraAmount = newAmount - maxAmount;
                newAmount = maxAmount;
            }
            curItem.setAmount(newAmount);
            list.setItem(row, col, gui, curItem);
            if(consume) cursorItem.setAmount(extraAmount);
        }
        if(consume) player.setItemOnCursor(cursorItem);
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
        final int row = event.getRow();
        final int col = event.getColumn();

        final ItemStack cursorItem = player.getItemOnCursor();
        final GUIListModule list = gui.getModule(GUIListModule.class);
        if(interactType == GUIInteractType.SINGLE_ITEM && list.containsItem(cursorItem)) return;
        if(interactType == GUIInteractType.SINGLE_MATERIAL && list.containsMaterial(cursorItem.getType())) return;
        final int cursorAmount = cursorItem.getAmount();
        final GUIItem guiItem = list.getItem(row, col, gui);
        final ItemStack bottomItem = guiItem.get(player);
        final int bottomAmount = bottomItem.getAmount();
        final int maxAmount = limit == -1 ? cursorItem.getMaxStackSize() : limit;

        guiItem.setAmount(maxAmount);
        list.setItem(row, col, gui, guiItem);
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
        final int row = event.getRow();
        final int col = event.getColumn();

        final GUIListModule list = gui.getModule(GUIListModule.class);
        final ItemStack cursorItem = player.getItemOnCursor();
        final int cursorAmount = cursorItem.getAmount();
        GUIItem guiItem = list.getItem(row, col, gui);

        if(guiItem == null) {
            if(interactType == GUIInteractType.SINGLE_ITEM && list.containsItem(cursorItem)) return;
            if(interactType == GUIInteractType.SINGLE_MATERIAL && list.containsMaterial(cursorItem.getType())) return;
            guiItem = new GUIItem(cursorItem.clone());
            guiItem.setMovable(true);
            guiItem.setAmount(1);
            final int index = list.getItemIndex(row, col, gui);
            final int size = list.getList().size();
            if(index > size) {
                list.addItem(guiItem);
            } else {
                list.addItem(row, col, gui, guiItem);
            }
        } else {
            if(!ItemComparison.equalsEachOther(guiItem.get(player), cursorItem)) return;
            final int maxAmount = limit == -1 ? guiItem.get(player).getMaxStackSize() : limit;
            if(guiItem.getAmount() >= maxAmount) return;
            guiItem.setAmount(guiItem.getAmount() + 1);
            list.setItem(row, col, gui, guiItem);
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
        final int row = event.getRow();
        final int col = event.getColumn();

        final GUIListModule list = gui.getModule(GUIListModule.class);
        final ItemStack cursorItem = player.getItemOnCursor();
        if(interactType == GUIInteractType.SINGLE_ITEM && list.containsItem(cursorItem)) return;
        if(interactType == GUIInteractType.SINGLE_MATERIAL && list.containsMaterial(cursorItem.getType())) return;
        final int maxAmount = limit == -1 ? cursorItem.getMaxStackSize() : limit;
        if(cursorItem.getAmount() > maxAmount) return;
        final GUIItem guiItem = list.getItem(row, col, gui);
        final ItemStack bottomItem = guiItem.get(player);

        guiItem.set(consume ? cursorItem : cursorItem.clone());
        list.setItem(row, col, gui, guiItem);
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
        final int row = event.getRow();
        final int col = event.getColumn();

        final GUIListModule list = gui.getModule(GUIListModule.class);
        final GUIItem guiItem = list.getItem(row, col, gui);
        final ItemStack stack = guiItem.get(player);
        final int curAmount = stack.getAmount();
        final int maxAmount = limit == -1 ? stack.getMaxStackSize() : limit;
        int itemToDropAmount = curAmount;

        if(curAmount > maxAmount) {
            guiItem.setAmount(curAmount - maxAmount);
            list.setItem(row, col, gui, guiItem);
            itemToDropAmount = maxAmount;
        } else list.removeItem(row, col, gui);
        ItemStack itemToDrop = stack.clone();
        itemToDrop.setAmount(itemToDropAmount);
        Location location = player.getEyeLocation();
        World world = location.getWorld();
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
        final int row = event.getRow();
        final int col = event.getColumn();

        final GUIListModule list = gui.getModule(GUIListModule.class);
        final GUIItem guiItem = list.getItem(row, col, gui);

        ItemStack itemToDrop = guiItem.get(player).clone();
        itemToDrop.setAmount(1);
        guiItem.setAmount(guiItem.getAmount() - 1);
        if(guiItem.getAmount() <= 0) list.removeItem(row, col, gui);
        else list.setItem(row, col, gui, guiItem);

        Location location = player.getEyeLocation();
        World world = location.getWorld();
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
        final GUIContainer gui = event.getGUI();
        GUIListModule list = gui.getModule(GUIListModule.class);
        final Player player = event.getPlayer();
        if(event.getClickedInventory() == gui.getInventory()) {
            final int row = event.getRow();
            final int col = event.getColumn();
            final GUIItem guiItem = list.getItem(row, col, gui);
            final ItemStack itemToMove = guiItem.get(player);
            final Inventory playerInv = player.getInventory();
            if(!consume) {
                list.removeItem(row, col, gui);
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
                    list.removeItem(row, col, gui);
                    return;
                }
            }
            if(itemToMove.getAmount() <= 0) {
                list.removeItem(row, col, gui);
                return;
            }
            for(int i = 0; i < playerInv.getStorageContents().length; ++i) {
                ItemStack curItem = playerInv.getItem(i);
                if(curItem != null) continue;
                curItem = itemToMove.clone();
                int newAmount = itemToMove.getAmount();
                int extraAmount = 0;
                int maxAmount = limit == -1 ? itemToMove.getMaxStackSize() : limit;
                if(newAmount > maxAmount) {
                    extraAmount = newAmount - maxAmount;
                    newAmount = maxAmount;
                }
                itemToMove.setAmount(extraAmount);
                curItem.setAmount(newAmount);
                playerInv.setItem(i, curItem);
                if(itemToMove.getAmount() <= 0) {
                    list.removeItem(row, col, gui);
                    return;
                }
            }
        } else {
            ItemStack itemToMove = event.getClickedInventory().getItem(event.getSlot());
            int itemToMoveAmt = itemToMove.getAmount();
            for(int i = 0; i < list.getSize(); ++i) {
                GUIItem curGUIItem = list.getItem(i);
                ItemStack curItem = curGUIItem == null ? null : curGUIItem.get(player);
                if(curItem == null || !curGUIItem.isMovable()) continue;
                if(!ItemComparison.equalsEachOther(curItem, itemToMove)) continue;
                int newAmount = curGUIItem.getAmount() + itemToMoveAmt;
                int extraAmount = 0;
                int maxAmount = limit == -1 ? curItem.getMaxStackSize() : limit;
                if(newAmount > maxAmount) {
                    extraAmount = newAmount - maxAmount;
                    newAmount = maxAmount;
                }
                if(consume) itemToMove.setAmount(extraAmount);
                itemToMoveAmt = extraAmount;
                curGUIItem.setAmount(newAmount);
                list.setItem(i, curGUIItem);
                if(itemToMoveAmt <= 0) return;
            }
            if(itemToMoveAmt <= 0 || !event.getLayer().getDefaultMoveState()) return;
            if(interactType == GUIInteractType.SINGLE_ITEM && list.containsItem(itemToMove)) return;
            if(interactType == GUIInteractType.SINGLE_MATERIAL && list.containsMaterial(itemToMove.getType())) return;
            final GUIItem guiItem = new GUIItem(itemToMove.clone());
            guiItem.setMovable(true);
            int newAmount = itemToMoveAmt;
            int extraAmount = 0;
            int maxAmount = limit == -1 ? itemToMove.getMaxStackSize() : limit;
            if(newAmount > maxAmount) {
                extraAmount = newAmount - maxAmount;
                newAmount = maxAmount;
            }
            if(consume) itemToMove.setAmount(extraAmount);
            guiItem.setAmount(newAmount);
            list.addItem(guiItem);
        }
    }

    /**
     * {@inheritDoc}
     *
     * @param event The {@link GUIClickEvent} of the click
     */
    @Override
    public void executeHotbarMoveAndReadd(GUIClickEvent event) {
        final GUIContainer gui = event.getGUI();
        final Player player = event.getPlayer();
        final int row = event.getRow();
        final int col = event.getColumn();

        final GUIListModule list = gui.getModule(GUIListModule.class);
        final int hotbarSlot = event.getHotbarButton();
        final Inventory playerInv = player.getInventory();
        final ItemStack hotbarItem = playerInv.getItem(hotbarSlot);
        if(interactType == GUIInteractType.SINGLE_ITEM && list.containsItem(hotbarItem)) return;
        if(interactType == GUIInteractType.SINGLE_MATERIAL && list.containsMaterial(hotbarItem.getType())) return;
        final GUIItem guiItem = list.getItem(row, col, gui);
        final ItemStack topItem = guiItem.get(player);
        final int maxAmount = limit == -1 ? hotbarItem.getMaxStackSize() : limit;

        if(hotbarItem.getAmount() > maxAmount) return;
        guiItem.set(hotbarItem);
        list.setItem(row, col, gui, guiItem);
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
        final int row = event.getRow();
        final int col = event.getColumn();

        final GUIListModule list = gui.getModule(GUIListModule.class);
        final int hotbarSlot = event.getHotbarButton();
        final Inventory playerInv = player.getInventory();
        final ItemStack curItem = playerInv.getItem(hotbarSlot);
        GUIItem guiItem = list.getItem(row, col, gui);
        if(interactType == GUIInteractType.SINGLE_ITEM && list.containsItem(curItem)) return;
        if(interactType == GUIInteractType.SINGLE_MATERIAL && list.containsMaterial(curItem.getType())) return;
        final boolean nullGuiItem = guiItem == null;

        if(nullGuiItem) {
            guiItem = new GUIItem((ItemStack) null);
            guiItem.setMovable(true);
        }
        if(consume) playerInv.setItem(hotbarSlot, guiItem.get(player));
        if(curItem == null) {
            list.removeItem(row, col, gui);
        } else {
            int curAmount = curItem.getAmount();
            int maxAmount = limit == -1 ? curItem.getMaxStackSize() : limit;
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
            guiItem.set(curItem);
        }
        if(nullGuiItem) {
            list.addItem(guiItem);
        } else {
            list.setItem(row, col, gui, guiItem);
        }
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
        final int row = event.getRow();
        final int col = event.getColumn();

        final GUIListModule list = gui.getModule(GUIListModule.class);
        final GUIItem guiItem = list.getItem(row, col, gui);
        if(guiItem == null) return;
        final ItemStack item = guiItem.get(player).clone();
        int maxAmount = limit == -1 ? item.getMaxStackSize() : limit;

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
        final GUIContainer gui = event.getGUI();
        final Player player = event.getPlayer();
        final GUILayer layer = event.getLayer();

        final ItemStack cursorItem = player.getItemOnCursor();
        final GUIListModule list = gui.getModule(GUIListModule.class);
        final int maxAmount = limit == -1 ? cursorItem.getMaxStackSize() : limit;

        if(cursorItem.getAmount() >= maxAmount) return;
        for(int amount = 1; amount <= maxAmount; ++amount) {
            for(int row = 1; row <= layer.getRows(); ++row) {
                for(int col = 1; col <= layer.getCols(); ++col) {
                    GUIItem curGuiItem = layer.getItem(row, col);
                    if(curGuiItem == null) continue;
                    if(!curGuiItem.isMovable()) continue;
                    ItemStack curItem = curGuiItem.get(player);
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
                    if(extraAmount <= 0) list.removeItem(row, col, gui);
                    else list.setItem(row, col, gui, curGuiItem);
                    if(cursorItem.getAmount() == maxAmount) return;
                }
            }
        }
    }

    /**
     * Not a list execution, therefore not implemented.
     *
     * @param event The {@link GUIClickEvent} of the click
     */
    @Override
    public void executeNothing(GUIClickEvent event) {}

    /**
     * Not a list execution, therefore not implemented.
     *
     * @param event The {@link GUIClickEvent} of the click
     */
    @Override
    public void executeDropAllCursor(GUIClickEvent event) {}

    /**
     * Not a list execution, therefore not implemented.
     *
     * @param event The {@link GUIClickEvent} of the click
     */
    @Override
    public void executeDropOneCursor(GUIClickEvent event) {}

    /**
     * Not a list execution, therefore not implemented.
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
     * Whether or not to consume the items
     *
     * @return Whether or not to consume items
     */
    public boolean consumesItems() {
        return consume;
    }

    /**
     * Set whether or not to consume the items
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