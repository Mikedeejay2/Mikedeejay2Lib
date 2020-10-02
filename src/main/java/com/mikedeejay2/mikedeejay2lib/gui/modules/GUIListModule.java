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

public class GUIListModule extends GUIModule
{
    protected List<GUIItem> list;
    private List<GUIItem> searchList;
    private boolean searchMode;
    private String searchTerm;
    private int curPage;
    private List<GUIItem> endItems;

    private ItemStack backItem;
    private ItemStack forwardItem;
    private ItemStack searchItem;
    private ItemStack searchOffItem;
    private boolean searchEnabled;

    public GUIListModule(PluginBase plugin)
    {
        super(plugin);

        this.list = new ArrayList<>();
        this.searchList = new ArrayList<>();

        curPage = 1;
        endItems = new ArrayList<>();
        this.backItem = ItemCreator.createHeadItem(Base64Heads.HEAD_BACKWARDS_WHITE, 1, GUIContainer.EMPTY_NAME);
        this.forwardItem = ItemCreator.createHeadItem(Base64Heads.HEAD_FORWARD_WHITE, 1, GUIContainer.EMPTY_NAME);
        this.searchItem = ItemCreator.createItem(Material.COMPASS, 1, "&f&oSearch list...");
        this.searchOffItem = ItemCreator.createItem(Material.BOOK, 1, "&f&oTurn off search mode");

        this.searchEnabled = false;
    }

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

    public void addEndItem(GUIItem endItem)
    {
        endItems.add(endItem);
    }

    public void removeEndItem(GUIItem endItem)
    {
        endItems.remove(endItem);
    }

    public void resetEndItems()
    {
        endItems = new ArrayList<>();
    }

    public void updatePage(boolean search, GUIContainer gui)
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

    public void updateListControls(boolean search, GUIContainer gui)
    {
        ArrayList<GUIItem> pageList = (ArrayList<GUIItem>) (search ? searchList : list);

        int amountOfPages = (int)Math.ceil((pageList.size() + endItems.size()) / ((gui.getRows() - 2.0f) * gui.getCols()));

        for(int i = 1; i <= amountOfPages; i++)
        {
            if(i == curPage) continue;
            if(i < curPage && i + 3 >= curPage)
            {
                int col = (i - curPage) + 5;
                ItemStack back = backItem.clone();
                back.setAmount(i <= 64 ? i : 1);
                ItemMeta backMeta = back.getItemMeta();
                backMeta.setDisplayName(Chat.chat("&fPage " + i));
                back.setItemMeta(backMeta);
                gui.setItem(gui.getRows(), col, back);
                gui.addItemEvent(gui.getRows(), col, new GUISwitchListPageEvent(plugin));
            }
            else if(i > curPage && i - 3 <= curPage)
            {
                int col = (i - curPage) + 5;
                ItemStack forward = forwardItem.clone();
                forward.setAmount(i <= 64 ? i : 1);
                ItemMeta forwardMeta = forward.getItemMeta();
                forwardMeta.setDisplayName(Chat.chat("&fPage " + i));
                forward.setItemMeta(forwardMeta);
                gui.setItem(gui.getRows(), col, forward);
                gui.addItemEvent(gui.getRows(), col, new GUISwitchListPageEvent(plugin));
            }
        }

        if(searchEnabled)
        {
            gui.setItem(gui.getRows(), 1, searchItem);
            gui.addItemEvent(gui.getRows(), 1, new GUIListSearchEvent(plugin));

            if(searchMode)
            {
                gui.setItem(gui.getRows(), 9, searchOffItem);
                gui.addItemEvent(gui.getRows(), 9, new GUIListSearchOffEvent(plugin));
            }
        }
    }

    public void addListItem(GUIItem item)
    {
        list.add(item);
    }

    public void changeGUIItem(GUIItem item, int row, int col, GUIContainer gui)
    {
        int pageSize = ((gui.getRows() - 2) * 9);
        int pageOffset = ((curPage-1) * pageSize);
        int index = gui.getSlotFromRowCol(row-2, col-1) + pageOffset;
        list.set(index, item);
    }

    public void setGUIItems(ArrayList<GUIItem> items)
    {
        this.list = items;
    }

    public void removeGUIItem(int index)
    {
        list.remove(index);
    }

    public void removeGUIItem(GUIItem item)
    {
        list.remove(item);
    }

    public int getListSlotFromRowCol(int row, int col, GUIContainer gui)
    {
        int pageSize = ((gui.getRows() - 2) * 9);
        int pageOffset = ((curPage-1) * pageSize);
        return gui.getSlotFromRowCol(row-2, col-1) + pageOffset;
    }

    public void toListPage(int index, Player player, GUIContainer gui)
    {
        curPage = index;
        gui.update(player);
    }

    public void enableSearchMode(String search)
    {
        this.searchMode = true;
        this.searchTerm = search;
    }

    public void disableSearchMode()
    {
        this.searchMode = false;
        this.searchTerm = null;
    }

    public void searchThroughList()
    {
        searchMode = true;
        searchList.clear();
        for(GUIItem item : list)
        {
            if(!SearchUtil.searchMetaFuzzy(item.getMetaView(), searchTerm)) continue;
            searchList.add(item);
        }
    }

    public ItemStack getBackItem()
    {
        return backItem;
    }

    public void setBackItem(ItemStack backItem)
    {
        this.backItem = backItem;
    }

    public ItemStack getForwardItem()
    {
        return forwardItem;
    }

    public void setForwardItem(ItemStack forwardItem)
    {
        this.forwardItem = forwardItem;
    }

    public boolean isSearchEnabled()
    {
        return searchEnabled;
    }

    public String getSearchTerm()
    {
        return searchTerm;
    }

    public void setSearchTerm(String searchTerm)
    {
        this.searchTerm = searchTerm;
    }

    public ItemStack getSearchItem()
    {
        return searchItem;
    }

    public void setSearchItem(ItemStack searchItem)
    {
        this.searchItem = searchItem;
    }

    public ItemStack getSearchOffItem()
    {
        return searchOffItem;
    }

    public void setSearchOffItem(ItemStack searchOffItem)
    {
        this.searchOffItem = searchOffItem;
    }

    public List<GUIItem> getList()
    {
        return list;
    }

    public List<GUIItem> getSearchList()
    {
        return searchList;
    }

    public int getCurPage()
    {
        return curPage;
    }

    public List<GUIItem> getEndItems()
    {
        return endItems;
    }
}
