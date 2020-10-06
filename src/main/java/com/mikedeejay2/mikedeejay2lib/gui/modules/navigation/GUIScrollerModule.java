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
    protected GUIItem upItem;
    protected GUIItem downItem;
    protected GUIItem leftItem;
    protected GUIItem rightItem;

    public GUIScrollerModule(PluginBase plugin)
    {
        super(plugin);

        this.upItem = new AnimatedGUIItem(ItemCreator.createHeadItem(Base64Heads.ARROW_UP_WHITE, 1, "Up"), false, 1, true);
        upItem.addEvent(new GUIScrollEvent(plugin, -1, 0));
        AnimatedGUIItem upItemAnim = (AnimatedGUIItem)upItem;
        upItemAnim.addFrame(ItemCreator.createHeadItem(Base64Heads.ARROW_UP_LIGHT_GRAY, 1, "Up"), 1);
        upItemAnim.addFrame(ItemCreator.createHeadItem(Base64Heads.ARROW_UP_WHITE, 1, "Up"), 1);
        upItemAnim.setIndex(1);

        this.downItem = new AnimatedGUIItem(ItemCreator.createHeadItem(Base64Heads.ARROW_DOWN_WHITE, 1, "Down"), false, 1, true);
        downItem.addEvent(new GUIScrollEvent(plugin, 1, 0));
        AnimatedGUIItem downItemAnim = (AnimatedGUIItem)downItem;
        downItemAnim.addFrame(ItemCreator.createHeadItem(Base64Heads.ARROW_DOWN_LIGHT_GRAY, 1, "Down"), 1);
        downItemAnim.addFrame(ItemCreator.createHeadItem(Base64Heads.ARROW_DOWN_WHITE, 1, "Down"), 1);
        downItemAnim.setIndex(1);

        this.leftItem = new AnimatedGUIItem(ItemCreator.createHeadItem(Base64Heads.ARROW_LEFT_WHITE, 1, "Left"), false, 1, true);
        leftItem.addEvent(new GUIScrollEvent(plugin, 0, -1));
        AnimatedGUIItem leftItemAnim = (AnimatedGUIItem)leftItem;
        leftItemAnim.addFrame(ItemCreator.createHeadItem(Base64Heads.ARROW_LEFT_LIGHT_GRAY, 1, "Left"), 1);
        leftItemAnim.addFrame(ItemCreator.createHeadItem(Base64Heads.ARROW_LEFT_WHITE, 1, "Left"), 1);
        leftItemAnim.setIndex(1);

        this.rightItem = new AnimatedGUIItem(ItemCreator.createHeadItem(Base64Heads.ARROW_RIGHT_WHITE, 1, "Right"), false, 1, true);
        rightItem.addEvent(new GUIScrollEvent(plugin, 0, 1));
        AnimatedGUIItem rightItemAnim = (AnimatedGUIItem)rightItem;
        rightItemAnim.addFrame(ItemCreator.createHeadItem(Base64Heads.ARROW_RIGHT_LIGHT_GRAY, 1, "Right"), 1);
        rightItemAnim.addFrame(ItemCreator.createHeadItem(Base64Heads.ARROW_RIGHT_WHITE, 1, "Right"), 1);
        rightItemAnim.setIndex(1);
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
