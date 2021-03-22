package com.mikedeejay2.mikedeejay2lib.gui;

/**
 * Simple Interface for constructing GUIs upon calling the {@link GUIConstructor#get()} method.
 *
 * @author Mikedeejay2
 */
@FunctionalInterface
public interface GUIConstructor
{
    GUIContainer get();
}
