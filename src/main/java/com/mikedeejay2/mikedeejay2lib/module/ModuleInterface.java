package com.mikedeejay2.mikedeejay2lib.module;

/**
 * An interface for a module.
 *
 * @see Module
 *
 * @author Mikedeejay2
 */
public interface ModuleInterface
{
    void onEnable();

    void onDisable();

    String getName();

    boolean isEnabled();
}
