package com.mikedeejay2.mikedeejay2lib.gui.util;

import com.mikedeejay2.mikedeejay2lib.gui.GUIContainer;
import com.mikedeejay2.mikedeejay2lib.gui.GUILayer;
import com.mikedeejay2.mikedeejay2lib.gui.item.GUIItem;
import org.apache.commons.lang3.Validate;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * Chaining system that matches a slot of a GUI to different conditions.
 *
 * @author Mikedeejay2
 */
public abstract class SlotMatcher {
    /**
     * Get a matcher for a range of rows and columns
     *
     * @param row1 The first row in the range
     * @param col1 The first column in the range
     * @param row2 The last row in the range
     * @param col2 The last column in the range
     * @return The constructed matcher
     */
    public static SlotMatcher inRange(int row1, int col1, int row2, int col2) {
        return new RangeMatcher(row1, col1, row2, col2);
    }

    /**
     * Get a matcher for a specific row and column
     *
     * @param row The row to match
     * @param col The column to match
     * @return The constructed matcher
     */
    public static SlotMatcher at(int row, int col) {
        return new RangeMatcher(row, col, row, col);
    }

    /**
     * Get a matcher for an {@link ItemStack} to match. The item is matched exactly.
     *
     * @param itemStack The {@link ItemStack} to match
     * @return The constructed matcher
     */
    public static SlotMatcher item(ItemStack itemStack) {
        return new ItemMatcher(itemStack);
    }

    /**
     * Get a matcher for a {@link Material} to match
     *
     * @param material The {@link Material} to match
     * @return The constructed matcher
     */
    public static SlotMatcher material(Material material) {
        return new MaterialMatcher(material);
    }

    /**
     * Get a matcher that matches the name of a {@link GUILayer}
     *
     * @param name The name of the layer to match
     * @return The constructed matcher
     */
    public static SlotMatcher layer(String name) {
        return new LayerNameMatcher(name);
    }

    /**
     * Get a matcher that matches the index of a {@link GUILayer}
     *
     * @param index The index of the layer to match
     * @return The constructed matcher
     */
    public static SlotMatcher layer(int index) {
        return new LayerIndexMatcher(index);
    }

    /**
     * Get a matcher that only matches empty slots
     *
     * @return The constructed matcher
     */
    public static SlotMatcher empty() {
        return new EmptyMatcher();
    }

    /**
     * Get a matcher that only matches filled slots
     *
     * @return The constructed matcher
     */
    public static SlotMatcher filled() {
        return SlotMatcher.not(SlotMatcher.empty());
    }

    /**
     * Get a matcher that matches the names of Items. The names are matched exactly.
     *
     * @param name The name to match
     * @return The constructed matcher
     */
    public static SlotMatcher named(String name) {
        return new NameMatcher(name);
    }

    /**
     * Get a matcher that matches the names of Items. The names are matched if they are anywhere in the item's name.
     *
     * @param name The name to match
     * @return The constructed matcher
     */
    public static SlotMatcher nameContains(String name) {
        return new NameContainsMatcher(name);
    }

    /**
     * Get a matcher that matches items that are movable in the GUI.
     *
     * @return The constructed matcher
     */
    public static SlotMatcher movable() {
        return new MovableMatcher();
    }

    /**
     * Get a matcher that matches only the first N matches. For this to work correctly, this matcher should be ANDed
     * with another matcher after all other matchers.
     * <p>
     * This matcher is not reusable. Once it's used once, it will remember its previous matches. If ANDing with a
     * matcher that is to be used multiple times, create the reusable matcher without this matcher, then before using,
     * AND the reusable matcher with this matcher to create a new temporary matcher.
     *
     * @param amount The amount of matches to make before not matching
     * @return The constructed matcher
     */
    public static SlotMatcher first(int amount) {
        return new FirstNMatcher(amount);
    }

    /**
     * Get a matcher that matches only the first match. For this to work correctly, this matcher should be ANDed
     * with another matcher after all other matchers.
     * <p>
     * This matcher is not reusable. Once it's used once, it will remember its previous matches. If ANDing with a
     * matcher that is to be used multiple times, create the reusable matcher without this matcher, then before using,
     * AND the reusable matcher with this matcher to create a new temporary matcher.
     *
     * @return The constructed matcher
     */
    public static SlotMatcher first() {
        return new FirstNMatcher(1);
    }

    /**
     * Combine two matchers using AND so that both matchers must be true to match
     *
     * @param matcher The other matcher
     * @return The new combined matcher
     */
    public SlotMatcher and(SlotMatcher matcher) {
        return new ConjunctionMatcher(this, matcher);
    }

    /**
     * Combine two matchers using OR so that only one matcher must be true to match
     *
     * @param matcher The other matcher
     * @return The new combined matcher
     */
    public SlotMatcher or(SlotMatcher matcher) {
        return new DisjunctionMatcher(this, matcher);
    }

    /**
     * Negate a matcher so that it matches the inverse
     *
     * @param matcher The matcher to negate
     * @return The new negated matcher
     */
    public static SlotMatcher not(SlotMatcher matcher) {
        return new NegatingMatcher(matcher);
    }

    /**
     * Check for a match with {@link MatchData}
     *
     * @param data The {@link MatchData} to match
     * @return Whether the <code>SlotMatcher</code> matches the <code>MatchData</code>
     */
    public abstract boolean matches(MatchData data);

    /**
     * Get whether the matching operation should be stopped
     *
     * @return The stop state
     */
    public boolean shouldStop() {
        return false;
    }

    /**
     * The data that will be passed to the matcher
     *
     * @author Mikedeejay2
     */
    public static class MatchData {
        /**
         * The {@link GUIContainer} instance
         */
        public final GUIContainer gui;

        /**
         * The {@link GUIItem} in the slot
         */
        public final GUIItem item;

        /**
         * The {@link GUILayer} of the item
         */
        public final GUILayer layer;

        /**
         * The {@link Player} viewing the GUI
         */
        public final Player player;

        /**
         * The row of the slot
         */
        public final int row;

        /**
         * The column of the slot
         */
        public final int col;

        /**
         * Construct new <code>Data</code>
         *
         * @param gui  The {@link GUIContainer} instance
         * @param item The {@link GUIItem} in the slot
         * @param layer The {@link GUILayer} of the item
         * @param player The {@link Player} viewing the GUI
         * @param row  The row of the slot
         * @param col  The column of the slot
         */
        public MatchData(GUIContainer gui, GUIItem item, GUILayer layer, Player player, int row, int col) {
            Validate.notNull(player, "Attempted to create MatchData of null Player");
            Validate.notNull(gui, "Attempted to create MatchData of null GUIContainer");
            this.gui = gui;
            this.item = item;
            this.layer = layer;
            this.player = player;
            this.row = row;
            this.col = col;
        }
    }

    /**
     * An abstract matcher that holds two matchers
     *
     * @author Mikedeejay2
     */
    protected abstract static class JunctionMatcher extends SlotMatcher {
        /**
         * The first matcher
         */
        protected final SlotMatcher matcher1;

        /**
         * The second matcher
         */
        protected final SlotMatcher matcher2;

        /**
         * Construct a new <code>JunctionMatcher</code>
         *
         * @param matcher1 The first matcher
         * @param matcher2 The second matcher
         */
        public JunctionMatcher(SlotMatcher matcher1, SlotMatcher matcher2) {
            this.matcher1 = matcher1;
            this.matcher2 = matcher2;
        }

        /**
         * Get whether the matching operation should be stopped
         *
         * @return The stop state
         */
        @Override
        public boolean shouldStop() {
            return matcher1.shouldStop() || matcher2.shouldStop();
        }
    }

    /**
     * An abstract matcher that holds another matcher
     *
     * @author Mikedeejay2
     */
    protected abstract static class WrappedMatcher extends SlotMatcher {
        /**
         * The matcher being wrapped
         */
        protected final SlotMatcher matcher;

        /**
         * Construct a new <code>WrappedMatcher</code>
         *
         * @param matcher The matcher being wrapped
         */
        public WrappedMatcher(SlotMatcher matcher) {
            this.matcher = matcher;
        }

        /**
         * Get whether the matching operation should be stopped
         *
         * @return The stop state
         */
        @Override
        public boolean shouldStop() {
            return matcher.shouldStop();
        }
    }

    /**
     * A {@link JunctionMatcher} that ANDs two matchers together
     *
     * @author Mikedeejay2
     */
    protected static class ConjunctionMatcher extends JunctionMatcher {
        /**
         * Construct a new <code>ConjunctionMatcher</code>
         *
         * @param matcher1 The first matcher
         * @param matcher2 The second matcher
         */
        public ConjunctionMatcher(SlotMatcher matcher1, SlotMatcher matcher2) {
            super(matcher1, matcher2);
        }

        /**
         * Check whether both matchers match with {@link MatchData}
         *
         * @param data The {@link MatchData} to match
         * @return Whether the <code>SlotMatcher</code> matches the <code>MatchData</code>
         */
        @Override
        public boolean matches(MatchData data) {
            return matcher1.matches(data) && matcher2.matches(data);
        }
    }

    /**
     * A {@link JunctionMatcher} that ORs two matchers together
     *
     * @author Mikedeejay2
     */
    protected static class DisjunctionMatcher extends JunctionMatcher {
        /**
         * Construct a new <code>DisjunctionMatcher</code>
         *
         * @param matcher1 The first matcher
         * @param matcher2 The second matcher
         */
        public DisjunctionMatcher(SlotMatcher matcher1, SlotMatcher matcher2) {
            super(matcher1, matcher2);
        }

        /**
         * Check whether either matchers match with {@link MatchData}
         *
         * @param data The {@link MatchData} to match
         * @return Whether the <code>SlotMatcher</code> matches the <code>MatchData</code>
         */
        @Override
        public boolean matches(MatchData data) {
            return matcher1.matches(data) || matcher2.matches(data);
        }
    }

    /**
     * A {@link WrappedMatcher} that NOTs a matcher
     *
     * @author Mikedeejay2
     */
    protected static class NegatingMatcher extends WrappedMatcher {
        /**
         * Construct a new <code>NegatingMatcher</code>
         *
         * @param matcher The matcher being wrapped
         */
        public NegatingMatcher(SlotMatcher matcher) {
            super(matcher);
        }

        /**
         * Check whether the matcher doesn't match with {@link MatchData}
         *
         * @param data The {@link MatchData} to match
         * @return Whether the <code>SlotMatcher</code> matches the <code>MatchData</code>
         */
        @Override
        public boolean matches(MatchData data) {
            return !matcher.matches(data);
        }

        /**
         * Get whether the matching operation should be stopped
         *
         * @return The stop state
         */
        @Override
        public boolean shouldStop() {
            return false;
        }
    }

    /**
     * A matcher that matches a row and column within a range
     *
     * @author Mikedeejay2
     */
    protected static class RangeMatcher extends SlotMatcher {
        /**
         * The minimum row to match
         */
        protected final int minRow;

        /**
         * The maximum row to match
         */
        protected final int maxRow;

        /**
         * The minimum column to match
         */
        protected final int minCol;

        /**
         * The maximum column to match
         */
        protected final int maxCol;

        /**
         * Construct a new <code>RangeMatcher</code>
         *
         * @param row1 The minimum row to match
         * @param col1 The minimum column to match
         * @param row2 The maximum row to match
         * @param col2 The maximum column to match
         */
        protected RangeMatcher(int row1, int col1, int row2, int col2) {
            this.minRow = Math.min(row1, row2);
            this.minCol = Math.min(col1, col2);
            this.maxRow = Math.max(row1, row2);
            this.maxCol = Math.max(col1, col2);
        }

        /**
         * Check whether the row and column in {@link MatchData} is in the range
         *
         * @param data The {@link MatchData} to match
         * @return Whether the <code>SlotMatcher</code> matches the <code>MatchData</code>
         */
        @Override
        public boolean matches(MatchData data) {
            return data.row >= minRow && data.row <= maxRow && data.col >= minCol && data.col <= maxCol;
        }
    }

    /**
     * A matcher that matches an {@link ItemStack} exactly
     *
     * @author Mikedeejay2
     */
    protected static class ItemMatcher extends SlotMatcher {
        /**
         * The {@link ItemStack} to be matched
         */
        protected final ItemStack itemStack;

        /**
         * Construct a new <code>ItemMatcher</code>
         *
         * @param itemStack The {@link ItemStack} to be matched
         */
        protected ItemMatcher(ItemStack itemStack) {
            this.itemStack = itemStack;
        }

        /**
         * Check whether the item in {@link MatchData} matches
         *
         * @param data The {@link MatchData} to match
         * @return Whether the <code>SlotMatcher</code> matches the <code>MatchData</code>
         */
        @Override
        public boolean matches(MatchData data) {
            return data.item != null && itemStack.equals(data.item.get());
        }
    }

    /**
     * A matcher that matches a {@link Material}
     *
     * @author Mikedeejay2
     */
    protected static class MaterialMatcher extends SlotMatcher {
        /**
         * The {@link Material} to be matched
         */
        protected final Material material;

        /**
         * Construct a new <code>MaterialMatcher</code>
         *
         * @param material The {@link Material} to be matched
         */
        protected MaterialMatcher(Material material) {
            this.material = material;
        }

        /**
         * Check whether the material in {@link MatchData} matches
         *
         * @param data The {@link MatchData} to match
         * @return Whether the <code>SlotMatcher</code> matches the <code>MatchData</code>
         */
        @Override
        public boolean matches(MatchData data) {
            return data.item != null && material == data.item.getType();
        }
    }

    /**
     * A matcher that matches a slot when the slot is empty
     *
     * @author Mikedeejay2
     */
    protected static class EmptyMatcher extends SlotMatcher {
        /**
         * Check whether the item in the slot is empty
         *
         * @param data The {@link MatchData} to match
         * @return Whether the <code>SlotMatcher</code> matches the <code>MatchData</code>
         */
        @Override
        public boolean matches(MatchData data) {
            return data.item == null ||
                data.item.getType().isAir() ||
                data.item.get().equals(data.gui.getBackgroundItem());
        }
    }

    /**
     * A matcher that matches the name of an item with {@link String#equals(Object)}
     *
     * @author Mikedeejay2
     */
    protected static class NameMatcher extends SlotMatcher {
        /**
         * The name to be matched
         */
        protected final String name;

        /**
         * Construct a new <code>NameMatcher</code>
         *
         * @param name The name to be matched
         */
        protected NameMatcher(String name) {
            this.name = name;
        }

        /**
         * Check whether the name of the item in {@link MatchData} matches
         *
         * @param data The {@link MatchData} to match
         * @return Whether the <code>SlotMatcher</code> matches the <code>MatchData</code>
         */
        @Override
        public boolean matches(MatchData data) {
            return data.item != null && name.equals(data.item.getName());
        }
    }

    /**
     * A matcher that matches the name of an item with {@link String#contains(CharSequence)};
     *
     * @author Mikedeejay2
     */
    protected static class NameContainsMatcher extends SlotMatcher {
        /**
         * The name to be matched
         */
        protected final String name;

        /**
         * Construct a new <code>NameContainsMatcher</code>
         *
         * @param name The name to be matched
         */
        protected NameContainsMatcher(String name) {
            this.name = name;
        }

        /**
         * Check whether the name of the item in {@link MatchData} matches
         *
         * @param data The {@link MatchData} to match
         * @return Whether the <code>SlotMatcher</code> matches the <code>MatchData</code>
         */
        @Override
        public boolean matches(MatchData data) {
            return data.item != null && name.contains(data.item.getName());
        }
    }

    /**
     * A matcher that matches an item that is movable
     *
     * @author Mikedeejay2
     */
    protected static class MovableMatcher extends SlotMatcher {
        /**
         * Check whether the item in the slot is movable
         *
         * @param data The {@link MatchData} to match
         * @return Whether the <code>SlotMatcher</code> matches the <code>MatchData</code>
         */
        @Override
        public boolean matches(MatchData data) {
            return data.item != null && data.item.isMovable();
        }
    }

    /**
     * A matcher that matches a {@link GUILayer} by name
     *
     * @author Mikedeejay2
     */
    protected static class LayerNameMatcher extends SlotMatcher {
        /**
         * The name of the layer to be matched
         */
        protected final String name;

        /**
         * Construct a new <code>LayerNameMatcher</code>
         *
         * @param name The name of the layer to be matched
         */
        protected LayerNameMatcher(String name) {
            this.name = name;
        }

        /**
         * Check whether the layer in {@link MatchData} matches
         *
         * @param data The {@link MatchData} to match
         * @return Whether the <code>SlotMatcher</code> matches the <code>MatchData</code>
         */
        @Override
        public boolean matches(MatchData data) {
            return data.layer != null && name.equals(data.layer.getName());
        }
    }

    /**
     * A matcher that matches a {@link GUILayer} by index
     *
     * @author Mikedeejay2
     */
    protected static class LayerIndexMatcher extends SlotMatcher {
        /**
         * The name of the layer to be matched
         */
        protected final int index;

        /**
         * Construct a new <code>LayerIndexMatcher</code>
         *
         * @param index The index of the layer to be matched
         */
        protected LayerIndexMatcher(int index) {
            this.index = index;
        }

        /**
         * Check whether the layer in {@link MatchData} matches
         *
         * @param data The {@link MatchData} to match
         * @return Whether the <code>SlotMatcher</code> matches the <code>MatchData</code>
         */
        @Override
        public boolean matches(MatchData data) {
            return data.layer != null && data.gui.getAllLayers().indexOf(data.layer) == index;
        }
    }

    /**
     * A matcher that only matches the first N matches
     *
     * @author Mikedeejay2
     */
    protected static class FirstNMatcher extends SlotMatcher {
        /**
         * The amount of matches to make
         */
        protected final int amount;

        /**
         * The current amount times that a match has occurred
         */
        protected int curMatches;

        /**
         * Construct a new <code>LayerIndexMatcher</code>
         *
         * @param amount The amount of matches to make
         */
        protected FirstNMatcher(int amount) {
            this.amount = amount;
            this.curMatches = 0;
        }

        /**
         * Check whether there are still matches available
         *
         * @param data The {@link MatchData} to match
         * @return Whether the <code>SlotMatcher</code> matches the <code>MatchData</code>
         */
        @Override
        public boolean matches(MatchData data) {
            return ++curMatches <= amount;
        }
    }
}
