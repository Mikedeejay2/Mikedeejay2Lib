package com.mikedeejay2.mikedeejay2lib.gui.modules.decoration;

import com.mikedeejay2.mikedeejay2lib.gui.animation.AnimationSpecification;
import com.mikedeejay2.mikedeejay2lib.gui.item.AnimatedGUIItem;
import com.mikedeejay2.mikedeejay2lib.gui.util.SlotMatcher;
import org.jetbrains.annotations.Nullable;

/**
 * A GUI module made easily specify where {@link AnimatedGUIItem AnimatedGUIItems} should be placed in a GUI, i.e.
 * <i>decorating</i> the GUI. This module uses the helper system {@link SlotMatcher} to match the slots that should be
 * decorated.
 *
 * @author Mikedeejay2
 */
public class GUIAnimDecoratorModule extends GUIAbstractDecoratorModule {
    /**
     * The {@link AnimationSpecification} to use
     */
    protected AnimationSpecification specification;

    /**
     * The animation multiplier
     */
    protected int animMultiplier;

    /**
     * The animated item used for decorating the GUI
     */
    protected AnimatedGUIItem guiItem;

    /**
     * Construct a new <code>GUIAnimDecoratorModule</code>
     *
     * @param matcher        The {@link SlotMatcher} to use
     * @param guiItem        The animated item used for decorating the GUI
     * @param specification  The {@link AnimationSpecification} to use
     * @param animMultiplier The animation multiplier
     * @param layerName      The name of the layer to decorate
     */
    public GUIAnimDecoratorModule(SlotMatcher matcher, AnimatedGUIItem guiItem, AnimationSpecification specification, int animMultiplier, @Nullable String layerName) {
        super(matcher, layerName);
        this.guiItem = guiItem;
        this.specification = specification;
        this.animMultiplier = animMultiplier;
    }

    /**
     * Construct a new <code>GUIAnimDecoratorModule</code>
     *
     * @param matcher        The {@link SlotMatcher} to use
     * @param guiItem        The animated item used for decorating the GUI
     * @param specification  The {@link AnimationSpecification} to use
     * @param layerName      The name of the layer to decorate
     */
    public GUIAnimDecoratorModule(SlotMatcher matcher, AnimatedGUIItem guiItem, AnimationSpecification specification, @Nullable String layerName) {
        this(matcher, guiItem, specification, 1, layerName);
    }

    /**
     * Construct a new <code>GUIAnimDecoratorModule</code>
     *
     * @param matcher        The {@link SlotMatcher} to use
     * @param guiItem        The animated item used for decorating the GUI
     * @param specification  The {@link AnimationSpecification} to use
     * @param animMultiplier The animation multiplier
     */
    public GUIAnimDecoratorModule(SlotMatcher matcher, AnimatedGUIItem guiItem, AnimationSpecification specification, int animMultiplier) {
        this(matcher, guiItem, specification, animMultiplier, null);
    }

    /**
     * Construct a new <code>GUIAnimDecoratorModule</code>
     *
     * @param matcher       The {@link SlotMatcher} to use
     * @param guiItem       The animated item used for decorating the GUI
     * @param specification The {@link AnimationSpecification} to use
     */
    public GUIAnimDecoratorModule(SlotMatcher matcher, AnimatedGUIItem guiItem, AnimationSpecification specification) {
        this(matcher, guiItem, specification, 1);
    }

    /**
     * Get the item on a successful slot match
     *
     * @param matchData The provided {@link SlotMatcher.MatchData}
     * @return The animated item to be used
     */
    @Override
    protected AnimatedGUIItem getItem(SlotMatcher.MatchData matchData) {
        int maxRow = matchData.gui.getRows();
        int maxCol = matchData.gui.getCols();
        return specification.getItemFor(guiItem, matchData.row, matchData.col, maxRow, maxCol, animMultiplier);
    }

    /**
     * Get the animated item used for decorating the GUI
     *
     * @return The animated item used for decorating the GUI
     */
    public AnimatedGUIItem getGuiItem() {
        return guiItem;
    }

    /**
     * Set the animated item used for decorating the GUI
     *
     * @param guiItem The new animated item
     */
    public void setGuiItem(AnimatedGUIItem guiItem) {
        this.guiItem = guiItem;
    }

    /**
     * Get the {@link AnimationSpecification} to use for animating the items
     *
     * @return The {@link AnimationSpecification}
     */
    public AnimationSpecification getSpecification() {
        return specification;
    }

    /**
     * Set the new {@link AnimationSpecification} to use for animating the items
     *
     * @param specification The new {@link AnimationSpecification}
     */
    public void setSpecification(AnimationSpecification specification) {
        this.specification = specification;
    }

    /**
     * Get the animation multiplier
     *
     * @return The animation multiplier
     */
    public int getAnimMultiplier() {
        return animMultiplier;
    }

    /**
     * Set the animation multiplier
     *
     * @param animMultiplier The new animation multiplier
     */
    public void setAnimMultiplier(int animMultiplier) {
        this.animMultiplier = animMultiplier;
    }
}
