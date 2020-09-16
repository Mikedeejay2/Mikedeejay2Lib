package com.mikedeejay2.mikedeejay2lib.util;

import com.mikedeejay2.mikedeejay2lib.PluginBase;
import com.mikedeejay2.mikedeejay2lib.util.chat.Chat;
import com.mikedeejay2.mikedeejay2lib.util.item.ItemCreator;

public abstract class PluginInstancer<P extends PluginBase>
{
    protected final P plugin;

    public PluginInstancer(P plugin)
    {
        this.plugin = plugin;
    }
}
