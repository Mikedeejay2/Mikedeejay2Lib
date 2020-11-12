package com.mikedeejay2.mikedeejay2lib.gui.modules;

import com.mikedeejay2.mikedeejay2lib.PluginBase;
import com.mikedeejay2.mikedeejay2lib.gui.GUIContainer;
import com.mikedeejay2.mikedeejay2lib.gui.GUILayer;
import com.mikedeejay2.mikedeejay2lib.gui.event.list.GUIListSearchEvent;
import com.mikedeejay2.mikedeejay2lib.gui.event.list.GUIListSearchOffEvent;
import com.mikedeejay2.mikedeejay2lib.gui.event.list.GUISwitchListPageEvent;
import com.mikedeejay2.mikedeejay2lib.gui.item.GUIItem;
import com.mikedeejay2.mikedeejay2lib.util.head.Base64Heads;
import com.mikedeejay2.mikedeejay2lib.util.item.ItemComparison;
import com.mikedeejay2.mikedeejay2lib.util.search.SearchUtil;
import com.mikedeejay2.mikedeejay2lib.util.chat.Chat;
import com.mikedeejay2.mikedeejay2lib.util.item.ItemCreator;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.*;

/**
 * GUI Module that turns the GUI into a list that shows a list of different
 * <tt>GUIItems</tt>. A list can also have multiple pages and can be searched
 * if the feature is enabled.
 *
 * @author Mikedeejay2
 */
public class GUIListModule extends GUIModule
{
    protected final PluginBase plugin;
    // The list of items that this list holds
    protected List<GUIItem> list;
    // The search list of the items if search is enabled
    private List<Map.Entry<Integer, GUIItem>> searchList;
    // Whether search mode is enabled or not
    private boolean searchMode;
    // The search term that has previously been used
    private String searchTerm;
    // The current page that the player is on
    private int curPage;
    // Items that will be appended to the very end of the list
    private List<GUIItem> endItems;

    // The back item that will be used
    private GUIItem backItem;
    // The forward item that will be used
    private GUIItem forwardItem;
    // The search item that will be used
    private GUIItem searchItem;
    // The turn search off item that will be used
    private GUIItem searchOffItem;
    // Whether search is allowed or not
    private boolean searchEnabled;

    public GUIListModule(PluginBase plugin)
    {
        this.plugin = plugin;
        this.list = new ArrayList<>();
        this.searchList = new ArrayList<>();

        curPage = 1;
        endItems = new ArrayList<>();
        this.backItem = new GUIItem(ItemCreator.createHeadItem(Base64Heads.ARROW_BACKWARD_WHITE, 1, GUIContainer.EMPTY_NAME));
        this.forwardItem = new GUIItem(ItemCreator.createHeadItem(Base64Heads.ARROW_FORWARD_WHITE, 1, GUIContainer.EMPTY_NAME));
        backItem.addEvent(new GUISwitchListPageEvent());
        forwardItem.addEvent(new GUISwitchListPageEvent());

        this.searchEnabled = false;
    }

    @Override
    public void onOpenHead(Player player, GUIContainer gui)
    {
        if(searchItem == null)
        {
            this.searchItem = new GUIItem(ItemCreator.createItem(Material.COMPASS, 1, "&f&o" + plugin.langManager().getTextLib(player, "gui.modules.list.search")));
            searchItem.addEvent(new GUIListSearchEvent(plugin));
        }
        if(searchOffItem == null)
        {
            this.searchOffItem = new GUIItem(ItemCreator.createItem(Material.BOOK, 1, "&f&o" + plugin.langManager().getTextLib(player, "gui.modules.list.search_off")));
            searchOffItem.addEvent(new GUIListSearchOffEvent());
        }
    }

    /**
     * Method that displays the list on GUI update
     *
     * @param player The player that is viewing the GUI
     * @param gui The GUI
     */
    @Override
    public void onUpdateHead(Player player, GUIContainer gui)
    {
        GUILayer layer = gui.getLayer("list", false);
        if(searchMode)
        {
            searchThroughList();
        }
        updateListControls(searchMode, layer, player);
        updatePage(searchMode, layer);
    }

    /**
     * Adds an item to the end of the list that will always stay at the
     * end of the list. Useful for special items that might add new items
     * to the list or something that should specifically be at the end of the
     * list.
     *
     * @param endItem The item that will be added to the end of the list
     */
    public void addEndItem(GUIItem endItem)
    {
        endItems.add(endItem);
    }

    /**
     * Remove an end item from the end items list.
     *
     * @param endItem End item to remove
     */
    public void removeEndItem(GUIItem endItem)
    {
        endItems.remove(endItem);
    }

    /**
     * Reset the end items to have no appended end items.
     */
    public void resetEndItems()
    {
        endItems.clear();
    }

    /**
     * Reset the end items to have no appended end items.
     */
    public void resetList()
    {
        list.clear();
        searchList.clear();
    }

    /**
     * Updates the current page, called on {@link GUIListModule#onUpdateHead(Player player, GUIContainer gui)}
     *
     * @param search Whether search mode is enabled or not
     * @param layer The layer to update the page on
     */
    private void updatePage(boolean search, GUILayer layer)
    {
        int pageSize = ((layer.getRows() - 2) * layer.getCols());
        int listSize = search ? searchList.size() : list.size();
        for(int i = 0; i < pageSize; i++)
        {
            int pageOffset = ((curPage-1) * pageSize);
            int row = layer.getRowFromSlot(i + layer.getCols()), col = layer.getColFromSlot(i);
            layer.removeItem(row, col);

            if(listSize >= (i+1) + pageOffset)
            {
                GUIItem item = search ? searchList.get(i + pageOffset).getValue() : list.get(i + pageOffset);
                layer.setItem(layer.getRowFromSlot(i + layer.getCols()), layer.getColFromSlot(i), item);
            }
            else if((i + 1) + pageOffset > listSize && !search)
            {
                int index = (i + pageOffset) - listSize;

                if(endItems.size() > index)
                {
                    GUIItem item = endItems.get(index);
                    layer.setItem(row, col, item);
                }
            }
        }
    }

    /**
     * Update the controls for the list, called on {@link GUIListModule#onUpdateHead(Player player, GUIContainer gui)}
     *
     * @param search Whether search mode is enabled or not
     * @param layer The layer to update the controls on
     */
    private void updateListControls(boolean search, GUILayer layer, Player player)
    {
        List<GUIItem> pageList = (List<GUIItem>) (search ? searchList : list);

        int amountOfPages = (int)Math.ceil((pageList.size() + endItems.size()) / ((layer.getRows() - 2.0f) * layer.getCols()));

        for(int i = 2; i <= 8; i++)
        {
            layer.removeItem(layer.getRows(), i);
        }

        for(int i = 1; i <= amountOfPages; i++)
        {
            if(i == curPage || i > curPage + 3 || i < curPage - 3) continue;
            if(i < curPage && i + 3 >= curPage)
            {
                int col = (i - curPage) + 5;
                GUIItem curItem = backItem.clone();
                curItem.setNameView(Chat.chat("&f" + plugin.langManager().getTextLib
                (
                    player, "gui.modules.list.page",
                    new String[]{"PAGE"},
                    new String[]{String.valueOf(i)}
                )));
                layer.setItem(layer.getRows(), col, curItem);
            }
            else if(i > curPage && i - 3 <= curPage)
            {
                int col = (i - curPage) + 5;
                GUIItem curItem = forwardItem.clone();
                curItem.setNameView(Chat.chat("&f" + plugin.langManager().getTextLib
                (
                    player, "gui.modules.list.page",
                    new String[]{"PAGE"},
                    new String[]{String.valueOf(i)}
                )));
                layer.setItem(layer.getRows(), col, curItem);
            }
        }

        if(searchEnabled)
        {
            layer.setItem(layer.getRows(), 1, searchItem);

            if(searchMode)
            {
                layer.setItem(layer.getRows(), 9, searchOffItem);
            }
        }
    }

    /**
     * Add an item to the list.
     *
     * @param item The item that will be added to the list
     */
    public void addListItem(GUIItem item)
    {
        list.add(item);
    }

    /**
     * Change a GUI item based off of a row and column.
     * Not recommended for usage but still included just in case.
     *
     * @param item The item to replace the current item
     * @param row The row to replace
     * @param col The column to replace
     * @param gui The GUI to update
     */
    public void changeGUIItem(GUIItem item, int row, int col, GUIContainer gui)
    {
        int pageSize = ((gui.getRows() - 2) * 9);
        int pageOffset = ((curPage-1) * pageSize);
        int index = gui.getSlotFromRowCol(row-2, col-1) + pageOffset;
        list.set(index, item);
    }

    /**
     * Set the entire list of <tt>GUIItems</tt> to a new list
     *
     * @param items The list of items to set this list to
     */
    public void setGUIItems(List<GUIItem> items)
    {
        this.list = items;
    }

    /**
     * Remove a <tt>GUIItem</tt> based on index
     *
     * @param index The index to the item that will be removed
     */
    public void removeGUIItem(int index)
    {
        list.remove(index);
    }

    /**
     * Remove a GUIItem based on the instance of that item
     *
     * @param item The item to remove from this list
     */
    public void removeGUIItem(GUIItem item)
    {
        list.remove(item);
    }

    /**
     * Get a list's index based off of it's row and column in the GUI.
     * Not recommended to use but included just in case.
     *
     * @param row The row to get the item from
     * @param col The column to get the item from
     * @param gui The GUI to get the index from
     * @return The index based off of the row and column
     */
    public int getListItemIndex(int row, int col, GUIContainer gui)
    {
        int pageSize = ((gui.getRows() - 2) * 9);
        int pageOffset = ((curPage-1) * pageSize);
        int index = gui.getSlotFromRowCol(row-2, col-1) + pageOffset;
        if(searchMode)
        {
            index = searchList.get(index).getKey();
        }
        return index;
    }

    /**
     * Get a list item based off of the <tt>GUIContainer</tt> that the list is located in
     * and the row and column of the item.
     *
     * @param row The row to get the item from
     * @param col The column to get the item from
     * @param gui The <tt>GUIContainer</tt> that this list is located in
     * @return The <tt>GUIItem</tt> at the location.
     */
    public GUIItem getItem(int row, int col, GUIContainer gui)
    {
        int index = getListItemIndex(row, col, gui);
        if(index >= list.size()) return null;
        return list.get(index);
    }

    /**
     * Get a list item from it's slot in the list.
     *
     * @param slot The slot to get the item from
     * @return The <tt>GUIItem</tt> of the slot
     */
    public GUIItem getItem(int slot)
    {
        return list.get(slot);
    }

    /**
     * Remove a list item from the list based off of the <tt>GUIContainer</tt> that the list is located in
     * and the row and column of the item.
     *
     * @param row The row to remove the item from
     * @param col The column to remove the item from
     * @param gui The <tt>GUIContainer</tt> that this list is located in
     */
    public void removeListItem(int row, int col, GUIContainer gui)
    {
        int index = getListItemIndex(row, col, gui);
        List<GUIItem> list = this.list;
        list.remove(index);
    }

    /**
     * Add a list item to the list based off of the <tt>GUIContainer</tt> that the list is located in
     *
     * @param row The row to add the item to
     * @param col The column to add the item to
     * @param gui The <tt>GUIContainer</tt> that this list is located in
     * @param item The <tt>GUIItem</tt> to be added
     */
    public void addListItem(int row, int col, GUIContainer gui, GUIItem item)
    {
        int index = getListItemIndex(row, col, gui);
        list.add(index, item);
    }

    /**
     * Change the page to a new page
     *
     * @param page The page number
     * @param player The player that is viewing the GUI
     * @param gui The GUI
     */
    public void toListPage(int page, Player player, GUIContainer gui)
    {
        curPage = page;
    }

    /**
     * Enable search mode in this list
     *
     * @param search The search term that has been searched
     */
    public void enableSearchMode(String search)
    {
        this.searchMode = true;
        this.searchTerm = search;
    }

    /**
     * Disable search mode in this list
     */
    public void disableSearchMode()
    {
        this.searchMode = false;
        this.searchTerm = null;
    }

    /**
     * Search through this entire list, called in {@link GUIListModule#onUpdateHead(Player, GUIContainer)}
     * if search mode is enabled.
     */
    private void searchThroughList()
    {
        searchMode = true;
        searchList.clear();
        for(int i = 0; i < list.size(); ++i)
        {
            GUIItem item = list.get(i);
            if(!SearchUtil.searchMetaFuzzy(item.getMetaView(), searchTerm)) continue;
            searchList.add(new AbstractMap.SimpleEntry<>(i, item));
        }
    }

    /**
     * Get the item that represents the back button
     *
     * @return The back item
     */
    public GUIItem getBackItem()
    {
        return backItem;
    }

    /**
     * Get the item that represents the forward button
     *
     * @return The forward button
     */
    public GUIItem getForwardItem()
    {
        return forwardItem;
    }

    /**
     * Is search mode enabled for this list
     *
     * @return Whether search mode is enabled for this list or not
     */
    public boolean isSearchEnabled()
    {
        return searchEnabled;
    }

    /**
     * Get the current search term for this list
     *
     * @return The current search term
     */
    public String getSearchTerm()
    {
        return searchTerm;
    }

    /**
     * Set the search term to be used for searching this list
     *
     * @param searchTerm The new search term
     */
    public void setSearchTerm(String searchTerm)
    {
        this.searchTerm = searchTerm;
    }

    /**
     * Get the item that represents the search button
     *
     * @return The search item
     */
    public GUIItem getSearchItem()
    {
        return searchItem;
    }

    /**
     * Get the item that represents the turn off search button
     *
     * @return The search off item
     */
    public GUIItem getSearchOffItem()
    {
        return searchOffItem;
    }

    /**
     * Get all <tt>GUIItems</tt> in this list
     *
     * @return All items that this list is holding
     */
    public List<GUIItem> getList()
    {
        return list;
    }

    /**
     * Get all <tt>GUIItems</tt> that are currently in the
     * search list and the indexes that they relate to in the list
     *
     * @return All items that are in the search list
     */
    public List<Map.Entry<Integer, GUIItem>> getSearchMap()
    {
        return searchList;
    }

    /**
     * Get the current page that this list is displaying
     *
     * @return The current page
     */
    public int getCurPage()
    {
        return curPage;
    }

    /**
     * Get all <tt>GUIItems</tt> of the end items of this list
     *
     * @return All end items of this list
     */
    public List<GUIItem> getEndItems()
    {
        return endItems;
    }

    /**
     * Get the current size of the list
     *
     * @return The size of the list
     */
    public int getSize()
    {
        return list.size();
    }

    /**
     * See whether this list contains an item matching an <tt>ItemStack</tt>
     *
     * @param item The item to search for
     * @return Whether the item was found in the list or not
     */
    public boolean containsItem(ItemStack item)
    {
        for(GUIItem guiItem : list)
        {
            ItemStack curItem = guiItem.getItemBase();
            if(curItem == null) continue;
            if(!ItemComparison.equalsEachOther(item, curItem)) continue;
            return true;
        }
        return false;
    }

    /**
     * See whether this list contains a material matching another material
     *
     * @param material The material to search for
     * @return Whether the material was found in the list or not
     */
    public boolean containsMaterial(Material material)
    {
        for(GUIItem guiItem : list)
        {
            ItemStack curItem = guiItem.getItemBase();
            if(curItem == null) continue;
            if(curItem.getType() != material) continue;
            return true;
        }
        return false;
    }
}
