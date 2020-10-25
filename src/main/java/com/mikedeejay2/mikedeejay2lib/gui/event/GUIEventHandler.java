package com.mikedeejay2.mikedeejay2lib.gui.event;

import com.mikedeejay2.mikedeejay2lib.gui.GUIContainer;
import com.mikedeejay2.mikedeejay2lib.gui.item.GUIItem;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * Holds all <tt>GUIEvents</tt> in a <tt>GUIItem</tt>. This class only serves the purpose
 * of holding multiple <tt>GUIEvents</tt> in a <tt>GUIItem</tt> and activating the events
 * when a player clicks on the <tt>GUIItem</tt>.
 *
 * @author Mikedeejay2
 */
public class GUIEventHandler implements Cloneable
{
    // A list of GUIEvents that this GUIEventHandler holds
    List<GUIEvent> events;

    public GUIEventHandler()
    {
        events = new ArrayList<>();
    }

    /**
     * Called when a player clicks on the parent <tt>GUIItem</tt>. This method iterates
     * through all of the <tt>GUIEvents</tt> that are held in the <tt>events</tt> list
     * and runs their <tt>execute()</tt> method.
     *
     * @param event The event of the click
     * @param gui The GUI that this event took place in
     */
    public void execute(InventoryClickEvent event, GUIContainer gui)
    {
        for(int i = 0; i < events.size(); i++)
        {
            GUIEvent guiEvent = events.get(i);
            guiEvent.execute(event, gui);
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
    public GUIEventHandler clone()
    {
        GUIEventHandler newEvents = null;
        try
        {
            newEvents = (GUIEventHandler) super.clone();
        }
        catch(CloneNotSupportedException e)
        {
            e.printStackTrace();
        }
        return newEvents;
    }
}
