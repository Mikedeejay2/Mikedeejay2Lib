package com.mikedeejay2.mikedeejay2lib.gui.modules;

import com.mikedeejay2.mikedeejay2lib.PluginBase;
import com.mikedeejay2.mikedeejay2lib.gui.GUIContainer;
import com.mikedeejay2.mikedeejay2lib.gui.event.list.GUIListSearchEvent;
import com.mikedeejay2.mikedeejay2lib.gui.event.list.GUIListSearchOffEvent;
import com.mikedeejay2.mikedeejay2lib.gui.event.list.GUISwitchListPageEvent;
import com.mikedeejay2.mikedeejay2lib.gui.item.GUIItem;
import com.mikedeejay2.mikedeejay2lib.util.head.Base64Heads;
import com.mikedeejay2.mikedeejay2lib.util.search.SearchUtil;
import com.mikedeejay2.mikedeejay2lib.util.chat.Chat;
import com.mikedeejay2.mikedeejay2lib.util.item.ItemCreator;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

/**
 * GUI Module that turns the GUI into a list that shows a list of different
 * <tt>GUIItems</tt>. A list can also have multiple pages and can be searched
 * if the feature is enabled.
 *
 * @author Mikedeejay2
 */
public class GUIListModule extends GUIModule
{
    // The list of items that this list holds
    protected List<GUIItem> list;
    // The search list of the items if search is enabled
    private List<GUIItem> searchList;
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
        super(plugin);

        this.list = new ArrayList<>();
        this.searchList = new ArrayList<>();

        curPage = 1;
        endItems = new ArrayList<>();
        this.backItem = new GUIItem(ItemCreator.createHeadItem(Base64Heads.HEAD_BACKWARDS_WHITE, 1, GUIContainer.EMPTY_NAME));
        this.forwardItem = new GUIItem(ItemCreator.createHeadItem(Base64Heads.HEAD_FORWARD_WHITE, 1, GUIContainer.EMPTY_NAME));
        this.searchItem = new GUIItem(ItemCreator.createItem(Material.COMPASS, 1, "&f&oSearch list..."));
        this.searchOffItem = new GUIItem(ItemCreator.createItem(Material.BOOK, 1, "&f&oTurn off search mode"));

        this.searchEnabled = false;
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
        if(searchMode)
        {
            searchThroughList();
        }
        updateListControls(searchMode, gui);
        updatePage(searchMode, gui);
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
        endItems = new ArrayList<>();
    }

    /**
     * Updates the current page, called on {@link GUIListModule#onUpdateHead(Player player, GUIContainer gui)}
     *
     * @param search Whether search mode is enabled or not
     * @param gui The GUI to update the page on
     */
    private void updatePage(boolean search, GUIContainer gui)
    {
        ArrayList<GUIItem> pageList = (ArrayList<GUIItem>) (search ? searchList : list);
        int pageSize = ((gui.getRows() - 2) * gui.getCols());
        for(int i = 0; i < pageSize; i++)
        {
            int pageOffset = ((curPage-1) * pageSize);
            int row = gui.getRowFromSlot(i + gui.getCols()), col = gui.getColFromSlot(i);
            gui.removeItem(row, col);

            if(pageList.size() >= (i+1) + pageOffset)
            {
                GUIItem item = pageList.get(i + pageOffset);
                gui.setItem(gui.getRowFromSlot(i + gui.getCols()), gui.getColFromSlot(i), item.getItem());
            }
            else if((i + 1) + pageOffset > pageList.size() && !search)
            {
                int index = (i + pageOffset) - pageList.size();

                if(endItems.size() > index)
                {
                    GUIItem item = endItems.get(index);
                    gui.setItem(row, col, item.getItem());
                }
            }
        }
    }

    /**
     * Update the controls for the list, called on {@link GUIListModule#onUpdateHead(Player player, GUIContainer gui)}
     *
     * @param search Whether search mode is enabled or not
     * @param gui The GUI to update the controls on
     */
    private void updateListControls(boolean search, GUIContainer gui)
    {
        ArrayList<GUIItem> pageList = (ArrayList<GUIItem>) (search ? searchList : list);

        int amountOfPages = (int)Math.ceil((pageList.size() + endItems.size()) / ((gui.getRows() - 2.0f) * gui.getCols()));

        for(int i = 1; i <= amountOfPages; i++)
        {
            if(i == curPage) continue;
            if(i < curPage && i + 3 >= curPage)
            {
                int col = (i - curPage) + 5;
                backItem.setNameView(Chat.chat("&fPage " + i));
                gui.setItem(gui.getRows(), col, backItem);
                gui.addEvent(gui.getRows(), col, new GUISwitchListPageEvent(plugin));
            }
            else if(i > curPage && i - 3 <= curPage)
            {
                int col = (i - curPage) + 5;
                forwardItem.setNameView(Chat.chat("&fPage " + i));
                gui.setItem(gui.getRows(), col, forwardItem);
                gui.addEvent(gui.getRows(), col, new GUISwitchListPageEvent(plugin));
            }
        }

        if(searchEnabled)
        {
            gui.setItem(gui.getRows(), 1, searchItem);
            gui.addEvent(gui.getRows(), 1, new GUIListSearchEvent(plugin));

            if(searchMode)
            {
                gui.setItem(gui.getRows(), 9, searchOffItem);
                gui.addEvent(gui.getRows(), 9, new GUIListSearchOffEvent(plugin));
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
    public void setGUIItems(ArrayList<GUIItem> items)
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
        return gui.getSlotFromRowCol(row-2, col-1) + pageOffset;
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
        gui.update(player);
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
        for(GUIItem item : list)
        {
            if(!SearchUtil.searchMetaFuzzy(item.getMetaView(), searchTerm)) continue;
            searchList.add(item);
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
     * Set the item that represents the back button
     *
     * @param backItem The new back item
     */
    public void setBackItem(GUIItem backItem)
    {
        this.backItem = backItem;
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
     * Set the item that represents the forward button
     *
     * @param forwardItem The new forward button
     */
    public void setForwardItem(GUIItem forwardItem)
    {
        this.forwardItem = forwardItem;
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
     * Set the item that represents the search button
     *
     * @param searchItem The new search item
     */
    public void setSearchItem(GUIItem searchItem)
    {
        this.searchItem = searchItem;
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
     * Set the item that represents the turn off search button
     *
     * @param searchOffItem The new search off item
     */
    public void setSearchOffItem(GUIItem searchOffItem)
    {
        this.searchOffItem = searchOffItem;
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
     * search list
     *
     * @return All items that are in the search list
     */
    public List<GUIItem> getSearchList()
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
}
