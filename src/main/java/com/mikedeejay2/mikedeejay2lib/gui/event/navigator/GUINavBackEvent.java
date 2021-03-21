package com.mikedeejay2.mikedeejay2lib.gui.event.navigator;

import com.mikedeejay2.mikedeejay2lib.PluginBase;
import com.mikedeejay2.mikedeejay2lib.gui.GUIContainer;
import com.mikedeejay2.mikedeejay2lib.gui.event.GUIEvent;
import com.mikedeejay2.mikedeejay2lib.gui.manager.NavigationSystem;
import com.mikedeejay2.mikedeejay2lib.gui.manager.PlayerGUI;
import com.mikedeejay2.mikedeejay2lib.gui.modules.navigation.GUINavigatorModule;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;

/**
 * Navigate the GUI back one GUI.
 *
 * @author Mikedeejay2
 */
public class GUINavBackEvent implements GUIEvent
{
    protected final PluginBase plugin;

    public GUINavBackEvent(PluginBase plugin)
    {
        this.plugin = plugin;
    }

    @Override
    public void execute(InventoryClickEvent event, GUIContainer gui)
    {
        Player player = (Player) event.getWhoClicked();
        ClickType clickType = event.getClick();
        if(clickType != ClickType.LEFT) return;
        GUINavigatorModule module = gui.getModule(GUINavigatorModule.class);
        PlayerGUI playerGUI = plugin.getGUIManager().getPlayer(player);
        NavigationSystem system = playerGUI.getNaviSystem(module.getNavigationID());
        GUIContainer backGUI = system.getBack();
        system.removeBack();
        system.addForward(gui);
        system.setFlag(true);
        backGUI.open(player);
    }
}