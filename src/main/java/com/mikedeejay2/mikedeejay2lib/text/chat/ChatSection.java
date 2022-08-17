package com.mikedeejay2.mikedeejay2lib.text.chat;

import com.mikedeejay2.mikedeejay2lib.BukkitPlugin;
import com.mikedeejay2.mikedeejay2lib.text.chat.modules.ChatModule;
import com.mikedeejay2.mikedeejay2lib.util.chat.ChatConverter;
import com.mikedeejay2.mikedeejay2lib.util.chat.Colors;
import net.md_5.bungee.api.chat.BaseComponent;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

/**
 * A section of text in a <code>ChatSlide</code>
 *
 * @see ChatSlide
 * @see ChatSystem
 *
 * @author Mikedeejay2
 */
public class ChatSection {
    /**
     * The {@link BukkitPlugin} instance
     */
    protected final BukkitPlugin plugin;

    /**
     * The list of Chat Modules for this section
     */
    protected List<ChatModule> modules;

    /**
     * The String of text for this section
     */
    protected String text;

    /**
     * Whether this section has been baked or not
     */
    protected boolean baked;

    /**
     * The baked array of components for this section
     */
    protected BaseComponent[] components;

    /**
     * Construct a new <code>ChatSection</code>
     *
     * @param plugin The {@link BukkitPlugin} instance
     */
    public ChatSection(BukkitPlugin plugin) {
        this.plugin = plugin;
        this.modules = new ArrayList<>();
        this.text = "";
        this.baked = false;
    }

    /**
     * Add a <code>ChatModule</code> to this <code>ChatSection</code>
     *
     * @param module The <code>ChatModule</code> to add
     * @return The current <code>ChatSection</code>
     */
    public ChatSection addModule(ChatModule module) {
        modules.add(module);
        baked = false;
        return this;
    }

    /**
     * Remove a <code>ChatModule</code> based off of the module's index
     *
     * @param index The index to remove the module at
     * @return The current <code>ChatSection</code>
     */
    public ChatSection removeModule(int index) {
        modules.remove(index);
        return this;
    }

    /**
     * Remove a <code>ChatModule</code> based off of the class of the module
     *
     * @param moduleClass The class of the module to remove
     * @return The current <code>ChatSection</code>
     */
    public ChatSection removeModule(Class<? extends ChatModule> moduleClass) {
        for(ChatModule module : modules) {
            if(moduleClass != module.getClass()) continue;
            modules.remove(module);
            return this;
        }
        return this;
    }

    /**
     * Remove a <code>ChatModule</code> based off of a reference to the module
     *
     * @param module The <code>ChatModule</code> to remove
     * @return The current <code>ChatSection</code>
     */
    public ChatSection removeModule(ChatModule module) {
        modules.remove(module);
        return this;
    }

    /**
     * See whether this <code>ChatSection</code> contains a <code>ChatModule</code> by reference
     *
     * @param module The <code>ChatModule</code> to search for
     * @return Whether the specified module was found or not
     */
    public boolean containsModule(ChatModule module) {
        return modules.contains(module);
    }

    /**
     * See whether this<code>ChatSection</code> contains a <code>ChatModule</code> based off of the
     * module's class
     *
     * @param moduleClass The class of the module to search for
     * @return Whether a module of the specified class was found or not
     */
    public boolean containsModule(Class<? extends ChatModule> moduleClass) {
        for(ChatModule module : modules) {
            if(moduleClass == module.getClass()) return true;
        }
        return false;
    }

    /**
     * Get a <code>ChatModule</code> from this <code>ChatSection</code> based off of the class of the module
     *
     * @param moduleClass The class of the <code>ChatModule</code> to get
     * @param <T>         The class type
     * @return The requested <code>ChatModule</code>, null if not found
     */
    public <T extends ChatModule> T getModule(Class<T> moduleClass) {
        for(ChatModule module : modules) {
            if(moduleClass == module.getClass()) return (T) module;
        }
        return null;
    }

    /**
     * Get a <code>ChatModule</code> from this <code>ChatSection</code> based off of the index of the module
     *
     * @param index The index to get the module from
     * @return The requested module
     */
    public ChatModule getModule(int index) {
        return modules.get(index);
    }

    /**
     * Add a String of text to this <code>ChatSection</code>
     *
     * @param text The String of text to add
     * @return The current <code>ChatSection</code>
     */
    public ChatSection addText(String text) {
        this.text += Colors.format(text);
        baked = false;
        return this;
    }

    /**
     * Set the String of text for this <code>ChatSection</code>
     *
     * @param text The String of text to set
     * @return The current <code>ChatSection</code>
     */
    public ChatSection setText(String text) {
        this.text = text;
        baked = false;
        return this;
    }

    /**
     * Cleaer the current text of this <code>ChatSection</code>
     *
     * @return The current <code>ChatSection</code>
     */
    public ChatSection clearText() {
        this.text = "";
        baked = false;
        return this;
    }

    /**
     * Bake this <code>ChatSection</code> into Bungee's chat API
     */
    protected void bake() {
        components = ChatConverter.getBaseComponentArray(text);
        modules.forEach(module -> module.onBake(this, components));
        baked = true;
    }

    /**
     * Print this <code>ChatSection</code> to a <code>CommandSender</code>
     *
     * @param sender The <code>CommandSender</code> that will receive the text
     * @return The current <code>ChatSection</code>
     */
    public ChatSection print(CommandSender sender) {
        if(!baked) bake();
        modules.forEach(module -> module.onPrint(this, sender));
        sender.spigot().sendMessage(components);
        return this;
    }
}
