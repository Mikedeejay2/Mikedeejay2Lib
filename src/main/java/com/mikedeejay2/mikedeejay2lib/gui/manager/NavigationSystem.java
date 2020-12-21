package com.mikedeejay2.mikedeejay2lib.gui.manager;

import com.mikedeejay2.mikedeejay2lib.gui.GUIContainer;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * Holds a web browser style navigation system that holds GUIs behind and forward
 * This class shouldn't be initialized on its own, instead use {@link PlayerGUI#getNaviSystem(String)}
 *
 * @author Mikedeejay2
 */
public class NavigationSystem
{
    // The GUIs behind the current GUI
    protected List<GUIContainer> back;
    // The GUIs ahead of the current GUI
    protected List<GUIContainer> forward;
    // The player that owns this NavigationSystem
    protected Player player;
    // The navigation ID of this system
    protected String navigationID;

    public NavigationSystem(Player player, String navigationID)
    {
        this.player = player;
        this.navigationID = navigationID;

        this.back = new ArrayList<>();
        this.forward = new ArrayList<>();
    }

    /**
     * Returns whether this navigation system is storing
     * previous GUIs
     *
     * @return If this system has back GUIs
     */
    public boolean hasBack()
    {
        return !back.isEmpty();
    }

    /**
     * Returns whether this navigation system is storing
     * forward GUIs
     *
     * @return If this system has forward GUIs
     */
    public boolean hasForward()
    {
        return !forward.isEmpty();
    }

    /**
     * Get the amount of GUIs back
     *
     * @return The amount of GUIs back
     */
    public long getAmountBack()
    {
        return back.size();
    }

    /**
     * Get the amount of GUIs forward
     *
     * @return The amount of GUIs forward
     */
    public long getAmountForward()
    {
        return forward.size();
    }

    /**
     * Clear the back GUIs
     */
    public void resetBack()
    {
        back.clear();
    }

    /**
     * Clear the forward GUIs
     */
    public void resetForward()
    {
        forward.clear();
    }

    /**
     * Get the closest GUI back
     *
     * @return The closest back GUI
     */
    public GUIContainer getBack()
    {
        return back.get(0);
    }

    /**
     * Get the closest GUI forward
     *
     * @return The closest forward GUI
     */
    public GUIContainer getForward()
    {
        return forward.get(0);
    }

    /**
     * Remove the closest GUI back
     */
    public void removeBack()
    {
        back.remove(0);
    }

    /**
     * Remove the closest GUI forward
     */
    public void removeForward()
    {
        forward.remove(0);
    }

    /**
     * Get the player owning this <tt>NavigationSystem</tt>
     *
     * @return The player
     */
    public Player getPlayer()
    {
        return player;
    }

    /**
     * Get this <tt>NavigationSystems</tt> ID
     *
     * @return This system's ID
     */
    public String getNavigationID()
    {
        return navigationID;
    }

    /**
     * Get the list of back GUIs
     *
     * @return The list of back GUIs
     */
    public List<GUIContainer> getBackList()
    {
        return back;
    }

    /**
     * Get the list of forward GUIs
     *
     * @return The list of forward GUIs
     */
    public List<GUIContainer> getForwardList()
    {
        return forward;
    }

    /**
     * Add a GUI to the back list
     *
     * @param gui The GUI to add
     */
    public void addBack(GUIContainer gui)
    {
        if(hasBack() && getBack().equals(gui)) return;
        back.add(0, gui);
    }

    /**
     * Add a GUI to the forward list
     *
     * @param gui The GUI to add
     */
    public void addForward(GUIContainer gui)
    {
        if(hasForward() && getForward().equals(gui)) return;
        forward.add(0, gui);
    }
}
