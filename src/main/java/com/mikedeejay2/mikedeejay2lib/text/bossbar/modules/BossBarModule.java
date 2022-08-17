package com.mikedeejay2.mikedeejay2lib.text.bossbar.modules;

import com.mikedeejay2.mikedeejay2lib.text.bossbar.BossBarSystem;

/**
 * A base <code>BossBarSystem</code> module for adding more code / functionality to
 * boss bars.
 *
 * @author Mikedeejay2
 */
public interface BossBarModule {
    /**
     * Method that is called when a boss bar is ticked
     *
     * @param system The <code>BossBarSystem</code> being ticked
     */
    default void onTick(BossBarSystem system) {}
}
