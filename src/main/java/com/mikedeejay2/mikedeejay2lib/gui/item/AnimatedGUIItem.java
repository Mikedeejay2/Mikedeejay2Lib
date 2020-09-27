package com.mikedeejay2.mikedeejay2lib.gui.item;

import com.mikedeejay2.mikedeejay2lib.gui.GUIContainer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class AnimatedGUIItem extends GUIItem
{
    protected HashMap<Long, ItemStack> frames;

    public AnimatedGUIItem(ItemStack item)
    {
        super(item);

        this.frames = new HashMap<>();
    }

    public void onOpen(Player player, GUIContainer container)
    {
        player.sendMessage("Opened");
    }
}
