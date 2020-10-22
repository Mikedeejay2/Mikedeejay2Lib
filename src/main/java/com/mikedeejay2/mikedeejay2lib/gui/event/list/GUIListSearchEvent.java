package com.mikedeejay2.mikedeejay2lib.gui.event.list;

import com.mikedeejay2.mikedeejay2lib.PluginBase;
import com.mikedeejay2.mikedeejay2lib.gui.GUIContainer;
import com.mikedeejay2.mikedeejay2lib.gui.event.GUIEvent;
import com.mikedeejay2.mikedeejay2lib.gui.item.GUIItem;
import com.mikedeejay2.mikedeejay2lib.gui.modules.GUIListModule;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;

/**
 * An event that prompts the user to search for something in a list GUI
 *
 * @author Mikedeejay2
 */
public class GUIListSearchEvent implements GUIEvent
{
    protected final PluginBase plugin;

    public GUIListSearchEvent(PluginBase plugin)
    {
        this.plugin = plugin;
    }

    @Override
    public void execute(Player player, int row, int col, GUIItem clicked, GUIContainer gui, InventoryAction action, ClickType clickType)
    {
        if(clickType != ClickType.LEFT) return;
        plugin.guiManager().getPlayer(player).closeGUI();
        player.closeInventory();
        // TODO: Open a book and quill here for the player to type in their search
        // After that,
        GUIListModule list = gui.getModule(GUIListModule.class);
        list.enableSearchMode("search term");
        gui.update(player);
    }
}
