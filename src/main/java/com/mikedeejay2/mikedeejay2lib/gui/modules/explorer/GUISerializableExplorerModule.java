package com.mikedeejay2.mikedeejay2lib.gui.modules.explorer;

import com.mikedeejay2.mikedeejay2lib.BukkitPlugin;
import com.mikedeejay2.mikedeejay2lib.data.filesystem.SerializableFolder;
import com.mikedeejay2.mikedeejay2lib.gui.GUIContainer;
import com.mikedeejay2.mikedeejay2lib.gui.GUILayer;
import com.mikedeejay2.mikedeejay2lib.gui.event.GUIClickEvent;
import com.mikedeejay2.mikedeejay2lib.gui.event.GUIEvent;
import com.mikedeejay2.mikedeejay2lib.gui.item.GUIItem;
import com.mikedeejay2.mikedeejay2lib.gui.modules.list.GUIListModule;
import com.mikedeejay2.mikedeejay2lib.item.ItemBuilder;
import com.mikedeejay2.mikedeejay2lib.text.Text;
import com.mikedeejay2.mikedeejay2lib.util.head.Base64Head;
import com.mikedeejay2.mikedeejay2lib.util.search.SearchUtil;
import com.mikedeejay2.mikedeejay2lib.util.structure.HistoryHolder;
import com.mikedeejay2.mikedeejay2lib.util.structure.tuple.ImmutablePair;
import org.apache.commons.lang3.Validate;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * An item explorer for viewing and interacting with organized items.
 *
 * @author Mikedeejay2
 */
public class GUISerializableExplorerModule<T extends ConfigurationSerializable> extends GUIListModule {
    /**
     * The folder currently being viewed
     */
    private SerializableFolder<T> folder;

    /**
     * The contents of the current folder as {@link GUIItem GUIItems}
     */
    private final List<GUIItem> folderItems;

    /**
     * The item view history
     */
    protected HistoryHolder<SerializableFolder<T>> history;

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

    protected Function<T, GUIItem> converter;

    /**
     * Construct a new <code>GUIItemExplorerModule</code>
     *
     * @param plugin    The {@link BukkitPlugin} instance
     * @param folder    The root {@link SerializableFolder} of the explorer
     * @param converter The converter used to convert between the serialized type and a GUIItem
     * @param viewMode  The viewing mode of the list
     * @param topRow    The top row location of the explorer
     * @param bottomRow The bottom row location of the explorer
     * @param leftCol   The left column location of the explorer
     * @param rightCol  The right column location of the explorer
     * @param layerName The name of the layer that the explorer will exist on
     */
    public GUISerializableExplorerModule(
        BukkitPlugin plugin,
        SerializableFolder<T> folder,
        Function<T, GUIItem> converter,
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
        this.setFolder(folder);

        this.converter = converter;

        this.backItemValid = new GUIItem(
            ItemBuilder.of(Base64Head.ARROW_LEFT_WHITE.get())
                .setName("&f" + Text.translatable("gui.modules.navigator.backward").get())
                .get())
            .addEvent(new GUINavFolderBackEvent());
        this.forwardItemValid = new GUIItem(
            ItemBuilder.of(Base64Head.ARROW_RIGHT_WHITE.get())
                .setName("&f" + Text.translatable("gui.modules.navigator.forward").get())
                .get())
            .addEvent(new GUINavFolderForwardEvent());
        this.deepSearch = false;
        this.searchDepth = 10;
    }

    /**
     * Construct a new <code>GUIItemExplorerModule</code>
     *
     * @param plugin    The {@link BukkitPlugin} instance
     * @param folder    The root {@link SerializableFolder} of the explorer
     * @param converter The converter used to convert between the serialized type and a GUIItem
     * @param viewMode  The viewing mode of the list
     * @param topRow    The top row location of the explorer
     * @param bottomRow The bottom row location of the explorer
     * @param leftCol   The left column location of the explorer
     * @param rightCol  The right column location of the explorer
     */
    public GUISerializableExplorerModule(
        BukkitPlugin plugin,
        SerializableFolder<T> folder,
        Function<T, GUIItem> converter,
        ListViewMode viewMode,
        int topRow,
        int bottomRow,
        int leftCol,
        int rightCol) {
        this(plugin, folder, converter, viewMode, topRow, bottomRow, leftCol, rightCol, "explorer");
    }

    /**
     * Construct a new <code>GUIItemExplorerModule</code>
     *
     * @param plugin    The {@link BukkitPlugin} instance
     * @param folder    The root {@link SerializableFolder} of the explorer
     * @param converter The converter used to convert between the serialized type and a GUIItem
     * @param topRow    The top row location of the explorer
     * @param bottomRow The bottom row location of the explorer
     * @param leftCol   The left column location of the explorer
     * @param rightCol  The right column location of the explorer
     */
    public GUISerializableExplorerModule(
        BukkitPlugin plugin,
        SerializableFolder<T> folder,
        Function<T, GUIItem> converter,
        int topRow,
        int bottomRow,
        int leftCol,
        int rightCol) {
        this(plugin, folder, converter, ListViewMode.SCROLL, topRow, bottomRow, leftCol, rightCol);
    }

    /**
     * Overridden <code>onOpenHead</code> that fills the explorer with items
     *
     * @param player The player that is viewing the GUI
     * @param gui    The GUI
     */
    @Override
    public void onOpenHead(Player player, GUIContainer gui) {
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
    private void fillList(Player player, GUIContainer gui) {
        this.resetList();
        List<? extends SerializableFolder<T>> folders = folder.getFolders();

        for(SerializableFolder<T> folder : folders) {
            GUIItem item = genFolderItem(folder);
            this.addItem(item);
        }
        for(GUIItem item : folderItems) {
            this.addItem(item);
        }
    }

    /**
     * Generate a folder item from an {@link SerializableFolder}
     *
     * @param folder The folder to get the item from
     * @return The new {@link GUIItem}
     */
    @NotNull
    private GUIItem genFolderItem(SerializableFolder<T> folder) {
        ItemStack item = ItemBuilder.of(Base64Head.FOLDER.get())
            .setName(folder.getName()).get();
        Validate.notNull(item, "Folder item can not be null");
        GUIItem guiItem = new GUIItem(item);
        guiItem.setName(folder.getName());
        guiItem.addEvent(new GUISwitchFolderEvent(folder));
        return guiItem;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void searchThroughList() {
        if(!deepSearch) {
            super.searchThroughList();
            return;
        }
        searchMode = true;
        searchList.clear();
        List<GUIItem> folders = new ArrayList<>();
        List<GUIItem> guiItems = new ArrayList<>();
        recurSearchFolders(folder, folders, 0);
        recurSearchItems(folder, guiItems, 0);
        folders.forEach(item -> searchList.add(new ImmutablePair<>(item, -1)));
        guiItems.forEach(item -> searchList.add(new ImmutablePair<>(item, -1)));
    }

    /**
     * Recursive deep search for folders in explorer folders.
     * Only called if {@link GUISerializableExplorerModule#deepSearch} is true.
     *
     * @param folder The current folder to search
     * @param list The list of generated items
     * @param depth The current depth of the search from the starting folder
     */
    private void recurSearchFolders(SerializableFolder<T> folder, List<GUIItem> list, int depth) {
        List<? extends SerializableFolder<T>> folders = folder.getFolders();
        for(SerializableFolder<T> curFolder : folders) {
            if(!curFolder.getName().toLowerCase().contains(searchTerm.toLowerCase())) continue;
            list.add(genFolderItem(curFolder));
        }
        if(searchDepth == -1 || depth < searchDepth) {
            for(SerializableFolder<T> curFolder : folders) {
                recurSearchFolders(curFolder, list, ++depth);
            }
        }
    }

    /**
     * Recursive deep search for items in explorer folders.
     * Only called if {@link GUISerializableExplorerModule#deepSearch} is true.
     *
     * @param folder The current folder to search
     * @param list The list of generated items
     * @param depth The current depth of the search from the starting folder
     */
    private void recurSearchItems(SerializableFolder<T> folder, List<GUIItem> list, int depth) {
        for(GUIItem item : folderItems) {
            if(!SearchUtil.searchMetaFuzzy(item.getMeta(), searchTerm)) continue;
            list.add(item);
        }
        if(searchDepth == -1 || depth < searchDepth) {
            for(SerializableFolder<T> curFolder : folder.getFolders()) {
                recurSearchItems(curFolder, list, ++depth);
            }
        }
    }

    /**
     * Get the current folder of this GUI
     *
     * @return The folder
     */
    public SerializableFolder<T> getFolder() {
        return folder;
    }

    /**
     * Set the current folder of this GUI
     *
     * @param folder The new folder to use
     */
    public void setFolder(SerializableFolder<T> folder) {
        this.folder = folder;
        this.folderItems.clear();
        for(T obj : folder.getItems()) {
            GUIItem item = converter.apply(obj);
            this.folderItems.add(item);
        }
    }

    /**
     * Get the {@link HistoryHolder} of the folder history for this GUI
     *
     * @return The folder history
     */
    public HistoryHolder<SerializableFolder<T>> getHistory() {
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
     * Event to switch a folder in a {@link GUISerializableExplorerModule} GUI
     *
     * @author Mikedeejay2
     */
    public static class GUISwitchFolderEvent<T extends ConfigurationSerializable> implements GUIEvent {
        /**
         * The folder to be switched to
         */
        private final SerializableFolder<T> folder;

        /**
         * Construct a new <code>GUISwitchFolderEvent</code>
         *
         * @param folder The folder to be switched to
         */
        public GUISwitchFolderEvent(SerializableFolder<T> folder) {
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
            GUISerializableExplorerModule<T> module = gui.getModule(GUISerializableExplorerModule.class);
            SerializableFolder<T> oldFolder = module.getFolder();
            module.setFolder(folder);
            module.getHistory().pushBack(oldFolder);
            module.getHistory().clearForward();
            module.setListLoc(1);
            gui.setInventoryName(folder.getName());
            gui.open(player);
        }
    }

    /**
     * Event to navigate back a folder in a {@link GUISerializableExplorerModule} GUI
     *
     * @author Mikedeejay2
     */
    public static class GUINavFolderBackEvent<T extends ConfigurationSerializable> implements GUIEvent {
        /**
         * Navigate back a folder
         *
         * @param info {@link GUIClickEvent} of the event
         */
        @Override
        public void execute(GUIClickEvent info) {
            Player player = info.getPlayer();
            GUIContainer gui = info.getGUI();
            GUISerializableExplorerModule<T> module = gui.getModule(GUISerializableExplorerModule.class);
            SerializableFolder<T> oldFolder = module.getFolder();
            SerializableFolder<T> folder = module.getHistory().popBack();
            module.setFolder(folder);
            module.getHistory().pushForward(oldFolder);
            module.setListLoc(1);
            gui.setInventoryName(folder.getName());
            gui.open(player);
        }
    }

    /**
     * Event to navigate forward a folder in a {@link GUISerializableExplorerModule} GUI
     *
     * @author Mikedeejay2
     */
    public static class GUINavFolderForwardEvent<T extends ConfigurationSerializable> implements GUIEvent {
        /**
         * Navigate forward a folder
         *
         * @param info {@link GUIClickEvent} of the event
         */
        @Override
        public void execute(GUIClickEvent info) {
            Player player = info.getPlayer();
            GUIContainer gui = info.getGUI();
            GUISerializableExplorerModule<T> module = gui.getModule(GUISerializableExplorerModule.class);
            SerializableFolder<T> oldFolder = module.getFolder();
            SerializableFolder<T> newFolder = module.getHistory().popForward();
            module.setFolder(newFolder);
            module.getHistory().pushBack(oldFolder);
            module.setListLoc(1);
            gui.setInventoryName(newFolder.getName());
            gui.open(player);
        }
    }
}
