package com.mikedeejay2.mikedeejay2lib.gui.event.fileexplorer;

import com.mikedeejay2.mikedeejay2lib.gui.GUIContainer;
import com.mikedeejay2.mikedeejay2lib.gui.event.GUIEvent;
import com.mikedeejay2.mikedeejay2lib.gui.modules.fileexplorer.GUIFileExplorerModule;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.io.File;

/**
 * Event to switch a folder in a {@link GUIFileExplorerModule} GUI
 *
 * @author Mikedeejay2
 */
public class GUISwitchFolderEvent implements GUIEvent
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
