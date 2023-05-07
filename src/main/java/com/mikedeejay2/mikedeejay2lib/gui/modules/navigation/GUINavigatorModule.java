package com.mikedeejay2.mikedeejay2lib.gui.modules.navigation;

import com.mikedeejay2.mikedeejay2lib.BukkitPlugin;
import com.mikedeejay2.mikedeejay2lib.gui.GUIContainer;
import com.mikedeejay2.mikedeejay2lib.gui.GUILayer;
import com.mikedeejay2.mikedeejay2lib.gui.event.GUIClickEvent;
import com.mikedeejay2.mikedeejay2lib.gui.event.sound.GUIPlaySoundEvent;
import com.mikedeejay2.mikedeejay2lib.gui.item.GUIItem;
import com.mikedeejay2.mikedeejay2lib.gui.manager.PlayerGUI;
import com.mikedeejay2.mikedeejay2lib.gui.modules.GUIModule;
import com.mikedeejay2.mikedeejay2lib.item.ItemBuilder;
import com.mikedeejay2.mikedeejay2lib.text.Text;
import com.mikedeejay2.mikedeejay2lib.util.head.Base64Head;
import com.mikedeejay2.mikedeejay2lib.util.structure.NavigationHolder;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

/**
 * A module that adds a web browser style navigator to the GUI that
 * can browse to previous and next GUIs
 *
 * @author Mikedeejay2
 */
public class GUINavigatorModule implements GUIModule {
    /**
     * The {@link BukkitPlugin} instance
     */
    protected final BukkitPlugin plugin;

    /**
     * The ID of the Navigator
     */
    protected String navigationID;

    /**
     * The valid back item
     */
    protected GUIItem validBackItem;

    /**
     * The valid forward item
     */
    protected GUIItem validForwardItem;

    /**
     * The invalid back item
     */
    protected GUIItem invalidBackItem;

    /**
     * The invalid forward item
     */
    protected GUIItem invalidForwardItem;
    /**
     * Flag for when a navigation has occurred
     */
    boolean navigated;

    /**
     * Construct a new <code>GUINavigatorModule</code>
     *
     * @param plugin       The {@link BukkitPlugin} instance
     * @param navigationID The navigation ID to use
     */
    public GUINavigatorModule(BukkitPlugin plugin, String navigationID) {
        this.plugin = plugin;
        this.navigationID = navigationID;
        this.validBackItem = new GUIItem(
            ItemBuilder.of(Base64Head.ARROW_LEFT_WHITE.get())
                .setName(Text.of("&f").concat("gui.modules.navigator.backward")));
        validBackItem.addEvent(new GUINavBackEvent(plugin));
        this.validForwardItem = new GUIItem(
            ItemBuilder.of(Base64Head.ARROW_RIGHT_WHITE.get())
                .setName(Text.of("&f").concat("gui.modules.navigator.forward")));
        validForwardItem.addEvent(new GUINavForwardEvent(plugin));
        this.invalidBackItem = new GUIItem(
            ItemBuilder.of(Base64Head.ARROW_LEFT_LIGHT_GRAY.get())
                .setName(Text.of("&7").concat("gui.modules.navigator.backward")));
        this.invalidForwardItem = new GUIItem(
            ItemBuilder.of(Base64Head.ARROW_RIGHT_LIGHT_GRAY.get())
                .setName(Text.of("&7").concat("gui.modules.navigator.forward")));

        this.navigated = false;
    }

    @Override
    public void onClose(Player player, GUIContainer gui) {
        if(navigated) {
            navigated = false;
            return;
        }
        PlayerGUI playerGUI = plugin.getGUIManager().getPlayer(player);
        if(!playerGUI.isGuiChange()) return;
        String curID = getNavigationID();
        NavigationHolder<GUIContainer> system = playerGUI.getNavigation(curID);
        if(system.hasBack() && system.peekBack() == gui) return;
        system.pushBack(gui);
        if(!system.hasForward()) return;
        system.clearForward();
    }

    /**
     * Method that adds the forward and back
     *
     * @param player The player that is viewing the GUI
     * @param gui The GUI
     */
    @Override
    public void onUpdateHead(Player player, GUIContainer gui) {
        NavigationHolder<GUIContainer> system = plugin.getGUIManager().getPlayer(player).getNavigation(navigationID);
        GUILayer layer = gui.getLayer("overlay", true);

        if(system.hasBack()) {
            int backAmount = (int) system.backSize();
            if(backAmount == 0) { backAmount = 1; }
            else if(backAmount > 64) { backAmount = 64; }
            if(validBackItem.getAmount() != backAmount) validBackItem.setAmount(backAmount);
            layer.setItem(1, 1, validBackItem);
        } else {
            layer.setItem(1, 1, invalidBackItem);
        }
        if(system.hasForward()) {
            int forwardAmount = (int) system.forwardSize();
            if(forwardAmount == 0) { forwardAmount = 1; }
            else if(forwardAmount > 64) { forwardAmount = 64; }
            if(validForwardItem.getAmount() != forwardAmount) validForwardItem.setAmount(forwardAmount);
            layer.setItem(1, 9, validForwardItem);
        } else {
            layer.setItem(1, 9, invalidForwardItem);
        }
    }

    /**
     * Get this GUI Navigator's ID
     *
     * @return The navigation ID
     */
    public String getNavigationID() {
        return navigationID;
    }

    /**
     * Get the valid back item that is used for going back in a GUI
     *
     * @return The valid back item
     */
    public GUIItem getValidBackItem() {
        return validBackItem;
    }

    /**
     * Set the valid back item that is used for going back in a GUI
     *
     * @param validBackItem The new valid back item
     */
    public void setValidBackItem(GUIItem validBackItem) {
        this.validBackItem = validBackItem;
    }

    /**
     * Get the valid forward item that is used for going forward in a GUI
     *
     * @return The valid forward item
     */
    public GUIItem getValidForwardItem() {
        return validForwardItem;
    }

    /**
     * Set the valid forward item that is used for going forward in a GUI
     *
     * @param validForwardItem The new valid back item
     */
    public void setValidForwardItem(GUIItem validForwardItem) {
        this.validForwardItem = validForwardItem;
    }

    /**
     * Get the invalid back item that is used for going back in a GUI
     *
     * @return The invalid back item
     */
    public GUIItem getInvalidBackItem() {
        return invalidBackItem;
    }

    /**
     * Set the invalid back item that is used for going back in a GUI
     *
     * @param invalidBackItem The new invalid back item
     */
    public void setInvalidBackItem(GUIItem invalidBackItem) {
        this.invalidBackItem = invalidBackItem;
    }

    /**
     * Get the invalid forward item that is used for going forward in a GUI
     *
     * @return The invalid forward item
     */
    public GUIItem getInvalidForwardItem() {
        return invalidForwardItem;
    }

    /**
     * Set the invalid forward item that is used for going forward in a GUI
     *
     * @param invalidForwardItem The new invalid forward item
     */
    public void setInvalidForwardItem(GUIItem invalidForwardItem) {
        this.invalidForwardItem = invalidForwardItem;
    }


    /**
     * Navigate the GUI back one GUI.
     *
     * @author Mikedeejay2
     */
    public static class GUINavBackEvent extends GUIPlaySoundEvent {
        /**
         * The {@link BukkitPlugin} instance
         */
        protected final BukkitPlugin plugin;

        /**
         * Construct a new <code>GUINavBackEvent</code>
         *
         * @param plugin The {@link BukkitPlugin} instance
         */
        public GUINavBackEvent(BukkitPlugin plugin) {
            super(Sound.UI_BUTTON_CLICK, 0.3f, 1f);
            this.plugin = plugin;
        }

        /**
         * Execute navigating the GUI back through history
         *
         * @param info {@link GUIClickEvent} of the event
         */
        @Override
        public void executeClick(GUIClickEvent info) {
            Player player = info.getPlayer();
            GUIContainer gui = info.getGUI();
            GUINavigatorModule module = gui.getModule(GUINavigatorModule.class);
            PlayerGUI playerGUI = plugin.getGUIManager().getPlayer(player);
            NavigationHolder<GUIContainer> system = playerGUI.getNavigation(module.getNavigationID());
            GUIContainer backGUI = system.popBack();
            system.pushForward(gui);
            module.navigated = true;
            backGUI.open(player);
            super.executeClick(info);
        }
    }

    /**
     * Navigate the GUI forward one GUI.
     *
     * @author Mikedeejay2
     */
    public static class GUINavForwardEvent extends GUIPlaySoundEvent {
        /**
         * The {@link BukkitPlugin} instance
         */
        protected final BukkitPlugin plugin;

        /**
         * Construct a new <code>GUINavForwardEvent</code>
         *
         * @param plugin The {@link BukkitPlugin} instance
         */
        public GUINavForwardEvent(BukkitPlugin plugin) {
            super(Sound.UI_BUTTON_CLICK, 0.3f, 1f);
            this.plugin = plugin;
        }

        /**
         * Execute navigating the GUI forward through history
         *
         * @param info {@link GUIClickEvent} of the event
         */
        @Override
        public void executeClick(GUIClickEvent info) {
            Player player = info.getPlayer();
            GUIContainer gui = info.getGUI();
            GUINavigatorModule module = gui.getModule(GUINavigatorModule.class);
            PlayerGUI playerGUI = plugin.getGUIManager().getPlayer(player);
            NavigationHolder<GUIContainer> system = playerGUI.getNavigation(module.getNavigationID());
            GUIContainer forwardGUI = system.popForward();
            system.pushBack(gui);
            module.navigated = true;
            forwardGUI.open(player);
            super.executeClick(info);
        }
    }
}
