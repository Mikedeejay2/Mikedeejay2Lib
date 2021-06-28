package com.mikedeejay2.mikedeejay2lib.gui.tree;

import com.mikedeejay2.mikedeejay2lib.gui.GUILayer;
import com.mikedeejay2.mikedeejay2lib.gui.item.GUIItem;
import com.mikedeejay2.mikedeejay2lib.item.ItemBuilder;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.List;

/**
 * A single node of a tree in a <code>GUITreeModule</code>
 *
 * @author Mikedeejay2
 */
public class GUITreeNode
{
    // A list of the node children
    protected List<GUITreeNode> children;
    // The item to display
    protected GUIItem item;
    // The branch item that will be used from the parent to this child node
    protected GUIItem branchItem;
    // The row to display this node at - relative if a child node
    protected int row;
    // The column to display this node at - relative if a child node
    protected int col;
    // The Branch Type to display
    protected BranchType branchType;

    public GUITreeNode(GUIItem item, int row, int col, BranchType type)
    {
        this.children = new ArrayList<>();
        this.item = item;
        this.branchItem = new GUIItem(ItemBuilder.of(Material.GRAY_STAINED_GLASS_PANE).setEmptyName().get());
        this.row = row;
        this.col = col;
        this.branchType = type;
    }

    /**
     * Get the item that this node displays
     *
     * @return The node item
     */
    public GUIItem getItem()
    {
        return item;
    }

    /**
     * Set a new node item that this node displays
     *
     * @param item The new item to set the node to
     */
    public void setItem(GUIItem item)
    {
        this.item = item;
    }

    /**
     * Get the branch item that displays from the parent item to this child item
     *
     * @return The branch item
     */
    public GUIItem getBranchItem()
    {
        return branchItem;
    }

    /**
     * Set the branch item that displays
     *
     * @param branchItem The new branch item to set to
     */
    public void setBranchItem(GUIItem branchItem)
    {
        this.branchItem = branchItem;
    }

    /**
     * Get the list of children that this node contains
     *
     * @return The list of children
     */
    public List<GUITreeNode> getChildren()
    {
        return children;
    }

    /**
     * Set the list of children that this node contains
     *
     * @param children The new list of items
     */
    public void setChildren(List<GUITreeNode> children)
    {
        this.children = children;
    }

    /**
     * Add a child node to the tree
     *
     * @param node The node to add
     */
    public void addChild(GUITreeNode node)
    {
        children.add(node);
    }

    /**
     * Remove a child node to the tree
     *
     * @param node The node to remove
     */
    public void removeChild(GUITreeNode node)
    {
        children.remove(node);
    }

    /**
     * Returns whether this node contains a child node
     *
     * @param node The node to see if it
     * @return Whether this node contains the child node
     */
    public boolean containsChild(GUITreeNode node)
    {
        return children.contains(node);
    }

    /**
     * Get the row that this node item exists on - relative if a child
     *
     * @return The row
     */
    public int getRow()
    {
        return row;
    }

    /**
     * Set the row that this node item exists on - should be relative if it is a child
     *
     * @param row The new row
     */
    public void setRow(int row)
    {
        this.row = row;
    }

    /**
     * Get the column that this node item exists on - relative if a child
     *
     * @return The column
     */
    public int getCol()
    {
        return col;
    }

    /**
     * Set the column that this node item exists on - relative if it is a child
     *
     * @param col The new column
     */
    public void setCol(int col)
    {
        this.col = col;
    }

    public void buildChildren(GUILayer layer, int curRow, int curCol)
    {
        for(GUITreeNode node : children)
        {
            GUIItem nodeItem = node.getItem();
            int     nodeRow  = curRow + node.getRow();
            int     nodeCol  = curCol + node.getCol();
            layer.setItem(nodeRow, nodeCol, nodeItem);

            GUIItem    nodeBranchItem = node.getBranchItem();
            BranchType nodeBranchType = node.getBranchType();
            int        startRow       = Math.min(curRow, nodeRow);
            int        endRow         = Math.max(curRow, nodeRow);
            int        startCol       = Math.min(curCol, nodeCol);
            int        endCol         = Math.max(curCol, nodeCol);

            switch(nodeBranchType)
            {
                case HORIZONTAL_FIRST:
                {
                    for(int i = startCol + 1; i < endCol; i++)
                    {
                        layer.setItem(curRow, i, nodeBranchItem);
                    }
                    for(int i = startRow; i < endRow; i++)
                    {
                        layer.setItem(i, nodeCol, nodeBranchItem);
                    }
                    break;
                }
                case VERTICAL_FIRST:
                {
                    for(int i = startRow; i < endRow; i++)
                    {
                        layer.setItem(i, curCol, nodeBranchItem);
                    }
                    for(int i = startCol + 1; i < endCol; i++)
                    {
                        layer.setItem(nodeRow, i, nodeBranchItem);
                    }
                    break;
                }
            }

            node.buildChildren(layer, nodeRow, nodeCol);
        }
    }

    /**
     * Get the branch type of this node
     *
     * @return The branch type
     */
    public BranchType getBranchType()
    {
        return branchType;
    }

    /**
     * Set this node's branch type to a new type
     *
     * @param branchType The new branch type
     */
    public void setBranchType(BranchType branchType)
    {
        this.branchType = branchType;
    }
}
