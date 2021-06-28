package com.mikedeejay2.mikedeejay2lib.gui.modules.fileexplorer;

import com.mikedeejay2.mikedeejay2lib.BukkitPlugin;
import com.mikedeejay2.mikedeejay2lib.gui.GUIContainer;
import com.mikedeejay2.mikedeejay2lib.gui.GUILayer;
import com.mikedeejay2.mikedeejay2lib.gui.event.GUIEvent;
import com.mikedeejay2.mikedeejay2lib.gui.item.GUIItem;
import com.mikedeejay2.mikedeejay2lib.gui.modules.list.GUIListModule;
import com.mikedeejay2.mikedeejay2lib.item.ItemBuilder;
import com.mikedeejay2.mikedeejay2lib.util.file.FileUtil;
import com.mikedeejay2.mikedeejay2lib.util.head.Base64Head;
import com.mikedeejay2.mikedeejay2lib.util.head.HeadUtil;
import com.mikedeejay2.mikedeejay2lib.util.structure.HistoryHolder;
import org.apache.commons.lang.Validate;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.io.File;
import java.util.LinkedList;
import java.util.Queue;

/**
 * A file explorer to browse real files.
 * File icons are based off of their respective extension name. See {@link HeadUtil#getHeadFromFileExtension(String)}
 * for all supported file extensions.
 *
 * @author Mikedeejay2
 */
public class GUIFileExplorerModule extends GUIListModule
{
    private static final String separator = File.separatorChar == '\\' ? "\\\\" : String.valueOf(File.separatorChar);

    // The file of this GUI
    protected File file;

    protected HistoryHolder<File> history;

    protected GUIItem backItemValid;
    protected GUIItem forwardItemValid;

    protected boolean allowSubFolders;
    protected boolean allowUpwardTraversal;

    public GUIFileExplorerModule(BukkitPlugin plugin, File file, ListViewMode viewMode, int topRow, int bottomRow, int leftCol, int rightCol, String layerName)
    {
        super(plugin, viewMode, topRow, bottomRow, leftCol, rightCol, layerName);
        Validate.notNull(file, "Folder is null");
        Validate.isTrue(file.isDirectory(), "Provided file is not a directory");
        this.history = new HistoryHolder<>();
        this.file = file;
        this.allowSubFolders = true;
        this.allowUpwardTraversal = false;
        this.addBack(1, 8);
        this.addForward(1, 9);

        this.backItemValid = new GUIItem(
            ItemBuilder.of(Base64Head.ARROW_LEFT_WHITE.get())
                .setName("&f" + plugin.getLibLangManager().getText("gui.modules.navigator.backward"))
                .get())
            .addEvent(new GUINavFileBackEvent());
        this.forwardItemValid = new GUIItem(
            ItemBuilder.of(Base64Head.ARROW_RIGHT_WHITE.get())
                .setName("&f" + plugin.getLibLangManager().getText("gui.modules.navigator.backward"))
                .get())
            .addEvent(new GUINavFileForwardEvent());
    }

    public GUIFileExplorerModule(BukkitPlugin plugin, File file, ListViewMode viewMode, int topRow, int bottomRow, int leftCol, int rightCol)
    {
        this(plugin, file, viewMode, topRow, bottomRow, leftCol, rightCol, "explorer");
    }

    public GUIFileExplorerModule(BukkitPlugin plugin, File file, int topRow, int bottomRow, int leftCol, int rightCol)
    {
        this(plugin, file, ListViewMode.SCROLL, topRow, bottomRow, leftCol, rightCol);
    }

    /**
     * Overridden <code>onOpenHead</code> that fills the file explorer with items
     *
     * @param player The player that is viewing the GUI
     * @param gui    The GUI
     */
    @Override
    public void onOpenHead(Player player, GUIContainer gui)
    {
        super.onOpenHead(player, gui);
        gui.setInventoryName(file.getName());
        resetList();
        localize(player);

        fillList(player, gui);
        if(allowUpwardTraversal)
        {
            fillUpwardTraversal();
        }
        GUILayer baseLayer = gui.getLayer(0);
        fillDecor(baseLayer);
    }

    @Override
    public void onUpdateHead(Player player, GUIContainer gui)
    {
        super.onUpdateHead(player, gui);

        GUILayer layer = gui.getLayer(layerName);
        setHistoryButtons(layer);
    }

    private void localize(Player player)
    {
        this.backItemValid.setName("&f" + plugin.getLibLangManager().getText(player, "gui.modules.navigator.backward"))
            .setAmount(Math.min(Math.max(1, history.backSize()), 64));
        this.forwardItemValid.setName("&f" + plugin.getLibLangManager().getText(player, "gui.modules.navigator.backward"))
            .setAmount(Math.min(Math.max(1, history.forwardSize()), 64));
    }

    private void setHistoryButtons(GUILayer layer)
    {
        layer.setItem(1, 1, history.hasBack() ? backItemValid : null);
        layer.setItem(1, 2, history.hasForward() ? forwardItemValid : null);
    }

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

    private void fillUpwardTraversal()
    {
        Queue<File> temp = new LinkedList<>();
        File curFile = file.getParentFile();
        while(curFile != null)
        {
            temp.add(curFile);
            curFile = curFile.getParentFile();
        }
        temp.forEach(file -> history.pushBack(file));
    }

    /**
     * Fill the GUI list with all files in the current folder
     *
     * @param player The player that is viewing the GUI
     * @param gui    The GUI
     */
    private void fillList(Player player, GUIContainer gui)
    {
        this.resetList();
        File[] files = file.listFiles();
        if(files == null)
        {
            plugin.sendMessage(player, "&c" + plugin.getLibLangManager().getText(player, "command.errors.general"));
            return;
        }
        Queue<GUIItem> folderQueue = new LinkedList<>();
        Queue<GUIItem> fileQueue = new LinkedList<>();
        for(File curFile : files)
        {
            ItemBuilder fileBuilder = ItemBuilder.of(Base64Head.STACK_OF_PAPER.get())
                    .setName("&f" + curFile.getName());

            String[] lore = curFile.getPath().split(separator);
            String[] newLore = new String[lore.length];
            for(int i = 0; i < lore.length; ++i)
            {
                newLore[i] = "&7" + lore[i];
            }
            fileBuilder.setLore(newLore);

            GUIItem fileItem = new GUIItem(null);
            if(curFile.isDirectory())
            {
                fileBuilder.setHeadBase64(Base64Head.FOLDER.get());
                fileItem.setItem(fileBuilder.get());
                if(allowSubFolders)
                {
                    fileItem.addEvent(new GUISwitchFolderEvent(curFile));
                }
                folderQueue.add(fileItem);
            }
            else
            {
                String extension = FileUtil.getFileExtension(curFile);
                Base64Head head = HeadUtil.getHeadFromFileExtension(extension);
                fileBuilder.setHeadBase64(head.get());
                fileItem.setItem(fileBuilder.get());
                fileQueue.add(fileItem);
            }
        }
        folderQueue.forEach(this::addListItem);
        fileQueue.forEach(this::addListItem);
    }

    /**
     * Get the current folder of this GUI
     *
     * @return The folder
     */
    public File getFile()
    {
        return file;
    }

    /**
     * Set the current folder of this GUI
     *
     * @param file The new folder to use
     */
    public void setFile(File file)
    {
        this.file = file;
    }

    public HistoryHolder<File> getHistory()
    {
        return history;
    }

    public boolean isAllowSubFolders()
    {
        return allowSubFolders;
    }

    public void setAllowSubFolders(boolean allowSubFolders)
    {
        this.allowSubFolders = allowSubFolders;
    }

    public boolean isAllowUpwardTraversal()
    {
        return allowUpwardTraversal;
    }

    public void setAllowUpwardTraversal(boolean allowUpwardTraversal)
    {
        this.allowUpwardTraversal = allowUpwardTraversal;
    }

    /**
     * Event to switch a folder in a {@link GUIFileExplorerModule} GUI
     *
     * @author Mikedeejay2
     */
    public static class GUISwitchFolderEvent implements GUIEvent
    {
        private final File file;

        public GUISwitchFolderEvent(File file)
        {
            this.file = file;
        }

        @Override
        public void execute(InventoryClickEvent event, GUIContainer gui)
        {
            Player player = (Player) event.getWhoClicked();
            GUIFileExplorerModule module = gui.getModule(GUIFileExplorerModule.class);
            File oldFile = module.getFile();
            module.setFile(file);
            module.getHistory().pushBack(oldFile);
            module.getHistory().clearForward();
            module.setListLoc(1);
            gui.setInventoryName(file.getName());
            gui.onClose(player);
            gui.open(player);
        }
    }

    public static class GUINavFileBackEvent implements GUIEvent
    {
        @Override
        public void execute(InventoryClickEvent event, GUIContainer gui)
        {
            Player player = (Player) event.getWhoClicked();
            GUIFileExplorerModule module = gui.getModule(GUIFileExplorerModule.class);
            File oldFile = module.getFile();
            File file = module.getHistory().popBack();
            module.setFile(file);
            module.getHistory().pushForward(oldFile);
            module.setListLoc(1);
            gui.setInventoryName(file.getName());
            gui.onClose(player);
            gui.open(player);
        }
    }

    public static class GUINavFileForwardEvent implements GUIEvent
    {
        @Override
        public void execute(InventoryClickEvent event, GUIContainer gui)
        {
            Player player = (Player) event.getWhoClicked();
            GUIFileExplorerModule module = gui.getModule(GUIFileExplorerModule.class);
            File oldFile = module.getFile();
            File newFile = module.getHistory().popForward();
            module.setFile(newFile);
            module.getHistory().pushBack(oldFile);
            module.setListLoc(1);
            gui.setInventoryName(newFile.getName());
            gui.onClose(player);
            gui.open(player);
        }
    }
}
