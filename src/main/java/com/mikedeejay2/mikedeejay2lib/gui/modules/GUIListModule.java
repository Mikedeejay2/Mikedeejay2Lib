package com.mikedeejay2.mikedeejay2lib.gui.modules;

import com.mikedeejay2.mikedeejay2lib.PluginBase;
import com.mikedeejay2.mikedeejay2lib.gui.GUIContainer;
import com.mikedeejay2.mikedeejay2lib.gui.event.list.GUIListSearchEvent;
import com.mikedeejay2.mikedeejay2lib.gui.event.list.GUIListSearchOffEvent;
import com.mikedeejay2.mikedeejay2lib.gui.event.list.GUISwitchListPageEvent;
import com.mikedeejay2.mikedeejay2lib.gui.item.GUIItem;
import com.mikedeejay2.mikedeejay2lib.gui.util.GUIMath;
import com.mikedeejay2.mikedeejay2lib.util.Base64Heads;
import com.mikedeejay2.mikedeejay2lib.util.SearchUtil;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

import static com.mikedeejay2.mikedeejay2lib.gui.GUIContainer.COLUMN_SIZE;

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

    public GUIListModule(PluginBase plugin, GUIContainer gui)
    {
        super(plugin, gui);

        this.list = new ArrayList<GUIItem>();
        this.searchList = new ArrayList<GUIItem>();

        this.curPage = curPage;
        endItems = new ArrayList<>();
        this.backItem = plugin.itemCreator().createHeadItem(Base64Heads.HEAD_BACKWARDS_WHITE, 1, " ");
        this.forwardItem = plugin.itemCreator().createHeadItem(Base64Heads.HEAD_FORWARD_WHITE, 1, " ");
    }

    @Override
    public void onUpdate(Player player)
    {
        if(searchMode)
        {
            searchThroughList();
        }
        updateListControls(searchMode);
        updatePage(searchMode);
    }

    @Override
    public void onClicked(Player player, int row, int col, GUIItem clicked)
    {

    }

    @Override
    public void onOpen(Player player)
    {

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

    public void updatePage(boolean search)
    {
        ArrayList<GUIItem> pageList = (ArrayList<GUIItem>) (search ? searchList : list);
        int pageSize = ((gui.getRows() - 2) * COLUMN_SIZE);
        for(int i = 0; i < pageSize; i++)
        {
            int pageOffset = ((curPage-1) * pageSize);
            int row = GUIMath.getRowFromSlot(i + COLUMN_SIZE), col = GUIMath.getColFromSlot(i);
            gui.fullResetSlot(row, col);

            if(pageList.size() >= (i+1) + pageOffset)
            {
                GUIItem item = pageList.get(i + pageOffset);
                gui.setSlotEvents(row, col, item.getEvents());
                gui.setItem(GUIMath.getRowFromSlot(i + COLUMN_SIZE), GUIMath.getColFromSlot(i), item.getItem());
            }
            else if((i + 1) + pageOffset > pageList.size() && !search)
            {
                int index = (i + pageOffset) - pageList.size();

                if(endItems.size() > index)
                {
                    GUIItem item = endItems.get(index);
                    gui.setItem(row, col, item.getItem());
                    gui.setSlotEvents(row, col, item.getEvents());
                }
            }
        }
    }

    public void updateListControls(boolean search)
    {
        ArrayList<GUIItem> pageList = (ArrayList<GUIItem>) (search ? searchList : list);
        for(int col = 1; col <= COLUMN_SIZE; col++)
        {
            gui.fullResetSlot(gui.getRows(), col);
        }

        int amountOfPages = (int)Math.ceil((pageList.size() + endItems.size()) / ((gui.getRows() - 2.0f) * COLUMN_SIZE));

        for(int i = 1; i <= amountOfPages; i++)
        {
            if(i == curPage) continue;
            if(i < curPage && i + 3 >= curPage)
            {
                int col = (i - curPage) + 5;
                gui.fullResetSlot(gui.getRows(), col);
                ItemStack back = backItem.clone();
                back.setAmount(i <= 64 ? i : 1);
                back.getItemMeta().setDisplayName("Page " + i);
                gui.setItem(gui.getRows(), col, back);
                gui.setSlotEvent(gui.getRows(), col, new GUISwitchListPageEvent(plugin));
            }
            else if(i > curPage && i - 3 <= curPage)
            {
                int col = (i - curPage) + 5;
                gui.fullResetSlot(gui.getRows(), col);
                ItemStack forward = forwardItem.clone();
                forward.setAmount(i <= 64 ? i : 1);
                forward.getItemMeta().setDisplayName("Page " + i);
                gui.setItem(gui.getRows(), col, forward);
                gui.setSlotEvent(gui.getRows(), col, new GUISwitchListPageEvent(plugin));
            }
        }

        gui.setItem(gui.getRows(), 1, Material.COMPASS, 1, "&f&oSearch list...");
        gui.setSlotEvent(gui.getRows(), 1, new GUIListSearchEvent(plugin));

        if(searchMode)
        {
            gui.setItem(gui.getRows(), 9, Material.BOOK, 1, "&f&oTurn off search mode");
            gui.setSlotEvent(gui.getRows(), 9, new GUIListSearchOffEvent(plugin));
        }
    }

    public void addGUIItem(GUIItem item)
    {
        list.add(item);
    }

    public void changeGUIItem(GUIItem item, int row, int col)
    {
        int pageSize = ((gui.getRows() - 2) * 9);
        int pageOffset = ((curPage-1) * pageSize);
        int index = GUIMath.getSlotFromRowCol(row-2, col-1) + pageOffset;
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

    public int getListSlotFromRowCol(int row, int col)
    {
        int pageSize = ((gui.getRows() - 2) * 9);
        int pageOffset = ((curPage-1) * pageSize);
        int index = GUIMath.getSlotFromRowCol(row-2, col-1) + pageOffset;
        return index;
    }

    public void toListPage(int index, Player player)
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
            if(!SearchUtil.searchMetaFuzzy(item.getItemMeta(), searchTerm)) continue;
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
}
