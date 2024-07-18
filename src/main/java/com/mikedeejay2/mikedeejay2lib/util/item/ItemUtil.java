package com.mikedeejay2.mikedeejay2lib.util.item;

import com.mikedeejay2.mikedeejay2lib.util.version.MinecraftVersion;
import org.bukkit.inventory.meta.ItemMeta;

public final class ItemUtil {
    public static String getName(ItemMeta meta) {
        if(MinecraftVersion.check(">=1.20.6")) {
            return meta.getItemName();
        }
        return meta.getDisplayName();
    }

    public static void setName(ItemMeta meta, String name) {
        if(MinecraftVersion.check(">=1.20.6")) {
            meta.setItemName(name);
            return;
        }
        meta.setDisplayName(name);
    }

    public static boolean hasName(ItemMeta meta) {
        if(MinecraftVersion.check(">=1.20.6")) {
            return meta.hasItemName();
        }
        return meta.hasDisplayName();
    }
}
