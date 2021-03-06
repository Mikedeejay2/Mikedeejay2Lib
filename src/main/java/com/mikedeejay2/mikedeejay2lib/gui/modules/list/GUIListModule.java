package com.mikedeejay2.mikedeejay2lib.gui.modules.list;

import com.mikedeejay2.mikedeejay2lib.PluginBase;
import com.mikedeejay2.mikedeejay2lib.gui.GUIContainer;
import com.mikedeejay2.mikedeejay2lib.gui.GUILayer;
import com.mikedeejay2.mikedeejay2lib.gui.event.list.GUIListSearchEvent;
import com.mikedeejay2.mikedeejay2lib.gui.event.list.GUIListSearchOffEvent;
import com.mikedeejay2.mikedeejay2lib.gui.event.list.GUISwitchListPageEvent;
import com.mikedeejay2.mikedeejay2lib.gui.item.GUIItem;
import com.mikedeejay2.mikedeejay2lib.gui.modules.GUIModule;
import com.mikedeejay2.mikedeejay2lib.util.chat.Chat;
import com.mikedeejay2.mikedeejay2lib.util.head.Base64Head;
import com.mikedeejay2.mikedeejay2lib.util.item.ItemComparison;
import com.mikedeejay2.mikedeejay2lib.util.item.ItemCreator;
import com.mikedeejay2.mikedeejay2lib.util.search.SearchUtil;
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
public class GUIListModule implements GUIModule
{
    protected final PluginBase plugin;
    // The list of items that this list holds
    protected List<GUIItem> list;
    // The search list of the items if search is enabled
    protected List<Map.Entry<GUIItem, Integer>> searchList;
    // Whether search mode is enabled or not
    protected boolean searchMode;
    // The search term that has previously been used
    protected String searchTerm;
    // The current page that the player is on
    protected int curLoc;
    // Items that will be appended to the very end of the list
    protected List<GUIItem> endItems;

    // The back item that will be used
    protected GUIItem backItem;
    // The forward item that will be used
    protected GUIItem forwardItem;
    // The search item that will be used
    protected GUIItem searchItem;
    // The turn search off item that will be used
    protected GUIItem searchOffItem;
    // Whether search is allowed or not
    protected boolean searchEnabled;
    // Whether this list has been changed since its last update
    protected boolean changed;

    protected ListViewMode viewMode;

    protected Map.Entry<Integer, Integer> topLeft;
    protected Map.Entry<Integer, Integer> bottomRight;
    protected Map.Entry<Integer, Integer> search;

    protected List<Map.Entry<Integer, Integer>> forwards;
    protected List<Map.Entry<Integer, Integer>> backs;

    protected String layerName;

    protected String pageChangePreName;
    protected String scrollChangePreName;

    public GUIListModule(PluginBase plugin, ListViewMode viewMode, int topRow, int bottomRow, int leftCol, int rightCol, String layerName)
    {
        this.plugin = plugin;
        this.list = new ArrayList<>();
        this.searchList = new ArrayList<>();
        this.layerName = layerName;

        curLoc = 1;
        endItems = new ArrayList<>();
        switch(viewMode)
        {
            case SCROLL:
            {
                this.backItem = new GUIItem(ItemCreator.createHeadItem(Base64Head.ARROW_UP_WHITE.get(), 1, GUIContainer.EMPTY_NAME));
                this.forwardItem = new GUIItem(ItemCreator.createHeadItem(Base64Head.ARROW_DOWN_WHITE.get(), 1, GUIContainer.EMPTY_NAME));
                backItem.addEvent(new GUISwitchListPageEvent());
                forwardItem.addEvent(new GUISwitchListPageEvent());
            } break;
            case PAGED:
            default:
            {
                this.backItem = new GUIItem(ItemCreator.createHeadItem(Base64Head.ARROW_BACKWARD_WHITE.get(), 1, GUIContainer.EMPTY_NAME));
                this.forwardItem = new GUIItem(ItemCreator.createHeadItem(Base64Head.ARROW_FORWARD_WHITE.get(), 1, GUIContainer.EMPTY_NAME));
                backItem.addEvent(new GUISwitchListPageEvent());
                forwardItem.addEvent(new GUISwitchListPageEvent());
            } break;
        }

        this.searchEnabled = false;
        this.changed = false;

        this.viewMode = viewMode;

        this.topLeft = new AbstractMap.SimpleEntry<>(topRow, leftCol);
        this.bottomRight = new AbstractMap.SimpleEntry<>(bottomRow, rightCol);
        this.forwards = new ArrayList<>();
        this.backs = new ArrayList<>();
        this.pageChangePreName = "&f";
        this.scrollChangePreName = "&f";
    }

    public GUIListModule(PluginBase plugin, ListViewMode viewMode, int topRow, int bottomRow, int leftCol, int rightCol)
    {
        this(plugin, viewMode, topRow, bottomRow, leftCol, rightCol, "list");
    }

    public GUIListModule(PluginBase plugin, int topRow, int bottomRow, int leftCol, int rightCol)
    {
        this(plugin, ListViewMode.PAGED, topRow, bottomRow, leftCol, rightCol, "list");
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
     * @param gui    The GUI
     */
    @Override
    public void onUpdateHead(Player player, GUIContainer gui)
    {
        GUILayer layer = gui.getLayer(layerName, false);
        if(searchMode)
        {
            searchThroughList();
        }
        updateListControls(layer, player);
        updatePage(layer);
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
        changed = true;
    }

    /**
     * Remove an end item from the end items list.
     *
     * @param endItem End item to remove
     */
    public void removeEndItem(GUIItem endItem)
    {
        endItems.remove(endItem);
        changed = true;
    }

    /**
     * Reset the end items to have no appended end items.
     */
    public void resetEndItems()
    {
        endItems.clear();
        changed = true;
    }

    /**
     * Reset the end items to have no appended end items.
     */
    public void resetList()
    {
        list.clear();
        searchList.clear();
        changed = true;
    }

    /**
     * Updates the current page, called on {@link GUIListModule#onUpdateHead(Player player, GUIContainer gui)}
     *
     * @param layer  The layer to update the page on
     */
    private void updatePage(GUILayer layer)
    {
        int topRow = topLeft.getKey();
        int bottomRow = bottomRight.getKey();
        int leftCol = topLeft.getValue();
        int rightCol = bottomRight.getValue();
        int rowDif = getSlotsPerRow();
        int colDif = getSlotsPerCol();

        int pageSize = getPageSize();
        int listSize = getCurSize();
        int pageOffset = getPageOffset();
        for(int i = 0; i < pageSize; i++)
        {
            // This algorithm calculates the normalized slot index based off of the bounding box of the list
            int slotIndex = ((topRow-1) * (layer.getCols() * ((int)(i / colDif)+1))) + (leftCol) + (i % colDif);
            --slotIndex;
            int row = layer.getRowFromSlot(slotIndex);
            int col = layer.getColFromSlot(slotIndex);
            // If a change has occurred all previous items have to be removed from the view first
            if(changed) layer.removeItem(row, col);

            if(listSize >= (i+1) + pageOffset) // List items
            {
                GUIItem item = searchMode ? searchList.get(i + pageOffset).getKey() : list.get(i + pageOffset);
                layer.setItem(row, col, item);
            }
            else if((i + 1) + pageOffset > listSize && !searchMode) // End items
            {
                int index = (i + pageOffset) - listSize;

                if(endItems.size() > index)
                {
                    GUIItem item = endItems.get(index);
                    layer.setItem(row, col, item);
                }
            }
        }
        changed = false;
    }

    /**
     * Update the controls for the list, called on {@link GUIListModule#onUpdateHead(Player player, GUIContainer gui)}
     *
     * @param layer  The layer to update the controls on
     */
    private void updateListControls(GUILayer layer, Player player)
    {
        int listSize = getCurSize();
        int amountOfPages = getPagesAmount(layer);

        // Remove previous forward items
        for(Map.Entry<Integer, Integer> entry : forwards)
        {
            int row = entry.getKey();
            int col = entry.getValue();
            layer.removeItem(row, col);
        }

        // Remove previous back items
        for(Map.Entry<Integer, Integer> entry : backs)
        {
            int row = entry.getKey();
            int col = entry.getValue();
            layer.removeItem(row, col);
        }

        int forwardSize = forwards.size();
        int backwardSize = backs.size();

        for(int i = curLoc + 1; i <= curLoc + forwardSize; ++i)
        {
            if(i > amountOfPages || i <= 0) continue;
            int index = Math.abs(i - curLoc) - 1;
            Map.Entry<Integer, Integer> entry = forwards.get(index);
            int row = entry.getKey();
            int col = entry.getValue();
            GUIItem curItem = forwardItem.clone();
            switch(viewMode)
            {
                case SCROLL:
                    curItem.setNameView(Chat.chat(scrollChangePreName + plugin.langManager().getTextLib(
                            player, "gui.modules.list.scroll_forward")));
                    break;
                case PAGED:
                default:
                    curItem.setNameView(Chat.chat(pageChangePreName + plugin.langManager().getTextLib(
                            player, "gui.modules.list.page",
                            new String[]{"PAGE"},
                            new String[]{String.valueOf(i)}
                            )));
                    break;
            }
            layer.setItem(row, col, curItem);
        }
        for(int i = curLoc - 1; i >= curLoc - backwardSize; --i)
        {
            if(i > amountOfPages || i <= 0) continue;
            int index = Math.abs(i - curLoc) - 1;
            Map.Entry<Integer, Integer> entry = backs.get(index);
            int row = entry.getKey();
            int col = entry.getValue();
            GUIItem curItem = backItem.clone();
            switch(viewMode)
            {
                case SCROLL:
                    curItem.setNameView(Chat.chat(scrollChangePreName + plugin.langManager().getTextLib(
                            player, "gui.modules.list.scroll_back")));
                    break;
                case PAGED:
                default:
                    curItem.setNameView(Chat.chat(pageChangePreName + plugin.langManager().getTextLib(
                            player, "gui.modules.list.page",
                            new String[]{"PAGE"},
                            new String[]{String.valueOf(i)}
                            )));
                    break;
            }
            layer.setItem(row, col, curItem);
        }

        if(searchEnabled)
        {
            layer.setItem(search.getKey(), search.getValue(), searchItem);

            if(searchMode)
            {
                layer.setItem(search.getKey(), search.getValue(), searchOffItem);
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
        changed = true;
    }

    /**
     * Change a GUI item based off of a row and column.
     * Not recommended for usage but still included just in case.
     *
     * @param item The item to replace the current item
     * @param row  The row to replace
     * @param col  The column to replace
     * @param gui  The GUI to update
     */
    public void changeGUIItem(GUIItem item, int row, int col, GUIContainer gui)
    {
        int pageOffset = getPageOffset();
        int index = gui.getSlotFromRowCol(row-2, col-1) + pageOffset;
        list.set(index, item);
        changed = true;
    }

    /**
     * Set the entire list of <tt>GUIItems</tt> to a new list
     *
     * @param items The list of items to set this list to
     */
    public void setGUIItems(List<GUIItem> items)
    {
        this.list = items;
        changed = true;
    }

    /**
     * Remove a <tt>GUIItem</tt> based on index
     *
     * @param index The index to the item that will be removed
     */
    public void removeGUIItem(int index)
    {
        list.remove(index);
        changed = true;
    }

    /**
     * Remove a GUIItem based on the instance of that item
     *
     * @param item The item to remove from this list
     */
    public void removeGUIItem(GUIItem item)
    {
        list.remove(item);
        changed = true;
    }

    /**
     * Get a list's index based off of its row and column in the GUI.
     * Not recommended to use but included just in case.
     *
     * @param row The row to get the item from
     * @param col The column to get the item from
     * @param gui The GUI to get the index from
     * @return The index based off of the row and column
     */
    public int getListItemIndex(int row, int col, GUIContainer gui)
    {
        int pageOffset = getPageOffset();
        int index = gui.getSlotFromRowCol(row-2, col-1) + pageOffset;
        if(searchMode)
        {
            index = searchList.get(index).getValue();
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
        changed = true;
    }

    /**
     * Add a list item to the list based off of the <tt>GUIContainer</tt> that the list is located in
     *
     * @param row  The row to add the item to
     * @param col  The column to add the item to
     * @param gui  The <tt>GUIContainer</tt> that this list is located in
     * @param item The <tt>GUIItem</tt> to be added
     */
    public void addListItem(int row, int col, GUIContainer gui, GUIItem item)
    {
        int index = getListItemIndex(row, col, gui);
        list.add(index, item);
        changed = true;
    }

    /**
     * Change the page to a new page
     *
     * @param page The page number
     */
    public void setListLoc(int page)
    {
        curLoc = page;
        changed = true;
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
        changed = true;
    }

    /**
     * Disable search mode in this list
     */
    public void disableSearchMode()
    {
        this.searchMode = false;
        this.searchTerm = null;
        changed = true;
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
            searchList.add(new AbstractMap.SimpleEntry<>(item, i));
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
     * search list and the indexes that they relate to in the list
     *
     * @return All items that are in the search list
     */
    public List<Map.Entry<GUIItem, Integer>> getSearchMap()
    {
        return searchList;
    }

    /**
     * Get the current page that this list is displaying
     *
     * @return The current page
     */
    public int getCurLoc()
    {
        return curLoc;
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

    public int getPageSize()
    {
        return getSlotsPerRow() * getSlotsPerCol();
    }

    public int getPageOffset()
    {
        switch(viewMode)
        {
            case SCROLL:
                return ((curLoc-1) * getSlotsPerCol());
            case PAGED:
            default:
                int pageSize = getPageSize();
                return ((curLoc-1) * pageSize);
        }
    }

    public int getPagesAmount(GUILayer layer)
    {
        switch(viewMode)
        {
            case SCROLL:
                return (int)Math.ceil((double)(getCurSize() + endItems.size()) / (double)(getPageSize() / layer.getCols()));
            case PAGED:
            default:
                return (int)Math.ceil((double)(getCurSize() + endItems.size()) / (double)getPageSize());
        }
    }

    public int getCurSize()
    {
        return (searchMode ? searchList.size() : list.size());
    }

    public int getSlotsPerRow()
    {
        int topRow = topLeft.getKey();
        int bottomRow = bottomRight.getKey();
        return (bottomRow - topRow) + 1;
    }

    public int getSlotsPerCol()
    {
        int leftCol = topLeft.getValue();
        int rightCol = bottomRight.getValue();
        return (rightCol - leftCol) + 1;
    }

    public GUIListModule setTopLeft(int row, int col)
    {
        this.topLeft = new AbstractMap.SimpleEntry<>(row, col);
        return this;
    }

    public GUIListModule setBottomRight(int row, int col)
    {
        this.bottomRight = new AbstractMap.SimpleEntry<>(row, col);
        return this;
    }

    public GUIListModule setSearch(int row, int col)
    {
        this.search = new AbstractMap.SimpleEntry<>(row, col);
        return this;
    }

    public GUIListModule addForward(int row, int col)
    {
        this.forwards.add(new AbstractMap.SimpleEntry<>(row, col));
        return this;
    }

    public GUIListModule addForward(int index, int row, int col)
    {
        this.forwards.add(index, new AbstractMap.SimpleEntry<>(row, col));
        return this;
    }

    public GUIListModule addBack(int row, int col)
    {
        this.backs.add(new AbstractMap.SimpleEntry<>(row, col));
        return this;
    }

    public GUIListModule addBack(int index, int row, int col)
    {
        this.backs.add(index, new AbstractMap.SimpleEntry<>(row, col));
        return this;
    }

    public GUIListModule clearForwards()
    {
        this.forwards.clear();
        return this;
    }

    public GUIListModule clearBacks()
    {
        this.backs.clear();
        return this;
    }

    public GUIListModule removeForward(int index)
    {
        this.forwards.remove(index);
        return this;
    }

    public GUIListModule removeBack(int index)
    {
        this.backs.remove(index);
        return this;
    }

    public Map.Entry<Integer, Integer> getTopLeft()
    {
        return topLeft;
    }

    public Map.Entry<Integer, Integer> getBottomRight()
    {
        return bottomRight;
    }

    public Map.Entry<Integer, Integer> getSearch()
    {
        return search;
    }

    public List<Map.Entry<Integer, Integer>> getForwards()
    {
        return forwards;
    }

    public List<Map.Entry<Integer, Integer>> getBacks()
    {
        return backs;
    }

    public String getLayerName()
    {
        return layerName;
    }

    public String getPageChangePreName()
    {
        return pageChangePreName;
    }

    public void setPageChangePreName(String pageChangePreName)
    {
        this.pageChangePreName = pageChangePreName;
    }

    public String getScrollChangePreName()
    {
        return scrollChangePreName;
    }

    public void setScrollChangePreName(String scrollChangePreName)
    {
        this.scrollChangePreName = scrollChangePreName;
    }
}
