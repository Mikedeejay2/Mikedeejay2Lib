package com.mikedeejay2.mikedeejay2lib.util.search;

import org.bukkit.inventory.meta.ItemMeta;

/**
 * Class for searching through different things to see if a piece of data exists
 * inside of it.
 *
 * @author Mikedeejay2
 */
public final class SearchUtil
{
    /**
     * Search for a String in a piece of ItemMeta.
     * This method generalizes the search term by removing spaces and
     * changing all the text to lower case on both the ItemMeta and the search term.
     * AKA a "fuzzy" search.
     * <p>
     * Uses <tt>.contains()</tt> to compare strings
     *
     * @param meta       ItemMeta to search through
     * @param searchTerm The search term to use
     * @return If search term was found in the ItemMeta or not
     */
    public static boolean searchMetaFuzzy(ItemMeta meta, String searchTerm)
    {
        String newName        = searchTerm.toLowerCase().replaceAll(" ", "");
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

    /**
     * Search for a String in a piece of ItemMeta.
     * This method searches the ItemMeta without generalizing the Strings.
     * That means that capitalization, spacing, etc counts towards the search.
     * <p>
     * Uses <tt>.contains()</tt> to compare strings
     *
     * @param meta       ItemMeta to search through
     * @param searchTerm The search term to use
     * @return If search term was found in the ItemMeta or not
     */
    public static boolean searchMeta(ItemMeta meta, String searchTerm)
    {
        if(meta.getDisplayName().contains(searchTerm)) return true;

        boolean flag = false;
        if(meta.hasLore())
        {
            for(String lore : meta.getLore())
            {
                if(lore.contains(searchTerm))
                {
                    flag = true;
                    break;
                }
            }
        }

        return flag;
    }

    /**
     * Search for a String in a piece of ItemMeta.
     * This method makes sure that the item's display name or a single lore string
     * matches the search term exactly.
     * <p>
     * Uses <tt>.equals()</tt> to compare strings
     *
     * @param meta       ItemMeta to search through
     * @param searchTerm The search term to use
     * @return If search term was found in the ItemMeta or not
     */
    public static boolean searchMetaExact(ItemMeta meta, String searchTerm)
    {
        if(meta.getDisplayName().equals(searchTerm)) return true;

        boolean flag = false;
        if(meta.hasLore())
        {
            for(String lore : meta.getLore())
            {
                if(lore.equals(searchTerm))
                {
                    flag = true;
                    break;
                }
            }
        }

        return flag;
    }
}
