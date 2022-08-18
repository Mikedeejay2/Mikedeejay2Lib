package com.mikedeejay2.mikedeejay2lib.gui.modules.util;

import com.mikedeejay2.mikedeejay2lib.gui.GUIContainer;
import com.mikedeejay2.mikedeejay2lib.gui.modules.GUIModule;
import com.mikedeejay2.mikedeejay2lib.gui.util.SlotMatcher;
import org.bukkit.entity.Player;

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
     * Construct a new <code>GUIAbstractMatchedModule</code>
     *
     * @param matcher The {@link SlotMatcher} to use
     */
    public GUIAbstractMatchedModule(SlotMatcher matcher) {
        this.matcher = matcher;
    }

    /**
     * Call {@link GUIContainer#forMatchedSlots(SlotMatcher, Player, Consumer)}
     *
     * @param player The player that is viewing the GUI
     * @param gui    The GUI
     */
    @Override
    public void onOpenHead(Player player, GUIContainer gui) {
        gui.forMatchedSlots(matcher, player, this::onMatch);
    }

    protected abstract void onMatch(SlotMatcher.MatchData matchData);

    public SlotMatcher getMatcher() {
        return matcher;
    }

    public void setMatcher(SlotMatcher matcher) {
        this.matcher = matcher;
    }
}
