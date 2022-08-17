package com.mikedeejay2.mikedeejay2lib.text.actionbar.modules;

import com.mikedeejay2.mikedeejay2lib.text.actionbar.ActionBar;
import com.mikedeejay2.mikedeejay2lib.text.actionbar.ActionBarFrame;

/**
 * A module for an <code>ActionBarSystem</code> for adding more code / functionality to
 * action bars.
 *
 * @see ActionBar
 *
 * @author Mikedeejay2
 */
public interface ActionBarModule {
    /**
     * Called when an action bar is ticked
     *
     * @param bar   The <code>ActionBarSystem</code> being ticked
     * @param frame The frame that the action bar is on
     */
    default void onTick(ActionBar bar, ActionBarFrame frame) {}

    /**
     * Called when an action bar is changing frames
     *
     * @param bar   The <code>ActionBarSystem</code> being ticked
     * @param frame The frame that the action bar is on
     */
    default void onFrame(ActionBar bar, ActionBarFrame frame) {}
}
