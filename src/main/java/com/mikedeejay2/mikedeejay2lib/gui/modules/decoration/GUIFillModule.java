package com.mikedeejay2.mikedeejay2lib.gui.modules.decoration;

import com.mikedeejay2.mikedeejay2lib.gui.GUIContainer;
import com.mikedeejay2.mikedeejay2lib.gui.item.GUIItem;
import com.mikedeejay2.mikedeejay2lib.gui.modules.GUIModule;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * Module that fills every slot with a GUI with an item.
 *
 * @author Mikedeejay2
 */
public class GUIFillModule implements GUIModule
{
    /**
     * The GUI item that will be used for the fill
     */
    private GUIItem item;

    /**
     * Construct a new <code>GUIFillModule</code>
     *
     * @param item The GUI item that will be used for the fill
     */
    public GUIFillModule(GUIItem item) {
        this.item = item;
    }

    /**
     * Get the item that will be used for the fill item
     *
     * @return The <code>GUIItem</code> that will be used
     */
    public GUIItem getItem() {
        return item;
    }

    /**
     * Set the <code>GUIItem</code> that the fill will use
     *
     * @param item GUIItem for the fill to use
     */
    public void setItem(GUIItem item) {
        this.item = item;
    }

    /**
     * Set the <code>GUIItem</code> that the fill will use
     *
     * @param fill ItemStack for the fill to use
     */
    public void setItem(ItemStack fill) {
        this.item = new GUIItem(fill);
    }

    /**
     * Method injected into the head of the GUI that fills the GUI with the item
     *
     * @param player Player that is viewing the GUI
     * @param gui    The GUI
     */
    @Override
    public void onOpenHead(Player player, GUIContainer gui) {
        for(int row = 1; row <= gui.getRows(); ++row) {
            for(int col = 1; col <= gui.getCols(); ++col) {
                gui.setItem(row, col, item);
            }
        }
    }
}
