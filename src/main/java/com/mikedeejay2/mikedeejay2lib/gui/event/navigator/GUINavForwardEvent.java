package com.mikedeejay2.mikedeejay2lib.gui.event.navigator;

import com.mikedeejay2.mikedeejay2lib.BukkitPlugin;
import com.mikedeejay2.mikedeejay2lib.gui.GUIContainer;
import com.mikedeejay2.mikedeejay2lib.gui.event.GUIEvent;
import com.mikedeejay2.mikedeejay2lib.gui.manager.PlayerGUI;
import com.mikedeejay2.mikedeejay2lib.gui.modules.navigation.GUINavigatorModule;
import com.mikedeejay2.mikedeejay2lib.util.structure.NavigationHolder;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;

/**
 * Navigate the GUI forward one GUI.
 *
 * @author Mikedeejay2
 */
public class GUINavForwardEvent implements GUIEvent
{
    protected final BukkitPlugin plugin;

    public GUINavForwardEvent(BukkitPlugin plugin)
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
        NavigationHolder<GUIContainer> system = playerGUI.getNavigation(module.getNavigationID());
        GUIContainer forwardGUI = system.popForward();
        system.pushBack(gui);
        system.setNavigationFlag(true);
        forwardGUI.open(player);
    }
}
