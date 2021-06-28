package com.mikedeejay2.mikedeejay2lib.text.actionbar.modules;

import com.mikedeejay2.mikedeejay2lib.text.actionbar.ActionBar;
import com.mikedeejay2.mikedeejay2lib.text.actionbar.ActionBarFrame;

/**
 * <code>ActionBarModule</code> for allowing custom setting of text for the current
 * frame of an action bar.
 *
 * @author Mikedeejay2
 */
public class ActionBarSetTextModule implements ActionBarModule
{
    // The String of text that the action bar will be changed to
    protected String text;

    /**
     * @param text The String of text that the action bar will be changed to
     */
    public ActionBarSetTextModule(String text)
    {
        this.text = text;
    }

    @Override
    public void onTick(ActionBar bar, ActionBarFrame frame)
    {
        frame.setText(text);
    }

    /**
     * Get the text of this module
     *
     * @return The text of this module
     */
    public String getText()
    {
        return text;
    }

    /**
     * Set the text of this module
     *
     * @param text The new text
     */
    public void setText(String text)
    {
        this.text = text;
    }
}
