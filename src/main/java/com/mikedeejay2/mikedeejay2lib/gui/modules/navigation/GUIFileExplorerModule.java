package com.mikedeejay2.mikedeejay2lib.gui.modules.navigation;

import com.mikedeejay2.mikedeejay2lib.BukkitPlugin;
import com.mikedeejay2.mikedeejay2lib.gui.GUIContainer;
import com.mikedeejay2.mikedeejay2lib.gui.event.navigation.GUIOpenNewEvent;
import com.mikedeejay2.mikedeejay2lib.gui.item.GUIItem;
import com.mikedeejay2.mikedeejay2lib.gui.modules.list.GUIListModule;
import com.mikedeejay2.mikedeejay2lib.gui.modules.list.ListViewMode;
import com.mikedeejay2.mikedeejay2lib.item.ItemBuilder;
import com.mikedeejay2.mikedeejay2lib.util.head.Base64Head;
import org.apache.commons.lang.Validate;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

public class GUIFileExplorerModule extends GUIListModule
{
    private final BiFunction<File, GUIContainer, GUIOpenNewEvent> guiFolderConstructor = (file, gui) -> new GUIOpenNewEvent(plugin, () -> {
        GUIContainer folderGUI = new GUIContainer(plugin, "Folder Navigator", gui.getRows());
        GUIFileExplorerModule explorerModule = new GUIFileExplorerModule(
                plugin, file, viewMode,
                topLeft.getKey(), bottomRight.getKey(),
                topLeft.getValue(), bottomRight.getValue(),
                layerName);
        forwards.forEach(entry -> explorerModule.addForward(entry.getKey(), entry.getValue()));
        backs.forEach(entry -> explorerModule.addBack(entry.getKey(), entry.getValue()));
        folderGUI.addModule(explorerModule);
        if(gui.containsModule(GUINavigatorModule.class))
        {
            folderGUI.addModule(gui.getModule(GUINavigatorModule.class));
        }
        return folderGUI;
    });

    protected File folder;

    public GUIFileExplorerModule(BukkitPlugin plugin, File folder, ListViewMode viewMode, int topRow, int bottomRow, int leftCol, int rightCol, String layerName)
    {
        super(plugin, viewMode, topRow, bottomRow, leftCol, rightCol, layerName);
        Validate.notNull(folder, "Folder is null");
        Validate.isTrue(folder.isDirectory(), "Provided file is not a directory");
        this.folder = folder;
    }

    public GUIFileExplorerModule(BukkitPlugin plugin, File folder, ListViewMode viewMode, int topRow, int bottomRow, int leftCol, int rightCol)
    {
        super(plugin, viewMode, topRow, bottomRow, leftCol, rightCol);
        Validate.notNull(folder, "Folder is null");
        Validate.isTrue(folder.isDirectory(), "Provided file is not a directory");
        this.folder = folder;
    }

    public GUIFileExplorerModule(BukkitPlugin plugin, File folder, int topRow, int bottomRow, int leftCol, int rightCol)
    {
        super(plugin, topRow, bottomRow, leftCol, rightCol);
        Validate.notNull(folder, "Folder is null");
        Validate.isTrue(folder.isDirectory(), "Provided file is not a directory");
        this.folder = folder;
    }

    @Override
    public void onOpenHead(Player player, GUIContainer gui)
    {
        super.onOpenHead(player, gui);
        gui.setInventoryName(folder.getName());
        resetList();
        fillList(player, gui);
    }

    private void fillList(Player player, GUIContainer gui)
    {
        File[] files = folder.listFiles();
        if(files == null)
        {
            plugin.sendMessage(player, "&c" + plugin.getLibLangManager().getText(player, "command.errors.general"));
            return;
        }
        Queue<GUIItem> folderQueue = new LinkedList<>();
        Queue<GUIItem> fileQueue = new LinkedList<>();
        for(File file : files)
        {
            ItemBuilder fileBuilder = ItemBuilder.of(Base64Head.STACK_OF_PAPER.get())
                    .setName("&f" + file.getName())
                    .setLore(Arrays.stream(file.getPath().split("\\\\"))
                                     .flatMap(s -> Arrays.stream(s.split("/")))
                                     .map(s -> "&7" + s)
                                     .collect(Collectors.toList()));

            GUIItem fileItem = new GUIItem(null);
            if(file.isDirectory())
            {
                fileBuilder.setHeadBase64(Base64Head.FOLDER.get());
                fileItem.setItem(fileBuilder.get());
                fileItem.addEvent(guiFolderConstructor.apply(file, gui));
                folderQueue.add(fileItem);
            }
            else
            {
                fileItem.setItem(fileBuilder.get());
                fileQueue.add(fileItem);
            }
        }
        folderQueue.forEach(this::addListItem);
        fileQueue.forEach(this::addListItem);
    }

    public File getFolder()
    {
        return folder;
    }

    public void setFolder(File folder)
    {
        this.folder = folder;
    }
}
