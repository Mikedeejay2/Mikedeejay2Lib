package com.mikedeejay2.mikedeejay2lib.gui.event.list;

import com.mikedeejay2.mikedeejay2lib.gui.GUIContainer;
import com.mikedeejay2.mikedeejay2lib.gui.GUILayer;
import com.mikedeejay2.mikedeejay2lib.gui.event.GUIEvent;
import com.mikedeejay2.mikedeejay2lib.gui.modules.list.GUIListModule;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.List;
import java.util.Map;

/**
 * An event that changes the current page of a list
 *
 * @author Mikedeejay2
 */
public class GUISwitchListLocEvent implements GUIEvent
{
    @Override
    public void execute(InventoryClickEvent event, GUIContainer gui)
    {
        ClickType clickType = event.getClick();
        int slot = event.getSlot();
        GUIListModule module = gui.getModule(GUIListModule.class);
        String listLayerName = module.getLayerName();
        GUILayer listLayer = gui.getLayer(listLayerName);
        int row = listLayer.getRowFromSlot(slot);
        int col = listLayer.getColFromSlot(slot);
        if(!gui.getTopLayer(row, col).getName().equals(listLayerName)) return;
        if(clickType != ClickType.LEFT) return;

        List<Map.Entry<Integer, Integer>> forwards = module.getForwards();
        List<Map.Entry<Integer, Integer>> backs = module.getBacks();

        int relative = 0;

        for(int i = 0; i < forwards.size(); ++i)
        {
            Map.Entry<Integer, Integer> entry = forwards.get(i);
            if(entry.getKey() != row || entry.getValue() != col) continue;
            relative = i + 1;
            break;
        }

        if(relative != 0)
        {
            module.setListLoc(module.getCurLoc() + relative);
            return;
        }

        for(int i = 0; i < backs.size(); ++i)
        {
            Map.Entry<Integer, Integer> entry = backs.get(i);
            if(entry.getKey() != row || entry.getValue() != col) continue;
            relative = -(i + 1);
            break;
        }

        if(relative != 0)
        {
            module.setListLoc(module.getCurLoc() + relative);
        }
    }
}
