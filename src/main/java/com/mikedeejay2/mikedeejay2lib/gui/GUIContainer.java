package com.mikedeejay2.mikedeejay2lib.gui;

import com.mikedeejay2.mikedeejay2lib.PluginBase;
import com.mikedeejay2.mikedeejay2lib.gui.event.GUIEvent;
import com.mikedeejay2.mikedeejay2lib.gui.event.GUIItemEvent;
import com.mikedeejay2.mikedeejay2lib.gui.item.GUIItem;
import com.mikedeejay2.mikedeejay2lib.gui.modules.GUIModule;
import com.mikedeejay2.mikedeejay2lib.util.PluginInstancer;
import com.mikedeejay2.mikedeejay2lib.util.array.ArrayUtil;
import com.mikedeejay2.mikedeejay2lib.util.chat.Chat;
import com.mikedeejay2.mikedeejay2lib.util.item.ItemCreator;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class GUIContainer extends PluginInstancer<PluginBase>
{
    protected static final int INVENTORY_COLS = 9;
    public static final String EMPTY_NAME = "§7";
    protected final ItemStack EMPTY_STACK;
    protected Inventory inventory;
    protected String inventoryName;
    protected int inventorySlots;
    protected int inventoryRows;
    protected GUIItem[][] items;
    protected List<GUIModule> modules;
    protected boolean defaultMoveState;

    public GUIContainer(PluginBase plugin, String inventoryName, int inventoryRows)
    {
        super(plugin);
        this.EMPTY_STACK = ItemCreator.createItem(Material.LIGHT_GRAY_STAINED_GLASS_PANE, 1 , EMPTY_NAME);
        this.inventoryName = Chat.chat(inventoryName);
        this.inventorySlots = inventoryRows * INVENTORY_COLS;
        this.inventoryRows = inventoryRows;
        this.items = new GUIItem[inventoryRows][INVENTORY_COLS];
        this.modules = new ArrayList<>();
        this.defaultMoveState = false;

        inventory = Bukkit.createInventory(null, inventorySlots, this.inventoryName);
    }

    public void open(Player player)
    {
        modules.forEach(module -> module.onOpenHead(player, this));
        update(player);
        player.openInventory(inventory);
        modules.forEach(module -> module.onOpenTail(player, this));
    }

    public void close(Player player)
    {
        modules.forEach(module -> module.onClose(player, this));
    }

    public void update(Player player)
    {
        modules.forEach(module -> module.onUpdateHead(player, this));
        for(int row = 0; row < inventoryRows; row++)
        {
            for(int col = 0; col < INVENTORY_COLS; col++)
            {
                int invSlot = getSlotFromRowCol(row, col);
                GUIItem guiItem = items[row][col];
                if(guiItem == null)
                {
                    if(!defaultMoveState) inventory.setItem(invSlot, EMPTY_STACK);
                    continue;
                }
                ItemStack itemStack = guiItem.getItem();
                boolean movable = guiItem.isMovable();

                if(itemStack != null)
                {
                    inventory.setItem(invSlot, itemStack);
                }
                else if(!movable)
                {
                    inventory.setItem(invSlot, EMPTY_STACK);
                }
            }
        }
        modules.forEach(module -> module.onUpdateTail(player, this));
    }

    public void clicked(Player player, int row, int col, GUIItem clicked)
    {
        modules.forEach(module -> module.onClickedHead(player, row, col, clicked, this));
        GUIItemEvent manager = getItemEvents(row, col);
        if(manager == null) return;
        manager.execute(player, row, col, clicked, this);
        modules.forEach(module -> module.onClickedTail(player, row, col, clicked, this));
    }

    public void setItem(int row, int col, Material material, int amount, String displayName, String... loreString)
    {
        setItem(row, col, ItemCreator.createItem(material, amount, displayName, loreString));
    }

    public void removeItem(int row, int col)
    {
        setItem(row, col, (GUIItem)null);
    }

    public void setItem(int row, int col, String headStr, int amount, String displayName, String... loreString)
    {
        setItem(row, col, ItemCreator.createHeadItem(headStr, amount, displayName, loreString));
    }

    public void setItem(int row, int col, ItemStack stack)
    {
        setItem(row, col, new GUIItem(stack));
    }

    public void setItem(int row, int col, GUIItem item)
    {
        if(item != null)
        {
            item.setRow(row);
            item.setCol(col);
        }
        items[--row][--col] = item;
    }

    public void setMoveState(int row, int col, boolean movable)
    {
        GUIItem item = items[--row][--col];
        item.setMovable(movable);
    }

    public GUIItemEvent getItemEvents(int row, int col)
    {
        GUIItem item = items[--row][--col];
        return item == null ? null : item.getEvents();
    }

    public void setItemEvents(int row, int col, GUIItemEvent manager)
    {
        GUIItem item = items[--row][--col];
        item.setEvents(manager);
    }

    public void addItemEvent(int row, int col, GUIEvent event)
    {
        GUIItem item = items[--row][--col];
        item.addEvent(event);
    }

    public void removeItemEvent(int row, int col, GUIEvent event)
    {
        GUIItem item = items[--row][--col];
        if(item != null) item.removeEvent(event);
    }

    public boolean doesSlotContainEvent(int row, int col, GUIEvent event)
    {
        GUIItem item = items[--row][--col];
        return item != null && item.containsEvent(event);
    }

    public void removeItemEvents(int row, int col)
    {
        GUIItem item = items[--row][--col];
        if(item != null) item.resetEvents();
    }

    public boolean canSlotBeMoved(int row, int col)
    {
        GUIItem item = items[--row][--col];
        return item == null ? defaultMoveState : item.isMovable();
    }

    public void swapItems(int row, int col, int newRow, int newCol)
    {
        --newRow; --newCol; --row; --col;
        GUIItem itemToMove = items[row][col];
        items[row][col] = items[newRow][newCol];
        items[newRow][newCol] = itemToMove;
    }

    public void setInventoryName(String newName)
    {
        this.inventoryName = Chat.chat(newName);
        this.inventory = Bukkit.createInventory(null, inventorySlots, inventoryName);
    }

    public boolean itemExists(int row, int col)
    {
        return items[--row][--col] != null;
    }

    public String getName()
    {
        return inventoryName;
    }

    public int getRows()
    {
        return inventoryRows;
    }

    public int getCols()
    {
        return INVENTORY_COLS;
    }

    public void addModule(GUIModule module)
    {
        modules.add(module);
    }

    public void removeModule(GUIModule module)
    {
        modules.remove(module);
    }

    public void removeModule(Class<? extends GUIModule> moduleClass)
    {
        String className = moduleClass.getName();
        for(GUIModule module : modules)
        {
            Class<? extends GUIModule> curClass = module.getClass();
            String curClassName = curClass.getName();
            if(!className.equals(curClassName)) continue;
            modules.remove(module);
            break;
        }
    }

    public boolean containsModule(GUIModule module)
    {
        return modules.contains(module);
    }

    public boolean containsModule(Class<? extends GUIModule> moduleClass)
    {
        String className = moduleClass.getName();
        for(GUIModule module : modules)
        {
            Class<? extends GUIModule> curClass = module.getClass();
            String curClassName = curClass.getName();
            if(!className.equals(curClassName)) continue;
            return true;
        }
        return false;
    }

    public <T extends GUIModule> T getModule(Class<T> moduleClass)
    {
        String className = moduleClass.getName();
        for(GUIModule module : modules)
        {
            Class<? extends GUIModule> curClass = module.getClass();
            String curClassName = curClass.getName();
            if(!className.equals(curClassName)) continue;
            return (T) module;
        }
        return null;
    }

    public Inventory getInventory()
    {
        return inventory;
    }

    public GUIItem getItem(int row, int col)
    {
        return items[--row][--col];
    }

    public boolean getDefaultMoveState()
    {
        return defaultMoveState;
    }

    public void setDefaultMoveState(boolean defaultMoveState)
    {
        this.defaultMoveState = defaultMoveState;
    }

    public List<GUIItem> getItemsAsList()
    {
        return ArrayUtil.toList(items);
    }

    public GUIItem[][] getItemsAsArray()
    {
        return items;
    }

    public int getSlotFromRowCol(int row, int col)
    {
        return (row * INVENTORY_COLS) + col;
    }

    public int getRowFromSlot(int slot)
    {
        return (slot / INVENTORY_COLS) + 1;
    }

    public int getColFromSlot(int slot)
    {
        return (slot % INVENTORY_COLS) + 1;
    }
}
