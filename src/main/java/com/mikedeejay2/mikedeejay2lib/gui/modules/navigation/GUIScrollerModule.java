package com.mikedeejay2.mikedeejay2lib.gui.modules.navigation;

import com.mikedeejay2.mikedeejay2lib.PluginBase;
import com.mikedeejay2.mikedeejay2lib.gui.GUIContainer;
import com.mikedeejay2.mikedeejay2lib.gui.GUILayer;
import com.mikedeejay2.mikedeejay2lib.gui.event.navigator.GUIScrollEvent;
import com.mikedeejay2.mikedeejay2lib.gui.item.GUIItem;
import com.mikedeejay2.mikedeejay2lib.gui.modules.GUIModule;
import com.mikedeejay2.mikedeejay2lib.util.head.Base64Heads;
import com.mikedeejay2.mikedeejay2lib.util.item.ItemCreator;
import org.bukkit.entity.Player;

public class GUIScrollerModule extends GUIModule
{
    protected GUIItem upItem;
    protected GUIItem downItem;
    protected GUIItem leftItem;
    protected GUIItem rightItem;

    public GUIScrollerModule(PluginBase plugin)
    {
        super(plugin);

        this.upItem = new GUIItem(ItemCreator.createHeadItem(Base64Heads.ARROW_UP_WHITE, 1, "Up"));
        upItem.addEvent(new GUIScrollEvent(plugin, -1, 0));
        this.downItem = new GUIItem(ItemCreator.createHeadItem(Base64Heads.ARROW_DOWN_WHITE, 1, "Down"));
        downItem.addEvent(new GUIScrollEvent(plugin, 1, 0));
        this.leftItem = new GUIItem(ItemCreator.createHeadItem(Base64Heads.ARROW_LEFT_WHITE, 1, "Left"));
        leftItem.addEvent(new GUIScrollEvent(plugin, 0, -1));
        this.rightItem = new GUIItem(ItemCreator.createHeadItem(Base64Heads.ARROW_RIGHT_WHITE, 1, "Right"));
        rightItem.addEvent(new GUIScrollEvent(plugin, 0, 1));
    }

    @Override
    public void onUpdateHead(Player player, GUIContainer gui)
    {
        GUILayer layer = gui.getLayer("overlay", true);
        int row = Math.min(gui.getRows(), GUIContainer.MAX_INVENTORY_ROWS);
        layer.setItem(row, 1, leftItem);
        layer.setItem(row, 2, rightItem);
        layer.setItem(row, 8, upItem);
        layer.setItem(row, 9, downItem);
        plugin.chat().debug("Row: " + row);
    }
}
