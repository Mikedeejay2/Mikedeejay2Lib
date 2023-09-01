package com.mikedeejay2.mikedeejay2lib.gui.modules.explorer;

import com.mikedeejay2.mikedeejay2lib.BukkitPlugin;
import com.mikedeejay2.mikedeejay2lib.gui.GUIContainer;
import com.mikedeejay2.mikedeejay2lib.gui.GUILayer;
import com.mikedeejay2.mikedeejay2lib.gui.event.GUIClickEvent;
import com.mikedeejay2.mikedeejay2lib.gui.event.GUIEvent;
import com.mikedeejay2.mikedeejay2lib.gui.event.item.GUICreativeMoveEvent;
import com.mikedeejay2.mikedeejay2lib.gui.item.GUIItem;
import com.mikedeejay2.mikedeejay2lib.gui.modules.list.GUIListModule;
import com.mikedeejay2.mikedeejay2lib.item.ItemBuilder;
import com.mikedeejay2.mikedeejay2lib.text.Text;
import com.mikedeejay2.mikedeejay2lib.util.head.Base64Head;
import com.mikedeejay2.mikedeejay2lib.util.search.SearchUtil;
import com.mikedeejay2.mikedeejay2lib.util.structure.HistoryHolder;
import com.mikedeejay2.mikedeejay2lib.util.structure.tuple.ImmutablePair;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * An abstract explorer for viewing and interacting with folder data types.
 *
 * @author Mikedeejay2
 */
public abstract class GUIExplorerBaseModule<F> extends GUIListModule {
    /**
     * The folder currently being viewed
     */
    protected F folder;

    /**
     * The contents of the current folder as {@link GUIItem GUIItems}
     */
    protected final List<GUIItem> folderItems;

    /**
     * The item view history
     */
    protected HistoryHolder<F> history;

    /**
     * The valid back navigation item
     */
    protected GUIItem backItemValid;

    /**
     * The valid forward navigation item
     */
    protected GUIItem forwardItemValid;

    /**
     * If search mode is enabled, allow deep search through all folders. This effects performance on large folders
     */
    protected boolean deepSearch;

    /**
     * If deep search is enabled, depth that items are searched through. -1 is infinite.
     */
    protected int searchDepth;

    /**
     * Whether the explorer acts like a creative menu
     */
    protected boolean creativeActions;

    /**
     * Construct a new <code>GUIExplorerBaseModule</code>
     *
     * @param plugin    The {@link BukkitPlugin} instance
     * @param folder    The root folder of the explorer
     * @param viewMode  The viewing mode of the list
     * @param topRow    The top row location of the explorer
     * @param bottomRow The bottom row location of the explorer
     * @param leftCol   The left column location of the explorer
     * @param rightCol  The right column location of the explorer
     * @param layerName The name of the layer that the explorer will exist on
     */
    public GUIExplorerBaseModule(
        BukkitPlugin plugin,
        F folder,
        ListViewMode viewMode,
        int topRow,
        int bottomRow,
        int leftCol,
        int rightCol,
        String layerName) {
        super(plugin, viewMode, topRow, bottomRow, leftCol, rightCol, layerName);
        this.history = new HistoryHolder<>();
        this.addBack(1, 8);
        this.addForward(1, 9);

        this.folderItems = new ArrayList<>();
        this.folder = folder;

        this.backItemValid = new GUIItem(
            ItemBuilder.of(Base64Head.ARROW_LEFT_WHITE.get())
                .setName("&f" + Text.translatable("gui.modules.navigator.backward").get())
                .get())
            .addEvent(new GUINavFolderBackEvent<>(this));
        this.forwardItemValid = new GUIItem(
            ItemBuilder.of(Base64Head.ARROW_RIGHT_WHITE.get())
                .setName("&f" + Text.translatable("gui.modules.navigator.forward").get())
                .get())
            .addEvent(new GUINavFolderForwardEvent<>(this));
        this.deepSearch = false;
        this.searchDepth = 10;
        this.creativeActions = false;
    }

    /**
     * Construct a new <code>GUIExplorerBaseModule</code>
     *
     * @param plugin    The {@link BukkitPlugin} instance
     * @param folder    The root folder of the explorer
     * @param viewMode  The viewing mode of the list
     * @param topRow    The top row location of the explorer
     * @param bottomRow The bottom row location of the explorer
     * @param leftCol   The left column location of the explorer
     * @param rightCol  The right column location of the explorer
     */
    public GUIExplorerBaseModule(
        BukkitPlugin plugin,
        F folder,
        ListViewMode viewMode,
        int topRow,
        int bottomRow,
        int leftCol,
        int rightCol) {
        this(plugin, folder, viewMode, topRow, bottomRow, leftCol, rightCol, "explorer");
    }

    /**
     * Construct a new <code>GUIExplorerBaseModule</code>
     *
     * @param plugin    The {@link BukkitPlugin} instance
     * @param folder    The root folder of the explorer
     * @param topRow    The top row location of the explorer
     * @param bottomRow The bottom row location of the explorer
     * @param leftCol   The left column location of the explorer
     * @param rightCol  The right column location of the explorer
     */
    public GUIExplorerBaseModule(
        BukkitPlugin plugin,
        F folder,
        int topRow,
        int bottomRow,
        int leftCol,
        int rightCol) {
        this(plugin, folder, ListViewMode.SCROLL, topRow, bottomRow, leftCol, rightCol);
    }

    /**
     * Get the name of a folder
     *
     * @param folder The folder to get the name of
     * @return The name of the folder
     */
    protected abstract Text getFolderName(F folder);

    /**
     * Get the folders contained within a folder
     *
     * @param folder The folder to get the contained folders from
     * @return The contained folders
     */
    protected abstract List<? extends F> getContainedFolders(F folder);

    /**
     * Get the items contained in a folder
     *
     * @param folder The folder to get the items from
     * @return The contained items
     */
    protected abstract List<GUIItem> getFolderItems(F folder);

    /**
     * Generate a folder item from a folder
     *
     * @param folder The folder to get the item from
     * @return The new {@link GUIItem}
     */
    @NotNull
    protected abstract GUIItem getFolderItem(F folder);

    /**
     * Overridden <code>onOpenHead</code> that fills the explorer with items
     *
     * @param player The player that is viewing the GUI
     * @param gui    The GUI
     */
    @Override
    public void onOpenHead(Player player, GUIContainer gui) {
        super.onOpenHead(player, gui);
        setFolder(this.folder);
        gui.setInventoryName(getFolderName(folder));
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
    public void onUpdateHead(Player player, GUIContainer gui) {
        super.onUpdateHead(player, gui);

        GUILayer layer = gui.getLayer(layerName);
        setHistoryButtons(layer);
    }

    /**
     * Localize the GUI history buttons
     *
     * @param player The reference player viewing the GUI
     */
    private void localize(Player player) {
        this.backItemValid.setName("&f" + Text.translatable("gui.modules.navigator.backward").get(player))
            .setAmount(Math.min(Math.max(1, history.backSize()), 64));
        this.forwardItemValid.setName("&f" + Text.translatable("gui.modules.navigator.forward").get(player))
            .setAmount(Math.min(Math.max(1, history.forwardSize()), 64));
    }

    /**
     * Set the folder history buttons
     *
     * @param layer The <code>GUILayer</code> to set the items on
     */
    private void setHistoryButtons(GUILayer layer) {
        layer.setItem(1, 1, history.hasBack() ? backItemValid : null);
        layer.setItem(1, 2, history.hasForward() ? forwardItemValid : null);
    }

    /**
     * Fill the decoration items for the GUI
     *
     * @param baseLayer The base layer of the GUI, the layer that the background decor will be set on
     */
    private void fillDecor(GUILayer baseLayer) {
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
    protected void fillList(Player player, GUIContainer gui) {
        this.resetList();
        List<? extends F> folders = getContainedFolders(folder);
        if(folders == null) {
            plugin.sendMessage(player, "&c" + Text.translatable("command.errors.general").get(player));
            return;
        }

        for(F folder : folders) {
            GUIItem item = getFolderItem(folder);
            this.addItem(item);
        }
        for(GUIItem item : folderItems) {

            this.addItem(item);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void searchThroughList(Player player) {
        if(!deepSearch) {
            super.searchThroughList(player);
            return;
        }
        searchMode = true;
        searchList.clear();
        List<GUIItem> folders = new ArrayList<>();
        List<GUIItem> guiItems = new ArrayList<>();
        recurSearchFolders(folder, folders, player, 0);
        recurSearchItems(folder, guiItems, 0);
        folders.forEach(item -> searchList.add(new ImmutablePair<>(item, -1)));
        guiItems.forEach(item -> searchList.add(new ImmutablePair<>(item, -1)));
    }

    /**
     * Recursive deep search for folders in explorer folders.
     * Only called if {@link GUIExplorerBaseModule#deepSearch} is true.
     *
     * @param folder The current folder to search
     * @param list The list of generated items
     * @param depth The current depth of the search from the starting folder
     */
    private void recurSearchFolders(F folder, List<GUIItem> list, Player player, int depth) {
        List<? extends F> folders = getContainedFolders(folder);
        for(F curFolder : folders) {
            if(!getFolderName(curFolder).get(player).toLowerCase().contains(searchTerm.toLowerCase())) continue;
            list.add(getFolderItem(curFolder));
        }
        if(searchDepth == -1 || depth < searchDepth) {
            for(F curFolder : folders) {
                recurSearchFolders(curFolder, list, player, ++depth);
            }
        }
    }

    /**
     * Recursive deep search for items in explorer folders.
     * Only called if {@link GUIExplorerBaseModule#deepSearch} is true.
     *
     * @param folder The current folder to search
     * @param list The list of generated items
     * @param depth The current depth of the search from the starting folder
     */
    private void recurSearchItems(F folder, List<GUIItem> list, int depth) {
        for(GUIItem item : folderItems) {
            if(!SearchUtil.searchMetaFuzzy(item.getMeta(), searchTerm)) continue;
            list.add(item);
        }
        if(searchDepth == -1 || depth < searchDepth) {
            for(F curFolder : getContainedFolders(folder)) {
                recurSearchItems(curFolder, list, ++depth);
            }
        }
    }

    /**
     * Get the current folder of this GUI
     *
     * @return The folder
     */
    public F getFolder() {
        return folder;
    }

    /**
     * Set the current folder of this GUI
     *
     * @param folder The new folder to use
     */
    public void setFolder(F folder) {
        this.folder = folder;
        this.folderItems.clear();
        final List<GUIItem> items = getFolderItems(folder);
        if(creativeActions) {
            for(GUIItem item : items) {
                if(item.containsEvent(GUICreativeMoveEvent.class)) continue;
                item.addEvent(new GUICreativeMoveEvent(item.get()));
            }
        }
        this.folderItems.addAll(items);
    }

    /**
     * Get the {@link HistoryHolder} of the folder history for this GUI
     *
     * @return The folder history
     */
    public HistoryHolder<F> getHistory() {
        return history;
    }

    /**
     * Get whether deep search is enabled
     *
     * @return Whether deep search is enabled
     */
    public boolean isDeepSearch() {
        return deepSearch;
    }

    /**
     * Set whether to allow deep search. If search mode is enabled, allow deep search through all folders.
     * This effects performance on large folders
     *
     * @param deepSearch The new deep search value
     */
    public void setDeepSearch(boolean deepSearch) {
        this.deepSearch = deepSearch;
    }

    /**
     * Get the deep search depth
     *
     * @return The deep search depth
     */
    public int getSearchDepth() {
        return searchDepth;
    }

    /**
     * Set the new deep search depth. -1 is infinite.
     *
     * @param searchDepth The new deep search depth
     */
    public void setSearchDepth(int searchDepth) {
        this.searchDepth = searchDepth;
    }

    /**
     * Get whether the explorer acts like a creative menu
     *
     * @return Whether the explorer acts like a creative menu
     */
    public boolean isCreativeActions() {
        return creativeActions;
    }

    /**
     * Set whether the explorer acts like a creative menu
     *
     * @param creativeActions The new creative actions state
     */
    public void setCreativeActions(boolean creativeActions) {
        this.creativeActions = creativeActions;
    }

    /**
     * Event to switch a folder in a {@link GUIExplorerBaseModule} GUI
     *
     * @author Mikedeejay2
     */
    public static class GUISwitchFolderEvent<F> implements GUIEvent {
        /**
         * The folder to be switched to
         */
        private final F folder;

        /**
         * The parent module
         */
        private final GUIExplorerBaseModule<F> module;

        /**
         * Construct a new <code>GUISwitchFolderEvent</code>
         *
         * @param module The parent module
         * @param folder The folder to be switched to
         */
        public GUISwitchFolderEvent(GUIExplorerBaseModule<F> module, F folder) {
            this.module = module;
            this.folder = folder;
        }

        /**
         * Switch the explorer to view the {@link GUISwitchFolderEvent#folder}
         *
         * @param info {@link GUIClickEvent} of the event
         */
        @Override
        public void execute(GUIClickEvent info) {
            Player player = info.getPlayer();
            GUIContainer gui = info.getGUI();
            F oldFolder = module.getFolder();
            module.setFolder(folder);
            module.getHistory().pushBack(oldFolder);
            module.getHistory().clearForward();
            module.setListLoc(1);
            gui.setInventoryName(module.getFolderName(folder));
            gui.open(player);
        }
    }

    /**
     * Event to navigate back a folder in a {@link GUIExplorerBaseModule} GUI
     *
     * @author Mikedeejay2
     */
    public static class GUINavFolderBackEvent<F> implements GUIEvent {
        /**
         * The parent module
         */
        private final GUIExplorerBaseModule<F> module;

        /**
         * Construct a new <code>GUINavFolderBackEvent</code>
         *
         * @param module The parent module
         */
        public GUINavFolderBackEvent(GUIExplorerBaseModule<F> module) {
            this.module = module;
        }

        /**
         * Navigate back a folder
         *
         * @param info {@link GUIClickEvent} of the event
         */
        @Override
        public void execute(GUIClickEvent info) {
            Player player = info.getPlayer();
            GUIContainer gui = info.getGUI();
            F oldFolder = module.getFolder();
            F folder = module.getHistory().popBack();
            module.setFolder(folder);
            module.getHistory().pushForward(oldFolder);
            module.setListLoc(1);
            gui.setInventoryName(module.getFolderName(folder));
            gui.open(player);
        }
    }

    /**
     * Event to navigate forward a folder in a {@link GUIExplorerBaseModule} GUI
     *
     * @author Mikedeejay2
     */
    public static class GUINavFolderForwardEvent<F> implements GUIEvent {
        /**
         * The parent module
         */
        private final GUIExplorerBaseModule<F> module;

        /**
         * Construct a new <code>GUINavFolderForwardEvent</code>
         *
         * @param module The parent module
         */
        public GUINavFolderForwardEvent(GUIExplorerBaseModule<F> module) {
            this.module = module;
        }

        /**
         * Navigate forward a folder
         *
         * @param info {@link GUIClickEvent} of the event
         */
        @Override
        public void execute(GUIClickEvent info) {
            Player player = info.getPlayer();
            GUIContainer gui = info.getGUI();
            F oldFolder = module.getFolder();
            F newFolder = module.getHistory().popForward();
            module.setFolder(newFolder);
            module.getHistory().pushBack(oldFolder);
            module.setListLoc(1);
            gui.setInventoryName(module.getFolderName(newFolder));
            gui.open(player);
        }
    }
}
