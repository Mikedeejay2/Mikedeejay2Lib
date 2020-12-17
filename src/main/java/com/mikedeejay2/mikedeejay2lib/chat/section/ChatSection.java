package com.mikedeejay2.mikedeejay2lib.chat.section;

import com.mikedeejay2.mikedeejay2lib.PluginBase;
import com.mikedeejay2.mikedeejay2lib.chat.modules.ChatModule;
import com.mikedeejay2.mikedeejay2lib.util.chat.Chat;
import net.md_5.bungee.api.chat.BaseComponent;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

public class ChatSection
{
    protected final PluginBase plugin;
    protected List<ChatModule> modules;
    protected String text;
    protected boolean baked;
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
     * Add a String of text to this <tt>ChatSection</tt>
     *
     * @param text The String of text to add
     * @return The current <tt>ChatSection</tt>
     */
    public ChatSection addText(String text)
    {
        this.text += Chat.chat(text);
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
        components = plugin.chat().getBaseComponentArray(text);
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
