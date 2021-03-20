package com.mikedeejay2.mikedeejay2lib.gui.modules.navigation;

import com.mikedeejay2.mikedeejay2lib.PluginBase;
import com.mikedeejay2.mikedeejay2lib.gui.GUIContainer;
import com.mikedeejay2.mikedeejay2lib.gui.GUILayer;
import com.mikedeejay2.mikedeejay2lib.gui.event.navigator.GUIScrollEvent;
import com.mikedeejay2.mikedeejay2lib.gui.item.AnimatedGUIItem;
import com.mikedeejay2.mikedeejay2lib.gui.item.GUIItem;
import com.mikedeejay2.mikedeejay2lib.gui.modules.GUIModule;
import com.mikedeejay2.mikedeejay2lib.item.ItemBuilder;
import com.mikedeejay2.mikedeejay2lib.util.head.Base64Head;
import org.bukkit.entity.Player;

public class GUIScrollerModule implements GUIModule
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
            String name = plugin.getLibLangManager().getText(player, "gui.modules.scroller.up");
            this.upItem = new AnimatedGUIItem(ItemBuilder.of(Base64Head.ARROW_UP_WHITE.get()).setName("&f" + name).get(), false, 1, true);
            upItem.addEvent(new GUIScrollEvent(-1, 0));
            AnimatedGUIItem upItemAnim = (AnimatedGUIItem) upItem;
            upItemAnim.addFrame(ItemBuilder.of(Base64Head.ARROW_UP_LIGHT_GRAY.get()).setName("&f" + name).get(), 1);
            upItemAnim.addFrame(ItemBuilder.of(Base64Head.ARROW_UP_WHITE.get()).setName("&f" + name).get(), 1);
            upItemAnim.setStartingIndex(1);
        }

        if(downItem == null)
        {
            String name = plugin.getLibLangManager().getText(player, "gui.modules.scroller.down");
            this.downItem = new AnimatedGUIItem(ItemBuilder.of(Base64Head.ARROW_DOWN_WHITE.get()).setName("&f" + name).get(), false, 1, true);
            downItem.addEvent(new GUIScrollEvent(1, 0));
            AnimatedGUIItem downItemAnim = (AnimatedGUIItem) downItem;
            downItemAnim.addFrame(ItemBuilder.of(Base64Head.ARROW_DOWN_LIGHT_GRAY.get()).setName("&f" + name).get(), 1);
            downItemAnim.addFrame(ItemBuilder.of(Base64Head.ARROW_DOWN_WHITE.get()).setName("&f" + name).get(), 1);
            downItemAnim.setStartingIndex(1);
        }

        if(leftItem == null)
        {
            String name = plugin.getLibLangManager().getText(player, "gui.modules.scroller.left");
            this.leftItem = new AnimatedGUIItem(ItemBuilder.of(Base64Head.ARROW_LEFT_WHITE.get()).setName("&f" + name).get(), false, 1, true);
            leftItem.addEvent(new GUIScrollEvent(0, -1));
            AnimatedGUIItem leftItemAnim = (AnimatedGUIItem) leftItem;
            leftItemAnim.addFrame(ItemBuilder.of(Base64Head.ARROW_LEFT_LIGHT_GRAY.get()).setName("&f" + name).get(), 1);
            leftItemAnim.addFrame(ItemBuilder.of(Base64Head.ARROW_LEFT_WHITE.get()).setName("&f" + name).get(), 1);
            leftItemAnim.setStartingIndex(1);
        }

        if(rightItem == null)
        {
            String name = plugin.getLibLangManager().getText(player, "gui.modules.scroller.right");
            this.rightItem = new AnimatedGUIItem(ItemBuilder.of(Base64Head.ARROW_RIGHT_WHITE.get()).setName("&f" + name).get(), false, 1, true);
            rightItem.addEvent(new GUIScrollEvent(0, 1));
            AnimatedGUIItem rightItemAnim = (AnimatedGUIItem) rightItem;
            rightItemAnim.addFrame(ItemBuilder.of(Base64Head.ARROW_RIGHT_LIGHT_GRAY.get()).setName("&f" + name).get(), 1);
            rightItemAnim.addFrame(ItemBuilder.of(Base64Head.ARROW_RIGHT_WHITE.get()).setName("&f" + name).get(), 1);
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
