package com.mikedeejay2.mikedeejay2lib.gui.interact.limit;

import com.mikedeejay2.mikedeejay2lib.gui.GUIContainer;
import com.mikedeejay2.mikedeejay2lib.gui.GUILayer;
import com.mikedeejay2.mikedeejay2lib.gui.interact.GUIInteractExecutor;
import com.mikedeejay2.mikedeejay2lib.gui.interact.normal.GUIInteractExecutorDefault;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

/**
 * A <tt>GUIInteractExecutor</tt> that moves items with a custom limit specified on construction.
 *
 * @author Mikedeejay2
 */
public class GUIInteractExecutorLimit extends GUIInteractExecutorDefault
{
    protected int limit;

    public GUIInteractExecutorLimit(int limit)
    {
        this.limit = Math.min(limit, 64);
    }

    public int getLimit()
    {
        return limit;
    }

    public void setLimit(int limit)
    {
        this.limit = limit;
    }
}
