package com.mikedeejay2.mikedeejay2lib.gui.modules.navigation;

import com.mikedeejay2.mikedeejay2lib.BukkitPlugin;
import com.mikedeejay2.mikedeejay2lib.gui.GUIContainer;
import com.mikedeejay2.mikedeejay2lib.gui.GUILayer;
import com.mikedeejay2.mikedeejay2lib.gui.event.GUIEvent;
import com.mikedeejay2.mikedeejay2lib.gui.event.GUIEventInfo;
import com.mikedeejay2.mikedeejay2lib.gui.item.AnimatedGUIItem;
import com.mikedeejay2.mikedeejay2lib.gui.item.GUIItem;
import com.mikedeejay2.mikedeejay2lib.gui.modules.GUIModule;
import com.mikedeejay2.mikedeejay2lib.item.ItemBuilder;
import com.mikedeejay2.mikedeejay2lib.text.Text;
import com.mikedeejay2.mikedeejay2lib.util.head.Base64Head;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;

/**
 * A module that allows for the scrolling of large GUIs.
 *
 * @author Mikedeejay2
 */
public class GUIScrollerModule implements GUIModule {
    /**
     * The {@link BukkitPlugin} instance
     */
    protected final BukkitPlugin plugin;

    /**
     * The <code>GUIItem</code> representing the up arrow
     */
    protected GUIItem upItem;

    /**
     * The <code>GUIItem</code> representing the down arrow
     */
    protected GUIItem downItem;

    /**
     * The <code>GUIItem</code> representing the left arrow
     */
    protected GUIItem leftItem;

    /**
     * The <code>GUIItem</code> representing the right arrow
     */
    protected GUIItem rightItem;

    /**
     * Construct a new <code>GUIScrollerModule</code>
     *
     * @param plugin The {@link BukkitPlugin} instance
     */
    public GUIScrollerModule(BukkitPlugin plugin) {
        this.plugin = plugin;
    }

    /**
     * Generate the UI for the scroller when the GUI opens
     *
     * @param player The player that is viewing the GUI
     * @param gui    The GUI
     */
    @Override
    public void onOpenHead(Player player, GUIContainer gui) {
        if(upItem == null) {
            String name = Text.translatable("gui.modules.scroller.up").get(player);
            this.upItem = new AnimatedGUIItem(ItemBuilder.of(Base64Head.ARROW_UP_WHITE.get()).setName("&f" + name).get(), false, 1, true);
            upItem.addEvent(new GUIScrollEvent(-1, 0));
            AnimatedGUIItem upItemAnim = (AnimatedGUIItem) upItem;
            upItemAnim.addFrame(ItemBuilder.of(Base64Head.ARROW_UP_LIGHT_GRAY.get()).setName("&f" + name).get(), 1);
            upItemAnim.addFrame(ItemBuilder.of(Base64Head.ARROW_UP_WHITE.get()).setName("&f" + name).get(), 1);
            upItemAnim.setStartingIndex(1);
        }

        if(downItem == null) {
            String name = Text.translatable("gui.modules.scroller.down").get(player);
            this.downItem = new AnimatedGUIItem(ItemBuilder.of(Base64Head.ARROW_DOWN_WHITE.get()).setName("&f" + name).get(), false, 1, true);
            downItem.addEvent(new GUIScrollEvent(1, 0));
            AnimatedGUIItem downItemAnim = (AnimatedGUIItem) downItem;
            downItemAnim.addFrame(ItemBuilder.of(Base64Head.ARROW_DOWN_LIGHT_GRAY.get()).setName("&f" + name).get(), 1);
            downItemAnim.addFrame(ItemBuilder.of(Base64Head.ARROW_DOWN_WHITE.get()).setName("&f" + name).get(), 1);
            downItemAnim.setStartingIndex(1);
        }

        if(leftItem == null) {
            String name = Text.translatable("gui.modules.scroller.left").get(player);
            this.leftItem = new AnimatedGUIItem(ItemBuilder.of(Base64Head.ARROW_LEFT_WHITE.get()).setName("&f" + name).get(), false, 1, true);
            leftItem.addEvent(new GUIScrollEvent(0, -1));
            AnimatedGUIItem leftItemAnim = (AnimatedGUIItem) leftItem;
            leftItemAnim.addFrame(ItemBuilder.of(Base64Head.ARROW_LEFT_LIGHT_GRAY.get()).setName("&f" + name).get(), 1);
            leftItemAnim.addFrame(ItemBuilder.of(Base64Head.ARROW_LEFT_WHITE.get()).setName("&f" + name).get(), 1);
            leftItemAnim.setStartingIndex(1);
        }

        if(rightItem == null) {
            String name = Text.translatable("gui.modules.scroller.right").get(player);
            this.rightItem = new AnimatedGUIItem(ItemBuilder.of(Base64Head.ARROW_RIGHT_WHITE.get()).setName("&f" + name).get(), false, 1, true);
            rightItem.addEvent(new GUIScrollEvent(0, 1));
            AnimatedGUIItem rightItemAnim = (AnimatedGUIItem) rightItem;
            rightItemAnim.addFrame(ItemBuilder.of(Base64Head.ARROW_RIGHT_LIGHT_GRAY.get()).setName("&f" + name).get(), 1);
            rightItemAnim.addFrame(ItemBuilder.of(Base64Head.ARROW_RIGHT_WHITE.get()).setName("&f" + name).get(), 1);
            rightItemAnim.setStartingIndex(1);
        }
    }

    /**
     * Update scroller controls on update of the GUI
     *
     * @param player The player that is viewing the GUI
     * @param gui    The GUI
     */
    @Override
    public void onUpdateHead(Player player, GUIContainer gui) {
        GUILayer layer = gui.getLayer("overlay", true);
        int row = Math.min(gui.getRows(), GUIContainer.MAX_INVENTORY_ROWS);
        layer.setItem(row, 6, leftItem);
        layer.setItem(row, 7, rightItem);
        layer.setItem(row, 3, upItem);
        layer.setItem(row, 4, downItem);
    }


    /**
     * An event for scrolling a large GUI in a direction
     *
     * @author Mikedeejay2
     */
    public static class GUIScrollEvent implements GUIEvent {
        /**
         * The row amount to scroll on click
         */
        protected int rowAmt;

        /**
         * The column amount to scroll on click
         */
        protected int colAmt;

        /**
         * Construct a new <code>GUIScrollEvent</code>
         *
         * @param rowAmt The row amount to scroll on click
         * @param colAmt The column amount to scroll on click
         */
        public GUIScrollEvent(int rowAmt, int colAmt) {
            this.rowAmt = rowAmt;
            this.colAmt = colAmt;
        }

        /**
         * Execute scrolling in the specified direction
         *
         * @param info {@link GUIEventInfo} of the event
         */
        @Override
        public void execute(GUIEventInfo info) {
            ClickType clickType = info.getClick();
            if(clickType != ClickType.LEFT) return;
            GUIContainer gui = info.getGUI();
            int rowOffset = gui.getRowOffset();
            int colOffset = gui.getColOffset();
            int totalRow  = rowOffset + Math.min(GUIContainer.MAX_INVENTORY_ROWS, gui.getRows());
            int totalCol  = colOffset + GUIContainer.MAX_INVENTORY_COLS;
            if(gui.getRows() >= totalRow + rowAmt && rowOffset + rowAmt >= 0) {
                gui.addRowOffset(rowAmt);
            }
            if(gui.getCols() >= totalCol + colAmt && colOffset + colAmt >= 0) {
                gui.addColOffset(colAmt);
            }
        }
    }
}
