package com.mikedeejay2.mikedeejay2lib.gui.modules.navigation;

import com.mikedeejay2.mikedeejay2lib.BukkitPlugin;
import com.mikedeejay2.mikedeejay2lib.gui.GUIContainer;
import com.mikedeejay2.mikedeejay2lib.gui.GUILayer;
import com.mikedeejay2.mikedeejay2lib.gui.event.GUIEvent;
import com.mikedeejay2.mikedeejay2lib.gui.event.GUIEventInfo;
import com.mikedeejay2.mikedeejay2lib.gui.item.GUIItem;
import com.mikedeejay2.mikedeejay2lib.gui.manager.PlayerGUI;
import com.mikedeejay2.mikedeejay2lib.gui.modules.GUIModule;
import com.mikedeejay2.mikedeejay2lib.item.ItemBuilder;
import com.mikedeejay2.mikedeejay2lib.text.Text;
import com.mikedeejay2.mikedeejay2lib.util.head.Base64Head;
import com.mikedeejay2.mikedeejay2lib.util.structure.NavigationHolder;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;

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
     * Construct a new <code>GUINavigatorModule</code>
     *
     * @param plugin       The {@link BukkitPlugin} instance
     * @param navigationID The navigation ID to use
     */
    public GUINavigatorModule(BukkitPlugin plugin, String navigationID) {
        this.plugin = plugin;
        this.navigationID = navigationID;
    }

    /**
     * Generate the navigation UI on open
     *
     * @param player The player that is viewing the GUI
     * @param gui    The GUI
     */
    @Override
    public void onOpenHead(Player player, GUIContainer gui) {
        navigationCheck(player);
        if(validBackItem == null) {
            String backward = Text.translatable("gui.modules.navigator.backward").get(player);
            this.validBackItem = new GUIItem(ItemBuilder.of(Base64Head.ARROW_LEFT_WHITE.get())
                                                     .setName("&f" + backward)
                                                     .get());
            validBackItem.addEvent(new GUINavBackEvent(plugin));
        }
        if(validForwardItem == null) {
            String forward = Text.translatable("gui.modules.navigator.forward").get(player);
            this.validForwardItem = new GUIItem(ItemBuilder.of(Base64Head.ARROW_RIGHT_WHITE.get())
                                                        .setName("&f" + forward)
                                                        .get());
            validForwardItem.addEvent(new GUINavForwardEvent(plugin));
        }
        if(invalidBackItem == null) {
            String backward = Text.translatable("gui.modules.navigator.backward").get(player);
            this.invalidBackItem = new GUIItem(ItemBuilder.of(Base64Head.ARROW_LEFT_LIGHT_GRAY.get())
                                                       .setName("&7" + backward)
                                                       .get());
        }
        if(invalidForwardItem == null) {
            String forward = Text.translatable("gui.modules.navigator.forward").get(player);
            this.invalidForwardItem = new GUIItem(ItemBuilder.of(Base64Head.ARROW_RIGHT_LIGHT_GRAY.get())
                                                          .setName("&7" + forward)
                                                          .get());
        }
    }

    /**
     * Checks whether the GUI is using a navigation system and if so calculate the forward
     * and back navigations.
     */
    private void navigationCheck(Player player) {
        PlayerGUI playerGUI = plugin.getGUIManager().getPlayer(player);
        GUIContainer oldGUI = playerGUI.getGUI();
        String curID = getNavigationID();
        NavigationHolder<GUIContainer> system = playerGUI.getNavigation(curID);
        if(system.isNavigationFlagged() || !playerGUI.isGuiOpened()) {
            system.setNavigationFlag(false);
            return;
        }
        if(oldGUI != null) {
            if(!oldGUI.containsModule(GUINavigatorModule.class)) return;
            String oldID = oldGUI.getModule(GUINavigatorModule.class).getNavigationID();
            if(!curID.equals(oldID)) return;
            if(system.hasBack() && system.peekBack() == oldGUI) return;
            system.pushBack(oldGUI);
            if(!system.hasForward()) return;
            system.clearForward();
        }
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
            validBackItem.setAmount(backAmount);
            layer.setItem(1, 1, validBackItem);
        } else {
            layer.setItem(1, 1, invalidBackItem);
        }
        if(system.hasForward()) {
            int forwardAmount = (int) system.forwardSize();
            if(forwardAmount == 0) { forwardAmount = 1; }
            else if(forwardAmount > 64) { forwardAmount = 64; }
            validForwardItem.setAmount(forwardAmount);
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
    public static class GUINavBackEvent implements GUIEvent {
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
            this.plugin = plugin;
        }

        /**
         * Execute navigating the GUI back through history
         *
         * @param info {@link GUIEventInfo} of the event
         */
        @Override
        public void execute(GUIEventInfo info) {
            Player player = info.getPlayer();
            ClickType clickType = info.getClick();
            if(clickType != ClickType.LEFT) return;
            GUIContainer gui = info.getGUI();
            GUINavigatorModule module = gui.getModule(GUINavigatorModule.class);
            PlayerGUI playerGUI = plugin.getGUIManager().getPlayer(player);
            NavigationHolder<GUIContainer> system = playerGUI.getNavigation(module.getNavigationID());
            GUIContainer backGUI = system.popBack();
            system.pushForward(gui);
            system.setNavigationFlag(true);
            gui.onClose(player);
            backGUI.open(player);
        }
    }

    /**
     * Navigate the GUI forward one GUI.
     *
     * @author Mikedeejay2
     */
    public static class GUINavForwardEvent implements GUIEvent {
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
            this.plugin = plugin;
        }

        /**
         * Execute navigating the GUI forward through history
         *
         * @param info {@link GUIEventInfo} of the event
         */
        @Override
        public void execute(GUIEventInfo info) {
            Player player = info.getPlayer();
            ClickType clickType = info.getClick();
            if(clickType != ClickType.LEFT) return;
            GUIContainer gui = info.getGUI();
            GUINavigatorModule module = gui.getModule(GUINavigatorModule.class);
            PlayerGUI playerGUI = plugin.getGUIManager().getPlayer(player);
            NavigationHolder<GUIContainer> system = playerGUI.getNavigation(module.getNavigationID());
            GUIContainer forwardGUI = system.popForward();
            system.pushBack(gui);
            system.setNavigationFlag(true);
            gui.onClose(player);
            forwardGUI.open(player);
        }
    }
}
