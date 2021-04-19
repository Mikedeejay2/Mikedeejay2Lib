package com.mikedeejay2.mikedeejay2lib.gui.modules.fileexplorer;

import com.mikedeejay2.mikedeejay2lib.gui.GUIContainer;
import com.mikedeejay2.mikedeejay2lib.gui.modules.GUIModule;
import org.bukkit.entity.Player;

import java.io.File;

public class GUIFileModule implements GUIModule
{
    private File file;

    public GUIFileModule(File file)
    {
        this.file = file;
    }

    @Override
    public void onOpenHead(Player player, GUIContainer gui)
    {

    }
}
