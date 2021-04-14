package com.mikedeejay2.mikedeejay2lib.gui.event.util;

import com.mikedeejay2.mikedeejay2lib.gui.GUIContainer;
import com.mikedeejay2.mikedeejay2lib.gui.GUILayer;
import com.mikedeejay2.mikedeejay2lib.gui.event.GUIEvent;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;

/**
 * Event for clearing an entire layer of all items in a GUI
 *
 * @author Mikedeejay2
 */
public class GUIClearLayerEvent implements GUIEvent
{
    protected String layerName;
    protected int index;
    protected GUILayer layer;
    int mode;

    public GUIClearLayerEvent(String layerName)
    {
        this.layerName = layerName;
        this.mode = 1;
    }

    public GUIClearLayerEvent(int index)
    {
        this.index =  index;
        this.mode = 2;
    }

    public GUIClearLayerEvent(GUILayer layer)
    {
        this.layer = layer;
        this.mode = 3;
    }

    @Override
    public void execute(InventoryClickEvent event, GUIContainer gui)
    {
        ClickType clickType = event.getClick();
        if(clickType != ClickType.LEFT) return;
        GUILayer layer = null;
        switch(mode)
        {
            case 1:
                layer = gui.getLayer(layerName);
                break;
            case 2:
                layer = gui.getLayer(index);
                break;
            case 3:
                layer = this.layer;
                break;
        }

        if(layer != null) layer.clearLayer();
    }
}
