package com.mikedeejay2.mikedeejay2lib.gui.event.list;

import com.mikedeejay2.mikedeejay2lib.PluginBase;
import com.mikedeejay2.mikedeejay2lib.gui.GUIContainer;
import com.mikedeejay2.mikedeejay2lib.gui.event.GUIEvent;
import com.mikedeejay2.mikedeejay2lib.gui.item.GUIItem;
import com.mikedeejay2.mikedeejay2lib.gui.modules.GUIListModule;
import org.bukkit.entity.Player;

public class GUIListSearchEvent extends GUIEvent
{
    public GUIListSearchEvent(PluginBase plugin)
    {
        super(plugin);
    }

    @Override
    public void execute(Player player, int row, int col, GUIItem clicked, GUIContainer gui)
    {
        plugin.guiManager().getPlayer(player).closeGUI();
        // TODO: Open a book and quill here for the player to type in their search
        // After that,
        GUIListModule list = gui.getModule(GUIListModule.class);
        list.enableSearchMode("search term");
        gui.update(player);
    }
}
