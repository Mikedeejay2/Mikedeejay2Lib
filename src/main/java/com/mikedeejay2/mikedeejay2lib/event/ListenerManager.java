package com.mikedeejay2.mikedeejay2lib.event;

import com.mikedeejay2.mikedeejay2lib.PluginBase;
import com.mikedeejay2.mikedeejay2lib.util.PluginInstancer;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;

import java.util.ArrayList;

public class ListenerManager extends PluginInstancer<PluginBase>
{
    private final ArrayList<Listener> listeners;

    public ListenerManager(PluginBase plugin)
    {
        super(plugin);
        listeners = new ArrayList<>();
    }

    public void addListener(Listener listener)
    {
        listeners.add(listener);
    }

    public void registerAll()
    {
        PluginManager manager = plugin.getServer().getPluginManager();
        listeners.forEach(listener -> manager.registerEvents(listener, plugin));
    }

    public ArrayList<Listener> getListeners()
    {
        return listeners;
    }
}
