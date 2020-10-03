package com.mikedeejay2.mikedeejay2lib.gui.modules;

import com.mikedeejay2.mikedeejay2lib.PluginBase;
import com.mikedeejay2.mikedeejay2lib.gui.GUIContainer;
import com.mikedeejay2.mikedeejay2lib.gui.item.GUIItem;
import com.mikedeejay2.mikedeejay2lib.util.PluginInstancer;
import org.bukkit.entity.Player;

/**
 * A method that acts as an injection into a <tt>GUIContainer</tt> to add features to the
 * GUI.
 *
 * @see GUIModule
 *
 * @author Mikedeejay2
 */
public abstract class GUIModule extends PluginInstancer<PluginBase>
{
    public GUIModule(PluginBase plugin)
    {
        super(plugin);
    }

    /**
     * Called on the head of the update method in <tt>GUIContainer</tt>
     *
     * @param player The player that is viewing the GUI
     * @param gui The GUI
     */
    public void onUpdateHead(Player player, GUIContainer gui) {}

    /**
     * Called on the tail of the update method in <tt>GUIContainer</tt>
     *
     * @param player The player that is viewing the GUI
     * @param gui The GUI
     */
    public void onUpdateTail(Player player, GUIContainer gui) {}

    /**
     * Called on the head of the open method in <tt>GUIContainer</tt>
     *
     * @param player The player that is viewing the GUI
     * @param gui The GUI
     */
    public void onOpenHead(Player player, GUIContainer gui) {}

    /**
     * Called on the tail of the open method in <tt>GUIContainer</tt>
     *
     * @param player The player that is viewing the GUI
     * @param gui The GUI
     */
    public void onOpenTail(Player player, GUIContainer gui) {}

    /**
     * Called in the close method in <tt>GUIContainer</tt>
     *
     * @param player The player that is viewing the GUI
     * @param gui The GUI
     */
    public void onClose(Player player, GUIContainer gui) {}

    /**
     * Called when the GUI is clicked, called in the head of the click method in <tt>GUIContainer</tt>
     *
     * @param player The player that is viewing the GUI
     * @param row The row that was clicked on
     * @param col The column that was clicked on
     * @param clicked The <tt>GUIItem</tt> that was clicked
     * @param gui The GUI
     */
    public void onClickedHead(Player player, int row, int col, GUIItem clicked, GUIContainer gui) {}

    /**
     * Called when the GUI is clicked, called in the tail of the click method in <tt>GUIContainer</tt>
     *
     * @param player The player that is viewing the GUI
     * @param row The row that was clicked on
     * @param col The column that was clicked on
     * @param clicked The <tt>GUIItem</tt> that was clicked
     * @param gui The GUI
     */
    public void onClickedTail(Player player, int row, int col, GUIItem clicked, GUIContainer gui) {}
}
