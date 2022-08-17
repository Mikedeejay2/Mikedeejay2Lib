package com.mikedeejay2.mikedeejay2lib.module;

/**
 * An interface for a module.
 *
 * @see Module
 *
 * @author Mikedeejay2
 */
public interface ModuleInterface {
    /**
     * Called when this module is enabled
     */
    void onEnable();

    /**
     * Called when this module is disabled
     */
    void onDisable();

    /**
     * Get the name of this module
     *
     * @return The name of this module
     */
    String getName();

    /**
     * Get whether this module is enabled
     *
     * @return Whether this module is enabled
     */
    boolean isEnabled();
}
