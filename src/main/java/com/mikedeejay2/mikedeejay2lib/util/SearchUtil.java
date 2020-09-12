package com.mikedeejay2.mikedeejay2lib.util;

import org.bukkit.inventory.meta.ItemMeta;

public class SearchUtil
{
    public static boolean metaContainsString(ItemMeta meta, String name)
    {
        String newName = name.toLowerCase().replaceAll(" ", "");
        String newDisplayName = meta.getDisplayName().toLowerCase().replaceAll(" ", "");
        if(newDisplayName.contains(newName)) return true;

        boolean flag = false;
        if(meta.hasLore())
        {
            for(String lore : meta.getLore())
            {
                String newLore = lore.toLowerCase().replaceAll(" ", "");
                if(newLore.contains(newName))
                {
                    flag = true;
                    break;
                }
            }
        }

        return flag;
    }
}
