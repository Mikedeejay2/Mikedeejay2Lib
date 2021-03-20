package com.mikedeejay2.mikedeejay2lib.text.chat;

import com.mikedeejay2.mikedeejay2lib.PluginBase;
import com.mikedeejay2.mikedeejay2lib.text.chat.modules.ChatModule;
import com.mikedeejay2.mikedeejay2lib.util.chat.ChatConverter;
import com.mikedeejay2.mikedeejay2lib.util.chat.Colors;
import net.md_5.bungee.api.chat.BaseComponent;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

/**
 * A section of text in a <tt>ChatSlide</tt>
 *
 * @see ChatSlide
 * @see ChatSystem
 *
 * @author Mikedeejay2
 */
public class ChatSection
{
    protected final PluginBase plugin;
    // The list of Chat Modules for this section
    protected List<ChatModule> modules;
    // The String of text for this section
    protected String text;
    // Whether this section has been baked or not
    protected boolean baked;
    // The baked array of components for this section
    protected BaseComponent[] components;

    public ChatSection(PluginBase plugin)
    {
        this.plugin = plugin;
        this.modules = new ArrayList<>();
        this.text = "";
        this.baked = false;
    }

    /**
     * Add a <tt>ChatModule</tt> to this <tt>ChatSection</tt>
     *
     * @param module The <tt>ChatModule</tt> to add
     * @return The current <tt>ChatSection</tt>
     */
    public ChatSection addModule(ChatModule module)
    {
        modules.add(module);
        baked = false;
        return this;
    }

    /**
     * Remove a <tt>ChatModule</tt> based off of the module's index
     *
     * @param index The index to remove the module at
     * @return The current <tt>ChatSection</tt>
     */
    public ChatSection removeModule(int index)
    {
        modules.remove(index);
        return this;
    }

    /**
     * Remove a <tt>ChatModule</tt> based off of the class of the module
     *
     * @param moduleClass The class of the module to remove
     * @return The current <tt>ChatSection</tt>
     */
    public ChatSection removeModule(Class<? extends ChatModule> moduleClass)
    {
        for(ChatModule module : modules)
        {
            if(moduleClass != module.getClass()) continue;
            modules.remove(module);
            return this;
        }
        return this;
    }

    /**
     * Remove a <tt>ChatModule</tt> based off of a reference to the module
     *
     * @param module The <tt>ChatModule</tt> to remove
     * @return The current <tt>ChatSection</tt>
     */
    public ChatSection removeModule(ChatModule module)
    {
        modules.remove(module);
        return this;
    }

    /**
     * See whether this <tt>ChatSection</tt> contains a <tt>ChatModule</tt> by reference
     *
     * @param module The <tt>ChatModule</tt> to search for
     * @return Whether the specified module was found or not
     */
    public boolean containsModule(ChatModule module)
    {
        return modules.contains(module);
    }

    /**
     * See whether this<tt>ChatSection</tt> contains a <tt>ChatModule</tt> based off of the
     * module's class
     *
     * @param moduleClass The class of the module to search for
     * @return Whether a module of the specified class was found or not
     */
    public boolean containsModule(Class<? extends ChatModule> moduleClass)
    {
        for(ChatModule module : modules)
        {
            if(moduleClass == module.getClass()) return true;
        }
        return false;
    }

    /**
     * Get a <tt>ChatModule</tt> from this <tt>ChatSection</tt> based off of the class of the module
     *
     * @param moduleClass The class of the <tt>ChatModule</tt> to get
     * @param <T>         The class type
     * @return The requested <tt>ChatModule</tt>, null if not found
     */
    public <T extends ChatModule> T getModule(Class<T> moduleClass)
    {
        for(ChatModule module : modules)
        {
            if(moduleClass == module.getClass()) return (T) module;
        }
        return null;
    }

    /**
     * Get a <tt>ChatModule</tt> from this <tt>ChatSection</tt> based off of the index of the module
     *
     * @param index The index to get the module from
     * @return The requested module
     */
    public ChatModule getModule(int index)
    {
        return modules.get(index);
    }

    /**
     * Add a String of text to this <tt>ChatSection</tt>
     *
     * @param text The String of text to add
     * @return The current <tt>ChatSection</tt>
     */
    public ChatSection addText(String text)
    {
        this.text += Colors.format(text);
        baked = false;
        return this;
    }

    /**
     * Set the String of text for this <tt>ChatSection</tt>
     *
     * @param text The String of text to set
     * @return The current <tt>ChatSection</tt>
     */
    public ChatSection setText(String text)
    {
        this.text = text;
        baked = false;
        return this;
    }

    /**
     * Cleaer the current text of this <tt>ChatSection</tt>
     *
     * @return The current <tt>ChatSection</tt>
     */
    public ChatSection clearText()
    {
        this.text = "";
        baked = false;
        return this;
    }

    /**
     * Bake this <tt>ChatSection</tt> into Bungee's chat API
     */
    protected void bake()
    {
        components = ChatConverter.getBaseComponentArray(text);
        modules.forEach(module -> module.onBake(this, components));
        baked = true;
    }

    /**
     * Print this <tt>ChatSection</tt> to a <tt>CommandSender</tt>
     *
     * @param sender The <tt>CommandSender</tt> that will receive the text
     * @return The current <tt>ChatSection</tt>
     */
    public ChatSection print(CommandSender sender)
    {
        if(!baked) bake();
        modules.forEach(module -> module.onPrint(this, sender));
        sender.spigot().sendMessage(components);
        return this;
    }
}
