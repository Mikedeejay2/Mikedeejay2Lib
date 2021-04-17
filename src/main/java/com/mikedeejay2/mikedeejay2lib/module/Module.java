package com.mikedeejay2.mikedeejay2lib.module;

public abstract class Module implements ModuleInterface
{
    private boolean isEnabled;

    public Module()
    {
        this.isEnabled = false;
    }

    private void enable()
    {
        this.isEnabled = true;
        this.onEnable();
    }

    private void disable()
    {
        this.isEnabled = false;
        this.onDisable();
    }

    @Override
    public boolean isEnabled()
    {
        return isEnabled;
    }
}
