package com.mikedeejay2.mikedeejay2lib.gui.modules;

import com.mikedeejay2.mikedeejay2lib.PluginBase;
import com.mikedeejay2.mikedeejay2lib.gui.GUIContainer;
import com.mikedeejay2.mikedeejay2lib.gui.item.GUIItem;
import com.mikedeejay2.mikedeejay2lib.util.Base64Heads;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class GUIBorderModule extends GUIModule
{
    private GUIItem borderItem;

    public GUIBorderModule(PluginBase plugin, GUIContainer gui)
    {
        super(plugin, gui);
        this.borderItem = new GUIItem(plugin.itemCreator().createHeadItem(Base64Heads.HEAD_WHITE, 1, " "));
    }

    public GUIItem getBorderItem()
    {
        return borderItem;
    }

    public void setBorderItem(GUIItem borderItem)
    {
        this.borderItem = borderItem;
    }

    public void setBorderItem(ItemStack borderItem)
    {
        this.borderItem = new GUIItem(borderItem);
    }

    @Override
    public void onUpdate(Player player)
    {
        for(int i = 0; i < GUIContainer.COLUMN_SIZE; i++)
        {
            gui.setItem(1, i, borderItem);
            gui.setItem(gui.getRows(), i, borderItem);
        }
    }

    @Override
    public void onOpen(Player player)
    {

    }

    @Override
    public void onClicked(Player player, int row, int col, GUIItem clicked)
    {

    }
}
