package com.mikedeejay2.mikedeejay2lib.gui.modules.decoration;

import com.mikedeejay2.mikedeejay2lib.gui.animation.AnimationSpecification;
import com.mikedeejay2.mikedeejay2lib.gui.item.AnimatedGUIItem;
import com.mikedeejay2.mikedeejay2lib.gui.util.SlotMatcher;

/**
 * Module that adds an animated background to a GUI
 * Use to add decoration to a GUI.
 *
 * @author Mikedeejay2
 */
public class GUIAnimBackgroundModule extends GUIAnimDecoratorModule {
    /**
     * Construct a new <code>GUIAnimBackgroundModule</code>
     *
     * @param backgroundItem The GUI item that will be used for the border
     * @param specification  The {@link AnimationSpecification} to use
     * @param animMultiplier The animation multiplier
     */
    public GUIAnimBackgroundModule(AnimatedGUIItem backgroundItem, AnimationSpecification specification, int animMultiplier) {
        super(SlotMatcher.always(), backgroundItem, specification, animMultiplier);
    }

    /**
     * Construct a new <code>GUIAnimBackgroundModule</code>
     *
     * @param backgroundItem The GUI item that will be used for the border
     * @param specification  The {@link AnimationSpecification} to use
     */
    public GUIAnimBackgroundModule(AnimatedGUIItem backgroundItem, AnimationSpecification specification) {
        super(SlotMatcher.always(), backgroundItem, specification);
    }

    /**
     * Construct a new <code>GUIAnimBackgroundModule</code> with the default top left diagonal {@link AnimationSpecification}
     *
     * @param backgroundItem The GUI item that will be used for the border
     */
    public GUIAnimBackgroundModule(AnimatedGUIItem backgroundItem) {
        this(backgroundItem, new AnimationSpecification(
            AnimationSpecification.Position.TOP_LEFT,
            AnimationSpecification.Style.LINEAR));
    }
}
