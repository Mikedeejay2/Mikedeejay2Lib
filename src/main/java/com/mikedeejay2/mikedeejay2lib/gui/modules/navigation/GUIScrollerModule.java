package com.mikedeejay2.mikedeejay2lib.gui.modules.navigation;

import com.mikedeejay2.mikedeejay2lib.PluginBase;
import com.mikedeejay2.mikedeejay2lib.gui.GUIContainer;
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
        this.downItem = new GUIItem(ItemCreator.createHeadItem(Base64Heads.ARROW_DOWN_WHITE, 1, "Down"));
        this.leftItem = new GUIItem(ItemCreator.createHeadItem(Base64Heads.ARROW_LEFT_WHITE, 1, "Left"));
        this.rightItem = new GUIItem(ItemCreator.createHeadItem(Base64Heads.ARROW_RIGHT_WHITE, 1, "Right"));
    }

    @Override
    public void onUpdateHead(Player player, GUIContainer gui)
    {

    }
}
