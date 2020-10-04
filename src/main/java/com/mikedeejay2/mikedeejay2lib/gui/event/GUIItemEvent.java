package com.mikedeejay2.mikedeejay2lib.gui.event;

import com.mikedeejay2.mikedeejay2lib.gui.GUIContainer;
import com.mikedeejay2.mikedeejay2lib.gui.item.GUIItem;
import com.mikedeejay2.mikedeejay2lib.gui.modules.GUIModule;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

/**
 * Holds all <tt>GUIEvents</tt> in a <tt>GUIItem</tt>. This class only serves the purpose
 * of holding multiple <tt>GUIEvents</tt> in a <tt>GUIItem</tt> and activating the events
 * when a player clicks on the <tt>GUIItem</tt>.
 *
 * @author Mikedeejay2
 */
public class GUIItemEvent implements Cloneable
{
    // A list of GUIEvents that this GUIItemEvent holds
    List<GUIEvent> events;

    public GUIItemEvent()
    {
        events = new ArrayList<>();
    }

    /**
     * Called when a player clicks on the parent <tt>GUIItem</tt>. This method iterates
     * through all of the <tt>GUIEvents</tt> that are held in the <tt>events</tt> list
     * and runs their <tt>execute()</tt> method.
     *
     * @param player The player that clicked the item
     * @param row The row that the item was clicked on
     * @param col The column that the item was clicked on
     * @param clicked the <tt>GUIItem</tt> that was clicked
     * @param gui The GUI that this event took place in
     */
    public void execute(Player player, int row, int col, GUIItem clicked, GUIContainer gui)
    {
        for(int i = 0; i < events.size(); i++)
        {
            GUIEvent event = events.get(i);
            event.execute(player, row, col, clicked, gui);
        }
    }

    /**
     * Get a list of the events that this object holds
     *
     * @return All events being stored
     */
    public List<GUIEvent> getEvents()
    {
        return events;
    }

    public void setEvents(List<GUIEvent> events)
    {
        this.events = events;
    }

    /**
     * Add a <tt>GUIEvent</tt> to this object that will be activated upon click
     *
     * @param event The event to add
     */
    public void addEvent(GUIEvent event)
    {
        events.add(event);
    }

    /**
     * Remove an event via instance of the event
     *
     * @param event The event to be removed
     */
    public void removeEvent(GUIEvent event)
    {
        events.remove(event);
    }

    /**
     * Returns whether this object holds the <tt>GUIEvent</tt>
     *
     * @param event The event to check for
     * @return Whether this object holds the event
     */
    public boolean containsEvent(GUIEvent event)
    {
        return events.contains(event);
    }

    /**
     * Remove an event via the event's class
     *
     * @param eventClass The class of the event that should be removed
     */
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

    /**
     * Returns whether this object holds a <tt>GUIEvent</tt> of the specified class
     *
     * @param eventClass The event class to search for
     * @return Whether this object holds the event
     */
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

    @Override
    public GUIItemEvent clone()
    {
        GUIItemEvent newEvents = null;
        try
        {
            newEvents = (GUIItemEvent) super.clone();
        }
        catch(CloneNotSupportedException e)
        {
            e.printStackTrace();
        }
        return newEvents;
    }
}
