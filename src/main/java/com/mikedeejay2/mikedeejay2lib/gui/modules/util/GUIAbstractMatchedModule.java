package com.mikedeejay2.mikedeejay2lib.gui.modules.util;

import com.mikedeejay2.mikedeejay2lib.gui.GUIContainer;
import com.mikedeejay2.mikedeejay2lib.gui.GUILayer;
import com.mikedeejay2.mikedeejay2lib.gui.modules.GUIModule;
import com.mikedeejay2.mikedeejay2lib.gui.util.SlotMatcher;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

/**
 * An abstract module that takes a {@link SlotMatcher} to operate upon all slots that match the matcher.
 *
 * @author Mikedeejay2
 */
public abstract class GUIAbstractMatchedModule implements GUIModule {
    /**
     * The {@link SlotMatcher} to use
     */
    protected SlotMatcher matcher;

    /**
     * The name of the layer to match on
     */
    protected @Nullable String layerName;

    /**
     * Construct a new <code>GUIAbstractMatchedModule</code>
     *
     * @param matcher   The {@link SlotMatcher} to use
     * @param layerName The name of the layer to match on
     */
    public GUIAbstractMatchedModule(SlotMatcher matcher, @Nullable String layerName) {
        this.matcher = matcher;
        this.layerName = layerName;
    }

    /**
     * Construct a new <code>GUIAbstractMatchedModule</code>
     *
     * @param matcher   The {@link SlotMatcher} to use
     */
    public GUIAbstractMatchedModule(SlotMatcher matcher) {
        this(matcher, null);
    }

    /**
     * Call {@link GUIContainer#forMatchedSlots(SlotMatcher, Player, Consumer)}
     *
     * @param player The player that is viewing the GUI
     * @param gui    The GUI
     */
    @Override
    public void onOpenHead(Player player, GUIContainer gui) {
        if(layerName != null) {
            GUILayer layer = gui.getLayer(layerName);
            layer.forMatchedSlots(matcher, player, this::onMatch);
        } else {
            gui.forMatchedSlots(matcher, player, this::onMatch);
        }
    }

    /**
     * Called when a match is found
     *
     * @param matchData The data associated with the match
     */
    protected abstract void onMatch(SlotMatcher.MatchData matchData);

    public SlotMatcher getMatcher() {
        return matcher;
    }

    public void setMatcher(SlotMatcher matcher) {
        this.matcher = matcher;
    }

    /**
     * Get the name of the layer to decorate
     *
     * @return The name of the layer to decorate
     */
    public @Nullable String getLayerName() {
        return layerName;
    }

    /**
     * Set the name of the layer to decorate
     *
     * @param layerName The new layer name
     */
    public void setLayerName(@Nullable String layerName) {
        this.layerName = layerName;
    }
}
