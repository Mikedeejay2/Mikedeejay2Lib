package com.mikedeejay2.mikedeejay2lib.gui.util;

import static com.mikedeejay2.mikedeejay2lib.gui.GUIContainer.COLUMN_SIZE;

public class GUIMath
{
    public static int getSlotFromRowCol(int row, int col)
    {
        return (row * COLUMN_SIZE) + col;
    }

    public static int getRowFromSlot(int slot)
    {
        return (slot / COLUMN_SIZE) + 1;
    }

    public static int getColFromSlot(int slot)
    {
        return (slot % COLUMN_SIZE) + 1;
    }
}
