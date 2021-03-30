package com.mikedeejay2.mikedeejay2lib.gui.event.list;

import com.mikedeejay2.mikedeejay2lib.BukkitPlugin;
import com.mikedeejay2.mikedeejay2lib.gui.GUIContainer;
import com.mikedeejay2.mikedeejay2lib.gui.event.GUIEvent;
import com.mikedeejay2.mikedeejay2lib.gui.modules.list.GUIListModule;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;

/**
 * An event that prompts the user to search for something in a list GUI
 *
 * @author Mikedeejay2
 */
public class GUIListSearchEvent implements GUIEvent
{
    protected final BukkitPlugin plugin;

    public GUIListSearchEvent(BukkitPlugin plugin)
    {
        this.plugin = plugin;
    }

    @Override
    public void execute(InventoryClickEvent event, GUIContainer gui)
    {
        Player player = (Player) event.getWhoClicked();
        ClickType clickType = event.getClick();
        if(clickType != ClickType.LEFT) return;
        gui.close(player);
        player.closeInventory();
        // TODO: Use chat event in GUIListener to capture search result
        GUIListModule list = gui.getModule(GUIListModule.class);
        list.enableSearchMode("search term");
    }
}
