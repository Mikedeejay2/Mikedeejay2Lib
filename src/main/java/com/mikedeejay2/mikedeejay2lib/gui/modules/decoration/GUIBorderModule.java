package com.mikedeejay2.mikedeejay2lib.gui.modules.decoration;

import com.mikedeejay2.mikedeejay2lib.gui.GUIContainer;
import com.mikedeejay2.mikedeejay2lib.gui.item.GUIItem;
import com.mikedeejay2.mikedeejay2lib.gui.modules.GUIModule;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * Module that adds a border of an item type to the top and bottom row of a GUI.
 * Used to add decoration to a GUI.
 *
 * @deprecated Use {@link GUIAnimDecoratorModule} instead
 * @author Mikedeejay2
 */
@Deprecated
public class GUIBorderModule implements GUIModule {
    /**
     * The GUI item that will be used for the border
     */
    private GUIItem borderItem;

    /**
     * Construct a new <code>GUIBorderModule</code>
     *
     * @param borderItem The GUI item that will be used for the border
     */
    @Deprecated
    public GUIBorderModule(GUIItem borderItem) {
        this.borderItem = borderItem;
    }

    /**
     * Get the item that will be used for the border item
     *
     * @return The <code>GUIItem</code> that will be used
     */
    public GUIItem getBorderItem() {
        return borderItem;
    }

    /**
     * Set the <code>GUIItem</code> that the border will use
     *
     * @param borderItem GUIItem for the border to use
     */
    public void setBorderItem(GUIItem borderItem) {
        this.borderItem = borderItem;
    }

    /**
     * Set the <code>GUIItem</code> that the border will use
     *
     * @param borderItem ItemStack for the border to use
     */
    public void setBorderItem(ItemStack borderItem) {
        this.borderItem = new GUIItem(borderItem);
    }

    /**
     * Method injected into the head of the GUI that adds a border to the GUI
     *
     * @param player Player that is viewing the GUI
     * @param gui    The GUI
     */
    @Override
    public void onOpenHead(Player player, GUIContainer gui) {
        for(int i = 1; i <= gui.getCols(); i++) {
            gui.setItem(1, i, borderItem);
            gui.setItem(gui.getRows(), i, borderItem);
        }
    }
}
