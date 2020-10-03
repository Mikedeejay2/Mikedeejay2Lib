package com.mikedeejay2.mikedeejay2lib.gui.event.navigator;

import com.mikedeejay2.mikedeejay2lib.PluginBase;
import com.mikedeejay2.mikedeejay2lib.gui.GUIContainer;
import com.mikedeejay2.mikedeejay2lib.gui.event.GUIEvent;
import com.mikedeejay2.mikedeejay2lib.gui.item.GUIItem;
import com.mikedeejay2.mikedeejay2lib.gui.manager.NavigationSystem;
import com.mikedeejay2.mikedeejay2lib.gui.manager.PlayerGUI;
import com.mikedeejay2.mikedeejay2lib.gui.modules.GUINavigatorModule;
import org.bukkit.entity.Player;

/**
 * Navigate the GUI forward one GUI.
 *
 * @author Mikedeejay2
 */
public class GUINavForwardEvent extends GUIEvent
{
    public GUINavForwardEvent(PluginBase plugin)
    {
        super(plugin);
    }

    @Override
    public void execute(Player player, int row, int col, GUIItem clicked, GUIContainer gui)
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
