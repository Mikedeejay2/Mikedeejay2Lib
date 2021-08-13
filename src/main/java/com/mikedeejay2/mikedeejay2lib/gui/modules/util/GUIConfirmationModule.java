package com.mikedeejay2.mikedeejay2lib.gui.modules.util;

import com.mikedeejay2.mikedeejay2lib.gui.GUIContainer;
import com.mikedeejay2.mikedeejay2lib.gui.item.GUIItem;
import com.mikedeejay2.mikedeejay2lib.gui.modules.GUIModule;
import org.bukkit.entity.Player;

/**
 * A simple confirm/deny confirmation module
 *
 * @author Mikedeejay2
 */
public class GUIConfirmationModule implements GUIModule
{
    /**
     * The confirm <code>GUIItem</code>
     */
    protected GUIItem confirmItem;
    /**
     * The deny <code>GUIItem</code>
     */
    protected GUIItem denyItem;

    /**
     * Construct a new <code>GUIConfirmationModule</code>
     *
     * @param confirmItem The confirm <code>GUIItem</code>
     * @param denyItem    The deny <code>GUIItem</code>
     */
    public GUIConfirmationModule(GUIItem confirmItem, GUIItem denyItem)
    {
        this.confirmItem = confirmItem;
        this.denyItem = denyItem;
    }

    /**
     * Add the confirm / deny items
     *
     * @param player The player that is viewing the GUI
     * @param gui    The GUI
     */
    @Override
    public void onOpenHead(Player player, GUIContainer gui)
    {
        int row = (int) Math.ceil(gui.getRows() / 2.0f);
        gui.setItem(row, 4, confirmItem);
        gui.setItem(row, 6, denyItem);
    }

    /**
     * Get the confirmation item
     *
     * @return The confirmation item
     */
    public GUIItem getConfirmItem()
    {
        return confirmItem;
    }

    /**
     * Set the confirmation item
     *
     * @param confirmItem New confirmation Item
     */
    public void setConfirmItem(GUIItem confirmItem)
    {
        this.confirmItem = confirmItem;
    }

    /**
     * Get the deny item
     *
     * @return The deny item
     */
    public GUIItem getDenyItem()
    {
        return denyItem;
    }

    /**
     * Set the deny item
     *
     * @param denyItem New deny Item
     */
    public void setDenyItem(GUIItem denyItem)
    {
        this.denyItem = denyItem;
    }
}
