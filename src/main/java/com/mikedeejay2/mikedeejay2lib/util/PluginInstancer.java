package com.mikedeejay2.mikedeejay2lib.util;

import com.mikedeejay2.mikedeejay2lib.PluginBase;

/**
 * A class that holds an instance to the plugin.
 * This is used instead of static referencing because if multiple
 * plugins use Mikedeejay2Lib only 1 static instance of the plugin
 * will be held for multiple plugins which results in a large amount
 * of errors.
 *
 * @param <P> The plugin type to use (Default is <tt>PluginBase</tt> but it's recommended to input the plugin's Main class instead)
 *
 * @author Mikedeejay2
 */
public abstract class PluginInstancer<P extends PluginBase>
{
    // The plugin instance
    protected final P plugin;

    public PluginInstancer(P plugin)
    {
        this.plugin = plugin;
    }
}
