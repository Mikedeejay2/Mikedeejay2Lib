package com.mikedeejay2.mikedeejay2lib.gui.event.list;

import com.mikedeejay2.mikedeejay2lib.gui.GUIContainer;
import com.mikedeejay2.mikedeejay2lib.gui.event.GUIEvent;
import com.mikedeejay2.mikedeejay2lib.gui.item.GUIItem;
import com.mikedeejay2.mikedeejay2lib.gui.modules.GUIListModule;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;

/**
 * An event that changes the current page of a list
 *
 * @author Mikedeejay2
 */
public class GUISwitchListPageEvent implements GUIEvent
{
    @Override
    public void execute(InventoryClickEvent event, GUIContainer gui)
    {
        Player player = (Player) event.getWhoClicked();
        ClickType clickType = event.getClick();
        int slot = event.getSlot();
        int row = gui.getRowFromSlot(slot);
        int col = gui.getColFromSlot(slot);
        GUIItem clicked = gui.getItem(row, col);
        if(clickType != ClickType.LEFT) return;
        String displayName = clicked.getNameView();
        String[] split = displayName.split(" ");
        int index = Integer.parseInt(split[split.length-1]);

        gui.getModule(GUIListModule.class).toListPage(index, player, gui);
    }
}
