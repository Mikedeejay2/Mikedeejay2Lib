package com.mikedeejay2.mikedeejay2lib.gui.modules.decoration;

import com.mikedeejay2.mikedeejay2lib.gui.GUIContainer;
import com.mikedeejay2.mikedeejay2lib.gui.GUILayer;
import com.mikedeejay2.mikedeejay2lib.gui.item.GUIItem;
import com.mikedeejay2.mikedeejay2lib.gui.modules.util.GUIAbstractMatchedModule;
import com.mikedeejay2.mikedeejay2lib.gui.util.SlotMatcher;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

/**
 * An abstract module that sets items to all slots matched by a {@link SlotMatcher}
 *
 * @author Mikedeejay2
 */
public abstract class GUIAbstractDecoratorModule extends GUIAbstractMatchedModule {

    /**
     * Construct a new <code>GUIMatchedDecorator</code>
     *
     * @param matcher The {@link SlotMatcher} to use
     * @param layerName The name of the layer to decorate
     */
    public GUIAbstractDecoratorModule(SlotMatcher matcher, @Nullable String layerName) {
        super(matcher, layerName);
    }

    /**
     * Construct a new <code>GUIMatchedDecorator</code>
     *
     * @param matcher The {@link SlotMatcher} to use
     */
    public GUIAbstractDecoratorModule(SlotMatcher matcher) {
        this(matcher, null);
    }

    /**
     * Called when a match is found, sets item to matched slot
     *
     * @param matchData The data associated with the match
     */
    @Override
    protected void onMatch(SlotMatcher.MatchData matchData) {
        GUILayer layer = matchData.gui.getLayer(layerName);
        layer.setItem(matchData.row, matchData.col, getItem(matchData));
    }

    /**
     * Make sure that layer name isn't null. This module is specifically meant to decorate a single layer.
     *
     * @param player The player that is viewing the GUI
     * @param gui    The GUI
     */
    @Override
    public void onOpenHead(Player player, GUIContainer gui) {
        if(layerName == null) layerName = gui.getLayer(0).getName();
        super.onOpenHead(player, gui);
    }

    /**
     * Get the item on a successful slot match
     *
     * @param matchData The provided {@link SlotMatcher.MatchData}
     * @return The item to be used
     */
    protected abstract GUIItem getItem(SlotMatcher.MatchData matchData);
}
