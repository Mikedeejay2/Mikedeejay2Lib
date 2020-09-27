package com.mikedeejay2.mikedeejay2lib.gui.event.list;

import com.mikedeejay2.mikedeejay2lib.PluginBase;
import com.mikedeejay2.mikedeejay2lib.gui.GUIContainer;
import com.mikedeejay2.mikedeejay2lib.gui.event.GUIEvent;
import com.mikedeejay2.mikedeejay2lib.gui.item.GUIItem;
import com.mikedeejay2.mikedeejay2lib.util.chat.Chat;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class GUIListSearchEvent extends GUIEvent
{
    public GUIListSearchEvent(PluginBase plugin)
    {
        super(plugin);
    }

    @Override
    public void execute(Player player, int row, int col, GUIItem clicked, GUIContainer gui)
    {
        Chat chat = plugin.chat();
        player.closeInventory();
        BaseComponent[] components1 = chat.getBaseComponentArray(chat.getTitleString());
        BaseComponent[] components2 = chat.getBaseComponentArray("&a&nClick Here");
        BaseComponent[] components3 = chat.getBaseComponentArray("&f to search the current GUI.");
        chat.setClickEvent(components2, chat.getClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/" + plugin.commandManager().getBaseCommand() + " input search "));

        chat.printComponents(player, components1, components2, components3);
    }
}
