package com.mikedeejay2.mikedeejay2lib.gui.animation;

/**
 * Holds the different types of movements that the movement <tt>AnimationFrame</tt> type
 * can run.
 * <p>
 * The types of movement are:
 * <ul>
 *     <li>OVERRIDE_ITEM - Override the item that the current item is being moved to.
 *     If an item is currently in the slot that this item is being moved to, override it.</li>
 *     <li>SWAP_ITEM - Swap the item in the slot that this frame is attempting to move to.
 *     If there is an item currently in the slot that this item is being moved to, swap them.</li>
 *     <li>PUSH_ITEM_DOWN - If there is an item currently in the slot that this item is being moved to,
 *     push that item down in the GUI.</li>
 *     <li>PUSH_ITEM_UP - If there is an item currently in the slot that this item is being moved to,
 *     push that item up in the GUI.</li>
 *     <li>PUSH_ITEM_LEFT - If there is an item currently in the slot that this item is being moved to,
 *     push that item to the left in the GUI.</li>
 *     <li>PUSH_ITEM_RIGHT - If there is an item currently in the slot that this item is being moved to,
 *     push that item to the right in the GUI.</li>
 * </ul>
 *
 * @author Mikedeejay2
 */
public enum MovementType
{
    OVERRIDE_ITEM,
    SWAP_ITEM,
    PUSH_ITEM_DOWN,
    PUSH_ITEM_UP,
    PUSH_ITEM_LEFT,
    PUSH_ITEM_RIGHT
}
