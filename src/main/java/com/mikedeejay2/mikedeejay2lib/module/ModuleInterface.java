package com.mikedeejay2.mikedeejay2lib.module;

public interface ModuleInterface
{
    void onEnable();

    void onDisable();

    String getName();

    boolean isEnabled();
}
