package com.mikedeejay2.mikedeejay2lib.text.actionbar.modules;

import com.mikedeejay2.mikedeejay2lib.text.actionbar.ActionBar;
import com.mikedeejay2.mikedeejay2lib.text.actionbar.ActionBarFrame;

/**
 * A module for an <tt>ActionBarSystem</tt> for adding more code / functionality to
 * action bars.
 *
 * @see ActionBar
 *
 * @author Mikedeejay2
 */
public abstract class ActionBarModule
{
    /**
     * Called when an action bar is ticked
     *
     * @param bar   The <tt>ActionBarSystem</tt> being ticked
     * @param frame The frame that the action bar is on
     */
    public void onTick(ActionBar bar, ActionBarFrame frame) {}

    /**
     * Called when an action bar is changing frames
     *
     * @param bar   The <tt>ActionBarSystem</tt> being ticked
     * @param frame The frame that the action bar is on
     */
    public void onFrame(ActionBar bar, ActionBarFrame frame) {}
}
