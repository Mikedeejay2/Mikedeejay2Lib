package com.mikedeejay2.mikedeejay2lib.gui.modules.fileexplorer;

import com.mikedeejay2.mikedeejay2lib.BukkitPlugin;
import com.mikedeejay2.mikedeejay2lib.gui.GUIContainer;
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
     * Overridden <tt>onOpenHead</tt> that fills the file explorer with items
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
        fillList(player, gui);
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
        history.pushBack(file);
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
            module.setFile(file);
            gui.setInventoryName(file.getName());
            gui.onClose(player);
            gui.open(player);
        }
    }
}
