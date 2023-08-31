package com.mikedeejay2.mikedeejay2lib.gui.modules.explorer;

import com.mikedeejay2.mikedeejay2lib.BukkitPlugin;
import com.mikedeejay2.mikedeejay2lib.gui.GUIContainer;
import com.mikedeejay2.mikedeejay2lib.gui.item.GUIItem;
import com.mikedeejay2.mikedeejay2lib.item.ItemBuilder;
import com.mikedeejay2.mikedeejay2lib.text.Text;
import com.mikedeejay2.mikedeejay2lib.util.file.FileUtil;
import com.mikedeejay2.mikedeejay2lib.util.head.Base64Head;
import com.mikedeejay2.mikedeejay2lib.util.head.HeadUtil;
import org.apache.commons.lang3.Validate;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.*;

/**
 * A file explorer to browse real files.
 * File icons are based off of their respective extension name. See {@link HeadUtil#getHeadFromFileExtension(String)}
 * for all supported file extensions.
 * <p>
 * This class is mostly a test and nowhere near a complete file explorer.
 *
 * @author Mikedeejay2
 */
public class GUIFileExplorerModule extends GUIExplorerBaseModule<File> {
    /**
     * The file separator character, different for different operating systems
     */
    private static final String separator = File.separatorChar == '\\' ? "\\\\" : String.valueOf(File.separatorChar);

    /**
     * Whether to allow subfolders to be explored. This could pose a security risk if this is not necessary.
     */
    protected boolean allowSubFolders;

    /**
     * Allow upward traversal in the GUI. This allows the GUI to navigate <strong>potentially all folders on a
     * drive</strong>. It is heavily recommended that this is not true, as it poses a security risk if it is not
     * absolutely necessary.
     */
    protected boolean allowUpwardTraversal;

    /**
     * Construct a new <code>GUIFileExplorer</code>
     *
     * @param plugin    The {@link BukkitPlugin} instance
     * @param file      The <code>File</code> to be opened in the GUI
     * @param viewMode  The viewing mode of the file list
     * @param topRow    The top row location of the explorer
     * @param bottomRow The bottom row location of the explorer
     * @param leftCol   The left column location of the explorer
     * @param rightCol  The right column location of the explorer
     * @param layerName The name of the layer that the explorer will exist on
     */
    public GUIFileExplorerModule(BukkitPlugin plugin, File file, ListViewMode viewMode, int topRow, int bottomRow, int leftCol, int rightCol, String layerName) {
        super(plugin, file, viewMode, topRow, bottomRow, leftCol, rightCol, layerName);
        Validate.notNull(file, "Folder is null");
        Validate.isTrue(file.isDirectory(), "Provided file is not a directory");
        this.allowSubFolders = true;
        this.allowUpwardTraversal = false;
    }

    /**
     * Construct a new <code>GUIFileExplorer</code>
     *
     * @param plugin    The {@link BukkitPlugin} instance
     * @param file      The <code>File</code> to be opened in the GUI
     * @param viewMode  The viewing mode of the file list
     * @param topRow    The top row location of the explorer
     * @param bottomRow The bottom row location of the explorer
     * @param leftCol   The left column location of the explorer
     * @param rightCol  The right column location of the explorer
     */
    public GUIFileExplorerModule(BukkitPlugin plugin, File file, ListViewMode viewMode, int topRow, int bottomRow, int leftCol, int rightCol) {
        this(plugin, file, viewMode, topRow, bottomRow, leftCol, rightCol, "explorer");
    }

    /**
     * Construct a new <code>GUIFileExplorer</code>
     *
     * @param plugin    The {@link BukkitPlugin} instance
     * @param file      The <code>File</code> to be opened in the GUI
     * @param topRow    The top row location of the explorer
     * @param bottomRow The bottom row location of the explorer
     * @param leftCol   The left column location of the explorer
     * @param rightCol  The right column location of the explorer
     */
    public GUIFileExplorerModule(BukkitPlugin plugin, File file, int topRow, int bottomRow, int leftCol, int rightCol) {
        this(plugin, file, ListViewMode.SCROLL, topRow, bottomRow, leftCol, rightCol);
    }

    @Override
    protected Text getFolderName(File folder) {
        return Text.of(folder.getName());
    }

    @Override
    protected List<File> getContainedFolders(File folder) {
        return Arrays.asList(folder.listFiles((file) -> file.isDirectory()));
    }

    @Override
    protected List<GUIItem> getFolderItems(File folder) {
        File[] files = folder.listFiles();
        if(files == null) return null;
        List<GUIItem> items = new ArrayList<>();
        for(File curFile : files) {
            ItemBuilder fileBuilder = ItemBuilder.of(Base64Head.STACK_OF_PAPER.get())
                .setName("&f" + curFile.getName());
            setPathLore(curFile, fileBuilder);

            GUIItem fileItem = new GUIItem((ItemStack) null);
            String extension = FileUtil.getFileExtension(curFile);
            Base64Head head = HeadUtil.getHeadFromFileExtension(extension);
            fileBuilder.setHeadBase64(head.get());
            fileItem.set(fileBuilder.get());
            items.add(fileItem);
        }
        return items;
    }

    @Override
    protected @NotNull GUIItem getFolderItem(File folder) {
        ItemBuilder fileBuilder = ItemBuilder.of(Base64Head.STACK_OF_PAPER.get())
            .setName("&f" + folder.getName());
        setPathLore(folder, fileBuilder);

        GUIItem fileItem = new GUIItem((ItemStack) null);
        fileBuilder.setHeadBase64(Base64Head.FOLDER.get());
        fileItem.set(fileBuilder.get());
        if(allowSubFolders) {
            fileItem.addEvent(new GUISwitchFolderEvent<>(this, folder));
        }
        return fileItem;
    }

    private static void setPathLore(File folder, ItemBuilder fileBuilder) {
        String[] lore = folder.getPath().split(separator);
        String[] newLore = new String[lore.length];
        for(int i = 0; i < lore.length; ++i) {
            newLore[i] = "&7" + lore[i];
        }
        fileBuilder.setLore(newLore);
    }

    /**
     * Overridden <code>onOpenHead</code> that fills the file explorer with items
     *
     * @param player The player that is viewing the GUI
     * @param gui    The GUI
     */
    @Override
    public void onOpenHead(Player player, GUIContainer gui) {
        super.onOpenHead(player, gui);
        if(allowUpwardTraversal) {
            fillUpwardTraversal();
        }
    }

    /**
     * Fill the upwards traversal of the file history
     */
    private void fillUpwardTraversal() {
        Queue<File> temp = new LinkedList<>();
        File curFile = folder.getParentFile();
        while(curFile != null) {
            temp.add(curFile);
            curFile = curFile.getParentFile();
        }
        temp.forEach(file -> history.pushBack(file));
    }

    /**
     * Get whether this GUI allows subfolders
     *
     * @return Whether this GUI allows subfolders
     */
    public boolean isAllowSubFolders() {
        return allowSubFolders;
    }

    /**
     * Set whether this GUI allows subfolders. This could pose a security risk if this is not necessary.
     *
     * @param allowSubFolders Whether this GUI allows subfolders
     */
    public void setAllowSubFolders(boolean allowSubFolders) {
        this.allowSubFolders = allowSubFolders;
    }

    /**
     * Get whether this GUI allows upward traversal. This allows the GUI to navigate <strong>potentially all folders on
     * a drive</strong>.
     *
     * @return Whether this GUI allows upward traversal
     */
    public boolean isAllowUpwardTraversal() {
        return allowUpwardTraversal;
    }

    /**
     * Set whether this GUI allows upward traversal. This allows the GUI to navigate <strong>potentially all folders on
     * a drive</strong>. It is heavily recommended that this is not true, as it poses a security risk if it is not
     * absolutely necessary.
     *
     * @param allowUpwardTraversal Whether this GUI allows upward traversal
     */
    public void setAllowUpwardTraversal(boolean allowUpwardTraversal) {
        this.allowUpwardTraversal = allowUpwardTraversal;
    }
}
