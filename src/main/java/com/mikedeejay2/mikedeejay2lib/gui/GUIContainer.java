package com.mikedeejay2.mikedeejay2lib.gui;

import com.mikedeejay2.mikedeejay2lib.PluginBase;
import com.mikedeejay2.mikedeejay2lib.gui.event.GUIEvent;
import com.mikedeejay2.mikedeejay2lib.gui.event.GUISlotEvent;
import com.mikedeejay2.mikedeejay2lib.gui.item.GUIItem;
import com.mikedeejay2.mikedeejay2lib.gui.modules.GUIModule;
import com.mikedeejay2.mikedeejay2lib.gui.util.GUIMath;
import com.mikedeejay2.mikedeejay2lib.util.PluginInstancer;
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
    public static final int COLUMN_SIZE = 9;
    protected final ItemStack EMPTY_STACK;
    protected Inventory inventory;
    protected String inventoryName;
    protected int inventorySlots;
    protected int inventoryRows;
    protected GUIItem[][] items;
    protected List<GUIModule> modules;
    protected ItemCreator itemCreator;

    public GUIContainer(PluginBase plugin, String inventoryName, int inventoryRows)
    {
        super(plugin);
        this.itemCreator = plugin.itemCreator();
        this.EMPTY_STACK = itemCreator.createItem(Material.LIGHT_GRAY_STAINED_GLASS_PANE, 1 ," ");
        this.inventoryName = plugin.chat().chat(inventoryName);
        this.inventorySlots = inventoryRows * COLUMN_SIZE;
        this.inventoryRows = inventoryRows;
        this.items = new GUIItem[inventoryRows][COLUMN_SIZE];
        this.modules = new ArrayList<>();

        inventory = Bukkit.createInventory(null, inventorySlots, this.inventoryName);
    }

    public void open(Player player)
    {
        modules.forEach(module -> module.onOpen(player));
        update(player);
        player.openInventory(inventory);
    }

    public void update(Player player)
    {
        modules.forEach(module -> module.onUpdate(player));
        for(int row = 0; row < items.length; row++)
        {
            for(int col = 0; col < items[row].length; col++)
            {
                int invSlot = GUIMath.getSlotFromRowCol(row, col);
                GUIItem guiItem = items[row][col];
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
    }

    public void clicked(Player player, int row, int col, GUIItem clicked)
    {
        modules.forEach(module -> module.onClicked(player, row, col, clicked));
        GUISlotEvent manager = getSlotEvents(row, col);
        if(manager == null) return;
        manager.execute(player, row, col, clicked, this);
    }

    public void setItem(int row, int col, Material material, int amount, String displayName, String... loreString)
    {
        setItem(row, col, itemCreator.createItem(material, amount, displayName, loreString));
    }

    public void removeItem(int row, int col)
    {
        setItem(row, col, null);
        setMoveState(row, col, false);
    }

    public void setItem(int row, int col, String headStr, int amount, String displayName, String... loreString)
    {
        setItem(row, col, itemCreator.createHeadItem(headStr, amount, displayName, loreString));
    }

    public void setItem(int row, int col, ItemStack stack)
    {
        items[--row][--col] = new GUIItem(stack);
    }

    public void setMoveState(int row, int col, boolean movable)
    {
        items[--row][--col].setMovable(movable);
    }

    public GUISlotEvent getSlotEvents(int row, int col)
    {
        return items[--row][--col].getEvents();
    }

    public void setSlotEvents(int row, int col, GUISlotEvent manager)
    {
        items[--row][--col].setEvents(manager);
    }

    public void setSlotEvent(int row, int col, GUIEvent event)
    {
        items[row-1][col-1].addEvent(event);
    }

    public void removeSlotEvent(int row, int col, GUIEvent event)
    {
        items[--row][--col].removeEvent(event);
    }

    public boolean doesSlotContainEvent(int row, int col, GUIEvent event)
    {
        return items[--row][--col].containsEvent(event);
    }

    public void removeSlotEvents(int row, int col)
    {
        items[--row][--col].resetEvents();
    }

    public boolean canSlotBeMoved(int row, int col)
    {
        --row; --col;
        return items[row][col].isMovable();
    }

    public void swapItems(int row, int col, int newRow, int newCol)
    {
        --newRow; --newCol; --row; --col;
        GUIItem itemToMove = items[row][col];
        items[row][col] = items[newRow][newCol];
        items[newRow][newCol] = itemToMove;
    }

    public void fullResetSlot(int row, int col)
    {
        removeItem(row, col);
        removeSlotEvents(row, col);
        setMoveState(row, col, false);
    }

    public void setInventoryName(String newName)
    {
        this.inventoryName = plugin.chat().chat(newName);
        this.inventory = Bukkit.createInventory(null, inventorySlots, inventoryName);
    }

    public String getName()
    {
        return inventoryName;
    }

    public int getRows()
    {
        return inventoryRows;
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
}
