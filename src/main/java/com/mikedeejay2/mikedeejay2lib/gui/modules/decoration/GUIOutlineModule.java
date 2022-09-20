package com.mikedeejay2.mikedeejay2lib.gui.modules.decoration;

import com.mikedeejay2.mikedeejay2lib.gui.GUIContainer;
import com.mikedeejay2.mikedeejay2lib.gui.item.GUIItem;
import com.mikedeejay2.mikedeejay2lib.gui.util.SlotMatcher;
import org.bukkit.entity.Player;

/**
 * Module that adds an outline of an item type to the sides of a GUI.
 * Use to add decoration to a GUI.
 *
 * @author Mikedeejay2
 */
public class GUIOutlineModule extends GUIDecoratorModule {
    /**
     * Construct a new <code>GUIOutlineModule</code>
     *
     * @param outlineItem The <code>GUIItem</code> to use
     */
    public GUIOutlineModule(GUIItem outlineItem) {
        super(null, outlineItem);
    }

    @Override
    public void onOpenHead(Player player, GUIContainer gui) {
        this.setMatcher(SlotMatcher.not(SlotMatcher.inRange(2, 2, gui.getRows() - 1, gui.getCols() - 1)));
        super.onOpenHead(player, gui);
    }
}
