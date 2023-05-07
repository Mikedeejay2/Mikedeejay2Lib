package com.mikedeejay2.mikedeejay2lib.gui.modules.navigation;

import com.mikedeejay2.mikedeejay2lib.BukkitPlugin;
import com.mikedeejay2.mikedeejay2lib.gui.GUIContainer;
import com.mikedeejay2.mikedeejay2lib.gui.GUILayer;
import com.mikedeejay2.mikedeejay2lib.gui.event.GUIClickEvent;
import com.mikedeejay2.mikedeejay2lib.gui.event.sound.GUIPlaySoundEvent;
import com.mikedeejay2.mikedeejay2lib.gui.item.AnimatedGUIItem;
import com.mikedeejay2.mikedeejay2lib.gui.item.GUIItem;
import com.mikedeejay2.mikedeejay2lib.gui.modules.GUIModule;
import com.mikedeejay2.mikedeejay2lib.item.ItemBuilder;
import com.mikedeejay2.mikedeejay2lib.text.Text;
import com.mikedeejay2.mikedeejay2lib.util.head.Base64Head;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

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

        Text upName = Text.of("gui.modules.scroller.up");
        this.upItem = new AnimatedGUIItem(
            ItemBuilder.of(Base64Head.ARROW_UP_WHITE.get()).setName(Text.of("&f").concat(upName)),
            false, 1, true)
            .addFrame(ItemBuilder.of(Base64Head.ARROW_UP_LIGHT_GRAY.get()).setName(Text.of("&f").concat(upName)), 1)
            .addFrame(ItemBuilder.of(Base64Head.ARROW_UP_WHITE.get()).setName(Text.of("&f").concat(upName)), 1)
            .setStartingIndex(1);
        upItem.addEvent(new GUIScrollEvent(-1, 0));
        Text downName = Text.of("gui.modules.scroller.down");
        this.downItem = new AnimatedGUIItem(
            ItemBuilder.of(Base64Head.ARROW_DOWN_WHITE.get())
                .setName(Text.of("&f").concat(downName)), false, 1, true)
            .addFrame(ItemBuilder.of(Base64Head.ARROW_DOWN_LIGHT_GRAY.get()).setName(Text.of("&f").concat(downName)), 1)
            .addFrame(ItemBuilder.of(Base64Head.ARROW_DOWN_WHITE.get()).setName(Text.of("&f").concat(downName)), 1)
            .setStartingIndex(1);
        downItem.addEvent(new GUIScrollEvent(1, 0));
        Text leftName = Text.of("gui.modules.scroller.left");
        this.leftItem = new AnimatedGUIItem(
            ItemBuilder.of(Base64Head.ARROW_LEFT_WHITE.get())
                .setName(Text.of("&f").concat(leftName)), false, 1, true)
            .addFrame(ItemBuilder.of(Base64Head.ARROW_LEFT_LIGHT_GRAY.get()).setName(Text.of("&f").concat(leftName)), 1)
            .addFrame(ItemBuilder.of(Base64Head.ARROW_LEFT_WHITE.get()).setName(Text.of("&f").concat(leftName)), 1)
            .setStartingIndex(1);
        leftItem.addEvent(new GUIScrollEvent(0, -1));
        Text rightName = Text.of("gui.modules.scroller.right");
        this.rightItem = new AnimatedGUIItem(
            ItemBuilder.of(Base64Head.ARROW_RIGHT_WHITE.get())
                .setName(Text.of("&f").concat(rightName)), false, 1, true)
            .addFrame(ItemBuilder.of(Base64Head.ARROW_RIGHT_LIGHT_GRAY.get()).setName(Text.of("&f").concat(rightName)), 1)
            .addFrame(ItemBuilder.of(Base64Head.ARROW_RIGHT_WHITE.get()).setName(Text.of("&f").concat(rightName)), 1)
            .setStartingIndex(1);
        rightItem.addEvent(new GUIScrollEvent(0, 1));
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
    public static class GUIScrollEvent extends GUIPlaySoundEvent {
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
            super(Sound.UI_BUTTON_CLICK, 0.3f, 1f);
            this.rowAmt = rowAmt;
            this.colAmt = colAmt;
        }

        /**
         * Execute scrolling in the specified direction
         *
         * @param info {@link GUIClickEvent} of the event
         */
        @Override
        public void executeClick(GUIClickEvent info) {
            GUIContainer gui = info.getGUI();
            int rowOffset = gui.getRowOffset();
            int colOffset = gui.getColumnOffset();
            int totalRow  = rowOffset + Math.min(GUIContainer.MAX_INVENTORY_ROWS, gui.getRows());
            int totalCol  = colOffset + GUIContainer.MAX_INVENTORY_COLS;
            if(gui.getRows() >= totalRow + rowAmt && rowOffset + rowAmt >= 0) {
                gui.addRowOffset(rowAmt);
            }
            if(gui.getCols() >= totalCol + colAmt && colOffset + colAmt >= 0) {
                gui.addColumnOffset(colAmt);
            }
            super.executeClick(info);
        }
    }
}
