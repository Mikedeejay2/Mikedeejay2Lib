package com.mikedeejay2.mikedeejay2lib.gui.event.navigator;

import com.mikedeejay2.mikedeejay2lib.PluginBase;
import com.mikedeejay2.mikedeejay2lib.gui.GUIContainer;
import com.mikedeejay2.mikedeejay2lib.gui.event.GUIEvent;
import com.mikedeejay2.mikedeejay2lib.gui.item.GUIItem;
import org.bukkit.entity.Player;

/**
 * An event for scrolling a large GUI in a direction
 *
 * @author Mikedeejay2
 */
public class GUIScrollEvent extends GUIEvent
{
    protected int rowAmt;
    protected int colAmt;

    public GUIScrollEvent(PluginBase plugin, int rowAmt, int colAmt)
    {
        super(plugin);
        this.rowAmt = rowAmt;
        this.colAmt = colAmt;
    }

    @Override
    public void execute(Player player, int row, int col, GUIItem clicked, GUIContainer gui)
    {
        int rowOffset = gui.getRowOffset();
        int colOffset = gui.getColOffset();
        int totalRow = rowOffset + Math.min(GUIContainer.MAX_INVENTORY_ROWS, gui.getRows());
        int totalCol = colOffset + GUIContainer.MAX_INVENTORY_COLS;
        plugin.chat().debug("Total rows: " + totalRow + ", rows: " + gui.getRows() + "\n" +
                            "Total cols: " + totalCol + ", cols: " + gui.getCols() + "\n" +
                            "Rows execute? " + (totalRow <= gui.getRows()) + ", Cols execute? " + (totalCol <= gui.getCols()));
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
