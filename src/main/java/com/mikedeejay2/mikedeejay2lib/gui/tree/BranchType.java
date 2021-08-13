package com.mikedeejay2.mikedeejay2lib.gui.tree;

/**
 * The way that a branch connects the parent item to the
 * child item in a <code>GUITreeModule</code>
 *
 * @author Mikedeejay2
 */
public enum BranchType
{
    /**
     * When creating a branch, run the branch horizontal first, such as:
     * <pre>
     *     ⇩ parent item
     *     ■───────────┐
     *                 │
     *                 │
     *                 │
     *                 ■ ⇦ child item
     * </pre>
     */
    HORIZONTAL_FIRST,

    /**
     * When creating a branch, run the branch vertical first, such as:
     * <pre>
     *     ⇩ parent item
     *     ■
     *     │
     *     │
     *     │
     *     └───────────■ ⇦ child item
     * </pre>
     */
    VERTICAL_FIRST
}
