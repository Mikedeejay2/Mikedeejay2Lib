package com.mikedeejay2.mikedeejay2lib.gui.modules.list;

import com.mikedeejay2.mikedeejay2lib.PluginBase;
import com.mikedeejay2.mikedeejay2lib.gui.GUIContainer;
import com.mikedeejay2.mikedeejay2lib.gui.GUILayer;
import com.mikedeejay2.mikedeejay2lib.gui.event.list.GUIListSearchEvent;
import com.mikedeejay2.mikedeejay2lib.gui.event.list.GUIListSearchOffEvent;
import com.mikedeejay2.mikedeejay2lib.gui.event.list.GUISwitchListLocEvent;
import com.mikedeejay2.mikedeejay2lib.gui.item.GUIItem;
import com.mikedeejay2.mikedeejay2lib.gui.modules.GUIModule;
import com.mikedeejay2.mikedeejay2lib.util.chat.Colors;
import com.mikedeejay2.mikedeejay2lib.util.head.Base64Head;
import com.mikedeejay2.mikedeejay2lib.util.item.ItemComparison;
import com.mikedeejay2.mikedeejay2lib.util.item.ItemCreator;
import com.mikedeejay2.mikedeejay2lib.util.search.SearchUtil;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * GUI Module that turns the GUI into a list that shows a list of different
 * <tt>GUIItems</tt>. A list can also have multiple pages (or scroll!) and can be searched
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
    // The current location that the player is on
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

    // The current view mode of the list
    protected ListViewMode viewMode;

    // The top left location of the list
    protected Map.Entry<Integer, Integer> topLeft;
    // The bottom right location of the list
    protected Map.Entry<Integer, Integer> bottomRight;
    // The location of the search button (if visible)
    protected Map.Entry<Integer, Integer> search;
    // The locations of the forwards buttons
    protected List<Map.Entry<Integer, Integer>> forwards;
    // The locations of the forwards buttons
    protected List<Map.Entry<Integer, Integer>> backs;

    // The list layer's name, used for getting the layer that the list is located on
    protected String layerName;

    // The prefix of the page change name. This can be used to change the color of text or add text
    protected String pageChangePreName;
    // The prefix of the scroll change name. This can be used to change the color of text or add text
    protected String scrollChangePreName;
    // The prefix of the search name. This can be used to change the color of text or add text
    protected String searchPreName;
    // The prefix of the search off name. This can be used to change the color of text or add text
    protected String searchOffPreName;

    /**
     * Construct a new GUI List module
     *
     * @param plugin    Reference to the <tt>PluginBase</tt> of the plugin
     * @param viewMode  The {@link ListViewMode} to used
     * @param topRow    The top row of the list's bounding box
     * @param bottomRow The bottom row of the list's bounding box
     * @param leftCol   The left column of the list's bounding box
     * @param rightCol  The right column of the list's bounding box
     * @param layerName The name of the <tt>GUILayer</tt> that will be used,
     *                  useful for if there are multiple lists in one GUI
     */
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
                backItem.addEvent(new GUISwitchListLocEvent());
                forwardItem.addEvent(new GUISwitchListLocEvent());
            } break;
            case PAGED:
            default:
            {
                this.backItem = new GUIItem(ItemCreator.createHeadItem(Base64Head.ARROW_BACKWARD_WHITE.get(), 1, GUIContainer.EMPTY_NAME));
                this.forwardItem = new GUIItem(ItemCreator.createHeadItem(Base64Head.ARROW_FORWARD_WHITE.get(), 1, GUIContainer.EMPTY_NAME));
                backItem.addEvent(new GUISwitchListLocEvent());
                forwardItem.addEvent(new GUISwitchListLocEvent());
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
        this.searchPreName = "&f&o";
        this.searchOffPreName = "&f&o";
    }

    /**
     * Construct a new GUI List module
     *
     * @param plugin    Reference to the <tt>PluginBase</tt> of the plugin
     * @param viewMode  The {@link ListViewMode} to used
     * @param topRow    The top row of the list's bounding box
     * @param bottomRow The bottom row of the list's bounding box
     * @param leftCol   The left column of the list's bounding box
     * @param rightCol  The right column of the list's bounding box
     */
    public GUIListModule(PluginBase plugin, ListViewMode viewMode, int topRow, int bottomRow, int leftCol, int rightCol)
    {
        this(plugin, viewMode, topRow, bottomRow, leftCol, rightCol, "list");
    }

    /**
     * Construct a new GUI List module
     *
     * @param plugin    Reference to the <tt>PluginBase</tt> of the plugin
     * @param topRow    The top row of the list's bounding box
     * @param bottomRow The bottom row of the list's bounding box
     * @param leftCol   The left column of the list's bounding box
     * @param rightCol  The right column of the list's bounding box
     */
    public GUIListModule(PluginBase plugin, int topRow, int bottomRow, int leftCol, int rightCol)
    {
        this(plugin, ListViewMode.PAGED, topRow, bottomRow, leftCol, rightCol, "list");
    }

    /**
     * Method called on the opening of a GUI
     *
     * @param player The player that is viewing the GUI
     * @param gui    The GUI
     */
    @Override
    public void onOpenHead(Player player, GUIContainer gui)
    {
        if(searchItem == null)
        {
            this.searchItem = new GUIItem(ItemCreator.createItem(Material.COMPASS, 1, searchPreName + plugin.getLibLangManager().getText(player, "gui.modules.list.search")));
            searchItem.addEvent(new GUIListSearchEvent(plugin));
        }
        if(searchOffItem == null)
        {
            this.searchOffItem = new GUIItem(ItemCreator.createItem(Material.BOOK, 1, searchOffPreName + plugin.getLibLangManager().getText(player, "gui.modules.list.search_off")));
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
        updateView(layer);
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
     * Updates the current list view, called on {@link GUIListModule#onUpdateHead(Player player, GUIContainer gui)}
     *
     * @param layer  The layer to update the view on
     */
    private void updateView(GUILayer layer)
    {
        int topRow = topLeft.getKey();
        // int bottomRow = bottomRight.getKey(); // unused
        int leftCol = topLeft.getValue();
        // int rightCol = bottomRight.getValue(); // unused
        // int rowDif = getSlotsPerRow(); // unused
        int colDif = getSlotsPerCol();

        int viewSize = getViewSize();
        int listSize = getCurSize();
        int viewOffset = getViewOffset();
        for(int i = 0; i < viewSize; i++)
        {
            // This algorithm calculates the normalized slot index based off of the bounding box of the list
            int slotIndex = ((topRow-1) * (layer.getCols() * ((int)(i / colDif)+1))) + (leftCol) + (i % colDif);
            --slotIndex;
            int row = layer.getRowFromSlot(slotIndex);
            int col = layer.getColFromSlot(slotIndex);
            // If a change has occurred all previous items have to be removed from the view first
            if(changed) layer.removeItem(row, col);

            if(listSize >= (i+1) + viewOffset) // List items
            {
                GUIItem item = searchMode ? searchList.get(i + viewOffset).getKey() : list.get(i + viewOffset);
                layer.setItem(row, col, item);
            }
            else if((i + 1) + viewOffset > listSize && !searchMode) // End items
            {
                int index = (i + viewOffset) - listSize;

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
        int maxViews = getMaxViews();

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
            if(i > maxViews || i <= 0) continue;
            int index = Math.abs(i - curLoc) - 1;
            Map.Entry<Integer, Integer> entry = forwards.get(index);
            int row = entry.getKey();
            int col = entry.getValue();
            GUIItem curItem = forwardItem.clone();
            switch(viewMode)
            {
                case SCROLL:
                    curItem.setNameView(Colors.format(scrollChangePreName + plugin.getLibLangManager().getText(
                            player, "gui.modules.list.scroll_forward")));
                    break;
                case PAGED:
                default:
                    curItem.setNameView(Colors.format(pageChangePreName + plugin.getLibLangManager().getText(
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
            if(i > maxViews || i <= 0) continue;
            int index = Math.abs(i - curLoc) - 1;
            Map.Entry<Integer, Integer> entry = backs.get(index);
            int row = entry.getKey();
            int col = entry.getValue();
            GUIItem curItem = backItem.clone();
            switch(viewMode)
            {
                case SCROLL:
                    curItem.setNameView(Colors.format(scrollChangePreName + plugin.getLibLangManager().getText(
                            player, "gui.modules.list.scroll_back")));
                    break;
                case PAGED:
                default:
                    curItem.setNameView(Colors.format(pageChangePreName + plugin.getLibLangManager().getText(
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
     * Add an item to the list at a specified index
     *
     * @param index The index to add the item at
     * @param item The item that will be added to the list
     */
    public void addListItem(int index, GUIItem item)
    {
        list.add(index, item);
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
        int viewOffset = getViewOffset();
        int index = gui.getSlotFromRowCol(row-2, col-1) + viewOffset;
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
        int viewOffset = getViewOffset();
        int index = gui.getSlotFromRowCol(row-2, col-1) + viewOffset;
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
     * Change the list view to a new location
     *
     * @param location The new location
     */
    public void setListLoc(int location)
    {
        curLoc = location;
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
     * Get the current view location that this list is displaying
     *
     * @return The current view location
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

    /**
     * Get the current viewing size of the list.
     * <p>
     * This method is based off of the area of the list's slots.
     * This method calls {@link GUIListModule#getSlotsPerRow()} * {@link GUIListModule#getSlotsPerCol()}
     * to calculate the area of the current view.
     *
     * @return The current view size (area)
     */
    public int getViewSize()
    {
        return getSlotsPerRow() * getSlotsPerCol();
    }

    /**
     * Get current view offset of the list. This is based off of the
     * current location in the list and the view mode of the list.
     *
     * @return The view offset
     */
    public int getViewOffset()
    {
        switch(viewMode)
        {
            case SCROLL:
                return ((curLoc-1) * getSlotsPerCol());
            case PAGED:
            default:
                int pageSize = getViewSize();
                return ((curLoc-1) * pageSize);
        }
    }

    /**
     * Get the maximum amount of views for this list.
     * <p>
     * For page view mode, this means the maximum amount of pages of this list.
     * <p>
     * For scroll view mode, this means the maximum amount of scrolls of this list.
     *
     * @return The maximum views
     */
    public int getMaxViews()
    {
        switch(viewMode)
        {
            case SCROLL:
                return (int)Math.ceil((double)((getCurSize() + endItems.size()) - getViewSize()) / (double)(getViewSize() / getSlotsPerCol())) + 1;
            case PAGED:
            default:
                return (int)Math.ceil((double)(getCurSize() + endItems.size()) / (double) getViewSize());
        }
    }

    /**
     * Get the current size of the list being viewed.
     *
     * @return The size of the currently selected list
     */
    public int getCurSize()
    {
        return (searchMode ? searchList.size() : list.size());
    }

    /**
     * Get the amount of slots per row of this list.
     * <p>
     * This is calculated from the bounding box of this list.
     *
     * @return The amount of slots per row
     */
    public int getSlotsPerRow()
    {
        int topRow = topLeft.getKey();
        int bottomRow = bottomRight.getKey();
        return (bottomRow - topRow) + 1;
    }

    /**
     * Get the amount of slots per column of this list.
     * <p>
     * This is calculated from the bounding box of this list.
     *
     * @return The amount of slots per column
     */
    public int getSlotsPerCol()
    {
        int leftCol = topLeft.getValue();
        int rightCol = bottomRight.getValue();
        return (rightCol - leftCol) + 1;
    }

    /**
     * Set the top left corner of this list's view.
     *
     * @param row The row of the top left corner
     * @param col The column of the top left corner
     * @return A reference to this <tt>GUIListModule</tt>
     */
    public GUIListModule setTopLeft(int row, int col)
    {
        this.topLeft = new AbstractMap.SimpleEntry<>(row, col);
        return this;
    }

    /**
     * Set the bottom right corner of this list's view.
     *
     * @param row The row of the bottom right corner
     * @param col The column of the bottom right corner
     * @return A reference to this <tt>GUIListModule</tt>
     */
    public GUIListModule setBottomRight(int row, int col)
    {
        this.bottomRight = new AbstractMap.SimpleEntry<>(row, col);
        return this;
    }

    /**
     * Set the location of this list's search button
     *
     * @param row The row
     * @param col The column
     * @return A reference to this <tt>GUIListModule</tt>
     */
    public GUIListModule setSearch(int row, int col)
    {
        this.search = new AbstractMap.SimpleEntry<>(row, col);
        return this;
    }

    /**
     * Add a location for a forward button to be placed.
     * Forward buttons increase per index. That means that a forward button of index 0
     * will move the current view by 1, but a forward index of 4 will move the view
     * by 5.
     *
     * @param row The row of the button
     * @param col The column of the button
     * @return A reference to this <tt>GUIListModule</tt>
     */
    public GUIListModule addForward(int row, int col)
    {
        this.forwards.add(new AbstractMap.SimpleEntry<>(row, col));
        return this;
    }

    /**
     * Add a location for a forward button to be placed.
     * Forward buttons increase per index. That means that a forward button of index 0
     * will move the current view by 1, but a forward index of 4 will move the view
     * by 5.
     *
     * @param index The index to add the new location at
     * @param row The row of the button
     * @param col The column of the button
     * @return A reference to this <tt>GUIListModule</tt>
     */
    public GUIListModule addForward(int index, int row, int col)
    {
        this.forwards.add(index, new AbstractMap.SimpleEntry<>(row, col));
        return this;
    }

    /**
     * Add a location for a back button to be placed.
     * Back buttons increase per index. That means that a back button of index 0
     * will move the current view by 1, but a back index of 4 will move the view
     * by 5.
     *
     * @param row The row of the button
     * @param col The column of the button
     * @return A reference to this <tt>GUIListModule</tt>
     */
    public GUIListModule addBack(int row, int col)
    {
        this.backs.add(new AbstractMap.SimpleEntry<>(row, col));
        return this;
    }

    /**
     * Add a location for a back button to be placed.
     * Back buttons increase per index. That means that a back button of index 0
     * will move the current view by 1, but a back index of 4 will move the view
     * by 5.
     *
     * @param index The index to add the new location at
     * @param row The row of the button
     * @param col The column of the button
     * @return A reference to this <tt>GUIListModule</tt>
     */
    public GUIListModule addBack(int index, int row, int col)
    {
        this.backs.add(index, new AbstractMap.SimpleEntry<>(row, col));
        return this;
    }

    /**
     * Clear the locations of all forward buttons.
     *
     * @return A reference to this <tt>GUIListModule</tt>
     */
    public GUIListModule clearForwards()
    {
        this.forwards.clear();
        return this;
    }

    /**
     * Clear the locations of all back buttons.
     *
     * @return A reference to this <tt>GUIListModule</tt>
     */
    public GUIListModule clearBacks()
    {
        this.backs.clear();
        return this;
    }

    /**
     * Remove a forward button via the button's index
     *
     * @param index The index to remove
     * @return A reference to this <tt>GUIListModule</tt>
     */
    public GUIListModule removeForward(int index)
    {
        this.forwards.remove(index);
        return this;
    }

    /**
     * Remove a back button via the button's index
     *
     * @param index The index to remove
     * @return A reference to this <tt>GUIListModule</tt>
     */
    public GUIListModule removeBack(int index)
    {
        this.backs.remove(index);
        return this;
    }

    /**
     * Get the top left location of this list's view
     *
     * @return An <tt>Entry</tt> holding the row in the key and column in the value
     */
    public Map.Entry<Integer, Integer> getTopLeft()
    {
        return topLeft;
    }

    /**
     * Get the bottom right location of this list's view
     *
     * @return An <tt>Entry</tt> holding the row in the key and column in the value
     */
    public Map.Entry<Integer, Integer> getBottomRight()
    {
        return bottomRight;
    }

    /**
     * Get the search location of this list's view
     *
     * @return An <tt>Entry</tt> holding the row in the key and column in the value
     */
    public Map.Entry<Integer, Integer> getSearch()
    {
        return search;
    }

    /**
     * Get the top left location of this list's view
     *
     * @return An <tt>Entry</tt> holding the row in the key and column in the value
     */
    public List<Map.Entry<Integer, Integer>> getForwards()
    {
        return forwards;
    }

    /**
     * Get the back locations of this list
     *
     * @return A list of entries containing the row in the key and column in the value
     */
    public List<Map.Entry<Integer, Integer>> getBacks()
    {
        return backs;
    }

    /**
     * Get the name of the layer that this list is located on in the GUI
     *
     * @return The layer name
     */
    public String getLayerName()
    {
        return layerName;
    }

    /**
     * Get the prefix of the page change name. This can be used to change the color of text or add text
     *
     * @return Page change prefix
     */
    public String getPageChangePreName()
    {
        return pageChangePreName;
    }

    /**
     * Set the prefix of the page change name. This can be used to change the color of text or add text
     *
     * @param pageChangePreName The new page change prefix
     */
    public void setPageChangePreName(String pageChangePreName)
    {
        this.pageChangePreName = pageChangePreName;
    }

    /**
     * Get the prefix of the scroll change name. This can be used to change the color of text or add text
     *
     * @return Scroll change prefix
     */
    public String getScrollChangePreName()
    {
        return scrollChangePreName;
    }

    /**
     * Set the prefix of the page change name. This can be used to change the color of text or add text
     *
     * @param scrollChangePreName The new scroll change prefix
     */
    public void setScrollChangePreName(String scrollChangePreName)
    {
        this.scrollChangePreName = scrollChangePreName;
    }
}
