package com.mikedeejay2.mikedeejay2lib.gui.event.navigator;

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
 * Navigate the GUI forward one GUI.
 *
 * @author Mikedeejay2
 */
public class GUINavForwardEvent implements GUIEvent
{
    protected final PluginBase plugin;

    public GUINavForwardEvent(PluginBase plugin)
    {
        this.plugin = plugin;
    }

    @Override
    public void execute(Player player, int row, int col, GUIItem clicked, GUIContainer gui, InventoryAction action, ClickType clickType)
    {
        GUINavigatorModule module = gui.getModule(GUINavigatorModule.class);
        PlayerGUI playerGUI = plugin.guiManager().getPlayer(player);
        NavigationSystem system = playerGUI.getNaviSystem(module.getNavigationID());
        GUIContainer forwardGUI = system.getForward();
        system.removeForward();
        system.addBack(gui);
        playerGUI.setGUI(forwardGUI);
    }
}
