package com.mikedeejay2.mikedeejay2lib.util.item;

/**
 * Enum representing several different iteration types for inventories.
 * <p>
 * The following are only to be used with <b>player</b> inventories:
 * <ul>
 *     <li>HOTBAR_ASCENDING</li>
 *     <li>HOTBAR_DESCENDING</li>
 *     <li>ARMOR_FIRST</li>
 *     <li>OFFHAND_FIRST</li>
 *     <li>PLAYER_ASCENDING</li>
 *     <li>PLAYER_DESCENDING</li>
 * </ul>
 *
 * @author Mikedeejay2
 */
public enum InventoryOrder
{
    // Start at index 0 and go up
    ASCENDING(false, 0, -1, true),
    // Start at inventory.length and work down to 0
    DESCENDING(false, -1, 0, false),
    // Iterate slots 0-8
    HOTBAR_ASCENDING(true, 0, 8, true),
    // Iterate slots 8-0
    HOTBAR_DESCENDING(true, 8, 0, false),
    // Iterate slots 36-39
    ARMOR(true, 36, 39, true),
    // Slot 40
    OFFHAND(true, 40, 40, true),
    // Iterate slots 9-35
    PLAYER_ASCENDING(true, 9, 35, true),
    // Iterate slots 35-9
    PLAYER_DESCENDING(true, 35, 9, false)
    ;

    private final boolean playerOnly;
    private final int start;
    private final int end;
    private final boolean increment;

    /**
     * @param playerOnly Whether the ordering is for a player's inventory only
     */
    InventoryOrder(boolean playerOnly, int start, int end, boolean increment)
    {
        this.playerOnly = playerOnly;
        this.start = start;
        this.end = end;
        this.increment = increment;
    }

    public boolean isPlayerOnly()
    {
        return playerOnly;
    }

    public int getStart()
    {
        return start;
    }

    public int getEnd()
    {
        return end;
    }

    public boolean isIncrement()
    {
        return increment;
    }
}
