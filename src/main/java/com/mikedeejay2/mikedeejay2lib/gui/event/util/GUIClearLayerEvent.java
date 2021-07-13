package com.mikedeejay2.mikedeejay2lib.gui.event.util;

import com.mikedeejay2.mikedeejay2lib.gui.GUIContainer;
import com.mikedeejay2.mikedeejay2lib.gui.GUILayer;
import com.mikedeejay2.mikedeejay2lib.gui.event.GUIEvent;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Event for clearing an entire layer of all items in a GUI
 *
 * @author Mikedeejay2
 */
public class GUIClearLayerEvent implements GUIEvent
{
    /**
     * The layer name of the layer to clear. Null if <code>mode</code> is not {@link ClearLayerMode#LAYER_NAME}
     */
    protected @Nullable String layerName;

    /**
     * The index of the layer to clear. <code>0</code> if <code>mode</code> is not {@link ClearLayerMode#INDEX}
     */
    protected int index;

    /**
     * The index of the layer to clear. Null if <code>mode</code> is not {@link ClearLayerMode#LAYER}
     */
    protected @Nullable GUILayer layer;

    /**
     * The {@link ClearLayerMode} of the event
     */
    protected ClearLayerMode mode;

    /**
     * Construct a new <code>GUIClearLayerEvent</code> based off of the layer's name
     *
     * @param layerName The layer name to clear
     */
    public GUIClearLayerEvent(@NotNull String layerName)
    {
        this.layerName = layerName;
        this.mode = ClearLayerMode.LAYER_NAME;
    }

    /**
     * Construct a new <code>GUIClearLayerEvent</code> based off of the layer's index
     *
     * @param index The index of the layer to clear
     */
    public GUIClearLayerEvent(int index)
    {
        this.index =  index;
        this.mode = ClearLayerMode.INDEX;
    }

    /**
     * Construct a new <code>GUIClearLayerEvent</code> based off of the layer's instance
     *
     * @param layer The layer to clear
     */
    public GUIClearLayerEvent(@NotNull GUILayer layer)
    {
        this.layer = layer;
        this.mode = ClearLayerMode.LAYER;
    }

    /**
     * {@inheritDoc}
     *
     * @param event The event of the click
     * @param gui   The GUI that the event took place in
     */
    @Override
    public void execute(InventoryClickEvent event, GUIContainer gui)
    {
        ClickType clickType = event.getClick();
        if(clickType != ClickType.LEFT) return;
        GUILayer layer = null;
        switch(mode)
        {
            case LAYER_NAME:
                layer = gui.getLayer(layerName);
                break;
            case INDEX:
                layer = gui.getLayer(index);
                break;
            case LAYER:
                layer = this.layer;
                break;
        }

        if(layer != null) layer.clearLayer();
    }

    /**
     * The layer mode used to clear the and reference the layer
     *
     * @author Mikedeejay2
     */
    private enum ClearLayerMode
    {
        LAYER_NAME,
        INDEX,
        LAYER;
    }
}
