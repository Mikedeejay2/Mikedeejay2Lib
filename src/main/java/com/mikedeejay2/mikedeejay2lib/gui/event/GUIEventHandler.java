package com.mikedeejay2.mikedeejay2lib.gui.event;

import com.mikedeejay2.mikedeejay2lib.gui.GUIContainer;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * Holds all <code>GUIEvents</code> in a <code>GUIItem</code>. This class only serves the purpose
 * of holding multiple <code>GUIEvents</code> in a <code>GUIItem</code> and activating the events
 * when a player clicks on the <code>GUIItem</code>.
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
     * Called when a player clicks on the parent <code>GUIItem</code>. This method iterates
     * through all of the <code>GUIEvents</code> that are held in the <code>events</code> list
     * and runs their <code>execute()</code> method.
     *
     * @param event The event of the click
     * @param gui   The GUI that this event took place in
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
     * Add a <code>GUIEvent</code> to this object that will be activated upon click
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
     * Returns whether this object holds the <code>GUIEvent</code>
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
        for(GUIEvent event : events)
        {
            if(eventClass != event.getClass()) continue;
            events.remove(event);
            break;
        }
    }

    /**
     * Returns whether this object holds a <code>GUIEvent</code> of the specified class
     *
     * @param eventClass The event class to search for
     * @return Whether this object holds the event
     */
    public boolean containsEvent(Class<? extends GUIEvent> eventClass)
    {
        for(GUIEvent event : events)
        {
            if(eventClass != event.getClass()) continue;
            return true;
        }
        return false;
    }

    /**
     * Attempts to get a <code>GUIEvent</code> of the specified class
     *
     * @param eventClass The event class to search for
     * @param <T> The <code>GUIEvent</code> type, from given class
     * @return The <code>GUIEvent</code> of the class type if it exists, null if it doesn't
     */
    public <T extends GUIEvent> T getEvent(Class<T> eventClass)
    {
        for(GUIEvent event : events)
        {
            if(eventClass != event.getClass()) continue;
            return (T) event;
        }
        return null;
    }

    /**
     * Get a <code>GUIEvent</code> based on the index that it's located in
     *
     * @param index The index of the event to get
     * @return The <code>GUIEvent</code> contained within the index
     */
    public GUIEvent getEvent(int index)
    {
        return events.get(index);
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
