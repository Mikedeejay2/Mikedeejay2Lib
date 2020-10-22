package com.mikedeejay2.mikedeejay2lib.gui.event.navigation;

import com.mikedeejay2.mikedeejay2lib.PluginBase;
import com.mikedeejay2.mikedeejay2lib.gui.GUIContainer;
import com.mikedeejay2.mikedeejay2lib.gui.event.GUIEvent;
import com.mikedeejay2.mikedeejay2lib.gui.item.GUIItem;
import com.mikedeejay2.mikedeejay2lib.gui.manager.NavigationSystem;
import com.mikedeejay2.mikedeejay2lib.gui.manager.PlayerGUI;
import com.mikedeejay2.mikedeejay2lib.gui.modules.navigation.GUINavigatorModule;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;

/**
 * Open a player's GUI to a specified GUI
 *
 * @author Mikedeejay2
 */
public class GUIOpenEvent implements GUIEvent
{
    protected final PluginBase plugin;

    // The GUI that will be opened
    private GUIContainer guiToOpen;

    public GUIOpenEvent(PluginBase plugin, GUIContainer gui)
    {
        this.plugin = plugin;
        this.guiToOpen = gui;
    }

    @Override
    public void execute(Player player, int row, int col, GUIItem clicked, GUIContainer gui, InventoryAction action, ClickType clickType)
    {
        if(clickType != ClickType.LEFT) return;
        if(gui.equals(guiToOpen)) return;
        PlayerGUI playerGUI = plugin.guiManager().getPlayer(player);
        navigationCheck(gui, playerGUI);
        playerGUI.setGUI(guiToOpen);
    }

    /**
     * Checks whether the GUI is using a navigation system and if so calculate the forward
     * and back navigations.
     *
     * @param gui The GUI that is being viewed
     * @param playerGUI The <tt>PlayerGUI</tt> of the player viewing the GUI
     */
    private void navigationCheck(GUIContainer gui, PlayerGUI playerGUI)
    {
        if(!(guiToOpen.containsModule(GUINavigatorModule.class) && gui.containsModule(GUINavigatorModule.class))) return;
        GUINavigatorModule curModule = gui.getModule(GUINavigatorModule.class);
        GUINavigatorModule openModule = gui.getModule(GUINavigatorModule.class);
        String curID = curModule.getNavigationID();
        String openID = openModule.getNavigationID();
        if(!curID.equals(openID)) return;
        NavigationSystem system = playerGUI.getNaviSystem(curID);
        if(system.hasBack() && system.getBack().equals(gui)) return;
        system.addBack(gui);
        if(!system.hasForward()) return;
        system.resetForward();
    }
}
