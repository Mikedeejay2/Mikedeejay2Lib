package com.mikedeejay2.mikedeejay2lib.gui.tree;

import com.mikedeejay2.mikedeejay2lib.PluginBase;
import com.mikedeejay2.mikedeejay2lib.gui.item.GUIItem;
import com.mikedeejay2.mikedeejay2lib.util.PluginInstancer;

import java.util.List;

public class GUITreeNode extends PluginInstancer<PluginBase>
{
    protected List<GUITreeNode> children;
    protected GUIItem item;

    public GUITreeNode(PluginBase plugin, GUIItem item)
    {
        super(plugin);
        this.item = item;
    }

    public GUIItem getItem()
    {
        return item;
    }

    public void setItem(GUIItem item)
    {
        this.item = item;
    }

    public List<GUITreeNode> getChildren()
    {
        return children;
    }

    public void setChildren(List<GUITreeNode> children)
    {
        this.children = children;
    }

    public void addChild(GUITreeNode node)
    {
        children.add(node);
    }

    public void
}
