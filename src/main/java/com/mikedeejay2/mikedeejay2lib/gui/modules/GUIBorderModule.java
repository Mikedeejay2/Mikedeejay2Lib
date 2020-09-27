package com.mikedeejay2.mikedeejay2lib.gui.modules;

import com.mikedeejay2.mikedeejay2lib.PluginBase;
import com.mikedeejay2.mikedeejay2lib.gui.GUIContainer;
import com.mikedeejay2.mikedeejay2lib.gui.item.GUIItem;
import com.mikedeejay2.mikedeejay2lib.util.Base64Heads;
import com.mikedeejay2.mikedeejay2lib.util.item.ItemCreator;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class GUIBorderModule extends GUIModule
{
    private GUIItem borderItem;

    public GUIBorderModule(PluginBase plugin)
    {
        super(plugin);
        this.borderItem = new GUIItem(ItemCreator.createHeadItem(Base64Heads.HEAD_WHITE, 1, GUIContainer.EMPTY_NAME));
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
    public void onUpdate(Player player, GUIContainer gui)
    {
        for(int i = 1; i <= GUIContainer.COLUMN_SIZE; i++)
        {
            gui.setItem(1, i, borderItem);
            gui.setItem(gui.getRows(), i, borderItem);
        }
    }

    @Override
    public void onOpen(Player player, GUIContainer gui)
    {

    }

    @Override
    public void onClose(Player player, GUIContainer gui)
    {

    }

    @Override
    public void onClicked(Player player, int row, int col, GUIItem clicked, GUIContainer gui)
    {

    }
}
