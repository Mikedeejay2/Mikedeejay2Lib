package com.mikedeejay2.mikedeejay2lib.gui.modules.decoration;

import com.mikedeejay2.mikedeejay2lib.gui.animation.GUIAnimPattern;
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
     * @param pattern        The animation pattern to use
     * @param animMultiplier The animation multiplier
     */
    public GUIAnimBackgroundModule(AnimatedGUIItem backgroundItem, GUIAnimPattern pattern, int animMultiplier) {
        super(SlotMatcher.always(), backgroundItem, pattern, animMultiplier);
    }

    /**
     * Construct a new <code>GUIAnimBackgroundModule</code>
     *
     * @param backgroundItem The GUI item that will be used for the border
     * @param pattern        The animation pattern to use
     */
    public GUIAnimBackgroundModule(AnimatedGUIItem backgroundItem, GUIAnimPattern pattern) {
        super(SlotMatcher.always(), backgroundItem, pattern);
    }

    /**
     * Construct a new <code>GUIAnimBackgroundModule</code> with the default top left diagonal {@link GUIAnimPattern}
     *
     * @param backgroundItem The GUI item that will be used for the border
     */
    public GUIAnimBackgroundModule(AnimatedGUIItem backgroundItem) {
        this(backgroundItem, GUIAnimPattern.TOP_LEFT_DIAGONAL);
    }
}
