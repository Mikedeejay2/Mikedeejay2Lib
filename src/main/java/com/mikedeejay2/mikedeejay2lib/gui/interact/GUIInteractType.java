package com.mikedeejay2.mikedeejay2lib.gui.interact;

/**
 * The GUI Interact type. Based on the type chosen, the <code>GUIInteractExecutor</code>
 * will act differently.
 *
 * @author Mikedeejay2
 */
public enum GUIInteractType
{
    /**
     * The default interaction type, matches vanilla movement
     */
    DEFAULT,

    /**
     * Only allow one of an exact item to exist in the GUI
     */
    SINGLE_ITEM,

    /**
     * Only allow one of a material to exist in the GUI
     */
    SINGLE_MATERIAL;
}
