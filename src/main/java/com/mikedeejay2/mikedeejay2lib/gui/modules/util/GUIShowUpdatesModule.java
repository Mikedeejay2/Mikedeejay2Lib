package com.mikedeejay2.mikedeejay2lib.gui.modules.util;

import com.mikedeejay2.mikedeejay2lib.gui.GUIContainer;
import com.mikedeejay2.mikedeejay2lib.gui.GUILayer;
import com.mikedeejay2.mikedeejay2lib.gui.item.AnimatedGUIItem;
import com.mikedeejay2.mikedeejay2lib.gui.item.GUIItem;
import com.mikedeejay2.mikedeejay2lib.gui.modules.GUIModule;
import com.mikedeejay2.mikedeejay2lib.item.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class GUIShowUpdatesModule implements GUIModule {
    private static final AnimatedGUIItem UPDATE_ITEM =
        new AnimatedGUIItem(ItemBuilder.of(Material.RED_STAINED_GLASS_PANE).setEmptyName(), false, 1)
            .addFrame((ItemStack) null, 1);

    @Override
    public void onUpdateHead(Player player, GUIContainer gui) {
        GUILayer updatesLayer = gui.getLayer("updates");
        for(GUILayer layer : gui.getAllLayers()) {
            int rowOffset = layer.isOverlay() ? gui.getRowOffset() : 0;
            int colOffset = layer.isOverlay() ? gui.getColumnOffset() : 0;
            if(layer == updatesLayer) continue;
            for(int row = 1; row <= layer.getRows(); ++row) {
                for(int col = 1; col <= layer.getCols(); ++col) {
                    GUIItem item = layer.getItemAbsolute(row, col);
                    if(item != null && item.isChanged()) {
                        updatesLayer.setItem(row + rowOffset, col + colOffset, UPDATE_ITEM.clone());
                    }
                }
            }
        }

        for(int row = 1; row <= updatesLayer.getRows(); ++row) {
            for(int col = 1; col <= updatesLayer.getCols(); ++col) {
                GUIItem item = updatesLayer.getItem(row, col);
                if(item != null && item.getType() == null) {
                    updatesLayer.removeItem(row, col);
                }
            }
        }
    }
}
