package com.mikedeejay2.mikedeejay2lib.module;

/**
 * A module class, used to be extended for adding functionality.
 * Used in the modules system, see {@link ModuleRegistry}
 *
 * @author Mikedeejay2
 */
public abstract class Module implements ModuleInterface {
    /**
     * Whether the module is enabled or not
     */
    private boolean isEnabled;

    /**
     * Internal constructor for setting the enabled state to false by default
     */
    public Module() {
        this.isEnabled = false;
    }

    /**
     * Internal method for enabling the module and calling {@link Module#onEnable()}
     */
    private void enable() {
        this.isEnabled = true;
        this.onEnable();
    }

    /**
     * Internal method for disabling the module and calling {@link Module#onDisable()}
     */
    private void disable() {
        this.isEnabled = false;
        this.onDisable();
    }

    /**
     * Get whether this <code>Module</code> is enabled or not
     *
     * @return The current enabled state
     */
    @Override
    public boolean isEnabled() {
        return isEnabled;
    }
}
