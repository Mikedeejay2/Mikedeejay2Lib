package com.mikedeejay2.mikedeejay2lib.gui.modules.explorer;

import com.mikedeejay2.mikedeejay2lib.BukkitPlugin;
import com.mikedeejay2.mikedeejay2lib.gui.GUIContainer;
import com.mikedeejay2.mikedeejay2lib.gui.GUILayer;
import com.mikedeejay2.mikedeejay2lib.gui.event.GUIEvent;
import com.mikedeejay2.mikedeejay2lib.gui.item.GUIItem;
import com.mikedeejay2.mikedeejay2lib.gui.modules.list.GUIListModule;
import com.mikedeejay2.mikedeejay2lib.item.ItemBuilder;
import com.mikedeejay2.mikedeejay2lib.util.head.Base64Head;
import com.mikedeejay2.mikedeejay2lib.util.structure.HistoryHolder;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Comparator;
import java.util.List;

/**
 * An item explorer for viewing and interacting with organized items.
 *
 * @author Mikedeejay2
 */
public class GUIItemExplorerModule extends GUIListModule
{
    /**
     * The folder currently being viewed
     */
    protected ItemFolder folder;

    /**
     * The item view history
     */
    protected HistoryHolder<ItemFolder> history;

    /**
     * The valid back navigation item
     */
    protected GUIItem backItemValid;

    /**
     * The valid forward navigation item
     */
    protected GUIItem forwardItemValid;

    /**
     * Construct a new <code>GUIItemExplorerModule</code>
     *
     * @param plugin    The {@link BukkitPlugin} instance
     * @param folder    The root {@link ItemFolder} of the explorer
     * @param viewMode  The viewing mode of the list
     * @param topRow    The top row location of the explorer
     * @param bottomRow The bottom row location of the explorer
     * @param leftCol   The left column location of the explorer
     * @param rightCol  The right column location of the explorer
     * @param layerName The name of the layer that the explorer will exist on
     */
    public GUIItemExplorerModule(BukkitPlugin plugin, ItemFolder folder, ListViewMode viewMode, int topRow, int bottomRow, int leftCol, int rightCol, String layerName)
    {
        super(plugin, viewMode, topRow, bottomRow, leftCol, rightCol, layerName);
        this.history = new HistoryHolder<>();
        this.addBack(1, 8);
        this.addForward(1, 9);

        this.folder = folder;

        this.backItemValid = new GUIItem(
            ItemBuilder.of(Base64Head.ARROW_LEFT_WHITE.get())
                .setName("&f" + plugin.getLibLangManager().getText("gui.modules.navigator.backward"))
                .get())
            .addEvent(new GUINavFolderBackEvent());
        this.forwardItemValid = new GUIItem(
            ItemBuilder.of(Base64Head.ARROW_RIGHT_WHITE.get())
                .setName("&f" + plugin.getLibLangManager().getText("gui.modules.navigator.backward"))
                .get())
            .addEvent(new GUINavFolderForwardEvent());
    }

    /**
     * Construct a new <code>GUIItemExplorerModule</code>
     *
     * @param plugin    The {@link BukkitPlugin} instance
     * @param folder    The root {@link ItemFolder} of the explorer
     * @param viewMode  The viewing mode of the list
     * @param topRow    The top row location of the explorer
     * @param bottomRow The bottom row location of the explorer
     * @param leftCol   The left column location of the explorer
     * @param rightCol  The right column location of the explorer
     */
    public GUIItemExplorerModule(BukkitPlugin plugin, ItemFolder folder, ListViewMode viewMode, int topRow, int bottomRow, int leftCol, int rightCol)
    {
        this(plugin, folder, viewMode, topRow, bottomRow, leftCol, rightCol, "explorer");
    }

    /**
     * Construct a new <code>GUIItemExplorerModule</code>
     *
     * @param plugin    The {@link BukkitPlugin} instance
     * @param folder    The root {@link ItemFolder} of the explorer
     * @param topRow    The top row location of the explorer
     * @param bottomRow The bottom row location of the explorer
     * @param leftCol   The left column location of the explorer
     * @param rightCol  The right column location of the explorer
     */
    public GUIItemExplorerModule(BukkitPlugin plugin, ItemFolder folder, int topRow, int bottomRow, int leftCol, int rightCol)
    {
        this(plugin, folder, ListViewMode.SCROLL, topRow, bottomRow, leftCol, rightCol);
    }

    /**
     * Overridden <code>onOpenHead</code> that fills the explorer with items
     *
     * @param player The player that is viewing the GUI
     * @param gui    The GUI
     */
    @Override
    public void onOpenHead(Player player, GUIContainer gui)
    {
        super.onOpenHead(player, gui);
        gui.setInventoryName(folder.getName());
        resetList();
        localize(player);

        fillList(player, gui);
        GUILayer baseLayer = gui.getLayer(0);
        fillDecor(baseLayer);
    }

    /**
     * Overridden update method that updates the history buttons
     *
     * @param player The player that is viewing the GUI
     * @param gui    The GUI
     */
    @Override
    public void onUpdateHead(Player player, GUIContainer gui)
    {
        super.onUpdateHead(player, gui);

        GUILayer layer = gui.getLayer(layerName);
        setHistoryButtons(layer);
    }

    /**
     * Localize the GUI history buttons
     *
     * @param player The reference player viewing the GUI
     */
    private void localize(Player player)
    {
        this.backItemValid.setName("&f" + plugin.getLibLangManager().getText(player, "gui.modules.navigator.backward"))
            .setAmount(Math.min(Math.max(1, history.backSize()), 64));
        this.forwardItemValid.setName("&f" + plugin.getLibLangManager().getText(player, "gui.modules.navigator.backward"))
            .setAmount(Math.min(Math.max(1, history.forwardSize()), 64));
    }

    /**
     * Set the folder history buttons
     *
     * @param layer The <code>GUILayer</code> to set the items on
     */
    private void setHistoryButtons(GUILayer layer)
    {
        layer.setItem(1, 1, history.hasBack() ? backItemValid : null);
        layer.setItem(1, 2, history.hasForward() ? forwardItemValid : null);
    }

    /**
     * Fill the decoration items for the GUI
     *
     * @param baseLayer The base layer of the GUI, the layer that the background decor will be set on
     */
    private void fillDecor(GUILayer baseLayer)
    {
        GUIItem background1 = backItemValid.clone();
        GUIItem background2 = forwardItemValid.clone();
        GUIItem background3 = backItem.clone();
        GUIItem background4 = forwardItem.clone();
        background1.setHeadBase64(Base64Head.ARROW_LEFT_LIGHT_GRAY.get()).resetEvents();
        background2.setHeadBase64(Base64Head.ARROW_RIGHT_LIGHT_GRAY.get()).resetEvents();
        background3.setHeadBase64(Base64Head.ARROW_UP_LIGHT_GRAY.get()).resetEvents();
        background4.setHeadBase64(Base64Head.ARROW_DOWN_LIGHT_GRAY.get()).resetEvents();
        baseLayer.setItem(1, 1, background1);
        baseLayer.setItem(1, 2, background2);
        baseLayer.setItem(1, 8, background3);
        baseLayer.setItem(1, 9, background4);
    }

    /**
     * Fill the GUI list with all items in the current folder
     *
     * @param player The player that is viewing the GUI
     * @param gui    The GUI
     */
    private void fillList(Player player, GUIContainer gui)
    {
        this.resetList();
        List<ItemFolder> folders = folder.getFolders();
        List<GUIItem> guiItems = folder.getItems();

        folders.sort(Comparator.comparing(ItemFolder::getName));
        guiItems.sort(Comparator.comparing(guiItem -> {
            if(guiItem == null) return null;
            ItemMeta meta = guiItem.getMeta();
            if(meta.hasDisplayName())
            {
                return meta.getDisplayName();
            }
            return guiItem.getType().toString();
        }));
        for(ItemFolder folder : folders)
        {
            GUIItem item = new GUIItem(folder.getFolderItem());
            item.setName(folder.getName());
            item.addEvent(new GUISwitchFolderEvent(folder));
            this.addListItem(item);
        }
        for(GUIItem item : guiItems)
        {
            this.addListItem(item);
        }
    }

    /**
     * Get the current folder of this GUI
     *
     * @return The folder
     */
    public ItemFolder getFolder()
    {
        return folder;
    }

    /**
     * Set the current folder of this GUI
     *
     * @param folder The new folder to use
     */
    public void setFolder(ItemFolder folder)
    {
        this.folder = folder;
    }

    /**
     * Get the {@link HistoryHolder} of the folder history for this GUI
     *
     * @return The folder history
     */
    public HistoryHolder<ItemFolder> getHistory()
    {
        return history;
    }

    /**
     * Event to switch a folder in a {@link GUIItemExplorerModule} GUI
     *
     * @author Mikedeejay2
     */
    public static class GUISwitchFolderEvent implements GUIEvent
    {
        /**
         * The folder to be switched to
         */
        private final ItemFolder folder;

        /**
         * Construct a new <code>GUISwitchFolderEvent</code>
         *
         * @param folder The folder to be switched to
         */
        public GUISwitchFolderEvent(ItemFolder folder)
        {
            this.folder = folder;
        }

        /**
         * Switch the explorer to view the {@link GUISwitchFolderEvent#folder}
         *
         * @param event The event of the click
         * @param gui   The GUI that the event took place in
         */
        @Override
        public void execute(InventoryClickEvent event, GUIContainer gui)
        {
            Player player = (Player) event.getWhoClicked();
            GUIItemExplorerModule module = gui.getModule(GUIItemExplorerModule.class);
            ItemFolder oldFolder = module.getFolder();
            module.setFolder(folder);
            module.getHistory().pushBack(oldFolder);
            module.getHistory().clearForward();
            module.setListLoc(1);
            gui.setInventoryName(folder.getName());
            gui.onClose(player);
            gui.open(player);
        }
    }

    /**
     * Event to navigate back a folder in a {@link GUIItemExplorerModule} GUI
     *
     * @author Mikedeejay2
     */
    public static class GUINavFolderBackEvent implements GUIEvent
    {
        /**
         * Navigate back a folder
         *
         * @param event The event of the click
         * @param gui   The GUI that the event took place in
         */
        @Override
        public void execute(InventoryClickEvent event, GUIContainer gui)
        {
            Player player = (Player) event.getWhoClicked();
            GUIItemExplorerModule module = gui.getModule(GUIItemExplorerModule.class);
            ItemFolder oldFolder = module.getFolder();
            ItemFolder folder = module.getHistory().popBack();
            module.setFolder(folder);
            module.getHistory().pushForward(oldFolder);
            module.setListLoc(1);
            gui.setInventoryName(folder.getName());
            gui.onClose(player);
            gui.open(player);
        }
    }

    /**
     * Event to navigate forward a folder in a {@link GUIItemExplorerModule} GUI
     *
     * @author Mikedeejay2
     */
    public static class GUINavFolderForwardEvent implements GUIEvent
    {
        /**
         * Navigate forward a folder
         *
         * @param event The event of the click
         * @param gui   The GUI that the event took place in
         */
        @Override
        public void execute(InventoryClickEvent event, GUIContainer gui)
        {
            Player player = (Player) event.getWhoClicked();
            GUIItemExplorerModule module = gui.getModule(GUIItemExplorerModule.class);
            ItemFolder oldFolder = module.getFolder();
            ItemFolder newFolder = module.getHistory().popForward();
            module.setFolder(newFolder);
            module.getHistory().pushBack(oldFolder);
            module.setListLoc(1);
            gui.setInventoryName(newFolder.getName());
            gui.onClose(player);
            gui.open(player);
        }
    }
}
