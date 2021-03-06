package com.mikedeejay2.mikedeejay2lib.gui.event.util;

import com.mikedeejay2.mikedeejay2lib.gui.GUIContainer;
import com.mikedeejay2.mikedeejay2lib.gui.GUILayer;
import com.mikedeejay2.mikedeejay2lib.gui.event.GUIEvent;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;

public class GUIClearLayerEvent implements GUIEvent
{
    protected String layerName;
    protected int index;
    protected boolean useIndex;

    public GUIClearLayerEvent(String layerName)
    {
        this.layerName = layerName;
        this.index = -1;
        this.useIndex = false;
    }

    public GUIClearLayerEvent(int index)
    {
        this.index = index;
        this.layerName = null;
        this.useIndex = true;
    }

    @Override
    public void execute(InventoryClickEvent event, GUIContainer gui)
    {
        ClickType clickType = event.getClick();
        if(clickType != ClickType.LEFT) return;
        GUILayer layer = null;
        if(useIndex)
        {
            layer = gui.getLayer(index);
        }
        else
        {
            layer = gui.getLayer(layerName);
        }

        layer.clearLayer();
    }
}
