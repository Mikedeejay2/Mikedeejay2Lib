package com.mikedeejay2.mikedeejay2lib.gui.tree;

import com.mikedeejay2.mikedeejay2lib.gui.GUIContainer;
import com.mikedeejay2.mikedeejay2lib.gui.GUILayer;
import com.mikedeejay2.mikedeejay2lib.gui.item.GUIItem;
import com.mikedeejay2.mikedeejay2lib.gui.modules.GUIModule;
import com.mikedeejay2.mikedeejay2lib.gui.tree.GUITreeNode;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * a <code>GUIModule</code> that allows the addition of tree like item structures.
 *
 * @author Mikedeejay2
 */
public class GUITreeModule implements GUIModule
{
    // The list of nodes that this tree module displays
    protected List<GUITreeNode> nodes;

    public GUITreeModule()
    {
        this.nodes = new ArrayList<>();
    }

    /**
     * Render the nodes
     *
     * @param player The player that is viewing the GUI
     * @param gui    The GUI
     */
    @Override
    public void onUpdateHead(Player player, GUIContainer gui)
    {
        GUILayer layer = gui.getLayer(0);
        for(GUITreeNode node : nodes)
        {
            GUIItem item = node.getItem();
            int row = node.getRow();
            int col = node.getCol();
            layer.setItem(row, col, item);

            node.buildChildren(layer, node.getRow(), node.getCol());
        }
    }

    /**
     * Add a node to the tree
     *
     * @param node The node to add
     */
    public void addNode(GUITreeNode node)
    {
        nodes.add(node);
    }

    /**
     * Remove a node from the tree
     *
     * @param node THe node to remove
     */
    public void removeNode(GUITreeNode node)
    {
        nodes.remove(node);
    }

    /**
     * Return whether this tree contains a node in it's root level
     *
     * @param node The node to search for
     * @return Whether this tree contains a node
     */
    public boolean containsNode(GUITreeNode node)
    {
        return nodes.contains(node);
    }
}
