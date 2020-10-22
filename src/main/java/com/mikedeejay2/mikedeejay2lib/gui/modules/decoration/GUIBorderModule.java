package com.mikedeejay2.mikedeejay2lib.gui.modules.decoration;

import com.mikedeejay2.mikedeejay2lib.PluginBase;
import com.mikedeejay2.mikedeejay2lib.gui.GUIContainer;
import com.mikedeejay2.mikedeejay2lib.gui.item.GUIItem;
import com.mikedeejay2.mikedeejay2lib.gui.modules.GUIModule;
import com.mikedeejay2.mikedeejay2lib.util.head.Base64Heads;
import com.mikedeejay2.mikedeejay2lib.util.item.ItemCreator;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * Module that adds a border of an item type to the top and bottom row of a GUI.
 * Use to add decoration to a GUI.
 *
 * @author Mikedeejay2
 */
public class GUIBorderModule extends GUIModule
{
    // The GUI item that will be used for the border
    private GUIItem borderItem;

    public GUIBorderModule()
    {
        this.borderItem = new GUIItem(ItemCreator.createHeadItem(Base64Heads.WHITE, 1, GUIContainer.EMPTY_NAME));
    }

    /**
     * Get the item that will be used for the border item
     *
     * @return The <tt>GUIItem</tt> that will be used
     */
    public GUIItem getBorderItem()
    {
        return borderItem;
    }

    /**
     * Set the <tt>GUIItem</tt> that the border will use
     *
     * @param borderItem GUIItem for the border to use
     */
    public void setBorderItem(GUIItem borderItem)
    {
        this.borderItem = borderItem;
    }

    /**
     * Set the <tt>GUIItem</tt> that the border will use
     *
     * @param borderItem ItemStack for the border to use
     */
    public void setBorderItem(ItemStack borderItem)
    {
        this.borderItem = new GUIItem(borderItem);
    }

    /**
     * Method injected into the head of the GUI that adds a border to the GUI
     *
     * @param player Player that is viewing the GUI
     * @param gui The GUi
     */
    @Override
    public void onUpdateHead(Player player, GUIContainer gui)
    {
        for(int i = 1; i <= gui.getCols(); i++)
        {
            gui.setItem(1, i, borderItem);
            gui.setItem(gui.getRows(), i, borderItem);
        }
    }
}
