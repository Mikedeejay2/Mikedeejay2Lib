package com.mikedeejay2.mikedeejay2lib.event;

import com.mikedeejay2.mikedeejay2lib.PluginBase;
import com.mikedeejay2.mikedeejay2lib.util.PluginInstancer;
import org.bukkit.event.Event;

public class EnhancedEvent<T extends Event, P extends PluginBase> extends PluginInstancer<P>
{
    private T event;

    public EnhancedEvent(P plugin)
    {
        super(plugin);
        plugin.getServer().getPluginManager().registerEvent();
    }
}
