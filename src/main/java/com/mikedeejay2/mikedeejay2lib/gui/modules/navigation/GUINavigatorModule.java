package com.mikedeejay2.mikedeejay2lib.gui.modules.navigation;

import com.mikedeejay2.mikedeejay2lib.PluginBase;
import com.mikedeejay2.mikedeejay2lib.gui.GUIContainer;
import com.mikedeejay2.mikedeejay2lib.gui.GUILayer;
import com.mikedeejay2.mikedeejay2lib.gui.event.navigator.GUINavBackEvent;
import com.mikedeejay2.mikedeejay2lib.gui.event.navigator.GUINavForwardEvent;
import com.mikedeejay2.mikedeejay2lib.gui.item.GUIItem;
import com.mikedeejay2.mikedeejay2lib.gui.manager.NavigationSystem;
import com.mikedeejay2.mikedeejay2lib.gui.manager.PlayerGUI;
import com.mikedeejay2.mikedeejay2lib.gui.modules.GUIModule;
import com.mikedeejay2.mikedeejay2lib.util.head.Base64Head;
import com.mikedeejay2.mikedeejay2lib.util.item.ItemCreator;
import org.bukkit.entity.Player;

/**
 * A module that adds a web browser style navigator to the GUI that
 * can browse to previous and next GUIs
 *
 * @author Mikedeejay2
 */
public class GUINavigatorModule implements GUIModule
{
    protected final PluginBase plugin;
    // The ID of the Navigator
    protected String navigationID;

    // The valid back item
    protected GUIItem validBackItem;
    // The valid forward item
    protected GUIItem validForwardItem;
    // The invalid back item
    protected GUIItem invalidBackItem;
    // The invalid forward item
    protected GUIItem invalidForwardItem;

    public GUINavigatorModule(PluginBase plugin, String navigationID)
    {
        this.plugin = plugin;
        this.navigationID = navigationID;
    }

    @Override
    public void onOpenHead(Player player, GUIContainer gui)
    {
        navigationCheck(player, gui);
        if(validBackItem == null)
        {
            String backward = plugin.getLibLangManager().getText(player, "gui.modules.navigator.backward");
            this.validBackItem = new GUIItem(ItemCreator.createHeadItem(Base64Head.ARROW_LEFT_WHITE.get(), 1, "&f" + backward));
            validBackItem.addEvent(new GUINavBackEvent(plugin));
        }
        if(validForwardItem == null)
        {
            String forward = plugin.getLibLangManager().getText(player, "gui.modules.navigator.forward");
            this.validForwardItem = new GUIItem(ItemCreator.createHeadItem(Base64Head.ARROW_RIGHT_WHITE.get(), 1, "&f" + forward));
            validForwardItem.addEvent(new GUINavForwardEvent(plugin));
        }
        if(invalidBackItem == null)
        {
            String backward = plugin.getLibLangManager().getText(player, "gui.modules.navigator.backward");
            this.invalidBackItem = new GUIItem(ItemCreator.createHeadItem(Base64Head.ARROW_LEFT_LIGHT_GRAY.get(), 1, "&7" + backward));
        }
        if(invalidForwardItem == null)
        {
            String forward = plugin.getLibLangManager().getText(player, "gui.modules.navigator.forward");
            this.invalidForwardItem = new GUIItem(ItemCreator.createHeadItem(Base64Head.ARROW_RIGHT_LIGHT_GRAY.get(), 1, "&7" + forward));
        }
    }



    /**
     * Checks whether the GUI is using a navigation system and if so calculate the forward
     * and back navigations.
     */
    private void navigationCheck(Player player, GUIContainer gui)
    {
        PlayerGUI playerGUI = plugin.getGUIManager().getPlayer(player);
        GUIContainer curGUI = playerGUI.getGUI();
        if(!(gui.containsModule(GUINavigatorModule.class) && curGUI.containsModule(GUINavigatorModule.class))) return;
        GUINavigatorModule curModule = curGUI.getModule(GUINavigatorModule.class);
        GUINavigatorModule openModule = gui.getModule(GUINavigatorModule.class);
        String curID = curModule.getNavigationID();
        String openID = openModule.getNavigationID();
        if(!curID.equals(openID)) return;
        NavigationSystem system = playerGUI.getNaviSystem(curID);
        if(system.hasBack() && system.getBack().equals(curGUI)) return;
        system.addBack(curGUI);
        if(!system.hasForward()) return;
        system.resetForward();
    }

    /**
     * Method that adds the forward and back
     *
     * @param player The player that is viewing the GUI
     * @param gui The GUI
     */
    @Override
    public void onUpdateHead(Player player, GUIContainer gui)
    {
        NavigationSystem system = plugin.getGUIManager().getPlayer(player).getNaviSystem(navigationID);
        GUILayer layer = gui.getLayer("overlay", true);

        if(system.hasBack())
        {
            int backAmount = (int) system.getAmountBack();
            if(backAmount == 0) { backAmount = 1; }
            else if(backAmount > 64) { backAmount = 64; }
            validBackItem.setAmountView(backAmount);
            layer.setItem(1, 1, validBackItem);
        }
        else
        {
            layer.setItem(1, 1, invalidBackItem);
        }
        if(system.hasForward())
        {
            int forwardAmount = (int) system.getAmountForward();
            if(forwardAmount == 0) { forwardAmount = 1; }
            else if(forwardAmount > 64) { forwardAmount = 64; }
            validForwardItem.setAmountView(forwardAmount);
            layer.setItem(1, 9, validForwardItem);
        }
        else
        {
            layer.setItem(1, 9, invalidForwardItem);
        }
    }

    /**
     * Get this GUI Navigator's ID
     *
     * @return The navigation ID
     */
    public String getNavigationID()
    {
        return navigationID;
    }

    /**
     * Get the valid back item that is used for going back in a GUI
     *
     * @return The valid back item
     */
    public GUIItem getValidBackItem()
    {
        return validBackItem;
    }

    /**
     * Set the valid back item that is used for going back in a GUI
     *
     * @param validBackItem The new valid back item
     */
    public void setValidBackItem(GUIItem validBackItem)
    {
        this.validBackItem = validBackItem;
    }

    /**
     * Get the valid forward item that is used for going forward in a GUI
     *
     * @return The valid forward item
     */
    public GUIItem getValidForwardItem()
    {
        return validForwardItem;
    }

    /**
     * Set the valid forward item that is used for going forward in a GUI
     *
     * @param validForwardItem The new valid back item
     */
    public void setValidForwardItem(GUIItem validForwardItem)
    {
        this.validForwardItem = validForwardItem;
    }

    /**
     * Get the invalid back item that is used for going back in a GUI
     *
     * @return The invalid back item
     */
    public GUIItem getInvalidBackItem()
    {
        return invalidBackItem;
    }

    /**
     * Set the invalid back item that is used for going back in a GUI
     *
     * @param invalidBackItem The new invalid back item
     */
    public void setInvalidBackItem(GUIItem invalidBackItem)
    {
        this.invalidBackItem = invalidBackItem;
    }

    /**
     * Get the invalid forward item that is used for going forward in a GUI
     *
     * @return The invalid forward item
     */
    public GUIItem getInvalidForwardItem()
    {
        return invalidForwardItem;
    }

    /**
     * Set the invalid forward item that is used for going forward in a GUI
     *
     * @param invalidForwardItem The new invalid forward item
     */
    public void setInvalidForwardItem(GUIItem invalidForwardItem)
    {
        this.invalidForwardItem = invalidForwardItem;
    }
}
