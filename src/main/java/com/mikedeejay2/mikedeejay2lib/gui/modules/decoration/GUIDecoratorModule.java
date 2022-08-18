package com.mikedeejay2.mikedeejay2lib.gui.modules.decoration;

import com.mikedeejay2.mikedeejay2lib.gui.item.GUIItem;
import com.mikedeejay2.mikedeejay2lib.gui.util.SlotMatcher;
import org.jetbrains.annotations.Nullable;

/**
 * A GUI module made easily specify where {@link GUIItem GUIItems} should be placed in a GUI, i.e. <i>decorating</i>
 * the GUI. This module uses the helper system {@link SlotMatcher} to match the slots that should be decorated.
 *
 * @author Mikedeejay2
 */
public class GUIDecoratorModule extends GUIAbstractDecoratorModule {
    /**
     * The item used for decorating the GUI
     */
    protected GUIItem guiItem;

    /**
     * Construct a new <code>GUIDecoratorModule</code>
     *
     * @param matcher The {@link SlotMatcher} to use
     * @param guiItem The item used for decorating the GUI
     * @param layerName The name of the layer to decorate
     */
    public GUIDecoratorModule(SlotMatcher matcher, GUIItem guiItem, @Nullable String layerName) {
        super(matcher, layerName);
        this.guiItem = guiItem;
    }

    /**
     * Construct a new <code>GUIDecoratorModule</code>
     *
     * @param matcher The {@link SlotMatcher} to use
     * @param guiItem The item used for decorating the GUI
     */
    public GUIDecoratorModule(SlotMatcher matcher, GUIItem guiItem) {
        this(matcher, guiItem, null);
    }

    /**
     * Get the item on a successful slot match
     *
     * @param matchData The provided {@link SlotMatcher.MatchData}
     * @return The item to be used
     */
    @Override
    protected GUIItem getItem(SlotMatcher.MatchData matchData) {
        return guiItem;
    }

    /**
     * Get the item used for decorating the GUI
     *
     * @return The item used for decorating the GUI
     */
    public GUIItem getGuiItem() {
        return guiItem;
    }

    /**
     * Set the item used for decorating the GUI
     *
     * @param guiItem The new item
     */
    public void setGuiItem(GUIItem guiItem) {
        this.guiItem = guiItem;
    }
}
