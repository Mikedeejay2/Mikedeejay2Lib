package com.mikedeejay2.mikedeejay2lib.text.bossbar.modules;

import com.mikedeejay2.mikedeejay2lib.text.bossbar.BossBarSystem;

/**
 * A base <tt>BossBarSystem</tt> module for adding more code / functionality to
 * boss bars.
 *
 * @author Mikedeejay2
 */
public abstract class BBModule
{
    /**
     * Method that is called when a boss bar is ticked
     *
     * @param system The <tt>BossBarSystem</tt> being ticked
     */
    public void onTick(BossBarSystem system) {}
}
