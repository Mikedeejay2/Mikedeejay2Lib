package com.mikedeejay2.mikedeejay2lib.gui.event;

import com.mikedeejay2.mikedeejay2lib.gui.GUIContainer;
import com.mikedeejay2.mikedeejay2lib.gui.item.GUIItem;
import com.mikedeejay2.mikedeejay2lib.gui.modules.GUIModule;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class GUISlotEvent
{
    List<GUIEvent> events;

    public GUISlotEvent()
    {
        events = new ArrayList<>();
    }

    public void execute(Player player, int row, int col, GUIItem clicked, GUIContainer gui)
    {
        for(GUIEvent event : events)
        {
            event.execute(player, row, col, clicked, gui);
        }
    }

    public List<GUIEvent> getEvents()
    {
        return events;
    }

    public void addEvent(GUIEvent event)
    {
        events.add(event);
    }

    public void removeEvent(GUIEvent event)
    {
        events.remove(event);
    }

    public boolean containsEvent(GUIEvent event)
    {
        return events.contains(event);
    }

    public void removeEvent(Class<? extends GUIEvent> eventClass)
    {
        String className = eventClass.getName();
        for(GUIEvent event : events)
        {
            Class<? extends GUIEvent> curClass = event.getClass();
            String curClassName = curClass.getName();
            if(!className.equals(curClassName)) continue;
            events.remove(event);
            break;
        }
    }

    public boolean containsEvent(Class<? extends GUIEvent> eventClass)
    {
        String className = eventClass.getName();
        for(GUIEvent event : events)
        {
            Class<? extends GUIEvent> curClass = event.getClass();
            String curClassName = curClass.getName();
            if(!className.equals(curClassName)) continue;
            return true;
        }
        return false;
    }
}
