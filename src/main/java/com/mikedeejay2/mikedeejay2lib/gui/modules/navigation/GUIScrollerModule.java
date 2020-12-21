package com.mikedeejay2.mikedeejay2lib.gui.modules.navigation;

import com.mikedeejay2.mikedeejay2lib.PluginBase;
import com.mikedeejay2.mikedeejay2lib.gui.GUIContainer;
import com.mikedeejay2.mikedeejay2lib.gui.GUILayer;
import com.mikedeejay2.mikedeejay2lib.gui.event.navigator.GUIScrollEvent;
import com.mikedeejay2.mikedeejay2lib.gui.item.AnimatedGUIItem;
import com.mikedeejay2.mikedeejay2lib.gui.item.GUIItem;
import com.mikedeejay2.mikedeejay2lib.gui.modules.GUIModule;
import com.mikedeejay2.mikedeejay2lib.util.head.Base64Heads;
import com.mikedeejay2.mikedeejay2lib.util.item.ItemCreator;
import org.bukkit.entity.Player;

public class GUIScrollerModule extends GUIModule
{
    protected final PluginBase plugin;

    protected GUIItem upItem;
    protected GUIItem downItem;
    protected GUIItem leftItem;
    protected GUIItem rightItem;

    public GUIScrollerModule(PluginBase plugin)
    {
        this.plugin = plugin;
    }

    @Override
    public void onOpenHead(Player player, GUIContainer gui)
    {
        if(upItem == null)
        {
            String name = plugin.langManager().getTextLib(player, "gui.modules.scroller.up");
            this.upItem = new AnimatedGUIItem(ItemCreator.createHeadItem(Base64Heads.ARROW_UP_WHITE, 1, "&f" + name), false, 1, true);
            upItem.addEvent(new GUIScrollEvent(-1, 0));
            AnimatedGUIItem upItemAnim = (AnimatedGUIItem) upItem;
            upItemAnim.addFrame(ItemCreator.createHeadItem(Base64Heads.ARROW_UP_LIGHT_GRAY, 1, "&f" + name), 1);
            upItemAnim.addFrame(ItemCreator.createHeadItem(Base64Heads.ARROW_UP_WHITE, 1, "&f" + name), 1);
            upItemAnim.setStartingIndex(1);
        }

        if(downItem == null)
        {
            String name = plugin.langManager().getTextLib(player, "gui.modules.scroller.down");
            this.downItem = new AnimatedGUIItem(ItemCreator.createHeadItem(Base64Heads.ARROW_DOWN_WHITE, 1, "&f" + name), false, 1, true);
            downItem.addEvent(new GUIScrollEvent(1, 0));
            AnimatedGUIItem downItemAnim = (AnimatedGUIItem) downItem;
            downItemAnim.addFrame(ItemCreator.createHeadItem(Base64Heads.ARROW_DOWN_LIGHT_GRAY, 1, "&f" + name), 1);
            downItemAnim.addFrame(ItemCreator.createHeadItem(Base64Heads.ARROW_DOWN_WHITE, 1, "&f" + name), 1);
            downItemAnim.setStartingIndex(1);
        }

        if(leftItem == null)
        {
            String name = plugin.langManager().getTextLib(player, "gui.modules.scroller.left");
            this.leftItem = new AnimatedGUIItem(ItemCreator.createHeadItem(Base64Heads.ARROW_LEFT_WHITE, 1, "&f" + name), false, 1, true);
            leftItem.addEvent(new GUIScrollEvent(0, -1));
            AnimatedGUIItem leftItemAnim = (AnimatedGUIItem) leftItem;
            leftItemAnim.addFrame(ItemCreator.createHeadItem(Base64Heads.ARROW_LEFT_LIGHT_GRAY, 1, "&f" + name), 1);
            leftItemAnim.addFrame(ItemCreator.createHeadItem(Base64Heads.ARROW_LEFT_WHITE, 1, "&f" + name), 1);
            leftItemAnim.setStartingIndex(1);
        }

        if(rightItem == null)
        {
            String name = plugin.langManager().getTextLib(player, "gui.modules.scroller.right");
            this.rightItem = new AnimatedGUIItem(ItemCreator.createHeadItem(Base64Heads.ARROW_RIGHT_WHITE, 1, "&f" + name), false, 1, true);
            rightItem.addEvent(new GUIScrollEvent(0, 1));
            AnimatedGUIItem rightItemAnim = (AnimatedGUIItem) rightItem;
            rightItemAnim.addFrame(ItemCreator.createHeadItem(Base64Heads.ARROW_RIGHT_LIGHT_GRAY, 1, "&f" + name), 1);
            rightItemAnim.addFrame(ItemCreator.createHeadItem(Base64Heads.ARROW_RIGHT_WHITE, 1, "&f" + name), 1);
            rightItemAnim.setStartingIndex(1);
        }
    }

    @Override
    public void onUpdateHead(Player player, GUIContainer gui)
    {
        GUILayer layer = gui.getLayer("overlay", true);
        int row = Math.min(gui.getRows(), GUIContainer.MAX_INVENTORY_ROWS);
        layer.setItem(row, 6, leftItem);
        layer.setItem(row, 7, rightItem);
        layer.setItem(row, 3, upItem);
        layer.setItem(row, 4, downItem);
    }
}
