package com.mikedeejay2.mikedeejay2lib.gui.animation;

/**
 * A simple enum that holds a type that an {@link AnimationFrame} can be
 * <p>
 * The types of frames are:
 * <ul>
 *     <li>ITEM - An item change frame</li>
 *     <li>MOVEMENT - A change in position</li>
 *     <li>BOTH - A combination of an item change and a movement change</li>
 * </ul>
 *
 * @author Mikedeejay2
 */
public enum FrameType
{
    /**
     * An item change frame
     */
    ITEM,
    /**
     * A change in position
     */
    MOVEMENT,
    /**
     * A combination of an item change and a movement change
     */
    BOTH
}
