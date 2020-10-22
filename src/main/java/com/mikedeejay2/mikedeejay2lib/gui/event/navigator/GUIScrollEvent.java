package com.mikedeejay2.mikedeejay2lib.gui.event.navigator;

import com.mikedeejay2.mikedeejay2lib.PluginBase;
import com.mikedeejay2.mikedeejay2lib.gui.GUIContainer;
import com.mikedeejay2.mikedeejay2lib.gui.event.GUIEvent;
import com.mikedeejay2.mikedeejay2lib.gui.item.GUIItem;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;

/**
 * An event for scrolling a large GUI in a direction
 *
 * @author Mikedeejay2
 */
public class GUIScrollEvent implements GUIEvent
{
    protected int rowAmt;
    protected int colAmt;

    public GUIScrollEvent(int rowAmt, int colAmt)
    {
        this.rowAmt = rowAmt;
        this.colAmt = colAmt;
    }

    @Override
    public void execute(Player player, int row, int col, GUIItem clicked, GUIContainer gui, InventoryAction action, ClickType clickType)
    {
        if(clickType != ClickType.LEFT) return;
        int rowOffset = gui.getRowOffset();
        int colOffset = gui.getColOffset();
        int totalRow = rowOffset + Math.min(GUIContainer.MAX_INVENTORY_ROWS, gui.getRows());
        int totalCol = colOffset + GUIContainer.MAX_INVENTORY_COLS;
        if(gui.getRows() >= totalRow + rowAmt && rowOffset + rowAmt >= 0)
        {
            gui.addRowOffset(rowAmt);
        }
        if(gui.getCols() >= totalCol + colAmt && colOffset + colAmt >= 0)
        {
            gui.addColOffset(colAmt);
        }
        gui.update(player);
    }
}
