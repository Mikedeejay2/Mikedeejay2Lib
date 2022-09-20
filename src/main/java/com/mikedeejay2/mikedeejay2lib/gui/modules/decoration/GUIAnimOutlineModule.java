package com.mikedeejay2.mikedeejay2lib.gui.modules.decoration;

import com.mikedeejay2.mikedeejay2lib.gui.GUIContainer;
import com.mikedeejay2.mikedeejay2lib.gui.animation.AnimationSpecification;
import com.mikedeejay2.mikedeejay2lib.gui.item.AnimatedGUIItem;
import com.mikedeejay2.mikedeejay2lib.gui.util.SlotMatcher;
import org.bukkit.entity.Player;

/**
 * Module that adds an outline of an item type to the sides of a GUI.
 * Use to add decoration to a GUI.
 *
 * @author Mikedeejay2
 */
public class GUIAnimOutlineModule extends GUIAnimDecoratorModule {
    /**
     * Construct a new <code>GUIAnimOutlineModule</code>
     *
     * @param outlineItem    The GUI item that will be used for the border
     * @param specification  The {@link AnimationSpecification} to use
     * @param animMultiplier The animation multiplier
     */
    public GUIAnimOutlineModule(AnimatedGUIItem outlineItem, AnimationSpecification specification, int animMultiplier) {
        super(null, outlineItem, specification, animMultiplier);
    }

    /**
     * Construct a new <code>GUIAnimOutlineModule</code>
     *
     * @param outlineItem The GUI item that will be used for the border
     * @param specification  The {@link AnimationSpecification} to use
     */
    public GUIAnimOutlineModule(AnimatedGUIItem outlineItem, AnimationSpecification specification) {
        super(null, outlineItem, specification);
    }

    /**
     * Construct a new <code>GUIAnimOutlineModule</code> with the default top left diagonal {@link AnimationSpecification}
     *
     * @param outlineItem The GUI item that will be used for the border
     */
    public GUIAnimOutlineModule(AnimatedGUIItem outlineItem) {
        this(outlineItem, new AnimationSpecification(
            AnimationSpecification.Position.TOP_LEFT,
            AnimationSpecification.Style.LINEAR));
    }

    @Override
    public void onOpenHead(Player player, GUIContainer gui) {
        this.setMatcher(SlotMatcher.not(SlotMatcher.inRange(2, 2, gui.getRows() - 1, gui.getCols() - 1)));
        super.onOpenHead(player, gui);
    }
}
